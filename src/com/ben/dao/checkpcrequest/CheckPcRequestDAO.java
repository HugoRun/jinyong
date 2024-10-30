/**
 *
 */
package com.ben.dao.checkpcrequest;

import com.ben.vo.checkpcrequest.CheckPcRequestVO;
import com.pub.db.jygamedb.JyGameDB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 侯浩军 屏蔽PC电脑用户访问
 */
public class CheckPcRequestDAO {
    JyGameDB con;

    /**
     * 查询库里边时候有相关白名单IP
     * @return <CheckPcRequestVO>
     */
    public List<CheckPcRequestVO> isCheckPcWhiteList() {
        try {
            con = new JyGameDB();
            String sql = "SELECT * FROM ip_whitelist";
            ResultSet rs = con.query(sql);
            List<CheckPcRequestVO> list = new ArrayList();
            while (rs.next()) {
                CheckPcRequestVO vo = new CheckPcRequestVO();
                vo.setIpPk(rs.getInt("ip_pk"));
                vo.setIpBegin(rs.getString("ip_begin"));
                vo.setIpEnd(rs.getString("ip_end"));
                list.add(vo);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return null;
    }

    /**
     * 黑名单
     * @param ip
     * @return
     */
    public boolean isCheckPcBlackList(String ip) {
        try {
            con = new JyGameDB();
            String sql = "SELECT * FROM ip_blacklist WHERE ip_list='" + ip + "'";
            //System.out.println("**************  " + sql);
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
}
