package com.ls.ben.vo.info.npc;

import java.util.Date;

/**
 * @author ls
 * ����:npc_deadtime_record
 * Mar 5, 2009
 */
public class NpcDeadRecordVO
{
	private int  id;         /**����*/
	private int  pPk;        /**���id*/
	private int  npcId;      /**NPC��ID*/
	private int  sceneId;    /** ����ID */
	private int  mapId;      /**map��ID*/
	private Date npcDeadTime;/** ��һ��NPC����ʱ�� */
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
