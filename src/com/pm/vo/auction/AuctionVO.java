package com.pm.vo.auction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuctionVO  {

	/**����id****/
	private int uAuctionId;
	/****����id*****/
	private int uPk;
	/*****��ɫid******/
	private int pPk;
	/*****������Ʒ����******/
	private int auctionType;
	/****������Ʒid*********/
	private int goodsId;
	
	/** ������Ʒ������ */
	private int goodsNumber;
	/*******������Ʒ����****/
	private String goodsName;
	/*********pay����1��ʯ2���ɾ�**********/
	private int pay_type;
	/**********���ļ۸�************/
	private int auction_price;
	/*********�����ߵ�upk************/
	private int auction_upk;
	/*********�����ߵ�ppk*********/
	private int auction_ppk;
	/**********���Ŀ�ʼʱ��**********/
	private Date auction_start_time;
	/******������Ʒ�۸������Ҷ���*****/
	private int goodsPrice;
	/********���Ը����ļ۸�*********/
	private int buyPrice;
	/*******������ʼʱ��***********/
	private String auctionTime;
	/******��Ʒ�Ƿ�����****/
	private int auctionFailed;
	
	
	/*******��Ʒ�Ƿ�������********/
	private int auctionSell;
	/*******��������**********/
	private String buyName;
	
	/******** �����Ƿ����**********/
	private int propUseControl;
	/** ��Ʒ������ */
	private int tableType;
	/** ��Ʒ���� */
	private int goodsType;
	/** �;� */
	private int wDurability;
	/** �;����� */
	private int wDuraConsume;
	/** �� */
	private int wBonding;
	/** �Ƿ񱻱��� */
	private int wProtect;
	/** �Ƿ����ȷ�� */
	private int wIsReconfirm;
	/** ��Ʒ�۸� */
	private int wPrice;
	/** ����������С���� */
	private int wFyXiao;
	/** �������������� */
	private int wFyDa;
	/** ����������С���� */
	private int wGjXiao;
	/** ����������󹥻� */
	private int wGjDa;
	/** ����������Ѫ */
	private int wHp;
	/** �������Է��� */
	private int wMp;
	/** �������Խ������ */
	private int wJinFy;
	/** ��������ľ������ */
	private int wMuFy;
	/** ��������ˮ������ */
	private int wShuiFy;
	/** �������Ի������ */
	private int wHuoFy;
	/** ���������������� */
	private int wTuFy;
	/** �������Խ𹥻��� */
	private int wJinGj;
	/** ��������ľ������ */
	private int wMuGj;
	/** ��������ˮ������ */
	private int wShuiGj;
	/** �������Ի𹥻��� */
	private int wHuoGj;
	/** ���������������� */
	private int wTuGj;
	
	/**Ʒ��*/
	private int wQuality;
	/**װ����������*/
	private int wWxType;
	/** ��װid */
	private int suitId;
	/**����buff�Ƿ���Ч��0��ʾ��Ч��1��ʾ��Ч*/
	private int   wBuffIsEffected;
	
	/** �㻯���Ը������� */
	private String enchantType;
	
	/** �㻯���Ը���ֵ */
	private int enchantValue;
	
	/** ׷�ӵ�Ѫ�� */
	private int wZjHp;
	/** ׷�ӵ����� */
	private int wZjMp;
	/** ׷�ӵĹ��� **/
	private int wZjWxGj;
	/**  ׷�ӵķ��� **/
	private int wZjWxFy;
	
	/** װ���ĵȼ� */
	private int wZbGrade;
	/**���װ���󶨴���*/
	private int wBondingNum;
	/**��������**/
	private int specialcontent;
	public int getWBondingNum()
	{
		return wBondingNum;
	}
	public void setWBondingNum(int bondingNum)
	{
		wBondingNum = bondingNum;
	}
	public int getWZbGrade()
	{
		return wZbGrade;
	}
	public void setWZbGrade(int zbGrade)
	{
		wZbGrade = zbGrade;
	}
	public int getWZjHp()
	{
		return wZjHp;
	}
	public void setWZjHp(int zjHp)
	{
		wZjHp = zjHp;
	}
	public int getWZjMp()
	{
		return wZjMp;
	}
	public void setWZjMp(int zjMp)
	{
		wZjMp = zjMp;
	}
	
	public String getEnchantType()
	{
		return enchantType;
	}
	public void setEnchantType(String enchantType)
	{
		this.enchantType = enchantType;
	}
	public int getEnchantValue()
	{
		return enchantValue;
	}
	public void setEnchantValue(int enchantValue)
	{
		this.enchantValue = enchantValue;
	}
	public int getUAuctionId() {
		return uAuctionId;
	}
	public void setUAuctionId(int auctionId) {
		uAuctionId = auctionId;
	}
	public int getUPk() {
		return uPk;
	}
	public void setUPk(int pk) {
		uPk = pk;
	}
	public int getPPk() {
		return pPk;
	}
	public void setPPk(int pk) {
		pPk = pk;
	}
	public int getAuctionType() {
		return auctionType;
	}
	public void setAuctionType(int auctionType) {
		this.auctionType = auctionType;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public int getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(int goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public int getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}
	public String getAuctionTime(int i,int a) {
		return auctionTime;
	}
	public String getAuctionTime() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt1 = null;
		try
		{
			dt1 = sf.parse(auctionTime);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		int index = sf.format(dt1).indexOf("-");
		return sf.format(dt1).substring(index+1);
	}
		
	public String getAuctionTime(int d) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt1 = null;
		try
		{
			dt1 = new Date((sf.parse(auctionTime).getTime() + d*24*60*60*1000));
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		int index = sf.format(dt1).indexOf("-");
		return sf.format(dt1).substring(index+1);
	}
	
	public void setAuctionTime(String auctionTime) {
		this.auctionTime = auctionTime;
	}
	public int getAuctionFailed() {
		return auctionFailed;
	}
	public void setAuctionFailed(int auctionFailed) {
		this.auctionFailed = auctionFailed;
	}
	public int getAuctionSell() {
		return auctionSell;
	}
	public void setAuctionSell(int auctionSell) {
		this.auctionSell = auctionSell;
	}
	public String getBuyName() {
		return buyName;
	}
	public void setBuyName(String buyName) {
		this.buyName = buyName;
	}
	public int getGoodsNumber()
	{
		return goodsNumber;
	}
	public void setGoodsNumber(int goodsNumber)
	{
		this.goodsNumber = goodsNumber;
	}
	public int getGoodsType()
	{
		return goodsType;
	}
	public void setGoodsType(int goodsType)
	{
		this.goodsType = goodsType;
	}

	public int getWDurability()
	{
		return wDurability;
	}
	public void setWDurability(int durability)
	{
		wDurability = durability;
	}
	public int getWDuraConsume()
	{
		return wDuraConsume;
	}
	public void setWDuraConsume(int duraConsume)
	{
		wDuraConsume = duraConsume;
	}
	public int getWBonding()
	{
		return wBonding;
	}
	public void setWBonding(int bonding)
	{
		wBonding = bonding;
	}
	public int getWFyXiao()
	{
		double fyXiao = wFyXiao*(1+0.025*wZbGrade);
		return (int)fyXiao;
	}
	
	public int getWFyXiaoYuan()
	{
		return wFyXiao;
	}
	public void setWFyXiao(int fyXiao)
	{
		wFyXiao = fyXiao;
	}
	public int getWFyDa()
	{
		double fyDa = wFyDa*(1+0.025*wZbGrade);
		return (int)fyDa;
	}
	public int getWFyDaYuan()
	{
		return wFyDa;
	}
	public void setWFyDa(int fyDa)
	{
		wFyDa = fyDa;
	}
	public int getWGjXiao()
	{
		double gjXiao = wGjXiao*(1+0.1*wZbGrade);
		return (int)gjXiao;
	}
	public int getWGjXiaoYuan()
	{
		return wGjXiao;
	}
	public void setWGjXiao(int gjXiao)
	{
		wGjXiao = gjXiao;
	}
	public int getWGjDa()
	{
		double gjDa = wGjDa*(1+0.1*wZbGrade);
		return (int)gjDa;
	}
	
	public int getWGjDaYuan()
	{
		return wGjDa;
	}
	public void setWGjDa(int gjDa)
	{
		wGjDa = gjDa;
	}
	public int getWHp()
	{
		return wHp;
	}
	public void setWHp(int hp)
	{
		wHp = hp;
	}
	public int getWMp()
	{
		return wMp;
	}
	public void setWMp(int mp)
	{
		wMp = mp;
	}
	public int getWJinFy()
	{
		return wJinFy;
	}
	public void setWJinFy(int jinFy)
	{
		wJinFy = jinFy;
	}
	public int getWMuFy()
	{
		return wMuFy;
	}
	public void setWMuFy(int muFy)
	{
		wMuFy = muFy;
	}
	public int getWShuiFy()
	{
		return wShuiFy;
	}
	public void setWShuiFy(int shuiFy)
	{
		wShuiFy = shuiFy;
	}
	public int getWHuoFy()
	{
		return wHuoFy;
	}
	public void setWHuoFy(int huoFy)
	{
		wHuoFy = huoFy;
	}
	public int getWTuFy()
	{
		return wTuFy;
	}
	public void setWTuFy(int tuFy)
	{
		wTuFy = tuFy;
	}
	public int getWJinGj()
	{
		return wJinGj;
	}
	public void setWJinGj(int jinGj)
	{
		wJinGj = jinGj;
	}
	public int getWMuGj()
	{
		return wMuGj;
	}
	public void setWMuGj(int muGj)
	{
		wMuGj = muGj;
	}
	public int getWShuiGj()
	{
		return wShuiGj;
	}
	public void setWShuiGj(int shuiGj)
	{
		wShuiGj = shuiGj;
	}
	public int getWHuoGj()
	{
		return wHuoGj;
	}
	public void setWHuoGj(int huoGj)
	{
		wHuoGj = huoGj;
	}
	public int getWTuGj()
	{
		return wTuGj;
	}
	public void setWTuGj(int tuGj)
	{
		wTuGj = tuGj;
	}
	public int getWQuality()
	{
		return wQuality;
	}
	public void setWQuality(int quality)
	{
		wQuality = quality;
	}
	public int getWWxType()
	{
		return wWxType;
	}
	public void setWWxType(int wxType)
	{
		wWxType = wxType;
	}
	public int getWBuffIsEffected()
	{
		return wBuffIsEffected;
	}
	public void setWBuffIsEffected(int buffIsEffected)
	{
		wBuffIsEffected = buffIsEffected;
	}
	public int getTableType()
	{
		return tableType;
	}
	public void setTableType(int tableType)
	{
		this.tableType = tableType;
	}
	public int getWProtect()
	{
		return wProtect;
	}
	public void setWProtect(int protect)
	{
		wProtect = protect;
	}
	public int getWIsReconfirm()
	{
		return wIsReconfirm;
	}
	public void setWIsReconfirm(int isReconfirm)
	{
		wIsReconfirm = isReconfirm;
	}
	public int getWPrice()
	{
		return wPrice;
	}
	public void setWPrice(int price)
	{
		wPrice = price;
	}
	public int getSuitId()
	{
		return suitId;
	}
	public void setSuitId(int suitId)
	{
		this.suitId = suitId;
	}
	public int getPropUseControl()
	{
		return propUseControl;
	}
	public void setPropUseControl(int propUseControl)
	{
		this.propUseControl = propUseControl;
	}
	public int getWZjWxGj()
	{
		return wZjWxGj;
	}
	public void setWZjWxGj(int zjWxGj)
	{
		wZjWxGj = zjWxGj;
	}
	public int getWZjWxFy()
	{
		return wZjWxFy;
	}
	public void setWZjWxFy(int zjWxFy)
	{
		wZjWxFy = zjWxFy;
	}
	public int getSpecialcontent()
	{
		return specialcontent;
	}
	public void setSpecialcontent(int specialcontent)
	{
		this.specialcontent = specialcontent;
	}
	public int getPay_type()
	{
		return pay_type;
	}
	public void setPay_type(int pay_type)
	{
		this.pay_type = pay_type;
	}
	public int getAuction_price()
	{
		return auction_price;
	}
	public void setAuction_price(int auction_price)
	{
		this.auction_price = auction_price;
	}
	public int getAuction_upk()
	{
		return auction_upk;
	}
	public void setAuction_upk(int auction_upk)
	{
		this.auction_upk = auction_upk;
	}
	public int getAuction_ppk()
	{
		return auction_ppk;
	}
	public void setAuction_ppk(int auction_ppk)
	{
		this.auction_ppk = auction_ppk;
	}
	public Date getAuction_start_time()
	{
		return auction_start_time;
	}
	public void setAuction_start_time(Date auction_start_time)
	{
		this.auction_start_time = auction_start_time;
	}
}
