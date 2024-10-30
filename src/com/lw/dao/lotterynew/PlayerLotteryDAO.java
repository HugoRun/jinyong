package com.lw.dao.lotterynew;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.lw.vo.lotterynew.LotteryOutPrintVO;
import com.lw.vo.lotterynew.PlayerLotteryVO;

public class PlayerLotteryDAO extends DaoBase
{
	Logger logger = Logger.getLogger(PlayerLotteryDAO.class);

	/** 插入玩家彩票信息* */
	public void insertPlayerLotteryInfo(PlayerLotteryVO vo)
	{
		String sql = "insert into u_lottery_yuanbao values (null,'"
				+ vo.getP_pk() + "','" + vo.getLottery_date() + "','"
				+ vo.getLottery_content() + "','" + vo.getLottery_zhu()
				+ "',now(),'" + vo.getLottery_bonus_lv() + "','"
				+ vo.getLottery_bonus() + "','0',now())";
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

	/** 玩家领取彩票的标记* */
	public void updatePlayerCatchBonus(int p_pk, String lottery_date)
	{
		String sql = "update u_lottery_yuanbao set is_have = 1,have_time = now() where p_pk = "
				+ p_pk + " and lottery_date = '" + lottery_date + "'";
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

	/** 得到玩家的信息* */
	public PlayerLotteryVO getPlayerLotteryInfo(int p_pk, String lottery_date)
	{
		PlayerLotteryVO vo = null;
		String sql = "select * from u_lottery_yuanbao where p_pk = " + p_pk
				+ " and lottery_date = '" + lottery_date + "'";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo = new PlayerLotteryVO();
				vo.setId(rs.getInt("id"));
				vo.setLottery_bonus(rs.getLong("lottery_bonus"));
				vo.setLottery_zhu(rs.getInt("lottery_zhu"));
				vo.setLottery_bonus_lv(rs.getInt("lottery_bonus_lv"));
				vo.setIs_have(rs.getInt("is_have"));
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

	// 得到头奖奖金金额
	public long getLotteryFristBonus(String lottery_date)
	{
		long num = 0;
		String sql = "select lottery_bonus as num from u_lottery_yuanbao where lottery_date = '"
				+ lottery_date + "' and lottery_bonus_lv = 4 limit 1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				num = rs.getLong("num");
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
		return num;
	}

	// 更新头奖奖金
	public void updateFristBonus(int lv, long bonus, String lottery_date)
	{
		String sql = "update u_lottery_yuanbao set lottery_bonus = '" + bonus
				+ "' where lottery_date = '" + lottery_date
				+ "' and lottery_bonus_lv = " + lv;
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

	// 得到总投注数量
	public long getLotteryAllZhu(String lottery_date)
	{
		long num = 0;
		String sql = "select sum(lottery_zhu) as num from u_lottery_yuanbao where lottery_date = '"
				+ lottery_date + "' ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				num = rs.getLong("num");
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
		return num;
	}

	// 得到排名领奖等信息
	public List<LotteryOutPrintVO> getPlayerLotteryAndSysLotteryInfo(
			int pageNO, int perPage)
	{
		List<LotteryOutPrintVO> list = new ArrayList<LotteryOutPrintVO>();
		LotteryOutPrintVO vo = null;
		String sql = "";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new LotteryOutPrintVO();
				list.add(vo);
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

	// 得到排名
	public List<LotteryOutPrintVO> getPlayerRank(String date)
	{
		List<LotteryOutPrintVO> list = new ArrayList<LotteryOutPrintVO>();
		LotteryOutPrintVO vo = null;
		String sql = "select sum(lottery_zhu*lottery_bonus) as num,p_pk from u_lottery_yuanbao where lottery_date != '"
				+ date + "' group by p_pk order by num desc limit 10 ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new LotteryOutPrintVO();
				vo.setP_pk(rs.getInt("p_pk"));
				vo.setBonus(rs.getLong("num"));
				list.add(vo);
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

	// 玩家的历史纪录
	public List<LotteryOutPrintVO> getPlayerLotteryHistory(int p_pk, int page,
			int perpage)
	{
		List<LotteryOutPrintVO> list = new ArrayList<LotteryOutPrintVO>();
		LotteryOutPrintVO vo = null;
		String sql = "select a.lottery_date as lottery_date,a.lottery_content as player_lottery,b.lottery_content as sys_lottery from u_lottery_yuanbao as a ,s_lottery_yuanbao as b where a.lottery_date = b.lottery_date and p_pk = '"
				+ p_pk
				+ "' order by lottery_date desc limit "
				+ (page * perpage) + "," + perpage;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new LotteryOutPrintVO();
				vo.setLottery_date(rs.getString("lottery_date"));
				vo.setPlayer_lottery(rs.getString("player_lottery"));
				vo.setSys_lottery(rs.getString("sys_lottery"));
				list.add(vo);
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

	public int getPlayerLotteryAllNum(int p_pk)
	{
		int num = 0;
		String sql = "select count(*) as num from u_lottery_yuanbao where p_pk = "
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
				num = rs.getInt("num");
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
		return num;
	}

	// 得到注数 今天
	public int getPlayerLotteryNumToday(int p_pk, String lottery_date, int lv)
	{
		int num = 0;
		String sql = "select count(*) as num from u_lottery_yuanbao where p_pk = '"
				+ p_pk
				+ "' and lottery_date like '%"
				+ lottery_date
				+ "%' and lottery_bonus_lv > " + lv;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
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
		catch (SQLException e)
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
