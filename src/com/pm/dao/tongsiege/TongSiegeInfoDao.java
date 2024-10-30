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
 * ��ȡ����ս���ϵĸ�����Ϣ
 * @author ZHANGJJ
 *
 */
public class TongSiegeInfoDao extends DaoBase 
{
	Logger logger = Logger.getLogger("log.dao");

	/**
	 * ���������ڵڶ��׶�ʱ��������
	 * @param pPk
	 * @param siegeId
	 * @param siegeNumber
	 */
	public void updateDeadNumber(int pPk, int siegeId, int siegeNumber)
	{
		String sql = "update tong_siege_info set dead_num = dead_num + 1 where p_pk="+pPk+" and siege_id = "+siegeId+" and siege_number = "+siegeNumber;
		logger.debug("�������ս����="+sql);
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
	 * �����ڵڶ��׶�ʱ��������
	 * @param pPk
	 * @param siegeId
	 * @param siegeNumber
	 */
	public void addDeadLimitNumber(int pPk, int siegeId, int siegeNumber)
	{
		String sql = "update tong_siege_info set dead_limit = dead_limit + 1 where p_pk="+pPk+" and siege_id = "+siegeId+" and siege_number = "+siegeNumber;
		logger.debug("�������ս����="+sql);
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
	 * �ڽ���ڶ��׶�ʱ������������
	 * @param pPk
	 * @param siegeId
	 * @param siegeNumber
	 */
	public void updateDeadNumber( int siegeId, int siegeNumber)
	{
		String sql = "update tong_siege_info set dead_num = 0 where siege_id = "+siegeId+" and siege_number = "+siegeNumber;
		logger.debug("�������ս����="+sql);
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
	 * �����ս���������˵�p_pk
	 * @param siegeId
	 * @param siegeFightNumber
	 * @return
	 */
	public List<Integer> getAllInTongSiegePPk(int siegeId, int siegeFightNumber)
	{
		String sql = "select p_pk from tong_siege_info where siege_id = "+siegeId+" and siege_number = "+siegeFightNumber;
		List<Integer> list = new ArrayList<Integer>();
		int p_pk = 0;
		logger.debug("�����ս���������˵�p_pk="+sql);
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
	 * �����ս���������˵�p_pk
	 * @param siegeId
	 * @param siegeFightNumber
	 * @return
	 */
	public List<Integer> getTongInfoPPkByTongID(int siegeId, int siegeFightNumber,int tongId)
	{
		String sql = "select p_pk from tong_siege_info where siege_id = "+siegeId
				+" and siege_number = "+siegeFightNumber+" and tong_id="+tongId;
		List<Integer> list = new ArrayList<Integer>();
		int p_pk = 0;
		logger.debug("ѡ��ȷ��һ��ϵͳ��Ϣ��sql="+sql);
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
