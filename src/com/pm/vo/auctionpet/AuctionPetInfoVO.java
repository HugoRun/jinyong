package com.pm.vo.auctionpet;

public class AuctionPetInfoVO
{

	/** ����������Ϣ�� */
	private int auctionPetInfoId;
	/** ���˽�ɫid */
	private int pPk;
	/** ����������Ϣ */
	private String auctionPetInfo;
	/** ��Ϣ����ʱ�� */
	private String addInfoTime;
	public int getAuctionPetInfoId()
	{
		return auctionPetInfoId;
	}
	public void setAuctionPetInfoId(int auctionPetInfoId)
	{
		this.auctionPetInfoId = auctionPetInfoId;
	}
	public int getPPk()
	{
		return pPk;
	}
	public void setPPk(int pk)
	{
		pPk = pk;
	}
	public String getAuctionPetInfo()
	{
		return auctionPetInfo;
	}
	public void setAuctionPetInfo(String auctionPetInfo)
	{
		this.auctionPetInfo = auctionPetInfo;
	}
	public String getAddInfoTime()
	{
		return addInfoTime;
	}
	public void setAddInfoTime(String addInfoTime)
	{
		this.addInfoTime = addInfoTime;
	}
}
