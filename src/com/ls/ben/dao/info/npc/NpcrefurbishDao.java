package com.ls.ben.dao.info.npc;

import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.npc.NpcrefurbishVO;
import com.ls.pub.db.DBConnection;

/**
 * ����:npcˢ�¿���
 * 
 * @author ��˧ 12:40:14 AM
 */
public class NpcrefurbishDao extends DaoBase
{

	/**
	 * �жϸõص��Ƿ���boss
	 * 
	 * @param scene_id
	 * @return
	 */
	public boolean isHaveBoss(int scene_id)
	{
		boolean result = false;
		String sql = "select npcrefurbish_id from npcrefurbish where scene_id="
				+ scene_id + " and isBoss=1 limit 1";
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
	 * ��ǰ�ص�NCP�Ƿ�BOSS
	 * 
	 * @param scene_id
	 * @param npc_id
	 * @return
	 */
	public boolean isBoss(int scene_id, int npc_id)
	{
		boolean result = false;
		String sql = "select npcrefurbish_id from npcrefurbish where npc_id = '"
				+ npc_id
				+ "' and  scene_id="
				+ scene_id
				+ " and isBoss=1 limit 1";
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
	 * �õ���ǰ�ص��npcˢ����Ϣ
	 * 
	 * @param scene_id
	 * @return
	 */
	public List<NpcrefurbishVO> getBySenceId(int scene_id)
	{
		List<NpcrefurbishVO> list = new ArrayList<NpcrefurbishVO>();
		NpcrefurbishVO vo = null;
		String sql = "select * from npcrefurbish where scene_id=" + scene_id
				+ " and ( (refurbish_time_ks='' and day_time_ks='') "
				+ // ��ˢ��ʱ������
				"or (curtime()>TIME(day_time_ks) and curtime()<TIME(day_time_js)) "
				+ // �ж����ʱ������
				"or (now()>TIMESTAMP(refurbish_time_ks) and now()<TIMESTAMP(refurbish_time_js)) )";// �жϾ���ʱ������
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

	/** ***�õ�һ�������ˢ�µ�NPC��Ϣ ***** */

	public List<NpcrefurbishVO> getNPCsBySenceId(int scene_id)
	{
		List<NpcrefurbishVO> list = new ArrayList<NpcrefurbishVO>();
		NpcrefurbishVO vo = null;
		String sql = "select * from npcrefurbish where scene_id in (select scene_id from scene where scene_mapqy=(select scene_mapqy from scene where scene_id="+scene_id+")) and refurbish_probability>=10 group by npc_id";
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
	 * �õ���ǰ�ص�npc����������npc��Ϣ
	 * 
	 * @param scene_id
	 * @return
	 */
	public List<NpcrefurbishVO> getDeadBySenceId(int scene_id)
	{
		List<NpcrefurbishVO> list = new ArrayList<NpcrefurbishVO>();
		NpcrefurbishVO vo = null;
		String sql = "select distinct npc_id,dead_time ,isBoss from npcrefurbish where scene_id="
				+ scene_id + " and isDead=1";
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
	 * ��������ʱ��
	 * 
	 * @param npc_id
	 * @param scene_id
	 */
	public void setDeadState(int npc_id, int scene_id)
	{
		String sql = "update npcrefurbish set dead_time = now(),isDead=1 where scene_id="
				+ scene_id + " and npc_id=" + npc_id;
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
	 * �ж�����ȴʱ���npc�Ƿ�����
	 */
	public boolean isDead(int npc_id, int scene_id)
	{
		boolean isDead = false;
		String sql = "select isDead from npcrefurbish where scene_id="
				+ scene_id + " and npc_id=" + npc_id;
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
	 * ������ȴ��npc
	 */
	public void reviveNPC(int npc_id, int scene_id)
	{
		String sql = "update npcrefurbish set isDead=0 where scene_id="
				+ scene_id + " and npc_id=" + npc_id;
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
