package com.ben.vo.friend;

import java.util.Date;

/**
 * 功能:好友
 * 
 * @author 侯浩军 9:23:19 AM
 */
public class FriendVO
{
	/** 玩家好友id */
	private int fPk;
	/** 玩家id */
	private int pPk;
	/** 好友ID */
	private int fdPk;
	/** 好友名称 */
	private String fdName;
	/** 加入时间 */
	private String createTime;
	/** 好友是否在线 */
	private int fOnline;
	/** 好友所在地图 */
	private int pMap;

	/** 好友所在地图名 */
	private String pMapName;
	private Date tim;
	/***新加了好友是否在线的字节***/
	private int login_state;
	
	//亲密度
	private int dear;
	
	//双方关系
	private int relation;
	
	//经验分享值
	private int expShare;
	
	//爱情甜蜜值
	private int love_dear;
	
	

	public int getLove_dear()
	{
		return love_dear;
	}

	public void setLove_dear(int love_dear)
	{
		this.love_dear = love_dear;
	}

	public int getExpShare()
	{
		return expShare;
	}

	public void setExpShare(int expShare)
	{
		this.expShare = expShare;
	}

	public int getRelation()
	{
		return relation;
	}

	public void setRelation(int relation)
	{
		this.relation = relation;
	}

	public int getDear()
	{
		return dear;
	}

	public void setDear(int dear)
	{
		this.dear = dear;
	}

	public String getPMapName()
	{
		return pMapName;
	}

	public void setPMapName(String mapName)
	{
		pMapName = mapName;
	}

	public int getPMap()
	{
		return pMap;
	}

	public void setPMap(int map)
	{
		pMap = map;
	}

	public int getFPk()
	{
		return fPk;
	}

	public void setFPk(int pk)
	{
		fPk = pk;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public int getFdPk()
	{
		return fdPk;
	}

	public void setFdPk(int fdPk)
	{
		this.fdPk = fdPk;
	}

	public String getFdName()
	{
		return fdName;
	}

	public void setFdName(String fdName)
	{
		this.fdName = fdName;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public int getFOnline()
	{
		return fOnline;
	}

	public void setFOnline(int online)
	{
		fOnline = online;
	}

	public Date getTim()
	{
		return tim;
	}

	public void setTim(Date tim)
	{
		this.tim = tim;
	}

	public int getLogin_state()
	{
		return login_state;
	}

	public void setLogin_state(int login_state)
	{
		this.login_state = login_state;
	}

}
