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
 * ����:��Ҽ��ܹ���
 * 
 * @author ��˧
 * 
 * 1:36:22 PM
 */
public class SkillService
{

	Logger logger = Logger.getLogger("log.service");

	/**
	 * ���ܹ���npc
	 */
	public int skillAttackNPC(Fighter player, List npcs)
	{
		int skillInjure = 0;
		if (player == null || npcs == null || npcs.size() == 0)
		{
			logger.info("��������");
			return skillInjure;
		}
		PlayerSkillVO playerSkill = player.getSkill();
		if (playerSkill == null)
		{
			logger.info("���û��װ�ؼ���");
			return skillInjure;
		}

		boolean isUse = true;// ��ʾ�˼����Ƿ����
		/** *******************�жϼ����Ƿ���ʹ��*********************** */
		// ������Ķ�����mp
		if (playerSkill.getSkExpend().equals("MP"))
		{
			if (playerSkill.getSkUsecondition() > player.getPMp())
			{
				isUse = false;
				logger.info("��ɫ�������㣬����ʹ�ô˼���");
				player.setSkillDisplay("�������㣬����ʹ�ô˼���<br/>");
			}
		}
		// �ж���ȴʱ���Ƿ��ѹ�

		// ���������������������
		if (!isUse)
			return skillInjure;
		/** *******************�жϼ����Ƿ���ʹ�ý���*********************** */
		NpcService npcService = new NpcService();

		NpcFighter npc = (NpcFighter) npcs.get(0);
		int wxInjure = AttackService.getWxInjureValue(player.getWx(), npc,
				player.getPGrade(), npc.getLevel(), player.getPPk());

		int skillGj = playerSkill.getSkDamage();

		int injure = 0;
		if (playerSkill.getSkType() == 1) // Ⱥ�幥��
		{
			logger.debug("��ɫ����Ⱥ�幥������");
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
		// ���幥��
		{
			logger.debug("��ɫ���������幥������");
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

		// ���¼���ʹ��״̬
		SkillService skillService = new SkillService();
		skillService.useSkill(player.getPPk(), playerSkill);

		player.setSkillDisplay(StringUtil.isoToGBK(playerSkill.getSkName()));
		player.setExpendMP(playerSkill.getSkUsecondition());

		RoleEntity roleInfo = RoleService.getRoleInfoById(player.getPPk() + "");
		roleInfo.getBasicInfo().updateMp(player.getPMp());

		return skillInjure;
	}

	/**
	 * �ж��Ƿ��в�׽����ļ���
	 * 
	 * @return
	 */
	public boolean havedCatchPet1()
	{
		boolean haved = false;
		return haved;
	}

	/**
	 * ѧϰ����
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
		// �޸ĸ��ַ��� ֪ͨ����
		result = "��ѧϰ��" + StringUtil.isoToGBK(skill_name);

		PlayerSkillVO playerSkillVO = null;
		playerSkillVO = playerSkillDao.getBySkId(p_pk, sk_id);

		if (playerSkillVO.getSkType() == 2 || playerSkillVO.getSkType() == 3
				|| playerSkillVO.getSkType() == 4)
		{
			result = "�������˾�ѧ" + StringUtil.isoToGBK(skill_name);
		}

		// ��ѧϰ�ļ��ܷ����ڴ���
		RoleSkillInfo roleSkillInfo = RoleCache.getByPpk(p_pk + "").getRoleSkillInfo();
		roleSkillInfo.addSkillToPlayer(playerSkillVO);

		return result;
	}

	/**
	 * ����s_pk�õ���Ҽ���
	 */
	public PlayerSkillVO getSkillOfPlayer(int p_pk, int sk_id)
	{
		// �õ���ɫskill��Ϣ
		PlayerSkillDao playerSkillDao = new PlayerSkillDao();
		PlayerSkillVO playerSkill = playerSkillDao.getPlayerSkillInfo(p_pk,
				sk_id);
		loadSkillDetail(playerSkill);
		return playerSkill;
	}

	/** ���ݼ�����õ�������Ϣ */
	public PlayerSkillVO getSkillOfPlayerByGroup(int p_pk, int sk_Group)
	{
		// �õ���ɫskill��Ϣ
		PlayerSkillDao playerSkillDao = new PlayerSkillDao();
		PlayerSkillVO playerSkill = playerSkillDao.getSkillInfoByGroup(p_pk,
				sk_Group);
		loadSkillDetail(playerSkill);
		return playerSkill;
	}

	/**
	 * ������Ҽ��ܵ���ϸ��Ϣ
	 */
	public void loadSkillDetail(PlayerSkillVO playerSkill)
	{
		if (playerSkill == null)
		{
			logger.info("����playerSkillΪ��");
			return;
		}
		SkillDao skillDao = new SkillDao();
		skillDao.loadPlayerSkillDetail(playerSkill);
	}

	/**
	 * �õ���������
	 */
	public String getSkillName(int sk_id)
	{
		SkillCache skillCaChe = new SkillCache();

		return skillCaChe.getNameById(sk_id);
	}

	/**
	 * ʹ�ü���
	 */
	public void useSkill(int p_pk, PlayerSkillVO skill)
	{
		// ��ɫ��������
		SkillUpService skillLevelUp = new SkillUpService();
		skillLevelUp.addSkillLevelUp(p_pk, skill);

		skill.setSkSleight(skill.getSkSleight() + 1);
		// ���¼���ʹ��״̬
		// PlayerSkillDao playerSkillDao = new PlayerSkillDao();
		// playerSkillDao.updateUsetimeAndSleight(p_pk, sk_id);
		// ���ݸ����ͷ�buff
		// todo:
	}

	/**
	 * �жϼ����Ƿ����
	 * 
	 * @param player
	 * @param playerSkill
	 * @return ���ؿձ�ʾ����ʹ��
	 */
	public String isUsabled(PartInfoVO player, PlayerSkillVO playerSkill)
	{
		String result = null;
		if (playerSkill == null || player == null)
		{
			logger.info("����Ϊ��");
			return "���ܲ���ʹ��";
		}
		/*// �жϼ���ʹ��Ҫ������������Ƿ���װ�����������
		EquipService equipService = new EquipService();
		if (playerSkill.getSkWeapontype() != 0
				&& playerSkill.getSkWeapontype() != equipService
						.getArmType(player.getPPk()))
		{
			logger.info("�������Ͳ���������ʹ�ô˼���");
			return result = "�������Ͳ���������ʹ�ô˼���";
		}*/

		// ������Ķ�����mp
		if (playerSkill.getSkExpend().equals("MP"))
		{
			// �ж������PK�����������񹦣���ָ��ͨ������ʮ���ƶ�Ҫ�������ſ���ʹ��
			if(playerSkill.getSkType()==7||playerSkill.getSkType()==8||playerSkill.getSkType()==10)
			{
					if(player.getPMp()!=player.getPUpMp())
					{
						return result="����"+playerSkill.getSkName()+"������������²ſ���ʹ�ô˼���,���Ȳ�����������";
					}
					//����ǵ�ָ��ͨ��Ҫ�ж��Ƿ�������Ѫ�����������Ѫ�ſ���ʹ�ô˼���
					if(playerSkill.getSkType()==8)
					{
						if(player.getPHp()!=player.getPUpHp())
						{
						    return result="����"+playerSkill.getSkName()+"������Ѫ����²ſ���ʹ�ô˼���,���Ȳ���������Ѫ";
						}
					}
			}
			else
			{
				if((playerSkill.getSkUsecondition() > player.getPMp()))
				{
    				return result = "�������㣬����ʹ�ô˼���";
				}
			}
		}

		// ������Ķ�����hp
		if (playerSkill.getSkExpend().equals("HP"))
		{
			if (playerSkill.getSkType() == 4)
			{
				if (getIsUsedSevenKill(player) == -1)
				{
					//"��ɫ��Ѫ���㣬����ʹ�ô˼���");
					return null;
				}
			}
			else
			{
				if (playerSkill.getSkUsecondition() > player.getPHp())
				{
					//logger.info("��ɫ��Ѫ���㣬����ʹ�ô˼���");
					return result = "��Ѫ���㣬����ʹ�ô˼���";
				}
			}
		}

		// �ж���ȴʱ���Ƿ��ѹ�

		if (!DateUtil.isOverdue(playerSkill.getSkUsetime(), playerSkill.getSkLqtime()))
		{
			return result = "��ȴʱ�仹û��";
		}
		//�жϴ˼����ܷ��NPC���������������ӵ�һ��ָ�Ͳ��ܶ�NPC���й���
		if(isTowardsNPC(playerSkill))
		{
			//logger.info("�˼��ܲ��ܶ�NPCʹ��");
			return result=playerSkill.getSkName()+" ����PK���ܲ��ܶ�NPC���й�����ѡ����������";
		}
		if (result == null)
		{
			// PlayerSkillDao playerSkillDao = new PlayerSkillDao();
			// ���¼���ʹ��״̬
			// playerSkillDao.updateUsetimeAndSleight(playerSkill.getPPk(),
			// playerSkill.getSkId());
		}

		return result;
	}

	/**
	 * �õ�������м���
	 */
	public List<PlayerSkillVO> getSkills111(int p_pk)
	{
		PlayerSkillDao playerSkillDao = new PlayerSkillDao();

		return playerSkillDao.getPlayerSkills(p_pk);
	}

	/**
	 * �ж��Ƿ��в�׽����ļ���
	 * 
	 * @param p_pk
	 */
	public boolean isHaveCatchPetSkill(int p_pk)
	{
		PlayerSkillDao playerSkillDao = new PlayerSkillDao();
		return playerSkillDao.isHaveCatchPetSkill(p_pk);
	}

	/**
	 * �õ���һ������������
	 */
	public int getNextLevelSleight(int sk_id)
	{
		SkillCache skillCaChe = new SkillCache();
		int sk_next_sleight = skillCaChe.getNextSleightById(sk_id + "");
		return sk_next_sleight;
	}

	/**
	 * ������ҵı�������Ч��
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
			 * //������ҳƺŸ���Ч��ֵ
			 *//** ���ӹ��� */
			/*
			 * int hoAttack = 0;
			 *//** ���ӷ��� */
			/*
			 * int hoDef = 0;
			 *//** ������Ѫ */
			/*
			 * int hoHp = 0;
			 *//** ���ӱ��� */
			/*
			 * double hoCrit = 0; HonourService honourService = new
			 * HonourService(); RoleHonourVO roleHonourVO =
			 * honourService.getRoleHonourIsReveal(player.getPPk(), 1);
			 * if(roleHonourVO!=null){ hoAttack = roleHonourVO.getHoAttack();
			 * hoDef = roleHonourVO.getHoDef(); hoHp = roleHonourVO.getHoHp();
			 * hoCrit = (double)roleHonourVO.getHoCrit(); }
			 */
			DecimalFormat df = new DecimalFormat("0.00");
			logger.info("������ҵı�����=" + passSkillInterface.getSkBjMultiple());
			// ������ҵı�����
			double d = player.getDropMultiple()
					+ passSkillInterface.getSkBjMultiple();
			String s = df.format(d);
			player.setDropMultiple(Double.parseDouble(s));

			// ������ҵ� ������
			player.setPGj((int) (player.getPGj() + infovo.getPGj()
					* (passSkillInterface.getSkGjMultiple())));
			player.setPGj(player.getPGj() + passSkillInterface.getSkGjAdd());

			// ������ҵķ�����
			player.setPFy((int) (player.getPFy() + infovo.getPFy()
					* (passSkillInterface.getSkFyMultiple())));
			player.setPFy(player.getPFy() + passSkillInterface.getSkFyAdd());

			// ������ҵ�mp
			player.setPUpMp((int) (player.getPUpMp() + infovo.getPUpMp()
					* (passSkillInterface.getSkMpMultiple())));
			player
					.setPUpMp(player.getPUpMp()
							+ passSkillInterface.getSkMpAdd());

			// ������ҵ�hp
			player.setPUpHp((int) (player.getPUpHp() + infovo.getPUpHp()
					* (passSkillInterface.getSkHpMultiple())));
			player
					.setPUpHp(player.getPUpHp()
							+ passSkillInterface.getSkHpAdd());

		}

	}

