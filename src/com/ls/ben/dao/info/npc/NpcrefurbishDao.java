package com.ls.ben.dao.info.npc;

import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.npc.NpcrefurbishVO;
import com.ls.pub.db.DBConnection;

/**
 * 功能:npc刷新控制
 * 
 * @author 刘帅 12:40:14 AM
 */
public class NpcrefurbishDao extends DaoBase
{

	/**
	 * 判断该地点是否有boss
	 * 
	 * @param scene_id
	 * @return
	 */
	public boolean isHaveBoss(int scene_id)
	{
		boolean result = false;
		String sql = "SELECT npcrefurbish_id FROM npcrefurbish WHERE scene_id = "
				+ scene_id + " AND isBoss=1 LIMIT 1";
		logger.debug(sql);

		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				result = true;
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
		return result;
	}

	/**
	 * 当前地点NCP是否BOSS
	 * 
	 * @param scene_id
	 * @param npc_id
	 * @return
	 */
	public boolean isBoss(int scene_id, int npc_id)
	{
		boolean result = false;
		String sql = "SELECT npcrefurbish_id FROM npcrefurbish WHERE npc_id = '"
				+ npc_id
				+ "' AND  scene_id = "
				+ scene_id
				+ " AND isBoss=1 LIMIT 1";
		logger.debug(sql);

		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				result = true;
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
		return result;
	}

	/**
	 * 得到当前地点的npc刷新信息
	 * 
	 * @param scene_id
	 * @return
	 */
	public List<NpcrefurbishVO> getBySenceId(int scene_id)
	{
		List<NpcrefurbishVO> list = new ArrayList<NpcrefurbishVO>();
		NpcrefurbishVO vo = null;
		String sql = "SELECT * FROM npcrefurbish WHERE scene_id = " + scene_id
				+ " AND ( (refurbish_time_ks='' AND day_time_ks='') "
				+ // 无刷新时间限制
				"or (curtime()>TIME(day_time_ks) AND curtime()<TIME(day_time_js)) "
				+ // 判断相对时间限制
				"or (now()>TIMESTAMP(refurbish_time_ks) AND now()<TIMESTAMP(refurbish_time_js)) )";// 判断绝对时间限制
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new NpcrefurbishVO();
				vo.setId(rs.getInt("npcrefurbish_id"));
				vo.setSceneId(scene_id);
				vo.setNpcId(rs.getInt("npc_id"));
				vo.setAttackswitch(rs.getInt("refurbish_attackswitch"));
				vo.setNumber(rs.getString("refurbish_number"));
				vo.setProbability(rs.getInt("refurbish_probability"));

				vo.setIsBoss(rs.getInt("isBoss"));
				vo.setIsDead(rs.getInt("isDead"));
				vo.setDeadTime(rs.getTimestamp("dead_time"));

				vo.setWeekStr(rs.getString("week_str"));
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

	/** ***得到一个区域可刷新的NPC信息 ***** */

	public List<NpcrefurbishVO> getNPCsBySenceId(int scene_id)
	{
		List<NpcrefurbishVO> list = new ArrayList<NpcrefurbishVO>();
		NpcrefurbishVO vo = null;
		String sql = "SELECT * FROM npcrefurbish WHERE scene_id IN (SELECT scene_id FROM scene WHERE scene_mapqy=(SELECT scene_mapqy FROM scene WHERE scene_id = "+scene_id+")) AND refurbish_probability>=10 GROUP BY npc_id";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new NpcrefurbishVO();
				vo.setId(rs.getInt("npcrefurbish_id"));
				vo.setSceneId(scene_id);
				vo.setNpcId(rs.getInt("npc_id"));
				vo.setAttackswitch(rs.getInt("refurbish_attackswitch"));
				vo.setNumber(rs.getString("refurbish_number"));
				vo.setProbability(rs.getInt("refurbish_probability"));

				vo.setIsBoss(rs.getInt("isBoss"));
				vo.setIsDead(rs.getInt("isDead"));
				vo.setDeadTime(rs.getTimestamp("dead_time"));

				vo.setWeekStr(rs.getString("week_str"));
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

	/**
	 * 得到当前地点npc已死的所有npc信息
	 * 
	 * @param scene_id
	 * @return
	 */
	public List<NpcrefurbishVO> getDeadBySenceId(int scene_id)
	{
		List<NpcrefurbishVO> list = new ArrayList<NpcrefurbishVO>();
		NpcrefurbishVO vo = null;
		String sql = "SELECT distinct npc_id,dead_time ,isBoss FROM npcrefurbish WHERE scene_id = "
				+ scene_id + " AND isDead=1";
		logger.debug(sql);

		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new NpcrefurbishVO();
				vo.setSceneId(scene_id);
				vo.setNpcId(rs.getInt("npc_id"));
				vo.setDeadTime(rs.getTimestamp("dead_time"));
				vo.setIsBoss(rs.getInt("isBoss"));
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

	/**
	 * 更新死亡时间
	 * 
	 * @param npc_id
	 * @param scene_id
	 */
	public void setDeadState(int npc_id, int scene_id)
	{
		String sql = "UPDATE npcrefurbish SET dead_time = now(),isDead=1 WHERE scene_id = "
				+ scene_id + " AND npc_id = " + npc_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);
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

	/**
	 * 判断有冷却时间的npc是否已死
	 */
	public boolean isDead(int npc_id, int scene_id)
	{
		boolean isDead = false;
		String sql = "SELECT isDead FROM npcrefurbish WHERE scene_id="
				+ scene_id + " AND npc_id=" + npc_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				if (rs.getInt("isDead") == 1)
				{
					isDead = true;
				}
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
		return isDead;
	}

	/**
	 * 复活冷却的npc
	 */
	public void reviveNPC(int npc_id, int scene_id)
	{
		String sql = "UPDATE npcrefurbish SET isDead=0 WHERE scene_id="
				+ scene_id + " AND npc_id=" + npc_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);
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
}
