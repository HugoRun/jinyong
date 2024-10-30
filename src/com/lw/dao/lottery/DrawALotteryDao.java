package com.lw.dao.lottery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.lw.vo.lottery.DrawALotteryVO;

public class DrawALotteryDao extends DaoBase
{

	/** 得到抽奖信息 */
	public DrawALotteryVO getDrawALotteryInfo(int id)
	{
		DrawALotteryVO drawALotteryVO = null;
		String sql = "SELECT * FROM lottery_draw WHERE id = " + id;
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

	/** 得到抽奖信息 */
	public List<DrawALotteryVO> getDrawALotteryInfoByTimeType(int timetype)
	{
		List<DrawALotteryVO> list = new ArrayList<DrawALotteryVO>();
		DrawALotteryVO drawALotteryVO = null;
		String sql = "SELECT * FROM lottery_draw WHERE is_run = 0 AND time_type = "
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

	// 更细加载过程
	public void updateIsRun(int timetype, int isrun)
	{
		String sql = "UPDATE lottery_draw SET `is_run` = " + isrun + " WHERE `time_type` = " + timetype;
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

	/** 得到今日注册玩家的人 */
	public List<Integer> getTodayRegPlayer(String date, int num,
			String level_min, String level_max)
	{
		List<Integer> list = new ArrayList<Integer>();
		int p_pk = 0;
		String sql = "SELECT p_pk FROM u_part_info WHERE create_time LIKE '%"
				+ date + "%' AND  p_grade >= '" + level_min
				+ "' AND p_grade <= '" + level_max
				+ "'  ORDER BY rand() LIMIT " + num;
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

	/** 得到充值玩家的人 */
	public List<Integer> getPayPlayer(String date, int num, String level_min,
			String level_max)
	{
		List<Integer> list = new ArrayList<Integer>();
		String sql = "SELECT a.p_pk FROM u_sky_pay_record AS a ,u_part_info AS b WHERE a.respones_result = 0 AND a.pay_time LIKE '%"
				+ date
				+ "%' AND a.p_pk = b.p_pk AND  b.p_grade >= '"
				+ level_min
				+ "' AND b.p_grade <= '"
				+ level_max
				+ "' GROUP BY a.p_pk ORDER BY RAND() LIMIT " + num;
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

	/** 得到今日在线玩家的人 */
	public List<Integer> getOnlinePlayer(int num, String level_min,
			String level_max)
	{
		List<Integer> list = new ArrayList<Integer>();
		int p_pk = 0;
		String sql = "SELECT p_pk FROM u_part_info WHERE login_state = 1 AND  p_grade >= '"
				+ level_min
				+ "' AND p_grade <= '"
				+ level_max
				+ "' ORDER BY rand() LIMIT " + num;
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

	/** 得到玩家的充值金额 */
	public int getChongzhiYb(int p_pk, String date)
	{
		int yuanbao = 0;
		String sql = "SELECT SUM (kbamt) AS yuanbao FROM u_sky_pay_record WHERE pay_time LIKE '%"
				+ date + "%' AND respones_result = '0' AND p_pk = " + p_pk;
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
