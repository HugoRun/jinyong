package com.ben.vo.honour;

/**
 * �ƺ�
 */
public class TitleVO
{
	/** �ƺ����� */
	public static final int GRADE = 1;// �ȼ��ƺţ�תְ��
	public static final int MARRIAGE = 2;// ���
	public static final int SWORN_BROTHER = 3;// ����
	public static final int VIP = 4;// VIP�ƺ�����
	
	/** ����id */
	private int id;
	/** �ƺ����� */
	private String name;
	/** �ƺ����� */
	private String des;
	/** �ƺ����� */
	private int type;
	/** �ƺ��������� */
	private String typeName;
	
	/** ���������ַ��� */
	private String attriStr;
	/** ʹ��ʱ�����ƣ�0Ϊ�����ƣ���λСʱ*/
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
