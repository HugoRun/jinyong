package com.pm.vo.sysInfo;

public class SystemControlInfoVO
{

	/**  ϵͳ������Ϣ��id*/
	private int controlId;
	
	/** ���˽�ɫid */
	//private int pPk;
	
	/**  ������������,1Ϊ��ҵȼ�,2Ϊ����,3Ϊ����,4Ϊ��ν,5Ϊ����ʱ��.  */
	 private int condition;
	
	/**  ��ҵȼ�  */
	 private String playerGrade;
	/**  ����id */
	 private int taskId;			
	 /**  ���� */
	 private int popularity;
	 
	 /**  ��ν */
	 private String title;
	 
	 /**  ����ʱ�� */
	 private String sendTime;

	 /**  �������� */
	 private String sendContent;
	 
	 /***  ��������,1Ϊ��ϵͳ��Ϣ,2Ϊ���ʼ�,3Ϊϵͳ��Ϣ���ʼ�����, 4Ϊ������ **/
	 private int sendType;
	 
	 
	 
	public int getSendType()
	{
		return sendType;
	}

	public void setSendType(int sendType)
	{
		this.sendType = sendType;
	}

	public String getSendContent()
	{
		return sendContent;
	}

	public void setSendContent(String sendContent)
	{
		this.sendContent = sendContent;
	}

	public int getControlId()
	{
		return controlId;
	}

	public void setControlId(int controlId)
	{
		this.controlId = controlId;
	}

	public int getCondition()
	{
		return condition;
	}

	public void setCondition(int condition)
	{
		this.condition = condition;
	}

	public String getPlayerGrade()
	{
		return playerGrade;
	}

	public void setPlayerGrade(String playerGrade)
	{
		this.playerGrade = playerGrade;
	}

	public int getTaskId()
	{
		return taskId;
	}

	public void setTaskId(int taskId)
	{
		this.taskId = taskId;
	}

	public int getPopularity()
	{
		return popularity;
	}

	public void setPopularity(int popularity)
	{
		this.popularity = popularity;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getSendTime()
	{
		return sendTime;
	}

	public void setSendTime(String sendTime)
	{
		this.sendTime = sendTime;
	}

	

}
