/**
 * 
 */
package com.ben.vo.communion.group;

/**
 * @author 侯浩军
 * 
 * 6:01:12 PM
 */
public class UGroupVO {
	/** 公共频道id */
	private int ugPk;
	/** 角色id */
	private int pPk;
	/** 角色名称 */
	private String pName;
	/** 公共聊天类容 */
	private String ugTitle;
	/** 所属组队 */
	private int ugBelong;
	/** 创建时间 */
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
