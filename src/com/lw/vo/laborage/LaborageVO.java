package com.lw.vo.laborage;

public class LaborageVO
{
	/** 时间最小值 */
	private int minTime;
	/** 时间最大值 */
	private int maxTime;
	/** 物品的集合 */
	private String laborageBonus;

	public String getLaborageBonus()
	{
		return laborageBonus;
	}

	public void setLaborageBonus(String laborageBonus)
	{
		this.laborageBonus = laborageBonus;
	}

	public int getMinTime()
	{
		return minTime;
	}

	public void setMinTime(int minTime)
	{
		this.minTime = minTime;
	}

	public int getMaxTime()
	{
		return maxTime;
	}

	public void setMaxTime(int maxTime)
	{
		this.maxTime = maxTime;
	}

}
