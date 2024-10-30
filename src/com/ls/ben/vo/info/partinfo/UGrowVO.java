/**
 * 
 */
package com.ls.ben.vo.info.partinfo;

/**
 * 功能:对应u_grow_info表
 * 
 * @author 刘帅
 * 
 * 6:20:50 PM
 */
public class UGrowVO
{
	/** id */
	private int gPk;
	/** 等级 */
	private int gGrade;
	/** 种族 */
	private int gRace;
	/** 本级经验 */
	private String gExp;
	/** 下级经验 */
	private String gNextExp;
	/** 血量值 */
	private int gHP;
	/** 法力值 */
	private int gMP;
	/** 攻击 */
	private int gGj;

	/** 防御 */
	private int gFy;

	/** 是否可自然升级 1表示可以自然升级，0表示不可以自然升级 */
	private int gIsAutogrow;

	/** ***人物暴击率**** */

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
