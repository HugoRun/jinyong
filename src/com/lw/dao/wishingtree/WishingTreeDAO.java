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

		String count_sql = "SELECT COUNT(*) FROM p_wishingtree WHERE top = 0 ORDER BY create_time desc";
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

			page_sql = "SELECT * FROM p_wishingtree WHERE top = 0 ORDER BY create_time DESC LIMIT "
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
		String sql = "SELECT * FROM p_wishingtree WHERE top = 1 ORDER BY top_time DESC LIMIT 3";
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
		String sql = "INSERT INTO p_wishingtree (id,p_pk,p_name,wishing,create_time) VALUES (null,"
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
		String sql = "UPDATE p_wishingtree SET top = 1 ,top_time = now() WHERE id = "
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
		String sql = "UPDATE p_wishingtree AS c SET c.top = 0 WHERE c.id IN (SELECT a.id FROM (SELECT b.id FROM p_wishingtree AS b WHERE b.top = 1 ORDER BY b.top_time LIMIT 1) a )";
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
		String sql = "SELECT COUNT(*) AS num FROM p_wishingtree WHERE top = 1";
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
		String sql = "DELETE FROM p_wishingtree WHERE id = " + id;
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
