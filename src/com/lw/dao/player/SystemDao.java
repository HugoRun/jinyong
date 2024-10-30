package com.lw.dao.player;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

public class SystemDao extends DaoBase
{

	/**
	 * 获得系统限制人数
	 * 
	 * @return
	 */
	public int getSystemOnlineNum()
	{
		int online_num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		String sql = "SELECT s_player from system ";
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				online_num = rs.getInt("s_player");
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
		return online_num;
	}
	
	/**获得万能密码*/
	public String getAllKey(){
		String all_key = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		String sql = "SELECT all_key from system ";
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				all_key = rs.getString("all_key");
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
		return all_key;
	}
	
	/**
	 * pc_islogininfoname 防PC登陆白名单ID 0 开 1关 
	 * 
	 * @return
	 */
	public int getSystemPcIslogininfoname()
	{
		int pc_islogininfoname = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		String sql = "SELECT pc_islogininfoname from system ";
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				pc_islogininfoname = rs.getInt("pc_islogininfoname");
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
		return pc_islogininfoname;
	}
	
	
	/**
	 * pc_black  防PC登陆黑名单IP 0 开 1关 
	 * 
	 * @return
	 */
	public int getSystemPcBlack()
	{
		int pc_black = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		String sql = "SELECT pc_black from system ";
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				pc_black = rs.getInt("pc_black");
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
		return pc_black;
	}
	
	/**
	 * pc_ua 防PC登陆UA 0 开 1关
	 * 
	 * @return
	 */
	public int getSystemPcUa()
	{
		int pc_ua = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		String sql = "SELECT pc_ua from system ";
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				pc_ua = rs.getInt("pc_ua");
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
		return pc_ua;
	}
	
	/**
	 * pc_link_number 防PC登陆点击次数3次 0 开 1关 
	 * 
	 * @return
	 */
	public int getSystemPcLinkNumber()
	{
		int pc_link_number = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		String sql = "SELECT pc_link_number from system ";
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				pc_link_number = rs.getInt("pc_link_number");
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
		return pc_link_number;
	}
}
