/**
 * 
 */
package com.ben.vo.friend;

/**
 * @author 侯浩军 黑名单 4:42:06 PM
 */
public class BlacklistVO
{
	/** 玩家好友id */
	private int bPk;
	/** 玩家id */
	private int pPk;
	/** 好友ID */
	private int blPk;
	/** 好友名称 */
	private String bName;
	/** 加入时间 */
	private String createTime;

	public int getBPk()
	{
		return bPk;
	}

	public void setBPk(int pk)
	{
		bPk = pk;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public int getBlPk()
	{
		return blPk;
	}

	public void setBlPk(int blPk)
	{
		this.blPk = blPk;
	}

	public String getBName()
	{
		return bName;
	}

	public void setBName(String name)
	{
		bName = name;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

}
