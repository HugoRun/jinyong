package com.lw.dao.player;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.ls.web.service.rank.RankService;
import com.lw.vo.player.PlayerStatisticsVO;

public class PlayerStatisticsDao extends DaoBase
{

	/** 插入玩家统计信息表 */
	public void insertPlayerStatistics(int u_pk, int p_pk, int grade,
			String date, String time)
	{
		String sql = "INSERT INTO game_player_statistics_info values (null,"
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

	/** 跟据玩家PPK得到玩家信息 */
	public PlayerStatisticsVO getPlayerInfo(int u_pk, int p_pk, String date)
	{
		PlayerStatisticsVO vo = null;
		String sql = "SELECT * FROM game_player_statistics_info where u_pk = "
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

	/** 更新玩家信息 */
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

	/** 更新玩家在线时间* */
	public void updatePlayerOnlineTime(int u_pk, int p_pk, int time, int id)
	{
		String sql = "update game_player_statistics_info set p_onlinetime = p_onlinetime + "
				+ time + " where id = " + id;
		//统计需要
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

	/** 统计玩家的在线时间 */
	public int getOnlineTime(String date)
	{
		int num = 0;
		String sql = "SELECT sum(p_onlinetime) as num from game_player_statistics_info where p_date = '"
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

	/** 统计玩家在线等级 */
	public int getOnlineGrade(String date)
	{
		int num = 0;
		String sql = "SELECT sum(p_grade) as num from game_player_statistics_info where p_date = '"
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

	/** 统计玩家在线平均等级 */
	public int getOnlineAvgGrade(int grade, String date)
	{
		int num = 0;
		String sql = "SELECT avg(p_grade) as num from game_player_statistics_info where p_grade > "
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

	/** 得到今天有多少玩家登陆游戏 */
	public int getOnlinePlayer(String date)
	{
		int num = 0;
		String sql = "SELECT count(distinct(p_pk)) as num from game_player_statistics_info where p_date = '"
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

	/** 得到玩家的活跃用户数量 */
	public int getPlayerOnlineActivity(String date)
	{
		int num = 0;
		String sql = "SELECT count(distinct(u_pk)) as num from game_player_statistics_info where p_login_time > '"
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

	/** 得到玩家账号数量今天登陆 */
	public int getOnlinePassport(String date)
	{
		int num = 0;
		String sql = "SELECT count(distinct(u_pk)) as num from game_player_statistics_info where p_date = '"
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
