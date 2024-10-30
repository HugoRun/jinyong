package com.pm.vo.setting;

public class SettingVO
{
	/** 系统设置表 */
	private int settingId;
	/** 个人角色id */
	private int pPk;
	/** 物品图片开关 */
	private int goodsPic;
	/** 角色形象图 */
	private int personPic;
	/**  npc怪物图开关 */
	private int npcPic;
	/**  宠物图开关 */
	private int petPic;
	/**  npc人物图开关 */
	private int operatePic;
	/**  交易控制开关 */
	private int dealControl;
	/** npc血条显示 */
	private int npcHpUp;
	
	/** 公告聊天开关 */
	private int publicComm;
	/** 阵营聊天 */
	private int campComm;
	/** 队伍聊天 */
	private int duiwuComm;
	/** 帮派聊天 */
	private int tongComm;
	/** 密语聊天 */
	private int secretComm;
	/**首页聊天快捷输入栏*/
	private int indexComm;
	
	
	public int getIndexComm()
	{
		return indexComm;
	}
	public void setIndexComm(int indexComm)
	{
		this.indexComm = indexComm;
	}
	public int getNpcHpUp()
	{
		return npcHpUp;
	}
	public void setNpcHpUp(int npcHpUp)
	{
		this.npcHpUp = npcHpUp;
	}
	public int getSettingId()
	{
		return settingId;
	}
	public void setSettingId(int settingId)
	{
		this.settingId = settingId;
	}
	public int getPPk()
	{
		return pPk;
	}
	public void setPPk(int pk)
	{
		pPk = pk;
	}
	public int getGoodsPic()
	{
		return goodsPic;
	}
	public void setGoodsPic(int goodsPic)
	{
		this.goodsPic = goodsPic;
	}
	public int getPersonPic()
	{
		return personPic;
	}
	public void setPersonPic(int personPic)
	{
		this.personPic = personPic;
	}
	public int getNpcPic()
	{
		return npcPic;
	}
	public void setNpcPic(int npcPic)
	{
		this.npcPic = npcPic;
	}
	public int getPetPic()
	{
		return petPic;
	}
	public void setPetPic(int petPic)
	{
		this.petPic = petPic;
	}
	public int getOperatePic()
	{
		return operatePic;
	}
	public void setOperatePic(int operatePic)
	{
		this.operatePic = operatePic;
	}
	public int getDealControl()
	{
		return dealControl;
	}
	public void setDealControl(int dealControl)
	{
		this.dealControl = dealControl;
	}
	public int getPublicComm()
	{
		return publicComm;
	}
	public void setPublicComm(int publicComm)
	{
		this.publicComm = publicComm;
	}
	public int getCampComm()
	{
		return campComm;
	}
	public void setCampComm(int campComm)
	{
		this.campComm = campComm;
	}
	public int getDuiwuComm()
	{
		return duiwuComm;
	}
	public void setDuiwuComm(int duiwuComm)
	{
		this.duiwuComm = duiwuComm;
	}
	public int getTongComm()
	{
		return tongComm;
	}
	public void setTongComm(int tongComm)
	{
		this.tongComm = tongComm;
	}
	public int getSecretComm()
	{
		return secretComm;
	}
	public void setSecretComm(int secretComm)
	{
		this.secretComm = secretComm;
	}
		
}
