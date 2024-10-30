package com.ls.ben.vo.instance;

import java.util.Date;

/**
 * @author ls
 * 副本存档
 */
public class InstanceArchiveVO
{
	int pPk;/** 角色id*/
    int mapId;/**副本所在map_id*/
    String deadBossRecord; /**记录已打死的boss所在的sence_id,形式如:223,45,1132*/
    Date createTime;/**进入副本时间*/
	public int getPPk()
	{
		return pPk;
	}
	public void setPPk(int pk)
	{
		pPk = pk;
	}
	public int getMapId()
	{
		return mapId;
	}
	public void setMapId(int mapId)
	{
		this.mapId = mapId;
	}
	public String getDeadBossRecord()
	{
		return deadBossRecord;
	}
	public void setDeadBossRecord(String deadBossRecord)
	{
		this.deadBossRecord = deadBossRecord;
	}
	public Date getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}  
}
