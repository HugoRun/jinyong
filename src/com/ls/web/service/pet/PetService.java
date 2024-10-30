package com.ls.web.service.pet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ben.dao.pet.PetDAO;
import com.ben.dao.petinfo.PetInfoDAO;
import com.ben.vo.pet.pet.PetShapeVO;
import com.ben.vo.pet.pet.PetVO;
import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.cache.staticcache.pet.PetCache;
import com.ls.ben.cache.staticcache.pet.PetSkillCache;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.dao.info.pet.PetInfoDao;
import com.ls.ben.dao.info.pet.PetSkillControlDao;
import com.ls.ben.dao.info.pet.PetSkillDao;
import com.ls.ben.vo.info.npc.NpcFighter;
import com.ls.ben.vo.info.partinfo.Fighter;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.ben.vo.info.pet.PetInfoVO;
import com.ls.ben.vo.info.pet.PetSkillControlVO;
import com.ls.ben.vo.info.pet.PetSkillVO;
import com.ls.model.property.RolePetInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.util.MathUtil;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.attack.AttackService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.log.LogService;
import com.ls.web.service.npc.NpcService;
import com.ls.web.service.player.FightService;
import com.ls.web.service.player.RoleService;
import com.pm.service.pic.PicService;

/**
 * ����:�������
 * 
 * @author ��˧
 * 
 * 9:37:59 AM
 */
public class PetService
{

	Logger logger = Logger.getLogger("log.service");

	/**
	 * ���﹥��npc
	 * 
	 * @param player
	 * @param npcs
	 */
	public void petAttackNpcs(Fighter player, List npcs)
	{
		if (player == null || npcs == null || npcs.size() == 0)
		{
			logger.debug("��ָ��");
			return;
		}

		RoleCache roleCache = new RoleCache();
		RoleEntity roleEntity = roleCache.getByPpk(player.getPPk() + "");
		RolePetInfo userPet = roleEntity.getRolePetInfo();
		PetInfoVO pet = userPet.getBringPet();

		// PetInfoDao petInfoDao = new PetInfoDao();
		// PetInfoVO pet = petInfoDao.getBringPetByPpk(player.getPPk());
		if (pet == null)
		{
			logger.debug("û�д�����");
			return;
		}
		else
		{
			logger.debug("��ɫ������μ�ս����");
			player.setPet(pet);
		}

		PetSkillVO petSkill = getPetSkill(pet);

		// �õ�����ı������ܼӳ�����
		List<Double> d = getPetPassSkillAttribute(pet);
		// �����˺�����
		double injureMultiple = 0;
		if (d.size() > 0 && d.get(0) != null)
		{
			injureMultiple = d.get(0);
		}
		// �����ʼӳ�
		double baojiMultiple = 0;
		if (d.size() > 1 && d.get(1) != null)
		{
			baojiMultiple = d.get(1);
		}
		if (petSkill != null && petSkill.getPetSkillType() != 0)// �������ܹ���
		{
			petSkillAttackNpc(player, pet, petSkill, npcs, injureMultiple,
					baojiMultiple);
		}
		else
		// ����������
		{
			petPhysicsAttackNpc(player, pet, petSkill, npcs, injureMultiple,
					baojiMultiple);
		}
		// һ�ι����ı���ﱾ��״̬��������
		// attackUpdateStat(player.getPPk());
	}

	// ���＼�ܹ���NPC
	private void petSkillAttackNpc(Fighter player, PetInfoVO pet,
			PetSkillVO petSkill, List npcs, double injureMultiple,
			double baojiMultiple)
	{

		NpcService npcService = new NpcService();
		NpcFighter npc = (NpcFighter) npcs.get(0);

		int wxInjure = 0;
		PetCache petCache = new PetCache();
		int pet_npcId = petCache.getNpcIdById(pet.getPetId());
		wxInjure = AttackService.getPetWxInjureValue(pet, npc, pet
				.getPetGrade(), npc.getLevel(), pet_npcId) * 2 / 5;

		int injure = 0;
		int skillInjure = 0;
		int physicsGj1 = 0;
		if (pet.getPetSkillDisplay() != null
				&& pet.getPetSkillDisplay().getPetSkillRound() < 1)
		{
			physicsGj1 = MathUtil.getRandomBetweenXY((int) (pet.getPetGjXiao()
					* (1 + injureMultiple) * (1 + pet.getPetSkillDisplay()
					.getPetSkillMultiple())), (int) (pet.getPetGjDa()
					* (1 + injureMultiple) * (1 + pet.getPetSkillDisplay()
					.getPetSkillMultiple())));
		}
		else
		{
			physicsGj1 = MathUtil.getRandomBetweenXY(
					(int) (pet.getPetGjXiao() * (1 + injureMultiple)),
					(int) (pet.getPetGjDa() * (1 + injureMultiple)));
		}// �õ���������չ�����
		int physicsGj = physicsGj1 * 2;
		player.setPetSkillDisplay(StringUtil.isoToGBK(petSkill
				.getPetSkillName()));
		if (petSkill.getPetSkillType() == 5)
		{
			player
					.setPetSkillDisplay(StringUtil.isoToGBK(petSkill
							.getPetSkillName())
							+ ",���غ��ڹ��������"
							+ petSkill.getPetSkillMultiple()
							* 100 + "%");
		}
		int skillGj = MathUtil.getRandomBetweenXY(petSkill.getPetSkillGjXiao(),
				petSkill.getPetSkillGjDa());
		if (petSkill.getPetSkillArea() == 1)// Ⱥ�幥��
		{
			logger.info("���﷢��Ⱥ�幥������");
			for (int i = npcs.size() - 1; i >= 0; i--)
			{
				npc = (NpcFighter) npcs.get(i);
				// �޸ļ����˺���ʽ
				skillInjure = AttackService.petSkillInjureExpressions(skillGj,
						pet.getPetGrade(), pet.getPetGrow(), npc
								.getNpcDefance(), npc.getLevel(), physicsGj);
				logger.info("���＼���˺�:" + skillInjure);
				injure = skillInjure;
				if (injure <= 0)
				{
					injure = 1;
				}
				npc.setPetInjureOut("-" + injure);
				npcService.updateNPCCurrentHP(player, npc, injure,
						NpcService.PETINJURE);
				if (npc.isDead())
				{
					if (npcs != null && npcs.size() != 0)
					{
						npcs.remove(i);
					}
				}
			}

		}
		else
		{
			logger.info("���﷢�����幥������");
			// skillInjure =
			// AttackService.normalInjureExpressions(skillGj,
			// npc.getNpcDefance(), pet.getPetGrade(), npc.getLevel());
			skillInjure = AttackService.petSkillInjureExpressions(skillGj, pet
					.getPetGrade(), pet.getPetGrow(), npc.getNpcDefance(), npc
					.getLevel(), physicsGj);
			if (petSkill.getPetSkillSeveral() != 1)// �жϹ�������
			{
				List<Integer> list = new ArrayList<Integer>();
				for (int x = 0; x < petSkill.getPetSkillSeveral(); x++)
				{
					skillGj = MathUtil.getRandomBetweenXY(petSkill
							.getPetSkillGjXiao(), petSkill.getPetSkillGjDa());
					skillInjure = AttackService
							.petSkillInjureExpressions(skillGj, pet
									.getPetGrade(), pet.getPetGrow(), npc
									.getNpcDefance(), npc.getLevel(), physicsGj);
					injure = (int) (skillInjure
							* petSkill.getPetSkillMultiple() / 2);
					logger.info("���＼���˺�:" + skillInjure);
					if (injure <= 0)
					{
						injure = 1;
					}
					list.add(injure);
					npcService.updateNPCCurrentHP(player, npc, injure,
							NpcService.PETINJURE);
					if (npc.isDead())
					{
						if (npcs != null && npcs.size() != 0)
						{
							npcs.remove(0);
						}
						break;
					}
				}
				String injureOut = "";
				for (int i = 0; i < list.size(); i++)
				{
					if (i == list.size() - 1)
					{
						injureOut = injureOut + "-" + list.get(i) + "";
					}
					else
					{
						injureOut = injureOut + "-" + list.get(i) + ",";
					}
				}
				npc.setPetInjureOut(injureOut);
			}
			else
			{

				injure = (int) ((skillInjure) * petSkill.getPetSkillMultiple());
				logger.info("���＼���˺�:" + skillInjure);
				if (injure <= 0)
				{
					injure = 1;
				}
				npc.setPetInjureOut("-" + injure);
				npcService.updateNPCCurrentHP(player, npc, injure,
						NpcService.PETINJURE);
				if (npc.isDead())
				{
					if (npcs != null && npcs.size() != 0)
					{
						npcs.remove(0);
					}
				}
			}
		}
		// ��Ѫ���ܵ��Д�
		if (petSkill.getPetSkillType() == 4)
		{
			pet.getPetSkillDisplay().setPetSkillRound(3);
			pet.getPetSkillDisplay().setPetSkillMultiple(
					petSkill.getPetSkillMultiple());
		}
		else
		{
			if (petSkill.getPetSkillType() == 3)
			{
				injure = injure / 2;
				player.setPlayerInjure(0 - injure);
				player.setJuexueInjure(",+" + injure);
				logger.debug("��Ҽ�Ѫ" + injure);
			}
			int round = pet.getPetSkillDisplay().updateSkillRound(
					pet.getPetSkillDisplay().getPetSkillRound());
			pet.getPetSkillDisplay().setPetSkillRound(round);
		}
	}

