/**
 * 
 */
package com.ben.vo.communion.camp;

/**
 * @author 侯浩军
 * 
 * 7:33:53 PM
 */
public class CampVO {
	/** 公共频道id */
	private int cPk;
	/** 角色id */
	private int pPk;
	/** 角色名称 */
	private String pNname;
	/** 公共聊天类容 */
	private String cTitle;
	/** 所属阵营 */
	private int cBelong;
	/** 创建时间 */
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
