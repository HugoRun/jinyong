package com.ls.ben.vo.info.partinfo;

import java.util.Date;

/**
 * 功能:u_wrap_info
 * 
 * @author 刘帅 9:13:48 AM
 */
public class PlayerWrapVO {

	/** 角色包袱表 */
	private int wPk;
	/** 创建人员信息id */
	private int uPk;
	/** 角色id */
	private int pPk;
	/** 包袱分类 */
	private int wType;
	/** 包裹格数 */
	private int wNumber;
	/** 包裹物品 */
	private String wArticle;
	/** 创建时间 */
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
