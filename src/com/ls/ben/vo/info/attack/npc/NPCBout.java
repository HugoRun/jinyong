package com.ls.ben.vo.info.attack.npc;

import java.util.List;



//��¼������ÿ�غϵ���ز���
public class NPCBout {
	/**��ɫid*/
	int pPk;
	
	/**�û���ǰ��Ѫ��*/
	int pCurrentHP;
	
	/** �û��ļ�Ѫ��   */
	int pInjureValue;
	/**�˺�����*/
	String pInjureDisplay;
	
	/** �û���ǰ����ֵ */
	int pCurrentMP;
	
	/**�û����ķ�������*/
	int pMPExpendValue;
	
	/** �û�������־ */
	boolean pIsDead = false;
	
	/** �û��������� */
	String pDeadDisplay = "���������ˣ�<br/>";
	
	/** �û���ʽ���� */
	String pSkillDisplay = "";
	
	/**��ɫʤ��,npc���е�����Ʒ*/
	List pDropGoods = null;
	
	/**��ɫʧȥ��Ʒ����*/
	String pDropDisplay = "";
	
	/**��ɫʤ���õ�����*/
	private int pGetExp;
	/**��ɫʤ���õ�Ǯ��*/
	private int pGetMoney;
	/**��ɫ�Ƿ�ʤ��*/
	private boolean pIsWin=false;
	/**��ɫ�Ƿ������μ�ս��*/
	private boolean pIsBringPet;
	/**���ܲ���ʹ�õ�����*/
	private String pSkillNoUseDisplay;
	/**�����ɫ�����ᱻ��ֵ����ǰ����*/
	private String pCurrentLevel = "";
	

	
	
	/**NPCid*/
	int nPk;
	
	/**����npc״̬*/
	List npcs = null;
	
	/** ����������־ */
	boolean nIsDead = false;
	
	/** NPC�������� */
	String nDeadDisplay = "";
	
	/** NPC��ʽ���� */
	String nSkillDisplay = "";
	
	/**�Ƿ�������npc*/
	boolean nIsHave = true;
	
	
	
	String petName = "";
	String petSkillName = "";
	

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getPetSkillName() {
		return petSkillName;
	}

	public void setPetSkillName(String petSkillName) {
		this.petSkillName = petSkillName;
	}

	public boolean isNIsHave() {
		return nIsHave;
	}

	public void setNIsHave(boolean isHave) {
		nIsHave = isHave;
	}

	public int getPPk() {
		return pPk;
	}

	public void setPPk(int pk) {
		pPk = pk;
	}

	public int getPCurrentHP() {
		return pCurrentHP;
	}

	public void setPCurrentHP(int currentHP) {
		pCurrentHP = currentHP;
		if( pCurrentHP <= 0 )
		{
			pIsDead = true;
		}
	}


	public int getPMPExpendValue() {
		return pMPExpendValue;
	}

	public void setPMPExpendValue(int expendValue) {
		pMPExpendValue = expendValue;
	}

	public int getNPk() {
		return nPk;
	}

	public void setNPk(int pk) {
		nPk = pk;
	}

	public int getPInjureValue() {
		return pInjureValue;
	}

	public void setPInjureValue(int injureValue) {
		pInjureValue = injureValue;
	}

	public String getPInjureDisplay() {
		return pInjureDisplay;
	}

	public void setPInjureDisplay(String injureDisplay) {
		pInjureDisplay = injureDisplay;
	}


	public int getPCurrentMP() {
		return pCurrentMP;
	}

	public void setPCurrentMP(int currentMP) {
		pCurrentMP = currentMP;
	}

	public boolean getPIsDead() {
		return pIsDead;
	}

	public void setPIsDead(boolean isDead) {
		pIsDead = isDead;
	}

	public boolean getNIsDead() {
		return nIsDead;
	}

	public void setNIsDead(boolean isDead) {
		nIsDead = isDead;
	}

	public String getPDeadDisplay() {
		return pDeadDisplay;
	}

	public void setPDeadDisplay(String deadDisplay) {
		pDeadDisplay = deadDisplay;
	}

	public String getNDeadDisplay() {
		return nDeadDisplay;
	}

	public void setNDeadDisplay(String deadDisplay) {
		nDeadDisplay = deadDisplay;
	}

	public String getPSkillDisplay() {
		return pSkillDisplay;
	}

	public void setPSkillDisplay(String skillDisplay) {
		pSkillDisplay = skillDisplay;
	}


	public String getNSkillDisplay() {
		return nSkillDisplay;
	}

	public void setNSkillDisplay(String skillDisplay) {
		nSkillDisplay = skillDisplay;
	}


	public List getPDropGoods() {
		return pDropGoods;
	}

	public void setPDropGoods(List dropGoods) {
		pDropGoods = dropGoods;
	}

	public String getPDropDisplay() {
		return pDropDisplay;
	}

	public void setPDropDisplay(String dropDisplay) {
		pDropDisplay = dropDisplay;
	}

	public int getPGetExp() {
		return pGetExp;
	}

	public void setPGetExp(int getExp) {
		pGetExp = getExp;
	}

	public int getPGetMoney() {
		return pGetMoney;
	}

	public void setPGetMoney(int getMoney) {
		pGetMoney = getMoney;
	}

	public List getNpcs() {
		return npcs;
	}

	public void setNpcs(List npcs) {
		this.npcs = npcs;
	}

	public boolean getPIsWin() {
		return pIsWin;
	}

	public void setPIsWin(boolean isWin) {
		pIsWin = isWin;
	}

	public boolean getPIsBringPet() {
		return pIsBringPet;
	}

	public void setPIsBringPet(boolean isBringPet) {
		pIsBringPet = isBringPet;
	}

	public String getPSkillNoUseDisplay() {
		return pSkillNoUseDisplay;
	}

	public void setPSkillNoUseDisplay(String skillNoUseDisplay) {
		pSkillNoUseDisplay = skillNoUseDisplay;
	}

	public String getPCurrentLevel() {
		return pCurrentLevel;
	}

	public void setPCurrentLevel(String currentLevel) {
		pCurrentLevel = currentLevel;
	}


}
