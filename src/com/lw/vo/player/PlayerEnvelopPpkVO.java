package com.lw.vo.player;

public class PlayerEnvelopPpkVO
{
	// �������
	int envelopID;
	// ���ID
	int ppk;
	// �������
	String pName;
	// ��Ŵ���ʱ��
	String beginTime;
	// ��Ž���ʱ��
	String endTime;
	// ���ʱ��
	String envelopTime;
	// �������
	int envelopType;
	public int getEnvelopID()
	{
		return envelopID;
	}
	public void setEnvelopID(int envelopID)
	{
		this.envelopID = envelopID;
	}
	public int getPpk()
	{
		return ppk;
	}
	public void setPpk(int ppk)
	{
		this.ppk = ppk;
	}
	public String getPName()
	{
		return pName;
	}
	public void setPName(String name)
	{
		pName = name;
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
	public String getEnvelopTime()
	{
		return envelopTime;
	}
	public void setEnvelopTime(String envelopTime)
	{
		this.envelopTime = envelopTime;
	}
	public int getEnvelopType()
	{
		return envelopType;
	}

	public void setEnvelopType(int envelopType)
	{
		this.envelopType = envelopType;
	}
}
