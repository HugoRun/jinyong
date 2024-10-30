package com.ls.web.action.wrap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.pet.PetInfoDao;
import com.ls.ben.vo.info.buff.BuffEffectVO;
import com.ls.ben.vo.info.effect.PropUseEffect;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.ben.vo.info.pet.PetInfoVO;
import com.ls.ben.vo.wrap.WrapTempVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.pub.constant.MapType;
import com.ls.pub.constant.PropType;
import com.ls.pub.constant.Wrap;
import com.ls.web.action.ActionBase;
import com.ls.web.service.buff.BuffEffectService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipDisplayService;
import com.ls.web.service.goods.equip.EquipService;
import com.ls.web.service.goods.prop.PropUseService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.validate.ValidateService;
import com.lw.service.player.PlayerPropGroupService;

public class WrapAction extends ActionBase
{
	Logger logger = Logger.getLogger("log.action");

	// 物品列表
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		int w_type = this.getWType(request);
		int page_no = this.getPageNo(request);

		RoleEntity roleInfo = this.getRoleEntity(request);

		int u_pk = roleInfo.getBasicInfo().getUPk();
		int p_pk = roleInfo.getBasicInfo().getPPk();

		String path = request.getServletPath();
		request.setAttribute("path", path);

		GoodsService goodsSerivce = new GoodsService();
		EconomyService economyServcie = new EconomyService();

		long yuanbao = economyServcie.getYuanbao(u_pk);

		QueryPage item_page = null;
		switch (w_type)
		{
			case Wrap.CURE:
				item_page = goodsSerivce.getPagePropList(p_pk, Wrap.CURE,
						page_no);
				break;
			case Wrap.BOOK:
				item_page = goodsSerivce.getPagePropList(p_pk, Wrap.BOOK,
						page_no);
				break;
			case Wrap.EQUIP:
				item_page = goodsSerivce.getPageEquipOnWrap(p_pk, page_no);
				break;
			case Wrap.TASK:
				item_page = goodsSerivce.getPagePropList(p_pk, Wrap.TASK,
						page_no);
				break;
			case Wrap.REST:
				item_page = goodsSerivce.getPagePropList(p_pk, Wrap.REST,
						page_no);
				break;
			case Wrap.SHOP:
				item_page = goodsSerivce.getPagePropList(p_pk, Wrap.SHOP,
						page_no);
				break;
		}
		item_page.setURL(response, "/wrap.do?cmd=n1");

