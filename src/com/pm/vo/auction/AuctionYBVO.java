package com.pm.vo.auction;

/**
 * ����Ԫ������
 * @author Administrator
 *
 */
public class AuctionYBVO
{
	/** Ԫ��id **/
	public int uybId 	;
	
	/** ����Ԫ���ߵ�p_pk */	
	public int pPk	;
	
	/** Ԫ��������״̬,1Ϊ��������״̬��2Ϊ�Ѿ�����״̬��3Ϊδ�����¼�״̬   */
	public int uybState   ;
	
	/** ����Ԫ������ */
	public int ybNum;
	
	/** ϣ�������ļ۸� */
	public int ybPrice;
	
	/** ����ʱ�� */
	public String auctionTime;

	public int getUybId()
	{
		return uybId;
	}

	public void setUybId(int uybId)
	{
		this.uybId = uybId;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public int getUybState()
	{
		return uybState;
	}

	public void setUybState(int uybState)
	{
		this.uybState = uybState;
	}

	public int getYbNum()
	{
		return ybNum;
	}

	public void setYbNum(int ybNum)
	{
		this.ybNum = ybNum;
	}

	public int getYbPrice()
	{
		return ybPrice;
	}

	public void setYbPrice(int ybPrice)
	{
		this.ybPrice = ybPrice;
	}

	public String getAuctionTime()
	{
		return auctionTime;
	}

	public void setAuctionTime(String auctionTime)
	{
		this.auctionTime = auctionTime;
	}
	
	
	
}
