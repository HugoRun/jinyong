package com.lw.vo.specialprop;

public class SpecialPropVO
{
	// 物品ID
	private int sppk;
	// 玩家PPK
	private int ppk;
	// 物品ID
	private int propid;
	// 特殊字节 1
	private String propoperate1;
	// 特殊字节 2
	private String propoperate2;
	// 特殊字节 3
	private String propoperate3;
	// 计时ID
	private int proptime;
	// 创建时间
	private String propdate;
	// 标记
	private int sign;

	public int getSign()
	{
		return sign;
	}

	public void setSign(int sign)
	{
		this.sign = sign;
	}

	public int getSppk()
	{
		return sppk;
	}

	public void setSppk(int sppk)
	{
		this.sppk = sppk;
	}

	public String getPropoperate1()
	{
		return propoperate1;
	}

	public void setPropoperate1(String propoperate1)
	{
		this.propoperate1 = propoperate1;
	}

	public String getPropoperate2()
	{
		return propoperate2;
	}

	public void setPropoperate2(String propoperate2)
	{
		this.propoperate2 = propoperate2;
	}

	public String getPropoperate3()
	{
		return propoperate3;
	}

	public void setPropoperate3(String propoperate3)
	{
		this.propoperate3 = propoperate3;
	}

	public int getProptime()
	{
		return proptime;
	}

	public void setProptime(int proptime)
	{
		this.proptime = proptime;
	}

	public String getPropdate()
	{
		return propdate;
	}

	public void setPropdate(String propdate)
	{
		this.propdate = propdate;
	}

	public int getPpk()
	{
		return ppk;
	}

	public void setPpk(int ppk)
	{
		this.ppk = ppk;
	}

	public int getPropid()
	{
		return propid;
	}

	public void setPropid(int propid)
	{
		this.propid = propid;
	}

}
