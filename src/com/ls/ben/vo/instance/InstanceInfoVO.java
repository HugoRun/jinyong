package com.ls.ben.vo.instance;

import java.util.Date;

/**
 * @author ls
 * 功能:副本信息
 * Feb 3, 2009
 */
public class InstanceInfoVO
{
	/**id*/
	int id;   
	
	/**描述*/
	String display ;
	
	/**副本地图id*/
    int mapId;
    
    /**
     * 玩家进入副本时的地址id
     */
    int enterSceneId;
    
    /**重置时间间隔，时间单位为天*/
    int resetTimeDistance;
    
    /**上一次的重置时间*/
    Date preResetTime;
    
    /**等级要求*/
    int levelLimit;  
    
    /**组队成员限制*/
    int groupLimit;
    
    /**
     * 有boss地点的数量
     */
    int bossSceneNum;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getDisplay()
	{
		return display;
	}

	public void setDisplay(String display)
	{
		this.display = display;
	}

	public int getMapId()
	{
		return mapId;
	}

	public void setMapId(int mapId)
	{
		this.mapId = mapId;
	}


	public int getResetTimeDistance()
	{
		return resetTimeDistance;
	}

	public void setResetTimeDistance(int resetTimeDistance)
	{
		this.resetTimeDistance = resetTimeDistance;
	}

	public Date getPreResetTime()
	{
		return preResetTime;
	}

	public void setPreResetTime(Date preResetTime)
	{
		this.preResetTime = preResetTime;
	}

	public int getLevelLimit()
	{
		return levelLimit;
	}

	public void setLevelLimit(int levelLimit)
	{
		this.levelLimit = levelLimit;
	}

	public int getGroupLimit()
	{
		return groupLimit;
	}

	public void setGroupLimit(int groupLimit)
	{
		this.groupLimit = groupLimit;
	}

	public int getEnterSenceId()
	{
		return enterSceneId;
	}

	public void setEnterSenceId(int enterSenceId)
	{
		this.enterSceneId = enterSenceId;
	}

	public int getBossSceneNum()
	{
		return bossSceneNum;
	}

	public void setBossSceneNum(int bossSceneNum)
	{
		this.bossSceneNum = bossSceneNum;
	}


}
