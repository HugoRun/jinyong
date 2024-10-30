package com.ls.pub.constant;

/**
 * 功能:道具类型常量
 * 
 * @author 刘帅 4:00:46 PM
 */
public class PropType
{
	/** 加血道具 */
	public static final int ADDHP = 1;
	/** 加蓝道具 */
	public static final int ADDMP = 2;
	/** 转职道具 */
	public static final int ZHUANZHI = 3;
	/** 回城道具 */
	public static final int GOBACKCITY = 4;
	/** 接受任务道具 */
	public static final int ACCEPTTASK = 5;
	/** 答题道具 */
	public static final int QUIZ = 6;
	/** 召唤道具 */
	public static final int CONJURE = 7;
	/** 标记道具 */
	public static final int MARKUP = 8;
	/** 传送道具 */
	public static final int Carry = 9;
	/** 宠物回复体力 */
	public static final int PETSINEW = 20;
	/** 宠物回复寿命 */
	public static final int PETLONGE = 21;
	/** 技能书道具 */
	public static final int SKILLBOOK = 22;
	/** buff道具 */
	public static final int BUFF = 23;
	/** 普通道具,不能使用的道具 */
	public static final int NORMAL = 24;
	/** 洗宠物道具 */
	public static final int INIT_PET = 25;
	/** 宠物蛋道具 */
	public static final int PET_EGG = 26;
	/** 接受指定任务 */
	public static final int ACCEPT_SPECIFY_TASK = 27;
	/** 从任务列表中接受任务 */
	public static final int ACCEPT_TASK_FROM_LIST = 28;
	/** 宝箱道具 */
	public static final int RARE_BOX = 29;
	/** 捆装药品 */
	public static final int BOX_CURE = 30;
	/** 点化道具 *//*
	public static final int ENCHANTMENT = 31;*/
	/** 发奖宝箱,和普通宝箱不同的是,此宝箱可能会掉落多件物品. */
	public static final int GEI_RARE_BOX = 32;
	/** 宠物技能书 */
	public static final int PETSKILLBOOK = 33;
	/** 装备生产提升成功率的道具 */
	public static final int UPGRADEHELPPROP = 34;
	/** 装备升级材料 */
	//public static final int UPGRADEPROP = 35;
	/** 装备转换五行道具 */
	public static final int EQUIP_CHANGE_WX = 36;
	/** 交易后使用道具 */
	public static final int EXCHANGEUSEPROP = 37;
	/** 小喇叭道具 */
	public static final int SPEAKER = 38;
	/** 免死道具（原地复活） */
	public static final int EXONERATIVE = 39;
	/** 某时间内死亡无惩罚道具 */
	public static final int OUTPUBLISH = 40;
	/** 装备类道具 */
	public static final int EQUIPPROP = 41;
	/** 生活技能书 */
	public static final int LIVESKILLBOOK = 42;
	/** 配方书 */
	public static final int SYNTHESIZEBOOK = 43;
	/** 装备解除绑定状态道具 */
	public static final int EQUIP_UNBIND = 44;
	
	public static final int REDUCEPKVALUE = 45;
	/** 减罪恶值道具，注：暂无用 */
	// public static final int ADDHPLIMIT = 46;/** 增加hp上限道具，注：暂无用 */
	/** 保护装备道具
	 * operate1:表示保护的时间（分钟）
	 *  */
	public static final int EQUIP_PROTECT = 47;
	/** 宠物经验道具 */
	public static final int PETEXP = 48;
	/** 掉落元宝的宝箱 */
	public static final int GET_YUANBAO_BOX = 49;
	/** 免PK道具 */
	public static final int AVOIDPKPROP = 50;
	/** 可写死宠物蛋道具 */
	public static final int PET_EGG_GUDING = 51;
	/** 天眼符 */
	public static final int TIANYANFU = 52;
	/** 黄金宝箱 */
	public static final int GOLD_BOX = 53;
	/** 金钥匙 */
	public static final int GOLD_KEY = 54;
	/** 多buff 道具 */
	// public static final int MANYBUFF = 55;
	/** 书籍类道具 * */
	public static final int BOOK = 56;
	/** 其他宝箱道具 * */
	public static final int OTHER_GOLD_BOX = 57;
	/** 称号 * */
	public static final int HONOUR = 58;
	/** 修理装备道具 * */
	public static final int FIX_ARM_PROP = 59;
	/** 传送符道具 * */
	public static final int SUIBIANCHUAN = 60;
	/** 好友传送符道具 * */
	public static final int FRIENDCHUAN = 61;
	/** 队伍传送符道具 * */
	public static final int GROUPCHUAN = 62;
	/** 心印符道具* */
	public static final int XINYINDU = 63;

