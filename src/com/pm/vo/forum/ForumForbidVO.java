package com.pm.vo.forum;

public class ForumForbidVO
{
	 /** ����ֹ���Ա�����*/
	public int frId;
	
	/** ��������������� */
   	public int forbidType ;
   
	
	 /**����ID*/
	public  int pPk;
	
	 /**��������*/
	public String pName ;
	
	/** ���ʱ��  */
	public int addTime;
   	
   	/** ����ʱ�� */	
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
