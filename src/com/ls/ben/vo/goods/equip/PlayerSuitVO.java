package com.ls.ben.vo.goods.equip;

/**
 * ����:�����װ
 * @author ��˧ 10:32:21 PM
 */
public class PlayerSuitVO
{
	/** id */
	private int suitId;
	/** ��װ���� */
	private String suitName;
	/** ��ɵ���װװ�������� */
	private int suitPartsNum;
	
	/** ��װЧ������ */
	private String effectsDescribe;
	/** ��װЧ�� */
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
