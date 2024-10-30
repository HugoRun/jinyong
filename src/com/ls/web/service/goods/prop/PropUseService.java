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
 * 功能:管理玩家道具的操作
 * 
 * @author 刘帅 3:54:58 PM
 */
public class PropUseService
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * 使用道具不指定使用哪个道具组
	 * 
	 * @param prop_id
	 *            道具id
	 * @param object
	 *            道具作用对象
	 * @param PropUseEffect
	 *            返回道具使用的效果
	 */
	public PropUseEffect usePropByPropID(RoleEntity role_info, int prop_id,
			int use_num,HttpServletRequest request, HttpServletResponse response)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();

		PropVO prop = PropCache.getPropById(prop_id); // 得到道具详细信息
		// 找到数量最少的道具组
		PlayerPropGroupVO propGroup = propGroupDao.getPropGroupByPropID(role_info.getBasicInfo().getPPk(), prop_id);
		if (propGroup == null)// 无道具
		{
			ShortcutService ShortcutService = new ShortcutService();
			ShortcutService.clearShortcutoperate_id(role_info.getBasicInfo()
					.getPPk(), prop_id);
			PropUseEffect propUseEffect = new PropUseEffect();
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("道具数量不够");
			return propUseEffect;
		}
		return useProp(role_info, prop, propGroup, use_num);
	}

	/**
	 * 使用pg_id组的道具
	 * 
	 * @param p_pk
	 * @param prop_id
	 * @param pg_pk
	 *            为-1时，表示不指定使用使用哪个道具组的道具；
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
	 * 使用pg_id组的道具
	 * 
	 * @param p_pk
	 * @param prop_id
	 * @param pg_pk
	 *            为-1时，表示不指定使用使用哪个道具组的道具；
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
//		PropVO prop = PropCache.getPropById(propID); // 得到道具详细信息
//		PropUseEffect propUseEffect = new PropUseEffect();
//
//		// 得到道具功能控制字段
//		String title_id = prop.getPropOperate1();
//
//		TitleInfoDao titleInfoDao = new TitleInfoDao();
//		TitleInfoVO titleInfo = titleInfoDao.getByTilteId(title_id);
//
//		if (player.getPGrade() >= titleInfo.getTitleLevelDown())// 判断是否转过职
//		{
//			resutl = "您已转过职,不能再使用了。";
//			propUseEffect.setIsEffected(false);
//			propUseEffect.setNoUseDisplay(resutl);
//			return resutl;
//		}
//		// *************道具使用效果*********************//
//		// 在称谓转变时查询系统消息控制表，发送合适的信息
//		SystemInfoService systemInfoService = new SystemInfoService();
//		systemInfoService.sendSystemInfoByTitleInfo(p_pk, title_id);
//		role_info.getBasicInfo().updateSchool(titleInfo.getSchoolId());// 拜师
//		growService.upgrade(player, role_info);
//		resutl = "您已升到了" + player.getPGrade() + "级";
//		return resutl;
//	}

	/**
	 * 使用pg_id组的道具宠物道具使用
	 * 
	 * @param p_pk
	 * @param prop_id
	 * @param pg_pk
	 *            为-1时，表示不指定使用使用哪个道具组的道具；
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
	 * 使用道具
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
			logger.info("参数错误");
			return null;
		}

		PropUseEffect propUseEffect = null;// 使用效果

		// 没有指定数量
		if (use_num <= 0)
		{
			use_num = 1;
		}

		logger.info("propClass=" + prop.getPropClass());
		switch (prop.getPropClass())
		{
			case PropType.ADDHP:// 加血道具
				propUseEffect = useADDHP(role_info, propGroup, prop, use_num);
				break;
			case PropType.ADDMP:// 加蓝道具
				propUseEffect = useADDMP(role_info, propGroup, prop, use_num);
				break;
			case PropType.CRUEALLHMP:// 加血道具
				propUseEffect = useADDHMP(role_info, propGroup, prop, use_num);
				break;
			case PropType.ZHUANZHI:// 转职道具
				propUseEffect = useZHUANZHI(role_info, propGroup, prop, use_num);
				break;
			case PropType.GOBACKCITY:// 回城道具使用
				propUseEffect = useGOBACKCITY(role_info, propGroup, prop,
						use_num);
				break;
			case PropType.Carry:// 回城道具使用
				propUseEffect = useCarry(role_info, propGroup, prop, use_num);
				break;
			case PropType.QUIZ:// 答题道具
				propUseEffect = useQUIZ(role_info, propGroup, prop, use_num);
				break;
			case PropType.CONJURE:// 召唤道具
				propUseEffect = useCONJURE(role_info, propGroup, prop, use_num);
				break;
			case PropType.MARKUP:// 标记道具
				propUseEffect = useMARKUP(role_info, propGroup, prop, use_num);
				break;
			case PropType.AVOIDPKPROP:// 免PK道具
				propUseEffect = useAVOIDPKPROP(role_info, propGroup, prop,
						use_num);
				break;
			/*case PropType.ROLE_BEOFF_EXP:// 这里是直接给玩家家道具时间
				propUseEffect = useRoleBeoffExp(role_info, propGroup, prop,
						use_num);
				break;*/
			// case PropType.PETSINEW:// 宠物回复体力
			// propUseEffect = petSINEW(player, propGroup, prop, use_num);
			// break;
			// case PropType.PETLONGE:// 宠物回复寿命
			// propUseEffect = petLONGE(player, propGroup, prop, use_num);
			// break;

			case PropType.SKILLBOOK:// 学习技能
				propUseEffect = useSKILLBOOK(role_info, propGroup, prop,
						use_num);
				break;
			case PropType.HONOUR:// 称号
				propUseEffect = useHONOUR(role_info, propGroup, prop, use_num);
				break;
			case PropType.VIP:// 成为VIP的道具
				propUseEffect = useVIP(role_info, propGroup, prop, use_num);
				break;
			case PropType.BUFF:// 使用道具buff
				propUseEffect = useBUFF(role_info, propGroup, prop, use_num);
				break;
			case PropType.INIT_PET:// 洗宠物道具
				propUseEffect = useINITPET(role_info, propGroup, prop, use_num);
				break;
			case PropType.PET_EGG:// 宠物蛋道具
				propUseEffect = usePETEGG(role_info, propGroup, prop, use_num);
				break;
			case PropType.ACCEPT_SPECIFY_TASK:// 接收指定任务道具
				propUseEffect = acceptSpesifyTask(role_info, propGroup, prop,
						use_num);
				break;
			case PropType.ACCEPT_TASK_FROM_LIST:// 从任务列表接收任务
				propUseEffect = acceptTaskFromList(role_info, propGroup, prop,
						use_num);
				break;
			case PropType.RARE_BOX:// 宝箱道具
				propUseEffect = breakRareBox(role_info, propGroup, prop,
						use_num);
				break;
			case PropType.NORMAL:// 普通道具不能使用
				propUseEffect = useNORMAL(role_info, propGroup, prop, use_num);
				break;
			case PropType.BOX_CURE: // 捆装药品
				propUseEffect = useBoxCure(role_info, propGroup, prop, use_num);
				break;
			case PropType.GEI_RARE_BOX: // 发奖宝箱
				propUseEffect = useGeiBoxCure(role_info, propGroup, prop,
						use_num);

				break;
			case PropType.OUTPUBLISH: // 某时间内死亡无惩罚道具
				propUseEffect = useDeadOutOfJinYang(role_info, propGroup, prop,
						use_num);

				break;
			case PropType.LIVESKILLBOOK: // 生活技能书的使用
				propUseEffect = useLiveSkillBook(role_info, propGroup, prop,
						use_num);

				break;
			case PropType.SYNTHESIZEBOOK: // 配方书的使用
				propUseEffect = useSynthesizeBook(role_info, propGroup, prop,
						use_num);

				break;
			case PropType.REDUCEPKVALUE: // 减罪恶值道具
				propUseEffect = useReducePkValue(role_info, propGroup, prop,
						use_num);

				break;

			case PropType.GET_YUANBAO_BOX: // 掉落元宝的宝箱
				propUseEffect = useYuanbaoBox(role_info, propGroup, prop,
						use_num);

				break;
			case PropType.PET_EGG_GUDING: // 写死宠物蛋
				propUseEffect = usePetEggGuding(role_info, propGroup, prop,
						use_num);

				break;
			case PropType.BOOK: // 书籍类道具
				propUseEffect = useBook(role_info, propGroup, prop, use_num);

				break;
			case PropType.XINYINDU: // 心印符道具
				propUseEffect = useXInyinfu(role_info, propGroup, prop, use_num);

				break;
			case PropType.BROTHERFU: // 兄弟情深符
				propUseEffect = useBrotherfu(role_info, propGroup, prop,
						use_num);

				break;
			case PropType.MERRYFU: // 夫妻情深符
				propUseEffect = useMerryFu(role_info, propGroup, prop, use_num);

				break;
			case PropType.ADD_LOVE_DEAR:// 增加夫妻甜蜜值道具
				propUseEffect = useAddLoveDear(role_info, propGroup, prop,
						use_num);

				break;
			case PropType.ADD_DEAR:// 增加亲密度道具
				propUseEffect = useAddDear(role_info, propGroup, prop, use_num);

				break;
			case PropType.GOLD_BOX:// 黄金宝箱道具
				String noUseInfo = "请您使用金钥匙来打开宝箱!";
				propUseEffect = useDefaultProp(role_info, propGroup, prop,
						use_num, noUseInfo);

				break;
			case PropType.ARMBOX:// 武器宝箱
				propUseEffect = useARMBOX(role_info, propGroup, prop, use_num);
				break;
			/*case PropType.YINSHEN:// 隐身道具
				propUseEffect = useYinshen(role_info, propGroup, prop, use_num);
				break;*/
			/*case PropType.FAN_YINSHEN:// 反隐身符道具
				propUseEffect = useFanYinshen(role_info, propGroup, prop,
						use_num);
				break;*/
			/*case PropType.QIANLIYAN:// 千里眼
				propUseEffect = useQianliyan(role_info, propGroup, prop,
						use_num);
				break;*/
			/*case PropType.XIANHAI:// 陷害符
				propUseEffect = useXianhai(role_info, propGroup, prop, use_num);
				break;*/
			case PropType.COMPASS:// 指南针
				propUseEffect = useCompass(role_info, propGroup, prop, use_num);
				break;
			case PropType.MIJING_MAP:// 秘境地图
				propUseEffect = useMijing(role_info, propGroup, prop, use_num);
				break;
			case PropType.TIAOZHAN://挑战书
				propUseEffect = useTIAOZHAN(role_info, propGroup, prop, use_num);
				break;
			default:
				propUseEffect = useDefaultProp(role_info, propGroup, prop,
						use_num, null);
				break;

		}

		// 有每天使用次数限制
		if (propUseEffect != null && propUseEffect.getIsEffected()
				&& prop.getPropUsedegree() > 0)
		{
			TimeControlService timeControlService = new TimeControlService();
			// 更新道具使用状态
			timeControlService.updateControlInfo(role_info.getBasicInfo()
					.getPPk(), prop.getPropID(), TimeControlService.PROP);
		}

		return propUseEffect;
	}

	/**
	 * 使用挑战书
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

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
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
			propUseEffect.setNoUseDisplay("该道具无法在此地图使用");
			return propUseEffect;
		}
		int curState = role_info.getStateInfo().getCurState();
		if (!(curState == PlayerState.GENERAL
				|| curState == PlayerState.TRADE
				|| curState == PlayerState.GROUP || curState == PlayerState.TALK))
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("您目前无法使用该道具");
			return propUseEffect;
		}
		propUseEffect.setPropType(prop.getPropClass());
		propUseEffect.setIsEffected(true);
		return propUseEffect;
	}
	
	/**
	 * 使用秘境地图
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

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
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
			propUseEffect.setNoUseDisplay("对不起，该道具不能使用");
			return propUseEffect;
		}
		int state = role_info.getStateInfo().getCurState();
		if (state != PlayerState.GENERAL && state != PlayerState.GROUP
				&& state != PlayerState.VIEWWRAP && state != PlayerState.TALK)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("对不起，该道具不能使用");
			return propUseEffect;
		}
		// 移除使用道具
		if (prop.getPropOperate1() == null
				|| prop.getPropOperate1().trim().equals(""))
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("对不起，数据错误，请通知GM");
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
						uif.setResult("您的队友要将您传送到秘境，您是否决定传送？");
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
	 * 使用指南针
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

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
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
			propUseEffect.setNoUseDisplay("您无法在迷宫外使用该物品。");
			return propUseEffect;
		}
		Compass com = new CompassService().findById(scene.getSceneID());
		if (com == null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("对不起，数据错误，请通知GM");
			return propUseEffect;
		}
		// 移除使用道具
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);
		propUseEffect.setPropType(prop.getPropClass());
		propUseEffect.setIsEffected(true);
		propUseEffect.setNoUseDisplay("根据指南针的指示，您接下来的行进路线是：" + com.getDes()
				+ "移动。");
		return propUseEffect;
	}

	/**
	 * 使用隐身符
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
//		// ************道具是否可以使用判断 **************//
//		// 道具使用基本判断
//		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
//		if (resutl != null)
//		{
//			propUseEffect.setIsEffected(false);
//			propUseEffect.setNoUseDisplay(resutl);
//			return propUseEffect;
//		}
//		// ************道具是否可以使用判断结束**************//
//		if (role_info.getBasicInfo().getPPk() != LangjunConstants.LANGJUN_PPK)
//		{
//			propUseEffect.setIsEffected(false);
//			propUseEffect.setNoUseDisplay("对不起，您不是千面郎君，因此不能使用该道具");
//			return propUseEffect;
//		}
////		GoodsService goodsService = new GoodsService();
////		goodsService.removeProps(propGroup, use_num);
//		// *************道具使用效果*********************//
//		// GoodsService goodsService = new GoodsService();
//		// goodsService.removeProps(propGroup, use_num);
//		// 移除使用道具
//		propUseEffect.setPropType(prop.getPropClass());
//		propUseEffect.setIsEffected(true);
//		propUseEffect.setNoUseDisplay("你使用了" + prop.getPropName()
//				+ "，在接下来的20秒内其他玩家将无法看见你的行踪");
//		return propUseEffect;
//	}

