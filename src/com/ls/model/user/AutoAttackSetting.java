package com.ls.model.user;

public class AutoAttackSetting
{
	/** ����,ֵΪ-1ʱ��ʾû�����ÿ�ݼ� */
	private int attackType=-1;
	/** ����id */
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
