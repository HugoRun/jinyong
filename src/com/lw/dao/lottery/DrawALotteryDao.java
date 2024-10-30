package com.lw.dao.lottery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.lw.vo.lottery.DrawALotteryVO;

public class DrawALotteryDao extends DaoBase
{

	/** �õ��齱��Ϣ */
	public DrawALotteryVO getDrawALotteryInfo(int id)
	{
		DrawALotteryVO drawALotteryVO = null;
		String sql = "select * from lottery_draw where id = " + id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				drawALotteryVO = new DrawALotteryVO();
				drawALotteryVO.setId(rs.getInt("id"));
				drawALotteryVO.setLotter_name(rs.getString("lottery_name"));
				drawALotteryVO.setDraw_people(rs.getInt("draw_people"));
				drawALotteryVO.setLevel_content(rs.getString("draw_level"));
				drawALotteryVO.setType(rs.getInt("type"));
				drawALotteryVO.setBonus_content(rs.getString("bonus"));
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
		return drawALotteryVO;
	}

	/** �õ��齱��Ϣ */
	public List<DrawALotteryVO> getDrawALotteryInfoByTimeType(int timetype)
	{
		List<DrawALotteryVO> list = new ArrayList<DrawALotteryVO>();
		DrawALotteryVO drawALotteryVO = null;
		String sql = "select * from lottery_draw where is_run = 0 and time_type = "
				+ timetype;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				drawALotteryVO = new DrawALotteryVO();
				drawALotteryVO.setId(rs.getInt("id"));
				drawALotteryVO.setLotter_name(rs.getString("lottery_name"));
				drawALotteryVO.setDraw_people(rs.getInt("draw_people"));
				drawALotteryVO.setLevel_content(rs.getString("draw_level"));
				drawALotteryVO.setType(rs.getInt("type"));
				drawALotteryVO.setBonus_content(rs.getString("bonus"));
				drawALotteryVO.setTimeType(rs.getInt("time_type"));
				drawALotteryVO.setTimeHour(rs.getInt("time_hour"));
				drawALotteryVO.setTimeminute(rs.getInt("time_minute"));
				drawALotteryVO.setTimeweek(rs.getString("time_week"));
				drawALotteryVO.setIsRun(rs.getInt("is_run"));
				list.add(drawALotteryVO);
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
		return list;
	}

	// ��ϸ���ع���
	public void updateIsRun(int timetype, int isrun)
	{
		String sql = "update lottery_draw set is_run = " + isrun
				+ " where time_type = " + timetype;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
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

	/** �õ�����ע����ҵ��� */
	public List<Integer> getTodayRegPlayer(String date, int num,
			String level_min, String level_max)
	{
		List<Integer> list = new ArrayList<Integer>();
		int p_pk = 0;
		String sql = "select p_pk from u_part_info where create_time like '%"
				+ date + "%' and  p_grade >= '" + level_min
				+ "' and p_grade <= '" + level_max
				+ "'  order by rand() limit " + num;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				p_pk = rs.getInt("p_pk");
				list.add(p_pk);
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
		return list;
	}

	/** �õ���ֵ��ҵ��� */
	public List<Integer> getPayPlayer(String date, int num, String level_min,
			String level_max)
	{
		List<Integer> list = new ArrayList<Integer>();
		String sql = "select a.p_pk from u_sky_pay_record as a ,u_part_info as b where a.respones_result = 0 and a.pay_time like '%"
				+ date
				+ "%' and a.p_pk = b.p_pk and  b.p_grade >= '"
				+ level_min
				+ "' and b.p_grade <= '"
				+ level_max
				+ "' group by a.p_pk order by RAND() limit " + num;
		int p_pk = 0;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				p_pk = rs.getInt("p_pk");
				list.add(p_pk);
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
		return list;
	}

	/** �õ�����������ҵ��� */
	public List<Integer> getOnlinePlayer(int num, String level_min,
			String level_max)
	{
		List<Integer> list = new ArrayList<Integer>();
		int p_pk = 0;
		String sql = "select p_pk from u_part_info where login_state = 1 and  p_grade >= '"
				+ level_min
				+ "' and p_grade <= '"
				+ level_max
				+ "' order by rand() limit " + num;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				p_pk = rs.getInt("p_pk");
				list.add(p_pk);
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
		return list;
	}

	/** �õ���ҵĳ�ֵ��� */
	public int getChongzhiYb(int p_pk, String date)
	{
		int yuanbao = 0;
		String sql = "select sum(kbamt) as yuanbao from u_sky_pay_record where pay_time like '%"
				+ date + "%' and respones_result = '0' and p_pk = " + p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				yuanbao = rs.getInt("yuanbao");
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
		return yuanbao;
	}
}
