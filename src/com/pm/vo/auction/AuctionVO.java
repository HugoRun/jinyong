package com.pm.vo.auction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuctionVO  {

	/**拍卖id****/
	private int uAuctionId;
	/****个人id*****/
	private int uPk;
	/*****角色id******/
	private int pPk;
	/*****拍卖物品类型******/
	private int auctionType;
	/****拍卖物品id*********/
	private int goodsId;
	
	/** 拍卖物品的数量 */
	private int goodsNumber;
	/*******拍卖物品名称****/
	private String goodsName;
	/*********pay类型1灵石2是仙晶**********/
	private int pay_type;
	/**********竞拍价格************/
	private int auction_price;
	/*********竞拍者的upk************/
	private int auction_upk;
	/*********竞拍者的ppk*********/
	private int auction_ppk;
	/**********竞拍开始时间**********/
	private Date auction_start_time;
	/******拍卖物品价格，由卖家定的*****/
	private int goodsPrice;
	/********买家愿意出的价格*********/
	private int buyPrice;
	/*******拍卖开始时间***********/
	private String auctionTime;
	/******物品是否流拍****/
	private int auctionFailed;
	
	
	/*******物品是否已卖出********/
	private int auctionSell;
	/*******买者姓名**********/
	private String buyName;
	
	/******** 道具是否可用**********/
	private int propUseControl;
	/** 物品表种类 */
	private int tableType;
	/** 物品类型 */
	private int goodsType;
	/** 耐久 */
	private int wDurability;
	/** 耐久消耗 */
	private int wDuraConsume;
	/** 绑定 */
	private int wBonding;
	/** 是否被保护 */
	private int wProtect;
	/** 是否二次确认 */
	private int wIsReconfirm;
	/** 物品价格 */
	private int wPrice;
	/** 附加属性最小防御 */
	private int wFyXiao;
	/** 附加属性最大防御 */
	private int wFyDa;
	/** 附加属性最小攻击 */
	private int wGjXiao;
	/** 附加属性最大攻击 */
	private int wGjDa;
	/** 附加属性气血 */
	private int wHp;
	/** 附加属性法力 */
	private int wMp;
	/** 附加属性金防御力 */
	private int wJinFy;
	/** 附加属性木防御力 */
	private int wMuFy;
	/** 附加属性水防御力 */
	private int wShuiFy;
	/** 附加属性火防御力 */
	private int wHuoFy;
	/** 附加属性土防御力 */
	private int wTuFy;
	/** 附加属性金攻击力 */
	private int wJinGj;
	/** 附加属性木攻击力 */
	private int wMuGj;
	/** 附加属性水攻击力 */
	private int wShuiGj;
	/** 附加属性火攻击力 */
	private int wHuoGj;
	/** 附加属性土攻击力 */
	private int wTuGj;
	
	/**品质*/
	private int wQuality;
	/**装备五行类型*/
	private int wWxType;
	/** 套装id */
	private int suitId;
	/**附加buff是否有效，0表示无效，1表示有效*/
	private int   wBuffIsEffected;
	
	/** 点化属性附加类型 */
	private String enchantType;
	
	/** 点化属性附加值 */
	private int enchantValue;
	
	/** 追加的血量 */
	private int wZjHp;
	/** 追加的内力 */
	private int wZjMp;
	/** 追加的攻击 **/
	private int wZjWxGj;
	/**  追加的防御 **/
	private int wZjWxFy;
	
	/** 装备的等级 */
	private int wZbGrade;
	/**解除装备绑定次数*/
	private int wBondingNum;
	/**特殊属性**/
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
