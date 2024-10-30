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
	 * �õ���Ҳ�Ʊ����Ϣ
	 * 
	 * @return
	 */
	public LotteryNumberVO getPlayerLottery()
	{
		LotteryNumberVO lotteryInfo = null;
		String sql = "select * from u_lottery_number ";
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

	/** ������Һ�Ͷע���͵õ���ȯ */
	public String getLotteryNumber(int p_pk, int lottery_type)
	{
		String lottery_number = null;
		String sql = "select lottery_number from u_lottery_number where p_pk = "
				+ p_pk + " and lottery_type = " + lottery_type;
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

	/** �������Ͷע��Ʊ */
	public void insertPlayerLottery(int p_pk, String lottery, int lottery_type,
			int p_add_money)
	{
		String sql = "insert into u_lottery_number values  (null,?,?,?,?)";
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

	/** ��ղ�Ʊ�� */
	public void delAll()
	{
		String sql = "delete from u_lottery_number ";
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

	/** �����Ҵ��Ʋ�Ʊ���� */
	public void delCharityNum(int p_pk)
	{
		String sql = "delete * from u_lottery_number where lottery_type = 1 and p_pk = "
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

	/** �õ��н�ע�� */
	public int getLotteryNumber(String lottery_number, int lottery_type)
	{
		int lottery_per_num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "select count(*) from u_lottery_number where lottery_number = '"
				+ lottery_number + "'" + " and lottery_type = " + lottery_type;
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

	/** �õ����������� */
	public int getLotteryPer(String lottery_number)
	{
		int lottery_per_mum = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "select sum(lottery_per_money) from u_lottery_number where lottery_number = '"
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

	/** �õ�ע�� */
	public int getLotteryTodayNumber()
	{
		int lottery_per_num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "select count(*) from u_lottery_number where lottery_type = 0";
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

	/** �õ��н��ߵ�ppk */
	public List<Integer> getWinPlayer(String lottery_number, int lottery_type)
	{
		List<Integer> list = new ArrayList<Integer>();
		int p_pk = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "select p_pk from u_lottery_number where lottery_number = '"
				+ lottery_number + "'" + " and lottery_type = " + lottery_type;
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

	/** �õ��н��ߵ�ppk */
	public List<Integer> getNotWinPlayer(String lottery_number, int lottery_type)
	{
		List<Integer> list = new ArrayList<Integer>();
		int p_pk = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "select p_pk from u_lottery_number where lottery_number != '"
				+ lottery_number + "'" + " and lottery_type = " + lottery_type;
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

	/** �õ����Ʊ�ߵ�ppk */
	public List<Integer> getAllPlayer(int lottery_type)
	{
		List<Integer> list = new ArrayList<Integer>();
		int p_pk = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "select p_pk from u_lottery_number where lottery_type = "
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
