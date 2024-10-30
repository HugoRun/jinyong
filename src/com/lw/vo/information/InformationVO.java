package com.lw.vo.information;

public class InformationVO
{
	/** 账号ID */
	private int uPk;
	/** 获得大礼包的当乐ID */
	private String id;
	/** 获得大礼包的类型 */
	private String type;

	public int getUPk()
	{
		return uPk;
	}

	public void setUPk(int pk)
	{
		uPk = pk;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
}
