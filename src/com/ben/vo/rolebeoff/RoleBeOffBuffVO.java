/**
 * 
 */
package com.ben.vo.rolebeoff;

/**
 * @author HHJ
 * 离线BUFF
 */
public class RoleBeOffBuffVO
{
	/** 主键id */
	private int bId;
	/** 玩家ID */
	private int pPk;
	/** 上次离线时间 */
	private String beOffTime;
	/** 离线经验 */
	private String beOffExp;

	public int getBId()
	{
		return bId;
	}

	public void setBId(int id)
	{
		bId = id;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public String getBeOffTime()
	{
		return beOffTime;
	}

	public void setBeOffTime(String beOffTime)
	{
		this.beOffTime = beOffTime;
	}

	public String getBeOffExp()
	{
		return beOffExp;
	}

	public void setBeOffExp(String beOffExp)
	{
		this.beOffExp = beOffExp;
	}

}
