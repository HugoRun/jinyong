package com.ls.ben.vo.info.attack;

/**
 * ����:����Ľ�Ǯ�;���
 * @author ��˧
 * 9:21:09 AM
 */
public class DropExpMoneyVO {
	/**npc���侭��*/	
	private int dropExp;
	/**npc�����Ǯ��*/
	private int dropMoney;
	/** npc��pet����ľ��� */
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
