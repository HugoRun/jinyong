package com.ls.ben.dao.cooparate.sky;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

/**
 * @author ls
 * 功能:u_sky_pay_record
 * Mar 13, 2009
 */
public class UPayRecordDao extends DaoBase
{
	/**
	 * 插入一条充值记录
	 * @return  返回键值
	 */
	public int insert(String skyid,String kbamt,int p_pk)
	{
		int id = -1;
		String sql = "insert into u_sky_pay_record (skyid,kbamt,pay_time,p_pk) values ('"
				+skyid+"',"
				+kbamt+",now(),"+p_pk+")";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			stmt.execute(sql);
			rs = stmt.getGeneratedKeys();
			if( rs.next() )
			{
				id = rs.getInt(1);
			}
			stmt.close();
		} catch (SQLException e) {
			e.toString();
		}
		finally
		{
			dbConn.closeConn();
		}
		return id;
	}
	
	
	
	/**
	 * 更新充值状态
	 */
	public void update( int id,String billid,String skybillid1,String skybillid2,String balance,String respones_result )
	{
		String sql = "update u_sky_pay_record  set billid='"+billid+"',skybillid1='"+skybillid1+ "',skybillid2='"+skybillid2+"',balance='"+balance+"',respones_result='"+respones_result+"' where id ="+id;
    	logger.debug(sql);
    	DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
    	conn = dbConn.getConn();
    	try {
    		stmt = conn.createStatement();
    		stmt.execute(sql);
    		stmt.close();
    	} catch (SQLException e) {
    		logger.debug(e.toString());
    	}
    	finally
    	{
    		dbConn.closeConn();
    	}
	}
	
	public int getMoney(int p_id)
	{
		int id = 0;
		String sql = "select kbamt from u_sky_pay_record where id = "+p_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if( rs.next() )
			{
				id = rs.getInt("kbamt");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.toString();
		}
		finally
		{
			dbConn.closeConn();
		}
		return id;
	}
}
