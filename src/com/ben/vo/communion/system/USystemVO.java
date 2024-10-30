/**
 * 
 */
package com.ben.vo.communion.system;

/**
 * @author 侯浩军
 * 
 * 6:06:16 PM
 */
public class USystemVO {
	/** 公共频道id */
	private int usPk;
	/** 角色id */
	private int pPk;
	/** 角色名称 */
	private String pName;
	/** 公共聊天类容 */
	private String usTitle;
	/** 创建时间 */
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
