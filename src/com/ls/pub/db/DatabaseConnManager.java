/**
 *
 */
package com.ls.pub.db;

import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 功能:数据库连接管理
 * @author 刘帅
 * 3:57:33 PM
 */
public class DatabaseConnManager {
    // 日志句柄
    Logger logger = Logger.getLogger(DatabaseConnManager.class);

    // 连接
    Connection gameConn = null;
    Connection gameUserConn = null;
    Connection gameLogConn = null;

    /***
     * 获取Game数据库连接
     * @return Connection
     */
    public Connection getJyGameConn() {
        if (gameConn == null) {
            try {
                return DBUtils.getConnByJDBC(DBConf.gameJDBCUrl);
            } catch (Exception e) {
                gameConn = DBUtils.getConnByUrl(DBConf.gameDBUrl);
            }
        }
        return gameConn;
    }

    /***
     * 获取GameUser数据库连接
     * @return Connection
     */
    public Connection getJyGameUserConn() {
        if (gameUserConn == null) {
            try {
                return DBUtils.getConnByJDBC(DBConf.gameUserJDBCUrl);
            } catch (Exception e) {
                gameUserConn = DBUtils.getConnByUrl(DBConf.gameUserDBUrl);
            }
        }
        return gameUserConn;
    }

    /***
     * 获取GameLog数据库连接
     * @return Connection
     */
    public Connection getJyGameLogConn() {
        if (gameLogConn == null) {
            try {
                return DBUtils.getConnByJDBC(DBConf.gameLogJDBCUrl);
            } catch (Exception e) {
                gameLogConn = DBUtils.getConnByUrl(DBConf.gameLogDBUrl);
            }
        }
        return gameLogConn;
    }

    /***
     * 关闭所有数据库连接
     */
    public void closeConn() {
        // logger.debug("关闭数据库连接");
        try {
            DBUtils.closeConn(gameConn);
            DBUtils.closeConn(gameUserConn);
            DBUtils.closeConn(gameLogConn);
        } catch (SQLException e) {
            // throw new RuntimeException(e);
        }
    }
}
