package com.ben.guaji.dao;

import com.ben.guaji.vo.GoodVo;
import com.ben.guaji.vo.GuaJiConstant;
import com.ben.guaji.vo.GuajiVo;
import com.ben.shitu.model.DateUtil;
import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.npc.NpcVO;
import com.ls.ben.vo.info.npc.NpcdropVO;
import com.ls.pub.db.DBConnection;
import com.ls.web.service.room.RoomService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class GuajiDao extends DaoBase {
    public int findNpcByLevel(int level) {
        int i = 0;
        String sql = "SELECT m.scene_id "
                + " FROM map m1, scene s, npcrefurbish m, npc n "
                + " WHERE s.scene_mapqy = m1.map_ID  "
                + " AND m1.map_type =2 "
                + " AND s.scene_id = m.scene_id "
                + " AND m.npc_id = n.npc_ID "
                + " AND n.npc_Level <= " + level
                + " AND s.scene_limit not like '%" + RoomService.NOT_CARRY_IN + "%' AND s.scene_ID not in (" + GuaJiConstant.CAN_NOT_GUAJI + ") "
                + " ORDER BY n.npc_Level DESC  "
                + " LIMIT 0 , 1";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            logger.debug(sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                i = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                dbConn.closeConn();
                return i;
            }
        }
    }

    public NpcVO findSceneOgreBySceneId(Object scene_id, int roleLevel) {
        List<NpcVO> list = new ArrayList<NpcVO>();
        if (null == scene_id) {
            return null;
        } else {
            String sql = "SELECT n.* FROM `npcrefurbish` m, npc n "
                    + " WHERE m.scene_id = " + scene_id
                    + " AND m.npc_id = n.npc_ID "
                    + " AND n.npc_level <= " + roleLevel
                    + " ORDER BY m.refurbish_probability DESC";
            DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
            conn = dbConn.getConn();
            try {
                logger.debug(sql);
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                list = get(rs);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    rs.close();
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    dbConn.closeConn();
                    return list == null ? null : list.size() > 0 ? list.get(0) : null;
                }
            }

        }

    }

    private List<NpcVO> get(ResultSet rs) throws java.sql.SQLException {
        List<NpcVO> list = new ArrayList<NpcVO>();
        if (rs != null) {
            while (rs.next()) {
                NpcVO npc = new NpcVO();
                npc.setNpcID(rs.getInt("npc_ID"));
                npc.setNpcName(rs.getString("npc_Name"));
                npc.setNpcHP(rs.getInt("npc_HP"));
                npc.setDefenceDa(rs.getInt("npc_defence_da"));
                npc.setDefenceXiao(rs.getInt("npc_defence_xiao"));
                npc.setJinFy(rs.getInt("npc_jin_fy"));
                npc.setMuFy(rs.getInt("npc_mu_fy"));
                npc.setShuiFy(rs.getInt("npc_shui_fy"));
                npc.setHuoFy(rs.getInt("npc_huo_fy"));
                npc.setTuFy(rs.getInt("npc_tu_fy"));
                npc.setDrop(rs.getInt("npc_drop"));
                npc.setLevel(rs.getInt("npc_Level"));
                npc.setExp(rs.getInt("npc_EXP"));
                npc.setMoney(rs.getString("npc_money"));
                npc.setNpcRefurbishTime(rs.getInt("npc_refurbish_time"));
                npc.setTake(rs.getInt("npc_take"));
                list.add(npc);
            }
        }
        return list;
    }

    /**
     * 找到NPC的掉落物 npc掉落查询，限制最多掉7个，按概率降幂和随机排序
     *
     * @param npc_ID
     * @return
     */
    public List<NpcdropVO> getNpcdropsByNpcID(int npc_ID, int start, int count) {
        ArrayList<NpcdropVO> list = new ArrayList<NpcdropVO>();
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            String sql = "SELECT * FROM `npcdrop` n WHERE n.npc_ID=" + npc_ID + " GROUP BY n.goods_id";
            if (count != 0) {
                sql += "LIMIT " + start + "," + count;
            }
            logger.debug(sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            NpcdropVO vo = null;
            while (rs.next()) {
                vo = new NpcdropVO();
                vo.setNpcdropID(rs.getInt("npcdrop_ID"));
                vo.setNpcID(rs.getInt("npc_ID"));
                vo.setGoodsType(rs.getInt("goods_type"));
                vo.setGoodsId(rs.getInt("goods_id"));
                vo.setGoodsName(rs.getString("goods_name"));
                vo.setNpcdropProbability(rs.getInt("npcdrop_probability"));
                vo.setNpcdropLuck(rs.getInt("npcdrop_luck"));
                vo.setNpcDropNumStr(rs.getString("npcdrop_num"));
                vo.setNpcDropImprot(rs.getInt("npcdrop_importance"));
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

    public int GetNpcJilv(Object npc_id) throws SQLException {
        int count = 0;
        if (npc_id != null) {
            String sql = "SELECT sum(n.npcdrop_probability) FROM `npcdrop` n WHERE n.npc_ID = " + npc_id;
            DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
            conn = dbConn.getConn();
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    count = rs.getInt(1);
                }
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conn.close();
            }
        }
        return count;
    }

    public List<NpcdropVO> getNpcdropsByNpcIDAndGood_id(int npc_id, String good_id) {
        ArrayList<NpcdropVO> list = new ArrayList<NpcdropVO>();
        if (good_id == null || "".equals(good_id.trim())) {
            return list;
        }
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            String sql = "SELECT * FROM npcdrop n where n.npc_ID=" + npc_id
                    + " and n.goods_id in (" + good_id
                    + ") group by n.goods_id";
            logger.debug(sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            NpcdropVO vo = null;
            while (rs.next()) {
                vo = new NpcdropVO();
                vo.setNpcdropID(rs.getInt("npcdrop_ID"));
                vo.setNpcID(rs.getInt("npc_ID"));
                vo.setGoodsType(rs.getInt("goods_type"));
                vo.setGoodsId(rs.getInt("goods_id"));
                vo.setGoodsName(rs.getString("goods_name"));
                vo.setNpcdropProbability(rs.getInt("npcdrop_probability"));
                vo.setNpcdropLuck(rs.getInt("npcdrop_luck"));
                vo.setNpcDropNumStr(rs.getString("npcdrop_num"));
                vo.setNpcDropImprot(rs.getInt("npcdrop_importance"));
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

    public void addAuto(GuajiVo guajiVo) throws SQLException {
        if (guajiVo != null) {
            // String sql = "insert into
            // auto(p_pk,ogre,time,begin_time,operate,level,guaji_type)
            // values(?,?,?,now(),?,?,?)";
            DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
            conn = dbConn.getConn();
            try {
                StringBuffer ope = new StringBuffer();
                Set<GoodVo> set = guajiVo.getGood();
                if (set != null) {
                    for (GoodVo gv : set) {
                        ope.append(gv.getGood_id() + "*" + gv.getGood_type()
                                + "+");
                    }
                }
                String sql = "INSERT INTO auto(p_pk,ogre,time,begin_time,operate,level,guaji_type) values("
                        + guajiVo.getP_pk()
                        + ","
                        + guajiVo.getNpc_id()
                        + ","
                        + guajiVo.getTime()
                        + ",'" + DateUtil.getDateSecondFormat() + "','"
                        + ope
                        + "',"
                        + guajiVo.getLevel()
                        + ","
                        + guajiVo.getGuaji_type() + ")";
                // ps = conn.prepareStatement(sql);
                // ps.setObject(1, guajiVo.getP_pk());
                // ps.setObject(2, guajiVo.getNpc_id());
                // ps.setObject(3, guajiVo.getTime());
                // ps.setObject(4, ope.toString());
                // ps.setObject(5, guajiVo.getLevel());
                // ps.setObject(6, guajiVo.getGuaji_type());
                // ps.execute(sql);
                // ps.close();
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conn.close();
            }
        }
    }

    public int findIsGuaji(Object p_pk) throws SQLException {
        int count = 0;
        if (p_pk != null) {
            String sql = "SELECT count(*) from auto a where a.p_pk = "
                    + p_pk
                    + " and (a.end_time = '' or a.end_time is null or a.end_time = null)";
            DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
            conn = dbConn.getConn();
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    count = rs.getInt(1);
                }
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conn.close();
            }
        }
        return count;
    }

    public GuajiVo findByPpk(Object p_pk) throws SQLException {
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        List<GuajiVo> list = new ArrayList<GuajiVo>();
        String sql = "SELECT * FROM auto a where a.p_pk = "
                + p_pk
                + " and (a.end_time = '' or a.end_time is null or a.end_time = null)";
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            list = getGuajiVo(rs);
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            return list == null || list.size() == 0 ? null : list.get(0);
        }
    }

    private List<GuajiVo> getGuajiVo(ResultSet rs) throws SQLException {
        List<GuajiVo> list = new ArrayList<GuajiVo>();
        if (rs != null) {
            while (rs.next()) {
                GuajiVo gv = new GuajiVo();
                gv.setTime(rs.getInt("time"));
                gv.setGuaji_type(rs.getInt("guaji_type"));
                gv.setLevel(rs.getInt("level"));
                gv.setNpc_id(rs.getInt("ogre"));
                gv.setId(rs.getLong("id"));
                gv.setStart_time(rs.getString("begin_time"));
                String s = rs.getString("operate");
                gv.setGood(getGoodVo(s));
                list.add(gv);
            }
        }
        return list;
    }

    private Set<GoodVo> getGoodVo(String s) {
        Set<GoodVo> set = new TreeSet<GoodVo>();
        if (s != null && !"".equals(s.trim())) {
            String[] ss = s.split("\\+");
            if (ss != null) {
                for (String sss : ss) {
                    String[] ssss = sss.split("\\*");
                    if (ssss != null && ssss.length == 2) {
                        GoodVo gv = new GoodVo();
                        gv.setGood_id(Integer.parseInt(ssss[0].trim()));
                        gv.setGood_type(Integer.parseInt(ssss[1].trim()));
                        set.add(gv);
                    }
                }
            }
        }
        return set;
    }

    public void updateEndTime(long id) {
        String sql = "update auto a set a.end_time = now() where a.id = " + id;
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            logger.debug(sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                dbConn.closeConn();
            }
        }
    }

    public int findTask(int id) throws SQLException {
        int i = 0;
        String sql = "SELECT n.npcdrop_taskid from npcdrop n where n.npcdrop_ID = " + id;
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                i = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            return i;
        }
    }

}
