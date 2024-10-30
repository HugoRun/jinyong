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
	 * 插入到二级密码表中
	 * @param u_pk
	 * @param secondPass
	 */
	public void insertSecondPass(int u_pk, String pass_md5)
	{
		String sql = "UPDATE u_second_pass SET second_pass = '"+pass_md5+"' WHERE u_pk = "+u_pk;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("插入到二级密码表中="+sql);
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
	 * 通过upk获取用户的二级密码
	 * @param pk
	 * @return
	 */
	public String getUserLoginPawByUPk(int u_pk)
	{
		String sql = "SELECT second_pass FROM u_second_pass WHERE u_pk = "+u_pk;
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
	 * 判断此账号是否已经设过二级密码
	 */
	public int hasAlreadySecondPass(int u_pk)
	{
		String sql = "SELECT pass_mail_send FROM u_second_pass WHERE u_pk = "+u_pk;
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
	 * 判断此账号是否已经设过二级密码
	 */
	public boolean hasAlreadySecondPass(String u_pk)
	{
		String sql = "SELECT u_pk FROM u_second_pass WHERE u_pk = "+u_pk;
		
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
	 * 将玩家的是否发送过二级密码设置邮件标志置为i
	 * @param pk
	 * @param i
	 */
	public void updateSendFlag(String u_pk,String pass_md5, int i)
	{
		String sql = "INSERT INTO u_second_pass VALUES (null,"+u_pk+",'"+pass_md5+"',now(),now(),now(),0,1)";
		logger.debug("将玩家的是否发送过二级密码设置邮件标志置为i="+sql);
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
	 * 获取第二次二级密码错误修改时间
	 * @param u_pk
	 * @return
	 */
	public SecondPassVO getSecondPassTime(int u_pk)
	{
		String sql = "SELECT * FROM u_second_pass WHERE u_pk = "+u_pk;
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
	 * 当输入二级密码错误一次时, 更新一次
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
		
		String sql = "UPDATE u_second_pass SET pass_first_time='"+passSecondTime
						+"',pass_second_time = '"+passThirdTime+"', pass_third_time = now(), pass_wrong_flag = pass_wrong_flag + 1 "
						+" WHERE u_pk = "+u_pk;
		logger.debug("当输入二级密码错误一次时="+sql);
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
	 * 更新二次密码的输入次数
	 * @param u_pk
	 * @param i
	 * @param j
	 */
	public void updateSecondPass(int u_pk, int second_pass_flag)
	{
		String sql = "UPDATE u_second_pass SET pass_wrong_flag="+second_pass_flag+" WHERE u_pk = "+u_pk;
		logger.debug("更新二次密码的输入次数="+sql);
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
