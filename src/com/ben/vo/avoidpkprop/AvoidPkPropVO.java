/**
 * 
 */
package com.ben.vo.avoidpkprop;

/**
 * @author Administrator
 * 
 */
public class AvoidPkPropVO
{
	/** ��PK����ID */
	private int aPk;
	/** ���id */
	private int pPk;
	/** ���߿�ʼʱ�� */
	private String beginTime;
	/** ���߽���ʱ�� */
	private String endTime;

	public int getAPk()
	{
		return aPk;
	}

	public void setAPk(int pk)
	{
		aPk = pk;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
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

}
