/*
 * �������� 2005-9-4
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package com.pub.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import javax.naming.*;
import javax.sql.DataSource;

import org.apache.struts.action.Action;
	/**
	 * �������������ݿ⽨����
	 *
	 * ����:Dan.Huang
	 */
	    public  class DBConnection  extends Action{ 
		public Connection conn=null;
		public Statement stmt=null;
		ResultSet rs = null;

	//	org.apache.log4j.Logger log = null;


		/**
		 * �����ݿ⽨������
		 *
		 * ����ֵ��Connection����
		 */
		public DBConnection() throws ClassNotFoundException,SQLException{
			try{
				//org.apache.log4j.PropertyConfigurator.configure("log4j.properties");//ָ����־�����ļ�
			   // log = org.apache.log4j.Logger.getLogger(DBConnection.class);//ָ����־����¼��
				
			  
			    Context ctx=new InitialContext();//�������ӳ�
				DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/mysql");//ָ����ʹ�õ����ӳ�
		    	
		    	conn = ds.getConnection();//�������ݿ�����
		    	////System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
			}catch(Exception e)//�����쳣
			{
				e.printStackTrace();
			}
		}
                /**
                 * �õ����ݿ�����
                 *
                 * ����ֵ��Connection����
                 */

		public Connection getConn(){
                      return (conn);//����Connection����
		}

                 /**
                 *@method:executeQuery
                 *<p>@param:SQL���</p>
                 *<p>@throws:�׳���ָ���쳣</p>
                 *<p>@return: ����� </p>
                 *
                 */

		public ResultSet executeQuery(String SQL){
			rs=null;
			try{
				stmt=conn.createStatement();
				rs=stmt.executeQuery(SQL);//ִ��SQL���
			}
			catch(SQLException ex)//�����쳣
			{
				ex.printStackTrace();
			}
			return rs;//���ؽ����
		}
                /**
                *@method:execute
                *<p>@param:SQL���</p>
                *<p>@throws:�׳���ָ���쳣</p>
                *<p>@return: �� </p>
                *
                */

		public void execute(String SQL)
		{
		    try{
		        stmt=conn.createStatement();
		        stmt.execute(SQL);//ִ��SQL���
		    }
		    catch(SQLException e)//�����쳣
		    {
				e.printStackTrace();
		    }
		}
                /**
                 *@method:executeUpdate
                 *<p>@param:SQL���</p>
                 *<p>@throws:�׳���ָ���쳣</p>
                 *<p>@return: �� </p>
                */

		public void executeUpdate(String SQL){

			try{
				stmt=conn.createStatement();
				stmt.executeUpdate(SQL);//ִ��SQL���
			}
			catch(SQLException ex){//�����쳣
				//log.info("aq.executeUpdate:"+ex.getMessage());
				//log.info("aq.executeUpdatestrSQL"+SQL);
			}
		}
           /**
           *@method:destroy
           *<p>@param:�����</p>
           *<p>@throws:�׳���ָ���쳣</p>
           *<p>@return: �� </p>
           */
   
		public void destroy(){
			
			try{
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				if(conn!=null)conn.close();//���ݿ����ӹر�
			
			}
			catch(Exception e)//�����쳣
			{
				e.printStackTrace();
			}
			
		}
	
	}
