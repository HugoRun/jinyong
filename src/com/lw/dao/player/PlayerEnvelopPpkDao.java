package com.lw.dao.player;

import java.util.Date;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

public class PlayerEnvelopPpkDao extends DaoBase
{

	/** ��ѯ�Ƿ񱻷�� */
	public boolean getPlayerFromEnvelop(int p_pk)
	{
		boolean x = false;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "select p_pk from u_envelop where e_state = 1 and e_type = 0 and p_pk = " + p_pk;
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

	/** ��ѯ��ҷ��ʱ�� */
	public Date getPlayerEnvelop(int p_pk)
	{
		Date x = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "select end_time from u_envelop where p_pk = " + p_pk;
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

	/** ɾ����ҷ�ű������� */
	public void delPlayerEnvelop(int p_pk)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "delete from u_envelop where p_pk = " + p_pk;
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
	
	/** ������ҷ�ű������� */
	public void updatePlayerEnvelop(int p_pk)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "update u_envelop set e_state = 0 where p_pk = " + p_pk;
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

	/** ��ѯ�Ƿ񱻷�� */
	public boolean getPlayerEnvelopForever(int p_pk)
	{
		boolean x = false;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "select p_pk from u_envelop where e_type = 1 and p_pk = " + p_pk;
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