	/**
	 * ������ҵı�������Ч��
	 * @param player
	 */
	public void loadPassiveSkillEffectByCache(PartInfoVO player,RoleEntity role_info)
	{
		PassSkillInterface passSkillInterface = getPassSkillPropertyInfo(player.getPPk());

		if (passSkillInterface != null)
		{

			DecimalFormat df = new DecimalFormat("0.00");
			logger.info("������ҵı�����=" + passSkillInterface.getSkBjMultiple());
			// ������ҵı�����
			double d = player.getDropMultiple()+ passSkillInterface.getSkBjMultiple();
			String s = df.format(d);
			player.setDropMultiple(Double.parseDouble(s));

			// ������ҵ� ������
			player.setPGj((int) (player.getPGj() + role_info.getBasicInfo().getBasicGj()* (passSkillInterface.getSkGjMultiple())));
			player.setPGj(player.getPGj() + passSkillInterface.getSkGjAdd());

			// ������ҵķ�����
			player.setPFy((int) (player.getPFy() + role_info.getBasicInfo().getBasicFy()* (passSkillInterface.getSkFyMultiple())));
			player.setPFy(player.getPFy() + passSkillInterface.getSkFyAdd());

			// ������ҵ�mp
			player.setPUpMp((int) (player.getPUpMp() + role_info.getBasicInfo().getUpMp()* (passSkillInterface.getSkMpMultiple())));
			player.setPUpMp(player.getPUpMp()+ passSkillInterface.getSkMpAdd());

			// ������ҵ�hp
			player.setPUpHp((int) (player.getPUpHp() + role_info.getBasicInfo().getUpHp()* (passSkillInterface.getSkHpMultiple())));
			player.setPUpHp(player.getPUpHp()+ passSkillInterface.getSkHpAdd());

		}

	}

