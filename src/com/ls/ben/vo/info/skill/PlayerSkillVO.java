package com.ls.ben.vo.info.skill;

import java.util.Date;

/**
 * ����:��u_skill_info��
 * 
 * @author ��˧
 * 
 * 4:39:55 PM
 */
public class PlayerSkillVO extends SkillVO
{
	
	/** id */
	private int sPk;

	/** ��ɫid */
	private int pPk;

	/** ����id */
	private int skId;

	/** ����ʹ�������� */
	private int skSleight;

	/** ��������һ�ε�ʹ�õ�ʱ�� */
	private Date skUsetime;

	/** ����ʱ�� */
	private Date createTime;

	/** ****�������ӳ�ϵ��****** */

	private double skGjMultiple;
	/** ****�������ӳ�ϵ��****** */

	private double skFyMultiple;
	/** ****HP�ӳ�ϵ��****** */

	private double skHpMultiple;
	/** ****MP�ӳ�ϵ��****** */

	private double skMpMultiple;
	/** ****�����ʼӳ�ϵ��****** */

	private double skBjMultiple;
	/** ****������������ֵ****** */

	private int skGjAdd;
	/** ****������������ֵ****** */

	private int skFyAdd;
	/** ****HP�ӳɸ�����ֵ****** */

	private int skHpAdd;
	/** ****MP�ӳɸ�����ֵ****** */

	private int skMpAdd;
	/** *****������******* */
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
