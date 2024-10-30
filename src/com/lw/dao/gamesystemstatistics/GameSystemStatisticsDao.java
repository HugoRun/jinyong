package com.lw.dao.gamesystemstatistics;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.lw.vo.gamesystemstatistics.GameSystemStatisticsVO;

public class GameSystemStatisticsDao extends DaoBase
{
	/**
	 * ������Ʒ����Ϣ
	 * 
	 * @param propID
	 *            ��ƷID
	 * @param propType
	 *            ��Ʒ����
	 * @param propNum
	 *            ��Ʒ����
	 * @param propApproachType
	 *            ��;��������
	 * @param propApproach
	 *            ;������
	 */
	public void insertPropMessage(int propID, int propType, String propNum,
			String propApproachType, String propApproach, String date,
			String time)
	{
		String sql = "insert into game_statistics values (null,?,?,?,?,?,?,?)";
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
	 * �õ���Ʒ����Ϣ
	 * 
	 * @param propID
	 *            ��ƷID
	 * @param propType
	 *            ��Ʒ����
	 * @param propApproachType
	 *            ��;��������
	 * @param propApproach
	 *            ;������
	 */
	public GameSystemStatisticsVO selectPropMessage(int propID, int propType,
			String propApproachType, String propApproach, String date,
			String time)
	{
		GameSystemStatisticsVO vo = null;
		String sql = "select * from game_statistics where prop_id = " + propID
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
	 * ������Ʒ����Ϣ
	 * 
	 * @param propID
	 *            ��ƷID
	 * @param propType
	 *            ��Ʒ����
	 * @param propNum
	 *            ��Ʒ����
	 * @param propApproachType
	 *            ��;��������
	 * @param propApproach
	 *            ;������
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

	/** ͳ�ƿ��(��Ҳֿ��Ǯ) */
	public int getPlayerCangkuMoney()
	{
		int num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		String sql = "select sum(uw_money_number) from u_warehouse_info";
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

	/** ͳ�ƿ��(��ҽ�Ǯ) */
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

	/** ͳ�ƿ��(��Ұ�������) */
	public int getPlayerGroupProp(int prop_id)
	{
		int num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		String sql = "select sum(prop_num) from u_propgroup_info where prop_id = "
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

	/** ͳ�ƿ��(��Ұ���װ��) */
	public int getPlayerGroupZhuangbei(int prop_id, int prop_type)
	{
		int num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		String sql = "select count(*) from u_part_equip where w_id = "
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

	/** ͳ�ƿ��(�ֿ����) */
	public int getPlayerCangku(int prop_id, int prop_type)
	{
		int num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		String sql = "select sum(uw_number) from u_warehouse_info where uw_prop_id = "
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

	/** ͳ�ƿ��(�ֿ�װ��) */
	public int getPlayerCangkuEquip(int prop_id, int prop_type)
	{
		int num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		String sql = "select count(*) from u_warehouse_equip where w_id = "
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

	/** ͳ�ƿ��(���ɲֿ�װ��) */
	public int getPlayerTongZhuangbei(int prop_id, int prop_type)
	{
		int num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		String sql = "select count(*) from u_tong_resource_equip where w_id = "
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

	/** ͳ�ƿ��(���ɲֿ����) */
	public int getPlayerTongProp(int prop_id)
	{
		int num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		String sql = "select count(*) from u_tong_resource_prop where prop_id = "
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

	/** ͳ�ƿ��(���ɲֿ�����) */
	public int getPlayerTongMoney()
	{
		int num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		String sql = "select sum(utm_money) from u_tong_money";
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

	/** ͳ�����Ԫ����� */
	public int getPlayerYuanBao()
	{
		int num = 0;
		String sql = "select sum(yuanbao) from u_login_info";
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

	/** �õ��̳ǵĵ��߼۸� */
	public int getCommodityPrice(int prop_id)
	{
		int price = 0;
		String sql = "select original_price from commodity_info where prop_id = "
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
