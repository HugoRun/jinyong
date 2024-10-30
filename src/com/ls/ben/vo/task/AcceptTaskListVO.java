package com.ls.ben.vo.task;

import com.ls.iface.function.Probability;

/**
 * 功能:accept_task_list
 * @author 刘帅
 * Oct 20, 2008  6:19:47 PM
 */
public class AcceptTaskListVO implements Probability
{
	/**任务ID*/
	private int Id;
	/**道具id*/
	private int  touchId;
	/**任务范围,如:1,5表示任务在1到之间包括1和5随机获得*/
	private String    taskArea;
	/**触发类型 1 道具触发任务 2 菜单触发任务*/
	private int taskType;
	
	private int probability;
	
	public int getId()
	{
		return Id;
	}
	public void setId(int id)
	{
		Id = id;
	} 
	public int getTouchId()
	{
		return touchId;
	}
	public void setTouchId(int touchId)
	{
		this.touchId = touchId;
	}
	public String getTaskArea()
	{
		return taskArea;
	}
	public void setTaskArea(String taskArea)
	{
		this.taskArea = taskArea;
	}
	public int getProbability()
	{
		return probability;
	}
	public void setProbability(int probability)
	{
		this.probability = probability;
	}
	public int getTaskType()
	{
		return taskType;
	}
	public void setTaskType(int taskType)
	{
		this.taskType = taskType;
	} 
	

}
