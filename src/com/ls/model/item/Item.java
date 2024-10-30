package com.ls.model.item;

import com.ls.model.user.RoleEntity;

/**
 * @author ls
 * 游戏里可以流通的的物品（道具，装备，金钱，声望，经验，帮派贡献度）
 */
abstract public class Item
{
	//**********************物品前缀字符串
	public static String PROP_PREFIX_STR = "d";//道具前缀字符串
	public static String UEQUP_PREFIX_STR = "uzb";//玩家装备前缀字符串
	
	//物品分割符
	public static String FIRST_SPLIT = ",";//物品一级分割符
	public static String SECOND_SPLIT = "-";//物品二级分割符
	
	protected String name;//名字
	protected int num;//数量
	
	abstract public void init(String itemStr) throws Exception;//初始化
	abstract public String toString();//得到字符串物品的表达式
	abstract public int getNeedWrapSpace();//需要的包裹空间
	abstract public void gain(RoleEntity roleInfo,int gain_type);//玩家获得该物品
	
	protected void init(String name,int num)
	{
		this.name = name;
		this.num = num;
	}
	//物品的数量是否足够
	public boolean isEnough( int needNum)
	{
		if( num>=needNum)
		{
			return true;
		}
		return false;
	}
	//更改物品数量
	public void update( int updateNum )
	{
		if( updateNum<0 &&  num<-updateNum )
		{
			return;
		}
		num+=updateNum;
	}
	
	//物品描述
	public String getDes()
	{
		return name+"×"+num;
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
