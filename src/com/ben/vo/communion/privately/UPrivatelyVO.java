/**
 * 
 */
package com.ben.vo.communion.privately;

/**
 * @author 侯浩军
 * 
 * 6:03:55 PM
 */
public class UPrivatelyVO {
	/** 公共频道id */
	private int upPk;
	/** 发送角色id */
	private int pPk;
	/** 发送角色名称 */
	private String pName;
	/** 接收角色id */
	private int pPkBy;
	/** 接收角色名称 */
	private String pNameBy;
	/** 公共聊天类容 */
	private String upTitle;
	/** 创建时间 */
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
