package com.lw.vo.wishingtree;

public class WishingTreeVO
{
	// 主键
	private int id;
	// 谁发的
	private int p_pk;
	// 名字
	private String name;
	// 内容
	private String wishing;
	// 是否置顶
	private int top;
	// 时间
	private String time;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getP_pk()
	{
		return p_pk;
	}

	public void setP_pk(int p_pk)
	{
		this.p_pk = p_pk;
	}

	public String getWishing()
	{
		return wishing;
	}

	public void setWishing(String wishing)
	{
		this.wishing = wishing;
	}

	public int getTop()
	{
		return top;
	}

	public void setTop(int top)
	{
		this.top = top;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