		request.setAttribute("item_page", item_page);
		request.setAttribute("yuanbao", "" + yuanbao);
		request.setAttribute("roleInfo", roleInfo);
		return mapping.findForward("wrap_list");
	}

	// 物品详情
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		String p_pk = (String) request.getSession().getAttribute("pPk");
		if (request.getSession().getAttribute("wraptempbean") == null)
		{
			WrapTempVO wraptempbean = new WrapTempVO();
			request.getSession().setAttribute("wraptempbean", wraptempbean);
		}
		WrapTempVO wtb = (WrapTempVO) request.getSession().getAttribute(
				"wraptempbean");
		wtb.setW_type((String) request.getSession().getAttribute("w_type"));
		wtb.setPage_no((String) request.getSession().getAttribute("page_no"));

		if (request.getParameter("pg_pk") != null)
		{
			wtb.setPg_pk(request.getParameter("pg_pk"));
		}
		if (request.getParameter("goods_id") != null)
		{
			wtb.setGoods_id(request.getParameter("goods_id"));
		}
		if (request.getParameter("goods_type") != null)
		{
			wtb.setGoods_type(request.getParameter("goods_type"));
		}
		if (request.getParameter("isReconfirm") != null)
		{
			wtb.setIsReconfirm(request.getParameter("isReconfirm"));
		}

		String goods_display = null;

		GoodsService goodsService = new GoodsService();

		if (wtb.getGoods_type().equals("41")
				|| wtb.getGoods_type().equals(PropType.JIEHUN_JIEZHI + ""))
		{
			String wupinlan = request.getParameter("wupinlan");
			if (wupinlan == null || wupinlan.equals("null"))
			{
				wupinlan = "0";
				request.setAttribute("wupinlan", "0");
			}
			else
			{
				request.setAttribute("wupinlan", wupinlan);
			}
			goods_display = goodsService.getEquipPropInfoWml(Integer
					.parseInt(p_pk), Integer.parseInt(wtb.getGoods_id()), wtb
					.getW_type(), wtb.getPg_pk(), wtb.getGoods_type(), wtb
					.getIsReconfirm(), wupinlan, request, response);
		}
		else
		{
			goods_display = goodsService.getPropInfoWml(Integer.parseInt(p_pk),
					Integer.parseInt(wtb.getGoods_id()), wtb.getW_type(), wtb
							.getPg_pk(), wtb.getGoods_type(), wtb
							.getIsReconfirm(), request, response);
		}
		request.setAttribute("pg_pk", wtb.getPg_pk());
		request.setAttribute("goods_id", wtb.getGoods_id());
		request.setAttribute("goods_type", wtb.getGoods_type());
		request.setAttribute("goods_display", goods_display);
		return mapping.findForward("goods_display");
	}

	/**
	 * 包裹里使用物品
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pg_pk = request.getParameter("pg_pk");
		String prop_id = request.getParameter("goods_id");
		String goods_type = request.getParameter("goods_type");
		String com = request.getParameter("commit");
		request.setAttribute("pg_pk", pg_pk);
		request.setAttribute("goods_id", prop_id);
		request.setAttribute("goods_type", goods_type);

		RoleEntity roleInfo = this.getRoleEntity(request);
		if (roleInfo.getBasicInfo().getSceneInfo().getMap().getMapType() == MapType.COMPASS)
		{
			// 如果在迷宫中使用传送道具
			if ((com == null || "".equals(com.trim()))
					&& new GoodsService().haveMiJing(roleInfo.getBasicInfo()
							.getPPk()))
			{
				// 没有确认
				int goodsType = Integer.parseInt(goods_type.trim());
				if (goodsType == PropType.GOBACKCITY
						|| goodsType == PropType.MARKUP
						|| goodsType == PropType.SUIBIANCHUAN
						|| goodsType == PropType.GROUPCHUAN
						|| goodsType == PropType.FRIENDCHUAN
						|| goodsType == PropType.XINYINDU)
				{
					// 使用的是传送道具
					return mapping.findForward("inlost");
				}
			}
		}
		return n21(mapping, form, request, response);
	}

	/**
	 * 商城里使用物品
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n22(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String p_pk = (String) request.getSession().getAttribute("pPk");
		String prop_id = request.getParameter("prop_id");

		GoodsService goodsService = new GoodsService();
		PlayerPropGroupVO prop_group = goodsService.getPropGroupByPropID(
				Integer.parseInt(p_pk), Integer.parseInt(prop_id));

		request.setAttribute("pg_pk", prop_group.getPgPk() + "");
		request.setAttribute("goods_id", prop_id);
		request.setAttribute("goods_type", prop_group.getPropType() + "");

		return n21(mapping, form, request, response);
	}

	// 使用物品控制
	public ActionForward n21(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		int p_pk = roleInfo.getBasicInfo().getPPk();

		String pg_pk = (String) request.getAttribute("pg_pk");
		String prop_id = (String) request.getAttribute("goods_id");
		String goods_type = (String) request.getAttribute("goods_type");

		request.setAttribute("pg_pk", pg_pk);
		request.setAttribute("goods_id", prop_id);
		request.setAttribute("goods_type", goods_type);

		PropUseService propUseService = new PropUseService();
		// 判断使用道具的基本条件
		String hint = propUseService.isPropUseByBasicCondition(roleInfo,
				PropCache.getPropById(Integer.parseInt(prop_id)), 1);
		if (hint != null)
		{
			request.setAttribute("exmess", hint);
			return this.n2(mapping, form, request, response);
		}

		if (pg_pk == null || prop_id == null)
		{
			logger.info("pg_pk或goods_id或w_type为空");
		}
		switch (Integer.parseInt(goods_type))
		{
			case PropType.EQUIP_UNBIND:// 解除装备绑定道具
			{
				return super.dispath(request, response,
						"/equip.do?cmd=bindEquipList&pg_pk=" + pg_pk);
			}
			case PropType.MIANTAIN_BAD_EQUIP:// 修理损坏的装备
			{
				return super.dispath(request, response,
						"/equip.do?cmd=badEquipList&pg_pk=" + pg_pk);
			}
			case PropType.EQUIP_PUNCH:// 打孔道具
			{
				return super.dispath(request, response,
						"/equip.do?cmd=punchEquipList");
			}
			case PropType.MARKUP:// 标记道具
			{
				return mapping.findForward("markup");
			}
				// 宠物喂养的道具使用
			case PropType.PETSINEW: // 宠物回复体力
			{
				return mapping.findForward("havingpetpetsinew");
			}
			case PropType.PETLONGE:// 宠物回复寿命
			{
				return mapping.findForward("havingpetpetsinew");
			}
			case PropType.PETEXP:// 宠物经验道具
			{
				return mapping.findForward("petfeedsubmit");
			}
			case PropType.PETSKILLBOOK:// 宠物技能书
			{
				return mapping.findForward("pet_skillbook_use");
			}
			case PropType.SPEAKER:// 小喇叭道具
			{
				return mapping.findForward("speaker");
			}
			case PropType.FIX_ARM_PROP:// 修理装备道具
			{
				return mapping.findForward("propmaintain");
			}
			case PropType.EQUIP_PROTECT:// 保护装备道具
			{
				return mapping.findForward("equip_protect");
			}
			case PropType.FRIENDCHUAN:// 好友传送
			{
				return mapping.findForward("friendchuan");
			}
			case PropType.BUFF: // buff道具
			{
				BuffEffectService buffService = new BuffEffectService();
				BuffEffectVO buffEffectVO = buffService.checkHasSameBuffType(
						p_pk, Integer.parseInt(pg_pk));
				if (buffEffectVO != null)
				{
					String reconfirm = request.getParameter("reconfirm");
					if (reconfirm == null || !reconfirm.equals("1"))
					{
						request.setAttribute("buff_id", buffEffectVO
								.getBuffId());
						return mapping.findForward("useBuffReconfirm");
					}
				}
				break;
			}

			case PropType.TIANYANFU:// 天眼符
			{
				return mapping.findForward("gold_box");
			}
			case PropType.GOLD_BOX:// 黄金宝箱
			{
				return mapping.findForward("gold_key");
			}
			case PropType.OTHER_GOLD_BOX:// 金蛋
			{
				return mapping.findForward("gold_key");
			}
			case PropType.SUIBIANCHUAN:// 传送符道具
			{
				return mapping.findForward("suibianchuan");
			}
			case PropType.GROUPCHUAN:// 队员传送符道具
			{
				return mapping.findForward("groupchuan");
			}

			case PropType.SCRTCHTICKET:// 刮刮乐
			{
				request.setAttribute("prop_id", prop_id);
				return mapping.findForward("scratchticket");
			}

			case PropType.BUFFRODAM:// 随机BUFF道具
			{
				logger.info("是否进入BUFF道具使用画面");
				request.setAttribute("pg_pk", pg_pk);
				return mapping.findForward("buff_use");
			}

			case PropType.TTBOX:// ttbox
			{
				request.setAttribute("prop_id", prop_id);
				return mapping.findForward("ttbox");
			}
			case PropType.LABABOX: // 拉霸宝箱
			{
				HttpSession propSession = request.getSession();
				propSession.setAttribute("prop_id", prop_id);// 宝箱ID
				return mapping.findForward("toLabaBox");
			}
				// case PropType.PROPOFLABABOX: // 拉霸宝箱刷新道具
				// {
				// request.setAttribute("prop_id", prop_id);
				// return mapping.findForward("toLabaBox");
				// }
			case PropType.EQUIP_UPGRADE_QUALITY: // 升级装备品质
			{
				return mapping.findForward("equip_upgrade_quality");
			}
		}

		PropUseEffect propUseEffect = propUseService.usePropByPropGroupID(
				roleInfo, Integer.parseInt(pg_pk), 1);

		// 使用后需要跳转页面
		// 特殊道具处理，如:回城卷直接传送到城市中心游戏场景
		switch (propUseEffect.getPropType())
		{
			case PropType.GOBACKCITY:// 回城卷
			{
				if (propUseEffect.getIsEffected())
				{
					return mapping.findForward("walk");
				}
				break;
			}
			case PropType.Carry:// 传送符
			{
				if (propUseEffect.getIsEffected())
				{
					return mapping.findForward("walk");
				}
				break;
			}
			case PropType.XINYINDU:// 心印符
			{
				if (propUseEffect.getIsEffected())
				{
					request.setAttribute("propUseEffect", propUseEffect);
					request.setAttribute("pg_pk", pg_pk);
					request.setAttribute("goods_id", prop_id);
					request.setAttribute("goods_type", goods_type);
					request.setAttribute("message", propUseEffect
							.getNoUseDisplay());
					return mapping.findForward("xinyinfu");
				}
				break;
			}
			case PropType.BROTHERFU:// 兄弟情深符
			{
				if (propUseEffect.getIsEffected())
				{
					request.setAttribute("propUseEffect", propUseEffect);
					request.setAttribute("pg_pk", pg_pk);
					request.setAttribute("goods_id", prop_id);
					request.setAttribute("goods_type", goods_type);
					return mapping.findForward("brother");
				}
				break;
			}
			case PropType.MERRYFU: // 夫妻情深符
			{
				if (propUseEffect.getIsEffected())
				{
					request.setAttribute("propUseEffect", propUseEffect);
					request.setAttribute("pg_pk", pg_pk);
					request.setAttribute("goods_id", prop_id);
					request.setAttribute("goods_type", goods_type);
					return mapping.findForward("merry");
				}
			}
			case PropType.ADD_LOVE_DEAR: // 增加夫妻甜蜜值道具
			{
				if (propUseEffect.getIsEffected())
				{
					request.setAttribute("propUseEffect", propUseEffect);
					request.setAttribute("pg_pk", pg_pk);
					request.setAttribute("goods_type", goods_type);
					request.setAttribute("goods_id", prop_id);
					return mapping.findForward("addlovedear");
				}
			}
			case PropType.ADD_DEAR:// 增加亲密度道具
			{
				if (propUseEffect.getIsEffected())
				{
					request.setAttribute("propUseEffect", propUseEffect);
					request.setAttribute("pg_pk", pg_pk);
					request.setAttribute("goods_type", goods_type);
					request.setAttribute("goods_id", prop_id);
					return mapping.findForward("adddear");
				}
			}
			case PropType.YINSHEN:// 使用隐身符
			{
				if (propUseEffect.getIsEffected())
				{
					request.setAttribute("message", propUseEffect
							.getNoUseDisplay());
					return mapping.findForward("langjun");
				}
			}
			case PropType.FAN_YINSHEN:// 使用反隐身符
			{
				if (propUseEffect.getIsEffected())
				{
					request.setAttribute("message", propUseEffect
							.getNoUseDisplay());
					return mapping.findForward("langjun");
				}
			}
			case PropType.QIANLIYAN:// 使用千里眼符
			{
				if (propUseEffect.getIsEffected())
				{
					request.setAttribute("message", propUseEffect
							.getNoUseDisplay());
					return mapping.findForward("langjun");
				}
			}
			case PropType.XIANHAI:// 陷害符
			{
				if (propUseEffect.getIsEffected())
				{
					request.setAttribute("propUseEffect", propUseEffect);
					return mapping.findForward("xianhai");
				}
			}
			case PropType.COMPASS:// 指南针
			{
				if (propUseEffect.getIsEffected())
				{
					request.setAttribute("message", propUseEffect
							.getNoUseDisplay());
					return mapping.findForward("message");
				}
			}
			case PropType.MIJING_MAP:// 秘境地图
			{
				if (propUseEffect.getIsEffected())
				{
					request.setAttribute("message", propUseEffect
							.getNoUseDisplay());
					return mapping.findForward("refurbish_scene");
				}
			}
			case PropType.TIAOZHAN:// 挑战
			{
				if (propUseEffect.getIsEffected())
				{
					request.setAttribute("pg_pk", pg_pk);
					return mapping.findForward("tiaozhan");
				}
			}
			case PropType.QUIZ:// 答题道具
			{
				request.setAttribute("propUseEffect", propUseEffect);
				return mapping.findForward("quiz_content");
			}
			case PropType.CONJURE:// 召唤道具
			{
				return mapping.findForward("walk");
			}
			case PropType.ZHUANZHI:// 转职道具
			{
				break;
			}
			case PropType.PET_EGG:// 宠物蛋道具
			{
				/*
				 * if( propUseEffect.isEffected() ) {
				 * request.setAttribute("pet_pk",
				 * propUseEffect.getEffectValue()); return
				 * mapping.findForward("pet_egg"); }
				 */
				break;
			}
			case PropType.INIT_PET:// 洗宠物道具
			{
				if (propUseEffect.getIsEffected())
				{
					request.setAttribute("pg_pk", pg_pk);
					return mapping.findForward("init_pet");
				}
				break;
			}
			case PropType.RARE_BOX:// 宝箱道具
			{
				if (propUseEffect.getIsEffected())
				{
					/* return mapping.findForward("rare_box"); */
				}
				break;
			}
			case PropType.GEI_RARE_BOX:// 发奖宝箱道具
			{
				if (propUseEffect.getIsEffected())
				{
					/*
					 * request.setAttribute("money", propUseEffect
					 * .getEffectValue()); request .setAttribute("exp",
					 * propUseEffect .getEffectValue1()); return
					 * mapping.findForward("gei_rare_box");
					 */
				}
				break;
			}

			case PropType.ARMBOX:// 发奖宝箱道具
			{
				if (propUseEffect.getIsEffected())
				{
					/*
					 * request.setAttribute("money", propUseEffect
					 * .getEffectValue()); request .setAttribute("exp",
					 * propUseEffect .getEffectValue1()); return
					 * mapping.findForward("gei_rare_box");
					 */
				}
				break;
			}

			case PropType.SKILLBOOK:// 技能学习道具
			{
				logger.info("是否进入技能学习道具，propUseEffect.isEffected()="
						+ propUseEffect.getIsEffected());
				if (propUseEffect.getIsEffected())
				{
					logger.info("是否进入技能学习道具");
					request.setAttribute("pPk", p_pk + "");
					request.setAttribute("propUseEffect", propUseEffect);
					request.setAttribute("pg_pk", pg_pk);
					request.setAttribute("goods_id", prop_id);
					request.setAttribute("goods_type", goods_type);
					return mapping.findForward("skillbook_use_hint");
				}
				break;
			}

			case PropType.EQUIPPROP:// 使用装备类道具
			{
				request.setAttribute("pg_pk", pg_pk);
				return mapping.findForward("equip_prop_submit");
			}
			case PropType.VIP:// 会员卡道具
			{
				if (propUseEffect.getIsEffected() == false)
				{
					request.setAttribute("hint", propUseEffect
							.getNoUseDisplay());
					return mapping.findForward("use_vip_hint");
				}
			}
		}

		request.setAttribute("propUseEffect", propUseEffect);
		request.setAttribute("pg_pk", pg_pk);
		request.setAttribute("goods_id", prop_id);
		request.setAttribute("goods_type", goods_type);
		return mapping.findForward("use_hint");
	}

	/**
	 * 查看包裹里的装备的装备详细信息
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		GoodsService goodsService = new GoodsService();
		EquipDisplayService equipDisplayService = new EquipDisplayService();

		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());

		String pwPk = request.getParameter("pwPk");
		String page_no = request.getParameter("page_no");

		PlayerEquipVO equip = (PlayerEquipVO) goodsService.getEquipByID(Integer
				.parseInt(pwPk));

		String equip_display = equipDisplayService.getEquipDisplay(roleInfo,
				equip, true);

		request.setAttribute("equip_display", equip_display);
		request.setAttribute("page_no", page_no);
		request.setAttribute("equip", equip);
		return mapping.findForward("equip_display");
	}

	/**
	 * 角色穿装备
	 */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		GoodsService goodsService = new GoodsService();
		EquipService equipService = new EquipService();

		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		String pwPk = request.getParameter("pwPk");
		String page_no = request.getParameter("page_no");
		request.setAttribute("page_no", page_no);

		PlayerEquipVO equip = (PlayerEquipVO) goodsService.getEquipByID(Integer
				.parseInt(pwPk));

		String hint = equipService.puton(roleInfo, equip);
		if (hint != null)
		{
			request.setAttribute("hint", hint);
			return mapping.findForward("addwrapno");
		}

		request.setAttribute("w_type", "3");
		return n1(mapping, form, request, response);
	}

	/**
	 * 角色丢弃包裹装备
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pwPk = request.getParameter("pwPk");

		GoodsService goodsService = new GoodsService();
		
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		if(equip.getGameEquip().getIsBind()!=0)
		{
			request.setAttribute("resultWml", equip.getFullName()+"不可丢弃");
			return mapping.findForward("delete_goods_page_ok");
		}
		if( equip.isReconfirm()==false)
		{
			//不需要二次确认
			return n13(mapping, form, request, response);
		}
		request.setAttribute("wName", equip.getFullName());
		request.setAttribute("pwPk", pwPk);
		return mapping.findForward("delete_goods_equip_ask");
	}

	/**
	 * 角色丢弃包裹物品
	 */
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pg_pk = request.getParameter("pg_pk");
		String goods_id = request.getParameter("goods_id");
		String goods_type = request.getParameter("goods_type");
		String isReconfirm = request.getParameter("isReconfirm");

		GoodsService goodsService = new GoodsService();
		
		PlayerPropGroupVO prop = goodsService.getPropByPgPk(Integer.parseInt(pg_pk));;
		
		//判断该道具是否可丢弃
		if (prop.isThrowed()==false)
		{
			request.setAttribute("resultWml", prop.getPropName()+"不可丢弃");
			return mapping.findForward("delete_goods_page_ok");
		}
		if( prop.getPropNum()==1 )
		{
			//如果只有一个直接丢弃
			request.setAttribute("goodsNumber", "1");
			return this.n8(mapping, form, request, response);
		}

		request.setAttribute("isReconfirm", isReconfirm);
		request.setAttribute("pg_pk", pg_pk);
		request.setAttribute("goods_id", goods_id);
		request.setAttribute("goods_type", goods_type);
		return mapping.findForward("delete_goods_page");
	}

	/**
	 * 角色丢弃包裹物品
	 */
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		int p_pk = roleInfo.getBasicInfo().getPPk();

		String pg_pk = null;
		if (request.getParameter("pg_pk") != null)
		{
			pg_pk = request.getParameter("pg_pk");
		}
		else
			if ((String) request.getAttribute("pg_pk") != null)
			{
				pg_pk = (String) request.getAttribute("pg_pk");
			}
		String goods_id = null;
		if (request.getParameter("goods_id") != null)
		{
			goods_id = request.getParameter("goods_id");
		}
		else
			if ((String) request.getAttribute("goods_id") != null)
			{
				goods_id = (String) request.getAttribute("goods_id");
			}
		String goods_type = null;
		if (request.getParameter("goods_type") != null)
		{
			goods_type = request.getParameter("goods_type");
		}
		else
			if ((String) request.getAttribute("goods_type") != null)
			{
				goods_type = (String) request.getAttribute("goods_type");
			}
		String goodsNumber = "1";
		if (request.getParameter("goodsNumber") != null)
		{
			goodsNumber = request.getParameter("goodsNumber");
		}
		else
			if ((String) request.getAttribute("goodsNumber") != null)
			{
				goodsNumber = (String) request.getAttribute("goodsNumber");
			}
		String isReconfirm = "";
		if (request.getParameter("isReconfirm") != null)
		{
			isReconfirm = request.getParameter("isReconfirm");
		}
		else
			if ((String) request.getAttribute("isReconfirm") != null)
			{
				isReconfirm = (String) request.getAttribute("isReconfirm");
			}
		GoodsService goodsService = new GoodsService();

		String hint = ValidateService
				.validateNonZeroNegativeIntegers(goodsNumber);

		if (hint == null)
		{
			if (goodsService.getPropNum(p_pk, Integer.parseInt(goods_id)) < Integer
					.parseInt(goodsNumber))
			{
				hint = "您没有这么多的"
						+ goodsService.getGoodsName(Integer.parseInt(goods_id),
								4) + "";
			}
			else
			{
				if (goods_type.equals("41"))
				{
					goodsService.removeSpecialProp(p_pk, Integer
							.parseInt(pg_pk), Integer.parseInt(goods_id));
					hint = "您丢弃了1个"
							+ goodsService.getGoodsName(Integer
									.parseInt(goods_id), 4) + "";
				}
				else
				{

					goodsService.removeProps(p_pk, Integer.parseInt(goods_id),Integer.parseInt(goodsNumber),GameLogManager.R_DROP);
					hint = "您丢弃了"
							+ goodsNumber
							+ "个"
							+ goodsService.getGoodsName(Integer
									.parseInt(goods_id), 4) + "";
				}
			}
		}
		request.setAttribute("resultWml", hint);
		request.setAttribute("pg_pk", pg_pk);
		request.setAttribute("goods_id", goods_id);
		request.setAttribute("goods_type", goods_type);
		request.setAttribute("isReconfirm", isReconfirm);
		return mapping.findForward("delete_goods_page_ok");
	}

	// 使用物品
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		String w_type = request.getParameter("w_type");
		String pg_pk = request.getParameter("pg_pk");
		String prop_id = request.getParameter("goods_id");
		String goods_type = request.getParameter("goods_type");
		String pet_pk = request.getParameter("pet_pk");
		String pagetype = request.getParameter("pagetype");
		if (pagetype == null)
		{
			pagetype = "1";
		}
		if (pg_pk == null || prop_id == null || w_type == null)
		{
			logger.info("pg_pk或goods_id或w_type为空");
		}

		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		int p_pk = roleInfo.getBasicInfo().getPPk();

		PropUseService propUseService = new PropUseService();
		PropUseEffect propUseEffect = propUseService.usePropByPropGroupIDpet(
				p_pk, Integer.parseInt(pg_pk), 1, pet_pk);

		request.setAttribute("pagetype", pagetype);
		request.setAttribute("pet_pk", pet_pk);
		request.setAttribute("w_type", w_type);
		request.setAttribute("propUseEffect", propUseEffect);
		request.setAttribute("pg_pk", pg_pk);
		request.setAttribute("goods_id", prop_id);
		request.setAttribute("goods_type", goods_type);
		return mapping.findForward("use_hint");
	}

	// 物品列表
	public ActionForward n10(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String w_type_str = request.getParameter("w_type");
		String page_no_str = request.getParameter("page_no");
		String pet_pk = request.getParameter("pet_pk");
		String petGrade = request.getParameter("petGrade");
		String petFatigue = request.getParameter("petFatigue");
		String petIsBring = request.getParameter("petIsBring");
		if (page_no_str == null)
		{
			page_no_str = (String) request.getAttribute("page_no");
		}
		int page_no;

		if (page_no_str == null || page_no_str.equals("null"))
		{
			page_no = 1;
		}
		else
		{
			page_no = Integer.parseInt(page_no_str);
		}

		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		int p_pk = roleInfo.getBasicInfo().getPPk();

		String path = request.getServletPath();
		request.setAttribute("path", path);
		GoodsService goodsSerivce = new GoodsService();
		if (Integer.parseInt(w_type_str) == Wrap.CURE)
		{
			QueryPage cure_page = goodsSerivce.getPagePropListpet(p_pk,
					Wrap.REST, page_no);
			request.setAttribute("prop_page", cure_page);
		}
		if (petIsBring == null)
		{
			PetInfoDao petInfoDao = new PetInfoDao();
			PetInfoVO pet = petInfoDao.getPet(Integer.parseInt(pet_pk));
			request.setAttribute("pet_pk", pet_pk);
			request.setAttribute("petGrade", pet.getPetGrade() + "");
			request.setAttribute("petFatigue", pet.getPetFatigue() + "");
			request.setAttribute("petIsBring", pet.getPetIsBring() + "");
			request.setAttribute("w_type", w_type_str);
			return mapping.findForward("wrap_list_pet");
		}
		request.setAttribute("pet_pk", pet_pk);
		request.setAttribute("petGrade", petGrade);
		request.setAttribute("petFatigue", petFatigue);
		request.setAttribute("petIsBring", petIsBring);
		request.setAttribute("w_type", w_type_str);
		return mapping.findForward("wrap_list_pet");
	}

	// 使用物品
	public ActionForward n11(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		String w_type = request.getParameter("w_type");
		String pg_pk = request.getParameter("pg_pk");
		String prop_id = request.getParameter("goods_id");
		String goods_type = request.getParameter("goods_type");
		String pet_pk = request.getParameter("pet_pk");
		String pagetype = request.getParameter("pagetype");
		if (pagetype == null)
		{
			pagetype = "2";
		}
		if (pg_pk == null || prop_id == null || w_type == null)
		{
			logger.info("pg_pk或goods_id或w_type为空");
		}

		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		int p_pk = roleInfo.getBasicInfo().getPPk();

		PropUseService propUseService = new PropUseService();
		PropUseEffect propUseEffect = propUseService.usePropByPropGroupIDpet(
				p_pk, Integer.parseInt(pg_pk), 1, pet_pk);

		request.setAttribute("pagetype", pagetype);
		request.setAttribute("w_type", w_type);
		request.setAttribute("propUseEffect", propUseEffect);
		request.setAttribute("pg_pk", pg_pk);
		request.setAttribute("goods_id", prop_id);
		request.setAttribute("goods_type", goods_type);
		request.setAttribute("pet_pk", pet_pk);
		return mapping.findForward("wrap_list_pet_hint");
	}

	/**
	 * 角色丢弃包裹物品
	 */
	public ActionForward n12(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		String pg_pk = request.getParameter("pg_pk");
		String goods_id = request.getParameter("goods_id");
		String goods_type = request.getParameter("goods_type");
		String goodsNumber = request.getParameter("goodsNumber");
		String isReconfirm = request.getParameter("isReconfirm");

		request.setAttribute("isReconfirm", isReconfirm);
		request.setAttribute("pg_pk", pg_pk);
		request.setAttribute("goods_id", goods_id);
		request.setAttribute("goods_type", goods_type);

		if (isReconfirm == null || isReconfirm.equals(""))
		{
			isReconfirm = "0";
		}
		if (goodsNumber == null || goodsNumber.equals("")
				|| goodsNumber.equals("null"))
		{
			GoodsService goodsSerivce = new GoodsService();
			PlayerPropGroupVO goodsGroup = goodsSerivce
					.getGoodsGroupByPgPk(Integer.parseInt(pg_pk));
			goodsNumber = goodsGroup.getPropNum() + "";
		}

		String hint = ValidateService
				.validateNonZeroNegativeIntegers(goodsNumber);
		// 验证输入的数量是否合法
		if (hint != null)
		{
			request.setAttribute("hint", hint);
			return mapping.findForward("delete_goods_page");
		}

		request.setAttribute("goodsNumber", goodsNumber);

		if (Integer.parseInt(isReconfirm) == 1)
		{
			return n8(mapping, form, request, response);
		}
		return mapping.findForward("delete_goods_page_ask");
	}

	/**
	 * 角色丢弃包裹装备
	 */
	public ActionForward n13(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);

		String pwPk = request.getParameter("pwPk");
		GoodsService GoodsService = new GoodsService();
		GoodsService.removeEquipById(roleInfo.getPPk(), Integer.parseInt(pwPk),GameLogManager.R_DROP);

		return n1(mapping, form, request, response);
	}

	/**
	 * 角色穿装备
	 */
	public ActionForward n14(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		GoodsService goodsService = new GoodsService();
		RoleService roleService = new RoleService();
		EquipService equipService = new EquipService();

		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());

		String pwPk = request.getParameter("pwPk");

		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		String hint = equipService.puton(roleInfo, equip);
		if (hint != null)
		{
			request.setAttribute("hint", hint);
			return mapping.findForward("addwrapno");
		}
		request.setAttribute("w_type", "3");
		return n1(mapping, form, request, response);
	}

	/** 宠物技能书的使用结果 */
	public ActionForward n15(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pg_pk = request.getParameter("pg_pk");
		String prop_id = request.getParameter("goods_id");
		String goods_type = request.getParameter("goods_type");
		String pet_pk = request.getParameter("pet_pk");
		if (pg_pk == null || prop_id == null)
		{
			logger.info("pg_pk或goods_id或w_type为空");
		}

		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		int p_pk = roleInfo.getBasicInfo().getPPk();

		PropUseService propUseService = new PropUseService();
		PropUseEffect propUseEffect = propUseService.usePropByPropGroupIDpet(
				p_pk, Integer.parseInt(pg_pk), 1, pet_pk);

		request.setAttribute("propUseEffect", propUseEffect);
		request.setAttribute("pg_pk", pg_pk);
		request.setAttribute("goods_id", prop_id);
		request.setAttribute("goods_type", goods_type);
		request.setAttribute("pet_pk", pet_pk);
		return mapping.findForward("pet_skillbook_use_result");
	}

	/** 玩家元宝买包裹格数 */
	public ActionForward n19(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		PlayerPropGroupService playerPropGroupService = new PlayerPropGroupService();
		String display = playerPropGroupService
				.getPlayerPropGroupInfo(roleInfo);
		if (display == null)
		{
			request.setAttribute("display", "您的物品栏位已经为最大了!<br/>");
			return mapping.findForward("display");
		}
		else
		{
			request.setAttribute("display", display);
			return mapping.findForward("submit");
		}
	}

	/** 玩家元宝买包裹格数 */
	public ActionForward n20(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		PlayerPropGroupService playerPropGroupService = new PlayerPropGroupService();
		String display = null;
		if(GameConfig.getChannelId()==Channel.TELE)
		{
			display=playerPropGroupService.buyPropGroup(request, roleInfo);
		}
		else
		{
			display=playerPropGroupService.buyPropGroup(roleInfo);
		}
		if (display == null)
		{
			request.setAttribute("display", "您的" + GameConfig.getYuanbaoName()
					+ "数量不够!<br/>");
			return mapping.findForward("display");
		}
		request.setAttribute("display", display);
		return mapping.findForward("display");
	}

	/** 宠物使用经验道具的跳转 */
	public ActionForward n23(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pg_pk = request.getParameter("pg_pk");
		String prop_id = request.getParameter("goods_id");
		String goods_type = request.getParameter("goods_type");
		request.setAttribute("pg_pk", pg_pk);
		request.setAttribute("goods_id", prop_id);
		request.setAttribute("goods_type", goods_type);
		return mapping.findForward("havingpetpetsinew");
	}

	private int getWType(HttpServletRequest request)
	{
		String w_type_str = request.getParameter("w_type");

		if ( StringUtils.isEmpty(w_type_str)==true || w_type_str.equals("null") )
		{
			w_type_str = (String) request.getSession().getAttribute("w_type");
			if (w_type_str == null)
			{
				w_type_str = "2";
			}
		}
		request.getSession().setAttribute("w_type", w_type_str);
		return Integer.parseInt(w_type_str);
	}
}