package com.lw.service.pet;

import com.lw.dao.pet.skill.PetSkillDisplayDao;

public class PetSkillDisplayService
{
	// ƴ�ӳ��＼����ʾ
	public String getPetSkillDisplay(int skill_id)
	{
		PetSkillDisplayDao dao = new PetSkillDisplayDao();
		String name = dao.getPlayerPetSkillName(skill_id);
		String display = dao.getPlayerPetSkillDisplay(skill_id);
		StringBuffer sb = new StringBuffer();
		sb.append("��������:" + name + "<br/>");
		sb.append("��������:" + display + "<br/>");
		return sb.toString();
	}
}
