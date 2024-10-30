package com.ben.vo.pet.pet;

/**
 * 功能: 宠物成长表
 * 
 * @author 侯浩军 3:26:49 PM
 */
public class PetShapeVO {
	/** 宠物成长id */
	private int shapeId;
	/** 宠物id */
	private int petId;
	/** 宠物类型1普通型 2异兽型 3瑞兽型 */
	private int petType;
	/** 等级 */
	private int shapeRating;
	/** 卖出价格 */
	private int shapeSale;
	/** 本级经验 达到改等级需要经验 */
	private String shapeBenExperience;
	/** 下级经验 达到下一级需要的经验 */
	private String shapeXiaExperience;
	/** 最小攻击 */
	private String shapeAttackXiao;
	/** 最大攻击 */
	private String shapeAttackDa;
	/** 升级 是否可自然升级 */
	private int shapeType;

	public int getShapeId() {
		return shapeId;
	}

	public void setShapeId(int shapeId) {
		this.shapeId = shapeId;
	}

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public int getPetType() {
		return petType;
	}

	public void setPetType(int petType) {
		this.petType = petType;
	}

	public int getShapeRating() {
		return shapeRating;
	}

	public void setShapeRating(int shapeRating) {
		this.shapeRating = shapeRating;
	}

	public int getShapeSale() {
		return shapeSale;
	}

	public void setShapeSale(int shapeSale) {
		this.shapeSale = shapeSale;
	}

	public String getShapeBenExperience() {
		return shapeBenExperience;
	}

	public void setShapeBenExperience(String shapeBenExperience) {
		this.shapeBenExperience = shapeBenExperience;
	}

	public String getShapeXiaExperience() {
		return shapeXiaExperience;
	}

	public void setShapeXiaExperience(String shapeXiaExperience) {
		this.shapeXiaExperience = shapeXiaExperience;
	}

	public String getShapeAttackXiao() {
		return shapeAttackXiao;
	}

	public void setShapeAttackXiao(String shapeAttackXiao) {
		this.shapeAttackXiao = shapeAttackXiao;
	}

	public String getShapeAttackDa() {
		return shapeAttackDa;
	}

	public void setShapeAttackDa(String shapeAttackDa) {
		this.shapeAttackDa = shapeAttackDa;
	}

	public int getShapeType() {
		return shapeType;
	}

	public void setShapeType(int shapeType) {
		this.shapeType = shapeType;
	}

}
