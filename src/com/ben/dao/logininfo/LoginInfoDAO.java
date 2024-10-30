package com.ben.dao.logininfo;

import java.sql.ResultSet;

import com.ben.vo.logininfo.LoginInfoVO;
import com.pub.MD5;
import com.pub.db.mysql.SqlData;

/**
 * @author 侯浩军
 * 
 * 3:07:36 PM
 */
public class LoginInfoDAO
{
	SqlData con;

	MD5 md5 = MD5.getInstance();

	// md5.getMD5ofStr();

	// 玩家登陆系统通过用户名
	public LoginInfoVO getUserInfoLoginName(String name)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_login_info where u_name='" + name
					+ "'";
			ResultSet rs = con.query(sql);
			LoginInfoVO vo = new LoginInfoVO();
			while (rs.next())
			{
				vo.setUPk(rs.getInt("u_pk"));
				vo.setUName(rs.getString("u_name"));
				vo.setUPaw(rs.getString("u_paw"));
				vo.setLoginState(rs.getInt("login_state"));
				vo.setCreateTime(rs.getString("create_time"));
			}
			return vo;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return null;
	}

	// 获得玩家注册信息通过uPk
	public LoginInfoVO getUserInfoByUPk(String uPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_login_info where u_pk=" + uPk;
			ResultSet rs = con.query(sql);
			LoginInfoVO vo = new LoginInfoVO();
			if (rs.next())
			{
				vo.setUPk(rs.getInt("u_pk"));
				vo.setUName(rs.getString("u_name"));
				vo.setUPaw(rs.getString("u_paw"));
				vo.setLoginState(rs.getInt("login_state"));
				vo.setCreateTime(rs.getString("create_time"));
			}
			return vo;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return null;
	}

	// 获得玩家登陆状态通过uPk
	public int getUserLoginInfoByUPk(String uPk)
	{
		int login_state = 0;
		try
		{
			con = new SqlData();
			String sql = "select login_state from u_login_info where u_pk="
					+ uPk;
			ResultSet rs = con.query(sql);
			if (rs.next())
			{
				login_state = rs.getInt("login_state");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return login_state;
	}

	// 玩家登陆系统通过密码
	public LoginInfoVO getUserInfoLoginPaw(String name, String paw)
	{
		LoginInfoVO vo = null;
		try
		{
			con = new SqlData();
			String sql = "select * from u_login_info where u_name='" + name
					+ "' and u_paw='" + paw + "'";
			ResultSet rs = con.query(sql);
			if (rs.next())
			{
				vo = new LoginInfoVO();
				vo.setUPk(rs.getInt("u_pk"));
				vo.setUName(rs.getString("u_name"));
				vo.setUPaw(rs.getString("u_paw"));
				vo.setLoginState(rs.getInt("login_state"));
				vo.setCreateTime(rs.getString("create_time"));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return vo;
	}

	// 玩家登陆系统通过密码
	public String getUserLoginPawByUPk(int upk)
	{
		try
		{
			con = new SqlData();
			String sql = "select u_paw from u_login_info where u_pk='" + upk
					+ "'";
			ResultSet rs = con.query(sql);
			String paw = "";
			while (rs.next())
			{
				paw = rs.getString("u_paw");
			}
			return paw;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return null;
	}


	// 玩家退出后修改登陆状态修改为1登陆 0 未登陆
	public void getloginStateTC(String loginState, String uPk)
	{
		try
		{
			con = new SqlData();
			String sql = "update u_login_info set login_state='" + loginState
					+ "' where u_pk=" + uPk;
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}

	// 设置所有玩家登陆状态为1登陆 0 未登陆
	public void updateLoginState(String loginState)
	{
		try
		{
			con = new SqlData();
			String sql = "update u_login_info set login_state='" + loginState
					+ "'";
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}

	/**
	 * 更新登录密码
	 * 
	 * @param u_pk
	 * @param newPass
	 */
	public void updatePassWord(int u_pk, String newPass)
	{
		try
		{
			con = new SqlData();
			String sql = "update u_login_info set u_paw='" + newPass
					+ "' where u_pk=" + u_pk;
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}

	}

	/**
	 * 判定是否在白名单
	 * @param name
	 * @return
	 */
	public boolean isLoginInfoName(String name)
	{
		try
		{
			con = new SqlData();
			String sql  = "select * from u_login_sift where u_name='"+name+"'";
			ResultSet rs = con.query(sql);
			while(rs.next()){
				return true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return false;
	}
}
