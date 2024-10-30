package com.lw.dao.lottery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.lw.vo.lottery.PlayerLotteryInfoVO;

public class PlayerLotteryInfoDao extends DaoBase
{

	/** �õ���ҵĲ�����Ϣ */
	public PlayerLotteryInfoVO getLotteryInfoByPpk(int p_pk)// synchronized
	{

		PlayerLotteryInfoVO lotteryInfo = null;
		String sql = "select * from  u_lottery_info where p_pk =" + p_pk;
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

	/** �����н��ܽ��õ���ҵ����� */
	public List<Integer> getLotteryRank()
	{
		int p_pk = 0;
		List<Integer> list = new ArrayList<Integer>();
		String sql = "select p_pk from  u_lottery_info order by lottery_all_bonus desc limit 10";
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

	/** ������Ҳ�Ʊ��Ϣ */
	public void setPlayerLotteryMessage(int p_pk)
	{
		String sql = "insert into u_lottery_info values  (null,?,0,0,0,0,1,0,0)";
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

	/** �õ���ҵ�Ͷע���� */
	public int getPlayerLotteryNum(int p_pk)
	{
		int lottery_num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "select lottery_num from u_lottery_info where p_pk ="
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

	/** �����н���ע������ */
	public void setPerMoney(int lottery_per_bonus)
	{
		String sql = "update u_lottery_info set lottery_per_bonus = "
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

	/** �����Ϣ���� */
	public void delPlayerMessage()
	{
		String sql = "update u_lottery_info set lottery_per_bonus = 0 ";
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

	/** ���¸���������Ʊ��Ϣ */
	public void delPlayerMessageByMonth()
	{
		String sql = "update u_lottery_info set lottery_num = 0,lottery_win_num = 0";
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
	 * ����ұ���Ƿ��������
	 */

	public void updatePlayerCatch(int p_pk)
	{
		String sql = "update u_lottery_info set lottery_catch_money = 1 where p_pk = "
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
	 * ����ұ���Ƿ��������
	 */

	public void updatePlayerCatchBySys()
	{
		String sql = "update u_lottery_info set lottery_catch_money = 0";
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

	/** �������ȡ�����ͬʱ����ҵ��ܽ�������� */
	public void updatePlayerAllBonus(int lottery_bonus, int p_pk)
	{
		String sql = "update u_lottery_info set lottery_all_bonus = lottery_all_bonus + "
				+ lottery_bonus + " where p_pk = " + p_pk;
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

	/** �����Ͷע��+1 */
	public void addPlayerLotteryNum(int p_pk)
	{
		String sql = "update u_lottery_info set lottery_num = lottery_num + 1 where p_pk = "
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

	/** ���Ӯȡ��Ʊʱ�� ����� ����+1 Ӯȡ����+1 */
	public void addPlayerWinNum(int p_pk)
	{
		String sql = "update u_lottery_info set lottery_win_num = lottery_win_num + 1 , lottery_bonus_multiple = lottery_bonus_multiple + 1 where p_pk = "
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

	/** ����ҵı������ */
	public void delPlayerLotteryMultiple(int p_pk)
	{
		String sql = "update u_lottery_info set  lottery_bonus_multiple = 1 where p_pk = "
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

	/** �õ�����ܽ��� */
	public int getPlayerAllBonus(int p_pk)
	{
		int lottery_all_bonus = 0;
		String sql = "select lottery_all_bonus from u_lottery_info where p_pk = "
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

	/** �õ���ҵ�ǰ���� �����һ���� ����ҵĴ�� */
	public int playerRank(int lottery_all_bonus)
	{
		int rank = 0;
		String sql = "select count(distinct lottery_all_bonus) allc from u_lottery_info where lottery_all_bonus >= "
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

	/** �õ���ע������ */
	public int oneLotteryMoney()
	{
		int lottery_num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "select lottery_per_bonus from u_lottery_info ";
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