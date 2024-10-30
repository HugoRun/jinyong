/**
 * 
 */
package com.ben.dao.checkpcrequest;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ben.vo.checkpcrequest.CheckPcRequestVO;
import com.pub.db.jygamedb.Jygamedb;

/**
 * @author ��ƾ� ����PC�����û�����
 * 
 */
public class CheckPcRequestDAO
{
	Jygamedb con;

	/**
	 * ��ѯ�����ʱ������ذ�����IP
	 * 
	 * @return
	 */
	public List isCheckPcWhiteList()
	{
		try
		{
			con = new Jygamedb();
			String sql = "select * from ip_whitelist";
			ResultSet rs = con.query(sql);
			List list = new ArrayList();
			while (rs.next())
			{
				CheckPcRequestVO vo = new CheckPcRequestVO();
				vo.setIpPk(rs.getInt("ip_pk"));
				vo.setIpBegin(rs.getString("ip_begin"));
				vo.setIpEnd(rs.getString("ip_end"));
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
	 * ������
	 * @param ip
	 * @return
	 */
	public boolean isCheckPcBlackList(String ip)
	{
		try
		{
			con = new Jygamedb();
			String sql = "select * from ip_blacklist where ip_list='" + ip + "'";
			//System.out.println("**************  " + sql);
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
}
