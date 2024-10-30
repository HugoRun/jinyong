package com.ls.web.service.skill;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.log4j.Logger;

import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.cache.staticcache.skill.SkillCache;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.dao.info.skill.PlayerSkillDao;
import com.ls.ben.dao.info.skill.SkillDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.effect.PropUseEffect;
import com.ls.ben.vo.info.npc.NpcFighter;
import com.ls.ben.vo.info.partinfo.Fighter;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.skill.PlayerSkillVO;
import com.ls.ben.vo.info.skill.SkillVO;
import com.ls.model.property.PassSkillInterface;
import com.ls.model.property.RoleShortCutInfo;
import com.ls.model.property.RoleSkillInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.attack.AttackService;
import com.ls.web.service.npc.NpcService;
import com.ls.web.service.player.RoleService;
import com.lw.dao.skill.Passskill;
import com.lw.service.skill.SkillUpService;

/**
 * 功能:玩家技能管理
 * 
 * @author 刘帅
 * 
 * 1:36:22 PM
 */
public class SkillService
{

	Logger logger = Logger.getLogger("log.service");

	/**
	 * 技能攻击npc
	 */
	public int skillAttackNPC(Fighter player, List npcs)
	{
		int skillInjure = 0;
		if (player == null || npcs == null || npcs.size() == 0)
		{
			logger.info("参数错误");
			return skillInjure;
		}
		PlayerSkillVO playerSkill = player.getSkill();
		if (playerSkill == null)
		{
			logger.info("玩家没有装载技能");
			return skillInjure;
		}

		boolean isUse = true;// 表示此技能是否可用
		/** *******************判断技能是否能使用*********************** */
		// 如果消耗对象是mp
		if (playerSkill.getSkExpend().equals("MP"))
		{
			if (playerSkill.getSkUsecondition() > player.getPMp())
			{
				isUse = false;
				logger.info("角色内力不足，不能使用此技能");
				player.setSkillDisplay("内力不足，不能使用此技能<br/>");
			}
		}
		// 判断冷却时间是否已过

		// 如果不可用则不做处理，返回
		if (!isUse)
			return skillInjure;
		/** *******************判断技能是否能使用结束*********************** */
		NpcService npcService = new NpcService();

		NpcFighter npc = (NpcFighter) npcs.get(0);
		int wxInjure = AttackService.getWxInjureValue(player.getWx(), npc,
				player.getPGrade(), npc.getLevel(), player.getPPk());

		int skillGj = playerSkill.getSkDamage();

		int injure = 0;
		if (playerSkill.getSkType() == 1) // 群体攻击
		{
			logger.debug("角色发动群体攻击技能");
			for (int i = 0; i < npcs.size(); i++)
			{
				npc = (NpcFighter) npcs.get(i);
				skillInjure = AttackService
						.normalInjureExpressions(skillGj, npc.getNpcDefance(),
								player.getPGrade(), npc.getLevel());
				npc.setPlayerInjure(skillInjure + wxInjure);

				npcService.updateNPCCurrentHP(player, npc, injure,
						NpcService.PLAYERINJURE);

				if (npc.isDead())
				{
					npcs.remove(i);
				}
			}
		}
		else
		// 单体攻击
		{
			logger.debug("角色发动单体体攻击技能");
			skillInjure = AttackService.normalInjureExpressions(skillGj, npc
					.getNpcDefance(), player.getPGrade(), npc.getLevel());
			npc.setPlayerInjure(skillInjure + wxInjure);
			npcService.updateNPCCurrentHP(player, npc, injure,
					NpcService.PLAYERINJURE);
			if (npc.isDead())
			{
				npcs.remove(0);
			}
		}

		// 更新技能使用状态
		SkillService skillService = new SkillService();
		skillService.useSkill(player.getPPk(), playerSkill);

		player.setSkillDisplay(StringUtil.isoToGBK(playerSkill.getSkName()));
		player.setExpendMP(playerSkill.getSkUsecondition());

		RoleEntity roleInfo = RoleService.getRoleInfoById(player.getPPk() + "");
		roleInfo.getBasicInfo().updateMp(player.getPMp());

		return skillInjure;
	}

