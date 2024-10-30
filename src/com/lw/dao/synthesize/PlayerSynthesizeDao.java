package com.lw.dao.synthesize;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

public class PlayerSynthesizeDao extends DaoBase
{

	public int getPlayerSynthesize(int p_pk, int s_id)
	{
		int i = 0;
		String sql = "select id from u_synthesize_info where p_pk =" + p_pk
				+ " and s_id = " + s_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				i = rs.getInt("id");
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
		return i;
	}
}
