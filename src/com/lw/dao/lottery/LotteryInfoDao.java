package com.lw.dao.lottery;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.lw.vo.lottery.LotteryInfoVO;

public class LotteryInfoDao extends DaoBase
{

	/** 得到博彩消息 */
	public LotteryInfoVO getLotteryInfo()
	{

		LotteryInfoVO lotteryInfo = null;
		String sql = "SELECT * FROM lottery ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				lotteryInfo = new LotteryInfoVO();
				lotteryInfo.setLotteryBonus(rs.getInt("lottery_bonus"));
				lotteryInfo.setSysCharityBonus(rs.getInt("sys_charity_bonus"));
				lotteryInfo.setLotteryTax(rs.getInt("lottery_tax"));
				lotteryInfo.setSysBonusType(rs.getInt("sys_bonus_type"));
				lotteryInfo.setSysBonusId(rs.getInt("sys_bonus_id"));
				lotteryInfo.setSysBonusIntro(rs.getInt("sys_bonus_intro"));
				lotteryInfo.setSysBonusNum(rs.getInt("sys_bonus_num"));
				lotteryInfo.setLotteryNumberPerDay(rs
						.getString("sys_lottery_number"));
				lotteryInfo.setLotterySubjoin(rs.getInt("sys_subjoin"));
				lotteryInfo.setLotteryCharityNum(rs
						.getString("sys_charity_number"));
				lotteryInfo.setLotteryWinNum(rs.getInt("lottery_win_num"));

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
		return lotteryInfo;
	}

	/** 更新每日中奖号码 */
	public void updateLotteryNumber(String sys_lottery_number)
	{
		String sql = "update lottery set sys_lottery_number = '"
				+ sys_lottery_number + "'";
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

	/** 生成慈善中奖号码 */
	public void updateLotteryCharityNumber(String lottery_number)
	{
		String sql = "update lottery set sys_charity_number = '"
				+ lottery_number + "'";
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

	/** 得到每日中奖号码 */
	public String getLotteryNumberEveryday()
	{
		String sys_lottery_number = null;
		String sql = "SELECT sys_lottery_number from lottery ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				sys_lottery_number = rs.getString("sys_lottery_number");
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
		return sys_lottery_number;
	}

	/** 慈善中奖号码 */
	public String getLotteryCharityNumber()
	{
		String lottery_number = null;
		String sql = "SELECT sys_charity_number from lottery ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				lottery_number = rs.getString("sys_charity_number");
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
		return lottery_number;
	}

	/** 更新奖池金额 */
	public void updateBotteryBonus(int lottery_bonus)
	{
		String sql = "update lottery set lottery_bonus = lottery_bonus + "
				+ lottery_bonus;
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

	/** 玩家得到奖金在支出表里 递加奖金金额 */
	public void descLotteryBonus(int lottery_bonus)
	{
		String sql = "update lottery set lottery_bonus = lottery_all_bonus + "
				+ lottery_bonus;
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

	/** 奖池领奖后的奖金 */
	public void delLotteryBonus(int lottery_bonus)
	{
		String sql = "update lottery set lottery_bonus = lottery_bonus - "
				+ lottery_bonus;
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

	/** 更新慈善金额 */
	public void updateCharityBonus(int sys_charity_bonus)
	{
		String sql = "update lottery set sys_charity_bonus = sys_charity_bonus + "
				+ sys_charity_bonus;
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

	/** 慈善奖金递减 */
	public void delCharityBonus(int sys_charity_bonus)
	{
		String sql = "update lottery set sys_charity_bonus = sys_charity_bonus - "
				+ sys_charity_bonus;
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

	/** 统计系统补贴奖金 */
	public void updateLotterySunjoin(int sys_subjoin)
	{
		String sql = "update lottery set lottery_subjoin = sys_subjoin + "
				+ sys_subjoin;
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

	/** 更新前日中奖注数 */
	public void updateLotteryWinNumber(int lottery_win_num)
	{
		String sql = "update lottery set lottery_win_num = " + lottery_win_num;
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
}