//	/**
//	 * 使用隐身符
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
//		// ************道具是否可以使用判断 **************//
//		// 道具使用基本判断
//		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
//		if (resutl != null)
//		{
//			propUseEffect.setIsEffected(false);
//			propUseEffect.setNoUseDisplay(resutl);
//			return propUseEffect;
//		}
//		// ************道具是否可以使用判断结束**************//
//		if (role_info.getBasicInfo().getPPk() != LangjunConstants.LANGJUN_PPK)
//		{
//			propUseEffect.setIsEffected(false);
//			propUseEffect.setNoUseDisplay("对不起，您不是千面郎君，因此不能使用该道具");
//			return propUseEffect;
//		}
//		// *************道具使用效果*********************//
//		GoodsService goodsService = new GoodsService();
//		goodsService.removeProps(propGroup, use_num);
//		LangjunConstants.LANGJUN_YINSHEN = DateUtil.getDate(new Date());
//		// 移除使用道具
//		propUseEffect.setPropType(prop.getPropClass());
//		propUseEffect.setIsEffected(true);
//		propUseEffect.setNoUseDisplay("你使用了" + prop.getPropName() + "，在接下来的"
//				+ LangjunConstants.YINSHEN_CANNOTVIEW_SECOND
//				+ "秒内其他玩家将无法看见你的行踪");
//		new SystemInfoService()
//				.insertSystemInfoBySystem("【千面郎君】使用了【隐身符】后暂时消失在黑暗之中，不见了踪影！");
//		return propUseEffect;
//	}

//	/**
//	 * 使用反隐身符
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
//		// ************道具是否可以使用判断 **************//
//		// 道具使用基本判断
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
//			propUseEffect.setNoUseDisplay("数据错误，请联系GM");
//			return propUseEffect;
//		}
//		if (LangjunConstants.LANGJUN_PPK == 0)
//		{
//			propUseEffect.setIsEffected(false);
//			propUseEffect.setNoUseDisplay("当前未出现千面郎君,该道具无法使用");
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
//			propUseEffect.setNoUseDisplay("数据错误，请联系GM");
//			return propUseEffect;
//		}
//		GoodsService goodsService = new GoodsService();
//		goodsService.removeProps(propGroup, use_num);
//		// ************道具是否可以使用判断结束**************//
//		// *************道具使用效果*********************//
//		LangjunConstants.FANYINSHEN.put(role_info.getBasicInfo().getPPk(),
//				DateUtil.addSecond(second1));
//		// 移除使用道具
//		propUseEffect.setPropType(prop.getPropClass());
//		propUseEffect.setIsEffected(true);
//		propUseEffect.setNoUseDisplay("您现在可以在地图中找到处于隐身状态的【千面郎君】了，该效果持续"
//				+ second1 + "秒。");
//		return propUseEffect;
//	}

