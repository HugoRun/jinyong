package com.ls.ben.vo.application;

import java.util.Date;

/**
 * 功能:记录玩家pk时的状态
 * @author 刘帅
 * 8:58:05 AM
 */
public class PkInfoVO
{
	/**pk状态  0:表示不在战斗状态；1:表示主动攻击状态；2:表示被动攻击状态**/
	private int   pkStat;
	/**本方死亡标志*/
	private boolean isDead;
	
	/**本方死亡通知*/
	private boolean  fightOverNotify;
	
	
	/**对方pk*/
	private String enemyPk;
	private boolean enemyIsDead;

	private int dropExp;
	private int attackMap;
	/**战斗开始时间*/
	private Date createTime;
	public int getPkStat()
	{
		return pkStat;
	}
	public void setPkStat(int pkStat)
	{
		this.pkStat = pkStat;
	}
	public boolean isDead()
	{
		return isDead;
	}
	public void setDead(boolean isDead)
	{
		this.isDead = isDead;
	}
	
	public String getEnemyPk()
	{
		return enemyPk;
	}
	public void setEnemyPk(String enemyPk)
	{
		this.enemyPk = enemyPk;
	}
	public int getDropExp()
	{
		return dropExp;
	}
	public void setDropExp(int dropExp)
	{
		this.dropExp = dropExp;
	}
	public int getAttackMap()
	{
		return attackMap;
	}
	public void setAttackMap(int attackMap)
	{
		this.attackMap = attackMap;
	}
	public Date getCreateTime()
	{
		return createTime;
	}
	public boolean isEnemyDead()
	{
		return enemyIsDead;
	}
	public void setIsEnemyDead(boolean enemyIsDead)
	{
		this.enemyIsDead = enemyIsDead;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
}
