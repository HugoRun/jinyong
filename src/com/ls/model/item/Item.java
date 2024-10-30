package com.ls.model.item;

import com.ls.model.user.RoleEntity;

/**
 * @author ls
 * ��Ϸ�������ͨ�ĵ���Ʒ�����ߣ�װ������Ǯ�����������飬���ɹ��׶ȣ�
 */
abstract public class Item
{
	//**********************��Ʒǰ׺�ַ���
	public static String PROP_PREFIX_STR = "d";//����ǰ׺�ַ���
	public static String UEQUP_PREFIX_STR = "uzb";//���װ��ǰ׺�ַ���
	
	//��Ʒ�ָ��
	public static String FIRST_SPLIT = ",";//��Ʒһ���ָ��
	public static String SECOND_SPLIT = "-";//��Ʒ�����ָ��
	
	protected String name;//����
	protected int num;//����
	
	abstract public void init(String itemStr) throws Exception;//��ʼ��
	abstract public String toString();//�õ��ַ�����Ʒ�ı��ʽ
	abstract public int getNeedWrapSpace();//��Ҫ�İ����ռ�
	abstract public void gain(RoleEntity roleInfo,int gain_type);//��һ�ø���Ʒ
	
	protected void init(String name,int num)
	{
		this.name = name;
		this.num = num;
	}
	//��Ʒ�������Ƿ��㹻
	public boolean isEnough( int needNum)
	{
		if( num>=needNum)
		{
			return true;
		}
		return false;
	}
	//������Ʒ����
	public void update( int updateNum )
	{
		if( updateNum<0 &&  num<-updateNum )
		{
			return;
		}
		num+=updateNum;
	}
	
	//��Ʒ����
	public String getDes()
	{
		return name+"��"+num;
	}
	
	public boolean equals(Object item)
	{
		return false;
	}
	public int getNum()
	{
		return num;
	}
}
