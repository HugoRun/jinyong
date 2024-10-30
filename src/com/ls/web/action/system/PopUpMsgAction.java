package com.ls.web.action.system;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.shitu.model.DateUtil;
import com.ben.tiaozhan.TiaozhanConstant;
import com.ben.vo.friend.FriendVO;
import com.ls.ben.vo.mall.CommodityVO;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.model.vip.Vip;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.pub.constant.player.PlayerState;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.action.ActionBase;
import com.ls.web.service.mall.MallService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.system.UMsgService;
import com.lw.service.player.PlayerPropGroupService;
import com.pm.service.systemInfo.SystemInfoService;
import com.web.service.friend.FriendService;

/**
 * 弹出式消息
 */
public class PopUpMsgAction extends ActionBase
{

	/**
	 * 消息处理
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		
		UMessageInfoVO uMsgInfo = (UMessageInfoVO) request.getAttribute("uMsgInfo");
		RoleService roleService = new RoleService();
		roleInfo.getStateInfo().setCurState(PlayerState.SYSMSG);
		UMessageInfoVO result = null;
		UMsgService uMsgService = new UMsgService();
		result = uMsgService.processMsg(uMsgInfo, roleInfo, request, response);
		if (result.getMsgType() == PopUpMsgType.NOTIFY_KILL_OTHER)
		{
			 return super.dispath(request, response, "/pk.do?cmd=notifyKillOther");
		}
		else if (result.getMsgType() == PopUpMsgType.NOTIFY_SELF_DEAD)
		{
			return super.dispath(request, response, "/pk.do?cmd=notifySelfDead");
		}
		else if (result.getMsgType() == PopUpMsgType.NOTIFY_OTHER_DEAD)
		{
			return super.dispath(request, response, "/pk.do?cmd=notifyOtherDead");
		}
		else if (result.getMsgType() == PopUpMsgType.JIEYI)
		{
			request.setAttribute("result", uMsgInfo);
			request.setAttribute("name", roleService.getName(uMsgInfo
					.getMsgOperate2())[0]);
			return mapping.findForward("jieyi");
		}
		else if (result.getMsgType() == PopUpMsgType.JIEHUN)
		{
			request.setAttribute("result", uMsgInfo);
			System.out.println(uMsgInfo.getMsgOperate2());
			request.setAttribute("name", roleService.getName(uMsgInfo
					.getMsgOperate2())[0]);
			request.setAttribute("ownname", roleInfo.getBasicInfo().getName());
			return mapping.findForward("jiehun");
		}
		else if (result.getMsgType() == PopUpMsgType.LIHUN)
		{
			request.setAttribute("result", uMsgInfo);
			List<FriendVO> list = new FriendService().isMerry(uMsgInfo
					.getMsgOperate2());
			if (list != null && list.size() > 0)
			{
				request.setAttribute("friendVo", list.get(0));
			}
			System.out.println(uMsgInfo.getMsgOperate2());
			request.setAttribute("name", roleService.getName(uMsgInfo
					.getMsgOperate2())[0]);
			return mapping.findForward("lihun");
		}
		else if (result.getMsgType() == PopUpMsgType.MERRY_AGREE)
		{
			// 同意结婚
			request.setAttribute("result", uMsgInfo);
			request.setAttribute("name", roleService.getName(uMsgInfo
					.getMsgOperate2())[0]);
			request.setAttribute("ownname", roleInfo.getBasicInfo().getName());
			return mapping.findForward("merryagree");
		}
		else if (result.getMsgType() == PopUpMsgType.CAN_NOT_MERRY)
		{
			request.setAttribute("result", uMsgInfo);
			request.setAttribute("name", roleService.getName(uMsgInfo
					.getMsgOperate2())[0]);
			request.setAttribute("ownname", roleInfo.getBasicInfo().getName());
			return mapping.findForward("merryresult");
		}
		else if (result.getMsgType() == PopUpMsgType.CHUSHI)
		{
			return mapping.findForward("chushi");
		}
		else if (result.getMsgType() == PopUpMsgType.LEITAI)
		{
			request.setAttribute("message", result.getMsgOperate1());
			return mapping.findForward("leitaiResult");
		}
		else if (result.getMsgType() == PopUpMsgType.LANGJUN)
		{
			roleInfo.getStateInfo().setCurState(PlayerState.GENERAL);
			request.setAttribute("message", result.getMsgOperate1());
			return mapping.findForward("langjun");
		}
		else if (result.getMsgType() == PopUpMsgType.MIJING_MAP)
		{
			request.setAttribute("message", result.getResult());
			request.setAttribute("where", result.getMsgOperate1());
			return mapping.findForward("mijing");
		}
		else if (result.getMsgType() == PopUpMsgType.COMMON)
		{
			request.setAttribute("message", result.getResult());
			return mapping.findForward("langjun");
		}
		else if (result.getMsgType() == PopUpMsgType.TIAOZHAN)
		{
			Date date = TiaozhanConstant.TIAOZHAN_TIME.get(roleInfo.getBasicInfo()
					.getPPk());
			if (DateUtil.checkMin(date, TiaozhanConstant.OVER_TIME))
			{
				request.setAttribute("message", "由于您在1分钟内没有进行选择，因此被视为拒绝挑战");
				new SystemInfoService().insertSystemInfoBySystem(roleInfo
						.getBasicInfo().getName()
						+ "被" + result.getResult() + "的霸气所震，竟然没有胆量接受战书！");
				return mapping.findForward("langjun");
			}
			else
			{
				request.setAttribute("message", result.getResult());
				request.setAttribute("tiao_ppk", result.getMsgOperate1());
				return mapping.findForward("tiaozhan");
			}
		}
		else if(result.getMsgType() == PopUpMsgType.NEWPLAYERGUIDEINFOMSG){
			if(result.getMsgPriority() != 0){
				roleInfo.getBasicInfo().updateSceneId(result.getMsgPriority()+"");
			}
			request.setAttribute("message", result.getMsgOperate1());
			return mapping.findForward("new_player_guide");
		}
		request.setAttribute("result", result.getResult());
		return mapping.findForward("result");
	}

	/**
	 * PK消息特殊处理
	 *//*
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			RoleEntity me = this.getRoleEntity(request);
			PKState pkState = me.getPKState();
			
			UMessageInfoVO result = (UMessageInfoVO) request.getAttribute("result");
			if (result != null)
			{
				request.setAttribute("uMsgInfo",result);
			}

			//PKNotifyService pkNotifyService = new PKNotifyService();
			//PKNotifyVO pkNotify = pkNotifyService.getNotfiy(me.getPPk());
			//if (pkNotify != null && pkNotify.getNotifyedPk() == me.getPPk())// 有通知且通知创造者不是自己
			{
				switch (pkState.getState())
				{
					case PKNotifyService.ATTACKED://通知玩家受到攻击
					{
						pkNotifyService.deleteNotify(pkNotify.getNPk());
						request.getRequestDispatcher("/pk.do?chair=" + request.getParameter("chair")
										+ "&cmd=n7&aPpk="
										+ pkNotify.getNotifyedPk() + "&bPpk="
										+ pkNotify.getCreateNotifyPk()
										+ "&tong=" + result.getMsgOperate2()).forward(request, response);
						return null;
					}
					case PKState.S_NOTIFY_SELF_DEAD://自己死亡通知其他人
					{
						//pkNotifyService.deleteNotifyByPlayer(pkNotify.getNotifyedPk(), pkNotify.getCreateNotifyPk());
						return super.dispath(request, response, "/pk.do?cmd=notifySelfDead");
					}

					case PKState.S_NOTIFY_KILL_OTHER://通知对方被自己杀死了
					{
						return super.dispath(request, response, "/pk.do?cmd=notifyKillOther");
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return mapping.findForward("refurbish_scene");
	}*/

