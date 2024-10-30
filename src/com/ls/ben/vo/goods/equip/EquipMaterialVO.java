package com.ls.ben.vo.goods.equip;

/**
 * @author ls
 * 装备升级材料
 */
public class EquipMaterialVO
{
	private int quality;//品质
	private int grade;//等级
	private int material1;//需要材料1的数量
	private int material2;//需要材料2的数量
	private int needMoney;//需要的钱
	private int rate;//成功率
	public int getQuality()
	{
		return quality;
	}
	public int getGrade()
	{
		return grade;
	}
	public int getMaterial1()
	{
		return material1;
	}
	public int getMaterial2()
	{
		return material2;
	}
	public int getNeedMoney()
	{
		return needMoney;
	}
	public int getRate()
	{
		return rate;
	}
	public void setQuality(int quality)
	{
		this.quality = quality;
	}
	public void setGrade(int grade)
	{
		this.grade = grade;
	}
	public void setMaterial1(int material1)
	{
		this.material1 = material1;
	}
	public void setMaterial2(int material2)
	{
		this.material2 = material2;
	}
	public void setNeedMoney(int needMoney)
	{
		this.needMoney = needMoney;
	}
	public void setRate(int rate)
	{
		this.rate = rate;
	}
}
