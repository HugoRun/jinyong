package com.ls.ben.dao.menu;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.map.SceneVO;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.pub.constant.MenuType;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 功能:npc菜单表的操作
 *
 * @author 刘帅 12:54:24 PM
 */
public class OperateMenuDao extends DaoBase {
    /**
     * 得到所有menu,并构建场景下的父菜单
     *
     * @return
     * @throws Exception
     */
    public HashMap<String, OperateMenuVO> getAllMenu(HashMap<String, SceneVO> scene_list) throws Exception {
        HashMap<String, OperateMenuVO> menu_list = null;
        OperateMenuVO menu = null;
        SceneVO scene = null;

        String total_sql = "SELECT COUNT(id) FROM `operate_menu_info`";
        String all_data_sql = "SELECT * FROM `operate_menu_info` ORDER BY id, menu_order ";
        logger.debug("total_sql:" + total_sql);
        logger.debug("all_data_sql:" + all_data_sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            int total = 0;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(total_sql);
            if (rs.next()) {
                total = rs.getInt(1);
            }

            menu_list = new HashMap<String, OperateMenuVO>(total);

            rs = stmt.executeQuery(all_data_sql);
            while (rs.next()) {
                menu = new OperateMenuVO();
                menu.setId(rs.getInt("id"));
                menu.setMenuName(StringUtil.isoToGBK(rs.getString("menu_name")));
                menu.setMenuMap(rs.getInt("menu_map"));
                menu.setMenuType(rs.getInt("menu_type"));
                menu.setMenuImg(rs.getString("menu_img"));
                menu.setMenuDialog(StringUtil.isoToGBK(rs.getString("menu_dialog")));
                menu.setMenuCamp(rs.getInt("menu_camp"));

                menu.setMenuOperate1(rs.getString("menu_operate1"));
                menu.setMenuOperate2(rs.getString("menu_operate2"));
                menu.setMenuOperate3(rs.getString("menu_operate3"));

                menu.setMenuTimeBegin(rs.getString("menu_time_begin"));
                menu.setMenuTimeEnd(rs.getString("menu_time_end"));
                menu.setMenuDayBegin(rs.getString("menu_day_begin"));
                menu.setMenuDayEnd(rs.getString("menu_day_end"));

                menu.setMenuFatherId(rs.getInt("menu_father_id"));
                menu.setMenuTasksId(rs.getString("menu_tasks_id"));
                menu.setMenuTaskFlag(rs.getInt("menu_task_flag"));
                menu.setMenuOperate4(rs.getInt("menu_operate4"));
                menu_list.put(menu.getId() + "", menu);
                if (menu.getMenuMap() != 0 && menu.getMenuFatherId() == 0 && menu.getMenuTaskFlag() == 0) {
                    //如果是父菜单，且不是任务菜单
                    scene = scene_list.get("" + menu.getMenuMap());
                    if (scene == null) {
                        throw new Exception("父菜单的数据错误：无该场景数据, menu.getMenuMap()=" + menu.getMenuMap());
                    }
                    scene.getFatherMenuList().put(menu.getId() + "", menu);
                }
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.getStackTrace();
            throw new Exception("菜单信息加载内存失败，当前菜单为：" + menu.getId() + ";错误信息:" + e.getMessage());

        } finally {
            dbConn.closeConn();
        }
        return menu_list;
    }


    /**
     * 通过id得到 menu
     *
     * @param map_id
     * @return
     */
    public OperateMenuVO getMenuById(int id) {
        OperateMenuVO menu = null;
        String sql = "SELECT * FROM  `operate_menu_info` WHERE id = " + id;
        logger.debug(sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                menu = new OperateMenuVO();
                menu.setId(rs.getInt("id"));
                menu.setMenuName(StringUtil.isoToGBK(rs.getString("menu_name")));
                menu.setMenuType(rs.getInt("menu_type"));
                menu.setMenuImg(rs.getString("menu_img"));
                menu.setMenuMap(rs.getInt("menu_map"));
                menu.setMenuOperate1(rs.getString("menu_operate1"));
                menu.setMenuOperate2(rs.getString("menu_operate2"));
                menu.setMenuOperate3(rs.getString("menu_operate3"));
                menu.setMenuCamp(rs.getInt("menu_camp"));
                menu.setMenuDialog(StringUtil.isoToGBK(rs.getString("menu_dialog")));
                menu.setMenuTimeBegin(rs.getString("menu_time_begin"));
                menu.setMenuTimeEnd(rs.getString("menu_time_end"));
                menu.setMenuDayBegin(rs.getString("menu_day_begin"));
                menu.setMenuDayEnd(rs.getString("menu_day_end"));
                menu.setMenuFatherId(rs.getInt("menu_father_id"));
                menu.setMenuOrder(rs.getInt("menu_order"));
                menu.setMenuTasksId(rs.getString("menu_tasks_id"));
                menu.setMenuTaskFlag(rs.getInt("menu_task_flag"));
                menu.setMenuOperate4(rs.getInt("menu_operate4"));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            logger.debug(e.getStackTrace());

        } finally {
            dbConn.closeConn();
        }
        return menu;
    }


    /**
     * 根据menu_type来获得菜单
     *
     * @param map_id
     * @return
     */
    public List<OperateMenuVO> getMainMenuByMenuType(int menu_type) {
        List<OperateMenuVO> list = new ArrayList<OperateMenuVO>();
        OperateMenuVO menu = null;
        String sql = "SELECT * FROM `operate_menu_info` WHERE menu_type = " + menu_type;
        logger.debug("根据menu_type来获得菜单=" + sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                menu = new OperateMenuVO();
                menu.setId(rs.getInt("id"));
                menu.setMenuName(StringUtil.isoToGBK(rs.getString("menu_name")));
                menu.setMenuMap(rs.getInt("menu_map"));
                menu.setMenuType(rs.getInt("menu_type"));
                menu.setMenuImg(rs.getString("menu_img"));

                menu.setMenuCamp(rs.getInt("menu_camp"));
                menu.setMenuDialog(StringUtil.isoToGBK(rs.getString("menu_dialog")));

                menu.setMenuOperate1(rs.getString("menu_operate1"));
                menu.setMenuOperate2(rs.getString("menu_operate2"));
                menu.setMenuOperate3(rs.getString("menu_operate3"));

                menu.setMenuTimeBegin(rs.getString("menu_time_begin"));
                menu.setMenuTimeEnd(rs.getString("menu_time_end"));
                menu.setMenuDayBegin(rs.getString("menu_day_begin"));
                menu.setMenuDayEnd(rs.getString("menu_day_end"));

                menu.setMenuFatherId(rs.getInt("menu_father_id"));
                menu.setMenuTasksId(rs.getString("menu_tasks_id"));
                menu.setMenuOperate4(rs.getInt("menu_operate4"));
                list.add(menu);
            }
            rs.close();
            stmt.close();

            logger.debug("operateMenuDap中的menu个数为 : " + list.size());
            return list;
        } catch (Exception e) {
            e.getStackTrace();

        } finally {
            dbConn.closeConn();
        }
        return list;
    }


