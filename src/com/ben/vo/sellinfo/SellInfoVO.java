/**
 * 
 */
package com.ben.vo.sellinfo;

import com.ls.ben.dao.info.partinfo.PlayerEquipDao;

/**
 * @author ��ƾ�
 * 
 * 2:21:45 PM
 */
public class SellInfoVO
{
	public static int SELLMONEY = 1;// 1��Ǯ����
	public static int SELLPROP = 2;// 2 ���߽���
	public static int SELLARM = 3;// 3 װ������
	public static int ZENGSONGPROP = 4;//4���͵���
	public static int ZENGSONGARM = 5;//5����װ��
	/** id */
	private int sPk;
	/** ���������ɫid */
	private int pPk;
	/** �������ɫid */
	private int pByPk;
	/** ��������Ҫ���׵���Ʒ */
	private int sWuping;
	/** ��������Ҫ��Ʒ���� */
	private int sWpType;
	/** ��������Ҫ��Ʒ������ */
	private int sWpNumber;
	/** ��������Ҫ��Ʒ�ļ۸������ */
	private long sWpSilverMoney;
	/** ��������Ҫ��Ʒ�ļ۸��ͭǮ */
	private int sWpCopperMoney;
	/** ���׷�ʽ 1��Ǯ���� 2 ���߽��� 3 װ������ */
	private int sellMode;
	/** ����ʱ�� */
	private String createTime;

	/**
	 * �õ���Ʒ����
	 * @return
	 */
	public String getWupingName()
	{
		if( sWpType==SELLARM)
		{
			PlayerEquipDao playerEquipDao = new PlayerEquipDao();
			return playerEquipDao.getNameById(sWuping);
		}
		return "";
	}
	
	public int getSPk()
	{
		return sPk;
	}

	public void setSPk(int pk)
	{
		sPk = pk;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public int getPByPk()
	{
		return pByPk;
	}

	public void setPByPk(int byPk)
	{
		pByPk = byPk;
	}

	public int getSWuping()
	{
		return sWuping;
	}

	public void setSWuping(int wuping)
	{
		sWuping = wuping;
	}

	public int getSWpType()
	{
		return sWpType;
	}

	public void setSWpType(int wpType)
	{
		sWpType = wpType;
	}

	public int getSWpNumber()
	{
		return sWpNumber;
	}

	public void setSWpNumber(int wpNumber)
	{
		sWpNumber = wpNumber;
	}

	public long getSWpSilverMoney()
	{
		return sWpSilverMoney;
	}

	public void setSWpSilverMoney(long wpSilverMoney)
	{
		sWpSilverMoney = wpSilverMoney;
	}

	public int getSellMode()
	{
		return sellMode;
	}

	public void setSellMode(int sellMode)
	{
		this.sellMode = sellMode;
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
