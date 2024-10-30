package com.ben.vo.honour;

/**
 * 称号
 */
public class TitleVO
{
	/** 称号类型 */
	public static final int GRADE = 1;// 等级称号（转职）
	public static final int MARRIAGE = 2;// 结婚
	public static final int SWORN_BROTHER = 3;// 结义
	public static final int VIP = 4;// VIP称号类型
	
	/** 主键id */
	private int id;
	/** 称号名称 */
	private String name;
	/** 称号描述 */
	private String des;
	/** 称号类型 */
	private int type;
	/** 称号类型名称 */
	private String typeName;
	
	/** 附加属性字符串 */
	private String attriStr;
	/** 使用时间限制：0为无限制，单位小时*/
	private int useTime;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getDes()
	{
		return des;
	}
	public void setDes(String des)
	{
		this.des = des;
	}
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public String getTypeName()
	{
		return typeName;
	}
	public void setTypeName(String typeName)
	{
		this.typeName = typeName;
	}
	public String getAttriStr()
	{
		return attriStr;
	}
	public void setAttriStr(String attriStr)
	{
		this.attriStr = attriStr;
	}
	public int getUseTime()
	{
		return useTime;
	}
	public void setUseTime(int useTime)
	{
		this.useTime = useTime;
	}
}
