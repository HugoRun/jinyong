package com.ls.ben.vo.info.pet;
/**
 * ����:
 * @author ��˧
 *
 * 9:45:55 AM
 */
public class PetshapeVO {
	/** ����ɳ�id */
	private int shapeId;
	/**����id*/
	private int petId;
	/**�����۸�*/
	private int shapeSale;
	/** �ȼ� */
	private int shapeRating;
	/** �������� �ﵽ�ĵȼ���Ҫ���� */
	private String shapeBenExperience;
	/** �¼����� �ﵽ��һ����Ҫ�ľ��� */
	private String shapeXiaExperience;
	/** ��С���� */
	private String shapeAttackXiao;
	/** ��󹥻� */
	private String shapeAttackDa;

	/** ���� �Ƿ����Ȼ���� */
	private int shapeType;

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public int getShapeId() {
		return shapeId;
	}

	public void setShapeId(int shapeId) {
		this.shapeId = shapeId;
	}

	public int getShapeRating() {
		return shapeRating;
	}

	public void setShapeRating(int shapeRating) {
		this.shapeRating = shapeRating;
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


	public int getShapeSale() {
		return shapeSale;
	}

	public void setShapeSale(int shapeSale) {
		this.shapeSale = shapeSale;
	}

	public int getShapeType() {
		return shapeType;
	}

	public void setShapeType(int shapeType) {
		this.shapeType = shapeType;
	}

}
