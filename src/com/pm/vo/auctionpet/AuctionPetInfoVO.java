package com.pm.vo.auctionpet;

public class AuctionPetInfoVO
{

	/** 宠物拍卖信息表 */
	private int auctionPetInfoId;
	/** 个人角色id */
	private int pPk;
	/** 宠物拍卖信息 */
	private String auctionPetInfo;
	/** 信息加入时间 */
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
