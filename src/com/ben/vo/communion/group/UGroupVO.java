/**
 * 
 */
package com.ben.vo.communion.group;

/**
 * @author ��ƾ�
 * 
 * 6:01:12 PM
 */
public class UGroupVO {
	/** ����Ƶ��id */
	private int ugPk;
	/** ��ɫid */
	private int pPk;
	/** ��ɫ���� */
	private String pName;
	/** ������������ */
	private String ugTitle;
	/** ������� */
	private int ugBelong;
	/** ����ʱ�� */
	private String createTime;

	public int getUgBelong() {
		return ugBelong;
	}

	public void setUgBelong(int ugBelong) {
		this.ugBelong = ugBelong;
	}

	public int getUgPk() {
		return ugPk;
	}

	public void setUgPk(int ugPk) {
		this.ugPk = ugPk;
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

	public String getUgTitle() {
		return ugTitle;
	}

	public void setUgTitle(String ugTitle) {
		this.ugTitle = ugTitle;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
