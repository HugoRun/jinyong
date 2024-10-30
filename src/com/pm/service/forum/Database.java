/*
 * Created on 2008-6-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.pm.service.forum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


import org.apache.log4j.Logger;
import com.ls.pub.db.DBConnection;


/**
 * @author 王喜成
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Database {
	/**
	 * 数据库访问URL
	 */
	private static String url;

	/**
	 * 数据库驱动
	 */
	private static String driver;

	/**
	 * 数据库访问用户名
	 */
	private static String username;

	/**
	 * 数据库访问口令
	 */
	private static String password;

	/**
	 * 访问类型
	 */
	private static String type;

	/**
	 * 数据源名称
	 */
	private static String datasource;

	/**
	 * 配置文件名称
	 */
	private final static String fileName = "database";
	/**
	 * 初始化日志
	 */
	private static Logger logger = Logger.getLogger(Database.class);

	private static ThreadLocal<Connection> connection = new ThreadLocal<Connection>();

	static {
		//config();
	}

	private static void config() {
		//读取系统配置
		PropertyResourceBundle resourceBundle = (PropertyResourceBundle) ResourceBundle
				.getBundle(fileName);
		//将系统设置赋值给类变量
		Enumeration enu = resourceBundle.getKeys();
		while (enu.hasMoreElements()) {
			String propertyName = enu.nextElement().toString();
			if (propertyName.equals("database.url"))
				url = resourceBundle.getString("database.url");
			if (propertyName.equals("database.driver"))
				driver = resourceBundle.getString("database.driver");
			if (propertyName.equals("database.username"))
				username = resourceBundle.getString("database.username");
			if (propertyName.equals("database.password"))
				password = resourceBundle.getString("database.password");
			if (propertyName.equals("database.type"))
				type = resourceBundle.getString("database.type");
			if (propertyName.equals("database.datasource"))
				datasource = resourceBundle.getString("database.datasource");
		}

	}
	/**public  synchronized static Connection getConnection()throws SQLException
	{
	//如果本地已有连接直接还回连接
		Connection con = (Connection) connection.get();
		if (con != null && !con.isClosed()) {
												//logger.info("直接还回连接");
			return con;
		}
		else{
			      //从JNDI中取得数据源
			try {
				////System.out.println("连接池连接1！");
				Context initCtx = new InitialContext();
				Context envCtx = (Context)initCtx.lookup( "java:comp/env");
				DataSource ds = (DataSource)envCtx.lookup( "jdbc/park");
				con= ds.getConnection();
				connection.set(con);
				//logger.info("连接池连接！");
				con.setAutoCommit(false);
				return con;
				
			}catch (Exception e)
			{
				try {//根据配置文件连接
					//logger.info("错误原因:"+e.getMessage());
					logger.info("根据配置文件连接！"+url);
					Class.forName(driver);
					con = DriverManager.getConnection(url, username,password);
					con.setAutoCommit(false);
					//connection.set(con);
					return con;

				} catch (ClassNotFoundException ee) {
					ee.printStackTrace();
					//logger.info("错误原因:"+e.getMessage());
					// 定义驱动程序
					String DBDRIVER = "net.sourceforge.jtds.jdbc.Driver";
					// // 定义连接字符串
					String CONNSTR = "jdbc:jtds:sqlserver://61.168.44.8:1433/jingcai";
					try
					{
						logger.info("普通连接！");
						Class.forName( DBDRIVER );
						con=DriverManager.getConnection( CONNSTR,"sa","(!wanxiang*)");
						//return DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/3gpark","sa","");
						con.setAutoCommit(false);
						return con;
						
					}
					catch ( ClassNotFoundException ex )
					{
						//logger.info("获得连接出错！"+ex.getMessage());
						ex.printStackTrace( System.err );
						return null;
					}
				}
				
			}
		} 		
	} */
	/*
	 * 获得一个连接
	 */  
	public static Connection getConnections() 
	{
		 try {
			// //System.out.println("获得连接");
		        Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();	 
		        //return DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/jingcai","sa","");
		       Connection con =DriverManager.getConnection("jdbc:jtds:sqlserver://61.168.44.8:1433/jingcai","sa","(!wanxiang*)");
		       //Connection con =DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/jingcai","sa","");
		       con.setAutoCommit(false);
		       return  con;
		    }
		    catch (Exception e) {
		      e.printStackTrace();
		    }
		    return null;
	} 	
	public static void commit() {
		Connection con = connection.get();
		try {
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void rollback() {
		Connection con = connection.get();
		try {
			con.rollback();
			//logger.info("回滚数据！");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public synchronized static void releaseConnection(Connection connection) {

		try {
			if (connection != null && !connection.isClosed())
				connection.close();
			logger.info("关闭数据库连接！");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection = null;
	}
public static void main(String[] args) {
	
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
	try {
		 con= dbConn.getConn();
		 ps = con.prepareStatement("select * from g_user");	
		 rs = ps.executeQuery();
		while(rs.next())
		{
			////System.out.println(rs.getObject(1));
		}
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		//logger.info("错误原因:"+e.getMessage());
		
	}finally {
		try{
		rs.close();
		ps.close();
		con.close();
		}catch(Exception e)
		{}
	}
}
}