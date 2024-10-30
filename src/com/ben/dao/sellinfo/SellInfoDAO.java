package com.ben.dao.sellinfo;

import com.ben.vo.sellinfo.SellInfoVO;
import com.pub.db.mysql.SqlData;

import java.sql.ResultSet;

/**
 * @author 侯浩军
 * <p>
 * 2:23:55 PM
 */
public class SellInfoDAO {
    SqlData con;

    /**
     * 添加交易信息
     *
     * @param pPk
     * @param pByPk
     * @param ssilverMoney
     * @param sCopperMoney
     * @param sellMode
     * @param createTime
     */
    public void addSelleInfo(String pPk, String pByPk, String ssilverMoney, String sCopperMoney, int sellMode, String createTime) {
        try {
            con = new SqlData();
            String sql = "INSERT INTO `u_sell_info`(s_pk, p_pk, p_by_pk, s_wp_silver_money, s_wp_copper_money, sell_mode, create_time) VALUES (null, '" + pPk + "', '" + pByPk + "', '" + ssilverMoney + "', '" + sCopperMoney + "', '" + sellMode + "', '" + createTime + "')";
            con.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    // 请求交易方修改交易表种的钱和状态
    public void getSellArmAdd(int pPk, String ByPk, String sWuping, int sWpType, int sWpNumber, String sWpSilverMoney, String sWpCopperMoney, int sellMode, String create_time) {
        try {
            con = new SqlData();
            String sql = "INSERT INTO `u_sell_info` VALUES(null, '" + pPk + "', '" + ByPk + "', '" + sWuping + "', '" + sWpType + "', '" + sWpNumber + "', '" + sWpSilverMoney + "', '" + sWpCopperMoney + "', '" + sellMode + "', '" + create_time + "')";
            con.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    // 通过注册ID 去找角色名是否存在
    public boolean getSelleInfoVs(String pPk) {
        try {
            con = new SqlData();
            String sql = "SELECT * FROM `u_sell_info` WHERE `p_by_pk` = " + pPk;
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
     * 查询交易类型 每次只查询一条
     *
     * @return
     */
    public SellInfoVO getSellMode(int pByPk) {
        try {
            con = new SqlData();
            String sql = "SELECT s_pk, sell_mode FROM `u_sell_info` WHERE p_by_pk = '" + pByPk + "' LIMIT 1";
            ResultSet rs = con.query(sql);
            SellInfoVO vo = null;
            if (rs.next()) {
                vo = new SellInfoVO();
                vo.setSPk(rs.getInt("s_pk"));
                vo.setSellMode(rs.getInt("sell_mode"));
            }
            return vo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return null;
    }

    public boolean isSellInfoIdBy(int pByPk) {
        try {
            con = new SqlData();
            String sql = "SELECT * FROM `u_sell_info` WHERE p_by_pk = " + pByPk;
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

    // 查询交易被请求着的ID号
    public int getSelleInfopPk(String pByPk) {
        try {
            con = new SqlData();
            String sql = "SELECT * FROM `u_sell_info` WHERE `p_by_pk` = " + pByPk;
            ResultSet rs = con.query(sql);
            SellInfoVO vo = new SellInfoVO();
            while (rs.next()) {
                vo.setPPk(rs.getInt("p_pk"));
            }
            return vo.getPPk();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return 0;
    }

    public void getSelleInfoDelete(String pByPk) {
        try {
            con = new SqlData();
            String sql = "DELETE FROM `u_sell_info` WHERE `p_by_pk` = " + pByPk;
            con.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    // 请求交易方修改交易表种的钱和状态
    public void getSellMoneyUpdate(String SilverMoney, String sCopperMoney, String SfOk, String pPk, String ByPk) {
        try {
            con = new SqlData();
            String sql = "UPDATE `u_sell_info` SET `s_silver_money` = '" + SilverMoney + "', s_copper_money = '" + sCopperMoney + "', s_sf_ok = '" + SfOk + "' WHERE p_pk = '" + pPk + "' AND p_by_pk = '" + ByPk + "'";
            con.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    // 请求交易方修改交易表种的钱和状态
    public void getSellWuPingUpdate(String zbid, String fenglei, String number, String Silver, String sCopper, String SfOk, String pPk, String ByPk) {
        try {
            con = new SqlData();
            String sql = "UPDATE `u_sell_info` SET s_wuping = '" + zbid + "', s_wp_type = '" + fenglei + "', s_wp_number = '" + number + "', s_wp_silver_money = '" + Silver + "', s_wp_copper_money = '" + sCopper + "', s_sf_ok = '" + SfOk + "' WHERE p_pk = '" + pPk + "' AND `p_by_pk` = '" + ByPk + "'";
            con.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    // 接受修改交易表种的钱和状态
    public void getSellByMoneyUpdate(String SilverMoney, String sCopperMoney, String sPk) {
        try {
            con = new SqlData();
            String sql = "UPDATE `u_sell_info` SET s_silver_money = '" + SilverMoney + "', s_copper_money = '" + sCopperMoney + "' WHERE s_pk = '" + sPk + "' ";
            con.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    /**
     * 查询一条交易记录
     *
     * @param sPk
     * @return
     */
    public SellInfoVO getSellView(int sPk) {
        try {
            con = new SqlData();
            String sql = "SELECT * FROM `u_sell_info` WHERE s_pk = " + sPk;
            ResultSet rs = con.query(sql);
            SellInfoVO vo = null;
            if (rs.next()) {
                vo = new SellInfoVO();
                vo.setSPk(rs.getInt("s_pk"));
                vo.setPPk(rs.getInt("p_pk"));
                vo.setPByPk(rs.getInt("p_by_pk"));
                vo.setSWuping(rs.getInt("s_wuping"));
                vo.setSWpType(rs.getInt("s_wp_type"));
                vo.setSWpNumber(rs.getInt("s_wp_number"));
                vo.setSWpSilverMoney(rs.getInt("s_wp_silver_money"));
                vo.setCreateTime(rs.getString("create_time"));
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
     * 删除交易信息
     *
     * @param sPk
     */
    public void deleteSelleInfo(String sPk) {
        try {
            con = new SqlData();
            String sql = "DELETE FROM `u_sell_info` WHERE s_pk = " + sPk;
            con.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    /**
     * 寻找在卖出表中有没有某人的某物
     *
     * @param pPk
     * @param goodsId   物品id
     * @param s_wp_type 物品类型
     * @return 如果为空则说明没有
     */

    public String getSellExistByPPkAndGoodsId(String pPk, String goodsId, int s_wp_type) {
        int exist = 0;
        try {

            con = new SqlData();
            String sql = "SELECT COUNT(1) AS exist1 FROM `u_sell_info` WHERE s_wuping = " + goodsId + " AND p_pk = " + pPk + " AND s_wp_type = " + s_wp_type;
            ResultSet rs = con.query(sql);
            if (rs.next()) {
                exist = rs.getInt("exist1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        if (exist == 0) {
            return "";
        } else {
            return "对不起,您的此物品已经给了他人，请等待对方做出回应再处理!";
        }
    }

    /**
     * 删除宠物表超时数据
     *
     * @param time
     */
    public void deleteSellPetTime(String time) {
        try {
            con = new SqlData();
            String sql = "DELETE FROM `u_pet_sell` WHERE `create_time` < '" + time + "'";
            con.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    /**
     * 删除物品交易表超时数据
     *
     * @param time
     */
    public void deleteSellPropTime(String time) {
        try {
            con = new SqlData();
            String sql = "DELETE FROM `u_sell_info` WHERE `create_time` < '" + time + "'";
            con.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }
}
