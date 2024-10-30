package com.dp.vo.credit;

public class PlayerCreditVO
{
	public static final int TONG_CREDIT = 100;//帮派声望类型
	private Integer pcid;// 角色对声望ID(主键)
	private Integer ppk;// 玩家角色ID
	private Integer pcount;// 声望数量
	private String creditname;// 声望名称
	private String creditdisplay;// 声望描述

	public Integer getPcid()
	{
		return pcid;
	}

	public void setPcid(Integer pcid)
	{
		this.pcid = pcid;
	}

	public Integer getPpk()
	{
		return ppk;
	}

	public void setPpk(Integer ppk)
	{
		this.ppk = ppk;
	}

	public String getCreditname()
	{
		return creditname;
	}

	public void setCreditname(String creditname)
	{
		this.creditname = creditname;
	}

	public String getCreditdisplay()
	{
		return creditdisplay;
	}

	public void setCreditdisplay(String creditdisplay)
	{
		this.creditdisplay = creditdisplay;
	}

	public Integer getPcount()
	{
		return pcount;
	}

	public void setPcount(Integer pcount)
	{
		this.pcount = pcount;
	}
}
