package com.lw.dao.wishingtree;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;
import com.lw.vo.wishingtree.WishingTreeVO;

public class WishingTreeDAO extends DaoBase
{
	/** 得到玩家信息 */
	public QueryPage getAllWishing(int page_no)
	{
		QueryPage queryPage = null;

		List<WishingTreeVO> list = new ArrayList<WishingTreeVO>();
		WishingTreeVO vo = null;

		int count = 0;

		String count_sql = "select count(*) from p_wishingtree where top = 0 order by create_time desc";
		String page_sql = null;

		logger.debug(count_sql);
		logger.debug(page_sql);

		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(count_sql);
			if (rs.next())
			{
				count = rs.getInt(1);
			}
			rs.close();

			queryPage = new QueryPage(page_no, count);

			page_sql = "select * from p_wishingtree where top = 0 order by create_time desc limit "
					+ queryPage.getStartOfPage()
					+ ","
					+ queryPage.getPageSize();

			rs = stmt.executeQuery(page_sql);
			while (rs.next())
			{
				vo = new WishingTreeVO();
				vo.setId(rs.getInt("id"));
				vo.setP_pk(rs.getInt("p_pk"));
				vo.setName(rs.getString("p_name"));
				vo.setWishing(rs.getString("wishing"));
				list.add(vo);
			}
			queryPage.setResult(list);
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
		return queryPage;
	}

	/** 得到玩家信息 */
	public List<WishingTreeVO> getTopWishing()
	{
		List<WishingTreeVO> list = new ArrayList<WishingTreeVO>();
		WishingTreeVO vo = null;
		String sql = "select * from p_wishingtree where top = 1 order by top_time desc limit 3";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new WishingTreeVO();
				vo.setId(rs.getInt("id"));
				vo.setP_pk(rs.getInt("p_pk"));
				vo.setName(rs.getString("p_name"));
				vo.setWishing(rs.getString("wishing"));
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

	// 插入祝福
	public void insertWishing(int p_pk, String p_name, String wishing)
	{
		String sql = "insert into p_wishingtree (id,p_pk,p_name,wishing,create_time) values (null,"
				+ p_pk + ",'" + p_name + "','" + wishing + "',now())";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e)
		{
			logger.info(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
	}

	// 更新祝福
	public void setTopWishing(int id)
	{
		String sql = "update p_wishingtree set top = 1 ,top_time = now() where id = "
				+ id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e)
		{
			logger.info(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
	}

	// 更新祝福
	public void setNomarWishing()
	{
		String sql = "update p_wishingtree as c set c.top = 0 where c.id in(select a.id from (select b.id from p_wishingtree as b where b.top = 1 order by b.top_time limit 1) a )";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e)
		{
			logger.info(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
	}

	/** 得到玩家信息 */
	public int getTopWishingNum()
	{
		int num = 0;
		String sql = "select count(*) as num from p_wishingtree where top = 1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				num = rs.getInt("num");
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
		return num;
	}

	// 更新祝福
	public void deleteWishing(int id)
	{
		String sql = "delete from p_wishingtree where id = " + id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e)
		{
			logger.info(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
	}
}
