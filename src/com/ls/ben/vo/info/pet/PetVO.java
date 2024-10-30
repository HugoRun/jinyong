/**
 * 
 */
package com.ls.ben.vo.info.pet;

/**
 * @author 侯浩军
 * 
 * 10:31:57 AM
 */
public class PetVO {
	/** 宠物id */
	private int petId;
	/** 宠物名称 */
	private String petName;
	/**宠物寿命*/
	private int petLife;
	/** 宠物图片 */
	private String petImg;
	/** 宠物最大成长率 如“min=1&max=1.2” */
	private int petDropDa;
	/** 宠物最小成长率 */
	private int petDropXiao;
	/** 五行属性 金=1，木=2，水=3，火=4，土=5 */
	private String petWx;
	/** 五行属性值 */
	private String petWxValue;
	/** 技能1 可学习的技能id */
	private int petSkillOne;
	/** 技能2 可学习的技能id */
	private int petSkillTwo;
	/** 技能3 可学习的技能id */
	private int petSkillThree;
	/** 技能4 可学习的技能id */
	private int petSkillFour;
	/** 技能5 可学习的技能id */
	private int petSkillFive;


	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}



	public String getPetImg() {
		return petImg;
	}

	public void setPetImg(String petImg) {
		this.petImg = petImg;
	}

	public int getPetDropDa() {
		return petDropDa;
	}

	public void setPetDropDa(int petDropDa) {
		this.petDropDa = petDropDa;
	}

	public int getPetDropXiao() {
		return petDropXiao;
	}

	public void setPetDropXiao(int petDropXiao) {
		this.petDropXiao = petDropXiao;
	}

	public String getPetWx() {
		return petWx;
	}

	public void setPetWx(String petWx) {
		this.petWx = petWx;
	}

	public String getPetWxValue() {
		return petWxValue;
	}

	public void setPetWxValue(String petWxValue) {
		this.petWxValue = petWxValue;
	}

	public int getPetSkillOne() {
		return petSkillOne;
	}

	public void setPetSkillOne(int petSkillOne) {
		this.petSkillOne = petSkillOne;
	}

	public int getPetSkillTwo() {
		return petSkillTwo;
	}

	public void setPetSkillTwo(int petSkillTwo) {
		this.petSkillTwo = petSkillTwo;
	}

	public int getPetSkillThree() {
		return petSkillThree;
	}

	public void setPetSkillThree(int petSkillThree) {
		this.petSkillThree = petSkillThree;
	}

	public int getPetSkillFour() {
		return petSkillFour;
	}

	public void setPetSkillFour(int petSkillFour) {
		this.petSkillFour = petSkillFour;
	}

	public int getPetSkillFive() {
		return petSkillFive;
	}

	public void setPetSkillFive(int petSkillFive) {
		this.petSkillFive = petSkillFive;
	}

	public int getPetLife() {
		return petLife;
	}

	public void setPetLife(int petLife) {
		this.petLife = petLife;
	}



}
