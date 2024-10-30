package com.lw.dao.gamesystemstatistics;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

public class SellInfoRecordDao extends DaoBase
{

	/** 插入一条玩家交易记录 */
	public void insertRecord(int p_pk_give, int p_pk_have, int prop_type,
			int prop_id, int num, long money)
	{
		String sql = "INSERT INTO game_sellinfo_record VALUES (null,"
				+ p_pk_give + "," + p_pk_have + "," + prop_type + "," + prop_id
				+ "," + num + "," + money + ",now())";
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

	/** 删除前七天的数据 */
	public void deleteRecord()
	{
		String sql = "DELETE FROM game_sellinfo_record WHERE s_date < DATE_ADD(now(), INTERVAL -7 DAY)";
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
