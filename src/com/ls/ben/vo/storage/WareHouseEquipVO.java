/**
 * 
 */
package com.ls.ben.vo.storage;

/**
 * @author zhangjj
 *
 */
public class WareHouseEquipVO
{
	/** 角色装备仓库表 */
	private int wPk;
	/** 角色id */
	private int pPk;
	/** 物品相关表类型 */
	private int tableType;
	/** 物品类型 */
	private int goodsType;
	/** 物品ID */
	private int wId;
	/** 物品名称 */
	private String wName;
	/** 耐久 */
	private int wDurability;
	/** 耐久消耗 */
	private int wDuraConsume;
	/** 绑定 */
	private int wBonding;
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
	/** 是否被装备 0 没有 1被装备了 */
	private int wType;
	
	/**品质*/
	private int wQuality;
	
	/** 套装id */
	private int suitId;
	/**装备五行类型*/
	private int wWxType;
	/**附加buff是否有效，0表示无效，1表示有效*/
	private int   wBuffIsEffected;
	
	
	/**保护*/
	private int wProtect;
	/**二次确认*/
	private int wIsreconfirm;

	/**卖出价钱*/
	private int wPrice;
	
	/** 装备点化附加属性类型  **/
	private String enchantType;
	
	
	/** 装备点化附加属性类型  **/
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
	/** 创建时间 */
	private String createTime;
	
	/** 装备初次使用 */
	private int pPoss;
	/**解除绑定次数*/
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

	public int getPPoss()
	{
		return pPoss;
	}

	public void setPPoss(int poss)
	{
		pPoss = poss;
	}

	public int getWZbGrade()
	{
		return wZbGrade;
	}

	public void setWZbGrade(int zbGrade)
	{
		wZbGrade = zbGrade;
	}

	public int getWPk()
	{
		return wPk;
	}

	public void setWPk(int pk)
	{
		wPk = pk;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public int getTableType()
	{
		return tableType;
	}

	public void setTableType(int tableType)
	{
		this.tableType = tableType;
	}

	public int getGoodsType()
	{
		return goodsType;
	}

	public void setGoodsType(int goodsType)
	{
		this.goodsType = goodsType;
	}

	public int getWId()
	{
		return wId;
	}

	public void setWId(int id)
	{
		wId = id;
	}

	public String getWName()
	{
		return wName;
	}

	public void setWName(String name)
	{
		wName = name;
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

	public int getWType()
	{
		return wType;
	}

	public void setWType(int type)
	{
		wType = type;
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

	public int getWProtect()
	{
		return wProtect;
	}

	public void setWProtect(int protect)
	{
		wProtect = protect;
	}

	public int getWIsreconfirm()
	{
		return wIsreconfirm;
	}

	public void setWIsreconfirm(int isreconfirm)
	{
		wIsreconfirm = isreconfirm;
	}

	public int getWPrice()
	{
		return wPrice;
	}

	public void setWPrice(int price)
	{
		wPrice = price;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public int getSuitId()
	{
		return suitId;
	}

	public void setSuitId(int suitId)
	{
		this.suitId = suitId;
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

}
