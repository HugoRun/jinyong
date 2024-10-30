package com.ls.web.service.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.ben.pk.active.PKActiveContent;
import com.ls.ben.cache.dynamic.manual.attack.AttacckCache;
import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.cache.dynamic.manual.view.ViewCache;
import com.ls.ben.cache.staticcache.map.SceneCache;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.dao.info.skill.SkillDao;
import com.ls.ben.vo.info.npc.NpcFighter;
import com.ls.ben.vo.info.partinfo.Fighter;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.skill.PlayerSkillVO;
import com.ls.ben.vo.map.MapVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.model.user.BasicInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.MapType;
import com.ls.pub.constant.buff.BuffType;
import com.ls.pub.constant.player.PlayerState;
import com.ls.pub.util.DateUtil;
import com.ls.web.service.buff.BuffEffectService;
import com.ls.web.service.group.GroupService;
import com.ls.web.service.room.RoomService;
import com.ls.web.service.skill.SkillService;
import com.lw.dao.laborage.PlayerLaborageDao;

public class PlayerService
{

	Logger logger = Logger.getLogger("log.service");


	/**
	 * �õ��û��Ľ�ɫ�б�
	 */
	public List<PartInfoVO> getRoleList(String u_pk)
	{
		PartInfoDao dao = new PartInfoDao();
		checkDeleteStateRoles(u_pk);
		return dao.getPartTypeList(u_pk);
	}

	/**
	 * �õ���������ʱ��
	 */
	public int getOnlineTimeInThisWeek(String pPk)
	{
		PlayerLaborageDao pldao = new PlayerLaborageDao();
		return pldao.getPlayerOnlineTimeNOW(Integer.parseInt(pPk));
	}

	/**
	 * ��鴦��ɾ������Ľ�ɫ�Ƿ񳬹������ڣ����������ѽ�ɫɾ�� ԭ��1Ϊ������0Ϊ����ɾ����-1 Ϊ�Ѿ�ɾ��
	 * ���ڸ�Ϊ0Ϊ������1Ϊ����ɾ����-1Ϊ�Ѿ�ɾ��
	 * 
	 * @param p_pk
	 * @param delete_flag
	 * @return
	 */
	private void checkDeleteStateRoles(String u_pk)
	{
		PartInfoDao partInfoDao = new PartInfoDao();
		List<PartInfoVO> role_list = partInfoDao.getDeleteStateRoles(u_pk);
		if (role_list != null && role_list.size() != 0)
		{
			for (PartInfoVO role : role_list)
			{
				{
					if (DateUtil.isOverdue(role.getDeleteTime(), DateUtil.DAY))// �ж��Ƿ񳬹�ɾ������ʱ�䣨��ǰ����ʱ��Ϊ1�죩
						updateDeleteFlag(role.getPPk(), -1);// �����ʱ��ɾ��
				}
			}
		}
	}

	/**
	 * ���ý�ɫ��ɾ��״̬
	 * 
	 * @param p_pk
	 * @param delete_flag
	 * @return
	 */
	public void updateDeleteFlag(int p_pk, int delete_flag)
	{
		PartInfoDao partInfoDao = new PartInfoDao();
		partInfoDao.updateDeleteFlag(p_pk, delete_flag);
	}


	/**
	 * ���������ʼ������ص�,���ǽ���Ұ����Ϊ��
	 * 
	 * @param player
	 * @return
	 */
	public PartInfoVO initPositionWithOutView(PartInfoVO player)
	{
		if (player == null)
		{
			logger.info("��ʼ����ҵص�:���Ϊ����Ч��");
		}
		RoleEntity roleEntity = RoleCache.getByPpk(player.getPPk());
		
		// �������������ĳЩ�½��д���, ���Ҳ�̫�����õ������ط�ȥ��
		dealWithDead(player);

		RoomService roomService = new RoomService();
		// �õ����������
		int resurrection_point = roomService.getResurrectionPoint(roleEntity);
		// ����map_id
		player.setPMap(resurrection_point + "");

		updateSceneAndView(player.getPPk(), resurrection_point);
		if(roleEntity != null){
			ViewCache viewCache = new ViewCache();
			viewCache.remove(roleEntity.getStateInfo().getView(), roleEntity);	
		}
		
		return player;
	}

