package com.ls.ben.dao.goods.equip;

import org.apache.log4j.Logger;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

/**
 * 装备展示dao
 * @author 张俊俊 
 * @version 1.0 
 *	2009.08.07 10:58
 *	
 */
public class EquipRelelaDao extends DaoBase
{

	Logger logger = Logger.getLogger("log.dao");
	
	/**
	 * 插入装备显示信息
	 * @param pwPk
	 * @param displayString
	 */
	public void insertEquipRelela(int pwPk, String displayString)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			String sql = "INSERT INTO zb_relela_info values (null,"+pwPk+",'"+displayString+"',now())";
			logger.debug(sql);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		
	}

	/**
	 * 获得装备展示信息
	 * @param pwpk
	 * @return
	 */
	public String getEquipRelelaInfo(String pwpk)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String relelaInfo = "";
		try {
    		String sql = "SELECT relelavar from zb_relela_info where pwpk = "+pwpk;
    		logger.debug(sql);
    		stmt = conn.createStatement();
    		rs = stmt.executeQuery(sql);
    		if ( rs.next()) {
    			relelaInfo = rs.getString("relelavar");
    		}
    		rs.close();
    		stmt.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	finally
    	{
    		dbConn.closeConn();
    	}
		return relelaInfo;
		
   }
	
	
	

}
