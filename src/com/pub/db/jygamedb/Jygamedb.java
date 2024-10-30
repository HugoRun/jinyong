package com.pub.db.jygamedb;

import com.ls.pub.db.DBConf;
import com.ls.pub.db.DBUtils;
import org.apache.log4j.Logger;

import java.sql.*;

/***
 * 功能:数据库操作类 可以完成对数据库的删除，插入，查找等
 * @author yzjcomcn
 * @version 1.0
 */
public class JyGameDB {
    public Connection conn = null;
    public Statement stmt = null;
    ResultSet rs = null;
    Logger logger = Logger.getLogger("log.dao");

    /*
     * 获得一个连接
     */
    public JyGameDB() throws ClassNotFoundException, SQLException {
        try {
            // conn = DBUtils.getConnByJDBC(DBConf.gameJDBCUrl);
            conn = DBUtils.getConnByUrl(DBConf.gameDBUrl);
        } catch (Exception e) {
            // 捕获异常
            e.printStackTrace();
        }
    }

    public Connection getConn() {
        return (conn);//返回Connection对象
    }

    /**
     * 功能:查找信息
     *
     * @param sql String
     * @return ResultSet
     * @throws SQLException
     */
    public ResultSet query(String sql) {
        rs = null;
        try {
            logger.debug("查询: " + sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);//执行SQL语句
        } catch (SQLException ex)//捕获异常
        {
            ex.printStackTrace();
        }
        return rs;//返回结果集
    }

    /**
     * 功能:执行更新，如插入等操作 update 更新 insert 插入 delete 删除
     *
     * @param sql String
     * @return int
     * @throws SQLException
     */
    public int update(String sql) {
        int temp = 0;
        try {
            logger.debug("更新:" + sql);
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
            ex.getMessage();
            ////System.out.println(ex.getMessage());
        }
        return rs;
    }

    /**
     * 功能:关闭连接
     */
    public void close() {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            ////System.out.println("关闭连接");
        } catch (Exception e) {
            // 捕获异常
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    logger.info(e.getMessage());
                }
            }
        }
    }
}
