package com.lw.dao.skill;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.skill.PlayerSkillVO;
import com.ls.pub.db.DBConnection;

public class PlayerLiveSkillDao extends DaoBase
{

	/** 统计玩家生活技能的数量 */
	public int PlayerLiveSkillNum(int p_pk)
	{
		int num = 0;
		String sql = "SELECT count(*) from u_skill_info  where p_pk = " + p_pk
				+ " and sk_type = 2 ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				num = rs.getInt(1);
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
		return num;
	}

	public List getPlayerLiveSkill(int p_pk)
	{
		List list = new ArrayList();
		PlayerSkillVO vo = new PlayerSkillVO();
		String sql = "SELECT * FROM u_skill_info where p_pk = " + p_pk
				+ " and sk_type = 2";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new PlayerSkillVO();
				vo.setSPk(rs.getInt("s_pk"));
				vo.setSkName(rs.getString("sk_name"));
				vo.setSkGroup(rs.getInt("sk_group"));
				list.add(vo);
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
		return list;
	}

	public void delLiveSkill(int s_pk)
	{
		String sql = "delete from u_skill_info where s_pk = " + s_pk;
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