	/**
	 * 判断是否有捕捉宠物的技能
	 * 
	 * @return
	 */
	public boolean havedCatchPet1()
	{
		boolean haved = false;
		return haved;
	}

	/**
	 * 学习技能
	 */
	public String studySkill(int p_pk, int sk_id)
	{
		String result = "";
		SkillDao skillDao = new SkillDao();
		PlayerSkillDao playerSkillDao = new PlayerSkillDao();
		PlayerSkillVO playerSkill = new PlayerSkillVO();

		playerSkill.setPPk(p_pk);
		playerSkill.setSkId(sk_id);
		skillDao.loadPlayerSkillDetail(playerSkill);

		SkillCache skillCaChe = new SkillCache();
		String skill_name = skillCaChe.getNameById(sk_id);

		playerSkill.setSkName(skill_name);
		playerSkillDao.add(playerSkill);
		// 修改该字符串 通知刘威
		result = "您学习了" + StringUtil.isoToGBK(skill_name);

		PlayerSkillVO playerSkillVO = null;
		playerSkillVO = playerSkillDao.getBySkId(p_pk, sk_id);

		if (playerSkillVO.getSkType() == 2 || playerSkillVO.getSkType() == 3
				|| playerSkillVO.getSkType() == 4)
		{
			result = "您领悟了绝学" + StringUtil.isoToGBK(skill_name);
		}

		// 将学习的技能放入内存中
		RoleSkillInfo roleSkillInfo = RoleCache.getByPpk(p_pk + "").getRoleSkillInfo();
		roleSkillInfo.addSkillToPlayer(playerSkillVO);

		return result;
	}

	/**
	 * 根据s_pk得到玩家技能
	 */
	public PlayerSkillVO getSkillOfPlayer(int p_pk, int sk_id)
	{
		// 得到角色skill信息
		PlayerSkillDao playerSkillDao = new PlayerSkillDao();
		PlayerSkillVO playerSkill = playerSkillDao.getPlayerSkillInfo(p_pk,
				sk_id);
		loadSkillDetail(playerSkill);
		return playerSkill;
	}

	/** 根据技能组得到技能信息 */
	public PlayerSkillVO getSkillOfPlayerByGroup(int p_pk, int sk_Group)
	{
		// 得到角色skill信息
		PlayerSkillDao playerSkillDao = new PlayerSkillDao();
		PlayerSkillVO playerSkill = playerSkillDao.getSkillInfoByGroup(p_pk,
				sk_Group);
		loadSkillDetail(playerSkill);
		return playerSkill;
	}

	/**
	 * 加载玩家技能的详细信息
	 */
	public void loadSkillDetail(PlayerSkillVO playerSkill)
	{
		if (playerSkill == null)
		{
			logger.info("参数playerSkill为空");
			return;
		}
		SkillDao skillDao = new SkillDao();
		skillDao.loadPlayerSkillDetail(playerSkill);
	}

	/**
	 * 得到技能名字
	 */
	public String getSkillName(int sk_id)
	{
		SkillCache skillCaChe = new SkillCache();

		return skillCaChe.getNameById(sk_id);
	}

	/**
	 * 使用技能
	 */
	public void useSkill(int p_pk, PlayerSkillVO skill)
	{
		// 角色技能升级
		SkillUpService skillLevelUp = new SkillUpService();
		skillLevelUp.addSkillLevelUp(p_pk, skill);

		skill.setSkSleight(skill.getSkSleight() + 1);
		// 更新技能使用状态
		// PlayerSkillDao playerSkillDao = new PlayerSkillDao();
		// playerSkillDao.updateUsetimeAndSleight(p_pk, sk_id);
		// 根据概率释放buff
		// todo:
	}

