/**
 * 
 */
package com.ben.vo.rolebeoff;

/**
 * @author HHJ 玩家离线记录
 */
public class RoleBeOffVO
{
	/** 主键id */
	private int offId;
	/** 玩家ID */
	private int pPk;
	/** 离线时间 */
	private String beOffTime;
	/** 离线时间分钟 */
	private String alreadyTime;
	/** 离线经验 */
	private String beOffExp;
	/** 道具累积时间 就是可共领取经验的时间 */
	private String propCumulateTime;

	public String getAlreadyTime()
	{
		return alreadyTime;
	}

	public void setAlreadyTime(String alreadyTime)
	{
		this.alreadyTime = alreadyTime;
	}

	public int getOffId()
	{
		return offId;
	}

	public void setOffId(int offId)
	{
		this.offId = offId;
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

	public String getPropCumulateTime()
	{
		return propCumulateTime;
	}

	public void setPropCumulateTime(String propCumulateTime)
	{
		this.propCumulateTime = propCumulateTime;
	}

}
