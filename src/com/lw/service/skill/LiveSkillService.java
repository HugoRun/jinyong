package com.lw.service.skill;

import java.util.ArrayList;
import java.util.List;

import com.ls.ben.cache.staticcache.skill.SkillCache;
import com.ls.ben.dao.info.skill.PlayerSkillDao;
import com.ls.ben.vo.info.skill.PlayerSkillVO;
import com.ls.ben.vo.info.skill.SkillVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.StatisticsType;
import com.ls.web.service.log.LogService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.skill.SkillService;
import com.lw.dao.skill.LiveSkillDao;
import com.lw.dao.skill.PlayerLiveSkillDao;
import com.lw.dao.synthesize.PlayerSynthesizeDao;
import com.lw.dao.synthesize.SynthesizeDao;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;

public class LiveSkillService
{
	/**
	 * 判断玩家是否学习过该生活技能
	 * 
	 * @return true 为可以学习该技能 false 为不能学习该技能
	 */
	public boolean isHaveThisLiveSkill(int p_pk, String sk_id)
	{
		SkillCache skillCaChe = new SkillCache();		
		int sk_group = skillCaChe.getGroupById(sk_id);
		
		List list = getPlayerLiveSkill(p_pk);
		if (list.size() == 0)
		{
			return true;
		}
		else
			if (list.size() == 1)
			{
				PlayerSkillVO playervo = (PlayerSkillVO) list.get(0);
								
				if (playervo.getSkGroup() != sk_group)
				{
					return true;
				}
			}
			else
				if (list.size() == 2)
				{
					PlayerSkillVO playervo = (PlayerSkillVO) list.get(0);
					PlayerSkillVO playervo1 = (PlayerSkillVO) list.get(1);
					if (playervo.getSkGroup() != sk_group
							&& playervo1.getSkGroup() != sk_group)
					{
						return true;
					}
				}	

		return false;

	}

	/**
	 * 判断玩家是否可以学习生活技能
	 * 
	 * @return true 为可以学次生活技能 false为不能学习
	 */
	public boolean isHaveLiveSkill(int p_pk)
	{
		PlayerLiveSkillDao dao = new PlayerLiveSkillDao();
		int num = dao.PlayerLiveSkillNum(p_pk);
		if (num == 2)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	/** 得到玩家可以学习的生活技能信息 */
	public List<SkillVO> getLiveSkill()
	{
		LiveSkillDao dao = new LiveSkillDao();
		List<SkillVO> skill = dao.getLiveSkillInfo();
		return skill;
	}

	/** 得到玩家学习过的生活技能 */
	public List getPlayerLiveSkill(int p_pk)
	{
		PlayerLiveSkillDao dao = new PlayerLiveSkillDao();
		List list = dao.getPlayerLiveSkill(p_pk);
		return list;
	}

	/** 忘记生活技能 */
	public void delLiveSkill(int s_pk)
	{
		PlayerLiveSkillDao dao = new PlayerLiveSkillDao();
		dao.delLiveSkill(s_pk);
	}

	/** 玩家学习生活技能 */
	public void studyLiveSkill(int p_pk, int sk_id, int money)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoById(p_pk+"");
		
		SkillService ss = new SkillService();
		ss.studySkill(p_pk, sk_id);
		
		//监控
		LogService logService = new LogService();
		logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", -money+"", "学习生活技能");
		
		roleInfo.getBasicInfo().addCopper(-money);
		// 执行统计
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(6,StatisticsType.MONEY, money, StatisticsType.USED, StatisticsType.BUY,p_pk);
	}