	/**
	 * 判断技能是否可用
	 * 
	 * @param player
	 * @param playerSkill
	 * @return 返回空表示可以使用
	 */
	public String isUsabled(PartInfoVO player, PlayerSkillVO playerSkill)
	{
		String result = null;
		if (playerSkill == null || player == null)
		{
			logger.info("参数为空");
			return "技能不能使用";
		}
		/*// 判断技能使用要求的武器类型是否与装备的武器相符
		EquipService equipService = new EquipService();
		if (playerSkill.getSkWeapontype() != 0
				&& playerSkill.getSkWeapontype() != equipService
						.getArmType(player.getPPk()))
		{
			logger.info("武器类型不符，不能使用此技能");
			return result = "武器类型不符，不能使用此技能";
		}*/

		// 如果消耗对象是mp
		if (playerSkill.getSkExpend().equals("MP"))
		{
			// 判断如果是PK技能如先天神功，弹指神通，降龙十八掌都要满内力才可以使用
			if(playerSkill.getSkType()==7||playerSkill.getSkType()==8||playerSkill.getSkType()==10)
			{
					if(player.getPMp()!=player.getPUpMp())
					{
						return result="技能"+playerSkill.getSkName()+"在满内力情况下才可以使用此技能,请先补充您的内力";
					}
					//如果是弹指神通还要判断是否是满气血，如果是满气血才可以使用此技能
					if(playerSkill.getSkType()==8)
					{
						if(player.getPHp()!=player.getPUpHp())
						{
						    return result="技能"+playerSkill.getSkName()+"在满气血情况下才可以使用此技能,请先补充您的气血";
						}
					}
			}
			else
			{
				if((playerSkill.getSkUsecondition() > player.getPMp()))
				{
    				return result = "内力不足，不能使用此技能";
				}
			}
		}

		// 如果消耗对象是hp
		if (playerSkill.getSkExpend().equals("HP"))
		{
			if (playerSkill.getSkType() == 4)
			{
				if (getIsUsedSevenKill(player) == -1)
				{
					//"角色气血不足，不能使用此技能");
					return null;
				}
			}
			else
			{
				if (playerSkill.getSkUsecondition() > player.getPHp())
				{
					//logger.info("角色气血不足，不能使用此技能");
					return result = "气血不足，不能使用此技能";
				}
			}
		}

		// 判断冷却时间是否已过

		if (!DateUtil.isOverdue(playerSkill.getSkUsetime(), playerSkill.getSkLqtime()))
		{
			return result = "冷却时间还没过";
		}
		//判断此技能能否对NPC发动攻击，新增加的一阳指就不能对NPC进行攻击
		if(isTowardsNPC(playerSkill))
		{
			//logger.info("此技能不能对NPC使用");
			return result=playerSkill.getSkName()+" 属于PK技能不能对NPC进行攻击请选择其他技能";
		}
		if (result == null)
		{
			// PlayerSkillDao playerSkillDao = new PlayerSkillDao();
			// 更新技能使用状态
			// playerSkillDao.updateUsetimeAndSleight(playerSkill.getPPk(),
			// playerSkill.getSkId());
		}

		return result;
	}

	/**
	 * 得到玩家所有技能
	 */
	public List<PlayerSkillVO> getSkills111(int p_pk)
	{
		PlayerSkillDao playerSkillDao = new PlayerSkillDao();

		return playerSkillDao.getPlayerSkills(p_pk);
	}

	/**
	 * 判断是否有捕捉宠物的技能
	 * 
	 * @param p_pk
	 */
	public boolean isHaveCatchPetSkill(int p_pk)
	{
		PlayerSkillDao playerSkillDao = new PlayerSkillDao();
		return playerSkillDao.isHaveCatchPetSkill(p_pk);
	}

	/**
	 * 得到下一级技能熟练度
	 */
	public int getNextLevelSleight(int sk_id)
	{
		SkillCache skillCaChe = new SkillCache();
		int sk_next_sleight = skillCaChe.getNextSleightById(sk_id + "");
		return sk_next_sleight;
	}

