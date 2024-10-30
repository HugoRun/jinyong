package com.pm.vo.passiveskill;

import com.ls.model.property.PassSkillInterface;

/**
 * 存储被动技能的属性
 * 
 * @author zjj
 * 
 */
public class PassSkillVO  implements PassSkillInterface
{
	/** 附加mp的百分比 */
	private double skMpMultiple;

	/** 附加hp的百分比 */
	private double skHpMultiple;

	/** 附加暴击率的百分比 */
	private double skBjMultiple;

	/** 附加攻击的百分比 */
	private double skGjMultiple;

	/** 附加防御的百分比 */
	private double skFyMultiple;

	/** 附加攻击的数值 */
	private int skGjAdd;

	/** 附加防御的数值 */
	private int skFyAdd;

	/** 附加hp的数值 */
	private int skHpAdd;

	/** 附加mp的数值 */
	private int skMpAdd;

	/** 附加金的百分比 */
	private double skJMultiple;

	/** 附加木的百分比 */
	private double skMMultiple;

	/** 附加水的百分比 */
	private double skSMultiple;

	/** 附加火的百分比 */
	private double skHMultiple;

	/** 附加土的百分比 */
	private double skTMultiple;

	public double getSkMpMultiple()
	{
		return skMpMultiple;
	}

	public void setSkMpMultiple(double skMpMultiple)
	{
		this.skMpMultiple = skMpMultiple;
	}

	public double getSkHpMultiple()
	{
		return skHpMultiple;
	}

	public void setSkHpMultiple(double skHpMultiple)
	{
		this.skHpMultiple = skHpMultiple;
	}

	public double getSkBjMultiple()
	{
		return skBjMultiple;
	}

	public void setSkBjMultiple(double skBjMultiple)
	{
		this.skBjMultiple = skBjMultiple;
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

	/**
	 * 根据 已经加成后的值来判断其应该加的数值
	 * 
	 * @param property
	 * @param multiry
	 * @param propertyAdd
	 * @return
	 */
	public double getProperty(double property, double multiry,
			double propertyAdd)
	{
		property = property - propertyAdd;

		double add_value = 0;

		add_value = (property / (1 + multiry) * multiry);

		return add_value;
	}

	public double getSkJMultiple()
	{
		return skJMultiple;
	}

	public void setSkJMultiple(double skJMultiple)
	{
		this.skJMultiple = skJMultiple;
	}

	public double getSkMMultiple()
	{
		return skMMultiple;
	}

	public void setSkMMultiple(double skMMultiple)
	{
		this.skMMultiple = skMMultiple;
	}

	public double getSkSMultiple()
	{
		return skSMultiple;
	}

	public void setSkSMultiple(double skSMultiple)
	{
		this.skSMultiple = skSMultiple;
	}

	public double getSkHMultiple()
	{
		return skHMultiple;
	}

	public void setSkHMultiple(double skHMultiple)
	{
		this.skHMultiple = skHMultiple;
	}

	public double getSkTMultiple()
	{
		return skTMultiple;
	}

	public void setSkTMultiple(double skTMultiple)
	{
		this.skTMultiple = skTMultiple;
	}

}
