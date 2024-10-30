package com.lw.dao.gamesystemstatistics;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

public class SellInfoRecordDao extends DaoBase
{

	/** ����һ����ҽ��׼�¼ */
	public void insertRecord(int p_pk_give, int p_pk_have, int prop_type,
			int prop_id, int num, long money)
	{
		String sql = "insert into game_sellinfo_record values (null,"
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

	/** ɾ��ǰ��������� */
	public void deleteRecord()
	{
		String sql = "delete from game_sellinfo_record where s_date < DATE_ADD(now(), INTERVAL -7 DAY)";
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