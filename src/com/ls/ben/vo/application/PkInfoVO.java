package com.ls.ben.vo.application;

import java.util.Date;

/**
 * ����:��¼���pkʱ��״̬
 * @author ��˧
 * 8:58:05 AM
 */
public class PkInfoVO
{
	/**pk״̬  0:��ʾ����ս��״̬��1:��ʾ��������״̬��2:��ʾ��������״̬**/
	private int   pkStat;
	/**����������־*/
	private boolean isDead;
	
	/**��������֪ͨ*/
	private boolean  fightOverNotify;
	
	
	/**�Է�pk*/
	private String enemyPk;
	private boolean enemyIsDead;

	private int dropExp;
	private int attackMap;
	/**ս����ʼʱ��*/
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
