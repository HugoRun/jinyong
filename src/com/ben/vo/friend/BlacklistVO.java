/**
 * 
 */
package com.ben.vo.friend;

/**
 * @author ��ƾ� ������ 4:42:06 PM
 */
public class BlacklistVO
{
	/** ��Һ���id */
	private int bPk;
	/** ���id */
	private int pPk;
	/** ����ID */
	private int blPk;
	/** �������� */
	private String bName;
	/** ����ʱ�� */
	private String createTime;

	public int getBPk()
	{
		return bPk;
	}

	public void setBPk(int pk)
	{
		bPk = pk;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public int getBlPk()
	{
		return blPk;
	}

	public void setBlPk(int blPk)
	{
		this.blPk = blPk;
	}

	public String getBName()
	{
		return bName;
	}

	public void setBName(String name)
	{
		bName = name;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

}
