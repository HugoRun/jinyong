/**
 * 
 */
package com.ben.vo.rolebeoff;

/**
 * @author HHJ
 * ����BUFF
 */
public class RoleBeOffBuffVO
{
	/** ����id */
	private int bId;
	/** ���ID */
	private int pPk;
	/** �ϴ�����ʱ�� */
	private String beOffTime;
	/** ���߾��� */
	private String beOffExp;

	public int getBId()
	{
		return bId;
	}

	public void setBId(int id)
	{
		bId = id;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public String getBeOffTime()
	{
		return beOffTime;
	}

	public void setBeOffTime(String beOffTime)
	{
		this.beOffTime = beOffTime;
	}

	public String getBeOffExp()
	{
		return beOffExp;
	}

	public void setBeOffExp(String beOffExp)
	{
		this.beOffExp = beOffExp;
	}

}
