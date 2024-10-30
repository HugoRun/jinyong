package com.lw.service.player;

import com.ls.ben.dao.goods.prop.PropDao;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.player.RoleService;

public class JueXueService
{
	// �жϾ�ѧ�Ƿ����ʹ��
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

	// �жϾ����Ƿ����ʹ��
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

	// �ж��Ƿ����ʹ��
	private boolean getIsUsedByProp(int p_pk)
	{
		// �鿴��������Ƿ��иõ���
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
	 * �ж��Ƿ����ʹ�ü��� ����1�������ʹ�ù��� ����2�����鲻��ʹ�� ����3����û�л�����
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

	// �õ� ���ɵ��ߵ�ID
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
