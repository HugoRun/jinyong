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
 * @author ��ϲ��
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Database {
	/**
	 * ���ݿ����URL
	 */
	private static String url;

	/**
	 * ���ݿ�����
	 */
	private static String driver;

	/**
	 * ���ݿ�����û���
	 */
	private static String username;

	/**
	 * ���ݿ���ʿ���
	 */
	private static String password;

	/**
	 * ��������
	 */
	private static String type;

	/**
	 * ����Դ����
	 */
	private static String datasource;

	/**
	 * �����ļ�����
	 */
	private final static String fileName = "database";
	/**
	 * ��ʼ����־
	 */
	private static Logger logger = Logger.getLogger(Database.class);

	private static ThreadLocal<Connection> connection = new ThreadLocal<Connection>();

	static {
		//config();
	}

	private static void config() {
		//��ȡϵͳ����
		PropertyResourceBundle resourceBundle = (PropertyResourceBundle) ResourceBundle
				.getBundle(fileName);
		//��ϵͳ���ø�ֵ�������
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
	//���������������ֱ�ӻ�������
		Connection con = (Connection) connection.get();
		if (con != null && !con.isClosed()) {
												//logger.info("ֱ�ӻ�������");
			return con;
		}
		else{
			      //��JNDI��ȡ������Դ
			try {
				////System.out.println("���ӳ�����1��");
				Context initCtx = new InitialContext();
				Context envCtx = (Context)initCtx.lookup( "java:comp/env");
				DataSource ds = (DataSource)envCtx.lookup( "jdbc/park");
				con= ds.getConnection();
				connection.set(con);
				//logger.info("���ӳ����ӣ�");
				con.setAutoCommit(false);
				return con;
				
			}catch (Exception e)
			{
				try {//���������ļ�����
					//logger.info("����ԭ��:"+e.getMessage());
					logger.info("���������ļ����ӣ�"+url);
					Class.forName(driver);
					con = DriverManager.getConnection(url, username,password);
					con.setAutoCommit(false);
					//connection.set(con);
					return con;

				} catch (ClassNotFoundException ee) {
					ee.printStackTrace();
					//logger.info("����ԭ��:"+e.getMessage());
					// ������������
					String DBDRIVER = "net.sourceforge.jtds.jdbc.Driver";
					// // ���������ַ���
					String CONNSTR = "jdbc:jtds:sqlserver://61.168.44.8:1433/jingcai";
					try
					{
						logger.info("��ͨ���ӣ�");
						Class.forName( DBDRIVER );
						con=DriverManager.getConnection( CONNSTR,"sa","(!wanxiang*)");
						//return DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/3gpark","sa","");
						con.setAutoCommit(false);
						return con;
						
					}
					catch ( ClassNotFoundException ex )
					{
						//logger.info("������ӳ���"+ex.getMessage());
						ex.printStackTrace( System.err );
						return null;
					}
				}
				
			}
		} 		
	} */
	/*
	 * ���һ������
	 */  
	public static Connection getConnections() 
	{
		 try {
			// //System.out.println("�������");
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
			//logger.info("�ع����ݣ�");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public synchronized static void releaseConnection(Connection connection) {

		try {
			if (connection != null && !connection.isClosed())
				connection.close();
			logger.info("�ر����ݿ����ӣ�");
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
		//logger.info("����ԭ��:"+e.getMessage());
		
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