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
	 * ͨ�����ID �ҳ��Լ���������Ϣ
	 * 
	 * @param p_pk
	 * @return
	 */
	public RoleBeOffVO getRoleBeOffRole(int p_pk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from role_be_off where p_pk='" + p_pk + "'";
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
	 * ��������ʱ�� �������ÿ�������޸�һ�������
	 * 
	 * @param p_pk
	 */
	public void upRoleBeOffRoleTime(String be_off_time, int p_pk)
	{
		try
		{
			con = new SqlData();
			String sql = "update role_be_off set be_off_time='" + be_off_time + "' where p_pk='" + p_pk + "'";
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
	 * ��������ʱ��
	 * @param be_off_time
	 * @param p_pk
	 */
	public void addRoleBeOffRoleTime(String be_off_time, int p_pk){
		try
		{
			con = new SqlData();
			String sql = "insert into role_be_off(off_id,p_pk,be_off_time) values(null,'"+p_pk+"','"+be_off_time+"')";
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
	 * �����ۻ�����
	 */
	public void upAlreadyTime(String already_time, int p_pk)
	{
		try
		{
			con = new SqlData();
			String sql = "update role_be_off set already_time='" + already_time
					+ "' where p_pk='" + p_pk + "'";
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
	 * �����ۻ�ʱ��
	 * 
	 * @param cumulate_time
	 * @param p_pk
	 */
	public void upRoleBeOffRoleCumulateTime(String cumulate_time, int p_pk)
	{
		try
		{
			con = new SqlData();
			String sql = "update role_be_off set prop_cumulate_time='"
					+ cumulate_time + "' where p_pk='" + p_pk + "'";
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
	 * �����ۻ�ʱ��
	 * @param be_off_time
	 * @param p_pk
	 */
	public void addRoleBeOffRoleCumulateTime(String prop_cumulate_time, int p_pk){
		try
		{
			con = new SqlData();
			String sql = "insert into role_be_off(off_id,p_pk,prop_cumulate_time) values(null,'"+p_pk+"','"+prop_cumulate_time+"')";
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
