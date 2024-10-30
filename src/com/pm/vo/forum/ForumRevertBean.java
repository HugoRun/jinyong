package com.pm.vo.forum;

public class ForumRevertBean {

	private int id; /**����ID:�������*/
	private int fid;/**����ID*/
	private int UserID;/**������ID*/
	private String UserName;/**�������û���*/
	private String content; /**����*/
	private String addTime;/**���ʱ��*/
	
	private String nickName;
	private String userImage;
	private int VIP;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFid() {
		return fid; 
	}
	public void setFid(int fid) {
		this.fid = fid;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAddTime() {
		return addTime;
	}
	public String getSmallAddTime() {
		if(addTime!=null&&addTime.length()>16)
		{
			return addTime.substring(5,16);
		}else
		{
			return addTime;
		}
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserImage() {
		return userImage;
	}
	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
	public int getVIP() {
		return VIP;
	}
	public void setVIP(int vip) {
		VIP = vip;
	}
	
	public String getSmallMobile() {
		if(UserName!=null&&UserName.length()==11)
		{
			return UserName.substring(0,3)+"������"+UserName.substring(8);
		}
		return UserName;
	}
}