	// ѧϰ���ܵ������ж� ����-2 ��ʾ�Ѿ�ѧ���ü��� ����-1Ϊ����ѧϰ ���ش���0 ��ʾ����ID
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
	 * ���ص�ֵ 0���������Ȳ��� -1��ʾ����ѧϰ -2 ��ʾ�ü���Ϊ��ʼ����
	 **************************************************************************/
	private int studySkillTermsBySleight(int p_pk, String terms)
	{
		String[] terms_str = terms.split(",");// 0Ϊ������ һΪ������
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

		// �õ��ü��ܵ���Ϣ
		SkillVO skillvo = SkillCache.getById(sk_id);

		// �������ݿ����Ϣ
		playerSkillDao.updatePassSkill(p_pk, skillvo, playerSkillVO.getSkId());

		// ��ѧϰ�ļ��ܷ����ڴ���
		PlayerSkillVO playerSkillVO_new = playerSkillDao.getById(playerSkillVO
				.getSPk());
		RoleSkillInfo roleSkillInfo = roleEntity.getRoleSkillInfo();
		roleSkillInfo.addSkillToPlayer(playerSkillVO_new);

		// ���¼��ؿ�ݼ�
		RoleShortCutInfo roleShortCutInfo = roleEntity.getRoleShortCutInfo();
		roleShortCutInfo.updateShortcutName(playerSkillVO.getSPk(), skillvo
				.getSkName());
		// �޸ĸ��ַ��� ֪ͨ����
		result = "��ѧϰ��" + StringUtil.isoToGBK(skillvo.getSkName());
		return result;
	}

