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

	// ��Ʒ�б�
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

	// ��Ʒ����
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
	 * ������ʹ����Ʒ
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
			// ������Թ���ʹ�ô��͵���
			if ((com == null || "".equals(com.trim()))
					&& new GoodsService().haveMiJing(roleInfo.getBasicInfo()
							.getPPk()))
			{
				// û��ȷ��
				int goodsType = Integer.parseInt(goods_type.trim());
				if (goodsType == PropType.GOBACKCITY
						|| goodsType == PropType.MARKUP
						|| goodsType == PropType.SUIBIANCHUAN
						|| goodsType == PropType.GROUPCHUAN
						|| goodsType == PropType.FRIENDCHUAN
						|| goodsType == PropType.XINYINDU)
				{
					// ʹ�õ��Ǵ��͵���
					return mapping.findForward("inlost");
				}
			}
		}
		return n21(mapping, form, request, response);
	}

	/**
	 * �̳���ʹ����Ʒ
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

	// ʹ����Ʒ����
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
		// �ж�ʹ�õ��ߵĻ�������
		String hint = propUseService.isPropUseByBasicCondition(roleInfo,
				PropCache.getPropById(Integer.parseInt(prop_id)), 1);
		if (hint != null)
		{
			request.setAttribute("exmess", hint);
			return this.n2(mapping, form, request, response);
		}

		if (pg_pk == null || prop_id == null)
		{
			logger.info("pg_pk��goods_id��w_typeΪ��");
		}
		switch (Integer.parseInt(goods_type))
		{
			case PropType.EQUIP_UNBIND:// ���װ���󶨵���
			{
				return super.dispath(request, response,
						"/equip.do?cmd=bindEquipList&pg_pk=" + pg_pk);
			}
			case PropType.MIANTAIN_BAD_EQUIP:// �����𻵵�װ��
			{
				return super.dispath(request, response,
						"/equip.do?cmd=badEquipList&pg_pk=" + pg_pk);
			}
			case PropType.EQUIP_PUNCH:// ��׵���
			{
				return super.dispath(request, response,
						"/equip.do?cmd=punchEquipList");
			}
			case PropType.MARKUP:// ��ǵ���
			{
				return mapping.findForward("markup");
			}
				// ����ι���ĵ���ʹ��
			case PropType.PETSINEW: // ����ظ�����
			{
				return mapping.findForward("havingpetpetsinew");
			}
			case PropType.PETLONGE:// ����ظ�����
			{
				return mapping.findForward("havingpetpetsinew");
			}
			case PropType.PETEXP:// ���ﾭ�����
			{
				return mapping.findForward("petfeedsubmit");
			}
			case PropType.PETSKILLBOOK:// ���＼����
			{
				return mapping.findForward("pet_skillbook_use");
			}
			case PropType.SPEAKER:// С���ȵ���
			{
				return mapping.findForward("speaker");
			}
			case PropType.FIX_ARM_PROP:// ����װ������
			{
				return mapping.findForward("propmaintain");
			}
			case PropType.EQUIP_PROTECT:// ����װ������
			{
				return mapping.findForward("equip_protect");
			}
			case PropType.FRIENDCHUAN:// ���Ѵ���
			{
				return mapping.findForward("friendchuan");
			}
			case PropType.BUFF: // buff����
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

			case PropType.TIANYANFU:// ���۷�
			{
				return mapping.findForward("gold_box");
			}
			case PropType.GOLD_BOX:// �ƽ���
			{
				return mapping.findForward("gold_key");
			}
			case PropType.OTHER_GOLD_BOX:// ��
			{
				return mapping.findForward("gold_key");
			}
			case PropType.SUIBIANCHUAN:// ���ͷ�����
			{
				return mapping.findForward("suibianchuan");
			}
			case PropType.GROUPCHUAN:// ��Ա���ͷ�����
			{
				return mapping.findForward("groupchuan");
			}

			case PropType.SCRTCHTICKET:// �ι���
			{
				request.setAttribute("prop_id", prop_id);
				return mapping.findForward("scratchticket");
			}

			case PropType.BUFFRODAM:// ���BUFF����
			{
				logger.info("�Ƿ����BUFF����ʹ�û���");
				request.setAttribute("pg_pk", pg_pk);
				return mapping.findForward("buff_use");
			}

			case PropType.TTBOX:// ttbox
			{
				request.setAttribute("prop_id", prop_id);
				return mapping.findForward("ttbox");
			}
			case PropType.LABABOX: // ���Ա���
			{
				HttpSession propSession = request.getSession();
				propSession.setAttribute("prop_id", prop_id);// ����ID
				return mapping.findForward("toLabaBox");
			}
				// case PropType.PROPOFLABABOX: // ���Ա���ˢ�µ���
				// {
				// request.setAttribute("prop_id", prop_id);
				// return mapping.findForward("toLabaBox");
				// }
			case PropType.EQUIP_UPGRADE_QUALITY: // ����װ��Ʒ��
			{
				return mapping.findForward("equip_upgrade_quality");
			}
		}

		PropUseEffect propUseEffect = propUseService.usePropByPropGroupID(
				roleInfo, Integer.parseInt(pg_pk), 1);

		// ʹ�ú���Ҫ��תҳ��
		// ������ߴ�����:�سǾ�ֱ�Ӵ��͵�����������Ϸ����
		switch (propUseEffect.getPropType())
		{
			case PropType.GOBACKCITY:// �سǾ�
			{
				if (propUseEffect.getIsEffected())
				{
					return mapping.findForward("walk");
				}
				break;
			}
			case PropType.Carry:// ���ͷ�
			{
				if (propUseEffect.getIsEffected())
				{
					return mapping.findForward("walk");
				}
				break;
			}
			case PropType.XINYINDU:// ��ӡ��
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
			case PropType.BROTHERFU:// �ֵ������
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
			case PropType.MERRYFU: // ���������
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
			case PropType.ADD_LOVE_DEAR: // ���ӷ�������ֵ����
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
			case PropType.ADD_DEAR:// �������ܶȵ���
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
			case PropType.YINSHEN:// ʹ�������
			{
				if (propUseEffect.getIsEffected())
				{
					request.setAttribute("message", propUseEffect
							.getNoUseDisplay());
					return mapping.findForward("langjun");
				}
			}
			case PropType.FAN_YINSHEN:// ʹ�÷������
			{
				if (propUseEffect.getIsEffected())
				{
					request.setAttribute("message", propUseEffect
							.getNoUseDisplay());
					return mapping.findForward("langjun");
				}
			}
			case PropType.QIANLIYAN:// ʹ��ǧ���۷�
			{
				if (propUseEffect.getIsEffected())
				{
					request.setAttribute("message", propUseEffect
							.getNoUseDisplay());
					return mapping.findForward("langjun");
				}
			}
			case PropType.XIANHAI:// �ݺ���
			{
				if (propUseEffect.getIsEffected())
				{
					request.setAttribute("propUseEffect", propUseEffect);
					return mapping.findForward("xianhai");
				}
			}
			case PropType.COMPASS:// ָ����
			{
				if (propUseEffect.getIsEffected())
				{
					request.setAttribute("message", propUseEffect
							.getNoUseDisplay());
					return mapping.findForward("message");
				}
			}
			case PropType.MIJING_MAP:// �ؾ���ͼ
			{
				if (propUseEffect.getIsEffected())
				{
					request.setAttribute("message", propUseEffect
							.getNoUseDisplay());
					return mapping.findForward("refurbish_scene");
				}
			}
			case PropType.TIAOZHAN:// ��ս
			{
				if (propUseEffect.getIsEffected())
				{
					request.setAttribute("pg_pk", pg_pk);
					return mapping.findForward("tiaozhan");
				}
			}
			case PropType.QUIZ:// �������
			{
				request.setAttribute("propUseEffect", propUseEffect);
				return mapping.findForward("quiz_content");
			}
			case PropType.CONJURE:// �ٻ�����
			{
				return mapping.findForward("walk");
			}
			case PropType.ZHUANZHI:// תְ����
			{
				break;
			}
			case PropType.PET_EGG:// ���ﵰ����
			{
				/*
				 * if( propUseEffect.isEffected() ) {
				 * request.setAttribute("pet_pk",
				 * propUseEffect.getEffectValue()); return
				 * mapping.findForward("pet_egg"); }
				 */
				break;
			}
			case PropType.INIT_PET:// ϴ�������
			{
				if (propUseEffect.getIsEffected())
				{
					request.setAttribute("pg_pk", pg_pk);
					return mapping.findForward("init_pet");
				}
				break;
			}
			case PropType.RARE_BOX:// �������
			{
				if (propUseEffect.getIsEffected())
				{
					/* return mapping.findForward("rare_box"); */
				}
				break;
			}
			case PropType.GEI_RARE_BOX:// �����������
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

			case PropType.ARMBOX:// �����������
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

			case PropType.SKILLBOOK:// ����ѧϰ����
			{
				logger.info("�Ƿ���뼼��ѧϰ���ߣ�propUseEffect.isEffected()="
						+ propUseEffect.getIsEffected());
				if (propUseEffect.getIsEffected())
				{
					logger.info("�Ƿ���뼼��ѧϰ����");
					request.setAttribute("pPk", p_pk + "");
					request.setAttribute("propUseEffect", propUseEffect);
					request.setAttribute("pg_pk", pg_pk);
					request.setAttribute("goods_id", prop_id);
					request.setAttribute("goods_type", goods_type);
					return mapping.findForward("skillbook_use_hint");
				}
				break;
			}

			case PropType.EQUIPPROP:// ʹ��װ�������
			{
				request.setAttribute("pg_pk", pg_pk);
				return mapping.findForward("equip_prop_submit");
			}
			case PropType.VIP:// ��Ա������
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
	 * �鿴�������װ����װ����ϸ��Ϣ
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
	 * ��ɫ��װ��
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
	 * ��ɫ��������װ��
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pwPk = request.getParameter("pwPk");

		GoodsService goodsService = new GoodsService();
		
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		if(equip.getGameEquip().getIsBind()!=0)
		{
			request.setAttribute("resultWml", equip.getFullName()+"���ɶ���");
			return mapping.findForward("delete_goods_page_ok");
		}
		if( equip.isReconfirm()==false)
		{
			//����Ҫ����ȷ��
			return n13(mapping, form, request, response);
		}
		request.setAttribute("wName", equip.getFullName());
		request.setAttribute("pwPk", pwPk);
		return mapping.findForward("delete_goods_equip_ask");
	}

	/**
	 * ��ɫ����������Ʒ
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
		
		//�жϸõ����Ƿ�ɶ���
		if (prop.isThrowed()==false)
		{
			request.setAttribute("resultWml", prop.getPropName()+"���ɶ���");
			return mapping.findForward("delete_goods_page_ok");
		}
		if( prop.getPropNum()==1 )
		{
			//���ֻ��һ��ֱ�Ӷ���
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
	 * ��ɫ����������Ʒ
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
				hint = "��û����ô���"
						+ goodsService.getGoodsName(Integer.parseInt(goods_id),
								4) + "";
			}
			else
			{
				if (goods_type.equals("41"))
				{
					goodsService.removeSpecialProp(p_pk, Integer
							.parseInt(pg_pk), Integer.parseInt(goods_id));
					hint = "��������1��"
							+ goodsService.getGoodsName(Integer
									.parseInt(goods_id), 4) + "";
				}
				else
				{

					goodsService.removeProps(p_pk, Integer.parseInt(goods_id),Integer.parseInt(goodsNumber),GameLogManager.R_DROP);
					hint = "��������"
							+ goodsNumber
							+ "��"
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

	// ʹ����Ʒ
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
			logger.info("pg_pk��goods_id��w_typeΪ��");
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

	// ��Ʒ�б�
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

	// ʹ����Ʒ
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
			logger.info("pg_pk��goods_id��w_typeΪ��");
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
	 * ��ɫ����������Ʒ
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
		// ��֤����������Ƿ�Ϸ�
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
	 * ��ɫ��������װ��
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
	 * ��ɫ��װ��
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

	/** ���＼�����ʹ�ý�� */
	public ActionForward n15(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pg_pk = request.getParameter("pg_pk");
		String prop_id = request.getParameter("goods_id");
		String goods_type = request.getParameter("goods_type");
		String pet_pk = request.getParameter("pet_pk");
		if (pg_pk == null || prop_id == null)
		{
			logger.info("pg_pk��goods_id��w_typeΪ��");
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

	/** ���Ԫ����������� */
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
			request.setAttribute("display", "������Ʒ��λ�Ѿ�Ϊ�����!<br/>");
			return mapping.findForward("display");
		}
		else
		{
			request.setAttribute("display", display);
			return mapping.findForward("submit");
		}
	}

	/** ���Ԫ����������� */
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
			request.setAttribute("display", "����" + GameConfig.getYuanbaoName()
					+ "��������!<br/>");
			return mapping.findForward("display");
		}
		request.setAttribute("display", display);
		return mapping.findForward("display");
	}

	/** ����ʹ�þ�����ߵ���ת */
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