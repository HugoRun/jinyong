package com.ls.web.service.player;

import org.apache.log4j.Logger;

import com.ls.ben.dao.info.partinfo.UGrowDao;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.partinfo.UGrowVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.system.SystemConfig;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.log.LogService;
import com.ls.web.service.rank.RankService;
import com.pm.service.systemInfo.SystemInfoService;

/**
 * @author ls 功能:玩家成长的相关逻辑 Mar 5, 2009
 */
public class GrowService
{

	Logger logger = Logger.getLogger("log.service");

	// 经验倍数
	public final static int EXP_MULTIPLE = 1;
	// npc掉落经验倍数
	public final static int EXP_NPCDROP = 1;

	/**
	 * 得到成长信息
	 */
	private UGrowVO getGrowInfo(RoleEntity roleEntity)
	{
		if (roleEntity == null)
		{
			logger.info("参数为空");
			return null;
		}
		int grade = roleEntity.getBasicInfo().getGrade();
		int race = roleEntity.getBasicInfo().getPRace();
		UGrowDao growDao = new UGrowDao();
		UGrowVO grow = growDao.getByGradeAndRace(grade, race);
		return grow;
	}

	/**
	 * 角色丢失经验处理每次减(next_exp-benji_exp)/20点经验，如果减去之后小于本级经验则不减经验
	 * 
	 * 04.09 玩家掉落经验减为原来的一半,即2.5%
	 * 
	 * @param player
	 * @return
	 */
	public long dropExperience(PartInfoVO player)
	{
		if (player.getPlayer_state_by_new() == 1)
		{
			return 0;
		}

		// pk为30级以下不掉经验
		if (player.getPGrade() <= 29)
			return 0;

		// 实际掉落的经验
		long dropedExp = 0;
		if (player == null)
		{
			return 0;
		}
		long current_exp = Long.parseLong(player.getPExperience());
		long benji_exp = Long.parseLong(player.getPBjExperience());
		long next_exp = Long.parseLong(player.getPXiaExperience());
		// 死亡后掉落本级经验的5%
		long shouldDropExp = benji_exp*5/100;
		/* 当当前经验等于本级经验的时候不掉 */
		if ((current_exp - shouldDropExp) < benji_exp)
		{
			dropedExp = 0;
			current_exp = benji_exp;
		}
		else
		{
			/*  */
			current_exp = current_exp - shouldDropExp;
			dropedExp = shouldDropExp;
		}

		PropertyService propertyService = new PropertyService();
		propertyService.updateExpProperty(player.getPPk(), current_exp);

		logger.debug("角色掉落经验:" + dropedExp);
		return dropedExp;
	}

	/**
	 * 死亡掉落当前等级经验的10%
	 * 
	 * @param player
	 * @return
	 */
	public long dropPlayerExperience(PartInfoVO player)
	{
		// 洪荒为10级以下不掉经验
		if (player.getPGrade() <= 10)
			return 0;
		long dropedExp = 0;
		if (player == null)
		{
			return 0;
		}
		long current_exp = Long.parseLong(player.getPExperience());
		long benji_exp = Long.parseLong(player.getPBjExperience());
		// 死亡后掉落本级经验的5%
		long shouldDropExp = benji_exp*5/100;
		/* 当当前经验等于本级经验的时候不掉 */
		if ((current_exp - shouldDropExp) < benji_exp)
		{
			dropedExp = current_exp-benji_exp;
			current_exp = benji_exp;
		}
		else
		{
			/*  */
			current_exp = current_exp - shouldDropExp;
			dropedExp = shouldDropExp;
		}

		PropertyService propertyService = new PropertyService();
		propertyService.updateExpProperty(player.getPPk(), current_exp);

		logger.debug("角色掉落经验:" + dropedExp);
		return dropedExp;
	}

