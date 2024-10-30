package com.ls.ben.vo.laba;

import com.ls.ben.vo.info.npc.NpcdropVO;

public class labaVO
{
	private int sNum;//用来判断后两个物品
	private NpcdropVO nvo;//掉落的物品
	public int getSNum()
	{
		return sNum;
	}
	public void setSNum(int num)
	{
		sNum = num;
	}
	public NpcdropVO getNvo()
	{
		return nvo;
	}
	public void setNvo(NpcdropVO nvo)
	{
		this.nvo = nvo;
	}
}
