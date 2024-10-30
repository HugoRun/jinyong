/**
 * 
 */
package com.pm.dao.tongsiege;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.pm.vo.tongsiege.TongSiegeBattleVO;
import com.pm.vo.tongsiege.TongSiegeControlVO;

/**
 * 关于攻城战的dao,主要查询关于攻城战的控制信息
 * 
 * @author Administrator
 * 
 */
public class TongSiegeBattleDao extends DaoBase
{
	Logger logger = Logger.getLogger("log.dao");

	/**
	 * 查询当前的攻城战战场的状态
	 * 
	 * @param siege_id
	 *            攻城战战场id
	 */
	public int isInFight(String siege_id)
	{
		int now_phase = 0;
		String sql = "SELECT now_phase from tong_siege_control where siege_id="
				+ siege_id + " order by siege_number desc limit 1";
		logger.debug("查询是否在攻城战时间内=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				now_phase = rs.getInt("now_phase");
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
		return now_phase;
	}

	/**
	 * 查看本战场的最近一次的信息
	 * 
	 * @param siege_id
	 *            攻城战战场id
	 */
	public TongSiegeControlVO getSiegeTongInfo(String siege_id)
	{
		TongSiegeControlVO tongSiegeControlVO = null;

		String sql = "SELECT * FROM tong_siege_control where siege_id="
				+ siege_id + " order by siege_number desc limit 1";
		logger.debug("查询是否在攻城战时间内=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				tongSiegeControlVO = new TongSiegeControlVO();
				tongSiegeControlVO.setControlId(rs.getInt("control_id"));
				tongSiegeControlVO.setSiegeId(rs.getInt("siege_id"));
				tongSiegeControlVO.setSiegeNumber(rs.getInt("siege_number"));
				tongSiegeControlVO.setSiegeStartTime(rs
						.getString("siege_start_time"));
				tongSiegeControlVO.setSiegeSignEnd(rs
						.getString("siege_sign_end"));
				tongSiegeControlVO.setLastWinTongid(rs
						.getInt("last_win_tongid"));
				tongSiegeControlVO.setNowPhase(rs.getInt("now_phase"));
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
		return tongSiegeControlVO;
	}

	/**
	 * 查找本战场上次胜利的帮派ID
	 * 
	 * @param siege_id
	 * @return
	 */
	public int getPreviousWinTong(String siege_id, int siegeFightNumber)
	{
		int tong_pk = 0;
		String sql = "SELECT last_win_tongid from tong_siege_control where siege_id = "
				+ siege_id
				+ " and siege_number="
				+ siegeFightNumber
				+ " order by siege_number desc";
		logger.debug(" 查找本战场上次胜利的帮派ID=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				tong_pk = rs.getInt("last_win_tongid");
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
		return tong_pk;
	}

	/**
	 * 获得上一次战斗序号
	 * 
	 * @param siege_id
	 * @return
	 */
	public int getPreiousFightNumber(String siege_id)
	{
		int siege_number = 0;
		String sql = "SELECT siege_number from tong_siege_control where siege_id="
				+ siege_id + " order by siege_number desc limit 1,2";
		logger.debug("获得上一次战斗序号=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				siege_number = rs.getInt("siege_number");
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
		return siege_number;
	}

	/**
	 * 查询在siege_id战场中siegeFightNumber次帮战是否有tong_pk帮派报过名了 返回true为已经报过名了
	 * 
	 * @param tong_pk
	 * @param siege_id
	 * @param siegeFightNumber
	 * @return
	 */
	public boolean isSigeUpAgo(int tong_pk, String siege_id,
			int siegeFightNumber)
	{
		boolean flag = false;
		String sql = "SELECT * FROM tong_siege_list where siege_id=" + siege_id
				+ " and siege_number=" + siegeFightNumber + " and tong_pk="
				+ tong_pk;
		logger.debug("查询是否报过名了=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				flag = true;
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
		return flag;
	}

	/**
	 * 插入帮派战报名
	 * 
	 * @param tong_pk
	 * @param siege_id
	 * @param siegeFightNumber
	 */
	public int insertSignUp(int tong_pk, String siege_id, int siegeFightNumber)
	{
		int result = 0;
		String sql = "INSERT INTO tong_siege_list values (null," + siege_id
				+ "," + siegeFightNumber + "," + tong_pk + ",now())";
		logger.debug("插入帮派战报名=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
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
		return result;

	}

	/**
	 * 根据mapID来确定攻城战场的ID
	 * 
	 * @param mapID
	 * @return
	 */
	public TongSiegeBattleVO getSiegeByMapId(int mapID)
	{
		String sql = "SELECT * FROM tong_siege_battle where map_id=" + mapID;
		TongSiegeBattleVO tongSiegeBattleVO = null;

		logger.debug("根据mapID来确定攻城战场的ID=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				tongSiegeBattleVO = new TongSiegeBattleVO();
				tongSiegeBattleVO.setSiegeId(rs.getInt("siege_id"));
				tongSiegeBattleVO.setSiegeName(rs.getString("siege_name"));
				tongSiegeBattleVO.setMapId(rs.getInt("map_id"));
				tongSiegeBattleVO.setAffectMapId(rs.getString("affect_map_id"));

				tongSiegeBattleVO.setTax(rs.getInt("tax"));
				tongSiegeBattleVO.setTaxMoney(rs.getInt("tax_money"));
				tongSiegeBattleVO.setOutScene(rs.getInt("out_scene"));
				tongSiegeBattleVO.setReliveScene(rs.getString("relive_scene"));

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

		return tongSiegeBattleVO;
	}

	/**
	 * 根据mapID来确定攻城战场的ID
	 * 
	 * @param mapID
	 * @return
	 */
	public TongSiegeBattleVO getSiegeByAffectMapId(int mapID)
	{
		String sql = "SELECT * FROM tong_siege_battle where affect_map_id="
				+ mapID;
		TongSiegeBattleVO tongSiegeBattleVO = null;

		logger.debug("根据mapID来确定攻城战场的ID=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				tongSiegeBattleVO = new TongSiegeBattleVO();
				tongSiegeBattleVO.setSiegeId(rs.getInt("siege_id"));
				tongSiegeBattleVO.setSiegeName(rs.getString("siege_name"));
				tongSiegeBattleVO.setMapId(rs.getInt("map_id"));
				tongSiegeBattleVO.setAffectMapId(rs.getString("affect_map_id"));

				tongSiegeBattleVO.setTax(rs.getInt("tax"));
				tongSiegeBattleVO.setTaxMoney(rs.getInt("tax_money"));
				tongSiegeBattleVO.setOutScene(rs.getInt("out_scene"));
				tongSiegeBattleVO.setReliveScene(rs.getString("relive_scene"));

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

		return tongSiegeBattleVO;
	}

	/**
	 * 根据siegeId来确定攻城战场的ID
	 * 
	 * @param mapID
	 * @return
	 */
	public TongSiegeBattleVO getSiegeBySiegeId(String siegeId)
	{
		String sql = "SELECT * FROM tong_siege_battle where siege_id="
				+ siegeId;
		TongSiegeBattleVO tongSiegeBattleVO = null;

		logger.debug("根据mapID来确定攻城战场的ID=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				tongSiegeBattleVO = new TongSiegeBattleVO();
				tongSiegeBattleVO.setSiegeId(rs.getInt("siege_id"));
				tongSiegeBattleVO.setSiegeName(rs.getString("siege_name"));
				tongSiegeBattleVO.setMapId(rs.getInt("map_id"));
				tongSiegeBattleVO.setAffectMapId(rs.getString("affect_map_id"));

				tongSiegeBattleVO.setTax(rs.getInt("tax"));
				tongSiegeBattleVO.setTaxMoney(rs.getInt("tax_money"));
				tongSiegeBattleVO.setOutScene(rs.getInt("out_scene"));
				tongSiegeBattleVO.setReliveScene(rs.getString("relive_scene"));

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

		return tongSiegeBattleVO;
	}

	/**
	 * 得到所有的战场数据
	 * 
	 * @return
	 */
	public List<TongSiegeBattleVO> getAllSiegeInfo()
	{
		String sql = "SELECT * FROM tong_siege_battle";
		TongSiegeBattleVO tongSiegeBattleVO = null;
		List<TongSiegeBattleVO> list = new ArrayList<TongSiegeBattleVO>();
		logger.debug("得到所有的战场数据=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				tongSiegeBattleVO = new TongSiegeBattleVO();
				tongSiegeBattleVO.setSiegeId(rs.getInt("siege_id"));
				tongSiegeBattleVO.setSiegeName(rs.getString("siege_name"));
				tongSiegeBattleVO.setMapId(rs.getInt("map_id"));
				tongSiegeBattleVO.setAffectMapId(rs.getString("affect_map_id"));

				tongSiegeBattleVO.setTax(rs.getInt("tax"));
				tongSiegeBattleVO.setTaxMoney(rs.getInt("tax_money"));
				tongSiegeBattleVO.setOutScene(rs.getInt("out_scene"));
				tongSiegeBattleVO.setReliveScene(rs.getString("relive_scene"));
				list.add(tongSiegeBattleVO);
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
	 * 更新战场的状态
	 * 
	 * @return
	 */
	public void updateTongSiegeNowPhase(int siegeId, int siegeNumber,
			int now_phase)
	{
		String sql = "update tong_siege_control set now_phase = " + now_phase
				+ " where siege_id=" + siegeId + " and siege_number="
				+ siegeNumber;
		logger.debug("更新战场的状态=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.execute(sql);
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
	 * 更新战场的状态
	 * 
	 * @return
	 */
	public void updateTongSiegeNowPhase(int siegeId, int now_phase)
	{
		String sql = "update tong_siege_control set now_phase = " + now_phase
				+ " where siege_id=" + siegeId
				+ " order by control_id desc limit 1";
		logger.debug("更新战场的状态=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.execute(sql);
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
	 * 获得 帮派战场信息 根据tongId
	 * 
	 * @param tongId
	 * @return
	 */
	public List<TongSiegeControlVO> getSiegeBattleVOByTongId(int tongId)
	{

		String sql = "SELECT * FROM tong_siege_control where last_win_tongid = "
				+ tongId + " order by control_id desc limit 1";
		TongSiegeControlVO tongSiegeControlVO = new TongSiegeControlVO();
		logger.debug("获得 帮派战场信息 根据tongId=" + sql);
		List<TongSiegeControlVO> list = new ArrayList<TongSiegeControlVO>();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				tongSiegeControlVO = new TongSiegeControlVO();
				tongSiegeControlVO.setControlId(rs.getInt("control_id"));
				tongSiegeControlVO.setSiegeId(rs.getInt("siege_id"));
				tongSiegeControlVO.setSiegeNumber(rs.getInt("siege_number"));
				tongSiegeControlVO.setSiegeStartTime(rs
						.getString("siege_start_time"));
				tongSiegeControlVO.setSiegeSignEnd(rs
						.getString("siege_sign_end"));
				tongSiegeControlVO.setLastWinTongid(rs
						.getInt("last_win_tongid"));
				tongSiegeControlVO.setNowPhase(rs.getInt("now_phase"));
				list.add(tongSiegeControlVO);
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
	 * 获得 帮派战场信息
	 * 
	 * @param tongId
	 * @return
	 */
	public List<TongSiegeControlVO> getSiegeBattleVOByTongId()
	{

		String sql = "SELECT * FROM (select * from tong_siege_control order by control_id desc )   ts group by siege_id";
		TongSiegeControlVO tongSiegeControlVO = new TongSiegeControlVO();
		logger.debug("获得 帮派战场信息 根据tongId=" + sql);
		List<TongSiegeControlVO> list = new ArrayList<TongSiegeControlVO>();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				tongSiegeControlVO = new TongSiegeControlVO();
				tongSiegeControlVO.setControlId(rs.getInt("control_id"));
				tongSiegeControlVO.setSiegeId(rs.getInt("siege_id"));
				tongSiegeControlVO.setSiegeNumber(rs.getInt("siege_number"));
				tongSiegeControlVO.setSiegeStartTime(rs
						.getString("siege_start_time"));
				tongSiegeControlVO.setSiegeSignEnd(rs
						.getString("siege_sign_end"));
				tongSiegeControlVO.setLastWinTongid(rs
						.getInt("last_win_tongid"));
				tongSiegeControlVO.setNowPhase(rs.getInt("now_phase"));
				list.add(tongSiegeControlVO);
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
	 * 修改税率
	 * 
	 * @param tongId
	 * @param tax
	 * @param siegeId
	 */
	public void changeTax(String tax, int siegeId)
	{
		String sql = "update tong_siege_battle set tax = " + tax
				+ " where siege_id=" + siegeId;
		logger.debug("修改税率=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.execute(sql);

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
	 * 更新税收款
	 * 
	 * @param sceneMapqy
	 * @param money
	 */
	public void updateTaxMoney(String sceneMapqy, int money)
	{
		String sql = "update tong_siege_battle set tax_money = tax_money+"
				+ money + " where affect_map_id=" + sceneMapqy;
		logger.debug(" 更新税收款=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.execute(sql);

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
	 * 更新税收款
	 * 
	 * @param siegeId
	 * @param money
	 *            减少的money
	 */
	public void reduceTaxMoney(String siegeId, int money)
	{
		String sql = "update tong_siege_battle set tax_money = tax_money-"
				+ money + " where siege_id=" + siegeId;
		logger.debug(" 更新税收款=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.execute(sql);

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
	 * 获得前300名的情况
	 * 
	 * @param siegeId
	 * @param siegeNumber
	 * @return
	 */
	public List<Integer> getAllKillMostThreehundred(int siegeId, int siegeNumber)
	{
		List<Integer> list = new ArrayList<Integer>();
		String sql = "SELECT tsp.p_pk from tong_siege_pklog tsp,u_part_info upi "
				+ "where siege_id = "
				+ siegeId
				+ " and siege_number = "
				+ siegeNumber
				+ " and tsp.p_pk=upi.p_pk "
				+ "order by pk_number desc,p_experience desc limit 300";
		logger.debug("获得前300名的情况=" + sql);

		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				list.add(rs.getInt("p_pk"));
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
	 * 将胜利帮派置为此次战斗的胜利帮派, 存入数据库.
	 * 
	 * @param winTongId
	 * @param siegeId
	 * @param siegeNumber
	 */
	public void updateWinTongId(int winTongId, int siegeId, int siegeNumber)
	{
		String sql = "update tong_siege_control set last_win_tongid="
				+ winTongId + " where siege_id=" + siegeId
				+ " and siege_number=" + siegeNumber;
		logger.debug(" 将胜利帮派置为此次战斗的胜利帮派=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.execute(sql);

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

	public TongSiegeBattleVO getSiegeByOutScene(String sceneId)
	{
		String sql = "SELECT * FROM tong_siege_battle where out_scene="
				+ sceneId;
		logger.debug(" 更新税收款=" + sql);
		TongSiegeBattleVO tongSiegeBattleVO = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				tongSiegeBattleVO = new TongSiegeBattleVO();
				tongSiegeBattleVO.setSiegeId(rs.getInt("siege_id"));
				tongSiegeBattleVO.setSiegeName(rs.getString("siege_name"));
				tongSiegeBattleVO.setMapId(rs.getInt("map_id"));
				tongSiegeBattleVO.setAffectMapId(rs.getString("affect_map_id"));

				tongSiegeBattleVO.setTax(rs.getInt("tax"));
				tongSiegeBattleVO.setTaxMoney(rs.getInt("tax_money"));
				tongSiegeBattleVO.setOutScene(rs.getInt("out_scene"));
				tongSiegeBattleVO.setReliveScene(rs.getString("relive_scene"));

			}

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
		return tongSiegeBattleVO;
	}

	public int getTongBattleID(int siege_id)
	{
		String sql = "SELECT control_id from tong_siege_control where siege_id = "
				+ siege_id + " order by siege_number desc limit 1 ";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		int id = 0;
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				id = rs.getInt("");
			}
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
		return id;
	}

}
