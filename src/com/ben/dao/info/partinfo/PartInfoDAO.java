package com.ben.dao.info.partinfo;

import com.ben.vo.info.partinfo.PartInfoVO;
import com.ls.pub.util.StringUtil;
import com.pub.db.jygamedb.JyGameDB;
import com.pub.db.mysql.SqlData;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author 侯浩军 pUpHp, pHp,pUpMp, 8:41:31 PM
 */
public class PartInfoDAO {
    SqlData con;
    JyGameDB con1;

    Logger logger = Logger.getLogger(PartInfoDAO.class);

    /**
     * 初始化快捷键
     *
     * @param pPk
     */
    public void initShortcut(String pPk) {
        try {
            con = new SqlData();
            String sql = "INSERT INTO `u_shortcut_info` VALUES " +
                    "(null, '" + pPk + "', '快捷键1', '快捷键1', 0, 0, 0)" +
                    ", (null, '" + pPk + "', '快捷键2', '快捷键2', 0, 0, 0)" +
                    ", (null, '" + pPk + "', '快捷键3', '快捷键3', 0, 0, 0)" +
                    ", (null, '" + pPk + "', '快捷键4', '快捷键4', 0, 0, 0)" +
                    ", (null, '" + pPk + "', '快捷键5', '快捷键5', 0, 0, 0)" +
                    ", (null, '" + pPk + "', '快捷键6', '快捷键6', 0, 0, 0)";
            con.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }


    /**
     * 通过注册ID 去找角色名是否存在
     */
    public boolean getPartTypeListName(String pName) {
        try {
            con = new SqlData();
            String sql = "SELECT p_pk FROM u_part_info WHERE p_name='" + pName + "'";
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
     * 判断角色是否是新手状态
     */
    public boolean getIsNewState(String pName) {
        try {
            con = new SqlData();
            String sql = "SELECT * FROM u_part_info WHERE p_name = '" + pName + "' AND `player_state_by_new` = 1";
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
     * 通过注册ID得到玩家的角色的数量
     */
    public int getRoleNum(String uPk) {
        int role_num = 0;
        try {
            con = new SqlData();
            String sql = "SELECT COUNT(p_pk) AS role_num FROM u_part_info WHERE u_pk = " + uPk + " AND delete_flag=0";
            ResultSet rs = con.query(sql);
            if (rs.next()) {
                role_num = rs.getInt("role_num");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return role_num;
    }


    /**
     * 通过角色ID 去找角色相关信息
     */
    public PartInfoVO getPartView(String pPk) {
        try {
            con = new SqlData();
            String sql = "SELECT * FROM u_part_info WHERE p_pk = '" + pPk + "'";
            ResultSet rs = con.query(sql);
            PartInfoVO vo = new PartInfoVO();
            while (rs.next()) {
                vo.setPPk(rs.getInt("p_pk"));
                vo.setUPk(rs.getInt("u_pk"));
                vo.setPName(rs.getString("p_name"));
                vo.setPSex(rs.getInt("p_sex"));
                vo.setPGrade(rs.getInt("p_grade"));
                vo.setPHp(rs.getInt("p_hp"));
                vo.setPMp(rs.getInt("p_mp"));
                vo.setPGj(rs.getInt("p_gj"));
                vo.setPFy(rs.getInt("p_fy"));
                vo.setPTeacherType(rs.getInt("p_teacher_type"));
                vo.setPTeacher(rs.getInt("p_teacher"));
                vo.setPHarness(rs.getInt("p_harness"));
                vo.setPFere(rs.getInt("p_fere"));
                vo.setPExperience(rs.getString("p_experience"));
                vo.setPXiaExperience(rs.getString("p_xia_experience"));
                vo.setPCopper(rs.getString("p_copper"));
                vo.setPPkValue(rs.getInt("p_pk_value"));
                vo.setPPks(rs.getInt("p_pks"));
                vo.setPMap(rs.getString("p_map"));
                vo.setCreateTime(rs.getString("create_time"));
                vo.setTe_level(rs.getInt("te_level"));
                vo.setChuangong(rs.getString("chuangong"));
                vo.setPBenJiExp(rs.getInt("p_benji_experience"));
                vo.setLast_shoutu_time(rs.getDate("last_shoutu_time"));
            }
            return vo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return null;
    }

    /**
     * 返回角色名称
     */
    public String getPartName(String pPk) {
        String p_name = null;
        try {
            con = new SqlData();
            String sql = "SELECT p_name FROM u_part_info WHERE p_pk=" + pPk;
            ResultSet rs = con.query(sql);
            while (rs.next()) {
                p_name = rs.getString("p_name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return p_name;
    }

    /**
     * 返回角色ID
     */
    public int getPartPk(String pName) {
        try {
            con = new SqlData();
            String sql = "SELECT p_pk FROM u_part_info WHERE p_name='" + pName + "'";
            ResultSet rs = con.query(sql);
            int p_pk = 0;
            if (rs.next()) {
                p_pk = rs.getInt("p_pk");
            }
            return p_pk;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return 0;
    }


    /**
     * 返回角色ID
     */
    public int getPartuPk(int pPk) {
        try {
            con = new SqlData();
            String sql = "SELECT u_pk FROM u_part_info WHERE p_pk='" + pPk + "'";
            ResultSet rs = con.query(sql);
            int u_pk = 0;
            if (rs.next()) {
                u_pk = rs.getInt("u_pk");
            }
            return u_pk;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return 0;
    }


    /**
     * 返回角色所在地图
     */
    public int getPartMap(int pPk) {
        try {
            con = new SqlData();
            String sql = "SELECT p_map FROM u_part_info WHERE p_pk=" + pPk;
            ResultSet rs = con.query(sql);
            int mapid = 0;
            if (rs.next()) {
                mapid = rs.getInt("p_map");
            }
            return mapid;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return 0;
    }


    /**
     * 查询玩家的用户名是否违反取名规则, 如果违反了规则, 返回true
     *
     * @param name
     * @return
     */
    public boolean getForbidName(String name) {
        boolean flag = false;
        String sql = "SELECT COUNT(1) AS num FROM jy_forbid_name WHERE str LIKE '%" + StringUtil.gbToISO(name) + "%'";
        try {
            con1 = new JyGameDB();
            ResultSet rs = con1.query(sql);
            if (rs.next()) {
                int i = rs.getInt("num");
                if (i != 0) {
                    flag = true;
                }
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con1.close();
        }
        return flag;
    }


    /**
     * 得到门派55级信息
     *
     * @param list
     * @param i
     */
    public void getMenPaiInfo(List<String> list, int menpai) {
        String sqlString = "";
        if (menpai == 0) { // 0是明教
            sqlString = "SELECT SUM(g_exp), SUM(g_next_exp), SUM(g_HP), SUM(g_MP), SUM(g_gj), SUM(g_fy) FROM u_grow_info " + "where g_pk IN (1,2,3,4,5,6,7,8,9) or (g_pk > 60 AND g_pk < 107)";
        } else if (menpai == 1) { // 1是丐帮
            sqlString = "SELECT SUM(g_exp), SUM(g_next_exp), SUM(g_HP), SUM(g_MP), SUM(g_gj), SUM(g_fy) FROM u_grow_info " + "where g_pk IN (1,2,3,4,5,6,7,8,9) or (g_pk > 111 AND g_pk < 158)";
        } else if (menpai == 2) { // 2是少林
            sqlString = "SELECT SUM(g_exp), SUM(g_next_exp), SUM(g_HP), SUM(g_MP), SUM(g_gj), SUM(g_fy) FROM u_grow_info " + "where g_pk < 51";
        }

        try {
            con1 = new JyGameDB();
            ResultSet rs = con1.query(sqlString);
            if (rs.next()) {
                list.add(rs.getInt("SUM(g_exp)") + "");
                list.add(rs.getInt("SUM(g_next_exp)") + "");
                list.add(rs.getInt("SUM(g_HP)") + "");

                list.add(rs.getInt("SUM(g_MP)") + "");
                list.add(rs.getInt("SUM(g_gj)") + "");
                list.add(rs.getInt("SUM(g_fy)") + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con1.close();
        }

    }


    /**
     * 添加除了宠物捕捉和60级技能以外的所有60级以下技能
     *
     * @param p_pk
     * @param menpai
     */
    public void addSkillInfo(int p_pk, int menpai) {
        String sql = "";
        if (menpai == 0) {
            sql = "INSERT INTO u_skill_info VALUES (null," + p_pk + ",13,'明教剑法(精通)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,310)," + "(null," + p_pk + ",25,'疯魔剑法(精通)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,311)," + "(null," + p_pk + ",34,'求全诀(精通)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,320)," + "(null," + p_pk + ",43,'圣火初动(精通)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,321)," + "(null," + p_pk + ",4,'野球拳(精通)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,100)," + "(null," + p_pk + ",16,'向阳功',1000,now(),now(),0,0,0,0,0,0,0,0,0,0,111113)," + "(null," + p_pk + ",47,'越女心法',1000,now(),now(),0,0,0,0,0,0,0,0,0,0,111116)," + "(null," + p_pk + "152,'蛤蟆功',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,0)";
        } else if (menpai == 1) {
            sql = "INSERT INTO u_skill_info values (null," + p_pk + ",10,'丐帮棍法(精通)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,210)," + "(null," + p_pk + ",22,'驱蛇棍法(精通)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,211)," + "(null," + p_pk + ",31,'棒打狗头(精通)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,220)," + "(null," + p_pk + ",40,'亢龙有悔(精通)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,221)," + "(null," + p_pk + ",4,'野球拳(精通)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,100)," + "(null," + p_pk + ",15,'莲花功',1000,now(),now(),0,0,0,0,0,0,0,0,0,0,111112)," + "(null," + p_pk + ",47,'越女心法',1000,now(),now(),0,0,0,0,0,0,0,0,0,0,111116)'" + "(null," + p_pk + "152,'蛤蟆功',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,0)";
        } else if (menpai == 2) {
            sql = "INSERT INTO u_skill_info values (null," + p_pk + ",7,'少林刀法(精通)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,110)," + "(null," + p_pk + ",19,'降魔刀法(精通)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,111)," + "(null," + p_pk + ",28,'穿云势(精通)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,120)," + "(null," + p_pk + ",36,'佛光初现(精通)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,121)," + "(null," + p_pk + ",4,'野球拳(精通)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,100)," + "(null," + p_pk + ",14,'袈裟功',1000,now(),now(),0,0,0,0,0,0,0,0,0,0,111111)," + "(null," + p_pk + ",47,'越女心法',1000,now(),now(),0,0,0,0,0,0,0,0,0,0,111116)," + "(null," + p_pk + "152,'蛤蟆功',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,0)";
        }

        try {
            con = new SqlData();
            con.update(sql);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }

    }

    public String[] getName(String p_pk) {
        String[] ss = new String[2];
        try {
            con = new SqlData();
            String sql = "SELECT u.p_name,u.p_sex FROM u_part_info u WHERE u.p_pk = " + p_pk;
            ResultSet rs = con.query(sql);
            if (rs.next()) {
                ss[0] = rs.getString("p_name");
                ss[1] = rs.getInt("p_sex") + "";
            }
            return ss;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return null;

    }

    public void updateTe_level(Object p_pk) {
        if (p_pk != null) {
            String sql = "UPDATE u_part_info u  SET u.te_level  = u.te_level  +1 WHERE u.p_pk = " + p_pk;
            try {
                con = new SqlData();
                con.update(sql);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                con.close();
            }
        }
    }

    public void updateMoney(Object p_pk, int addMoney) {
        if (p_pk != null) {
            String sql = "UPDATE u_part_info u  SET u.p_copper  = u.p_copper  + " + addMoney + " WHERE u.p_pk = " + p_pk;
            try {
                con = new SqlData();
                con.update(sql);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                con.close();
            }
        }
    }
}
