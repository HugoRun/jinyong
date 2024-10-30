package com.ls.ben.vo.info.npc;

import com.ls.model.user.RoleEntity;

/**
 * 功能:npcshop
 *
 * @author 刘帅
 * 2:53:43 PM
 */
public class NpcShopVO {

    /**
     * NPC卖出id
     */
    private int npcshopId;
    /**
     * 菜单id
     */
    private int npcId;
    /**
     * 物品类型,表示是哪个表
     */
    private int goodsType;
    /**
     * 物品id
     */
    private int goodsId;
    /**
     * 物品名字
     */
    private String goodsName;
    /**
     * 价钱
     */
    private int npcShopGoodsbuy;

    /**
     * 得到玩家购买的实际价钱
     *
     * @return
     */
    public int getPrice(RoleEntity roleInfo) {
        int price = npcShopGoodsbuy;
        int tax = roleInfo.getBasicInfo().getSceneInfo().getMap().getBarea().getTax();
        price *= (100 + tax) / 100;

        if (roleInfo.isRedname())//红名玩家游戏商店购买药品、卷轴、装备等，以及修理装备价格翻倍
        {
            price *= 2;
        }
        return price;
    }

    public int getNpcshopId() {
        return npcshopId;
    }

    public void setNpcshopId(int npcshopId) {
        this.npcshopId = npcshopId;
    }

    public int getNpcId() {
        return npcId;
    }

    public void setNpcId(int npcId) {
        this.npcId = npcId;
    }

    public int getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getNpcShopGoodsbuy() {
        return npcShopGoodsbuy;
    }

    public void setNpcShopGoodsbuy(int npcShopGoodsbuy) {
        this.npcShopGoodsbuy = npcShopGoodsbuy;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
