package com.ls.ben.vo.info.login;

import com.ls.pub.constant.player.PlayerState;

/**
 * 功能:记录活动用户信息
 * @author 刘帅
 * 1:40:16 PM
 */
public class UserInfoVO
{
	/**sessionId*/
	String sessionId;
	
	/**角色id*/
	String pPk;
	
	/** 是否重复  */
	boolean iskicked = false;

	/**
	 * 玩家状态
	 */
	int stat = PlayerState.GENERAL;
	
	/**
	 * 通知是否受到其他玩家攻击
	 */
	boolean isAttacked = false;
	
	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	public String getPPk() {
		return pPk;
	}

	public void setPPk(String pk) {
		pPk = pk;
	}

	public boolean isIskicked()
	{
		return iskicked;
	}

	public void setIskicked(boolean iskicked)
	{
		this.iskicked = iskicked;
	}

	public int getStat()
	{
		return stat;
	}

	public void setStat(int stat)
	{
		this.stat = stat;
	}

	public boolean isAttacked()
	{
		return isAttacked;
	}

	public void setAttacked(boolean isAttacked)
	{
		this.isAttacked = isAttacked;
	}

	
	
}
