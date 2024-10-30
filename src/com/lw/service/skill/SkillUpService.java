package com.lw.service.skill;

import com.ls.ben.cache.staticcache.skill.SkillCache;
import com.ls.ben.dao.info.skill.PlayerSkillDao;
import com.ls.ben.vo.info.skill.PlayerSkillVO;
import com.ls.model.property.RoleSkillInfo;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.skill.SkillService;
import com.lw.dao.skill.SkillUpDao;

public class SkillUpService
{

	/** ******�жϼ����Ƿ�����*********** */
	public boolean ifAddSkill(PlayerSkillVO skill, int p_pk)
	{
		SkillService ss = new SkillService();
		int sk_id = skill.getSkId();
		int current_sleight = skill.getSkSleight();
		int next_sleight = ss.getNextLevelSleight(sk_id);

		if (next_sleight != 0 && current_sleight != 0
				&& current_sleight >= next_sleight)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/** ******�жϼ����Ƿ�����*********** */
	public boolean ifAddSkill(int skId, int p_pk)
	{
		SkillUpDao dao = new SkillUpDao();
		int current_sleight = dao.getPlayerSleight(skId, p_pk);
		int next_sleight = SkillCache.getById(skId).getSkNextSleight();

		if (next_sleight != 0 && current_sleight != 0
				&& current_sleight >= next_sleight)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/** ������������ */
	public void addSkillLevelUp(int p_pk, PlayerSkillVO skill)
	{
		RoleEntity role_info = RoleService.getRoleInfoById(p_pk+"");
		
		if( role_info==null || role_info.isOnline()==false )
		{
			return;//�����Ҳ��������ܲ�����
		}
		
		SkillUpDao dao = new SkillUpDao();
		int sk_group = skill.getSkGroup();
		int s_pk = skill.getSPk();
		int sk_sleight = skill.getSkSleight();		
		
		if (ifAddSkill(skill, p_pk) == true && s_pk != 0)
		{	
			int sk_next_id = dao.getNextSkill(sk_sleight, sk_group);
			dao.updateSkillName(sk_next_id, sk_group, p_pk);
			//dao.updateSkillID(sk_next_id, sk_group, p_pk);
			dao.changeName(s_pk, sk_next_id);
			
			// ���ڴ����Ƴ�ԭ���ļ���,ת������������ļ���
			PlayerSkillDao playerSkillDao = new PlayerSkillDao();
			PlayerSkillVO playerSkillVO = null;
			playerSkillVO = playerSkillDao.getById(skill.getSPk());
			RoleSkillInfo roleSkillInfo = role_info.getRoleSkillInfo();
			
//			roleSkillInfo.removeSkillFromPlayer(s_pk);
//			roleSkillInfo.addSkillToPlayer(playerSkillVO);
			
			roleSkillInfo.updateSkillName(p_pk,s_pk, playerSkillVO.getSkName());
			
		}
	}

	/** ѧϰ��������� */
	public void studyLiveSkill(int p_pk, int sk_id, int sk_next_id)
	{
		SkillUpDao dao = new SkillUpDao();
		int sk_group = dao.getSkGroup(sk_id, p_pk);
		dao.updateSkillName(sk_next_id, sk_group, p_pk);
		//dao.updateSkillID(sk_next_id, sk_group, p_pk);
	}

	/** �ж�����Ƿ����ʹ�øü����� */
	public boolean isPlayerHaverThisSkill(int p_pk, int sk_id)
	{
		SkillUpDao dao = new SkillUpDao();
		int sk_group = dao.getSkGroup(sk_id, p_pk);
		if (sk_group != 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
