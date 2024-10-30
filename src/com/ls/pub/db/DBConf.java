package com.ls.pub.db;

/***
 * 数据库配置
 */
public class DBConf {
    // 游戏数据库
    public static String gameDBUrl = "jdbc:mysql://127.0.0.1:3306/jygame?autoReconnect=true";
    public static String gameJDBCUrl = "java:comp/env/jdbc/jygame";
    // 用户数据库
    public static String gameUserDBUrl = "jdbc:mysql://127.0.0.1:3306/jygame_user?autoReconnect=true";
    public static String gameUserJDBCUrl = "java:comp/env/jdbc/jygame_user";
    // 日志数据库
    public static String gameLogDBUrl = "jdbc:mysql://127.0.0.1:3306/jygame_log?autoReconnect=true";
    public static String gameLogJDBCUrl = "java:comp/env/jdbc/jygame_log";
    // 数据库账号密码
    public static String username = "sy_mmo";
    public static String password = "sy_mmo123456";

}
