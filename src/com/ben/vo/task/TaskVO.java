package com.ben.vo.task;

/**
 * 功能:
 * @author 侯浩军 10:58:10 AM
 */
public class TaskVO {
	public final static String taskZu = "sy_zhuxian,sx_zhuxian,st_zhuxian,yzs_zhuxian,jzz_zhuxian";
	/** 任务标识 */
	private int tId;
	/** 任务组同一系列任务使用一个任务组code */
	private String tZu;
	/** 任务组排序表示在此任务group中的位置 */
	private String tZuxl;
	/** 任务名 */
	private String tName;
	/** 任务领取最小等级 */
	private int tLevelXiao;
	/** 任务领取最大等级 */
	private int tLevelDa;
	/** 任务称号限制 */
	private String tSchool;
	/** 任务类型 */
	private int tType;
	/** 开始任务对白 */
	private String tDisplay;
	/** 开始任务提示 */
	private String tTishi;
	/**开始任务提示的关键字，多个以,号分割*/
	private String tKey;
	/**关键字所在的地图，多个以,号分割*/
	private String tKeyValue;
	/** 任务开始给予道具 */
	private String tGeidj;
	/** 任务开始给予道具数量 */
	private String tGeidjNumber;

	/** 中间点 如有多个中间点应用，隔开 */
	private String tPoint;
	/** 通过中间点的描述 */
	private String tZjms;
	/** 不能通过中间点的描述 */
	private String tBnzjms;
	/** 通过中间点需要的物品 */
	private String tZjdwp;
	/** 通过中间点需要物品的数量 */
	private int tZjdwpNumber;
	/** 通过中间点是否删除任务道具0不删除1删除 */
	private int tDjsc;

	/** 通过中间点给的物品 */
	private String tMidstGs;

	/** 完成任务需要道具 */
	private String tGoods;
	/** 完成任务需要道具数量 */
	private String tGoodsNumber;
	/** 完成任务需要装备 */
	private String tGoodszb;
	/** 完成任务需要装备数量 */
	private String tGoodszbNumber;

	/** 完成任务需要的杀戮 */
	private String tKilling;
	/** 完成任务需要的杀戮数量 */
	private int tKillingNo;
	/** 完成任务金钱奖励 */
	private String tMoney;
	/** 完成任务经验奖励 */
	private String tExp;
	/** 完成任务声望奖励种类 */
	private int tSwType;
	/** 完成任务声望奖励 */
	private String tSw;
	/** 物质奖励 */
	private String tEncouragement;
	/** 物质奖励的数量 */
	private String tWncouragementNo;
	/** 下一步任务开始npc的id */
	private int tXrwnpcId;
	/** 下一个任务id */
	private int tNext;

	/** 物质奖励 */
	private String tEncouragementZb;
	/** 物质奖励的数量 */
	private String tEncouragementNoZb;
	/** 是否可循环接受任务 0 不可以循环 1可以循环 */
	private int tCycle;
	
