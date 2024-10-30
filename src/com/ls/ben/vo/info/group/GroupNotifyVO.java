package com.ls.ben.vo.info.group;

import java.util.Date;

/**
 * 功能:组队通知u_groupnotify_info
 * 
 * @author 刘帅 3:47:01 PM
 */
public class GroupNotifyVO {

	

	/** id */
	private int nPk;
	/** 被通知的玩家id */
	private int notifyedPk;
	/** 产生通知的玩家id */
	private int createNotifyPk;
	/** 通知的内容* */
	private String notifyContent;
	/** 通知类型:1:通知有人申请组队；2:通知队伍解散；3.通知对方同意组队；4:。。。。。。。。。。。** */
	private int notifyType;
	/** 创建时间 */
	private Date createTime;

	public int getNPk() {
		return nPk;
	}

	public void setNPk(int pk) {
		nPk = pk;
	}

	public int getNotifyedPk() {
		return notifyedPk;
	}

	public void setNotifyedPk(int notifyedPk) {
		this.notifyedPk = notifyedPk;
	}

	public int getCreateNotifyPk() {
		return createNotifyPk;
	}

	public void setCreateNotifyPk(int createNotifyPk) {
		this.createNotifyPk = createNotifyPk;
	}

	public String getNotifyContent() {
		return notifyContent;
	}

	public void setNotifyContent(String notifyContent) {
		this.notifyContent = notifyContent;
	}

	public int getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(int notifyType) {
		this.notifyType = notifyType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
