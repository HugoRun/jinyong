/**
 * 
 */
package com.ls.ben.dao.system;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

/**
 * ���ܣ����Ƶ�����Ҽ�¼
 * @author ls
 * Apr 29, 2009
 * 11:43:00 AM
 */
public class ExceptionUserLogDao extends DaoBase
{
	public void insert(String uPk, String pPk,String exception_ip,String time_space,String time)
	{
		String sql = "insert into exception_user_log values (null,'"+uPk+"','"+pPk+"','"+exception_ip+"','"+time_space+"','"+time+"')";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_LOG_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
	}
	
	/**
	 * �ж��Ƿ��иü�¼
	 * @return
	 */
	public boolean isHave(String uPk,String pPk,String exception_ip)
	{
		boolean result = false;
		String sql = "select * from exception_user_log where p_pk="+pPk+" and exception_ip='"+exception_ip+"' and now() < log_time";
		//System.out.println("----------------------- "+sql);
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_LOG_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if( rs.next() )
			{
				result = true;
			}
			
			rs.close();
			stmt.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return result;
	}
	
	/**
	 * �ж��Ƿ��иü�¼
	 * @return
	 */
	public boolean isHave(String uPk)
	{
		boolean result = false;
		String sql = "select * from exception_user_log where u_pk="+uPk+" and log_time > now()";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_LOG_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if( rs.next() )
			{
				result = true;
			}
			
			rs.close();
			stmt.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return result;
	}
}