//	/**
//	 * 使用千里眼
//	 */
//	private PropUseEffect useQianliyan(RoleEntity role_info,
//			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
//	{
//		PropUseEffect propUseEffect = new PropUseEffect();
//
//		// ************道具是否可以使用判断 **************//
//		// 道具使用基本判断
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
//			propUseEffect.setNoUseDisplay("数据错误，请联系GM");
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
//			propUseEffect.setNoUseDisplay("数据错误，请联系GM");
//			return propUseEffect;
//		}
//		GoodsService goodsService = new GoodsService();
//		goodsService.removeProps(propGroup, use_num);
//		// ************道具是否可以使用判断结束**************//
//		// *************道具使用效果*********************//
//		LangjunConstants.QIANLIYAN.put(role_info.getBasicInfo().getPPk(),
//				DateUtil.addSecond(second1));
//		// 移除使用道具
//		propUseEffect.setPropType(prop.getPropClass());
//		propUseEffect.setIsEffected(true);
//		propUseEffect.setNoUseDisplay("您现在可以随时看到【千面郎君】的藏身处了，该效果持续" + second
//				+ "秒。");
//		return propUseEffect;
//	}

	/**
	 * 增加夫妻甜蜜值道具的使用
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

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************道具是否可以使用判断结束**************//
		int count = new FriendService().getFriendNum(role_info.getBasicInfo()
				.getPPk());
		if (count <= 0)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("您还没有好友呢");
			return propUseEffect;
		}

		// *************道具使用效果*********************//

		// 移除使用道具
		// GoodsService goodsService = new GoodsService();
		// goodsService.removeProps(propGroup, use_num);
		propUseEffect.setPropType(prop.getPropClass());
		propUseEffect.setIsEffected(true);
		return propUseEffect;
	}

	/**
	 * 增加夫妻甜蜜值道具的使用
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

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************道具是否可以使用判断结束**************//
		List<FriendVO> list = new FriendDAO().isMerry(role_info.getBasicInfo()
				.getPPk()
				+ "");
		if (list == null || list.size() == 0)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("您还没有结婚");
			return propUseEffect;
		}

		// *************道具使用效果*********************//

		// 移除使用道具
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);
		propUseEffect.setPropType(prop.getPropClass());
		propUseEffect.setIsEffected(true);
		return propUseEffect;
	}

	/**
	 * 夫妻情深符道具的使用
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

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************道具是否可以使用判断结束**************//
		// List<FriendVO> list = new
		// FriendDAO().isMerry(role_info.getBasicInfo().getPPk()+"");
		// if(list==null||list.size()==0){
		// propUseEffect.setIsEffected(false);
		// propUseEffect.setNoUseDisplay("您还没有结婚");
		// return propUseEffect;
		// }

		List<FriendVO> list111 = new FriendService().findCanGetExp(role_info
				.getBasicInfo().getPPk(), 2, 0, 0);
		if (list111 == null || list111.size() == 0)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("没有经验可以领取");
			return propUseEffect;
		}
		if (role_info.getBasicInfo().getGrade() == 39||role_info.getBasicInfo().getGrade() == 59
				||role_info.getBasicInfo().getGrade() == 69
				||role_info.getBasicInfo().getGrade() == 79
				|| role_info.getBasicInfo().getGrade() == GameConfig
						.getGradeUpperLimit())
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("你目前在转职状态不能领取经验");
			return propUseEffect;
		}

		// *************道具使用效果*********************//

		// 移除使用道具
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);
		propUseEffect.setPropType(prop.getPropClass());
		propUseEffect.setIsEffected(true);
		return propUseEffect;
	}

	/**
	 * 兄弟情深符道具的使用
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

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************道具是否可以使用判断结束**************//
		int count = new FriendService().findCanGetExpCount(role_info
				.getBasicInfo().getPPk(), 1);
		if (count <= 0)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("没有经验可以领取");
			return propUseEffect;
		}
		if (role_info.getBasicInfo().getGrade() == 39
				|| role_info.getBasicInfo().getGrade() == GameConfig
						.getGradeUpperLimit())
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("你目前在转职状态不能领取经验");
			return propUseEffect;
		}

		propUseEffect.setPropType(prop.getPropClass());
		propUseEffect.setIsEffected(true);
		return propUseEffect;
	}

	/**
	 * 心印符道具的使用
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

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************道具是否可以使用判断结束**************//

		String cur_scene_id = role_info.getBasicInfo().getSceneId();

		int barea_point = 0;
		// 得到配偶中心点id
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
			propUseEffect.setNoUseDisplay("您的配偶当前不在线");
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

		// *************道具使用效果*********************//

		// 移除使用道具
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);
		role_info.getBasicInfo().updateSceneId(barea_point + "");// 更改玩家scene_id
		CompassService.removeMiJing(role_info.getBasicInfo().getPPk(),
				propGroup.getPropType());// 删除秘境地图
		propUseEffect.setPropType(prop.getPropClass());
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectValue(barea_point + "");
		propUseEffect.setNoUseDisplay("您使用了" + prop.getPropName() + "传送到"
				+ (re.getBasicInfo().getSex() == 1 ? "老公" : "老婆")
				+ re.getBasicInfo().getName() + "的所在地!");
		return propUseEffect;
	}

	/**
	 * 使用 书籍类道具
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

		// 道具是否可以使用判断
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// 获得秘籍ID
		String infoIdString = prop.getPropOperate1();

		// 获得是否删除标志,1为删除,2为不删除
		String deleteflag = prop.getPropDrop();

		MiJiDao mijiDao = new MiJiDao();
		MiJiVO miJiVO = mijiDao.getMiJiById(Integer.parseInt(infoIdString));
		if (miJiVO != null)
		{
			if (deleteflag.equals("1"))
			{
				// 移除使用道具
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
			propUseEffect.setEffectDisplay("此秘籍暂时空白!");
			return propUseEffect;

		}
	}

	/**
	 * 使用 减罪恶值道具
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

		// 道具是否可以使用判断
		// 道具使用基本判断
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
	 * 使用其他道具
	 */
	private PropUseEffect useDefaultProp(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num,
			String noUseDisplay)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// 道具是否可以使用判断
		// 道具使用基本判断
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
			propUseEffect.setNoUseDisplay("非使用物品!");
		}
		else
		{
			propUseEffect.setNoUseDisplay(noUseDisplay);
		}

		return propUseEffect;
	}

	/**
	 * // 某时间内死亡无惩罚道具
	 */
	private PropUseEffect useDeadOutOfJinYang(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{

		PropUseEffect propUseEffect = new PropUseEffect();

		// 道具是否可以使用判断
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		// 得到道具功能控制字段,第一功能字段为有效时间
		String effect_time = prop.getPropOperate1();
		// 在这儿,第二个参数应该是id, 实际上是道具的种类, 如果有可使用道具id也等于此种类的数值的话,可能会冲突
		TimeControlService timeService = new TimeControlService();
		timeService.updateControlInfoByTime(role_info.getBasicInfo().getPPk(),
				prop.getPropClass(), TimeControlService.ANOTHERPROP, Integer
						.valueOf(effect_time));

		// 加到buff表中
		BuffEffectService buffEffectService = new BuffEffectService();
		buffEffectService.createBuffEffect(role_info.getBasicInfo().getPPk(),
				BuffSystem.PLAYER, Integer.parseInt(prop.getPropOperate2()),
				Integer.parseInt(effect_time));

		// 移除使用道具
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);

		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay("您使用了" + prop.getPropName() + ","
				+ effect_time + "分钟内死亡不损失经验！");
		return propUseEffect;
	}

	/**
	 * 使用发奖宝箱道具.
	 */
	private PropUseEffect useGeiBoxCure(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// 道具是否可以使用判断
		// 道具使用基本判断
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

		// 得到道具功能控制字段
		String npc_id = prop.getPropOperate1();

		NpcService npcService = new NpcService();
		GoodsService goodsService = new GoodsService();

		// 清楚原来的掉落物
		role_info.getDropSet().clearDropItem();
		
		// 掉落物品
		npcService.dropGoodsByJiangRareBox(Integer.parseInt(npc_id), player);

		// 得到掉落物
		List<DropGoodsVO> dropgoods = role_info.getDropSet().getList();
		// 判断包裹各数否够
		if (goodsService.isEnoughWrapSpace(p_pk, dropgoods.size()))
		{// 够了

			// 掉落金钱
			// NpcDao npcdao = new NpcDao();
			// NpcVO npcvo = npcdao.getNpcById(Integer.parseInt(npc_id));
			//			
			NpcVO npcvo = NpcCache.getById(Integer.parseInt(npc_id));
			int dropmoney = npcvo.getDropMoney();
			if (dropmoney != 0)
			{

				role_info.getBasicInfo().addCopper(dropmoney);// 给角色增加金钱
				// 执行统计
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(6, StatisticsType.MONEY, dropmoney,
						StatisticsType.DEDAO, StatisticsType.BAOXIANG, p_pk);

			}
			if (npcvo.getExp() != 0)
			{
				// partInfoDao.updateExperience(p_pk,
				// Integer.parseInt(role_info.getBasicInfo().getCurExp())
				// + npcvo.getExp());

				// 监控
				LogService logService = new LogService();
				logService.recordExpLog(role_info.getBasicInfo().getPPk(),
						role_info.getBasicInfo().getName(), role_info
								.getBasicInfo().getCurExp(), npcvo.getExp()
								+ "", "宝箱得到");

				role_info.getBasicInfo().updateAddCurExp(npcvo.getExp());

			}
			DropExpMoneyVO dropExpMoney = new DropExpMoneyVO();
			dropExpMoney.setDropMoney(dropmoney);
			dropExpMoney.setDropExp(npcvo.getExp());
			role_info.getDropSet().addExpAndMoney(dropExpMoney);

			propUseEffect.setPropType(PropType.GEI_RARE_BOX);
			propUseEffect.setIsEffected(true);
			// propUseEffect.setEffectDisplay("发奖宝箱");
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
				// 执行统计
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(dropGoods.getGoodsId(), dropGoods
						.getGoodsType(), dropGoods.getDropNum(),
						StatisticsType.DEDAO, StatisticsType.BAOXIANG, p_pk);

			}

			String hint = "";
			if (dropmoney != 0)
			{
				hint = "您获得了金钱" + MoneyUtil.changeCopperToStr(dropmoney)
						+ "!<br/>";
			}
			hint = hint + ss + "";
			propUseEffect.setEffectDisplay(hint);
			// 移除使用道具
			goodsService.removeProps(propGroup, use_num);
			role_info.getDropSet().clearDropItem();
			role_info.getDropSet().clearExpAndMoney();
			if(propGroup.getPropId() == 4228){
				SystemInfoService systemInfoService = new SystemInfoService();
				systemInfoService.insertSystemInfoBySystem(role_info.getBasicInfo().getName()+"打开了帮主回馈大礼包!");
			}
		}
		else
		{
			role_info.getDropSet().clearDropItem();
			resutl = "您的包裹格数不够!请清理包裹!";
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		return propUseEffect;
	}

	/**
	 * 使用捆装药品
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

		// 道具使用基本判断
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

		// 获得捆装药品剩余的存量
		int surplus = propGroupDao.getSurplus(player.getPPk(), propGroup
				.getPgPk(), SpecialNumber.KUNZHUANG);
		if (surplus == 0)
		{
			// 如果为零，则说明此捆装药品没有信息在特殊道具信息表中.并且将此捆装药品的绑定标志置为绑定状态.
			propGroupDao.insertSpecial(player.getPPk(), propGroup.getPgPk(),
					prop.getPropOperate2(), SpecialNumber.KUNZHUANG);
			propGroupDao.updatePropToBonding(propGroup.getPgPk());
			surplus = Integer.valueOf(prop.getPropOperate2());
		}
		logger.info("surplues=" + surplus);
		// 判断是加血的捆装药还是加内力的捆装药，1为加血的,2为加蓝的
		int box_type = Integer.valueOf(prop.getPropOperate3());
		if (box_type == 1)
		{
			// 道具的特有使用要求
			if (player.getPHp() == player.getPMaxHp())
			{
				propUseEffect.setIsEffected(false);
				propUseEffect.setNoUseDisplay("您的血量已达上限");
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

			logger.info("增加血量=" + add_hp);

			if (add_hp + player.getPHp() > player.getPMaxHp())
			{
				add_hp = player.getPMaxHp() - player.getPHp();
			}
			player.setPHp(player.getPHp() + add_hp);

			GoodsService goodsService = new GoodsService();

			// 当总血量少于每次加血量，且少于人物所需加血量时,移除使用道具，否则减少该道具的总可用回复量
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
			// 血量更新
			role_info.getBasicInfo().updateHp(player.getPHp());

			propUseEffect.setPropType(PropType.ADDHP);
			propUseEffect.setIsEffected(true);
			propUseEffect.setEffectDisplay("您使用了"
					+ StringUtil.isoToGBK(propGroup.getPropName()) + ",恢复了生命"
					+ add_hp + "点.");
			propUseEffect.setEffectValue("" + add_hp);

		}
		else
			if (box_type == 2)
			{
				if (player.getPMp() == player.getPMaxMp())
				{
					propUseEffect.setIsEffected(false);
					propUseEffect.setNoUseDisplay("您的内力已达上限");
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

				logger.info("增加气血=" + add_mp);

				if (add_mp + player.getPMp() > player.getPMaxMp())
				{
					add_mp = player.getPMaxMp() - player.getPMp();
				}
				player.setPMp(player.getPMp() + add_mp);

				GoodsService goodsService = new GoodsService();

				// 当总血量少于每次加血量，且少于人物所需加血量时,移除使用道具，否则减少该道具的总可用回复量
				if (surplus <= requieMp
						&& surplus <= Integer.parseInt(prop.getPropOperate1()))
				{
					goodsService.removeProps(propGroup, 1);
					logger.info("用完要换！");
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
				// 法力更新
				role_info.getBasicInfo().updateMp(player.getPMp());

				propUseEffect.setPropType(PropType.ADDMP);
				propUseEffect.setIsEffected(true);
				propUseEffect.setEffectDisplay("您使用了"
						+ StringUtil.isoToGBK(propGroup.getPropName())
						+ ",恢复了内力" + add_mp + "点.");
				propUseEffect.setEffectValue("" + add_mp);

			}
		logger.info("isEffected=" + propUseEffect.getIsEffected() + "药品描述="
				+ propUseEffect.getEffectDisplay());
		return propUseEffect;
	}

	/**
	 * 使用装备修复道具
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

		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, propGroup.getPropInfo(), 1);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		GoodsService goodsService = new GoodsService();
		
		int total_endure = Integer.parseInt(propGroup.getPropInfo().getPropOperate1());
		
		int left_endure = role_info.getEquipOnBody().maintainAllByEndure(total_endure);//修理装备
		String hint = null;
		if( left_endure<=0 )//修复身上全部装备
		{
			hint = "你使用" + propGroup.getPropInfo().getPropName()+ "修复身上装备全部耐久！";
		}
		else//修复部分装备
		{
			hint = "你使用"+propGroup.getPropInfo().getPropName()+"修复身上装备耐久" + total_endure + "点，还有" + left_endure+ "点耐久没有修复！";
		}
		goodsService.removeProps(propGroup, 1);
		propUseEffect.setIsEffected(false);
		propUseEffect.setNoUseDisplay(hint);
		return propUseEffect;
	}

	/**
	 * 使用道具宠物
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
			logger.info("参数错误");
			return null;
		}

		PropUseEffect propUseEffect = null;// 使用效果

		// 没有指定数量
		if (use_num <= 0)
		{
			use_num = 1;
		}

		switch (prop.getPropClass())
		{
			case PropType.PETSINEW:// 宠物回复体力
				propUseEffect = petSINEW(role_info, propGroup, prop, use_num,
						pet_pk);
				break;
			case PropType.PETLONGE:// 宠物回复寿命
				propUseEffect = petLONGE(role_info, propGroup, prop, use_num,
						pet_pk);
				break;
			case PropType.PETEXP:// 宠物经验道具
				propUseEffect = petExp(role_info, propGroup, prop, use_num,
						pet_pk);
				break;
			/** 宠物学习技能 */
			case PropType.PETSKILLBOOK:// 宠物学习技能
				propUseEffect = usePETSKILLBOOK(role_info, propGroup, prop,
						use_num, pet_pk);
				break;
		}

		// 更新出战宠物信息
		logger.info("更新出战宠物信息");
		RolePetInfo userPet = role_info.getRolePetInfo();
		userPet.initPet(Integer.parseInt(pet_pk), role_info.getBasicInfo()
				.getPPk());

		// 有每天使用次数限制
		if (propUseEffect.getIsEffected() && prop.getPropUsedegree() > 0)
		{
			TimeControlService timeControlService = new TimeControlService();
			// 更新道具使用状态
			timeControlService.updateControlInfo(role_info.getBasicInfo()
					.getPPk(), prop.getPropID(), TimeControlService.PROP);
		}
		return propUseEffect;
	}

	/**
	 * 普通道具
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

		// 道具是否可以使用判断
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		propUseEffect.setPropType(PropType.NORMAL);
		propUseEffect.setIsEffected(false);
		propUseEffect.setEffectDisplay("普通道具");
		propUseEffect.setEffectValue("");
		return propUseEffect;
	}

	/**
	 * 砸宝箱
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

		// 道具是否可以使用判断
		// 道具使用基本判断
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

		// 得到道具功能控制字段
		String npc_id = prop.getPropOperate1();
		// 得到会掉出几个物品
		String dropNum = prop.getPropOperate2();

		NpcService npcService = new NpcService();
		GoodsService goodsService = new GoodsService();

		// 清楚原来的掉落物
		role_info.getDropSet().clearDropItem();
		
		// 掉落物品
		npcService.dropGoodsByRareBox(Integer.parseInt(npc_id), player, dropNum);

		// 得到掉落物
		List<DropGoodsVO> dropgoods = role_info.getDropSet().getList();

		// 判断包裹各数否够
		if (goodsService.isEnoughWrapSpace(player.getPPk(), dropgoods.size()))
		{// 够了

			// 掉落金钱
			// NpcDao npcdao = new NpcDao();
			// NpcVO npcvo = npcdao.getNpcById(Integer.parseInt(npc_id));

			NpcVO npcvo = NpcCache.getById(Integer.parseInt(npc_id));
			int money = npcvo.getDropMoney();
			if (money != 0)
			{
				role_info.getBasicInfo().addCopper(money);// 给角色增加金钱
				// 执行统计
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(6, StatisticsType.MONEY, money,
						StatisticsType.DEDAO, StatisticsType.BAOXIANG, player
								.getPPk());
				hint = "您获得了金钱" + MoneyUtil.changeCopperToStr(money) + "!<br/>";

			}
			if (npcvo.getExp() != 0)
			{
				// partInfoDao.updateExperience(player.getPPk(), Integer
				// .valueOf(player.getPExperience())
				// + npcvo.getExp());

				// 监控
				LogService logService = new LogService();
				logService.recordExpLog(role_info.getBasicInfo().getPPk(),
						role_info.getBasicInfo().getName(), role_info
								.getBasicInfo().getCurExp(), npcvo.getExp()
								+ "", "砸宝箱得到");

				role_info.getBasicInfo().updateAddCurExp(npcvo.getExp());
			}
			DropExpMoneyVO dropExpMoney = new DropExpMoneyVO();
			dropExpMoney.setDropMoney(money);
			dropExpMoney.setDropExp(npcvo.getExp());
			role_info.getDropSet().addExpAndMoney(dropExpMoney);

			propUseEffect.setPropType(PropType.GEI_RARE_BOX);
			propUseEffect.setIsEffected(true);
			// propUseEffect.setEffectDisplay("发奖宝箱");
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
				// 执行统计
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(dropGoods.getGoodsId(), dropGoods
						.getGoodsType(), dropGoods.getDropNum(),
						StatisticsType.DEDAO, StatisticsType.BAOXIANG, player
								.getPPk());
				if (hint != null)
				{
					if (i == 0)
					{
						hint = "获得了物品:" + ss + "";
					}
					else
					{
						hint = hint + ss + "";
					}
				}
				else
				{
					hint = "您获得了物品:" + ss + "";
				}
			}
			hint = hint + "<br/>";
			if(prop.getPropID() == 4092 || prop.getPropID() == 4093 || prop.getPropID() == 4094){
			String info = role_info.getBasicInfo().getName()+"点燃了璀璨夜空烟花时,意外得到了"+ss+"!";
		    SystemInfoService infoService = new SystemInfoService();
		    infoService.insertSystemInfoBySystem(info) ;
			}
			propUseEffect.setEffectDisplay(hint);
			// 移除使用道具
			goodsService.removeProps(propGroup, use_num);
			role_info.getDropSet().clearDropItem();
			role_info.getDropSet().clearExpAndMoney();
		}
		else
		{
			role_info.getDropSet().clearDropItem();
			resutl = "您的包裹格数不够!请清理包裹!";
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		/*
		 * propUseEffect.setPropType(PropType.RARE_BOX);
		 * propUseEffect.setIsEffected(true);
		 * propUseEffect.setEffectDisplay("砸宝箱");
		 * propUseEffect.setEffectValue("");
		 */
		return propUseEffect;
	}

	/**
	 * 从任务列表接收任务
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

		// 道具是否可以使用判断
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		GoodsService goodsService = new GoodsService();
		TaskSubService taskService = new TaskSubService();
		int task_type = 1;// 触发类型 1 道具触发任务 2 菜单触发任务
		// 接受任务
		String hint = taskService.accectTaskFromList(role_info, prop
				.getPropID(), propUseEffect, task_type);
		if (hint == null)
		{
			// 移除使用道具
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
		// propUseEffect.setEffectDisplay("您接收了任务");
		propUseEffect.setEffectValue("");
		return propUseEffect;
	}

	/**
	 * 接收指定任务道具
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

		// 道具是否可以使用判断
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		// 得到道具功能控制字段
		String task_id = prop.getPropOperate1();

		GoodsService goodsService = new GoodsService();
		TaskSubService taskService = new TaskSubService();

		// 移除使用道具
		goodsService.removeProps(propGroup, use_num);
		// 接受任务
		taskService.acceptTask(role_info, Integer.parseInt(task_id));

		propUseEffect.setPropType(PropType.ACCEPT_SPECIFY_TASK);
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay("您接收了指定任务");
		propUseEffect.setEffectValue("");
		return propUseEffect;
	}

	/**
	 * 宠物蛋道具使用
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

		// 道具是否可以使用判断
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		int p_pk = role_info.getBasicInfo().getPPk();

		// 得到道具功能控制字段
		String pet_id = prop.getPropOperate1();
		String pet_name = "";

		GoodsService goodsService = new GoodsService();
		PetService petService = new PetService();

		int pet_num = petService.getNumOfPet(p_pk);
		if (pet_num > 5)
		{
			resutl = "宠物的数量已满";
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		else
		{
			// 移除使用道具
			goodsService.removeProps(propGroup, use_num);
			// 生成宠物
			pet_name = petService.createPetByPetID(p_pk, Integer
					.parseInt(pet_id));
		}

		propUseEffect.setPropType(PropType.PET_EGG);
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay("您使用了" + propGroup.getPropName()
				+ ",获得了" + StringUtil.isoToGBK(pet_name));
		propUseEffect.setEffectValue("");
		return propUseEffect;
	}

	/**
	 * 洗宠物道具
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

		// 道具是否可以使用判断
		// 道具使用基本判断
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
	 * 使用buff道具
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

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************道具是否可以使用判断结束**************//

		int p_pk = role_info.getBasicInfo().getPPk();

		// 得到道具功能控制字段
		String buff_id_s = prop.getPropOperate1();
		String[] buff_id = null;
		if (buff_id_s != null && !buff_id_s.equals(""))
			buff_id = buff_id_s.split(",");

		// 删除身上的相同类型的buff
		// buffEffectService.deleteSameBuff(player, propGroup);

		// 移除使用道具
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);

		BuffMenuService buffMenuService = new BuffMenuService();
		// *************道具使用效果*********************//
		for (int i = 0; i < buff_id.length; i++)
		{
			buffMenuService.setBuffStatus(p_pk, Integer.parseInt(buff_id[i]));
		}

		propUseEffect.setPropType(PropType.BUFF);
		propUseEffect.setIsEffected(true);
		// propUseEffect.setEffectDisplay("buff效果生效");
		propUseEffect.setBuffType(buff_id);
		propUseEffect.setEffectDisplay(prop);
		return propUseEffect;
	}

	/**
	 * 使用技能书道具
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

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************道具是否可以使用判断结束**************//

		int p_pk = role_info.getBasicInfo().getPPk();

		SkillService skillService = new SkillService();
		String display = skillService.studySkillAll(p_pk, prop, propUseEffect);

		if (display.contains("您已经学习过")) // 还没有学此技能
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(display);
			return propUseEffect;
		}
		else
			if (display.contains("您还没有学习"))
			{
				propUseEffect.setIsEffected(false);
				propUseEffect.setNoUseDisplay(display);
				return propUseEffect;
			}
			else
				if (display.contains("熟练度"))
				{
					propUseEffect.setIsEffected(false);
					propUseEffect.setNoUseDisplay(display);
					return propUseEffect;
				}
				else
				{
					// 移除使用道具
					GoodsService goodsService = new GoodsService();
					goodsService.removeProps(propGroup, use_num);

					propUseEffect.setPropType(PropType.SKILLBOOK);
					propUseEffect.setIsEffected(true);
					propUseEffect.setEffectDisplay(display);
					return propUseEffect;
				}
	}

	/**
	 * 称号
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

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************道具是否可以使用判断结束**************//

		RoleTitleSet role_title = role_info.getTitleSet();
		

		// 得到道具功能控制字段
		String conditions = prop.getPropOperate1();// 得到称号的条件
		String t_id = prop.getPropOperate2();// 得到称号的ID
		
		TitleVO condi_title = TitleCache.getById(conditions);
		TitleVO new_title = TitleCache.getById(t_id);
		
		// 首先是否满足得到条件
		if ( condi_title!=null && role_title.isHaveByTId(condi_title) == false)//是否有条件称号
		{// 不满足条件
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("你必须先获得" + condi_title.getName()
					+ "称号后才能获得" + new_title.getName() + "称号");// 没有满足告诉什么话
			return propUseEffect;
		}
		
		
		String hint = role_info.getTitleSet().gainTitle(new_title);
		if( hint!=null )//获得称号失败
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(hint);
			return propUseEffect;
		}
		
		// 移除使用道具
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);
		propUseEffect.setIsEffected(false);
		propUseEffect.setNoUseDisplay("您获得了新的称号:" + new_title.getName());
		return propUseEffect;
	}

	/**
	 * 使用会员卡
	 */
	private PropUseEffect useVIP(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();
		propUseEffect.setPropType(prop.getPropClass());

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************道具是否可以使用判断结束**************//
		// 得到道具功能控制字段
		String vip_title_id = prop.getPropOperate2().trim();// VIP称号id
		TitleVO vip_title = TitleCache.getById(vip_title_id);
		
		// 首先判断是否可以直接换取一个会员
		String isGainNewVipTitle = role_info.getTitleSet().isGainNewVipTitle(vip_title);
		if (isGainNewVipTitle != null )
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(isGainNewVipTitle);
			return propUseEffect;
		}
		// 满足条件以后
		// 移除使用道具
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
	 * 宠物回复体力
	 * 
	 * @param prop
	 * @param object
	 */
	public PropUseEffect petSINEW(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num, String pet_pk)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// 道具是否可以使用判断
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		int p_pk = role_info.getBasicInfo().getPPk();

		/*
		 * //道具的特有使用要求 if( player.getPHp()==player.getPUpHp() ) {
		 * propUseEffect.setIsEffected(false);
		 * propUseEffect.setNoUseDisplay("您的血量已达上限"); return propUseEffect; }
		 */

		// 通过角色ID 取出角色拥有的宠物
		PetInfoDAO dao = new PetInfoDAO();
		PetInfoVO petInfoVO = (PetInfoVO) dao.getPetInfoshiyong(p_pk + "",
				pet_pk);
		if (petInfoVO == null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("您的宠物没有一个参战");
			return propUseEffect;
		}
		// 道具是否可以使用判断结束
		int add_hp = Integer.parseInt(prop.getPropOperate1());// 特殊字段 //
		// 取出的是宠物加体力的值
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
			propUseEffect.setEffectDisplay("您的宠物体力已经满了,不需要再使用.");
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
		// 移除使用道具
		goodsService.removeProps(propGroup, use_num);
		// 修改宠物体力
		dao.petFatigue(p_pk, petInfoVO.getPetPk(), petFatigue);

		propUseEffect.setPropType(PropType.ADDHP);
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay("您使用了" + propGroup.getPropName()
				+ ",您的宠物体力增加" + add_hp + "点");
		// 判断是否加慢 不了返回 显示继续加满
		if (petFatigue < 100)
		{
			int isPetFatigue = 1;
			propUseEffect.setIsPetFatigue(isPetFatigue);
		}
		propUseEffect.setEffectValue("" + add_hp);
		return propUseEffect;
	}

	/**
	 * 宠物经验道具
	 * 
	 * @param prop
	 * @param object
	 */
	public PropUseEffect petExp(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num, String pet_pk)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// 道具是否可以使用判断
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		/*
		 * //道具的特有使用要求 if( player.getPHp()==player.getPUpHp() ) {
		 * propUseEffect.setIsEffected(false);
		 * propUseEffect.setNoUseDisplay("您的血量已达上限"); return propUseEffect; }
		 */

		// 通过角色ID 取出角色拥有的宠物
		PetInfoDAO dao = new PetInfoDAO();
		PetInfoVO petInfoVO = (PetInfoVO) dao.getPetInfoshiyong(role_info
				.getBasicInfo().getPPk()
				+ "", pet_pk);
		if (petInfoVO == null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("您的宠物没有一个参战");
			return propUseEffect;
		}
		// 道具是否可以使用判断结束
		int add_hp = Integer.parseInt(prop.getPropOperate1());// 特殊字段 //所加的体力
		int exp = Integer.parseInt(prop.getPropOperate2());// 特殊字段 //所加的经验
		// 取出的是宠物加体力的值
		int petFatigue = 0;

		if (add_hp == 100)
		{
			petFatigue = 100;
		}
		//水军说玩家想用就让他用20091209
		/*if (petInfoVO.getPetFatigue() == 100)
		{
			petFatigue = (100 - petInfoVO.getPetFatigue())
					+ petInfoVO.getPetFatigue();
			propUseEffect.setPropType(PropType.ADDHP);
			propUseEffect.setIsEffected(true);
			propUseEffect.setEffectDisplay("您的宠物不能在继续喂食了.");
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

		// 修改宠物经验
		// 宠物成长
		HhjPetService petService = new HhjPetService();
		String pet_display = petService.getPetGrandirProp(petInfoVO.getPetPk(),
				role_info.getBasicInfo().getGrade(), exp);

		// 移除使用道具
		goodsService.removeProps(propGroup, use_num);
		// 修改宠物体力
		dao.petFatigue(role_info.getBasicInfo().getPPk(), petInfoVO.getPetPk(),
				petFatigue);

		propUseEffect.setPropType(PropType.ADDHP);
		propUseEffect.setIsEffected(true);
		// propUseEffect.setEffectDisplay("您使用了" + propGroup.getPropName()+
		// ",您的宠物体力增加" + exp + "点经验");
		propUseEffect.setEffectDisplay(pet_display);
		// 判断是否加慢 不了返回 显示继续加满
		if (petFatigue < 100)
		{
			int isPetFatigue = 1;
			propUseEffect.setIsPetFatigue(isPetFatigue);
		}
		propUseEffect.setEffectValue("" + add_hp);
		return propUseEffect;
	}

	/**
	 * 宠物回复寿命
	 * 
	 * @param prop
	 * @param object
	 */
	public PropUseEffect petLONGE(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num, String pet_pk)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// 道具是否可以使用判断
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}

		int p_pk = role_info.getBasicInfo().getPPk();

		// 通过角色ID 取出角色拥有的宠物
		PetInfoDAO dao = new PetInfoDAO();
		PetInfoVO petInfoVO = (PetInfoVO) dao.getPetInfoshiyong(p_pk + "",
				pet_pk);
		if (petInfoVO == null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("您的宠物没有一个参战");
			return propUseEffect;
		}
		// 道具是否可以使用判断结束
		int add_hp = Integer.parseInt(prop.getPropOperate1());// 特殊字段
		// 取出的是宠物加体力的值
		int petLonge = add_hp + petInfoVO.getPetLonge();
		// 判断宠物寿命上线\
		if (petInfoVO.getPetLonge() == petInfoVO.getPetLife())
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("您的宠物寿命已经到达上限了");
			return propUseEffect;
		}
		if (petLonge > petInfoVO.getPetLife())
		{
			petLonge = petInfoVO.getPetLife();
		}

		GoodsService goodsService = new GoodsService();
		/*
		 * //TODPO 取消宠物寿命使用限制 if (petInfoVO.getLongeNumber() ==
		 * petInfoVO.getLongeNumberOk()) { propUseEffect.setIsEffected(false);
		 * propUseEffect.setNoUseDisplay("已经超过该道具的使用次数了"); return propUseEffect; }
		 */
		PetInfoDAO petInfoDAO = new PetInfoDAO();
		int pet_longe = petInfoDAO.pet_longe(p_pk, Integer.parseInt(pet_pk));
		if (pet_longe == 0)
		{
			propUseEffect.setNoUseDisplay("您的宠物寿命为零 不能在使用药物增加寿命了！");
			return propUseEffect;
		}
		int longeNumberOk = petInfoVO.getLongeNumberOk() + 1;

		// 移除使用道具
		goodsService.removeProps(propGroup, use_num);
		// 修改宠物寿命
		dao.petLonge(p_pk, petInfoVO.getPetPk(), petLonge, longeNumberOk);

		propUseEffect.setPropType(PropType.ADDHP);
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay("您使用了" + propGroup.getPropName());
		propUseEffect.setEffectValue("" + add_hp);
		return propUseEffect;
	}

	/**
	 * 使用标记道具
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

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************道具是否可以使用判断结束**************//

		// 得到道具功能控制字段
		// 无需控制字段

		// *************道具使用效果*********************//
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
		if (isUse != 1)// 标记
		{
			if (roomService.isCarryedIn(current_scene_id) != null)// 不能传入的地点不能做标记
			{
				propUseEffect.setPropType(PropType.MARKUP);
				propUseEffect.setIsEffected(false);
				propUseEffect.setNoUseDisplay("此地点不能使用该道具");
				return propUseEffect;
			}

			if (scene_id == -1)// 没有标记
			{
				CoordinateVO coordinate = new CoordinateVO();
				coordinate.setPPk(p_pk);
				coordinate.setCoordinatePropId(prop.getPropID());
				coordinate.setCoordinate(current_scene_id);
				coordinateDao.add(coordinate);
			}
			else
			// 更新标记
			{
				coordinateDao.updateCoordinate(p_pk, prop.getPropID(),
						current_scene_id);
			}

			scene_id = current_scene_id;
			String sceneName = roomService.getName(scene_id);

			propUseEffect.setPropType(PropType.MARKUP);
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("已经在" + sceneName + "做了标记");
			return propUseEffect;
		}
		else
		// 使用
		{
			// 判断是否可以传送
			if (roomService.isCarryFromSceneAToSceneB(current_scene_id,
					scene_id) != null)
			{
				propUseEffect.setPropType(prop.getPropClass());
				propUseEffect.setIsEffected(false);
				// propUseEffect.setEffectValue("不能传送");
				propUseEffect.setEffectValue(current_scene_id + "");
				return propUseEffect;
			}
			else
			{
				// 移除使用道具
				GoodsService goodsService = new GoodsService();
				goodsService.removeProps(propGroup, use_num);
				int prop_num = goodsService.getPropNum(p_pk, prop.getPropID());
				if (prop_num == 0)// 标记道具为0时删除标记
				{
					// 删除标记
					coordinateDao.delete(p_pk, prop.getPropID());
				}
				else
				{
					coordinateDao.updateNoUsed(p_pk, prop.getPropID());
				}
				role_info.getBasicInfo().updateSceneId(scene_id + "");
				CompassService.removeMiJing(p_pk, propGroup.getPropType());// 删除秘境地图
			}
		}

		// 移除使用道具
		// 用标记道具标记时，不用移除道具

		propUseEffect.setPropType(PropType.MARKUP);
		propUseEffect.setIsEffected(true);
		scene_name = roomService.getName(scene_id);
		propUseEffect.setEffectDisplay("已经在" + scene_name + "做了标记");
		propUseEffect.setEffectValue("" + scene_id);
		return propUseEffect;
	}

	/**
	 * 使用召唤道具
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

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************道具是否可以使用判断结束**************//

		// 得到道具功能控制字段
		String condition = prop.getPropOperate1();

		// 移除使用道具
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);
		// *************道具使用效果*********************//
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
	 * 答题道具的使用
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

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************道具是否可以使用判断结束**************//

		// 得到道具功能控制字段
		String quizs_str = prop.getPropOperate1();// 取出题目范围字符串
		QuizService quizService = new QuizService();
		QuizVO quiz = quizService.getRandomQuizByConfine(quizs_str);// 随机得到一个题目

		// 判断是否可以放下题目所发道具或装备
		String goodstr = quiz.getAwardGoods();
		if (goodstr != null && !goodstr.equals("") && !goodstr.equals("null"))
		{
			String[] goodStrings = goodstr.split(";");
			if (role_info.getBasicInfo().getWrapSpare() < goodStrings.length)
			{
				propUseEffect.setIsEffected(false);
				propUseEffect.setNoUseDisplay("您的包裹空间不够!");
				return propUseEffect;
			}
		}

		// *************道具使用效果*********************//

		// 移除使用道具
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
	 * 回城道具的使用
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

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************道具是否可以使用判断结束**************//

		String cur_scene_id = role_info.getBasicInfo().getSceneId();

		String new_scene_id = prop.getPropOperate1();// 新的地点ID
		Pattern p = Pattern
				.compile(Expression.positive_integer_contain0_regexp);
		Matcher m = p.matcher(new_scene_id);
		boolean b = m.matches();
		if (b == false)
		{
			propUseEffect.setPropType(prop.getPropClass());
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("道具错误,请重新使用!");
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

		// *************道具使用效果*********************//

		// 移除使用道具
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);
		role_info.getBasicInfo().updateSceneId(new_scene_id + "");// 更改玩家scene_id
		CompassService.removeMiJing(role_info.getBasicInfo().getPPk(),
				propGroup.getPropType());// 删除秘境地图
		propUseEffect.setPropType(prop.getPropClass());
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectValue(new_scene_id + "");
		return propUseEffect;
	}

	// 传送道具
	private PropUseEffect useGOBACKCITY(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************道具是否可以使用判断结束**************//

		String cur_scene_id = role_info.getBasicInfo().getSceneId();

		int barea_point = 0;
		// 得到城市中心点id
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

		// *************道具使用效果*********************//

		// 移除使用道具
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);
		role_info.getBasicInfo().updateSceneId(barea_point + "");// 更改玩家scene_id

		propUseEffect.setPropType(prop.getPropClass());
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectValue(barea_point + "");
		return propUseEffect;
	}

	/**
	 * 使用转职道具
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

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************道具是否可以使用判断结束**************//
		PlayerService playerService = new PlayerService();
		PartInfoVO player = playerService.getPlayerByPpk(role_info.getBasicInfo().getPPk());
		RoleTitleSet role_title = role_info.getTitleSet();

		// 得到道具功能控制字段
		String conditions = prop.getPropOperate1();// 得到称号的条件
		String t_id = prop.getPropOperate2();// 得到称号的ID
		
		TitleVO condi_title = TitleCache.getById(conditions);
		TitleVO new_title = TitleCache.getById(t_id);
		
		// 判断是否达到下级经验
		if (Integer.parseInt(role_info.getBasicInfo().getCurExp().trim()) < Integer.parseInt(role_info.getBasicInfo().getNextGradeExp().trim()))
		{
			resutl = "您的经验不足,不能转职.";
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		
		// 首先是否满足得到条件
		if ( condi_title!=null && role_title.isHaveByTId(condi_title) == false)//是否有条件称号
		{// 不满足条件
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("你必须先获得" + condi_title.getName()
					+ "称号后才能转职");// 没有满足告诉什么话
			return propUseEffect;
		}
		
		// 判断是否已转过职
		if (role_title.isHaveByTId(new_title) == true)//是否有条件称号
		{// 不满足条件
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("您已转过职,不能再使用了.");// 没有满足告诉什么话
			return propUseEffect;
		}
		
		String hint = role_info.getTitleSet().gainTitle(new_title);
		if( hint!=null )//获得称号失败
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(hint);
			return propUseEffect;
		}

		// *************道具使用效果*********************//
		GoodsService goodsService = new GoodsService();
		// 移除使用道具
		goodsService.removeProps(propGroup, use_num);
		
		// 玩家升级
		growService.upgrade(player, role_info);
		propUseEffect.setTitle(t_id);
		propUseEffect.setPropType(PropType.ZHUANZHI);
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay("您使用了"
				+ StringUtil.isoToGBK(propGroup.getPropName()) + "<br/>您已升到了"
				+ player.getPGrade() + "级");
		return propUseEffect;
	}

	/**
	 * 使用加血道具
	 * 
	 * @param prop
	 * @param object
	 */
	private PropUseEffect useADDHP(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// 道具是否可以使用判断
		// 道具使用基本判断
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

		// 道具的特有使用要求
		if (player.getPHp() == player.getPMaxHp())
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("您的血量已达上限");
			return propUseEffect;
		}

		// 道具是否可以使用判断结束

		int add_hp = Integer.parseInt(prop.getPropOperate1());
		if (add_hp + player.getPHp() > player.getPMaxHp())
		{
			add_hp = player.getPMaxHp() - player.getPHp();
		}

		player.setPHp(player.getPHp() + add_hp);

		GoodsService goodsService = new GoodsService();

		// 移除使用道具
		goodsService.removeProps(propGroup, use_num);
		// 血量更新
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
		propUseEffect.setEffectDisplay("您使用了"
				+ StringUtil.isoToGBK(propGroup.getPropName()) + ",增加了"
				+ add_hp + "点血量!");
		propUseEffect.setEffectValue("" + add_hp);
		return propUseEffect;
	}

	/**
	 * 这里是直接给玩家家道具时间
	 * 
	 * @param prop
	 * @param object
	 */
