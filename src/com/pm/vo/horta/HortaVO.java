package com.pm.vo.horta;

public class HortaVO
{

	/** ϵͳ������ID  */  	  		
	public int  hortaId;
	/*** ϵͳ�������� **/	
	public int  hortaType;
	/**   ϵͳ��������    **/
	public String horta_name;
	/**   ���影��,�����˽�����ҳ����ʾ��˳��    **/
	public int hortaSonId;
	/**   ���影������    **/
	public String horta_sonName;
	
	/**   ϵͳ��������֮, ��Ա�ȼ� ,�������û��Ҫ��ͳͳ������   **/
	public String  vipGrade;
	/**   ϵͳ��������֮, ����ʱ��   **/
	public int onlineTime;
	/**   ϵͳ��������֮, ��ҵȼ�    **/
	public String  wjGrade;
/*	*//**   ϵͳ��������֮, ����Ա�   **//*
	public int  wjSex	;
	*//**   ϵͳ��������֮, �������   **//*
	public int  wjMenpai;
	*//**   ϵͳ��������֮, ��ҳƺ�, ����ж���ƺ�,��","�ָ�   **//*
	public String wjTitle;
	*//**   ϵͳ��������֮, �������   **//*
	public String  wjCredit;
	*//**   ϵͳ��������֮, �������һ��   **//*
	public String wjNext;
*/	
	/**    ����װ������Ʒ, ��";"�ָ�, �Ͷһ��˵�������������ͬ    ****/
	public String giveGoods;
	/**  �Ƿ�ֻ����ȡһ��  */
	public int isOnlyOne;
	/**  һ��֮��ֻ����ȡһ��  */
	public String onces;
	
	/** ʧ��˵�� */
	public String display;
	
	/** ����˵�� */
	public String hortaDisplay;
	
	
	
	public String getHortaDisplay()
	{
		return hortaDisplay;
	}

	public void setHortaDisplay(String hortaDisplay)
	{
		this.hortaDisplay = hortaDisplay;
	}

	public String getDisplay()
	{
		return display;
	}

	public void setDisplay(String display)
	{
		this.display = display;
	}

	public int getIsOnlyOne()
	{
		return isOnlyOne;
	}

	public void setIsOnlyOne(int isOnlyOne)
	{
		this.isOnlyOne = isOnlyOne;
	}

	public String getOnces()
	{
		return onces;
	}

	public void setOnces(String onces)
	{
		this.onces = onces;
	}

	public int getHortaId()
	{
		return hortaId;
	}

	public void setHortaId(int hortaId)
	{
		this.hortaId = hortaId;
	}

	public int getHortaType()
	{
		return hortaType;
	}

	public void setHortaType(int hortaType)
	{
		this.hortaType = hortaType;
	}

	public String getHorta_name()
	{
		return horta_name;
	}

	public void setHorta_name(String horta_name)
	{
		this.horta_name = horta_name;
	}

	public int getHortaSonId()
	{
		return hortaSonId;
	}

	public void setHortaSonId(int hortaSonId)
	{
		this.hortaSonId = hortaSonId;
	}

	public String getHorta_sonName()
	{
		return horta_sonName;
	}

	public void setHorta_sonName(String horta_sonName)
	{
		this.horta_sonName = horta_sonName;
	}

	public String getVipGrade()
	{
		return vipGrade;
	}

	public void setVipGrade(String vipGrade)
	{
		this.vipGrade = vipGrade;
	}

	public int getOnlineTime()
	{
		return onlineTime;
	}

	public void setOnlineTime(int onlineTime)
	{
		this.onlineTime = onlineTime;
	}

	public String getWjGrade()
	{
		return wjGrade;
	}

	public void setWjGrade(String wjGrade)
	{
		this.wjGrade = wjGrade;
	}


	public String getGiveGoods()
	{
		return giveGoods;
	}

	public void setGiveGoods(String giveGoods)
	{
		this.giveGoods = giveGoods;
	}
	
	
	
}
