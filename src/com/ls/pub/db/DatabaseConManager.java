/**
 * 
 */
package com.ls.pub.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;


/**
 * 功能:数据库连接管理
 * 
 * @author 刘帅
 * 
 * 3:57:33 PM
 */
public class DatabaseConManager {
	Logger logger =  Logger.getLogger(DatabaseConManager.class);
	Connection jygameConn = null;
	Connection jygameUserConn = null;
	String url = "";
	
	
	public Connection getJygameConnByUrl() {
		if (jygameConn == null) {
			try {
				
				url = "jdbc:mysql://192.168.1.110:3306/jygame?autoReconnect=true";
				//url = "jdbc:mysql://127.0.0.1:3306/jygame?autoReconnect=true";
				Class.forName("com.mysql.jdbc.Driver");
				jygameConn = DriverManager.getConnection(url, "jygame","jygame");
				//logger.debug("直接获得数据库连接-------");
				return jygameConn;
			} catch (Exception e) {
				////System.out.println("获得连接时出错-------:" + e.getMessage());
				e.printStackTrace();
			}
		}
		return jygameConn;
	}
	
	public Connection getJygameConn()
	{
		if (jygameConn == null) {
			try {
				DataSource ds = null; 
				InitialContext ctx=new InitialContext(); 
				ds=(DataSource)ctx.lookup("java:comp/env/jdbc/jygame"); 
				jygameConn = ds.getConnection(); 
				return jygameConn;
			} catch (Exception e) {
				////System.out.println("获得连接时出错1111111111:" + e.getMessage());
				//e.printStackTrace();
				jygameConn = getJygameConnByUrl();
			}
		}
		return jygameConn;
	}
	
	

	public Connection getJygameUserConn() {
		if (jygameUserConn == null) {
			try {
				
				DataSource ds = null; 
				InitialContext ctx=new InitialContext(); 
				ds=(DataSource)ctx.lookup("java:comp/env/jdbc/jygame_user"); 
				jygameUserConn = ds.getConnection(); 
				//logger.debug("从连接池获得数据库连接") ;
				return jygameUserConn;
			} catch (Exception e) {
				////System.out.println("获得连接时出错2222222222:" + e.getMessage());
				jygameUserConn = getJygameUserConnByUrl();
				//e.printStackTrace();
			}
		}
		return jygameUserConn;
	}

	public Connection getJygameUserConnByUrl() {
		if (jygameUserConn == null) {
			try {
				//url ="jdbc:mysql://127.0.0.1:3306/jygame_user?autoReconnect=true";
				url = "jdbc:mysql://192.168.1.110:3306/jygame_user?autoReconnect=true";
				Class.forName("com.mysql.jdbc.Driver");
				jygameUserConn = DriverManager.getConnection(url, "jygame","jygame");
				//logger.debug("直接获得数据库连接");
				return jygameUserConn;
			} catch (Exception e) {
				////System.out.println("获得连接时出错3333333333:" + e.getMessage());
				e.printStackTrace();
			}
		}
		return jygameUserConn;
	}
	
	public void closeJygameConn() {
		if (jygameConn != null) {
			try {
				jygameConn.close();
			} catch (SQLException ex) {
			}
		}
	}

	public void closeJygameUserConn() {
		if (jygameUserConn != null) {
			try {
				jygameUserConn.close();
			} catch (SQLException ex) {
			}
		}
	}
	public void closeConn()
	{
		//logger.debug("关闭数据库连接");
		closeJygameConn();
		closeJygameUserConn();
	}
}
