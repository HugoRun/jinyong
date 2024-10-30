package com.lw.dao.laborage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.lw.vo.laborage.LaborageVO;

public class LaborageDao extends DaoBase
{

	public LaborageVO getLaborage()
	{
		LaborageVO vo = null;
		String sql = "SELECT * FROM laborage ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo = new LaborageVO();
				vo.setMinTime(rs.getInt("min_time"));
				vo.setMaxTime(rs.getInt("max_time"));
				vo.setLaborageBonus(rs.getString("laborage_bonus"));
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
		return vo;
	}

	/** 根据时间最大值获得该奖励的物品 */
	public LaborageVO getLaborageByTime(int min_time)
	{
		LaborageVO vo = null;
		String sql = "SELECT * FROM laborage WHERE min_time = " + min_time;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo = new LaborageVO();
				vo.setMinTime(rs.getInt("min_time"));
				vo.setMaxTime(rs.getInt("max_time"));
				vo.setLaborageBonus(rs.getString("laborage_bonus"));
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
		return vo;
	}

	/** 得到最小时间 */
	public List getMinTime()
	{
		List list = new ArrayList();
		String sql = "SELECT min_time FROM laborage ORDER BY max_time desc";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				int x = rs.getInt("min_time");
				list.add(x);
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
		return list;
	}

	public String getFirstDay(int x)
	{
		String sql = "SELECT adddate(sysdate(),-" + x + ")";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				Date date = rs.getDate(1);
				return date.getMonth() + 1 + "月" + date.getDate() + "日";
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
		return null;
	}

	public String getLastDay(int x)
	{
		String sql = "SELECT adddate(sysdate(),-" + x + ")";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				Date date = rs.getDate(1);
				return date.getMonth() + 1 + "月" + date.getDate() + "日";
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
		return null;
	}
}
