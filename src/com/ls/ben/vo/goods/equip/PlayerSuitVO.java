package com.ls.ben.vo.goods.equip;

/**
 * 功能:玩家套装
 * @author 刘帅 10:32:21 PM
 */
public class PlayerSuitVO
{
	/** id */
	private int suitId;
	/** 套装名称 */
	private String suitName;
	/** 组成的套装装备的数量 */
	private int suitPartsNum;
	
	/** 套装效果描述 */
	private String effectsDescribe;
	/** 套装效果 */
	private String effects;
	
	public int getSuitId()
	{
		return suitId;
	}
	public void setSuitId(int suitId)
	{
		this.suitId = suitId;
	}
	public String getSuitName()
	{
		return suitName;
	}
	public void setSuitName(String suitName)
	{
		this.suitName = suitName;
	}
	public int getSuitPartsNum()
	{
		return suitPartsNum;
	}
	public void setSuitPartsNum(int suitPartsNum)
	{
		this.suitPartsNum = suitPartsNum;
	}
	public String getEffectsDescribe()
	{
		return effectsDescribe;
	}
	public void setEffectsDescribe(String effectsDescribe)
	{
		this.effectsDescribe = effectsDescribe;
	}
	public String getEffects()
	{
		return effects;
	}
	public void setEffects(String effects)
	{
		this.effects = effects;
	}

}