	/** 兄弟情深符道具 */
	public static final int BROTHERFU = 64;

	/** 夫妻情深符道具 */
	public static final int MERRYFU = 65;

	/** 增加甜蜜值道具 */
	public static final int ADD_LOVE_DEAR = 66;

	/** 结婚戒指 */
	public static final int JIEHUN_JIEZHI = 67;
	/** 领取离线经验的道具 */
	public static final int ROLE_BEOFF_EXP = 68;
	/** 增加亲密度道具 */
	public static final int ADD_DEAR = 69;
	/** 成为VIP的道具 */
	public static final int VIP = 70;
	/** 隐身符 */
	public static final int YINSHEN = 71;
	/** 陷害符 */
	public static final int XIANHAI = 72;
	/** 武器宝箱* */
	public static final int ARMBOX = 73;
	/** 指南针 */
	public static final int COMPASS = 75;
	/** 秘境地图 */
	public static final int MIJING_MAP = 74;
	/** 反隐身符 */
	public static final int FAN_YINSHEN = 76;
	/** 千里眼 */
	public static final int QIANLIYAN = 77;
	/** 挑战书 */
	public static final int TIAOZHAN = 78;
	/** 刮刮乐 */
	public static final int SCRTCHTICKET = 79;
	/** PK不能使用的药品 */
	public static final int CRUEALLHMP = 80;
	/** 随机BUFF */
	public static final int BUFFRODAM = 81;
	/** 陆涛新宝箱 */
	public static final int TTBOX = 82;
	/** 拉霸宝箱 */
	public static final int LABABOX = 83;
	/*****宠物捕捉**********/
	public static final int GETPETPROP = 84;
	/** 拉霸宝箱刷新道具 */
	public static final int PROPOFLABABOX = 85;
	/*******提升装备品质********/
	public static final int EQUIP_UPGRADE_QUALITY = 86;
	/**装备保护道具（升级，转换五行用）*/
	public static final int SUCCESS_RATE = 87;
	/** 装备升级用材料1 *//*
	public static final int EQUIP_UPGRADE_MATRIAL1 = 88;
	*//** 装备升级用材料2 *//*
	public static final int EQUIP_UPGRADE_MATRIAL2 = 89;*/
	/**
	 * 打孔道具
	 */
	public static final int EQUIP_PUNCH = 91;
	
	/**
	 * 镶嵌装备用的宝石：
	 * 等级表示宝石的等级
	 * operate1:表示装备的属性
	 * operate2:表示下一级宝石的id
	 * operate3:表示描述，如：木攻+5
	 */
	public static final int EQUIP_INLAY_STONE_JIN = 92;//金属性宝石
	public static final int EQUIP_INLAY_STONE_MU = 93;//木属性宝石
	public static final int EQUIP_INLAY_STONE_SHUI = 94;//水属性宝石
	public static final int EQUIP_INLAY_STONE_HUO = 95;//火金属性宝石
	public static final int EQUIP_INLAY_STONE_TU = 96;//土金属性宝石
	/**
	 * 修复破损的道具
	 */
	public static final int MIANTAIN_BAD_EQUIP = 97;
	/**
	 * 帮派材料道具
	 * operate1:表示贡献度
	 */
	public static final int F_MATERIAL = 98;
}