	/**
	 * �������������ĳЩ�½��д���, ���Ҳ�̫�����õ������ط�ȥ��
	 */
	private void dealWithDead(PartInfoVO player)
	{

		RoomService roomService = new RoomService();
		SceneVO sceneVO = roomService.getById(player.getPMap());
		MapVO mapVO = sceneVO.getMap();
		if (mapVO.getMapType() == MapType.TONGBATTLE)
		{
		}
	}

	/**
	 * �����װ�ؼ�����ϸ��Ϣ
	 * @param sk_id
	 * @return
	 */
	public PlayerSkillVO loadPlayerSkill(Fighter player, int s_pk)
	{
		if (player == null)
		{
			return null;
		}
		if (s_pk == -1)
		{
			return null;
		}
		if (player.getSkill() != null)
		{
			return player.getSkill();
		}
		
		SkillService skillService = new SkillService();
		
		PlayerSkillVO playerSkill = null;
		
		playerSkill = skillService.getSkillInfo(player.getPPk(), s_pk);

		// �õ�������ϸ��Ϣ
		SkillDao skillDao = new SkillDao();
		skillDao.loadPlayerSkillDetailByInside(playerSkill);
		// �����װ�ؼ�����ϸ��Ϣ
		player.setSkill(playerSkill);
		return playerSkill;
	}


	
	/**
	 * ͨ������������������ԣ����ָ���Ч����
	 */
	private String loadPlayerPropertyByCache(PartInfoVO player,RoleEntity role_info)
	{
		GroupService groupService = new GroupService();
		BuffEffectService buffEffectService = new BuffEffectService();
		StringBuffer effect_describe = new StringBuffer();
		SkillService skillService = new SkillService();
		MyService myService = new MyServiceImpl();
		
		//����װ��Ч��
		role_info.getEquipOnBody().loadEquipProterty(player);
		
		//��������Ч��
		role_info.getMountSet().loadPropertys(player);
		
		// �������Ч��
		groupService.loadGroupEffect(player);
		
		// �������buffЧ��
		effect_describe.append(buffEffectService.loadBuffEffectOfPlayer(player));
		
		// ���ر�������Ч��
		skillService.loadPassiveSkillEffectByCache(player,role_info);
		
		// ���ط��������Ѫ����
		myService.addBloodMax(player);
		
		//���سƺ�Ч�� �Ժ���Ҫ���ص��������
		role_info.getTitleSet().loadProperty(player);
		return effect_describe.toString();
	}

	/**
	 * ͨ��id �õ������Ϣ
	 * @param p_pk
	 * @return
	 */
	public Fighter getFighterByPpk(int p_pk)
	{
		Fighter player = new Fighter();
		this.loadFighterByPpk(player, p_pk);
		return player;
	}
	
	/**
	 * ͨ��id ���������Ϣ
	 * @param p_pk
	 * @return
	 */
	public String loadFighterByPpk(PartInfoVO player, int p_pk)
	{
		logger.info("������һ�����Ϣ.............");
		RoleEntity role_info = RoleService.getRoleInfoById(p_pk+"");
		
		if( role_info!=null)
		loadFighterByCache(player,role_info);
		player.setContentdisplay(role_info.getBasicInfo().getMenpaiskilldisplay());
		return loadPlayerPropertyByCache(player,role_info);
	}
	
	/**
	 * ͨ���ڴ���������Ϣ
	 * @param p_pk
	 * @return
	 */
	private void loadFighterByCache(PartInfoVO player, RoleEntity role_info)
	{
		if (player == null)
		{
			player = new PartInfoVO();
		}
		BasicInfo basic_info = role_info.getBasicInfo();
		logger.info("���ڴ��м�����һ�����Ϣ.............");
		loadPartInfoByBasicInfo(player,basic_info);
		logger.info("���ڴ������Ҹ�����Ϣ.............");
	}

