package com.pm.service.setting;

import com.ls.model.user.RoleEntity;
import com.ls.model.user.SettingInfo;
import com.ls.pub.constant.SettingType;

public class SysSettingService
{

	/**
	 * �ı�ָ��ϵͳ�������õ����͵�����
	 * @param roleInfo
	 * @param type
	 */
	public void updateSetting(RoleEntity roleInfo, int type )
	{
		SettingInfo settingInfo = roleInfo.getSettingInfo();
		switch(type)
		{
			case SettingType.CAMPCHAT:
				settingInfo.updateCampChat();
				break;
			case SettingType.DEALCONTROL:
				settingInfo.updateDealControl();
				break;
			case SettingType.DUIWUCHAT:
				settingInfo.updateDuiwuChat();
				break;
			case SettingType.GOODSPIC:
				settingInfo.updateGoodsPic();
				break;
			case SettingType.INDEXCHAT:
				settingInfo.updateIndexChat();
				break;
			case SettingType.NPCHPUP:
				settingInfo.updateNpcHpUp();
				break;
			case SettingType.NPCPIC:
				settingInfo.updateNpcPic();
				break;
			case SettingType.OPERATEPIC:
				settingInfo.updateOperatePic();
				break;
			case SettingType.PERSONPIC:
				settingInfo.updatePersonPic();
				break;
			case SettingType.PETPIC:
				settingInfo.updatePetPic();
				break;
			case SettingType.PUBLICCHAT:
				settingInfo.updatePublicChat();
				break;
			case SettingType.SECRETCHAT:
				settingInfo.updateSecretChat();
				break;
			case SettingType.TONGCHAT:
				settingInfo.updateTongChat();
				break;
		}
	}

}
