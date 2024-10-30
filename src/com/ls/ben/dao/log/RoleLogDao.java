package com.ls.ben.dao.log;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

/**
 * @author ls 功能:角色升级监控日志 Feb 3, 2009
 */
public class RoleLogDao extends DaoBase
{

	/**
	 * 插入日志
	 * 
	 * @param p_pk
	 * @param content
	 */
	public void insert(int p_pk, String role_name, String content)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_LOG_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "INSERT INTO u_upgrade_log (p_pk,role_name,content,createtime) values ("
					+ p_pk + ",'" + role_name + "','" + content + "',now())";
			logger.debug(sql);
			stmt = conn.createStatement();
			stmt.execute(sql);
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

	}

	/**
	 * 插入日志
	 * 
	 * @param p_pk
	 * @param content
	 */
	public void insertRecordMoneyLog(int p_pk, String role_name,
			String old_num, String new_num, String content)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_LOG_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "INSERT INTO u_log_money values (null,'" + p_pk
					+ "','" + role_name + "','" + old_num + "','" + new_num
					+ "','" + content + "',now())";
			logger.debug(sql);
			stmt = conn.createStatement();
			stmt.execute(sql);
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

	}

	/**
	 * 插入日志
	 * 
	 * @param p_pk
	 * @param content
	 */
	public void insertRecordExpLog(int p_pk, String role_name, String old_num,
			String new_num, String content)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_LOG_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "INSERT INTO u_log_exp values (null,'" + p_pk + "','"
					+ role_name + "','" + old_num + "','" + new_num + "','"
					+ content + "',now())";
			logger.debug(sql);
			stmt = conn.createStatement();
			stmt.execute(sql);
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

	}

	/**
	 * 插入日志
	 * 
	 * @param p_pk
	 * @param content
	 */
	public void insertRecordYBLog(int p_pk, String role_name, String old_num,
			String new_num, String content)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_LOG_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "INSERT INTO u_log_yb values (null,'" + p_pk + "','"
					+ role_name + "','" + old_num + "','" + new_num + "','"
					+ content + "',now())";
			logger.debug(sql);
			stmt = conn.createStatement();
			stmt.execute(sql);
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

	}

	/**
	 * 插入日志
	 * 
	 * @param p_pk
	 * @param content
	 */
	public void insertRecordPlayerLog(int p_pk, String role_name,
			String old_num, String new_num, String content)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_LOG_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "INSERT INTO u_log_player values (null,'" + p_pk
					+ "','" + role_name + "','" + old_num + "','" + new_num
					+ "','" + content + "',now())";
			logger.debug(sql);
			stmt = conn.createStatement();
			stmt.execute(sql);
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

	}
}
