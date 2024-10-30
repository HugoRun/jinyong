package com.pm.vo.pkelimi;
/**
 * 消除罪恶值表
 * @author zhangjj
 *
 */
public class PKValueElimiVO
{

	/** 消除罪恶值id */
	private int pkvalue_elimi;
	/** 个人角色id */
	private int pPK;
	/** 罪恶值 */
	private int pkvalue;
	/** 是否在监狱区域 */
	private int isPerion;
	/** 最后消除时间 */
	private String lastTime;
	
	
	public int getPkvalue_elimi()
	{
		return pkvalue_elimi;
	}
	public void setPkvalue_elimi(int pkvalue_elimi)
	{
		this.pkvalue_elimi = pkvalue_elimi;
	}
	public int getPPK()
	{
		return pPK;
	}
	public void setPPK(int ppk)
	{
		pPK = ppk;
	}
	public int getPkvalue()
	{
		return pkvalue;
	}
	public void setPkvalue(int pkvalue)
	{
		this.pkvalue = pkvalue;
	}
	public int getIsPerion()
	{
		return isPerion;
	}
	public void setIsPerion(int isPerion)
	{
		this.isPerion = isPerion;
	}
	public String getLastTime()
	{
		return lastTime;
	}
	public void setLastTime(String lastTime)
	{
		this.lastTime = lastTime;
	}
	
}
