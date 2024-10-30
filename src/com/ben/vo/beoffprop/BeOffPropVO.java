/**
 *
 */
package com.ben.vo.beoffprop;

/**
 * @author HHJ
 *
 */
public class BeOffPropVO {
    /** 主键id */
    private int beId;
    /** 道具名称 */
    private String propName;
    /** 道具描述 */
    private String propDisplay;
    /** 所需元宝 */
    private String propMoney;
    /** 道具时间 小时计算 */
    private String propTime;

    public int getBeId() {
        return beId;
    }

    public void setBeId(int beId) {
        this.beId = beId;
    }

    public String getPropDisplay() {
        return propDisplay;
    }

    public void setPropDisplay(String propDisplay) {
        this.propDisplay = propDisplay;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getPropMoney() {
        return propMoney;
    }

    public void setPropMoney(String propMoney) {
        this.propMoney = propMoney;
    }

    public String getPropTime() {
        return propTime;
    }

    public void setPropTime(String propTime) {
        this.propTime = propTime;
    }

}
