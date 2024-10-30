/**
 *
 */
package com.ben.dao.avoidpkprop;

import java.sql.ResultSet;

import com.ben.vo.avoidpkprop.AvoidPkPropVO;
import com.pub.db.mysql.SqlData;

/**
 * @author 侯浩军
 *
 */
public class AvoidPkPropDAO {
    SqlData con;

    /**
     * 插入免PK道具的使用时间
     */
    public void addAvoidPkProp(int pPK, String beginTime, String endTime) {
        try {
            con = new SqlData();
            String sql = "INSERT INTO `u_avoidpkprop` VALUES(null, '" + pPK + "', '" + beginTime + "', '" + endTime + "')";
            con.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    /**
     * 删除保护时间
     *
     * @param pPk
     */
    public void deleteAvoidPkProp(int pPk) {
        try {
            con = new SqlData();
            String sql = "DELETE FROM `u_avoidpkprop` WHERE p_pk='" + pPk + "'";
            con.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    /**
     * 判断是否在保护时间内
     * @param pPk
     * @return
     */
    public boolean isAvoidPkPropTime(int pPk) {
        try {
            con = new SqlData();
            String sql = "SELECT * FROM `u_avoidpkprop` WHERE end_time > now() AND p_pk='" + pPk + "'";
            ResultSet rs = con.query(sql);
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return false;
    }

    /**
     * 返回是否在有效保护范围内
     * @param pPk
     * @param time
     * @return
     */
    public AvoidPkPropVO getAvoidPkProp(int pPk, String time) {
        try {
            con = new SqlData();
            String sql = "SELECT * FROM `u_avoidpkprop` WHERE begin_time < '" + time + "' AND end_time > '" + time + "' AND p_pk='" + pPk + "'";
            ResultSet rs = con.query(sql);
            AvoidPkPropVO vo = null;
            if (rs.next()) {
                vo = new AvoidPkPropVO();
                vo.setAPk(rs.getInt("a_pk"));
                vo.setPPk(rs.getInt("p_pk"));
                vo.setBeginTime(rs.getString("begin_time"));
                vo.setEndTime(rs.getString("end_time"));
            }
            return vo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return null;
    }
}
