package com.ls.ben.vo.info.effect;

import com.ls.ben.dao.info.buff.BuffDao;
import com.ls.ben.vo.goods.prop.PropVO;

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
	
	/**使用效果变化数值1*/
	private String effectValue1;
	
	/**标示道具是否成功使用*/
	private boolean isEffected;
	
	/**不能使用描述*/
	private String noUseDisplay;
	//专职的称号
	private String title;
	//技能学习道具的技能id
	private String skillId;
	//判断是否加慢体力
	private int isPetFatigue = 0;
	/** 针对buff的不同情况，有不同的描述 */
	private String buffDisplay;
	/** buffType */
	private String[] buffType;
	
	
	
	
	public String[] getBuffType()
	{
		return buffType;
	}
	public void setBuffType(String[] buffType)
	{
		this.buffType = buffType;
	}
	public String getBuffDisplay()
	{
		return buffDisplay;
	}
	public void setBuffDisplay(String buffDisplay)
	{
		BuffDao buffDao = new BuffDao();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buffType.length; i++)
		{
			sb.append(buffDao.getBuffDisplay(Integer.parseInt(buffType[i])));
		}
		
		this.buffDisplay = sb.toString();
	}
	
	public void setEffectDisplay(PropVO prop)
	{
		BuffDao buffDao = new BuffDao();
		StringBuffer sb = new StringBuffer();
		sb.append("您使用了").append(prop.getPropName()).append(",");
		for (int i = 0; i < buffType.length; i++)
		{
			sb.append(buffDao.getBuffDisplay(Integer.parseInt(buffType[i])));
		}
		
		this.effectDisplay = sb.toString();
	}
	
	public int getIsPetFatigue()
	{
		return isPetFatigue;
	}
	public void setIsPetFatigue(int isPetFatigue)
	{
		this.isPetFatigue = isPetFatigue;
	}
	public String getSkillId()
	{
		return skillId;
	}
	public void setSkillId(String skillId)
	{
		this.skillId = skillId;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void setEffected(boolean isEffected)
	{
		this.isEffected = isEffected;
	}
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
	public boolean getIsEffected()
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
	public String getEffectValue1() {
		return effectValue1;
	}
	public void setEffectValue1(String effectValue1) {
		this.effectValue1 = effectValue1;
	}
}
