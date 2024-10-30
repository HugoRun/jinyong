package com.lw.dao.laborage;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.lw.vo.laborage.PlayerLaborageVO;

public class PlayerLaborageDao extends DaoBase
{

	public PlayerLaborageVO getPlayerLaborage()
	{
		PlayerLaborageVO vo = null;
		String sql = "select * from u_laborage ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo = new PlayerLaborageVO();
				vo.setLaborageId(rs.getInt("laborage_id"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setLaborageThisTime(rs.getInt("laborage_this_time"));
				vo.setLaborageOldtime(rs.getInt("laborage_old_time"));
				vo.setLaboragecatch(rs.getInt("laborage_catch"));

			}
			rs.close();
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
		return vo;
	}

	/** �����������ʱ��� */
	public void buildPlayerLaborage(int p_pk)
	{
		String sql = "insert into u_laborage values  (null,?,0,0,0)";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, p_pk);

			ps.executeUpdate();
			ps.close();
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

	/** �������PPK����������ʱ�� */
	public int getPlayerOnlineTime(int p_pk)
	{
		int time = 0;
		String sql = "select laborage_old_time from u_laborage where p_pk = "
				+ p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				time = rs.getInt("laborage_old_time");
			}
			rs.close();
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
		return time;
	}
	
	/** �������PPK����������ʱ�� */
	public int getPlayerOnlineTimeNOW(int p_pk)
	{
		int time = 0;
		String sql = "select laborage_this_time from u_laborage where p_pk = "
				+ p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				time = rs.getInt("laborage_this_time");
			}
			rs.close();
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
		return time;
	}

	/** �������PPK��������ȡ�ʸ� */
	public int getPlayerCatch(int p_pk)
	{
		int i = 0;
		String sql = "select laborage_catch from u_laborage where p_pk = "
				+ p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				i = rs.getInt("laborage_catch");
			}
			rs.close();
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
		return i;
	}

	/** ������ұ�������ʱ�� */
	public void updatePlayerOnlineTime(int p_pk, int laborage_this_time)
	{
		String sql = "update u_laborage set laborage_this_time = laborage_this_time + "
				+ laborage_this_time + " where p_pk = " + p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
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

	/** �����ȡ���������ұ����ȡ�� */
	public void updatePlayerCatchMoney(int p_pk)
	{
		String sql = "update u_laborage set laborage_catch = 1 where p_pk = "
				+ p_pk;
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

	/** ���������ȡ��� */
	public void updatePlayerCatch()
	{
		String sql = "update u_laborage set laborage_catch = 0 ";
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

	/** ����ͳ�Ƶ�����һ�ܵ�ʱ�� */
	public void updatePlayerTime()
	{
		String sql = "update u_laborage set laborage_old_time = laborage_this_time";
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

	/** ����ͳ�Ƶ�����һ�ܵ�ʱ�� */
	public void updatePlayerTimeZreo()
	{
		String sql = "update u_laborage set laborage_this_time = 0";
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
