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
 * 功能:宠物管理
 * 
 * @author 刘帅
 * 
 * 9:37:59 AM
 */
public class PetService
{

	Logger logger = Logger.getLogger("log.service");

	/**
	 * 宠物攻击npc
	 * 
	 * @param player
	 * @param npcs
	 */
	public void petAttackNpcs(Fighter player, List npcs)
	{
		if (player == null || npcs == null || npcs.size() == 0)
		{
			logger.debug("空指针");
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
			logger.debug("没有带宠物");
			return;
		}
		else
		{
			logger.debug("角色带宠物参加战斗！");
			player.setPet(pet);
		}

		PetSkillVO petSkill = getPetSkill(pet);

		// 得到宠物的被动技能加成属性
		List<Double> d = getPetPassSkillAttribute(pet);
		// 攻击伤害倍数
		double injureMultiple = 0;
		if (d.size() > 0 && d.get(0) != null)
		{
			injureMultiple = d.get(0);
		}
		// 暴击率加成
		double baojiMultiple = 0;
		if (d.size() > 1 && d.get(1) != null)
		{
			baojiMultiple = d.get(1);
		}
		if (petSkill != null && petSkill.getPetSkillType() != 0)// 发动技能攻击
		{
			petSkillAttackNpc(player, pet, petSkill, npcs, injureMultiple,
					baojiMultiple);
		}
		else
		// 发动物理攻击
		{
			petPhysicsAttackNpc(player, pet, petSkill, npcs, injureMultiple,
					baojiMultiple);
		}
		// 一次攻击改变宠物本身状态如寿命等
		// attackUpdateStat(player.getPPk());
	}

