package com.ls.web.service.player;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ben.shitu.model.ShituConstant;
import com.ls.ben.cache.dynamic.manual.attack.AttacckCache;
import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.dao.info.skill.PlayerSkillDao;
import com.ls.ben.dao.info.skill.SkillDao;
import com.ls.ben.vo.info.attack.DropExpMoneyVO;
import com.ls.ben.vo.info.attack.DropGoodsVO;
import com.ls.ben.vo.info.attack.FightList;
import com.ls.ben.vo.info.npc.NpcAttackVO;
import com.ls.ben.vo.info.npc.NpcFighter;
import com.ls.ben.vo.info.partinfo.Fighter;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.skill.PlayerSkillVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.model.user.RoleEntity;
import com.ls.model.vip.Vip;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.MapType;
import com.ls.pub.constant.PropType;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.constant.buff.BuffSystem;
import com.ls.pub.util.MathUtil;
import com.ls.web.service.attack.AttackService;
import com.ls.web.service.buff.BuffEffectService;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipService;
import com.ls.web.service.group.GroupService;
import com.ls.web.service.instance.InstanceService;
import com.ls.web.service.npc.NpcService;
import com.ls.web.service.rank.RankService;
import com.ls.web.service.room.RoomService;
import com.ls.web.service.skill.SkillService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.lw.service.menpaicontest.MenpaiContestService;
import com.pub.GameArgs;
import com.web.service.expnpcdrop.ExpnpcdropService;

/**
 * 功能:处理玩家战斗相关事物
 * 
 * @author 刘帅 4:53:40 PM
 */
