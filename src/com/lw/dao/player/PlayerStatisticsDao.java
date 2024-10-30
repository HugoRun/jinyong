package com.lw.dao.player;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.ls.web.service.rank.RankService;
import com.lw.vo.player.PlayerStatisticsVO;

public class PlayerStatisticsDao extends DaoBase
{

	/** �������ͳ����Ϣ�� */
	public void insertPlayerStatistics(int u_pk, int p_pk, int grade,
			String date, String time)
	{
		String sql = "insert into game_player_statistics_info values (null,"
				+ u_pk + "," + p_pk + "," + grade + ",0,'" + date + "','"
				+ time + "',now(),now())";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug(sql);
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

	/** �������PPK�õ������Ϣ */
	public PlayerStatisticsVO getPlayerInfo(int u_pk, int p_pk, String date)
	{
		PlayerStatisticsVO vo = null;
		String sql = "select * from game_player_statistics_info where u_pk = "
				+ u_pk + " and p_pk = " + p_pk + " and p_date = '" + date + "'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug(sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo = new PlayerStatisticsVO();
				vo.setId(rs.getInt("id"));
				vo.setUpk(rs.getInt("u_pk"));
				vo.setPpk(rs.getInt("p_pk"));
				vo.setGrade(rs.getInt("p_grade"));
				vo.setOnlinetime(rs.getInt("p_onlinetime"));
				vo.setP_date(rs.getString("p_date"));
				vo.setP_time(rs.getString("p_time"));
				vo.setLogintimeold(rs.getTimestamp("p_login_time_old"));
				vo.setLogintime(rs.getTimestamp("p_login_time"));
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
		return vo;
	}

	/** ���������Ϣ */
	public void updatePlayerInfo(int u_pk, int p_pk, int grade, String date,
			String time, int id)
	{
		String sql = "update game_player_statistics_info set p_grade = "
				+ grade
				+ ",p_date = '"
				+ date
				+ "',p_time = '"
				+ time
				+ "',p_login_time_old = p_login_time , p_login_time = now() where id = "
				+ id;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug(sql);
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

	/** �����������ʱ��* */
	public void updatePlayerOnlineTime(int u_pk, int p_pk, int time, int id)
	{
		String sql = "update game_player_statistics_info set p_onlinetime = p_onlinetime + "
				+ time + " where id = " + id;
		//ͳ����Ҫ
		new RankService().updateAdd(p_pk, "zhong", time);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug(sql);
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

	/** ͳ����ҵ�����ʱ�� */
	public int getOnlineTime(String date)
	{
		int num = 0;
		String sql = "select sum(p_onlinetime) as num from game_player_statistics_info where p_date = '"
				+ date + "'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug(sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				num = rs.getInt("num");
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
		return num;
	}

	/** ͳ��������ߵȼ� */
	public int getOnlineGrade(String date)
	{
		int num = 0;
		String sql = "select sum(p_grade) as num from game_player_statistics_info where p_date = '"
				+ date + "'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug(sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				num = rs.getInt("num");
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
		return num;
	}

	/** ͳ���������ƽ���ȼ� */
	public int getOnlineAvgGrade(int grade, String date)
	{
		int num = 0;
		String sql = "select avg(p_grade) as num from game_player_statistics_info where p_grade > "
				+ grade + " and p_date = '" + date + "'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug(sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				num = rs.getInt("num");
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
		return num;
	}

	/** �õ������ж�����ҵ�½��Ϸ */
	public int getOnlinePlayer(String date)
	{
		int num = 0;
		String sql = "select count(distinct(p_pk)) as num from game_player_statistics_info where p_date = '"
				+ date + "'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug(sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				num = rs.getInt("num");
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
		return num;
	}

	/** �õ���ҵĻ�Ծ�û����� */
	public int getPlayerOnlineActivity(String date)
	{
		int num = 0;
		String sql = "select count(distinct(u_pk)) as num from game_player_statistics_info where p_login_time > '"
				+ date + "'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug(sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				num = rs.getInt("num");
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
		return num;
	}

	/** �õ�����˺����������½ */
	public int getOnlinePassport(String date)
	{
		int num = 0;
		String sql = "select count(distinct(u_pk)) as num from game_player_statistics_info where p_date = '"
				+ date + "'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug(sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				num = rs.getInt("num");
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
		return num;
	}

}
