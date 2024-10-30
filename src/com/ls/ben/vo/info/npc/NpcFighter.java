package com.ls.ben.vo.info.npc;

import org.apache.log4j.Logger;


/**
 * 功能:
 * @author 刘帅
 * 10:28:39 AM
 */
public class NpcFighter extends NpcAttackVO {
	Logger logger = Logger.getLogger(NpcFighter.class);
	private int playerInjure;
	private int petInjure;
	private boolean isDead;
	private int expendMP;
	private String skillName = "普通";
	/**伤害显示描述*/
	private String petInjureOut;
	
	private int dropExp;
	
	
	
	public int getDropExp() {
		return dropExp;
	}
	public void setDropExp(int dropExp) {
		this.dropExp = dropExp;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	public int getPlayerInjure() {
		return playerInjure;
	}
	public void setPlayerInjure(int playerInjure) {
		
		int currentHP=getCurrentHP()-playerInjure;
		if(getCurrentHP() < 0){
			setCurrentHP(0);
			isDead = true;
			logger.debug("npc死亡");
		}
		if( currentHP<= 0 )
		{	
			setCurrentHP(0);
			isDead = true;
			logger.debug("npc死亡");
		}
//		else
//		{
//			setCurrentHP(currentHP);
//		}
		this.playerInjure = playerInjure;
	}
	public int getExpendMP() {
		return expendMP;
	}
	public void setExpendMP(int expendMP) {
		this.expendMP = expendMP;
	}
	public boolean isDead() {
		return isDead;
	}
	public int getPetInjure() {
		return petInjure;
	}
	public void setPetInjure(int petInjure) {
		
		int currentHP=getCurrentHP()-petInjure;
		if(getCurrentHP() < 0){
			setCurrentHP(0);
			isDead = true;
			logger.debug("npc死亡");
		}
		if( currentHP<= 0 )
		{
			setCurrentHP(0);
			isDead = true;
			logger.debug("玩家死亡");
		}
//		else
//		{
//			setCurrentHP(currentHP);
//		}
		
		this.petInjure = petInjure;
	}
	public String getPetInjureOut()
	{
		return petInjureOut;
	}
	public void setPetInjureOut(String petInjureOut)
	{
		this.petInjureOut = petInjureOut;
	}
}
