/**
 * 
 */
package com.ben.dao.embargo;

import java.sql.ResultSet;
import com.pub.db.mysql.SqlData;

/**
 * @author ��ƾ� 9:27:47 AM
 */
public class EmbargoDAO
{
	SqlData con;

	/**
	 * �ж��Ƿ��ڽ���ʱ����
	 * 
	 * @param pPk
	 * @param time
	 * @return
	 */
	public String isEmbargo(int pPk, String time)
	{
		try
		{ 
			con = new SqlData();
			String sql = "select e_time from u_embargo where p_pk='" + pPk + "' and begin_time < '" + time + "' and end_time > '" + time + "'";
			ResultSet rs = con.query(sql);
			String eTime = null;
			if (rs.next())
			{
				eTime = rs.getString("e_time");
			}
			return eTime;
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
