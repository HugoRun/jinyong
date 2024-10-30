package com.ls.ben.dao.register;

import java.sql.ResultSet;

import com.ben.vo.logininfo.LoginInfoVO;
import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

/**
 * @author ls 功能:注册dao Jan 10, 2009
 */
public class RegisterDao extends DaoBase
{
	/**
	 * 添加用户
	 */
	public int addUser(String user_name, String ip)
	{
		int u_pk = -1;

		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{

			String sql = "insert into u_login_info values(null,?,'',1,now(),?,now(),0,0)";
			logger.debug(sql);
			ps = conn.prepareStatement(sql);
			ps.setString(1, user_name);
			ps.setString(2, ip);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next())
			{
				u_pk = rs.getInt(1);
			}

			rs.close();
			ps.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return u_pk;
	}

	/**
	 * 添加用户
	 */
	public int addUser(String user_name,String pwass, String ip,String super_qudao,String qudao)
	{
		int u_pk = -1;

		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{

			String sql = "insert into u_login_info(u_pk,u_name,u_paw,login_state,create_time,last_login_ip,last_login_time,yuanbao,jifen,super_qudao,qudao) values(null,'" + user_name+ "','"+pwass+"',1,now(),'" + ip + "',now(),0,0,'"+(super_qudao==null?"":super_qudao.trim())+"','"+(qudao==null?"":qudao.trim())+"')";
			logger.debug(sql);
			stmt = conn.createStatement();
			stmt.execute(sql);

			rs = stmt.getGeneratedKeys();
			if (rs.next())
			{
				u_pk = rs.getInt(1);
			}

			rs.close();
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return u_pk;
	}
	/** 更新登陆用户IP */
	public void updateIp(int u_pk, String ip)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{

			String sql = "update u_login_info set last_login_ip = '" + ip
					+ "' where u_pk = " + u_pk;
			logger.debug(sql);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}
	
	@SuppressWarnings("finally")
	public LoginInfoVO findByUpk(int uPk){
		LoginInfoVO li = null;
		String sql = "select * from u_login_info u where u.u_pk = "+uPk;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				li = new LoginInfoVO();
				li.setCreateTime(rs.getString("create_time"));
				li.setLoginState(rs.getInt("login_state"));
				li.setQudao(rs.getString("qudao"));
				li.setSuper_qudao(rs.getString("super_qudao"));
				li.setUName(rs.getString("u_name"));
				li.setUPk(rs.getInt("u_pk"));
			}
			rs.close();
			stmt.close();
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
			return li;
		}
	}
	
    public void updateQudao(int uPk,String super_qudao,String qudao){
		String sql = "update u_login_info u set u.super_qudao = '"+super_qudao.trim()+"' ,u.qudao = '"+qudao+"' where u.u_pk = "+uPk;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally
		{
			dbConn.closeConn();
		}
	}
	
	public void relationPassportSina(String sina_uid, String passport_visitor){
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{

			String sql = "update u_passport_info as a, u_login_info as b set a.user_id = '"+sina_uid+"' ,a.user_name = '"+sina_uid+"',b.u_name = '"+sina_uid+"' where a.user_id = b.u_name and a.user_id = '"+passport_visitor+"'";
			logger.debug(sql);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}

}
