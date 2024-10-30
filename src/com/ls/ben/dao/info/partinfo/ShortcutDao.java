package com.ls.ben.dao.info.partinfo;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.partinfo.ShortcutVO;
import com.ls.pub.db.DBConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 功能:玩家快捷键表
 *
 * @author 刘帅 6:00:48 PM
 */
public class ShortcutDao extends DaoBase {
    private boolean add;

    /**
     * 得到一个玩家的快捷键
     *
     * @param p_pk
     * @return
     */
    public List<ShortcutVO> getByPpk1(int p_pk) {
        List<ShortcutVO> shortcuts = new ArrayList<ShortcutVO>();
        ShortcutVO shortcut = null;
        String sql = "SELECT * FROM u_shortcut_info WHERE p_pk=" + p_pk + " ORDER BY sc_pk";
        logger.debug(sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                shortcut = new ShortcutVO();
                shortcut.setScPk(rs.getInt("sc_pk"));
                shortcut.setPPk(p_pk);
                shortcut.setScName(rs.getString("sc_name"));
                shortcut.setScDisplay(rs.getString("sc_display"));
                shortcut.setScType(rs.getInt("sc_type"));
                shortcut.setOperateId(rs.getInt("operate_id"));
                shortcuts.add(shortcut);
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {

            e.printStackTrace();
            logger.debug(e.toString());
        } finally {
            dbConn.closeConn();
        }
        return shortcuts;
    }

    /**
     * 更新一个快捷键
     */
    public int updateByPpk(int sc_pk, int sc_type, String sc_display, int operate_id) {
        int result = -1;
        String sql = "UPDATE `u_shortcut_info` SET sc_type = " + sc_type + ", sc_display='" + sc_display + "', operate_id = " + operate_id + " WHERE sc_pk = " + sc_pk;
        logger.debug(sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            result = stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return result;
    }

    /**
     * 根据sc_pk得到一个快捷键的详细信息
     */
    public ShortcutVO getByScPk1(int sc_pk) {
        ShortcutVO shortcut = null;
        String sql = "SELECT * FROM u_shortcut_info WHERE sc_pk = " + sc_pk;
        logger.debug(sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                shortcut = new ShortcutVO();
                shortcut.setScPk(sc_pk);
                shortcut.setPPk(rs.getInt("p_pk"));
                shortcut.setScName(rs.getString("sc_name"));
                shortcut.setScDisplay(rs.getString("sc_display"));
                shortcut.setScType(rs.getInt("sc_type"));
                shortcut.setOperateId(rs.getInt("operate_id"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {

            e.printStackTrace();

        } finally {
            dbConn.closeConn();
        }
        return shortcut;
    }

    /**
     * 把所有快捷键恢复到初始值
     *
     * @param p_pk
     */
    public int clearShortcut(int p_pk) {
        int result = -1;
        String sql = "UPDATE u_shortcut_info SET sc_type=0,operate_id=0,sc_display=sc_name WHERE p_pk=" + p_pk;
        logger.debug(sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            result = stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return result;
    }

    /**
     * 例:杀怪状态使用包子完了以后包裹里在没有包子了 则吧快捷键里的包子快捷键设置为空
     *
     * @param p_pk
     */
    public int clearShortcutoperate_id(int p_pk, int djid) {
        int result = -1;
        String sql = "UPDATE u_shortcut_info SET sc_type=0,sc_display=sc_name WHERE p_pk=" + p_pk + " AND operate_id=" + djid;
        logger.debug(sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            result = stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return result;
    }

    public LinkedHashMap<Integer, ShortcutVO> getAllByPpk(int p_pk) {
        LinkedHashMap<Integer, ShortcutVO> shortcuts = new LinkedHashMap<Integer, ShortcutVO>();
        ShortcutVO shortcut = null;
        String sql = "SELECT * FROM u_shortcut_info WHERE p_pk=" + p_pk + " ORDER BY sc_pk";
        logger.debug(sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                shortcut = new ShortcutVO();
                shortcut.setScPk(rs.getInt("sc_pk"));
                shortcut.setPPk(p_pk);
                shortcut.setScName(rs.getString("sc_name"));
                shortcut.setScDisplay(rs.getString("sc_display"));
                shortcut.setScType(rs.getInt("sc_type"));
                shortcut.setOperateId(rs.getInt("operate_id"));
                shortcuts.put(rs.getInt("sc_pk"), shortcut);
                logger.debug("取出时快捷键名为=" + shortcut.getScName());
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {

            e.printStackTrace();
            logger.debug(e.toString());
        } finally {
            dbConn.closeConn();
        }

        return shortcuts;
    }
}
