package com.lw.service.skill;

import java.util.ArrayList;
import java.util.List;

import com.ben.dao.pet.PetDAO;
import com.ben.dao.petinfo.PetInfoDAO;
import com.ben.vo.pet.pet.PetVO;
import com.ben.vo.petinfo.PetInfoVO;
import com.ls.ben.cache.staticcache.pet.PetSkillCache;
import com.ls.ben.dao.info.pet.PetInfoDao;
import com.ls.ben.dao.info.pet.PetSkillDao;
import com.ls.ben.vo.info.pet.PetSkillControlVO;
import com.ls.pub.util.MathUtil;
import com.ls.web.service.pet.PetService;
import com.lw.dao.pet.skill.PetSkillLevelUpDao;

public class PetSkillLevelUpService
{
	/** 得到宠物身上技能的下一级技能ID */
	public int getNextSkId(int pet_skill_id)
	{
		PetSkillLevelUpDao dao = new PetSkillLevelUpDao();
		PetSkillCache petSkillCache = new PetSkillCache();
		int gp = petSkillCache.getGroupID(pet_skill_id);
		int lv = petSkillCache.getPetSkLevel(pet_skill_id) + 1;
		int id = dao.getSkillIDByLevel(gp, lv);
		return id;
	}

	/** 得到宠物能学到的1级技能 */
	public List<Integer> getPetAllSkillLvOne(int pet_pk)
	{
		PetSkillLevelUpDao pdao = new PetSkillLevelUpDao();
		int pet_id = pdao.getPetPetidByPetpk(pet_pk);
		List<Integer> list = new ArrayList<Integer>();
		PetDAO dao = new PetDAO();
		PetVO vo = dao.getPetViewByPetID(pet_id);
		if (vo.getPetSkillOne() != 0)
		{
			int x = vo.getPetSkillOne();
			list.add(x);
		}
		if (vo.getPetSkillTwo() != 0)
		{
			int x = vo.getPetSkillTwo();
			list.add(x);
		}
		if (vo.getPetSkillThree() != 0)
		{
			int x = vo.getPetSkillThree();
			list.add(x);
		}
		if (vo.getPetSkillFour() != 0)
		{
			int x = vo.getPetSkillFour();
			list.add(x);
		}
		if (vo.getPetSkillFive() != 0)
		{
			int x = vo.getPetSkillFive();
			list.add(x);
		}

		return list;

	}

	/** 得到宠物控制表里能发出的技能* */
	public List<Integer> getPetSkillControl(int pet_id)
	{
		PetSkillLevelUpDao pdao = new PetSkillLevelUpDao();
		List<Integer> list = pdao.getPetSkillControl(pet_id);
		return list;
	}

	/** 判断宠物能悟出的一级技能 */
	public int getPetSkillLvOne(int pet_pk)
	{
		PetSkillLevelUpDao dao = new PetSkillLevelUpDao();
		PetSkillCache petSkillCache = new PetSkillCache();
		List<Integer> list = getPetAllSkillLvOne(pet_pk);

		for (int i = 0; i < list.size(); i++)
		{
			int skill_id = list.get(i);
			if (dao.getPetSkOne(pet_pk) == 0 && dao.getPetSkTwo(pet_pk) == 0
					&& dao.getPetSkThree(pet_pk) == 0
					&& dao.getPetSkFour(pet_pk) == 0
					&& dao.getPetSkFive(pet_pk) == 0)
			{
				return skill_id;
			}
			else
			{

				if (petSkillCache.getGroupID(skill_id) != petSkillCache
						.getGroupID(dao.getPetSkOne(pet_pk))
						&& petSkillCache.getGroupID(skill_id) != petSkillCache
								.getGroupID(dao.getPetSkTwo(pet_pk))
						&& petSkillCache.getGroupID(skill_id) != petSkillCache
								.getGroupID(dao.getPetSkThree(pet_pk))
						&& petSkillCache.getGroupID(skill_id) != petSkillCache
								.getGroupID(dao.getPetSkFour(pet_pk))
						&& petSkillCache.getGroupID(skill_id) != petSkillCache
								.getGroupID(dao.getPetSkFive(pet_pk)))
				{
					return skill_id;
				}
			}
		}
		return -1;
	}

