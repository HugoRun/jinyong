package com.pm.dao.mail;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.pm.vo.mail.MailBonusVO;

import java.sql.SQLException;

public class MailBonusDao extends DaoBase {
    /**
     * 插入玩家获得的奖励*
     */
    public void insertBonus(int p_pk, int mail_id, String bonus) {
        String sql = "INSERT INTO `u_mail_bonus` VALUES (null," + p_pk + "," + mail_id + ",'" + bonus + "',0)";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
    }

    /**
     * 更新玩家领取
     */
    public void updateState(int p_pk, int mail_id) {
        String sql = "UPDATE `u_mail_bonus` SET is_have = 1 WHERE p_pk = " + p_pk + " AND mail_id = " + mail_id;
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
    }

    /**
     * 得到奖励内容
     */
    public MailBonusVO getMailBonus(int p_pk, int mail_id) {
        MailBonusVO vo = null;
        String sql = "SELECT * FROM u_mail_bonus where p_pk = " + p_pk + " and mail_id = " + mail_id;
        logger.debug(sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                vo = new MailBonusVO();
                vo.setId(rs.getInt("id"));
                vo.setMail_id(rs.getInt("mail_id"));
                vo.setP_pk(rs.getInt("p_pk"));
                vo.setBonus(rs.getString("bonus"));
                vo.setIs_have(rs.getInt("is_have"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return vo;
    }
}
