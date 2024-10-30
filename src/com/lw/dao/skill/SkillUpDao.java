package com.lw.dao.skill;

import java.sql.SQLException;

import com.ls.ben.cache.staticcache.skill.SkillCache;
import com.ls.ben.dao.info.skill.SkillDao;
import com.ls.pub.db.DBConnection;

public class SkillUpDao extends SkillDao
{

	/** ******根据技能ID得到技能组ID******* */
	public int getSkGroup(int sk_id, int p_pk)
	{
		int sk_group = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "SELECT sk_group from u_skill_info where sk_id =" + sk_id
				+ " and p_pk = " + p_pk;
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				sk_group = rs.getInt("sk_group");
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return sk_group;

	}

	/** *******根据玩家ID得到玩家技能熟练度*********** */
	public int getPlayerSleight(int sk_id, int p_pk)
	{
		int sk_sleight = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "SELECT sk_sleight from u_skill_info where sk_id ="
				+ sk_id + " and p_pk = " + p_pk;
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				sk_sleight = rs.getInt("sk_sleight");
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return sk_sleight;
	}

	/** *******根据技能ID得到技能下一级升级需要的熟练度*********** */
	public int getLevelSleight(int sk_id)
	{
		int sk_next_sleight = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		String sql = "SELECT sk_next_sleight from skill where sk_id =" + sk_id;
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				sk_next_sleight = rs.getInt("sk_next_sleight");
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return sk_next_sleight;
	}

	/** *******根据技能组ID和熟练度得到下一级技能ID********** */
	public int getNextSkill(int sk_sleight, int sk_group)
	{
		int sk_next_id = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		String sql = "SELECT sk_id from skill where sk_sleight <= "+sk_sleight+" and sk_group = "+sk_group+" order by sk_id desc limit 1";
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				sk_next_id = rs.getInt("sk_id");
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return sk_next_id;

	}

	/** **更新技能名称******* */
	public void updateSkillName(int sk_next_id, int sk_group, int p_pk)
	{

		SkillCache skillCaChe = new SkillCache();		
		String sk_name = skillCaChe.getNameById(sk_next_id);
		
		String sql = "update u_skill_info set  sk_id = " + sk_next_id+" ,sk_name = '" + sk_name
				+ "' where sk_group = " + sk_group + " and p_pk = " + p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}

	/** *****更新技能ID************ */
	public void updateSkillID1(int sk_next_id, int sk_group, int p_pk)
	{

		String sql = "update u_skill_info set sk_id = " + sk_next_id
				+ " where sk_group = " + sk_group + " and p_pk = " + p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}

	/** *********得到更新的s_pk*********** */

	public int getSpk(int p_pk, int sk_id)
	{
		int s_pk = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "SELECT s_pk from u_skill_info where p_pk  =" + p_pk
				+ " and sk_id = " + sk_id;
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				s_pk = rs.getInt("s_pk");
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return s_pk;
	}

	/** ********更换掉快捷键上的技能名称**************** */
	public void changeName(int s_pk, int sk_next_id)
	{
		SkillCache skillCaChe = new SkillCache();		
		String sk_name = skillCaChe.getNameById(sk_next_id);
		
		String sql = "update u_shortcut_info set sc_display = '" + sk_name
				+ "' where sc_type = 1 and  operate_id = " + s_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}
}
