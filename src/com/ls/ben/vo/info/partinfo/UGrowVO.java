/**
 * 
 */
package com.ls.ben.vo.info.partinfo;

/**
 * ����:��Ӧu_grow_info��
 * 
 * @author ��˧
 * 
 * 6:20:50 PM
 */
public class UGrowVO
{
	/** id */
	private int gPk;
	/** �ȼ� */
	private int gGrade;
	/** ���� */
	private int gRace;
	/** �������� */
	private String gExp;
	/** �¼����� */
	private String gNextExp;
	/** Ѫ��ֵ */
	private int gHP;
	/** ����ֵ */
	private int gMP;
	/** ���� */
	private int gGj;

	/** ���� */
	private int gFy;

	/** �Ƿ����Ȼ���� 1��ʾ������Ȼ������0��ʾ��������Ȼ���� */
	private int gIsAutogrow;

	/** ***���ﱩ����**** */

	private double gDropMultiple;

	public double getGDropMultiple()
	{
		return gDropMultiple;
	}

	public void setGDropMultiple(double dropMultiple)
	{
		gDropMultiple = dropMultiple;
	}

	public int getGPk()
	{
		return gPk;
	}

	public void setGPk(int pk)
	{
		gPk = pk;
	}


	public String getGExp()
	{
		return gExp;
	}

	public void setGExp(String exp)
	{
		gExp = exp;
	}

	public String getGNextExp()
	{
		return gNextExp;
	}

	public void setGNextExp(String nextExp)
	{
		gNextExp = nextExp;
	}

	public int getGHP()
	{
		return gHP;
	}

	public void setGHP(int ghp)
	{
		gHP = ghp;
	}

	public int getGMP()
	{
		return gMP;
	}

	public void setGMP(int gmp)
	{
		gMP = gmp;
	}


	public int getGGj()
	{
		return gGj;
	}

	public void setGGj(int gj)
	{
		gGj = gj;
	}

	public int getGFy()
	{
		return gFy;
	}

	public void setGFy(int fy)
	{
		gFy = fy;
	}

	public int getGIsAutogrow()
	{
		return gIsAutogrow;
	}

	public void setGIsAutogrow(int isAutogrow)
	{
		gIsAutogrow = isAutogrow;
	}

	public int getGGrade()
	{
		return gGrade;
	}

	public void setGGrade(int grade)
	{
		gGrade = grade;
	}

	public int getGRace()
	{
		return gRace;
	}

	public void setGRace(int race)
	{
		gRace = race;
	}

}
