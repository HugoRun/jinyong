package com.ls.model.organize.faction.game;

/**
 * @author ls
 * （帮派，祠堂）帮派升级用材料
 */
public class FUpgradeMaterial
{
	//材料类型
	public static int F_UPGRADE=1;//帮派升级材料
	public static int C_UPGRADE=2;//祠堂升级材料
	
	private int grade;//等级
	
	//需要帮派材料
	private String fMStr;//需要帮派材料字符串,形式如：道具id-道具数量，道具id-道具数量，
	private int prestige;//声望
	
	//需要个人材料
	private int mId;//需要个人材料道具id
	private int mNum;//需要个人材料数量
	private String mDes;//需要个人材料描述
	private int money;//需要个人的钱
	
	private String effectDes;//效果描述
	
	public int getGrade()
	{
		return grade;
	}
	public void setGrade(int grade)
	{
		this.grade = grade;
	}
	public String getFMStr()
	{
		return fMStr;
	}
	public void setFMStr(String str)
	{
		fMStr = str;
	}
	public int getMId()
	{
		return mId;
	}
	public void setMId(int id)
	{
		mId = id;
	}
	public int getMNum()
	{
		return mNum;
	}
	public void setMNum(int num)
	{
		mNum = num;
	}
	public String getMDes()
	{
		return mDes;
	}
	public void setMDes(String des)
	{
		mDes = des;
	}
	public String getEffectDes()
	{
		return effectDes;
	}
	public void setEffectDes(String effectDes)
	{
		this.effectDes = effectDes;
	}
	public int getPrestige()
	{
		return prestige;
	}
	public void setPrestige(int prestige)
	{
		this.prestige = prestige;
	}
	public int getMoney()
	{
		return money;
	}
	public void setMoney(int money)
	{
		this.money = money;
	}
	
}
