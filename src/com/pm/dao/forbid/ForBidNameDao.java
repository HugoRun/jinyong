package com.pm.dao.forbid;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ls.ben.cache.staticcache.forbid.ForBidCache;
import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

public class ForBidNameDao extends DaoBase
{

	/**
	 * ȡ�ý�ֹȡ������
	 * @return
	 */
	public HashMap<Integer, String> getForBidName()
	{
		HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
		StringBuffer sBuffer = new StringBuffer();
		StringBuffer commBuffer = new StringBuffer();
		sBuffer.append("��Ӫ");
		commBuffer.append("���");
		String sql = "select onechar,str from jy_forbid_name";
		
        
        logger.debug("ִ�����ݿ�ͳ�Ƶ�="+sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try
        {
        	stmt = conn.createStatement();
        	rs = stmt.executeQuery(sql);
        	
        	while (rs.next())
        	{
        		sBuffer.append(",").append(rs.getString("str"));
        		commBuffer.append(",").append(rs.getString("onechar"));
        	}
        	//logger.debug("sBuffer="+sBuffer.toString());
        	//logger.debug("commBuffer="+commBuffer.toString());
        	
        	hashMap.put(ForBidCache.FORBIDNAME, sBuffer.toString());
        	hashMap.put(ForBidCache.FORBIDCOMM, commBuffer.toString());
        	rs.close();
        	stmt.close();
        } catch (SQLException e)
        {
        	e.printStackTrace();
        } finally
        {
        	
        	dbConn.closeConn();
        }
		return hashMap;		
	}
	

}
