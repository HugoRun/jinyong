package com.lw.dao.gamesystemstatistics;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.lw.vo.gamesystemstatistics.GameSystemStatisticsVO;

public class GameSystemStatisticsDao extends DaoBase
{
	/**
	 * 插入物品的信息
	 * 
	 * @param propID
	 *            物品ID
	 * @param propType
	 *            物品类型
	 * @param propNum
	 *            物品数量
	 * @param propApproachType
	 *            该途径的类型
	 * @param propApproach
	 *            途径类型
	 */
	public void insertPropMessage(int propID, int propType, String propNum,
			String propApproachType, String propApproach, String date,
			String time)
	{
		String sql = "INSERT INTO game_statistics values (null,?,?,?,?,?,?,?)";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, propID);
			ps.setInt(i++, propType);
			ps.setString(i++, propNum);
			ps.setString(i++, propApproachType);
			ps.setString(i++, propApproach);
			ps.setString(i++, date);
			ps.setString(i++, time);

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

	/**
	 * 得到物品饿信息
	 * 
	 * @param propID
	 *            物品ID
	 * @param propType
	 *            物品类型
	 * @param propApproachType
	 *            该途径的类型
	 * @param propApproach
	 *            途径类型
	 */
	public GameSystemStatisticsVO selectPropMessage(int propID, int propType,
			String propApproachType, String propApproach, String date,
			String time)
	{
		GameSystemStatisticsVO vo = null;
		String sql = "SELECT * FROM game_statistics where prop_id = " + propID
				+ " and prop_type = " + propType
				+ " and prop_approach_type = '" + propApproachType
				+ "' and prop_approach = '" + propApproach + "' and date = '"
				+ date + "' and time = '" + time + "'";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo = new GameSystemStatisticsVO();
				vo.setGameSystemStatisticsID(rs.getInt("gs_id"));
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

	/**
	 * 更新物品的信息
	 * 
	 * @param propID
	 *            物品ID
	 * @param propType
	 *            物品类型
	 * @param propNum
	 *            物品数量
	 * @param propApproachType
	 *            该途径的类型
	 * @param propApproach
	 *            途径类型
	 */
	public void updatePropMessage(int propID, int propType, String propNum,
			String propApproachType, String propApproach, String date,
			String time)
	{
		String sql = "update game_statistics set prop_num = prop_num +'"
				+ propNum + "' where prop_id = " + propID + " and prop_type = "
				+ propType + " and prop_approach_type = '" + propApproachType
				+ "' and prop_approach = '" + propApproach + "' and date = '"
				+ date + "' and time = '" + time + "'";
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

	/** 统计库存(玩家仓库金钱) */
	public int getPlayerCangkuMoney()
	{
		int num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		String sql = "SELECT sum(uw_money_number) from u_warehouse_info";
		logger.debug(sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				num = rs.getInt(1);
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

	/** 统计库存(玩家金钱) */
	public int getPlayerGroupMoney()
	{
		int num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		String sql = " select sum(p_copper) from u_part_info ";
		logger.debug(sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				num = rs.getInt(1);
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

	/** 统计库存(玩家包裹道具) */
	public int getPlayerGroupProp(int prop_id)
	{
		int num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		String sql = "SELECT sum(prop_num) from u_propgroup_info where prop_id = "
				+ prop_id;
		logger.debug(sql);
		conn = dbConn.getConn();
		try
		{

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				num = rs.getInt(1);
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

	/** 统计库存(玩家包裹装备) */
	public int getPlayerGroupZhuangbei(int prop_id, int prop_type)
	{
		int num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		String sql = "SELECT count(*) from u_part_equip where w_id = "
				+ prop_id + " and table_type = " + prop_type;
		logger.debug(sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				num = rs.getInt(1);
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

	/** 统计库存(仓库道具) */
	public int getPlayerCangku(int prop_id, int prop_type)
	{
		int num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		String sql = "SELECT sum(uw_number) from u_warehouse_info where uw_prop_id = "
				+ prop_id;
		logger.debug(sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next())
			{
				num = rs.getInt(1);
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

	/** 统计库存(仓库装备) */
	public int getPlayerCangkuEquip(int prop_id, int prop_type)
	{
		int num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		String sql = "SELECT count(*) from u_warehouse_equip where w_id = "
				+ prop_id + " and table_type = " + prop_type;
		logger.debug(sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next())
			{
				num = rs.getInt(1);
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

	/** 统计库存(帮派仓库装备) */
	public int getPlayerTongZhuangbei(int prop_id, int prop_type)
	{
		int num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		String sql = "SELECT count(*) from u_tong_resource_equip where w_id = "
				+ prop_id + " and table_type = " + prop_type;
		logger.debug(sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				num = rs.getInt(1);
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

	/** 统计库存(帮派仓库道具) */
	public int getPlayerTongProp(int prop_id)
	{
		int num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		String sql = "SELECT count(*) from u_tong_resource_prop where prop_id = "
				+ prop_id;
		logger.debug(sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				num = rs.getInt(1);
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

	/** 统计库存(帮派仓库银两) */
	public int getPlayerTongMoney()
	{
		int num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		String sql = "SELECT sum(utm_money) from u_tong_money";
		logger.debug(sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				num = rs.getInt(1);
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

	/** 统计玩家元宝库存 */
	public int getPlayerYuanBao()
	{
		int num = 0;
		String sql = "SELECT sum(yuanbao) from u_login_info";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug(sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				num = rs.getInt(1);
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

	/** 得到商城的道具价格 */
	public int getCommodityPrice(int prop_id)
	{
		int price = 0;
		String sql = "SELECT original_price from commodity_info where prop_id = "
				+ prop_id;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		logger.debug(sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				price = rs.getInt(1);
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
		return price;
	}
}
