package com.pm.dao.statistics;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

/**
 * �������ں�̨����ͳ��, ר��������������
 * 
 * @author Administrator
 * 
 */

public class StatisticsDao extends DaoBase
{

	/**
	 * �������ʱ������Ƿ��д�uPk�ļ�¼
	 * 
	 * @param pk
	 * @return
	 */
	public int hasOnlineTimeRecord(String uPk, String today)
	{
		int flag = 0;
		String sql = "select id from user_online_time where u_pk=" + uPk
				+ " and recordTime = '" + today + "'";
		logger.debug("��ѯ�Ƿ���ͳ�Ƽ�¼:" + sql);
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
	 * ������ҵ�����ʱ��
	 * 
	 * @param uPk
	 */
	public void updateOnLineTime(String uPk, long onlinetime, String today)
	{

		String sql = "update user_online_time set onlinetime = onlinetime + "
				+ onlinetime + " where u_pk=" + uPk + " and recordTime = '"
				+ today + "'";
		logger.debug("������ҵ�����ʱ��:" + sql);
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
	 * �ѵ�½��Ϣ���뵽���ݿ���
	 * 
	 * @param uPk
	 */
	public void insertToLoginInfo(int uPk)
	{
		String sql = "insert into p_record_login values (null,'" + uPk
				+ "',1,now())";
		logger.debug("������ɫʱ����ϵͳ����:" + sql);
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
	 * ��ѯ��ҵ�½ʱ����Ƿ��д�uPk��¼
	 * 
	 * @param uPk
	 * @return
	 */
	public int hasRecord(int uPk)
	{
		int flag = 0;
		String sql = "select id from p_record_login where u_pk=" + uPk;
		logger.debug("��ѯ�Ƿ���ͳ�Ƽ�¼:" + sql);
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
	 * ������ҵĵ�½ʱ��
	 * 
	 * @param uPk
	 */
	public void updateLoginInfo(int uPk)
	{

		String sql = "update p_record_login set loginTime = now(), loginStatus = 1 where u_pk="
				+ uPk;
		logger.debug("������ɫʱ����ϵͳ����:" + sql);
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
	 * �鿴��ɫ��½���Ƿ�����ؽ�ɫ��Ϣ
	 * 
	 * @param pPk
	 * @return
	 */
	public int hasRecordPPk(String pPk)
	{
		int flag = 0;
		String sql = "select id from user_record_login where p_pk=" + pPk;
		logger.debug("��ѯ�Ƿ���ͳ�Ƽ�¼:" + sql);
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
	 * �����ɫ��Ϣ��¼��
	 * 
	 * @param pk
	 * @param p_grade
	 */
	public void insertToPersonLoginInfo(String pPk, int p_grade)
	{
		String sql = "insert into user_record_login values (null,'" + pPk
				+ "','" + p_grade + "',1,now())";
		logger.debug("�����ɫ��Ϣ��¼��:" + sql);
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
	 * ���½�ɫ��½��Ϣͳ�Ʊ�
	 * 
	 * @param pk
	 * @param p_grade
	 */
	public void updatePersonLoginInfo(String pPk, int p_grade)
	{
		String sql = "update user_record_login set loginTime = now(), loginStatus = 1, p_grade = "
				+ p_grade + " where p_pk=" + pPk;
		logger.debug("���½�ɫ��½��Ϣͳ�Ʊ�:" + sql);
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
	 * �����ɫ����ʱ��
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
		logger.debug(" ��������ʱ��:" + sql);
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
	 * ����ڿ�ʼ�ȼ��ͽ����ȼ�֮�����ҽ�ɫ��,�ճ��ȼ����.
	 * 
	 * @param startGrade
	 *            ��ʼ�ȼ�
	 * @param endGrade
	 *            �����ȼ�
	 * @return
	 */
	public int getAllUserGradeInfo(int startGrade, int endGrade)
	{
		int person_num = 0;
		String sql = "select sum(1) as person_num from u_part_info where p_grade >"
				+ startGrade + " and p_grade < " + endGrade;
		logger.debug("ѡ��ʼ�ȼ��ͽ����ȼ���������=" + sql);
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
	 * ���뵽ÿ����ҵȼ���Ϣ����
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
		logger.debug("���뵽ÿ����ҵȼ���Ϣ����:" + sql);
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
	 * ����ڿ�ʼ�ȼ��ͽ����ȼ�֮�����ҽ�ɫ��,������ҵȼ����.
	 * 
	 * @param startGrade
	 *            ��ʼ�ȼ�
	 * @param endGrade
	 *            �����ȼ�
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
		logger.debug("��һ����ѡ��ʼ�ȼ��ͽ����ȼ���������=" + sql);
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
	 * ���뵽ÿ��������ҵȼ�����
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
		logger.debug("���뵽ÿ��������ҵȼ���Ϣ����:" + sql);
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
	 * ����ڿ�ʼ�ȼ��ͽ����ȼ�֮�����ҽ�ɫ��,��Ĭ��ҵȼ����.
	 * 
	 * @param startGrade
	 *            ��ʼ�ȼ�
	 * @param endGrade
	 *            �����ȼ�
	 * @return
	 */
	public int getSilverPlayerGrade(int startGrade, int endGrade)
	{
		int person_num = 0;
		// ���ϲ�ѯ, ȡ����p_record_login���е�½���ʱ�䳬�������u_pk�ļ��ϣ���ȡ��
		// u_part_info�����ڴ˼����е����н�ɫ�����������������ڲ����������ĵȼ��ξ���.
		String sql = "select sum(1) as num from u_part_info u where p_grade > "
				+ startGrade
				+ " and p_grade < "
				+ endGrade
				+ " and u.u_pk in (select u_pk from (select p.u_pk from u_login_info p where now() > "
				+ " (p.last_login_time + INTERVAL 7 DAY)) aaa)";
		logger.debug("ѡ��ʼ�ȼ��ͽ����ȼ��ĳ�Ĭ��ɫ����=" + sql);
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
	 * ���뵽��Ĭ��ҵȼ�����
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
		logger.debug("���뵽��Ĭ��ҵȼ���Ϣ����:" + sql);
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
	 * ���������ʱ������ʱ��֮�����ҽ�ɫ��,�������ʱ����.
	 * 
	 * @param least
	 *            ����ʱ��
	 * @param most
	 *            ���ʱ��
	 * @return
	 */
	public int getOnlinePlayerTime(int least, int most)
	{
		int person_num = 0;
		String sql = "select sum(1) as person_num from user_online_time where onlinetime > "
				+ least * 60 + " and onlinetime < " + most * 60;
		logger.debug("���������ʱ������ʱ��֮�����ҽ�ɫ��=" + sql);
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
	 * ���뵽�������ʱ�����
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
		logger.debug("���뵽ÿ������ʱ����Ϣ����:" + sql);
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
	 * ���ע��������
	 * 
	 * @return
	 */
	public int getAllRegistNum()
	{
		int allRegistNum = 0;
		String sql = "select sum(1) as person_num from u_login_info";
		logger.debug("���ע��������=" + sql);
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
	 * ��ý�ɫ����
	 * 
	 * @return
	 */
	public int getAllUserNum()
	{

		int allUserNum = 0;
		String sql = "select sum(1) as person_num from u_part_info";
		logger.debug("��ý�ɫ����=" + sql);
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
	 * ��ý���ע�������
	 * 
	 * @return
	 */
	public int getTodayRegistNum(String date)
	{
		int allTodayRegistNum = 0;
		String sql = "select sum(1) as person_num from u_login_info where create_time like '%"+date+"%'";
		logger.debug("��ý���ע�������=" + sql);
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
	 * ��ý����������
	 * 
	 * @return
	 */
	public int getTodayOnlineNum()
	{
		int allTodayOnlineNum = 0;
		String sql = "select sum(1) as all_num from u_login_info where now() < (last_login_time + INTERVAL 1 DAY)";
		logger.debug("�����������=" + sql);
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
	 * ��ý��ջ�Ծ�û�
	 * 
	 * @return
	 */
	public int getTodayActiveNum(String today)
	{
		int allTodayActiveNum = 0;
		String sql = "select sum(1) as active_num from user_online_time where onlinetime > 1800 and createTime like '%"+today+"%'";
		logger.debug("���ջ�Ծ�û�=" + sql);
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
	 * ��������û�����ʱ��.
	 * 
	 * @return
	 */
	public long getAllPlayerOnlineTime()
	{
		long allPlayerOnlineTime = 0L;
		String sql = "select sum(onlinetime) as all_online_time from user_online_time where now() < (createTime + INTERVAL 1 DAY)";
		logger.debug("��������û�����ʱ��=" + sql);
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
	 * ���ƽ����ɫ���ߵȼ�
	 * 
	 * @return
	 */
	public double getAvgPlayerOnlineGrade(int grade)
	{
		double avgPlayerOnlineGrade = 0L;
		String sql = "select avg(p_grade) as avg_grade from user_record_login where p_grade > "
				+ grade + " and now() < (loginTime + INTERVAL 1 DAY)";
		logger.debug("���ƽ����ɫ���ߵȼ�=" + sql);
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
	 * ���ƽ����������
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
		logger.debug("���ƽ����������=" + sql);
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
	 * �����������ʱ�������, ��Сʱ����.
	 * 
	 * @param nowOnlineNum
	 *            ��������
	 * @param hours
	 *            ��ǰСʱ
	 * @param today
	 *            ��ǰ����
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
		logger.debug(" �����������ʱ�������=" + sql1);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql1);
			int i = ps.executeUpdate();
			logger.debug("���µķ���ֵ=" + i);
			if (i == 0)
			{
				ps = conn.prepareStatement(sql2);
				int a = ps.executeUpdate();
				logger.debug("����ķ���ֵ=" + a);
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
	 * �������������
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
		logger.debug(" �������������=" + sql);
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
	 * ɾ������Ҫ������
	 */
	public void deleteUnneedRecordData()
	{
		String sql = "delete from user_record_login where now() < (loginTime + INTERVAL 1 DAY)";
		String sql1 = "delete from user_online_time where now() < (createTime + INTERVAL 1 DAY)";
		logger.debug(" ɾ������Ҫ������=" + sql + " ,sql1=" + sql1);
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
	 * ��ȡƽ���ȼ�
	 * 
	 * @param i
	 * @return
	 */
	public int getAvgGrade(int minGrade)
	{
		int avg_grade = 0;
		String sql = "select avg(p_grade) as avg_grade from u_part_info where p_grade > "
				+ minGrade;
		logger.debug("��ȡƽ���ȼ�=" + sql);
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
	 * ���ص�ǰ���ڵ�ǰСʱ�ڵ�Сʱ����
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
		logger.debug("��ȡƽ���ȼ�=" + sql);
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
	
	/**�õ�������ҳ���9��������*/
	public int getGrade9today(int grade,String today){
		int num = 0;
		String sql = "select count(*) as num from u_part_info where p_grade > "+grade+" and create_time like '%"+today+"%'";
		logger.debug("ÿ�쳬��9�������=" + sql);
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
 * �鿴�������ʱ��α����Ƿ��е���ļ�¼
 * 
 * @return
 * 
 * public int hasOnlineNumRecord(String today) { int flag = 0; String sql =
 * "select sum(1) as has_num from user_online_num where record_time =
 * '"+today+"'"; logger.debug("�鿴�������ʱ��α����Ƿ��е���ļ�¼="+sql); DBConnection dbConn =
 * new DBConnection(DBConnection.JYGAMEUSER_DB); try{ conn = dbConn.getConn();
 * stmt = conn.createStatement(); rs = stmt.executeQuery(sql); if (rs.next()) {
 * flag = rs.getInt("has_num"); }
 * 
 * rs.close(); stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
 * finally { dbConn.closeConn(); } return flag; }
 */

/**
 * ���²����û�����ʱ��α�
 * 
 * @param nowOnlineNum
 * @param nowHours
 * @param today
 * 
 * public void updatePlayerOnlineNumRecord(int nowOnlineNum, String nowHours,
 * String today) { String col = "hours"+nowHours; String sql = "update
 * user_online_num set hour_time = "+nowOnlineNum+" where record_time =
 * '"+today+"'"; logger.debug("���²����û�����ʱ��α�="+sql); DBConnection dbConn = new
 * DBConnection(DBConnection.JYGAMEUSER_DB); try{ conn = dbConn.getConn(); ps =
 * conn.prepareStatement(sql); ps.executeUpdate(); ps.close();
 * 
 * }catch (SQLException e) { e.printStackTrace(); } finally {
 * dbConn.closeConn(); } }
 */
