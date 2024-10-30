package com.ls.pub.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * 功能:数据库链接管理
 * @author 刘帅
 * 9:42:40 PM
 */
public class DBConnection
{
	Logger logger = Logger.getLogger(DBConnection.class);
	public static final int GAME_DB = 1;//游戏数据库
	public static final int GAME_USER_DB = 2;//玩家数据库
	public static final int GAME_LOG_DB = 3;//日志数据库
	private Connection conn = null;

	public DBConnection(int db_type)
	{
		switch (db_type)
		{
			case GAME_DB:
			{
				conn = createJygameConn();
				break;
			}
			case GAME_USER_DB:
			{
				conn = createJygameUserConn();
				break;
			}
			case GAME_LOG_DB:
			{
				conn = createJygameLogConn();
				break;
			}
		}
	}
	
	/**
	 * 获得数据库链接
	 * @return
	 */
	public Connection getConn()
	{
		return conn;
	}

	/**
	 * 获得jygame数据库的链接
	 * @return
	 */
	private Connection createJygameConn()
	{
		Connection jygameConn = null;
		try
		{
			DataSource ds = null;
			InitialContext ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/jygame");
			jygameConn = ds.getConnection();
			return jygameConn;
		} catch (Exception e)
		{
			logger.info(e.getMessage());
		}
		return jygameConn;
	}

	/**
	 * 获得jygame_user数据库的链接
	 * @return
	 */
	private Connection createJygameUserConn()
	{
		Connection jygameUserConn = null;
		try
		{
			DataSource ds = null;
			InitialContext ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/jygame_user");
			jygameUserConn = ds.getConnection();
			return jygameUserConn;
		} catch (Exception e)
		{
			logger.info(e.getMessage());
		}
		return jygameUserConn;
	}
	/**
	 * 获得jygame_log数据库的链接
	 * @return
	 */
	private Connection createJygameLogConn()
	{
		Connection jygameUserConn = null;
		try
		{
			DataSource ds = null;
			InitialContext ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/jygame_log");
			jygameUserConn = ds.getConnection();
			return jygameUserConn;
		} catch (Exception e)
		{
			logger.info(e.getMessage());
		}
		return jygameUserConn;
	}

	/**
	 * 关闭链接
	 */
	public void closeConn()
	{
		if (conn != null)
		{
			try
			{
				conn.close();
			} catch (Exception e)
			{
				logger.info(e.getMessage());
			}
		}
	}

	/**
	 * 事务提交开始
	 */
	public void begin()
	{
		if (conn != null)
		{
			try
			{
				conn.setAutoCommit(false);
			} catch (SQLException e)
			{
				//logger.info(e.getMessage());
			}
		} else
		{
			logger.info("事务开始时:数据库链接没有打开");
		}
	}

	/**
	 *  事务提交
	 */
	public void commit()
	{
		try
		{
			if (conn != null && !conn.getAutoCommit())
			{
				conn.commit();
				conn.setAutoCommit(true);
			} else
			{
				if (conn == null)
				{
					//logger.info("事务提交时:数据库链接没有打开");
				} else
				{
					//logger.info("事务提交时:事物还没开始，不能提交");
				}
			}
		} catch (SQLException e)
		{
			//logger.info(e.getMessage());
		}
	}

	/**
	 * 事务回滚
	 */
	public void rollback()
	{
		try
		{
			if (conn != null && !conn.getAutoCommit())
			{
				conn.rollback();
				conn.setAutoCommit(true);
			} else
			{
				if (conn == null)
				{
					//logger.info("事务回滚时:数据库链接没有打开");
				} else
				{
					//logger.info("事务回滚时:事物还没有开始，不能回滚");
				}
			}
		} catch (SQLException e)
		{
			//logger.info(e.getMessage());
		}
	}
}