	/**
	 * ͨ��id�õ���ɫ��Ϣ
	 * @param p_pk
	 * @return
	 */
	public PartInfoVO getPlayerByPpk(int p_pk)
	{
		RoleEntity role_info = RoleService.getRoleInfoById(p_pk+"");
		return getPlayerByRoleInfo(role_info);
	}
	
	/**
	 * ͨ��id�õ���ɫ��Ϣ
	 * @param p_pk
	 * @return
	 */
	public PartInfoVO getPlayerByRoleInfo(RoleEntity roleInfo)
	{
		PartInfoVO player = getPlayerBasicInfo(roleInfo.getPPk());
		loadPlayerPropertyByCache(player,roleInfo);
		return player;
	}

	/**
	 * ͨ��id�õ���ɫ������Ϣ
	 * @param p_pk
	 * @return
	 */
	public PartInfoVO getPlayerBasicInfo(int p_pk)
	{
		RoleEntity role_info = RoleService.getRoleInfoById(p_pk+"");
		return getPlayerBasicInfoByCache(role_info);
	}
	
	
	/**
	 * ͨ���ڴ�õ���ҵĻ�����Ϣ
	 * @param role_info
	 * @return
	 */
	private PartInfoVO getPlayerBasicInfoByCache( RoleEntity role_info )
	{
		if( role_info==null )
		{
			return null;
		}
		BasicInfo basic_info = role_info.getBasicInfo();
		PartInfoVO part_info = new PartInfoVO();
		
		loadPartInfoByBasicInfo(part_info, basic_info);
		
		return part_info;
	}
	