	/**
	 * 给角色增加经验(角色的成长),，如过角色升级返回成长描述
	 */
	public String playerGrow(RoleEntity roleEntity, PartInfoVO player,
			long experience)
	{

		String grow_display = null;
		PartInfoVO character = player;
		long current_experience = Long.parseLong(character.getPExperience()
				.trim());// 当前经验值
		long xia_experience = Long.parseLong(character.getPXiaExperience()
				.trim());// 下一级所需经验值

		long last_experience = current_experience + experience;// 增加所得经验后的经验值

		if (player.getPGrade() == GameConfig.getGradeUpperLimit())// 如果到最高级则不升级
		{
			if (current_experience == xia_experience)
			{
				// 到最高级进行的操作
				grow_display = maxGradeDoThing(roleEntity, experience, 1);
				return grow_display;
			}
			else
			{
				if (current_experience > xia_experience)
				{
					roleEntity.getBasicInfo().updateCurExp(xia_experience + "");
					grow_display = maxGradeDoThing(roleEntity, experience, 1);
					return grow_display;
				}
				else
				{
					if (last_experience > xia_experience)
					{
						last_experience = xia_experience;
						grow_display = "您获得了:经验+" + experience + "点";
					}
				}
			}
		}
		else
		{
			if (character == null)
			{
				logger.info("角色增加经验时出错:角色类为空。");
				return null;
			}

			if (last_experience > xia_experience)// 当前经验已到升级经验要求
			{

				UGrowDao growDao = new UGrowDao();
				boolean isAutoGrow = growDao.isAutogrow(character.getPGrade(),
						character.getPRace());

				if (!isAutoGrow)// 大于等于9级时，不可以自然升级
				{
					// 当前经验已到升级经验要求,但不能自然升级，需要转职道具升级
					last_experience = xia_experience;// 不能升级时经验不增加，当前经验就是下级经验
					grow_display = "升级经验已达到,完成转职任务后方可升级";
				}
				else
				// 可以自然升级
				{
					grow_display = upgrade(character, roleEntity);// 玩家升级
				}
			}
			else
			{
				grow_display = "您获得了:经验+" + experience + "点";
			}
		}

		logger.info("给角色增加经验:" + experience + "点");

		// 监控
		LogService logService = new LogService();
		logService.recordExpLog(roleEntity.getBasicInfo().getPPk(), roleEntity
				.getBasicInfo().getName(), roleEntity.getBasicInfo()
				.getCurExp(), experience + "", "打怪得到");

		// 统计需要
		new RankService().updateAdd(roleEntity.getBasicInfo().getPPk(),
				"p_exp", experience);
		// 给玩家增加经验
		updateExperience(character, last_experience);

		character.setPExperience(last_experience + "");

		return grow_display;
	}

	/**
	 * 
	 * @param roleEntity
	 * @param experience
	 * @param type
	 *            为1代表有返回,为0代表返回null值
	 * @return
	 */
	private String maxGradeDoThing(RoleEntity roleEntity, long experience,
			int type)
	{
		String maxGradeDisplay = null;
		if (roleEntity.getBasicInfo().getGrade() == GameConfig
				.getGradeUpperLimit()
				&& roleEntity.getBasicInfo().getCurExp().equals(
						roleEntity.getBasicInfo().getNextGradeExp()))
		{
			int translateExp = Math.round((float) ((float) experience / 1000));
			roleEntity.getBasicInfo().addCopper(translateExp);

			maxGradeDisplay = "由于您已经到达最高级,所以所得经验的千分之一转化为金钱!<br/>获得"
					+ translateExp+"灵石";
		}

		if (type == 1)
		{
			return maxGradeDisplay;
		}
		else
		{
			return null;
		}
	}