	/**
	 * 弹出式消息购买物品
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		String c_id = request.getParameter("c_id");// 获取商城ID
		String discount = request.getParameter("discount");// 取得消息类型
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getCommodityInfo(c_id);// 取得物品在商城的信息
		// 取得该物品在商城的折扣
		commodityVO.setDiscount(Integer.parseInt(discount));
		String hint = mallService.buy(roleInfo, commodityVO, 1 + "");// 购买
		if (hint != null)
		{
			if (hint.indexOf("金额不足") != -1)
			{
				StringBuffer sb = new StringBuffer();
				sb.append(hint + ",请前往商城进行"+GameConfig.getYuanbaoName()+"充值。<br/>");
				sb.append("<anchor> ");
				sb.append("<go method=\"get\" href=\""
						+ response.encodeURL(GameConfig.getContextPath()
								+ "/sky/bill.do?cmd=n0") + "\"></go>");
				sb.append("快速充值");
				sb.append("</anchor>");
				request.setAttribute("result", sb.toString());
				return mapping.findForward("msghint");
			}
			request.setAttribute("result", hint);
			return mapping.findForward("msghint");
		}
		else
		{
			Vip vip = roleInfo.getTitleSet().getVIP();
			if (vip != null)
			{// 是VIP会员
				if(Integer.parseInt(discount) == -1){
					hint = "你是"
						+ vip.getName()
						+ ",在"
						+ (Integer.parseInt(discount) / 10)
						+ "折优惠的基础上再打"
						+ (vip.getDiscount() / 10)
						+ "折为"
						+ ((commodityVO.getOriginalPrice()
								* Integer.parseInt(discount) / 100)
								* vip.getDiscount() / 100);
				}else{
					hint = "你是"
						+ vip.getName()
						+ ",打"
						+ (vip.getDiscount() / 10)
						+ "折后价钱为"
						+ ((commodityVO.getOriginalPrice())
								* vip.getDiscount() / 100);
				}
			}
			else
			{
				hint = "";
			}
			if(Integer.parseInt(discount) == -1){
				hint = "你购买"
					+ commodityVO.getPropName()
					+ "×1,花费"
					+ (commodityVO.getOriginalPrice()) + ""+GameConfig.getYuanbaoName()+"！" + hint;
			}else{
				hint = "你购买"
					+ commodityVO.getPropName()
					+ "×1，原价"
					+ commodityVO.getOriginalPrice()
					+ "元宝，"
					+ (Integer.parseInt(discount) / 10)
					+ "折优惠后"
					+ (commodityVO.getOriginalPrice()
							* Integer.parseInt(discount) / 100) + ""+GameConfig.getYuanbaoName()+"！" + hint;
			}
			
			request.setAttribute("result", hint);
			return mapping.findForward("msghint");
		}
	}

	/**
	 * 弹出式消息购买物品
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		String c_id = request.getParameter("c_id");// 获取商城ID
		String discount = request.getParameter("discount");// 取得消息类型
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getCommodityInfo(c_id);// 取得物品在商城的信息
		// 取得该物品在商城的折扣
		commodityVO.setDiscount(Integer.parseInt(discount));
		String hint = mallService.buy(roleInfo, commodityVO, 1 + "");// 购买
		if (hint != null)
		{
			if (hint.indexOf("金额不足") != -1)
			{
				StringBuffer sb = new StringBuffer();
				sb.append(hint + ",请前往商城进行"+GameConfig.getYuanbaoName()+"充值。<br/>");
				sb
						.append("★本次充值完成后，请立即返回角色选择页面，重新选择角色进入游戏进行一折会员购买。（如做其他相关操作则可能导致无法购买）★<br/>");
				sb.append("☆操作流程：充值完成后，点击菜单中的“系统”，选择返回首页即可☆<br/>");
				sb.append("<anchor> ");
				sb.append("<go method=\"get\" href=\""
						+ response.encodeURL(GameConfig.getContextPath()
								+ "/sky/bill.do?cmd=n0") + "\"></go>");
				sb.append("快速充值");
				sb.append("</anchor>");
				request.setAttribute("result", sb.toString());
				return mapping.findForward("msghint");
			}
			request.setAttribute("result", hint);
			return mapping.findForward("msghint");
		}
		else
		{
			Vip vip = roleInfo.getTitleSet().getVIP();
			if (vip != null)
			{// 是VIP会员
				hint = "你是"
						+ vip.getName()
						+ ",在"
						+ (Integer.parseInt(discount) / 10)
						+ "折优惠的基础上在打"
						+ (vip.getDiscount() / 10)
						+ "折为"
						+ ((commodityVO.getOriginalPrice()
								* Integer.parseInt(discount) / 100)
								* vip.getDiscount() / 100);
			}
			else
			{
				hint = "";
			}
			hint = "你购买"
					+ commodityVO.getPropName()
					+ "×1，原价"
					+ commodityVO.getOriginalPrice()
					+ ""+GameConfig.getYuanbaoName()+"，"
					+ (Integer.parseInt(discount) / 10)
					+ "折优惠后"
					+ (commodityVO.getOriginalPrice()
							* Integer.parseInt(discount) / 100) + ""+GameConfig.getYuanbaoName()+"！" + hint;
			request.setAttribute("result", hint);
			return mapping.findForward("msghint");
		}
	}

	/**
	 * 弹出式消息购买包裹格子
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
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
			request.setAttribute("result", "您的"+GameConfig.getYuanbaoName()+"数量不够!<br/>");
			return mapping.findForward("msghint");
		}
		request.setAttribute("result", display);
		return mapping.findForward("msghint");
	}

	/**
	 * 购买会员
	 */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		String prop_id = request.getParameter("prop_id").trim();
		String discount = request.getParameter("discount").trim();
		// 首先通过道具ID 得到商城的信息
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getPropCommodityInfo(prop_id);
		if (commodityVO != null)
		{
			commodityVO.setDiscount(Integer.parseInt(discount));
			String hint = mallService.buy(roleInfo, commodityVO, 1 + "");// 购买
			if (hint != null)
			{
				request.setAttribute("result", hint);
				return mapping.findForward("msghint");
			}
			else
			{
				Vip vip = roleInfo.getTitleSet().getVIP();
				if (vip != null)
				{// 是VIP会员
					hint = "你是"
							+ vip.getName()
							+ ",在"
							+ (Integer.parseInt(discount) / 10)
							+ "折优惠的基础上在打"
							+ (vip.getDiscount() / 10)
							+ "折为"
							+ ((commodityVO.getOriginalPrice()
									* Integer.parseInt(discount) / 100)
									* vip.getDiscount() / 100);
				}
				else
				{
					hint = "";
				}
				hint = "你购买"
						+ commodityVO.getPropName()
						+ "×1，原价"
						+ commodityVO.getOriginalPrice()
						+ ""+GameConfig.getYuanbaoName()+"，"
						+ (Integer.parseInt(discount) / 10)
						+ "折优惠后"
						+ (commodityVO.getOriginalPrice()
								* Integer.parseInt(discount) / 100) + ""+GameConfig.getYuanbaoName()+"！"
						+ hint;
				roleInfo.getBasicInfo().updateAddCurExp(1);
				request.setAttribute("result", hint);
				return mapping.findForward("msghint");
			}
		}
		else
		{
			String result = "数据错误请联系管理员";
			request.setAttribute("result", result);
			return mapping.findForward("msghint");
		}

	}
}