	/**
	 * �������PPK �� ��Ʒ��Ϣ
	 * 
	 * ���� 0��ʾ�����Ȳ��� ���ش���0 ����ID��ʾ�м��������ļ���û��ѧϰ
	 * 
	 */
	public String studySkillAll(int p_pk, PropVO prop,
			PropUseEffect propUseEffect)
	{
		String display = "";
		String terms_one = prop.getPropOperate2();// ��������
		String terms_two = prop.getPropOperate1();// �����Ⱥ���������
		String[] terms_str_one = terms_one.split(",");
		String[] terms_str_two = terms_two.split(",");
		int study_sk_id = Integer.parseInt(terms_str_one[0].trim());
		propUseEffect.setSkillId(study_sk_id + "");
		int retult_one = studySkillTermsBySkill(p_pk, terms_one);
		if (retult_one > 0)
		{
			display = "����û��ѧϰ" + StringUtil.isoToGBK(getSkillName(retult_one));
			return display;
		}
		else
			if (retult_one == -2)
			{
				display = "���Ѿ�ѧϰ��"
						+ StringUtil.isoToGBK(getSkillName(study_sk_id));
				return display;
			}
		int retult_two = studySkillTermsBySleight(p_pk, terms_two);
		// ������û�ﵽҪ��
		if (retult_two == 0)
		{
			return "����" + StringUtil.isoToGBK(getSkillName(study_sk_id))
					+ "��������û�ﵽҪ��!";
		}
		// ��ʼ����
		else
			if (retult_two == -2)
			{
				display = studySkill(p_pk, study_sk_id);
				return display;
			}
		// ���������Ĵ���
		PlayerSkillVO vo = getSkillOfPlayerByGroup(p_pk, Integer
				.parseInt(terms_str_two[0].trim()));
		display = updateSkill(p_pk, study_sk_id, vo);
		return display;
	}
	
	
	// ����ȭ�ĵ������� ���ص���ʹ�õ�Ѫ�� ���Ϊ -1 ������ʹ��
	public int getIsUsedSevenKill(PartInfoVO player)
	{
		int result = -1;
		int usedHp_one = player.getPUpHp() / 10;// ���Ѫ���İٷ�֮ʮ
		int usedHp_two = player.getPHp() * 7 / 10;// ��ǰѪ���İٷ�֮��ʮ
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
	
	//��󡹦�ĵ�������,���ص���ʹ�õ�����
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

	/** �õ��������ܵ����� */
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
	 * �õ���������������������ļ���
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
	 * ͨ�������õ���Ҽ�����Ϣ
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
	 * @author  �׺���
	 * @param PlayerSkillVO
	 * @return boolean
	 *   �����ӵļ��ܿ��ܲ��ܶ�NPC���й������ڴ���ͳһ�ж�
	 *   ����������˲��ܹ���NPC�ļ����ڴ����case����
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
