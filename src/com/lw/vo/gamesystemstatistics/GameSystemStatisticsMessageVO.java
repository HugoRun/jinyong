package com.lw.vo.gamesystemstatistics;

public class GameSystemStatisticsMessageVO
{
	/** ���� */
	private int houtaiID;
	/** ��ƷID */
	private int propID;
	/** ��Ʒ���� */
	private int propType;
	/** ���� */
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