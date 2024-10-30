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
import com.pm.vo.sysInfo.SystemControlInfoVO;

/**
 * 
 * 获取攻城战场上的个人信息
 * @author ZHANGJJ
 *
 */
public class TongSiegeInfoDao extends DaoBase 
{
	Logger logger = Logger.getLogger("log.dao");

	/**
	 * 更新人物在第二阶段时的死亡数
	 * @param pPk
	 * @param siegeId
	 * @param siegeNumber
	 */
	public void updateDeadNumber(int pPk, int siegeId, int siegeNumber)
	{
		String sql = "update tong_siege_info set dead_num = dead_num + 1 where p_pk="+pPk+" and siege_id = "+siegeId+" and siege_number = "+siegeNumber;
		logger.debug("插入帮派战报名="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);			
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
	}
	
	
	

	/**
	 * 减少在第二阶段时的死亡数
	 * @param pPk
	 * @param siegeId
	 * @param siegeNumber
	 */
	public void addDeadLimitNumber(int pPk, int siegeId, int siegeNumber)
	{
		String sql = "update tong_siege_info set dead_limit = dead_limit + 1 where p_pk="+pPk+" and siege_id = "+siegeId+" and siege_number = "+siegeNumber;
		logger.debug("插入帮派战报名="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);			
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}		
	}
	
	
	/**
	 * 在进入第二阶段时将死亡数清零
	 * @param pPk
	 * @param siegeId
	 * @param siegeNumber
	 */
	public void updateDeadNumber( int siegeId, int siegeNumber)
	{
		String sql = "update tong_siege_info set dead_num = 0 where siege_id = "+siegeId+" and siege_number = "+siegeNumber;
		logger.debug("插入帮派战报名="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);			
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
	}
	
	
	

	/**
	 * 获得在战场上所有人的p_pk
	 * @param siegeId
	 * @param siegeFightNumber
	 * @return
	 */
	public List<Integer> getAllInTongSiegePPk(int siegeId, int siegeFightNumber)
	{
		String sql = "SELECT p_pk from tong_siege_info where siege_id = "+siegeId+" and siege_number = "+siegeFightNumber;
		List<Integer> list = new ArrayList<Integer>();
		int p_pk = 0;
		logger.debug("获得在战场上所有人的p_pk="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		//SystemControlInfoVO scIvo = null;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				p_pk = rs.getInt("p_pk");
				list.add(p_pk);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return list;		
	}
	
	
	/**
	 * 获得在战场上所有人的p_pk
	 * @param siegeId
	 * @param siegeFightNumber
	 * @return
	 */
	public List<Integer> getTongInfoPPkByTongID(int siegeId, int siegeFightNumber,int tongId)
	{
		String sql = "SELECT p_pk from tong_siege_info where siege_id = "+siegeId
				+" and siege_number = "+siegeFightNumber+" and tong_id="+tongId;
		List<Integer> list = new ArrayList<Integer>();
		int p_pk = 0;
		logger.debug("选择确定一条系统消息的sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		//SystemControlInfoVO scIvo = null;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				p_pk = rs.getInt("p_pk");
				list.add(p_pk);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return list;		
	}

	
}
