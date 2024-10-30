package com.pm.vo.forum;

public class ForumForbidVO
{
	 /** 块表禁止发言表主键*/
	public int frId;
	
	/** 分类帮助类型主键 */
   	public int forbidType ;
   
	
	 /**版主ID*/
	public  int pPk;
	
	 /**版主名称*/
	public String pName ;
	
	/** 添加时间  */
	public int addTime;
   	
   	/** 结束时间 */	
	public  String forbidEndTime;

	public int getFrId()
	{
		return frId;
	}

	public void setFrId(int frId)
	{
		this.frId = frId;
	}

	
	public int getForbidType()
	{
		return forbidType;
	}

	public void setForbidType(int forbidType)
	{
		this.forbidType = forbidType;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public String getPName()
	{
		return pName;
	}

	public void setPName(String name)
	{
		pName = name;
	}

	public int getAddTime()
	{
		return addTime;
	}

	public void setAddTime(int addTime)
	{
		this.addTime = addTime;
	}

	public String getForbidEndTime()
	{
		return forbidEndTime;
	}

	public void setForbidEndTime(String forbidEndTime)
	{
		this.forbidEndTime = forbidEndTime;
	}
	
	
	
	
	
	
	
	
}
