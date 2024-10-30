/**
 * 
 */
package com.ls.ben.dao.info.npc;

import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.npc.NpcAttackVO;
import com.ls.ben.vo.info.npc.NpcskillVO;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;

/**
 * 功能:
 * 
 * @author 刘帅
 * 
 * 7:23:19 PM
 */
public class NpcskilDao extends DaoBase
{
	/**
	 * npc得到自己的五行属性
	 * 
	 * @param npc_id
	 * @return
	 */
	public void loadNPCWx(NpcAttackVO npc)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{

			String sql = "SELECT npcski_wx FROM npcskill WHERE npc_id = "
					+ npc.getNpcID() + " LIMIT 1";
			logger.debug(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				npc.setWx(rs.getInt("npcski_wx"));
			}
			rs.close();
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();

		}
	}

	public List getSkillByNpcID(int npc_id)
	{
		ArrayList result = new ArrayList();
		NpcskillVO vo = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{

			String sql = "SELECT * FROM npcskill WHERE npc_id = " + npc_id;
			logger.debug(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new NpcskillVO();
				vo.setNpcId(rs.getInt("npcski_id"));
				vo.setNpcskiName(StringUtil.isoToGBK(rs
						.getString("npcski_name")));
				vo.setNpcskiWx(rs.getInt("npcski_wx"));
				vo.setNpcskiWxInjure(rs.getInt("npcski_wx_injure"));
				vo.setNpcskiInjureDa(rs.getInt("npcski_injure_da"));
				vo.setNpcskiInjureXiao(rs.getInt("npcski_injure_xiao"));
				vo.setProbability(rs.getInt("npcski_probability"));
				result.add(vo);
			}
			rs.close();
			stmt.close();
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();

		}

		return result;
	}

	/** 得到NPC的属性数值 */
	public List<NpcskillVO> getWxNum(int npc_id)
	{
		List<NpcskillVO> list = new ArrayList<NpcskillVO>();
		NpcskillVO vo = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{

			String sql = "SELECT npcski_wx ,npcski_wx_injure FROM npcskill WHERE npc_id = "
					+ npc_id;
			logger.debug(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new NpcskillVO();
				vo.setNpcskiWx(rs.getInt("npcski_wx"));
				vo.setNpcskiWxInjure(rs.getInt("npcski_wx_injure"));
				list.add(vo);
			}
			rs.close();
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();

		}

		return list;
	}

}
