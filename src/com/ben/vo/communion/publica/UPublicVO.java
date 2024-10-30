/**
 * 
 */
package com.ben.vo.communion.publica;

/**
 * @author 侯浩军
 * 
 * 5:59:22 PM
 */
public class UPublicVO {
	/** 公共频道id */
	private int puPk;
	/** 角色id */
	private int pPk;
	/** 角色名称 */
	private String pName;
	/** 公共聊天类容 */
	private String puTitle;
	/** 创建时间 */
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
