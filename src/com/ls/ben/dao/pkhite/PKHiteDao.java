package com.ls.ben.dao.pkhite;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.pkhite.PKHiteVO;
import com.ls.pub.db.DBConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理pk仇恨系统的dao
 *
 * @author Thomas.lei
 */
public class PKHiteDao extends DaoBase {
    /**
     * ****查看是否此玩家已经有仇恨记录,如果有则返回记录*******
     */
    public PKHiteVO checkIsHaveHiteRecord(int p_pk, int enemyPpk) {
        String sql = "SELECT * FROM u_pk_hite WHERE p_pk=" + p_pk + " AND enemyPpk = " + enemyPpk;
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        logger.debug(sql);
        PKHiteVO pv = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                pv = new PKHiteVO();
                pv.setId(rs.getInt("id"));
                pv.setP_pk(rs.getInt("p_pk"));
                pv.setEnemyPpk(rs.getInt("enemyPpk"));
                pv.setEnemyName(rs.getString("enemyName"));
                pv.setEnemyGrade(rs.getInt("enemyGrade"));
                pv.setHitePoint(rs.getInt("hitePoint"));
                pv.setGeneralPkCount(rs.getInt("generalPKcount"));
                pv.setActivePkCount(rs.getInt("activePKcount"));
                pv.setUpdateTime(rs.getDate("updateTime"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();

        }
        return pv;
    }

    /**
     * *****给玩家添加一个新的仇恨对象**********
     */
    public void addEnemy(PKHiteVO pv) {
        String sql = "INSERT INTO u_pk_hite (p_pk,enemyUpk,enemyPpk,enemyName,enemyGrade,hitePoint,generalPKcount,activePkcount,updateTime) VALUES (" + pv.getP_pk() + "," + pv.getEnemyUpk() + "," + pv.getEnemyPpk() + ",'" + pv.getEnemyName() + "'," + pv.getEnemyGrade() + "," + pv.getHitePoint() + "," + pv.getGeneralPkCount() + "," + pv.getActivePkCount() + ",now())";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        logger.debug(sql);
        try {
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
     * *****玩家已经有仇恨对象则更新仇恨点********
     */
    public void updateHitePoint(PKHiteVO pv) {
        String sql = "UPDATE u_pk_hite SET enemyGrade=" + pv.getEnemyGrade() + ",hitePoint=" + pv.getHitePoint() + ",generalPKcount=" + pv.getGeneralPkCount() + ",activePkcount=" + pv.getActivePkCount() + ",updateTime=now() WHERE id=" + pv.getId();
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        logger.debug(sql);
        try {
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
     * *******分页查询玩家的仇恨表***********
     */
    public List<PKHiteVO> getEnemys(int ppk, int index, int limit) {
        String sql = "SELECT * FROM `u_pk_hite` WHERE p_pk = " + ppk + " ORDER BY `hitePoint` DESC LIMIT " + index * limit + ", " + limit;
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        logger.debug(sql);
        List<PKHiteVO> list = new ArrayList<PKHiteVO>();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                PKHiteVO pv = new PKHiteVO();
                pv.setId(rs.getInt("id"));
                pv.setP_pk(rs.getInt("p_pk"));
                pv.setEnemyUpk(rs.getInt("enemyUpk"));
                pv.setEnemyPpk(rs.getInt("enemyPpk"));
                pv.setEnemyName(rs.getString("enemyName"));
                pv.setEnemyGrade(rs.getInt("enemyGrade"));
                pv.setHitePoint(rs.getInt("hitePoint"));
                pv.setGeneralPkCount(rs.getInt("generalPKcount"));
                pv.setActivePkCount(rs.getInt("activePKcount"));
                pv.setUpdateTime(rs.getDate("updateTime"));
                list.add(pv);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();

        }
        return list;
    }

    /**
     * **********得到记录总条数*************
     */
    public int getRecordNum(int ppk) {
        String sql = "SELECT COUNT(*) AS total FROM u_pk_hite WHERE p_pk=" + ppk;
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        logger.debug(sql);
        int count = 0;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                count = rs.getInt("total");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return count;
    }

    /**
     * **********玩家删除角色的时候删除所有和玩家有关的仇恨信息*************
     */
    public void removeHiteInfo(int ppk) {
        String sql = "DELETE FROM u_pk_hite WHERE p_pk=" + ppk + " or enemyPpk=" + ppk;
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        logger.debug(sql);
        int count = 0;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
    }
}
