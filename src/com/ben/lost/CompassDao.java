package com.ben.lost;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.pub.constant.MenuType;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;

public class CompassDao extends DaoBase
{
	public Compass findById(int scene_id)
	{
		String sql = "select * from compass c where c.scene_id = " + scene_id;
		List<Compass> list = new ArrayList<Compass>();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			list = get(rs);
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
			return list == null || list.size() == 0 ? null : list.get(0);
		}
	}

	public List<Compass> findAll()
	{
		String sql = "select * from compass";
		List<Compass> list = new ArrayList<Compass>();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			list = get(rs);
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
			return list;
		}
	}

	private List<Compass> get(ResultSet rs) throws SQLException
	{
		List<Compass> list = new ArrayList<Compass>();
		if (rs != null)
		{
			while (rs.next())
			{
				Compass compass = new Compass();
				compass.setDes(rs.getString("des"));
				compass.setId(rs.getInt("id"));
				compass.setScene_id(rs.getInt("scene_id"));
				list.add(compass);
			}
		}
		return list;
	}




}