    /**
     * 得到父菜单id为menu_father_id的所有子菜单
     *
     * @param menu_father_id 父菜单id
     * @param race           种族
     * @return
     */
    public List<OperateMenuVO> getSonMenuByMap(int menu_father_id, int race) {
        List<OperateMenuVO> list = new ArrayList<OperateMenuVO>();
        OperateMenuVO menu = null;
        String sql = "SELECT * FROM `operate_menu_info` WHERE `menu_father_id` = " + menu_father_id + " AND `menu_camp` IN (0," + race + ") ORDER BY `menu_order` ";
        logger.debug(sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                menu = new OperateMenuVO();
                menu.setId(rs.getInt("id"));
                menu.setMenuName(StringUtil.isoToGBK(rs.getString("menu_name")));
                menu.setMenuMap(rs.getInt("menu_map"));
                menu.setMenuType(rs.getInt("menu_type"));
                menu.setMenuImg(rs.getString("menu_img"));
                menu.setMenuDialog(StringUtil.isoToGBK(rs.getString("menu_dialog")));
                menu.setMenuCamp(rs.getInt("menu_camp"));

                menu.setMenuOperate1(rs.getString("menu_operate1"));
                menu.setMenuOperate2(rs.getString("menu_operate2"));
                menu.setMenuOperate3(rs.getString("menu_operate3"));

                menu.setMenuTimeBegin(rs.getString("menu_time_begin"));
                menu.setMenuTimeEnd(rs.getString("menu_time_end"));
                menu.setMenuDayBegin(rs.getString("menu_day_begin"));
                menu.setMenuDayEnd(rs.getString("menu_day_end"));
                menu.setMenuFatherId(menu_father_id);
                menu.setMenuTasksId(rs.getString("menu_tasks_id"));
                menu.setMenuOperate4(rs.getInt("menu_operate4"));
                list.add(menu);
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        } finally {
            dbConn.closeConn();
        }
        return list;
    }

