package com.ls.ben.vo.info.pet;

public class PetSkillDisplay
{
	// �غ�����
	private int petSkillRound = 0;
	// �ӳɵ�����
	private int petSkillTypeMultiple = 0;
	// �ӳ�ϵ��
	private double petSkillMultiple = 0;

	public int getPetSkillRound()
	{
		return petSkillRound;
	}

	public void setPetSkillRound(int petSkillRound)
	{
		this.petSkillRound = petSkillRound;
	}

	public int getPetSkillTypeMultiple()
	{
		return petSkillTypeMultiple;
	}

	public void setPetSkillTypeMultiple(int petSkillTypeMultiple)
	{
		this.petSkillTypeMultiple = petSkillTypeMultiple;
	}

	public double getPetSkillMultiple()
	{
		return petSkillMultiple;
	}

	public void setPetSkillMultiple(double petSkillMultiple)
	{
		this.petSkillMultiple = petSkillMultiple;
	}

	public int updateSkillRound(int round)
	{
		round = round - 1;
		if (round < 0)
		{
			round = 0;
		}
		return round;
	}
}
