package com.pm.vo.forum;

public class ForumBean {

	private int id; /**����ID:�������*/
	private int classID;/**����ID*/
	private int UserID;/**������ID*/
	private String UserName;/**�������û���*/
	private String title;/**����*/
	private String content; /**����*/	
	private int readNum	;/**�Ķ�����*/
	private int revertNum;/**�ظ�����*/
	private int vouch;/**�Ƿ��Ƽ���*/
	private int taxis;/**���������ǣ������Ƿ� �ö�*/
	private String addTime;/**���ʱ��*/
	
	private String className;/**��̳��������*/
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getClassID() {
		return classID;
	}
	public void setClassID(int classID) {
		this.classID = classID;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getReadNum() {
		return readNum;
	}
	public void setReadNum(int readNum) {
		this.readNum = readNum;
	}
	public int getRevertNum() {
		return revertNum;
	}
	public void setRevertNum(int revertNum) {
		this.revertNum = revertNum;
	}
	public int getVouch() {
		return vouch;
	}
	public void setVouch(int vouch) {
		this.vouch = vouch;
	}
	public int getTaxis() {
		return taxis;
	}
	public void setTaxis(int taxis) {
		this.taxis = taxis;
	}
	public String getAddTime() {
		if(addTime==null)
			return "";
		else if(addTime.length()>16)
		{
			return addTime.substring(5,16);
		}
		return addTime;
	}
	public String getRealAddTime() {
		return addTime;
	}
	public String getSmallAddTime() {
		if(addTime!=null&&addTime.length()>16)
		{
			return addTime.substring(2,16);
		}else
		{
			return addTime;
		}
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSmallMobile() {
		if(UserName!=null&&UserName.length()==11)
		{
			return UserName.substring(0,3)+"������"+UserName.substring(8);
		}
		return UserName;
	}
}