public class FightService
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * 玩家A主动攻击玩家B
	 * 
	 * @param playerA
	 * @param playerB
	 */
	public void attackPlayer(Fighter playerA, Fighter playerB)
	{
		if (playerA == null || playerB == null)
		{
			logger.debug("参数错误！");
			return;
		}

		if (playerA.getSkill() == null)
		{
			// 物理攻击

			physicsAttackPlayer(playerA, playerB);
		}
		else
		{
			// 技能攻击
			skillAttackPlayer(playerA, playerB);
		}
		// 打人PK减去武器持久
		EquipService equipService = new EquipService();
		equipService.useWeapon(playerA.getPPk());
		// 挨打PK减去武器持久
		equipService.useEquip(playerB.getPPk());
	}

	/**
	 * pk时技能攻击
	 * @param playerA
	 * @param playerB
	 * @param bout
	 */
	private void skillAttackPlayer(Fighter playerA, Fighter playerB)
	{
		//判断参数为空则返回不作处理
		if (playerA == null || playerA == null)
		{
			logger.debug("参数错误");
			return;
		}
		PlayerSkillVO playerSkill = playerA.getSkill();
		//判断技能为空则返回不作处理
		if (playerSkill == null)
		{
			logger.debug("玩家没有装载技能");
			return;
		}
		//表示技能是否可以使用
		boolean isUse = true;
		//如果MP不足则不能使用技能
		if(playerSkill.getSkExpend().equals("MP"))
		{
			if (playerSkill.getSkUsecondition() > playerA.getPMp())
			{
				isUse = false;
				playerA.setSkillNoUseDisplay("内力不足，不能使用此技能<br/>");
				playerA.setSkill(null);
			}
			else
			{
				//扣除所使用的MP
				playerA.setExpendMP(playerSkill.getSkUsecondition());
				PropertyService propertyService = new PropertyService();
				propertyService.updateMpProperty(playerA.getPPk(), playerA.getPMp());
			}
		}
		// 如果不可用则不做处理返回
		if (!isUse)
		{
			return;
		}
		//技能攻击技能攻击技能攻击技能攻击技能攻击技能攻击
		int injure =this.getInjure(playerA, playerB, playerSkill);
		logger.debug("技能伤害是:" + injure);
		// 更新技能使用情况
		SkillService sse = new SkillService();
		sse.useSkill(playerA.getPPk(), playerA.getSkill());
		// 更新playerB状态
		playerB.setPlayerInjure(injure);
		statUpdateByPlayerInjure(playerB, playerA, -1);
		//师门技能给玩家附加的BUFF效果
		givePlayerBuff(playerB, playerSkill);
	}

	/**
	 * pk时物理攻击
	 * 
	 * @param playerA
	 * @param playerB
	 * @param bout
	 */
	public void physicsAttackPlayer(Fighter playerA, Fighter playerB)
	{
		if (playerA == null || playerB == null)
		{
			logger.debug("参数错误！");
			return;
		}
		int injure = 0;
		int wxInjure = 0;
		int physicsInjure = 0;
		// 得到五行伤害
		wxInjure = AttackService.getWxInjureValue(playerA.getWx(), playerB.getWx(), playerA.getPGrade(), playerB.getPGrade(), playerA.getPPk());
		// 得到物理伤害
		physicsInjure = AttackService.normalInjureExpressions(getPlayerActBySkillContent(playerA, playerA.getGj()),getPlayerDefBySkillContent(playerB, playerB.getFy()), playerA.getPGrade(), playerB.getPGrade());
		//判断对方如果已经中毒则攻击加强
		RoleEntity roleInfoB = RoleCache.getByPpk(playerB.getPPk());
		int huihe= roleInfoB.getBasicInfo().getActhuihe();
		if(roleInfoB.getBasicInfo().isPoisoning()&&roleInfoB.getBasicInfo().getAddDamage()>0&&roleInfoB.getBasicInfo().getPoisonCount()>0)
		{
			physicsInjure+=roleInfoB.getBasicInfo().getAddDamage();
		}
		//判断有一定几率使攻击产生暴击的效果，暴击伤害增加2倍的效果
		if (MathUtil.PercentageRandomByParamdouble((playerA.getDropMultiple()),
				100))
		{
			playerA.setSkillDisplay("普通攻击,产生了暴击!");
			// 人对玩家的技能伤害=玩家攻击玩家的技能伤害/1.5+玩家对玩家的五行伤害；
			injure = (wxInjure + physicsInjure * 3 / 2);// 爆机伤害2倍
		}
		else
		{
			playerA.setSkillDisplay("普通攻击.");
			// 人对玩家的技能伤害=玩家攻击玩家的技能伤害/1.5+玩家对玩家的五行伤害；
			injure = (wxInjure + physicsInjure * 3 / 2) / 2;
		}
		if (injure <= 0)
		{
			injure = 1;
		}

		logger.debug("物理伤害是:" + injure);

		// 更新playerB状态
		playerB.setPlayerInjure(injure);
		statUpdateByPlayerInjure(playerB,playerA,-1);
	}

	/**
	 * 自动反击时随机加载一个可以使用的主动攻击技能
	 * 
	 * @param playerA
	 * @return true表示加载成功，false表示加载失败
	 */
	private boolean loadRandAttackSkill(Fighter playerA)
	{
		int p_pk = playerA.getPPk();

		SkillService skillService = new SkillService();

		// 得到玩家身上的主动攻击所有技能
		List<PlayerSkillVO> skillList = skillService.getAttackSkills(p_pk);

		if (skillList != null && skillList.size() != 0)
		{
			SkillDao skillDao = new SkillDao();
			PlayerSkillVO skill = null;
			for (int i = skillList.size() - 1; i >= 0; i--)
			{
				skill = skillList.get(i);

				skillDao.loadPlayerSkillDetailByInside(skill);// 加载技能属性详情

				if (skillService.isUsabled(playerA, skill) != null)
				{
					// 技能不能使用
					skillList.remove(i);
				}
			}
		}

		if (skillList != null && skillList.size() != 0)
		{
			int rand_index = MathUtil.getRandomBetweenXY(0,
					skillList.size() - 1);

			PlayerSkillVO rand_skill = skillList.get(rand_index);// 得到随机技能
			playerA.setSkill(rand_skill);
			return true;
		}
		else
		{
			return false;
		}

	}

	/**
	 * pk时玩家自动反击，playerA反击playerB，只有物理攻击
	 * 
	 * @param playerA
	 * @param playerB
	 * @param bout
	 */
	public void autoCounterattack(Fighter playerA, Fighter playerB)
	{
		boolean is_successed = loadRandAttackSkill(playerA);// 加载一个可用的随机主动技能

		RoleEntity roleInfoA = RoleCache.getByPpk(playerA.getPPk());
		if(roleInfoA.getBasicInfo().getXuanyunhuihe() >0){
			roleInfoA.getBasicInfo().setXuanyunhuihe(roleInfoA.getBasicInfo().getXuanyunhuihe() - 1);
			return;
		}	
		if (is_successed == true)// 玩家有技能发动，使用技能攻击
		{
			skillAttackPlayer(playerA, playerB);
		}
		else
		{
			physicsAttackPlayer(playerA, playerB);
		}

		// 打人PK减去武器持久
		EquipService equipService = new EquipService();
		equipService.useWeapon(playerA.getPPk());
		// 挨打PK减去武器持久
		equipService.useEquip(playerB.getPPk());
	}

	/**
	 * 角色攻击npcs
	 * 
	 * @param player
	 * @param npcs
	 */
	public void attackNPC(Fighter player, List npcs)
	{
		if (player == null || npcs == null || npcs.size() == 0)
		{
			logger.debug("参数错误！");
			return;
		}

		if (player.getSkill() == null)
		{
			// 物理攻击
			physicsAttackNPC(player, npcs);
		}
		else
		{
			// 技能攻击
			skillAttackNPC(player, npcs);
		}

		// 打怪减去武器持久
		EquipService equipService = new EquipService();
		equipService.useWeapon(player.getPPk());

		// 监听buff状态
		BuffEffectService buffEffectService = new BuffEffectService();
		buffEffectService.updateSpareBout(player.getPPk());
	}

	/**
	 * 技能攻击npc
	 */
	public void skillAttackNPC(Fighter player, List npcs)
	{
		if (player == null || npcs == null || npcs.size() == 0)
		{
			logger.debug("参数错误");
			return;
		}
		PlayerSkillVO playerSkill = player.getSkill();
		if (playerSkill == null)
		{
			logger.debug("玩家没有装载技能");
			return;
		}
		/** *******************判断技能是否能使用*********************** */
		SkillService skillService = new SkillService();
		String use_display = null;/*//skillService.isUsabled(player, playerSkill);

		// 判断技能是否能用
		if (use_display != null)
		{
			logger.debug("角色内力不足，不能使用此技能");
			player.setSkillNoUseDisplay(use_display + "<br/>");
			player.setSkill(null);
			return;
		}*/

		// 更新技能使用状态
		PlayerSkillDao playerSkillDao = new PlayerSkillDao();
		if (playerSkill.getSkType() != 4)// 绝学判断
		{
			if (use_display == null)
			{
				playerSkillDao.updateUsetimeAndSleight(playerSkill.getPPk(),
						playerSkill.getSkId());
			}
		}
		else
		{
			playerSkillDao.updateUsetimeAndSleight(playerSkill.getPPk(),
					playerSkill.getSkId());
		}

		/*********************判断技能是否能使用结束*********************** */
		NpcService npcService = new NpcService();

		NpcFighter npc = (NpcFighter) npcs.get(0);
		int injure = 0;
		int skillInjure = 0;
		int wxInjure = 0;

		wxInjure = AttackService.getWxInjureValue(player.getWx(), npc, player
				.getPGrade(), npc.getLevel(), player.getPPk());// * 10;

		int skillGj = (playerSkill.getSkDamage() + player.getGj());
		logger.info("************技能攻击: 属性攻击和装备攻击:" + player.getGj() + ";技能攻击:"
				+ skillGj);

		player.setSkillDisplay(playerSkill.getSkName());
		boolean isAttackDizzyNPC = MathUtil.isAppearByPercentage(playerSkill
				.getSkYun());

		boolean isHaveBuffEffect = false;
		BuffEffectService buffEffectService = new BuffEffectService();
		if (playerSkill.getSkBuff() != 0
				&& MathUtil.isAppearByPercentage(playerSkill
						.getSkBuffProbability()))
		{
			isHaveBuffEffect = true;
		}
        //发动技能攻击
		if (playerSkill.getSkArea() == 1) // 群体攻击
		{
			logger.info("角色发动群体攻击技能");

			logger.info("npcs.size()=" + npcs.size());

			for (int i = npcs.size() - 1; i >= 0; i--)
			{
				npc = (NpcFighter) npcs.get(i);
				// 如果npc类型为旗杆,就会不反击玩家
				if (npc.getNpcType() == NpcAttackVO.MAST)
				{
					logger.info("因为是旗杆,所有不反击玩家");
					break;
				}

				if (isAttackDizzyNPC)
				{
					npc.setDizzyBoutNum(NpcService.PLAYERINJURE);
				}
				if (MathUtil.PercentageRandomByParamdouble((player
						.getDropMultiple()), 100))
				{
					skillInjure = AttackService.normalInjureExpressions(
							skillGj, npc.getNpcDefance(), player
									.getPGrade(), npc.getLevel());
					injure = (skillInjure + wxInjure) * 2 + 20;
					// 显示效果
					player.setSkillDisplay(playerSkill.getSkName()
							+ "攻击,产生了暴击效果!");
				}
				else
				{
					skillInjure = AttackService.normalInjureExpressions(
							skillGj, npc.getNpcDefance(), player
									.getPGrade(), npc.getLevel());
					injure = skillInjure + wxInjure + 20;
					if (player.getSkillDisplay() == null)
					{
						// 显示效果
						player.setSkillDisplay(playerSkill.getSkName()
								+ "攻击.");
					}
				}
				if (injure <= 0)
				{
					injure = 1;
				}
				npcService.updateNPCCurrentHP(player, npc, injure,
						NpcService.PLAYERINJURE);
				if (npc.isDead())
				{
					if (npcs != null && npcs.size() != 0)
					{
						npcs.remove(0);
					}

					if (npc.getNpcType() == NpcAttackVO.CITYDOOR
							|| npc.getNpcType() == NpcAttackVO.DIAOXIANG)
					{
						AttacckCache attacckCache = new AttacckCache();
						attacckCache.clearNpcBySceneId(npc.getSceneId()
								+ "");
					}
				}

				else
				{
					if (isHaveBuffEffect)
					{
						buffEffectService.createBuffEffect(npc.getID(),
								BuffSystem.NPC, playerSkill.getSkBuff());
					}
				}
				// logger.info("处理后的npc个数="+npcs.size());
			}

			if (npcs.size() > 0)
			{
				// 是否产生击晕效果
				if (isAttackDizzyNPC)
				{
					npcService.addDizzyBoutNumOfNPCs(player.getPPk(),playerSkill.getSkYunBout());
				}
			}
		}
		else
		// 单体攻击
		{
			logger.info("角色发动单体体攻击技能");
			skillInjure = AttackService
					.normalInjureExpressions(skillGj, npc.getNpcDefance(),
							player.getPGrade(), npc.getLevel());
			if (MathUtil.PercentageRandomByParamdouble((player
					.getDropMultiple()), 100))
			{
				injure = (skillInjure + wxInjure) * 2 + 20;
				// 显示效果
				player.setSkillDisplay(playerSkill.getSkName()
						+ "攻击,产生了暴击效果!");
			}
			else
			{
				injure = skillInjure + wxInjure + 20;
				// 显示效果
				player.setSkillDisplay(playerSkill.getSkName() + "攻击.");
			}
			if (injure <= 0)
			{
				injure = 1;
			}
			npcService.updateNPCCurrentHP(player, npc, injure,
					NpcService.PLAYERINJURE);
			if (npc.isDead())
			{
				if (npcs != null && npcs.size() != 0)
				{
					npcs.remove(0);
				}
				if (npc.getNpcType() == NpcAttackVO.CITYDOOR
						|| npc.getNpcType() == NpcAttackVO.DIAOXIANG)
				{
					AttacckCache attacckCache = new AttacckCache();
					attacckCache.clearNpcBySceneId(npc.getSceneId() + "");
				}
			}
			else
			{
				// 是否产生击晕效果
				if (npc.getDizzyBoutNum() == 0 && isAttackDizzyNPC)
				{
					npc.setDizzyBoutNum(playerSkill.getSkYunBout());
					npcService.updateDizzyBoutNumOfNPC(npc.getID(),
							playerSkill.getSkYunBout());
				}

				if (isHaveBuffEffect)
				{
					buffEffectService.createBuffEffect(npc.getID(),
							BuffSystem.NPC, playerSkill.getSkBuff());
				}
			}
		}
		// 更新技能使用情况
		SkillService sse = new SkillService();
		sse.useSkill(player.getPPk(), player.getSkill());
	
	}

	/**
	 * 物理攻击
	 * 
	 * @param player
	 * @param npcs
	 * @param bout
	 */
	public void physicsAttackNPC(Fighter player, List npcs)
	{
		if (player == null || npcs == null || npcs.size() == 0)
		{
			logger.debug("参数错误！");
			return;
		}
		// 攻击第一只NPC
		NpcFighter npc = (NpcFighter) npcs.get(0);

		int injureValue = 0;
		int wxInjureValue = 0;
		int physicsInjureValue = 0;
		AttacckCache attacckCache = new AttacckCache();

		// 五行伤害
		wxInjureValue = AttackService.getWxInjureValue(player.getWx(), npc,
				player.getPGrade(), npc.getLevel(), player.getPPk());// * 10;
		// 物理伤害
		physicsInjureValue = AttackService.getPhysicsInjureValue(player, npc);

		logger.info("五行伤害:" + wxInjureValue);
		logger.info("物理伤害:" + physicsInjureValue);
		if (MathUtil.PercentageRandomByParamdouble((player.getDropMultiple()),
				100))
		{
			injureValue = (physicsInjureValue + wxInjureValue) * 2 + 20;
			if (injureValue <= 0)
			{
				injureValue = 1;
			}

			// 更新NPC状态
			NpcService npcService = new NpcService();

			npcService.updateNPCCurrentHP(player, npc, injureValue,
					NpcService.PLAYERINJURE);
			if (npc.isDead())
			{
				if (npcs != null && npcs.size() != 0)
				{
					npcs.remove(0);
				}
				if (npc.getNpcType() == NpcAttackVO.CITYDOOR
						|| npc.getNpcType() == NpcAttackVO.DIAOXIANG)
				{
					attacckCache.clearNpcBySceneId(npc.getSceneId() + "");
				}
			}
			player.setSkillDisplay("普通攻击，产生了暴击效果。");
		}
		else
		{

			// 总伤害
			injureValue = physicsInjureValue + wxInjureValue + 20;

			if (injureValue <= 0) 
			{
				injureValue = 1;
			}

			// 更新NPC状态
			NpcService npcService = new NpcService();

			npcService.updateNPCCurrentHP(player, npc, injureValue,
					NpcService.PLAYERINJURE);
			if (npc.isDead())
			{
				if (npcs != null && npcs.size() != 0)
				{
					npcs.remove(0);
				}
				if (npc.getNpcType() == NpcAttackVO.CITYDOOR
						|| npc.getNpcType() == NpcAttackVO.DIAOXIANG)
				{
					attacckCache.clearNpcBySceneId(npc.getSceneId() + "");
				}

			}
			// 更新回合状态
			player.setSkillDisplay("普通攻击。");
		}

	}

	/**
	 * 战斗胜利时，玩家所得到的总的经验和宠物得到的总的经验
	 * 角色经验的计算公式：(原始经验+系统经验倍数加成的经验+buff得到的经验倍数+离线经验)*组队效果
	 * 注：系统经验倍数加成的经验：如系统两倍是就加一倍 宠物经验的计算公式：(原始经验+buff得到的经验倍数)*组队效果
	 * 注：buff得到的经验倍数，是指宠物的经验倍数的buff
	 * 
	 * @param player
	 * @param exp_of_role
	 *            角色得到的原始经验
	 * @param exp_of_pet
	 *            宠物得到的原始经验
	 * @return 结果为result[0]表示角色得到的总的经验，result[1]表示宠物得到的总的经验
	 */
	private int[] getFigthWinExp(PartInfoVO player, int exp_of_role,
			int exp_of_pet)
	{
		int result[] = { exp_of_role, exp_of_role };

		int total_exp_of_role = exp_of_role;
		int total_exp_of_pet = exp_of_pet;

		int p_pk = player.getPPk();
		int scene_id = Integer.valueOf(player.getPMap());

		// 去掉离线经验RoleBeOffBuffService roleBeOffBuffService = new
		// RoleBeOffBuffService();
		ExpnpcdropService expnpcdropService = new ExpnpcdropService();
		RoomService sceneService = new RoomService();
		GroupService groupService = new GroupService();

		int groupEffectValue = 0;// 组队效果值.说明:副本区域没有经验加成的效果

		if (sceneService.getMapType(scene_id) != MapType.INSTANCE)// 非副本区域
		{
			groupEffectValue = groupService.getGroupEffectPercent(p_pk,
					scene_id);// 得到组队效果值
		}

		logger.info("组队效果值：" + groupEffectValue);

		// ********************计算玩家所得总的经验

		int sys_exp_multiple = expnpcdropService.getExpNpcdrop();// 系统经验倍数

		int role_sys_exp_append = exp_of_role * (sys_exp_multiple - 1);// 系统经验加成。说明：因为是加多少陪得关系，所以倍数要减一
		int role_buff_exp_append = (int) (1.0 * exp_of_role
				* player.getAppendExp() / 100); // buff经验加成.
		// int role_off_line_buff_exp =
		// roleBeOffBuffService.getRoleOffLineExp(p_pk, exp_of_role);//得到玩家的离线经验

		total_exp_of_role = (int) (1.0
				* (exp_of_role + role_sys_exp_append + role_buff_exp_append)
				* (100 + groupEffectValue) / 100);

		logger.info("角色原始经验：" + exp_of_role + ";系统加成经验:" + role_sys_exp_append
				+ ";buff加成经验:" + role_buff_exp_append + ";");
		logger.info("角色总经验:" + total_exp_of_role);
		// ********************计算玩家所得总的经验结束

		// ********************计算宠物所得总的经验
		int pet_buff_exp_append = (int) (1.0 * exp_of_pet
				* player.getAppendPetExp() / 100); // buff经验加成

		total_exp_of_pet = (int) (1.0 * (exp_of_pet + pet_buff_exp_append)
				* (100 + groupEffectValue) / 100); // 总经验

		logger.info("宠物原始经验：" + exp_of_pet + ";" + pet_buff_exp_append);
		logger.info("宠物总经验:" + total_exp_of_pet);
		// ********************计算宠物所得总的经验结束

		result[0] = total_exp_of_role;
		result[1] = total_exp_of_pet;
		return result;
	}

	/**
	 * 角色胜利的处理
	 * 
	 * @param player
	 * @param npcAttackVO
	 * @return
	 */
	public PartInfoVO playerWIN(PartInfoVO player, FightList winList)
	{
		int p_pk = player.getPPk();

		PartInfoVO winCharacter = player;
		RoleEntity role_info = RoleService.getRoleInfoById(p_pk + "");

		EconomyService economyService = new EconomyService();
		GrowService growService = new GrowService();
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		InstanceService instanceService = new InstanceService();

		/** *****************战斗结束后所得经验和钱的整理********************************** */
		DropExpMoneyVO dropExpMoney = role_info.getDropSet().getExpAndMoney();

		int exp_result[] = getFigthWinExp(player, dropExpMoney.getDropExp(),
				dropExpMoney.getDropPetExp());

		int total_exp_of_role = exp_result[0];// 玩家得到的总的经验
		int total_exp_of_pet = exp_result[1];// 宠物得到的总的经验
		MyService my = new MyServiceImpl();
		if (my.isTeaOnline(p_pk))
		{
			total_exp_of_role = total_exp_of_role * ShituConstant.MORE_EXP
					/ 100;
		}

		winList.setExp(total_exp_of_role);
		winList.setMoney(dropExpMoney.getDropMoney());
		/** *************************************************************************** */
		// 记录副本boss进度
		instanceService.archive(p_pk, Integer.parseInt(player.getPMap()));

		// 增加经验,玩家成长
		String grow_display = growService.playerGrow(role_info, player,
				total_exp_of_role);
		winList.setGrowDisplay(grow_display);

		my.shareExp(p_pk, player.getPGrade(), total_exp_of_role);
		// 增加钱
		economyService.addMoney(player, dropExpMoney.getDropMoney());
		// 执行统计
		gsss.addPropNum(6, StatisticsType.MONEY, dropExpMoney.getDropMoney(),
				StatisticsType.DEDAO, StatisticsType.DAGUAI, p_pk);

		// NPC掉落的物品
		List<DropGoodsVO> npcdrops = role_info.getDropSet().getList();
		winList.setDropGoods(npcdrops);
		role_info.getDropSet().clearExpAndMoney();
		return winCharacter;
	}

	/**
	 * PK掉落物品
	 * @param playerB
	 * @param playerA
	 * @param tong
	 */
	private void dropGoodsByPK(Fighter playerB, Fighter playerA)
	{
		RoomService roomService = new RoomService();
		if (!roomService.isLimitedByAttribute(Integer.valueOf(playerB.getPMap()), RoomService.DROP_GOODS))
		{
			int pkValue = playerB.getPPkValue();
			if ( MathUtil.isAppearByPercentage(pkValue, GameArgs.PK_DEAD_DROP_RATE) )
			{
				GoodsService goodsService = new GoodsService();
				// 掉装备
				goodsService.dropGoods(playerB, playerA);
			}
		}
	}
	/**
	 * pk死亡后掉落灵石给胜利者加上
	 * @param playerB
	 * @param playerA
	 */
	private void dropLingshi(Fighter loser, Fighter winer)
	{
		RoleEntity loser_info = RoleService.getRoleInfoById(loser.getPPk());
		RoleEntity winer_info = RoleService.getRoleInfoById(winer.getPPk());
		int num=MathUtil.getRandomBetweenXY(1, 10);
		long lingshi=loser_info.getBasicInfo().getCopper();
		long dropLingshi=lingshi*num/100;
		if( dropLingshi>0 )
		{
			loser_info.getBasicInfo().addCopper(-dropLingshi);
			winer_info.getBasicInfo().addCopper(dropLingshi);
			loser.appendKillDisplay("损失").append(dropLingshi).append(GameConfig.getMoneyUnitName()).append("<br/>");
			winer.appendKillDisplay("得到").append(dropLingshi).append(GameConfig.getMoneyUnitName()).append("<br/>");
		}
	}
	
	/**
	 * 角色被NPC打死处理（减经验值，随机掉落物品,HP,MP初始化）
	 * 
	 * @param dead  死亡者
	 * @param playerA  如果为npc战斗,则这是和playeB一样的,只是为了接口一致. 如果为pk战斗,则playerA为胜利者.
	 * @return
	 */
	private PartInfoVO dead(Fighter dead, Fighter playerA,int npc_type)
	{
		if( dead.getPPk()==playerA.getPPk())
		{
			//被NPC打死
			deadByNpc(dead,npc_type);
		}
		else
		{
			//被其他玩家打死
			deadByPK(dead, playerA);
		}
		// 死亡后必须做的事
		deadMustDoThing(dead);
		return dead;
	}
	
	/**
	 * 玩家被NPC打死
	 * @param dead
	 * @param npc_type	npc类型
	 * @return
	 */
	private PartInfoVO deadByNpc(Fighter dead,int npc_type)
	{
		RoleEntity dead_info = RoleService.getRoleInfoById(dead.getPPk());
		
		TimeControlService timeService = new TimeControlService();
		GrowService growService = new GrowService();
		// 查看此地点的地点属性是否掉落经验  洪荒所有地图都掉经验
		//boolean isSceneDropExp = roomService.isLimitedByAttribute(Integer.valueOf(dead.getPMap()), RoomService.DROP_EXP);
		// 检查身上是否在免死符有效时间内, 如在就不掉经验
		boolean isOutOfPublish = timeService.isUseableToTimeControl(dead.getPPk(), PropType.OUTPUBLISH, TimeControlService.ANOTHERPROP);
		Vip vip = dead_info.getTitleSet().getVIP();	
		long drop_exp = growService.dropPlayerExperience(dead);
		dead_info.getStateInfo().setShouldDropExperience(drop_exp);
		dead_info.getStateInfo().setDeadDropExp(drop_exp);
		dead.setDropExp(drop_exp);
		if (isOutOfPublish==false || vip!=null)
		{
			//死亡掉落经验
			dead.setNotDropExp(true);
		}
		return dead;
	}
	
	/**
	 * PK死亡处理
	 * @param loser			失败的玩家
	 * @param winner		胜利的玩家
	 * @param tong			战斗类型
	 * @return					
	 */
	private PartInfoVO deadByPK(Fighter loser, Fighter winner)
	{
		RoleEntity loser_info = RoleService.getRoleInfoById(loser.getPPk());
		RoleEntity winer_info = RoleService.getRoleInfoById(winner.getPPk());
		
		//增加帮派贡献
		if( winner.getPRace()!=loser.getPRace() )//如果不是一个种族
		{
			if( winer_info.getBasicInfo().addFContributeAndFPrestige() )
			{
				winner.appendKillDisplay("增加1点氏族荣誉,氏族增加1点声望").append("<br/>");
			}
		}
			
		//增加罪恶值
		addPkValue(winner,loser);
		
		//掉落灵石
		dropLingshi(loser, winner);
		//掉物品
		dropGoodsByPK(loser, winner);
		// 掉经验
		dropExp(loser, winner);
		//添加仇恨信息 
		AttackService.processPkHite(loser,winner);
		//掉落装备耐久上限
		String consume_equip_endure_des = loser_info.getEquipOnBody().consumeMaxEndure();
		//给胜利玩家加上杀人数
		new MenpaiContestService().updatePlayerKillNum(winner.getPPk(),loser.getPPk());
		// 统计需要
		new RankService().updateAdd(winner.getPPk(), "kill", 1);
		new RankService().updateAdd(loser.getPPk(), "dead", 1);
		
		//初始化杀人者的战斗相关变量
		winer_info.getBasicInfo().initFightState();
		
		//****************************死亡描述
		if( StringUtils.isEmpty(consume_equip_endure_des)==false )
		{
			loser.appendKillDisplay(consume_equip_endure_des).append("<br/>");
		}
		return loser;
	}

	/**
	 * 死亡后必须做的事
	 * @param dead 死亡者
	 */
	private void deadMustDoThing(Fighter dead)
	{
		RoleEntity dead_info = RoleCache.getByPpk(dead.getPPk());
		
		PlayerService playerService = new PlayerService();
		PropertyService propertyService = new PropertyService();
		
		// 07.16新加,将经验掉到stateInfo中
		dead_info.getStateInfo().setDeadDropExp(dead.getDropExp());
		//初始化杀人者的状态变量
		dead_info.getBasicInfo().initFightState();
		
		//初始化血量和法力
		propertyService.updateHpProperty(dead.getPPk(), dead.getPUpHp());
		propertyService.updateMpProperty(dead.getPPk(), dead.getPUpMp());

		// 仍然会给他将地点更新, 但是视野模糊掉.如果其选择使用免死道具，则将其地点返回原点，否则
		dead_info.getBasicInfo().setShouldScene(Integer.parseInt(dead_info.getBasicInfo().getSceneId()));
		playerService.initPositionWithOutView(dead);
		//更新玩家秘境地点
		SceneVO scene = dead_info.getBasicInfo().getSceneInfo();
		RoomService roomservice = new RoomService();
		String test= dead_info.getBasicInfo().getShouldScene()+"";
		int resurrection_point = roomservice.getResurrectionPoint(dead_info);
		dead_info.getBasicInfo().updateSceneId(resurrection_point + "");
	}

	/**
	 * 掉落经验,如果掉经验,则掉落到stateInfo里
	 * @param loser
	 * @param winer
	 * @return
	 */
	private void dropExp(Fighter loser, Fighter winner)
	{
		TimeControlService timeService = new TimeControlService();
		GrowService growService = new GrowService();
		RoomService roomService = new RoomService();
		
		// 查看此地点的地点属性是否掉落经验
		boolean is_dropexp_by_scene = roomService.isLimitedByAttribute(Integer.valueOf(loser.getPMap()), RoomService.DROP_EXP);
		if (true)//目前的地图都掉落经验
		{
			// 检查身上是否在免死符有效时间内, 如在就不掉经验
			boolean is_punished = timeService.isUseableToTimeControl(loser.getPPk(), PropType.OUTPUBLISH, TimeControlService.ANOTHERPROP);
			if( is_punished )
			{
				// 检查身上有没有免死道具
				PlayerPropGroupDao ppgdao = new PlayerPropGroupDao();
				int miansi_prop_num = ppgdao.getPropNumByPropType(loser.getPPk(),PropType.EXONERATIVE);
				loser.setDeadProp(miansi_prop_num);
				
				RoleEntity role_info = RoleService.getRoleInfoById(loser.getPPk()+"");
				Vip vip = role_info.getTitleSet().getVIP();
				if ( vip == null)//不是会员才掉经验
				{
					long dropexp = growService.dropExperience(loser);
					loser.setDropExp(dropexp);
					if( dropexp>0 )
					{
						loser.appendKillDisplay("掉落经验：").append(dropexp).append("点<br/>");
					}
				}
			}
		}
	}


	/**
	 * playerA攻击playerB，playerB受到攻击伤害更新自身状态
	 * 
	 * @param playerA
	 * @param player
	 * @param injure
	 * @param bout
	 * @param tong  -1为npc，0为普通，1为帮战，2为阵营pk
	 * @return
	 */
	public boolean statUpdateByPlayerInjure(Fighter playerB, Fighter playerA,int npc_type)
	{
		PropertyService propertyService = new PropertyService();
		//如果中毒则更新缓存
		RoleEntity roleInfoB = RoleCache.getByPpk(playerB.getPPk());
		// 判断是否死亡
		if (!playerB.isDead())// 没死
		{
			//判断如果是中毒状态则更改回合数  中毒只在3个回合内有效果
			if(roleInfoB.getBasicInfo().isPoisoning()&&roleInfoB.getBasicInfo().getPoisonCount()>0)
			{
				int count=roleInfoB.getBasicInfo().getPoisonCount();
				roleInfoB.getBasicInfo().setPoisonCount(count-1);
				if(count==1)
				{
					roleInfoB.getBasicInfo().setPoisoning(false);
					roleInfoB.getBasicInfo().setAddDamage(0);
				}
			}
			propertyService.updateHpProperty(playerB.getPPk(), playerB.getPHp());
		}
		else
		{//死了
			propertyService.updateHpProperty(playerA.getPPk(), playerA.getPHp());
			dead(playerB, playerA, npc_type);
		}

		return false;
	}

	/**
	 * playerA攻击playerB，playerB受到攻击伤害更新自身状态
	 * 
	 * @param playerA
	 * @param player
	 * @param injure
	 * @param bout
	 * @return
	 */
	public boolean statUpdateByPetInjure(Fighter playerB, Fighter playerA,
			int injure, String tong,int npc_type)
	{
		playerB.setPetInjure(injure);

		// 判断是否死亡
		if (!playerB.isDead())// 没死
		{
			PropertyService propertyService = new PropertyService();
			propertyService.updateHpProperty(playerB.getPPk(), playerB.getPHp());
		}
		else
		// 死了
		{
			dead(playerB, playerA ,npc_type);
		}

		return false;
	}

	/**
	 * 增加罪恶值 playerA杀死playerB
	 * 
	 * @param winner
	 * @param loser
	 */
	private int addPkValue(Fighter winner, Fighter loser)
	{
		if (winner == null || loser == null)
		{
			return 0;
		}
		
		RoleEntity winner_info = RoleService.getRoleInfoById(winner.getPPk());
		
		if( winner_info.getPKState().isZhudongAttackOther(loser.getPPk())==false )//只有主动攻击方才增加罪恶值
		{
			return 0;
		}
		
		if( winner.getPPks()==2 && loser.getPPks()==2 && winner.getPRace()!=loser.getPRace())//如果不是同一种族，且PK开关都开着，不增加罪恶值
		{
			return 0;
		}
		
		if (loser.getPPkValue() >= GameArgs.RED_NAME_VALUE)// 红名状态不加罪恶值
		{
			return 0;
		}
		
		// [（玩家等级-被杀死玩家等级）+1]*10
		int add_pk_value = (winner.getPGrade() - loser.getPGrade() + 1) * 10;
		if (add_pk_value < 10)
		{
			add_pk_value = 10;
		}
		// 对方是黄名状态罪恶值除2
		if (loser.getPPkValue() > GameArgs.YELLOW_NAME_VALUE && loser.getPPkValue() < GameArgs.RED_NAME_VALUE)
		{
			add_pk_value = add_pk_value / 2;
		}
		
		if (add_pk_value > 0)
		{
			winner.setPPkValue(winner.getPPkValue() + add_pk_value);
			winner.appendKillDisplay("增加").append(add_pk_value).append("点罪恶值<br/>");
			
			winner_info.getBasicInfo().addEvilValue(add_pk_value);
		}
		return add_pk_value;
	}

	/**
	 * 玩家在使用九转所应该回到的sceneID
	 * 
	 * @param roleInfo
	 * @return
	 */
	public int getShouldScene(RoleEntity roleInfo)
	{
		int sceneId = roleInfo.getBasicInfo().getShouldScene();
		RoomService roomService = new RoomService();
		if (sceneId == 0)
		{
			sceneId = Integer.parseInt(roleInfo.getBasicInfo().getSceneId());
		}
		int mapType = roomService.getMapType(sceneId);

		if (mapType == MapType.TONGBATTLE)//帮战地图
		{
			/*SceneVO sceneVO = roomService.getById(sceneId+ "");
			MapVO mapVO = MapCache.getById(sceneVO.getSceneMapqy());
			TongSiegeBattleService tongSiegeBattleService = new TongSiegeBattleService();
			TongSiegeBattleVO tongSiegeBattleVO = tongSiegeBattleService
					.getSiegeByMapId(mapVO.getMapID());
			TongSiegeControlVO tongSiegeControlVO = tongSiegeBattleService
					.getSiegeTongInfo(tongSiegeBattleVO.getSiegeId() + "");

			if (tongSiegeControlVO.getNowPhase() == FinalNumber.FORTH)
			{
				TongSiegeInfoService tongSiegeInfoService = new TongSiegeInfoService();
				TongSiegeInfoVO tongSiegeInfoVO = tongSiegeInfoService
						.getPersonInfo(roleInfo.getBasicInfo().getPPk(),
								tongSiegeBattleVO.getSiegeId() + "",
								tongSiegeControlVO.getSiegeNumber());

				if (tongSiegeInfoVO.getDeadIimit() >= (FinalNumber.DEADLIMIT + 1)
						|| tongSiegeInfoVO.getDeadNum() >= FinalNumber.DEADLIMIT)
				{
					return tongSiegeBattleVO.getOutScene();
				}
			}*/
		}
		if(mapType == MapType.MENPAICONTEST || mapType == MapType.UNCHARTEDROOM){
			int resurrection_point = roomService.getResurrectionPoint(roleInfo);
			return resurrection_point;
		}

		return sceneId;
	}
	/**
	 * 计算技能攻击时对对方造成的伤害值
	 * @param playerA
	 * @param playerB
	 */
	private int getInjure(Fighter playerA,Fighter playerB,PlayerSkillVO playerSkill)
	{
		int injure = 0;
		int skillInjure = 0;
		int wxInjure = 0;
		// 得到五行伤害值
		wxInjure = AttackService.getWxInjureValue(playerA.getWx(), playerB.getWx(), playerA.getPGrade(), playerB.getPGrade(), playerA.getPPk());
		// 得到技能攻击数
		int skillGj = ((playerSkill.getSkDamage() + playerA.getGj()));
		playerA.setSkillDisplay(playerSkill.getSkName() + "攻击.");
		logger.debug("角色发动单体体攻击技能");
		skillInjure = AttackService.normalInjureExpressions(getPlayerActBySkillContent(playerA, skillGj),getPlayerDefBySkillContent(playerB, playerB.getFy()), playerA.getPGrade(), playerB.getPGrade());
		// 人对玩家的物理伤害=玩家攻击玩家的物理伤害/1.5+玩家对玩家的五行伤害；
		injure = (skillInjure * 3 / 2 + wxInjure) / 2;
		playerA.setSkillDisplay(playerSkill.getSkName() + "攻击.");
		// 按一定几率产生暴击
		if (MathUtil.PercentageRandomByParamdouble((playerA.getDropMultiple()),
				100))
		{
			// 伤害2倍 8.6
			injure = (skillInjure * 3 / 2 + wxInjure);
			playerA.setSkillDisplay(playerSkill.getSkName() + "攻击,产生了暴击效果!");
		}
		if (injure <= 0)
		{
			injure = 1;
		}
		return injure;
	}
	
	
	//师门技能给玩家附加BUFF效果
	private void givePlayerBuff(Fighter player,PlayerSkillVO playerSkill){
		int buff_id = playerSkill.getSkBuff();
		int probability = playerSkill.getSkBuffProbability();
		int content = playerSkill.getSkYunBout();
		RoleEntity roleInfo = RoleCache.getByPpk(player.getPPk());
		if(roleInfo.getBasicInfo().getXuanyunhuihe() == 0&&roleInfo.getBasicInfo().getActhuihe()==0&&roleInfo.getBasicInfo().getDefhuihe()==0){
			roleInfo.getBasicInfo().setMenpaiskilldisplay("");
		}
		if(probability != 0){
			if(MathUtil.isAppearByPercentage(probability)){
				if(buff_id != 0){
					if(buff_id == 1){
						roleInfo.getBasicInfo().setXuanyunhuihe(3);
						roleInfo.getBasicInfo().setMenpaiskilldisplay(roleInfo.getBasicInfo().getName()+"在眩晕状态,无法反击");
					}
					if(buff_id == 2){
						roleInfo.getBasicInfo().setActhuihe(5);
						roleInfo.getBasicInfo().setActcontent(content);
						roleInfo.getBasicInfo().setMenpaiskilldisplay(roleInfo.getBasicInfo().getName()+"的攻击力下降"+content+"%");
					}
					if(buff_id == 3){
						roleInfo.getBasicInfo().setDefhuihe(5);
						roleInfo.getBasicInfo().setDefcontent(content);
						roleInfo.getBasicInfo().setMenpaiskilldisplay(roleInfo.getBasicInfo().getName()+"的防御力下降"+content+"%");
					}
			}
		}
	}
	}
	
	private int getPlayerActBySkillContent(Fighter player,int gj){
		RoleEntity roleInfo = RoleCache.getByPpk(player.getPPk());
		if(roleInfo.getBasicInfo().getActhuihe() > 0){
			roleInfo.getBasicInfo().setActhuihe(roleInfo.getBasicInfo().getActhuihe() -1);
			return gj - (gj*roleInfo.getBasicInfo().getActcontent()/100);
		}else{
			return gj;
		}
	}
	private int getPlayerDefBySkillContent(Fighter player,int fy){
		RoleEntity roleInfo = RoleCache.getByPpk(player.getPPk());
		if(roleInfo.getBasicInfo().getDefhuihe() > 0){
			roleInfo.getBasicInfo().setDefhuihe(roleInfo.getBasicInfo().getDefhuihe() -1);
			return fy - (fy*roleInfo.getBasicInfo().getDefcontent()/100);
		}else{
			return fy;
		}
	}
	
}
