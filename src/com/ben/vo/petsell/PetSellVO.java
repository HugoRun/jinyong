/**
 * 
 */
package com.ben.vo.petsell;

/**
 * @author ��ƾ�
 * 
 * 2:09:29 PM
 */
public class PetSellVO {
	/** ID */
	private int psPk;
	/** �����ɫid */
	private int pPk;
	/** �������ɫid */
	private int pByPk;
	/** ��Ӧpet�����id */
	private int petId;
	/** ��������Ҫ��Ʒ�ļ۸������ */
	private int psSilverMoney;
	/** ��������Ҫ��Ʒ�ļ۸��ͭǮ */
	private int psCopperMoney;

	public int getPsPk() {
		return psPk;
	}

	public void setPsPk(int psPk) {
		this.psPk = psPk;
	}

	public int getPPk() {
		return pPk;
	}

	public void setPPk(int pk) {
		pPk = pk;
	}

	public int getPByPk() {
		return pByPk;
	}

	public void setPByPk(int byPk) {
		pByPk = byPk;
	}

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public int getPsSilverMoney() {
		return psSilverMoney;
	}

	public void setPsSilverMoney(int psSilverMoney) {
		this.psSilverMoney = psSilverMoney;
	}

	public int getPsCopperMoney() {
		return psCopperMoney;
	}

	public void setPsCopperMoney(int psCopperMoney) {
		this.psCopperMoney = psCopperMoney;
	}

}
