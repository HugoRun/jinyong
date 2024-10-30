package com.ls.ben.vo.info.group;

/**
 * 功能:u_group_info
 * @author 刘帅
 * 1:15:03 PM
 */
public class GroupInfoVO {
	
	 public static final int MAX_MEMBER_NUM=5;
	
	 /**id*/
	 private int  gPk;
	 /**角色id*/
	 private int  pPk;
	 /**
	  * 成员名字
	  */
	 private String pName;
	 /**是否是队长，1表示是，0表示不是*/
	 
	 /**玩家等级*/
	 private int pGrade;
	 private int  gCaptainPk;
	 
	 /** 玩家所在地图id */
	 private int pMap;
	 
	 /** 玩家所在地图名 */
	 private String pMapName;
	 
	 
	public String getPMapName()
	{
		return pMapName;
	}
	public void setPMapName(String mapName)
	{
		pMapName = mapName;
	}
	public int getPMap()
	{
		return pMap;
	}
	public void setPMap(int map)
	{
		pMap = map;
	}
	public int getGPk() {
		return gPk;
	}
	public void setGPk(int pk) {
		gPk = pk;
	}
	public int getPPk() {
		return pPk;
	}
	public void setPPk(int pk) {
		pPk = pk;
	}
	public int getGCaptainPk() {
		return gCaptainPk;
	}
	public void setGCaptainPk(int captainPk) {
		gCaptainPk = captainPk;
	}
	public String getPName() {
		return pName;
	}
	public void setPName(String name) {
		pName = name;
	}
	public int getPGrade() {
		return pGrade;
	}
	public void setPGrade(int grade) {
		pGrade = grade;
	}

	
}
