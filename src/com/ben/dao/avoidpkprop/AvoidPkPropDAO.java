/**
 * 
 */
package com.ben.dao.avoidpkprop;

import java.sql.ResultSet;

import com.ben.vo.avoidpkprop.AvoidPkPropVO;
import com.pub.db.mysql.SqlData;

/**
 * @author 侯浩军
 * 
 */
public class AvoidPkPropDAO
{
	SqlData con;

	/**
	 * 插入免PK道具的使用时间
	 */
	public void addAvoidPkProp(int pPK, String beginTime, String endTime)
	{
		try
		{
			con = new SqlData();
			String sql = "insert into u_avoidpkprop values(null,'" + pPK
					+ "','" + beginTime + "','" + endTime + "')";
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
	 * 删除保护时间
	 * 
	 * @param pPk
	 */
	public void deleteAvoidPkProp(int pPk)
	{
		try
		{
			con = new SqlData();
			String sql = "delete from u_avoidpkprop where p_pk='" + pPk + "'";
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
	 * 判断是否在保护时间内
	 * 
	 * @param pPk
	 * @param tim
	 * @return
	 */
	public boolean isAvoidPkPropTime(int pPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_avoidpkprop where end_time > now() and p_pk='" + pPk + "'";
			ResultSet rs = con.query(sql);
			if (rs.next())
			{
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

	/**
	 * 返回是否在有效保护范围内
	 * @param pPk
	 * @param time
	 * @return
	 */
	public AvoidPkPropVO getAvoidPkProp(int pPk, String time)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_avoidpkprop where begin_time < '"+time+"' and end_time > '"+time+"' and p_pk='" + pPk + "'";
			ResultSet rs = con.query(sql);
			AvoidPkPropVO vo = null;
			if (rs.next())
			{
				vo = new AvoidPkPropVO();
				vo.setAPk(rs.getInt("a_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setBeginTime(rs.getString("begin_time"));
				vo.setEndTime(rs.getString("end_time"));
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
}
