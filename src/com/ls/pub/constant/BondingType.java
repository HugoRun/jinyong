package com.ls.pub.constant;
/**
 * 物品的绑定类别
 * */
public class BondingType
{ 
	/**绑定	0是不绑定，1是拾取绑定，2是装备绑定,3交易绑定*/
	public static final int NOBOND = 0;//没有绑定
	public static final int PROTECTEDBOND=1;//绑定收保护的不可丢弃
    public static final int PICKBOUND=1;//拾取绑定
    public static final int ARMBOND=2;//装备绑定
    public static final int EXCHANGEBOND=3;//交易绑定
}
