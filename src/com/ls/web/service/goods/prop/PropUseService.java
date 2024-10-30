package com.ls.web.service.goods.prop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ben.dao.TimeShow;
import com.ben.dao.friend.FriendDAO;
import com.ben.dao.petinfo.PetInfoDAO;
import com.ben.lost.Compass;
import com.ben.lost.CompassService;
import com.ben.vo.friend.FriendVO;
import com.ben.vo.honour.TitleVO;
import com.ben.vo.petinfo.PetInfoVO;
import com.ls.ben.cache.staticcache.honour.TitleCache;
import com.ls.ben.cache.staticcache.npc.NpcCache;
import com.ls.ben.cache.staticcache.pet.PetSkillCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.CoordinateDao;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.attack.DropExpMoneyVO;
import com.ls.ben.vo.info.attack.DropGoodsVO;
import com.ls.ben.vo.info.effect.PropUseEffect;
import com.ls.ben.vo.info.npc.NpcAttackVO;
import com.ls.ben.vo.info.npc.NpcVO;
import com.ls.ben.vo.info.partinfo.CoordinateVO;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.ben.vo.info.quiz.QuizVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.group.GroupModel;
import com.ls.model.log.GameLogManager;
import com.ls.model.property.RolePetInfo;
import com.ls.model.property.RoleTitleSet;
import com.ls.model.user.RoleEntity;
import com.ls.model.vip.Vip;
import com.ls.model.vip.VipManager;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.MapType;
import com.ls.pub.constant.PropType;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.constant.buff.BuffSystem;
import com.ls.pub.constant.player.PlayerState;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.pub.util.MoneyUtil;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.Quiz.QuizService;
import com.ls.web.service.buff.BuffEffectService;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.log.LogService;
import com.ls.web.service.map.MapService;
import com.ls.web.service.menu.buff.BuffMenuService;
import com.ls.web.service.npc.NpcService;
import com.ls.web.service.npc.RefurbishService;
import com.ls.web.service.pet.PetService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.GrowService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.player.ShortcutService;
import com.ls.web.service.room.RoomService;
import com.ls.web.service.skill.SkillService;
import com.ls.web.service.system.UMsgService;
import com.ls.web.service.task.TaskSubService;
import com.lw.dao.pet.skill.PetSkillLevelUpDao;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.lw.service.pet.PetEggService;
import com.lw.service.skill.PetSkillLevelUpService;
import com.lw.service.skill.SkillUpService;
import com.lw.service.synthesize.PlayerSynthesizeService;
import com.pm.constant.SpecialNumber;
import com.pm.dao.miji.MiJiDao;
import com.pm.service.systemInfo.SystemInfoService;
import com.pm.vo.miji.MiJiVO;
import com.pub.ben.info.Expression;
import com.web.jieyi.util.Constant;
import com.web.service.avoidpkprop.AvoidPkPropService;
import com.web.service.friend.FriendService;
import com.web.service.petservice.HhjPetService;

/**
 * ����:������ҵ��ߵĲ���
 * 
 * @author ��˧ 3:54:58 PM
 */
public class PropUseService
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * ʹ�õ��߲�ָ��ʹ���ĸ�������
	 * 
	 * @param prop_id
	 *            ����id
	 * @param object
	 *            �������ö���
	 * @param PropUseEffect
	 *            ���ص���ʹ�õ�Ч��
	 */
	public PropUseEffect usePropByPropID(RoleEntity role_info, int prop_id,
			int use_num,HttpServletRequest request, HttpServletResponse response)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();

		PropVO prop = PropCache.getPropById(prop_id); // �õ�������ϸ��Ϣ
		// �ҵ��������ٵĵ�����
		PlayerPropGroupVO propGroup = propGroupDao.getPropGroupByPropID(role_info.getBasicInfo().getPPk(), prop_id);
		if (propGroup == null)// �޵���
		{
			ShortcutService ShortcutService = new ShortcutService();
			ShortcutService.clearShortcutoperate_id(role_info.getBasicInfo()
					.getPPk(), prop_id);
			PropUseEffect propUseEffect = new PropUseEffect();
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("������������");
			return propUseEffect;
		}
		return useProp(role_info, prop, propGroup, use_num);
	}

	/**
	 * ʹ��pg_id��ĵ���
	 * 
	 * @param p_pk
	 * @param prop_id
	 * @param pg_pk
	 *            Ϊ-1ʱ����ʾ��ָ��ʹ��ʹ���ĸ�������ĵ��ߣ�
	 * @return
	 */
	public PropUseEffect usePropByPropGroupID(RoleEntity role_info, int pg_pk,
			int use_num)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();

		PlayerPropGroupVO propGroup = propGroupDao.getByPgPk(pg_pk);

		return useProp(role_info, propGroup.getPropInfo(), propGroup, use_num);
	}

	/**
	 * ʹ��pg_id��ĵ���
	 * 
	 * @param p_pk
	 * @param prop_id
	 * @param pg_pk
	 *            Ϊ-1ʱ����ʾ��ָ��ʹ��ʹ���ĸ�������ĵ��ߣ�
	 * @return
	 */
