package com.ls.pub.db;

import java.sql.*;

public class JyGameUserDB {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    public JyGameUserDB() {
    }

    /*
     * 获得一个连接
     */
    public Connection getConnection() {
        try {
            return DBUtils.getConnByUrl(DBConf.gameUserDBUrl);
        } catch (Exception e) {
            ////System.out.println("获得连接时出错:"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 功能:查找信息
     *
     * @param sql String
     * @return ResultSet
     * @throws SQLException
     */
    public ResultSet query(String sql) {
        try {
            // //System.out.println("sql = "+sql);
            conn = getConnection();
            stmt = conn.createStatement(); //生成prepareStatement 对象
            rs = stmt.executeQuery(sql); //执行查询，结果返回到结果集中
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    /**
     * 功能:执行更新，如插入等操作
     * update  更新
     * insert  插入
     * delete  删除
     *
     * @param sql String
     * @return int
     * @throws SQLException
     */
    public int update(String sql) {
        int temp = 0;
        try {
            ////System.out.println(sql);
            conn = getConnection();
            stmt = conn.createStatement();
            temp = stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return temp;
    }

    public ResultSet resultSetCallable() {
        CallableStatement stmt1 = null;
        ResultSet rs = null;
        conn = getConnection();
        try {
            stmt1 = conn.prepareCall("{ ? = call NewsOrder(?,?,?)} ");
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
            ////System.out.println(ex.getMessage());
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
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            stmt = null;
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex2) {
                ex2.printStackTrace();
            }
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
            }
            rs = null;
        }
    }
  /*public static void main(String[] args) {
		
	  SqlData con=new SqlData();
	  String sql="INSERT INTO affiche values(2,'123','123321','123123123');";
	  con.update(sql);
	}*/
}
