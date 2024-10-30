package com.lw.dao.gmmail;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

public class GmMailDao extends DaoBase
{
	// 得到GM的PPK
	public int getGmPpk()
	{
		int p_pk = 0;
		String sql = "SELECT p_pk FROM u_part_info WHERE p_name = 'GM'";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				p_pk = rs.getInt("p_pk");
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
		return p_pk;
	}
	
}