	/**
	 * ͨ��BasicInfo����partInfo��Ϣ
	 */
	private void loadPartInfoByBasicInfo(PartInfoVO player,BasicInfo basic_info)
	{
		if( player==null )
		{
			player = new PartInfoVO();
		}
		if( basic_info==null )
		{
			return;
		}
		
		player.setUPk(basic_info.getUPk());
		player.setPPk(basic_info.getPPk());
		player.setPName(basic_info.getName());
		player.setPSex(basic_info.getSex());
		player.setPHarness(basic_info.getMarried());

		player.setPUpHp(basic_info.getUpHp());
		player.setPUpMp(basic_info.getUpMp());

		player.setPHp(basic_info.getHp());
		player.setPMp(basic_info.getMp());

		player.setPGj(basic_info.getBasicGj());
		player.setPFy(basic_info.getBasicFy());

		player.setPGrade(basic_info.getGrade());
		player.setPExperience(basic_info.getCurExp());
		player.setPBjExperience(basic_info.getPreGradeExp());
		player.setPXiaExperience(basic_info.getNextGradeExp());

		player.setPCopper(basic_info.getCopper()+"");
		
		player.setPPks(basic_info.getPkSwitch());
		player.setPPkValue(basic_info.getEvilValue());
		player.setPkChangeTime(basic_info.getPkChangeTime());

		player.setPMap(basic_info.getSceneId());

		player.setPWrapContent(basic_info.getWrapContent());

		player.setDropMultiple(basic_info.getMultipleDamage());
		player.setTe_level(basic_info.getTe_level());
		player.setChuangong(basic_info.getChuangong());
		player.setPRace(basic_info.getPRace());
		player.setPlayer_state_by_new(basic_info.getPlayer_state_by_new());
	}

	
	/**
	 * ���ݸ���Ч�����ͣ�������Ҷ�Ӧ������ֵ
	 * @param player
	 * @param append_attribute_type   ������������
	 * @param append_value ��������ֵ
	 * @param append_mode  ���ӷ�ʽ��1��ʾ���ӣ�2��ʾ����
	 */
	public void updateAttribteOfPlayer(PartInfoVO player,
			int append_attribute_type, int append_value, int append_mode)
	{
		if (player == null)
		{
			logger.info("��������");
			return;
		}

		if (append_mode == 2)
		{
			// debuff
			append_value = -append_value;
		}
		switch (append_attribute_type)
		{
			case BuffType.CHANGER_HP:
			{
				int change_value = append_value;
				player.setPHp(player.getPHp() + change_value);
				break;
			}
			case BuffType.CHANGER_MP:
			{
				int change_value = append_value;
				player.setPMp(player.getPMp() + change_value);
				break;
			}
			case BuffType.ATTACK:
			{
				int change_value = append_value;
				player.setPGj(player.getPGj() + change_value);
				break;
			}
			case BuffType.DEFENCE:
			{
				int change_value = append_value;
				player.setPFy(player.getPFy() + change_value);
				break;
			}
			case BuffType.HP_UP:
			{
				int change_value = append_value;
				player.setPUpHp(player.getPUpHp() + change_value);
				break;
			}
			case BuffType.MP_UP:
			{
				int change_value = append_value;
				player.setPUpMp(player.getPUpMp() + change_value);
				break;
			}
				// *********************���й�����buff
			case BuffType.JIN_ATTACK:
			{
				int change_value = append_value;
				player.getWx().setJinGj(
						player.getWx().getJinGj() + change_value);
				break;
			}
			case BuffType.MU_ATTACK:
			{
				int change_value = append_value;
				player.getWx().setMuGj(player.getWx().getMuGj() + change_value);
				break;
			}
			case BuffType.SHUI_ATTACK:
			{
				int change_value = append_value;
				player.getWx().setShuiGj(
						player.getWx().getShuiGj() + change_value);
				break;
			}
			case BuffType.HUO_ATTACK:
			{
				int change_value = append_value;
				player.getWx().setHuoGj(
						player.getWx().getHuoGj() + change_value);
				break;
			}
			case BuffType.TU_ATTACK:
			{
				int change_value = append_value;
				player.getWx().setTuGj(player.getWx().getTuGj() + change_value);
				break;
			}
				// ************************************���з�����buff
			case BuffType.JIN_DEFENCE:
			{
				int change_value = append_value;
				player.getWx().setJinFy(
						player.getWx().getJinFy() + change_value);
				break;
			}
			case BuffType.MU_DEFENCE:
			{
				int change_value = append_value;
				player.getWx().setMuFy(player.getWx().getMuFy() + change_value);
				break;
			}
			case BuffType.SHUI_DEFENCE:
			{
				int change_value = append_value;
				player.getWx().setShuiFy(
						player.getWx().getShuiFy() + change_value);
				break;
			}
			case BuffType.HUO_DEFENCE:
			{
				int change_value = append_value;
				player.getWx().setHuoFy(
						player.getWx().getHuoFy() + change_value);
				break;
			}
			case BuffType.TU_DEFENCE:
			{
				int change_value = append_value;
				player.getWx().setTuFy(player.getWx().getTuFy() + change_value);
				break;
			}

			case BuffType.ADD_CATCH_PROBABILITY:// ���Ӳ������
			{
				int change_value = (append_value);
				player.setAppendCatchProbability(player
						.getAppendCatchProbability()
						+ change_value);
				break;
			}
			case BuffType.ADD_DROP_PROBABILITY:// ���ӵ������
			{
				int change_value = (append_value);
				player.setAppendDropProbability(player
						.getAppendDropProbability()
						+ change_value);
				break;
			}
			case BuffType.ADD_EXP:// ����ӳ�
			{
				int change_value = (append_value);
				player.setAppendExp(player.getAppendExp() + change_value);
				break;
			}
			case BuffType.ADD_NONSUCH_PROBABILITY:// ���Ӽ�Ʒװ���������
			{
				int change_value = (append_value);
				player.setAppendNonsuchProbability(player
						.getAppendNonsuchProbability()
						+ change_value);
				break;
			}

			case BuffType.IMMUNITY_POISON:// **�Ƿ������ж�
			{
				if (append_value == 1)
				{
					player.setImmunityPoison(true);
				}
				break;
			}
			case BuffType.IMMUNITY__DIZZY:// **�Ƿ����߻���
			{
				if (append_value == 1)
				{
					player.setImmunityDizzy(true);
				}
				break;
			}
			case BuffType.CHANGER_BJ:// �����ʼӳ�
			{
				int change_value = append_value;
				player.setDropMultiple(player.getDropMultiple() + change_value);
				break;
			}
		}
	}

