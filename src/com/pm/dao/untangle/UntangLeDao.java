package com.pm.dao.untangle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

public class UntangLeDao extends DaoBase
{

	/**
	 * 测试，获得玩家的个人排名，第一为江湖排名，第二为江湖经验多少
	 * 
	 * @param pPk
	 *            玩家的个人id
	 * @return
	 */
	public int[] getGradePaiMimgQuan(String pPk)
	{
		String sql = "SELECT COUNT(1) AS num,(SELECT glory_value+up.p_experience AS fen FROM u_part_info up "
				+ "join u_tong_glory ut ON ut.p_pk = up.p_pk WHERE up.p_pk = "
				+ pPk
				+ " LIMIT 1 ) AS zijifen"
				+ " FROM u_part_info up join u_tong_glory ut ON ut.p_pk = up.p_pk WHERE "
				+ "(glory_value+up.p_experience) > (SELECT glory_value+up.p_experience AS fen FROM u_part_info up "
				+ "join u_tong_glory ut ON ut.p_pk = up.p_pk WHERE up.p_pk = "
				+ pPk
				+ " LIMIT 1 ) or "
				+ "( (glory_value+up.p_experience) = (SELECT glory_value+up.p_experience AS fen FROM u_part_info up "
				+ "join u_tong_glory ut ON ut.p_pk = up.p_pk WHERE up.p_pk = "
				+ pPk
				+ " LIMIT 1 ) AND "
				+ "up.create_time < (SELECT up.create_time FROM u_part_info up WHERE up.p_pk = "
				+ pPk + "))";
		logger.debug("获得有多少人比此人的江湖排名更高的sql=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		int[] paiming = new int[2];

		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				paiming[0] = rs.getInt("num");
				paiming[1] = rs.getInt("zijifen");
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
		return paiming;
	}

	/**
	 * 获得有多少人比此人的江湖排名更高
	 * 
	 * @param pPk
	 *            玩家的个人id
	 * @return
	 */
	public int getGradePaiMimg(String pPk)
	{
		String sql = "SELECT up.p_pk  FROM u_part_info up JOIN  u_tong_glory ut "
				+ "on ut.p_pk = up.p_pk WHERE (glory_value+up.p_experience) > "
				+ "(SELECT glory_value+up.p_experience AS fen FROM u_part_info up "
				+ " join u_tong_glory ut ON ut.p_pk = up.p_pk WHERE up.p_pk = "
				+ pPk + " LIMIT 1 ) ";
		logger.debug("获得有多少人比此人的江湖排名更高的sql=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		int paiming = 0;
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				paiming++;
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
		return paiming;
	}

	/**
	 * 获得在等级排名相同时此人所排的名次
	 * 
	 * @param pPk
	 *            玩家的个人id
	 * @param fen
	 *            玩家的等级积分
	 * @return
	 */
	public int getSamePaiMing(String pPk, int fen)
	{
		String sql = "SELECT up.p_pk FROM u_part_info up JOIN  u_tong_glory ut "
				+ "on ut.p_pk = up.p_pk WHERE (glory_value+up.p_experience) = "
				+ fen
				+ " AND up.create_time < (SELECT up.create_time FROM u_part_info up WHERE up.p_pk = "
				+ pPk + ")";
		logger.debug("获得在等级排名相同时此人所排的名次sql=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		int paiming = 0;
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				paiming++;
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
		return paiming;
	}

	/**
	 * 获得此江湖排名的玩家的p_pk和积分值, 返回值是一个数组,第一个元素是p_pk, 第二个元素是glory_vlaue, 即江湖分值.
	 * 
	 * @param paiming
	 * @return
	 */
	public int[] getBigPerson(int paiming)
	{
		if (paiming <= 0)
		{
			paiming = 1;
		}
		String sql = "SELECT up.p_pk,glory_value+up.p_experience AS fen "
				+ "FROM u_part_info up JOIN  u_tong_glory ut "
				+ "on ut.p_pk = up.p_pk ORDER BY fen DESC LIMIT "
				+ (paiming - 1) + ",1";
		int[] bigPerson = new int[2];
		logger.debug("获得此江湖排名的玩家的p_pk和积分值的sql=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);

		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				bigPerson[0] = rs.getInt("p_pk");
				bigPerson[1] = rs.getInt("fen");
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
		return bigPerson;
	}
	
	
	/**
	 * 获得此江湖排名的玩家的积分值, 是glory_vlaue, 即江湖分值.
	 * 
	 * @param paiming
	 * @return
	 */
	public int getJiangHuFenShu(int paiming)
	{
		if (paiming <= 0)
		{
			paiming = 1;
		}
		String sql = "SELECT glory_value+up.p_experience AS fen "
				+ "FROM u_part_info up JOIN  u_tong_glory ut "
				+ "on ut.p_pk = up.p_pk ORDER BY fen DESC LIMIT "
				+ (paiming - 1) + ",1";
		int bigPerson = 0;
		logger.debug("获得此江湖排名的玩家的p_pk和积分值的sql=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);

		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				bigPerson = rs.getInt("fen");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return bigPerson;
	}

	/**
	 * 根据pPk来获得综合江湖分数
	 * 
	 * @param pPk
	 * @return
	 */
	public int getFenShuByPPk(String pPk)
	{
		String sql = "SELECT IFNULL(glory_value,0)+up.p_experience AS fen "
				+ "FROM u_part_info up JOIN  u_tong_glory ut "
				+ "on ut.p_pk = up.p_pk WHERE up.p_pk = " + pPk;
		int fenshu = 0;
		logger.debug("根据pPk来获得综合江湖分数的sql=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);

		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				fenshu = rs.getInt("fen");
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
		return fenshu;

	}

	/**
	 * 测试，获得玩家的个人排名，第一为金钱排名，第二为金钱多少
	 * 
	 * @param pPk
	 *            玩家的个人id
	 * @return
	 */
	public int[] getMoneyPaiMimgQuan(String pPk)
	{
		String sql = "SELECT COUNT(1) AS num , (SELECT uw_money_number+up.p_copper AS fen FROM u_part_info up "
				+ "join u_warehouse_info uw ON uw.p_pk = up.p_pk AND uw.uw_type=8 WHERE up.p_pk = "
				+ pPk
				+ ") AS money FROM "
				+ "u_part_info up join u_warehouse_info uw ON uw.p_pk = up.p_pk AND uw.uw_type=8 "
				+ "where (uw_money_number+up.p_copper) > (SELECT uw_money_number+up.p_copper AS fen FROM u_part_info up "
				+ "join u_warehouse_info uw ON uw.p_pk = up.p_pk AND uw.uw_type=8 WHERE up.p_pk = "
				+ pPk
				+ ") or "
				+ "((uw_money_number+up.p_copper) = (SELECT uw_money_number+up.p_copper AS fen FROM u_part_info up "
				+ "join u_warehouse_info uw ON uw.p_pk = up.p_pk AND uw.uw_type=8 WHERE up.p_pk = "
				+ pPk
				+ ") AND "
				+ "up.create_time < (SELECT up.create_time FROM u_part_info up WHERE up.p_pk = "
				+ pPk + "))";
		logger.debug("获得有多少人比此人的江湖排名更高的sql=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		int[] paiming = new int[2];

		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				paiming[0] = rs.getInt("num");
				paiming[1] = rs.getInt("money");
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
		return paiming;
	}

	/**
	 * 获得有多少人比此pPk的金钱更多
	 */
	public int getMoneyPaiMing(String pPk)
	{
		String sql = "SELECT COUNT(1) AS num FROM u_part_info up JOIN  u_warehouse_info uw "
				+ "on uw.p_pk = up.p_pk AND uw.uw_type=8 WHERE (uw_money_number+up.p_copper) > "
				+ "(SELECT uw_money_number+up.p_copper AS fen FROM u_part_info up JOIN  "
				+ "u_warehouse_info uw ON uw.p_pk = up.p_pk AND uw.uw_type=8 WHERE up.p_pk = "
				+ pPk + ")";
		logger.debug("获得此pPk的金钱排名sql=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		int paiming = 0;
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				paiming = rs.getInt("num");
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
		return paiming;
	}

	/**
	 * 获得在财富等级排名相同时此人所排的名次
	 * 
	 * @param pPk
	 *            玩家的个人id
	 * @param fen
	 *            玩家的等级积分
	 * @return
	 */
	public int getMoneySamePaiMing(String pPk, int fen)
	{
		String sql = "SELECT COUNT(1) AS num FROM u_part_info up JOIN  u_warehouse_info uw "
				+ "on uw.p_pk = up.p_pk AND uw.uw_type=8 WHERE (uw_money_number+up.p_copper) ="
				+ fen
				+ " AND up.create_time < (SELECT up.create_time FROM u_part_info up WHERE up.p_pk = "
				+ pPk + ")";
		logger.debug("获得在财富等级排名相同时此人所排的名次sql=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		int paiming = 0;
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				paiming = rs.getInt("num");
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
		return paiming;
	}

	/**
	 * 获得此财富排名的玩家的p_pk和积分值, 返回值是一个数组,第一个元素是p_pk, 第二个元素是glory_vlaue, 即江湖分值.
	 * 
	 * @param paiming
	 * @return
	 */
	public int[] getBigMoneyPerson(int paiming)
	{
		if (paiming <= 0)
		{
			paiming = 1;
		}
		String sql = "SELECT up.p_pk,uw_money_number+p_copper AS money"
				+ " FROM u_warehouse_info uw JOIN  u_part_info up  ON uw.p_pk = up.p_pk "
				+ "AND uw_type = 8 ORDER BY money DESC LIMIT " + (paiming - 1)
				+ ",1";
		int[] bigPerson = new int[2];
		logger.debug(" 获得此财富排名的玩家的p_pk和积分值,=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);

		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				bigPerson[0] = rs.getInt("p_pk");
				bigPerson[1] = rs.getInt("money");
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
		return bigPerson;

	}

	/**
	 * 根据pPk来获得综合金钱分数
	 * 
	 * @param pPk
	 * @return
	 */
	public int getMoneyByPPk(String pPk)
	{
		String sql = "SELECT IFNULL(uw_money_number,0)+p_copper AS money"
				+ " FROM u_warehouse_info uw JOIN  u_part_info up "
				+ " ON uw.p_pk = up.p_pk AND uw_type = 8 WHERE up.p_pk = "
				+ pPk;
		int money = 0;
		logger.debug("根据pPk来获得综合金钱分数的sql=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);

		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				money = rs.getInt("money");
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
		return money;
	}

	/**
	 * 获得当前服务器的江湖排名前十名
	 * 
	 * @return
	 */
	public List<PartInfoVO> getPaiMingList()
	{
		List<PartInfoVO> list = new ArrayList<PartInfoVO>();
		String sql = "SELECT up.p_pk,up.p_name,up.p_camp,IFNULL(glory_value,0)+up.p_experience AS fen "
				+ "FROM u_part_info up join u_tong_glory ut "
				+ "on ut.p_pk = up.p_pk ORDER BY fen desc,up.create_time LIMIT 10";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("获得当前服务器的江湖排名前十名sql=" + sql);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next())
			{
				PartInfoVO vo = new PartInfoVO();
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPName(rs.getString("p_name"));
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

	/**
	 * 获得当前服务器的财富排名前十名
	 */
	private List<PartInfoVO> getMoneyPaiMingList1()
	{
		List<PartInfoVO> list = new ArrayList<PartInfoVO>();
		String sql = "SELECT IFNULL(uw_money_number,0)+p_copper AS money, up.p_pk,up.p_name,up.p_camp"
				+ " FROM u_warehouse_info uw "
				+ " join u_part_info up ON uw.p_pk = up.p_pk AND uw_type = 8 "
				+ "ORDER BY money desc,up.create_time LIMIT 10";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);

		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				PartInfoVO vo = new PartInfoVO();
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPName(rs.getString("p_name"));
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

	// 根据开宝箱的个数判断财富排名
	public List<PartInfoVO> getMoneyPaiMingList()
	{
		List<PartInfoVO> list = new ArrayList<PartInfoVO>();
		String sql = "SELECT b.p_pk,b.p_name,b.p_tong_name,a.goldbox_num FROM u_goldbox_num_info AS a ,u_part_info AS b WHERE a.p_pk = b.p_pk ORDER BY a.goldbox_num DESC LIMIT 10";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				PartInfoVO vo = new PartInfoVO();
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPName(rs.getString("p_name"));
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

	/** 黄金宝箱的操作 */
	public int getPlayerGoldBoxRecord(int u_pk, int p_pk)
	{
		int num = -1;
		String sql = "SELECT p_pk FROM u_goldbox_num_info WHERE u_pk = " + u_pk
				+ " AND p_pk = " + p_pk;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				num = rs.getInt("p_pk");
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

	/** 玩家添加黄金宝箱记录 */
	public void insertPlayerGoldBoxRecord(int u_pk, int p_pk)
	{
		String sql = "INSERT INTO u_goldbox_num_info VALUES (null," + u_pk
				+ "," + p_pk + ",0,now())";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}

	/** 更新玩家黄金宝箱的记录 */
	public void updatePlayerGoldBoxRecord(int u_pk, int p_pk)
	{
		String sql = "UPDATE u_goldbox_num_info SET goldbox_num = goldbox_num + 1 WHERE u_pk = "
				+ u_pk + " AND p_pk = " + p_pk;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}

	/** 得到杀人排名* */
	public List<PartInfoVO> getKillRank()
	{
		List<PartInfoVO> list = new ArrayList<PartInfoVO>();
		String sql = "SELECT p_pk,p_name,p_camp,u_kill_num FROM u_part_info WHERE u_kill_num !=0 ORDER BY u_kill_num DESC LIMIT 10";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);

		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				PartInfoVO vo = new PartInfoVO();
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPName(rs.getString("p_name"));
				vo.setKillNum(rs.getInt("u_kill_num"));
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
	
	/**增加杀人数量**/
	public void updatePlayerKillNum(int p_pk){
		String sql = "UPDATE u_part_info SET u_kill_num = u_kill_num + 1 WHERE p_pk = "+p_pk;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}
}
