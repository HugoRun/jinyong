package com.ls.pub.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * ����:���ݿ����ӹ���
 * @author ��˧
 * 9:42:40 PM
 */
public class DBConnection
{
	Logger logger = Logger.getLogger(DBConnection.class);
	public static final int GAME_DB = 1;//��Ϸ���ݿ�
	public static final int GAME_USER_DB = 2;//������ݿ�
	public static final int GAME_LOG_DB = 3;//��־���ݿ�
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
	 * ������ݿ�����
	 * @return
	 */
	public Connection getConn()
	{
		return conn;
	}

	/**
	 * ���jygame���ݿ������
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
	 * ���jygame_user���ݿ������
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
	 * ���jygame_log���ݿ������
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
	 * �ر�����
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
	 * �����ύ��ʼ
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
			logger.info("����ʼʱ:���ݿ�����û�д�");
		}
	}

	/**
	 *  �����ύ
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
					//logger.info("�����ύʱ:���ݿ�����û�д�");
				} else
				{
					//logger.info("�����ύʱ:���ﻹû��ʼ�������ύ");
				}
			}
		} catch (SQLException e)
		{
			//logger.info(e.getMessage());
		}
	}

	/**
	 * ����ع�
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
					//logger.info("����ع�ʱ:���ݿ�����û�д�");
				} else
				{
					//logger.info("����ع�ʱ:���ﻹû�п�ʼ�����ܻع�");
				}
			}
		} catch (SQLException e)
		{
			//logger.info(e.getMessage());
		}
	}
}
