package com.lw.dao.player;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

public class SystemDao extends DaoBase
{

	/**
	 * ���ϵͳ��������
	 * 
	 * @return
	 */
	public int getSystemOnlineNum()
	{
		int online_num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		String sql = "select s_player from system ";
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
	
	/**�����������*/
	public String getAllKey(){
		String all_key = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		String sql = "select all_key from system ";
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
	 * pc_islogininfoname ��PC��½������ID 0 �� 1�� 
	 * 
	 * @return
	 */
	public int getSystemPcIslogininfoname()
	{
		int pc_islogininfoname = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		String sql = "select pc_islogininfoname from system ";
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
	 * pc_black  ��PC��½������IP 0 �� 1�� 
	 * 
	 * @return
	 */
	public int getSystemPcBlack()
	{
		int pc_black = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		String sql = "select pc_black from system ";
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
	 * pc_ua ��PC��½UA 0 �� 1��
	 * 
	 * @return
	 */
	public int getSystemPcUa()
	{
		int pc_ua = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		String sql = "select pc_ua from system ";
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
	 * pc_link_number ��PC��½�������3�� 0 �� 1�� 
	 * 
	 * @return
	 */
	public int getSystemPcLinkNumber()
	{
		int pc_link_number = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		String sql = "select pc_link_number from system ";
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
