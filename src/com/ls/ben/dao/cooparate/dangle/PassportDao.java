package com.ls.ben.dao.cooparate.dangle;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.cooperate.dangle.PassportVO;
import com.ls.pub.db.DBConnection;

/**
 * @author ls
 * 功能:当乐通行证dao（u_passport_info）
 * Jan 10, 2009
 */
public class PassportDao extends DaoBase
{
	/**
	 * 添加新的通行证信息
	 * @param user_id
	 * @param user_name
	 * @param u_pk
	 * @param channel_id                         渠道id
	 */
	public void addNewPassport(String user_id,String user_name,int u_pk,int channel_id)
	{
		String sql = "insert into u_passport_info (user_id,user_name,channel_id,u_pk,create_time) values (?,?,?,?,now())";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user_id);
			ps.setString(2, user_name);
			ps.setInt(3, channel_id);
			ps.setInt(4, u_pk);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.toString();
		}
		finally
		{
			dbConn.closeConn();
		}
	}
	
	/**
	 * 判断是否有当前通行证信息
	 * @param user_id
	 * @param channel_id             渠道id
	 * @return
	 */
	public boolean isHavePassport( int user_id,int channel_id )
	{
		boolean result = false;
		
		String sql = "select id from u_passport_info where user_id="+user_id+" and channel_id="+channel_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if( rs.next() )
			{
				result = true;
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
		
		return result;
	}
	
	/**
	 * 根据user_id查询通行证信息
	 * @param user_id
	 * @param channel_id            渠道id
	 * @return
	 */
	public PassportVO getPassportByUserID( String user_id, int channel_id)
	{
		PassportVO passport = null;
		
		String sql = "select * from u_passport_info where user_id='"+user_id+"' and channel_id="+channel_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if( rs.next() )
			{
				passport = new PassportVO();
				passport.setId(rs.getInt("id"));
				passport.setUserId(rs.getString("user_id"));
				passport.setUserName(rs.getString("user_name"));
				passport.setUserState(rs.getInt("user_state"));
				passport.setUPk(rs.getInt("u_pk"));
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
		
		return passport;
	}
	/**
	 * 根据用户名查询通行证信息
	 * @param user_name
	 * @param channel_id
	 * @return
	 */
	public PassportVO getPassportByUserName( String user_name, int channel_id)
	{
		PassportVO passport = null;
		
		String sql = "select * from u_passport_info where user_name='"+user_name+"' and channel_id='"+channel_id+"'";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if( rs.next() )
			{
				passport = new PassportVO();
				passport.setId(rs.getInt("id"));
				passport.setUserId(rs.getString("user_id"));
				passport.setUserName(rs.getString("user_name"));
				passport.setUserState(rs.getInt("user_state"));
				passport.setUPk(rs.getInt("u_pk"));
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
		
		return passport;
	}
	/**
	 * 根据u_pk查询通行证信息
	 */
	public PassportVO getPassportByUPk( int u_pk )
	{
		PassportVO passport = null;
		
		String sql = "select * from u_passport_info where u_pk="+u_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if( rs.next() )
			{
				passport = new PassportVO();
				passport.setId(rs.getInt("id"));
				passport.setUserId(rs.getString("user_id"));
				passport.setUserName(rs.getString("user_name"));
				passport.setUserState(rs.getInt("user_state"));
				passport.setUPk(u_pk);
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
		
		return passport;
	}
	
	
	/**
	 * 更新用户状态
	 */
	public void updateState( int u_pk ,int state )
	{
		
	}
}
