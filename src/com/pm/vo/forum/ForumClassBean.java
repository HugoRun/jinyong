package com.pm.vo.forum;

public class ForumClassBean {

	/**论坛分类ID:类别主键*/
	private int classID;
	/**论坛分类父ID，当为0时表示顶级分类*/
	private int fid	;
	/**版主ID*/
	private int UserID	;
	/**版主名称*/
	private String UserName;
	/**论坛分类名称*/
	private String className;
	//论坛分类简称
	private String smallName;
	/**论坛分类创建时间*/
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
