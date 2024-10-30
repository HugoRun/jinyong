package com.web.action.vip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.vo.honour.TitleVO;
import com.ls.ben.cache.staticcache.honour.TitleCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.mall.CommodityVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.web.action.ActionBase;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.mall.MallService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.RoleService;
import com.web.service.vip.VipService;

/**
 * 会员逻辑
 */
public class VipAction extends ActionBase
{
	public static Logger logger = Logger.getLogger("log.action");

	/**
	 * 使用会员卡
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		String prop_id = request.getParameter("prop_id").trim();
		
		// 丢弃这个道具
		GoodsService goodsService = new GoodsService();
		int del_num = goodsService.removeProps(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(prop_id), 1,GameLogManager.R_USE);
		if( del_num>0 )
		{
			PropVO prop = PropCache.getPropById(Integer.parseInt(prop_id));
			String vip_title_id = prop.getPropOperate2().trim();// VIP称号id
			TitleVO vip_title = TitleCache.getById(vip_title_id);
			roleInfo.getTitleSet().gainTitle(vip_title);//获得会员称号
		}

		request.setAttribute("hint", roleInfo.getTitleSet().getVIP().getHint());
		return mapping.findForward("vippage");
	}

	/**
	 * 购买VIP道具
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		String prop_id = request.getParameter("prop_id").trim();
		// 首先通过道具ID 得到商城的信息
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getPropCommodityInfo(prop_id);
		if (commodityVO != null)
		{
			commodityVO.setDiscount(80);
			String hint = mallService.buy(roleInfo, commodityVO, 1 + "");// 购买
			if (hint != null)
			{
				request.setAttribute("hint", hint);
				return mapping.findForward("mallvipprop");
			}
			else
			{
				hint = "你购买" + commodityVO.getPropName() + "×1，原价"
						+ commodityVO.getOriginalPrice() + ""+GameConfig.getYuanbaoName()+"，8折优惠后"
						+ (commodityVO.getOriginalPrice() * 80 / 100) + ""+GameConfig.getYuanbaoName()+"！";
				request.setAttribute("hint", hint);
				return mapping.findForward("mallvipprop");
			}
		}
		else
		{
			String hint = "数据错误请联系管理员";
			request.setAttribute("hint", hint);
			return mapping.findForward("mallvipprop");
		}

	}

	/***************************************************************************
	 * 领取VIP工资用
	 **************************************************************************/
	/** 跳转到领取VIP工资的页面 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		VipService vs = new VipService();
		String outprint = vs.getPlayerGetVipMoneyPrint();
		request.setAttribute("outprint", outprint);
		return mapping.findForward("getlaorage");
	}

	/** *玩家没不是VIP会员购买的跳转* */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		String prop_id = request.getParameter("prop_id").trim();
		// 首先通过道具ID 得到商城的信息
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getPropCommodityInfo(prop_id);
		if (commodityVO != null)
		{
			commodityVO.setDiscount(80);
			String hint = mallService.buy(roleInfo, commodityVO, 1 + "");// 购买
			if (hint != null)
			{
				EconomyService economyService = new EconomyService();
				long yuanbao = economyService.getYuanbao(roleInfo
						.getBasicInfo().getUPk());
				if (commodityVO.getOriginalPrice() > yuanbao)
				{
					return mapping.findForward("chongzhi");
				}
				else
				{
					request.setAttribute("hint", hint);
					return mapping.findForward("mallvipprop");
				}
			}
			else
			{
				if(GameConfig.getChannelId() == Channel.AIR){
					request.setAttribute("hint", "您花费"+GameConfig.getYuanbaoName()+"×"
							+ commodityVO.getOriginalPrice() + "购买了"
							+ commodityVO.getPropName() + "×1!请在角色包裹商城栏类使用"
							+ commodityVO.getPropName() + "即可成为乾坤会员!");
				}else{
					request.setAttribute("hint", "您花费"+GameConfig.getYuanbaoName()+"×"
							+ commodityVO.getOriginalPrice() + "购买了"
							+ commodityVO.getPropName() + "×1!请在角色包裹商城栏类使用"
							+ commodityVO.getPropName() + "即可成为铁血会员!");
				}
				return mapping.findForward("mallvipprop");
			}
		}
		else
		{
			String hint = "数据错误请联系管理员";
			request.setAttribute("hint", hint);
			return mapping.findForward("mallvipprop");
		}
	}

	/** ***玩家领取工资流程***** */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		VipService vs = new VipService();
		String result = vs.playerGetVipMoney(roleInfo);
		if (result == null)// 领取过一次
		{
			request.setAttribute("hint", "您已经领取过今天的工资了,请明天再来!");
			return mapping.findForward("mallvipprop");
		}
		else
		{
			if (result.equals("nomoney"))// 不是会员没有工资领取
			{
				String outprint = vs.getPlayerGetVipMoneyByBuyVip(response,
						request);
				if (outprint == null)
				{
					request.setAttribute("hint", "没有会员物品,请与GM联系!");
					return mapping.findForward("mallvipprop");
				}
				else
				{
					request.setAttribute("outprint", outprint);
					return mapping.findForward("buyvip");
				}

			}
			else
			{
				request.setAttribute("hint", result);
				return mapping.findForward("mallvipprop");
			}
		}
	}
}