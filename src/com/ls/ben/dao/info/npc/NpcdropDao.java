package com.ls.ben.dao.info.npc;

import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.npc.NpcdropVO;
import com.ls.pub.db.DBConnection;
import com.ls.web.service.log.DataErrorLog;


public class NpcdropDao extends DaoBase {
	/**
	 * 找到NPC的掉落物
	 * npc掉落查询，限制最多掉7个，按概率降幂和随机排序
	 * @param npc_ID
	 * @return
	 */
	public List<NpcdropVO> getNpcdropsByNpcID( int npc_ID,String task_condition ) {
		ArrayList<NpcdropVO> list = new ArrayList<NpcdropVO>();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {

			String sql = "select * from npcdrop where npc_ID=" + npc_ID + " and npcdrop_taskid in ("+task_condition+") " +
					" and ( (begin_time='' and begin_day='') " +//无刷新时间限制
					"or (curtime()>TIME(begin_time) and curtime()<TIME(end_time)) " +//判断相对时间限制
					"or (now()>TIMESTAMP(begin_day) and now()<TIMESTAMP(end_day)) )"+//判断绝对时间限制
					" order by rand()";
			logger.debug(sql);
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql); 
			NpcdropVO vo = null;
			while (rs.next()) {
				vo = new NpcdropVO();
				vo.setNpcdropID(rs.getInt("npcdrop_ID"));
				vo.setNpcID(rs.getInt("npc_ID"));
				vo.setGoodsType(rs.getInt("goods_type"));
				vo.setGoodsId(rs.getInt("goods_id"));
				vo.setGoodsName(rs.getString("goods_name"));
				vo.setNpcdropProbability(rs.getInt("npcdrop_probability"));
				vo.setNpcdropLuck(rs.getInt("npcdrop_luck"));
				vo.setNpcDropNumStr(rs.getString("npcdrop_num"));
				vo.setNpcDropImprot(rs.getInt("npcdrop_importance"));
				vo.setQuality(rs.getInt("quality"));
				vo.setWeekStr(rs.getString("week_str"));
				list.add(vo);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			DataErrorLog.debugData("NpcdropDao.getNpcdropsByNpcID:掉落数据错误，条件npc_ID="+npc_ID+";task_condition="+task_condition);
		} finally {
			dbConn.closeConn();
		}
		return list;
	}
	
