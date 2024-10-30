package com.ls.model.user;

public class AutoAttackSetting
{
	/** 类型,值为-1时表示没有设置快捷键 */
	private int attackType=-1;
	/** 操作id */
	private int uSkillId=0;

	public int getAttackType()
	{
		return attackType;
	}

	public int getUSkillId()
	{
		return uSkillId;
	}

	public void set(int attackType, int uSkillId)
	{
		this.attackType = attackType;
		this.uSkillId = uSkillId;
	}

	public void init()
	{
		this.attackType = -1;
		this.uSkillId = 0;
	}

}
