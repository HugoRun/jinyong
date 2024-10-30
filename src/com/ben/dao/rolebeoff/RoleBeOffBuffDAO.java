/**
 * 
 */
package com.ben.dao.rolebeoff;

import java.sql.ResultSet;

import com.ben.vo.rolebeoff.RoleBeOffBuffVO;
import com.pub.db.mysql.SqlData;

/**
 * @author HHJ
 * 
 */
public class RoleBeOffBuffDAO
{
	SqlData con;

	/**
	 * 查询玩家离线BUFF
	 * 
	 * @param p_pk
	 * @return
	 */
	public RoleBeOffBuffVO getRoleBeOffBuff(int p_pk)
	{
		try
		{
			con = new SqlData();
			String sql = "SELECT * FROM role_be_off_buff where p_pk='" + p_pk
					+ "'";
			ResultSet rs = con.query(sql);
			RoleBeOffBuffVO vo = null;
			if (rs.next())
			{
				vo = new RoleBeOffBuffVO();
				vo.setBId(rs.getInt("b_id"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setBeOffTime(rs.getString("be_off_time"));
				vo.setBeOffExp(rs.getString("be_off_exp"));
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
	 * 增加时间
	 */
	public void addRoleBeOffBuffTime(String be_off_time, int p_pk)
	{
		try
		{
			con = new SqlData();
			String sql = "INSERT INTO role_be_off_buff(b_id,p_pk,be_off_time) values(null,'"
					+ p_pk + "','" + be_off_time + "')";
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
	 * 修改离线时间
	 * 
	 * @param be_off_time
	 * @param p_pk
	 */
	public void updateRoleBeOffBuffTime(String be_off_time, int p_pk)
	{
		try
		{
			con = new SqlData();
			String sql = "update role_be_off_buff set be_off_time='"
					+ be_off_time + "' where  p_pk='" + p_pk + "'";
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
	 * 修改经验
	 * @param be_off_exp
	 * @param p_pk
	 */
	public void updateRoleBeOffBuffExp(String be_off_exp,int p_pk)
	{
		try
		{
			con = new SqlData();
			String sql = "update role_be_off_buff set be_off_exp='"
					+ be_off_exp + "' where  p_pk='" + p_pk + "'";
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
	 * 增加经验
	 */
	public void addRoleBeOffBuffExp(String be_off_exp, int p_pk)
	{
		try
		{
			con = new SqlData();
			String sql = "INSERT INTO role_be_off_buff(b_id,p_pk,be_off_exp) values(null,'" + p_pk + "','" + be_off_exp + "')";
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
