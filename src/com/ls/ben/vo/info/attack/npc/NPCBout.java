package com.ls.ben.vo.info.attack.npc;

import java.util.List;



//记录攻击的每回合的相关参数
public class NPCBout {
	/**角色id*/
	int pPk;
	
	/**用户当前的血量*/
	int pCurrentHP;
	
	/** 用户的减血量   */
	int pInjureValue;
	/**伤害描述*/
	String pInjureDisplay;
	
	/** 用户当前法力值 */
	int pCurrentMP;
	
	/**用户消耗法力的量*/
	int pMPExpendValue;
	
	/** 用户死亡标志 */
	boolean pIsDead = false;
	
	/** 用户死亡描述 */
	String pDeadDisplay = "您被打死了！<br/>";
	
	/** 用户招式描述 */
	String pSkillDisplay = "";
	
	/**角色胜利,npc所有掉落物品*/
	List pDropGoods = null;
	
	/**角色失去物品描述*/
	String pDropDisplay = "";
	
	/**角色胜利得到经验*/
	private int pGetExp;
	/**角色胜利得到钱数*/
	private int pGetMoney;
	/**角色是否胜利*/
	private boolean pIsWin=false;
	/**角色是否带宠物参加战斗*/
	private boolean pIsBringPet;
	/**技能不能使用的描述*/
	private String pSkillNoUseDisplay;
	/**如果角色升级会被赋值，当前级数*/
	private String pCurrentLevel = "";
	

	
	
	/**NPCid*/
	int nPk;
	
	/**所有npc状态*/
	List npcs = null;
	
	/** 怪物死亡标志 */
	boolean nIsDead = false;
	
	/** NPC死亡描述 */
	String nDeadDisplay = "";
	
	/** NPC招式描述 */
	String nSkillDisplay = "";
	
	/**是否还有其他npc*/
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
