package com.ls.ben.vo.info.login;

import com.ls.pub.constant.player.PlayerState;

/**
 * ����:��¼��û���Ϣ
 * @author ��˧
 * 1:40:16 PM
 */
public class UserInfoVO
{
	/**sessionId*/
	String sessionId;
	
	/**��ɫid*/
	String pPk;
	
	/** �Ƿ��ظ�  */
	boolean iskicked = false;

	/**
	 * ���״̬
	 */
	int stat = PlayerState.GENERAL;
	
	/**
	 * ֪ͨ�Ƿ��ܵ�������ҹ���
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
