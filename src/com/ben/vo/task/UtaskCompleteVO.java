/**
 * 
 */
package com.ben.vo.task;

/**
 * @author ��ƾ�
 * 
 * 10:05:51 AM
 */
public class UtaskCompleteVO
{
	/** ����Ѿ���ɹ�������id */
	private int cPk;
	/** ��ɫid */
	private int pPk;
	/** �Ѿ���ɵ������� */
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
