package com.ls.ben.vo.info.pet;

/**
 * 功能:pet实例类
 * 
 * @author 刘帅
 * 
 * 9:05:32 AM
 */
public class PetSkillControlVO
{
	/** 技能id */
	private int controlId;
	/** 宠物id */
	private int petId;
	/** 技能id */
	private int petSkillId;
	/** 出招几率 */
	private int controlDrop;
	/** 宠物技能组 */
	private int petSkGroup;

	public int getPetSkGroup()
	{
		return petSkGroup;
	}

	public void setPetSkGroup(int petSkGroup)
	{
		this.petSkGroup = petSkGroup;
	}

	public int getControlId()
	{
		return controlId;
	}

	public void setControlId(int controlId)
	{
		this.controlId = controlId;
	}

	public int getPetId()
	{
		return petId;
	}

	public void setPetId(int petId)
	{
		this.petId = petId;
	}

	public int getPetSkillId()
	{
		return petSkillId;
	}

	public void setPetSkillId(int petSkillId)
	{
		this.petSkillId = petSkillId;
	}

	public int getControlDrop()
	{
		return controlDrop;
	}

	public void setControlDrop(int controlDrop)
	{
		this.controlDrop = controlDrop;
	}

}
