package com.lw.service.bonus;

import com.ls.ben.cache.staticcache.equip.EquipCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.model.equip.GameEquip;
import com.ls.model.user.RoleEntity;
import com.ls.pub.util.MoneyUtil;

public class BonusService
{
	// 是否可以领取该奖励
	public boolean ifPlayerGetThisBonus(RoleEntity roleInfo, String bonus)
	{
		return true;
	}

	// 玩家领取奖励
	public void getThisBonus(RoleEntity roleInfo, String bonus)
	{

	}

	// 奖励显示
	public String getBonusPrint(String bonus)
	{
		StringBuffer sb = new StringBuffer();
		String[] bonusprop = bonus.split(",");
		for (int i = 0; i < bonusprop.length; i++)
		{
			String[] prop = bonusprop[i].split("-");
			if (prop[0].equals("1"))
			{
				int equip_id = Integer.parseInt(prop[1]);
				GameEquip equip = EquipCache.getById(equip_id);
				sb.append(equip.getName() + "×" + prop[2]);
			}
			else if (prop[0].equals("4"))
			{
				int prop_id = Integer.parseInt(prop[1]);
				PropVO pvo =  PropCache.getPropById(prop_id);
				sb.append(pvo.getPropName() + "×" + prop[2]);
			}
			else if (prop[0].equals("5"))
			{
				sb.append("经验  " + prop[2]);
			}
			else
			{
				sb.append("银两"+ MoneyUtil.changeCopperToStr(prop[2]));
			}
			if (i != bonusprop.length - 1)
			{
				sb.append(",");
			}
		}
		return sb.toString();
	}

}
