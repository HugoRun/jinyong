package com.lw.dao.gamesystemstatistics;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.lw.vo.gamesystemstatistics.GameSystemStatisticsMessageVO;

public class GameSystemStatisticsMessageDao extends DaoBase
{

	/** �ж��Ƿ����Ʒ��¼���̨ͳ�� */
	public int getGameSystemStatisticsMessage(int propID, int propType)
	{
		int id = 0;
		String sql = "select gsp_id from game_statistics_prop where prop_id = "
				+ propID + " and prop_type = " + propType;
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

	/** �õ�ͳ����Ʒ */
	public List<GameSystemStatisticsMessageVO> getProp()
	{
		List<GameSystemStatisticsMessageVO> list = new ArrayList<GameSystemStatisticsMessageVO>();
		GameSystemStatisticsMessageVO vo = null;
		String sql = "select * from game_statistics_prop";
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
