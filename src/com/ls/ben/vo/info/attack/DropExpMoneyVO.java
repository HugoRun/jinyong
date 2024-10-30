package com.ls.ben.vo.info.attack;

/**
 * 功能:掉落的金钱和经验
 * @author 刘帅
 * 9:21:09 AM
 */
public class DropExpMoneyVO {
	/**npc掉落经验*/	
	private int dropExp;
	/**npc掉落的钱数*/
	private int dropMoney;
	/** npc给pet掉落的经验 */
	private int dropPetExp;
	
	public void add(DropExpMoneyVO dropExpMoney)
	{
		if( dropExpMoney!=null )
		{
			dropExp += dropExpMoney.getDropExp();
			dropMoney += dropExpMoney.getDropMoney();
			dropPetExp += dropExpMoney.getDropPetExp();
		}
	}
	
	public int getDropPetExp()
	{
		return dropPetExp;
	}
	public void setDropPetExp(int dropPetExp)
	{
		this.dropPetExp = dropPetExp;
	}
	public int getDropExp() {
		return dropExp;
	}
	public void setDropExp(int dropExp) {
		this.dropExp = dropExp;
	}
	public int getDropMoney() {
		return dropMoney;
	}
	public void setDropMoney(int dropMoney) {
		this.dropMoney = dropMoney;
	}
	
	
	

}
