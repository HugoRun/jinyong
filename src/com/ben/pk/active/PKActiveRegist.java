package com.ben.pk.active;

import java.util.Date;

/**
 * @author thomas.lei
 * 功能：PK活动报名封装类
 * 27/04/10 PM
 */
public class PKActiveRegist {
    private int id;
    private int roleID; // 角色ID
    private int roleLevel; // 角色等级
    private String roleName; // 角色名称
    private Date registTime; // 报名时间
    private int isWin; // 是否胜利

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(int roleLevel) {
        this.roleLevel = roleLevel;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRoleTime(Date roleTime) {
        this.registTime = roleTime;
    }

    public int getIsWin() {
        return isWin;
    }

    public void setIsWin(int isWin) {
        this.isWin = isWin;
    }

}
