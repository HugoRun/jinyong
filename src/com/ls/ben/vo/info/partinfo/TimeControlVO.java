package com.ls.ben.vo.info.partinfo;

import java.util.Date;

/**
 * ����:u_time_control��
 * 
 * @author ��˧ Sep 25, 2008 11:47:38 AM
 */
public class TimeControlVO
{
	/** id */
	private int id;
	/** ��ɫid */
	private int pPk;
	/**��Ҫ���ƵĶ���*/
	private int   objectId;
	/**��Ҫ���ƵĶ�������ͣ���:���ߣ��˵���*/
	private int objectType;
	/**��¼���һ�ε�ʹ��ʱ��*/
	private Date useDatetime;
	/**��¼�����ʹ�ô���*/
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
