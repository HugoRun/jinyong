package com.ls.model.organize.faction.game;

/**
 * @author ls
 * �����ɣ����ã����������ò���
 */
public class FUpgradeMaterial
{
	//��������
	public static int F_UPGRADE=1;//������������
	public static int C_UPGRADE=2;//������������
	
	private int grade;//�ȼ�
	
	//��Ҫ���ɲ���
	private String fMStr;//��Ҫ���ɲ����ַ���,��ʽ�磺����id-��������������id-����������
	private int prestige;//����
	
	//��Ҫ���˲���
	private int mId;//��Ҫ���˲��ϵ���id
	private int mNum;//��Ҫ���˲�������
	private String mDes;//��Ҫ���˲�������
	private int money;//��Ҫ���˵�Ǯ
	
	private String effectDes;//Ч������
	
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
