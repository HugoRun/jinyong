package com.ls.ben.dao.login;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

public class LoginDao extends DaoBase
{
	/**
	 * ����һ���µ��˺�
	 * @param user_name
	 * @param pwd
	 * @return    ����u_pk 
	 */
	public int incert( String user_name ,String pwd,String login_ip )
	{
		int u_pk = -1;
		String sql = "insert into u_login_info values(null,'" + user_name+ "',MD5('" + pwd + "'),'" + 1 + "',now(),'"+login_ip+"',now(),0,0)";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			rs = stmt.getGeneratedKeys();
			if (rs.next()) 
			{
				u_pk = rs.getInt(1);
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
		return u_pk;
	}
	
	/**
	 * �����˺ŵ�½״̬
	 * @param user_name
	 * @param pwd
	 * @return    ����u_pk
	 */
	public void updateState( String u_pk,String login_ip )
	{
		String sql = "update u_login_info set login_state='1',last_login_ip = '"+login_ip+"',last_login_time = now() where u_pk="+u_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
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
	 * �жϸ��û����Ƿ����
	 * @param user_name
	 * @return
	 */
	public boolean isHaveName(String user_name )
	{
		boolean result = false;
		String sql = "select u_pk from  u_login_info where u_name = '"+user_name+"'";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if ( rs.next()) 
			{
				result = true;
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
		return result;
	}
}
