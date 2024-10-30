package com.ls.ben.dao.cooparate.bill;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.cooperate.bill.UAccountRecordVO;
import com.ls.pub.db.DBConnection;

/**
 * @author ls
 * ����:u_account_record
 * Mar 13, 2009
 */
public class UAccountRecordDao extends DaoBase
{
	Logger logger = Logger.getLogger("log.pay");
	/**
	 * ����һ����ֵ��¼
	 * @param account_record
	 * @return  ���ؼ�ֵ
	 */
	public int insert(UAccountRecordVO account_record)
	{
		if( account_record==null )
		{
			logger.debug("�����ֵ��¼ʱ����UAccountRecordVOΪ��");
		}
		int id = -1;
		String sql = "insert into u_account_record  values (null,"
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
	 * �õ�һ����ֵ��¼
	 * @param user_id
	 * @param channel_id
	 * @return
	 */
	public UAccountRecordVO getById( int id )
	{
		UAccountRecordVO account_record = null;
		
		String sql = "select * from u_account_record where id="+id;
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
	 * �õ�һ����ֵ��¼
	 * @param user_id
	 * @param channel_id
	 * @return
	 */
	public UAccountRecordVO getRecord( String code, String channel )
	{
		UAccountRecordVO account_record = null;
		
		String sql = "select * from u_account_record where code='"+code+"' and channel="+channel+" limit 1";
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
	 * ���³�ֵ״̬
	 * @param id
	 * @param account_state
	 */
	public void update( int id ,String account_state)
	{
		String sql = "update u_account_record  set account_state='"+account_state+ "',payment_time=now() where id ="+id;
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
