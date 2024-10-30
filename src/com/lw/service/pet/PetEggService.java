package com.lw.service.pet;

import java.text.DecimalFormat;

import com.ben.dao.pet.PetDAO;
import com.ben.dao.petinfo.PetInfoDAO;
import com.ben.vo.pet.pet.PetShapeVO;
import com.ben.vo.pet.pet.PetVO;
import com.ls.ben.vo.goods.prop.PropVO;

public class PetEggService
{
	public String creatPetByEgg(PropVO prop, int p_pk)
	{
		String hint = null;

		DecimalFormat dfs = new DecimalFormat("0");
		int pet_id = Integer.parseInt(prop.getPropOperate1());
		// 生成宠物基本信息
		PetDAO petDAO = new PetDAO();
		PetVO pet = (PetVO) petDAO.getPetViewByPetID(pet_id);
		if (pet != null)
		{
			String[] unit = prop.getPropOperate2().split(",");
			int pet_level = Integer.parseInt(unit[0]);// 宠物等级
			double pet_grow = Double.parseDouble(unit[1]);// 宠物成长
			int pet_skill1 = Integer.parseInt(unit[2]);// 宠物技能1
			int pet_skill2 = Integer.parseInt(unit[3]);// 宠物技能2
			int pet_skill3 = Integer.parseInt(unit[4]);// 宠物技能3
			int pet_skill4 = Integer.parseInt(unit[5]);// 宠物技能4
			int pet_skill5 = Integer.parseInt(unit[6]);// 宠物技能5

			double cc = pet_grow * pet_level / 3;
			double gongji = 0.0;
			// double gongjix=0.0;
			if (pet_level < 9)
			{
				gongji = (10 + pet_level * 5) + 4 * pet_grow;
			}
			else
			{
				gongji = Math.pow(pet_level, 0.5) * 20 + pet_grow
						* (3 * pet_level - pet_level) / 2;
			}

			int petGjDa = Integer.parseInt(dfs.format(gongji))
					+ Integer.parseInt(dfs.format(cc));
			int petGjXiao = petGjDa * 95 / 100;

			PetShapeVO petShapeVO = (PetShapeVO) petDAO.getPetShapeView(pet
					.getPetType(), pet_level);

			/** 宠物图片 */
			String petPic = pet.getPetImg();
			/** 宠物名称 */
			String petName = null;
			if (pet_skill1 == 0 && pet_skill2 == 0 && pet_skill3 == 0
					&& pet_skill4 == 0 && pet_skill5 == 0)
			{
				petName = pet.getPetName();
			}
			else
			{
				petName = pet.getPetName() + "*";
			}

			/** 宠物昵称 */
			String petNickname = petName;
			/** 卖出价格 */
			String petSale = petShapeVO.getShapeSale() + "";
			/** 五行属性金=1，木=2，水=3，火=4，土=5 */
			String petWx = pet.getPetWx();
			/** 五行属性值 */
			String petWxValue = pet.getPetWxValue();
			/** 寿命* */
			String petLife = pet.getPetLonge() + "";
			/** 升级 是否可自然升级 */
			String petType = pet.getPetType() + "";
			/** 是否在身上:1表示在战斗状态，0表示否 */
			int petIsBring = 0;
			/** 疲劳度0-100,出战状态下增加疲劳度，一个小时加10点 */
			String petFatigue = pet.getPetFatigue() + "";
			/** 宠物寿命 */
			String petLonge = pet.getPetLonge() + "";
			/** 增加寿命道具使用次数 */
			String longeNumber = pet.getLongeNumber() + "";
			/** 寿命道具已经使用次数 */
			int longeNumberOk = 0;
			/** 这个宠物最多可以学习多少个技能 */
			String skillControl = pet.getSkillControl() + "";

			/** 经验 */
			String petExp = petShapeVO.getShapeBenExperience();
			/** 下级经验达到下一级需要的经验 */
			int petXiaExps = (int) (Integer.parseInt(petShapeVO
					.getShapeXiaExperience().trim()) * pet_grow);
			String petXiaExp = petXiaExps + "";

			PetInfoDAO petInfoDAO = new PetInfoDAO();
			petInfoDAO.getPetInfoAdd(p_pk + "", pet_id + "", petName,
					petNickname, pet_level + "", petExp, petXiaExp, petGjXiao,
					petGjDa, petSale, pet_grow + "", petWx, petWxValue,
					pet_skill1 + "", pet_skill2 + "", pet_skill3 + "",
					pet_skill4 + "", pet_skill5 + "", petLife, petType,
					petIsBring, petFatigue, petLonge, longeNumber,
					longeNumberOk, skillControl, pet.getPetType(), petPic, pet
							.getPetViolenceDorp());
			hint = petName;
		}
		else
		{
			hint = "创建宠物失败";
		}
		return hint;

	}
}
