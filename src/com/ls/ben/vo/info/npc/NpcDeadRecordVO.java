package com.ls.ben.vo.info.npc;

import java.util.Date;

/**
 * @author ls
 * 功能:npc_deadtime_record
 * Mar 5, 2009
 */
public class NpcDeadRecordVO
{
	private int  id;         /**主键*/
	private int  pPk;        /**玩家id*/
	private int  npcId;      /**NPC的ID*/
	private int  sceneId;    /** 场景ID */
	private int  mapId;      /**map的ID*/
	private Date npcDeadTime;/** 上一次NPC死亡时间 */
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
	public int getNpcId()
	{
		return npcId;
	}
	public void setNpcId(int npcId)
	{
		this.npcId = npcId;
	}
	public int getSceneId()
	{
		return sceneId;
	}
	public void setSceneId(int sceneId)
	{
		this.sceneId = sceneId;
	}
	public Date getNpcDeadTime()
	{
		return npcDeadTime;
	}
	public void setNpcDeadTime(Date npcDeadTime)
	{
		this.npcDeadTime = npcDeadTime;
	}
	public int getMapId()
	{
		return mapId;
	}
	public void setMapId(int mapId)
	{
		this.mapId = mapId;
	}
}
