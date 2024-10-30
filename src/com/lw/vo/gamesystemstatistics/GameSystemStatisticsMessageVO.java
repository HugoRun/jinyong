package com.lw.vo.gamesystemstatistics;

public class GameSystemStatisticsMessageVO
{
	/** 主键 */
	private int houtaiID;
	/** 物品ID */
	private int propID;
	/** 物品类型 */
	private int propType;
	/** 日期 */
	private String date;

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public int getPropID()
	{
		return propID;
	}

	public void setPropID(int propID)
	{
		this.propID = propID;
	}

	public int getPropType()
	{
		return propType;
	}

	public void setPropType(int propType)
	{
		this.propType = propType;
	}

	public int getHoutaiID()
	{
		return houtaiID;
	}

	public void setHoutaiID(int houtaiID)
	{
		this.houtaiID = houtaiID;
	}
}