	/** 判断宠物技能是否为宠物已有技能的技能组里的技能 TRUE为有 false为没有 */
	public boolean petSkillByGroup(int pet_pk, int pet_skill_id)
	{
		PetSkillLevelUpDao dao = new PetSkillLevelUpDao();
		PetSkillCache petSkillCache = new PetSkillCache();

		if (petSkillCache.getGroupID(pet_skill_id) != petSkillCache
				.getGroupID(dao.getPetSkOne(pet_pk))
				&& petSkillCache.getGroupID(pet_skill_id) != petSkillCache
						.getGroupID(dao.getPetSkTwo(pet_pk))
				&& petSkillCache.getGroupID(pet_skill_id) != petSkillCache
						.getGroupID(dao.getPetSkThree(pet_pk))
				&& petSkillCache.getGroupID(pet_skill_id) != petSkillCache
						.getGroupID(dao.getPetSkFour(pet_pk))
				&& petSkillCache.getGroupID(pet_skill_id) != petSkillCache
						.getGroupID(dao.getPetSkFive(pet_pk)))
		{
			return false;
		}
		return true;
	}

	/** 宠物战斗后的领悟到新技能和技能升级 */
	public String petSkillLevelUp(int pet_pk)
	{
		if (MathUtil.PercentageRandomByParamdouble(2.5, 100))
		{
			PetInfoDAO infodao = new PetInfoDAO();
			PetInfoVO petInfoVO = infodao.getPetInfoView(pet_pk + "");
			return petgetNewSkill(petInfoVO);
		}
		return "";
	}

