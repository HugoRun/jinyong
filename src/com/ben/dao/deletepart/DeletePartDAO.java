/**
 *
 */
package com.ben.dao.deletepart;

import com.pub.db.mysql.SqlData;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 侯浩军
 * <p>
 * 2:47:38 PM
 */
public class DeletePartDAO {
    SqlData dbc;
    Logger logger = Logger.getLogger(DeletePartDAO.class);

    public int DeletePart(int pPk) {

        try {
            dbc = new SqlData();
            dbc.begin();
            dbc.update("DELETE FROM `u_part_info` WHERE p_pk = " + pPk);
            logger.info("玩家视野是在这里删除的 ************  ");
            dbc.update("DELETE FROM `u_part_equip` WHERE p_pk = " + pPk);
            dbc.update("DELETE FROM `u_warehouse_info` WHERE p_pk = " + pPk);

            dbc.update("DELETE FROM `p_pet_info` WHERE p_pk = " + pPk);
            dbc.update("DELETE FROM `u_pet_sell` WHERE p_pk = " + pPk);
            dbc.update("DELETE FROM `u_sell_info` WHERE p_pk = " + pPk);

            dbc.update("DELETE FROM `n_dropgoods_info` WHERE p_pk = " + pPk);
            dbc.update("DELETE FROM `n_dropExpMoney_info` WHERE p_pk = " + pPk);

            dbc.update("DELETE FROM `u_shortcut_info` WHERE p_pk = " + pPk);
            dbc.update("DELETE FROM `u_propgroup_info` WHERE p_pk = " + pPk);
            dbc.update("DELETE FROM `u_coordinate_info` WHERE p_pk = " + pPk);
            dbc.update("DELETE FROM `u_group_info` WHERE p_pk = " + pPk);
            dbc.update("DELETE FROM `u_skill_info` WHERE p_pk = " + pPk);

            dbc.update("DELETE FROM `u_time_control` WHERE p_pk = " + pPk);
            dbc.update("DELETE FROM `u_task` WHERE p_pk = " + pPk);
            dbc.update("DELETE FROM `u_task_complete` WHERE p_pk = " + pPk);

            dbc.update("DELETE FROM `u_auction` WHERE p_pk = " + pPk);
            dbc.update("DELETE FROM `u_auction_info` WHERE p_pk = " + pPk);

            dbc.update("DELETE FROM `u_friend` WHERE p_pk = " + pPk);
            dbc.update("DELETE FROM `u_blacklist` WHERE p_pk = " + pPk);
            dbc.update("DELETE FROM `s_system_info` WHERE p_pk = " + pPk);

            dbc.update("DELETE FROM `u_auction_pet` WHERE p_pk = " + pPk);
            dbc.update("DELETE FROM `u_auctionpet_info` WHERE p_pk = " + pPk);
            dbc.update("DELETE FROM `s_setting_info` WHERE p_pk = " + pPk);
            dbc.update("DELETE FROM `u_warehouse_equip` WHERE p_pk = " + pPk);

            dbc.update("DELETE FROM `u_quiz_info` WHERE p_pk = " + pPk);
            dbc.update("DELETE FROM `u_special_prop` WHERE p_pk = " + pPk);
            dbc.commit();// 提交JDBC事务
            dbc.close();
            return 1;
        } catch (Exception exc) {
            dbc.rollback();
            exc.printStackTrace();
            dbc.close();
            return -1;
        }
    }

    /**
     * 确定某角色的删除时间，并将删除标志置为1，即有效位.
     *
     * @param pk       pk
     * @param u_pk     u_pk
     * @param isRookie isRookie
     * @return 返回1表示删除成功，0表示失败
     */
    public int delete(String pk, String u_pk, boolean isRookie) {
        int result = 0;
        String dateStr = "|" + new Date().getSeconds();
        String sql = "";
        if (!isRookie) {
            sql = "UPDATE u_part_info SET delete_flag = 1 ,delete_time = now(),p_name=CONCAT(p_name,'" + dateStr + "') WHERE p_pk = " + pk + " AND u_pk = " + u_pk;
        } else {
            sql = "DELETE FROM u_part_info WHERE p_pk = " + pk + " AND u_pk = " + u_pk;
        }
        try {
            dbc = new SqlData();
            result = dbc.update(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return result;
    }

    /**
     * 恢复某角色的删除时间，并将删除标志置为0，即无效位.
     *
     * @param pk   pk
     * @param u_pk u_pk
     */
    public void sureResumeTime(String pk, String u_pk) {
        String sql = "UPDATE `u_part_info` SET `delete_flag` = 0 WHERE p_pk = " + pk + " AND u_pk = " + u_pk;
        try {
            dbc = new SqlData();
            dbc.update(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }

    }

    /**
     * 恢复某角色的删除时间，并将删除标志置为0，即无效位.
     */
    public void deleteByTime() {
        List<Integer> list = new ArrayList<Integer>();
        String sql = "SELECT p_pk FROM u_part_info WHERE delete_flag = 1 AND now() > (delete_time + INTERVAL 1 DAY)";
        try {
            dbc = new SqlData();
            ResultSet rs = dbc.query(sql);
            while (rs.next()) {
                list.add(rs.getInt("p_pk"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }

        if (!list.isEmpty()) {
            for (Integer integer : list) {
                DeletePart(integer);
            }
        }
    }
}
