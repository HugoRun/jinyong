package com.ls.ben.vo.info.npc;

import org.apache.log4j.Logger;

import com.ls.ben.vo.info.attribute.attack.SingleWXAttack;
import com.ls.ben.vo.info.attribute.attack.WXDefence;
import com.ls.pub.util.MathUtil;


public class NpcAttackVO extends NpcVO implements WXDefence,SingleWXAttack {
	Logger logger =  Logger.getLogger(NpcAttackVO.class);
	
	public static final int DEADNPC = 1;
	public static final int LOSENPC = 2;
	/** ���������ֵ�npc,��ս������� */
	public static final int MAST = 3;
	
	
	/** ����ս���� */
	public static final int CITYDOOR = 4;
	/** ��Ӣ�۵���ս */
	public static final int DIAOXIANG = 6;
	
	/** ���������ֵ�npc,����ս�����л��,���Ǿ������˲�ͬ */
	public static final int ZHAOHUN = 5;
	
	public static final int NIANSHOU = 8;
	
	/**��ǰս��ID*/
	private int ID;
	/**������Ա��Ϣid*/
	private int uPk;
	/**��ɫID*/
	private int pPk;
	/**npc��ǰѪֵ*/
	private int currentHP;
	
	 /**������������	0��ʾ�˵�ˢ�³���npc����������ң�1��ʾ�˵�ˢ�³���npc����������ң�*/
	private int nAttackswitch;
	
	
	/**������������С����֮�����ȡnpc����ֵ*/
	private int npcDefance;

	/**��ʾnpc�Ƿ���ս��״̬��1��ʾ�ǣ�0��ʾ��Ĭ��Ϊ0*/
	private int npcIsAttack=0;

	
	
	/**��������:��=1��ľ=2��ˮ=3����=4����=5��*/
	private int wx;
	
	/**�����˺�*/
	private int wxValue;
	
	/**
	 * npc���ͣ����Ա���ܵ�npcΪ2���Ϳ��Ա�������npcΪ1
	 */
	private int npcType;
	/**
	 * npc���ڵص�
	 */
	private int sceneId;

	/** ����״̬��ʣ��غ��� */
	private int dizzyBoutNum;
	
	/**�ж�״̬ʣ��غ���**/
	private int poisonBoutNum;
	
	/**���ӹ���*/
	public int appendGj=0;

	/***NPC��������***/
	public String npcdeaddisplay;
	
	/***NPC�˺���ֵ*****/
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
			logger.debug("����������С������ֵδ����ʼ��");
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