	// ����������NPC
	private void petPhysicsAttackNpc(Fighter player, PetInfoVO pet,
			PetSkillVO petSkill, List npcs, double injureMultiple,
			double baojiMultiple)
	{
		NpcService npcService = new NpcService();
		NpcFighter npc = (NpcFighter) npcs.get(0);

		int wxInjure = 0;
		PetCache petCache = new PetCache();
		int pet_npcId = petCache.getNpcIdById(pet.getPetId());
		wxInjure = AttackService.getPetWxInjureValue(pet, npc, pet
				.getPetGrade(), npc.getLevel(), pet_npcId) * 2 / 5;

		int injure = 0;
		int physicsInjure = 0;
		int physicsGj1 = 0;
		if (pet.getPetSkillDisplay() != null
				&& pet.getPetSkillDisplay().getPetSkillRound() < 1)
		{
			physicsGj1 = MathUtil.getRandomBetweenXY((int) (pet.getPetGjXiao()
					* (1 + injureMultiple) * (1 + pet.getPetSkillDisplay()
					.getPetSkillMultiple())), (int) (pet.getPetGjDa()
					* (1 + injureMultiple) * (1 + pet.getPetSkillDisplay()
					.getPetSkillMultiple())));
		}
		else
		{
			physicsGj1 = MathUtil.getRandomBetweenXY(
					(int) (pet.getPetGjXiao() * (1 + injureMultiple)),
					(int) (pet.getPetGjDa() * (1 + injureMultiple)));
		}
		int physicsGj = physicsGj1 * 2;
		physicsInjure = (int) (AttackService.normalInjureExpressions(physicsGj,
				npc.getNpcDefance(), pet.getPetGrade(), npc.getLevel()));
		if (petSkill != null
				&& petSkill.getPetSkillType() == 0
				&& MathUtil.PercentageRandomByParamdouble((pet
						.getPetViolenceDrop() + baojiMultiple), 1))// �õ�������
		// ���ж��Ƿ��������
		{

			injure = (physicsInjure) * 2;
			logger.info("���������˺�:" + injure);

			if (injure <= 0)
			{
				injure = 1;
			}
			npc.setPetInjureOut("-" + injure);
			npcService.updateNPCCurrentHP(player, npc, injure,
					NpcService.PETINJURE);
			if (npc.isDead())
			{
				if (npcs != null && npcs.size() != 0)
				{
					npcs.remove(0);
				}
			}
			player.setPetSkillDisplay("��ͨ����,��������");
			logger.debug("���﷢����ͨ��������");

		}
		else
		{
			injure = physicsInjure;
			logger.info("���������˺�:" + injure);

			if (injure <= 0)
			{
				injure = 1;
			}
			npc.setPetInjureOut("-" + injure);
			npcService.updateNPCCurrentHP(player, npc, injure,
					NpcService.PETINJURE);
			if (npc.isDead())
			{
				if (npcs != null && npcs.size() != 0)
				{
					npcs.remove(0);
				}
			}
			player.setPetSkillDisplay("��ͨ����");
			logger.debug("���﷢����ͨ��������");
		}

	}

	/**
	 * ���﹥����
	 * 
	 * @param playerA
	 * @param playerB
	 */
	public void petAttackPlayer(Fighter playerA, Fighter playerB, String tong)
	{
		int wxInjure = 0;

		if (playerA == null || playerB == null)
		{
			logger.debug("��ָ��");
			return;
		}
		// PlayerService playerService = new PlayerService();
		RoleCache roleCache = new RoleCache();
		RoleEntity roleEntity = roleCache.getByPpk(playerA.getPPk() + "");
		RolePetInfo userPet = roleEntity.getRolePetInfo();
		PetInfoVO pet = userPet.getBringPet();

		// PetInfoDao petInfoDao = new PetInfoDao();
		// PetInfoVO pet = petInfoDao.getBringPetByPpk(playerA.getPPk());
		if (pet == null)
		{
			logger.debug("û�д�����");
			return;
		}
		else
		{
			logger.debug("��ɫ������μ�ս����");
			playerA.setPet(pet);
		}

		// PetDAO petdao = new PetDAO();
		PetCache petCache = new PetCache();

		// �õ�����ı������ܼӳ�����
		List<Double> d = getPetPassSkillAttribute(pet);
		// �����˺�����
		double injureMultiple = 0;
		if (d.size() > 0 && d.get(0) != null)
		{
			injureMultiple = d.get(0);
		}
		// �����ʼӳ�
		double baojiMultiple = 0;
		if (d.size() > 1 && d.get(1) != null)
		{
			baojiMultiple = d.get(1);
		}

		int pet_npcId = petCache.getNpcIdById(pet.getPetId());

		wxInjure = AttackService.getPetWxInjureValue(pet, playerB.getWx(), pet
				.getPetGrade(), playerB.getPGrade(), pet_npcId) * 2 / 5;

		PetSkillVO petSkill = getPetSkill(pet);
		if (petSkill != null)// �������ܹ���
		{
			petSkillAttackPK(pet, playerA, playerB, petSkill, tong, wxInjure,
					baojiMultiple, injureMultiple);
		}
		else
		// ����������
		{
			petPhysicsAttackPK(pet, playerA, playerB, petSkill, tong, wxInjure,
					baojiMultiple, injureMultiple);
		}

		// һ�ι����ı���ﱾ��״̬��������
		// attackUpdateStat(playerA.getPPk());
	}

