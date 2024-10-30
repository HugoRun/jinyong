package com.ls.ben.vo.info.pet;

/**
 * 功能:
 * 
 * @author 刘帅
 * 
 * 9:45:55 AM
 */
public class PetSkillVO
{
	/** 技能id */
	private int petSkillId;
	/** 技能名称 */
	private String petSkillName;
	/** 技能描述 */
	private String petSkillBewrite;
	/** 技能类型 */
	private int petSkillType;

	/** 最小伤害值 */
	private int petSkillGjXiao;
	/** 最大伤害值 */
	private int petSkillGjDa;

	/** 使用范围 */
	private int petSkillArea;

	/** 暴击率加成 */
	// private int petViolenceDropMultiple;
	/** 攻击次数 */
	private int petSkillSeveral;

	/** 攻击倍率 */
	private double petSkillMultiple;

	/** 暴击率加成 */
	private double petViolenceDropMultiple;

	/** 物理攻击力加成 */
	private double petInjureMultiple;
	/** 宠物技能组 */
	private int petSkGroup;

	/** 宠物技能等级 */
	private int petSkLevel;

	/** 宠物学习该技能所需要的等级 */
	private int petGrade;

	public int getPetGrade()
	{
		return petGrade;
	}

	public void setPetGrade(int petGrade)
	{
		this.petGrade = petGrade;
	}

	public int getPetSkGroup()
	{
		return petSkGroup;
	}

	public void setPetSkGroup(int petSkGroup)
	{
		this.petSkGroup = petSkGroup;
	}

	public int getPetSkLevel()
	{
		return petSkLevel;
	}

	public void setPetSkLevel(int petSkLevel)
	{
		this.petSkLevel = petSkLevel;
	}

	public int getPetSkillSeveral()
	{
		return petSkillSeveral;
	}

	public void setPetSkillSeveral(int petSkillSeveral)
	{
		this.petSkillSeveral = petSkillSeveral;
	}

	public double getPetSkillMultiple()
	{
		return petSkillMultiple;
	}

	public void setPetSkillMultiple(double petSkillMultiple)
	{
		this.petSkillMultiple = petSkillMultiple;
	}

	public double getPetViolenceDropMultiple()
	{
		return petViolenceDropMultiple;
	}

	public void setPetViolenceDropMultiple(double petViolenceDropMultiple)
	{
		this.petViolenceDropMultiple = petViolenceDropMultiple;
	}

	public double getPetInjureMultiple()
	{
		return petInjureMultiple;
	}

	public void setPetInjureMultiple(double petInjureMultiple)
	{
		this.petInjureMultiple = petInjureMultiple;
	}

	public int getPetSkillId()
	{
		return petSkillId;
	}

	public void setPetSkillId(int petSkillId)
	{
		this.petSkillId = petSkillId;
	}

	public String getPetSkillName()
	{
		return petSkillName;
	}

	public void setPetSkillName(String petSkillName)
	{
		this.petSkillName = petSkillName;
	}

	public String getPetSkillBewrite()
	{
		return petSkillBewrite;
	}

	public void setPetSkillBewrite(String petSkillBewrite)
	{
		this.petSkillBewrite = petSkillBewrite;
	}

	public int getPetSkillType()
	{
		return petSkillType;
	}

	public void setPetSkillType(int petSkillType)
	{
		this.petSkillType = petSkillType;
	}

	public int getPetSkillArea()
	{
		return petSkillArea;
	}

	public void setPetSkillArea(int petSkillArea)
	{
		this.petSkillArea = petSkillArea;
	}

	public int getPetSkillGjXiao()
	{
		return petSkillGjXiao;
	}

	public void setPetSkillGjXiao(int petSkillGjXiao)
	{
		this.petSkillGjXiao = petSkillGjXiao;
	}

	public int getPetSkillGjDa()
	{
		return petSkillGjDa;
	}

	public void setPetSkillGjDa(int petSkillGjDa)
	{
		this.petSkillGjDa = petSkillGjDa;
	}

	// public int getPetViolenceDropMultiple()
	// {
	// return petViolenceDropMultiple;
	// }
	//
	// public void setPetViolenceDropMultiple(int petViolenceDropMultiple)
	// {
	// this.petViolenceDropMultiple = petViolenceDropMultiple;
	// }

}