	/**
	 * 给玩家增加经验
	 * 
	 * @param character
	 * @param last_experience
	 */
	private void updateExperience(PartInfoVO character, long last_experience)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(character.getPPk()
				+ "");
		roleInfo.getBasicInfo().updateCurExp(last_experience + "");
	}

	/**
	 * 任务给角色增加经验(角色的成长),，如过角色升级返回成长描述
	 */
	public String playerGrowTask(RoleEntity roleEntity, PartInfoVO player,
			int experience)
	{
		String grow_display = null;
		PartInfoVO character = player;

		if (character == null)
		{
			logger.info("角色增加经验时出错:角色类为空。");
			return null;
		}

		long current_experience = Long.parseLong(character.getPExperience());// 当前经验值
		long xia_experience = Long.parseLong(character.getPXiaExperience());// 下一级所需经验值

		long last_experience = current_experience + experience;// 增加所得经验后的经验值
		if (player.getPGrade() == GameConfig.getGradeUpperLimit())// 如果到最高级则不升级
		{

			if (current_experience == xia_experience)
			{
				// 到最高级进行的操作
				grow_display = maxGradeDoThing(roleEntity, experience, 1);
				return grow_display;
			}
			else
			{
				if (current_experience > xia_experience)
				{
					roleEntity.getBasicInfo().updateCurExp(xia_experience + "");
					grow_display = maxGradeDoThing(roleEntity, experience, 1);
					return grow_display;
				}
				else
				{
					if (last_experience > xia_experience)
					{
						last_experience = xia_experience;
					}
				}
			}

		}
		else
		{
			if (last_experience > xia_experience)// 当前经验已到升级经验要求
			{

				UGrowDao growDao = new UGrowDao();
				boolean isAutoGrow = growDao.isAutogrow(character.getPGrade(),
						character.getPRace());

				if (!isAutoGrow)// 不可以自然升级
				{
					// 当前经验已到升级经验要求,但不能自然升级，需要转职道具升级
					last_experience = xia_experience;// 不能升级时经验不增加，当前经验就是下级经验
					grow_display = "升级经验已达到,完成专职任务后方可升级";
				}
				else
				// 可以自然升级
				{
					grow_display = upgrade(character, roleEntity);// 玩家升级
				}
			}
			else
			{
				logger.info("给角色增加经验" + experience + "点");
			}
		}

		// 监控
		LogService logService = new LogService();
		logService.recordExpLog(roleEntity.getBasicInfo().getPPk(), roleEntity
				.getBasicInfo().getName(), roleEntity.getBasicInfo()
				.getCurExp(), experience + "", "任务得到");

		// 统计需要
		new RankService().updateAdd(roleEntity.getBasicInfo().getPPk(),
				"p_exp", experience);
		character.setPExperience(last_experience + "");

		roleEntity.getBasicInfo().updateCurExp(last_experience + "");

		return grow_display;
	}

	/**
	 * 将当前经验掉落到经验金钱表中
	 */
	/*
	 * public void dropExpToExpTable(int p_pk, long exp) { RoleEntity roleEntity =
	 * RoleService.getRoleInfoById(p_pk + ""); if(roleEntity != null){
	 * roleEntity.getStateInfo().setDeadDropExp(exp); } }
	 */

	/**
	 * 玩家升级，根据成长表，更新u_part_info表
	 * 
	 * @param player
	 * @return 升级描述
	 */
	public String upgrade(PartInfoVO player, RoleEntity roleEntity)
	{
		String upgrade_display = "";
		if (player == null)
		{
			logger.info("参数为空");
			return upgrade_display;
		}
		if(player.getPGrade()==GameConfig.getGradeUpperHighLimit())
		{
			return upgrade_display;
		}
		SystemInfoService SystemInfoService = new SystemInfoService();

		// 玩家等级加一
		player.setPGrade(player.getPGrade() + 1);
		// 升级处理
		roleEntity.getBasicInfo().addGrade();

		// 发送升级系统消息 
		SystemInfoService.sendSystemInfoByPGrade(player.getPPk(), player.getPGrade());
		// 发送新手引导
		SystemInfoService.setNewPlayerGuideInfoMSG(roleEntity, 10 + "", player
				.getPGrade()
				+ "");
		// 新浪的新手
		SystemInfoService.setNewPlayerGuideInfoMSG(roleEntity, 11 + "", player
				.getPGrade()
				+ "");

		// 得到成长信息
		UGrowVO grow = getGrowInfo(roleEntity);
		upgrade_display = "恭喜您,您升到" + roleEntity.getBasicInfo().getGrade()
		+ "级!气血增加" + grow.getGHP() + ",内力增加" + grow.getGMP() + ",攻击增加"
		+ grow.getGGj() * SystemConfig.attackParameter + ",防御增加"
		+ grow.getGFy() * SystemConfig.attackParameter + "!";
		return upgrade_display;
	}
}
