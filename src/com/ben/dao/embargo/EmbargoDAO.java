/**
 *
 */
package com.ben.dao.embargo;

import com.pub.db.mysql.SqlData;

import java.sql.ResultSet;

/**
 * @author 侯浩军 9:27:47 AM
 */
public class EmbargoDAO {
    SqlData con;

    /**
     * 判断是否还在禁言时间内
     *
     * @param pPk
     * @param time
     * @return
     */
    public String isEmbargo(int pPk, String time) {
        try {
            con = new SqlData();
            String sql = "SELECT e_time FROM u_embargo WHERE p_pk = '" + pPk + "' AND begin_time < '" + time + "' AND end_time > '" + time + "'";
            ResultSet rs = con.query(sql);
            String eTime = null;
            if (rs.next()) {
                eTime = rs.getString("e_time");
            }
            return eTime;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return null;
    }
}
