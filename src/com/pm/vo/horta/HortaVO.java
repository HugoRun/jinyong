package com.pm.vo.horta;

public class HortaVO
{

	/** 系统奖励表ID  */  	  		
	public int  hortaId;
	/*** 系统奖励类型 **/	
	public int  hortaType;
	/**   系统奖励名称    **/
	public String horta_name;
	/**   具体奖励,决定了奖励在页面显示的顺序    **/
	public int hortaSonId;
	/**   具体奖励名称    **/
	public String horta_sonName;
	
	/**   系统奖励条件之, 会员等级 ,以下如果没有要求统统填入零   **/
	public String  vipGrade;
	/**   系统奖励条件之, 在线时间   **/
	public int onlineTime;
	/**   系统奖励条件之, 玩家等级    **/
	public String  wjGrade;
/*	*//**   系统奖励条件之, 玩家性别   **//*
	public int  wjSex	;
	*//**   系统奖励条件之, 玩家门派   **//*
	public int  wjMenpai;
	*//**   系统奖励条件之, 玩家称号, 如果有多个称号,以","分割   **//*
	public String wjTitle;
	*//**   系统奖励条件之, 玩家声望   **//*
	public String  wjCredit;
	*//**   系统奖励条件之, 空余等下一个   **//*
	public String wjNext;
*/	
	/**    奖励装备或物品, 以";"分割, 和兑换菜单的制作方法相同    ****/
	public String giveGoods;
	/**  是否只能领取一次  */
	public int isOnlyOne;
	/**  一天之内只能领取一次  */
	public String onces;
	
	/** 失败说明 */
	public String display;
	
	/** 奖励说明 */
	public String hortaDisplay;
	
	
	
	public String getHortaDisplay()
	{
		return hortaDisplay;
	}

	public void setHortaDisplay(String hortaDisplay)
	{
		this.hortaDisplay = hortaDisplay;
	}

	public String getDisplay()
	{
		return display;
	}

	public void setDisplay(String display)
	{
		this.display = display;
	}

	public int getIsOnlyOne()
	{
		return isOnlyOne;
	}

	public void setIsOnlyOne(int isOnlyOne)
	{
		this.isOnlyOne = isOnlyOne;
	}

	public String getOnces()
	{
		return onces;
	}

	public void setOnces(String onces)
	{
		this.onces = onces;
	}

	public int getHortaId()
	{
		return hortaId;
	}

	public void setHortaId(int hortaId)
	{
		this.hortaId = hortaId;
	}

	public int getHortaType()
	{
		return hortaType;
	}

	public void setHortaType(int hortaType)
	{
		this.hortaType = hortaType;
	}

	public String getHorta_name()
	{
		return horta_name;
	}

	public void setHorta_name(String horta_name)
	{
		this.horta_name = horta_name;
	}

	public int getHortaSonId()
	{
		return hortaSonId;
	}

	public void setHortaSonId(int hortaSonId)
	{
		this.hortaSonId = hortaSonId;
	}

	public String getHorta_sonName()
	{
		return horta_sonName;
	}

	public void setHorta_sonName(String horta_sonName)
	{
		this.horta_sonName = horta_sonName;
	}

	public String getVipGrade()
	{
		return vipGrade;
	}

	public void setVipGrade(String vipGrade)
	{
		this.vipGrade = vipGrade;
	}

	public int getOnlineTime()
	{
		return onlineTime;
	}

	public void setOnlineTime(int onlineTime)
	{
		this.onlineTime = onlineTime;
	}

	public String getWjGrade()
	{
		return wjGrade;
	}

	public void setWjGrade(String wjGrade)
	{
		this.wjGrade = wjGrade;
	}


	public String getGiveGoods()
	{
		return giveGoods;
	}

	public void setGiveGoods(String giveGoods)
	{
		this.giveGoods = giveGoods;
	}
	
	
	
}