	/**
	 * 是否是最后一条任务
	 * @return
	 */
	public boolean isLast()
	{
		if( this.tId==this.tNext )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	public String getTKey()
	{
		return tKey;
	}

	public void setTKey(String key)
	{
		tKey = key;
	}

	public String getTKeyValue()
	{
		return tKeyValue;
	}

	public void setTKeyValue(String keyValue)
	{
		tKeyValue = keyValue;
	}

	public int getTCycle()
	{
		return tCycle;
	}

	public void setTCycle(int cycle)
	{
		tCycle = cycle;
	}

	public String getTEncouragementZb()
	{
		return tEncouragementZb;
	}

	public void setTEncouragementZb(String encouragementZb)
	{
		tEncouragementZb = encouragementZb;
	}

	public String getTEncouragementNoZb()
	{
		return tEncouragementNoZb;
	}

	public void setTEncouragementNoZb(String encouragementNoZb)
	{
		tEncouragementNoZb = encouragementNoZb;
	}

	public String getTMidstGs()
	{
		return tMidstGs;
	}

	public void setTMidstGs(String midstGs)
	{
		tMidstGs = midstGs;
	}


	public int getTId()
	{
		return tId;
	}

	public String getTGeidj()
	{
		return tGeidj;
	}

	public void setTGeidj(String geidj)
	{
		tGeidj = geidj;
	}

	public String getTGeidjNumber()
	{
		return tGeidjNumber;
	}

	public void setTGeidjNumber(String geidjNumber)
	{
		tGeidjNumber = geidjNumber;
	}


	public String getTGoodsNumber()
	{
		return tGoodsNumber;
	}

	public void setTGoodsNumber(String goodsNumber)
	{
		tGoodsNumber = goodsNumber;
	}

	public String getTGoodszb()
	{
		return tGoodszb;
	}

	public void setTGoodszb(String goodszb)
	{
		tGoodszb = goodszb;
	}

	public String getTGoodszbNumber()
	{
		return tGoodszbNumber;
	}

	public void setTGoodszbNumber(String goodszbNumber)
	{
		tGoodszbNumber = goodszbNumber;
	}

	public void setTId(int id)
	{
		tId = id;
	}

	public String getTZu()
	{
		return tZu;
	}

	public void setTZu(String zu)
	{
		tZu = zu;
	}

	public String getTZuxl()
	{
		return tZuxl;
	}

	public void setTZuxl(String zuxl)
	{
		tZuxl = zuxl;
	}

	public String getTName()
	{
		return tName;
	}

	public void setTName(String name)
	{
		tName = name;
	}

	public int getTLevelXiao()
	{
		return tLevelXiao;
	}

	public void setTLevelXiao(int levelXiao)
	{
		tLevelXiao = levelXiao;
	}

	public int getTLevelDa()
	{
		return tLevelDa;
	}

	public void setTLevelDa(int levelDa)
	{
		tLevelDa = levelDa;
	}


	public String getTSchool()
	{
		return tSchool;
	}

	public void setTSchool(String school)
	{
		tSchool = school;
	}

	public int getTType()
	{
		return tType;
	}

	public void setTType(int type)
	{
		tType = type;
	}


	public String getTDisplay()
	{
		return tDisplay;
	}

	public void setTDisplay(String display)
	{
		tDisplay = display;
	}

	public String getTTishi()
	{
		return tTishi;
	}

	public void setTTishi(String tishi)
	{
		tTishi = tishi;
	}

	public String getTPoint()
	{
		return tPoint;
	}

	public void setTPoint(String point)
	{
		tPoint = point;
	}

	public String getTZjms()
	{
		return tZjms;
	}

	public void setTZjms(String zjms)
	{
		tZjms = zjms;
	}

	public String getTBnzjms()
	{
		return tBnzjms;
	}

	public void setTBnzjms(String bnzjms)
	{
		tBnzjms = bnzjms;
	}

	public String getTZjdwp()
	{
		return tZjdwp;
	}

	public void setTZjdwp(String zjdwp)
	{
		tZjdwp = zjdwp;
	}

	public int getTZjdwpNumber()
	{
		return tZjdwpNumber;
	}

	public void setTZjdwpNumber(int zjdwpNumber)
	{
		tZjdwpNumber = zjdwpNumber;
	}

	public int getTDjsc()
	{
		return tDjsc;
	}

	public void setTDjsc(int djsc)
	{
		tDjsc = djsc;
	}

	public String getTGoods()
	{
		return tGoods;
	}

	public void setTGoods(String goods)
	{
		tGoods = goods;
	}

	public String getTKilling()
	{
		return tKilling;
	}

	public void setTKilling(String killing)
	{
		tKilling = killing;
	}

	public int getTKillingNo()
	{
		return tKillingNo;
	}

	public void setTKillingNo(int killingNo)
	{
		tKillingNo = killingNo;
	}


	public String getTMoney()
	{
		return tMoney;
	}

	public void setTMoney(String money)
	{
		tMoney = money;
	}

	public String getTExp()
	{
		return tExp;
	}

	public void setTExp(String exp)
	{
		tExp = exp;
	}

	public int getTSwType()
	{
		return tSwType;
	}

	public void setTSwType(int swType)
	{
		tSwType = swType;
	}

	public String getTSw()
	{
		return tSw;
	}

	public void setTSw(String sw)
	{
		tSw = sw;
	}

	public String getTEncouragement()
	{
		return tEncouragement;
	}

	public void setTEncouragement(String encouragement)
	{
		tEncouragement = encouragement;
	}

	public String getTWncouragementNo()
	{
		return tWncouragementNo;
	}

	public void setTWncouragementNo(String wncouragementNo)
	{
		tWncouragementNo = wncouragementNo;
	}

	public int getTXrwnpcId()
	{
		return tXrwnpcId;
	}

	public void setTXrwnpcId(int xrwnpcId)
	{
		tXrwnpcId = xrwnpcId;
	}

	public int getTNext()
	{
		return tNext;
	}

	public void setTNext(int next)
	{
		tNext = next;
	}
}
