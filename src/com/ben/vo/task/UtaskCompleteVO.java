/**
 * 
 */
package com.ben.vo.task;

/**
 * @author 侯浩军
 * 
 * 10:05:51 AM
 */
public class UtaskCompleteVO
{
	/** 玩家已经完成过的任务id */
	private int cPk;
	/** 角色id */
	private int pPk;
	/** 已经完成的任务组 */
	private String taskZu;

	public int getCPk()
	{
		return cPk;
	}

	public void setCPk(int pk)
	{
		cPk = pk;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public String getTaskZu()
	{
		return taskZu;
	}

	public void setTaskZu(String taskZu)
	{
		this.taskZu = taskZu;
	}

}
