package com.ls.ben.vo.info.group;

/**
 * ����:u_group_info
 * @author ��˧
 * 1:15:03 PM
 */
public class GroupInfoVO {
	
	 public static final int MAX_MEMBER_NUM=5;
	
	 /**id*/
	 private int  gPk;
	 /**��ɫid*/
	 private int  pPk;
	 /**
	  * ��Ա����
	  */
	 private String pName;
	 /**�Ƿ��Ƕӳ���1��ʾ�ǣ�0��ʾ����*/
	 
	 /**��ҵȼ�*/
	 private int pGrade;
	 private int  gCaptainPk;
	 
	 /** ������ڵ�ͼid */
	 private int pMap;
	 
	 /** ������ڵ�ͼ�� */
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
