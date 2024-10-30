/**
 * 
 */
package com.ben.vo.pet.pet;

/**
 * @author 侯浩军
 * 功能:宠物表
 * 10:31:57 AM
 */
public class PetVO {
	/** 宠物id */
	private int petId;
	/** 相关NPCID */
	private int npcId;
	/** 宠物名称 */
	private String petName;
	/** 宠物图片 */
	private String petImg;
	/** 宠物成长率*/
	private double petDropDa;
	/** 宠物成长率 */
	private double petDropXiao;
	/** 五行属性 金=1，木=2，水=3，火=4，土=5 */
	private String petWx;
	/** 五行属性值 */
	private String petWxValue;
	/** 升级 是否可自然升级 */
	private int petIsAutoGrow;
	/** 疲劳度0-100,出战状态下增加疲劳度，一个小时加10点 */
	private int petFatigue;
	/** 宠物寿命 */
	private int petLonge;
	/** 寿命道具使用次数 */
	private int longeNumber;
	/** 这个宠物最多可以学习多少个技能 */
	private int skillControl;
	/**技能1	可学习的技能id*/
	private int petSkillOne;
	/**技能2	可学习的技能id*/
    private int petSkillTwo;
    /**技能3	可学习的技能id*/
    private int petSkillThree;
    /**技能4	可学习的技能id*/
    private int petSkillFour;
    /**技能5	可学习的技能id*/
    private int petSkillFive;
    
    private int petType;
    
    /** 宠物暴击率 */
    private double petViolenceDorp;
    
	public double getPetViolenceDorp()
	{
		return petViolenceDorp;
	}

	public void setPetViolenceDorp(double petViolenceDorp)
	{
		this.petViolenceDorp = petViolenceDorp;
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

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public int getNpcId() {
		return npcId;
	}

	public void setNpcId(int npcId) {
		this.npcId = npcId;
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

	public double getPetDropDa() {
		return petDropDa;
	}

	public void setPetDropDa(double petDropDa) {
		this.petDropDa = petDropDa;
	}

	public double getPetDropXiao() {
		return petDropXiao;
	}

	public void setPetDropXiao(double petDropXiao) {
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

	public int getPetType() {
		return petType;
	}

	public void setPetType(int petType) {
		this.petType = petType;
	}

	public int getPetFatigue() {
		return petFatigue;
	}

	public void setPetFatigue(int petFatigue) {
		this.petFatigue = petFatigue;
	}

	public int getPetLonge() {
		return petLonge;
	}

	public void setPetLonge(int petLonge) {
		this.petLonge = petLonge;
	}

	public int getLongeNumber() {
		return longeNumber;
	}

	public void setLongeNumber(int longeNumber) {
		this.longeNumber = longeNumber;
	}

	public int getSkillControl() {
		return skillControl;
	}

	public void setSkillControl(int skillControl) {
		this.skillControl = skillControl;
	}

	public int getPetIsAutoGrow()
	{
		return petIsAutoGrow;
	}

	public void setPetIsAutoGrow(int petIsAutoGrow)
	{
		this.petIsAutoGrow = petIsAutoGrow;
	}

}
