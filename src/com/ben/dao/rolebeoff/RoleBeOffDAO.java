/**
 * 
 */
package com.ben.dao.rolebeoff;

import java.sql.ResultSet;

import com.ben.vo.rolebeoff.RoleBeOffVO;
import com.pub.db.mysql.SqlData;

/**
 * @author HHJ
 * 
 */
public class RoleBeOffDAO
{
	SqlData con;

	/**
	 * 通过玩家ID 找出自己的离线信息
	 * 
	 * @param p_pk
	 * @return
	 */
	public RoleBeOffVO getRoleBeOffRole(int p_pk)
	{
		try
		{
			con = new SqlData();
			String sql = "SELECT * FROM role_be_off WHERE p_pk='" + p_pk + "'";
			ResultSet rs = con.query(sql);
			RoleBeOffVO vo = null;
			if (rs.next())
			{
				vo = new RoleBeOffVO();
				vo.setOffId(rs.getInt("off_id"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setBeOffTime(rs.getString("be_off_time"));
				vo.setAlreadyTime(rs.getString("already_time"));
				vo.setBeOffExp(rs.getString("be_off_exp"));
				vo.setPropCumulateTime(rs.getString("prop_cumulate_time"));
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

	/**
	 * 更新离线时间 就是玩家每次下线修改一次这个表
	 * 
	 * @param p_pk
	 */
	public void upRoleBeOffRoleTime(String be_off_time, int p_pk)
	{
		try
		{
			con = new SqlData();
			String sql = "UPDATE role_be_off SET be_off_time='" + be_off_time + "' WHERE p_pk='" + p_pk + "'";
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
	 * 增加下线时间
	 * @param be_off_time
	 * @param p_pk
	 */
	public void addRoleBeOffRoleTime(String be_off_time, int p_pk){
		try
		{
			con = new SqlData();
			String sql = "INSERT INTO role_be_off(off_id,p_pk,be_off_time) VALUES(null,'"+p_pk+"','"+be_off_time+"')";
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
	 * 更新累积经验
	 */
	public void upAlreadyTime(String already_time, int p_pk)
	{
		try
		{
			con = new SqlData();
			String sql = "UPDATE role_be_off SET already_time='" + already_time
					+ "' WHERE p_pk='" + p_pk + "'";
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
	 * 更新累积时间
	 * 
	 * @param cumulate_time
	 * @param p_pk
	 */
	public void upRoleBeOffRoleCumulateTime(String cumulate_time, int p_pk)
	{
		try
		{
			con = new SqlData();
			String sql = "UPDATE role_be_off SET prop_cumulate_time='"
					+ cumulate_time + "' WHERE p_pk='" + p_pk + "'";
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
	 * 增加累积时间
	 * @param be_off_time
	 * @param p_pk
	 */
	public void addRoleBeOffRoleCumulateTime(String prop_cumulate_time, int p_pk){
		try
		{
			con = new SqlData();
			String sql = "INSERT INTO role_be_off(off_id,p_pk,prop_cumulate_time) values(null,'"+p_pk+"','"+prop_cumulate_time+"')";
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
}
