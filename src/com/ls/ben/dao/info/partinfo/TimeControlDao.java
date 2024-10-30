package com.ls.ben.dao.info.partinfo;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.partinfo.TimeControlVO;
import com.ls.pub.db.DBConnection;

/**
 * ����:u_time_control��
 * @author ��˧
 * Sep 25, 2008  11:50:26 AM
 */
public class TimeControlDao extends DaoBase
{
	/**
	 * �����־
	 * @param p_pk
	 * @param prop_id
	 */
	public void add( int p_pk,int object_id,int object_type )
	{
		String sql = "insert into u_time_control (p_pk,object_id,object_type,use_datetime,use_times) values (?,?,?,NOW(),1)";
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
	 * �����־
	 * @param p_pk
	 * @param prop_id
	 */
	public void add( int p_pk,int object_id,int object_type,int minutes )
	{
		String sql = "insert into u_time_control (p_pk,object_id,object_type,use_datetime,use_times) values (?,?,?,NOW()+INTERVAL "+minutes+" minute,1)";
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
	 * �ж��Ƿ��е���ʹ����־
	 * @param p_pk
	 * @param object_id
	 */
	public boolean isHaveControlInfo(int p_pk,int object_id,int object_type)
	{
		boolean isHave = false;
		String sql = "select id from u_time_control where p_pk="+p_pk +" and object_id="+object_id + " and object_type = " + object_type + "  and object_type = " + object_type + " limit 1";
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
	 * �õ���ҵ���ʹ����־
	 */
	public TimeControlVO getControlInfo( int p_pk,int object_id,int object_type )
	{
		TimeControlVO propUseLog = null;
		String sql = "select * from u_time_control where p_pk="+p_pk +" and object_id="+object_id + " and object_type = " + object_type + "  limit 1";
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
	 * �����һ��ʹ��,����״̬
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
	 * �����һ��ʹ��,����״̬,��ʱ���ӳ�
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
	 * ����ʹ��,����״̬,��ʱ���ӳ�
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
	 * ����ʹ��״̬��ʹ��ʱ���ʹ�ô�����
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

