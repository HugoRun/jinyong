package com.ls.model.equip;

import com.ls.pub.constant.Equip;


/**
 * 功能：身上装备的位置
 * @author ls
 * Aug 18, 2009
 * 10:54:11 AM
 */
public class PositionOnEquip
{
	public static final int EQUIP_NUM_ON_BODY = 8;//身上可以穿的装备的数量
	
	public final static int WEAPON = 1;
	public final static int HAT = 2;
	public final static int CLOTHING = 3;
	public final static int TROUSERS = 4;//裤子
	public final static int SHOES = 5;
	public final static int JEWELRY_1 = 6;//饰品1
	public final static int JEWELRY_2 = 7;//饰品2
	public final static int JEWELRY_3 = 8;//饰品3
	
	
	/**
	 * 根据部位得到装备类型
	 * @return
	 */
	public static int getEquipByPosotion( int position )
	{
		if( position>=6 )
		{
			return Equip.JEWELRY;
		}
		return position;
	}
}
