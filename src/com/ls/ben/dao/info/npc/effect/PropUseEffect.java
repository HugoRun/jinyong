package com.ls.ben.dao.info.npc.effect;

/**
 * ����:��¼����ʹ��Ч��
 * @author ��˧
 * 2:57:26 PM
 */
public class PropUseEffect
{
	/**��������*/
	private int propType;
	
	/**ʹ��Ч������*/
	private String effectDisplay;
	
	/**ʹ��Ч���仯��ֵ*/
	private String effectValue;
	
	/**��ʾ�����Ƿ�ɹ�ʹ��*/
	private boolean isEffected;
	
	/**����ʹ������*/
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
