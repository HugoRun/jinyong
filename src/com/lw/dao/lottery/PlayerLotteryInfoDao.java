package com.lw.dao.lottery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.lw.vo.lottery.PlayerLotteryInfoVO;

public class PlayerLotteryInfoDao extends DaoBase
{

	/** 得到玩家的博彩信息 */
	public PlayerLotteryInfoVO getLotteryInfoByPpk(int p_pk)// synchronized
	{

		PlayerLotteryInfoVO lotteryInfo = null;
		String sql = "SELECT * FROM  u_lottery_info WHERE p_pk =" + p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				lotteryInfo = new PlayerLotteryInfoVO();
				lotteryInfo.setPPk(rs.getInt("p_pk"));
				lotteryInfo.setLotteryNum(rs.getInt("lottery_num"));
				lotteryInfo.setLotteryWinNum(rs.getInt("lottery_win_num"));
				lotteryInfo.setLotteryCatchMoney(rs
						.getInt("lottery_catch_money"));
				lotteryInfo.setLotteryPerBonus(rs.getInt("lottery_per_bonus"));
				lotteryInfo.setLotteryBonusMultiple(rs
						.getInt("lottery_bonus_multiple"));
				lotteryInfo.setLotteryCharity(rs.getInt("lottery_charity"));
				lotteryInfo.setLotteryAllBonus(rs.getInt("lottery_all_bonus"));

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

	/** 根据中奖总金额得到玩家的排名 */
	public List<Integer> getLotteryRank()
	{
		int p_pk = 0;
		List<Integer> list = new ArrayList<Integer>();
		String sql = "SELECT p_pk FROM  u_lottery_info ORDER BY lottery_all_bonus DESC LIMIT 10";
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

	/** 插入玩家彩票信息 */
	public void setPlayerLotteryMessage(int p_pk)
	{
		String sql = "INSERT INTO u_lottery_info VALUES  (null,?,0,0,0,0,1,0,0)";
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

	/** 得到玩家的投注次数 */
	public int getPlayerLotteryNum(int p_pk)
	{
		int lottery_num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "SELECT lottery_num FROM u_lottery_info WHERE p_pk ="
				+ p_pk;
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				lottery_num = rs.getInt("lottery_num");
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
		return lottery_num;
	}

	/** 插入中奖单注奖金金额 */
	public void setPerMoney(int lottery_per_bonus)
	{
		String sql = "UPDATE u_lottery_info SET lottery_per_bonus = "
				+ lottery_per_bonus;
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

	/** 玩家信息请零 */
	public void delPlayerMessage()
	{
		String sql = "UPDATE u_lottery_info SET lottery_per_bonus = 0 ";
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

	/** 按月给玩家清零彩票信息 */
	public void delPlayerMessageByMonth()
	{
		String sql = "UPDATE u_lottery_info SET lottery_num = 0,lottery_win_num = 0";
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

	/**
	 * 给玩家标记是否领过奖金
	 */

	public void updatePlayerCatch(int p_pk)
	{
		String sql = "UPDATE u_lottery_info SET lottery_catch_money = 1 WHERE p_pk = "
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
	
	/**
	 * 给玩家标记是否领过奖金
	 */

	public void updatePlayerCatchBySys()
	{
		String sql = "UPDATE u_lottery_info SET lottery_catch_money = 0";
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

	/** 玩家在领取奖金的同时给玩家的总奖金额增加 */
	public void updatePlayerAllBonus(int lottery_bonus, int p_pk)
	{
		String sql = "UPDATE u_lottery_info SET lottery_all_bonus = lottery_all_bonus + "
				+ lottery_bonus + " WHERE p_pk = " + p_pk;
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

	/** 给玩家投注数+1 */
	public void addPlayerLotteryNum(int p_pk)
	{
		String sql = "UPDATE u_lottery_info SET lottery_num = lottery_num + 1 WHERE p_pk = "
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

	/** 玩家赢取彩票时候 给玩家 倍数+1 赢取次数+1 */
	public void addPlayerWinNum(int p_pk)
	{
		String sql = "UPDATE u_lottery_info SET lottery_win_num = lottery_win_num + 1 , lottery_bonus_multiple = lottery_bonus_multiple + 1 WHERE p_pk = "
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

	/** 给玩家的倍数清空 */
	public void delPlayerLotteryMultiple(int p_pk)
	{
		String sql = "UPDATE u_lottery_info SET  lottery_bonus_multiple = 1 WHERE p_pk = "
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

	/** 得到玩家总奖金 */
	public int getPlayerAllBonus(int p_pk)
	{
		int lottery_all_bonus = 0;
		String sql = "SELECT lottery_all_bonus FROM u_lottery_info WHERE p_pk = "
				+ p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				lottery_all_bonus = rs.getInt("lottery_all_bonus");
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
		return lottery_all_bonus;
	}

	/** 得到玩家当前排名 跟玩家一样的 比玩家的大的 */
	public int playerRank(int lottery_all_bonus)
	{
		int rank = 0;
		String sql = "SELECT COUNT(distinct lottery_all_bonus) allc FROM u_lottery_info WHERE lottery_all_bonus >= "
				+ lottery_all_bonus;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				rank = rs.getInt(1);
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
		return rank;
	}

	/** 得到单注奖金金额 */
	public int oneLotteryMoney()
	{
		int lottery_num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "SELECT lottery_per_bonus FROM u_lottery_info ";
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				lottery_num = rs.getInt("lottery_per_bonus");
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
		return lottery_num;
	}
}
