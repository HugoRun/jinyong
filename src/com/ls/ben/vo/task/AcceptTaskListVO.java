package com.ls.ben.vo.task;

import com.ls.iface.function.Probability;

/**
 * ����:accept_task_list
 * @author ��˧
 * Oct 20, 2008  6:19:47 PM
 */
public class AcceptTaskListVO implements Probability
{
	/**����ID*/
	private int Id;
	/**����id*/
	private int  touchId;
	/**����Χ,��:1,5��ʾ������1��֮�����1��5������*/
	private String    taskArea;
	/**�������� 1 ���ߴ������� 2 �˵���������*/
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
