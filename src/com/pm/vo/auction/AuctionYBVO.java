package com.pm.vo.auction;

/**
 * 拍卖元宝的类
 * @author Administrator
 *
 */
public class AuctionYBVO
{
	/** 元宝id **/
	public int uybId 	;
	
	/** 拍卖元宝者的p_pk */	
	public int pPk	;
	
	/** 元宝跑卖的状态,1为正在卖出状态，2为已经卖出状态，3为未卖出下架状态   */
	public int uybState   ;
	
	/** 拍卖元宝数量 */
	public int ybNum;
	
	/** 希望拍卖的价格 */
	public int ybPrice;
	
	/** 拍卖时间 */
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
