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
	 * ���ԣ������ҵĸ�����������һΪ�����������ڶ�Ϊ�����������
	 * 
	 * @param pPk
	 *            ��ҵĸ���id
	 * @return
	 */
	public int[] getGradePaiMimgQuan(String pPk)
	{
		String sql = "select count(1) as num,(select glory_value+up.p_experience as fen from u_part_info up "
				+ "join u_tong_glory ut on ut.p_pk = up.p_pk where up.p_pk = "
				+ pPk
				+ " limit 1 ) as zijifen"
				+ " from u_part_info up join u_tong_glory ut on ut.p_pk = up.p_pk where "
				+ "(glory_value+up.p_experience) > (select glory_value+up.p_experience as fen from u_part_info up "
				+ "join u_tong_glory ut on ut.p_pk = up.p_pk where up.p_pk = "
				+ pPk
				+ " limit 1 ) or "
				+ "( (glory_value+up.p_experience) = (select glory_value+up.p_experience as fen from u_part_info up "
				+ "join u_tong_glory ut on ut.p_pk = up.p_pk where up.p_pk = "
				+ pPk
				+ " limit 1 ) and "
				+ "up.create_time < (select up.create_time from u_part_info up where up.p_pk = "
				+ pPk + "))";
		logger.debug("����ж����˱ȴ��˵Ľ����������ߵ�sql=" + sql);
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
	 * ����ж����˱ȴ��˵Ľ�����������
	 * 
	 * @param pPk
	 *            ��ҵĸ���id
	 * @return
	 */
	public int getGradePaiMimg(String pPk)
	{
		String sql = "select up.p_pk  from u_part_info up  join u_tong_glory ut "
				+ "on ut.p_pk = up.p_pk where (glory_value+up.p_experience) > "
				+ "(select glory_value+up.p_experience as fen from u_part_info up "
				+ " join u_tong_glory ut on ut.p_pk = up.p_pk where up.p_pk = "
				+ pPk + " limit 1 ) ";
		logger.debug("����ж����˱ȴ��˵Ľ����������ߵ�sql=" + sql);
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
	 * ����ڵȼ�������ͬʱ�������ŵ�����
	 * 
	 * @param pPk
	 *            ��ҵĸ���id
	 * @param fen
	 *            ��ҵĵȼ�����
	 * @return
	 */
	public int getSamePaiMing(String pPk, int fen)
	{
		String sql = "select up.p_pk from u_part_info up  join u_tong_glory ut "
				+ "on ut.p_pk = up.p_pk where (glory_value+up.p_experience) = "
				+ fen
				+ " and up.create_time < (select up.create_time from u_part_info up where up.p_pk = "
				+ pPk + ")";
		logger.debug("����ڵȼ�������ͬʱ�������ŵ�����sql=" + sql);
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
	 * ��ô˽�����������ҵ�p_pk�ͻ���ֵ, ����ֵ��һ������,��һ��Ԫ����p_pk, �ڶ���Ԫ����glory_vlaue, ��������ֵ.
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
		String sql = "select up.p_pk,glory_value+up.p_experience as fen "
				+ "from u_part_info up  join u_tong_glory ut "
				+ "on ut.p_pk = up.p_pk order by fen desc limit "
				+ (paiming - 1) + ",1";
		int[] bigPerson = new int[2];
		logger.debug("��ô˽�����������ҵ�p_pk�ͻ���ֵ��sql=" + sql);
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
	 * ��ô˽�����������ҵĻ���ֵ, ��glory_vlaue, ��������ֵ.
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
		String sql = "select glory_value+up.p_experience as fen "
				+ "from u_part_info up  join u_tong_glory ut "
				+ "on ut.p_pk = up.p_pk order by fen desc limit "
				+ (paiming - 1) + ",1";
		int bigPerson = 0;
		logger.debug("��ô˽�����������ҵ�p_pk�ͻ���ֵ��sql=" + sql);
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
	 * ����pPk������ۺϽ�������
	 * 
	 * @param pPk
	 * @return
	 */
	public int getFenShuByPPk(String pPk)
	{
		String sql = "select IFNULL(glory_value,0)+up.p_experience as fen "
				+ "from u_part_info up  join u_tong_glory ut "
				+ "on ut.p_pk = up.p_pk where up.p_pk = " + pPk;
		int fenshu = 0;
		logger.debug("����pPk������ۺϽ���������sql=" + sql);
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
	 * ���ԣ������ҵĸ�����������һΪ��Ǯ�������ڶ�Ϊ��Ǯ����
	 * 
	 * @param pPk
	 *            ��ҵĸ���id
	 * @return
	 */
	public int[] getMoneyPaiMimgQuan(String pPk)
	{
		String sql = "select count(1) as num , (select uw_money_number+up.p_copper as fen from u_part_info up "
				+ "join u_warehouse_info uw on uw.p_pk = up.p_pk and uw.uw_type=8 where up.p_pk = "
				+ pPk
				+ ") as money from "
				+ "u_part_info up join u_warehouse_info uw on uw.p_pk = up.p_pk and uw.uw_type=8 "
				+ "where (uw_money_number+up.p_copper) > (select uw_money_number+up.p_copper as fen from u_part_info up "
				+ "join u_warehouse_info uw on uw.p_pk = up.p_pk and uw.uw_type=8 where up.p_pk = "
				+ pPk
				+ ") or "
				+ "((uw_money_number+up.p_copper) = (select uw_money_number+up.p_copper as fen from u_part_info up "
				+ "join u_warehouse_info uw on uw.p_pk = up.p_pk and uw.uw_type=8 where up.p_pk = "
				+ pPk
				+ ") and "
				+ "up.create_time < (select up.create_time from u_part_info up where up.p_pk = "
				+ pPk + "))";
		logger.debug("����ж����˱ȴ��˵Ľ����������ߵ�sql=" + sql);
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
	 * ����ж����˱ȴ�pPk�Ľ�Ǯ����
	 */
	public int getMoneyPaiMing(String pPk)
	{
		String sql = "select count(1) as num from u_part_info up  join u_warehouse_info uw "
				+ "on uw.p_pk = up.p_pk and uw.uw_type=8 where (uw_money_number+up.p_copper) > "
				+ "(select uw_money_number+up.p_copper as fen from u_part_info up  join "
				+ "u_warehouse_info uw on uw.p_pk = up.p_pk and uw.uw_type=8 where up.p_pk = "
				+ pPk + ")";
		logger.debug("��ô�pPk�Ľ�Ǯ����sql=" + sql);
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
	 * ����ڲƸ��ȼ�������ͬʱ�������ŵ�����
	 * 
	 * @param pPk
	 *            ��ҵĸ���id
	 * @param fen
	 *            ��ҵĵȼ�����
	 * @return
	 */
	public int getMoneySamePaiMing(String pPk, int fen)
	{
		String sql = "select count(1) as num from u_part_info up  join u_warehouse_info uw "
				+ "on uw.p_pk = up.p_pk and uw.uw_type=8 where (uw_money_number+up.p_copper) ="
				+ fen
				+ " and up.create_time < (select up.create_time from u_part_info up where up.p_pk = "
				+ pPk + ")";
		logger.debug("����ڲƸ��ȼ�������ͬʱ�������ŵ�����sql=" + sql);
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
	 * ��ô˲Ƹ���������ҵ�p_pk�ͻ���ֵ, ����ֵ��һ������,��һ��Ԫ����p_pk, �ڶ���Ԫ����glory_vlaue, ��������ֵ.
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
		String sql = "select up.p_pk,uw_money_number+p_copper as money"
				+ " from u_warehouse_info uw  join u_part_info up  on uw.p_pk = up.p_pk "
				+ "and uw_type = 8 order by money desc limit " + (paiming - 1)
				+ ",1";
		int[] bigPerson = new int[2];
		logger.debug(" ��ô˲Ƹ���������ҵ�p_pk�ͻ���ֵ,=" + sql);
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
	 * ����pPk������ۺϽ�Ǯ����
	 * 
	 * @param pPk
	 * @return
	 */
	public int getMoneyByPPk(String pPk)
	{
		String sql = "select IFNULL(uw_money_number,0)+p_copper as money"
				+ " from u_warehouse_info uw  join u_part_info up "
				+ " on uw.p_pk = up.p_pk and uw_type = 8 where up.p_pk = "
				+ pPk;
		int money = 0;
		logger.debug("����pPk������ۺϽ�Ǯ������sql=" + sql);
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
	 * ��õ�ǰ�������Ľ�������ǰʮ��
	 * 
	 * @return
	 */
	public List<PartInfoVO> getPaiMingList()
	{
		List<PartInfoVO> list = new ArrayList<PartInfoVO>();
		String sql = "select up.p_pk,up.p_name,up.p_camp,IFNULL(glory_value,0)+up.p_experience as fen "
				+ "from u_part_info up join u_tong_glory ut "
				+ "on ut.p_pk = up.p_pk order by fen desc,up.create_time limit 10";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("��õ�ǰ�������Ľ�������ǰʮ��sql=" + sql);
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
	 * ��õ�ǰ�������ĲƸ�����ǰʮ��
	 */
	private List<PartInfoVO> getMoneyPaiMingList1()
	{
		List<PartInfoVO> list = new ArrayList<PartInfoVO>();
		String sql = "select IFNULL(uw_money_number,0)+p_copper as money, up.p_pk,up.p_name,up.p_camp"
				+ " from u_warehouse_info uw "
				+ " join u_part_info up on uw.p_pk = up.p_pk and uw_type = 8 "
				+ "order by money desc,up.create_time limit 10";
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

	// ���ݿ�����ĸ����жϲƸ�����
	public List<PartInfoVO> getMoneyPaiMingList()
	{
		List<PartInfoVO> list = new ArrayList<PartInfoVO>();
		String sql = "select b.p_pk,b.p_name,b.p_tong_name,a.goldbox_num from u_goldbox_num_info as a ,u_part_info as b where a.p_pk = b.p_pk order by a.goldbox_num desc limit 10";
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

	/** �ƽ���Ĳ��� */
	public int getPlayerGoldBoxRecord(int u_pk, int p_pk)
	{
		int num = -1;
		String sql = "select p_pk from u_goldbox_num_info where u_pk = " + u_pk
				+ " and p_pk = " + p_pk;
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

	/** �����ӻƽ����¼ */
	public void insertPlayerGoldBoxRecord(int u_pk, int p_pk)
	{
		String sql = "insert into u_goldbox_num_info values (null," + u_pk
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

	/** ������һƽ���ļ�¼ */
	public void updatePlayerGoldBoxRecord(int u_pk, int p_pk)
	{
		String sql = "update u_goldbox_num_info set goldbox_num = goldbox_num + 1 where u_pk = "
				+ u_pk + " and p_pk = " + p_pk;
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

	/** �õ�ɱ������* */
	public List<PartInfoVO> getKillRank()
	{
		List<PartInfoVO> list = new ArrayList<PartInfoVO>();
		String sql = "select p_pk,p_name,p_camp,u_kill_num from u_part_info where u_kill_num !=0 order by u_kill_num desc limit 10";
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
	
	/**����ɱ������**/
	public void updatePlayerKillNum(int p_pk){
		String sql = "update u_part_info set u_kill_num = u_kill_num + 1 where p_pk = "+p_pk;
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
