package com.ls.ben.vo.goods.equip;

import com.ls.pub.util.MathUtil;
import com.ls.web.service.log.DataErrorLog;

/**
 * 功能:装备生成时，根据规则生成附加属性
 * @author 刘帅
 * Oct 13, 2008  3:20:05 PM
 */
public class EquipAppendAttributeVO
{
	/**id*/
	private int id;
	private int quality;//装备品质
	/**等级下限*/
	private int    levelLower;
	/**等级上限*/
	private int levelUpper;
	/**附加属性类型:血=1，蓝=2，金攻=3，木攻=4，水攻=5，火攻=6，土攻=7，金防=8，木防=9，水防=10，火防=11，土防=12*/
	private int attributeType;  
	 /**属性值范围，形式如:10,20-21,30-31,40*/
	private String    valueArea;
	 /**属性值的概率控制，，形式如:50-35-15*/
	private String    valueProbability;
	
	/**
	 * 得到属性值
	 * @return
	 */
	public int getValue()
	{
		int value = 0;
		try{
			String[] values = valueArea.split("-");
			String[] probabilitys = valueProbability.split("-");
			String value_area = MathUtil.getRandomValueByProbalilitys(values, probabilitys);
			if( value_area==null )
			{
				value = 0;
			}
			else
			{
				value = MathUtil.getValueByStr(value_area);
			}
		}catch(Exception e)
		{
			DataErrorLog.debugData("equip_append_attribute表,valueArea字段数据错误：id="+id+"valueArea="+valueArea);
		}
		
		return value;
	}
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getLevelLower()
	{
		return levelLower;
	}
	public void setLevelLower(int levelLower)
	{
		this.levelLower = levelLower;
	}
	public int getLevelUpper()
	{
		return levelUpper;
	}
	public void setLevelUpper(int levelUpper)
	{
		this.levelUpper = levelUpper;
	}
	public int getAttributeType()
	{
		return attributeType;
	}
	public void setAttributeType(int attributeType)
	{
		this.attributeType = attributeType;
	}
	public String getValueArea()
	{
		return valueArea;
	}
	public void setValueArea(String valueArea)
	{
		this.valueArea = valueArea;
	}
	public String getValueProbability()
	{
		return valueProbability;
	}
	public void setValueProbability(String valueProbability)
	{
		this.valueProbability = valueProbability;
	}

	public int getQuality()
	{
		return quality;
	}

	public void setQuality(int quality)
	{
		this.quality = quality;
	} 
}
