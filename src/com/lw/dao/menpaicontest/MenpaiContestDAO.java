package com.lw.dao.menpaicontest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.lw.vo.menpaicontest.MenpaiContestPlayerVO;
import com.lw.vo.menpaicontest.MenpaiContestVO;
import com.lw.vo.menpaicontest.MenpaiNpcVO;

public class MenpaiContestDAO extends DaoBase
{

	// 得到后台数据
	public MenpaiContestVO selectMenpaiContestData()
	{
		MenpaiContestVO vo = null;
		String sql = "SELECT * FROM menpaicontest ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{   
				vo = new MenpaiContestVO();
				vo.setOver_hour(rs.getInt("over_hour"));
				vo.setOver_minute(rs.getInt("over_minute"));
				vo.setReady_hour(rs.getInt("ready_hour"));
				vo.setReady_minute(rs.getInt("ready_minute"));
				vo.setRun_hour(rs.getInt("run_hour"));
				vo.setRun_minute(rs.getInt("run_minute"));
				vo.setTime_week(rs.getString("time_week"));
				vo.setAll_hour(rs.getInt("all_hour"));
				vo.setAll_minute(rs.getInt("all_minute"));
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

	// 玩家最终奖励
	public List<MenpaiContestPlayerVO> selectMenpaiOverDataList(int p_type)
	{
		List<MenpaiContestPlayerVO> list = new ArrayList<MenpaiContestPlayerVO>();
		MenpaiContestPlayerVO vo = null;
		String sql = "SELECT * FROM p_menpaicontest WHERE into_state = 1 AND p_type = "
				+ p_type;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new MenpaiContestPlayerVO();
				vo.setP_name(rs.getString("p_name"));
				vo.setP_pk(rs.getInt("p_pk"));
				vo.setKill_num(rs.getInt("kill_num"));
				vo.setWin_num(rs.getInt("win_num"));
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

	// 得到玩家排名数据
	public List<MenpaiContestPlayerVO> selectPlayerRankDataList(int pageNo,
			String p_type)
	{
		List<MenpaiContestPlayerVO> list = new ArrayList<MenpaiContestPlayerVO>();
		MenpaiContestPlayerVO vo = null;

		int pageNum = 0;

		String page_sql = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			if (pageNo != 0)
			{
				pageNum = (pageNo - 1) * 10;
			}
			else
			{
				pageNum = 0;
			}

			page_sql = "SELECT * FROM p_menpaicontest WHERE win_num != 0 AND p_type = "
					+ p_type
					+ " ORDER BY win_num desc,win_num_time LIMIT "
					+ pageNum + " , 10";

			rs = stmt.executeQuery(page_sql);
			while (rs.next())
			{
				vo = new MenpaiContestPlayerVO();
				vo.setP_name(rs.getString("p_name"));
				vo.setP_pk(rs.getInt("p_pk"));
				vo.setWin_num(rs.getInt("win_num"));
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

	// 得到玩家数据
	public MenpaiContestPlayerVO selectPlayerData(int p_pk)
	{
		MenpaiContestPlayerVO vo = null;
		String sql = "SELECT * FROM p_menpaicontest WHERE p_pk = " + p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new MenpaiContestPlayerVO();
				vo.setId(rs.getInt("id"));
				vo.setP_pk(rs.getInt("p_pk"));
				vo.setP_type(rs.getInt("p_type"));
				vo.setWin_state(rs.getInt("win_state"));
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

	// 玩家插入数据
	public void insertPlayerData(int p_pk, String p_name, int p_type)
	{
		String sql = "INSERT INTO p_menpaicontest (id,p_pk,p_name,p_type,into_state,into_time) VALUES (null,"
				+ p_pk + ",'" + p_name + "'," + p_type + ",1,now())";
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

	// 玩家更新进入时间
	public void updatePlayerInData(int p_pk)
	{
		String sql = "UPDATE p_menpaicontest SET into_state = 1,into_time = now(),kill_num = 0 WHERE p_pk = "
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

	// 玩家更新出去时间
	public void updatePlayerOutData(int p_pk)
	{
		String sql = "UPDATE p_menpaicontest SET into_state = 0,out_time = now(),old_kill_num = kill_num WHERE p_pk = "
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

	// 玩家杀人数量的更新
	public void updatePlayerKillData(int p_pk)
	{
		String sql = "UPDATE p_menpaicontest SET kill_num = kill_num + 1,kill_time = now() WHERE p_pk = "
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

	// 玩家杀人数量的更新
	public void updatePlayerBeiKillData(int p_pk, int kill_p_pk)
	{
		String sql = "UPDATE p_menpaicontest SET kill_p_pk = " + kill_p_pk
				+ " , kill_p_pk_time = now() WHERE p_pk = " + p_pk;
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

	// 更新玩家排名玩家排名
	public void updatePlayerRankData(int p_pk)
	{
		String sql = "UPDATE p_menpaicontest SET win_num = win_num + 1,win_num_time = now() WHERE p_pk = "
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

	// 更新玩家领取和胜利的状态
	public void updatePlayerRankState(int p_pk, int win_state)
	{
		String sql = "UPDATE p_menpaicontest SET win_state = " + win_state
				+ " WHERE p_pk = " + p_pk;
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

	// 得到玩家排名数据
	public int selectPlayerRankDataNum(String p_type)
	{
		int pageNum = 0;

		String page_sql = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			page_sql = "SELECT COUNT(*) AS num FROM p_menpaicontest WHERE win_num != 0 AND p_type = "
					+ p_type;

			rs = stmt.executeQuery(page_sql);
			if (rs.next())
			{
				pageNum = rs.getInt("num");
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
		return pageNum;
	}

	// 得到玩家数据
	public List<MenpaiContestPlayerVO> selectPlayerIn()
	{
		List<MenpaiContestPlayerVO> list = new ArrayList<MenpaiContestPlayerVO>();
		MenpaiContestPlayerVO vo = null;
		String sql = "SELECT * FROM p_menpaicontest WHERE into_state = 1 ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new MenpaiContestPlayerVO();
				vo.setP_pk(rs.getInt("p_pk"));
				vo.setKill_num(rs.getInt("kill_num"));
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

	/** *******************门派NPC*********************** */
	public MenpaiNpcVO getPlayerMenpaiNpc(int p_type, int p_lv)
	{
		MenpaiNpcVO vo = null;
		String sql = "SELECT * FROM menpainpc WHERE p_type = " + p_type
				+ " AND npc_lv = " + p_lv;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo = new MenpaiNpcVO();
				vo.setId(rs.getInt("id"));
				vo.setP_type(rs.getInt("p_type"));
				vo.setNpc_id(rs.getInt("npc_id"));
				vo.setNpc_lv(rs.getInt("npc_lv"));
				vo.setScence_id(rs.getInt("scence_id"));
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
