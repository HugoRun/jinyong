package com.ls.ben.vo.info.npc;

import com.ls.ben.cache.staticcache.npc.NpcCache;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.MathUtil;
import com.web.service.expnpcdrop.ExpnpcdropService;

/**   npc掉落表
 * @author 刘帅
 */
public class NpcdropVO {
	/** npc掉落表id */
	private int npcdropID;
	/** 怪物id */
	private int npcID;
	/** 物品类型 */
	private int goodsType;
	/** 物品id */
	private int goodsId;
	/** 物品名称*/
	private String goodsName;
	/** 落几率 杀死该npc掉落该物品的几率，100表示万分之一几率 */
	private int npcdropProbability;
	/** 大暴几率 大暴表示所有物品掉落几率为原来10倍，10表示杀死该npc有10%的几率大暴 */
	private int npcdropLuck;
	/**
	 * npc实际掉落物品的数范围字符串
	 * */
	private String npcDropNumStr;
	
	/**npc实际掉落物品的数量*/
	private int npcDropNum;
	
	/** 掉落途径,1:暂无用；2:打怪得到的物品；3:宝箱得到的物品；4：打怪得到的物品(特殊)； 5：表示宝箱掉落*/
	private int npcDropImprot;

	/**指定掉落的品质,0普通1优秀2良好3精品  -1表示按概率生成品质*/
	private int quality;
	
	/**
	 * 刷新时间(星期控制)
	 */
	private String weekStr;
	
	public NpcVO getNpcInfo()
	{
		return NpcCache.getById(npcID);
	}
	
	/**
	 * 是否掉落
	 * @param appendRate	追加玩家附加的概率
	 * @return
	 */
	public boolean isDrop( int appendRate )
	{
		if( DateUtil.isBetweenWeekStr(weekStr)==false )
		{
			return false;
		}
		
		int total_drop_rate = this.npcdropProbability;//总的掉落率
		
		if (MathUtil.isAppearByPercentage(this.npcdropLuck,MathUtil.DENOMINATOR)) // 判断大暴是否产生
		{
			total_drop_rate *= 5;//增加5被概率
		}
		//追加玩家附加的概率
		if(appendRate == 0){
			appendRate = 100;
		}
		total_drop_rate = total_drop_rate+total_drop_rate*(appendRate-100)/100;
		
		// 追加系统系统掉落倍数
		ExpnpcdropService expnpcdropService = new ExpnpcdropService(); 
		int sys_rate = expnpcdropService.getCimeliaNpcdrop();
		total_drop_rate *=sys_rate;
		
		return MathUtil.isAppearByPercentage(total_drop_rate, MathUtil.DROPDENOMINATOR);
	}
	
	public int getNpcDropImprot()
	{
		return npcDropImprot;
	}

	public void setNpcDropImprot(int npcDropImprot)
	{
		this.npcDropImprot = npcDropImprot;
	}

	public int getNpcdropID() {
		return npcdropID;
	}

	public void setNpcdropID(int npcdropID) {
		this.npcdropID = npcdropID;
	}

	public int getNpcID() {
		return npcID;
	}

	public void setNpcID(int npcID) {
		this.npcID = npcID;
	}

	public int getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public int getNpcdropProbability() {
		return npcdropProbability;
	}

	public void setNpcdropProbability(int npcdropProbability) {
		if( npcdropProbability<0 )
		{
			this.npcdropProbability = MathUtil.DROPDENOMINATOR;
		}
		else
		{
			this.npcdropProbability = npcdropProbability;		
		}
	}

	public int getNpcdropLuck() {
		return npcdropLuck;
	}

	public void setNpcdropLuck(int npcdropLuck) {
		this.npcdropLuck = npcdropLuck;
	}

	public int getNpcDropNum() {
		return npcDropNum;
	}

	public String getNpcDropNumStr() {
		return npcDropNumStr;
	}

	public void setNpcDropNumStr(String npcDropNumStr) {
		this.npcDropNumStr = npcDropNumStr;
		String temp[]=npcDropNumStr.split(",");
		this.npcDropNum = MathUtil.getRandomBetweenXY(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]));
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public int getQuality()
	{
		return quality;
	}

	public void setQuality(int quality)
	{
		this.quality = quality;
	}

	public void setWeekStr(String weekStr)
	{
		this.weekStr = weekStr;
	}

	

}