	/** 宠物学习新技能或者已有技能升級 */
	public String petgetNewSkill(PetInfoVO petInfoVO)
	{
		int skill_lv = 4;// 宠物技能等级的最大值
		PetSkillLevelUpDao dao = new PetSkillLevelUpDao();
		// PetSkillDao ps = new PetSkillDao();
		PetSkillCache petSkillCache = new PetSkillCache();
		PetService petService = new PetService();

		int sk1 = petInfoVO.getPetSkillOne();
		int lv1 = petSkillCache.getPetSkLevel(sk1) + 1;
		int gp1 = petSkillCache.getGroupID(sk1);

		if (sk1 != 0
				&& lv1 != 1
				&& lv1 < skill_lv
				&& petInfoVO.getPetGrade() >= dao
						.getSkillGradeByLevel(gp1, lv1))
		{
			String name = petSkillCache.getName(sk1);
			dao.updatePetSkOne(petInfoVO.getPetPk(), dao.getSkillIDByLevel(gp1,
					lv1));
			return "<br/>您宠物的技能" + name + "升级了";

		}
		else
		{
			int petSkillLvOne = getPetSkillLvOne(petInfoVO.getPetPk()); // 如果没有技能,则获取的新技能id
			if (sk1 == 0 && petSkillLvOne != -1)
			{
				String name = petSkillCache.getName(petSkillLvOne);
				dao.updatePetSkOne(petInfoVO.getPetPk(), petSkillLvOne);
				petService.addSkillFlag(petInfoVO.getPetPk() + "");
				return "<br/>您的宠物悟出了技能:" + name;
			}
		}

		int sk2 = petInfoVO.getPetSkillTwo();
		int lv2 = petSkillCache.getPetSkLevel(sk2) + 1;
		int gp2 = petSkillCache.getGroupID(sk2);

		if (sk2 != 0
				&& lv2 != 1
				&& lv2 < skill_lv
				&& petInfoVO.getPetGrade() >= dao
						.getSkillGradeByLevel(gp2, lv2))
		{
			String name = petSkillCache.getName(sk2);
			dao.updatePetSkTwo(petInfoVO.getPetPk(), dao.getSkillIDByLevel(gp2,
					lv2));
			return "<br/>您宠物的技能" + name + "升级了";

		}
		else
		{
			int petSkillLvOne = getPetSkillLvOne(petInfoVO.getPetPk()); // 如果没有技能,则获取的新技能id
			if (sk2 == 0 && petSkillLvOne != -1)
			{
				String name = petSkillCache.getName(petSkillLvOne);
				dao.updatePetSkTwo(petInfoVO.getPetPk(), petSkillLvOne);
				petService.addSkillFlag(petInfoVO.getPetPk() + "");
				return "<br/>您的宠物悟出了技能:" + name;
			}
		}

		int sk3 = petInfoVO.getPetSkillThree();
		int lv3 = petSkillCache.getPetSkLevel(sk3) + 1;
		int gp3 = petSkillCache.getGroupID(sk3);
		if (sk3 != 0
				&& lv3 != 1
				&& lv3 < skill_lv
				&& petInfoVO.getPetGrade() >= dao
						.getSkillGradeByLevel(gp3, lv3))
		{
			String name = petSkillCache.getName(sk3);
			dao.updatePetSkThree(petInfoVO.getPetPk(), dao.getSkillIDByLevel(
					gp3, lv3));
			return "<br/>您宠物的技能" + name + "升级了";

		}
		else
		{
			int petSkillLvOne = getPetSkillLvOne(petInfoVO.getPetPk()); // 如果没有技能,则获取的新技能id
			if (sk3 == 0 && petSkillLvOne != -1)
			{
				String name = petSkillCache.getName(petSkillLvOne);
				dao.updatePetSkThree(petInfoVO.getPetPk(), petSkillLvOne);
				petService.addSkillFlag(petInfoVO.getPetPk() + "");
				return "<br/>您的宠物悟出了技能:" + name;
			}
		}

		int sk4 = petInfoVO.getPetSkillFour();
		int lv4 = petSkillCache.getPetSkLevel(sk4) + 1;
		int gp4 = petSkillCache.getGroupID(sk4);
		if (sk4 != 0
				&& lv4 != 1
				&& lv4 < skill_lv
				&& petInfoVO.getPetGrade() >= dao
						.getSkillGradeByLevel(gp4, lv4))
		{
			String name = petSkillCache.getName(sk4);
			dao.updatePetSkFour(petInfoVO.getPetPk(), dao.getSkillIDByLevel(
					gp4, lv4));
			return "<br/>您宠物的技能" + name + "升级了";

		}
		else
		{
			int petSkillLvOne = getPetSkillLvOne(petInfoVO.getPetPk()); // 如果没有技能,则获取的新技能id
			if (sk4 == 0 && petSkillLvOne != -1)
			{
				String name = petSkillCache.getName(petSkillLvOne);
				dao.updatePetSkFour(petInfoVO.getPetPk(), petSkillLvOne);
				petService.addSkillFlag(petInfoVO.getPetPk() + "");
				return "<br/>您的宠物悟出了技能:" + name;
			}
		}

		int sk5 = petInfoVO.getPetSkillFive();
		int lv5 = petSkillCache.getPetSkLevel(sk5) + 1;
		int gp5 = petSkillCache.getGroupID(sk5);
		if (sk5 != 0
				&& lv5 != 1
				&& lv5 < skill_lv
				&& petInfoVO.getPetGrade() >= dao
						.getSkillGradeByLevel(gp5, lv5))
		{
			String name = petSkillCache.getName(sk5);
			dao.updatePetSkFive(petInfoVO.getPetPk(), dao.getSkillIDByLevel(
					gp5, lv5));
			return "<br/>您宠物的技能" + name + "升级了";

		}
		else
		{
			int petSkillLvOne = getPetSkillLvOne(petInfoVO.getPetPk()); // 如果没有技能,则获取的新技能id
			if (sk5 == 0 && petSkillLvOne != -1)
			{
				String name = petSkillCache.getName(petSkillLvOne);
				dao.updatePetSkFive(petInfoVO.getPetPk(), petSkillLvOne);
				petService.addSkillFlag(petInfoVO.getPetPk() + "");
				return "<br/>您的宠物悟出了技能:" + name;
			}
		}
		return "";
	}

