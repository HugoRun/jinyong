/**
 * 
 */
package com.ben.vo.expnpcdrop;

/**
 * @author ��ƾ� npc���侭�鱶�� 6:04:19 PM
 */
public class ExpNpcdropVO
{
	/** id */
	private int enPk;
	/** Ĭ�Ͼ��鱶�� */
	private int defaultExp;
	/** ��ʼʱ�� */
	private String beginTime;
	/** ����ʱ��ʱ�� */
	private String endTime;
	/** ���鱶�� */
	private int enMultiple;
	/** �������� */
	private int enCimelia;
	/** ִ�� 0 ��ִ�� 1ִ�� */
	private int enforce;
	/** 1�Ǿ������ 2�ǵ������� */
	private int expCimelia;
	/** ���ָ�ʽ 1(16:00:00���ָ�ʽ) 2(2009-01-16 16:37:45���ָ�ʽ) */
	private int acquitFormat;

	public int getAcquitFormat()
	{
		return acquitFormat;
	}

	public void setAcquitFormat(int acquitFormat)
	{
		this.acquitFormat = acquitFormat;
	}

	public int getEnPk()
	{
		return enPk;
	}

	public void setEnPk(int enPk)
	{
		this.enPk = enPk;
	}

	public int getDefaultExp()
	{
		return defaultExp;
	}

	public void setDefaultExp(int defaultExp)
	{
		this.defaultExp = defaultExp;
	}

	public String getBeginTime()
	{
		return beginTime;
	}

	public void setBeginTime(String beginTime)
	{
		this.beginTime = beginTime;
	}

	public String getEndTime()
	{
		return endTime;
	}

	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}

	public int getEnMultiple()
	{
		return enMultiple;
	}

	public void setEnMultiple(int enMultiple)
	{
		this.enMultiple = enMultiple;
	}

	public int getEnCimelia()
	{
		return enCimelia;
	}

	public void setEnCimelia(int enCimelia)
	{
		this.enCimelia = enCimelia;
	}

	public int getEnforce()
	{
		return enforce;
	}

	public void setEnforce(int enforce)
	{
		this.enforce = enforce;
	}

	public int getExpCimelia()
	{
		return expCimelia;
	}

	public void setExpCimelia(int expCimelia)
	{
		this.expCimelia = expCimelia;
	}

}
