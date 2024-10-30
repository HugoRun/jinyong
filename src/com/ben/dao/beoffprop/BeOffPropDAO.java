/**
 *
 */
package com.ben.dao.beoffprop;

import com.ben.vo.beoffprop.BeOffPropVO;
import com.pub.db.jygamedb.JyGameDB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HHJ
 */
public class BeOffPropDAO {
    JyGameDB conn;

    /**
     * 查询所有离线道具
     *
     * @return
     */
    public List getBeOffPropList() {
        try {
            conn = new JyGameDB();
            String sql = "SELECT * FROM `be_off_prop`";
            ResultSet rs = conn.query(sql);
            BeOffPropVO vo = null;
            List list = new ArrayList();
            while (rs.next()) {
                vo = new BeOffPropVO();
                vo.setBeId(rs.getInt("be_id"));
                vo.setPropName(rs.getString("prop_name"));
                vo.setPropDisplay(rs.getString("prop_display"));
                vo.setPropMoney(rs.getString("prop_money"));
                vo.setPropTime(rs.getString("prop_time"));
                list.add(vo);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return null;
    }

    /**
     * 查看详细信息
     *
     * @param be_id
     * @return
     */
    public BeOffPropVO getBeOffPropView(int be_id) {
        try {
            conn = new JyGameDB();
            String sql = "SELECT * FROM `be_off_prop` WHERE be_id = '" + be_id + "'";
            ResultSet rs = conn.query(sql);
            BeOffPropVO vo = null;
            if (rs.next()) {
                vo = new BeOffPropVO();
                vo.setBeId(rs.getInt("be_id"));
                vo.setPropName(rs.getString("prop_name"));
                vo.setPropDisplay(rs.getString("prop_display"));
                vo.setPropMoney(rs.getString("prop_money"));
                vo.setPropTime(rs.getString("prop_time"));
            }
            return vo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return null;
    }
}
