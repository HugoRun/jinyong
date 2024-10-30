/*
 * 创建日期 2005-9-4
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.pub.db;

import org.apache.struts.action.Action;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 本类用于与数据库建立接
 * <p>
 * 作者:Dan.Huang
 */
public class DBConnection extends Action {
    public Connection conn = null;
    public Statement stmt = null;
    ResultSet rs = null;

    //	org.apache.log4j.Logger log = null;


    /**
     * 与数据库建立连接
     * <p>
     * 返回值－Connection对象
     */
    public DBConnection() throws ClassNotFoundException, SQLException {
        try {
            //org.apache.log4j.PropertyConfigurator.configure("log4j.properties");//指定日志输入文件
            // log = org.apache.log4j.Logger.getLogger(DBConnection.class);//指定日志所记录类


            Context ctx = new InitialContext();//声明连接池
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/mysql");//指定所使用的连接池

            conn = ds.getConnection();//创建数据库连接
            ////System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
        } catch (Exception e)//捕获异常
        {
            e.printStackTrace();
        }
    }

    /**
     * 得到数据库连接
     * <p>
     * 返回值－Connection对象
     */

    public Connection getConn() {
        return (conn);//返回Connection对象
    }

    /**
     * @method:executeQuery <p>@param:SQL语句</p>
     * <p>@throws:抛出空指针异常</p>
     * <p>@return: 结果集 </p>
     */

    public ResultSet executeQuery(String SQL) {
        rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL);//执行SQL语句
        } catch (SQLException ex)//捕获异常
        {
            ex.printStackTrace();
        }
        return rs;//返回结果集
    }

    /**
     * @method:execute <p>@param:SQL语句</p>
     * <p>@throws:抛出空指针异常</p>
     * <p>@return: 无 </p>
     */

    public void execute(String SQL) {
        try {
            stmt = conn.createStatement();
            stmt.execute(SQL);//执行SQL语句
        } catch (SQLException e)//捕获异常
        {
            e.printStackTrace();
        }
    }

    /**
     * @method:executeUpdate <p>@param:SQL语句</p>
     * <p>@throws:抛出空指针异常</p>
     * <p>@return: 无 </p>
     */

    public void executeUpdate(String SQL) {

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(SQL);//执行SQL语句
        } catch (SQLException ex) {//捕获异常
            //log.info("aq.executeUpdate:"+ex.getMessage());
            //log.info("aq.executeUpdatestrSQL"+SQL);
        }
    }

    /**
     * @method:destroy <p>@param:无语句</p>
     * <p>@throws:抛出空指针异常</p>
     * <p>@return: 无 </p>
     */

    public void destroy() {

        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();//数据库连接关闭

        } catch (Exception e)//捕获异常
        {
            e.printStackTrace();
        }

    }

}
