package com.pm.vo.sysInfo;

public class SystemControlInfoVO
{

	/**  系统控制消息表id*/
	private int controlId;
	
	/** 个人角色id */
	//private int pPk;
	
	/**  控制条件类型,1为玩家等级,2为任务,3为声望,4为称谓,5为发送时间.  */
	 private int condition;
	
	/**  玩家等级  */
	 private String playerGrade;
	/**  任务id */
	 private int taskId;			
	 /**  声望 */
	 private int popularity;
	 
	 /**  称谓 */
	 private String title;
	 
	 /**  发送时间 */
	 private String sendTime;

	 /**  发送内容 */
	 private String sendContent;
	 
	 /***  发送类型,1为发系统消息,2为发邮件,3为系统消息和邮件都发, 4为都不发 **/
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
