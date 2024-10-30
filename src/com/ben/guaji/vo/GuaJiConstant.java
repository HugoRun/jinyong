package com.ben.guaji.vo;

import java.util.HashMap;
import java.util.Map;

import com.ls.ben.vo.goods.prop.PropVO;

public class GuaJiConstant
{
	// 1221�޸Ĺһ�����
	// ��ͨ���߹һ�
	public final static int COMMON = 0;
	public final static int COMMON_YUANBAO = 1;
	// ˫������,����,�������߹һ�
	public final static int DOUBLE_G = 1;
	public final static int DOUBLE_G_YUANBAO = 2;
	// �屶����,����,�������߹һ�
	public final static int FIVE_G = 2;
	public final static int FIVE_G_YUANBAO = 4;
	// �˱�����,����,�������߹һ�
	public final static int EIGHT_G = 3;
	public final static int EIGHT_G_YUANBAO = 6;
	// ʮ������,����,�������߹һ�
	public final static int TEN_G = 4;
	public final static int TEN_G_YUANBAO = 10;
	// ˫���������߹һ�
	// public final static int DOUBLE_MONEY = 5;
	// public final static int DOUBLE_MONEY_YUANBAO = 6;
	// �����޵����߹һ�
	// public final static int FULL = 6;
	// public final static int FULL_YUANBAO = 25;
	public static Map<Integer, Integer> DUIYING = new HashMap<Integer, Integer>();

	static
	{
		DUIYING.put(COMMON, COMMON_YUANBAO);
		DUIYING.put(DOUBLE_G, DOUBLE_G_YUANBAO);
		DUIYING.put(FIVE_G, FIVE_G_YUANBAO);
		DUIYING.put(EIGHT_G, EIGHT_G_YUANBAO);
		DUIYING.put(TEN_G, TEN_G_YUANBAO);
	}

	// ���һ�ʱ��(����)
	public final static int MAX_GUAJI_TIME = 360;

	// �һ����ʹ���
	public static Map<Integer, GuajiVo> GUAJIVO = new HashMap<Integer, GuajiVo>();

	// ʰȡȫ��װ��
	public final static int ALL = 1;
	// ��ʰȡ���š�����
	public final static int YOU = 2;
	// ��ʰȡ����������
	public final static int JING = 3;
	// ��ʰȡ����������
	public final static int JI = 4;
	// ���ʱ���
	public final static int BILV = 1000000;
	// ��Ʒ����
	public final static int JI_BILV = 6;
	// ��Ʒ����
	public final static int JING_BILV = 200000;
	// ���ܹһ��ص�
	public final static String CAN_NOT_GUAJI = "22,364,332,391,184,493,608,89,981,1025";

	/*// װ������(id)
	public static Map<Integer, AccouteVO> EQUIP = new HashMap<Integer, AccouteVO>(
			1500);
	public static Map<Integer, ArmVO> ARM = new HashMap<Integer, ArmVO>(1500);
	public static Map<Integer, JewelryVO> JEWELRY = new HashMap<Integer, JewelryVO>(
			1500);
	public static Map<String, AccouteVO> EQUIP_NAME = new HashMap<String, AccouteVO>(
			1500);
	public static Map<String, ArmVO> ARM_NAME = new HashMap<String, ArmVO>(1500);
	public static Map<String, JewelryVO> JEWELRY_NAME = new HashMap<String, JewelryVO>(
			1500);*/
	public static Map<String, PropVO> PROP_NAME = new HashMap<String, PropVO>(
			1500);
	// ��ѹһ�����,0Ϊ����1Ϊ��
	public static final String GUAJI_CONTROL = "guaji_control";
	// #��ѹһ������ڼ�1,2,3,4,5,6,7
	public static final String GUAJI_WEEK = "guaji_week";
	// #��ѹһ���ʼʱ��,��λΪСʱ
	public static final String GUAJI_BEGIN_TIME = "guaji_begin_time";
	// #��ѹһ�����ʱ�䣬��λΪСʱ
	public static final String GUAJI_END_TIME = "guaji_end_time";

}
