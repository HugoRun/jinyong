/**
 * 
 */
package com.ben.vo.rolebeoff;

/**
 * @author HHJ ������߼�¼
 */
public class RoleBeOffVO
{
	/** ����id */
	private int offId;
	/** ���ID */
	private int pPk;
	/** ����ʱ�� */
	private String beOffTime;
	/** ����ʱ����� */
	private String alreadyTime;
	/** ���߾��� */
	private String beOffExp;
	/** �����ۻ�ʱ�� ���ǿɹ���ȡ�����ʱ�� */
	private String propCumulateTime;

	public String getAlreadyTime()
	{
		return alreadyTime;
	}

	public void setAlreadyTime(String alreadyTime)
	{
		this.alreadyTime = alreadyTime;
	}

	public int getOffId()
	{
		return offId;
	}

	public void setOffId(int offId)
	{
		this.offId = offId;
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

	public String getPropCumulateTime()
	{
		return propCumulateTime;
	}

	public void setPropCumulateTime(String propCumulateTime)
	{
		this.propCumulateTime = propCumulateTime;
	}

}
