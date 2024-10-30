package com.ls.ben.vo.goods.equip;

import com.ls.pub.util.MathUtil;
import com.ls.web.service.log.DataErrorLog;

/**
 * ����:װ������ʱ�����ݹ������ɸ�������
 * @author ��˧
 * Oct 13, 2008  3:20:05 PM
 */
public class EquipAppendAttributeVO
{
	/**id*/
	private int id;
	private int quality;//װ��Ʒ��
	/**�ȼ�����*/
	private int    levelLower;
	/**�ȼ�����*/
	private int levelUpper;
	/**������������:Ѫ=1����=2����=3��ľ��=4��ˮ��=5����=6������=7�����=8��ľ��=9��ˮ��=10�����=11������=12*/
	private int attributeType;  
	 /**����ֵ��Χ����ʽ��:10,20-21,30-31,40*/
	private String    valueArea;
	 /**����ֵ�ĸ��ʿ��ƣ�����ʽ��:50-35-15*/
	private String    valueProbability;
	
	/**
	 * �õ�����ֵ
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
			DataErrorLog.debugData("equip_append_attribute��,valueArea�ֶ����ݴ���id="+id+"valueArea="+valueArea);
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
