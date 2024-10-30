package com.ls.ben.vo.mounts;

import java.util.Date;

import com.ls.ben.dao.mounts.MountsDao;
import com.ls.web.service.mounts.MountsService;


/**
 * 用户坐骑对应实体类 对应表u_mounts_temp
 * @author Thomas.lei
 *
 */
public class UserMountsVO
{
	private int id;
	private int ppk;
	private int mountsID;
	private int mountsState;
	private Date getTime;
	
	public String getPartName()
	{
		MountsDao md=new MountsDao();
		return md.getPname(ppk);
	}
	public MountsVO getMountInfo()
	{
		MountsService mountsService = new MountsService();
		return mountsService.getMountsInfo(mountsID);
	}
	
	public String getNextLevelID()
	{
		return getMountInfo().getNextLevelID();
	}
	public int getHightLevel()
	{
		return getMountInfo().getHightLevel();
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getPpk()
	{
		return ppk;
	}
	public void setPpk(int ppk)
	{
		this.ppk = ppk;
	}
	public int getMountsID()
	{
		return mountsID;
	}
	public void setMountsID(int mountsID)
	{
		this.mountsID = mountsID;
	}
	public String getMountsName()
	{
		return getMountInfo().getName();
	}
	public int getMountsLevle()
	{
		return getMountInfo().getLevel();
	}
	public int getMountsType()
	{
		return getMountInfo().getType();
	}
	public int getMountsState()
	{
		return mountsState;
	}
	public void setMountsState(int mountsState)
	{
		this.mountsState = mountsState;
	}
	public Date getGetTime()
	{
		return getTime;
	}
	public void setGetTime(Date getTime)
	{
		this.getTime = getTime;
	}

}
