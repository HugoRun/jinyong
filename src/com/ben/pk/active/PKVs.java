package com.ben.pk.active;

/**
 * @author thomas.lei
 * 功能：PK活动对阵表对应的封装类
 * 27/04/10 PM
 */
public class PKVs {
    private int id;
    private int roleAID; // 角色A的ID
    private String roleAName; // 角色A的名称
    private int roleBID; // 角色B的ID
    private String roleBName; // 角色B的名称
    private int winRoleID; // PK获胜方的ID

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleAID() {
        return roleAID;
    }

    public void setRoleAID(int roleAID) {
        this.roleAID = roleAID;
    }

    public int getRoleBID() {
        return roleBID;
    }

    public void setRoleBID(int roleBID) {
        this.roleBID = roleBID;
    }

    public String getRoleAName() {
        return roleAName;
    }

    public void setRoleAName(String roleAName) {
        this.roleAName = roleAName;
    }

    public String getRoleBName() {
        return roleBName;
    }

    public void setRoleBName(String roleBName) {
        this.roleBName = roleBName;
    }

    public int getWinRoleID() {
        return winRoleID;
    }

    public void setWinRoleID(int winRoleID) {
        this.winRoleID = winRoleID;
    }
}
