package com.ls.pub.constant.buff;

/**
 * 功能:buff类型
 * @author 刘帅
 * 10:43:44 AM
 */
public class BuffType {
	/*
	 * 增益Buff:
	经验加成	    1	道具	时间	Y时间段内获得X倍数经验加成
	掉落率提高	2	道具	时间	Y时间段内获得X倍数掉宝加成
	捕捉率提高	3	道具	时间	Y时间段内捕捉宠物几率提高X点
	极品装备掉率	4	道具	时间	Y时间段内极品装备附着提高X点
	回复HP	    5	道具	回合	（战斗中）Y时间段内每回合回复X点HP
	回复MP	    6	道具	回合	（战斗中）Y时间段内每回合回复X点MP
	免疫中毒	    7	道具	时间	Y时间段内玩家中毒免疫；
	免疫击晕   	8	道具	时间	Y时间段内玩家击晕免疫；
	攻击力增强	9	道具	时间	Y时间段内获得X点攻击力加成
	防御力增强	10	道具	时间	Y时间段内获得X点防御力加成
	HP上限增强	11	道具	时间	Y时间段内获得X点Hp上限加成
	MP上限增强	12	道具	时间	Y时间段内获得X点Mp上限加成
	金属性伤害提高	13	道具	时间	Y时间段内提高玩家金属性伤害X点
	木属性伤害提高	14	道具	时间	Y时间段内提高玩家木属性伤害X点
	水属性伤害提高	15	道具	时间	Y时间段内提高玩家水属性伤害X点
	火属性伤害提高	16	道具	时间	Y时间段内提高玩家火属性伤害X点
	土属性伤害提高	17	道具	时间	Y时间段内提高玩家土属性伤害X点
	金属性防御提高	18	道具	时间	Y时间段内提高玩家金属性防御X点
	木属性防御提高	19	道具	时间	Y时间段内提高玩家木属性防御X点
	水属性防御提高	20	道具	时间	Y时间段内提高玩家水属性防御X点
	火属性防御提高	21	道具	时间	Y时间段内提高玩家火属性防御X点
	土属性防御提高	22	道具	时间	Y时间段内提高玩家土属性防御X点
	宠物经验提高	23  道具 时间 Y时间段内提高玩家宠物经验X点
	减罪恶值速度提高 25 道具 时间 Y时间段内提高玩家罪恶值减少速度
	免死符		26	道具	时间	 Y时间段内玩家死亡可以不掉经验值	
	不可传送		28  道具 时间	 Y时间段内玩家不可以传送
    */
	
	public static final int ADD_EXP = 1;
	public static final int ADD_DROP_PROBABILITY = 2;
	public static final int ADD_CATCH_PROBABILITY = 3;
	public static final int ADD_NONSUCH_PROBABILITY = 4;
	
	public static final int CHANGER_HP = 5;
	public static final int CHANGER_MP = 6;
	
	public static final int IMMUNITY_POISON = 7;
	public static final int IMMUNITY__DIZZY = 8;
	
	public static final int ATTACK = 9;
	public static final int DEFENCE = 10;
	public static final int HP_UP = 11;
	public static final int MP_UP = 12;
	
	//五行的buff效果
	public static final int JIN_ATTACK = 13;
	public static final int MU_ATTACK = 14;
	public static final int SHUI_ATTACK = 15;
	public static final int HUO_ATTACK = 16;
	public static final int TU_ATTACK = 17;
	
	public static final int JIN_DEFENCE = 18;
	public static final int MU_DEFENCE = 19;
	public static final int SHUI_DEFENCE = 20;
	public static final int HUO_DEFENCE = 21;
	public static final int TU_DEFENCE = 22;
	
	//宠物经验条
	public static final int PET_EXP = 23;
	
	//人物暴击率
	public static final int CHANGER_BJ = 24;
	
	// 减少罪恶值的速度
	public static final int REDUCEPKVALUE = 25;
	public static final int OUTOFDEADPUBLISH = 26;
	//避怪道具BUFF
	public static final int BIGUAIDAOJUBUFF = 27;
	//VIPBUFF
	public static final int VIPBUFF = 28;
	/** 传送菜单不可传送 */
	public static final int NOPERMISSCHUANSONG = 29;
}
