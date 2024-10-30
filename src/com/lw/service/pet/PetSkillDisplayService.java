package com.lw.service.pet;

import com.lw.dao.pet.skill.PetSkillDisplayDao;

public class PetSkillDisplayService
{
	// 拼接宠物技能显示
	public String getPetSkillDisplay(int skill_id)
	{
		PetSkillDisplayDao dao = new PetSkillDisplayDao();
		String name = dao.getPlayerPetSkillName(skill_id);
		String display = dao.getPlayerPetSkillDisplay(skill_id);
		StringBuffer sb = new StringBuffer();
		sb.append("技能名称:" + name + "<br/>");
		sb.append("技能描述:" + display + "<br/>");
		return sb.toString();
	}
}