	/** 判断玩家具有哪种类型的生活技能 */
	public List getPlayerLiveSkillType(int p_pk)
	{
		List list = new ArrayList();
		PlayerLiveSkillDao dao = new PlayerLiveSkillDao();
		List skill = dao.getPlayerLiveSkill(p_pk);
		if (skill.size() == 0)
		{
			return null;
		}
		if (skill.size() == 1)
		{
			PlayerSkillVO vo1 = (PlayerSkillVO) skill.get(0);
			if (vo1.getSkName().contains("烹饪"))
			{
				list.add(1);
			}
			if (vo1.getSkName().contains("炼药"))
			{
				list.add(2);
			}
			if (vo1.getSkName().contains("锻造"))
			{
				list.add(3);
			}
			if (vo1.getSkName().contains("织造"))
			{
				list.add(4);
			}
			if (vo1.getSkName().contains("珠宝"))
			{
				list.add(5);
			}
			if (vo1.getSkName().contains("木匠"))
			{
				list.add(6);
			}
		}
		if (skill.size() == 2)
		{
			PlayerSkillVO vo1 = (PlayerSkillVO) skill.get(0);
			PlayerSkillVO vo2 = (PlayerSkillVO) skill.get(1);
			if (vo1 != null)
			{
				if (vo1.getSkName().contains("烹饪"))
				{
					list.add(1);
				}
				if (vo1.getSkName().contains("炼药"))
				{
					list.add(2);
				}
				if (vo1.getSkName().contains("锻造"))
				{
					list.add(3);
				}
				if (vo1.getSkName().contains("织造"))
				{
					list.add(4);
				}
				if (vo1.getSkName().contains("珠宝"))
				{
					list.add(5);
				}
				if (vo1.getSkName().contains("木匠"))
				{
					list.add(6);
				}
			}
			if (vo2 != null)
			{
				if (vo2.getSkName().contains("烹饪"))
				{
					list.add(1);
				}
				if (vo2.getSkName().contains("炼药"))
				{
					list.add(2);
				}
				if (vo2.getSkName().contains("锻造"))
				{
					list.add(3);
				}
				if (vo2.getSkName().contains("织造"))
				{
					list.add(4);
				}
				if (vo2.getSkName().contains("珠宝"))
				{
					list.add(5);
				}
				if (vo2.getSkName().contains("木匠"))
				{
					list.add(6);
				}
			}
		}
		return list;
	}

	/**
	 * 判断玩家是否具有该类型的生活技能
	 * 
	 * @return true 为有 false 为没有
	 */
	public boolean isHaveSkillType(int p_pk, int s_type)
	{
		if (getPlayerLiveSkillType(p_pk) == null)
		{
			return false;
		}
		List list = getPlayerLiveSkillType(p_pk);
		if (list.size() == 1)
		{
			int s1 = Integer.parseInt(list.get(0).toString());
			if (s1 == s_type)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			int s1 = Integer.parseInt(list.get(0).toString());
			int s2 = Integer.parseInt(list.get(1).toString());
			if (s1 == s_type)
			{
				return true;
			}
			else
				if (s2 == s_type)
				{
					return true;
				}
				else
				{
					return false;
				}
		}
	}

	/**
	 * 得到玩家技能等级
	 * 
	 * @return 0为没有此技能 1为初级 2为中级 3为高级 4为宗师级
	 */
	public int getPlayerLiveSkillLevel(int p_pk, int s_type)
	{
		List list = new ArrayList();
		int sk_group = getSkillGroup(s_type);
		PlayerLiveSkillDao dao = new PlayerLiveSkillDao();
		List skill = dao.getPlayerLiveSkill(p_pk);
		if (skill.size() == 1)
		{
			PlayerSkillVO vo1 = (PlayerSkillVO) skill.get(0);
			if (vo1 != null && vo1.getSkGroup() == sk_group)
			{
				if (vo1.getSkName().contains("初级"))
				{
					return 1;
				}
				if (vo1.getSkName().contains("中级"))
				{
					return 2;
				}
				if (vo1.getSkName().contains("高级"))
				{
					return 3;
				}
				if (vo1.getSkName().contains("宗师"))
				{
					return 4;
				}
			}
		}
		else
		{
			PlayerSkillVO vo1 = (PlayerSkillVO) skill.get(0);
			PlayerSkillVO vo2 = (PlayerSkillVO) skill.get(1);
			if (vo1 != null && vo1.getSkGroup() == sk_group)
			{
				if (vo1.getSkName().contains("初级"))
				{
					return 1;
				}
				if (vo1.getSkName().contains("中级"))
				{
					return 2;
				}
				if (vo1.getSkName().contains("高级"))
				{
					return 3;
				}
				if (vo1.getSkName().contains("宗师"))
				{
					return 4;
				}
			}
			if (vo2 != null && vo2.getSkGroup() == sk_group)
			{
				if (vo2.getSkName().contains("初级"))
				{
					return 1;
				}
				if (vo2.getSkName().contains("中级"))
				{
					return 2;
				}
				if (vo2.getSkName().contains("高级"))
				{
					return 3;
				}
				if (vo2.getSkName().contains("宗师"))
				{
					return 4;
				}
			}
		}
		return 0;
	}