//	public String full_timeprop(int p_pk, int propID)
//	{
//		GrowService growService = new GrowService();
//		PlayerService playerService = new PlayerService();
//		PartInfoVO player = playerService.getPlayerByPpk(p_pk);
//
//		String resutl = null;
//
//		RoleService roleService = new RoleService();
//		RoleEntity role_info = roleService.getRoleInfoById(p_pk + "");
//
//		PropVO prop = PropCache.getPropById(propID); // �õ�������ϸ��Ϣ
//		PropUseEffect propUseEffect = new PropUseEffect();
//
//		// �õ����߹��ܿ����ֶ�
//		String title_id = prop.getPropOperate1();
//
//		TitleInfoDao titleInfoDao = new TitleInfoDao();
//		TitleInfoVO titleInfo = titleInfoDao.getByTilteId(title_id);
//
//		if (player.getPGrade() >= titleInfo.getTitleLevelDown())// �ж��Ƿ�ת��ְ
//		{
//			resutl = "����ת��ְ,������ʹ���ˡ�";
//			propUseEffect.setIsEffected(false);
//			propUseEffect.setNoUseDisplay(resutl);
//			return resutl;
//		}
//		// *************����ʹ��Ч��*********************//
//		// �ڳ�νת��ʱ��ѯϵͳ��Ϣ���Ʊ����ͺ��ʵ���Ϣ
//		SystemInfoService systemInfoService = new SystemInfoService();
//		systemInfoService.sendSystemInfoByTitleInfo(p_pk, title_id);
//		role_info.getBasicInfo().updateSchool(titleInfo.getSchoolId());// ��ʦ
//		growService.upgrade(player, role_info);
//		resutl = "����������" + player.getPGrade() + "��";
//		return resutl;
//	}

	/**
	 * ʹ��pg_id��ĵ��߳������ʹ��
	 * 
	 * @param p_pk
	 * @param prop_id
	 * @param pg_pk
	 *            Ϊ-1ʱ����ʾ��ָ��ʹ��ʹ���ĸ�������ĵ��ߣ�
	 * @return
	 */
	public PropUseEffect usePropByPropGroupIDpet(int p_pk, int pg_pk,
			int use_num, String pet_pk)
	{
		RoleEntity role_info = RoleService.getRoleInfoById(p_pk + "");

		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();

		PlayerPropGroupVO propGroup = propGroupDao.getByPgPk(pg_pk);

		return useProppet(role_info, propGroup.getPropInfo(), propGroup, use_num, pet_pk);
	}

	/**
	 * ʹ�õ���
	 * 
	 * @param p_pk
	 * @param prop_id
	 * @param pg_pk
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useProp(RoleEntity role_info, PropVO prop,
			PlayerPropGroupVO propGroup, int use_num)
	{
		logger.info("role_info=" + role_info + " ,prop=" + prop
				+ " ,propGourp=" + propGroup);
		if (role_info == null || prop == null || propGroup == null)
		{
			logger.info("��������");
			return null;
		}

		PropUseEffect propUseEffect = null;// ʹ��Ч��

		// û��ָ������
		if (use_num <= 0)
		{
			use_num = 1;
		}

		logger.info("propClass=" + prop.getPropClass());
		switch (prop.getPropClass())
		{
			case PropType.ADDHP:// ��Ѫ����
				propUseEffect = useADDHP(role_info, propGroup, prop, use_num);
				break;
			case PropType.ADDMP:// ��������
				propUseEffect = useADDMP(role_info, propGroup, prop, use_num);
				break;
			case PropType.CRUEALLHMP:// ��Ѫ����
				propUseEffect = useADDHMP(role_info, propGroup, prop, use_num);
				break;
			case PropType.ZHUANZHI:// תְ����
				propUseEffect = useZHUANZHI(role_info, propGroup, prop, use_num);
				break;
			case PropType.GOBACKCITY:// �سǵ���ʹ��
				propUseEffect = useGOBACKCITY(role_info, propGroup, prop,
						use_num);
				break;
			case PropType.Carry:// �سǵ���ʹ��
				propUseEffect = useCarry(role_info, propGroup, prop, use_num);
				break;
			case PropType.QUIZ:// �������
				propUseEffect = useQUIZ(role_info, propGroup, prop, use_num);
				break;
			case PropType.CONJURE:// �ٻ�����
				propUseEffect = useCONJURE(role_info, propGroup, prop, use_num);
				break;
			case PropType.MARKUP:// ��ǵ���
				propUseEffect = useMARKUP(role_info, propGroup, prop, use_num);
				break;
			case PropType.AVOIDPKPROP:// ��PK����
				propUseEffect = useAVOIDPKPROP(role_info, propGroup, prop,
						use_num);
				break;
			/*case PropType.ROLE_BEOFF_EXP:// ������ֱ�Ӹ���Ҽҵ���ʱ��
				propUseEffect = useRoleBeoffExp(role_info, propGroup, prop,
						use_num);
				break;*/
			// case PropType.PETSINEW:// ����ظ�����
			// propUseEffect = petSINEW(player, propGroup, prop, use_num);
			// break;
			// case PropType.PETLONGE:// ����ظ�����
			// propUseEffect = petLONGE(player, propGroup, prop, use_num);
			// break;

			case PropType.SKILLBOOK:// ѧϰ����
				propUseEffect = useSKILLBOOK(role_info, propGroup, prop,
						use_num);
				break;
			case PropType.HONOUR:// �ƺ�
				propUseEffect = useHONOUR(role_info, propGroup, prop, use_num);
				break;
			case PropType.VIP:// ��ΪVIP�ĵ���
				propUseEffect = useVIP(role_info, propGroup, prop, use_num);
				break;
			case PropType.BUFF:// ʹ�õ���buff
				propUseEffect = useBUFF(role_info, propGroup, prop, use_num);
				break;
			case PropType.INIT_PET:// ϴ�������
				propUseEffect = useINITPET(role_info, propGroup, prop, use_num);
				break;
			case PropType.PET_EGG:// ���ﵰ����
				propUseEffect = usePETEGG(role_info, propGroup, prop, use_num);
				break;
			case PropType.ACCEPT_SPECIFY_TASK:// ����ָ���������
				propUseEffect = acceptSpesifyTask(role_info, propGroup, prop,
						use_num);
				break;
			case PropType.ACCEPT_TASK_FROM_LIST:// �������б��������
				propUseEffect = acceptTaskFromList(role_info, propGroup, prop,
						use_num);
				break;
			case PropType.RARE_BOX:// �������
				propUseEffect = breakRareBox(role_info, propGroup, prop,
						use_num);
				break;
			case PropType.NORMAL:// ��ͨ���߲���ʹ��
				propUseEffect = useNORMAL(role_info, propGroup, prop, use_num);
				break;
			case PropType.BOX_CURE: // ��װҩƷ
				propUseEffect = useBoxCure(role_info, propGroup, prop, use_num);
				break;
			case PropType.GEI_RARE_BOX: // ��������
				propUseEffect = useGeiBoxCure(role_info, propGroup, prop,
						use_num);

				break;
			case PropType.OUTPUBLISH: // ĳʱ���������޳ͷ�����
				propUseEffect = useDeadOutOfJinYang(role_info, propGroup, prop,
						use_num);

				break;
			case PropType.LIVESKILLBOOK: // ��������ʹ��
				propUseEffect = useLiveSkillBook(role_info, propGroup, prop,
						use_num);

				break;
			case PropType.SYNTHESIZEBOOK: // �䷽���ʹ��
				propUseEffect = useSynthesizeBook(role_info, propGroup, prop,
						use_num);

				break;
			case PropType.REDUCEPKVALUE: // �����ֵ����
				propUseEffect = useReducePkValue(role_info, propGroup, prop,
						use_num);

				break;

			case PropType.GET_YUANBAO_BOX: // ����Ԫ���ı���
				propUseEffect = useYuanbaoBox(role_info, propGroup, prop,
						use_num);

				break;
			case PropType.PET_EGG_GUDING: // д�����ﵰ
				propUseEffect = usePetEggGuding(role_info, propGroup, prop,
						use_num);

				break;
			case PropType.BOOK: // �鼮�����
				propUseEffect = useBook(role_info, propGroup, prop, use_num);

				break;
			case PropType.XINYINDU: // ��ӡ������
				propUseEffect = useXInyinfu(role_info, propGroup, prop, use_num);

				break;
			case PropType.BROTHERFU: // �ֵ������
				propUseEffect = useBrotherfu(role_info, propGroup, prop,
						use_num);

				break;
			case PropType.MERRYFU: // ���������
				propUseEffect = useMerryFu(role_info, propGroup, prop, use_num);

				break;
			case PropType.ADD_LOVE_DEAR:// ���ӷ�������ֵ����
				propUseEffect = useAddLoveDear(role_info, propGroup, prop,
						use_num);

				break;
			case PropType.ADD_DEAR:// �������ܶȵ���
				propUseEffect = useAddDear(role_info, propGroup, prop, use_num);

				break;
			case PropType.GOLD_BOX:// �ƽ������
				String noUseInfo = "����ʹ�ý�Կ�����򿪱���!";
				propUseEffect = useDefaultProp(role_info, propGroup, prop,
						use_num, noUseInfo);

				break;
			case PropType.ARMBOX:// ��������
				propUseEffect = useARMBOX(role_info, propGroup, prop, use_num);
				break;
			/*case PropType.YINSHEN:// �������
				propUseEffect = useYinshen(role_info, propGroup, prop, use_num);
				break;*/
			/*case PropType.FAN_YINSHEN:// �����������
				propUseEffect = useFanYinshen(role_info, propGroup, prop,
						use_num);
				break;*/
			/*case PropType.QIANLIYAN:// ǧ����
				propUseEffect = useQianliyan(role_info, propGroup, prop,
						use_num);
				break;*/
			/*case PropType.XIANHAI:// �ݺ���
				propUseEffect = useXianhai(role_info, propGroup, prop, use_num);
				break;*/
			case PropType.COMPASS:// ָ����
				propUseEffect = useCompass(role_info, propGroup, prop, use_num);
				break;
			case PropType.MIJING_MAP:// �ؾ���ͼ
				propUseEffect = useMijing(role_info, propGroup, prop, use_num);
				break;
			case PropType.TIAOZHAN://��ս��
				propUseEffect = useTIAOZHAN(role_info, propGroup, prop, use_num);
				break;
			default:
				propUseEffect = useDefaultProp(role_info, propGroup, prop,
						use_num, null);
				break;

		}

		// ��ÿ��ʹ�ô�������
		if (propUseEffect != null && propUseEffect.getIsEffected()
				&& prop.getPropUsedegree() > 0)
		{
			TimeControlService timeControlService = new TimeControlService();
			// ���µ���ʹ��״̬
			timeControlService.updateControlInfo(role_info.getBasicInfo()
					.getPPk(), prop.getPropID(), TimeControlService.PROP);
		}

		return propUseEffect;
	}

	/**
	 * ʹ����ս��
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useTIAOZHAN(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		int mapType = role_info.getBasicInfo().getSceneInfo().getMap().getMapType();
		if(mapType!= MapType.SAFE && mapType!= MapType.DANGER){
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("�õ����޷��ڴ˵�ͼʹ��");
			return propUseEffect;
		}
		int curState = role_info.getStateInfo().getCurState();
		if (!(curState == PlayerState.GENERAL
				|| curState == PlayerState.TRADE
				|| curState == PlayerState.GROUP || curState == PlayerState.TALK))
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("��Ŀǰ�޷�ʹ�øõ���");
			return propUseEffect;
		}
		propUseEffect.setPropType(prop.getPropClass());
		propUseEffect.setIsEffected(true);
		return propUseEffect;
	}
	
	/**
	 * ʹ���ؾ���ͼ
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useMijing(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		SceneVO scene = role_info.getBasicInfo().getSceneInfo();
		if (scene.getMap().getMapType() != MapType.SAFE)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("�Բ��𣬸õ��߲���ʹ��");
			return propUseEffect;
		}
		int state = role_info.getStateInfo().getCurState();
		if (state != PlayerState.GENERAL && state != PlayerState.GROUP
				&& state != PlayerState.VIEWWRAP && state != PlayerState.TALK)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("�Բ��𣬸õ��߲���ʹ��");
			return propUseEffect;
		}
		// �Ƴ�ʹ�õ���
		if (prop.getPropOperate1() == null
				|| prop.getPropOperate1().trim().equals(""))
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("�Բ������ݴ�����֪ͨGM");
			return propUseEffect;
		}
		new PlayerService().updateSceneAndView(role_info.getBasicInfo()
				.getPPk(), Integer.parseInt(prop.getPropOperate1().trim()));
		GroupModel gm = role_info.getStateInfo().getGroupInfo();
		if (gm != null)
		{
			List<RoleEntity> list = gm.getMemberList();
			if (list != null)
			{
				UMsgService uMsgService = new UMsgService();
				for (RoleEntity re : list)
				{
					int state1 = re.getStateInfo().getCurState();
					SceneVO scene1 = re.getBasicInfo().getSceneInfo();
					if ((state1 == PlayerState.GENERAL
							|| state1 == PlayerState.GROUP
							|| state1 == PlayerState.VIEWWRAP || state == PlayerState.TALK)
							&& (scene1.getMap().getMapType() != MapType.SAFE))
					{
						UMessageInfoVO uif = new UMessageInfoVO();
						uif.setCreateTime(new Date());
						uif.setMsgPriority(PopUpMsgType.MIJING_MAP_FIRST);
						uif.setMsgType(PopUpMsgType.MIJING_MAP);
						uif.setPPk(re.getBasicInfo().getPPk());
						uif.setResult("���Ķ���Ҫ�������͵��ؾ������Ƿ�������ͣ�");
						uif.setMsgOperate2(prop.getPropOperate1().trim());
						uMsgService.sendPopUpMsg(uif);
					}
				}
			}
		}

		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);
		propUseEffect.setPropType(prop.getPropClass());
		propUseEffect.setIsEffected(true);
		return propUseEffect;
	}

	/**
	 * ʹ��ָ����
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useCompass(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		SceneVO scene = role_info.getBasicInfo().getSceneInfo();
		if (scene.getMap().getMapType() != MapType.COMPASS)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("���޷����Թ���ʹ�ø���Ʒ��");
			return propUseEffect;
		}
		Compass com = new CompassService().findById(scene.getSceneID());
		if (com == null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("�Բ������ݴ�����֪ͨGM");
			return propUseEffect;
		}
		// �Ƴ�ʹ�õ���
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);
		propUseEffect.setPropType(prop.getPropClass());
		propUseEffect.setIsEffected(true);
		propUseEffect.setNoUseDisplay("����ָ�����ָʾ�������������н�·���ǣ�" + com.getDes()
				+ "�ƶ���");
		return propUseEffect;
	}

	/**
	 * ʹ�������
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
//	private PropUseEffect useXianhai(RoleEntity role_info,
//			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
//	{
//		PropUseEffect propUseEffect = new PropUseEffect();
//
//		// ************�����Ƿ����ʹ���ж� **************//
//		// ����ʹ�û����ж�
//		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
//		if (resutl != null)
//		{
//			propUseEffect.setIsEffected(false);
//			propUseEffect.setNoUseDisplay(resutl);
//			return propUseEffect;
//		}
//		// ************�����Ƿ����ʹ���жϽ���**************//
//		if (role_info.getBasicInfo().getPPk() != LangjunConstants.LANGJUN_PPK)
//		{
//			propUseEffect.setIsEffected(false);
//			propUseEffect.setNoUseDisplay("�Բ���������ǧ���ɾ�����˲���ʹ�øõ���");
//			return propUseEffect;
//		}
////		GoodsService goodsService = new GoodsService();
////		goodsService.removeProps(propGroup, use_num);
//		// *************����ʹ��Ч��*********************//
//		// GoodsService goodsService = new GoodsService();
//		// goodsService.removeProps(propGroup, use_num);
//		// �Ƴ�ʹ�õ���
//		propUseEffect.setPropType(prop.getPropClass());
//		propUseEffect.setIsEffected(true);
//		propUseEffect.setNoUseDisplay("��ʹ����" + prop.getPropName()
//				+ "���ڽ�������20����������ҽ��޷������������");
//		return propUseEffect;
//	}

//	/**
//	 * ʹ�������
//	 * 
//	 * @param player
//	 * @param propGroup
//	 * @param prop
//	 * @param use_num
//	 * @return
//	 */
//	private PropUseEffect useYinshen(RoleEntity role_info,
//			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
//	{
//		PropUseEffect propUseEffect = new PropUseEffect();
//
//		// ************�����Ƿ����ʹ���ж� **************//
//		// ����ʹ�û����ж�
//		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
//		if (resutl != null)
//		{
//			propUseEffect.setIsEffected(false);
//			propUseEffect.setNoUseDisplay(resutl);
//			return propUseEffect;
//		}
//		// ************�����Ƿ����ʹ���жϽ���**************//
//		if (role_info.getBasicInfo().getPPk() != LangjunConstants.LANGJUN_PPK)
//		{
//			propUseEffect.setIsEffected(false);
//			propUseEffect.setNoUseDisplay("�Բ���������ǧ���ɾ�����˲���ʹ�øõ���");
//			return propUseEffect;
//		}
//		// *************����ʹ��Ч��*********************//
//		GoodsService goodsService = new GoodsService();
//		goodsService.removeProps(propGroup, use_num);
//		LangjunConstants.LANGJUN_YINSHEN = DateUtil.getDate(new Date());
//		// �Ƴ�ʹ�õ���
//		propUseEffect.setPropType(prop.getPropClass());
//		propUseEffect.setIsEffected(true);
//		propUseEffect.setNoUseDisplay("��ʹ����" + prop.getPropName() + "���ڽ�������"
//				+ LangjunConstants.YINSHEN_CANNOTVIEW_SECOND
//				+ "����������ҽ��޷������������");
//		new SystemInfoService()
//				.insertSystemInfoBySystem("��ǧ���ɾ���ʹ���ˡ������������ʱ��ʧ�ںڰ�֮�У���������Ӱ��");
//		return propUseEffect;
//	}

//	/**
//	 * ʹ�÷������
//	 * 
//	 * @param player
//	 * @param propGroup
//	 * @param prop
//	 * @param use_num
//	 * @return
//	 */
//	private PropUseEffect useFanYinshen(RoleEntity role_info,
//			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
//	{
//		PropUseEffect propUseEffect = new PropUseEffect();
//
//		// ************�����Ƿ����ʹ���ж� **************//
//		// ����ʹ�û����ж�
//		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
//		if (resutl != null)
//		{
//			propUseEffect.setIsEffected(false);
//			propUseEffect.setNoUseDisplay(resutl);
//			return propUseEffect;
//		}
//		String second = prop.getPropOperate1();
//		if (second == null || "".equals(second.trim()))
//		{
//			propUseEffect.setIsEffected(false);
//			propUseEffect.setNoUseDisplay("���ݴ�������ϵGM");
//			return propUseEffect;
//		}
//		if (LangjunConstants.LANGJUN_PPK == 0)
//		{
//			propUseEffect.setIsEffected(false);
//			propUseEffect.setNoUseDisplay("��ǰδ����ǧ���ɾ�,�õ����޷�ʹ��");
//			return propUseEffect;
//		}
//		int second1 = 0;
//		try
//		{
//			second1 = Integer.parseInt(second.trim());
//		}
//		catch (Exception e)
//		{
//			propUseEffect.setIsEffected(false);
//			propUseEffect.setNoUseDisplay("���ݴ�������ϵGM");
//			return propUseEffect;
//		}
//		GoodsService goodsService = new GoodsService();
//		goodsService.removeProps(propGroup, use_num);
//		// ************�����Ƿ����ʹ���жϽ���**************//
//		// *************����ʹ��Ч��*********************//
//		LangjunConstants.FANYINSHEN.put(role_info.getBasicInfo().getPPk(),
//				DateUtil.addSecond(second1));
//		// �Ƴ�ʹ�õ���
//		propUseEffect.setPropType(prop.getPropClass());
//		propUseEffect.setIsEffected(true);
//		propUseEffect.setNoUseDisplay("�����ڿ����ڵ�ͼ���ҵ���������״̬�ġ�ǧ���ɾ����ˣ���Ч������"
//				+ second1 + "�롣");
//		return propUseEffect;
//	}

