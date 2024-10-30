package com.ls.ben.dao.info.npc.effect;

/**
 * 功能:记录道具使用效果
 * @author 刘帅
 * 2:57:26 PM
 */
public class PropUseEffect
{
	/**道具种类*/
	private int propType;
	
	/**使用效果描述*/
	private String effectDisplay;
	
	/**使用效果变化数值*/
	private String effectValue;
	
	/**标示道具是否成功使用*/
	private boolean isEffected;
	
	/**不能使用描述*/
	private String noUseDisplay;
	
	
	public String getEffectDisplay()
	{
		return effectDisplay;
	}
	public void setEffectDisplay(String effectDisplay)
	{
		this.effectDisplay = effectDisplay;
	}
	public String getEffectValue()
	{
		return effectValue;
	}
	public void setEffectValue(String effectValue)
	{
		this.effectValue = effectValue;
	}
	public boolean isEffected()
	{
		return isEffected;
	}
	public void setIsEffected(boolean isEffected)
	{
		this.isEffected = isEffected;
	}
	public String getNoUseDisplay()
	{
		return noUseDisplay;
	}
	public void setNoUseDisplay(String noUseDisplay)
	{
		this.noUseDisplay = noUseDisplay;
	}
	public int getPropType()
	{
		return propType;
	} 
	public void setPropType(int propType)
	{
		this.propType = propType;
	}
}
