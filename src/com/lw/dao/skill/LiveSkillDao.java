package com.lw.dao.skill;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.skill.SkillVO;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class LiveSkillDao extends DaoBase {

    /**
     * 得到生活技能
     */
    public List<SkillVO> getLiveSkillInfo() {
        List<SkillVO> list = new ArrayList<SkillVO>();
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            String sql = "SELECT * FROM `skill` WHERE `sk_type` = 2";
            logger.debug(sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            SkillVO vo = null;
            if (rs.next()) {
                vo = new SkillVO();
                vo.setSkId(rs.getInt("sk_id"));
                vo.setSkName(StringUtil.isoToGBK(rs.getString("sk_name")));
                vo.setSkDisplay(StringUtil.isoToGBK(rs.getString("sk_display")));
                vo.setSkType(rs.getInt("sk_type"));
                vo.setSkExpend(rs.getString("sk_expend"));
                vo.setSkUsecondition(rs.getInt("sk_usecondition"));
                vo.setSkDamageDi(rs.getInt("sk_damage_di"));
                vo.setSkDamageGao(rs.getInt("sk_damage_gao"));
                vo.setSkArea(rs.getInt("sk_area"));
                vo.setSkBaolv(rs.getString("sk_baolv"));
                vo.setSkYun(rs.getInt("sk_yun"));
                vo.setSkYunBout(rs.getInt("sk_yun_bout"));
                vo.setSkBuff(rs.getInt("sk_buff"));
                vo.setSkBuffProbability(rs.getInt("sk_buff_probability"));
                vo.setSkLqtime(rs.getInt("sk_lqtime"));
                vo.setSkGjMultiple(rs.getDouble("sk_gj_multiple"));
                vo.setSkFyMultiple(rs.getDouble("sk_fy_multiple"));
                vo.setSkHpMultiple(rs.getDouble("sk_hp_multiple"));
                vo.setSkMpMultiple(rs.getDouble("sk_mp_multiple"));
                vo.setSkBjMultiple(rs.getDouble("sk_bj_multiple"));
                vo.setSkGjAdd(rs.getInt("sk_gj_add"));
                vo.setSkFyAdd(rs.getInt("sk_fy_add"));
                vo.setSkHpAdd(rs.getInt("sk_hp_add"));
                vo.setSkMpAdd(rs.getInt("sk_mp_add"));
                vo.setSkGroup(rs.getInt("sk_group"));
                list.add(vo);

            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return list;
    }

    /**
     * 根据技能名称得到生活技能所在技能组
     */
    public int getLiveSkillGroup(String sk_name) {
        int sk_group = 0;
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            String sql = "SELECT `sk_group` FROM `skill` WHERE `sk_name` = '" + sk_name + "'";
            logger.debug(sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                sk_group = rs.getInt("sk_group");
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return sk_group;
    }

    /**
     * 根据技能名称得到生活技能ID
     */
    public int getLiveSkillID(String sk_name) {
        int sk_id = 0;
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            String sql = "SELECT `sk_id` FROM `skill` WHERE `sk_name` = '" + sk_name + "'";
            logger.debug(sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                sk_id = rs.getInt("sk_id");
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return sk_id;
    }
}