    /**
     * 通过menu_type和阵营归属得到得到 menu类型
     *
     * @param menu_type 目录类型
     * @param camp      阵营归属
     * @return
     */
    public List<OperateMenuVO> getMenuByMenuTypeAndCamp(int menu_type, int camp) {
        List<OperateMenuVO> list = new ArrayList<OperateMenuVO>();
        OperateMenuVO menu = null;
        String sql = "SELECT * FROM `operate_menu_info` WHERE `menu_type` = " + menu_type + " AND `menu_camp` = " + camp;
        logger.debug(sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                menu = new OperateMenuVO();
                menu.setId(rs.getInt("id"));
                //menu.setMenuName(StringUtil.isoToGBK(rs.getString("menu_name")));
                menu.setMenuMap(rs.getInt("menu_map"));
                menu.setMenuType(rs.getInt("menu_type"));
                //menu.setMenuImg(rs.getString("menu_img"));
                //menu.setMenuDialog(StringUtil.isoToGBK(rs.getString("menu_dialog")));
                menu.setMenuCamp(rs.getInt("menu_camp"));
                menu.setMenuOperate4(rs.getInt("menu_operate4"));
                //menu.setMenuOperate1(rs.getString("menu_operate1"));
                //menu.setMenuOperate2(rs.getString("menu_operate2"));
                //menu.setMenuOperate3(rs.getString("menu_operate3"));

                //menu.setMenuTimeBegin(rs.getString("menu_time_begin"));
                //menu.setMenuTimeEnd(rs.getString("menu_time_end"));
                //menu.setMenuDayBegin(rs.getString("menu_day_begin"));
                //menu.setMenuDayEnd(rs.getString("menu_day_end"));
                //menu.setMenuRefurbishTime(rs.getString("menu_refurbish_time"));
                //menu.setMenuFatherId(rs.getInt("menu_father_id"));
                //menu.setMenuTasksId(rs.getString("menu_tasks_id"));
                list.add(menu);
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        } finally {
            dbConn.closeConn();
        }
        return list;
    }

    /**
     * 获得npc_id依靠任务id
     * <p>
     * 03.25 修改，增加了任务id 前后的,
     *
     * @param taskid
     * @return
     */
    public OperateMenuVO getNpcIdByTaskId(String taskid) {
        OperateMenuVO vo = null;
        String searchTask = "," + taskid + ",";
        String sql = "SELECT id, menu_map FROM `operate_menu_info` WHERE menu_tasks_id LIKE '%" + searchTask + "%' ";
        logger.debug("获得npc_id依靠任务id=" + sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            vo = new OperateMenuVO();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                vo.setId(rs.getInt("id"));
                vo.setMenuMap(rs.getInt("menu_map"));
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        } finally {
            dbConn.closeConn();
        }
        return vo;
    }

