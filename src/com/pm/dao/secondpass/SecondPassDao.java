package com.pm.dao.secondpass;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.pm.vo.passsecond.SecondPassVO;

public class SecondPassDao extends DaoBase {
	/**
	 * ���뵽�����������
	 * @param u_pk
	 * @param secondPass
	 */
	public void insertSecondPass(int u_pk, String pass_md5)
	{
		String sql = "update u_second_pass set second_pass = '"+pass_md5+"' where u_pk="+u_pk;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("���뵽�����������="+sql);
		try{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e){
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
	}

	/**
	 * ͨ��upk��ȡ�û��Ķ�������
	 * @param pk
	 * @return
	 */
	public String getUserLoginPawByUPk(int u_pk)
	{
		String sql = "select second_pass from u_second_pass where u_pk="+u_pk;
		String paw = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				paw = rs.getString("second_pass");
			}
		} catch (SQLException e){
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return paw;
	}

	/**
	 * �жϴ��˺��Ƿ��Ѿ������������
	 */
	public int hasAlreadySecondPass(int u_pk)
	{
		String sql = "select pass_mail_send from u_second_pass where u_pk="+u_pk;
		int paw = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{	
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				paw = rs.getInt("pass_mail_send");
			}	
		} catch (SQLException e){
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return paw;
	}
	
	/**
	 * �жϴ��˺��Ƿ��Ѿ������������
	 */
	public boolean hasAlreadySecondPass(String u_pk)
	{
		String sql = "select u_pk from u_second_pass where u_pk="+u_pk;
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{	
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				return true;
			}		
		} catch (SQLException e){
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}				
		return false;
	}

	/**
	 * ����ҵ��Ƿ��͹��������������ʼ���־��Ϊi
	 * @param pk
	 * @param i
	 */
	public void updateSendFlag(String u_pk,String pass_md5, int i)
	{
		String sql = "insert into u_second_pass values (null,"+u_pk+",'"+pass_md5+"',now(),now(),now(),0,1)";
		logger.debug("����ҵ��Ƿ��͹��������������ʼ���־��Ϊi="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e){
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
	}

	/**
	 * ��ȡ�ڶ��ζ�����������޸�ʱ��
	 * @param u_pk
	 * @return
	 */
	public SecondPassVO getSecondPassTime(int u_pk)
	{
		String sql = "select * from u_second_pass where u_pk="+u_pk;
		SecondPassVO secondPassVO = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				secondPassVO = new SecondPassVO();
				secondPassVO.setPassId(rs.getInt("pass_id"));
				secondPassVO.setUPk(rs.getInt("u_pk"));
				secondPassVO.setSecondPass(rs.getString("second_pass"));
				secondPassVO.setPassFirstTime(rs.getString("pass_first_time"));
				secondPassVO.setPassSecondTime(rs.getString("pass_second_time"));
				secondPassVO.setPassThirdTime(rs.getString("pass_third_time"));
				secondPassVO.setPassWrongFlag(rs.getInt("pass_wrong_flag"));
			}
		} catch (SQLException e){
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return secondPassVO;
	}

	/**
	 * ����������������һ��ʱ, ����һ��
	 * @param u_pk
	 * @param passSecondTime
	 */
	public void updateSecondPassSelect(int u_pk, String passSecondTime,String passThirdTime)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date passSecondDate = null;
		Date thirdDate = null ; 
		try
		{
			passSecondDate = df.parse(passSecondTime);
			thirdDate = df.parse(passThirdTime);
			passSecondTime = df.format(passSecondDate);
			passThirdTime = df.format(thirdDate);
		}
		catch (ParseException e1)
		{
			e1.printStackTrace();
		}
		
		String sql = "update u_second_pass set pass_first_time='"+passSecondTime
						+"',pass_second_time = '"+passThirdTime+"', pass_third_time = now(), pass_wrong_flag = pass_wrong_flag + 1 "
						+" where u_pk = "+u_pk;
		logger.debug("����������������һ��ʱ="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e){
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		
	}

	/**
	 * ���¶���������������
	 * @param u_pk
	 * @param i
	 * @param j
	 */
	public void updateSecondPass(int u_pk, int second_pass_flag)
	{
		String sql = "update u_second_pass set pass_wrong_flag="+second_pass_flag+" where u_pk="+u_pk;
		logger.debug("���¶���������������="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e){
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		
		
	}

}
