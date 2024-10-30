package com.ls.ben.vo.info.npc;

import com.ls.pub.util.MathUtil;

public class NpcVO {
	/**npcID*/
	private int npcID;
	/**����	npc����*/
	private String npcName;
	/**ͼƬ*/
	private String pic;
	/**��Ѫ	npc��Ѫֵ*/
	private int npcHP;
	
	/**������	npc����*/
	private int defenceDa;
	/**��С����	npc����*/
	private int defenceXiao;

	 /**�����ֵ*/
	private int jinFy;
	/**ľ����*/
	private int muFy;
	/**ˮ����*/
	private int shuiFy;
	/**�����*/
	private int huoFy;
	/**������*/
	private int tuFy;
	
	
	
	/**������	��ʾΪ����������20��ʾ20%�ı�����*/
	private int drop;
	/**�ȼ�	npc�ȼ�*/
	private int level;
	/**����	ɱ��npc��þ���*/
	private int exp;
	/**��Ʒ����Ǯ��*/
	private String money;
	/**�ɷ�׽	0��ʾ���ɲ�׽��1��ʾ���Բ�׽*/
	private int take;
	 /**Ψһ�ؼ��֣�Ŀ��Ϊ�˸��ݹؼ���ȡ��idֵ*/
	private String npcKey;
	
	/**NPC�����Ǯ���������*/
	private int dropMoney;
	
	/**ˢ��ʱ����	����Ϊ��λ*/
	private int npcRefurbishTime;
	
	/**
	 * npc���ͣ����Ա���ܵ�npcΪ2���Ϳ��Ա�������npcΪ1
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
