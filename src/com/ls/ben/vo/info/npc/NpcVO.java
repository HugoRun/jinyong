package com.ls.ben.vo.info.npc;

import com.ls.pub.util.MathUtil;

public class NpcVO {
	/**npcID*/
	private int npcID;
	/**名称	npc名称*/
	private String npcName;
	/**图片*/
	private String pic;
	/**气血	npc气血值*/
	private int npcHP;
	
	/**最大防御	npc防御*/
	private int defenceDa;
	/**最小防御	npc防御*/
	private int defenceXiao;

	 /**金防御值*/
	private int jinFy;
	/**木防御*/
	private int muFy;
	/**水防御*/
	private int shuiFy;
	/**火防御*/
	private int huoFy;
	/**土防御*/
	private int tuFy;
	
	
	
	/**暴击率	表示为正整数，如20表示20%的暴击率*/
	private int drop;
	/**等级	npc等级*/
	private int level;
	/**经验	杀死npc获得经验*/
	private int exp;
	/**物品掉落钱数*/
	private String money;
	/**可否捕捉	0表示不可捕捉，1表示可以捕捉*/
	private int take;
	 /**唯一关键字，目的为了根据关键字取到id值*/
	private String npcKey;
	
	/**NPC掉落的钱数随机生成*/
	private int dropMoney;
	
	/**刷新时间间隔	分钟为单位*/
	private int npcRefurbishTime;
	
	/**
	 * npc类型，可以被打败的npc为2；和可以被打死的npc为1
	 */
	private int npcType;
	
	public int getNpcType()
	{
		return npcType;
	}


	public void setNpcType(int npcType)
	{
		this.npcType = npcType;
	}


	public int getDropMoney()
	{
		String temp[]=money.split(",");
		this.dropMoney = MathUtil.getRandomBetweenXY(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]));
		return dropMoney;
	}
	
	
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}

	public int getNpcID() {
		return npcID;
	}
	public void setNpcID(int npcID) {
		this.npcID = npcID;
	}
	public String getNpcName() {
		return npcName;
	}
	public void setNpcName(String npcName) {
		this.npcName = npcName;
	}
	public int getNpcHP() {
		return npcHP;
	}
	public void setNpcHP(int npcHP) {
		this.npcHP = npcHP;
	}

	public int getDefenceDa() {
		return defenceDa;
	}
	public void setDefenceDa(int defenceDa) {
		this.defenceDa = defenceDa;
	}
	public int getDefenceXiao() {
		return defenceXiao;
	}
	public void setDefenceXiao(int defenceXiao) {
		this.defenceXiao = defenceXiao;
	}

	public int getJinFy() {
		return jinFy;
	}
	public void setJinFy(int jinFy) {
		if(jinFy<0)
		{
			jinFy=0;
		}
		this.jinFy = jinFy;
	}
	public int getMuFy() {
		return muFy;
	}
	public void setMuFy(int muFy) {
		if(muFy<0)
		{
			muFy=0;
		}
		this.muFy = muFy;
	}
	public int getShuiFy() {
		return shuiFy;
	}
	public void setShuiFy(int shuiFy) {
		if(shuiFy<0)
		{
			shuiFy=0;
		}
		this.shuiFy = shuiFy;
	}
	public int getHuoFy() {
		return huoFy;
	}
	public void setHuoFy(int huoFy) {
		if(huoFy<0)
		{
			huoFy=0;
		}
		this.huoFy = huoFy;
	}
	public int getTuFy() {
		return tuFy;
	}
	public void setTuFy(int tuFy) {
		if(tuFy<0)
		{
			tuFy=0;
		}
		this.tuFy = tuFy;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getTake() {
		return take;
	}
	public void setTake(int take) {
		this.take = take;
	}
	public String getNpcKey() {
		return npcKey;
	}
	public void setNpcKey(String npcKey) {
		this.npcKey = npcKey;
	}


	public int getNpcRefurbishTime()
	{
		return npcRefurbishTime;
	}


	public void setNpcRefurbishTime(int npcRefurbishTime)
	{
		this.npcRefurbishTime = npcRefurbishTime;
	}


	public String getPic()
	{
		return pic;
	}


	public void setPic(String pic)
	{
		this.pic = pic;
	}


	public int getDrop()
	{
		return drop;
	}


	public void setDrop(int drop)
	{
		this.drop = drop;
	}



}
