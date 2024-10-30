package com.lw.dao.lotterynew;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.lw.vo.lotterynew.LotteryVO;

public class LotteryDAO extends DaoBase
{
	Logger logger = Logger.getLogger(LotteryDAO.class);

	/** �����Ʊ������* */
	public void insertLotteryInfo(LotteryVO vo)
	{
		String sql = "insert into s_lottery_yuanbao values (null,'"
				+ vo.getLottery_date() + "','0','0','"
				+ vo.getLottery_content() + "','0,0,0',now())";
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

	/** ���²�Ʊ��Ͷע�����н����* */
	public void updateLotteryAllybAndPlayer(String lottery_date, long yb,
			String catch_player)
	{
		String sql = "update s_lottery_yuanbao set lottery_all_yb = lottery_all_yb + "
				+ yb
				+ " ,lottery_catch_player = '"
				+ catch_player
				+ "' where lottery_date = '" + lottery_date + "'";
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

	/** ���²�Ʊ����ȡ���* */
	public void updateLotteryCatchyb(String lottery_date, long yb)
	{
		String sql = "update s_lottery_yuanbao set lottery_catch_yb = lottery_all_yb + "
				+ yb + " where lottery_date = '" + lottery_date + "'";
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

	/** �õ���Ʊ��Ϣ ��ǰ��** */
	public LotteryVO selectLotteryInfoByDate(String lottery_date)
	{
		LotteryVO vo = new LotteryVO();
		String sql = "select * from s_lottery_yuanbao where lottery_date = '"
				+ lottery_date + "'";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo.setId(rs.getInt("id"));
				vo.setLottery_date(rs.getString("lottery_date"));
				vo.setLottery_content(rs.getString("lottery_content"));
				vo.setLottery_catch_player(rs.getString("lottery_catch_player"));
				vo.setLottery_all_yb(rs.getLong("lottery_all_yb"));
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

	/** �õ��������� */
	public LotteryVO getBonusInfo()
	{
		LotteryVO vo = new LotteryVO();
		String sql = "select * from lottery_new  ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo.setSys_lottery_bonus(rs.getString("lottery_bonus"));
				vo.setSys_lottery_yb(rs.getLong("lottery_yb"));
				vo.setSys_lottery_tax(rs.getInt("lottery_tax"));
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

	/** ���½��� */
	public void updateLotteryBonus(long yb)
	{
		String sql = "update lottery_new set lottery_yb = " + yb;
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

	// ��Ʊ��ʷ��¼
	public List<LotteryVO> getLotteryHistory(int page, int perpage)
	{
		List<LotteryVO> list = new ArrayList<LotteryVO>();
		LotteryVO vo = null;
		String sql = "select * from s_lottery_yuanbao order by lottery_date desc limit "
				+ (page * perpage + 1) + "," + perpage;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new LotteryVO();
				vo.setLottery_date(rs.getString("lottery_date"));
				vo.setLottery_all_yb(rs.getLong("lottery_all_yb"));
				vo.setLottery_content(rs.getString("lottery_content"));
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

	// ��Ʊ��ʷ��¼
	public int getLotteryHistoryAllNum()
	{
		int num = 0;
		String sql = "select count(*) as num from s_lottery_yuanbao";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				num = rs.getInt("num");
				if (num != 0)
				{
					num = num - 1;
				}
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