/**
 *
 */
package com.pub;

import com.ben.vo.info.partinfo.PartInfoVO;
import com.pub.db.mysql.SqlData;

import java.sql.ResultSet;

/**
 * @author 侯浩军
 *
 * 3:43:32 PM
 */
public class SystemUserDao {
    SqlData con;


    // 该类是判断角色MONEY的如果铜钱大于100的时候就转换100铜钱==1银子
    public PartInfoVO getMoney(String pPk) {
        try {
            con = new SqlData();
            String sql = "SELECT * FROM `u_part_info` WHERE p_pk = '" + pPk + "'";
            ResultSet rs = con.query(sql);
            PartInfoVO vo = new PartInfoVO();
            while (rs.next()) {
                vo.setPCopper(rs.getString("p_copper"));
            }
            return vo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return null;
    }

    public void getMoneyUpdate(String pSilver, String pCopper, String pPk) {
        try {
            con = new SqlData();
            String sql = "UPDATE `u_part_info` SET `p_silver` = '" + pSilver + "', `p_copper` = '" + pCopper + "' WHERE `p_pk` = '" + pPk + "'";
            con.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

}
