package com.lw.vo.laborage;

public class LaborageVO
{
	/** ʱ����Сֵ */
	private int minTime;
	/** ʱ�����ֵ */
	private int maxTime;
	/** ��Ʒ�ļ��� */
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
