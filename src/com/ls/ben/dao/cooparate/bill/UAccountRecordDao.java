package com.ls.ben.dao.cooparate.bill;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.cooperate.bill.UAccountRecordVO;
import com.ls.pub.db.DBConnection;

/**
 * @author ls
 * 功能:u_account_record
 * Mar 13, 2009
 */
public class UAccountRecordDao extends DaoBase
{
	Logger logger = Logger.getLogger("log.pay");
	/**
	 * 插入一条充值记录
	 * @param account_record
	 * @return  返回键值
	 */
	public int insert(UAccountRecordVO account_record)
	{
		if( account_record==null )
		{
			logger.debug("插入充值记录时错误，UAccountRecordVO为空");
		}
		int id = -1;
		String sql = "INSERT INTO u_account_record  VALUES (null,"
				+account_record.getUPk()+","
				+account_record.getPPk()+",'"
				+account_record.getCode()+"','"
				+account_record.getPwd()+"',"
				+account_record.getMoney()+",'"
				+account_record.getChannel()+"','"
				+account_record.getAccountState()
				+"',now(),null)";
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
	 * 得到一条充值记录
	 * @param user_id
	 * @param channel_id
	 * @return
	 */
	public UAccountRecordVO getById( int id )
	{
		UAccountRecordVO account_record = null;
		
		String sql = "SELECT * FROM u_account_record WHERE id="+id;
		logger.info(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if( rs.next() )
			{
				account_record = new UAccountRecordVO();
				account_record.setId(rs.getInt("id"));
				account_record.setUPk(rs.getInt("u_pk"));
				account_record.setPPk(rs.getInt("p_pk"));
				account_record.setMoney(rs.getInt("money"));
				account_record.setChannel(rs.getString("channel"));
				account_record.setAccountState(rs.getString("account_state"));
			}
			
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		
		return account_record;
	}
	/**
	 * 得到一条充值记录
	 * @param user_id
	 * @param channel_id
	 * @return
	 */
	public UAccountRecordVO getRecord( String code, String channel )
	{
		UAccountRecordVO account_record = null;
		
		String sql = "SELECT * FROM u_account_record WHERE code='"+code+"' AND channel="+channel+" LIMIT 1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if( rs.next() )
			{
				account_record = new UAccountRecordVO();
				account_record.setId(rs.getInt("id"));
				account_record.setUPk(rs.getInt("u_pk"));
				account_record.setPPk(rs.getInt("p_pk"));
				account_record.setMoney(rs.getInt("money"));
				account_record.setChannel(rs.getString("channel"));
				account_record.setAccountState(rs.getString("account_state"));
			}
			
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		
		return account_record;
	}
	
	
	/**
	 * 更新充值状态
	 * @param id
	 * @param account_state
	 */
	public void update( int id ,String account_state)
	{
		String sql = "UPDATE u_account_record  SET account_state='"+account_state+ "',payment_time=now() WHERE id ="+id;
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
	
}
