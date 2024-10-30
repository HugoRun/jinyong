package com.pm.dao.statistics;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

/**
 * 此类用于后台数据统计, 专门用来数据输入
 * 
 * @author Administrator
 * 
 */

public class StatisticsDao extends DaoBase
{

	/**
	 * 获得在线时间表中是否有此uPk的记录
	 * 
	 * @param pk
	 * @return
	 */
	public int hasOnlineTimeRecord(String uPk, String today)
	{
		int flag = 0;
		String sql = "select id from user_online_time where u_pk=" + uPk
				+ " and recordTime = '" + today + "'";
		logger.debug("查询是否有统计记录:" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				flag = rs.getInt("id");
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

		return flag;
	}

	/**
	 * 更新玩家的在线时间
	 * 
	 * @param uPk
	 */
	public void updateOnLineTime(String uPk, long onlinetime, String today)
	{

		String sql = "update user_online_time set onlinetime = onlinetime + "
				+ onlinetime + " where u_pk=" + uPk + " and recordTime = '"
				+ today + "'";
		logger.debug("更新玩家的在线时间:" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

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
	 * 把登陆消息插入到数据库中
	 * 
	 * @param uPk
	 */
	public void insertToLoginInfo(int uPk)
	{
		String sql = "insert into p_record_login values (null,'" + uPk
				+ "',1,now())";
		logger.debug("创建角色时创建系统设置:" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

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
	 * 查询玩家登陆时间表是否有此uPk记录
	 * 
	 * @param uPk
	 * @return
	 */
	public int hasRecord(int uPk)
	{
		int flag = 0;
		String sql = "select id from p_record_login where u_pk=" + uPk;
		logger.debug("查询是否有统计记录:" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				flag = rs.getInt("id");
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

		return flag;
	}

	/**
	 * 更新玩家的登陆时间
	 * 
	 * @param uPk
	 */
	public void updateLoginInfo(int uPk)
	{

		String sql = "update p_record_login set loginTime = now(), loginStatus = 1 where u_pk="
				+ uPk;
		logger.debug("创建角色时创建系统设置:" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

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
	 * 查看角色登陆表是否有相关角色信息
	 * 
	 * @param pPk
	 * @return
	 */
	public int hasRecordPPk(String pPk)
	{
		int flag = 0;
		String sql = "select id from user_record_login where p_pk=" + pPk;
		logger.debug("查询是否有统计记录:" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				flag = rs.getInt("id");
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

		return flag;
	}

	/**
	 * 插入角色信息记录表
	 * 
	 * @param pk
	 * @param p_grade
	 */
	public void insertToPersonLoginInfo(String pPk, int p_grade)
	{
		String sql = "insert into user_record_login values (null,'" + pPk
				+ "','" + p_grade + "',1,now())";
		logger.debug("插入角色信息记录表:" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

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
	 * 更新角色登陆信息统计表
	 * 
	 * @param pk
	 * @param p_grade
	 */
	public void updatePersonLoginInfo(String pPk, int p_grade)
	{
		String sql = "update user_record_login set loginTime = now(), loginStatus = 1, p_grade = "
				+ p_grade + " where p_pk=" + pPk;
		logger.debug("更新角色登陆信息统计表:" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

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
	 * 插入角色在线时间
	 * 
	 * @param pk
	 * @param pk2
	 * @param onlineTime
	 */
	public void insertOnLineTime(String uPk, String pPk, long onlineTime,
			String today)
	{
		String sql = "insert into user_online_time values (null," + uPk + ","
				+ pPk + "," + onlineTime + ",'" + today + "',now())";
		logger.debug(" 插入在线时间:" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

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

	/***************************************************************************
	 * 获得在开始等级和结束等级之间的玩家角色数,日常等级监控.
	 * 
	 * @param startGrade
	 *            开始等级
	 * @param endGrade
	 *            结束等级
	 * @return
	 */
	public int getAllUserGradeInfo(int startGrade, int endGrade)
	{
		int person_num = 0;
		String sql = "select sum(1) as person_num from u_part_info where p_grade >"
				+ startGrade + " and p_grade < " + endGrade;
		logger.debug("选择开始等级和结束等级的人数量=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				person_num = rs.getInt("person_num");
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
		return person_num;
	}

	/**
	 * 插入到每日玩家等级信息表中
	 * 
	 * @param grade1
	 * @param grade2to9
	 * @param grade10to19
	 * @param grade20to29
	 * @param grade30to39
	 * @param grade40to49
	 * @param grade50to59
	 * @param grade60
	 * @param today
	 */
	public void insertEveryDayPlayerGrade(int grade1, int grade2to9,
			int grade10to19, int grade20to29, int grade30to39, int grade40to49,
			int grade50to59, int grade60, int avg_grade, String today)
	{
		String sql = "insert into user_everyday_grade values (null," + grade1
				+ "," + grade2to9 + "," + grade10to19 + "," + grade20to29 + ","
				+ grade30to39 + "," + grade40to49 + "," + grade50to59 + ","
				+ grade60 + "," + avg_grade + ",'" + today + "',now())";
		logger.debug("插入到每日玩家等级信息表中:" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

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
	 * 获得在开始等级和结束等级之间的玩家角色数,上线玩家等级监控.
	 * 
	 * @param startGrade
	 *            开始等级
	 * @param endGrade
	 *            结束等级
	 * @return
	 */
	public int getOnlinePlayerGrade(int startGrade, int endGrade)
	{
		int person_num = 0;
		String sql = "select sum(1) as person_num from user_record_login where p_grade >"
				+ startGrade
				+ " and p_grade < "
				+ endGrade
				+ " and now() < (loginTime + INTERVAL 1 DAY)";
		logger.debug("在一天内选择开始等级和结束等级的人数量=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				person_num = rs.getInt("person_num");
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
		return person_num;
	}

	/**
	 * 插入到每日上线玩家等级表中
	 * 
	 * @param grade1
	 * @param grade2to9
	 * @param grade10to19
	 * @param grade20to29
	 * @param grade30to39
	 * @param grade40to49
	 * @param grade50to59
	 * @param grade60
	 * @param today
	 */
	public void insertIntoOnlinePlayerGrade(int grade1to9, int grade10to19,
			int grade20to29, int grade30to39, int grade40to49, int grade50to59,
			int grade60, String today)
	{
		String sql = "insert into user_login_grade values (null," + grade1to9
				+ "," + grade10to19 + "," + grade20to29 + "," + grade30to39
				+ "," + grade40to49 + "," + grade50to59 + "," + grade60 + ",'"
				+ today + "',now())";
		logger.debug("插入到每日上线玩家等级信息表中:" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

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
	 * 获得在开始等级和结束等级之间的玩家角色数,沉默玩家等级监控.
	 * 
	 * @param startGrade
	 *            开始等级
	 * @param endGrade
	 *            结束等级
	 * @return
	 */
	public int getSilverPlayerGrade(int startGrade, int endGrade)
	{
		int person_num = 0;
		// 联合查询, 取出在p_record_login表中登陆最后时间超过七天的u_pk的集合，再取出
		// u_part_info表中在此集合中的所有角色的数量，此数量由在参数所决定的等级段决定.
		String sql = "select sum(1) as num from u_part_info u where p_grade > "
				+ startGrade
				+ " and p_grade < "
				+ endGrade
				+ " and u.u_pk in (select u_pk from (select p.u_pk from u_login_info p where now() > "
				+ " (p.last_login_time + INTERVAL 7 DAY)) aaa)";
		logger.debug("选择开始等级和结束等级的沉默角色数量=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				person_num = rs.getInt("num");
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
		return person_num;
	}

	/**
	 * 插入到沉默玩家等级表中
	 * 
	 * @param grade1to9
	 * @param grade10to19
	 * @param grade20to29
	 * @param grade30to39
	 * @param grade40to49
	 * @param grade50to59
	 * @param grade60
	 * @param today
	 */
	public void insertIntoSilverPlayerGrade(int grade1to9, int grade10to19,
			int grade20to29, int grade30to39, int grade40to49, int grade50to59,
			int grade60, String today)
	{
		String sql = "insert into user_silence_grade values (null," + grade1to9
				+ "," + grade10to19 + "," + grade20to29 + "," + grade30to39
				+ "," + grade40to49 + "," + grade50to59 + "," + grade60 + ",'"
				+ today + "',now())";
		logger.debug("插入到沉默玩家等级信息表中:" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

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
	 * 获得在最少时间和最多时间之间的玩家角色数,玩家上线时间监控.
	 * 
	 * @param least
	 *            最少时间
	 * @param most
	 *            最多时间
	 * @return
	 */
	public int getOnlinePlayerTime(int least, int most)
	{
		int person_num = 0;
		String sql = "select sum(1) as person_num from user_online_time where onlinetime > "
				+ least * 60 + " and onlinetime < " + most * 60;
		logger.debug("获得在最少时间和最多时间之间的玩家角色数=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				person_num = rs.getInt("person_num");
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
		return person_num;
	}

	/**
	 * 插入到玩家上线时间表中
	 * 
	 * @param time10min
	 * @param time30min
	 * @param time60min
	 * @param time120min
	 * @param time120minUp
	 * @param today
	 */
	public void insertIntoOnlineTime(int time10min, int time30min,
			int time60min, int time120min, int time120minUp, String today)
	{
		String sql = "insert into user_grade_statistics values (null,"
				+ time10min + "," + time30min + "," + time60min + ","
				+ time120min + "," + time120minUp + ",'" + today + "',now())";
		logger.debug("插入到每日上线时间信息表中:" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

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
	 * 获得注册总人数
	 * 
	 * @return
	 */
	public int getAllRegistNum()
	{
		int allRegistNum = 0;
		String sql = "select sum(1) as person_num from u_login_info";
		logger.debug("获得注册总人数=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				allRegistNum = rs.getInt("person_num");
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
		return allRegistNum;
	}

	/**
	 * 获得角色总数
	 * 
	 * @return
	 */
	public int getAllUserNum()
	{

		int allUserNum = 0;
		String sql = "select sum(1) as person_num from u_part_info";
		logger.debug("获得角色总数=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				allUserNum = rs.getInt("person_num");
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
		return allUserNum;
	}

	/**
	 * 获得今日注册玩家数
	 * 
	 * @return
	 */
	public int getTodayRegistNum(String date)
	{
		int allTodayRegistNum = 0;
		String sql = "select sum(1) as person_num from u_login_info where create_time like '%"+date+"%'";
		logger.debug("获得今日注册玩家数=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				allTodayRegistNum = rs.getInt("person_num");
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
		return allTodayRegistNum;
	}

	/**
	 * 获得今日在线玩家
	 * 
	 * @return
	 */
	public int getTodayOnlineNum()
	{
		int allTodayOnlineNum = 0;
		String sql = "select sum(1) as all_num from u_login_info where now() < (last_login_time + INTERVAL 1 DAY)";
		logger.debug("今日在线玩家=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				allTodayOnlineNum = rs.getInt("all_num");
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
		return allTodayOnlineNum;
	}

	/**
	 * 获得今日活跃用户
	 * 
	 * @return
	 */
	public int getTodayActiveNum(String today)
	{
		int allTodayActiveNum = 0;
		String sql = "select sum(1) as active_num from user_online_time where onlinetime > 1800 and createTime like '%"+today+"%'";
		logger.debug("今日活跃用户=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				allTodayActiveNum = rs.getInt("active_num");
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
		return allTodayActiveNum;
	}

	/**
	 * 获得所有用户在线时间.
	 * 
	 * @return
	 */
	public long getAllPlayerOnlineTime()
	{
		long allPlayerOnlineTime = 0L;
		String sql = "select sum(onlinetime) as all_online_time from user_online_time where now() < (createTime + INTERVAL 1 DAY)";
		logger.debug("获得所有用户在线时间=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				allPlayerOnlineTime = rs.getInt("all_online_time");
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
		return allPlayerOnlineTime;
	}

	/**
	 * 获得平均角色在线等级
	 * 
	 * @return
	 */
	public double getAvgPlayerOnlineGrade(int grade)
	{
		double avgPlayerOnlineGrade = 0L;
		String sql = "select avg(p_grade) as avg_grade from user_record_login where p_grade > "
				+ grade + " and now() < (loginTime + INTERVAL 1 DAY)";
		logger.debug("获得平均角色在线等级=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				avgPlayerOnlineGrade = rs.getDouble("avg_grade");
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
		return avgPlayerOnlineGrade;
	}

	/**
	 * 获得平均在线人数
	 * 
	 * @return
	 */
	public double getAvgPlayerOnlineNum(String today)
	{
		double avgPlayerOnlineNum = 0;
		String sql = "select (hour_0+hour_1+hour_2+hour_3+hour_4+hour_5+hour_6"
				+ "+hour_7+hour_8+hour_9+hour_10+hour_11+hour_12+hour_13+hour_14+hour_15"
				+ "+hour_16+hour_17+hour_18+hour_19+hour_20+hour_21+hour_22+hour_23) as "
				+ "avg_player_num,createTime from user_online_num where createTime = '"+today+"'";
		logger.debug("获得平均在线人数=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				avgPlayerOnlineNum = rs.getDouble("avg_player_num");
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
		return avgPlayerOnlineNum;
	}

	/**
	 * 插入玩家在线时间段人数, 按小时计算.
	 * 
	 * @param nowOnlineNum
	 *            在线人数
	 * @param hours
	 *            当前小时
	 * @param today
	 *            当前日期
	 */
	public void insertPlayerOnlineNumRecord(int nowOnlineNum, String hours,
			String today)
	{
		String sql1 = "update user_online_num set hour_"
				+ Integer.parseInt(hours) + " = " + nowOnlineNum
				+ " where createTime='" + today + "'";
		String sql2 = "insert into user_online_num(id,hour_"
				+ Integer.parseInt(hours) + ",createTime) values(null,"
				+ nowOnlineNum + ",date(now()))";
		logger.debug(" 插入玩家在线时间段人数=" + sql1);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql1);
			int i = ps.executeUpdate();
			logger.debug("更新的返回值=" + i);
			if (i == 0)
			{
				ps = conn.prepareStatement(sql2);
				int a = ps.executeUpdate();
				logger.debug("插入的返回值=" + a);
			}

			ps.close();
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
	 * 插入玩家总览表
	 * 
	 * @param allRegeistNum
	 * @param allUserNum
	 * @param todayRegistNum
	 * @param todayOnlineNum
	 * @param todayActiveNum
	 * @param avgPlaerOnlineTime
	 * @param avgPlayerOnlineGrade
	 * @param avgPlayerOnlineNum
	 * @param today
	 */
	public void insertPlayerInfoOverview(int allRegeistNum, int allUserNum,
			int todayRegistNum, int todayOnlineNum, int todayActiveNum,
			int avgPlaerOnlineTime, double avgPlayerOnlineGrade,
			double avgPlayerOnlineNum,int grade9today, String today)
	{
		String sql = "insert into user_info_overview values (null,"
				+ allRegeistNum + "," + allUserNum + "," + todayRegistNum + ","
				+ todayOnlineNum + "," + todayActiveNum + ","
				+ avgPlaerOnlineTime + "," + avgPlayerOnlineGrade + ","
				+ avgPlayerOnlineNum + ","+grade9today+",'" + today + "',now())";
		logger.debug(" 插入玩家总览表=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

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
	 * 删除不需要的数据
	 */
	public void deleteUnneedRecordData()
	{
		String sql = "delete from user_record_login where now() < (loginTime + INTERVAL 1 DAY)";
		String sql1 = "delete from user_online_time where now() < (createTime + INTERVAL 1 DAY)";
		logger.debug(" 删除不需要的数据=" + sql + " ,sql1=" + sql1);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps = conn.prepareStatement(sql1);
			ps.executeUpdate();
			ps.close();

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
	 * 获取平均等级
	 * 
	 * @param i
	 * @return
	 */
	public int getAvgGrade(int minGrade)
	{
		int avg_grade = 0;
		String sql = "select avg(p_grade) as avg_grade from u_part_info where p_grade > "
				+ minGrade;
		logger.debug("获取平均等级=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				avg_grade = rs.getInt("avg_grade");
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
		return avg_grade;
	}

	/**
	 * 返回当前日期当前小时内的小时人数
	 * 
	 * @param nowHours
	 * @param today
	 * @return
	 */
	public int getNowHourNum(String nowHours, String today)
	{

		int nowNum = 0;
		String sql = "select hour_" + Integer.parseInt(nowHours)
				+ " as num from user_online_num where createTime = '" + today
				+ "'";
		logger.debug("获取平均等级=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				nowNum = rs.getInt("num");
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
		return nowNum;
	}
	
	/**得到当天玩家超过9级的数量*/
	public int getGrade9today(int grade,String today){
		int num = 0;
		String sql = "select count(*) as num from u_part_info where p_grade > "+grade+" and create_time like '%"+today+"%'";
		logger.debug("每天超过9级的玩家=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
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

/**
 * 查看玩家在线时间段表中是否有当天的记录
 * 
 * @return
 * 
 * public int hasOnlineNumRecord(String today) { int flag = 0; String sql =
 * "select sum(1) as has_num from user_online_num where record_time =
 * '"+today+"'"; logger.debug("查看玩家在线时间段表中是否有当天的记录="+sql); DBConnection dbConn =
 * new DBConnection(DBConnection.JYGAMEUSER_DB); try{ conn = dbConn.getConn();
 * stmt = conn.createStatement(); rs = stmt.executeQuery(sql); if (rs.next()) {
 * flag = rs.getInt("has_num"); }
 * 
 * rs.close(); stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
 * finally { dbConn.closeConn(); } return flag; }
 */

/**
 * 更新插入用户在线时间段表
 * 
 * @param nowOnlineNum
 * @param nowHours
 * @param today
 * 
 * public void updatePlayerOnlineNumRecord(int nowOnlineNum, String nowHours,
 * String today) { String col = "hours"+nowHours; String sql = "update
 * user_online_num set hour_time = "+nowOnlineNum+" where record_time =
 * '"+today+"'"; logger.debug("更新插入用户在线时间段表="+sql); DBConnection dbConn = new
 * DBConnection(DBConnection.JYGAMEUSER_DB); try{ conn = dbConn.getConn(); ps =
 * conn.prepareStatement(sql); ps.executeUpdate(); ps.close();
 * 
 * }catch (SQLException e) { e.printStackTrace(); } finally {
 * dbConn.closeConn(); } }
 */
