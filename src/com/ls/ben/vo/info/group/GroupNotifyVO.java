package com.ls.ben.vo.info.group;

import java.util.Date;

/**
 * ����:���֪ͨu_groupnotify_info
 * 
 * @author ��˧ 3:47:01 PM
 */
public class GroupNotifyVO {

	

	/** id */
	private int nPk;
	/** ��֪ͨ�����id */
	private int notifyedPk;
	/** ����֪ͨ�����id */
	private int createNotifyPk;
	/** ֪ͨ������* */
	private String notifyContent;
	/** ֪ͨ����:1:֪ͨ����������ӣ�2:֪ͨ�����ɢ��3.֪ͨ�Է�ͬ����ӣ�4:����������������������** */
	private int notifyType;
	/** ����ʱ�� */
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
