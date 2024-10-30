package com.lw.vo.laborage;

public class PlayerLaborageVO
{
	/** 玩家时间表 */
	private int laborageId;
	/** 玩家PPK */
	private int pPk;
	/** 玩家活动时间统计表 */
	private int laborageThisTime;
	/** 玩家上周总时间表 */
	private int laborageOldtime;
	/** 领工资的领取字节 */
	private int laboragecatch;

	public int getLaborageId()
	{
		return laborageId;
	}

	public void setLaborageId(int laborageId)
	{
		this.laborageId = laborageId;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pPk)
	{
		this.pPk = pPk;
	}

	public int getLaborageThisTime()
	{
		return laborageThisTime;
	}

	public void setLaborageThisTime(int laborageThisTime)
	{
		this.laborageThisTime = laborageThisTime;
	}

	public int getLaborageOldtime()
	{
		return laborageOldtime;
	}

	public void setLaborageOldtime(int laborageOldtime)
	{
		this.laborageOldtime = laborageOldtime;
	}

	public int getLaboragecatch()
	{
		return laboragecatch;
	}

	public void setLaboragecatch(int laboragecatch)
	{
		this.laboragecatch = laboragecatch;
	}

}
