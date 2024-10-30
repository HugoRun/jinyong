package com.pub.db.jygamedb;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

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
public class Jygamedb {
	public Connection con = null;
	public Statement stmt = null;
	ResultSet rs = null;
	Logger logger = Logger.getLogger("log.dao");
	/*
	 * 获得一个连接
	 */
	public Jygamedb() throws ClassNotFoundException,SQLException{ 
		try{ 
		    Context ctx=new InitialContext();//声明连接池
			DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/jygame");//指定所使用的连接池
	    	con = ds.getConnection();//创建数据库连接
	    	////System.out.println("创建连接");
		}catch(Exception e)//捕获异常
		{
			e.printStackTrace();
		} 
	}
	public Connection getConn(){
        return (con);//返回Connection对象
}
	/**
	 * 功能:查找信息
	 * 
	 * @param user_id
	 *            String
	 * @throws SQLException
	 * @return ResultSet
	 */
	public ResultSet query(String sql) {
		rs=null;
		try{
			logger.debug("查询:"+sql);
			stmt=con.createStatement();
			rs=stmt.executeQuery(sql);//执行SQL语句
		}
		catch(SQLException ex)//捕获异常
		{
			ex.printStackTrace();
		}
		return rs;//返回结果集
	}

	/**
	 * 功能:执行更新，如插入等操作 update 更新 insert 插入 delete 删除
	 * 
	 * @param sql
	 *            String
	 * @throws SQLException
	 * @return int
	 */
	public int update(String sql) {
		int temp = 0;
		try {
			logger.debug("更新:"+sql);  
			stmt = con.createStatement();
			temp = stmt.executeUpdate(sql);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return temp;
	}

	public ResultSet resultSetCallable() {
		CallableStatement stmt1 = null;
		ResultSet rs = null;
		 
		try {
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
		} catch (SQLException ex) {
			ex.getMessage();
			////System.out.println(ex.getMessage());
		} finally {
			// close();
		}
		return rs;
	}

	/**
	 * 功能:关闭连接
	 */
	public void close() {
		try{
			if(rs!=null)rs.close();
			if(stmt!=null)stmt.close();
			////System.out.println("关闭连接");
		}
		catch(Exception e)//捕获异常
		{
			e.printStackTrace();
		}
		finally {
			if (con != null)
			{
				try
				{
					con.close();
				} catch (Exception e)
				{
					logger.info(e.getMessage());
				}
			}
		}
	} 
}
