package com.ls.ben.dao.log;

import java.io.BufferedReader;
import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.ls.web.service.log.DataErrorLog;

/**
 * @author ls
 * 记录日志
 */
public class GameLogDao extends DaoBase
{
	private static GameLogDao logDao = new GameLogDao();
	
	private GameLogDao(){};
	
	public static GameLogDao getInstance()
	{
		return logDao;
	}
	
	/**
	 * 根据sql插入日志
	 * @param log_sql	sql语句
	 */
	public void incertBySql(String log_sql)
	{
		if( log_sql!=null )
		{
			DBConnection dbConn = new DBConnection(DBConnection.GAME_LOG_DB);
			conn = dbConn.getConn();
			try
			{
				stmt = conn.createStatement();
				stmt.execute(log_sql);
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

		}
	}
	/**
	 * 通过文件记录日志
	 * @param buffer		文件缓存
	 */
	public void incertByBufferedReader(BufferedReader buffer)
	{
		if( buffer!=null )
		{
			DBConnection dbConn = new DBConnection(DBConnection.GAME_LOG_DB);
			conn = dbConn.getConn();
			try
			{
				stmt = conn.createStatement();
				String sql = "";
				while( (sql=buffer.readLine())!=null )
				{
					try
					{
						stmt.execute(sql);
					}
					catch (SQLException e)
					{
						DataErrorLog.debugLogic("gamelog语句问题，sql="+sql);
					}
				}
				
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
		}
	}


}
