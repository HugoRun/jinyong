package com.ls.model.equip;

/**
 * @author ls
 * 当装备升到最高级时，给装备附加的属性
 */
public class EquipAppendAttri
{
	private int id;
	private String attriStr;
	private String attriDes;
	public int getId()
	{
		return id;
	}
	public String getAttriStr()
	{
		return attriStr;
	}
	public String getAttriDes()
	{
		return attriDes;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public void setAttriStr(String attriStr)
	{
		this.attriStr = attriStr;
	}
	public void setAttriDes(String attriDes)
	{
		this.attriDes = attriDes;
	}
}