	// ���＼�ܹ���
	private void petSkillAttackPK(PetInfoVO pet, Fighter playerA,
			Fighter playerB, PetSkillVO petSkill, String tong, int wxInjure,
			double baojiMultiple, double injureMultiple)
	{
		FightService fightService = new FightService();
		int skillInjure = 0;
		int injure = 0;
		int physicsGj1 = 0;
		if (pet.getPetSkillDisplay() != null
				&& pet.getPetSkillDisplay().getPetSkillRound() < 1)
		{
			physicsGj1 = MathUtil.getRandomBetweenXY((int) (pet.getPetGjXiao()
					* (1 + injureMultiple) * (1 + pet.getPetSkillDisplay()
					.getPetSkillMultiple())), (int) (pet.getPetGjDa()
					* (1 + injureMultiple) * (1 + pet.getPetSkillDisplay()
					.getPetSkillMultiple())));
		}
		else
		{
			physicsGj1 = MathUtil.getRandomBetweenXY(
					(int) (pet.getPetGjXiao() * (1 + injureMultiple)),
					(int) (pet.getPetGjDa() * (1 + injureMultiple)));
		}

		playerA.setPetSkillDisplay(StringUtil.isoToGBK(petSkill
				.getPetSkillName()));
		if (petSkill.getPetSkillType() == 5)
		{
			playerA
					.setPetSkillDisplay(StringUtil.isoToGBK(petSkill
							.getPetSkillName())
							+ ",���غ��ڹ��������"
							+ petSkill.getPetSkillMultiple()
							* 100 + "%");
		}
		int skillGj1 = MathUtil.getRandomBetweenXY((int) (pet.getPetGjXiao()
				* (1 + injureMultiple) + petSkill.getPetSkillGjXiao()),
				(int) (pet.getPetGjDa() * (1 + injureMultiple) + petSkill
						.getPetSkillGjDa()));
		// 5��5�� ����PK ����x4
		int physicsGj = physicsGj1 * 4;
		int skillGj = skillGj1 * 4;

		if (petSkill.getPetSkillSeveral() != 1)// �жϹ�������
		{
			List<Integer> list = new ArrayList<Integer>();
			for (int x = 0; x < petSkill.getPetSkillSeveral(); x++)
			{

				skillGj = MathUtil.getRandomBetweenXY((int) (petSkill
						.getPetSkillGjXiao()
						* (1 + injureMultiple) + petSkill.getPetSkillGjXiao()),
						(int) (petSkill.getPetSkillGjDa()
								* (1 + injureMultiple) + petSkill
								.getPetSkillGjDa()));
				int skillGj_temp = skillGj + physicsGj * 60 / 100;

				skillInjure = AttackService.normalInjureExpressions(
						skillGj_temp, playerB.getFy(), pet.getPetGrade(),
						playerB.getPGrade());
				injure = (int) (skillInjure * petSkill.getPetSkillMultiple()) / 3;
				if (injure <= 0)
				{
					injure = 1;
				}
				list.add(injure);
				fightService.statUpdateByPetInjure(playerB, playerA, injure,
						tong,-1);
				logger.info("���﷢�����ܹ�������,�����˺�=" + injure + " ,wxInjure="
						+ wxInjure);

			}
			String injureOut = "";
			for (int i = 0; i < list.size(); i++)
			{
				if (i == list.size() - 1)
				{
					injureOut = injureOut + "-" + list.get(i) + "";
				}
				else
				{
					injureOut = injureOut + "-" + list.get(i) + ",";
				}
			}
			playerB.setPetInjureOut(injureOut);
		}
		else
		{

			skillInjure = AttackService.normalInjureExpressions(
					skillGj * 80 / 100, playerB.getFy(), pet.getPetGrade(),
					playerB.getPGrade());
			injure = (int) (skillInjure * petSkill.getPetSkillMultiple()) / 3;
			if (injure <= 0)
			{
				injure = 1;
			}
			playerB.setPetInjureOut("-" + injure);
			fightService.statUpdateByPetInjure(playerB, playerA, injure, tong,-1);
			logger
					.debug("���﷢�����ܹ�������,�����˺�=" + injure + " ,wxInjure="
							+ wxInjure);

		}
		logger.debug("�������й����˺�ֵ:" + wxInjure + skillInjure);
		// ��Ѫ���ܵ��Д�
		if (petSkill.getPetSkillType() == 4)
		{
			pet.getPetSkillDisplay().setPetSkillRound(3);
			pet.getPetSkillDisplay().setPetSkillMultiple(
					petSkill.getPetSkillMultiple());
		}
		else
		{
			if (petSkill.getPetSkillType() == 3)
			{
				injure = injure / 2;
				playerA.setPlayerInjure(0 - injure);
				playerA.setJuexueInjure(",+" + injure);
				logger.debug("��Ҽ�Ѫ" + injure);
			}
			int round = pet.getPetSkillDisplay().updateSkillRound(
					pet.getPetSkillDisplay().getPetSkillRound());
			pet.getPetSkillDisplay().setPetSkillRound(round);
		}
	}

	// ������ͨ����
	private void petPhysicsAttackPK(PetInfoVO pet, Fighter playerA,
			Fighter playerB, PetSkillVO petSkill, String tong, int wxInjure,
			double baojiMultiple, double injureMultiple)
	{
		FightService fightService = new FightService();
		int physicsInjure = 0;
		int injure = 0;
		int physicsGj = 0;
		if (pet.getPetSkillDisplay() != null
				&& pet.getPetSkillDisplay().getPetSkillRound() < 1)
		{
			physicsGj = MathUtil.getRandomBetweenXY((int) (pet.getPetGjXiao()
					* (1 + injureMultiple) * (1 + pet.getPetSkillDisplay()
					.getPetSkillMultiple())), (int) (pet.getPetGjDa()
					* (1 + injureMultiple) * (1 + pet.getPetSkillDisplay()
					.getPetSkillMultiple())));
		}
		else
		{
			physicsGj = MathUtil.getRandomBetweenXY(
					(int) (pet.getPetGjXiao() * (1 + injureMultiple)),
					(int) (pet.getPetGjDa() * (1 + injureMultiple)));
		}
		// 5��5�� ����PK ����x4
		physicsInjure = AttackService.normalInjureExpressions(physicsGj * 4,
				playerB.getFy(), pet.getPetGrade(), playerB.getPGrade());
		if (petSkill != null
				&& petSkill.getPetSkillType() == 0
				&& MathUtil.PercentageRandomByParamdouble((pet
						.getPetViolenceDrop() + baojiMultiple), 1))
		{
			injure = (physicsInjure) * 2 / 3;
			if (injure <= 0)
			{
				injure = 1;
			}
			playerB.setPetInjureOut("-" + injure);
			fightService.statUpdateByPetInjure(playerB, playerA, injure, tong,-1);
			playerA.setPetSkillDisplay("��ͨ����,��������");
			logger.info("���﷢����ͨ�������ܣ���ͨ�˺�(˫��)=" + injure + " ,wxInjure="
					+ wxInjure * 2);
		}
		else
		{
			injure = physicsInjure / 3;
			if (injure <= 0)
			{
				injure = 1;
			}
			playerB.setPetInjureOut("-" + injure);
			fightService.statUpdateByPetInjure(playerB, playerA, injure, tong,-1);
			playerA.setPetSkillDisplay("��ͨ����");
			logger
					.debug("���﷢����ͨ�������ܣ���ͨ�˺�=" + injure + " ,wxInjure="
							+ wxInjure);
		}
		logger.debug("�������й����˺�ֵ:" + wxInjure + physicsInjure);
		int round = pet.getPetSkillDisplay().updateSkillRound(
				pet.getPetSkillDisplay().getPetSkillRound());
		pet.getPetSkillDisplay().setPetSkillRound(round);
	}

	/**
	 * �õ������ʹ�ü���
	 */
	public PetSkillVO getPetSkill(PetInfoVO pet)
	{
		PetSkillCache petSkillCache = new PetSkillCache();

		PetSkillVO petSkill = null;
		// PetSkillDao dao = new PetSkillDao();
		List<PetSkillControlVO> petControls = new ArrayList<PetSkillControlVO>();
		PetSkillControlVO petControl = null;
		PetSkillControlDao petControlDao = new PetSkillControlDao();
		if (petSkillCache.getPetSkillType(pet.getPetSkillOne()) != 0)// �жϼ��������Ƿ�Ϊ��������
		{
			int pet_skill_gourp = -1;
			pet_skill_gourp = this.getPetSkillGroupID(pet.getPetSkillOne());
			petControl = new PetSkillControlVO();
			// petControl.setControlId(pet.getPetSkillOne());
			petControl = petControlDao.getByPetAndSkillGroup(pet.getPetId(),
					pet_skill_gourp);
			petControl.setPetSkillId(pet.getPetSkillOne());
			petControls.add(petControl);
		}
		if (petSkillCache.getPetSkillType(pet.getPetSkillTwo()) != 0)// �жϼ��������Ƿ�Ϊ��������
		{
			int pet_skill_gourp = -1;
			pet_skill_gourp = this.getPetSkillGroupID(pet.getPetSkillTwo());
			petControl = new PetSkillControlVO();
			// petControl.setControlId(pet.getPetSkillTwo());
			petControl = petControlDao.getByPetAndSkillGroup(pet.getPetId(),
					pet_skill_gourp);
			petControl.setPetSkillId(pet.getPetSkillTwo());
			petControls.add(petControl);
		}
		if (petSkillCache.getPetSkillType(pet.getPetSkillThree()) != 0)// �жϼ��������Ƿ�Ϊ��������
		{
			int pet_skill_gourp = -1;
			pet_skill_gourp = this.getPetSkillGroupID(pet.getPetSkillThree());
			petControl = new PetSkillControlVO();
			// petControl.setControlId(pet.getPetSkillThree());
			petControl = petControlDao.getByPetAndSkillGroup(pet.getPetId(),
					pet_skill_gourp);
			petControl.setPetSkillId(pet.getPetSkillThree());
			petControls.add(petControl);
		}
		if (petSkillCache.getPetSkillType(pet.getPetSkillFour()) != 0)// �жϼ��������Ƿ�Ϊ��������
		{
			int pet_skill_gourp = -1;
			pet_skill_gourp = this.getPetSkillGroupID(pet.getPetSkillFour());
			petControl = new PetSkillControlVO();
			// petControl.setControlId(pet.getPetSkillFour());
			petControl = petControlDao.getByPetAndSkillGroup(pet.getPetId(),
					pet_skill_gourp);
			petControl.setPetSkillId(pet.getPetSkillFour());
			petControls.add(petControl);
		}
		if (petSkillCache.getPetSkillType(pet.getPetSkillFive()) != 0)// �жϼ��������Ƿ�Ϊ��������
		{
			int pet_skill_gourp = -1;
			pet_skill_gourp = this.getPetSkillGroupID(pet.getPetSkillFive());
			petControl = new PetSkillControlVO();
			// petControl.setControlId(pet.getPetSkillFive());
			petControl = petControlDao.getByPetAndSkillGroup(pet.getPetId(),
					pet_skill_gourp);
			petControl.setPetSkillId(pet.getPetSkillFive());
			petControls.add(petControl);
		}
		petControl = new PetSkillControlVO();
		petControl = getSkillByControl(petControls);
		if (petControl == null)
		{
			return null;
		}
		else
		{
			petSkill = petSkillCache
					.getPetSkillById(petControl.getPetSkillId());
			return petSkill;
		}
	}

