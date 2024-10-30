package com.ls.ben.dao.login;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

public class LoginDao extends DaoBase {
    /**
     * 插入一个新的账号
     *
     * @param user_name
     * @param pwd
     * @return 返回u_pk
     */
    public int incert(String user_name, String pwd, String login_ip) {
        int u_pk = -1;
        String sql = "INSERT INTO u_login_info VALUES(null, '" + user_name + "', MD5('" + pwd + "'), '" + 1 + "', now(), '" + login_ip + "', now(), 0, 0, '', '')";
        logger.debug(sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                u_pk = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return u_pk;
    }

    /**
     * 更新账号登陆状态
     *
     * @param u_pk u_pk
     * @param login_ip login_ip
     * @return 返回u_pk
     */
    public void updateState(String u_pk, String login_ip) {
        String sql = "UPDATE `u_login_info` SET login_state = '1', last_login_ip = '" + login_ip + "', last_login_time = now() WHERE u_pk = " + u_pk;
        logger.debug(sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            dbConn.closeConn();
        }
    }


    /**
     * 判断该用户名是否存在
     *
     * @param user_name
     * @return
     */
    public boolean isHaveName(String user_name) {
        boolean result = false;
        String sql = "SELECT u_pk FROM  u_login_info WHERE u_name = '" + user_name + "'";
        logger.debug(sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                result = true;
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return result;
    }
}
