package com.lw.vo.synthesize;

public class PlayerSynthesizeVO
{
	// 主键
	private int id;
	// 玩家PPK
	private int pPk;
	// 配方ID
	private int synthesizeID;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public int getSynthesizeID()
	{
		return synthesizeID;
	}

	public void setSynthesizeID(int synthesizeID)
	{
		this.synthesizeID = synthesizeID;
	}
}
