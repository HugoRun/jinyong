package com.lw.vo.information;

public class InformationVO
{
	/** �˺�ID */
	private int uPk;
	/** ��ô�����ĵ���ID */
	private String id;
	/** ��ô���������� */
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