	/** 宠物随机忘却一级技能 */
	public String petSkillForget(int pet_pk)
	{
		if (MathUtil.isAppearByPercentage(20))
		{
			return petDelSkillOne(pet_pk);
		}
		return "";
	}

	/** 宠物忘记技能 */
	public String petDelSkillOne(int pet_pk)
	{
		PetSkillLevelUpDao dao = new PetSkillLevelUpDao();
		PetSkillDao ps = new PetSkillDao();
		PetSkillCache petSkillCache = new PetSkillCache();

		int sk1 = dao.getPetSkOne(pet_pk);
		int lv1 = petSkillCache.getPetSkLevel(sk1);
		if (lv1 == 1)
		{
			String name = petSkillCache.getName(sk1);
			dao.deletePetSkOne(pet_pk);
			return "<br/>您的宠物忘记了技能:" + name;

		}

		int sk2 = dao.getPetSkTwo(pet_pk);
		int lv2 = petSkillCache.getPetSkLevel(sk2);
		if (lv2 == 1)
		{
			String name = petSkillCache.getName(sk2);
			dao.deletePetSkTwo(pet_pk);
			return "<br/>您的宠物忘记了技能:" + name;

		}

		int sk3 = dao.getPetSkThree(pet_pk);
		int lv3 = petSkillCache.getPetSkLevel(sk3);
		if (lv3 == 1)
		{
			String name = petSkillCache.getName(sk3);
			dao.deletePetSkThree(pet_pk);
			return "<br/>您的宠物忘记了技能:" + name;

		}

		int sk4 = dao.getPetSkFour(pet_pk);
		int lv4 = petSkillCache.getPetSkLevel(sk4);
		if (lv4 == 1)
		{
			String name = petSkillCache.getName(sk4);
			dao.deletePetSkFour(pet_pk);
			return "<br/>您的宠物忘记了技能:" + name;

		}

		int sk5 = dao.getPetSkFive(pet_pk);
		int lv5 = petSkillCache.getPetSkLevel(sk5);
		if (lv5 == 1)
		{
			String name = petSkillCache.getName(sk5);
			dao.deletePetSkFive(pet_pk);
			return "<br/>您的宠物忘记了技能:" + name;
		}
		return "";
	}

