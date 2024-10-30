package com.ls.ben.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.ls.pub.db.DBConnection;


/**
 * 功能:dao的父类
 * @author  刘帅
 * 5:30:10 PM
 */
public class DaoBase {
	protected Logger logger = Logger.getLogger("log.dao");
	protected Connection conn = null;
	protected Statement stmt = null;
	protected PreparedStatement ps = null;
	protected ResultSet rs = null;

	/**
	 * 执行updateSql
	 * @param update_sql
	 */
	public int executeUpdateSql( String update_sql )
	{
		int result = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug("sql="+update_sql);
		try
		{
			stmt = conn.createStatement();
			result = stmt.executeUpdate(update_sql);
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
}
