package com.lw.dao.synthesize;

import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.lw.vo.synthesize.SynthesizeVO;

public class SynthesizeDao extends DaoBase
{

	/** �õ��䷽����Ϣ */
	public SynthesizeVO getSynthesize(int s_id)
	{
		SynthesizeVO vo = null;
		String sql = "select * from synthesize where  s_id = " + s_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{

				vo = new SynthesizeVO();
				vo.setSynthesizeID(rs.getInt("s_id"));
				vo.setProp(rs.getString("prop"));
				vo.setSynthesizeProp(rs.getString("s_prop"));
				vo.setSynthesizeSleight(rs.getInt("s_sleight"));
				vo.setSynthesizeMaxSleight(rs.getInt("s_max_sleight"));
				vo.setSynthesizeBook(rs.getInt("s_book"));
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

	/** ������������͵õ��䷽�� */
	public List<SynthesizeVO> getSynthesize(int s_type, int s_level)
	{
		List<SynthesizeVO> list = new ArrayList<SynthesizeVO>();
		SynthesizeVO vo = null;
		String sql = "select * from synthesize where  s_type = " + s_type
				+ " and s_level <= " + s_level;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{

				vo = new SynthesizeVO();
				vo.setSynthesizeID(rs.getInt("s_id"));
				vo.setProp(rs.getString("prop"));
				vo.setSynthesizeProp(rs.getString("s_prop"));
				vo.setSynthesizeSleight(rs.getInt("s_sleight"));
				vo.setSynthesizeMaxSleight(rs.getInt("s_max_sleight"));
				vo.setSynthesizeBook(rs.getInt("s_book"));
				list.add(vo);
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

	/** �����䷽ID�õ������������ */
	public int getSynthesizeMaxSleight(int s_id)
	{
		int s_max_sleight = 0;
		String sql = "select s_max_sleight from synthesize where  s_id = "
				+ s_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				s_max_sleight = rs.getInt("s_max_sleight");
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
		return s_max_sleight;
	}

	/** �����䷽ID�õ���Ҫ���������� */
	public int getSynthesizeMinSleight(int s_id)
	{
		int s_min_sleight = 0;
		String sql = "select s_min_sleight from synthesize where  s_id = "
				+ s_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				s_min_sleight = rs.getInt("s_min_sleight");
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
		return s_min_sleight;
	}

	/** �����䷽ID�õ����������� */
	public int getSynthesizeSleight(int s_id)
	{
		int s_sleight = 0;
		String sql = "select s_sleight from synthesize where  s_id = " + s_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				s_sleight = rs.getInt("s_sleight");
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
		return s_sleight;
	}

	/** �����䷽ID�ж��Ƿ���Ҫ�䷽�� */
	public int getSynthesizeHaveBook(int s_id)
	{
		int s_book = 0;
		String sql = "select s_book from synthesize where  s_id = " + s_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				s_book = rs.getInt("s_book");
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
		return s_book;
	}

	/** ������Ʒ���Ƶõ���Ʒ����Ϣ */
	public String getPropInfo(String prop_name)
	{
		String info = null;
		String sql = "select prop_display from prop where  prop_name = '"
				+ prop_name + "'";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				info = rs.getString("prop_display");
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
		return info;
	}

	/** �䷽��ҳ���� */
	public List<SynthesizeVO> getSynthesizeList(int s_type, int s_level,
			int thispage, int perpagenum)
	{
		List<SynthesizeVO> list = new ArrayList<SynthesizeVO>();
		SynthesizeVO vo = null;
		String sql = "select * from synthesize where  s_type = " + s_type
				+ " and s_level <= " + s_level + " limit " + perpagenum
				+ " offset " + perpagenum * (thispage - 1);
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{

				vo = new SynthesizeVO();
				vo.setSynthesizeID(rs.getInt("s_id"));
				vo.setProp(rs.getString("prop"));
				vo.setSynthesizeProp(rs.getString("s_prop"));
				vo.setSynthesizeSleight(rs.getInt("s_sleight"));
				vo.setSynthesizeMinSleight(rs.getInt("s_min_sleight"));
				vo.setSynthesizeMaxSleight(rs.getInt("s_max_sleight"));
				vo.setSynthesizeBook(rs.getInt("s_book"));
				list.add(vo);
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
}
