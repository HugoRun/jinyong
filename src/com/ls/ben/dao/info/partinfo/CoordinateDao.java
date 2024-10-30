package com.ls.ben.dao.info.partinfo;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.partinfo.CoordinateVO;
import com.ls.pub.db.DBConnection;

/**
 * 功能:u_coordinate_info表
 * @author 刘帅 4:25:53 PM
 */
public class CoordinateDao extends DaoBase
{
	/**
	 * 添加标记信息
	 * 
	 * @param coordinate
	 */
	public void add(CoordinateVO coordinate)
	{
		if (coordinate == null)
		{
			logger.debug("coordinate为空");
		}
		String sql = "INSERT INTO u_coordinate_info(p_pk,coordinate_prop_id,coordinate) values ("
				+ coordinate.getPPk()
				+ ","
				+ coordinate.getCoordinatePropId()
				+ "," + coordinate.getCoordinate() + ")";
		logger.debug(sql);
		logger.debug("p_pk:" + coordinate.getPPk() + ";CoordinatePropId="
				+ coordinate.getCoordinatePropId() + ";Coordinate="
				+ coordinate.getCoordinate());
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
	}

	/**
	 * 删除道具coordinate_prop_id的标记
	 * 
	 * @param p_pk
	 * @param coordinate_prop_id
	 * @return
	 */
	public int delete(int p_pk, int coordinate_prop_id)
	{
		int result = -1;
		String sql = "delete from u_coordinate_info where p_pk=" + p_pk
				+ " and coordinate_prop_id=" + coordinate_prop_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
		return result;
	}

	/**
	 * 删除道具coordinate_prop_id的标记
	 * 
	 * @param p_pk
	 * @param coordinate_prop_id
	 * @return
	 */
	public int getCoordinate(int p_pk, int coordinate_prop_id)
	{
		int coordinate = -1;
		String sql = "SELECT coordinate from u_coordinate_info where p_pk="
				+ p_pk + " and coordinate_prop_id=" + coordinate_prop_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				coordinate = rs.getInt("coordinate");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
		return coordinate;
	}

	/**
	 * 更新道具coordinate_prop_id的标记
	 * 
	 * @param p_pk
	 * @param coordinate_prop_id
	 * @return
	 */
	public int updateCoordinate(int p_pk, int coordinate_prop_id, int coordinate)
	{
		int result = -1;
		String sql = "update u_coordinate_info set coordinate = " + coordinate
				+ " where p_pk=" + p_pk + " and coordinate_prop_id="
				+ coordinate_prop_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
		return result;
	}

	/**
	 * 当前标记道具是否使用
	 * 
	 * @param p_pk
	 * @param coordinate_prop_id
	 * @return
	 */
	public int isUse(int p_pk, int coordinate_prop_id)
	{
		int isUse = 0;
		String sql = "SELECT prop_isUse from u_coordinate_info where p_pk="
				+ p_pk + " and coordinate_prop_id=" + coordinate_prop_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				isUse = rs.getInt("prop_isUse");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
		return isUse;
	}

	/**
	 * 标记道具以使用
	 * @param p_pk
	 * @param coordinate_prop_id
	 * @return
	 */
	public int useProp(int p_pk, int coordinate_prop_id)
	{
		int result = -1;
		String sql = "update u_coordinate_info set prop_isUse = 1 where p_pk="
				+ p_pk + " and coordinate_prop_id=" + coordinate_prop_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}

		return result;
	}
	
	/**
	 * 标记道具以非使用状态
	 * @param p_pk
	 * @param coordinate_prop_id
	 * @return
	 */
	public int updateNoUsed(int p_pk, int coordinate_prop_id)
	{
		int result = -1;
		String sql = "update u_coordinate_info set prop_isUse = 0 where p_pk="
				+ p_pk + " and coordinate_prop_id=" + coordinate_prop_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}

		return result;
	}
}