//	/**
//	 * ʹ��ǧ����
//	 */
//	private PropUseEffect useQianliyan(RoleEntity role_info,
//			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
//	{
//		PropUseEffect propUseEffect = new PropUseEffect();
//
//		// ************�����Ƿ����ʹ���ж� **************//
//		// ����ʹ�û����ж�
//		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
//		if (resutl != null)
//		{
//			propUseEffect.setIsEffected(false);
//			propUseEffect.setNoUseDisplay(resutl);
//			return propUseEffect;
//		}
//		String second = prop.getPropOperate1();
//		if (second == null || "".equals(second.trim()))
//		{
//			propUseEffect.setIsEffected(false);
//			propUseEffect.setNoUseDisplay("���ݴ�������ϵGM");
//			return propUseEffect;
//		}
//		int second1 = 0;
//		try
//		{
//			second1 = Integer.parseInt(second.trim());
//		}
//		catch (Exception e)
//		{
//			propUseEffect.setIsEffected(false);
//			propUseEffect.setNoUseDisplay("���ݴ�������ϵGM");
//			return propUseEffect;
//		}
//		GoodsService goodsService = new GoodsService();
//		goodsService.removeProps(propGroup, use_num);
//		// ************�����Ƿ����ʹ���жϽ���**************//
//		// *************����ʹ��Ч��*********************//
//		LangjunConstants.QIANLIYAN.put(role_info.getBasicInfo().getPPk(),
//				DateUtil.addSecond(second1));
//		// �Ƴ�ʹ�õ���
//		propUseEffect.setPropType(prop.getPropClass());
//		propUseEffect.setIsEffected(true);
//		propUseEffect.setNoUseDisplay("�����ڿ�����ʱ������ǧ���ɾ����Ĳ����ˣ���Ч������" + second
//				+ "�롣");
//		return propUseEffect;
//	}

	/**
	 * ���ӷ�������ֵ���ߵ�ʹ��
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useAddDear(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************�����Ƿ����ʹ���жϽ���**************//
		int count = new FriendService().getFriendNum(role_info.getBasicInfo()
				.getPPk());
		if (count <= 0)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("����û�к�����");
			return propUseEffect;
		}

		// *************����ʹ��Ч��*********************//

		// �Ƴ�ʹ�õ���
		// GoodsService goodsService = new GoodsService();
		// goodsService.removeProps(propGroup, use_num);
		propUseEffect.setPropType(prop.getPropClass());
		propUseEffect.setIsEffected(true);
		return propUseEffect;
	}

	/**
	 * ���ӷ�������ֵ���ߵ�ʹ��
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useAddLoveDear(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************�����Ƿ����ʹ���жϽ���**************//
		List<FriendVO> list = new FriendDAO().isMerry(role_info.getBasicInfo()
				.getPPk()
				+ "");
		if (list == null || list.size() == 0)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("����û�н��");
			return propUseEffect;
		}

		// *************����ʹ��Ч��*********************//

		// �Ƴ�ʹ�õ���
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);
		propUseEffect.setPropType(prop.getPropClass());
		propUseEffect.setIsEffected(true);
		return propUseEffect;
	}

	/**
	 * ������������ߵ�ʹ��
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useMerryFu(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************�����Ƿ����ʹ���жϽ���**************//
		// List<FriendVO> list = new
		// FriendDAO().isMerry(role_info.getBasicInfo().getPPk()+"");
		// if(list==null||list.size()==0){
		// propUseEffect.setIsEffected(false);
		// propUseEffect.setNoUseDisplay("����û�н��");
		// return propUseEffect;
		// }

		List<FriendVO> list111 = new FriendService().findCanGetExp(role_info
				.getBasicInfo().getPPk(), 2, 0, 0);
		if (list111 == null || list111.size() == 0)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("û�о��������ȡ");
			return propUseEffect;
		}
		if (role_info.getBasicInfo().getGrade() == 39||role_info.getBasicInfo().getGrade() == 59
				||role_info.getBasicInfo().getGrade() == 69
				||role_info.getBasicInfo().getGrade() == 79
				|| role_info.getBasicInfo().getGrade() == GameConfig
						.getGradeUpperLimit())
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("��Ŀǰ��תְ״̬������ȡ����");
			return propUseEffect;
		}

		// *************����ʹ��Ч��*********************//

		// �Ƴ�ʹ�õ���
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);
		propUseEffect.setPropType(prop.getPropClass());
		propUseEffect.setIsEffected(true);
		return propUseEffect;
	}

	/**
	 * �ֵ���������ߵ�ʹ��
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useBrotherfu(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************�����Ƿ����ʹ���жϽ���**************//
		int count = new FriendService().findCanGetExpCount(role_info
				.getBasicInfo().getPPk(), 1);
		if (count <= 0)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("û�о��������ȡ");
			return propUseEffect;
		}
		if (role_info.getBasicInfo().getGrade() == 39
				|| role_info.getBasicInfo().getGrade() == GameConfig
						.getGradeUpperLimit())
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("��Ŀǰ��תְ״̬������ȡ����");
			return propUseEffect;
		}

		propUseEffect.setPropType(prop.getPropClass());
		propUseEffect.setIsEffected(true);
		return propUseEffect;
	}

	/**
	 * ��ӡ�����ߵ�ʹ��
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useXInyinfu(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************�����Ƿ����ʹ���жϽ���**************//

		String cur_scene_id = role_info.getBasicInfo().getSceneId();

		int barea_point = 0;
		// �õ���ż���ĵ�id
		FriendService fs = new FriendService();
		List<FriendVO> list = fs
				.isMerry(role_info.getBasicInfo().getPPk() + "");
		if (list == null || list.size() == 0)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(Constant.CAN_NOT_USE_XINYINFU);
			return propUseEffect;
		}
		FriendVO fo = list.get(0);
		RoleEntity re = RoleService.getRoleInfoById(fo.getFdPk() + "");
		if (re == null || re.isOnline()==false)
		{
			propUseEffect.setPropType(prop.getPropClass());
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("������ż��ǰ������");
			return propUseEffect;
		}
		barea_point = Integer.parseInt(re.getBasicInfo().getSceneId().trim());
		RoomService roomService = new RoomService();
		String carry_hint = roomService.isCarryFromSceneAToSceneB(Integer
				.parseInt(cur_scene_id), barea_point);
		if (carry_hint != null)
		{
			propUseEffect.setPropType(prop.getPropClass());
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(carry_hint);
			return propUseEffect;
		}

		// *************����ʹ��Ч��*********************//

		// �Ƴ�ʹ�õ���
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);
		role_info.getBasicInfo().updateSceneId(barea_point + "");// �������scene_id
		CompassService.removeMiJing(role_info.getBasicInfo().getPPk(),
				propGroup.getPropType());// ɾ���ؾ���ͼ
		propUseEffect.setPropType(prop.getPropClass());
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectValue(barea_point + "");
		propUseEffect.setNoUseDisplay("��ʹ����" + prop.getPropName() + "���͵�"
				+ (re.getBasicInfo().getSex() == 1 ? "�Ϲ�" : "����")
				+ re.getBasicInfo().getName() + "�����ڵ�!");
		return propUseEffect;
	}

	/**
	 * ʹ�� �鼮�����
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useBook(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// �����Ƿ����ʹ���ж�
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ����ؼ�ID
		String infoIdString = prop.getPropOperate1();

		// ����Ƿ�ɾ����־,1Ϊɾ��,2Ϊ��ɾ��
		String deleteflag = prop.getPropDrop();

		MiJiDao mijiDao = new MiJiDao();
		MiJiVO miJiVO = mijiDao.getMiJiById(Integer.parseInt(infoIdString));
		if (miJiVO != null)
		{
			if (deleteflag.equals("1"))
			{
				// �Ƴ�ʹ�õ���
				GoodsService goodsService = new GoodsService();
				goodsService.removeProps(propGroup, use_num);
			}
			propUseEffect.setIsEffected(true);
			propUseEffect.setEffectDisplay(miJiVO.getMjInfo());
			return propUseEffect;
		}
		else
		{
			propUseEffect.setIsEffected(true);
			propUseEffect.setEffectDisplay("���ؼ���ʱ�հ�!");
			return propUseEffect;

		}
	}

	/**
	 * ʹ�� �����ֵ����
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useReducePkValue(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// �����Ƿ����ʹ���ж�
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		return null;
	}

	/***************************************************************************
	 * ʹ����������
	 */
	private PropUseEffect useDefaultProp(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num,
			String noUseDisplay)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// �����Ƿ����ʹ���ж�
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		if (noUseDisplay == null || noUseDisplay.equals("")
				|| noUseDisplay.equals("null"))
		{
			propUseEffect.setNoUseDisplay("��ʹ����Ʒ!");
		}
		else
		{
			propUseEffect.setNoUseDisplay(noUseDisplay);
		}

		return propUseEffect;
	}

	/**
	 * // ĳʱ���������޳ͷ�����
	 */
	private PropUseEffect useDeadOutOfJinYang(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{

		PropUseEffect propUseEffect = new PropUseEffect();

		// �����Ƿ����ʹ���ж�
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		// �õ����߹��ܿ����ֶ�,��һ�����ֶ�Ϊ��Чʱ��
		String effect_time = prop.getPropOperate1();
		// �����,�ڶ�������Ӧ����id, ʵ�����ǵ��ߵ�����, ����п�ʹ�õ���idҲ���ڴ��������ֵ�Ļ�,���ܻ��ͻ
		TimeControlService timeService = new TimeControlService();
		timeService.updateControlInfoByTime(role_info.getBasicInfo().getPPk(),
				prop.getPropClass(), TimeControlService.ANOTHERPROP, Integer
						.valueOf(effect_time));

		// �ӵ�buff����
		BuffEffectService buffEffectService = new BuffEffectService();
		buffEffectService.createBuffEffect(role_info.getBasicInfo().getPPk(),
				BuffSystem.PLAYER, Integer.parseInt(prop.getPropOperate2()),
				Integer.parseInt(effect_time));

		// �Ƴ�ʹ�õ���
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);

		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay("��ʹ����" + prop.getPropName() + ","
				+ effect_time + "��������������ʧ���飡");
		return propUseEffect;
	}

	/**
	 * ʹ�÷����������.
	 */
	private PropUseEffect useGeiBoxCure(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// �����Ƿ����ʹ���ж�
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		int p_pk = role_info.getBasicInfo().getPPk();
		PlayerService playerService = new PlayerService();
		PartInfoVO player = playerService.getPlayerByPpk(p_pk);

		// �õ����߹��ܿ����ֶ�
		String npc_id = prop.getPropOperate1();

		NpcService npcService = new NpcService();
		GoodsService goodsService = new GoodsService();

		// ���ԭ���ĵ�����
		role_info.getDropSet().clearDropItem();
		
		// ������Ʒ
		npcService.dropGoodsByJiangRareBox(Integer.parseInt(npc_id), player);

		// �õ�������
		List<DropGoodsVO> dropgoods = role_info.getDropSet().getList();
		// �жϰ���������
		if (goodsService.isEnoughWrapSpace(p_pk, dropgoods.size()))
		{// ����

			// �����Ǯ
			// NpcDao npcdao = new NpcDao();
			// NpcVO npcvo = npcdao.getNpcById(Integer.parseInt(npc_id));
			//			
			NpcVO npcvo = NpcCache.getById(Integer.parseInt(npc_id));
			int dropmoney = npcvo.getDropMoney();
			if (dropmoney != 0)
			{

				role_info.getBasicInfo().addCopper(dropmoney);// ����ɫ���ӽ�Ǯ
				// ִ��ͳ��
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(6, StatisticsType.MONEY, dropmoney,
						StatisticsType.DEDAO, StatisticsType.BAOXIANG, p_pk);

			}
			if (npcvo.getExp() != 0)
			{
				// partInfoDao.updateExperience(p_pk,
				// Integer.parseInt(role_info.getBasicInfo().getCurExp())
				// + npcvo.getExp());

				// ���
				LogService logService = new LogService();
				logService.recordExpLog(role_info.getBasicInfo().getPPk(),
						role_info.getBasicInfo().getName(), role_info
								.getBasicInfo().getCurExp(), npcvo.getExp()
								+ "", "����õ�");

				role_info.getBasicInfo().updateAddCurExp(npcvo.getExp());

			}
			DropExpMoneyVO dropExpMoney = new DropExpMoneyVO();
			dropExpMoney.setDropMoney(dropmoney);
			dropExpMoney.setDropExp(npcvo.getExp());
			role_info.getDropSet().addExpAndMoney(dropExpMoney);

			propUseEffect.setPropType(PropType.GEI_RARE_BOX);
			propUseEffect.setIsEffected(true);
			// propUseEffect.setEffectDisplay("��������");
			propUseEffect.setEffectValue(dropmoney + "");
			propUseEffect.setEffectValue1(npcvo.getExp() + "");

			String ss = "";
			String douhao = "";
			for (int i = 0; i < dropgoods.size(); i++)
			{
				DropGoodsVO dropGoods = (DropGoodsVO) dropgoods.get(i);
				if ((i + 1) != dropgoods.size())
				{
					douhao = ",";
				}
				goodsService.putGoodsToWrap(p_pk, dropGoods.getGoodsId(),
						dropGoods.getGoodsType(), dropGoods.getGoodsQuality(),
						dropGoods.getDropNum(),GameLogManager.G_BOX_DROP);
				ss += dropGoods.getGoodsName() + "x" + dropGoods.getDropNum()
						+ douhao;
				// ִ��ͳ��
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(dropGoods.getGoodsId(), dropGoods
						.getGoodsType(), dropGoods.getDropNum(),
						StatisticsType.DEDAO, StatisticsType.BAOXIANG, p_pk);

			}

			String hint = "";
			if (dropmoney != 0)
			{
				hint = "������˽�Ǯ" + MoneyUtil.changeCopperToStr(dropmoney)
						+ "!<br/>";
			}
			hint = hint + ss + "";
			propUseEffect.setEffectDisplay(hint);
			// �Ƴ�ʹ�õ���
			goodsService.removeProps(propGroup, use_num);
			role_info.getDropSet().clearDropItem();
			role_info.getDropSet().clearExpAndMoney();
			if(propGroup.getPropId() == 4228){
				SystemInfoService systemInfoService = new SystemInfoService();
				systemInfoService.insertSystemInfoBySystem(role_info.getBasicInfo().getName()+"���˰������������!");
			}
		}
		else
		{
			role_info.getDropSet().clearDropItem();
			resutl = "���İ�����������!���������!";
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		return propUseEffect;
	}

	/**
	 * ʹ����װҩƷ
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useBoxCure(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();

		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		PlayerService playerService = new PlayerService();
		PartInfoVO player = playerService.getPlayerByPpk(role_info
				.getBasicInfo().getPPk());

		// �����װҩƷʣ��Ĵ���
		int surplus = propGroupDao.getSurplus(player.getPPk(), propGroup
				.getPgPk(), SpecialNumber.KUNZHUANG);
		if (surplus == 0)
		{
			// ���Ϊ�㣬��˵������װҩƷû����Ϣ�����������Ϣ����.���ҽ�����װҩƷ�İ󶨱�־��Ϊ��״̬.
			propGroupDao.insertSpecial(player.getPPk(), propGroup.getPgPk(),
					prop.getPropOperate2(), SpecialNumber.KUNZHUANG);
			propGroupDao.updatePropToBonding(propGroup.getPgPk());
			surplus = Integer.valueOf(prop.getPropOperate2());
		}
		logger.info("surplues=" + surplus);
		// �ж��Ǽ�Ѫ����װҩ���Ǽ���������װҩ��1Ϊ��Ѫ��,2Ϊ������
		int box_type = Integer.valueOf(prop.getPropOperate3());
		if (box_type == 1)
		{
			// ���ߵ�����ʹ��Ҫ��
			if (player.getPHp() == player.getPMaxHp())
			{
				propUseEffect.setIsEffected(false);
				propUseEffect.setNoUseDisplay("����Ѫ���Ѵ�����");
				return propUseEffect;
			}
			int add_hp = 0;
			int most_hp = Integer.parseInt(prop.getPropOperate1());
			if (surplus < most_hp)
			{
				most_hp = surplus;
			}
			int requieHp = player.getPMaxHp() - player.getPHp();
			if (requieHp > most_hp)
			{
				add_hp = most_hp;
			}
			else
			{
				add_hp = player.getPMaxHp() - player.getPHp();
			}

			logger.info("����Ѫ��=" + add_hp);

			if (add_hp + player.getPHp() > player.getPMaxHp())
			{
				add_hp = player.getPMaxHp() - player.getPHp();
			}
			player.setPHp(player.getPHp() + add_hp);

			GoodsService goodsService = new GoodsService();

			// ����Ѫ������ÿ�μ�Ѫ�������������������Ѫ��ʱ,�Ƴ�ʹ�õ��ߣ�������ٸõ��ߵ��ܿ��ûظ���
			if (surplus <= requieHp
					&& surplus <= Integer.parseInt(prop.getPropOperate1()))
			{
				goodsService.removeProps(propGroup, 1);

				boolean propGroupNot = propGroupDao.getUserHasProp(player
						.getPPk(), prop.getPropID());

				if (!propGroupNot)
				{
					ShortcutService shortcutService = new ShortcutService();
					shortcutService.clearShortcutoperate_id(player.getPPk(),
							prop.getPropID());
				}
			}
			else
			{
				goodsService.reduceBoxCure(propGroup, add_hp,
						SpecialNumber.KUNZHUANG);
			}
			// Ѫ������
			role_info.getBasicInfo().updateHp(player.getPHp());

			propUseEffect.setPropType(PropType.ADDHP);
			propUseEffect.setIsEffected(true);
			propUseEffect.setEffectDisplay("��ʹ����"
					+ StringUtil.isoToGBK(propGroup.getPropName()) + ",�ָ�������"
					+ add_hp + "��.");
			propUseEffect.setEffectValue("" + add_hp);

		}
		else
			if (box_type == 2)
			{
				if (player.getPMp() == player.getPMaxMp())
				{
					propUseEffect.setIsEffected(false);
					propUseEffect.setNoUseDisplay("���������Ѵ�����");
					return propUseEffect;
				}

				int add_mp = 0;
				int most_mp = Integer.parseInt(prop.getPropOperate1());
				if (surplus < most_mp)
				{
					most_mp = surplus;
				}
				int requieMp = player.getPMaxMp() - player.getPMp();
				if (requieMp > most_mp)
				{
					add_mp = most_mp;
				}
				else
				{
					add_mp = player.getPMaxMp() - player.getPMp();
				}

				logger.info("������Ѫ=" + add_mp);

				if (add_mp + player.getPMp() > player.getPMaxMp())
				{
					add_mp = player.getPMaxMp() - player.getPMp();
				}
				player.setPMp(player.getPMp() + add_mp);

				GoodsService goodsService = new GoodsService();

				// ����Ѫ������ÿ�μ�Ѫ�������������������Ѫ��ʱ,�Ƴ�ʹ�õ��ߣ�������ٸõ��ߵ��ܿ��ûظ���
				if (surplus <= requieMp
						&& surplus <= Integer.parseInt(prop.getPropOperate1()))
				{
					goodsService.removeProps(propGroup, 1);
					logger.info("����Ҫ����");
					boolean propGroupNot = propGroupDao.getUserHasProp(player
							.getPPk(), prop.getPropID());

					if (!propGroupNot)
					{
						ShortcutService shortcutService = new ShortcutService();
						shortcutService.clearShortcutoperate_id(
								player.getPPk(), prop.getPropID());
					}
				}
				else
				{
					goodsService.reduceBoxCure(propGroup, add_mp,
							SpecialNumber.KUNZHUANG);
				}
				// ��������
				role_info.getBasicInfo().updateMp(player.getPMp());

				propUseEffect.setPropType(PropType.ADDMP);
				propUseEffect.setIsEffected(true);
				propUseEffect.setEffectDisplay("��ʹ����"
						+ StringUtil.isoToGBK(propGroup.getPropName())
						+ ",�ָ�������" + add_mp + "��.");
				propUseEffect.setEffectValue("" + add_mp);

			}
		logger.info("isEffected=" + propUseEffect.getIsEffected() + "ҩƷ����="
				+ propUseEffect.getEffectDisplay());
		return propUseEffect;
	}

	/**
	 * ʹ��װ���޸�����
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	public PropUseEffect useFixArmProp(RoleEntity role_info,PlayerPropGroupVO propGroup)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, propGroup.getPropInfo(), 1);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		GoodsService goodsService = new GoodsService();
		
		int total_endure = Integer.parseInt(propGroup.getPropInfo().getPropOperate1());
		
		int left_endure = role_info.getEquipOnBody().maintainAllByEndure(total_endure);//����װ��
		String hint = null;
		if( left_endure<=0 )//�޸�����ȫ��װ��
		{
			hint = "��ʹ��" + propGroup.getPropInfo().getPropName()+ "�޸�����װ��ȫ���;ã�";
		}
		else//�޸�����װ��
		{
			hint = "��ʹ��"+propGroup.getPropInfo().getPropName()+"�޸�����װ���;�" + total_endure + "�㣬����" + left_endure+ "���;�û���޸���";
		}
		goodsService.removeProps(propGroup, 1);
		propUseEffect.setIsEffected(false);
		propUseEffect.setNoUseDisplay(hint);
		return propUseEffect;
	}

	/**
	 * ʹ�õ��߳���
	 * 
	 * @param p_pk
	 * @param prop_id
	 * @param pg_pk
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useProppet(RoleEntity role_info, PropVO prop,
			PlayerPropGroupVO propGroup, int use_num, String pet_pk)
	{
		if (role_info == null || prop == null || propGroup == null)
		{
			logger.info("��������");
			return null;
		}

		PropUseEffect propUseEffect = null;// ʹ��Ч��

		// û��ָ������
		if (use_num <= 0)
		{
			use_num = 1;
		}

		switch (prop.getPropClass())
		{
			case PropType.PETSINEW:// ����ظ�����
				propUseEffect = petSINEW(role_info, propGroup, prop, use_num,
						pet_pk);
				break;
			case PropType.PETLONGE:// ����ظ�����
				propUseEffect = petLONGE(role_info, propGroup, prop, use_num,
						pet_pk);
				break;
			case PropType.PETEXP:// ���ﾭ�����
				propUseEffect = petExp(role_info, propGroup, prop, use_num,
						pet_pk);
				break;
			/** ����ѧϰ���� */
			case PropType.PETSKILLBOOK:// ����ѧϰ����
				propUseEffect = usePETSKILLBOOK(role_info, propGroup, prop,
						use_num, pet_pk);
				break;
		}

		// ���³�ս������Ϣ
		logger.info("���³�ս������Ϣ");
		RolePetInfo userPet = role_info.getRolePetInfo();
		userPet.initPet(Integer.parseInt(pet_pk), role_info.getBasicInfo()
				.getPPk());

		// ��ÿ��ʹ�ô�������
		if (propUseEffect.getIsEffected() && prop.getPropUsedegree() > 0)
		{
			TimeControlService timeControlService = new TimeControlService();
			// ���µ���ʹ��״̬
			timeControlService.updateControlInfo(role_info.getBasicInfo()
					.getPPk(), prop.getPropID(), TimeControlService.PROP);
		}
		return propUseEffect;
	}

	/**
	 * ��ͨ����
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useNORMAL(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// �����Ƿ����ʹ���ж�
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		propUseEffect.setPropType(PropType.NORMAL);
		propUseEffect.setIsEffected(false);
		propUseEffect.setEffectDisplay("��ͨ����");
		propUseEffect.setEffectValue("");
		return propUseEffect;
	}

	/**
	 * �ұ���
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect breakRareBox(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();
		String hint = null;

		// �����Ƿ����ʹ���ж�
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		int p_pk = role_info.getBasicInfo().getPPk();
		PlayerService playerService = new PlayerService();
		PartInfoVO player = playerService.getPlayerByPpk(p_pk);

		// �õ����߹��ܿ����ֶ�
		String npc_id = prop.getPropOperate1();
		// �õ������������Ʒ
		String dropNum = prop.getPropOperate2();

		NpcService npcService = new NpcService();
		GoodsService goodsService = new GoodsService();

		// ���ԭ���ĵ�����
		role_info.getDropSet().clearDropItem();
		
		// ������Ʒ
		npcService.dropGoodsByRareBox(Integer.parseInt(npc_id), player, dropNum);

		// �õ�������
		List<DropGoodsVO> dropgoods = role_info.getDropSet().getList();

		// �жϰ���������
		if (goodsService.isEnoughWrapSpace(player.getPPk(), dropgoods.size()))
		{// ����

			// �����Ǯ
			// NpcDao npcdao = new NpcDao();
			// NpcVO npcvo = npcdao.getNpcById(Integer.parseInt(npc_id));

			NpcVO npcvo = NpcCache.getById(Integer.parseInt(npc_id));
			int money = npcvo.getDropMoney();
			if (money != 0)
			{
				role_info.getBasicInfo().addCopper(money);// ����ɫ���ӽ�Ǯ
				// ִ��ͳ��
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(6, StatisticsType.MONEY, money,
						StatisticsType.DEDAO, StatisticsType.BAOXIANG, player
								.getPPk());
				hint = "������˽�Ǯ" + MoneyUtil.changeCopperToStr(money) + "!<br/>";

			}
			if (npcvo.getExp() != 0)
			{
				// partInfoDao.updateExperience(player.getPPk(), Integer
				// .valueOf(player.getPExperience())
				// + npcvo.getExp());

				// ���
				LogService logService = new LogService();
				logService.recordExpLog(role_info.getBasicInfo().getPPk(),
						role_info.getBasicInfo().getName(), role_info
								.getBasicInfo().getCurExp(), npcvo.getExp()
								+ "", "�ұ���õ�");

				role_info.getBasicInfo().updateAddCurExp(npcvo.getExp());
			}
			DropExpMoneyVO dropExpMoney = new DropExpMoneyVO();
			dropExpMoney.setDropMoney(money);
			dropExpMoney.setDropExp(npcvo.getExp());
			role_info.getDropSet().addExpAndMoney(dropExpMoney);

			propUseEffect.setPropType(PropType.GEI_RARE_BOX);
			propUseEffect.setIsEffected(true);
			// propUseEffect.setEffectDisplay("��������");
			propUseEffect.setEffectValue(money + "");
			propUseEffect.setEffectValue1(npcvo.getExp() + "");

			String ss = "";
			String douhao = "";
			for (int i = 0; i < dropgoods.size(); i++)
			{
				DropGoodsVO dropGoods = (DropGoodsVO) dropgoods.get(i);
				if (i != dropgoods.size() - 1)
				{
					douhao = ",";
				}
				goodsService.putGoodsToWrap(player.getPPk(), dropGoods
						.getGoodsId(), dropGoods.getGoodsType(), dropGoods
						.getGoodsQuality(), dropGoods.getDropNum(),GameLogManager.G_BOX_DROP);
				ss = dropGoods.getGoodsName() + "x" + dropGoods.getDropNum()
						+ douhao;
				// ִ��ͳ��
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(dropGoods.getGoodsId(), dropGoods
						.getGoodsType(), dropGoods.getDropNum(),
						StatisticsType.DEDAO, StatisticsType.BAOXIANG, player
								.getPPk());
				if (hint != null)
				{
					if (i == 0)
					{
						hint = "�������Ʒ:" + ss + "";
					}
					else
					{
						hint = hint + ss + "";
					}
				}
				else
				{
					hint = "���������Ʒ:" + ss + "";
				}
			}
			hint = hint + "<br/>";
			if(prop.getPropID() == 4092 || prop.getPropID() == 4093 || prop.getPropID() == 4094){
			String info = role_info.getBasicInfo().getName()+"��ȼ����ҹ���̻�ʱ,����õ���"+ss+"!";
		    SystemInfoService infoService = new SystemInfoService();
		    infoService.insertSystemInfoBySystem(info) ;
			}
			propUseEffect.setEffectDisplay(hint);
			// �Ƴ�ʹ�õ���
			goodsService.removeProps(propGroup, use_num);
			role_info.getDropSet().clearDropItem();
			role_info.getDropSet().clearExpAndMoney();
		}
		else
		{
			role_info.getDropSet().clearDropItem();
			resutl = "���İ�����������!���������!";
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		/*
		 * propUseEffect.setPropType(PropType.RARE_BOX);
		 * propUseEffect.setIsEffected(true);
		 * propUseEffect.setEffectDisplay("�ұ���");
		 * propUseEffect.setEffectValue("");
		 */
		return propUseEffect;
	}

	/**
	 * �������б��������
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect acceptTaskFromList(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// �����Ƿ����ʹ���ж�
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		GoodsService goodsService = new GoodsService();
		TaskSubService taskService = new TaskSubService();
		int task_type = 1;// �������� 1 ���ߴ������� 2 �˵���������
		// ��������
		String hint = taskService.accectTaskFromList(role_info, prop
				.getPropID(), propUseEffect, task_type);
		if (hint == null)
		{
			// �Ƴ�ʹ�õ���
			goodsService.removeProps(propGroup, use_num);
		}
		else
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(hint);
			return propUseEffect;
		}

		propUseEffect.setPropType(PropType.ACCEPT_TASK_FROM_LIST);
		propUseEffect.setIsEffected(true);
		// propUseEffect.setEffectDisplay("������������");
		propUseEffect.setEffectValue("");
		return propUseEffect;
	}

	/**
	 * ����ָ���������
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect acceptSpesifyTask(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// �����Ƿ����ʹ���ж�
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		// �õ����߹��ܿ����ֶ�
		String task_id = prop.getPropOperate1();

		GoodsService goodsService = new GoodsService();
		TaskSubService taskService = new TaskSubService();

		// �Ƴ�ʹ�õ���
		goodsService.removeProps(propGroup, use_num);
		// ��������
		taskService.acceptTask(role_info, Integer.parseInt(task_id));

		propUseEffect.setPropType(PropType.ACCEPT_SPECIFY_TASK);
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay("��������ָ������");
		propUseEffect.setEffectValue("");
		return propUseEffect;
	}

	/**
	 * ���ﵰ����ʹ��
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect usePETEGG(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// �����Ƿ����ʹ���ж�
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		int p_pk = role_info.getBasicInfo().getPPk();

		// �õ����߹��ܿ����ֶ�
		String pet_id = prop.getPropOperate1();
		String pet_name = "";

		GoodsService goodsService = new GoodsService();
		PetService petService = new PetService();

		int pet_num = petService.getNumOfPet(p_pk);
		if (pet_num > 5)
		{
			resutl = "�������������";
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		else
		{
			// �Ƴ�ʹ�õ���
			goodsService.removeProps(propGroup, use_num);
			// ���ɳ���
			pet_name = petService.createPetByPetID(p_pk, Integer
					.parseInt(pet_id));
		}

		propUseEffect.setPropType(PropType.PET_EGG);
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay("��ʹ����" + propGroup.getPropName()
				+ ",�����" + StringUtil.isoToGBK(pet_name));
		propUseEffect.setEffectValue("");
		return propUseEffect;
	}

	/**
	 * ϴ�������
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useINITPET(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// �����Ƿ����ʹ���ж�
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		propUseEffect.setPropType(PropType.INIT_PET);
		propUseEffect.setIsEffected(true);
		return propUseEffect;
	}

	/**
	 * ʹ��buff����
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useBUFF(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************�����Ƿ����ʹ���жϽ���**************//

		int p_pk = role_info.getBasicInfo().getPPk();

		// �õ����߹��ܿ����ֶ�
		String buff_id_s = prop.getPropOperate1();
		String[] buff_id = null;
		if (buff_id_s != null && !buff_id_s.equals(""))
			buff_id = buff_id_s.split(",");

		// ɾ�����ϵ���ͬ���͵�buff
		// buffEffectService.deleteSameBuff(player, propGroup);

		// �Ƴ�ʹ�õ���
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);

		BuffMenuService buffMenuService = new BuffMenuService();
		// *************����ʹ��Ч��*********************//
		for (int i = 0; i < buff_id.length; i++)
		{
			buffMenuService.setBuffStatus(p_pk, Integer.parseInt(buff_id[i]));
		}

		propUseEffect.setPropType(PropType.BUFF);
		propUseEffect.setIsEffected(true);
		// propUseEffect.setEffectDisplay("buffЧ����Ч");
		propUseEffect.setBuffType(buff_id);
		propUseEffect.setEffectDisplay(prop);
		return propUseEffect;
	}

	/**
	 * ʹ�ü��������
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useSKILLBOOK(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();
		propUseEffect.setPropType(prop.getPropClass());

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************�����Ƿ����ʹ���жϽ���**************//

		int p_pk = role_info.getBasicInfo().getPPk();

		SkillService skillService = new SkillService();
		String display = skillService.studySkillAll(p_pk, prop, propUseEffect);

		if (display.contains("���Ѿ�ѧϰ��")) // ��û��ѧ�˼���
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(display);
			return propUseEffect;
		}
		else
			if (display.contains("����û��ѧϰ"))
			{
				propUseEffect.setIsEffected(false);
				propUseEffect.setNoUseDisplay(display);
				return propUseEffect;
			}
			else
				if (display.contains("������"))
				{
					propUseEffect.setIsEffected(false);
					propUseEffect.setNoUseDisplay(display);
					return propUseEffect;
				}
				else
				{
					// �Ƴ�ʹ�õ���
					GoodsService goodsService = new GoodsService();
					goodsService.removeProps(propGroup, use_num);

					propUseEffect.setPropType(PropType.SKILLBOOK);
					propUseEffect.setIsEffected(true);
					propUseEffect.setEffectDisplay(display);
					return propUseEffect;
				}
	}

	/**
	 * �ƺ�
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useHONOUR(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();
		propUseEffect.setPropType(prop.getPropClass());

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************�����Ƿ����ʹ���жϽ���**************//

		RoleTitleSet role_title = role_info.getTitleSet();
		

		// �õ����߹��ܿ����ֶ�
		String conditions = prop.getPropOperate1();// �õ��ƺŵ�����
		String t_id = prop.getPropOperate2();// �õ��ƺŵ�ID
		
		TitleVO condi_title = TitleCache.getById(conditions);
		TitleVO new_title = TitleCache.getById(t_id);
		
		// �����Ƿ�����õ�����
		if ( condi_title!=null && role_title.isHaveByTId(condi_title) == false)//�Ƿ��������ƺ�
		{// ����������
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("������Ȼ��" + condi_title.getName()
					+ "�ƺź���ܻ��" + new_title.getName() + "�ƺ�");// û���������ʲô��
			return propUseEffect;
		}
		
		
		String hint = role_info.getTitleSet().gainTitle(new_title);
		if( hint!=null )//��óƺ�ʧ��
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(hint);
			return propUseEffect;
		}
		
		// �Ƴ�ʹ�õ���
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);
		propUseEffect.setIsEffected(false);
		propUseEffect.setNoUseDisplay("��������µĳƺ�:" + new_title.getName());
		return propUseEffect;
	}

	/**
	 * ʹ�û�Ա��
	 */
	private PropUseEffect useVIP(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();
		propUseEffect.setPropType(prop.getPropClass());

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************�����Ƿ����ʹ���жϽ���**************//
		// �õ����߹��ܿ����ֶ�
		String vip_title_id = prop.getPropOperate2().trim();// VIP�ƺ�id
		TitleVO vip_title = TitleCache.getById(vip_title_id);
		
		// �����ж��Ƿ����ֱ�ӻ�ȡһ����Ա
		String isGainNewVipTitle = role_info.getTitleSet().isGainNewVipTitle(vip_title);
		if (isGainNewVipTitle != null )
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(isGainNewVipTitle);
			return propUseEffect;
		}
		// ���������Ժ�
		// �Ƴ�ʹ�õ���
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);
		role_info.getTitleSet().gainTitle(vip_title);
		
		Vip vip = VipManager.getByTId(vip_title.getId());
		propUseEffect.setIsEffected(true);
		propUseEffect.setPropType(PropType.VIP);
		propUseEffect.setEffectDisplay(vip.getHint());
		return propUseEffect;
	}

	/**
	 * ����ظ�����
	 * 
	 * @param prop
	 * @param object
	 */
	public PropUseEffect petSINEW(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num, String pet_pk)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// �����Ƿ����ʹ���ж�
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		int p_pk = role_info.getBasicInfo().getPPk();

		/*
		 * //���ߵ�����ʹ��Ҫ�� if( player.getPHp()==player.getPUpHp() ) {
		 * propUseEffect.setIsEffected(false);
		 * propUseEffect.setNoUseDisplay("����Ѫ���Ѵ�����"); return propUseEffect; }
		 */

		// ͨ����ɫID ȡ����ɫӵ�еĳ���
		PetInfoDAO dao = new PetInfoDAO();
		PetInfoVO petInfoVO = (PetInfoVO) dao.getPetInfoshiyong(p_pk + "",
				pet_pk);
		if (petInfoVO == null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("���ĳ���û��һ����ս");
			return propUseEffect;
		}
		// �����Ƿ����ʹ���жϽ���
		int add_hp = Integer.parseInt(prop.getPropOperate1());// �����ֶ� //
		// ȡ�����ǳ����������ֵ
		int petFatigue = 0;

		if (add_hp == 100)
		{
			petFatigue = 100;
		}
		if (petInfoVO.getPetFatigue() == 100)
		{
			petFatigue = (100 - petInfoVO.getPetFatigue())
					+ petInfoVO.getPetFatigue();
			propUseEffect.setPropType(PropType.ADDHP);
			propUseEffect.setIsEffected(true);
			propUseEffect.setEffectDisplay("���ĳ��������Ѿ�����,����Ҫ��ʹ��.");
			propUseEffect.setEffectValue("" + add_hp);
			return propUseEffect;
		}
		if (petInfoVO.getPetFatigue() < 100
				&& (add_hp + petInfoVO.getPetFatigue()) >= 100)
		{
			petFatigue = 100;
		}
		if ((add_hp + petInfoVO.getPetFatigue()) < 100)
		{
			petFatigue = add_hp + petInfoVO.getPetFatigue();
		}

		GoodsService goodsService = new GoodsService();
		// �Ƴ�ʹ�õ���
		goodsService.removeProps(propGroup, use_num);
		// �޸ĳ�������
		dao.petFatigue(p_pk, petInfoVO.getPetPk(), petFatigue);

		propUseEffect.setPropType(PropType.ADDHP);
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay("��ʹ����" + propGroup.getPropName()
				+ ",���ĳ�����������" + add_hp + "��");
		// �ж��Ƿ���� ���˷��� ��ʾ��������
		if (petFatigue < 100)
		{
			int isPetFatigue = 1;
			propUseEffect.setIsPetFatigue(isPetFatigue);
		}
		propUseEffect.setEffectValue("" + add_hp);
		return propUseEffect;
	}

	/**
	 * ���ﾭ�����
	 * 
	 * @param prop
	 * @param object
	 */
	public PropUseEffect petExp(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num, String pet_pk)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// �����Ƿ����ʹ���ж�
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		/*
		 * //���ߵ�����ʹ��Ҫ�� if( player.getPHp()==player.getPUpHp() ) {
		 * propUseEffect.setIsEffected(false);
		 * propUseEffect.setNoUseDisplay("����Ѫ���Ѵ�����"); return propUseEffect; }
		 */

		// ͨ����ɫID ȡ����ɫӵ�еĳ���
		PetInfoDAO dao = new PetInfoDAO();
		PetInfoVO petInfoVO = (PetInfoVO) dao.getPetInfoshiyong(role_info
				.getBasicInfo().getPPk()
				+ "", pet_pk);
		if (petInfoVO == null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("���ĳ���û��һ����ս");
			return propUseEffect;
		}
		// �����Ƿ����ʹ���жϽ���
		int add_hp = Integer.parseInt(prop.getPropOperate1());// �����ֶ� //���ӵ�����
		int exp = Integer.parseInt(prop.getPropOperate2());// �����ֶ� //���ӵľ���
		// ȡ�����ǳ����������ֵ
		int petFatigue = 0;

		if (add_hp == 100)
		{
			petFatigue = 100;
		}
		//ˮ��˵������þ�������20091209
		/*if (petInfoVO.getPetFatigue() == 100)
		{
			petFatigue = (100 - petInfoVO.getPetFatigue())
					+ petInfoVO.getPetFatigue();
			propUseEffect.setPropType(PropType.ADDHP);
			propUseEffect.setIsEffected(true);
			propUseEffect.setEffectDisplay("���ĳ��ﲻ���ڼ���ιʳ��.");
			propUseEffect.setEffectValue("" + add_hp);
			return propUseEffect;
		}*/
		if (petInfoVO.getPetFatigue() < 100
				&& (add_hp + petInfoVO.getPetFatigue()) >= 100)
		{
			petFatigue = 100;
		}
		if ((add_hp + petInfoVO.getPetFatigue()) < 100)
		{
			petFatigue = add_hp + petInfoVO.getPetFatigue();
		}

		GoodsService goodsService = new GoodsService();

		// �޸ĳ��ﾭ��
		// ����ɳ�
		HhjPetService petService = new HhjPetService();
		String pet_display = petService.getPetGrandirProp(petInfoVO.getPetPk(),
				role_info.getBasicInfo().getGrade(), exp);

		// �Ƴ�ʹ�õ���
		goodsService.removeProps(propGroup, use_num);
		// �޸ĳ�������
		dao.petFatigue(role_info.getBasicInfo().getPPk(), petInfoVO.getPetPk(),
				petFatigue);

		propUseEffect.setPropType(PropType.ADDHP);
		propUseEffect.setIsEffected(true);
		// propUseEffect.setEffectDisplay("��ʹ����" + propGroup.getPropName()+
		// ",���ĳ�����������" + exp + "�㾭��");
		propUseEffect.setEffectDisplay(pet_display);
		// �ж��Ƿ���� ���˷��� ��ʾ��������
		if (petFatigue < 100)
		{
			int isPetFatigue = 1;
			propUseEffect.setIsPetFatigue(isPetFatigue);
		}
		propUseEffect.setEffectValue("" + add_hp);
		return propUseEffect;
	}

	/**
	 * ����ظ�����
	 * 
	 * @param prop
	 * @param object
	 */
	public PropUseEffect petLONGE(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num, String pet_pk)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// �����Ƿ����ʹ���ж�
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		int p_pk = role_info.getBasicInfo().getPPk();

		// ͨ����ɫID ȡ����ɫӵ�еĳ���
		PetInfoDAO dao = new PetInfoDAO();
		PetInfoVO petInfoVO = (PetInfoVO) dao.getPetInfoshiyong(p_pk + "",
				pet_pk);
		if (petInfoVO == null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("���ĳ���û��һ����ս");
			return propUseEffect;
		}
		// �����Ƿ����ʹ���жϽ���
		int add_hp = Integer.parseInt(prop.getPropOperate1());// �����ֶ�
		// ȡ�����ǳ����������ֵ
		int petLonge = add_hp + petInfoVO.getPetLonge();
		// �жϳ�����������\
		if (petInfoVO.getPetLonge() == petInfoVO.getPetLife())
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("���ĳ��������Ѿ�����������");
			return propUseEffect;
		}
		if (petLonge > petInfoVO.getPetLife())
		{
			petLonge = petInfoVO.getPetLife();
		}

		GoodsService goodsService = new GoodsService();
		/*
		 * //TODPO ȡ����������ʹ������ if (petInfoVO.getLongeNumber() ==
		 * petInfoVO.getLongeNumberOk()) { propUseEffect.setIsEffected(false);
		 * propUseEffect.setNoUseDisplay("�Ѿ������õ��ߵ�ʹ�ô�����"); return propUseEffect; }
		 */
		PetInfoDAO petInfoDAO = new PetInfoDAO();
		int pet_longe = petInfoDAO.pet_longe(p_pk, Integer.parseInt(pet_pk));
		if (pet_longe == 0)
		{
			propUseEffect.setNoUseDisplay("���ĳ�������Ϊ�� ������ʹ��ҩ�����������ˣ�");
			return propUseEffect;
		}
		int longeNumberOk = petInfoVO.getLongeNumberOk() + 1;

		// �Ƴ�ʹ�õ���
		goodsService.removeProps(propGroup, use_num);
		// �޸ĳ�������
		dao.petLonge(p_pk, petInfoVO.getPetPk(), petLonge, longeNumberOk);

		propUseEffect.setPropType(PropType.ADDHP);
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay("��ʹ����" + propGroup.getPropName());
		propUseEffect.setEffectValue("" + add_hp);
		return propUseEffect;
	}

	/**
	 * ʹ�ñ�ǵ���
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useMARKUP(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{

		PropUseEffect propUseEffect = new PropUseEffect();

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************�����Ƿ����ʹ���жϽ���**************//

		// �õ����߹��ܿ����ֶ�
		// ��������ֶ�

		// *************����ʹ��Ч��*********************//
		int p_pk = role_info.getBasicInfo().getPPk();
		String cur_scene_id_str = role_info.getBasicInfo().getSceneId();

		String scene_name = null;
		CoordinateDao coordinateDao = new CoordinateDao();
		int isUse = coordinateDao.isUse(p_pk, prop.getPropID());
		int current_scene_id = Integer.parseInt(cur_scene_id_str);
		int scene_id = coordinateDao.getCoordinate(p_pk, prop.getPropID());

		// logger.info("scene_id="+scene_id+"
		// ,player.getPMap="+player.getPMap());
		RoomService roomService = new RoomService();

		// logger.info("isUse="+isUse+" ,scene_id="+scene_id);
		if (isUse != 1)// ���
		{
			if (roomService.isCarryedIn(current_scene_id) != null)// ���ܴ���ĵص㲻�������
			{
				propUseEffect.setPropType(PropType.MARKUP);
				propUseEffect.setIsEffected(false);
				propUseEffect.setNoUseDisplay("�˵ص㲻��ʹ�øõ���");
				return propUseEffect;
			}

			if (scene_id == -1)// û�б��
			{
				CoordinateVO coordinate = new CoordinateVO();
				coordinate.setPPk(p_pk);
				coordinate.setCoordinatePropId(prop.getPropID());
				coordinate.setCoordinate(current_scene_id);
				coordinateDao.add(coordinate);
			}
			else
			// ���±��
			{
				coordinateDao.updateCoordinate(p_pk, prop.getPropID(),
						current_scene_id);
			}

			scene_id = current_scene_id;
			String sceneName = roomService.getName(scene_id);

			propUseEffect.setPropType(PropType.MARKUP);
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("�Ѿ���" + sceneName + "���˱��");
			return propUseEffect;
		}
		else
		// ʹ��
		{
			// �ж��Ƿ���Դ���
			if (roomService.isCarryFromSceneAToSceneB(current_scene_id,
					scene_id) != null)
			{
				propUseEffect.setPropType(prop.getPropClass());
				propUseEffect.setIsEffected(false);
				// propUseEffect.setEffectValue("���ܴ���");
				propUseEffect.setEffectValue(current_scene_id + "");
				return propUseEffect;
			}
			else
			{
				// �Ƴ�ʹ�õ���
				GoodsService goodsService = new GoodsService();
				goodsService.removeProps(propGroup, use_num);
				int prop_num = goodsService.getPropNum(p_pk, prop.getPropID());
				if (prop_num == 0)// ��ǵ���Ϊ0ʱɾ�����
				{
					// ɾ�����
					coordinateDao.delete(p_pk, prop.getPropID());
				}
				else
				{
					coordinateDao.updateNoUsed(p_pk, prop.getPropID());
				}
				role_info.getBasicInfo().updateSceneId(scene_id + "");
				CompassService.removeMiJing(p_pk, propGroup.getPropType());// ɾ���ؾ���ͼ
			}
		}

		// �Ƴ�ʹ�õ���
		// �ñ�ǵ��߱��ʱ�������Ƴ�����

		propUseEffect.setPropType(PropType.MARKUP);
		propUseEffect.setIsEffected(true);
		scene_name = roomService.getName(scene_id);
		propUseEffect.setEffectDisplay("�Ѿ���" + scene_name + "���˱��");
		propUseEffect.setEffectValue("" + scene_id);
		return propUseEffect;
	}

	/**
	 * ʹ���ٻ�����
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useCONJURE(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{

		PropUseEffect propUseEffect = new PropUseEffect();

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************�����Ƿ����ʹ���жϽ���**************//

		// �õ����߹��ܿ����ֶ�
		String condition = prop.getPropOperate1();

		// �Ƴ�ʹ�õ���
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);
		// *************����ʹ��Ч��*********************//
		int p_pk = role_info.getBasicInfo().getPPk();
		String cur_scene_id = role_info.getBasicInfo().getSceneId();

		RefurbishService refurbishService = new RefurbishService();
		refurbishService.createNPCByCondition(p_pk, condition,
				NpcAttackVO.DEADNPC, Integer.parseInt(cur_scene_id));

		propUseEffect.setPropType(PropType.CONJURE);
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay("");
		return propUseEffect;
	}

	/**
	 * ������ߵ�ʹ��
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useQUIZ(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************�����Ƿ����ʹ���жϽ���**************//

		// �õ����߹��ܿ����ֶ�
		String quizs_str = prop.getPropOperate1();// ȡ����Ŀ��Χ�ַ���
		QuizService quizService = new QuizService();
		QuizVO quiz = quizService.getRandomQuizByConfine(quizs_str);// ����õ�һ����Ŀ

		// �ж��Ƿ���Է�����Ŀ�������߻�װ��
		String goodstr = quiz.getAwardGoods();
		if (goodstr != null && !goodstr.equals("") && !goodstr.equals("null"))
		{
			String[] goodStrings = goodstr.split(";");
			if (role_info.getBasicInfo().getWrapSpare() < goodStrings.length)
			{
				propUseEffect.setIsEffected(false);
				propUseEffect.setNoUseDisplay("���İ����ռ䲻��!");
				return propUseEffect;
			}
		}

		// *************����ʹ��Ч��*********************//

		// �Ƴ�ʹ�õ���
		// GoodsService goodsService = new GoodsService();
		// goodsService.removeProps(propGroup, use_num);

		propUseEffect.setPropType(PropType.QUIZ);
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay(quiz.getQuizContent());
		propUseEffect.setEffectValue(quiz.getQuizAnswers());
		propUseEffect.setEffectValue1(quiz.getId() + "");
		return propUseEffect;
	}

	/**
	 * �سǵ��ߵ�ʹ��
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useCarry(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************�����Ƿ����ʹ���жϽ���**************//

		String cur_scene_id = role_info.getBasicInfo().getSceneId();

		String new_scene_id = prop.getPropOperate1();// �µĵص�ID
		Pattern p = Pattern
				.compile(Expression.positive_integer_contain0_regexp);
		Matcher m = p.matcher(new_scene_id);
		boolean b = m.matches();
		if (b == false)
		{
			propUseEffect.setPropType(prop.getPropClass());
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("���ߴ���,������ʹ��!");
			return propUseEffect;
		}
		RoomService roomService = new RoomService();
		String carry_hint = roomService.isCarryFromSceneAToSceneB(Integer
				.parseInt(cur_scene_id), Integer.parseInt(new_scene_id));
		if (carry_hint != null)
		{
			propUseEffect.setPropType(prop.getPropClass());
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(carry_hint);
			return propUseEffect;
		}

		// *************����ʹ��Ч��*********************//

		// �Ƴ�ʹ�õ���
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);
		role_info.getBasicInfo().updateSceneId(new_scene_id + "");// �������scene_id
		CompassService.removeMiJing(role_info.getBasicInfo().getPPk(),
				propGroup.getPropType());// ɾ���ؾ���ͼ
		propUseEffect.setPropType(prop.getPropClass());
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectValue(new_scene_id + "");
		return propUseEffect;
	}

	// ���͵���
	private PropUseEffect useGOBACKCITY(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************�����Ƿ����ʹ���жϽ���**************//

		String cur_scene_id = role_info.getBasicInfo().getSceneId();

		int barea_point = 0;
		// �õ��������ĵ�id
		MapService mapService = new MapService();
		barea_point = mapService.getBareaPointBySceneID(Integer
				.parseInt(cur_scene_id));
		RoomService roomService = new RoomService();
		String carry_hint = roomService.isCarryFromSceneAToSceneB(Integer
				.parseInt(cur_scene_id), barea_point);
		if (carry_hint != null)
		{
			propUseEffect.setPropType(prop.getPropClass());
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(carry_hint);
			return propUseEffect;
		}

		// *************����ʹ��Ч��*********************//

		// �Ƴ�ʹ�õ���
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);
		role_info.getBasicInfo().updateSceneId(barea_point + "");// �������scene_id

		propUseEffect.setPropType(prop.getPropClass());
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectValue(barea_point + "");
		return propUseEffect;
	}

	/**
	 * ʹ��תְ����
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useZHUANZHI(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		GrowService growService = new GrowService();
		PropUseEffect propUseEffect = new PropUseEffect();

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************�����Ƿ����ʹ���жϽ���**************//
		PlayerService playerService = new PlayerService();
		PartInfoVO player = playerService.getPlayerByPpk(role_info.getBasicInfo().getPPk());
		RoleTitleSet role_title = role_info.getTitleSet();

		// �õ����߹��ܿ����ֶ�
		String conditions = prop.getPropOperate1();// �õ��ƺŵ�����
		String t_id = prop.getPropOperate2();// �õ��ƺŵ�ID
		
		TitleVO condi_title = TitleCache.getById(conditions);
		TitleVO new_title = TitleCache.getById(t_id);
		
		// �ж��Ƿ�ﵽ�¼�����
		if (Integer.parseInt(role_info.getBasicInfo().getCurExp().trim()) < Integer.parseInt(role_info.getBasicInfo().getNextGradeExp().trim()))
		{
			resutl = "���ľ��鲻��,����תְ.";
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		
		// �����Ƿ�����õ�����
		if ( condi_title!=null && role_title.isHaveByTId(condi_title) == false)//�Ƿ��������ƺ�
		{// ����������
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("������Ȼ��" + condi_title.getName()
					+ "�ƺź����תְ");// û���������ʲô��
			return propUseEffect;
		}
		
		// �ж��Ƿ���ת��ְ
		if (role_title.isHaveByTId(new_title) == true)//�Ƿ��������ƺ�
		{// ����������
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("����ת��ְ,������ʹ����.");// û���������ʲô��
			return propUseEffect;
		}
		
		String hint = role_info.getTitleSet().gainTitle(new_title);
		if( hint!=null )//��óƺ�ʧ��
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(hint);
			return propUseEffect;
		}

		// *************����ʹ��Ч��*********************//
		GoodsService goodsService = new GoodsService();
		// �Ƴ�ʹ�õ���
		goodsService.removeProps(propGroup, use_num);
		
		// �������
		growService.upgrade(player, role_info);
		propUseEffect.setTitle(t_id);
		propUseEffect.setPropType(PropType.ZHUANZHI);
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay("��ʹ����"
				+ StringUtil.isoToGBK(propGroup.getPropName()) + "<br/>����������"
				+ player.getPGrade() + "��");
		return propUseEffect;
	}

	/**
	 * ʹ�ü�Ѫ����
	 * 
	 * @param prop
	 * @param object
	 */
	private PropUseEffect useADDHP(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// �����Ƿ����ʹ���ж�
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		PlayerService playerService = new PlayerService();
		PartInfoVO player = playerService.getPlayerByPpk(role_info
				.getBasicInfo().getPPk());

		// ���ߵ�����ʹ��Ҫ��
		if (player.getPHp() == player.getPMaxHp())
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("����Ѫ���Ѵ�����");
			return propUseEffect;
		}

		// �����Ƿ����ʹ���жϽ���

		int add_hp = Integer.parseInt(prop.getPropOperate1());
		if (add_hp + player.getPHp() > player.getPMaxHp())
		{
			add_hp = player.getPMaxHp() - player.getPHp();
		}

		player.setPHp(player.getPHp() + add_hp);

		GoodsService goodsService = new GoodsService();

		// �Ƴ�ʹ�õ���
		goodsService.removeProps(propGroup, use_num);
		// Ѫ������
		role_info.getBasicInfo().updateHp(player.getPHp());

		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		boolean propGroupNot = propGroupDao.getUserHasProp(player.getPPk(),
				prop.getPropID());
		if (!propGroupNot)
		{
			ShortcutService shortcutService = new ShortcutService();
			shortcutService.clearShortcutoperate_id(player.getPPk(), prop
					.getPropID());
		}
		propUseEffect.setPropType(PropType.ADDHP);
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay("��ʹ����"
				+ StringUtil.isoToGBK(propGroup.getPropName()) + ",������"
				+ add_hp + "��Ѫ��!");
		propUseEffect.setEffectValue("" + add_hp);
		return propUseEffect;
	}

	/**
	 * ������ֱ�Ӹ���Ҽҵ���ʱ��
	 * 
	 * @param prop
	 * @param object
	 */
