package com.ls.ben.vo.info.partinfo;

import java.util.Date;

/**
 * 功能:u_time_control表
 * 
 * @author 刘帅 Sep 25, 2008 11:47:38 AM
 */
public class TimeControlVO
{
	/** id */
	private int id;
	/** 角色id */
	private int pPk;
	/**需要控制的对象*/
	private int   objectId;
	/**需要控制的对象的类型，如:道具，菜单等*/
	private int objectType;
	/**记录最后一次的使用时间*/
	private Date useDatetime;
	/**记录当天的使用次数*/
	private int useTimes;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getPPk()
	{
		return pPk;
	}
	public void setPPk(int pk)
	{
		pPk = pk;
	}
	public int getObjectId()
	{
		return objectId;
	}
	public void setObjectId(int objectId)
	{
		this.objectId = objectId;
	}
	public int getObjectType()
	{
		return objectType;
	}
	public void setObjectType(int objectType)
	{
		this.objectType = objectType;
	}
	public Date getUseDatetime()
	{
		return useDatetime;
	}
	public void setUseDatetime(Date useDatetime)
	{
		this.useDatetime = useDatetime;
	}
	public int getUseTimes()
	{
		return useTimes;
	}
	public void setUseTimes(int useTimes)
	{
		this.useTimes = useTimes;
	}

}
