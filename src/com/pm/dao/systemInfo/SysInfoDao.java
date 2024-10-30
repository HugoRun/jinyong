package com.pm.dao.systemInfo;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.pub.db.DBConnection;
import com.pm.vo.sysInfo.SystemControlInfoVO;
import com.pm.vo.sysInfo.SystemInfoVO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysInfoDao extends DaoBase {
    /**
     * 插入系统消息
     *
     * @param p_pk      个人角色id，如果是系统公告和特别通知直接写零。
     * @param info_type 消息类型 1是个人消息，2是系统公告，3是特别通知,4是小喇叭。 10帮会结束消息,5是pk通知消息,7是装备展示信息
     * @param info      需要插入的消息
     * @return if sussend return 1,else return -1
     */
    public int insertSysInfo(int p_pk, int info_type, String info) {
        int i = -1;
        String sql = "INSERT INTO s_system_info values (null,'" + p_pk + "','" + info_type + "','" + info + "',now())";
        logger.debug("SysInfoDao insertSysInfo :sql=" + sql);

        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        try {
            conn = dbConn.getConn();
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            i = 1;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }

        return i;
    }

    /**
     * 插入系统消息
     *
     * @param p_pk      个人角色id，如果是系统公告和特别通知直接写零。
     * @param info_type 消息类型 1是个人消息，2是系统公告，3是特别通知,4是小喇叭。 10帮会结束消息,5是pk通知消息,7是装备展示信息
     * @param info      需要插入的消息
     * @return if sussend return 1,else return -1
     */
    public int insertSysInfoTongTime(int p_pk, int info_type, String info, String time) {
        int i = -1;
        String sql = "INSERT INTO s_system_info values (null,'" + p_pk + "','" + info_type + "','" + info + "','" + time + "')";
        logger.debug("SysInfoDao insertSysInfo :sql=" + sql);

        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        try {
            conn = dbConn.getConn();
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            i = 1;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }

        return i;
    }

    /**
     * 插入系统消息
     *
     * @param p_pk      个人角色id，如果是系统公告和特别通知直接写零。
     * @param info_type 消息类型 1是个人消息，2是系统公告，3是特别通知,4是小喇叭。 10帮会结束消息,5是pk通知消息,7是装备展示信息
     * @param info      需要插入的消息
     * @return if sussend return 1,else return -1
     */
    public int insertSysInfo(int p_pk, int info_type, String info, int second) {
        int i = -1;
        String sql = "INSERT INTO s_system_info values (null,'" + p_pk + "','" + info_type + "','" + info + "',NOW()+INTERVAL " + second + " second)";
        logger.debug("SysInfoDao insertSysInfo :sql=" + sql);

        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        try {
            conn = dbConn.getConn();
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            i = 1;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }

        return i;
    }

    /**
     * 插入系统消息
     *
     * @param p_pk      个人角色id，如果是系统公告和特别通知直接写零。
     * @param info_type 消息类型 1是个人消息，2是系统公告，3是特别通知,4是小喇叭。 10帮会结束消息,5是pk通知消息,7是装备展示信息
     * @param info      需要插入的消息
     * @return if sussend return 1,else return -1
     */
    public int insertSysInfo(int p_pk, int info_type, String info, String sendTime) {
        int i = -1;
        String sql = "INSERT INTO s_system_info values (null,'" + p_pk + "','" + info_type + "','" + info + "','" + sendTime + "')";
        logger.debug("SysInfoDao insertSysInfo :sql=" + sql);

        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        try {
            conn = dbConn.getConn();
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            i = 1;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }

        return i;
    }

    /**
     * 选择系统信息表的消息条数
     *
     * @param p_pk
     * @param number    要选择消息数目
     * @param info_type 消息类型 1是个人消息，2是系统公告，3是特别通知,4是小喇叭。 10帮会结束消息,5是pk通知消息,7是装备展示信息
     * @return if sussend return 1,else return -1
     */
    public List<SystemInfoVO> selectSysInfo(int p_pk, int info_type, int number) {
        List<SystemInfoVO> list = new ArrayList<SystemInfoVO>();
        String sql = "SELECT * FROM s_system_info where p_pk=" + p_pk + " and info_type='" + info_type + "' limit " + number;
        logger.debug("选择系统消息的sql=" + sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);

        SystemInfoVO sIvo = null;
        try {

            conn = dbConn.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                sIvo = new SystemInfoVO();
                sIvo.setSysInfoId(rs.getInt("sysInfo_id"));
                sIvo.setPPk(rs.getInt("p_pk"));
                sIvo.setInfoType(rs.getInt("info_type"));
                sIvo.setSystemInfo(rs.getString("system_info"));
                sIvo.setCreateTime(rs.getString("happen_time"));
                list.add(sIvo);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }

        return list;
    }


    /**
     * 查询系统消息表中个人有关的系统消息
     *
     * @param pPk 个人角色id.
     * @return SystemInfoVO
     */
    public SystemInfoVO getSystemSelfInfo(String pPk) {
        String sql = "SELECT * FROM s_system_info where sysInfo_id = (select min(sysInfo_id) from s_system_info " +
                "where NOW() >= happen_time and (happen_time + INTERVAL 10 second) >= NOW() and p_pk=" + pPk + " )";
        //logger.debug("选择确定一条系统消息的sql="+sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        SystemInfoVO sIvo = null;
        logger.debug(" 查询系统消息表中个人有关的系统消息=" + sql);
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                sIvo = new SystemInfoVO();
                sIvo.setSysInfoId(rs.getInt("sysInfo_id"));
                sIvo.setPPk(rs.getInt("p_pk"));
                sIvo.setInfoType(rs.getInt("info_type"));
                sIvo.setSystemInfo(rs.getString("system_info"));
                sIvo.setCreateTime(rs.getString("happen_time"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return sIvo;
    }


    /**
     * 获得三条系统消息
     *
     * @return
     */
    public List<SystemInfoVO> getSystemInfoThree(int p_pk) {
        List<SystemInfoVO> list = new ArrayList<SystemInfoVO>();
		/*String sql = "SELECT * FROM s_system_info where NOW() > happen_time and " +
				"(happen_time + INTERVAL 10 second) > NOW() and ( p_pk=0 or p_pk="
				+p_pk+" ) order by info_type desc,happen_time asc limit 3";*/
        String sql = "SELECT * FROM s_system_info where NOW() >= happen_time and (happen_time + INTERVAL 10 second) >= NOW() " +
                "and ( (info_type = 1 and p_pk = " + p_pk + ") or info_type not in (1,5) ) order by happen_time desc limit 3";


        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        logger.debug(" 获得三条系统消息=" + sql);
        //System.out.println(" 获得三条系统消息="+sql);
        SystemInfoVO sIvo = null;
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                sIvo = new SystemInfoVO();
                sIvo.setSysInfoId(rs.getInt("sysInfo_id"));
                sIvo.setPPk(rs.getInt("p_pk"));
                sIvo.setInfoType(rs.getInt("info_type"));
                sIvo.setSystemInfo(rs.getString("system_info"));
                sIvo.setCreateTime(rs.getString("happen_time"));
                list.add(sIvo);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }

        return list;
    }

    /**
     * 在聊天拦看系统消息时间定义20分钟
     *
     * @return
     */
    public List<SystemInfoVO> getSystemInfoThreeTime(String p_pk) {
        List<SystemInfoVO> list = new ArrayList<SystemInfoVO>();
        String sql = "SELECT * FROM s_system_info where NOW() > happen_time and " +
                "(happen_time + INTERVAL 20 minute) > NOW() and ( (info_type = 1 and p_pk = " + p_pk
                + ") or info_type not in (1,5) ) order by happen_time desc limit 5";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        logger.debug(" 获得三条系统消息=" + sql);
        SystemInfoVO sIvo = null;
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                sIvo = new SystemInfoVO();
                sIvo.setSysInfoId(rs.getInt("sysInfo_id"));
                sIvo.setPPk(rs.getInt("p_pk"));
                sIvo.setInfoType(rs.getInt("info_type"));
                sIvo.setSystemInfo(rs.getString("system_info"));
                sIvo.setCreateTime(rs.getString("happen_time"));
                list.add(sIvo);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }

        return list;
    }

    /**
     * 查询系统消息表中的系统消息
     *
     * @return SystemInfoVO
     */
    public SystemInfoVO getSystemInfo() {
        String sql = "SELECT * FROM s_system_info " +
                "where sysInfo_id = (select min(sysInfo_id) from s_system_info where NOW() > happen_time " +
                "AND (happen_time + INTERVAL 10 second) > NOW() " +
                "AND p_pk=0 " +
                "GROUP BY info_type DESC)";
        logger.debug("查询系统消息表中的系统消息sql=" + sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        SystemInfoVO sIvo = null;
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                sIvo = new SystemInfoVO();
                sIvo.setSysInfoId(rs.getInt("sysInfo_id"));
                sIvo.setPPk(rs.getInt("p_pk"));
                sIvo.setInfoType(rs.getInt("info_type"));
                sIvo.setSystemInfo(rs.getString("system_info"));
                sIvo.setCreateTime(rs.getString("happen_time"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return sIvo;
    }


    /**
     * 按系统消息Id来选择一条系统消息
     *
     * @param sysInfoId 系统消息id
     * @return SystemInfoVO
     */
    public SystemInfoVO getSystemInfo(int sysInfoId) {
        String sql = "SELECT * FROM s_system_info where sysInfo_id=" + sysInfoId;
        logger.debug("选择确定一条系统消息的sql=" + sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        SystemInfoVO sIvo = null;
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                sIvo = new SystemInfoVO();
                sIvo.setSysInfoId(rs.getInt("sysInfo_id"));
                sIvo.setPPk(rs.getInt("p_pk"));
                sIvo.setInfoType(rs.getInt("info_type"));
                sIvo.setSystemInfo(rs.getString("system_info"));
                sIvo.setCreateTime(rs.getString("happen_time"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return sIvo;
    }

    /**
     * 删除超过15分钟的系统消息
     */
    public void deleteMoreFifteenMinutes() {
        String sql = "DELETE FROM `s_system_info` WHERE now() > (happen_time + INTERVAL 15 minute)";
        logger.debug("删除超过15分钟的sql=" + sql);

        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        try {
            conn = dbConn.getConn();
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
    }

    /**
     * 获得系统控制表中的控制条件的关于等级控制的条件
     */
    public List<SystemControlInfoVO> getSystemInfoControlByPGrade() {
        String sql = "SELECT * FROM u_systeminfo_control where condition_type = 1";
        List<SystemControlInfoVO> list = new ArrayList<SystemControlInfoVO>();
        logger.debug("查找系统消息控制表的sql=" + sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        SystemControlInfoVO scIvo = null;
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                scIvo = new SystemControlInfoVO();
                scIvo.setControlId(rs.getInt("control_id"));
                scIvo.setCondition(rs.getInt("condition_type"));
                scIvo.setPlayerGrade(rs.getString("player_grade"));
                //scIvo.setTaskId(rs.getInt("task_id"));
                //scIvo.setPopularity(rs.getInt("popularity"));
                //scIvo.setTitle(rs.getString("title"));
                //scIvo.setSendTime(rs.getString("send_time"));
                scIvo.setSendContent(rs.getString("send_content"));
                scIvo.setSendType(rs.getInt("send_type"));
                list.add(scIvo);
                //	map.put(rs.getString("player_grade"), rs.getString("send_content"));

            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return list;
    }


    /**
     * 获得系统控制表中的控制条件的关于任务控制的条件
     */
    public Map<Integer, String> getSystemInfoControlByTaskId() {
        String sql = "SELECT * FROM u_systeminfo_control where condition_type = 2";
        Map<Integer, String> map = new HashMap<Integer, String>();
        logger.debug("查找系统消息控制表的sql=" + sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        //SystemControlInfoVO scIvo = null;
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                map.put(rs.getInt("task_id"), rs.getString("send_content"));

            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return map;
    }

    /**
     * 获得系统控制表中的控制条件的关于任务控制的条件
     */
    public List<SystemControlInfoVO> getSystemInfoControlListByTaskId() {
        String sql = "SELECT * FROM u_systeminfo_control where condition_type = 2";
        List<SystemControlInfoVO> list = new ArrayList<SystemControlInfoVO>();
        logger.debug("查找系统消息控制表的sql=" + sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        SystemControlInfoVO scIvo = null;
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                scIvo = new SystemControlInfoVO();
                scIvo.setControlId(rs.getInt("control_id"));
                scIvo.setCondition(rs.getInt("condition_type"));
                scIvo.setPlayerGrade(rs.getString("player_grade"));
                scIvo.setTaskId(rs.getInt("task_id"));
//				scIvo.setPopularity(rs.getInt("popularity"));
//				scIvo.setTitle(rs.getString("title"));
//				scIvo.setSendTime(rs.getString("send_time"));
                scIvo.setSendContent(rs.getString("send_content"));
                scIvo.setSendType(rs.getInt("send_type"));
                list.add(scIvo);

            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return list;
    }


    /**
     * 获得系统控制表中的控制条件的关于声望控制的条件
     */
    public Map<Integer, String> getSystemInfoControlByPopularity() {
        String sql = "SELECT * FROM u_systeminfo_control where condition_type = 3";
        Map<Integer, String> map = new HashMap<Integer, String>();
        logger.debug("查找系统消息控制表的sql=" + sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        //SystemControlInfoVO scIvo = null;
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                map.put(rs.getInt("popularity"), rs.getString("send_content"));

            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return map;
    }

    /**
     * 获得系统控制表中的控制条件的关于称谓控制的条件
     */
    public Map<String, String> getSystemInfoControlByTitle() {
        String sql = "SELECT * FROM u_systeminfo_control where condition_type = 4";
        Map<String, String> map = new HashMap<String, String>();
        logger.debug("查找系统消息控制表的sql=" + sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        //SystemControlInfoVO scIvo = null;
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                map.put(rs.getString("title"), rs.getString("send_content"));

            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return map;
    }

    /**
     * 获得系统控制表中的控制条件的关于称谓控制的条件
     *
     * @param title_id
     */
    public Map<String, String> getSystemInfoControlByTitle(String title_id) {
        String sql = "SELECT * FROM u_systeminfo_control where condition_type = 4 and title ='" + title_id + "'";
        Map<String, String> map = new HashMap<String, String>();
        logger.debug("查找系统消息控制表的sql=" + sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        //SystemControlInfoVO scIvo = null;
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                map.put(rs.getString("title"), rs.getString("send_content"));

            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return map;
    }

    /**
     * 获得系统控制表中的控制条件的关于称谓控制的条件
     *
     * @param title_id
     */
    public List<SystemControlInfoVO> getSystemInfoControlListByTitle(String title_id) {
        String sql = "SELECT * FROM u_systeminfo_control where condition_type = 4 and title ='" + title_id + "'";
        List<SystemControlInfoVO> list = new ArrayList<SystemControlInfoVO>();

        logger.debug("查找系统消息控制表的sql=" + sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        SystemControlInfoVO scIvo = null;
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                scIvo = new SystemControlInfoVO();
                scIvo.setControlId(rs.getInt("control_id"));
                scIvo.setCondition(rs.getInt("condition_type"));
                scIvo.setPlayerGrade(rs.getString("player_grade"));
                scIvo.setTaskId(rs.getInt("task_id"));
                scIvo.setPopularity(rs.getInt("popularity"));
                scIvo.setTitle(rs.getString("title"));
                scIvo.setSendTime(rs.getString("send_time"));
                scIvo.setSendContent(rs.getString("send_content"));
                scIvo.setSendType(rs.getInt("send_type"));
                list.add(scIvo);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return list;
    }


    /**
     * 获得系统控制表中的控制条件的关于综合控制的条件
     */
    public List<SystemControlInfoVO> getSystemInfoControlByPCollage() {
        String sql = "SELECT * FROM u_systeminfo_control where condition_type = 6 ";
        List<SystemControlInfoVO> list = new ArrayList<SystemControlInfoVO>();
        SystemControlInfoVO infovo = null;
        logger.debug("选择确定一条系统消息的sql=" + sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        //SystemControlInfoVO scIvo = null;
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                infovo = new SystemControlInfoVO();
                infovo.setControlId(rs.getInt("control_id"));
                infovo.setCondition(rs.getInt("condition_type"));
                infovo.setPlayerGrade(rs.getString("player_grade"));
                infovo.setTaskId(rs.getInt("task_id"));
                infovo.setPopularity(rs.getInt("popularity"));
                infovo.setTitle(rs.getString("title"));
                infovo.setSendTime(rs.getString("send_time"));
                infovo.setSendContent(rs.getString("send_content"));
                infovo.setSendType(rs.getInt("send_type"));
                list.add(infovo);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return list;
    }


    public List<SystemControlInfoVO> getSystemInfoControlByNewPlayer() {
        String sql = "SELECT * FROM u_systeminfo_control where condition_type = 7 ";
        List<SystemControlInfoVO> list = new ArrayList<SystemControlInfoVO>();
        SystemControlInfoVO infovo = null;
        logger.debug("选择确定一条系统消息的sql=" + sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        //SystemControlInfoVO scIvo = null;
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                infovo = new SystemControlInfoVO();
                infovo.setControlId(rs.getInt("control_id"));
                infovo.setCondition(rs.getInt("condition_type"));
                infovo.setPlayerGrade(rs.getString("player_grade"));
                infovo.setTaskId(rs.getInt("task_id"));
                infovo.setPopularity(rs.getInt("popularity"));
                infovo.setTitle(rs.getString("title"));
                infovo.setSendTime(rs.getString("send_time"));
                infovo.setSendContent(rs.getString("send_content"));
                infovo.setSendType(rs.getInt("send_type"));
                list.add(infovo);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return list;
    }


    /**
     * 获得定时发送系统消息的所有信息
     *
     * @return
     */
    public List getSystemInfoCOntrolByTime() {
        String sql = "SELECT * FROM u_systeminfo_control where condition_type = 5 ";
        List<SystemControlInfoVO> list = new ArrayList<SystemControlInfoVO>();
        SystemControlInfoVO infovo = null;
        logger.debug("选择确定一条系统消息的sql=" + sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        //SystemControlInfoVO scIvo = null;
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                infovo = new SystemControlInfoVO();
                infovo.setControlId(rs.getInt("control_id"));
                infovo.setSendTime(rs.getString("send_time"));
                infovo.setSendContent(rs.getString("send_content"));
                infovo.setSendType(rs.getInt("send_type"));
                list.add(infovo);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return list;
    }

    /**
     * 获得定时发送系统消息的所有信息
     *
     * @return
     */
    public SystemControlInfoVO getSystemInfoByTypeAndID(String type, String id) {
        String sql = "SELECT * FROM u_systeminfo_control where condition_type = '" + type + "' and task_id = '" + id + "' limit 1";
        SystemControlInfoVO infovo = null;
        logger.debug("选择确定一条系统消息的sql=" + sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        //SystemControlInfoVO scIvo = null;
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                infovo = new SystemControlInfoVO();
                infovo.setControlId(rs.getInt("control_id"));
                infovo.setSendTime(rs.getString("send_time"));
                infovo.setSendContent(rs.getString("send_content"));
                infovo.setSendType(rs.getInt("send_type"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return infovo;
    }

    /**
     * 获得定时发送系统消息的所有信息
     *
     * @return
     */
    public List<SystemControlInfoVO> getSystemInfoByNewPlayerGuide(String type, String id) {
        List<SystemControlInfoVO> list = new ArrayList<SystemControlInfoVO>();
        String sql = "SELECT * FROM u_systeminfo_control where condition_type = '" + type + "' and task_id = '" + id + "'";
        SystemControlInfoVO infovo = null;
        logger.debug("选择确定一条系统消息的sql=" + sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        //SystemControlInfoVO scIvo = null;
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                infovo = new SystemControlInfoVO();
                infovo.setControlId(rs.getInt("control_id"));
                infovo.setSendTime(rs.getString("send_time"));
                infovo.setSendContent(rs.getString("send_content"));
                infovo.setPopularity(rs.getInt("popularity"));
                infovo.setSendType(rs.getInt("send_type"));
                list.add(infovo);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return list;
    }


    /**
     * 给所有在sceneStr中人发信息
     * sceneStr是形如(111,112,113,115)类的scene_name集合
     * systemInfo 消息
     *
     * @param systemInfo
     * @param sceneStr
     */
    public void insertSysInfo(String systemInfo, String sceneStr) {
        PartInfoDao partInfoDao = new PartInfoDao();
        List<Integer> list = partInfoDao.getPPkListBySceneStr(sceneStr);

        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);

        String sql = "INSERT INTO s_system_info values (null,?,1,'" + systemInfo + "',now())";
        logger.debug("给所有在sceneStr中人发信息=" + sql);
        try {
            conn = dbConn.getConn();
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            if (list != null) {
                logger.debug("总人数=" + list.size());
            } else {
                logger.debug("空的");
            }
            for (int i = 0; i < list.size(); i++) {
                int p_pk = list.get(i);
                ps.setInt(1, p_pk);
                //stmt.addBatch();
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
            ps.close();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
    }

    /**
     * 更新时间罪恶值等的系统消息显示时间
     *
     * @param p_pk
     * @param buffEffectTime
     * @param propName
     */
    public void updateTimeReducePkValue(int p_pk, int buffEffectTime, String propName) {
        String sql = "update s_system_info set happen_time = (happen_time +  INTERVAL " + buffEffectTime
                + " minute) where p_pk = " + p_pk + " and info_type = 1 and system_info like '%" + propName + "%'";
        logger.debug("=" + sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
    }

    /**
     * 根据pPk和系统消息的一些关键词来删除不需要的系统消息
     *
     * @param pPk
     * @param propName
     */
    public void deleteByPPkInfo(int pPk, String propName) {
        String sql = "DELETE FROM `s_system_info` WHERE `info_type` = 1 AND p_pk = " + pPk + " AND `system_info` LIKE '%" + propName + "%' ";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        logger.debug("根据pPk和系统消息的一些关键词来删除不需要的系统消息=" + sql);
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }

    }

    /**
     * 查询是否有 特定的 系统消息
     *
     * @param pPk
     * @param propName
     * @return
     */
    public boolean selectByPPkInfo(int pPk, String propName) {
        boolean flag = false;
        String sql = "SELECT * FROM s_system_info where info_type = 1 and p_pk=" + pPk + " and system_info like '%" + propName + "%' ";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        logger.debug("查询是否有 特定的 系统消息=" + sql);
        try {
            conn = dbConn.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                flag = true;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return flag;
    }

    /**
     * 根据pPk和系统消息的一些关键词来更新系统消息的时间
     * @param pPk
     * @param propName

    public void updateByPPkInfo(int pPk, String propName,int buffTime)
    {
    String sql = "update s_system_info set happen_time = happen_time + "+  +" where info_type = 1 and p_pk="+pPk+" and system_info like '%"+propName+"%' ";
    DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
    logger.debug("根据pPk和系统消息的一些关键词来删除不需要的系统消息="+sql);
    try{
    conn = dbConn.getConn();
    stmt = conn.createStatement();
    stmt.execute(sql);
    stmt.close();
    } catch (SQLException e) {
    e.printStackTrace();
    } finally {
    dbConn.closeConn();
    }

    } */
}
