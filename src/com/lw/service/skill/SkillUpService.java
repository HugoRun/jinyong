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

	/** ******判断技能是否升级*********** */
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
	
	/** ******判断技能是否升级*********** */
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

	/** 技能升级过程 */
	public void addSkillLevelUp(int p_pk, PlayerSkillVO skill)
	{
		RoleEntity role_info = RoleService.getRoleInfoById(p_pk+"");
		
		if( role_info==null || role_info.isOnline()==false )
		{
			return;//如果玩家不在线则技能不升级
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
			
			// 从内存中移除原来的技能,转而增加升级后的技能
			PlayerSkillDao playerSkillDao = new PlayerSkillDao();
			PlayerSkillVO playerSkillVO = null;
			playerSkillVO = playerSkillDao.getById(skill.getSPk());
			RoleSkillInfo roleSkillInfo = role_info.getRoleSkillInfo();
			
//			roleSkillInfo.removeSkillFromPlayer(s_pk);
//			roleSkillInfo.addSkillToPlayer(playerSkillVO);
			
			roleSkillInfo.updateSkillName(p_pk,s_pk, playerSkillVO.getSkName());
			
		}
	}

	/** 学习生活技能流程 */
	public void studyLiveSkill(int p_pk, int sk_id, int sk_next_id)
	{
		SkillUpDao dao = new SkillUpDao();
		int sk_group = dao.getSkGroup(sk_id, p_pk);
		dao.updateSkillName(sk_next_id, sk_group, p_pk);
		//dao.updateSkillID(sk_next_id, sk_group, p_pk);
	}

	/** 判断玩家是否可以使用该技能书 */
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
