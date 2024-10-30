package com.lw.dao.player;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

public class PlayerStaDao extends DaoBase
{
	public void insertPlayerSta(int u_pk, String channel_id)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "insert into u_passport_info set create_time = now(), channel_id = '" + channel_id + "' , u_pk = " + u_pk;
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (Exception e)
		{
		}
		finally
		{
			dbConn.closeConn();
		}
	}
}
