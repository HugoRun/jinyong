package com.ls.ben.vo.info.pet;

/**
 * ����:petʵ����
 * 
 * @author ��˧
 * 
 * 9:05:32 AM
 */
public class PetSkillControlVO
{
	/** ����id */
	private int controlId;
	/** ����id */
	private int petId;
	/** ����id */
	private int petSkillId;
	/** ���м��� */
	private int controlDrop;
	/** ���＼���� */
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
