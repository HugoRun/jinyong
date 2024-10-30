package com.ls.ben.vo.goods.equip;

/**
 * 功能:套装
 * @author 刘帅 10:32:21 PM
 */
public class SuitVO
{
	/** id */
	private int suitId;
	/** 套装名称 */
	private String suitName;
	/** 组成的套装装备的数量 */
	private int suitPartsNum;
	/** 两件效果 */
	private String twoEffects;
	/** 两件效果描述 */
	private String twoEffectsDescribe;
	/** 三件效果 */
	private String threeEffects;
	/** 三件效果描述 */
	private String threeEffectsDescribe;
	/** 四件效果 */
	private String fourEffects;
	/** 四件效果描述 */
	private String fourEffectsDescribe;
	public int getSuitId()
	{
		return suitId;
	}
	public String getSuitName()
	{
		return suitName;
	}
	public int getSuitPartsNum()
	{
		return suitPartsNum;
	}
	public String getTwoEffects()
	{
		return twoEffects;
	}
	public String getTwoEffectsDescribe()
	{
		return twoEffectsDescribe;
	}
	public String getThreeEffects()
	{
		return threeEffects;
	}
	public String getThreeEffectsDescribe()
	{
		return threeEffectsDescribe;
	}
	public String getFourEffects()
	{
		return fourEffects;
	}
	public String getFourEffectsDescribe()
	{
		return fourEffectsDescribe;
	}
	public void setSuitId(int suitId)
	{
		this.suitId = suitId;
	}
	public void setSuitName(String suitName)
	{
		this.suitName = suitName;
	}
	public void setTwoEffects(String twoEffects)
	{
		this.twoEffects = twoEffects;
	}
	public void setTwoEffectsDescribe(String twoEffectsDescribe)
	{
		this.twoEffectsDescribe = twoEffectsDescribe;
	}
	public void setThreeEffects(String threeEffects)
	{
		this.threeEffects = threeEffects;
	}
	public void setThreeEffectsDescribe(String threeEffectsDescribe)
	{
		this.threeEffectsDescribe = threeEffectsDescribe;
	}
	public void setFourEffects(String fourEffects)
	{
		this.fourEffects = fourEffects;
	}
	public void setFourEffectsDescribe(String fourEffectsDescribe)
	{
		this.fourEffectsDescribe = fourEffectsDescribe;
	}
}
