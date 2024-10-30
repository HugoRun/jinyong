package com.ls.pub.db;

import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 功能:数据库链接管理
 *
 * @author 刘帅
 * 9:42:40 PM
 */
public class DBConnection {
    // 日志句柄
    Logger logger = Logger.getLogger(DBConnection.class);
    // 数据库类型
    public static final int GAME_DB         = 1; // 游戏数据库
    public static final int GAME_USER_DB    = 2; // 玩家数据库
    public static final int GAME_LOG_DB     = 3; // 日志数据库
    private Connection conn = null;

    public DBConnection(int db_type) {
        switch (db_type) {
            case GAME_DB: {
                conn = createJyGameConn();
                break;
            }
            case GAME_USER_DB: {
                conn = createJyGameUserConn();
                break;
            }
            case GAME_LOG_DB: {
                conn = createJyGameLogConn();
                break;
            }
        }
    }

    /**
     * 获得数据库链接
     * @return Connection
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * 获得jygame数据库的链接
     * @return Connection
     */
    private Connection createJyGameConn() {
        try {
            return DBUtils.getConnByUrl(DBConf.gameDBUrl);
            // return DBUtils.getConnByJDBC(DBConf.gameJDBCUrl);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    /**
     * 获得jygame_user数据库的链接
     * @return Connection
     */
    private Connection createJyGameUserConn() {
        try {
            return DBUtils.getConnByUrl(DBConf.gameUserDBUrl);
            // return DBUtils.getConnByJDBC(DBConf.gameUserJDBCUrl);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    /**
     * 获得jygame_log数据库的链接
     * @return Connection
     */
    private Connection createJyGameLogConn() {
        try {
            return DBUtils.getConnByUrl(DBConf.gameLogDBUrl);
            // return DBUtils.getConnByJDBC(DBConf.gameLogJDBCUrl);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    /**
     * 关闭链接
     */
    public void closeConn() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        }
    }

    /**
     * 事务提交开始
     */
    public void begin() {
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                //logger.info(e.getMessage());
            }
        } else {
            logger.info("事务开始时:数据库链接没有打开");
        }
    }

    /**
     * 事务提交
     */
    public void commit() {
        try {
            if (conn != null && !conn.getAutoCommit()) {
                conn.commit();
                conn.setAutoCommit(true);
            } else {
                if (conn == null) {
                    logger.info("事务提交时:数据库链接没有打开");
                } else {
                    logger.info("事务提交时:事物还没开始，不能提交");
                }
            }
        } catch (SQLException e) {
            //logger.info(e.getMessage());
        }
    }

    /**
     * 事务回滚
     */
    public void rollback() {
        try {
            if (conn != null && !conn.getAutoCommit()) {
                conn.rollback();
                conn.setAutoCommit(true);
            } else {
                if (conn == null) {
                    logger.info("事务回滚时:数据库链接没有打开");
                } else {
                    logger.info("事务回滚时:事物还没有开始，不能回滚");
                }
            }
        } catch (SQLException e) {
            //logger.info(e.getMessage());
        }
    }
}
