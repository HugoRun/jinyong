/**
 * 
 */
package com.ben.dao.friend;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ben.vo.friend.BlacklistVO;
import com.pub.db.mysql.SqlData;

/**
 * @author 侯浩军 黑名单 4:45:09 PM
 */
public class BlacklistDAO
{
	SqlData con;

	/**
	 * 加黑名单
	 */
	public void blacklistAdd(int pPk, String pByPk, String pByName, String time)
	{
		try
		{
			con = new SqlData();
			String sql = "insert into u_blacklist values(null,'" + pPk + "','"
					+ pByPk + "','" + pByName + "','" + time + "')";
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
	 * 查询是否已经有该黑名单了
	 */
	public boolean whetherblacklist(int pPk, String pByPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_blacklist where p_pk='" + pPk
					+ "' and bl_pk='" + pByPk + "'";
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
	 * 好友List
	 */
	public List<BlacklistVO> getBlacklistList(int pPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_blacklist where p_pk='" + pPk + "'";
			ResultSet rs = con.query(sql);
			List<BlacklistVO> list = new ArrayList<BlacklistVO>();
			while (rs.next())
			{
				BlacklistVO vo = new BlacklistVO();
				vo.setBPk(rs.getInt("b_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setBlPk(rs.getInt("bl_pk"));
				vo.setBName(rs.getString("b_name"));
				vo.setCreateTime(rs.getString("create_time"));
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
	 * 好友List列表
	 */
	public List<BlacklistVO> getBlacklistListPage(int pPk,int page,int perpagenum)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_blacklist where p_pk='" + pPk + "' limit "+page*perpagenum+","+perpagenum;
			ResultSet rs = con.query(sql);
			List<BlacklistVO> list = new ArrayList<BlacklistVO>();
			while (rs.next())
			{
				BlacklistVO vo = new BlacklistVO();
				vo.setBPk(rs.getInt("b_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setBlPk(rs.getInt("bl_pk"));
				vo.setBName(rs.getString("b_name"));
				vo.setCreateTime(rs.getString("create_time"));
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
	 * 删除好友
	 */
	public void getDeleteBlacklist(int pPk, String pByPk)
	{
		try
		{
			con = new SqlData();
			String sql = "delete from u_blacklist where p_pk='" + pPk
					+ "' and bl_pk='" + pByPk + "'";
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
	 * 返回是否在自己的黑名单中
	 * @param pPk   玩家PK
	 * @param blp_pk  黑名单玩家PK
	 * @return
	 */
	public boolean isBlacklist(int pPk,int blp_pk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_blacklist where p_pk='"+pPk+"' and bl_pk='"+blp_pk+"'";
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
