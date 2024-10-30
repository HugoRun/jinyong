package com.pm.vo.passiveskill;

import com.ls.model.property.PassSkillInterface;

/**
 * �洢�������ܵ�����
 * 
 * @author zjj
 * 
 */
public class PassSkillVO  implements PassSkillInterface
{
	/** ����mp�İٷֱ� */
	private double skMpMultiple;

	/** ����hp�İٷֱ� */
	private double skHpMultiple;

	/** ���ӱ����ʵİٷֱ� */
	private double skBjMultiple;

	/** ���ӹ����İٷֱ� */
	private double skGjMultiple;

	/** ���ӷ����İٷֱ� */
	private double skFyMultiple;

	/** ���ӹ�������ֵ */
	private int skGjAdd;

	/** ���ӷ�������ֵ */
	private int skFyAdd;

	/** ����hp����ֵ */
	private int skHpAdd;

	/** ����mp����ֵ */
	private int skMpAdd;

	/** ���ӽ�İٷֱ� */
	private double skJMultiple;

	/** ����ľ�İٷֱ� */
	private double skMMultiple;

	/** ����ˮ�İٷֱ� */
	private double skSMultiple;

	/** ���ӻ�İٷֱ� */
	private double skHMultiple;

	/** �������İٷֱ� */
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
	 * ���� �Ѿ��ӳɺ��ֵ���ж���Ӧ�üӵ���ֵ
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
