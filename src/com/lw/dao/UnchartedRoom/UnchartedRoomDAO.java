package com.lw.dao.UnchartedRoom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.lw.vo.UnchartedRoom.UnchartedRoomVO;

public class UnchartedRoomDAO extends DaoBase
{
	/** 得到玩家信息 */
	public List<UnchartedRoomVO> getUnchartedRoomPlayer()
	{
		List<UnchartedRoomVO> list = new ArrayList<UnchartedRoomVO>();
		UnchartedRoomVO vo = null;
		String sql = "";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new UnchartedRoomVO();
				vo.setP_pk(rs.getInt("p_pk"));
				vo.setState(rs.getInt("into_state"));
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

	// 第一次进入秘境
	public void insertInUnchartedRoomState(int p_pk)
	{
		String sql = "INSERT INTO p_unchartedroom (id,p_pk,into_state,into_num,into_time,out_time) values (null,"
				+ p_pk + ",1,1,now(),now())";
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

	// 更新玩家跳出秘境的状态
	public void updateInUnchartedRoomState(int p_pk)
	{
		String sql = "update p_unchartedroom set into_time = now(),into_state = 1,into_num = into_num + 1 where p_pk = "
				+ p_pk;
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

	// 更新玩家进入秘境的状态
	public void updateOutUnchartedRoomState(int p_pk)
	{
		String sql = "update p_unchartedroom set out_time = now(),into_state = 0 where p_pk = "
				+ p_pk;
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

	// 得到玩家在秘境里的人数
	public int getUnchartedRoomPlayerNum()
	{
		int num = 0;
		String sql = "SELECT count(*) as num from p_unchartedroom where into_state = 1";
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

	// 得到玩家在秘境的信息
	public UnchartedRoomVO getUnchartedRoomPlayerVO(int p_pk)
	{
		UnchartedRoomVO vo = null;
		String sql = "SELECT * FROM p_unchartedroom where p_pk = " + p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo = new UnchartedRoomVO();
				vo.setP_pk(rs.getInt("p_pk"));
				vo.setState(rs.getInt("into_state"));
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
}
