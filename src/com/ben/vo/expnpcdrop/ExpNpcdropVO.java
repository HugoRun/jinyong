/**
 * 
 */
package com.ben.vo.expnpcdrop;

/**
 * @author 侯浩军 npc掉落经验倍数 6:04:19 PM
 */
public class ExpNpcdropVO
{
	/** id */
	private int enPk;
	/** 默认经验倍数 */
	private int defaultExp;
	/** 开始时间 */
	private String beginTime;
	/** 结束时间时间 */
	private String endTime;
	/** 经验倍数 */
	private int enMultiple;
	/** 掉宝倍数 */
	private int enCimelia;
	/** 执行 0 不执行 1执行 */
	private int enforce;
	/** 1是经验掉率 2是掉宝掉率 */
	private int expCimelia;
	/** 表现格式 1(16:00:00这种格式) 2(2009-01-16 16:37:45这种格式) */
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
