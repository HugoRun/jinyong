package com.ls.ben.dao.goods.equip;

import org.apache.log4j.Logger;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

/**
 * װ��չʾdao
 * @author �ſ��� 
 * @version 1.0 
 *	2009.08.07 10:58
 *	
 */
public class EquipRelelaDao extends DaoBase
{

	Logger logger = Logger.getLogger("log.dao");
	
	/**
	 * ����װ����ʾ��Ϣ
	 * @param pwPk
	 * @param displayString
	 */
	public void insertEquipRelela(int pwPk, String displayString)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			String sql = "insert into zb_relela_info values (null,"+pwPk+",'"+displayString+"',now())";
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
	 * ���װ��չʾ��Ϣ
	 * @param pwpk
	 * @return
	 */
	public String getEquipRelelaInfo(String pwpk)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String relelaInfo = "";
		try {
    		String sql = "select relelavar from zb_relela_info where pwpk = "+pwpk;
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