	// 寵物技能攻擊NPC
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
		}// 得到宠物的最终攻击力
		int physicsGj = physicsGj1 * 2;
		player.setPetSkillDisplay(StringUtil.isoToGBK(petSkill
				.getPetSkillName()));
		if (petSkill.getPetSkillType() == 5)
		{
			player
					.setPetSkillDisplay(StringUtil.isoToGBK(petSkill
							.getPetSkillName())
							+ ",三回合内攻击力提高"
							+ petSkill.getPetSkillMultiple()
							* 100 + "%");
		}
		int skillGj = MathUtil.getRandomBetweenXY(petSkill.getPetSkillGjXiao(),
				petSkill.getPetSkillGjDa());
		if (petSkill.getPetSkillArea() == 1)// 群体攻击
		{
			logger.info("宠物发动群体攻击技能");
			for (int i = npcs.size() - 1; i >= 0; i--)
			{
				npc = (NpcFighter) npcs.get(i);
				// 修改技能伤害公式
				skillInjure = AttackService.petSkillInjureExpressions(skillGj,
						pet.getPetGrade(), pet.getPetGrow(), npc
								.getNpcDefance(), npc.getLevel(), physicsGj);
				logger.info("宠物技能伤害:" + skillInjure);
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
			logger.info("宠物发动单体攻击技能");
			// skillInjure =
			// AttackService.normalInjureExpressions(skillGj,
			// npc.getNpcDefance(), pet.getPetGrade(), npc.getLevel());
			skillInjure = AttackService.petSkillInjureExpressions(skillGj, pet
					.getPetGrade(), pet.getPetGrow(), npc.getNpcDefance(), npc
					.getLevel(), physicsGj);
			if (petSkill.getPetSkillSeveral() != 1)// 判断攻击次数
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
					logger.info("宠物技能伤害:" + skillInjure);
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
				logger.info("宠物技能伤害:" + skillInjure);
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
		// 嗜血技能的判斷
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
				logger.debug("玩家加血" + injure);
			}
			int round = pet.getPetSkillDisplay().updateSkillRound(
					pet.getPetSkillDisplay().getPetSkillRound());
			pet.getPetSkillDisplay().setPetSkillRound(round);
		}
	}

	// 宠物物理攻擊NPC
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
						.getPetViolenceDrop() + baojiMultiple), 1))// 得到暴击率
		// 并判断是否产生暴击
		{

			injure = (physicsInjure) * 2;
			logger.info("宠物物理伤害:" + injure);

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
			player.setPetSkillDisplay("普通攻击,产生暴击");
			logger.debug("宠物发动普通攻击技能");

		}
		else
		{
			injure = physicsInjure;
			logger.info("宠物物理伤害:" + injure);

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
			player.setPetSkillDisplay("普通攻击");
			logger.debug("宠物发动普通攻击技能");
		}

	}

	/**
	 * 宠物攻击人
	 * 
	 * @param playerA
	 * @param playerB
	 */
	public void petAttackPlayer(Fighter playerA, Fighter playerB, String tong)
	{
		int wxInjure = 0;

		if (playerA == null || playerB == null)
		{
			logger.debug("空指针");
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
			logger.debug("没有带宠物");
			return;
		}
		else
		{
			logger.debug("角色带宠物参加战斗！");
			playerA.setPet(pet);
		}

		// PetDAO petdao = new PetDAO();
		PetCache petCache = new PetCache();

		// 得到宠物的被动技能加成属性
		List<Double> d = getPetPassSkillAttribute(pet);
		// 攻击伤害倍数
		double injureMultiple = 0;
		if (d.size() > 0 && d.get(0) != null)
		{
			injureMultiple = d.get(0);
		}
		// 暴击率加成
		double baojiMultiple = 0;
		if (d.size() > 1 && d.get(1) != null)
		{
			baojiMultiple = d.get(1);
		}

		int pet_npcId = petCache.getNpcIdById(pet.getPetId());

		wxInjure = AttackService.getPetWxInjureValue(pet, playerB.getWx(), pet
				.getPetGrade(), playerB.getPGrade(), pet_npcId) * 2 / 5;

		PetSkillVO petSkill = getPetSkill(pet);
		if (petSkill != null)// 发动技能攻击
		{
			petSkillAttackPK(pet, playerA, playerB, petSkill, tong, wxInjure,
					baojiMultiple, injureMultiple);
		}
		else
		// 发动物理攻击
		{
			petPhysicsAttackPK(pet, playerA, playerB, petSkill, tong, wxInjure,
					baojiMultiple, injureMultiple);
		}

		// 一次攻击改变宠物本身状态如寿命等
		// attackUpdateStat(playerA.getPPk());
	}

	// 寵物技能攻擊
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
							+ ",三回合内攻击力提高"
							+ petSkill.getPetSkillMultiple()
							* 100 + "%");
		}
		int skillGj1 = MathUtil.getRandomBetweenXY((int) (pet.getPetGjXiao()
				* (1 + injureMultiple) + petSkill.getPetSkillGjXiao()),
				(int) (pet.getPetGjDa() * (1 + injureMultiple) + petSkill
						.getPetSkillGjDa()));
		// 5月5号 宠物PK 攻击x4
		int physicsGj = physicsGj1 * 4;
		int skillGj = skillGj1 * 4;

		if (petSkill.getPetSkillSeveral() != 1)// 判断攻击次数
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
				logger.info("宠物发动技能攻击技能,技能伤害=" + injure + " ,wxInjure="
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
					.debug("宠物发动技能攻击技能,技能伤害=" + injure + " ,wxInjure="
							+ wxInjure);

		}
		logger.debug("宠物五行攻击伤害值:" + wxInjure + skillInjure);
		// 嗜血技能的判斷
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
				logger.debug("玩家加血" + injure);
			}
			int round = pet.getPetSkillDisplay().updateSkillRound(
					pet.getPetSkillDisplay().getPetSkillRound());
			pet.getPetSkillDisplay().setPetSkillRound(round);
		}
	}

	// 寵物普通攻擊
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
		// 5月5号 宠物PK 攻击x4
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
			playerA.setPetSkillDisplay("普通攻击,产生暴击");
			logger.info("宠物发动普通攻击技能，普通伤害(双倍)=" + injure + " ,wxInjure="
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
			playerA.setPetSkillDisplay("普通攻击");
			logger
					.debug("宠物发动普通攻击技能，普通伤害=" + injure + " ,wxInjure="
							+ wxInjure);
		}
		logger.debug("宠物五行攻击伤害值:" + wxInjure + physicsInjure);
		int round = pet.getPetSkillDisplay().updateSkillRound(
				pet.getPetSkillDisplay().getPetSkillRound());
		pet.getPetSkillDisplay().setPetSkillRound(round);
	}

	/**
	 * 得到宠物的使用技能
	 */
	public PetSkillVO getPetSkill(PetInfoVO pet)
	{
		PetSkillCache petSkillCache = new PetSkillCache();

		PetSkillVO petSkill = null;
		// PetSkillDao dao = new PetSkillDao();
		List<PetSkillControlVO> petControls = new ArrayList<PetSkillControlVO>();
		PetSkillControlVO petControl = null;
		PetSkillControlDao petControlDao = new PetSkillControlDao();
		if (petSkillCache.getPetSkillType(pet.getPetSkillOne()) != 0)// 判断技能类型是否为主动技能
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
		if (petSkillCache.getPetSkillType(pet.getPetSkillTwo()) != 0)// 判断技能类型是否为主动技能
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
		if (petSkillCache.getPetSkillType(pet.getPetSkillThree()) != 0)// 判断技能类型是否为主动技能
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
		if (petSkillCache.getPetSkillType(pet.getPetSkillFour()) != 0)// 判断技能类型是否为主动技能
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
		if (petSkillCache.getPetSkillType(pet.getPetSkillFive()) != 0)// 判断技能类型是否为主动技能
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
	 * 得到宠物被动技能的属性
	 * 
	 * @param PetInfoVO
	 * @return list 第一位是伤害倍数 第二位是暴击率加成
	 */
	public List<Double> getPetPassSkillAttribute(PetInfoVO pet)
	{
		PetSkillDao dao = new PetSkillDao();
		PetSkillVO vo = null;
		List<Double> list = new ArrayList<Double>();
		List<PetSkillVO> skilllist = dao.getPassSkill(pet.getPetSkillOne(), pet
				.getPetSkillTwo(), pet.getPetSkillThree(), pet
				.getPetSkillFour(), pet.getPetSkillFive());
		// 得到宠物被动技能的加成属性 按最大值得到 该处得到的是伤害倍数
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
		// 得到宠物被动技能的加成属性 按最大值得到 该处得到的是暴击率加成
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
	 * 根据宠物控制表得到宠物技能
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
		if (petSkillControl == null)// 没有发动技能攻击
		{
			return null;
		}
		else
		{
			return petSkillControl;
		}
	}

	/**
	 * 得到宠物技能所在的组id
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
	 * 更改宠物昵称
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
	 * 用道具洗宠物
	 * 
	 * @param pet_pk
	 *            宠物id
	 * @param pg_pk
	 *            道具组id
	 * @return
	 */
	public String initPetByProp(int pet_pk, int pg_pk)
	{
		GoodsService goodsService = new GoodsService();
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();

		PlayerPropGroupVO propGroup = null;
		propGroup = propGroupDao.getByPgPk(pg_pk);// 得到道具信息

		goodsService.removeProps(propGroup, 1);// 删除洗宠物的道具
		initPet(pet_pk);// 执行宠物洗点

		// 出战宠物信息重载
		RoleCache roleCache = new RoleCache();
		RoleEntity roleEntity = roleCache.getByPpk(propGroup.getPPk() + "");
		RolePetInfo userPet = roleEntity.getRolePetInfo();
		userPet.initPet(pet_pk, propGroup.getPPk());

		return null;
	}

	/**
	 * 返回所查看宠物的寿命
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
	 * 宠物洗点,重新初始化宠物
	 */
	private void initPet(int pet_pk)
	{
		PetInfoDao petInfoDao = new PetInfoDao();
		PetDAO petDAO = new PetDAO();

		PetInfoVO player_pet = petInfoDao.getPet(pet_pk);// 得到洗宠物的信息
		PetVO pet = petDAO.getPetViewByPetID(player_pet.getPetId());// 通过宠物ID
		// 得到宠物表
		PetShapeVO petShapeVO = (PetShapeVO) petDAO.getPetShapeView(player_pet
				.getPetType(), 1);// 通过宠物等级和宠物ID和宠物类型 去找宠物成长信息

		String pet_grow_str = "";
		double pet_grow = 0;// 成长率
		int pet_xia_exp = 0;// 下级经验

		int gj_xiao = 0;// 攻击小
		int gj_da = 0;// 攻击大

		int pet_sale = 0;// 卖出价格

		int pet_skill_one = 0;//
		int pet_skill_two = 0;//
		int pet_skill_three = 0;//
		int pet_skill_four = 0;//
		int pet_skill_five = 0;//

		int grow_quality = getGrowQuality(pet_grow);// 得到成长率的品质
		if (player_pet.getPetInitNum() == 0)// 判断是不是第一次洗宠物
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
		 * pet.getPetSkillOne(); //30% logger.info("第1种技能100%可能已经产生附着"); }
		 * }if(pet.getPetSkillTwo()!=0){
		 * if(MathUtil.isAppearByPercentage(50,100)){ pet_skill_two =
		 * pet.getPetSkillTwo(); //3% logger.info("第2种技能50%可能已经产生附着"); }
		 * }if(pet.getPetSkillThree()!=0){
		 * if(MathUtil.PercentageRandomByParamdouble(10,100)){ pet_skill_three =
		 * pet.getPetSkillThree();//% logger.info("第3种技能10%可能已经产生附着"); }
		 * }if(pet.getPetSkillFour()!=0){
		 * if(MathUtil.PercentageRandomByParamdouble(1,100)){ pet_skill_four =
		 * pet.getPetSkillFour();//0.03% logger.info("第4种技能1%可能已经产生附着"); }
		 * }if(pet.getPetSkillFive()!=0){
		 * if(MathUtil.PercentageRandomByParamdouble(0.1,100)){ pet_skill_five =
		 * pet.getPetSkillFive();//0.003% logger.info("第5种技能0.1%可能已经产生附着"); } }
		 */
		if (pet.getSkillControl() != 0)
		{ // 只有在宠物拥有技能数不为零时需要计算获得何种技能

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
					logger.info("第1种技能100%可能已经产生附着");
				}
				if (MathUtil.isAppearByPercentage(50, 100))
				{
					if (skill_num < pet.getSkillControl())
					{ // 在已有的技能数小于宠物所应有的技能情况下,才能继续.
						do
						{
							i = random.nextInt(list.size());
							pet_skill_two = list.get(i);
							logger.info("第2种技能50%可能已经产生附着");
							skill_num++;
						} while (pet_skill_two == pet_skill_one
								|| pet.getSkillControl() == 1);
					}
				}
				if (MathUtil.PercentageRandomByParamdouble(10, 100))
				{
					if (skill_num < pet.getSkillControl())
					{ // 在已有的技能数小于宠物所应有的技能情况下,才能继续.
						do
						{
							i = random.nextInt(list.size());
							pet_skill_three = list.get(i);
							logger.info("第3种技能10%可能已经产生附着");
							skill_num++;
						} while (pet_skill_three == pet_skill_one
								|| pet_skill_three == pet_skill_two);
					}
				}
				if (MathUtil.PercentageRandomByParamdouble(1, 100))
				{
					if (skill_num < pet.getSkillControl())
					{ // 在已有的技能数小于宠物所应有的技能情况下,才能继续.
						do
						{
							i = random.nextInt(list.size());
							pet_skill_four = list.get(i);
							logger.info("第4种技能1%可能已经产生附着");
							skill_num++;
						} while (pet_skill_four == pet_skill_one
								|| pet_skill_four == pet_skill_two
								|| pet_skill_four == pet_skill_three);
					}
				}
				if (MathUtil.PercentageRandomByParamdouble(0.1, 100))
				{
					if (skill_num < pet.getSkillControl())
					{ // 在已有的技能数小于宠物所应有的技能情况下,才能继续.
						do
						{
							i = random.nextInt(list.size());
							pet_skill_five = list.get(i);
							logger.info("第5种技能0.1%可能已经产生附着");
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
	 * 得到成长率的品质
	 */
	public int getGrowQuality(double pet_grow)
	{
		int grow_quality = 0;
		if (pet_grow >= 1.0 && pet_grow <= 1.5)// 正品
		{
			grow_quality = 0;
		}
		else
			if (pet_grow >= 1.6 && pet_grow <= 2.0)// 上品
			{
				grow_quality = 1;
			}
			else
				if (pet_grow >= 2.1 && pet_grow <= 2.5)// 极品
				{
					grow_quality = 2;
				}
		return grow_quality;
	}

	/**
	 * 根据成长率的品质得到成长率
	 */
	public double getGrowByQuality(int grow_qulity)
	{
		return MathUtil.getRandomDoubleXY(1.0, 1.5);
	}

	/**
	 * 根据成长率的品质得到成长率
	 */
	public double getGrowByQuality(double petDropXiao, double petDropDa)
	{
		return MathUtil.getRandomDoubleXY(petDropXiao, petDropDa);
	}

	/**
	 * 将宠物置于出战或 不出战的状态
	 * 
	 * @param petIsBring
	 *            1为出战,0为不出战.
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
			userPet.unBringpet(); // 将宠物置于未出战状态
			dao.petIsBring(pPk, petPk, petIsBring);
		}
		else
			if (petIsBring == 1)
			{

				int petpk = dao.getPetPk(pPk); // 其身上 是否有
				// 如果 有参战的宠物,则需将其休战
				if (petPk > 0)
				{
					dao.petIsBring(pPk, petpk, 0);
					userPet.unBringpet(); // 将宠物置于未出战状态
				}

				// 将宠物出战
				dao.petIsBring(pPk, petPk, petIsBring);
				userPet.bringPet(petPk);

			}
	}

	/**
	 * 根据宠物id构造一个宠物
	 * 
	 * （11.26宠物技能附着改正, 原来为附着的技能严格控制为pet表中所填的对应的技能, 现在修改为宠物技能如果附着成功,
	 * 所得到的技能是此pet所可能具有的任何一个技能.
	 */
	public String createPetByPetID(int p_pk, int pet_id)
	{
		String hint = null;

		PetDAO petDAO = new PetDAO();
		PetVO pet = (PetVO) petDAO.getPetViewByPetID(pet_id);
		if (pet != null)
		{

			/** ***************宠物技能附着****************** */
			/** 技能1 可学习的技能id */
			String petSkillOne = "0";
			/** 技能2 可学习的技能id */
			String petSkillTwo = "0";
			/** 技能3 可学习的技能id */
			String petSkillThree = "0";
			/** 技能4 可学习的技能id */
			String petSkillFour = "0";
			/** 技能5 可学习的技能id */
			String petSkillFive = "0";
			/**
			 * if(pet.getPetSkillOne()!=0){
			 * if(MathUtil.isAppearByPercentage(30,100)){ petSkillOne =
			 * pet.getPetSkillOne()+""; //30% logger.info("第1种技能30%可能已经产生附着"); }
			 * }if(pet.getPetSkillTwo()!=0){
			 * if(MathUtil.isAppearByPercentage(3,100)){ petSkillTwo =
			 * pet.getPetSkillTwo()+""; //3% logger.info("第2种技能3%可能已经产生附着"); }
			 * }if(pet.getPetSkillThree()!=0){
			 * if(MathUtil.PercentageRandomByParamdouble(0.3,100)){
			 * petSkillThree = pet.getPetSkillThree()+"";//%
			 * logger.info("第3种技能0.3%可能已经产生附着"); }
			 * }if(pet.getPetSkillFour()!=0){
			 * if(MathUtil.PercentageRandomByParamdouble(0.03,100)){
			 * petSkillFour = pet.getPetSkillFour()+"";//0.03%
			 * logger.info("第4种技能0.03%可能已经产生附着"); }
			 * }if(pet.getPetSkillFive()!=0){
			 * if(MathUtil.PercentageRandomByParamdouble(0.003,100)){
			 * petSkillFive = pet.getPetSkillFive()+"";//0.003%
			 * logger.info("第5种技能0.003%可能已经产生附着"); } }
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
				{ // 在已有的技能数小于宠物所应有的技能情况下,才能继续.
					i = random.nextInt(5);
					petSkillOne = list.get(i) + "";
					skill_num++;
					logger.info("第1种技能30%可能已经产生附着");
				}
			}
			if (MathUtil.isAppearByPercentage(3, 100))
			{
				if (skill_num < pet.getSkillControl())
				{ // 在已有的技能数小于宠物所应有的技能情况下,才能继续.
					do
					{
						i = random.nextInt(5);
						petSkillTwo = list.get(i) + "";
						skill_num++;
					} while (petSkillTwo == petSkillOne);
				}
				logger.info("第2种技能3%可能已经产生附着");
			}
			if (MathUtil.PercentageRandomByParamdouble(0.3, 100))
			{
				if (skill_num < pet.getSkillControl())
				{ // 在已有的技能数小于宠物所应有的技能情况下,才能继续.
					do
					{
						i = random.nextInt(5);
						petSkillThree = list.get(i) + "";
						logger.info("第3种技能0.3%可能已经产生附着");
						skill_num++;
					} while (petSkillThree == petSkillOne
							|| petSkillThree == petSkillTwo);
				}
			}
			if (MathUtil.PercentageRandomByParamdouble(0.03, 100))
			{
				if (skill_num < pet.getSkillControl())
				{ // 在已有的技能数小于宠物所应有的技能情况下,才能继续.
					do
					{
						i = random.nextInt(5);
						petSkillFour = list.get(i) + "";
						logger.info("第4种技能0.03%可能已经产生附着");
						skill_num++;
					} while (petSkillFour == petSkillOne
							|| petSkillFour == petSkillTwo
							|| petSkillFour == petSkillThree);
				}
			}

			if (MathUtil.PercentageRandomByParamdouble(0.003, 100))
			{
				if (skill_num < pet.getSkillControl())
				{ // 在已有的技能数小于宠物所应有的技能情况下,才能继续.

					do
					{
						i = random.nextInt(5);
						petSkillFive = list.get(i) + "";
						logger.info("第5种技能0.003%可能已经产生附着");
					} while (petSkillFive == petSkillOne
							|| petSkillFive == petSkillTwo
							|| petSkillFive == petSkillThree
							|| petSkillFive == petSkillFour);
				}
			}

			/** ***************以下是取出宠物信息然后换算成捕捉后的宠物信息****************** */
			/** 宠物成长率 */
			double petDropDa = pet.getPetDropDa();
			/** 宠物成长率 */
			double petDropXiao = pet.getPetDropXiao();
			double pet_grow = MathUtil
					.getRandomDoubleXY(petDropXiao, petDropDa);

			/** 宠物成长率” */
			DecimalFormat df = new DecimalFormat("0.00");
			String pet_grow_str = df.format(pet_grow);
			/** 角色id */
			String pPk = p_pk + "";
			/** 宠物图片 */
			String petPic = pet.getPetImg();
			/** 对应pet表里的id */
			String petId = pet.getPetId() + "";
			/** 宠物名称 */
			String petName = pet.getPetName();
			/** 宠物昵称 */
			String petNickname = pet.getPetName();
			/** 等级 */
			String petGrade = "1";
			// 通过宠物等级和宠物ID和宠物类型 去找宠物成长信息
			PetShapeVO petShapeVO = (PetShapeVO) petDAO.getPetShapeView(pet
					.getPetType(), 1);
			if (petShapeVO == null)
			{
				hint = "成长表错误";
				return hint;
			}

			/** 经验 */
			String petExp = "0";
			/** 下级经验达到下一级需要的经验 */
			int petXiaExps = (int) (Integer.parseInt(petShapeVO
					.getShapeXiaExperience().trim()) * pet_grow);
			String petXiaExp = petXiaExps + "";

			/** 最小攻击 */
			int petGjXiao = (int) (15 + 4 * pet_grow);
			;
			/** 最大攻击 */
			int petGjDa = petGjXiao;

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
			hint = "创建宠物失败";
		}
		return hint;
	}

	/**
	 * 得到宠物详细信息
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
	 * 得到宠物信息wml脚本
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
	 * 得到宠物信息wml脚本
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
			logger.info("参数错误");
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
		resultWml.append("宠物:" + StringUtil.isoToGBK(pet.getPetName())
				+ "<br/>");
		resultWml.append("状态:");
		if (pet.getPetIsBring() == 1)
		{
			resultWml.append("战斗" + "<br/>");
		}
		else
		{
			resultWml.append("休息" + "<br/>");
		}
		resultWml.append("等级:" + pet.getPetGrade() + "级<br/>");
		/** ***********TODO************************* */
		resultWml.append("经验:" + pet.getPetBenExp() + "/" + pet.getPetXiaExp()
				+ "<br/>");
		if (petSkillCache.getInjureMultiple(pet.getPetSkillOne()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillTwo()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillThree()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillFour()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillFive()) != 0)
		{
			resultWml.append("攻击:"
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
			resultWml.append("攻击:" + pet.getPetGjXiao() + "-"
					+ pet.getPetGjDa() + "<br/>");

		}
		/** ***********TODO************************* */
		// resultWml.append("攻击:"+pet.getPetGjXiao()+"-"+pet.getPetGjDa()+"<br/>");
		// resultWml.append("最大攻击:"+pet.getPetGjDa()+"<br/>");
		// resultWml.append("价格:"+pet.getPetSale()+"<br/>");
		resultWml.append("成长率:" + pet.getPetGrow() + "<br/>");
		String wxValue = "";
		if (pet.getWxValue() != 0)
		{
			wxValue = "+" + pet.getWxValue();
		}
		// resultWml.append("五行属性值:"+pet.getWxValue()+"<br/>");
		if (pet.getPetSkillOne() == 0 && pet.getPetSkillTwo() == 0
				&& pet.getPetSkillThree() == 0 && pet.getPetSkillFour() == 0
				&& pet.getPetSkillFive() == 0)
		{
			resultWml.append("技能:无<br/>");
		}
		else
		{
			// resultWml.append("技能:" +
			// petSkillDao.getName(pet.getPetSkillOne())
			// + petSkillDao.getName(pet.getPetSkillTwo())
			// + petSkillDao.getName(pet.getPetSkillThree())
			// + petSkillDao.getName(pet.getPetSkillFour())
			// + petSkillDao.getName(pet.getPetSkillFive()) + "<br/>");

			resultWml.append("技能:"
					+ petSkillCache.getName(pet.getPetSkillOne())
					+ petSkillCache.getName(pet.getPetSkillTwo())
					+ petSkillCache.getName(pet.getPetSkillThree())
					+ petSkillCache.getName(pet.getPetSkillFour())
					+ petSkillCache.getName(pet.getPetSkillFive()) + "<br/>");
		}
		resultWml.append("寿命:" + pet.getPetLonge() + "/" + pet.getPetLife()
				+ "<br/>");
		resultWml.append("体力:" + pet.getPetFatigue() + "<br/>");
		resultWml.append("卖出价格:" + pet.getPetSale() + "文<br/>");
		return resultWml.toString();
	}

	/**
	 * 得到可以被洗的宠物，没有在身上的且十级一上的包括十级 (11.25优化, 可以被洗的包括一级以上的都可以)
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
	 * 得到宠物数量
	 * 
	 * @param p_pk
	 */
	public int getNumOfPet(int p_pk)
	{
		PetInfoDao petInfoDao = new PetInfoDao();
		return petInfoDao.getNumOfPet(p_pk);
	}

	/**
	 * 处理宠物卖出
	 */
	public void petSale(String pPk, String petPk)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoById(pPk);

		PetInfoDAO petInfoDAO = new PetInfoDAO();

		// 角色卖出宠物
		int petPrice = petInfoDAO.getPetPriceByPetPk(petPk);
		logger.info("卖出宠物价格:" + petPrice);
		// 从身上删除宠物
		petInfoDAO.getPetInfoDelte(petPk);

		// 监控
		LogService logService = new LogService();
		logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo
				.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()
				+ "", petPrice + "", "卖宠物");

		// 向玩家身上加钱
		roleInfo.getBasicInfo().addCopper(petPrice);

	}

	/**
	 * 当宠物 学习宠物技能书时 ,以及领悟技能时, 将其名字加上*号
	 */
	public void addSkillFlag(String petpk)
	{
		if (petpk == null || petpk.equals(""))
		{
			logger.info("学习技能时加*号时,宠物id为零");
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
	 * 得到宠物信息wml脚本
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
			logger.info("参数错误");
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
		resultWml.append("宠物:" + StringUtil.isoToGBK(pet.getPetName())
				+ "<br/>");
		resultWml.append("等级:" + pet.getPetGrade() + "级<br/>");
		/** ***********TODO************************* */
		resultWml.append("经验:" + pet.getPetBenExp() + "/" + pet.getPetXiaExp()
				+ "<br/>");
		if (petSkillCache.getInjureMultiple(pet.getPetSkillOne()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillTwo()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillThree()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillFour()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillFive()) != 0)
		{
			resultWml.append("攻击:"
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
			resultWml.append("攻击:" + pet.getPetGjXiao() + "-"
					+ pet.getPetGjDa() + "<br/>");

		}
		/** ***********TODO************************* */
		// resultWml.append("攻击:"+pet.getPetGjXiao()+"-"+pet.getPetGjDa()+"<br/>");
		// resultWml.append("最大攻击:"+pet.getPetGjDa()+"<br/>");
		// resultWml.append("价格:"+pet.getPetSale()+"<br/>");
		resultWml.append("成长率:" + pet.getPetGrow() + "<br/>");
		String wxValue = "";
		if (pet.getWxValue() != 0)
		{
			wxValue = "+" + pet.getWxValue();
		}
		// resultWml.append("五行属性值:"+pet.getWxValue()+"<br/>");
		if (pet.getPetSkillOne() == 0 && pet.getPetSkillTwo() == 0
				&& pet.getPetSkillThree() == 0 && pet.getPetSkillFour() == 0
				&& pet.getPetSkillFive() == 0)
		{
			resultWml.append("技能:无<br/>");
		}
		else
		{
			// resultWml.append("技能:" +
			// petSkillDao.getName(pet.getPetSkillOne())
			// + petSkillDao.getName(pet.getPetSkillTwo())
			// + petSkillDao.getName(pet.getPetSkillThree())
			// + petSkillDao.getName(pet.getPetSkillFour())
			// + petSkillDao.getName(pet.getPetSkillFive()) + "<br/>");

			resultWml.append("技能:"
					+ petSkillCache.getName(pet.getPetSkillOne())
					+ petSkillCache.getName(pet.getPetSkillTwo())
					+ petSkillCache.getName(pet.getPetSkillThree())
					+ petSkillCache.getName(pet.getPetSkillFour())
					+ petSkillCache.getName(pet.getPetSkillFive()) + "<br/>");
		}
		return resultWml.toString();
	}

	// 得到宠物技能的描述
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
	 * 得到宠物信息wml脚本
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
			logger.info("参数错误");
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
		resultWml.append("宠物:" + StringUtil.isoToGBK(pet.getPetName())
				+ "<br/>");
		resultWml.append("状态:");
		if (pet.getPetIsBring() == 1)
		{
			resultWml.append("战斗" + "<br/>");
		}
		else
		{
			resultWml.append("休息" + "<br/>");
		}
		resultWml.append("等级:" + pet.getPetGrade() + "级<br/>");
		/** ***********TODO************************* */
		resultWml.append("经验:" + pet.getPetBenExp() + "/" + pet.getPetXiaExp()
				+ "<br/>");
		if (petSkillCache.getInjureMultiple(pet.getPetSkillOne()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillTwo()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillThree()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillFour()) != 0
				|| petSkillCache.getInjureMultiple(pet.getPetSkillFive()) != 0)
		{
			resultWml.append("攻击:"
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
			resultWml.append("攻击:" + pet.getPetGjXiao() + "-"
					+ pet.getPetGjDa() + "<br/>");

		}
		/** ***********TODO************************* */
		// resultWml.append("攻击:"+pet.getPetGjXiao()+"-"+pet.getPetGjDa()+"<br/>");
		// resultWml.append("最大攻击:"+pet.getPetGjDa()+"<br/>");
		// resultWml.append("价格:"+pet.getPetSale()+"<br/>");
		resultWml.append("成长率:" + pet.getPetGrow() + "<br/>");
		String wxValue = "";
		if (pet.getWxValue() != 0)
		{
			wxValue = "+" + pet.getWxValue();
		}
		// resultWml.append("五行属性值:"+pet.getWxValue()+"<br/>");
		if (pet.getPetSkillOne() == 0 && pet.getPetSkillTwo() == 0
				&& pet.getPetSkillThree() == 0 && pet.getPetSkillFour() == 0
				&& pet.getPetSkillFive() == 0)
		{
			resultWml.append("技能:无<br/>");
		}
		else
		{
			// resultWml.append("技能:" +
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

			resultWml.append("技能:" + skill + "<br/>");
		}
		resultWml.append("寿命:" + pet.getPetLonge() + "/" + pet.getPetLife()
				+ "<br/>");
		resultWml.append("体力:" + pet.getPetFatigue() + "<br/>");
		resultWml.append("卖出价格:" + pet.getPetSale() + "文<br/>");
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
