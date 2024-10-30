package com.ls.pub.constant.player;

/**
 * 玩家状态常量，
 * @author 刘帅
 * 1:58:44 PM
 */
public class PlayerState {

	/**
	 * 平常状态
	 */
	public static final int GENERAL = 1;
	/**
	 * npc战斗状态
	 */
	public static final int NPCFIGHT = 2;
	/**
	 * 交易状态
	 */
	public static final int TRADE = 3;
	/**
	 * 组队状态
	 */
	public static final int GROUP = 4;
	/**
	 * 离线状态
	 */
	public static final int OUTLINE = 5;
	/**
	 * pk战斗状态
	 */
	public static final int PKFIGHT = 6;
	/**
	 * 战斗状态
	 */
	public static final int FIGHT = 7;
	/**
	 * 私聊
	 */
	public static final int TALK = 8;
	
	/**
	 * 在商城时受保护
	 */
	public static final int MALL = 9;
	/**
	 * 论坛时受保护
	 */
	public static final int FORUM = 10;
	/**
	 * 特殊状态,目前用于pk后捡取物品时保护其不被打扰,
	 * 但是也不影响其接受pk胜利和失败信息
	 */
	public static final int EXTRA = 11;
	/**
	 * 系统特殊类型弹出式消息
	 */
	public static final int SYSMSG = 12;
	/**
	 * 在包裹栏中
	 */
	public static final int VIEWWRAP = 13;
	/*******宝箱状态*********/
	public static final int BOX = 13;
	
	/**
	 * 返回状态所对应的名称
	 * @param state
	 * @return
	 */
	public String returnStateName(int state){
		String hint = "战斗";
		if(state == PlayerState.GENERAL){
			return hint = "平常状态";
		}
		if(state == PlayerState.NPCFIGHT){
			return hint = "战斗";
		}
		if(state == PlayerState.TRADE){
			return hint = "交易";
		}
		if(state == PlayerState.GROUP){
			return hint = "组队";
		}
		if(state == PlayerState.OUTLINE){
			return hint = "离线";
		}
		if(state == PlayerState.PKFIGHT){
			return hint = "战斗";
		}
		if(state == PlayerState.FIGHT){
			return hint = "战斗";
		}
		if(state == PlayerState.TALK){
			return hint = "私聊";
		}
		if ( state == PlayerState.FORUM) {
			return hint = "论坛";
		}
		if ( state == PlayerState.EXTRA) {
			return hint = "捡取物品";
		}
		if ( state == PlayerState.SYSMSG) {
			return hint = "查看消息";
		}
		if ( state == PlayerState.VIEWWRAP) {
			return hint = "查看物品";
		}
		if ( state == PlayerState.BOX) {
			return hint = "开宝箱状态";
		}
		if ( state == PlayerState.MALL) {
			return hint = "商城状态";
		}
		return hint;
	}
	
}
