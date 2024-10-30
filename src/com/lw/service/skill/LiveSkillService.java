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
	 * �ж�����Ƿ�ѧϰ���������
	 * 
	 * @return true Ϊ����ѧϰ�ü��� false Ϊ����ѧϰ�ü���
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
	 * �ж�����Ƿ����ѧϰ�����
	 * 
	 * @return true Ϊ����ѧ������� falseΪ����ѧϰ
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

	/** �õ���ҿ���ѧϰ���������Ϣ */
	public List<SkillVO> getLiveSkill()
	{
		LiveSkillDao dao = new LiveSkillDao();
		List<SkillVO> skill = dao.getLiveSkillInfo();
		return skill;
	}

	/** �õ����ѧϰ��������� */
	public List getPlayerLiveSkill(int p_pk)
	{
		PlayerLiveSkillDao dao = new PlayerLiveSkillDao();
		List list = dao.getPlayerLiveSkill(p_pk);
		return list;
	}

	/** ��������� */
	public void delLiveSkill(int s_pk)
	{
		PlayerLiveSkillDao dao = new PlayerLiveSkillDao();
		dao.delLiveSkill(s_pk);
	}

	/** ���ѧϰ����� */
	public void studyLiveSkill(int p_pk, int sk_id, int money)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoById(p_pk+"");
		
		SkillService ss = new SkillService();
		ss.studySkill(p_pk, sk_id);
		
		//���
		LogService logService = new LogService();
		logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", -money+"", "ѧϰ�����");
		
		roleInfo.getBasicInfo().addCopper(-money);
		// ִ��ͳ��
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(6,StatisticsType.MONEY, money, StatisticsType.USED, StatisticsType.BUY,p_pk);
	}

	/** �ж���Ҿ����������͵������ */
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
			if (vo1.getSkName().contains("���"))
			{
				list.add(1);
			}
			if (vo1.getSkName().contains("��ҩ"))
			{
				list.add(2);
			}
			if (vo1.getSkName().contains("����"))
			{
				list.add(3);
			}
			if (vo1.getSkName().contains("֯��"))
			{
				list.add(4);
			}
			if (vo1.getSkName().contains("�鱦"))
			{
				list.add(5);
			}
			if (vo1.getSkName().contains("ľ��"))
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
				if (vo1.getSkName().contains("���"))
				{
					list.add(1);
				}
				if (vo1.getSkName().contains("��ҩ"))
				{
					list.add(2);
				}
				if (vo1.getSkName().contains("����"))
				{
					list.add(3);
				}
				if (vo1.getSkName().contains("֯��"))
				{
					list.add(4);
				}
				if (vo1.getSkName().contains("�鱦"))
				{
					list.add(5);
				}
				if (vo1.getSkName().contains("ľ��"))
				{
					list.add(6);
				}
			}
			if (vo2 != null)
			{
				if (vo2.getSkName().contains("���"))
				{
					list.add(1);
				}
				if (vo2.getSkName().contains("��ҩ"))
				{
					list.add(2);
				}
				if (vo2.getSkName().contains("����"))
				{
					list.add(3);
				}
				if (vo2.getSkName().contains("֯��"))
				{
					list.add(4);
				}
				if (vo2.getSkName().contains("�鱦"))
				{
					list.add(5);
				}
				if (vo2.getSkName().contains("ľ��"))
				{
					list.add(6);
				}
			}
		}
		return list;
	}

	/**
	 * �ж�����Ƿ���и����͵������
	 * 
	 * @return true Ϊ�� false Ϊû��
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
	 * �õ���Ҽ��ܵȼ�
	 * 
	 * @return 0Ϊû�д˼��� 1Ϊ���� 2Ϊ�м� 3Ϊ�߼� 4Ϊ��ʦ��
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
				if (vo1.getSkName().contains("����"))
				{
					return 1;
				}
				if (vo1.getSkName().contains("�м�"))
				{
					return 2;
				}
				if (vo1.getSkName().contains("�߼�"))
				{
					return 3;
				}
				if (vo1.getSkName().contains("��ʦ"))
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
				if (vo1.getSkName().contains("����"))
				{
					return 1;
				}
				if (vo1.getSkName().contains("�м�"))
				{
					return 2;
				}
				if (vo1.getSkName().contains("�߼�"))
				{
					return 3;
				}
				if (vo1.getSkName().contains("��ʦ"))
				{
					return 4;
				}
			}
			if (vo2 != null && vo2.getSkGroup() == sk_group)
			{
				if (vo2.getSkName().contains("����"))
				{
					return 1;
				}
				if (vo2.getSkName().contains("�м�"))
				{
					return 2;
				}
				if (vo2.getSkName().contains("�߼�"))
				{
					return 3;
				}
				if (vo2.getSkName().contains("��ʦ"))
				{
					return 4;
				}
			}
		}
		return 0;
	}

	/**
	 * �ж�����Ƿ���м�����
	 * 
	 * @return trueΪ�� falseΪû��
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

	/** ���ݼ������ͻ�ȡ������ */
	public int getSkillGroup(int s_type)
	{
		int group = 0;
		if (s_type == 1)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "���(����)";
			group = dao.getLiveSkillGroup(sk_name);
		}
		if (s_type == 2)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "��ҩ(����)";
			group = dao.getLiveSkillGroup(sk_name);
		}
		if (s_type == 3)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "����(����)";
			group = dao.getLiveSkillGroup(sk_name);
		}
		if (s_type == 4)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "֯��(����)";
			group = dao.getLiveSkillGroup(sk_name);
		}
		if (s_type == 5)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "�鱦(����)";
			group = dao.getLiveSkillGroup(sk_name);
		}
		if (s_type == 6)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "ľ��(����)";
			group = dao.getLiveSkillGroup(sk_name);
		}
		return group;
	}

	/** ���ݼ�����õ���ҵ������� */
	public int getPlayerSleight(int p_pk, int sk_group)
	{
		PlayerSkillDao dao = new PlayerSkillDao();
		PlayerSkillVO vo = dao.getSkillInfoByGroup(p_pk, sk_group);
		return vo.getSkSleight();
	}

	/** ��������������� */
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

	/** ���ݼ������ͻ�ȡ������ */
	public int getSkillID(int s_type)
	{
		int id = 0;
		if (s_type == 1)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "���(����)";
			id = dao.getLiveSkillID(sk_name);
		}
		if (s_type == 2)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "��ҩ(����)";
			id = dao.getLiveSkillID(sk_name);
		}
		if (s_type == 3)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "����(����)";
			id = dao.getLiveSkillID(sk_name);
		}
		if (s_type == 4)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "֯��(����)";
			id = dao.getLiveSkillID(sk_name);
		}
		if (s_type == 5)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "�鱦(����)";
			id = dao.getLiveSkillID(sk_name);
		}
		if (s_type == 6)
		{
			LiveSkillDao dao = new LiveSkillDao();
			String sk_name = "ľ��(����)";
			id = dao.getLiveSkillID(sk_name);
		}
		return id;
	}
}