	/**
	 * �õ����ﱻ�����ܵ�����
	 * 
	 * @param PetInfoVO
	 * @return list ��һλ���˺����� �ڶ�λ�Ǳ����ʼӳ�
	 */
	public List<Double> getPetPassSkillAttribute(PetInfoVO pet)
	{
		PetSkillDao dao = new PetSkillDao();
		PetSkillVO vo = null;
		List<Double> list = new ArrayList<Double>();
		List<PetSkillVO> skilllist = dao.getPassSkill(pet.getPetSkillOne(), pet
				.getPetSkillTwo(), pet.getPetSkillThree(), pet
				.getPetSkillFour(), pet.getPetSkillFive());
		// �õ����ﱻ�����ܵļӳ����� �����ֵ�õ� �ô��õ������˺�����
		for (int i = 0; i < skilllist.size(); i++)
		{
			if (i != 0)
			{
				vo = skilllist.get(i);
				double x = vo.getPetInjureMultiple();
				double y = Double.parseDouble(list.get(0).toString());
				if (x > y)
				{
					list.remove(0);
					list.add(0, y);
				}
			}
			else
			{
				vo = skilllist.get(i);
				double x = vo.getPetInjureMultiple();
				list.add(0, x);
			}
		}
		// �õ����ﱻ�����ܵļӳ����� �����ֵ�õ� �ô��õ����Ǳ����ʼӳ�
		for (int i = 0; i < skilllist.size(); i++)
		{
			if (i != 0)
			{
				vo = skilllist.get(i);
				double x = vo.getPetViolenceDropMultiple();
				double y = Double.parseDouble(list.get(0).toString());
				if (x > y)
				{
					list.remove(1);
					list.add(1, y);
				}
			}
			else
			{
				vo = skilllist.get(i);
				double x = vo.getPetViolenceDropMultiple();
				list.add(1, x);
			}
		}
		return list;
	}

	/**
	 * ���ݳ�����Ʊ�õ����＼��
	 * 
	 * @param petControls
	 * @return
	 */
	public PetSkillControlVO getSkillByControl(
			List<PetSkillControlVO> petControls)
	{
		PetSkillControlVO petSkillControl = null;
		if (petControls == null || petControls.size() == 0)
		{
			return null;
		}

		int temp_x = 0;
		int temp_y = 0;
		int random = (int) (Math.random() * 100);
		for (PetSkillControlVO petControl : petControls)
		{
			temp_x = temp_y;
			temp_y = temp_y + petControl.getControlDrop();
			if (random >= temp_x && random < temp_y)
			{
				petSkillControl = petControl;
				break;
			}
		}
		if (petSkillControl == null)// û�з������ܹ���
		{
			return null;
		}
		else
		{
			return petSkillControl;
		}
	}

	/**
	 * �õ����＼�����ڵ���id
	 * 
	 * @return
	 */
	public int getPetSkillGroupID(int pet_skill_id)
	{
		int group_id = -1;

		// PetSkillDao petSkillDao = new PetSkillDao();
		// group_id = petSkillDao.getGroupID(pet_skill_id);

		PetSkillCache petSkillCache = new PetSkillCache();
		group_id = petSkillCache.getGroupID(pet_skill_id);

		return group_id;
	}

	/**
	 * ���ĳ����ǳ�
	 * 
	 * @param pet_pk
	 * @param pet_nickname
	 */
	public void updateNickName(int pet_pk, String pet_nickname, String pPk)
	{

		RoleCache roleCache = new RoleCache();
		RoleEntity roleEntity = roleCache.getByPpk(pPk);
		RolePetInfo userPet = roleEntity.getRolePetInfo();
		logger.info("userPet=" + userPet);
		userPet.updateNickName(pet_pk, pet_nickname);

		PetInfoDao petInfoDao = new PetInfoDao();
		petInfoDao.updateNickName(pet_pk, pet_nickname);
	}

	/**
	 * �õ���ϴ����
	 * 
	 * @param pet_pk
	 *            ����id
	 * @param pg_pk
	 *            ������id
	 * @return
	 */
	public String initPetByProp(int pet_pk, int pg_pk)
	{
		GoodsService goodsService = new GoodsService();
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();

		PlayerPropGroupVO propGroup = null;
		propGroup = propGroupDao.getByPgPk(pg_pk);// �õ�������Ϣ

		goodsService.removeProps(propGroup, 1);// ɾ��ϴ����ĵ���
		initPet(pet_pk);// ִ�г���ϴ��

		// ��ս������Ϣ����
		RoleCache roleCache = new RoleCache();
		RoleEntity roleEntity = roleCache.getByPpk(propGroup.getPPk() + "");
		RolePetInfo userPet = roleEntity.getRolePetInfo();
		userPet.initPet(pet_pk, propGroup.getPPk());

		return null;
	}

	/**
	 * �������鿴���������
	 * 
	 * @param petPk
	 * @return
	 */
	public int pet_longess(int petPk)
	{

		PetInfoDAO dao = new PetInfoDAO();
		return dao.pet_longess(petPk);
	}

