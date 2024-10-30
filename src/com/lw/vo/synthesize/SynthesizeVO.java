package com.lw.vo.synthesize;

public class SynthesizeVO
{
	// 配方ID
	private int synthesizeID;
	// 配方类型
	private int synthesizeTye;
	// 配方等级
	private int synthesizeLv;
	// 合成需要的物品
	private String prop;
	// 生成物品
	private String synthesizeProp;
	// 合成成功得到的熟练度
	private int synthesizeSleight;
	// 合成技能熟练度的下限
	private int synthesizeMinSleight;
	// 该配方能获得的熟练度上限
	private int synthesizeMaxSleight;
	// 是否需要技能书
	private int synthesizeBook;

	public int getSynthesizeBook()
	{
		return synthesizeBook;
	}

	public void setSynthesizeBook(int synthesizeBook)
	{
		this.synthesizeBook = synthesizeBook;
	}

	public int getSynthesizeID()
	{
		return synthesizeID;
	}

	public void setSynthesizeID(int synthesizeID)
	{
		this.synthesizeID = synthesizeID;
	}

	public int getSynthesizeTye()
	{
		return synthesizeTye;
	}

	public void setSynthesizeTye(int synthesizeTye)
	{
		this.synthesizeTye = synthesizeTye;
	}

	public int getSynthesizeLv()
	{
		return synthesizeLv;
	}

	public void setSynthesizeLv(int synthesizeLv)
	{
		this.synthesizeLv = synthesizeLv;
	}

	public String getProp()
	{
		return prop;
	}

	public void setProp(String prop)
	{
		this.prop = prop;
	}

	public String getSynthesizeProp()
	{
		return synthesizeProp;
	}

	public void setSynthesizeProp(String synthesizeProp)
	{
		this.synthesizeProp = synthesizeProp;
	}

	public int getSynthesizeSleight()
	{
		return synthesizeSleight;
	}

	public void setSynthesizeSleight(int synthesizeSleight)
	{
		this.synthesizeSleight = synthesizeSleight;
	}

	public int getSynthesizeMaxSleight()
	{
		return synthesizeMaxSleight;
	}

	public void setSynthesizeMaxSleight(int synthesizeMaxSleight)
	{
		this.synthesizeMaxSleight = synthesizeMaxSleight;
	}

	public int getSynthesizeMinSleight()
	{
		return synthesizeMinSleight;
	}

	public void setSynthesizeMinSleight(int synthesizeMinSleight)
	{
		this.synthesizeMinSleight = synthesizeMinSleight;
	}
}
