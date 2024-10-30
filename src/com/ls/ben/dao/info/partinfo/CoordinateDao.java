package com.ls.ben.dao.info.partinfo;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.partinfo.CoordinateVO;
import com.ls.pub.db.DBConnection;

/**
 * ����:u_coordinate_info��
 * @author ��˧ 4:25:53 PM
 */
public class CoordinateDao extends DaoBase
{
	/**
	 * ��ӱ����Ϣ
	 * 
	 * @param coordinate
	 */
	public void add(CoordinateVO coordinate)
	{
		if (coordinate == null)
		{
			logger.debug("coordinateΪ��");
		}
		String sql = "insert into u_coordinate_info(p_pk,coordinate_prop_id,coordinate) values ("
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
	 * ɾ������coordinate_prop_id�ı��
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
	 * ɾ������coordinate_prop_id�ı��
	 * 
	 * @param p_pk
	 * @param coordinate_prop_id
	 * @return
	 */
	public int getCoordinate(int p_pk, int coordinate_prop_id)
	{
		int coordinate = -1;
		String sql = "select coordinate from u_coordinate_info where p_pk="
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
	 * ���µ���coordinate_prop_id�ı��
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
	 * ��ǰ��ǵ����Ƿ�ʹ��
	 * 
	 * @param p_pk
	 * @param coordinate_prop_id
	 * @return
	 */
	public int isUse(int p_pk, int coordinate_prop_id)
	{
		int isUse = 0;
		String sql = "select prop_isUse from u_coordinate_info where p_pk="
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
	 * ��ǵ�����ʹ��
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
	 * ��ǵ����Է�ʹ��״̬
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
