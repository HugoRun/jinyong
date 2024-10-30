package com.lw.dao.gamesystemstatistics;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.lw.vo.gamesystemstatistics.GameSystemStatisticsMessageVO;

public class GameSystemStatisticsMessageDao extends DaoBase
{

	/** 判断是否该物品被录入后台统计 */
	public int getGameSystemStatisticsMessage(int propID, int propType)
	{
		int id = 0;
		String sql = "SELECT gsp_id FROM game_statistics_prop WHERE prop_id = "
				+ propID + " AND prop_type = " + propType;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				id = rs.getInt("gsp_id");
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return id;
	}

	/** 得到统计物品 */
	public List<GameSystemStatisticsMessageVO> getProp()
	{
		List<GameSystemStatisticsMessageVO> list = new ArrayList<GameSystemStatisticsMessageVO>();
		GameSystemStatisticsMessageVO vo = null;
		String sql = "SELECT * FROM game_statistics_prop";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new GameSystemStatisticsMessageVO();
				vo.setPropID(rs.getInt("prop_id"));
				vo.setPropType(rs.getInt("prop_type"));
				vo.setDate(rs.getString("date"));
				list.add(vo);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
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
