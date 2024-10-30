package com.ben.rank.model;

import java.util.HashMap;
import java.util.Map;

import com.ls.pub.config.GameConfig;

public class RankConstants
{
	public static Map<String, Integer> FILED_TYPE = new HashMap<String, Integer>();

	//排行规则描述
	public static Map<String,String> DETAIL = new HashMap<String, String>();
	//该排行第一的描述
	public static Map<String,String> FIRST_DES = new HashMap<String, String>();
	
	//个人榜
	public final static String DENGJI = "r_p_exp";//等级
	public final static String KILL = "r_kill";//杀手榜
	public final static String DEAR = "r_dear";//爱情
	public final static String EVIL = "r_evil";//恶人榜
	public final static String GLORY = "r_glory";//荣誉榜
	public final static String MONEY = "r_money";//金银榜
	public final static String YUANBAO = "r_yuanbao";//富豪榜
	public final static String CREDIT = "r_credit";//声望榜
	public final static String VIP = "r_vip";//尊贵榜
	public final static String BANGKILL = "r_bangkill";//屠血榜
	public final static String KILLNPC = "r_killnpc";//杀怪榜
	public final static String WEI_TASK = "r_wei_task";//威望榜
	public final static String DEAD = "r_dead";//死亡榜
	public final static String KILLBOSS = "r_killboss";//击杀榜
	public final static String ZHONG = "r_zhong";//忠贞榜
	public final static String SALE = "r_sale";//生意榜
	public final static String YI = "r_yi";//义气榜
	public final static String MENG = "r_meng";//猛将榜
	public final static String SHENG = "r_sheng";//江湖圣榜
	public final static String BOYI = "r_boyi";//博弈榜
	public final static String LOST = "r_lost";//迷宫排行榜
	public final static String KILLLANG = "r_killlang";//杀死千面郎君
	public final static String LANGJUN = "r_langjun";//成为千面郎君
	public final static String ANS = "r_ans";//神算榜
	public final static String OPEN = "r_open";//开光榜
	
	//宠物榜
	public final static String CHONGWU = "p_chongwu";//宠物
	public final static String PET = "p_pet";//狂宠
	//帮派榜
	public final static String F_PRESTIGE = "f_prestige";//帮派声望榜
	public final static String F_ZHANLI = "f_zhanli";//帮派战力榜
	public final static String F_RICH = "f_rich";//帮派财富榜
	//装备榜
	public final static String SHENBING = "e_shenbing";//神兵榜
	public final static String XIANJIA = "e_xianjia";//仙甲榜
	public final static String FABAO = "e_fabao";//法宝榜
	public final static String ZUOQI = "e_zuoqi";//坐骑榜