    /**
     * 获得menuvo依靠 sceneId和menuOperate1
     *
     * @param sceneId
     * @param menuOperate1
     * @return
     */
    public OperateMenuVO getOperateMenuVOBySceneAndOperate1(String sceneId, String menuOperate1) {

        String sql = "SELECT * FROM `operate_menu_info` where menu_map=" + sceneId + " and menu_operate1=" + menuOperate1;

        OperateMenuVO menu = null;
        logger.debug(sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                menu = new OperateMenuVO();
                menu.setId(rs.getInt("id"));
                menu.setMenuName(StringUtil.isoToGBK(rs.getString("menu_name")));
                menu.setMenuMap(rs.getInt("menu_map"));
                menu.setMenuType(rs.getInt("menu_type"));
                menu.setMenuImg(rs.getString("menu_img"));
                menu.setMenuDialog(StringUtil.isoToGBK(rs.getString("menu_dialog")));
                menu.setMenuCamp(rs.getInt("menu_camp"));

                menu.setMenuOperate1(rs.getString("menu_operate1"));
                menu.setMenuOperate2(rs.getString("menu_operate2"));
                menu.setMenuOperate3(rs.getString("menu_operate3"));

                menu.setMenuTimeBegin(rs.getString("menu_time_begin"));
                menu.setMenuTimeEnd(rs.getString("menu_time_end"));
                menu.setMenuDayBegin(rs.getString("menu_day_begin"));
                menu.setMenuDayEnd(rs.getString("menu_day_end"));
                menu.setMenuFatherId(rs.getInt("menu_father_id"));
                menu.setMenuTasksId(rs.getString("menu_tasks_id"));
                menu.setMenuOperate4(rs.getInt("menu_operate4"));
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        } finally {
            dbConn.closeConn();
        }


        return menu;
    }


    /**
     * 获得menuvo依靠 sceneId和menu_type
     *
     * @param sceneId
     * @param type
     * @return
     */
    public OperateMenuVO getOperateMenuVOBySceneAndType(int sceneId, int type) {

        String sql = "SELECT * FROM `operate_menu_info` WHERE `menu_map`=" + sceneId + " AND `menu_type` = " + type;

        OperateMenuVO menu = null;
        logger.debug(sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                menu = new OperateMenuVO();
                menu.setId(rs.getInt("id"));
                menu.setMenuName(StringUtil.isoToGBK(rs.getString("menu_name")));
                menu.setMenuMap(rs.getInt("menu_map"));
                menu.setMenuType(rs.getInt("menu_type"));
                menu.setMenuImg(rs.getString("menu_img"));
                menu.setMenuDialog(StringUtil.isoToGBK(rs.getString("menu_dialog")));
                menu.setMenuCamp(rs.getInt("menu_camp"));

                menu.setMenuOperate1(rs.getString("menu_operate1"));
                menu.setMenuOperate2(rs.getString("menu_operate2"));
                menu.setMenuOperate3(rs.getString("menu_operate3"));

                menu.setMenuTimeBegin(rs.getString("menu_time_begin"));
                menu.setMenuTimeEnd(rs.getString("menu_time_end"));
                menu.setMenuDayBegin(rs.getString("menu_day_begin"));
                menu.setMenuDayEnd(rs.getString("menu_day_end"));
                menu.setMenuFatherId(rs.getInt("menu_father_id"));
                menu.setMenuTasksId(rs.getString("menu_tasks_id"));
                menu.setMenuOperate4(rs.getInt("menu_operate4"));
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        } finally {
            dbConn.closeConn();
        }


        return menu;
    }

    /**
     * 更新旗杆的阵营归属
     *
     * @param menu_id
     * @param p_camp
     */
    public void updateOperateMenuCamp(String menu_id, int menu_camp) {
        String sql = "update operate_menu_info set menu_camp=" + menu_camp + " where id=" + menu_id;
        logger.debug(sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        } finally {
            dbConn.closeConn();
        }
    }