	/**
	 * ����ϴ��,���³�ʼ������
	 */
	private void initPet(int pet_pk)
	{
		PetInfoDao petInfoDao = new PetInfoDao();
		PetDAO petDAO = new PetDAO();

		PetInfoVO player_pet = petInfoDao.getPet(pet_pk);// �õ�ϴ�������Ϣ
		PetVO pet = petDAO.getPetViewByPetID(player_pet.getPetId());// ͨ������ID
		// �õ������
		PetShapeVO petShapeVO = (PetShapeVO) petDAO.getPetShapeView(player_pet
				.getPetType(), 1);// ͨ������ȼ��ͳ���ID�ͳ������� ȥ�ҳ���ɳ���Ϣ

		String pet_grow_str = "";
		double pet_grow = 0;// �ɳ���
		int pet_xia_exp = 0;// �¼�����

		int gj_xiao = 0;// ����С
		int gj_da = 0;// ������

		int pet_sale = 0;// �����۸�

		int pet_skill_one = 0;//
		int pet_skill_two = 0;//
		int pet_skill_three = 0;//
		int pet_skill_four = 0;//
		int pet_skill_five = 0;//

		int grow_quality = getGrowQuality(pet_grow);// �õ��ɳ��ʵ�Ʒ��
		if (player_pet.getPetInitNum() == 0)// �ж��ǲ��ǵ�һ��ϴ����
		{
			grow_quality++;
			pet_grow = getGrowByQuality(pet.getPetDropXiao(), pet
					.getPetDropDa());
		}
		else
			if (player_pet.getPetInitNum() > 0)
			{
				pet_grow = getGrowByQuality(pet.getPetDropXiao(), pet
						.getPetDropDa());
			}
		DecimalFormat df = new DecimalFormat("0.00");
		pet_grow_str = df.format(pet_grow);

		pet_xia_exp = (int) (Integer.parseInt(petShapeVO
				.getShapeXiaExperience().trim()) * pet_grow);

		gj_xiao = (int) (15 + 4 * pet_grow);
		gj_da = gj_xiao;

		pet_sale = petShapeVO.getShapeSale();

		/**
		 * if(pet.getPetSkillOne()!=0){
		 * if(MathUtil.isAppearByPercentage(100,100)){ pet_skill_one =
		 * pet.getPetSkillOne(); //30% logger.info("��1�ּ���100%�����Ѿ���������"); }
		 * }if(pet.getPetSkillTwo()!=0){
		 * if(MathUtil.isAppearByPercentage(50,100)){ pet_skill_two =
		 * pet.getPetSkillTwo(); //3% logger.info("��2�ּ���50%�����Ѿ���������"); }
		 * }if(pet.getPetSkillThree()!=0){
		 * if(MathUtil.PercentageRandomByParamdouble(10,100)){ pet_skill_three =
		 * pet.getPetSkillThree();//% logger.info("��3�ּ���10%�����Ѿ���������"); }
		 * }if(pet.getPetSkillFour()!=0){
		 * if(MathUtil.PercentageRandomByParamdouble(1,100)){ pet_skill_four =
		 * pet.getPetSkillFour();//0.03% logger.info("��4�ּ���1%�����Ѿ���������"); }
		 * }if(pet.getPetSkillFive()!=0){
		 * if(MathUtil.PercentageRandomByParamdouble(0.1,100)){ pet_skill_five =
		 * pet.getPetSkillFive();//0.003% logger.info("��5�ּ���0.1%�����Ѿ���������"); } }
		 */
		if (pet.getSkillControl() != 0)
		{ // ֻ���ڳ���ӵ�м�������Ϊ��ʱ��Ҫ�����ú��ּ���

			int i = 0;
			int skill_num = 0;
			List<Integer> list = new ArrayList<Integer>();
			if (pet.getPetSkillOne() != 0)
				list.add(pet.getPetSkillOne());
			if (pet.getPetSkillTwo() != 0)
				list.add(pet.getPetSkillTwo());
			if (pet.getPetSkillThree() != 0)
				list.add(pet.getPetSkillThree());
			if (pet.getPetSkillFour() != 0)
				list.add(pet.getPetSkillFour());
			if (pet.getPetSkillFive() != 0)
				list.add(pet.getPetSkillFive());

			if (list.size() != 0)
			{
				Random random = new Random();
				if (MathUtil.isAppearByPercentage(100, 100))
				{
					i = random.nextInt(list.size());
					pet_skill_one = list.get(i);
					skill_num++;
					logger.info("��1�ּ���100%�����Ѿ���������");
				}
				if (MathUtil.isAppearByPercentage(50, 100))
				{
					if (skill_num < pet.getSkillControl())
					{ // �����еļ�����С�ڳ�����Ӧ�еļ��������,���ܼ���.
						do
						{
							i = random.nextInt(list.size());
							pet_skill_two = list.get(i);
							logger.info("��2�ּ���50%�����Ѿ���������");
							skill_num++;
						} while (pet_skill_two == pet_skill_one
								|| pet.getSkillControl() == 1);
					}
				}
				if (MathUtil.PercentageRandomByParamdouble(10, 100))
				{
					if (skill_num < pet.getSkillControl())
					{ // �����еļ�����С�ڳ�����Ӧ�еļ��������,���ܼ���.
						do
						{
							i = random.nextInt(list.size());
							pet_skill_three = list.get(i);
							logger.info("��3�ּ���10%�����Ѿ���������");
							skill_num++;
						} while (pet_skill_three == pet_skill_one
								|| pet_skill_three == pet_skill_two);
					}
				}
				if (MathUtil.PercentageRandomByParamdouble(1, 100))
				{
					if (skill_num < pet.getSkillControl())
					{ // �����еļ�����С�ڳ�����Ӧ�еļ��������,���ܼ���.
						do
						{
							i = random.nextInt(list.size());
							pet_skill_four = list.get(i);
							logger.info("��4�ּ���1%�����Ѿ���������");
							skill_num++;
						} while (pet_skill_four == pet_skill_one
								|| pet_skill_four == pet_skill_two
								|| pet_skill_four == pet_skill_three);
					}
				}
				if (MathUtil.PercentageRandomByParamdouble(0.1, 100))
				{
					if (skill_num < pet.getSkillControl())
					{ // �����еļ�����С�ڳ�����Ӧ�еļ��������,���ܼ���.
						do
						{
							i = random.nextInt(list.size());
							pet_skill_five = list.get(i);
							logger.info("��5�ּ���0.1%�����Ѿ���������");
							skill_num++;
						} while (pet_skill_five == pet_skill_one
								|| pet_skill_five == pet_skill_two
								|| pet_skill_five == pet_skill_three
								|| pet_skill_five == pet_skill_four);
					}
				}
			}
		}

		String xing = "";
		if (player_pet.getPetName().contains("*"))
		{

		}
		else
		{
			if (pet_skill_one != 0 || pet_skill_two != 0
					|| pet_skill_three != 0 || pet_skill_four != 0
					|| pet_skill_five != 0)
			{
				xing = "*";
			}
		}
		petInfoDao.initPet(pet_pk, pet_grow_str, pet_xia_exp, gj_xiao, gj_da,
				pet_sale, pet_skill_one, pet_skill_two, pet_skill_three,
				pet_skill_four, pet_skill_five, xing);

		RoleCache roleCache = new RoleCache();
		RoleEntity roleEntity = roleCache.getByPpk(player_pet.getPPk() + "");
		RolePetInfo userPet = roleEntity.getRolePetInfo();
		userPet.initPet(pet_pk, player_pet.getPPk());

	}

	/**
	 * �õ��ɳ��ʵ�Ʒ��
	 */
	public int getGrowQuality(double pet_grow)
	{
		int grow_quality = 0;
		if (pet_grow >= 1.0 && pet_grow <= 1.5)// ��Ʒ
		{
			grow_quality = 0;
		}
		else
			if (pet_grow >= 1.6 && pet_grow <= 2.0)// ��Ʒ
			{
				grow_quality = 1;
			}
			else
				if (pet_grow >= 2.1 && pet_grow <= 2.5)// ��Ʒ
				{
					grow_quality = 2;
				}
		return grow_quality;
	}

	/**
	 * ���ݳɳ��ʵ�Ʒ�ʵõ��ɳ���
	 */
	public double getGrowByQuality(int grow_qulity)
	{
		return MathUtil.getRandomDoubleXY(1.0, 1.5);
	}

	/**
	 * ���ݳɳ��ʵ�Ʒ�ʵõ��ɳ���
	 */
	public double getGrowByQuality(double petDropXiao, double petDropDa)
	{
		return MathUtil.getRandomDoubleXY(petDropXiao, petDropDa);
	}

	/**
	 * ���������ڳ�ս�� ����ս��״̬
	 * 
	 * @param petIsBring
	 *            1Ϊ��ս,0Ϊ����ս.
	 * @param petPk
	 * @param pPk
	 */
	public void petIsBring(int pPk, int petPk, int petIsBring)
	{
		RoleCache roleCache = new RoleCache();
		RoleEntity roleEntity = roleCache.getByPpk(pPk);
		RolePetInfo userPet = roleEntity.getRolePetInfo();

		PetInfoDAO dao = new PetInfoDAO();
		if (petIsBring == 0)
		{
			userPet.unBringpet(); // ����������δ��ս״̬
			dao.petIsBring(pPk, petPk, petIsBring);
		}
		else
			if (petIsBring == 1)
			{

				int petpk = dao.getPetPk(pPk); // ������ �Ƿ���
				// ��� �в�ս�ĳ���,���轫����ս
				if (petPk > 0)
				{
					dao.petIsBring(pPk, petpk, 0);
					userPet.unBringpet(); // ����������δ��ս״̬
				}

				// �������ս
				dao.petIsBring(pPk, petPk, petIsBring);
				userPet.bringPet(petPk);

			}
	}

