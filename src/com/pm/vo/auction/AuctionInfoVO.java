package com.pm.vo.auction;

public class AuctionInfoVO {

	/****������Ϣid**********/
	private int auctionInfoId;
	/******��ɫid***********/
	private int pPk;
	/*********������Ϣ**********/
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
