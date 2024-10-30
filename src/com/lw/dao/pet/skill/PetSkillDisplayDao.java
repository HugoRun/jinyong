package com.lw.dao.pet.skill;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

public class PetSkillDisplayDao extends DaoBase
{
	// ����Skill_ID�õ����ܵ�����
	public String getPlayerPetSkillDisplay(int skill_id)
	{
		String display = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		Connection conn = dbConn.getConn();
		String sql = "select pet_skill_bewrite from pet_skill where pet_skill_id = "
				+ skill_id;
		logger.debug(sql);
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				display = rs.getString("pet_skill_bewrite");
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
		return display;
	}

	// ����Skill_ID�õ����ܵ�����
	public String getPlayerPetSkillName(int skill_id)
	{
		String display = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		Connection conn = dbConn.getConn();
		String sql = "select pet_skill_name from pet_skill where pet_skill_id = "
				+ skill_id;
		logger.debug(sql);
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				display = rs.getString("pet_skill_name");
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
		return display;
	}
}
