/**
 * 
 */
package com.ben.vo.avoidpkprop;

/**
 * @author Administrator
 * 
 */
public class AvoidPkPropVO
{
	/** 免PK道具ID */
	private int aPk;
	/** 玩家id */
	private int pPk;
	/** 道具开始时间 */
	private String beginTime;
	/** 道具结束时间 */
	private String endTime;

	public int getAPk()
	{
		return aPk;
	}

	public void setAPk(int pk)
	{
		aPk = pk;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public String getBeginTime()
	{
		return beginTime;
	}

	public void setBeginTime(String beginTime)
	{
		this.beginTime = beginTime;
	}

	public String getEndTime()
	{
		return endTime;
	}

	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}

}
