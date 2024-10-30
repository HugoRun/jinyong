package com.ls.ben.dao.info.partinfo;

import com.ls.ben.dao.BasicDaoSupport;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.model.organize.faction.Faction;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartInfoDao extends BasicDaoSupport<PartInfoVO> {
    public PartInfoDao() {
        super("u_part_info", DBConnection.GAME_USER_DB);
    }

    /**
     * 创建角色
     *
     * @param uPk            账号
     * @param pName
     * @param pSex
     * @param pUpHp          血量上限
     * @param pUpMp          法力上限
     * @param pGj
     * @param pFy
     * @param pXiaExperience
     * @param pCopper
     * @param pPks           PK开关
     * @param pDropMultiple  暴击率
     * @param pMap           初始化场景地址
     * @param pWrapContent   包裹上限
     * @param race           种族
     * @return
     */
    public int add(String uPk, String pName, String pSex, String pUpHp, String pUpMp, String pGj, String pFy, String pCopper, String pPks, String pDropMultiple, String pMap, String pWrapContent, int race) {
        int p_pk = -1;
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            String sql = "INSERT INTO u_part_info(u_pk,p_name,p_sex,p_up_hp,p_hp,p_up_mp,p_mp,p_gj,p_fy" + ",p_copper,p_pks,p_drop_multiple,p_map,p_wrap_content,p_race,create_time) " + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())";
            logger.debug(sql);
            ps = conn.prepareStatement(sql);

            ps.setInt(1, Integer.parseInt(uPk));
            ps.setString(2, pName);
            ps.setInt(3, Integer.parseInt(pSex));

            ps.setInt(4, Integer.parseInt(pUpHp));
            ps.setInt(5, Integer.parseInt(pUpHp));
            ps.setInt(6, Integer.parseInt(pUpHp));
            ps.setInt(7, Integer.parseInt(pUpHp));
            ps.setInt(8, Integer.parseInt(pGj));
            ps.setInt(9, Integer.parseInt(pFy));

            ps.setString(10, pCopper);

            ps.setInt(11, Integer.parseInt(pPks));
            ps.setDouble(12, Double.parseDouble(pDropMultiple));
            ps.setInt(13, Integer.parseInt(pMap));

            ps.setInt(14, Integer.parseInt(pWrapContent));

            ps.setInt(15, race);

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                p_pk = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return p_pk;
    }


    /**
     * 指定帮派成员职位的数量
     */
    public int getCurFJobNum(int fId, int fJob) {
        return super.getNumBySql("WHERE f_id=" + fId + " AND f_job=" + fJob);
    }

    /**
     * 帮派成员列表
     *
     * @param pName
     * @return
     */
    public QueryPage getPageListByFId(int f_id, int page_no) {
        String condition_sql = "WHERE f_id = " + f_id;
        String order_sql = "ORDER BY f_job desc,p_grade desc,f_contribute desc,f_jointime desc";
        return super.loadPageList(condition_sql, order_sql, page_no);
    }

    /**
     * 得到帮派队长id
     *
     * @return
     */
    public int getFactinLeader(int f_id) {
        int p_pk = -1;
        String sql = "SELECT p_pk FROM u_part_info WHERE f_id = " + f_id + " AND f_job=" + Faction.ZUZHANG;
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                p_pk = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            logger.debug(e.toString());
        } finally {
            dbConn.closeConn();
        }
        return p_pk;
    }

    /**
     * 帮派长老级别以上的成员列表
     *
     * @param pName
     * @return
     */
    public List<Integer> getUpZhanglaoListByFId(int f_id) {
        List<Integer> list = new ArrayList<Integer>();
        String sql = "SELECT p_pk FROM u_part_info WHERE f_id = " + f_id + " AND f_job >= " + Faction.ZHANGLAO;
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            logger.debug(e.toString());
        } finally {
            dbConn.closeConn();
        }
        return list;
    }

    /**
     * 帮派长老列表
     *
     * @param pName
     * @return
     */
    public QueryPage getPageZhanglaoListByFId(int f_id, int page_no) {
        String condition_sql = "WHERE f_id = " + f_id + " AND f_job=" + Faction.ZHANGLAO;
        String order_sql = "ORDER BY f_job desc,p_grade desc,f_contribute desc,f_jointime desc";
        return super.loadPageList(condition_sql, order_sql, page_no);
    }

    // 通过注册ID 去找角色 一个注册ID只用创建5个角色
    public int getByName(String pName) {
        int p_pk = -1;
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {

            String sql = "SELECT p_pk FROM u_part_info WHERE p_name='" + pName + "'";
            logger.debug(sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                p_pk = rs.getInt("p_pk");
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return p_pk;
    }


    // 通过注册ID 去找角色 一个注册ID只用创建5个角色
    public List<PartInfoVO> getPartTypeList(String uPk) {
        List<PartInfoVO> list = new ArrayList<PartInfoVO>();
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            String sql = "SELECT * FROM u_part_info WHERE u_pk = " + uPk + " AND delete_flag = 0";
            logger.debug(sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                PartInfoVO vo = new PartInfoVO();
                vo.setUPk(rs.getInt("u_pk"));
                vo.setPPk(rs.getInt("p_pk"));
                vo.setPName(rs.getString("p_name"));
                vo.setPSex(rs.getInt("p_sex"));
                vo.setPGrade(rs.getInt("p_grade"));
                vo.setPExperience(rs.getString("p_experience"));
                vo.setPMap(rs.getString("p_map"));
                vo.setDeleteFlag(rs.getInt("delete_flag"));
                vo.setDeleteTime(rs.getTimestamp("delete_time"));
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
     * 获得账号下 正在被删除的角色列表
     * 修改 确定删除的角色 删除标记从0改为1
     */
    public List<PartInfoVO> getDeleteStateRoles(String uPk) {
        List<PartInfoVO> list = new ArrayList<PartInfoVO>();
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            String sql = "SELECT p_pk,delete_time FROM u_part_info WHERE u_pk = " + uPk + " AND delete_flag=1";
            logger.debug(sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                PartInfoVO vo = new PartInfoVO();
                vo.setPPk(rs.getInt("p_pk"));
                vo.setDeleteTime(rs.getTimestamp("delete_time"));
                list.add(vo);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        logger.debug("partInfo.size=" + list.size());
        return list;

    }

    /**
     * 通过id 得到玩家信息
     *
     * @param p_pk
     * @return
     */
    public PartInfoVO getPartInfoByID(int p_pk) {
        PartInfoVO vo = null;
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {

            String sql = "SELECT * FROM u_part_info WHERE p_pk = " + p_pk;
            logger.debug(sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                vo = new PartInfoVO();
                vo.setUPk(rs.getInt("u_pk"));
                vo.setPPk(rs.getInt("p_pk"));
                vo.setPName(rs.getString("p_name"));
                vo.setPSex(rs.getInt("p_sex"));

                vo.setPUpHp(rs.getInt("p_up_hp"));
                vo.setPUpMp(rs.getInt("p_up_mp"));

                vo.setPHp(rs.getInt("p_hp"));
                vo.setPMp(rs.getInt("p_mp"));

                vo.setPGj(rs.getInt("p_gj"));
                vo.setPFy(rs.getInt("p_fy"));

                vo.setPGrade(rs.getInt("p_grade"));
                vo.setPExperience(rs.getString("p_experience"));

                vo.setPCopper(rs.getString("p_copper"));

                vo.setPPks(rs.getInt("p_pks"));
                vo.setPPkValue(rs.getInt("p_pk_value"));
                vo.setPkChangeTime(rs.getTimestamp("p_pk_changetime"));
                vo.setPMap(rs.getString("p_map"));

                vo.setPWrapContent(rs.getInt("p_wrap_content"));

                vo.setPSex(rs.getInt("p_sex"));
                vo.setPHarness(rs.getInt("p_harness"));
                vo.setDropMultiple(rs.getDouble("p_drop_multiple"));
                vo.setTe_level(rs.getInt("te_level"));
                vo.setChuangong(rs.getString("chuangong"));
                vo.setLast_shoutu_time(rs.getDate("last_shoutu_time"));
                vo.setPlayer_state_by_new(rs.getInt("player_state_by_new"));

                vo.setFId(rs.getInt("f_id"));
                vo.setFJob(rs.getInt("f_job"));
                vo.setFContribute(rs.getInt("f_contribute"));
                vo.setFTitle(rs.getString("f_title"));
                vo.setPRace(rs.getInt("p_race"));
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return vo;
    }


    /**
     * 设置角色的删除状态
     *
     * @param p_pk
     * @param delete_flag
     * @return
     */
    public int updateDeleteFlag(int p_pk, int delete_flag) {
        int result = -1;
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            String set_delete_time = "";
            if (delete_flag == 0)//如果时删除缓冲则，要设置delete_time为当前时间
            {
                set_delete_time = ",delete_time=now() ";
            }
            String sql = "UPDATE u_part_info SET delete_flag = " + delete_flag + set_delete_time + " WHERE p_pk = " + p_pk;
            logger.debug(sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return result;
    }


    /**
     * 得到角色名字
     *
     * @param p_pk
     */
    public String getNameByPpk(int p_pk) {
        String p_name = "";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            String sql = "SELECT p_name FROM u_part_info WHERE p_pk = " + p_pk;
            logger.debug("得到角色名字=" + sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                p_name = rs.getString("p_name");
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return p_name;
    }

    /**
     * * sceneStr是形如(111,112,113,115)类的scene_name集合
     * 得到在sceneStr中的所有p_pk集合
     *
     * @param sceneStr
     * @return
     */
    public List<Integer> getPPkListBySceneStr(String sceneStr) {
        List<Integer> list = new ArrayList<Integer>();
        String sql = "SELECT p_pk FROM u_part_info WHERE p_map IN " + sceneStr;

        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            logger.debug("得到在sceneStr中的所有p_pk集合=" + sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getInt("p_pk"));
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
     * 获得 在当前区域内 帮派数量最多的一个或几个
     *
     * @param list     是帮派ID的组合
     * @param sceneStr 是sceneID的组合,
     * @return
     */
    public List<int[]> getMostTongPersonInMap(List<Integer> list, String sceneStr) {
        // 将帮派ID组合成字符串
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("(");
        for (int i = 0; i < list.size(); i++) {
            sBuffer.append(list.get(i));
            if (i != list.size() - 1) {
                sBuffer.append(",");
            }
        }
        sBuffer.append(")");

        int mostPerson = 0;
        // 首先找出在当前区域内帮派数量最多的一个
        String sql = "SELECT * FROM (SELECT p_tong,COUNT(1) AS numb FROM u_part_info WHERE p_map IN " + sceneStr + " AND p_tong IN " + sBuffer.toString() + " GROUP BY p_tong ORDER BY numb desc) asd LIMIT 1";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            logger.debug("得到在sceneStr中的所有p_pk集合=" + sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                mostPerson = rs.getInt("numb");
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<int[]> list2 = new ArrayList<int[]>();
        // 然后找出所有 人数等于此 人数的帮派
        String sql2 = "SELECT * FROM (SELECT p_tong, COUNT(1) AS numb FROM u_part_info WHERE p_map IN " + sceneStr + " AND p_tong IN " + sBuffer.toString() + "GROUP BY p_tong ORDER BY numb desc) asd WHERE numb >= " + mostPerson;
        conn = dbConn.getConn();
        try {
            logger.debug("得到在sceneStr中的所有p_pk集合=" + sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql2);
            while (rs.next()) {
                int[] tongIdPerson = new int[2];
                tongIdPerson[0] = rs.getInt("p_tong");
                tongIdPerson[1] = rs.getInt("numb");
                list2.add(tongIdPerson);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return list2;

    }


    /**
     * 获得 在当前区域内 帮派数量最多的一个或几个
     *
     * @param list     是帮派ID的组合
     * @param sceneStr 是sceneID的组合,
     * @return
     */
    public List<Integer> getAllPersonInMap(String sceneStr) {
        // 首先找出在当前区域内帮派数量最多的一个
        String sql = "SELECT p_pk FROM u_part_info WHERE p_map IN " + sceneStr + " ";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        List<Integer> list = new ArrayList<Integer>();
        conn = dbConn.getConn();
        try {
            logger.debug("得到在sceneStr中的所有p_pk集合=" + sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getInt("p_pk"));
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
     * 获得此PPk的删除标志
     *
     * @param pk
     * @return
     */
    public int getDeleteState(String pPk) {
        String sql = "SELECT delete_flag FROM u_part_info WHERE p_pk = " + pPk;
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        int delete_flag = 0;
        conn = dbConn.getConn();
        try {
            logger.debug("得到在sceneStr中的所有p_pk集合=" + sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                delete_flag = rs.getInt("delete_flag");
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return delete_flag;
    }


    public void updateLoginState(String p_pk, int login_state) {
        String sql = "UPDATE u_part_info SET login_state = " + login_state + " WHERE p_pk ='" + p_pk + "'";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
    }

    public void updateLoginStateByUpk(String u_pk, int login_state) {
        String sql = "UPDATE u_part_info SET login_state = " + login_state + " WHERE u_pk ='" + u_pk + "'";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
    }

    public void updateLoginState(String login_state) {
        String sql = "UPDATE u_part_info SET login_state = '" + login_state + "'";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
    }

    /**
     * 获得所有p_pk的集合
     *
     * @return
     */
    public List<Integer> getAllPPkList() {
        List<Integer> list = new ArrayList<Integer>();
        String sql = "SELECT p_pk FROM u_part_info ";

        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            logger.debug("获得所有p_pk的集合=" + sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getInt("p_pk"));
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

    public void updateP_harness(Object ppk, int p_harness) {
        String sql = "UPDATE u_part_info u SET u.p_harness = " + p_harness + " WHERE u.p_pk = " + ppk;
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
    }

    public void updateLastTime(Object ppk) {
        if (ppk != null) {
            String sql = "UPDATE u_part_info u SET u.last_shoutu_time = now() WHERE u.p_pk = " + ppk;
            DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
            conn = dbConn.getConn();
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                dbConn.closeConn();
            }
        }
    }

    @Override
    protected PartInfoVO loadData(ResultSet rs) throws SQLException {
        PartInfoVO vo = new PartInfoVO();
        vo.setUPk(rs.getInt("u_pk"));
        vo.setPPk(rs.getInt("p_pk"));
        vo.setPName(rs.getString("p_name"));
        vo.setPSex(rs.getInt("p_sex"));
        vo.setPGrade(rs.getInt("p_grade"));
        vo.setPMap(rs.getString("p_map"));
        vo.setLoginState(rs.getInt("login_state"));

        vo.setFId(rs.getInt("f_id"));
        vo.setFJob(rs.getInt("f_job"));
        vo.setFContribute(rs.getInt("f_contribute"));
        vo.setFTitle(rs.getString("f_title"));

        return vo;
    }
}
