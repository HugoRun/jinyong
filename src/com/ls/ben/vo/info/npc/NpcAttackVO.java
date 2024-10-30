package com.ls.ben.vo.info.npc;

import org.apache.log4j.Logger;

import com.ls.ben.vo.info.attribute.attack.SingleWXAttack;
import com.ls.ben.vo.info.attribute.attack.WXDefence;
import com.ls.pub.util.MathUtil;


public class NpcAttackVO extends NpcVO implements WXDefence,SingleWXAttack {
	Logger logger =  Logger.getLogger(NpcAttackVO.class);
	
	public static final int DEADNPC = 1;
	public static final int LOSENPC = 2;
	/** 攻击不还手的npc,如战场的旗杆 */
	public static final int MAST = 3;
	
	
	/** 攻城战城门 */
	public static final int CITYDOOR = 4;
	/** 攻英雄雕像战 */
	public static final int DIAOXIANG = 6;
	
	/** 攻击不还手的npc,攻城战场的招魂幡,但是具体和旗杆不同 */
	public static final int ZHAOHUN = 5;
	
	public static final int NIANSHOU = 8;
	
	/**当前战斗ID*/
	private int ID;
	/**创建人员信息id*/
	private int uPk;
	/**角色ID*/
	private int pPk;
	/**npc当前血值*/
	private int currentHP;
	
	 /**主动攻击开关	0表示此点刷新出的npc被动攻击玩家，1表示此点刷新出的npc主动攻击玩家，*/
	private int nAttackswitch;
	
	
	/**在最大防御和最小防御之间随机取npc防御值*/
	private int npcDefance;

	/**标示npc是否处于战斗状态；1表示是，0表示否，默认为0*/
	private int npcIsAttack=0;

	
	
	/**技能五行:金=1，木=2，水=3，火=4，土=5。*/
	private int wx;
	
	/**五行伤害*/
	private int wxValue;
	
	/**
	 * npc类型，可以被打败的npc为2；和可以被打死的npc为1
	 */
	private int npcType;
	/**
	 * npc所在地点
	 */
	private int sceneId;

	/** 击晕状态的剩余回合数 */
	private int dizzyBoutNum;
	
	/**中毒状态剩余回合数**/
	private int poisonBoutNum;
	
	/**附加攻击*/
	public int appendGj=0;

	/***NPC死亡描述***/
	public String npcdeaddisplay;
	
	/***NPC伤害数值*****/
	public int npccountnum;

	public int getNpcIsAttack() {
		return npcIsAttack;
	}
	public void setNpcIsAttack(int npcIsAttack) {
		this.npcIsAttack = npcIsAttack;
	}
	public int getID() {
		return ID;
	}
	public void setID(int id) {
		ID = id;
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
	public int getCurrentHP() {
		return currentHP;
	}
	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}
	public int getNAttackswitch() {
		return nAttackswitch;
	}
	public void setNAttackswitch(int attackswitch) {
		nAttackswitch = attackswitch;
	}
	public int getNpcDefance() {
		if( getDefenceDa()==0 )
		{
			logger.debug("最大防御或最小防御的值未被初始化");
			return -1;
		}
		npcDefance = MathUtil.getRandomBetweenXY(getDefenceXiao(),getDefenceDa());
		return npcDefance;
	}

	public int getWx() {
		return wx;
	}
	public void setWx(int wx) {
		this.wx = wx;
	}
	public int getWxValue() {
		return wxValue;
	}
	public void setWxValue(int wxValue) {
		this.wxValue = wxValue;
	}
	public int getNpcType() {
		return npcType;
	}
	public void setNpcType(int npcType) {
		this.npcType = npcType;
	}
	public int getSceneId()
	{
		return sceneId;
	}
	public void setSceneId(int sceneId)
	{
		this.sceneId = sceneId;
	}
	
	public int getDizzyBoutNum()
	{
		return dizzyBoutNum;
	}
	public void setDizzyBoutNum(int dizzyBoutNum)
	{
		this.dizzyBoutNum = dizzyBoutNum;
	}
	public int getAppendGj()
	{
		return appendGj;
	}
	public void setAppendGj(int appendGj)
	{
		this.appendGj = appendGj;
	}
	public String getNpcdeaddisplay()
	{
		return npcdeaddisplay;
	}
	public void setNpcdeaddisplay(String npcdeaddisplay)
	{
		this.npcdeaddisplay = npcdeaddisplay;
	}
	public int getNpccountnum()
	{
		return npccountnum;
	}
	public void setNpccountnum(int npccountnum)
	{
		this.npccountnum = npccountnum;
	}
	public int getPoisonBoutNum()
	{
		return poisonBoutNum;
	}
	public void setPoisonBoutNum(int poisonBoutNum)
	{
		this.poisonBoutNum = poisonBoutNum;
	}
	
}
