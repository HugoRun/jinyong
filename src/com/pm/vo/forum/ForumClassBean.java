package com.pm.vo.forum;

public class ForumClassBean {

	/**��̳����ID:�������*/
	private int classID;
	/**��̳���ุID����Ϊ0ʱ��ʾ��������*/
	private int fid	;
	/**����ID*/
	private int UserID	;
	/**��������*/
	private String UserName;
	/**��̳��������*/
	private String className;
	//��̳������
	private String smallName;
	/**��̳���ഴ��ʱ��*/
	private String addTime;
	
	
	public int getClassID() {
		return classID;
	}
	public void setClassID(int classID) {
		this.classID = classID;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getSmallName() {
		return smallName;
	}
	public void setSmallName(String smallName) {
		this.smallName = smallName;
	}
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	
	
}
