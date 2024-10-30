package com.ls.ben.vo.info.effect;

import com.ls.ben.dao.info.buff.BuffDao;
import com.ls.ben.vo.goods.prop.PropVO;

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
	
	/**ʹ��Ч���仯��ֵ1*/
	private String effectValue1;
	
	/**��ʾ�����Ƿ�ɹ�ʹ��*/
	private boolean isEffected;
	
	/**����ʹ������*/
	private String noUseDisplay;
	//רְ�ĳƺ�
	private String title;
	//����ѧϰ���ߵļ���id
	private String skillId;
	//�ж��Ƿ��������
	private int isPetFatigue = 0;
	/** ���buff�Ĳ�ͬ������в�ͬ������ */
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
		sb.append("��ʹ����").append(prop.getPropName()).append(",");
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
