package com.lw.vo.gamesystemstatistics;

public class GameSystemStatisticsVO
{
	/** ���� */
	private int gameSystemStatisticsID;
	/** ��ƷID */
	private int propID;
	/** ��Ʒ���� */
	private int propType;
	/** ��Ʒ���� */
	private int propNum;
	/** ��Ʒ���;������ */
	private String propApproachType;
	/** �ж���Ʒ�����ĵõ����ǿ�� */
	private String propApproach;
	/** ���� */
	private String date;
	/** ʱ�� */
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
