/**
 * 
 */
package com.ben.vo.beoffprop;

/**
 * @author HHJ
 * 
 */
public class BeOffPropVO
{
	/** ����id */
	private int beId;
	/** �������� */
	private String propName;
	/** �������� */
	private String propDisplay;
	/** ����Ԫ�� */
	private String propMoney;
	/** ����ʱ�� Сʱ���� */
	private String propTime;

	public int getBeId()
	{
		return beId;
	}

	public String getPropDisplay()
	{
		return propDisplay;
	}

	public void setPropDisplay(String propDisplay)
	{
		this.propDisplay = propDisplay;
	}

	public void setBeId(int beId)
	{
		this.beId = beId;
	}

	public String getPropName()
	{
		return propName;
	}

	public void setPropName(String propName)
	{
		this.propName = propName;
	}

	public String getPropMoney()
	{
		return propMoney;
	}

	public void setPropMoney(String propMoney)
	{
		this.propMoney = propMoney;
	}

	public String getPropTime()
	{
		return propTime;
	}

	public void setPropTime(String propTime)
	{
		this.propTime = propTime;
	}

}
