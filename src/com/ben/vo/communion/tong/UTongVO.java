/**
 * 
 */
package com.ben.vo.communion.tong;

/**
 * @author ��ƾ�
 * 
 * 6:02:34 PM
 */
public class UTongVO {
	/** ����Ƶ��id */
	private int utPk;
	/** ��ɫid */
	private int pPk;
	/** ��ɫ���� */
	private String pName;
	/** ������������ */
	private String utTitle;
	/** ������� */
	private int utBelong;
	/** ����ʱ�� */
	private String createTime;

	public int getUtBelong() {
		return utBelong;
	}

	public void setUtBelong(int utBelong) {
		this.utBelong = utBelong;
	}

	public int getUtPk() {
		return utPk;
	}

	public void setUtPk(int utPk) {
		this.utPk = utPk;
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

	public String getUtTitle() {
		return utTitle;
	}

	public void setUtTitle(String utTitle) {
		this.utTitle = utTitle;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
