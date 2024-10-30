package com.ls.ben.vo.mall;

import java.util.Date;

/**
 * 功能：商城记录
 * @author ls
 * May 12, 2009
 * 3:53:50 PM
 */
public class MallLogVO
{
	/*1）	玩家id(u_pk)
	2）	角色名字
	3）	记录描述
	4）	创建时间*/
	private int uPk;
	private String roleName;
	private String log;
	private Date datetime;
	
	public int getUPk()
	{
		return uPk;
	}
	public void setUPk(int pk)
	{
		uPk = pk;
	}
	public String getRoleName()
	{
		return roleName;
	}
	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}
	public String getLog()
	{
		return log;
	}
	public void setLog(String log)
	{
		this.log = log;
	}
	public Date getDatetime()
	{
		return datetime;
	}
	public void setDatetime(Date datetime)
	{
		this.datetime = datetime;
	}
}
