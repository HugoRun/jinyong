package com.ben.guaji.vo;

import java.util.HashMap;
import java.util.Map;

import com.ls.ben.vo.goods.prop.PropVO;

public class GuaJiConstant
{
	// 1221修改挂机调整
	// 普通离线挂机
	public final static int COMMON = 0;
	public final static int COMMON_YUANBAO = 1;
	// 双倍经验,幸运,掉率离线挂机
	public final static int DOUBLE_G = 1;
	public final static int DOUBLE_G_YUANBAO = 2;
	// 五倍经验,幸运,掉率离线挂机
	public final static int FIVE_G = 2;
	public final static int FIVE_G_YUANBAO = 4;
	// 八倍经验,幸运,掉率离线挂机
	public final static int EIGHT_G = 3;
	public final static int EIGHT_G_YUANBAO = 6;
	// 十倍经验,幸运,掉率离线挂机
	public final static int TEN_G = 4;
	public final static int TEN_G_YUANBAO = 10;
	// 双倍银两离线挂机
	// public final static int DOUBLE_MONEY = 5;
	// public final static int DOUBLE_MONEY_YUANBAO = 6;
	// 超级无敌离线挂机
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

	// 最多挂机时间(分钟)
	public final static int MAX_GUAJI_TIME = 360;

	// 挂机类型储存
	public static Map<Integer, GuajiVo> GUAJIVO = new HashMap<Integer, GuajiVo>();

	// 拾取全部装备
	public final static int ALL = 1;
	// 仅拾取“优”以上
	public final static int YOU = 2;
	// 仅拾取“精”以上
	public final static int JING = 3;
	// 仅拾取“极”以上
	public final static int JI = 4;
	// 概率比率
	public final static int BILV = 1000000;
	// 极品比率
	public final static int JI_BILV = 6;
	// 精品比率
	public final static int JING_BILV = 200000;
	// 不能挂机地点
	public final static String CAN_NOT_GUAJI = "22,364,332,391,184,493,608,89,981,1025";

	/*// 装备集合(id)
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
	// 免费挂机开关,0为开，1为关
	public static final String GUAJI_CONTROL = "guaji_control";
	// #免费挂机是星期几1,2,3,4,5,6,7
	public static final String GUAJI_WEEK = "guaji_week";
	// #免费挂机开始时间,单位为小时
	public static final String GUAJI_BEGIN_TIME = "guaji_begin_time";
	// #免费挂机结束时间，单位为小时
	public static final String GUAJI_END_TIME = "guaji_end_time";

}