//	private PropUseEffect useRoleBeoffExp(RoleEntity role_info,
//			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
//	{
//		PropUseEffect propUseEffect = new PropUseEffect();
//		propUseEffect.setPropType(prop.getPropClass());
//		// ************道具是否可以使用判断 **************//
//		// 道具使用基本判断
//		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
//		if (resutl != null)
//		{
//			propUseEffect.setIsEffected(false);
//			propUseEffect.setNoUseDisplay(resutl);
//			return propUseEffect;
//		}
//		// ************道具是否可以使用判断结束**************//
//		int p_pk = role_info.getBasicInfo().getPPk();
//		// 得到道具功能控制字段
//		RoleBeOffService roleBeOffService = new RoleBeOffService();
//		String hint = roleBeOffService.addroleBeOffExpOfPropTime(p_pk, prop
//				.getPropID(), use_num + "");
//		// 移除使用道具
//		GoodsService goodsService = new GoodsService();
//		propUseEffect.setPropType(PropType.ROLE_BEOFF_EXP);
//		goodsService.removeProps(propGroup, use_num);
//		propUseEffect.setIsEffected(false);
//		propUseEffect.setNoUseDisplay(hint);
//		return propUseEffect;
//	}

	/**
	 * 使用加蓝道具
	 * 
	 * @param prop
	 * @param object
	 */
	private PropUseEffect useADDMP(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// 道具是否可以使用判断
		// 道具使用基本判断
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

		// 道具的特有使用要求
		if (propGroup == null || propGroup.getPropNum() == 0)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("物品已无");
			return propUseEffect;
		}
		if (player.getPMp() == player.getPMaxMp())
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("您的内力已达上限");
			return propUseEffect;
		}

		// 道具是否可以使用判断结束

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

		// 移除使用道具
		goodsService.removeProps(propGroup, use_num);
		// 法力更新
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
		propUseEffect.setEffectDisplay("您使用了"
				+ StringUtil.isoToGBK(propGroup.getPropName()) + ",增加了"
				+ add_mp + "点内力!");
		propUseEffect.setEffectValue("" + add_mp);
		return propUseEffect;
	}

	/**
	 * 满补充道具
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
			propUseEffect.setNoUseDisplay("PK状态不可使用该道具");
			return propUseEffect;
		}
		
		// 道具使用基本判断
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

		// 道具的特有使用要求
		if (propGroup == null || propGroup.getPropNum() == 0)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("物品已无");
			return propUseEffect;
		}
		if (prop.getPropOperate1().equals("1"))
		{
			if (player.getPHp() == player.getPMaxHp())
			{
				propUseEffect.setIsEffected(false);
				propUseEffect.setNoUseDisplay("您的血量已达上限");
				return propUseEffect;
			}

			// 移除使用道具
			goodsService.removeProps(propGroup, use_num);
			// 血量更新
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
			propUseEffect.setEffectDisplay("您使用了"
					+ StringUtil.isoToGBK(propGroup.getPropName()) + ",血量补满了!");
			propUseEffect.setEffectValue("all");
		}
		if (prop.getPropOperate1().equals("2"))
		{
			if (player.getPMp() == player.getPMaxMp())
			{
				propUseEffect.setIsEffected(false);
				propUseEffect.setNoUseDisplay("您的内力已达上限");
				return propUseEffect;
			}

			// 移除使用道具
			goodsService.removeProps(propGroup, use_num);
			// 法力更新
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
			propUseEffect.setEffectDisplay("您使用了"
					+ StringUtil.isoToGBK(propGroup.getPropName()) + ",内力补满了!");
			propUseEffect.setEffectValue("all");
		}
		return propUseEffect;
	}

	/**
	 * 道具的基本使用条件判断,返回空表示能使用
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
			logger.info("参数player为空");
			return result.toString();
		}

		// 道具是否可以使用判断
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		int tatol_prop_num = propGroupDao.getPropNumByByPropID(role_info.getPPk(), prop.getPropID());

		// 根据道具类型，判断是否能使用
		if (prop.getPropClass() == PropType.NORMAL
				|| prop.getPropClass() == PropType.EXONERATIVE)
		{
			result.append("非使用物品");
			return result.toString();
		}

		// 根据玩家状态，判断是否能使用
		// todo:

		// 有每天使用次数限制
		if (prop.getPropUsedegree() > 0)
		{
			TimeControlService timeControlService = new TimeControlService();
			// 判断是否可用
			if (!timeControlService.isUseable(
					role_info.getPPk(), prop.getPropID(),
					TimeControlService.PROP, prop.getPropUsedegree()))
			{
				result.append(prop.getPropName()).append("一天只能使用").append(prop.getPropUsedegree()).append("次,今天已不能使用了.");
				return result.toString();
			}
		}

		if (tatol_prop_num < use_num)
		{
			result.append("道具数量不够");
			return result.toString();
		}

		String[] grade_condition = prop.getPropReLevel().split(",");
		if (role_info.getBasicInfo().getGrade() < Integer.parseInt(grade_condition[0])
				|| role_info.getBasicInfo().getGrade() > Integer.parseInt(grade_condition[1]))
		{

			result.append("您的等级与使用等级不符");
			return result.toString();
		}

		if (prop.getPropSex() != 0 && role_info.getBasicInfo().getSex() != prop.getPropSex())
		{
			result.append("您的性别不符");
			return result.toString();
		}

		if (role_info.getTitleSet().isHaveByTitleStr(prop.getPropJob())==false )
		{
			result.append("称号不符合");
			return result.toString();
		}

		if (prop.getMarriage() != 0 && role_info.getBasicInfo().getMarried() != prop.getMarriage()) // 有结婚要求
		{
			result.append("结婚条件不符");
			return result.toString();
		}
		return null;
	}

	/** 宠物技能书的使用 */
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
				propUseEffect.setNoUseDisplay("您的宠物不能学习该技能");
				return propUseEffect;
			}
			if (dao.getPetGradeByPetpk(Integer.parseInt(pet_pk)) < dao
					.getPetGrade(Integer.parseInt(prop.getPropOperate1())))
			{
				propUseEffect.setPropType(PropType.PETSKILLBOOK);
				propUseEffect.setIsEffected(false);
				propUseEffect.setNoUseDisplay("您的宠物还未达到学习该技能的等级");
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
				propUseEffect.setNoUseDisplay("您宠物的技能等级尚未达到要求");
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
			propUseEffect.setNoUseDisplay("您的宠物已经学习过该技能");
			return propUseEffect;

		}
	}

	/** 使用生活技能书 */
	private PropUseEffect useLiveSkillBook(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();
		propUseEffect.setPropType(prop.getPropClass());

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		int sk_id1 = Integer.parseInt(prop.getPropOperate1());// 上一级技能的id
		int sk_id2 = Integer.parseInt(prop.getPropOperate2());// 学习技能的id
		SkillUpService sus = new SkillUpService();
		if (sus.isPlayerHaverThisSkill(role_info.getBasicInfo().getPPk(),
				sk_id1) == false)
		{
			propUseEffect.setPropType(PropType.LIVESKILLBOOK);
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("您还没有学习低等级技能!");
			return propUseEffect;
		}
		else
		{
			if (sus.ifAddSkill(sk_id1, role_info.getBasicInfo().getPPk()) == true)
			{
				// 移除使用道具
				GoodsService goodsService = new GoodsService();
				goodsService.removeProps(propGroup, use_num);
				// 学习该技能
				sus.studyLiveSkill(role_info.getBasicInfo().getPPk(), sk_id1,
						sk_id2);
				propUseEffect.setPropType(PropType.LIVESKILLBOOK);
				propUseEffect.setIsEffected(true);
				propUseEffect.setEffectDisplay("成功学习!");
				return propUseEffect;
			}
			else
			{
				propUseEffect.setPropType(PropType.LIVESKILLBOOK);
				propUseEffect.setIsEffected(false);
				propUseEffect.setNoUseDisplay("您的熟练度没有达到要求!");
				return propUseEffect;
			}
		}
	}

	/** 使用配方书 */
	private PropUseEffect useSynthesizeBook(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();
		propUseEffect.setPropType(prop.getPropClass());

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		int s_id = Integer.parseInt(prop.getPropOperate1());// 学习配方的id
		PlayerSynthesizeService pss = new PlayerSynthesizeService();
		if (pss.isHaveSynthesize(role_info.getBasicInfo().getPPk(), s_id) == true)
		{
			// 移除使用道具
			GoodsService goodsService = new GoodsService();
			goodsService.removeProps(propGroup, use_num);
			// 学习该配方

			propUseEffect.setPropType(PropType.SYNTHESIZEBOOK);
			propUseEffect.setIsEffected(true);
			propUseEffect.setEffectDisplay("您已经学会该配方!");
			return propUseEffect;
		}
		else
		{
			propUseEffect.setPropType(PropType.SYNTHESIZEBOOK);
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay("您已经学过该配方!");
			return propUseEffect;
		}
	}

	/** 使用元宝宝箱 */
	private PropUseEffect useYuanbaoBox(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();
		propUseEffect.setPropType(prop.getPropClass());

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		EconomyService es = new EconomyService();
		int yuanbao = Integer.parseInt(prop.getPropOperate1());// 元宝数量
		es.addYuanbao(role_info.getBasicInfo().getPPk(), role_info
				.getBasicInfo().getUPk(), yuanbao, StatisticsType.BAOXIANG);
		// 移除使用道具
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, use_num);

		propUseEffect.setPropType(PropType.GET_YUANBAO_BOX);
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay("您获得了【"+GameConfig.getYuanbaoName()+"】×" + yuanbao + "!");
		return propUseEffect;
	}

	/**
	 * 宠物蛋道具使用
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

		// 道具是否可以使用判断
		// 道具使用基本判断
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
			resutl = "宠物的数量已满";
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		else
		{
			// 移除使用道具
			goodsService.removeProps(propGroup, use_num);
			// 生成宠物
			pet_name = petEggService.creatPetByEgg(prop, p_pk);
		}

		propUseEffect.setPropType(PropType.PET_EGG_GUDING);
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay("您使用了" + propGroup.getPropName()
				+ ",获得了" + StringUtil.isoToGBK(pet_name));
		propUseEffect.setEffectValue("");
		return propUseEffect;
	}

	/**
	 * 免PK 道具
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

		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
		String resutl = isPropUseByBasicCondition(role_info, prop, use_num);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		// ************道具是否可以使用判断结束**************//

		// 获取免PK道具的使用时间 分钟计算
		String propTime = prop.getPropOperate1();
		// 获得当前时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String beginTime = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量
		TimeShow timeShow = new TimeShow();
		String endTime = timeShow.endTime(Integer.parseInt(propTime));// 取得当前时间几分钟之后的时间

		AvoidPkPropService avoidPkPropService = new AvoidPkPropService();
		avoidPkPropService.addAvoidPkProp(role_info.getBasicInfo().getPPk(),
				beginTime, endTime);
		GoodsService goodsService = new GoodsService();
		// 移除使用道具
		goodsService.removeProps(propGroup, use_num);
		propUseEffect.setIsEffected(true);
		propUseEffect.setEffectDisplay("您使用了"
				+ StringUtil.isoToGBK(propGroup.getPropName()) + "<br/>您将在"
				+ propTime + "分钟内不被人杀害");
		return propUseEffect;
	}

	// 武器宝箱
	private PropUseEffect useARMBOX(RoleEntity role_info,
			PlayerPropGroupVO propGroup, PropVO prop, int use_num)
	{
		PropUseEffect propUseEffect = new PropUseEffect();

		// 道具是否可以使用判断
		// 道具使用基本判断
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

		// 清楚原来的掉落物
		role_info.getDropSet().clearDropItem();
		
		// 掉落物品
		npcService.dropGoodsByJiangRareBox(Integer.parseInt(npc_id), player);

		// 得到掉落物
		List<DropGoodsVO> dropgoods = role_info.getDropSet().getList();
		// 判断包裹各数否够
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
				// 执行统计
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(dropGoods.getGoodsId(), dropGoods
						.getGoodsType(), dropGoods.getDropNum(),
						StatisticsType.DEDAO, StatisticsType.BAOXIANG, p_pk);

			}
			propUseEffect.setEffectDisplay(ss);
			// 移除使用道具
			goodsService.removeProps(propGroup, use_num);
		}
		else
		{
			role_info.getDropSet().clearDropItem();
			resutl = "您的包裹格数不够!请清理包裹!";
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return propUseEffect;
		}
		return propUseEffect;
	}

}
