/**
 * 
 */
package com.ben.vo.communion.camp;

/**
 * @author ��ƾ�
 * 
 * 7:33:53 PM
 */
public class CampVO {
	/** ����Ƶ��id */
	private int cPk;
	/** ��ɫid */
	private int pPk;
	/** ��ɫ���� */
	private String pNname;
	/** ������������ */
	private String cTitle;
	/** ������Ӫ */
	private int cBelong;
	/** ����ʱ�� */
	private String createTime;

	public int getCPk() {
		return cPk;
	}

	public void setCPk(int pk) {
		cPk = pk;
	}

	public int getPPk() {
		return pPk;
	}

	public void setPPk(int pk) {
		pPk = pk;
	}

	public String getPNname() {
		return pNname;
	}

	public void setPNname(String nname) {
		pNname = nname;
	}

	public String getCTitle() {
		return cTitle;
	}

	public void setCTitle(String title) {
		cTitle = title;
	}

	public int getCBelong() {
		return cBelong;
	}

	public void setCBelong(int belong) {
		cBelong = belong;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
