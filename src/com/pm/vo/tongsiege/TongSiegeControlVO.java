package com.pm.vo.tongsiege;

/**
 * 帮派攻城控制表
 * @author ZHANGJJ
 * @version 1.0
 */
public class TongSiegeControlVO
{
	/** 帮派攻城战控制表ID  */
	public int controlId;
	/*** 攻城战ID,代表着某个城市的攻城  **/	
	public int siegeId	;
	/**   siege_id所代表的帮派马上要开始的是第几次攻城战   ***/	
	public int 	siegeNumber;
	/**   下次攻城战开始时间  */	
	public String 	siegeStartTime;
	/**   下次攻城战报名截止时间 */	
	public String 	siegeSignEnd;
	/**    此攻城战场上次胜利帮派ID   **/	
	public int 	lastWinTongid;
	/**     当前阶段,1为攻城第一阶段,2为攻城第二阶段,3为攻城结束等待下次开始   ***/	
	public int 	nowPhase	;
	public int getControlId()
	{
		return controlId;
	}
	public void setControlId(int controlId)
	{
		this.controlId = controlId;
	}
	public int getSiegeId()
	{
		return siegeId;
	}
	public void setSiegeId(int siegeId)
	{
		this.siegeId = siegeId;
	}
	public int getSiegeNumber()
	{
		return siegeNumber;
	}
	public void setSiegeNumber(int siegeNumber)
	{
		this.siegeNumber = siegeNumber;
	}
	
	public String getSiegeStartTime()
	{
		return siegeStartTime;
	}
	public void setSiegeStartTime(String siegeStartTime)
	{
		this.siegeStartTime = siegeStartTime;
	}
	public String getSiegeSignEnd()
	{
		return siegeSignEnd;
	}
	public void setSiegeSignEnd(String siegeSignEnd)
	{
		this.siegeSignEnd = siegeSignEnd;
	}
	public int getLastWinTongid()
	{
		return lastWinTongid;
	}
	public void setLastWinTongid(int lastWinTongid)
	{
		this.lastWinTongid = lastWinTongid;
	}
	public int getNowPhase()
	{
		return nowPhase;
	}
	public void setNowPhase(int nowPhase)
	{
		this.nowPhase = nowPhase;
	}	
	
	
	

}