//	private PropUseEffect useRoleBeoffExp(RoleEntity role_info,
//			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
//	{
//		PropUseEffect propUseEffect = new PropUseEffect();
//		propUseEffect.setPropType(prop.getPropClass());
//		// ************�����Ƿ����ʹ���ж� **************//
//		// ����ʹ�û����ж�
//		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
//		if (resutl != null)
//		{
//			propUseEffect.setIsEffected(false);
//			propUseEffect.setNoUseDisplay(resutl);
//			return propUseEffect;
//		}
//		// ************�����Ƿ����ʹ���жϽ���**************//
//		int p_pk = role_info.getBasicInfo().getPPk();
//		// �õ����߹��ܿ����ֶ�
//		RoleBeOffService roleBeOffService = new RoleBeOffService();
//		String hint = roleBeOffService.addroleBeOffExpOfPropTime(p_pk, prop
//				.getPropID(), use_num + "");
//		// �Ƴ�ʹ�õ���
//		GoodsService goodsService = new GoodsService();
//		propUseEffect.setPropType(PropType.ROLE_BEOFF_EXP);
//		goodsService.removeProps(propGroup, use_num);
//		propUseEffect.setIsEffected(false);
//		propUseEffect.setNoUseDisplay(hint);
//		return propUseEffect;
//	}

	/**
	 * ʹ�ü�������
	 * 
	 * @param prop
	 * @param object
	 */
	private PropUseEffect useADDMP(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// �����Ƿ����ʹ���ж�
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setPropType(prop.getPropClass());
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		PlayerService playerService = new PlayerService();
		PartInfoVO player = playerService.getPlayerByPpk(role_info
				.getBasicInfo().getPPk());

		// ���ߵ�����ʹ��Ҫ��
		if (propGroup == null || propGroup.getPropNum() == 0)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("��Ʒ����");
			return propUseEffect;
		}
		if (player.getPMp() == player.getPMaxMp())
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("���������Ѵ�����");
			return propUseEffect;
		}

		// �����Ƿ����ʹ���жϽ���

		int add_mp = Integer.parseInt(prop.getPropOperate1());

		if (add_mp + player.getPMp() >= player.getPMaxMp())
		{
			add_mp = player.getPMaxMp() - player.getPMp();
			player.setPMp(player.getPMaxMp());
		}
		else
		{
			player.setPMp(player.getPMp() + add_mp);
		}

		GoodsService goodsService = new GoodsService();

		// �Ƴ�ʹ�õ���
		goodsService.removeProps(propGroup, use_num);
		// ��������
		role_info.getBasicInfo().updateMp(player.getPMp());

		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		boolean propGroupNot = propGroupDao.getUserHasProp(player.getPPk(),
				prop.getPropID());
		if (!propGroupNot)
		{
			ShortcutService shortcutService = new ShortcutService();
			shortcutService.clearShortcutoperate_id(player.getPPk(), prop
					.getPropID());
		}
		propUseEffect.setPropType(PropType.ADDMP);
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay("��ʹ����"
				+ StringUtil.isoToGBK(propGroup.getPropName()) + ",������"
				+ add_mp + "������!");
		propUseEffect.setEffectValue("" + add_mp);
		return propUseEffect;
	}

	/**
	 * ���������
	 * 
	 * @param prop
	 * @param object
	 */
	private PropUseEffect useADDHMP(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();
		GoodsService goodsService = new GoodsService();

		int state = role_info.getStateInfo().getCurState();
		
		if(state == PlayerState.PKFIGHT || state == PlayerState.PKFIGHT || state == PlayerState.FIGHT){
			propUseEffect.setPropType(prop.getPropClass());
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("PK״̬����ʹ�øõ���");
			return propUseEffect;
		}
		
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setPropType(prop.getPropClass());
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		PlayerService playerService = new PlayerService();
		PartInfoVO player = playerService.getPlayerByPpk(role_info
				.getBasicInfo().getPPk());

		// ���ߵ�����ʹ��Ҫ��
		if (propGroup == null || propGroup.getPropNum() == 0)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("��Ʒ����");
			return propUseEffect;
		}
		if (prop.getPropOperate1().equals("1"))
		{
			if (player.getPHp() == player.getPMaxHp())
			{
				propUseEffect.setIsEffected(false);
				propUseEffect.setNoUseDisplay("����Ѫ���Ѵ�����");
				return propUseEffect;
			}

			// �Ƴ�ʹ�õ���
			goodsService.removeProps(propGroup, use_num);
			// Ѫ������
			role_info.getBasicInfo().updateHp(player.getPMaxHp());

			PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
			boolean propGroupNot = propGroupDao.getUserHasProp(player.getPPk(),
					prop.getPropID());
			if (!propGroupNot)
			{
				ShortcutService shortcutService = new ShortcutService();
				shortcutService.clearShortcutoperate_id(player.getPPk(), prop
						.getPropID());
			}
			propUseEffect.setPropType(PropType.CRUEALLHMP);
			propUseEffect.setIsEffected(true);
			propUseEffect.setEffectDisplay("��ʹ����"
					+ StringUtil.isoToGBK(propGroup.getPropName()) + ",Ѫ��������!");
			propUseEffect.setEffectValue("all");
		}
		if (prop.getPropOperate1().equals("2"))
		{
			if (player.getPMp() == player.getPMaxMp())
			{
				propUseEffect.setIsEffected(false);
				propUseEffect.setNoUseDisplay("���������Ѵ�����");
				return propUseEffect;
			}

			// �Ƴ�ʹ�õ���
			goodsService.removeProps(propGroup, use_num);
			// ��������
			role_info.getBasicInfo().updateMp(player.getPMaxMp());

			PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
			boolean propGroupNot = propGroupDao.getUserHasProp(player.getPPk(),
					prop.getPropID());
			if (!propGroupNot)
			{
				ShortcutService shortcutService = new ShortcutService();
				shortcutService.clearShortcutoperate_id(player.getPPk(), prop
						.getPropID());
			}
			propUseEffect.setPropType(PropType.CRUEALLHMP);
			propUseEffect.setIsEffected(true);
			propUseEffect.setEffectDisplay("��ʹ����"
					+ StringUtil.isoToGBK(propGroup.getPropName()) + ",����������!");
			propUseEffect.setEffectValue("all");
		}
		return propUseEffect;
	}

	/**
	 * ���ߵĻ���ʹ�������ж�,���ؿձ�ʾ��ʹ��
	 * 
	 * @param player
	 * @param propGroup
	 * @return
	 */
	public String isPropUseByBasicCondition(RoleEntity role_info, PropVO prop,
			int use_num)
	{
		StringBuffer result = new StringBuffer();
		if (role_info == null || prop == null)
		{
			logger.info("����playerΪ��");
			return result.toString();
		}

		// �����Ƿ����ʹ���ж�
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		int tatol_prop_num = propGroupDao.getPropNumByByPropID(role_info.getPPk(), prop.getPropID());

		// ���ݵ������ͣ��ж��Ƿ���ʹ��
		if (prop.getPropClass() == PropType.NORMAL
				|| prop.getPropClass() == PropType.EXONERATIVE)
		{
			result.append("��ʹ����Ʒ");
			return result.toString();
		}

		// �������״̬���ж��Ƿ���ʹ��
		// todo:

		// ��ÿ��ʹ�ô�������
		if (prop.getPropUsedegree() > 0)
		{
			TimeControlService timeControlService = new TimeControlService();
			// �ж��Ƿ����
			if (!timeControlService.isUseable(
					role_info.getPPk(), prop.getPropID(),
					TimeControlService.PROP, prop.getPropUsedegree()))
			{
				result.append(prop.getPropName()).append("һ��ֻ��ʹ��").append(prop.getPropUsedegree()).append("��,�����Ѳ���ʹ����.");
				return result.toString();
			}
		}

		if (tatol_prop_num < use_num)
		{
			result.append("������������");
			return result.toString();
		}

		String[] grade_condition = prop.getPropReLevel().split(",");
		if (role_info.getBasicInfo().getGrade() < Integer.parseInt(grade_condition[0])
				|| role_info.getBasicInfo().getGrade() > Integer.parseInt(grade_condition[1]))
		{

			result.append("���ĵȼ���ʹ�õȼ�����");
			return result.toString();
		}

		if (prop.getPropSex() != 0 && role_info.getBasicInfo().getSex() != prop.getPropSex())
		{
			result.append("�����Ա𲻷�");
			return result.toString();
		}

		if (role_info.getTitleSet().isHaveByTitleStr(prop.getPropJob())==false )
		{
			result.append("�ƺŲ�����");
			return result.toString();
		}

		if (prop.getMarriage() != 0 && role_info.getBasicInfo().getMarried() != prop.getMarriage()) // �н��Ҫ��
		{
			result.append("�����������");
			return result.toString();
		}
		return null;
	}

	/** ���＼�����ʹ�� */
	private PropUseEffect usePETSKILLBOOK(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num, String pet_pk)
	{
		PetSkillCache petSkillCache = new PetSkillCache();
		PropUseEffect propUseEffect = new PropUseEffect();
		propUseEffect.setPropType(prop.getPropClass());
		PetSkillLevelUpDao dao = new PetSkillLevelUpDao();
		PetSkillLevelUpService ps = new PetSkillLevelUpService();
		PetService petService = new PetService();
		if (Integer.parseInt(prop.getPropOperate1()) != dao.getPetSkOne(Integer
				.parseInt(pet_pk))
				&& Integer.parseInt(prop.getPropOperate1()) != dao
						.getPetSkTwo(Integer.parseInt(pet_pk))
				&& Integer.parseInt(prop.getPropOperate1()) != dao
						.getPetSkThree(Integer.parseInt(pet_pk))
				&& Integer.parseInt(prop.getPropOperate1()) != dao
						.getPetSkFour(Integer.parseInt(pet_pk))
				&& Integer.parseInt(prop.getPropOperate1()) != dao
						.getPetSkFive(Integer.parseInt(pet_pk)))
		{
			if (ps.getPetGpBySk(Integer.parseInt(prop.getPropOperate1()),
					Integer.parseInt(pet_pk)) == false)
			{
				propUseEffect.setPropType(PropType.PETSKILLBOOK);
				propUseEffect.setIsEffected(false);
				propUseEffect.setNoUseDisplay("���ĳ��ﲻ��ѧϰ�ü���");
				return propUseEffect;
			}
			if (dao.getPetGradeByPetpk(Integer.parseInt(pet_pk)) < dao
					.getPetGrade(Integer.parseInt(prop.getPropOperate1())))
			{
				propUseEffect.setPropType(PropType.PETSKILLBOOK);
				propUseEffect.setIsEffected(false);
				propUseEffect.setNoUseDisplay("���ĳ��ﻹδ�ﵽѧϰ�ü��ܵĵȼ�");
				return propUseEffect;

			}
			if (petSkillCache.getPetSkLevel(Integer.parseInt(prop
					.getPropOperate1())) > 1
					&& Integer.parseInt(prop.getPropOperate1()) != ps
							.getNextSkId(dao.getPetSkOne(Integer
									.parseInt(pet_pk)))
					&& Integer.parseInt(prop.getPropOperate1()) != ps
							.getNextSkId(dao.getPetSkTwo(Integer
									.parseInt(pet_pk)))
					&& Integer.parseInt(prop.getPropOperate1()) != ps
							.getNextSkId(dao.getPetSkThree(Integer
									.parseInt(pet_pk)))
					&& Integer.parseInt(prop.getPropOperate1()) != ps
							.getNextSkId(dao.getPetSkFour(Integer
									.parseInt(pet_pk)))
					&& Integer.parseInt(prop.getPropOperate1()) != ps
							.getNextSkId(dao.getPetSkFive(Integer
									.parseInt(pet_pk))))
			{

				propUseEffect.setPropType(PropType.PETSKILLBOOK);
				propUseEffect.setIsEffected(false);
				propUseEffect.setNoUseDisplay("������ļ��ܵȼ���δ�ﵽҪ��");
				return propUseEffect;
			}
			else
			{
				if (petSkillCache.getPetSkLevel(Integer.parseInt(prop
						.getPropOperate1())) > 1)
				{
					String intosk = ps.petAddSkill(Integer.parseInt(pet_pk),
							Integer.parseInt(prop.getPropOperate1()));
					GoodsService goodsService = new GoodsService();
					goodsService.removeProps(propGroup, use_num);
					propUseEffect.setPropType(PropType.PETSKILLBOOK);
					propUseEffect.setIsEffected(true);
					propUseEffect.setEffectDisplay(intosk);

					petService.addSkillFlag(pet_pk);
					return propUseEffect;
				}
				else
				{
					String delsk = ps.petSkillForget(Integer.parseInt(pet_pk));
					String intosk = ps.petAddSkill(Integer.parseInt(pet_pk),
							Integer.parseInt(prop.getPropOperate1()));
					GoodsService goodsService = new GoodsService();
					goodsService.removeProps(propGroup, use_num);
					propUseEffect.setPropType(PropType.PETSKILLBOOK);
					propUseEffect.setIsEffected(true);
					propUseEffect.setEffectDisplay(intosk + " " + delsk);

					petService.addSkillFlag(pet_pk);
					return propUseEffect;
				}
			}
		}
		else
		{
			propUseEffect.setPropType(PropType.PETSKILLBOOK);
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("���ĳ����Ѿ�ѧϰ���ü���");
			return propUseEffect;

		}
	}

	/** ʹ��������� */
	private PropUseEffect useLiveSkillBook(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();
		propUseEffect.setPropType(prop.getPropClass());

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		int sk_id1 = Integer.parseInt(prop.getPropOperate1());// ��һ�����ܵ�id
		int sk_id2 = Integer.parseInt(prop.getPropOperate2());// ѧϰ���ܵ�id
		SkillUpService sus = new SkillUpService();
		if (sus.isPlayerHaverThisSkill(role_info.getBasicInfo().getPPk(),
				sk_id1) == false)
		{
			propUseEffect.setPropType(PropType.LIVESKILLBOOK);
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("����û��ѧϰ�͵ȼ�����!");
			return propUseEffect;
		}
		else
		{
			if (sus.ifAddSkill(sk_id1, role_info.getBasicInfo().getPPk()) == true)
			{
				// �Ƴ�ʹ�õ���
				GoodsService goodsService = new GoodsService();
				goodsService.removeProps(propGroup, use_num);
				// ѧϰ�ü���
				sus.studyLiveSkill(role_info.getBasicInfo().getPPk(), sk_id1,
						sk_id2);
				propUseEffect.setPropType(PropType.LIVESKILLBOOK);
				propUseEffect.setIsEffected(true);
				propUseEffect.setEffectDisplay("�ɹ�ѧϰ!");
				return propUseEffect;
			}
			else
			{
				propUseEffect.setPropType(PropType.LIVESKILLBOOK);
				propUseEffect.setIsEffected(false);
				propUseEffect.setNoUseDisplay("����������û�дﵽҪ��!");
				return propUseEffect;
			}
		}
	}

	/** ʹ���䷽�� */
	private PropUseEffect useSynthesizeBook(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();
		propUseEffect.setPropType(prop.getPropClass());

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		int s_id = Integer.parseInt(prop.getPropOperate1());// ѧϰ�䷽��id
		PlayerSynthesizeService pss = new PlayerSynthesizeService();
		if (pss.isHaveSynthesize(role_info.getBasicInfo().getPPk(), s_id) == true)
		{
			// �Ƴ�ʹ�õ���
			GoodsService goodsService = new GoodsService();
			goodsService.removeProps(propGroup, use_num);
			// ѧϰ���䷽

			propUseEffect.setPropType(PropType.SYNTHESIZEBOOK);
			propUseEffect.setIsEffected(true);
			propUseEffect.setEffectDisplay("���Ѿ�ѧ����䷽!");
			return propUseEffect;
		}
		else
		{
			propUseEffect.setPropType(PropType.SYNTHESIZEBOOK);
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("���Ѿ�ѧ�����䷽!");
			return propUseEffect;
		}
	}

	/** ʹ��Ԫ������ */
	private PropUseEffect useYuanbaoBox(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();
		propUseEffect.setPropType(prop.getPropClass());

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		EconomyService es = new EconomyService();
		int yuanbao = Integer.parseInt(prop.getPropOperate1());// Ԫ������
		es.addYuanbao(role_info.getBasicInfo().getPPk(), role_info
				.getBasicInfo().getUPk(), yuanbao, StatisticsType.BAOXIANG);
		// �Ƴ�ʹ�õ���
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);

		propUseEffect.setPropType(PropType.GET_YUANBAO_BOX);
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay("������ˡ�"+GameConfig.getYuanbaoName()+"����" + yuanbao + "!");
		return propUseEffect;
	}

	/**
	 * ���ﵰ����ʹ��
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect usePetEggGuding(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// �����Ƿ����ʹ���ж�
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		int p_pk = role_info.getBasicInfo().getPPk();

		String pet_name = "";

		GoodsService goodsService = new GoodsService();
		PetService petService = new PetService();
		PetEggService petEggService = new PetEggService();

		int pet_num = petService.getNumOfPet(p_pk);
		if (pet_num > 5)
		{
			resutl = "�������������";
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		else
		{
			// �Ƴ�ʹ�õ���
			goodsService.removeProps(propGroup, use_num);
			// ���ɳ���
			pet_name = petEggService.creatPetByEgg(prop, p_pk);
		}

		propUseEffect.setPropType(PropType.PET_EGG_GUDING);
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay("��ʹ����" + propGroup.getPropName()
				+ ",�����" + StringUtil.isoToGBK(pet_name));
		propUseEffect.setEffectValue("");
		return propUseEffect;
	}

	/**
	 * ��PK ����
	 * 
	 * @param player
	 * @param propGroup
	 * @param prop
	 * @param use_num
	 * @return
	 */
	private PropUseEffect useAVOIDPKPROP(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************�����Ƿ����ʹ���жϽ���**************//

		// ��ȡ��PK���ߵ�ʹ��ʱ�� ���Ӽ���
		String propTime = prop.getPropOperate1();
		// ��õ�ǰʱ��
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String beginTime = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
		TimeShow timeShow = new TimeShow();
		String endTime = timeShow.endTime(Integer.parseInt(propTime));// ȡ�õ�ǰʱ�伸����֮���ʱ��

		AvoidPkPropService avoidPkPropService = new AvoidPkPropService();
		avoidPkPropService.addAvoidPkProp(role_info.getBasicInfo().getPPk(),
				beginTime, endTime);
		GoodsService goodsService = new GoodsService();
		// �Ƴ�ʹ�õ���
		goodsService.removeProps(propGroup, use_num);
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay("��ʹ����"
				+ StringUtil.isoToGBK(propGroup.getPropName()) + "<br/>������"
				+ propTime + "�����ڲ�����ɱ��");
		return propUseEffect;
	}

	// ��������
	private PropUseEffect useARMBOX(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// �����Ƿ����ʹ���ж�
		// ����ʹ�û����ж�
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		int p_pk = role_info.getBasicInfo().getPPk();
		PlayerService playerService = new PlayerService();
		PartInfoVO player = playerService.getPlayerByPpk(p_pk);
		String npc_id_bak[] = prop.getPropOperate1().split(",");
		String npc_id = prop.getPropOperate1();
		npc_id = npc_id_bak[0];
	/*	if (role_info.getBasicInfo().getSchool().equals("shaolin"))
		{
			npc_id = npc_id_bak[0];
		}
		if (role_info.getBasicInfo().getSchool().equals("gaibang"))
		{
			npc_id = npc_id_bak[1];
		}
		if (role_info.getBasicInfo().getSchool().equals("mingjiao"))
		{
			npc_id = npc_id_bak[2];
		}*/

		NpcService npcService = new NpcService();
		GoodsService goodsService = new GoodsService();

		// ���ԭ���ĵ�����
		role_info.getDropSet().clearDropItem();
		
		// ������Ʒ
		npcService.dropGoodsByJiangRareBox(Integer.parseInt(npc_id), player);

		// �õ�������
		List<DropGoodsVO> dropgoods = role_info.getDropSet().getList();
		// �жϰ���������
		if (goodsService.isEnoughWrapSpace(p_pk, dropgoods.size()))
		{
			propUseEffect.setPropType(PropType.ARMBOX);
			propUseEffect.setIsEffected(true);

			String ss = "";
			String douhao = "";
			for (int i = 0; i < dropgoods.size(); i++)
			{
				DropGoodsVO dropGoods = (DropGoodsVO) dropgoods.get(i);
				if ((i + 1) != dropgoods.size())
				{
					douhao = ",";
				}
				goodsService.putGoodsToWrap(p_pk, dropGoods.getGoodsId(),
						dropGoods.getGoodsType(), dropGoods.getGoodsQuality(),
						dropGoods.getDropNum(),GameLogManager.G_BOX_DROP);
				ss += dropGoods.getGoodsName() + "x" + dropGoods.getDropNum()
						+ douhao;
				// ִ��ͳ��
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(dropGoods.getGoodsId(), dropGoods
						.getGoodsType(), dropGoods.getDropNum(),
						StatisticsType.DEDAO, StatisticsType.BAOXIANG, p_pk);

			}
			propUseEffect.setEffectDisplay(ss);
			// �Ƴ�ʹ�õ���
			goodsService.removeProps(propGroup, use_num);
		}
		else
		{
			role_info.getDropSet().clearDropItem();
			resutl = "���İ�����������!���������!";
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		return propUseEffect;
	}

}
