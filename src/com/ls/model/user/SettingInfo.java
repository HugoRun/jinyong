package com.ls.model.user;

import com.pm.dao.setting.SettingDao;
import com.pm.vo.setting.SettingVO;

/**
 * 功能：角色的系统设置信息
 * @author ls
 * Apr 8, 2009
 * 9:48:26 AM
 */
public class SettingInfo extends UserBaseWithSave
{
	/** id */
	private int id;
	/** 物品图片开关 */
	private short goodsPic;
	/** 角色形象图 */
	private short personPic;
	/**  npc怪物图开关 */
	private short npcPic;
	/**  宠物图开关 */
	private short petPic;
	/**  npc人物图开关 */
	private short operatePic;
	/**  交易控制开关 */
	private short dealControl;
	/** 战斗时npc血条显示位置控制 */
	private short npcHpUp;
	
	/** 公告聊天开关 */
	private short publicChat;
	/** 阵营聊天 */
	private short campChat;
	/** 队伍聊天 */
	private short duiwuChat;
	/** 帮派聊天 */
	private short tongChat;
	/** 密语聊天 */
	private short secretChat;
	/**首页聊天快捷输入栏*/
	private short indexChat;
	/**自動打怪的設置*/
	private AutoAttackSetting autoAttackSetting;
	
	public SettingInfo( int p_pk )
	{
		super(p_pk);
		
		SettingVO settingInfo = new SettingDao().getSettingInfo(p_pk+"");
	
		id = settingInfo.getSettingId();
		goodsPic = (short) settingInfo.getGoodsPic();
		personPic = (short) settingInfo.getPersonPic();
		npcPic = (short) settingInfo.getNpcPic();
		petPic = (short) settingInfo.getPetPic();
		operatePic = (short) settingInfo.getOperatePic();
		dealControl = (short) settingInfo.getDealControl();
		npcHpUp = (short) settingInfo.getNpcHpUp();
		
		publicChat = (short) settingInfo.getPublicComm();
		campChat = (short) settingInfo.getCampComm();
		duiwuChat = (short) settingInfo.getDuiwuComm();
		tongChat = (short) settingInfo.getTongComm();
		secretChat = (short) settingInfo.getSecretComm();
		indexChat = (short) settingInfo.getIndexComm();
		
		autoAttackSetting = new AutoAttackSetting();
		
		super.initPersistenceEntity("s_setting_info","setting_id",id+"");
	}


	public void updateGoodsPic()
	{
		this.setGoodsPic((short) -goodsPic);
	}


	public void updatePersonPic()
	{
		this.setPersonPic((short) -personPic);
	}


	public void updateNpcPic()
	{
		this.setNpcPic((short) -npcPic);
	}


	public void updatePetPic()
	{
		this.setPetPic((short) -petPic);
	}


	public void updateOperatePic()
	{
		this.setOperatePic((short) -operatePic);
	}


	public void updateDealControl()
	{
		this.setDealControl((short) -dealControl);
	}


	public void updateNpcHpUp()
	{
		this.setNpcHpUp((short) -npcHpUp);
	}


	public void updatePublicChat()
	{
		this.setPublicChat((short) -publicChat);
	}


	public void updateCampChat()
	{
		this.setCampChat((short) -campChat);
	}


	public void updateDuiwuChat()
	{
		this.setDuiwuChat((short) -duiwuChat);
	}


	public void updateTongChat()
	{
		this.setTongChat((short) -tongChat);
	}


	public void updateSecretChat()
	{
		this.setSecretChat((short) -secretChat);
	}


	public void updateIndexChat()
	{
		this.setIndexChat((short) -indexChat);
	}


	public int getGoodsPic()
	{
		return goodsPic;
	}

	public int getPersonPic()
	{
		return personPic;
	}

	public int getNpcPic()
	{
		return npcPic;
	}

	public int getPetPic()
	{
		return petPic;
	}

	public int getOperatePic()
	{
		return operatePic;
	}

	public int getDealControl()
	{
		return dealControl;
	}

	public int getNpcHpUp()
	{
		return npcHpUp;
	}

	public int getPublicChat()
	{
		return publicChat;
	}

	public int getCampChat()
	{
		return campChat;
	}

	public int getDuiwuChat()
	{
		return duiwuChat;
	}

	public int getTongChat()
	{
		return tongChat;
	}

	public int getSecretChat()
	{
		return secretChat;
	}

	public int getIndexChat()
	{
		return indexChat;
	}


	private void setGoodsPic(short goodsPic)
	{
		this.goodsPic = goodsPic;
		uPartInfoTab.addPersistenceColumn("goods_pic", goodsPic+"");
	}


	private void setPersonPic(short personPic)
	{
		this.personPic = personPic;
		uPartInfoTab.addPersistenceColumn("person_pic", personPic+"");
	}


	private void setNpcPic(short npcPic)
	{
		this.npcPic = npcPic;
		uPartInfoTab.addPersistenceColumn("npc_pic", npcPic+"");
	}


	private void setPetPic(short petPic)
	{
		this.petPic = petPic;
		uPartInfoTab.addPersistenceColumn("pet_pic", petPic+"");
	}


	private void setOperatePic(short operatePic)
	{
		this.operatePic = operatePic;
		uPartInfoTab.addPersistenceColumn("operate_pic", operatePic+"");
	}


	private void setDealControl(short dealControl)
	{
		this.dealControl = dealControl;
		uPartInfoTab.addPersistenceColumn("deal_control", dealControl+"");
	}


	private void setNpcHpUp(short npcHpUp)
	{
		this.npcHpUp = npcHpUp;
		uPartInfoTab.addPersistenceColumn("npc_hp_position", npcHpUp+"");
		
	}


	private void setPublicChat(short publicChat)
	{
		this.publicChat = publicChat;
		uPartInfoTab.addPersistenceColumn("public_comm", publicChat+"");
	}


	private void setCampChat(short campChat)
	{
		this.campChat = campChat;
		uPartInfoTab.addPersistenceColumn("camp_comm", campChat+"");
	}


	private void setDuiwuChat(short duiwuChat)
	{
		this.duiwuChat = duiwuChat;
		uPartInfoTab.addPersistenceColumn("duiwu_comm", duiwuChat+"");
	}


	private void setTongChat(short tongChat)
	{
		this.tongChat = tongChat;
		uPartInfoTab.addPersistenceColumn("tong_comm", tongChat+"");
	}


	private void setSecretChat(short secretChat)
	{
		this.secretChat = secretChat;
		uPartInfoTab.addPersistenceColumn("secret_comm", secretChat+"");
	}


	private void setIndexChat(short indexChat)
	{
		this.indexChat = indexChat;
		uPartInfoTab.addPersistenceColumn("index_comm", indexChat+"");
	}


	public AutoAttackSetting getAutoAttackSetting()
	{
		return autoAttackSetting;
	}
}
