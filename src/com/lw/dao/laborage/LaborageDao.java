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
		String sql = "select * from laborage ";
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

	/** ����ʱ�����ֵ��øý�������Ʒ */
	public LaborageVO getLaborageByTime(int min_time)
	{
		LaborageVO vo = null;
		String sql = "select * from laborage where min_time = " + min_time;
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

	/** �õ���Сʱ�� */
	public List getMinTime()
	{
		List list = new ArrayList();
		String sql = "select min_time from laborage order by max_time desc";
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
		String sql = "select adddate(sysdate(),-" + x + ")";
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
				return date.getMonth() + 1 + "��" + date.getDate() + "��";
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
		String sql = "select adddate(sysdate(),-" + x + ")";
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
				return date.getMonth() + 1 + "��" + date.getDate() + "��";
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