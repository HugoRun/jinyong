package com.ls.ben.vo.info.skill;

import java.util.Date;

/**
 * 功能:表u_skill_info的
 * 
 * @author 刘帅
 * 
 * 4:39:55 PM
 */
public class PlayerSkillVO extends SkillVO
{
	
	/** id */
	private int sPk;

	/** 角色id */
	private int pPk;

	/** 技能id */
	private int skId;

	/** 技能使用熟练度 */
	private int skSleight;

	/** 本技能上一次的使用的时间 */
	private Date skUsetime;

	/** 创建时间 */
	private Date createTime;

	/** ****攻击力加成系数****** */

	private double skGjMultiple;
	/** ****防御力加成系数****** */

	private double skFyMultiple;
	/** ****HP加成系数****** */

	private double skHpMultiple;
	/** ****MP加成系数****** */

	private double skMpMultiple;
	/** ****暴击率加成系数****** */

	private double skBjMultiple;
	/** ****攻击力附加数值****** */

	private int skGjAdd;
	/** ****防御力附加数值****** */

	private int skFyAdd;
	/** ****HP加成附加数值****** */

	private int skHpAdd;
	/** ****MP加成附加数值****** */

	private int skMpAdd;
	/** *****技能组******* */
	private int skGroup;
	
	public int getSkGroup()
	{
		return skGroup;
	}

	public void setSkGroup(int skGroup)
	{
		this.skGroup = skGroup;
	}

	public int getSPk()
	{
		return sPk;
	}

	public void setSPk(int pk)
	{
		sPk = pk;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public int getSkId()
	{
		return skId;
	}

	public void setSkId(int skId)
	{
		this.skId = skId;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getSkUsetime()
	{
		return skUsetime;
	}

	public void setSkUsetime(Date skUsetime)
	{
		this.skUsetime = skUsetime;
	}

	public int getSkSleight()
	{
		return skSleight;
	}

	public void setSkSleight(int skSleight)
	{
		this.skSleight = skSleight;
	}

	public double getSkGjMultiple()
	{
		return skGjMultiple;
	}

	public void setSkGjMultiple(double skGjMultiple)
	{
		this.skGjMultiple = skGjMultiple;
	}

	public double getSkFyMultiple()
	{
		return skFyMultiple;
	}

	public void setSkFyMultiple(double skFyMultiple)
	{
		this.skFyMultiple = skFyMultiple;
	}

	public double getSkHpMultiple()
	{
		return skHpMultiple;
	}

	public void setSkHpMultiple(double skHpMultiple)
	{
		this.skHpMultiple = skHpMultiple;
	}

	public double getSkMpMultiple()
	{
		return skMpMultiple;
	}

	public void setSkMpMultiple(double skMpMultiple)
	{
		this.skMpMultiple = skMpMultiple;
	}

	public double getSkBjMultiple()
	{
		return skBjMultiple;
	}

	public void setSkBjMultiple(double skBjMultiple)
	{
		this.skBjMultiple = skBjMultiple;
	}

	public int getSkGjAdd()
	{
		return skGjAdd;
	}

	public void setSkGjAdd(int skGjAdd)
	{
		this.skGjAdd = skGjAdd;
	}

	public int getSkFyAdd()
	{
		return skFyAdd;
	}

	public void setSkFyAdd(int skFyAdd)
	{
		this.skFyAdd = skFyAdd;
	}

	public int getSkHpAdd()
	{
		return skHpAdd;
	}

	public void setSkHpAdd(int skHpAdd)
	{
		this.skHpAdd = skHpAdd;
	}

	public int getSkMpAdd()
	{
		return skMpAdd;
	}

	public void setSkMpAdd(int skMpAdd)
	{
		this.skMpAdd = skMpAdd;
	}
}
