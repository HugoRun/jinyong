/**
 * 
 */
package com.ben.dao.beoffprop;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ben.vo.beoffprop.BeOffPropVO;
import com.pub.db.jygamedb.Jygamedb;

/**
 * @author HHJ
 * 
 */
public class BeOffPropDAO
{
	Jygamedb con;

	/**
	 * 查询所有离线道具
	 * 
	 * @return
	 */
	public List getBeOffPropList()
	{
		try
		{
			con = new Jygamedb();
			String sql = "select * from be_off_prop";
			ResultSet rs = con.query(sql);
			BeOffPropVO vo = null;
			List list = new ArrayList();
			while (rs.next())
			{
				vo = new BeOffPropVO();
				vo.setBeId(rs.getInt("be_id"));
				vo.setPropName(rs.getString("prop_name"));
				vo.setPropDisplay(rs.getString("prop_display"));
				vo.setPropMoney(rs.getString("prop_money"));
				vo.setPropTime(rs.getString("prop_time"));
				list.add(vo);
			}
			return list;
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
	 * 查看详细信息
	 * 
	 * @param be_id
	 * @return
	 */
	public BeOffPropVO getBeOffPropView(int be_id)
	{
		try
		{
			con = new Jygamedb();
			String sql = "select * from be_off_prop where be_id = '" + be_id
					+ "'";
			ResultSet rs = con.query(sql);
			BeOffPropVO vo = null;
			if (rs.next())
			{
				vo = new BeOffPropVO();
				vo.setBeId(rs.getInt("be_id"));
				vo.setPropName(rs.getString("prop_name"));
				vo.setPropDisplay(rs.getString("prop_display"));
				vo.setPropMoney(rs.getString("prop_money"));
				vo.setPropTime(rs.getString("prop_time"));
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
