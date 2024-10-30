package com.ls.ben.vo.pkhite;

import java.util.Date;

public class PKHiteVO
{
	private int id;
	private int p_pk;
	private int enemyUpk;
	private int enemyPpk;
	private String enemyName;
	private int enemyGrade;
	private int hitePoint;
	private int generalPkCount;
	private int activePkCount;
	private Date updateTime;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getP_pk()
	{
		return p_pk;
	}
	public void setP_pk(int p_pk)
	{
		this.p_pk = p_pk;
	}
	public int getEnemyPpk()
	{
		return enemyPpk;
	}
	public void setEnemyPpk(int enemyPpk)
	{
		this.enemyPpk = enemyPpk;
	}
	public String getEnemyName()
	{
		return enemyName;
	}
	public void setEnemyName(String enemyName)
	{
		this.enemyName = enemyName;
	}
	public int getEnemyGrade()
	{
		return enemyGrade;
	}
	public void setEnemyGrade(int enemyGrade)
	{
		this.enemyGrade = enemyGrade;
	}
	public int getHitePoint()
	{
		return hitePoint;
	}
	public void setHitePoint(int hitePoint)
	{
		this.hitePoint = hitePoint;
	}
	public int getGeneralPkCount()
	{
		return generalPkCount;
	}
	public void setGeneralPkCount(int generalPkCount)
	{
		this.generalPkCount = generalPkCount;
	}
	public int getActivePkCount()
	{
		return activePkCount;
	}
	public void setActivePkCount(int activePkCount)
	{
		this.activePkCount = activePkCount;
	}
	public Date getUpdateTime()
	{
		return updateTime;
	}
	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}
	public int getEnemyUpk()
	{
		return enemyUpk;
	}
	public void setEnemyUpk(int enemyUpk)
	{
		this.enemyUpk = enemyUpk;
	}
}
