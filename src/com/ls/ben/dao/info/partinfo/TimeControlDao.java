package com.ls.ben.dao.info.partinfo;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.partinfo.TimeControlVO;
import com.ls.pub.db.DBConnection;

/**
 * 功能:u_time_control表
 * @author 刘帅
 * Sep 25, 2008  11:50:26 AM
 */
public class TimeControlDao extends DaoBase
{
	/**
	 * 添加日志
	 * @param p_pk
	 * @param prop_id
	 */
	public void add( int p_pk,int object_id,int object_type )
	{
		String sql = "INSERT INTO u_time_control (p_pk,object_id,object_type,use_datetime,use_times) values (?,?,?,NOW(),1)";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, p_pk);
			ps.setInt(i++, object_id);
			ps.setInt(i++, object_type);
			ps.execute();
			ps.close();
		} catch (SQLException e)
		{
			e.printStackTrace();

		} finally
		{
			dbConn.closeConn();
		}
	}
	
	/**
	 * 添加日志
	 * @param p_pk
	 * @param prop_id
	 */
	public void add( int p_pk,int object_id,int object_type,int minutes )
	{
		String sql = "INSERT INTO u_time_control (p_pk,object_id,object_type,use_datetime,use_times) values (?,?,?,NOW()+INTERVAL "+minutes+" minute,1)";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, p_pk);
			ps.setInt(i++, object_id);
			ps.setInt(i++, object_type);
			ps.execute();
			ps.close();
		} catch (SQLException e)
		{
			e.printStackTrace();

		} finally
		{
			dbConn.closeConn();
		}
	}
	
	/**
	 * 判断是否有道具使用日志
	 * @param p_pk
	 * @param object_id
	 */
	public boolean isHaveControlInfo(int p_pk,int object_id,int object_type)
	{
		boolean isHave = false;
		String sql = "SELECT id from u_time_control where p_pk="+p_pk +" and object_id="+object_id + " and object_type = " + object_type + "  and object_type = " + object_type + " limit 1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if( rs.next() )
			{
				isHave = true;
			}
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();

		} finally
		{
			dbConn.closeConn();
		}
		return isHave;
	}
	
	/**
	 * 得到玩家道具使用日志
	 */
	public TimeControlVO getControlInfo( int p_pk,int object_id,int object_type )
	{
		TimeControlVO propUseLog = null;
		String sql = "SELECT * FROM u_time_control where p_pk="+p_pk +" and object_id="+object_id + " and object_type = " + object_type + "  limit 1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if( rs.next() )
			{
				propUseLog = new TimeControlVO();
				propUseLog.setId(rs.getInt("id"));
				propUseLog.setPPk(p_pk);
				propUseLog.setObjectId(object_id);
				propUseLog.setObjectType(object_type);
				propUseLog.setUseDatetime(rs.getTimestamp("use_datetime"));
				propUseLog.setUseTimes(rs.getInt("use_times"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();

		} finally
		{
			dbConn.closeConn();
		}
		return propUseLog;
	}
	
	/**
	 * 当天第一次使用,更新状态
	 * @param p_pk
	 * @param object_id
	 */
	public void updateFirstTimeState( int p_pk,int object_id,int object_type )
	{
		String sql = "update u_time_control set use_datetime=now(),use_times=1 where p_pk="+p_pk +" and object_id="+object_id + " and object_type = " + object_type + " ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();

		} finally
		{
			dbConn.closeConn();
		}
	}
	
	/**
	 * 当天第一次使用,更新状态,有时间延迟
	 * @param p_pk
	 * @param object_id
	 */
	public void updateFirstTimeStateByTime( int p_pk,int object_id,int object_type,int minutes )
	{
		String sql = "update u_time_control set use_datetime=NOW()+INTERVAL "+minutes+" minute,use_times=1 where p_pk="+p_pk +" and object_id="+object_id + " and object_type = " + object_type + " ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();

		} finally
		{
			dbConn.closeConn();
		}
	}
	
	/**
	 * 当天使用,更新状态,有时间延迟
	 * @param p_pk
	 * @param object_id
	 */
	public void updateTimeStateByTime( int p_pk,int object_id,int object_type,int minutes )
	{
		String sql = "update u_time_control set use_datetime=use_datetime+INTERVAL "+minutes+" minute,use_times=1 where p_pk="+p_pk +" and object_id="+object_id + " and object_type = " + object_type + " ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();

		} finally
		{
			dbConn.closeConn();
		}
	}
	
	
	/**
	 * 更新使用状态（使用时间和使用次数）
	 * @param p_pk
	 * @param object_id
	 */
	public void updateUseState( int p_pk,int object_id,int object_type )
	{
		String sql = "update u_time_control set use_datetime=now(),use_times=use_times+1 where p_pk="+p_pk +" and object_id="+object_id + " and object_type = " + object_type + " ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();

		} finally
		{
			dbConn.closeConn();
		}
	}
}

