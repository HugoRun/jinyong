package com.ls.model.equip;

import com.ls.pub.constant.Equip;


/**
 * ���ܣ�����װ����λ��
 * @author ls
 * Aug 18, 2009
 * 10:54:11 AM
 */
public class PositionOnEquip
{
	public static final int EQUIP_NUM_ON_BODY = 8;//���Ͽ��Դ���װ��������
	
	public final static int WEAPON = 1;
	public final static int HAT = 2;
	public final static int CLOTHING = 3;
	public final static int TROUSERS = 4;//����
	public final static int SHOES = 5;
	public final static int JEWELRY_1 = 6;//��Ʒ1
	public final static int JEWELRY_2 = 7;//��Ʒ2
	public final static int JEWELRY_3 = 8;//��Ʒ3
	
	
	/**
	 * ���ݲ�λ�õ�װ������
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
