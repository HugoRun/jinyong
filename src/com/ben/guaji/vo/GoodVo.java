package com.ben.guaji.vo;

import java.io.Serializable;

public class GoodVo implements Comparable<GoodVo>, Serializable
{
	// 掉落物品id
	private int good_id;
	// 掉落物品名称
	private String good_name;
	// 掉落物品类型
	private int good_type;
	public GoodVo(){
		super();
	}

	public GoodVo(int good_id, String good_name, int good_type)
	{
		super();
		this.good_id = good_id;
		this.good_name = good_name;
		this.good_type = good_type;
	}

	public int getGood_id()
	{
		return good_id;
	}

	public void setGood_id(int good_id)
	{
		this.good_id = good_id;
	}



	public String getGood_name()
	{
		return good_name;
	}

	public void setGood_name(String good_name)
	{
		this.good_name = good_name;
	}

	public int getGood_type()
	{
		return good_type;
	}

	public void setGood_type(int good_type)
	{
		this.good_type = good_type;
	}

	public int compareTo(GoodVo gv)
	{
		if (gv.getGood_id() == this.good_id
				&& gv.getGood_type() == this.good_type)
		{
			return 0;
		}
		else
		{
			return -1;
		}
	}

}
