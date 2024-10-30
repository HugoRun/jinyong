package com.pm.constant;

/**
 * 拍卖费系数
 * @author Administrator
 *
 */
public class AuctionNumber
{

	/** 拍卖费率现在为0.95 */
	public final static double AUCTIONNUMBER = 0.95;
	
	/*** 元宝拍卖----正在卖状态 */
	public final static int YUANSELLING = 1; 
	
	/*** 元宝拍卖----已经状态 */
	public final static int YUANSELLED = 2;
	
	/*** 元宝拍卖----下架状态 */
	public final static int YUANNOTSELL = 3;

	public static final int BACKED = 4;
	
}
