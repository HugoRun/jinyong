package com.lw.vo.wishingtree;

public class WishingTreeVO
{
	// ����
	private int id;
	// ˭����
	private int p_pk;
	// ����
	private String name;
	// ����
	private String wishing;
	// �Ƿ��ö�
	private int top;
	// ʱ��
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
