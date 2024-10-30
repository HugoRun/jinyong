package com.ls.model.organize.faction;

import java.sql.Date;

import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.model.user.RoleEntity;

/**
 * @author ls
 * 申请入帮信息
 */
public class FApplyInfo
{
	private int id;
	private int pPk;
	private int fId;//帮派id
	private Date createTime;

	public RoleEntity getRoleEntity()
	{
		return RoleCache.getByPpk(pPk);
	}
	
	public int getId()
	{
		return id;
	}
	public int getPPk()
	{
		return pPk;
	}
	public int getFId()
	{
		return fId;
	}
	public Date getCreateTime()
	{
		return createTime;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public void setPPk(int pk)
	{
		pPk = pk;
	}
	public void setFId(int id)
	{
		fId = id;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
}
