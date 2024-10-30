package com.ls.ben.vo.instance;

import java.util.Date;

/**
 * @author ls
 * ����:������Ϣ
 * Feb 3, 2009
 */
public class InstanceInfoVO
{
	/**id*/
	int id;   
	
	/**����*/
	String display ;
	
	/**������ͼid*/
    int mapId;
    
    /**
     * ��ҽ��븱��ʱ�ĵ�ַid
     */
    int enterSceneId;
    
    /**����ʱ������ʱ�䵥λΪ��*/
    int resetTimeDistance;
    
    /**��һ�ε�����ʱ��*/
    Date preResetTime;
    
    /**�ȼ�Ҫ��*/
    int levelLimit;  
    
    /**��ӳ�Ա����*/
    int groupLimit;
    
    /**
     * ��boss�ص������
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
