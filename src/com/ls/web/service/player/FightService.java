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
 * ����:�������ս���������
 * 
 * @author ��˧ 4:53:40 PM
 */
public class FightService
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * ���A�����������B
	 * 
	 * @param playerA
	 * @param playerB
	 */
	public void attackPlayer(Fighter playerA, Fighter playerB)
	{
		if (playerA == null || playerB == null)
		{
			logger.debug("��������");
			return;
		}

		if (playerA.getSkill() == null)
		{
			// ������

			physicsAttackPlayer(playerA, playerB);
		}
		else
		{
			// ���ܹ���
			skillAttackPlayer(playerA, playerB);
		}
		// ����PK��ȥ�����־�
		EquipService equipService = new EquipService();
		equipService.useWeapon(playerA.getPPk());
		// ����PK��ȥ�����־�
		equipService.useEquip(playerB.getPPk());
	}

	/**
	 * pkʱ���ܹ���
	 * @param playerA
	 * @param playerB
	 * @param bout
	 */
	private void skillAttackPlayer(Fighter playerA, Fighter playerB)
	{
		//�жϲ���Ϊ���򷵻ز�������
		if (playerA == null || playerA == null)
		{
			logger.debug("��������");
			return;
		}
		PlayerSkillVO playerSkill = playerA.getSkill();
		//�жϼ���Ϊ���򷵻ز�������
		if (playerSkill == null)
		{
			logger.debug("���û��װ�ؼ���");
			return;
		}
		//��ʾ�����Ƿ����ʹ��
		boolean isUse = true;
		//���MP��������ʹ�ü���
		if(playerSkill.getSkExpend().equals("MP"))
		{
			if (playerSkill.getSkUsecondition() > playerA.getPMp())
			{
				isUse = false;
				playerA.setSkillNoUseDisplay("�������㣬����ʹ�ô˼���<br/>");
				playerA.setSkill(null);
			}
			else
			{
				//�۳���ʹ�õ�MP
				playerA.setExpendMP(playerSkill.getSkUsecondition());
				PropertyService propertyService = new PropertyService();
				propertyService.updateMpProperty(playerA.getPPk(), playerA.getPMp());
			}
		}
		// �������������������
		if (!isUse)
		{
			return;
		}
		//���ܹ������ܹ������ܹ������ܹ������ܹ������ܹ���
		int injure =this.getInjure(playerA, playerB, playerSkill);
		logger.debug("�����˺���:" + injure);
		// ���¼���ʹ�����
		SkillService sse = new SkillService();
		sse.useSkill(playerA.getPPk(), playerA.getSkill());
		// ����playerB״̬
		playerB.setPlayerInjure(injure);
		statUpdateByPlayerInjure(playerB, playerA, -1);
		//ʦ�ż��ܸ���Ҹ��ӵ�BUFFЧ��
		givePlayerBuff(playerB, playerSkill);
	}

	/**
	 * pkʱ������
	 * 
	 * @param playerA
	 * @param playerB
	 * @param bout
	 */
	public void physicsAttackPlayer(Fighter playerA, Fighter playerB)
	{
		if (playerA == null || playerB == null)
		{
			logger.debug("��������");
			return;
		}
		int injure = 0;
		int wxInjure = 0;
		int physicsInjure = 0;
		// �õ������˺�
		wxInjure = AttackService.getWxInjureValue(playerA.getWx(), playerB.getWx(), playerA.getPGrade(), playerB.getPGrade(), playerA.getPPk());
		// �õ������˺�
		physicsInjure = AttackService.normalInjureExpressions(getPlayerActBySkillContent(playerA, playerA.getGj()),getPlayerDefBySkillContent(playerB, playerB.getFy()), playerA.getPGrade(), playerB.getPGrade());
		//�ж϶Է�����Ѿ��ж��򹥻���ǿ
		RoleEntity roleInfoB = RoleCache.getByPpk(playerB.getPPk());
		int huihe= roleInfoB.getBasicInfo().getActhuihe();
		if(roleInfoB.getBasicInfo().isPoisoning()&&roleInfoB.getBasicInfo().getAddDamage()>0&&roleInfoB.getBasicInfo().getPoisonCount()>0)
		{
			physicsInjure+=roleInfoB.getBasicInfo().getAddDamage();
		}
		//�ж���һ������ʹ��������������Ч���������˺�����2����Ч��
		if (MathUtil.PercentageRandomByParamdouble((playerA.getDropMultiple()),
				100))
		{
			playerA.setSkillDisplay("��ͨ����,�����˱���!");
			// �˶���ҵļ����˺�=��ҹ�����ҵļ����˺�/1.5+��Ҷ���ҵ������˺���
			injure = (wxInjure + physicsInjure * 3 / 2);// �����˺�2��
		}
		else
		{
			playerA.setSkillDisplay("��ͨ����.");
			// �˶���ҵļ����˺�=��ҹ�����ҵļ����˺�/1.5+��Ҷ���ҵ������˺���
			injure = (wxInjure + physicsInjure * 3 / 2) / 2;
		}
		if (injure <= 0)
		{
			injure = 1;
		}

		logger.debug("�����˺���:" + injure);

		// ����playerB״̬
		playerB.setPlayerInjure(injure);
		statUpdateByPlayerInjure(playerB,playerA,-1);
	}

	/**
	 * �Զ�����ʱ�������һ������ʹ�õ�������������
	 * 
	 * @param playerA
	 * @return true��ʾ���سɹ���false��ʾ����ʧ��
	 */
	private boolean loadRandAttackSkill(Fighter playerA)
	{
		int p_pk = playerA.getPPk();

		SkillService skillService = new SkillService();

		// �õ�������ϵ������������м���
		List<PlayerSkillVO> skillList = skillService.getAttackSkills(p_pk);

		if (skillList != null && skillList.size() != 0)
		{
			SkillDao skillDao = new SkillDao();
			PlayerSkillVO skill = null;
			for (int i = skillList.size() - 1; i >= 0; i--)
			{
				skill = skillList.get(i);

				skillDao.loadPlayerSkillDetailByInside(skill);// ���ؼ�����������

				if (skillService.isUsabled(playerA, skill) != null)
				{
					// ���ܲ���ʹ��
					skillList.remove(i);
				}
			}
		}

		if (skillList != null && skillList.size() != 0)
		{
			int rand_index = MathUtil.getRandomBetweenXY(0,
					skillList.size() - 1);

			PlayerSkillVO rand_skill = skillList.get(rand_index);// �õ��������
			playerA.setSkill(rand_skill);
			return true;
		}
		else
		{
			return false;
		}

	}

	/**
	 * pkʱ����Զ�������playerA����playerB��ֻ��������
	 * 
	 * @param playerA
	 * @param playerB
	 * @param bout
	 */
	public void autoCounterattack(Fighter playerA, Fighter playerB)
	{
		boolean is_successed = loadRandAttackSkill(playerA);// ����һ�����õ������������

		RoleEntity roleInfoA = RoleCache.getByPpk(playerA.getPPk());
		if(roleInfoA.getBasicInfo().getXuanyunhuihe() >0){
			roleInfoA.getBasicInfo().setXuanyunhuihe(roleInfoA.getBasicInfo().getXuanyunhuihe() - 1);
			return;
		}	
		if (is_successed == true)// ����м��ܷ�����ʹ�ü��ܹ���
		{
			skillAttackPlayer(playerA, playerB);
		}
		else
		{
			physicsAttackPlayer(playerA, playerB);
		}

		// ����PK��ȥ�����־�
		EquipService equipService = new EquipService();
		equipService.useWeapon(playerA.getPPk());
		// ����PK��ȥ�����־�
		equipService.useEquip(playerB.getPPk());
	}

	/**
	 * ��ɫ����npcs
	 * 
	 * @param player
	 * @param npcs
	 */
	public void attackNPC(Fighter player, List npcs)
	{
		if (player == null || npcs == null || npcs.size() == 0)
		{
			logger.debug("��������");
			return;
		}

		if (player.getSkill() == null)
		{
			// ������
			physicsAttackNPC(player, npcs);
		}
		else
		{
			// ���ܹ���
			skillAttackNPC(player, npcs);
		}

		// ��ּ�ȥ�����־�
		EquipService equipService = new EquipService();
		equipService.useWeapon(player.getPPk());

		// ����buff״̬
		BuffEffectService buffEffectService = new BuffEffectService();
		buffEffectService.updateSpareBout(player.getPPk());
	}

	/**
	 * ���ܹ���npc
	 */
	public void skillAttackNPC(Fighter player, List npcs)
	{
		if (player == null || npcs == null || npcs.size() == 0)
		{
			logger.debug("��������");
			return;
		}
		PlayerSkillVO playerSkill = player.getSkill();
		if (playerSkill == null)
		{
			logger.debug("���û��װ�ؼ���");
			return;
		}
		/** *******************�жϼ����Ƿ���ʹ��*********************** */
		SkillService skillService = new SkillService();
		String use_display = null;/*//skillService.isUsabled(player, playerSkill);

		// �жϼ����Ƿ�����
		if (use_display != null)
		{
			logger.debug("��ɫ�������㣬����ʹ�ô˼���");
			player.setSkillNoUseDisplay(use_display + "<br/>");
			player.setSkill(null);
			return;
		}*/

		// ���¼���ʹ��״̬
		PlayerSkillDao playerSkillDao = new PlayerSkillDao();
		if (playerSkill.getSkType() != 4)// ��ѧ�ж�
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

		/*********************�жϼ����Ƿ���ʹ�ý���*********************** */
		NpcService npcService = new NpcService();

		NpcFighter npc = (NpcFighter) npcs.get(0);
		int injure = 0;
		int skillInjure = 0;
		int wxInjure = 0;

		wxInjure = AttackService.getWxInjureValue(player.getWx(), npc, player
				.getPGrade(), npc.getLevel(), player.getPPk());// * 10;

		int skillGj = (playerSkill.getSkDamage() + player.getGj());
		logger.info("************���ܹ���: ���Թ�����װ������:" + player.getGj() + ";���ܹ���:"
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
        //�������ܹ���
		if (playerSkill.getSkArea() == 1) // Ⱥ�幥��
		{
			logger.info("��ɫ����Ⱥ�幥������");

			logger.info("npcs.size()=" + npcs.size());

			for (int i = npcs.size() - 1; i >= 0; i--)
			{
				npc = (NpcFighter) npcs.get(i);
				// ���npc����Ϊ���,�ͻ᲻�������
				if (npc.getNpcType() == NpcAttackVO.MAST)
				{
					logger.info("��Ϊ�����,���в��������");
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
					// ��ʾЧ��
					player.setSkillDisplay(playerSkill.getSkName()
							+ "����,�����˱���Ч��!");
				}
				else
				{
					skillInjure = AttackService.normalInjureExpressions(
							skillGj, npc.getNpcDefance(), player
									.getPGrade(), npc.getLevel());
					injure = skillInjure + wxInjure + 20;
					if (player.getSkillDisplay() == null)
					{
						// ��ʾЧ��
						player.setSkillDisplay(playerSkill.getSkName()
								+ "����.");
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
				// logger.info("������npc����="+npcs.size());
			}

			if (npcs.size() > 0)
			{
				// �Ƿ��������Ч��
				if (isAttackDizzyNPC)
				{
					npcService.addDizzyBoutNumOfNPCs(player.getPPk(),playerSkill.getSkYunBout());
				}
			}
		}
		else
		// ���幥��
		{
			logger.info("��ɫ���������幥������");
			skillInjure = AttackService
					.normalInjureExpressions(skillGj, npc.getNpcDefance(),
							player.getPGrade(), npc.getLevel());
			if (MathUtil.PercentageRandomByParamdouble((player
					.getDropMultiple()), 100))
			{
				injure = (skillInjure + wxInjure) * 2 + 20;
				// ��ʾЧ��
				player.setSkillDisplay(playerSkill.getSkName()
						+ "����,�����˱���Ч��!");
			}
			else
			{
				injure = skillInjure + wxInjure + 20;
				// ��ʾЧ��
				player.setSkillDisplay(playerSkill.getSkName() + "����.");
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
				// �Ƿ��������Ч��
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
		// ���¼���ʹ�����
		SkillService sse = new SkillService();
		sse.useSkill(player.getPPk(), player.getSkill());
	
	}

	/**
	 * ������
	 * 
	 * @param player
	 * @param npcs
	 * @param bout
	 */
	public void physicsAttackNPC(Fighter player, List npcs)
	{
		if (player == null || npcs == null || npcs.size() == 0)
		{
			logger.debug("��������");
			return;
		}
		// ������һֻNPC
		NpcFighter npc = (NpcFighter) npcs.get(0);

		int injureValue = 0;
		int wxInjureValue = 0;
		int physicsInjureValue = 0;
		AttacckCache attacckCache = new AttacckCache();

		// �����˺�
		wxInjureValue = AttackService.getWxInjureValue(player.getWx(), npc,
				player.getPGrade(), npc.getLevel(), player.getPPk());// * 10;
		// �����˺�
		physicsInjureValue = AttackService.getPhysicsInjureValue(player, npc);

		logger.info("�����˺�:" + wxInjureValue);
		logger.info("�����˺�:" + physicsInjureValue);
		if (MathUtil.PercentageRandomByParamdouble((player.getDropMultiple()),
				100))
		{
			injureValue = (physicsInjureValue + wxInjureValue) * 2 + 20;
			if (injureValue <= 0)
			{
				injureValue = 1;
			}

			// ����NPC״̬
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
			player.setSkillDisplay("��ͨ�����������˱���Ч����");
		}
		else
		{

			// ���˺�
			injureValue = physicsInjureValue + wxInjureValue + 20;

			if (injureValue <= 0) 
			{
				injureValue = 1;
			}

			// ����NPC״̬
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
			// ���»غ�״̬
			player.setSkillDisplay("��ͨ������");
		}

	}

	/**
	 * ս��ʤ��ʱ��������õ����ܵľ���ͳ���õ����ܵľ���
	 * ��ɫ����ļ��㹫ʽ��(ԭʼ����+ϵͳ���鱶���ӳɵľ���+buff�õ��ľ��鱶��+���߾���)*���Ч��
	 * ע��ϵͳ���鱶���ӳɵľ��飺��ϵͳ�����Ǿͼ�һ�� ���ﾭ��ļ��㹫ʽ��(ԭʼ����+buff�õ��ľ��鱶��)*���Ч��
	 * ע��buff�õ��ľ��鱶������ָ����ľ��鱶����buff
	 * 
	 * @param player
	 * @param exp_of_role
	 *            ��ɫ�õ���ԭʼ����
	 * @param exp_of_pet
	 *            ����õ���ԭʼ����
	 * @return ���Ϊresult[0]��ʾ��ɫ�õ����ܵľ��飬result[1]��ʾ����õ����ܵľ���
	 */
	private int[] getFigthWinExp(PartInfoVO player, int exp_of_role,
			int exp_of_pet)
	{
		int result[] = { exp_of_role, exp_of_role };

		int total_exp_of_role = exp_of_role;
		int total_exp_of_pet = exp_of_pet;

		int p_pk = player.getPPk();
		int scene_id = Integer.valueOf(player.getPMap());

		// ȥ�����߾���RoleBeOffBuffService roleBeOffBuffService = new
		// RoleBeOffBuffService();
		ExpnpcdropService expnpcdropService = new ExpnpcdropService();
		RoomService sceneService = new RoomService();
		GroupService groupService = new GroupService();

		int groupEffectValue = 0;// ���Ч��ֵ.˵��:��������û�о���ӳɵ�Ч��

		if (sceneService.getMapType(scene_id) != MapType.INSTANCE)// �Ǹ�������
		{
			groupEffectValue = groupService.getGroupEffectPercent(p_pk,
					scene_id);// �õ����Ч��ֵ
		}

		logger.info("���Ч��ֵ��" + groupEffectValue);

		// ********************������������ܵľ���

		int sys_exp_multiple = expnpcdropService.getExpNpcdrop();// ϵͳ���鱶��

		int role_sys_exp_append = exp_of_role * (sys_exp_multiple - 1);// ϵͳ����ӳɡ�˵������Ϊ�ǼӶ�����ù�ϵ�����Ա���Ҫ��һ
		int role_buff_exp_append = (int) (1.0 * exp_of_role
				* player.getAppendExp() / 100); // buff����ӳ�.
		// int role_off_line_buff_exp =
		// roleBeOffBuffService.getRoleOffLineExp(p_pk, exp_of_role);//�õ���ҵ����߾���

		total_exp_of_role = (int) (1.0
				* (exp_of_role + role_sys_exp_append + role_buff_exp_append)
				* (100 + groupEffectValue) / 100);

		logger.info("��ɫԭʼ���飺" + exp_of_role + ";ϵͳ�ӳɾ���:" + role_sys_exp_append
				+ ";buff�ӳɾ���:" + role_buff_exp_append + ";");
		logger.info("��ɫ�ܾ���:" + total_exp_of_role);
		// ********************������������ܵľ������

		// ********************������������ܵľ���
		int pet_buff_exp_append = (int) (1.0 * exp_of_pet
				* player.getAppendPetExp() / 100); // buff����ӳ�

		total_exp_of_pet = (int) (1.0 * (exp_of_pet + pet_buff_exp_append)
				* (100 + groupEffectValue) / 100); // �ܾ���

		logger.info("����ԭʼ���飺" + exp_of_pet + ";" + pet_buff_exp_append);
		logger.info("�����ܾ���:" + total_exp_of_pet);
		// ********************������������ܵľ������

		result[0] = total_exp_of_role;
		result[1] = total_exp_of_pet;
		return result;
	}

	/**
	 * ��ɫʤ���Ĵ���
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

		/** *****************ս�����������þ����Ǯ������********************************** */
		DropExpMoneyVO dropExpMoney = role_info.getDropSet().getExpAndMoney();

		int exp_result[] = getFigthWinExp(player, dropExpMoney.getDropExp(),
				dropExpMoney.getDropPetExp());

		int total_exp_of_role = exp_result[0];// ��ҵõ����ܵľ���
		int total_exp_of_pet = exp_result[1];// ����õ����ܵľ���
		MyService my = new MyServiceImpl();
		if (my.isTeaOnline(p_pk))
		{
			total_exp_of_role = total_exp_of_role * ShituConstant.MORE_EXP
					/ 100;
		}

		winList.setExp(total_exp_of_role);
		winList.setMoney(dropExpMoney.getDropMoney());
		/** *************************************************************************** */
		// ��¼����boss����
		instanceService.archive(p_pk, Integer.parseInt(player.getPMap()));

		// ���Ӿ���,��ҳɳ�
		String grow_display = growService.playerGrow(role_info, player,
				total_exp_of_role);
		winList.setGrowDisplay(grow_display);

		my.shareExp(p_pk, player.getPGrade(), total_exp_of_role);
		// ����Ǯ
		economyService.addMoney(player, dropExpMoney.getDropMoney());
		// ִ��ͳ��
		gsss.addPropNum(6, StatisticsType.MONEY, dropExpMoney.getDropMoney(),
				StatisticsType.DEDAO, StatisticsType.DAGUAI, p_pk);

		// NPC�������Ʒ
		List<DropGoodsVO> npcdrops = role_info.getDropSet().getList();
		winList.setDropGoods(npcdrops);
		role_info.getDropSet().clearExpAndMoney();
		return winCharacter;
	}

	/**
	 * PK������Ʒ
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
				// ��װ��
				goodsService.dropGoods(playerB, playerA);
			}
		}
	}
	/**
	 * pk�����������ʯ��ʤ���߼���
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
			loser.appendKillDisplay("��ʧ").append(dropLingshi).append(GameConfig.getMoneyUnitName()).append("<br/>");
			winer.appendKillDisplay("�õ�").append(dropLingshi).append(GameConfig.getMoneyUnitName()).append("<br/>");
		}
	}
	
	/**
	 * ��ɫ��NPC��������������ֵ�����������Ʒ,HP,MP��ʼ����
	 * 
	 * @param dead  ������
	 * @param playerA  ���Ϊnpcս��,�����Ǻ�playeBһ����,ֻ��Ϊ�˽ӿ�һ��. ���Ϊpkս��,��playerAΪʤ����.
	 * @return
	 */
	private PartInfoVO dead(Fighter dead, Fighter playerA,int npc_type)
	{
		if( dead.getPPk()==playerA.getPPk())
		{
			//��NPC����
			deadByNpc(dead,npc_type);
		}
		else
		{
			//��������Ҵ���
			deadByPK(dead, playerA);
		}
		// ���������������
		deadMustDoThing(dead);
		return dead;
	}
	
	/**
	 * ��ұ�NPC����
	 * @param dead
	 * @param npc_type	npc����
	 * @return
	 */
	private PartInfoVO deadByNpc(Fighter dead,int npc_type)
	{
		RoleEntity dead_info = RoleService.getRoleInfoById(dead.getPPk());
		
		TimeControlService timeService = new TimeControlService();
		GrowService growService = new GrowService();
		// �鿴�˵ص�ĵص������Ƿ���侭��  ������е�ͼ��������
		//boolean isSceneDropExp = roomService.isLimitedByAttribute(Integer.valueOf(dead.getPMap()), RoomService.DROP_EXP);
		// ��������Ƿ�����������Чʱ����, ���ھͲ�������
		boolean isOutOfPublish = timeService.isUseableToTimeControl(dead.getPPk(), PropType.OUTPUBLISH, TimeControlService.ANOTHERPROP);
		Vip vip = dead_info.getTitleSet().getVIP();	
		long drop_exp = growService.dropPlayerExperience(dead);
		dead_info.getStateInfo().setShouldDropExperience(drop_exp);
		dead_info.getStateInfo().setDeadDropExp(drop_exp);
		dead.setDropExp(drop_exp);
		if (isOutOfPublish==false || vip!=null)
		{
			//�������侭��
			dead.setNotDropExp(true);
		}
		return dead;
	}
	
	/**
	 * PK��������
	 * @param loser			ʧ�ܵ����
	 * @param winner		ʤ�������
	 * @param tong			ս������
	 * @return					
	 */
	private PartInfoVO deadByPK(Fighter loser, Fighter winner)
	{
		RoleEntity loser_info = RoleService.getRoleInfoById(loser.getPPk());
		RoleEntity winer_info = RoleService.getRoleInfoById(winner.getPPk());
		
		//���Ӱ��ɹ���
		if( winner.getPRace()!=loser.getPRace() )//�������һ������
		{
			if( winer_info.getBasicInfo().addFContributeAndFPrestige() )
			{
				winner.appendKillDisplay("����1����������,��������1������").append("<br/>");
			}
		}
			
		//�������ֵ
		addPkValue(winner,loser);
		
		//������ʯ
		dropLingshi(loser, winner);
		//����Ʒ
		dropGoodsByPK(loser, winner);
		// ������
		dropExp(loser, winner);
		//��ӳ����Ϣ 
		AttackService.processPkHite(loser,winner);
		//����װ���;�����
		String consume_equip_endure_des = loser_info.getEquipOnBody().consumeMaxEndure();
		//��ʤ����Ҽ���ɱ����
		new MenpaiContestService().updatePlayerKillNum(winner.getPPk(),loser.getPPk());
		// ͳ����Ҫ
		new RankService().updateAdd(winner.getPPk(), "kill", 1);
		new RankService().updateAdd(loser.getPPk(), "dead", 1);
		
		//��ʼ��ɱ���ߵ�ս����ر���
		winer_info.getBasicInfo().initFightState();
		
		//****************************��������
		if( StringUtils.isEmpty(consume_equip_endure_des)==false )
		{
			loser.appendKillDisplay(consume_equip_endure_des).append("<br/>");
		}
		return loser;
	}

	/**
	 * ���������������
	 * @param dead ������
	 */
	private void deadMustDoThing(Fighter dead)
	{
		RoleEntity dead_info = RoleCache.getByPpk(dead.getPPk());
		
		PlayerService playerService = new PlayerService();
		PropertyService propertyService = new PropertyService();
		
		// 07.16�¼�,���������stateInfo��
		dead_info.getStateInfo().setDeadDropExp(dead.getDropExp());
		//��ʼ��ɱ���ߵ�״̬����
		dead_info.getBasicInfo().initFightState();
		
		//��ʼ��Ѫ���ͷ���
		propertyService.updateHpProperty(dead.getPPk(), dead.getPUpHp());
		propertyService.updateMpProperty(dead.getPPk(), dead.getPUpMp());

		// ��Ȼ��������ص����, ������Ұģ����.�����ѡ��ʹ���������ߣ�����ص㷵��ԭ�㣬����
		dead_info.getBasicInfo().setShouldScene(Integer.parseInt(dead_info.getBasicInfo().getSceneId()));
		playerService.initPositionWithOutView(dead);
		//��������ؾ��ص�
		SceneVO scene = dead_info.getBasicInfo().getSceneInfo();
		RoomService roomservice = new RoomService();
		String test= dead_info.getBasicInfo().getShouldScene()+"";
		int resurrection_point = roomservice.getResurrectionPoint(dead_info);
		dead_info.getBasicInfo().updateSceneId(resurrection_point + "");
	}

	/**
	 * ���侭��,���������,����䵽stateInfo��
	 * @param loser
	 * @param winer
	 * @return
	 */
	private void dropExp(Fighter loser, Fighter winner)
	{
		TimeControlService timeService = new TimeControlService();
		GrowService growService = new GrowService();
		RoomService roomService = new RoomService();
		
		// �鿴�˵ص�ĵص������Ƿ���侭��
		boolean is_dropexp_by_scene = roomService.isLimitedByAttribute(Integer.valueOf(loser.getPMap()), RoomService.DROP_EXP);
		if (true)//Ŀǰ�ĵ�ͼ�����侭��
		{
			// ��������Ƿ�����������Чʱ����, ���ھͲ�������
			boolean is_punished = timeService.isUseableToTimeControl(loser.getPPk(), PropType.OUTPUBLISH, TimeControlService.ANOTHERPROP);
			if( is_punished )
			{
				// ���������û����������
				PlayerPropGroupDao ppgdao = new PlayerPropGroupDao();
				int miansi_prop_num = ppgdao.getPropNumByPropType(loser.getPPk(),PropType.EXONERATIVE);
				loser.setDeadProp(miansi_prop_num);
				
				RoleEntity role_info = RoleService.getRoleInfoById(loser.getPPk()+"");
				Vip vip = role_info.getTitleSet().getVIP();
				if ( vip == null)//���ǻ�Ա�ŵ�����
				{
					long dropexp = growService.dropExperience(loser);
					loser.setDropExp(dropexp);
					if( dropexp>0 )
					{
						loser.appendKillDisplay("���侭�飺").append(dropexp).append("��<br/>");
					}
				}
			}
		}
	}


	/**
	 * playerA����playerB��playerB�ܵ������˺���������״̬
	 * 
	 * @param playerA
	 * @param player
	 * @param injure
	 * @param bout
	 * @param tong  -1Ϊnpc��0Ϊ��ͨ��1Ϊ��ս��2Ϊ��Ӫpk
	 * @return
	 */
	public boolean statUpdateByPlayerInjure(Fighter playerB, Fighter playerA,int npc_type)
	{
		PropertyService propertyService = new PropertyService();
		//����ж�����»���
		RoleEntity roleInfoB = RoleCache.getByPpk(playerB.getPPk());
		// �ж��Ƿ�����
		if (!playerB.isDead())// û��
		{
			//�ж�������ж�״̬����Ļغ���  �ж�ֻ��3���غ�����Ч��
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
		{//����
			propertyService.updateHpProperty(playerA.getPPk(), playerA.getPHp());
			dead(playerB, playerA, npc_type);
		}

		return false;
	}

	/**
	 * playerA����playerB��playerB�ܵ������˺���������״̬
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

		// �ж��Ƿ�����
		if (!playerB.isDead())// û��
		{
			PropertyService propertyService = new PropertyService();
			propertyService.updateHpProperty(playerB.getPPk(), playerB.getPHp());
		}
		else
		// ����
		{
			dead(playerB, playerA ,npc_type);
		}

		return false;
	}

	/**
	 * �������ֵ playerAɱ��playerB
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
		
		if( winner_info.getPKState().isZhudongAttackOther(loser.getPPk())==false )//ֻ���������������������ֵ
		{
			return 0;
		}
		
		if( winner.getPPks()==2 && loser.getPPks()==2 && winner.getPRace()!=loser.getPRace())//�������ͬһ���壬��PK���ض����ţ����������ֵ
		{
			return 0;
		}
		
		if (loser.getPPkValue() >= GameArgs.RED_NAME_VALUE)// ����״̬�������ֵ
		{
			return 0;
		}
		
		// [����ҵȼ�-��ɱ����ҵȼ���+1]*10
		int add_pk_value = (winner.getPGrade() - loser.getPGrade() + 1) * 10;
		if (add_pk_value < 10)
		{
			add_pk_value = 10;
		}
		// �Է��ǻ���״̬���ֵ��2
		if (loser.getPPkValue() > GameArgs.YELLOW_NAME_VALUE && loser.getPPkValue() < GameArgs.RED_NAME_VALUE)
		{
			add_pk_value = add_pk_value / 2;
		}
		
		if (add_pk_value > 0)
		{
			winner.setPPkValue(winner.getPPkValue() + add_pk_value);
			winner.appendKillDisplay("����").append(add_pk_value).append("�����ֵ<br/>");
			
			winner_info.getBasicInfo().addEvilValue(add_pk_value);
		}
		return add_pk_value;
	}

	/**
	 * �����ʹ�þ�ת��Ӧ�ûص���sceneID
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

		if (mapType == MapType.TONGBATTLE)//��ս��ͼ
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
	 * ���㼼�ܹ���ʱ�ԶԷ���ɵ��˺�ֵ
	 * @param playerA
	 * @param playerB
	 */
	private int getInjure(Fighter playerA,Fighter playerB,PlayerSkillVO playerSkill)
	{
		int injure = 0;
		int skillInjure = 0;
		int wxInjure = 0;
		// �õ������˺�ֵ
		wxInjure = AttackService.getWxInjureValue(playerA.getWx(), playerB.getWx(), playerA.getPGrade(), playerB.getPGrade(), playerA.getPPk());
		// �õ����ܹ�����
		int skillGj = ((playerSkill.getSkDamage() + playerA.getGj()));
		playerA.setSkillDisplay(playerSkill.getSkName() + "����.");
		logger.debug("��ɫ���������幥������");
		skillInjure = AttackService.normalInjureExpressions(getPlayerActBySkillContent(playerA, skillGj),getPlayerDefBySkillContent(playerB, playerB.getFy()), playerA.getPGrade(), playerB.getPGrade());
		// �˶���ҵ������˺�=��ҹ�����ҵ������˺�/1.5+��Ҷ���ҵ������˺���
		injure = (skillInjure * 3 / 2 + wxInjure) / 2;
		playerA.setSkillDisplay(playerSkill.getSkName() + "����.");
		// ��һ�����ʲ�������
		if (MathUtil.PercentageRandomByParamdouble((playerA.getDropMultiple()),
				100))
		{
			// �˺�2�� 8.6
			injure = (skillInjure * 3 / 2 + wxInjure);
			playerA.setSkillDisplay(playerSkill.getSkName() + "����,�����˱���Ч��!");
		}
		if (injure <= 0)
		{
			injure = 1;
		}
		return injure;
	}
	
	
	//ʦ�ż��ܸ���Ҹ���BUFFЧ��
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
						roleInfo.getBasicInfo().setMenpaiskilldisplay(roleInfo.getBasicInfo().getName()+"��ѣ��״̬,�޷�����");
					}
					if(buff_id == 2){
						roleInfo.getBasicInfo().setActhuihe(5);
						roleInfo.getBasicInfo().setActcontent(content);
						roleInfo.getBasicInfo().setMenpaiskilldisplay(roleInfo.getBasicInfo().getName()+"�Ĺ������½�"+content+"%");
					}
					if(buff_id == 3){
						roleInfo.getBasicInfo().setDefhuihe(5);
						roleInfo.getBasicInfo().setDefcontent(content);
						roleInfo.getBasicInfo().setMenpaiskilldisplay(roleInfo.getBasicInfo().getName()+"�ķ������½�"+content+"%");
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