	public NpcdropVO getNpcdropsById(String npc_drop_id) {
		String sql = "select * from npcdrop where npcdrop_ID='"+npc_drop_id+"'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		NpcdropVO vo = null;
		try {
			logger.debug(sql);
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql); 
			while (rs.next()) {
				vo = new NpcdropVO();
				vo.setNpcdropID(rs.getInt("npcdrop_ID"));
				vo.setNpcID(rs.getInt("npc_ID"));
				vo.setGoodsType(rs.getInt("goods_type"));
				vo.setGoodsId(rs.getInt("goods_id"));
				vo.setGoodsName(rs.getString("goods_name"));
				vo.setNpcdropProbability(rs.getInt("npcdrop_probability"));
				vo.setNpcdropLuck(rs.getInt("npcdrop_luck"));
				vo.setNpcDropNumStr(rs.getString("npcdrop_num"));
				vo.setNpcDropImprot(rs.getInt("npcdrop_importance")); 
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return vo;
	}
	
	
	

	public void getNpcdropAdd11(String npcID, String goodsType, String goodsId,
			String npcdropProbability, String npcdropLuck) {
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {

			String sql = "insert into npcdrop values(null,'" + npcID + "','"
					+ goodsType + "','" + goodsId + "','" + npcdropProbability
					+ "','" + npcdropLuck + "')";
			logger.debug(sql);
			stmt = conn.createStatement(); 
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
	}

	public void getNpcdropUpdate1(String npcID, String goodsType,
			String goodsId, String npcdropProbability, String npcdropLuck,
			String id) {
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {

			String sql = "update npcdrop set npc_ID='" + npcID
					+ "',goods_type='" + goodsType + "',goods_id='" + goodsId
					+ "',npcdrop_probability='" + npcdropProbability
					+ "',npcdrop_luck='" + npcdropLuck + "' where npcdrop_ID="
					+ id;
			logger.debug(sql);
			stmt = conn.createStatement(); 
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
	}

	//刮刮了专用方法
	public List<NpcdropVO> getNpcdropsByScratchTicket( String npc_ID,String task_condition ) {
		ArrayList<NpcdropVO> list = new ArrayList<NpcdropVO>();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {

			String sql = "select * from npcdrop where npc_ID in (" + npc_ID + ") and npcdrop_taskid in ("+task_condition+
					") order by rand() limit 11";
			logger.debug(sql);
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql); 
			NpcdropVO vo = null;
			while (rs.next()) {
				vo = new NpcdropVO();
				vo.setNpcdropID(rs.getInt("npcdrop_ID"));
				vo.setNpcID(rs.getInt("npc_ID"));
				vo.setGoodsType(rs.getInt("goods_type"));
				vo.setGoodsId(rs.getInt("goods_id"));
				vo.setGoodsName(rs.getString("goods_name"));
				vo.setNpcdropProbability(rs.getInt("npcdrop_probability"));
				vo.setNpcdropLuck(rs.getInt("npcdrop_luck"));
				vo.setNpcDropNumStr(rs.getString("npcdrop_num"));
				vo.setNpcDropImprot(rs.getInt("npcdrop_importance"));
				vo.setQuality(rs.getInt("quality"));
				list.add(vo);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return list;
	}

	//拉霸(虎运)专用方法
	public List<NpcdropVO> getNpcdropsForLaBa( String npc_ID) {
		ArrayList<NpcdropVO> list = new ArrayList<NpcdropVO>();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {

			String sql = "select * from npcdrop where npc_ID in (" + npc_ID + ") order by rand() limit 11";
			logger.debug(sql);
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql); 
			NpcdropVO vo = null;
			if (rs.next()) {
				vo = new NpcdropVO();
				vo.setNpcdropID(rs.getInt("npcdrop_ID"));
				vo.setNpcID(rs.getInt("npc_ID"));
				vo.setGoodsType(rs.getInt("goods_type"));
				vo.setGoodsId(rs.getInt("goods_id"));
				vo.setGoodsName(rs.getString("goods_name"));
				vo.setNpcdropProbability(rs.getInt("npcdrop_probability"));
				vo.setNpcdropLuck(rs.getInt("npcdrop_luck"));
				vo.setNpcDropNumStr(rs.getString("npcdrop_num"));
				vo.setNpcDropImprot(rs.getInt("npcdrop_importance"));
				vo.setQuality(rs.getInt("quality"));
				list.add(vo);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return list;
	}
	public List<NpcdropVO> getNpcDropByNpcId(int npcId)
	{
		ArrayList<NpcdropVO> list = new ArrayList<NpcdropVO>();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {

			String sql = "select*From npcdrop where npc_ID="+npcId+" and npcdrop_taskid=0";
			logger.debug(sql);
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql); 
			NpcdropVO vo = null;
			while(rs.next()) {
				vo = new NpcdropVO();
				vo.setNpcdropID(rs.getInt("npcdrop_ID"));
				vo.setNpcID(rs.getInt("npc_ID"));
				vo.setGoodsType(rs.getInt("goods_type"));
				vo.setGoodsId(rs.getInt("goods_id"));
				vo.setGoodsName(rs.getString("goods_name"));
				vo.setNpcdropProbability(rs.getInt("npcdrop_probability"));
				vo.setNpcdropLuck(rs.getInt("npcdrop_luck"));
				vo.setNpcDropNumStr(rs.getString("npcdrop_num"));
				vo.setNpcDropImprot(rs.getInt("npcdrop_importance"));
				vo.setQuality(rs.getInt("quality"));
				list.add(vo);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return list;
	}
}
