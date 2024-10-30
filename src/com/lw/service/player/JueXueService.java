package com.lw.service.player;

import com.ls.ben.dao.goods.prop.PropDao;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.player.RoleService;

public class JueXueService
{
	// 判断绝学是否可以使用
	private boolean getIsUsedByTime(int p_pk, int sk_id)
	{
		TimeControlService timeControlService = new TimeControlService();
		boolean skill_used = timeControlService.isUseable(p_pk, sk_id,
				TimeControlService.SKILL, 1);
		if (skill_used == true)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	// 判断经验是否可以使用
	private boolean getIsUsedByExp(int p_pk)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleEntity = roleService.getRoleInfoById(p_pk + "");
		if (roleEntity.getBasicInfo().getGrade() == GameConfig.getGradeUpperLimit())
		{
			return true;
		}
		else
		{
			long used_exp = (Long.parseLong(roleEntity.getBasicInfo()
					.getNextGradeExp()) - Long.parseLong(roleEntity
					.getBasicInfo().getPreGradeExp())) / 20;
			long user_exp = (Long.parseLong(roleEntity.getBasicInfo()
					.getCurExp()) - Long.parseLong(roleEntity.getBasicInfo()
					.getPreGradeExp()));
			if (user_exp > used_exp)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}

	// 判断是否可以使用
	private boolean getIsUsedByProp(int p_pk)
	{
		// 查看玩家身上是否有该道具
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		int tatol_prop_num = propGroupDao.getPropNumByByPropID(p_pk, GameConfig
				.getJuexuePropID());
		if (tatol_prop_num == 0)
		{
			return false;
		}
		else
		{
			return true;
		}

		// GameConfig.getJuexuePropType();
	}

	/***************************************************************************
	 * 判断是否可以使用技能 返回1代表今天使用过了 返回2代表经验不能使用 返回3代表没有化工丸
	 * 
	 **************************************************************************/
	public int userSkillJiayi(int p_pk, int sk_id)
	{
		boolean trem_one = getIsUsedByTime(p_pk, sk_id);
		boolean trem_two = getIsUsedByExp(p_pk);
		boolean trem_three = getIsUsedByProp(p_pk);
		if (trem_one == false)
		{
			return 1;
		}
		else
			if (trem_two == false)
			{
				return 2;
			}
			else
				if (trem_three == false)
				{
					return 3;
				}
				else
				{
					return -1;
				}
	}

	// 得到 生成道具的ID
	public int getJiayiProducePropID(RoleEntity roleInfo)
	{
		// String level = "1," + (roleInfo.getBasicInfo().getGrade() - 10);
		String level = "1,50";
		PropDao dao = new PropDao();
		int prop_id = dao.getPropIDByPropLevel(level, GameConfig
				.getJuexuePropType());
		return prop_id;
	}
}
