/**
 * 
 */
package com.ben.vo.communion.privately;

/**
 * @author ��ƾ�
 * 
 * 6:03:55 PM
 */
public class UPrivatelyVO {
	/** ����Ƶ��id */
	private int upPk;
	/** ���ͽ�ɫid */
	private int pPk;
	/** ���ͽ�ɫ���� */
	private String pName;
	/** ���ս�ɫid */
	private int pPkBy;
	/** ���ս�ɫ���� */
	private String pNameBy;
	/** ������������ */
	private String upTitle;
	/** ����ʱ�� */
	private String createTime;

	public int getUpPk() {
		return upPk;
	}

	public void setUpPk(int upPk) {
		this.upPk = upPk;
	}

	public int getPPk() {
		return pPk;
	}

	public void setPPk(int pk) {
		pPk = pk;
	}

	public String getPName() {
		return pName;
	}

	public void setPName(String name) {
		pName = name;
	}

	public int getPPkBy() {
		return pPkBy;
	}

	public void setPPkBy(int pkBy) {
		pPkBy = pkBy;
	}

	public String getPNameBy() {
		return pNameBy;
	}

	public void setPNameBy(String nameBy) {
		pNameBy = nameBy;
	}

	public String getUpTitle() {
		return upTitle;
	}

	public void setUpTitle(String upTitle) {
		this.upTitle = upTitle;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
