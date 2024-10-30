package com.ben.dao.friend;

import com.ben.vo.friend.FriendVO;
import com.ls.web.service.rank.RankService;
import com.pub.db.mysql.SqlData;
import com.web.jieyi.util.Constant;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能:好友
 *
 * @author 侯浩军 9:24:55 AM
 */
public class FriendDAO {
    SqlData con;

    /**
     * 加好友
     */
    public void friendAdd(int pPk, String pByPk, String pByName, int online, String time) {
        try {
            con = new SqlData();
            String sql = "INSERT INTO `u_friend` (p_pk, fd_pk, fd_name, fd_online, create_time) VALUES('" + pPk + "', '" + pByPk + "', '" + pByName + "', '" + online + "', '" + time + "')";
            con.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    /**
     * 查询是否已经有该好友了
     */
    public boolean whetherfriend(int pPk, String pByPk) {
        try {
            con = new SqlData();
            String sql = "SELECT * FROM `u_friend` WHERE p_pk = '" + pPk + "' AND fd_pk = '" + pByPk + "'";
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
     * 好友List
     */
    public List<FriendVO> getFriendListAll(int pPk, int pageno, int perpage) {
        try {
            String sql = null;
            con = new SqlData();
            sql = "SELECT upf.p_map, f_pk, uf.p_pk, uf.dear, uf.love_dear, uf.relation, uf.exp_share, fd_pk, fd_name, uf.fd_online, uf.create_time, uli.login_state FROM u_friend AS uf, u_part_info AS upf, u_login_info AS uli WHERE uf.fd_pk = upf.p_pk AND  upf.u_pk = uli.u_pk AND uf.p_pk =" + pPk + " ORDER BY uli.login_state DESC";
            if (perpage != 0) {
                sql += " LIMIT " + pageno + ", " + perpage;
            }
            ResultSet rs = con.query(sql);
            List<FriendVO> list = new ArrayList<FriendVO>();
            while (rs.next()) {
                FriendVO vo = new FriendVO();
                vo.setFPk(rs.getInt("f_pk"));
                vo.setPPk(rs.getInt("p_pk"));
                vo.setFdPk(rs.getInt("fd_pk"));
                vo.setFdName(rs.getString("fd_name"));
                vo.setFOnline(rs.getInt("fd_online"));
                vo.setCreateTime(rs.getString("create_time"));
                vo.setPMap(rs.getInt("p_map"));
                vo.setDear(rs.getInt("dear"));
                vo.setRelation(rs.getInt("relation"));
                vo.setExpShare(rs.getInt("exp_share"));
                vo.setLove_dear(rs.getInt("love_dear"));
                vo.setLogin_state(rs.getInt("login_state"));
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
     * 好友List
     */
    public List<FriendVO> getFriendListAll(int pPk) {
        try {
            String sql = null;
            con = new SqlData();

            sql = "SELECT upf.p_map, f_pk, uf.p_pk, uf.dear, uf.love_dear, uf.relation, uf.exp_share, fd_pk, fd_name, uf.fd_online, uf.create_time FROM u_friend uf JOIN u_part_info upf ON uf.p_pk = upf.p_pk WHERE uf.fd_pk =" + pPk + " ORDER BY uf.fd_online DESC";

            // //System.out.println(sql);
            ResultSet rs = con.query(sql);
            List<FriendVO> list = new ArrayList<FriendVO>();
            while (rs.next()) {
                FriendVO vo = new FriendVO();
                vo.setFPk(rs.getInt("f_pk"));
                vo.setPPk(rs.getInt("p_pk"));
                vo.setFdPk(rs.getInt("fd_pk"));
                vo.setFdName(rs.getString("fd_name"));
                vo.setFOnline(rs.getInt("fd_online"));
                vo.setCreateTime(rs.getString("create_time"));
                vo.setPMap(rs.getInt("p_map"));
                vo.setDear(rs.getInt("dear"));
                vo.setRelation(rs.getInt("relation"));
                vo.setExpShare(rs.getInt("exp_share"));
                vo.setLove_dear(rs.getInt("love_dear"));
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
     * 好友View
     */
    public FriendVO getFriendView(int pPk, String pByPk) {
        try {
            con = new SqlData();
            String sql = "SELECT * FROM u_friend WHERE p_pk = '" + pPk + "' AND fd_pk = '" + pByPk + "'";
            ResultSet rs = con.query(sql);
            FriendVO vo = null;
            if (rs.next()) {
                vo = new FriendVO();
                vo.setFPk(rs.getInt("f_pk"));
                vo.setPPk(rs.getInt("p_pk"));
                vo.setFdPk(rs.getInt("fd_pk"));
                vo.setFdName(rs.getString("fd_name"));
                vo.setCreateTime(rs.getString("create_time"));
                vo.setDear(rs.getInt("dear"));
                vo.setRelation(rs.getInt("relation"));
                vo.setExpShare(rs.getInt("exp_share"));
                vo.setLove_dear(rs.getInt("love_dear"));
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
     * 删除好友
     */
    public void getDeleteFriend(int pPk, String pByPk) {
        try {
            con = new SqlData();
            String sql = "DELETE FROM u_friend WHERE p_pk = '" + pPk + "' AND fd_pk = '" + pByPk + "'";
            con.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    /**
     * 更新在线还是不在线
     *
     * @param pPk
     * @param fdOnline 0 不在线 1在线
     */
    public void updateFriendOnline(int pPk, int fdOnline) {
        try {
            con = new SqlData();
            String sql = "UPDATE u_friend SET fd_online='" + fdOnline + "' WHERE fd_pk = " + pPk;
            con.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }

    }

    /**
     * 在线好友List
     */
    public List<FriendVO> getFriendListAllOnline(int pPk, int pageno, int perpage) {
        try {
            String sql = null;
            con = new SqlData();
            if (perpage != 0) {
                sql = "SELECT upf.p_map, f_pk, uf.dear, uf.love_dear, uf.relation, uf.exp_share, uf.p_pk, fd_pk, fd_name, uf.fd_online, uf.create_time FROM u_friend uf JOIN u_part_info upf ON uf.fd_pk = upf.p_pk WHERE uf.p_pk =" + pPk + " AND uf.fd_online = 1 LIMIT " + pageno + ", " + perpage;
            } else {
                sql = "SELECT upf.p_map, f_pk, uf.dear, uf.love_dear, uf.relation, uf.exp_share, uf.p_pk, fd_pk, fd_name, uf.fd_online, uf.create_time FROM u_friend uf JOIN u_part_info upf ON uf.fd_pk = upf.p_pk WHERE uf.p_pk =" + pPk + " AND uf.fd_online = 1";
            }
            ResultSet rs = con.query(sql);
            List<FriendVO> list = new ArrayList<FriendVO>();
            while (rs.next()) {
                FriendVO vo = new FriendVO();
                vo.setFPk(rs.getInt("f_pk"));
                vo.setPPk(rs.getInt("p_pk"));
                vo.setFdPk(rs.getInt("fd_pk"));
                vo.setFdName(rs.getString("fd_name"));
                vo.setFOnline(rs.getInt("fd_online"));
                vo.setCreateTime(rs.getString("create_time"));
                vo.setPMap(rs.getInt("p_map"));
                vo.setDear(rs.getInt("dear"));
                vo.setRelation(rs.getInt("relation"));
                vo.setExpShare(rs.getInt("exp_share"));
                vo.setLove_dear(rs.getInt("love_dear"));
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
     * 可以结义好友List
     */
    public List<FriendVO> getFriendListAll1(int pPk, int pageno, int perpage, int relation) {
        List<FriendVO> list = new ArrayList<FriendVO>();
        try {
            String sql = null;
            con = new SqlData();
            if (perpage != 0) {
                sql = "SELECT upf.p_map, uf.dear, uf.love_dear, uf.relation, uf.exp_share, f_pk, uf.p_pk, fd_pk, fd_name, uf.fd_online, uf.create_time, uf.tim FROM u_friend uf JOIN u_part_info upf ON uf.fd_pk = upf.p_pk WHERE uf.p_pk =" + pPk + " AND uf.relation = " + relation + " ORDER BY uf.fd_online DESC LIMIT " + pageno + ", " + perpage;
            } else {
                sql = "SELECT upf.p_map, uf.dear, uf.love_dear, uf.relation, uf.exp_share, f_pk, uf.p_pk, fd_pk, fd_name, uf.fd_online, uf.create_time, uf.tim FROM u_friend uf JOIN u_part_info upf ON uf.fd_pk = upf.p_pk WHERE uf.p_pk =" + pPk + " AND uf.relation = " + relation + " ORDER BY uf.fd_online DESC";
            }
            ResultSet rs = con.query(sql);

            list = get(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return list;
    }

    private List<FriendVO> get(ResultSet rs) throws java.sql.SQLException {
        List<FriendVO> list = new ArrayList<FriendVO>();
        if (rs != null) {
            while (rs.next()) {
                FriendVO vo = new FriendVO();
                vo.setFPk(rs.getInt("f_pk"));
                vo.setPPk(rs.getInt("p_pk"));
                vo.setFdPk(rs.getInt("fd_pk"));
                vo.setFdName(rs.getString("fd_name"));
                vo.setCreateTime(rs.getString("create_time"));
                vo.setDear(rs.getInt("dear"));
                vo.setRelation(rs.getInt("relation"));
                vo.setExpShare(rs.getInt("exp_share"));
                vo.setLove_dear(rs.getInt("love_dear"));
                vo.setTim(rs.getTimestamp("tim"));
                list.add(vo);
            }
        }
        return list;
    }

    public void jieyi(String pPk, String pByPk, int relation) {

        try {
            con = new SqlData();
            String add = "";
            if (relation == 0) {
                add = ", u.dear = 0, u.exp_share = 0, u.love_dear = 0 ";
            }
            if (relation == 2) {
                add = ", u.love_dear = " + Constant.INIT_LOVE_DEAR;
            }
            String sql = "UPDATE u_friend u SET u.tim = now(), u.relation = " + relation + add + " WHERE u.p_pk = " + pPk.trim() + " AND u.fd_pk = " + pByPk.trim();
            con.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    /**
     * 查看自己是否已结婚
     */
    public List<FriendVO> isMerry(String pPk) {
        List<FriendVO> list = new ArrayList<FriendVO>();
        try {

            con = new SqlData();
            String sql = "SELECT * FROM u_friend u WHERE u.p_pk = '" + pPk.trim() + "' AND u.relation IN  (2, 3) ";
            ResultSet rs = con.query(sql);
            list = get(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return list;
    }

    /**
     * 可以结婚的好友
     *
     * @param pPk
     * @param pageno
     * @param perpage
     * @return
     */
    public List<FriendVO> getCanMerry(int pPk, int gender, int pageno, int perpage) {
        List<FriendVO> list = new ArrayList<FriendVO>();
        try {
            String sql = null;
            con = new SqlData();
            if (perpage != 0) {
                sql = "SELECT upf.p_map, uf.dear, uf.love_dear, uf.relation, uf.exp_share, f_pk, uf.p_pk, fd_pk, fd_name, uf.fd_online, uf.create_time, uf.tim FROM u_friend uf, u_part_info upf WHERE uf.p_pk = " + pPk + " AND uf.relation = 0 AND uf.fd_pk = upf.p_pk AND upf.p_sex != " + gender + " ORDER BY uf.fd_online DESC LIMIT " + pageno + ", " + perpage;
            } else {
                sql = "SELECT upf.p_map, uf.dear, uf.love_dear, uf.relation, uf.exp_share, f_pk, uf.p_pk, fd_pk, fd_name, uf.fd_online, uf.create_time, uf.tim FROM u_friend uf, u_part_info upf WHERE uf.p_pk = " + pPk + " AND uf.relation = 0 AND  uf.fd_pk = upf.p_pk AND upf.p_sex != " + gender;
            }
            ResultSet rs = con.query(sql);

            list = get(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return list;
    }

    /**
     * 增加亲密度
     *
     * @param p_pk    p_pk
     * @param fd_pk   fd_pk
     * @param dear    增加或者减少多少，带有符号
     * @param f_name  f_name
     * @param fd_name fd_name
     */
    public void addDear(String p_pk, String fd_pk, String dear, String f_name, String fd_name) {
        if (getFriendView(Integer.parseInt(p_pk.trim()), fd_pk) != null && getFriendView(Integer.parseInt(fd_pk.trim()), p_pk) != null) {
            String sql = "UPDATE u_friend u SET u.dear = u.dear " + dear + " WHERE u.p_pk = " + p_pk + " AND u.fd_pk = " + fd_pk;
            String sql1 = "UPDATE u_friend u SET u.dear = u.dear " + dear + " WHERE u.p_pk = " + fd_pk + " AND u.fd_pk = " + p_pk;
            new RankService().updateYiqi(p_pk, 1, fd_name);
            new RankService().updateYiqi(fd_pk, 1, f_name);
            try {
                con = new SqlData();
                con.update(sql);
                con.update(sql1);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                con.close();
            }
        }
    }

    public int jieyiCount(String p_pk, String ids, int relation) {
        String sql = "SELECT COUNT(*) AS ccc FROM u_friend u WHERE u.p_pk = " + p_pk + " AND u.fd_pk IN (" + ids.trim() + ") AND u.relation IN (1, 2) ";
        try {
            con = new SqlData();
            ResultSet rs = con.query(sql);
            while (rs.next()) {
                return rs.getInt("ccc");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return 0;
    }

    public List<FriendVO> findFriends(String p_pk, String fp_pks) {
        List<FriendVO> list = new ArrayList<FriendVO>();
        if (p_pk == null || "".equals(p_pk.trim()) || fp_pks == null || fp_pks.trim().equals("")) {
            return list;
        }
        String sql = "SELECT * FROM u_friend u WHERE u.p_pk = " + p_pk + " AND u.fd_pk IN (" + ((fp_pks.lastIndexOf(", ") + 1 == fp_pks.length()) ? fp_pks.substring(0, fp_pks.lastIndexOf(", ")) : fp_pks) + ")";
        try {
            con = new SqlData();
            ResultSet rs = con.query(sql);
            list = get(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return list;
    }

    // 共享经验
    public void setExpShare(int p_pk, int fd_pk, int exp) {
        String sql = "UPDATE u_friend u SET u.exp_share = " + exp + " WHERE u.p_pk = " + p_pk + " AND u.fd_pk = " + fd_pk;
        try {
            con = new SqlData();
            con.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    // 查看可以获取经验的好友列表
    public List<FriendVO> findCanGetExp(int p_pk, int relation, int start, int count) {
        String sql = "";
        if (count != 0) {
            sql = "SELECT * FROM u_friend u WHERE u.p_pk = " + p_pk + " AND u.relation = " + relation + " AND u.exp_share > 0 LIMIT " + start + ", " + count;
        } else {
            sql = "SELECT * FROM u_friend u WHERE u.p_pk = " + p_pk + " AND u.relation = " + relation + " AND u.exp_share > 0 ";
        }
        List<FriendVO> list = new ArrayList<FriendVO>();
        try {
            con = new SqlData();
            ResultSet rs = con.query(sql);
            list = get(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return list;
    }

    // 领取经验
    public void getExp(int f_pk) {
        String sql = "UPDATE u_friend u SET u.exp_share = 0 WHERE u.f_pk = " + f_pk;
        try {
            con = new SqlData();
            con.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    public List<FriendVO> findLoveDear() {
        String sql = "SELECT * FROM `u_friend` u WHERE u.relation = 2 AND u.love_dear > 0";
        List<FriendVO> list = new ArrayList<FriendVO>();
        try {
            con = new SqlData();
            ResultSet rs = con.query(sql);
            list = get(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return list;
    }

    // 每十分钟减少1点爱情甜蜜度
    public void delLoveDear(int f_pk) {
        String sql = "UPDATE u_friend u SET u.love_dear = u.love_dear -1 WHERE u.f_pk = " + f_pk;
        try {
            con = new SqlData();
            con.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    public void addLoveDear(int p_pk, int fd_pk, int love_dear, String fd_name, String f_name) {
        if (love_dear > 0) {
            String sql = "UPDATE u_friend u SET u.love_dear = u.love_dear + " + love_dear + " WHERE u.p_pk = " + p_pk + " AND u.fd_pk = " + fd_pk;
            String sql1 = "UPDATE u_friend u SET u.love_dear = u.love_dear + " + love_dear + " WHERE u.p_pk = " + fd_pk + " AND u.fd_pk = " + p_pk;
            // 统计需要
            new RankService().updateDear(p_pk, love_dear, fd_name);
            // 统计需要
            new RankService().updateDear(fd_pk, love_dear, f_name);
            try {
                con = new SqlData();
                con.update(sql);
                con.update(sql1);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                con.close();
            }
        }
    }

    public int isFuQi(int p_pk, String fp_pks, int relation) {
        int i = 0;
        if (fp_pks != null && !"".equals(fp_pks.trim())) {
            String sql = "SELECT COUNT(*) AS coo FROM u_friend u WHERE u.relation = " + relation + " AND u.p_pk = " + p_pk + " AND u.fd_pk IN (" + ((fp_pks.lastIndexOf(", ") + 1 == fp_pks.length()) ? fp_pks.substring(0, fp_pks.lastIndexOf(", ")) : fp_pks) + ")";
            try {
                con = new SqlData();
                ResultSet rs = con.query(sql);
                while (rs.next()) {
                    i = rs.getInt("coo");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                con.close();
            }
        }
        return i;
    }

    public int findLove_dear(int p_pk) {
        String sql = "SELECT u.love_dear FROM u_friend u WHERE u.p_pk = " + p_pk + " AND u.relation = 2";
        int i = 0;
        try {
            con = new SqlData();
            ResultSet rs = con.query(sql);
            while (rs.next()) {
                i = rs.getInt("love_dear");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return i;
    }

    public FriendVO findById(int id) {
        try {
            con = new SqlData();
            String sql = "SELECT * FROM u_friend u WHERE u.f_pk = " + id;
            ResultSet rs = con.query(sql);
            FriendVO vo = null;
            if (rs.next()) {
                vo = new FriendVO();
                vo.setFPk(rs.getInt("f_pk"));
                vo.setPPk(rs.getInt("p_pk"));
                vo.setFdPk(rs.getInt("fd_pk"));
                vo.setFdName(rs.getString("fd_name"));
                vo.setCreateTime(rs.getString("create_time"));
                vo.setDear(rs.getInt("dear"));
                vo.setRelation(rs.getInt("relation"));
                vo.setExpShare(rs.getInt("exp_share"));
                vo.setLove_dear(rs.getInt("love_dear"));
            }
            return vo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return null;
    }

    // 增加亲密度
    public void addLove(Object p_pk, Object fd_pk, int addlovel) {
        try {
            con = new SqlData();
            String sql = "UPDATE u_friend u SET u.dear = u.dear + " + addlovel + " WHERE u.p_pk = " + p_pk + " AND u.fd_pk = " + fd_pk;
            String sql1 = "UPDATE u_friend u SET u.dear = u.dear + " + addlovel + " WHERE u.p_pk = " + fd_pk + " AND u.fd_pk = " + p_pk;
            con.update(sql);
            con.update(sql1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    // 查看可以获取经验的好友列表
    public List<FriendVO> jieyi(Object p_pk, int relation) {

        List<FriendVO> list = new ArrayList<FriendVO>();
        if (p_pk == null) {
            return list;
        }
        String sql = "";
        sql = "SELECT * FROM u_friend u WHERE u.p_pk = " + p_pk + " AND u.relation = " + relation;
        try {
            con = new SqlData();
            ResultSet rs = con.query(sql);
            list = get(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return list;
    }

    // 排行榜
    public List<FriendVO> paihang(int relation) {
        List<FriendVO> list = new ArrayList<FriendVO>();
        String sql = "";
        if (relation == 1) {
            sql = "SELECT * FROM `u_friend` u WHERE u.relation = 1 ORDER BY dear DESC, tim ASC LIMIT 20";
        } else {
            sql = "SELECT * FROM `u_friend` u WHERE u.relation = 2 ORDER BY love_dear DESC, tim ASC LIMIT 20";
        }
        try {
            con = new SqlData();
            ResultSet rs = con.query(sql);
            list = get(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return list;
    }

    // 自己的排名
    public int getOwn(int p_pk, int relation) {
        String sq = "";
        String sql = "";
        String sql1 = "";
        if (relation == 1) {
            sq = "SELECT MAX(u.dear) FROM u_friend u WHERE u.relation = 1 AND u.p_pk = " + p_pk;
            sql = "SELECT COUNT(*) FROM u_friend u WHERE u.relation = 1 AND u.dear > (SELECT MAX(a.dear) FROM u_friend a WHERE a.p_pk =" + p_pk + " )";
            sql1 = "SELECT COUNT(*) FROM u_friend u WHERE u.relation = 1 AND " + " u.dear = (SELECT a.dear FROM u_friend a WHERE a.p_pk = " + p_pk + " AND a.relation = 1 ORDER BY a.dear DESC LIMIT 1 )  " + " AND u.tim <=  (SELECT b.tim FROM u_friend b WHERE b.p_pk = " + p_pk + " AND b.relation = 1 ORDER BY b.dear DESC LIMIT 1 )";
        } else {
            sq = "SELECT MAX(u.love_dear) FROM u_friend u WHERE u.relation = 2 AND u.p_pk = " + p_pk;
            sql = "SELECT COUNT(*) FROM u_friend u WHERE u.relation = 2 AND u.love_dear > (SELECT MAX(a.love_dear) FROM u_friend a WHERE a.p_pk =" + p_pk + " )";
            sql1 = "SELECT COUNT(*) FROM u_friend u WHERE u.relation = 2 AND " + " u.love_dear = (SELECT a.love_dear FROM u_friend a WHERE a.p_pk = " + p_pk + " AND a.relation = 2 ORDER BY a.love_dear DESC LIMIT 1 )  " + " AND u.tim <=  (SELECT b.tim FROM u_friend b WHERE b.p_pk = " + p_pk + " AND b.relation = 2 ORDER BY b.love_dear DESC LIMIT 1 )";

        }
        int i = 0;
        try {
            con = new SqlData();
            int j1 = 0;
            ResultSet r = con.query(sq);
            while (r.next()) {
                j1 = r.getInt(1);
            }
            if (j1 != 0) {
                ResultSet rs = con.query(sql);
                while (rs.next()) {
                    i = rs.getInt(1);
                }
                ResultSet rs1 = con.query(sql1);
                while (rs1.next()) {
                    int j = rs1.getInt(1);
                    i += j == 0 ? i == 0 ? 0 : 1 : j;
                }
                rs.close();
                rs1.close();
            }
            r.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return i;
    }

    /**
     * *******玩家删除角色的时候删除所有关联的好友信息******
     */
    public void removeFriendInfo(int ppk) {
        String sql = "DELETE FROM u_friend WHERE p_pk = " + ppk + " OR fd_pk = " + ppk;
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
