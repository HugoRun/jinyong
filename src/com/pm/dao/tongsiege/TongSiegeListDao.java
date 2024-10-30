/**
 * 
 */
package com.pm.dao.tongsiege;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.pm.vo.tongsiege.TongSiegeInfoVO;

/**
 * 帮派攻城战参战帮派列表
 * @author zhangjj
 *
 */
public class TongSiegeListDao extends DaoBase 
{

	/**
	 * 查询此战场当前波数的参战帮派列表
	 * @param siegeFightNumber
	 * @param siegeId
	 * @return
	 */
	public List<Integer> getNowJoinTongId(int siegeFightNumber, String siegeId)
	{
		List<Integer> list = new ArrayList<Integer>();
		String sql = "SELECT tong_pk from tong_siege_list where siege_id="+siegeId+" and siege_number = "+siegeFightNumber;
		logger.debug("查询此战场当前波数的参战帮派列表="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		int tong_pk = 0;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				tong_pk = rs.getInt("tong_pk");
				list.add(tong_pk);	
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return list;
	}
	
	
	
	
	/**
	 * 插入战场上的个人信息
	 * @param p_pk	人物id
	 * @param siege_id	战场ID
	 * @param siege_number	此战场本次的序号数
	 * @param attack_type	战斗类型,,1为个人参战,2为帮派参战
	 * @param join_type   参加类型 1为攻城,2为守城
	 * @param tong_pk	帮派ID
	 * @param secondlimitdead	在第二阶段的死亡次数
	 * @return
	 */
	public void addTongSiegeUserInfo(int p_pk, String siege_id,
			String siege_number, int attack_type,int join_type, String tong_pk,
			int secondlimitdead)
	{
		String sql = "INSERT INTO tong_siege_info values (null,?,?,?,?,0,?,?,?)";
		
		logger.debug("插入战场上的个人信息=0"+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, p_pk);
			ps.setInt(i++, attack_type);
			ps.setInt(i++, join_type);
			ps.setInt(i++, Integer.parseInt(tong_pk));
			
			
			ps.setInt(i++, secondlimitdead);
			ps.setInt(i++, Integer.parseInt(siege_id));
			ps.setInt(i++, Integer.parseInt(siege_number));

			ps.execute();
			ps.close();
		}
		catch (SQLException e)
		{
			logger.debug(e.toString());

		}
		finally
		{
			dbConn.closeConn();
		}
	}




	/**
	 * 得到个人信息, 
	 * @param p_pk
	 * @param siege_id
	 * @param siege_number
	 * @return
	 */
	public TongSiegeInfoVO getPersonInfo(int p_pk, String siege_id,
			int siege_number)
	{
		String sql = "SELECT * FROM tong_siege_info where p_pk="+p_pk+" and siege_id="+siege_id+" and siege_number="+siege_number;
		TongSiegeInfoVO tongSiegeInfoVO = null;
		logger.debug("查询此战场当前波数的参战帮派列表="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				tongSiegeInfoVO = new TongSiegeInfoVO();
				tongSiegeInfoVO.setInfoId(rs.getInt("info_id"));
				tongSiegeInfoVO.setPPk(p_pk);
				tongSiegeInfoVO.setAttackType(rs.getInt("attack_type"));
				tongSiegeInfoVO.setJoinType(rs.getInt("join_type"));
				tongSiegeInfoVO.setTongId(rs.getInt("tong_id"));
				
				tongSiegeInfoVO.setDeadNum(rs.getInt("dead_num"));
				tongSiegeInfoVO.setDeadIimit(rs.getInt("dead_limit"));
				tongSiegeInfoVO.setSiegeId(rs.getInt("siege_id"));
				tongSiegeInfoVO.setSiegeNumber(rs.getInt("siege_number"));				
				
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return tongSiegeInfoVO;
	}
	
	
	/**
	 * 得到个人信息, 
	 * @param p_pk
	 * @return
	 */
	public TongSiegeInfoVO getPersonInfo(int p_pk)
	{
		String sql = "SELECT * FROM tong_siege_info where p_pk="+p_pk+" order by info_id desc";
		TongSiegeInfoVO tongSiegeInfoVO = null;
		logger.debug("查询此战场当前波数的参战帮派列表="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				tongSiegeInfoVO = new TongSiegeInfoVO();
				tongSiegeInfoVO.setInfoId(rs.getInt("info_id"));
				tongSiegeInfoVO.setPPk(p_pk);
				tongSiegeInfoVO.setAttackType(rs.getInt("attack_type"));
				tongSiegeInfoVO.setJoinType(rs.getInt("join_type"));
				tongSiegeInfoVO.setTongId(rs.getInt("tong_id"));
				
				tongSiegeInfoVO.setDeadNum(rs.getInt("dead_num"));
				tongSiegeInfoVO.setDeadIimit(rs.getInt("dead_limit"));
				tongSiegeInfoVO.setSiegeId(rs.getInt("siege_id"));
				tongSiegeInfoVO.setSiegeNumber(rs.getInt("siege_number"));				
				
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return tongSiegeInfoVO;
	}
	
	

}