	/**
	 * ������ߴ���
	 * 
	 * @param p_pk
	 */
	/*
	 * public void outLine(RoleEntity roleEntity) { GroupService groupService =
	 * new GroupService(); GroupNotifyService groupNotifyService = new
	 * GroupNotifyService(); FieldService fieldService = new FieldService();
	 * NpcService npcService = new NpcService(); int pPk =
	 * roleEntity.getBasicInfo().getPPk();
	 * 
	 * 
	 * npcService.clearJobs(pPk);//���������ʱ������
	 * groupService.leaveGroup(roleEntity); // �����ɢ
	 * groupNotifyService.clareNotify(pPk);// ������֪ͨ
	 * fieldService.leaveField(pPk,roleEntity.getBasicInfo().getUPk()+"");
	 * //��������ս�����˳� }
	 */
	/**
	 * ����ɫ״̬
	 * 
	 * @param p_pk
	 *            ���������id
	 * @param action
	 *            ��ǰ��Ҫִ�е���Ϊ
	 * @return
	 */
	public String checkRoleState(int p_pk, int action)
	{
		PlayerState playerState = new PlayerState();

		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");

		String hint = null;
		if (roleInfo == null || roleInfo.isOnline()==false )
		{
			return hint = "��Ҳ�����";
		}
		int cur_state = roleInfo.getStateInfo().getCurState();// ��ҵ�ǰ״̬

		// ����ִ�е��ǽ���״̬ ����A������B
		if (action == PlayerState.TRADE)
		{
			// ��ȡB�ĵ�ǰ״̬
			if (cur_state != PlayerState.GENERAL)
			{// ֻ����ƽ��״̬�½��н���
				hint = "�Է�����" + playerState.returnStateName(cur_state)
						+ ",���Ժ��ٽ���" + playerState.returnStateName(action);
				return hint;
			}
		}
		// ����ִ�����״̬ ����A����B
		if (action == PlayerState.GROUP)
		{
			// ��ȡB�ĵ�ǰ״̬
			if (cur_state != PlayerState.GENERAL)
			{// ֻ����ƽ��״̬�½������
				hint = "�Է�����" + playerState.returnStateName(cur_state)
						+ ",���Ժ��ٽ���" + playerState.returnStateName(action);
				return hint;
			}
		}
		// ����ִ��PK״̬ ����A����B
		if (action == PlayerState.PKFIGHT)
		{
			// ��ȡB�ĵ�ǰ״̬ //������̳�����ڽ��ף�������ӣ���������,���ڼ�ȡ��Ʒ������״̬�²ſ��Խ�������PK
			if (cur_state == PlayerState.TRADE
					|| cur_state == PlayerState.GROUP
					|| cur_state == PlayerState.OUTLINE
					|| cur_state == PlayerState.PKFIGHT
					|| cur_state == PlayerState.FORUM
					|| cur_state == PlayerState.EXTRA)
			{
				hint = "�Է�����" + playerState.returnStateName(cur_state)
						+ ",���Ժ��ٽ���" + playerState.returnStateName(action);
				return hint;
			}
		}

		// ����ִ��PK״̬ ����A����B
		if (action == PlayerState.PKFIGHT)
		{
			return "�Է�����ս��,���Ժ��ٽ���" + playerState.returnStateName(action);
		}

		// ����˽��״̬ ����A����B
		if (action == PlayerState.TALK)
		{
			// ��ȡB�ĵ�ǰ״̬ //���ڽ��ף�������ӣ��������ߵ�״̬�²ſ��Խ�������PK
			if (cur_state != PlayerState.GENERAL)
			{
				// hint =
				// "�Է�����"+playerState.returnStateName(cur_state)+",���Ժ��ٽ���"+playerState.returnStateName(nonceState);
				hint = "�Է�����" + playerState.returnStateName(cur_state);
				return hint;
			}
		}
		
		String scene_id = roleInfo.getBasicInfo().getSceneId();
		SceneVO sceneVO = SceneCache.getById(scene_id);
		MapVO mapVO = sceneVO.getMap();
		if(mapVO.getMapType() == MapType.TIANGUAN){
			hint = "��������ս���,�����������!";
			return hint;
		}

		return hint;
	}

