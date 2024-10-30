package com.lw.vo.gamesystemstatistics;

public class GameSystemStatisticsVO
{
	/** 主键 */
	private int gameSystemStatisticsID;
	/** 物品ID */
	private int propID;
	/** 物品类型 */
	private int propType;
	/** 物品数量 */
	private int propNum;
	/** 物品获得途径类型 */
	private String propApproachType;
	/** 判断物品是消耗得到还是库存 */
	private String propApproach;
	/** 日期 */
	private String date;
	/** 时间 */
	private String time;

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
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

	public int getPropNum()
	{
		return propNum;
	}

	public void setPropNum(int propNum)
	{
		this.propNum = propNum;
	}

	public String getPropApproachType()
	{
		return propApproachType;
	}

	public void setPropApproachType(String propApproachType)
	{
		this.propApproachType = propApproachType;
	}

	public String getPropApproach()
	{
		return propApproach;
	}

	public void setPropApproach(String propApproach)
	{
		this.propApproach = propApproach;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public int getGameSystemStatisticsID()
	{
		return gameSystemStatisticsID;
	}

	public void setGameSystemStatisticsID(int gameSystemStatisticsID)
	{
		this.gameSystemStatisticsID = gameSystemStatisticsID;
	}
}