    /**
     * 根据演武管理员类型和所在地图id来确定menu_operate1
     *
     * @param flag_type
     * @param map_ID
     * @return
     */
    public String getJoinPointByFieldManager(int field_manager_type, int map_ID) {
        String sql = "SELECT menu_operate1 from operate_menu_info where menu_type = " + field_manager_type + " and menu_operate2 like '%" + map_ID + "%'";
        logger.debug("根据演武管理员类型和所在地图id来确定进入点=" + sql);
        String menu_operate1 = "";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                menu_operate1 = rs.getString("menu_operate1");
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        } finally {
            dbConn.closeConn();
        }
        return menu_operate1;
    }

    /**
     * 根据演武管理员类型和所在地图id来确定menu_operate2
     * 目的是获得此战场的出去点
     *
     * @param flag_type
     * @param map_ID
     * @return
     */
    public String getOutPointByFieldManager(int field_manager_type, int map_ID) {
        String sql = "SELECT menu_operate2 from operate_menu_info where menu_type = " + field_manager_type + " and menu_operate2 like '%" + map_ID + "%'";

        logger.debug("根据演武管理员类型和所在地图id来确定进入点=" + sql);
        String menu_operate2 = "";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                menu_operate2 = rs.getString("menu_operate2");
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        } finally {
            dbConn.closeConn();
        }
        return menu_operate2;
    }

    /**
     * 根据演武管理员类型和所在地图id来确定menu_operate3
     * 目的是获得此战场的出去点
     *
     * @param flag_type
     * @param map_ID
     * @return
     */
    public String getFieldTypeByFieldManager(int field_manager_type, int map_ID) {
        String sql = "SELECT menu_operate3 from operate_menu_info where menu_type = " + field_manager_type + " and menu_operate3 like '%" + map_ID + "%'";

        logger.debug("根据演武管理员类型和所在地图id来确定进入点=" + sql);
        String menu_operate3 = "";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                menu_operate3 = rs.getString("menu_operate3");
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        } finally {
            dbConn.closeConn();
        }
        return menu_operate3;
    }

    /**
     * 在旗杆npc死亡后，将旗杆的阵营转化为攻击旗杆胜利者的阵营,也就是对立阵营
     *
     * @param npcID
     * @param camp
     */
    public void updateMastCamp(int menu_type, int camp, int map) {
        String sql = "update operate_menu_info set menu_camp = " + camp + " where menu_type = " + menu_type + " and menu_map =" + map;
        logger.debug("旗杆npc死亡转化阵营=" + sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        } finally {
            dbConn.closeConn();
        }
    }

    /**
     * 在招魂幡死亡后,将其的的所属帮派转化
     *
     * @param npcID
     * @param camp
     */
    public int updateZHAOHUN(String sceneId, String menuOperate1, int tongPk) {
        menuOperate1 = menuOperate1 + ",1";
        int menu_id = 0;
        String sql = "update operate_menu_info set menu_operate3 = '" + tongPk + "' where menu_map = " + sceneId + " and menu_operate1 like '%" + menuOperate1 + "%'";
        logger.debug("旗杆npc死亡转化阵营=" + sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        }

        String sql2 = "select id from operate_menu_info where menu_map = " + sceneId + " and menu_operate1 ='" + menuOperate1 + "'";
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql2);
            if (rs.next()) {
                menu_id = rs.getInt("id");
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        } finally {
            dbConn.closeConn();
        }

        return menu_id;
    }


    /**
     * 招魂幡的初始化
     *
     * @param npcID
     * @param camp
     */
    public void updateZHAOHUN() {
        String sql = "update operate_menu_info set menu_operate3 = '-1' where menu_type = " + MenuType.ZHAOHUN;
        logger.debug("招魂幡的初始化=" + sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        } finally {
            dbConn.closeConn();
        }
    }

    /**
     * 获得菜单 依靠类型和特殊类型3
     *
     * @param menuType
     * @param menuoperate3
     * @return
     */
    public List<OperateMenuVO> getOperateMenuByTypeAndOperate3(int menuType, String menuoperate3) {
        String sql = "SELECT * FROM `operate_menu_info` WHERE `menu_type` = " + menuType + " AND `menu_operate3` = '" + menuoperate3 + "'";
        List<OperateMenuVO> list = new ArrayList<OperateMenuVO>();
        OperateMenuVO menu = null;
        logger.debug(sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                menu = new OperateMenuVO();
                menu.setId(rs.getInt("id"));
                menu.setMenuName(StringUtil.isoToGBK(rs.getString("menu_name")));
                menu.setMenuMap(rs.getInt("menu_map"));
                menu.setMenuType(rs.getInt("menu_type"));
                menu.setMenuImg(rs.getString("menu_img"));
                menu.setMenuDialog(StringUtil.isoToGBK(rs.getString("menu_dialog")));
                menu.setMenuCamp(rs.getInt("menu_camp"));

                menu.setMenuOperate1(rs.getString("menu_operate1"));
                menu.setMenuOperate2(rs.getString("menu_operate2"));
                menu.setMenuOperate3(rs.getString("menu_operate3"));

                menu.setMenuTimeBegin(rs.getString("menu_time_begin"));
                menu.setMenuTimeEnd(rs.getString("menu_time_end"));
                menu.setMenuDayBegin(rs.getString("menu_day_begin"));
                menu.setMenuDayEnd(rs.getString("menu_day_end"));
                menu.setMenuFatherId(rs.getInt("menu_father_id"));
                menu.setMenuTasksId(rs.getString("menu_tasks_id"));
                menu.setMenuOperate4(rs.getInt("menu_operate4"));
                list.add(menu);
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        } finally {
            dbConn.closeConn();
        }
        return list;
    }

    private List<OperateMenuVO> getMenu(ResultSet rs) throws SQLException {
        List<OperateMenuVO> list = new ArrayList<OperateMenuVO>();
        if (rs != null) {
            while (rs.next()) {
                OperateMenuVO menu = new OperateMenuVO();
                menu.setId(rs.getInt("id"));
                menu.setMenuName(StringUtil.isoToGBK(rs.getString("menu_name")));
                menu.setMenuMap(rs.getInt("menu_map"));
                menu.setMenuType(rs.getInt("menu_type"));
                menu.setMenuImg(rs.getString("menu_img"));
                menu.setMenuDialog(StringUtil.isoToGBK(rs.getString("menu_dialog")));
                menu.setMenuCamp(rs.getInt("menu_camp"));

                menu.setMenuOperate1(rs.getString("menu_operate1"));
                menu.setMenuOperate2(rs.getString("menu_operate2"));
                menu.setMenuOperate3(rs.getString("menu_operate3"));

                menu.setMenuTimeBegin(rs.getString("menu_time_begin"));
                menu.setMenuTimeEnd(rs.getString("menu_time_end"));
                menu.setMenuDayBegin(rs.getString("menu_day_begin"));
                menu.setMenuDayEnd(rs.getString("menu_day_end"));

                menu.setMenuFatherId(rs.getInt("menu_father_id"));
                menu.setMenuTasksId(rs.getString("menu_tasks_id"));
                menu.setMenuTaskFlag(rs.getInt("menu_task_flag"));
                menu.setMenuOperate4(rs.getInt("menu_operate4"));

                menu.setWeekStr(rs.getString("week_str"));
                list.add(menu);
            }
        }
        return list;
    }

    public List<OperateMenuVO> findAll_Sheare_menu() {
        String sql = "SELECT * FROM `operate_menu_info` o where o.menu_type = " + MenuType.SHEARE;
        List<OperateMenuVO> list = new ArrayList<OperateMenuVO>();
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            logger.debug(sql);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            list = getMenu(rs);
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
            return list;
        }
    }

    public void updateOperateMenuMenpaiConstant(String menu_id, String menu_name) {
        String sql = "update operate_menu_info set menu_name= '" + menu_name + "' where id=" + menu_id;
        logger.debug(sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        } finally {
            dbConn.closeConn();
        }
    }

    public void updateOperateMenuMenpaiNpc(String menu_id, int menu_operate4) {
        String sql = "update operate_menu_info set menu_operate4=" + menu_operate4 + " where id=" + menu_id;
        logger.debug(sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        } finally {
            dbConn.closeConn();
        }
    }


}