	/**
	 * 加载玩家的被动技能效果
	 * 
	 * @param player
	 */
	public void loadPassiveSkillEffectByDB(PartInfoVO player)
	{
		PassSkillInterface passSkillInterface = getPassSkillPropertyInfo(player
				.getPPk());

		PartInfoDao infodao = new PartInfoDao();
		PartInfoVO infovo = infodao.getPartInfoByID(player.getPPk());

		if (passSkillInterface != null)
		{

			/*
			 * //加载玩家称号附加效果值
			 *//** 增加攻击 */
			/*
			 * int hoAttack = 0;
			 *//** 增加防御 */
			/*
			 * int hoDef = 0;
			 *//** 增加气血 */
			/*
			 * int hoHp = 0;
			 *//** 增加暴击 */
			/*
			 * double hoCrit = 0; HonourService honourService = new
			 * HonourService(); RoleHonourVO roleHonourVO =
			 * honourService.getRoleHonourIsReveal(player.getPPk(), 1);
			 * if(roleHonourVO!=null){ hoAttack = roleHonourVO.getHoAttack();
			 * hoDef = roleHonourVO.getHoDef(); hoHp = roleHonourVO.getHoHp();
			 * hoCrit = (double)roleHonourVO.getHoCrit(); }
			 */
			DecimalFormat df = new DecimalFormat("0.00");
			logger.info("增加玩家的暴击率=" + passSkillInterface.getSkBjMultiple());
			// 增加玩家的暴击率
			double d = player.getDropMultiple()
					+ passSkillInterface.getSkBjMultiple();
			String s = df.format(d);
			player.setDropMultiple(Double.parseDouble(s));

			// 增加玩家的 攻击力
			player.setPGj((int) (player.getPGj() + infovo.getPGj()
					* (passSkillInterface.getSkGjMultiple())));
			player.setPGj(player.getPGj() + passSkillInterface.getSkGjAdd());

			// 增加玩家的防御力
			player.setPFy((int) (player.getPFy() + infovo.getPFy()
					* (passSkillInterface.getSkFyMultiple())));
			player.setPFy(player.getPFy() + passSkillInterface.getSkFyAdd());

			// 增加玩家的mp
			player.setPUpMp((int) (player.getPUpMp() + infovo.getPUpMp()
					* (passSkillInterface.getSkMpMultiple())));
			player
					.setPUpMp(player.getPUpMp()
							+ passSkillInterface.getSkMpAdd());

			// 增加玩家的hp
			player.setPUpHp((int) (player.getPUpHp() + infovo.getPUpHp()
					* (passSkillInterface.getSkHpMultiple())));
			player
					.setPUpHp(player.getPUpHp()
							+ passSkillInterface.getSkHpAdd());

		}

	}

	/**
	 * 加载玩家的被动技能效果
	 * @param player
	 */
	public void loadPassiveSkillEffectByCache(PartInfoVO player,RoleEntity role_info)
	{
		PassSkillInterface passSkillInterface = getPassSkillPropertyInfo(player.getPPk());

		if (passSkillInterface != null)
		{

			DecimalFormat df = new DecimalFormat("0.00");
			logger.info("增加玩家的暴击率=" + passSkillInterface.getSkBjMultiple());
			// 增加玩家的暴击率
			double d = player.getDropMultiple()+ passSkillInterface.getSkBjMultiple();
			String s = df.format(d);
			player.setDropMultiple(Double.parseDouble(s));

			// 增加玩家的 攻击力
			player.setPGj((int) (player.getPGj() + role_info.getBasicInfo().getBasicGj()* (passSkillInterface.getSkGjMultiple())));
			player.setPGj(player.getPGj() + passSkillInterface.getSkGjAdd());

			// 增加玩家的防御力
			player.setPFy((int) (player.getPFy() + role_info.getBasicInfo().getBasicFy()* (passSkillInterface.getSkFyMultiple())));
			player.setPFy(player.getPFy() + passSkillInterface.getSkFyAdd());

			// 增加玩家的mp
			player.setPUpMp((int) (player.getPUpMp() + role_info.getBasicInfo().getUpMp()* (passSkillInterface.getSkMpMultiple())));
			player.setPUpMp(player.getPUpMp()+ passSkillInterface.getSkMpAdd());

			// 增加玩家的hp
			player.setPUpHp((int) (player.getPUpHp() + role_info.getBasicInfo().getUpHp()* (passSkillInterface.getSkHpMultiple())));
			player.setPUpHp(player.getPUpHp()+ passSkillInterface.getSkHpAdd());

		}

	}

