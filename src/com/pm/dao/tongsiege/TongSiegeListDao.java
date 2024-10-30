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
 * ���ɹ���ս��ս�����б�
 * @author zhangjj
 *
 */
public class TongSiegeListDao extends DaoBase 
{

	/**
	 * ��ѯ��ս����ǰ�����Ĳ�ս�����б�
	 * @param siegeFightNumber
	 * @param siegeId
	 * @return
	 */
	public List<Integer> getNowJoinTongId(int siegeFightNumber, String siegeId)
	{
		List<Integer> list = new ArrayList<Integer>();
		String sql = "select tong_pk from tong_siege_list where siege_id="+siegeId+" and siege_number = "+siegeFightNumber;
		logger.debug("��ѯ��ս����ǰ�����Ĳ�ս�����б�="+sql);
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
	 * ����ս���ϵĸ�����Ϣ
	 * @param p_pk	����id
	 * @param siege_id	ս��ID
	 * @param siege_number	��ս�����ε������
	 * @param attack_type	ս������,,1Ϊ���˲�ս,2Ϊ���ɲ�ս
	 * @param join_type   �μ����� 1Ϊ����,2Ϊ�س�
	 * @param tong_pk	����ID
	 * @param secondlimitdead	�ڵڶ��׶ε���������
	 * @return
	 */
	public void addTongSiegeUserInfo(int p_pk, String siege_id,
			String siege_number, int attack_type,int join_type, String tong_pk,
			int secondlimitdead)
	{
		String sql = "insert into tong_siege_info values (null,?,?,?,?,0,?,?,?)";
		
		logger.debug("����ս���ϵĸ�����Ϣ=0"+sql);
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
	 * �õ�������Ϣ, 
	 * @param p_pk
	 * @param siege_id
	 * @param siege_number
	 * @return
	 */
	public TongSiegeInfoVO getPersonInfo(int p_pk, String siege_id,
			int siege_number)
	{
		String sql = "select * from tong_siege_info where p_pk="+p_pk+" and siege_id="+siege_id+" and siege_number="+siege_number;
		TongSiegeInfoVO tongSiegeInfoVO = null;
		logger.debug("��ѯ��ս����ǰ�����Ĳ�ս�����б�="+sql);
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
	 * �õ�������Ϣ, 
	 * @param p_pk
	 * @return
	 */
	public TongSiegeInfoVO getPersonInfo(int p_pk)
	{
		String sql = "select * from tong_siege_info where p_pk="+p_pk+" order by info_id desc";
		TongSiegeInfoVO tongSiegeInfoVO = null;
		logger.debug("��ѯ��ս����ǰ�����Ĳ�ս�����б�="+sql);
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
