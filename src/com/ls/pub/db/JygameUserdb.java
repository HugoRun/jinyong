package com.ls.pub.db;
import java.sql.*; 

public class JygameUserdb {
  Connection con = null;
  Statement stmt = null;
  ResultSet rs = null;
  public JygameUserdb() {
  }
/*
 * 获得一个连接 
 */  
public Connection getConnetion() 
{
	 try {
		 String url = "jdbc:mysql://127.0.0.1:3306/jygame_user?autoReconnect=true";
		 //url = "jdbc:mysql://127.0.0.1:3306/jygame?autoReconnect=true";
		 //url = "jdbc:mysql://192.168.1.102:3306/jygame_user?autoReconnect=true";
	     Class.forName("com.mysql.jdbc.Driver"); 
		 con= DriverManager.getConnection(url,"root","root");
	     return con;
	    }
	    catch (Exception e) {
	      ////System.out.println("获得连接时出错:"+e.getMessage());
	      e.printStackTrace();
	    }
	    return null;
} 
  /**
   * 功能:查找信息
   * @param user_id String
   * @throws SQLException
   * @return ResultSet
   */
  public ResultSet query(String sql) {
    try {
       // //System.out.println("sql = "+sql);
    	con=getConnetion();
      stmt = con.createStatement(); //生成prepareStatement 对象
      rs = stmt.executeQuery(sql); //执行查询，结果返回到结果集中  
    }
    catch (SQLException ex) {
      ex.printStackTrace();
    }
    return rs;
  }

  /**
   * 功能:执行更新，如插入等操作
   * update  更新
   * insert  插入
   * delete  删除
   * @param sql String
   * @throws SQLException
   * @return int
   */ 
  public int update(String sql) {
    int temp = 0;
    try {
    	////System.out.println(sql); 
      con=getConnetion();
      stmt = con.createStatement();
      temp = stmt.executeUpdate(sql); 	
    }
    catch (SQLException ex) {
      ex.printStackTrace();
    }
    return temp;
  }

  public ResultSet resultSetCallable()
  {
    CallableStatement stmt1 = null;
    ResultSet rs = null;
    con=getConnetion();
    try {
      stmt1 = con.prepareCall("{ ? = call NewsOrder(?,?,?)} ");
    int aa = 0;
    stmt1.setInt(2,1);
    stmt1.setInt(3,10);
    stmt1.registerOutParameter(1,Types.OTHER);
    stmt1.registerOutParameter(4,Types.INTEGER);
    stmt1.execute();
    int temp = stmt1.getInt(3);
    
    rs = (ResultSet)stmt1.getObject(1);
   // //System.out.println("返回值为:" + temp);
    }
    catch (SQLException ex) {
     ////System.out.println(ex.getMessage());
    }
    finally
    {
       // close();
    }
    return rs;
  }

    
  /**
   * 功能:关闭连接
   */
  public void close() {
    if (stmt != null) {
      try {
        stmt.close();
      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
      }
      stmt = null;
    }
    if (rs != null) {
      try {
        rs.close();
      }
      catch (SQLException ex2) {
        ex2.printStackTrace();
      }
      try {
        if (!con.isClosed()) {
          con.close();
        }
      }
      catch (SQLException ex) {
      }
      rs = null;
    }
  }
  /*public static void main(String[] args) {
		
	  SqlData con=new SqlData();
	  String sql="insert into affiche values(2,'123','123321','123123123');";
	  con.update(sql);
	}*/
}