	/** 宠物学习技能 */
	public String petAddSkill(int pet_pk, int pet_skill_id)
	{
		PetSkillLevelUpDao dao = new PetSkillLevelUpDao();
		PetInfoDao infodao = new PetInfoDao();
		// PetSkillDao ps = new PetSkillDao();
		PetSkillCache petSkillCache = new PetSkillCache();
		com.ls.ben.vo.info.pet.PetInfoVO infovo = infodao.getPet(pet_pk);

		if (petSkillCache.getPetSkLevel(pet_skill_id) < 2)
		{
			if (infovo.getPetSkillOne() == 0
					&& petSkillByGroup(pet_pk, pet_skill_id) == false)
			{
				dao.updatePetSkOne(pet_pk, pet_skill_id);
				return "您的宠物学会了技能:" + petSkillCache.getName(pet_skill_id);
			}

			if (infovo.getPetSkillTwo() == 0
					&& petSkillByGroup(pet_pk, pet_skill_id) == false)
			{
				dao.updatePetSkTwo(pet_pk, pet_skill_id);
				return "您的宠物学会了技能:" + petSkillCache.getName(pet_skill_id);
			}
			if (infovo.getPetSkillThree() == 0
					&& petSkillByGroup(pet_pk, pet_skill_id) == false)
			{
				dao.updatePetSkThree(pet_pk, pet_skill_id);
				return "您的宠物学会了技能:" + petSkillCache.getName(pet_skill_id);
			}
			if (infovo.getPetSkillFour() == 0
					&& petSkillByGroup(pet_pk, pet_skill_id) == false)
			{
				dao.updatePetSkFour(pet_pk, pet_skill_id);
				return "您的宠物学会了技能:" + petSkillCache.getName(pet_skill_id);
			}
			if (infovo.getPetSkillFive() == 0
					&& petSkillByGroup(pet_pk, pet_skill_id) == false)
			{
				dao.updatePetSkFive(pet_pk, pet_skill_id);
				return "您的宠物学会了技能:" + petSkillCache.getName(pet_skill_id);
			}
		}
		else
		{
			if (petSkillCache.getGroupID(pet_skill_id) == petSkillCache
					.getGroupID(dao.getPetSkOne(pet_pk))
					&& petSkillCache.getPetSkLevel(pet_skill_id) == (petSkillCache
							.getPetSkLevel(infovo.getPetSkillOne()) + 1))
			{
				dao.updatePetSkOne(pet_pk, pet_skill_id);
				return "您的宠物学会了技能:" + petSkillCache.getName(pet_skill_id);

			}
			if (petSkillCache.getGroupID(pet_skill_id) == petSkillCache
					.getGroupID(dao.getPetSkTwo(pet_pk))
					&& petSkillCache.getPetSkLevel(pet_skill_id) == (petSkillCache
							.getPetSkLevel(infovo.getPetSkillTwo()) + 1))
			{
				dao.updatePetSkTwo(pet_pk, pet_skill_id);
				return "您的宠物学会了技能:" + petSkillCache.getName(pet_skill_id);

			}
			if (petSkillCache.getGroupID(pet_skill_id) == petSkillCache
					.getGroupID(dao.getPetSkThree(pet_pk))
					&& petSkillCache.getPetSkLevel(pet_skill_id) == (petSkillCache
							.getPetSkLevel(infovo.getPetSkillThree()) + 1))
			{
				dao.updatePetSkThree(pet_pk, pet_skill_id);
				return "您的宠物学会了技能:" + petSkillCache.getName(pet_skill_id);

			}
			if (petSkillCache.getGroupID(pet_skill_id) == petSkillCache
					.getGroupID(dao.getPetSkFour(pet_pk))
					&& petSkillCache.getPetSkLevel(pet_skill_id) == (petSkillCache
							.getPetSkLevel(infovo.getPetSkillFour()) + 1))
			{
				dao.updatePetSkFour(pet_pk, pet_skill_id);
				return "您的宠物学会了技能:" + petSkillCache.getName(pet_skill_id);

			}
			if (petSkillCache.getGroupID(pet_skill_id) == petSkillCache
					.getGroupID(dao.getPetSkFive(pet_pk))
					&& petSkillCache.getPetSkLevel(pet_skill_id) == (petSkillCache
							.getPetSkLevel(infovo.getPetSkillFive()) + 1))
			{
				dao.updatePetSkFive(pet_pk, pet_skill_id);
				return "您的宠物学会了技能:" + petSkillCache.getName(pet_skill_id);
			}

			else
			{
				return "抱歉学习技能失败";
			}

		}
		return "抱歉学习技能失败";
	}

	/** 根据技能ID判断宠物是否有该技能组 true为有 false为没有 */
	public boolean getPetGpBySk(int pet_skill_id, int pet_pk)
	{
		PetSkillLevelUpDao dao = new PetSkillLevelUpDao();
		PetSkillCache petSkillCache = new PetSkillCache();
		List list = dao.getPetControlGroup(dao.getPetPetidByPetpk(pet_pk));
		if (list != null && list.size() != 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				PetSkillControlVO vo = (PetSkillControlVO) list.get(i);
				if (petSkillCache.getGroupID(pet_skill_id) == vo
						.getPetSkGroup())
				{
					return true;
				}

			}

		}
		return false;
	}
}
