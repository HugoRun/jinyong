/**
 * 
 */
package com.ben.vo.communion.system;

/**
 * @author ��ƾ�
 * 
 * 6:06:16 PM
 */
public class USystemVO {
	/** ����Ƶ��id */
	private int usPk;
	/** ��ɫid */
	private int pPk;
	/** ��ɫ���� */
	private String pName;
	/** ������������ */
	private String usTitle;
	/** ����ʱ�� */
	private String createTime;

	public int getUsPk() {
		return usPk;
	}

	public void setUsPk(int usPk) {
		this.usPk = usPk;
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

	public String getUsTitle() {
		return usTitle;
	}

	public void setUsTitle(String usTitle) {
		this.usTitle = usTitle;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
