package com.lw.dao.lottery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.lw.vo.lottery.LotteryNumberVO;

public class LotteryNumberDao extends DaoBase

{

	/**
	 * 得到玩家彩票的信息
	 * 
	 * @return
	 */
	public LotteryNumberVO getPlayerLottery()
	{
		LotteryNumberVO lotteryInfo = null;
		String sql = "SELECT * FROM u_lottery_number ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				lotteryInfo = new LotteryNumberVO();
				lotteryInfo.setLottery(rs.getInt("lottery_id"));
				lotteryInfo.setPPk(rs.getInt("p_pk"));
				lotteryInfo.setLotteryNumber(rs.getString("lottery_number"));
				lotteryInfo.setLotteryType(rs.getInt("lottery_type"));
				lotteryInfo.setPlayerAddMoney(rs.getInt("lottery_per_money"));
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

	/** 根据玩家和投注类型得到奖券 */
	public String getLotteryNumber(int p_pk, int lottery_type)
	{
		String lottery_number = null;
		String sql = "SELECT lottery_number FROM u_lottery_number WHERE p_pk = "
				+ p_pk + " AND lottery_type = " + lottery_type;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				lottery_number = rs.getString("lottery_number");
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

	/** 插入玩家投注彩票 */
	public void insertPlayerLottery(int p_pk, String lottery, int lottery_type,
			int p_add_money)
	{
		String sql = "INSERT INTO u_lottery_number VALUES  (null,?,?,?,?)";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, p_pk);
			ps.setString(i++, lottery);
			ps.setInt(i++, lottery_type);
			ps.setInt(i++, p_add_money);

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

	/** 清空彩票表 */
	public void delAll()
	{
		String sql = "DELETE FROM u_lottery_number ";
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

	/** 清空玩家慈善彩票内容 */
	public void delCharityNum(int p_pk)
	{
		String sql = "DELETE * FROM u_lottery_number WHERE lottery_type = 1 AND p_pk = "
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

	/** 得到中奖注数 */
	public int getLotteryNumber(String lottery_number, int lottery_type)
	{
		int lottery_per_num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "SELECT COUNT(*) FROM u_lottery_number WHERE lottery_number = '"
				+ lottery_number + "'" + " AND lottery_type = " + lottery_type;
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				lottery_per_num = rs.getInt(1);
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
		return lottery_per_num;
	}

	/** 得到奖金分配比例 */
	public int getLotteryPer(String lottery_number)
	{
		int lottery_per_mum = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "SELECT SUM (lottery_per_money) FROM u_lottery_number WHERE lottery_number = '"
				+ lottery_number + "'";
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				lottery_per_mum = rs.getInt(1);
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
		return lottery_per_mum;
	}

	/** 得到注数 */
	public int getLotteryTodayNumber()
	{
		int lottery_per_num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "SELECT COUNT(*) FROM u_lottery_number WHERE lottery_type = 0";
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				lottery_per_num = rs.getInt(1);
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
		return lottery_per_num;
	}

	/** 得到中奖者的ppk */
	public List<Integer> getWinPlayer(String lottery_number, int lottery_type)
	{
		List<Integer> list = new ArrayList<Integer>();
		int p_pk = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "SELECT p_pk FROM u_lottery_number WHERE lottery_number = '"
				+ lottery_number + "'" + " AND lottery_type = " + lottery_type;
		logger.debug(sql);
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

	/** 得到中奖者的ppk */
	public List<Integer> getNotWinPlayer(String lottery_number, int lottery_type)
	{
		List<Integer> list = new ArrayList<Integer>();
		int p_pk = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "SELECT p_pk FROM u_lottery_number WHERE lottery_number != '"
				+ lottery_number + "'" + " AND lottery_type = " + lottery_type;
		logger.debug(sql);
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

	/** 得到买彩票者的ppk */
	public List<Integer> getAllPlayer(int lottery_type)
	{
		List<Integer> list = new ArrayList<Integer>();
		int p_pk = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "SELECT p_pk FROM u_lottery_number WHERE lottery_type = "
				+ lottery_type;
		logger.debug(sql);
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
}
