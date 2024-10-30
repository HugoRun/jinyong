/**
 * 
 */
package com.ben.vo.petsell;

/**
 * @author 侯浩军
 * 
 * 2:09:29 PM
 */
public class PetSellVO {
	/** ID */
	private int psPk;
	/** 请求角色id */
	private int pPk;
	/** 被请求角色id */
	private int pByPk;
	/** 对应pet表里的id */
	private int petId;
	/** 发出请求要物品的价格的银子 */
	private int psSilverMoney;
	/** 发出请求要物品的价格的铜钱 */
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
