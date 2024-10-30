package com.lw.dao.player;

import java.util.Date;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

public class PlayerEnvelopPpkDao extends DaoBase
{

	/** 查询是否被封号 */
	public boolean getPlayerFromEnvelop(int p_pk)
	{
		boolean x = false;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "SELECT p_pk FROM u_envelop WHERE e_state = 1 AND e_type = 0 AND p_pk = " + p_pk;
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				x = rs.getBoolean(1);
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
		return x;
	}

	/** 查询玩家封号时间 */
	public Date getPlayerEnvelop(int p_pk)
	{
		Date x = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "SELECT end_time FROM u_envelop WHERE p_pk = " + p_pk;
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				x = rs.getTimestamp("end_time");
			}
			rs.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return x;
	}

	/** 删除玩家封号表里数据 */
	public void delPlayerEnvelop(int p_pk)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "DELETE FROM u_envelop WHERE p_pk = " + p_pk;
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	
	/** 更新玩家封号表里数据 */
	public void updatePlayerEnvelop(int p_pk)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "UPDATE u_envelop SET e_state = 0 WHERE p_pk = " + p_pk;
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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

	/** 查询是否被封号 */
	public boolean getPlayerEnvelopForever(int p_pk)
	{
		boolean x = false;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "SELECT p_pk FROM u_envelop WHERE e_type = 1 AND p_pk = " + p_pk;
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				x = rs.getBoolean(1);
			}
			rs.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return x;
	}
}
