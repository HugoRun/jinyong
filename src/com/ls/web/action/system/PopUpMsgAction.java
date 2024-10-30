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
 * ����ʽ��Ϣ
 */
public class PopUpMsgAction extends ActionBase
{

	/**
	 * ��Ϣ����
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
			// ͬ����
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
				request.setAttribute("message", "��������1������û�н���ѡ����˱���Ϊ�ܾ���ս");
				new SystemInfoService().insertSystemInfoBySystem(roleInfo
						.getBasicInfo().getName()
						+ "��" + result.getResult() + "�İ������𣬾�Ȼû�е�������ս�飡");
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
	 * PK��Ϣ���⴦��
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
			//if (pkNotify != null && pkNotify.getNotifyedPk() == me.getPPk())// ��֪ͨ��֪ͨ�����߲����Լ�
			{
				switch (pkState.getState())
				{
					case PKNotifyService.ATTACKED://֪ͨ����ܵ�����
					{
						pkNotifyService.deleteNotify(pkNotify.getNPk());
						request.getRequestDispatcher("/pk.do?chair=" + request.getParameter("chair")
										+ "&cmd=n7&aPpk="
										+ pkNotify.getNotifyedPk() + "&bPpk="
										+ pkNotify.getCreateNotifyPk()
										+ "&tong=" + result.getMsgOperate2()).forward(request, response);
						return null;
					}
					case PKState.S_NOTIFY_SELF_DEAD://�Լ�����֪ͨ������
					{
						//pkNotifyService.deleteNotifyByPlayer(pkNotify.getNotifyedPk(), pkNotify.getCreateNotifyPk());
						return super.dispath(request, response, "/pk.do?cmd=notifySelfDead");
					}

					case PKState.S_NOTIFY_KILL_OTHER://֪ͨ�Է����Լ�ɱ����
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
	 * ����ʽ��Ϣ������Ʒ
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		String c_id = request.getParameter("c_id");// ��ȡ�̳�ID
		String discount = request.getParameter("discount");// ȡ����Ϣ����
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getCommodityInfo(c_id);// ȡ����Ʒ���̳ǵ���Ϣ
		// ȡ�ø���Ʒ���̳ǵ��ۿ�
		commodityVO.setDiscount(Integer.parseInt(discount));
		String hint = mallService.buy(roleInfo, commodityVO, 1 + "");// ����
		if (hint != null)
		{
			if (hint.indexOf("����") != -1)
			{
				StringBuffer sb = new StringBuffer();
				sb.append(hint + ",��ǰ���̳ǽ���"+GameConfig.getYuanbaoName()+"��ֵ��<br/>");
				sb.append("<anchor> ");
				sb.append("<go method=\"get\" href=\""
						+ response.encodeURL(GameConfig.getContextPath()
								+ "/sky/bill.do?cmd=n0") + "\"></go>");
				sb.append("���ٳ�ֵ");
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
			{// ��VIP��Ա
				if(Integer.parseInt(discount) == -1){
					hint = "����"
						+ vip.getName()
						+ ",��"
						+ (Integer.parseInt(discount) / 10)
						+ "���ŻݵĻ������ٴ�"
						+ (vip.getDiscount() / 10)
						+ "��Ϊ"
						+ ((commodityVO.getOriginalPrice()
								* Integer.parseInt(discount) / 100)
								* vip.getDiscount() / 100);
				}else{
					hint = "����"
						+ vip.getName()
						+ ",��"
						+ (vip.getDiscount() / 10)
						+ "�ۺ��ǮΪ"
						+ ((commodityVO.getOriginalPrice())
								* vip.getDiscount() / 100);
				}
			}
			else
			{
				hint = "";
			}
			if(Integer.parseInt(discount) == -1){
				hint = "�㹺��"
					+ commodityVO.getPropName()
					+ "��1,����"
					+ (commodityVO.getOriginalPrice()) + ""+GameConfig.getYuanbaoName()+"��" + hint;
			}else{
				hint = "�㹺��"
					+ commodityVO.getPropName()
					+ "��1��ԭ��"
					+ commodityVO.getOriginalPrice()
					+ "Ԫ����"
					+ (Integer.parseInt(discount) / 10)
					+ "���Żݺ�"
					+ (commodityVO.getOriginalPrice()
							* Integer.parseInt(discount) / 100) + ""+GameConfig.getYuanbaoName()+"��" + hint;
			}
			
			request.setAttribute("result", hint);
			return mapping.findForward("msghint");
		}
	}

	/**
	 * ����ʽ��Ϣ������Ʒ
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		String c_id = request.getParameter("c_id");// ��ȡ�̳�ID
		String discount = request.getParameter("discount");// ȡ����Ϣ����
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getCommodityInfo(c_id);// ȡ����Ʒ���̳ǵ���Ϣ
		// ȡ�ø���Ʒ���̳ǵ��ۿ�
		commodityVO.setDiscount(Integer.parseInt(discount));
		String hint = mallService.buy(roleInfo, commodityVO, 1 + "");// ����
		if (hint != null)
		{
			if (hint.indexOf("����") != -1)
			{
				StringBuffer sb = new StringBuffer();
				sb.append(hint + ",��ǰ���̳ǽ���"+GameConfig.getYuanbaoName()+"��ֵ��<br/>");
				sb
						.append("�ﱾ�γ�ֵ��ɺ����������ؽ�ɫѡ��ҳ�棬����ѡ���ɫ������Ϸ����һ�ۻ�Ա���򡣣�����������ز�������ܵ����޷����򣩡�<br/>");
				sb.append("��������̣���ֵ��ɺ󣬵���˵��еġ�ϵͳ����ѡ�񷵻���ҳ���ɡ�<br/>");
				sb.append("<anchor> ");
				sb.append("<go method=\"get\" href=\""
						+ response.encodeURL(GameConfig.getContextPath()
								+ "/sky/bill.do?cmd=n0") + "\"></go>");
				sb.append("���ٳ�ֵ");
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
			{// ��VIP��Ա
				hint = "����"
						+ vip.getName()
						+ ",��"
						+ (Integer.parseInt(discount) / 10)
						+ "���ŻݵĻ������ڴ�"
						+ (vip.getDiscount() / 10)
						+ "��Ϊ"
						+ ((commodityVO.getOriginalPrice()
								* Integer.parseInt(discount) / 100)
								* vip.getDiscount() / 100);
			}
			else
			{
				hint = "";
			}
			hint = "�㹺��"
					+ commodityVO.getPropName()
					+ "��1��ԭ��"
					+ commodityVO.getOriginalPrice()
					+ ""+GameConfig.getYuanbaoName()+"��"
					+ (Integer.parseInt(discount) / 10)
					+ "���Żݺ�"
					+ (commodityVO.getOriginalPrice()
							* Integer.parseInt(discount) / 100) + ""+GameConfig.getYuanbaoName()+"��" + hint;
			request.setAttribute("result", hint);
			return mapping.findForward("msghint");
		}
	}

	/**
	 * ����ʽ��Ϣ�����������
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
			request.setAttribute("result", "����"+GameConfig.getYuanbaoName()+"��������!<br/>");
			return mapping.findForward("msghint");
		}
		request.setAttribute("result", display);
		return mapping.findForward("msghint");
	}

	/**
	 * �����Ա
	 */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		String prop_id = request.getParameter("prop_id").trim();
		String discount = request.getParameter("discount").trim();
		// ����ͨ������ID �õ��̳ǵ���Ϣ
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getPropCommodityInfo(prop_id);
		if (commodityVO != null)
		{
			commodityVO.setDiscount(Integer.parseInt(discount));
			String hint = mallService.buy(roleInfo, commodityVO, 1 + "");// ����
			if (hint != null)
			{
				request.setAttribute("result", hint);
				return mapping.findForward("msghint");
			}
			else
			{
				Vip vip = roleInfo.getTitleSet().getVIP();
				if (vip != null)
				{// ��VIP��Ա
					hint = "����"
							+ vip.getName()
							+ ",��"
							+ (Integer.parseInt(discount) / 10)
							+ "���ŻݵĻ������ڴ�"
							+ (vip.getDiscount() / 10)
							+ "��Ϊ"
							+ ((commodityVO.getOriginalPrice()
									* Integer.parseInt(discount) / 100)
									* vip.getDiscount() / 100);
				}
				else
				{
					hint = "";
				}
				hint = "�㹺��"
						+ commodityVO.getPropName()
						+ "��1��ԭ��"
						+ commodityVO.getOriginalPrice()
						+ ""+GameConfig.getYuanbaoName()+"��"
						+ (Integer.parseInt(discount) / 10)
						+ "���Żݺ�"
						+ (commodityVO.getOriginalPrice()
								* Integer.parseInt(discount) / 100) + ""+GameConfig.getYuanbaoName()+"��"
						+ hint;
				roleInfo.getBasicInfo().updateAddCurExp(1);
				request.setAttribute("result", hint);
				return mapping.findForward("msghint");
			}
		}
		else
		{
			String result = "���ݴ�������ϵ����Ա";
			request.setAttribute("result", result);
			return mapping.findForward("msghint");
		}

	}
}