	// 学习技能的条件判断 返回-2 表示已经学过该技能 返回-1为可以学习 返回大于0 表示条件ID
	private int studySkillTermsBySkill(int p_pk, String terms)
	{
		String[] terms_str = terms.split(",");
		int study_sk_id = Integer.parseInt(terms_str[0].trim());
		PlayerSkillVO vo = getSkillOfPlayer(p_pk, study_sk_id);
		if (vo != null)
		{
			return -2;
		}
		else
		{
			if (terms_str.length != 1)
			{
				PlayerSkillVO pvo = null;
				for (int i = 1; i < terms_str.length; i++)
				{
					int sk_id = Integer.parseInt(terms_str[i].trim());
					pvo = getSkillOfPlayer(p_pk, sk_id);
					if (pvo != null)
					{
						return -1;
					}
				}
				return Integer.parseInt(terms_str[1].trim());
			}
			else
			{
				return -1;
			}
		}
	}

	/***************************************************************************
	 * 返回的值 0代表熟练度不够 -1表示可以学习 -2 表示该技能为初始技能
	 **************************************************************************/
	private int studySkillTermsBySleight(int p_pk, String terms)
	{
		String[] terms_str = terms.split(",");// 0为技能组 一为熟练度
		PlayerSkillVO vo = getSkillOfPlayerByGroup(p_pk, Integer
				.parseInt(terms_str[0].trim()));
		if (vo == null)
		{
			return -2;
		}
		else
		{
			if (vo.getSkSleight() < Integer.parseInt(terms_str[1].trim()))
			{
				return 0;
			}
			else
			{
				return -1;
			}
		}
	}

	private String updateSkill(int p_pk, int sk_id, PlayerSkillVO playerSkillVO)
	{
		String result = "";
		PlayerSkillDao playerSkillDao = new PlayerSkillDao();
		RoleEntity roleEntity = RoleCache.getByPpk(p_pk);

		// 得到该技能的信息
		SkillVO skillvo = SkillCache.getById(sk_id);

		// 更新数据库的信息
		playerSkillDao.updatePassSkill(p_pk, skillvo, playerSkillVO.getSkId());

		// 将学习的技能放入内存中
		PlayerSkillVO playerSkillVO_new = playerSkillDao.getById(playerSkillVO
				.getSPk());
		RoleSkillInfo roleSkillInfo = roleEntity.getRoleSkillInfo();
		roleSkillInfo.addSkillToPlayer(playerSkillVO_new);

		// 重新加载快捷键
		RoleShortCutInfo roleShortCutInfo = roleEntity.getRoleShortCutInfo();
		roleShortCutInfo.updateShortcutName(playerSkillVO.getSPk(), skillvo
				.getSkName());
		// 修改该字符串 通知刘威
		result = "您学习了" + StringUtil.isoToGBK(skillvo.getSkName());
		return result;
	}