	/**
	 * 判断玩家是否具有技能书
	 * 
	 * @return true为有 false为没有
	 */
	public boolean isPlayerHaveSynthesizeBook(int p_pk, int s_id)
	{
		SynthesizeDao sdao = new SynthesizeDao();
		PlayerSynthesizeDao dao = new PlayerSynthesizeDao();
		if (sdao.getSynthesizeHaveBook(s_id) != 0
				&& dao.getPlayerSynthesize(p_pk, s_id) != 0)
		{
			return true;
		}
		else
			if (sdao.getSynthesizeHaveBook(s_id) == 0)
			{
				return true;
			}
			else
			{
				return false;
			}
	}

	/** 根据技能类型获取技能组 */
	public int getSkillGroup(int s_type)
	{
		int group = 0;
		if (s_type == 1)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "烹饪(初级)";
			group = dao.getLiveSkillGroup(sk_name);
		}
		if (s_type == 2)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "炼药(初级)";
			group = dao.getLiveSkillGroup(sk_name);
		}
		if (s_type == 3)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "锻造(初级)";
			group = dao.getLiveSkillGroup(sk_name);
		}
		if (s_type == 4)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "织造(初级)";
			group = dao.getLiveSkillGroup(sk_name);
		}
		if (s_type == 5)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "珠宝(初级)";
			group = dao.getLiveSkillGroup(sk_name);
		}
		if (s_type == 6)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "木匠(初级)";
			group = dao.getLiveSkillGroup(sk_name);
		}
		return group;
	}

	/** 根据技能组得到玩家的熟练度 */
	public int getPlayerSleight(int p_pk, int sk_group)
	{
		PlayerSkillDao dao = new PlayerSkillDao();
		PlayerSkillVO vo = dao.getSkillInfoByGroup(p_pk, sk_group);
		return vo.getSkSleight();
	}

	/** 更新生活技能熟练度 */
	public void updatePlayerSleight(int p_pk, int sk_group, int sleight,
			int maxsleight)
	{
		PlayerSkillDao dao = new PlayerSkillDao();
		int playersleight = getPlayerSleight(p_pk, sk_group);
		int sleight1 = playersleight + sleight;
		if (sleight1 > maxsleight)
		{

			dao.updateUsetimeAndSleight(p_pk, sk_group, maxsleight);
		}
		else
		{
			dao.updateUsetimeAndSleight(p_pk, sk_group, sleight1);
		}
	}

	/** 根据技能类型获取技能组 */
	public int getSkillID(int s_type)
	{
		int id = 0;
		if (s_type == 1)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "烹饪(初级)";
			id = dao.getLiveSkillID(sk_name);
		}
		if (s_type == 2)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "炼药(初级)";
			id = dao.getLiveSkillID(sk_name);
		}
		if (s_type == 3)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "锻造(初级)";
			id = dao.getLiveSkillID(sk_name);
		}
		if (s_type == 4)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "织造(初级)";
			id = dao.getLiveSkillID(sk_name);
		}
		if (s_type == 5)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "珠宝(初级)";
			id = dao.getLiveSkillID(sk_name);
		}
		if (s_type == 6)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "木匠(初级)";
			id = dao.getLiveSkillID(sk_name);
		}
		return id;
	}
}
