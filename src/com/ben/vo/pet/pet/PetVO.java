/**
 * 
 */
package com.ben.vo.pet.pet;

/**
 * @author ��ƾ�
 * ����:�����
 * 10:31:57 AM
 */
public class PetVO {
	/** ����id */
	private int petId;
	/** ���NPCID */
	private int npcId;
	/** �������� */
	private String petName;
	/** ����ͼƬ */
	private String petImg;
	/** ����ɳ���*/
	private double petDropDa;
	/** ����ɳ��� */
	private double petDropXiao;
	/** �������� ��=1��ľ=2��ˮ=3����=4����=5 */
	private String petWx;
	/** ��������ֵ */
	private String petWxValue;
	/** ���� �Ƿ����Ȼ���� */
	private int petIsAutoGrow;
	/** ƣ�Ͷ�0-100,��ս״̬������ƣ�Ͷȣ�һ��Сʱ��10�� */
	private int petFatigue;
	/** �������� */
	private int petLonge;
	/** ��������ʹ�ô��� */
	private int longeNumber;
	/** �������������ѧϰ���ٸ����� */
	private int skillControl;
	/**����1	��ѧϰ�ļ���id*/
	private int petSkillOne;
	/**����2	��ѧϰ�ļ���id*/
    private int petSkillTwo;
    /**����3	��ѧϰ�ļ���id*/
    private int petSkillThree;
    /**����4	��ѧϰ�ļ���id*/
    private int petSkillFour;
    /**����5	��ѧϰ�ļ���id*/
    private int petSkillFive;
    
    private int petType;
    
    /** ���ﱩ���� */
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
