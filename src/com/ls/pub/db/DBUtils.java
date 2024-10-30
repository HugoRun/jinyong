package com.ls.pub.db;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    /***
     * 关闭数据库连接
     */
    public static void closeConn(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    /***
     * 根据URL获取连接
     * @param url url
     * @return Connection
     */
    public static Connection getConnByUrl(String url) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(url, DBConf.username, DBConf.password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 从JDBC Url获取连接
     * @param url url
     * @return Connection
     */
    public static Connection getConnByJDBC(String url) throws SQLException, NamingException {
        InitialContext context = new InitialContext();
        DataSource dataSource = (DataSource)context.lookup(url);
        return dataSource.getConnection();
    }
}
