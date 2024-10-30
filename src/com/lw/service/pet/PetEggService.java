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
		// ���ɳ��������Ϣ
		PetDAO petDAO = new PetDAO();
		PetVO pet = (PetVO) petDAO.getPetViewByPetID(pet_id);
		if (pet != null)
		{
			String[] unit = prop.getPropOperate2().split(",");
			int pet_level = Integer.parseInt(unit[0]);// ����ȼ�
			double pet_grow = Double.parseDouble(unit[1]);// ����ɳ�
			int pet_skill1 = Integer.parseInt(unit[2]);// ���＼��1
			int pet_skill2 = Integer.parseInt(unit[3]);// ���＼��2
			int pet_skill3 = Integer.parseInt(unit[4]);// ���＼��3
			int pet_skill4 = Integer.parseInt(unit[5]);// ���＼��4
			int pet_skill5 = Integer.parseInt(unit[6]);// ���＼��5

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

			/** ����ͼƬ */
			String petPic = pet.getPetImg();
			/** �������� */
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

			/** �����ǳ� */
			String petNickname = petName;
			/** �����۸� */
			String petSale = petShapeVO.getShapeSale() + "";
			/** �������Խ�=1��ľ=2��ˮ=3����=4����=5 */
			String petWx = pet.getPetWx();
			/** ��������ֵ */
			String petWxValue = pet.getPetWxValue();
			/** ����* */
			String petLife = pet.getPetLonge() + "";
			/** ���� �Ƿ����Ȼ���� */
			String petType = pet.getPetType() + "";
			/** �Ƿ�������:1��ʾ��ս��״̬��0��ʾ�� */
			int petIsBring = 0;
			/** ƣ�Ͷ�0-100,��ս״̬������ƣ�Ͷȣ�һ��Сʱ��10�� */
			String petFatigue = pet.getPetFatigue() + "";
			/** �������� */
			String petLonge = pet.getPetLonge() + "";
			/** ������������ʹ�ô��� */
			String longeNumber = pet.getLongeNumber() + "";
			/** ���������Ѿ�ʹ�ô��� */
			int longeNumberOk = 0;
			/** �������������ѧϰ���ٸ����� */
			String skillControl = pet.getSkillControl() + "";

			/** ���� */
			String petExp = petShapeVO.getShapeBenExperience();
			/** �¼�����ﵽ��һ����Ҫ�ľ��� */
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
			hint = "��������ʧ��";
		}
		return hint;

	}
}
