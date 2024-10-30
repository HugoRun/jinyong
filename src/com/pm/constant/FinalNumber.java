package com.pm.constant;
/**
 * 放置各种常量
 * @author zhangjj 
 * 
 */
public class FinalNumber
{
	/**
	 * 战场区域的braea点
	 */
	public static final int  FIELDBAREA = 9;
	
	/**
	 * 战场区域的map点
	 */
	public static final int  FIELDMAP1 = 67;
	
	/**
	 * 现在 人物 死亡时掉落的 普通装备的概率
	 */
	public static final int PUTONG = 100;
	
	/**
	 * 现在 人物 死亡时掉落的 优秀装备的概率
	 */
	public static final int YOUXIU = 75;
	/**
	 * 现在 人物 死亡时掉落的 良好装备的概率
	 */
	public static final int LIANGHAO = 30;
	/**
	 * 现在 人物 死亡时掉落的 极品装备的概率
	 */
	public static final int JIPIN = 10;

	/** 在攻城战第二阶段时人物死亡的极限次数 */
	public static final int SECONDLIMITDEAD = 5;

	/*** 攻城战中个人参战***/
	public static final int PERSONBATTLE = 1;	
	
	/*** 攻城战中帮派参战***/
	public static final int TONGBATTLE = 2;	
	
	/*** 攻城战中守城作战***/
	public static final int RECOVERBATTLE = 2;	
	
	/*** 攻城战中攻城作战***/
	public static final int ATTACKBATTLE = 1;	
	
	/** 攻城状态,0为准备阶段 **/
	public static final int RANDYFORPK  = 0;
	
	/** 攻城状态,1为第一阶段 **/
	public static final int FIRST  = 1;
	
	/** 攻城状态,2为城门开始被人打打 **/
	public static final int SECOND  = 2;
	
	/** 攻城状态,3为城门被打破 **/
	public static final int THIRD  = 3;
	
	/** 攻城状态,4为英雄雕像被打破攻城进入第二阶段 **/
	public static final int FORTH  = 4;
	
	/** 攻城状态,在第二阶段最多死亡次数,5次加上三次免死  **/
	public static final int DEADLIMIT  = 8;
	
}
