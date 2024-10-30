/**
 * 
 */
package com.ben.vo.communion.tong;

/**
 * @author 侯浩军
 * 
 * 6:02:34 PM
 */
public class UTongVO {
	/** 公共频道id */
	private int utPk;
	/** 角色id */
	private int pPk;
	/** 角色名称 */
	private String pName;
	/** 公共聊天类容 */
	private String utTitle;
	/** 所属帮会 */
	private int utBelong;
	/** 创建时间 */
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