	/**
	 * 传入玩家PPK 和 物品信息
	 * 
	 * 返回 0表示熟练度不够 返回大于0 技能ID表示有技能条件的技能没有学习
	 * 
	 */
	public String studySkillAll(int p_pk, PropVO prop,
			PropUseEffect propUseEffect)
	{
		String display = "";
		String terms_one = prop.getPropOperate2();// 技能限制
		String terms_two = prop.getPropOperate1();// 熟练度和其他限制
		String[] terms_str_one = terms_one.split(",");
		String[] terms_str_two = terms_two.split(",");
		int study_sk_id = Integer.parseInt(terms_str_one[0].trim());
		propUseEffect.setSkillId(study_sk_id + "");
		int retult_one = studySkillTermsBySkill(p_pk, terms_one);
		if (retult_one > 0)
		{
			display = "您还没有学习" + StringUtil.isoToGBK(getSkillName(retult_one));
			return display;
		}
		else
			if (retult_one == -2)
			{
				display = "您已经学习过"
						+ StringUtil.isoToGBK(getSkillName(study_sk_id));
				return display;
			}
		int retult_two = studySkillTermsBySleight(p_pk, terms_two);
		// 熟练度没达到要求
		if (retult_two == 0)
		{
			return "您的" + StringUtil.isoToGBK(getSkillName(study_sk_id))
					+ "的熟练度没达到要求!";
		}
		// 初始技能
		else
			if (retult_two == -2)
			{
				display = studySkill(p_pk, study_sk_id);
				return display;
			}
		// 技能升级的处理
		PlayerSkillVO vo = getSkillOfPlayerByGroup(p_pk, Integer
				.parseInt(terms_str_two[0].trim()));
		display = updateSkill(p_pk, study_sk_id, vo);
		return display;
	}
	
	
	// 七伤拳的单独处理 返回的是使用的血量 如果为 -1 代表不能使用
	public int getIsUsedSevenKill(PartInfoVO player)
	{
		int result = -1;
		int usedHp_one = player.getPUpHp() / 10;// 最大血量的百分之十
		int usedHp_two = player.getPHp() * 7 / 10;// 当前血量的百分之七十
		int hp = player.getPHp();
		if (hp < usedHp_one)
		{
			return result;
		}
		else
		{
			if (usedHp_two > hp)
			{
				return result;
			}
			else
			{
				return usedHp_two;
			}
		}
	}
	
	//蛤蟆功的单独处理,返回的是使用的内力
/*	public int getIsUsedHaMaSkill(PartInfoVO player)
	{
		int result=-1;
		int usedMp_two=500;
		int mp=player.getPMp();
		if(mp<usedMp_two)
		{
			return result;
		}
		else {
			return usedMp_two;
		}
		
		
	}*/

	/** 得到被动技能的属性 */
	public PassSkillInterface getPassSkillPropertyInfo(int p_pk)
	{
		RoleService roleService = new RoleService();
		RoleEntity role_info = roleService.getRoleInfoById(p_pk + "");
		if (role_info == null)
		{
			Passskill passskill = new Passskill();
			return passskill.getPassSkillByPPk(p_pk);
		}
		else
		{
			return role_info.getRoleSkillInfo().getPassSkillProperty();
		}
	}

	/**
	 * 得到玩家身上所有主动攻击的技能
	 */
	public List<PlayerSkillVO> getAttackSkills(int p_pk)
	{
		RoleService roleService = new RoleService();
		RoleEntity role_info = roleService.getRoleInfoById(p_pk + "");
		if (role_info != null)
		{
			return role_info.getRoleSkillInfo().getAttackSkillList();
		}
		else
		{
			PlayerSkillDao playerSkillDao = new PlayerSkillDao();
			return playerSkillDao.getAttackSkills(p_pk);
		}
	}

	/**
	 * 通过主键得到玩家技能信息
	 */
	public PlayerSkillVO getSkillInfo(int p_pk, int s_pk)
	{
		RoleEntity role_info = RoleService.getRoleInfoById(p_pk + "");
		if (role_info != null)
		{
			return role_info.getRoleSkillInfo().getSkillBySPk(s_pk);
		}
		else
		{
			PlayerSkillDao playerSkillDao = new PlayerSkillDao();
			return playerSkillDao.getById(s_pk);
		}
	}
	
	/**
	 * 2010.4.16 PM
	 * @author  雷郝艺
	 * @param PlayerSkillVO
	 * @return boolean
	 *   新增加的技能可能不能对NPC进行攻击，在此做统一判断
	 *   如果再增加了不能攻击NPC的技能在此添加case即可
	 */
	public boolean isTowardsNPC(PlayerSkillVO playerSkillVO)
	{
		if(playerSkillVO!=null)
		{
			int skillType=playerSkillVO.getSkType();
			switch(skillType)
			{
				case 6:
					return true;
				case 7:
					return true;
				case 8:
					return true;
				case 9:
					return true;
				case 10:
					return true;
					default:
						return false;
			}
		}
		return false;
	}
}
