/**
 * 
 */
package com.ben.vo.equipmsg;

/**
 * @author Administrator
 * 
 */
public class EquipMsgVO
{
	/** 装备掉落消息提示ID */
	private int ePk;
	/** 玩家id */
	private int pPk;
	/** 消息内容 */
	private String eMsg;
	public int getEPk()
	{
		return ePk;
	}
	public void setEPk(int pk)
	{
		ePk = pk;
	}
	public int getPPk()
	{
		return pPk;
	}
	public void setPPk(int pk)
	{
		pPk = pk;
	}
	public String getEMsg()
	{
		return eMsg;
	}
	public void setEMsg(String msg)
	{
		eMsg = msg;
	}
	
	
}
