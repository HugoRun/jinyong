/**
 * 
 */
package com.ben.vo.communion.publica;

/**
 * @author ��ƾ�
 * 
 * 5:59:22 PM
 */
public class UPublicVO {
	/** ����Ƶ��id */
	private int puPk;
	/** ��ɫid */
	private int pPk;
	/** ��ɫ���� */
	private String pName;
	/** ������������ */
	private String puTitle;
	/** ����ʱ�� */
	private String createTime;

	public int getPuPk() {
		return puPk;
	}

	public void setPuPk(int puPk) {
		this.puPk = puPk;
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

	public String getPuTitle() {
		return puTitle;
	}

	public void setPuTitle(String puTitle) {
		this.puTitle = puTitle;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
