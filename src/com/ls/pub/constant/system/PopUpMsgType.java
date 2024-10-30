package com.ls.pub.constant.system;

/**
 * @author ls 功能:弹出消息类型 Mar 9, 2009
 */
public class PopUpMsgType
{
	//****************消息类型
	public final static int INSTANCE = 1;
	// 组队消息
	public final static int MESSAGE_GROUP = 2;
	// 交易消息
	public final static int MESSAGE_SWAP = 3;
	// PK消息
	public final static int MESSAGE_PK = 4;

	// 攻城战场弹出消息
	public final static int TONGSIEGE = 5;

	// 结义弹出消息
	public final static int JIEYI = 6;

	// 结婚弹出消息
	public final static int JIEHUN = 7;

	// 离婚弹出消息
	public final static int LIHUN = 8;

	// 同意结婚弹出消息
	public final static int MERRY_AGREE = 9;

	// 结婚物品不满足
	public final static int CAN_NOT_MERRY = 10;

	// 出师消息
	public final static int CHUSHI = 11;
	// 系统特殊消息
	public final static int SYS_TESHU_MSG = 12;
	//弹出式系统消息
	public final static int XITONG = 13;
	//擂台弹出消息
	public final static int LEITAI = 14;
	//千面郎君
	public final static int LANGJUN = 15;
	//秘境地图
	public final static int MIJING_MAP = 16;
	//普通提示消息
	public final static int COMMON = 17;
	
	//插旗子
	public final static int TIAOZHAN = 18;
	
	/** ************消息优先级*************** */
	// 系统特殊消息
	public final static int SYS_TESHU_MSG_FIRST = 1;
	// PK消息优先级
	public final static int MESSAGE_PK_FIRST = 0;//pk的弹出式消息最高
	// 交易消息优先级
	public final static int MESSAGE_SWAP_FIRST = 3;
	// 组队消息优先级
	public final static int MESSAGE_GROUP_FIRST = 4;

	// 结义消息优先级
	public final static int JIEYI_FIRST = 5;

	// 结婚消息优先级
	public final static int JIEHUN_FIRST = 6;

	// 离婚消息优先级
	public final static int LIHUN_FIRST = 7;

	// 同意结婚消息优先级
	public final static int MERRY_AGREE_FIRST = 8;

	// 结婚物品不满足优先级
	public final static int CAN_NOT_MERRY_FIRST = 9;

	// 出师消息优先级
	public final static int CHUSHI_FIRST = 10;
	
	//擂台消息优先级
	public final static int LEITAI_FIRST = 11;
	
	//躲过千面郎君追杀优先级
	public final static int XIAN_LANGJUN_FIRST = 0;
	
	//千面郎君优先级
	public final static int LANGJUN_FIRST = 13;
	
	//秘境地图优先级
	public final static int MIJING_MAP_FIRST = 14;
	
	//普通消息优先级
	public final static int COMMON_FIRST = 15;
	
	//插旗子消息优先级
	public final static int TIAOZHAN_FIRST = 16;

	/** 
	 *   弹出式系统消息优先级,此优化级应该极小, 只有在平常状态下才能进行,并且只对自己负责.
	 *	其他弹出式消息如果不是特殊情况,请大于此优先级
	 */
	public final static int XITONG_FIRST = 30;
	
	public final static int NEWPLAYERGUIDEINFOMSG = 31;
	
	public final static int NOTIFY_KILL_OTHER = 32;//通知你把对方打死
	
	public final static int NOTIFY_SELF_DEAD = 33;//通知自己被打死
	
	public final static int NOTIFY_OTHER_DEAD = 34;//通知别人被打死
	

	/** ************系统特殊弹出式消息类型*************** */
	public final static int GO_UP_GRADE = 1;//##等级情况
	public final static int WRAP_LOWER_LIMIT = 2;//##包裹情况
	public final static int PET_FATIGUE = 3;//##宠物情况
	public final static int ATTAIN_PROP_TYPE = 4;//获得道具情况 
	public final static int TASK_INSTANCE = 5;//##副本任务情况
	public final static int TASK_30TONG = 6;//30级帮派结束任务情况
	public final static int TASK_30PK = 7;//30级PK结束任务情况
	public final static int MENU_INSTANCE = 8;//副本菜单
	public final static int MENU_SIEGE = 9;//##攻城菜单情况
	public final static int PROP_GRADE = 10;//特殊道具在特殊等级的情况
	public final static int VIP_ENDTIME = 11;//VIP会员到期以后
	public final static int PK_SWITCH = 12;//PK开关 当打开的时候
	public final static int NEW_ROLE = 13;//新角色进入游戏提示
	public final static int CNN_TODAY  = 14;//商城今日快讯
	public final static int USE_PROP  = 15;//使用道具
}
