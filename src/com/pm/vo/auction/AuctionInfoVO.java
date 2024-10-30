package com.pm.vo.auction;

public class AuctionInfoVO {

	/****拍卖信息id**********/
	private int auctionInfoId;
	/******角色id***********/
	private int pPk;
	/*********拍卖信息**********/
	private String auctionInfo;
	public int getAuctionInfoId() {
		return auctionInfoId;
	}
	public void setAuctionInfoId(int auctionInfoId) {
		this.auctionInfoId = auctionInfoId;
	}
	public int getPPk() {
		return pPk;
	}
	public void setPPk(int pk) {
		pPk = pk;
	}
	public String getAuctionInfo() {
		return auctionInfo;
	}
	public void setAuctionInfo(String auctionInfo) {
		this.auctionInfo = auctionInfo;
	}
	
}