	/**
	 * ���ݳ���id����һ������
	 * 
	 * ��11.26���＼�ܸ��Ÿ���, ԭ��Ϊ���ŵļ����ϸ����Ϊpet��������Ķ�Ӧ�ļ���, �����޸�Ϊ���＼��������ųɹ�,
	 * ���õ��ļ����Ǵ�pet�����ܾ��е��κ�һ������.
	 */
	public String createPetByPetID(int p_pk, int pet_id)
	{
		String hint = null;

		PetDAO petDAO = new PetDAO();
		PetVO pet = (PetVO) petDAO.getPetViewByPetID(pet_id);
		if (pet != null)
		{

			/** ***************���＼�ܸ���****************** */
			/** ����1 ��ѧϰ�ļ���id */
			String petSkillOne = "0";
			/** ����2 ��ѧϰ�ļ���id */
			String petSkillTwo = "0";
			/** ����3 ��ѧϰ�ļ���id */
			String petSkillThree = "0";
			/** ����4 ��ѧϰ�ļ���id */
			String petSkillFour = "0";
			/** ����5 ��ѧϰ�ļ���id */
			String petSkillFive = "0";
			/**
			 * if(pet.getPetSkillOne()!=0){
			 * if(MathUtil.isAppearByPercentage(30,100)){ petSkillOne =
			 * pet.getPetSkillOne()+""; //30% logger.info("��1�ּ���30%�����Ѿ���������"); }
			 * }if(pet.getPetSkillTwo()!=0){
			 * if(MathUtil.isAppearByPercentage(3,100)){ petSkillTwo =
			 * pet.getPetSkillTwo()+""; //3% logger.info("��2�ּ���3%�����Ѿ���������"); }
			 * }if(pet.getPetSkillThree()!=0){
			 * if(MathUtil.PercentageRandomByParamdouble(0.3,100)){
			 * petSkillThree = pet.getPetSkillThree()+"";//%
			 * logger.info("��3�ּ���0.3%�����Ѿ���������"); }
			 * }if(pet.getPetSkillFour()!=0){
			 * if(MathUtil.PercentageRandomByParamdouble(0.03,100)){
			 * petSkillFour = pet.getPetSkillFour()+"";//0.03%
			 * logger.info("��4�ּ���0.03%�����Ѿ���������"); }
			 * }if(pet.getPetSkillFive()!=0){
			 * if(MathUtil.PercentageRandomByParamdouble(0.003,100)){
			 * petSkillFive = pet.getPetSkillFive()+"";//0.003%
			 * logger.info("��5�ּ���0.003%�����Ѿ���������"); } }
			 */
			int i = 0;
			int skill_num = 0;
			List<Integer> list = new ArrayList<Integer>();
			list.add(pet.getPetSkillOne());
			list.add(pet.getPetSkillTwo());
			list.add(pet.getPetSkillThree());
			list.add(pet.getPetSkillFour());
			list.add(pet.getPetSkillFive());

			Random random = new Random();
			if (MathUtil.isAppearByPercentage(30, 100))
			{
				if (skill_num < pet.getSkillControl())
				{ // �����еļ�����С�ڳ�����Ӧ�еļ��������,���ܼ���.
					i = random.nextInt(5);
					petSkillOne = list.get(i) + "";
					skill_num++;
					logger.info("��1�ּ���30%�����Ѿ���������");
				}
			}
			if (MathUtil.isAppearByPercentage(3, 100))
			{
				if (skill_num < pet.getSkillControl())
				{ // �����еļ�����С�ڳ�����Ӧ�еļ��������,���ܼ���.
					do
					{
						i = random.nextInt(5);
						petSkillTwo = list.get(i) + "";
						skill_num++;
					} while (petSkillTwo == petSkillOne);
				}
				logger.info("��2�ּ���3%�����Ѿ���������");
			}
			if (MathUtil.PercentageRandomByParamdouble(0.3, 100))
			{
				if (skill_num < pet.getSkillControl())
				{ // �����еļ�����С�ڳ�����Ӧ�еļ��������,���ܼ���.
					do
					{
						i = random.nextInt(5);
						petSkillThree = list.get(i) + "";
						logger.info("��3�ּ���0.3%�����Ѿ���������");
						skill_num++;
					} while (petSkillThree == petSkillOne
							|| petSkillThree == petSkillTwo);
				}
			}
			if (MathUtil.PercentageRandomByParamdouble(0.03, 100))
			{
				if (skill_num < pet.getSkillControl())
				{ // �����еļ�����С�ڳ�����Ӧ�еļ��������,���ܼ���.
					do
					{
						i = random.nextInt(5);
						petSkillFour = list.get(i) + "";
						logger.info("��4�ּ���0.03%�����Ѿ���������");
						skill_num++;
					} while (petSkillFour == petSkillOne
							|| petSkillFour == petSkillTwo
							|| petSkillFour == petSkillThree);
				}
			}

			if (MathUtil.PercentageRandomByParamdouble(0.003, 100))
			{
				if (skill_num < pet.getSkillControl())
				{ // �����еļ�����С�ڳ�����Ӧ�еļ��������,���ܼ���.

					do
					{
						i = random.nextInt(5);
						petSkillFive = list.get(i) + "";
						logger.info("��5�ּ���0.003%�����Ѿ���������");
					} while (petSkillFive == petSkillOne
							|| petSkillFive == petSkillTwo
							|| petSkillFive == petSkillThree
							|| petSkillFive == petSkillFour);
				}
			}

			/** ***************������ȡ��������ϢȻ����ɲ�׽��ĳ�����Ϣ****************** */
			/** ����ɳ��� */
			double petDropDa = pet.getPetDropDa();
			/** ����ɳ��� */
			double petDropXiao = pet.getPetDropXiao();
			double pet_grow = MathUtil
					.getRandomDoubleXY(petDropXiao, petDropDa);

			/** ����ɳ��ʡ� */
			DecimalFormat df = new DecimalFormat("0.00");
			String pet_grow_str = df.format(pet_grow);
			/** ��ɫid */
			String pPk = p_pk + "";
			/** ����ͼƬ */
			String petPic = pet.getPetImg();
			/** ��Ӧpet�����id */
			String petId = pet.getPetId() + "";
			/** �������� */
			String petName = pet.getPetName();
			/** �����ǳ� */
			String petNickname = pet.getPetName();
			/** �ȼ� */
			String petGrade = "1";
			// ͨ������ȼ��ͳ���ID�ͳ������� ȥ�ҳ���ɳ���Ϣ
			PetShapeVO petShapeVO = (PetShapeVO) petDAO.getPetShapeView(pet
					.getPetType(), 1);
			if (petShapeVO == null)
			{
				hint = "�ɳ������";
				return hint;
			}

			/** ���� */
			String petExp = "0";
			/** �¼�����ﵽ��һ����Ҫ�ľ��� */
			int petXiaExps = (int) (Integer.parseInt(petShapeVO
					.getShapeXiaExperience().trim()) * pet_grow);
			String petXiaExp = petXiaExps + "";

			/** ��С���� */
			int petGjXiao = (int) (15 + 4 * pet_grow);
			;
			/** ��󹥻� */
			int petGjDa = petGjXiao;

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

			String xing = "*";
			if (!petSkillOne.equals("0") || !petSkillTwo.equals("0")
					|| !petSkillThree.equals("0") || !petSkillFour.equals("0")
					|| !petSkillFive.equals("0"))
			{
				petNickname = petNickname + xing;
			}

			PetInfoDAO petInfoDAO = new PetInfoDAO();
			petInfoDAO.getPetInfoAdd(pPk, petId, petName, petNickname,
					petGrade, petExp, petXiaExp, petGjXiao, petGjDa, petSale,
					pet_grow_str, petWx, petWxValue, petSkillOne, petSkillTwo,
					petSkillThree, petSkillFour, petSkillFive, petLife,
					petType, petIsBring, petFatigue, petLonge, longeNumber,
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

	/**
	 * �õ�������ϸ��Ϣ
	 * 
	 * @param pet_pk
	 * @return
	 */
	public PetInfoVO getPetByID(int pet_pk)
	{
		PetInfoDao petInfoDao = new PetInfoDao();
		return petInfoDao.getPet(pet_pk);
	}

	/**
	 * �õ�������Ϣwml�ű�
	 * 
	 * @param pet
	 * @return
	 */
	public String getPetDisplayWml(int pet_pk)
	{
		PetInfoDao petInfoDao = new PetInfoDao();
		PetInfoVO pet = petInfoDao.getPet(pet_pk);

		return getPetDisplayWml(pet);
	}

	/**
	 * �õ�������Ϣwml�ű�
	 * 
	 * @param pet
	 * @return
	 */
	public String getPetDisplayWml(PetInfoVO pet)
	{
		PetSkillCache petSkillCache = new PetSkillCache();
		PicService picService = new PicService();
		if (pet == null)
		{
			logger.info("��������");
			return null;
		}
		if (pet.getPPk() < 0)
		{
			pet.setPPk(-pet.getPPk());
		}

		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoById(pet.getPPk() + "");

		StringBuffer resultWml = new StringBuffer();
		resultWml.append(picService.getPetPicStr(roleInfo, pet.getPetPk() + "")
				+ StringUtil.isoToGBK(pet.getPetNickname()) + "<br/>");
		// resultWml.append(picService.getPetPicStr(Math.abs(pet.getPPk())+"",
		// pet.getPetPk()+""));
		resultWml.append("����:" + StringUtil.isoToGBK(pet.getPetName())
				+ "<br/>");
		resultWml.append("״̬:");
		if (pet.getPetIsBring() == 1)
		{
			resultWml.append("ս��" + "<br/>");
		}
		else
		{
			resultWml.append("��Ϣ" + "<br/>");
		}
		resultWml.append("�ȼ�:" + pet.getPetGrade() + "��<br/>");
		/** ***********TODO************************* */
		resultWml.append("����:" + pet.getPetBenExp() + "/" + pet.getPetXiaExp()
				+ "<br/>");
		if (petSkillCache.getInjureMultiple(pet.getPetSkillOne()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillTwo()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillThree()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillFour()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillFive()) != 0)
		{
			resultWml.append("����:"
					+ (Integer.valueOf((int) (pet.getPetGjXiao() * (1
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillOne())
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillTwo())
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillThree())
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillFour()) + petSkillCache
							.getInjureMultiple(pet.getPetSkillFive())))))
					+ "-"
					+ Integer.valueOf((int) (pet.getPetGjDa() * (1
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillOne())
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillTwo())
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillThree())
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillFour()) + petSkillCache
							.getInjureMultiple(pet.getPetSkillFive()))))
					+ "<br/>");
		}
		else
		{
			resultWml.append("����:" + pet.getPetGjXiao() + "-"
					+ pet.getPetGjDa() + "<br/>");

		}
		/** ***********TODO************************* */
		// resultWml.append("����:"+pet.getPetGjXiao()+"-"+pet.getPetGjDa()+"<br/>");
		// resultWml.append("��󹥻�:"+pet.getPetGjDa()+"<br/>");
		// resultWml.append("�۸�:"+pet.getPetSale()+"<br/>");
		resultWml.append("�ɳ���:" + pet.getPetGrow() + "<br/>");
		String wxValue = "";
		if (pet.getWxValue() != 0)
		{
			wxValue = "+" + pet.getWxValue();
		}
		// resultWml.append("��������ֵ:"+pet.getWxValue()+"<br/>");
		if (pet.getPetSkillOne() == 0 && pet.getPetSkillTwo() == 0
				&& pet.getPetSkillThree() == 0 && pet.getPetSkillFour() == 0
				&& pet.getPetSkillFive() == 0)
		{
			resultWml.append("����:��<br/>");
		}
		else
		{
			// resultWml.append("����:" +
			// petSkillDao.getName(pet.getPetSkillOne())
			// + petSkillDao.getName(pet.getPetSkillTwo())
			// + petSkillDao.getName(pet.getPetSkillThree())
			// + petSkillDao.getName(pet.getPetSkillFour())
			// + petSkillDao.getName(pet.getPetSkillFive()) + "<br/>");

			resultWml.append("����:"
					+ petSkillCache.getName(pet.getPetSkillOne())
					+ petSkillCache.getName(pet.getPetSkillTwo())
					+ petSkillCache.getName(pet.getPetSkillThree())
					+ petSkillCache.getName(pet.getPetSkillFour())
					+ petSkillCache.getName(pet.getPetSkillFive()) + "<br/>");
		}
		resultWml.append("����:" + pet.getPetLonge() + "/" + pet.getPetLife()
				+ "<br/>");
		resultWml.append("����:" + pet.getPetFatigue() + "<br/>");
		resultWml.append("�����۸�:" + pet.getPetSale() + "��<br/>");
		return resultWml.toString();
	}

	/**
	 * �õ����Ա�ϴ�ĳ��û�������ϵ���ʮ��һ�ϵİ���ʮ�� (11.25�Ż�, ���Ա�ϴ�İ���һ�����ϵĶ�����)
	 * 
	 * @param p_pk
	 * @return
	 */
	public List<PetInfoVO> getInitPetList(int p_pk)
	{
		PetInfoDao petInfoDao = new PetInfoDao();
		return petInfoDao.getInitPetList(p_pk);
	}

	/**
	 * �õ���������
	 * 
	 * @param p_pk
	 */
	public int getNumOfPet(int p_pk)
	{
		PetInfoDao petInfoDao = new PetInfoDao();
		return petInfoDao.getNumOfPet(p_pk);
	}

	/**
	 * �����������
	 */
	public void petSale(String pPk, String petPk)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoById(pPk);

		PetInfoDAO petInfoDAO = new PetInfoDAO();

		// ��ɫ��������
		int petPrice = petInfoDAO.getPetPriceByPetPk(petPk);
		logger.info("��������۸�:" + petPrice);
		// ������ɾ������
		petInfoDAO.getPetInfoDelte(petPk);

		// ���
		LogService logService = new LogService();
		logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo
				.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()
				+ "", petPrice + "", "������");

		// ��������ϼ�Ǯ
		roleInfo.getBasicInfo().addCopper(petPrice);

	}

	/**
	 * ������ ѧϰ���＼����ʱ ,�Լ�������ʱ, �������ּ���*��
	 */
	public void addSkillFlag(String petpk)
	{
		if (petpk == null || petpk.equals(""))
		{
			logger.info("ѧϰ����ʱ��*��ʱ,����idΪ��");
			return;
		}
		PetInfoDao infodao = new PetInfoDao();
		PetInfoVO infovo = infodao.getPet(Integer.parseInt(petpk));
		if (infovo.getPetName().contains("*"))
		{
			return;
		}
		else
		{
			if (infovo.getPetName().indexOf("*") == -1)
			{
				infodao.updatePetName(petpk);
			}

			if (infovo.getPetName().equals(infovo.getPetNickname()))
			{
				infodao.updateNickName(Integer.parseInt(petpk), infovo
						.getPetNickname()
						+ "*");
			}
		}

	}

	/**
	 * �õ�������Ϣwml�ű�
	 * 
	 * @param pet
	 * @return
	 */

	public String getOtherPetDisplayWml(int pet_pk)
	{
		PetInfoDao petInfoDao = new PetInfoDao();
		PetInfoVO pet = petInfoDao.getPet(pet_pk);

		return getOtherPetDisplayWml(pet);
	}

	private String getOtherPetDisplayWml(PetInfoVO pet)
	{
		PetSkillCache petSkillCache = new PetSkillCache();
		PicService picService = new PicService();
		if (pet == null)
		{
			logger.info("��������");
			return null;
		}
		if (pet.getPPk() < 0)
		{
			pet.setPPk(-pet.getPPk());
		}

		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoById(pet.getPPk() + "");

		StringBuffer resultWml = new StringBuffer();
		resultWml.append(picService.getPetPicStr(roleInfo, pet.getPetPk() + "")
				+ StringUtil.isoToGBK(pet.getPetNickname()) + "<br/>");
		// resultWml.append(picService.getPetPicStr(Math.abs(pet.getPPk())+"",
		// pet.getPetPk()+""));
		resultWml.append("����:" + StringUtil.isoToGBK(pet.getPetName())
				+ "<br/>");
		resultWml.append("�ȼ�:" + pet.getPetGrade() + "��<br/>");
		/** ***********TODO************************* */
		resultWml.append("����:" + pet.getPetBenExp() + "/" + pet.getPetXiaExp()
				+ "<br/>");
		if (petSkillCache.getInjureMultiple(pet.getPetSkillOne()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillTwo()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillThree()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillFour()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillFive()) != 0)
		{
			resultWml.append("����:"
					+ (Integer.valueOf((int) (pet.getPetGjXiao() * (1
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillOne())
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillTwo())
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillThree())
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillFour()) + petSkillCache
							.getInjureMultiple(pet.getPetSkillFive())))))
					+ "-"
					+ Integer.valueOf((int) (pet.getPetGjDa() * (1
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillOne())
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillTwo())
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillThree())
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillFour()) + petSkillCache
							.getInjureMultiple(pet.getPetSkillFive()))))
					+ "<br/>");
		}
		else
		{
			resultWml.append("����:" + pet.getPetGjXiao() + "-"
					+ pet.getPetGjDa() + "<br/>");

		}
		/** ***********TODO************************* */
		// resultWml.append("����:"+pet.getPetGjXiao()+"-"+pet.getPetGjDa()+"<br/>");
		// resultWml.append("��󹥻�:"+pet.getPetGjDa()+"<br/>");
		// resultWml.append("�۸�:"+pet.getPetSale()+"<br/>");
		resultWml.append("�ɳ���:" + pet.getPetGrow() + "<br/>");
		String wxValue = "";
		if (pet.getWxValue() != 0)
		{
			wxValue = "+" + pet.getWxValue();
		}
		// resultWml.append("��������ֵ:"+pet.getWxValue()+"<br/>");
		if (pet.getPetSkillOne() == 0 && pet.getPetSkillTwo() == 0
				&& pet.getPetSkillThree() == 0 && pet.getPetSkillFour() == 0
				&& pet.getPetSkillFive() == 0)
		{
			resultWml.append("����:��<br/>");
		}
		else
		{
			// resultWml.append("����:" +
			// petSkillDao.getName(pet.getPetSkillOne())
			// + petSkillDao.getName(pet.getPetSkillTwo())
			// + petSkillDao.getName(pet.getPetSkillThree())
			// + petSkillDao.getName(pet.getPetSkillFour())
			// + petSkillDao.getName(pet.getPetSkillFive()) + "<br/>");

			resultWml.append("����:"
					+ petSkillCache.getName(pet.getPetSkillOne())
					+ petSkillCache.getName(pet.getPetSkillTwo())
					+ petSkillCache.getName(pet.getPetSkillThree())
					+ petSkillCache.getName(pet.getPetSkillFour())
					+ petSkillCache.getName(pet.getPetSkillFive()) + "<br/>");
		}
		return resultWml.toString();
	}

	// �õ����＼�ܵ�����
	public String getPetSkillDisplay(PetInfoVO pet, int skill_id,
			String skill_name, HttpServletRequest request,
			HttpServletResponse response)
	{
		if (skill_name.equals(""))
		{
			return "";
		}
		else
		{
			StringBuffer resultWml = new StringBuffer();
			resultWml.append("<anchor> ");
			resultWml.append("<go method=\"post\" href=\""
					+ response.encodeURL(GameConfig.getContextPath()
							+ "/petinfoaction.do") + "\">");
			resultWml.append("<postfield name=\"cmd\" value=\"n7\" /> ");
			resultWml.append("<postfield name=\"petPk\" value=\""
					+ pet.getPetPk() + "\" /> ");
			resultWml.append("<postfield name=\"petGrade\" value=\""
					+ pet.getPetGrade() + "\" /> ");
			resultWml.append("<postfield name=\"petFatigue\" value=\""
					+ pet.getPetFatigue() + "\" /> ");
			resultWml.append("<postfield name=\"petIsBring\" value=\""
					+ pet.getPetIsBring() + "\" /> ");
			resultWml.append("<postfield name=\"skill_id\" value=\"" + skill_id
					+ "\" /> ");
			resultWml.append("</go>");
			resultWml.append(skill_name);
			resultWml.append("</anchor> ");
			return resultWml.toString();
		}
	}

	/**
	 * �õ�������Ϣwml�ű�
	 * 
	 * @param pet
	 * @return
	 */
	public String getPetDisplayWmlOwn(PetInfoVO pet,
			HttpServletRequest request, HttpServletResponse response)
	{
		PetSkillCache petSkillCache = new PetSkillCache();
		PicService picService = new PicService();
		if (pet == null)
		{
			logger.info("��������");
			return null;
		}
		if (pet.getPPk() < 0)
		{
			pet.setPPk(-pet.getPPk());
		}

		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoById(pet.getPPk() + "");

		StringBuffer resultWml = new StringBuffer();
		resultWml.append(picService.getPetPicStr(roleInfo, pet.getPetPk() + "")
				+ StringUtil.isoToGBK(pet.getPetNickname()) + "<br/>");
		// resultWml.append(picService.getPetPicStr(Math.abs(pet.getPPk())+"",
		// pet.getPetPk()+""));
		resultWml.append("����:" + StringUtil.isoToGBK(pet.getPetName())
				+ "<br/>");
		resultWml.append("״̬:");
		if (pet.getPetIsBring() == 1)
		{
			resultWml.append("ս��" + "<br/>");
		}
		else
		{
			resultWml.append("��Ϣ" + "<br/>");
		}
		resultWml.append("�ȼ�:" + pet.getPetGrade() + "��<br/>");
		/** ***********TODO************************* */
		resultWml.append("����:" + pet.getPetBenExp() + "/" + pet.getPetXiaExp()
				+ "<br/>");
		if (petSkillCache.getInjureMultiple(pet.getPetSkillOne()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillTwo()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillThree()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillFour()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillFive()) != 0)
		{
			resultWml.append("����:"
					+ (Integer.valueOf((int) (pet.getPetGjXiao() * (1
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillOne())
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillTwo())
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillThree())
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillFour()) + petSkillCache
							.getInjureMultiple(pet.getPetSkillFive())))))
					+ "-"
					+ Integer.valueOf((int) (pet.getPetGjDa() * (1
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillOne())
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillTwo())
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillThree())
							+ petSkillCache.getInjureMultiple(pet
									.getPetSkillFour()) + petSkillCache
							.getInjureMultiple(pet.getPetSkillFive()))))
					+ "<br/>");
		}
		else
		{
			resultWml.append("����:" + pet.getPetGjXiao() + "-"
					+ pet.getPetGjDa() + "<br/>");

		}
		/** ***********TODO************************* */
		// resultWml.append("����:"+pet.getPetGjXiao()+"-"+pet.getPetGjDa()+"<br/>");
		// resultWml.append("��󹥻�:"+pet.getPetGjDa()+"<br/>");
		// resultWml.append("�۸�:"+pet.getPetSale()+"<br/>");
		resultWml.append("�ɳ���:" + pet.getPetGrow() + "<br/>");
		String wxValue = "";
		if (pet.getWxValue() != 0)
		{
			wxValue = "+" + pet.getWxValue();
		}
		// resultWml.append("��������ֵ:"+pet.getWxValue()+"<br/>");
		if (pet.getPetSkillOne() == 0 && pet.getPetSkillTwo() == 0
				&& pet.getPetSkillThree() == 0 && pet.getPetSkillFour() == 0
				&& pet.getPetSkillFive() == 0)
		{
			resultWml.append("����:��<br/>");
		}
		else
		{
			// resultWml.append("����:" +
			// petSkillDao.getName(pet.getPetSkillOne())
			// + petSkillDao.getName(pet.getPetSkillTwo())
			// + petSkillDao.getName(pet.getPetSkillThree())
			// + petSkillDao.getName(pet.getPetSkillFour())
			// + petSkillDao.getName(pet.getPetSkillFive()) + "<br/>");
			String skill = "";
			if (pet.getPetSkillOne() != 0)
			{
				skill = skill
						+ getPetSkillDisplay(pet, pet.getPetSkillOne(),
								petSkillCache.getName(pet.getPetSkillOne()),
								request, response);
			}
			if (pet.getPetSkillTwo() != 0)
			{
				skill = skill
						+ getPetSkillDisplay(pet, pet.getPetSkillTwo(),
								petSkillCache.getName(pet.getPetSkillTwo()),
								request, response);
			}
			if (pet.getPetSkillThree() != 0)
			{
				skill = skill
						+ getPetSkillDisplay(pet, pet.getPetSkillThree(),
								petSkillCache.getName(pet.getPetSkillThree()),
								request, response);
			}
			if (pet.getPetSkillFour() != 0)
			{
				skill = skill
						+ getPetSkillDisplay(pet, pet.getPetSkillFour(),
								petSkillCache.getName(pet.getPetSkillFour()),
								request, response);
			}
			if (pet.getPetSkillFive() != 0)
			{
				skill = skill
						+ getPetSkillDisplay(pet, pet.getPetSkillFive(),
								petSkillCache.getName(pet.getPetSkillFive()),
								request, response);
			}

			resultWml.append("����:" + skill + "<br/>");
		}
		resultWml.append("����:" + pet.getPetLonge() + "/" + pet.getPetLife()
				+ "<br/>");
		resultWml.append("����:" + pet.getPetFatigue() + "<br/>");
		resultWml.append("�����۸�:" + pet.getPetSale() + "��<br/>");
		return resultWml.toString();
	}

	public String getPetDisplayWmlOwn(int pet_pk, HttpServletRequest request,
			HttpServletResponse response)
	{
		PetInfoDao petInfoDao = new PetInfoDao();
		PetInfoVO pet = petInfoDao.getPet(pet_pk);

		return getPetDisplayWmlOwn(pet, request, response);
	}
}