	/**
	 * ����ɫ״̬
	 * 
	 * @param p_pk
	 *            ���������id
	 * @param action
	 *            ��ǰ��Ҫִ�е���Ϊ
	 * @return
	 */
	public String isRoleState(int p_pk, int type)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");
		String hint = null;
		if (roleInfo == null)
		{
			if (type == 1)
			{// ����
				return hint = "���׳�ʱ�����ױ�ȡ����";
			}
			if (type == 2)
			{// �������
				return hint = "�Է������ߡ�";
			}
		}
		else
		{// ��Ҫ�������Է����ĳЩ��������״̬
			if (type == 1)
			{// ����
				if (roleInfo.getSettingInfo().getDealControl() == -1)
				{
					return hint = "�Է���ҵĽ��׿����ǹرյģ�";
				}
			}
			SceneVO sceneVO = roleInfo.getBasicInfo().getSceneInfo();
			MapVO mapVO = sceneVO.getMap();
			if(mapVO.getMapType() == MapType.TIANGUAN){
				hint = "��������ս���,�����������!";
				return hint;
			}
		}
		return hint;
	}

	/**
	 * �����ʱ������
	 * 
	 * @param p_pk
	 * @param attack_state
	 *            all:��ʾ���������ʱ��npc;zd:��ʾֻ�������npc
	 */
	public void clearTempData(int p_pk, String attack_state)
	{
		RoleEntity role_info = RoleService.getRoleInfoById(p_pk+"");
		// ���֮ǰ�ľ����Ǯ
		role_info.getDropSet().clearExpAndMoney();
		// ���֮ǰ�ĵ�����
		role_info.getDropSet().clearDropItem();

		AttacckCache attacckCache = new AttacckCache();

		List<NpcFighter> zd_npcs = attacckCache.getZDAttackNpc(p_pk,
				AttacckCache.ZDATTACKNPC);
		if (zd_npcs != null)
		{
			// logger.debug("���" + zd_npcs.size() + "������NPC");
			zd_npcs.clear();// �������npc
		}

		if (attack_state.equals("all"))
		{
			List<NpcFighter> bd_npcs = attacckCache.getZDAttackNpc(p_pk,
					AttacckCache.BDATTACKNPC);
			if (bd_npcs != null)
			{
				// logger.debug("���" + bd_npcs.size() + "������NPC");
				bd_npcs.clear();// �������npc
			}
		}
	}

	/**
	 * ������ҳ�������Ұ
	 */
	public void updateSceneAndView(int p_pk, int new_scene_id)
	{
		ViewCache viewCahce = new ViewCache();

		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");

		if (roleInfo != null)
		{
			SceneVO new_scene = SceneCache.getById(new_scene_id+"");
			String hint = new_scene.isEntered(roleInfo);
			//�ж��Ƿ���Խ��������
			if( hint!=null)
			{
				return;
			}
			
			roleInfo.getBasicInfo().updateSceneId(new_scene_id + "");// �������scene_id

			String old_view = roleInfo.getStateInfo().getView();
			String new_view = null;

			new_view = getCurrentView(roleInfo.getBasicInfo().getPPk(),new_scene_id);

			viewCahce.remove(old_view, roleInfo);// �Ƴ����֮ǰ����Ұ
			roleInfo.getStateInfo().setView(new_view);// ���������Ұ
			viewCahce.put(new_view, roleInfo);// ���������Ұ
		}
	}


	/**
	 * �õ���Ұ�ڵ�����б��ַ��� ����15���֣���ֻ��ʾ15�ּӡ���������
	 */
	public String getPlayerListStrByScene(RoleEntity roleInfo)
	{
		String result = null;

		ViewCache viewCache = new ViewCache();

		StringBuffer player_list_str = new StringBuffer();

		String cur_view = null;

		cur_view = roleInfo.getStateInfo().getView();

		LinkedHashSet<RoleEntity> role_list = viewCache
				.getRolesByView(cur_view);
		PlayerService ps=new PlayerService();
		if (role_list.size() > 1)
		{
			Iterator list = role_list.iterator();
			RoleEntity otherRoleInfo = null;
			int i = 1;
			while (list.hasNext())
			{
				otherRoleInfo = (RoleEntity) list.next();
				PartInfoVO partInfo=ps.getPlayerByRoleInfo(otherRoleInfo);
				/***************����ʾ�Լ�*******/
				if (otherRoleInfo.equals(roleInfo))
				{
					continue;
				}
				/*******�жϲ�������Ҳ���ʾ******/
				if (i == 1)
				{
					player_list_str.append(otherRoleInfo.getBasicInfo().getName());
				}
				else
				{
					player_list_str.append(",").append(otherRoleInfo.getBasicInfo().getName());
				}
				i++;
			}
			if (player_list_str.length() >= 15)
			{
				result = player_list_str.toString().substring(0, 15) + "....";
			}
			else
			{
				result = player_list_str.toString();
			}
		}

		return result;
	}

	/**
	 * �õ���ǰ��Ұ
	 * 
	 * @param scene_id
	 * @return
	 */
	public String getCurrentView(int p_pk, int scene_id)
	{
		int map_type = -1;
		String view = null;

		RoomService roomService = new RoomService();
		GroupService groupService = new GroupService();

		map_type = roomService.getMapType(scene_id);
		SceneVO scene = roomService.getById(scene_id + "");

		view = scene.getSceneKen();

		if (map_type == MapType.INSTANCE)
		{
			int caption_pk = groupService.getCaptionPk(p_pk);
			view = view + "_group" + caption_pk;
		}
		if (map_type == MapType.TIANGUAN)
		{
			view = view + "_" + p_pk;
		}
		else
		{
		}
		return view;
	}

	/**
	 * �õ��������ͬһ��Ұ�����
	 * 
	 * @param p_pk
	 * @param map_id
	 * @return
	 */
	public List<RoleEntity> getPlayersByView(RoleEntity roleInfo)
	{
		ViewCache viewCache = new ViewCache();

		List<RoleEntity> result = null;

		String cur_view = null;

		cur_view = roleInfo.getStateInfo().getView();

		LinkedHashSet<RoleEntity> role_list = viewCache
				.getRolesByView(cur_view);

		LinkedHashSet<RoleEntity> new_role_list = (LinkedHashSet<RoleEntity>) role_list.clone();

		new_role_list.remove(roleInfo);

		result = new ArrayList(new_role_list);

		return result;
	}

	/**
	 * ����ҵĲ�ͬ״̬�����Ӳ�ͬ�ı��
	 * @param players
	 */
	public void addUserStateFlag(List<RoleEntity> players)
	{
		RoleEntity roleEntity = null;
		for ( int i=0;i<players.size();i++) {
			roleEntity = players.get(i);
			if(roleEntity.getStateInfo().getGroupInfo()!=null)
			{
				roleEntity.getBasicInfo().setDisplayName(roleEntity.getBasicInfo().getName()+"*");
			} else 	if ( roleEntity.getStateInfo().getCurState() == PlayerState.PKFIGHT) {
				roleEntity.getBasicInfo().setDisplayName("��"+roleEntity.getBasicInfo().getName());
			} else {
				roleEntity.getBasicInfo().setDisplayName(roleEntity.getBasicInfo().getName());				
			}
		}
	}
	
}
