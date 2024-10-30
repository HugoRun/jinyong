/**
 * 
 */
package com.pm.dao.tongsiege;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.pm.vo.tongsiege.TongMoneyInfoVO;

/**
 * @author zhangjj
 *
 */
public class TongMoneyInfoDao extends DaoBase
{

	/**
	 * 增加 发送奖金的情况
	 * @param pPk
	 * @param tPk
	 * @param personMoney
	 */
	public int addTongMoneyInfo(int pPk, int tPk, int personMoney)
	{
		String sql = "INSERT INTO tong_money_info values (null,"+pPk+","+tPk+",1,"+personMoney+",now())";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug("增加 发送奖金的情况="+sql);
		int result = 0;
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}  finally
		{			
			dbConn.closeConn();
		}
		return result;
	}

	/**
	 * 获得未取回的金钱信息
	 * @param pPk
	 * @param infoId
	 * @return
	 */
	public TongMoneyInfoVO getBackMoneyInfo(int pPk, String infoId)
	{
		String sql = "SELECT * FROM tong_money_info where back_type = 1 and  p_pk="+pPk+" and info_id = "+infoId;
		TongMoneyInfoVO tongMoneyInfoVO = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("获得未取回的金钱信息="+sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				tongMoneyInfoVO = new TongMoneyInfoVO();
				tongMoneyInfoVO.setInfoId(Integer.parseInt(infoId));
				tongMoneyInfoVO.setPPk(pPk);
				tongMoneyInfoVO.setBackType(rs.getInt("back_type"));
				tongMoneyInfoVO.setTongId(rs.getInt("tong_id"));
				tongMoneyInfoVO.setMoneyNum(rs.getInt("money_num"));
				tongMoneyInfoVO.setSendtime(rs.getString("sendtime"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}  finally
		{			
			dbConn.closeConn();
		}
		return tongMoneyInfoVO;
		
	}

	/**
	 * 更新取回状态
	 * @param pPk
	 * @param infoId
	 */
	public void updateBackType(int pPk, String infoId)
	{
		String sql = "update tong_money_info set back_type = 2  where p_pk="+pPk+" and info_id = "+infoId;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("更新取回状态="+sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);			
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}  finally
		{			
			dbConn.closeConn();
		}		
	}
	
	

}
