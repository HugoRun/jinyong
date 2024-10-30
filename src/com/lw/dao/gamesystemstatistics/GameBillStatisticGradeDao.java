package com.lw.dao.gamesystemstatistics;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

public class GameBillStatisticGradeDao extends DaoBase
{
	// 得到充值玩家等级的人次
	public int getChongzhiGrade(int min_grade, int max_grade, String time)
	{
		int num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		String sql = "";
		logger.debug(sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				num = rs.getInt(1);
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
		return num;
	}

	// 插入统计表
	public void insertChongzhiGrade(int one, int two, int three, int four,
			int five, int six, int seven)
	{
		String sql = "";
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
