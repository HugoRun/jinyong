package com.pub.db.mysql;

import java.sql.*;

import javax.naming.*;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;

/**
 * 功能:数据库操作类 可以完成对数据库的删除，插入，查找等
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author yzjcomcn
 * @version 1.0
 */
public class SqlData extends Action
{
	public Connection con = null;
	public Statement stmt = null;
	ResultSet rs = null;
	Logger logger = Logger.getLogger("log.dao");

	/*
	 * 获得一个连接
	 */
	public SqlData() throws ClassNotFoundException, SQLException
	{
		/*
		 * try { Class.forName("com.mysql.jdbc.Driver"); con=
		 * DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/jygame_user?autoReconnect=true","root","root");
		 * return con; } catch (Exception e) {
		 * //System.out.println("获得连接时出错:"+e.getMessage()); e.printStackTrace(); }
		 * return null;
		 */
		try
		{
			// org.apache.log4j.PropertyConfigurator.configure("log4j.properties");//指定日志输入文件
			// log =
			// org.apache.log4j.Logger.getLogger(DBConnection.class);//指定日志所记录类

			Context ctx = new InitialContext();// 声明连接池
			DataSource ds = (DataSource) ctx
					.lookup("java:comp/env/jdbc/jygame_user");// 指定所使用的连接池

			con = ds.getConnection();// 创建数据库连接
			// //System.out.println("创建连接");
		}
		catch (Exception e)// 捕获异常
		{
			e.printStackTrace();
		}

		/*
		 * try { DataSource ds = null; InitialContext ctx = new
		 * InitialContext(); ds = (DataSource)
		 * ctx.lookup("java:comp/env/jdbc/jygame_user"); con =
		 * ds.getConnection(); return con; } catch (Exception e) {
		 * //System.out.println("获得连接时出错1111111111:" + e.getMessage()); return
		 * null; // e.printStackTrace(); }
		 */
	}

	public Connection getConn()
	{
		return (con);// 返回Connection对象
	}

	/**
	 * 功能:查找信息
	 * 
	 * @param user_id
	 *            String
	 * @throws SQLException
	 * @return ResultSet
	 */
	public ResultSet query(String sql)
	{
		rs = null;
		try
		{
			logger.debug("查询:" + sql);
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);// 执行SQL语句
		}
		catch (SQLException ex)// 捕获异常
		{
			ex.printStackTrace();
		}
		return rs;// 返回结果集
	}

	/**
	 * 功能:执行更新，如插入等操作 update 更新 insert 插入 delete 删除
	 * 
	 * @param sql
	 *            String
	 * @throws SQLException
	 * @return int
	 */
	public int update(String sql)
	{
		int temp = 0;
		try
		{
			logger.debug("更新:" + sql);
			stmt = con.createStatement();
			temp = stmt.executeUpdate(sql);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		return temp;
	}

	public ResultSet resultSetCallable()
	{
		CallableStatement stmt1 = null;
		ResultSet rs = null;

		try
		{
			stmt1 = con.prepareCall("{ ? = call NewsOrder(?,?,?)} ");
			int aa = 0;
			stmt1.setInt(2, 1);
			stmt1.setInt(3, 10);
			stmt1.registerOutParameter(1, Types.OTHER);
			stmt1.registerOutParameter(4, Types.INTEGER);
			stmt1.execute();
			int temp = stmt1.getInt(3);

			rs = (ResultSet) stmt1.getObject(1);
			// //System.out.println("返回值为:" + temp);
		}
		catch (SQLException ex)
		{
			ex.getMessage();
		}
		finally
		{
			close();
		}
		return rs;
	}

	/**
	 * 功能:关闭连接
	 */
	public synchronized void close()
	{
		try
		{
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			// //System.out.println("关闭连接");
		}
		catch (Exception e)// 捕获异常
		{
			e.printStackTrace();
		}
		finally
		{
			if (con != null)
			{
				try
				{
					con.close();
				}
				catch (Exception e)
				{
					logger.info(e.getMessage());
				}
			}
		}

	}

	/**
	 * 事务提交开始
	 */
	public void begin()
	{
		if (con != null)
		{
			try
			{
				con.setAutoCommit(false);
			}
			catch (SQLException e)
			{
				// logger.info(e.getMessage());
			}
		}
		else
		{
			// logger.info("事务开始时:数据库链接没有打开");
		}
	}

	/**
	 * 事务提交
	 */
	public synchronized void commit()
	{
		try
		{
			if (con != null && !con.getAutoCommit())
			{
				con.commit();
				con.setAutoCommit(true);
			}
			else
			{
				if (con == null)
				{
					// logger.info("事务提交时:数据库链接没有打开");
				}
				else
				{
					// logger.info("事务提交时:事物还没开始，不能提交");
				}
			}
		}
		catch (SQLException e)
		{
			// logger.info(e.getMessage());
		}
	}

	/**
	 * 事务回滚
	 */
	public void rollback()
	{
		try
		{
			if (con != null && !con.getAutoCommit())
			{
				con.rollback();
				con.setAutoCommit(true);
			}
			else
			{
				if (con == null)
				{
					// logger.info("事务回滚时:数据库链接没有打开");
				}
				else
				{
					// logger.info("事务回滚时:事物还没有开始，不能回滚");
				}
			}
		}
		catch (SQLException e)
		{
			// logger.info(e.getMessage());
		}

	}

	/**
	 * 功能:查找信息
	 * 
	 * @param user_id
	 *            String
	 * @throws SQLException
	 * @return ResultSet
	 * @throws SQLException 
	 */
	public ResultSet query(String sql, Object[] args) throws SQLException
	{
		rs = null;
		try
		{
			logger.debug("查询:" + sql);
			stmt = con.createStatement();
			PreparedStatement ps = con.prepareStatement(sql);
			if (args != null)
			{
				for (int i = 0; i < args.length; i++)
				{
					ps.setObject(i + 1, args[i]);
				}
			}
			rs = ps.executeQuery();
		}
		catch (SQLException ex)// 捕获异常
		{
			ex.printStackTrace();
		}
		return rs;// 返回结果集
	}

	/**
	 * 功能:执行更新，如插入等操作 update 更新 insert 插入 delete 删除
	 * 
	 * @param sql
	 *            String
	 * @throws SQLException
	 * @return int
	 * @throws SQLException 
	 */
	public int update(String sql, Object[] args) throws SQLException
	{
		int temp = 0;
		try
		{
			logger.debug("更新:" + sql);
			PreparedStatement ps = con.prepareStatement(sql);
			if(args!=null){
				for(int i = 0;i<args.length;i++){
					ps.setObject(i+1, args[i]);
				}
			}
			temp = ps.executeUpdate();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}finally{
			con.close();
		}
		return temp;
	}

}
/*
 * public static void main(String[] args) { // TODO Auto-generated method stub
 * SqlData con=new SqlData(); String sql="insert into affiche
 * values(2,'123','123321','123123123');"; con.update(sql); }
 */

