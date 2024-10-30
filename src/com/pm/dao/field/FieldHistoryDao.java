package com.pm.dao.field;

import java.sql.SQLException;

import com.ben.dao.info.partinfo.PartInfoDAO;
import com.ls.ben.dao.DaoBase;
import com.ls.ben.dao.map.SceneDao;
import com.ls.ben.dao.menu.OperateMenuDao;
import com.ls.ben.vo.map.SceneVO;
import com.ls.pub.constant.MenuType;
import com.ls.pub.db.DBConnection;
import com.ls.web.service.room.RoomService;
import com.pm.constant.FinalNumber;

public class FieldHistoryDao extends DaoBase {
	/**
	 * 获得上场战斗的胜利者
	 * @param field_type 战场的编号,以示区分
	 * @return
	 */
	public int getPreviousVictor(String field_type)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "select fh_victor from s_field_history where fh_type = "+field_type+" order by fh_id desc limit 1";
		logger.debug("获得上场战斗的胜利者="+sql);
		int victor = 0;
		try
		{
			stmt = conn.createStatement();
		
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				victor = rs.getInt("fh_victor");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			
			dbConn.closeConn();
		}
		return victor;
	}
	
	/**
	 * 获得上场战斗的胜利者
	 * @param field_type 战场的编号,以示区分
	 * @return
	 */
	public int getPreviousVictorByScene(String sceneID)
	{
		RoomService roomService = new RoomService();
		int map_id = roomService.getMapId(Integer.parseInt(sceneID));
		
		OperateMenuDao oepartmenudao = new OperateMenuDao();
		String menu_operate3 = oepartmenudao.getFieldTypeByFieldManager(MenuType.FIELDMANAGER, map_id);
		
		return getPreviousVictor(menu_operate3);
	}
	
	/**
	 * 获得上场战斗的场次序号
	 * @return
	 */
	public int getPreviousID( String field_type)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "select fh_sequence from s_field_history where fh_type = "+field_type+" order by fh_create_time desc limit 1";
		logger.debug("获得上场战斗的场次序号="+sql);
		int fh_id = 0;
		try
		{
			stmt = conn.createStatement();
		
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				fh_id = rs.getInt("fh_sequence");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			
			dbConn.closeConn();
		}
		return fh_id;
	}

	/**
	 * 把这场战斗的胜利者记录下来以供查询
	 * @param fh_victor
	 * @param date
	 * @param field_type
	 * @param fh_sequence
	 */
	public void insertIntoHistory(int fh_victor,String date,String field_type,int fh_sequence)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "insert into s_field_history value (null,"+fh_sequence+",null,'"+date+"',"+fh_victor+","+field_type+",now())";
		logger.debug("把这场战斗的胜利者记录下来以供查询="+sql);
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);		
			stmt.close();
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		
	}
	
	/**
	 * 检查其是否还在战场内
	 * 返回0代表不在战场内, 1代表在战场内
	 * @param pPk
	 * @return
	 */
	
	public int checkInFieldByPPk(int pPk) {
		
		int field_barea = FinalNumber.FIELDBAREA;
		//这是采取跨库查询的方法写的,效率高, 但移植性差
		//String sql = "select count(1) as has_exist from jygame_user_test.u_part_info where p_pk = "+pPk+" and p_map in " +
		//		"(select scene_ID from jygame_test.scene,jygame_test.map,jygame_test.barea where " +
		//		"map.map_from = barea.barea_ID and scene.scene_mapqy = map.map_ID and barea.barea_ID = "+field_barea+")";
		
		PartInfoDAO partInfoDao = new PartInfoDAO();
		int p_map = partInfoDao.getPartMap(pPk);

		String sql = "select count(1) as has_exist from scene where scene_ID = "+p_map+" and "+p_map+" in (select scene_ID from scene,map, " +
				"barea where map.map_from = barea.barea_ID " +
				"and scene.scene_mapqy = map.map_ID and barea_ID = "+field_barea+" ) ";
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		logger.debug("检查其是否还在战场内="+sql);
		int has_exist = 0;
		try
		{
			stmt = conn.createStatement();
		
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				has_exist = rs.getInt("has_exist");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			
			dbConn.closeConn();
		}
		return has_exist;
		
	}

	/**
	 * 获得某一阵营的胜利次数
	 * @param camp
	 * @return
	 */
	public int getCampWinNum(int camp)
	{
		
		String sql = "select count(1) as win_num from s_field_history where fh_victor = "+camp;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug("获得某一阵营的胜利次数="+sql);
		int has_exist = 0;
		try
		{
			stmt = conn.createStatement();
		
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				has_exist = rs.getInt("win_num");
			}
			
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			
			dbConn.closeConn();
		}
		return has_exist;
		
	}

	
}