	static
	{
		int i = 1;
		FILED_TYPE.put(DENGJI, i++);
		FILED_TYPE.put(KILL, i++);
		FILED_TYPE.put(DEAR, i++);
		FILED_TYPE.put(EVIL, i++);
		FILED_TYPE.put(GLORY, i++);
		FILED_TYPE.put(MONEY,i++);  //6
		FILED_TYPE.put(YUANBAO, i++);
		FILED_TYPE.put(CREDIT, i++);//8
		FILED_TYPE.put(OPEN, i++);
		FILED_TYPE.put(VIP, i++);//10
		FILED_TYPE.put(BANGKILL, i++);
		FILED_TYPE.put(KILLNPC, i++);
		FILED_TYPE.put(WEI_TASK, i++);
		FILED_TYPE.put(DEAD, i++);//14
		FILED_TYPE.put(KILLBOSS, i++);
		FILED_TYPE.put(ZHONG, i++);
		FILED_TYPE.put(SALE, i++);//17
		FILED_TYPE.put(YI, i++);
		FILED_TYPE.put(ANS, i++);
		FILED_TYPE.put(MENG, i++);
		FILED_TYPE.put(SHENBING, i++);
		FILED_TYPE.put(CHONGWU, i++);//22
		FILED_TYPE.put(PET, i++);//23
		FILED_TYPE.put(SHENG, i++);
		FILED_TYPE.put(BOYI, i++);
		FILED_TYPE.put(LOST, i++);
		FILED_TYPE.put(KILLLANG, i++);
		FILED_TYPE.put(LANGJUN, i++);
		FILED_TYPE.put(XIANJIA, 29);
		FILED_TYPE.put(FABAO, 30);
		FILED_TYPE.put(ZUOQI, 31);
		
		DETAIL.put(DENGJI,"【等级榜】*依据等级高低进行排行*（每月重置）");
		DETAIL.put(KILL, "【战力榜】*依据杀人数目进行排行*（每月重置）");
		DETAIL.put(DEAR, "【爱情榜】*依据亲密度进行双人组合排行*（每周重置）");
		DETAIL.put(EVIL, "【血战榜】*依据罪恶值进行排行的血战榜！*（每月重置）");
		DETAIL.put(GLORY, "【荣誉榜】*依据击杀对方阵营获得荣誉点进行排行*（每月重置）");
		DETAIL.put(MONEY, "【仙石榜】*依据"+GameConfig.getMoneyUnitName()+"数进行排行*");
		DETAIL.put(YUANBAO, "【仙晶榜】*依据"+GameConfig.getYuanbaoName()+"消耗进行排行*（每周重置）");
		DETAIL.put(CREDIT, "【声望榜】*依据师徒关系的声望进行排行*");
		
		DETAIL.put(VIP, "【尊贵榜】*依据会员级别和时长进行排行*（每月重置）");
		DETAIL.put(BANGKILL, "【屠血榜】*依据帮派杀人数总和进行排行*（每月重置）");
		DETAIL.put(KILLNPC, "【杀怪榜】*依据杀怪点数进行排行*（每周重置）");
		DETAIL.put(WEI_TASK, "【威望榜】*依据获得的威望进行排行*");
		DETAIL.put(DEAD, "【死亡榜】*依据被杀死的次数进行排行*（每周重置）");
		DETAIL.put(KILLBOSS,"【击杀榜】*依据击杀副本Boss的点数进行排行*（每月重置）");
		DETAIL.put(ZHONG, "【忠贞榜】*依据在线时长进行排行*（每周重置）");
		DETAIL.put(SALE, "【生意榜】*依据拍卖成功次数进行排行*（每月重置）");
		DETAIL.put(YI, "【义气榜】*依据义气度进行双人组合排行*（每周重置）");
		DETAIL.put(MENG, "【猛将榜】*依据完成副本的点数进行排行*（每月重置）");
		DETAIL.put(SHENG,"【江湖圣榜】*依据经验值，在线时间及江湖声望进行排行*");
		DETAIL.put(BOYI, "【博彩榜】*根据彩票中奖金额进行排行*");
		DETAIL.put(LOST, "【探险榜】*依据本周累计完成迷宫的层数进行排行*（每月重置）");
		DETAIL.put(KILLLANG, "【神捕榜】*依据杀死真千面郎君次数进行排行*");
		DETAIL.put(LANGJUN, "【生存榜】*依据成功躲过追杀次数进行排行*");
		
		//装备榜
		DETAIL.put(SHENBING,"【神兵榜】*依据武器的攻击和属性攻击进行排行的*");
		FIRST_DES.put(SHENBING,"★混沌神兵★");
		DETAIL.put(FABAO,"【法宝榜】*依据法宝的攻击防御和属性攻击防御进行排行的*");
		FIRST_DES.put(FABAO,"★混沌灵宝★");
		DETAIL.put(XIANJIA,"【仙甲榜】*依据防具的防御和属性防御进行排行的*");
		FIRST_DES.put(XIANJIA,"★混沌仙甲★");
		DETAIL.put(ZUOQI,"【坐骑榜】*依据坐骑的等级进行排行的*");
		FIRST_DES.put(ZUOQI,"★混沌仙骑★");
		
		//帮派榜
		DETAIL.put(F_PRESTIGE,"【声望榜】*依据帮会声望的多少进行排行*");
		FIRST_DES.put(F_PRESTIGE, "★遂人氏★");
		DETAIL.put(F_ZHANLI,"【战力榜】*依据氏族的等级、战斗力和人数进行排行*");
		FIRST_DES.put(F_ZHANLI, "★盘古氏★");
		DETAIL.put( F_RICH,"【财富榜】*依据帮会材料的多少进行排行*");
		FIRST_DES.put( F_RICH, "★伏羲氏★");
		//活动榜
		DETAIL.put(OPEN, "【开光榜】*依据开黄金宝箱数量进行排行*（每周重置）");
		FIRST_DES.put(OPEN, "★福仙★");
		DETAIL.put(ANS, "【神算榜】*依据答题正确条数进行排行*（每周重置）");
		FIRST_DES.put(ANS, "★天师★");
	}
	
	public static void main(String[] args)
	{
		for(String s : FILED_TYPE.keySet()){
			System.out.println(s +" : "+FILED_TYPE.get(s));
		}
	}
}
