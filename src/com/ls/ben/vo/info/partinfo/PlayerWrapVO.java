package com.ls.ben.vo.info.partinfo;

import java.util.Date;

/**
 * ����:u_wrap_info
 * 
 * @author ��˧ 9:13:48 AM
 */
public class PlayerWrapVO {

	/** ��ɫ������ */
	private int wPk;
	/** ������Ա��Ϣid */
	private int uPk;
	/** ��ɫid */
	private int pPk;
	/** �������� */
	private int wType;
	/** �������� */
	private int wNumber;
	/** ������Ʒ */
	private String wArticle;
	/** ����ʱ�� */
	private Date createTime;
	public int getWPk() {
		return wPk;
	}
	public void setWPk(int pk) {
		wPk = pk;
	}
	public int getUPk() {
		return uPk;
	}
	public void setUPk(int pk) {
		uPk = pk;
	}
	public int getPPk() {
		return pPk;
	}
	public void setPPk(int pk) {
		pPk = pk;
	}
	public int getWType() {
		return wType;
	}
	public void setWType(int type) {
		wType = type;
	}
	public int getWNumber() {
		return wNumber;
	}
	public void setWNumber(int number) {
		wNumber = number;
	}
	public String getWArticle() {
		return wArticle;
	}
	public void setWArticle(String article) {
		wArticle = article;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
