package com.ls.ben.vo.instance;

import java.util.Date;

/**
 * @author ls
 * �����浵
 */
public class InstanceArchiveVO
{
	int pPk;/** ��ɫid*/
    int mapId;/**��������map_id*/
    String deadBossRecord; /**��¼�Ѵ�����boss���ڵ�sence_id,��ʽ��:223,45,1132*/
    Date createTime;/**���븱��ʱ��*/
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
