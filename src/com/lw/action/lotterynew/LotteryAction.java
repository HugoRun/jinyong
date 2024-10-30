package com.lw.action.lotterynew;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.web.service.player.RoleService;
import com.lw.service.lotterynew.LotteryOutInfoService;
import com.lw.service.lotterynew.LotteryService;
import com.lw.service.lotterynew.PlayerLotteryService;

public class LotteryAction extends DispatchAction
{
	Logger logger = Logger.getLogger(LotteryAction.class);

	/** ����������* */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		LotteryOutInfoService lotteryOutInfoService = new LotteryOutInfoService();
		String display = lotteryOutInfoService.getLotteryMenu();
		request.setAttribute("display", display);
		return mapping.findForward("menu");
	}

	/** ���ɲ�Ʊ* */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String lottery_zhu = request.getParameter("zhu");
		String num1 = request.getParameter("num1");
		String num2 = request.getParameter("num2");
		String num3 = request.getParameter("num3");
		String num4 = request.getParameter("num4");
		String lottery_num = num1 + "," + num2 + "," + num3 + "," + num4;
		if (lottery_zhu == null)
		{
			request.setAttribute("display", "������Ͷע!");
			return mapping.findForward("display");
		}
		RoleService roleService = new RoleService();
		RoleEntity role_info = roleService.getRoleInfoBySession(request
				.getSession());
		LotteryOutInfoService lotteryOutInfoService = new LotteryOutInfoService();
		String display = lotteryOutInfoService.playerBuyLottery(role_info,
				lottery_num, Integer.parseInt(lottery_zhu));
		request.setAttribute("display", display);
		return mapping.findForward("display");
	}

	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity role_info = roleService.getRoleInfoBySession(request
				.getSession());
		if (role_info.getBasicInfo().getGrade() < 5)
		{
			request.setAttribute("display", "���ĵȼ�С��5�����ܲμӾ���!");
			return mapping.findForward("display");
		}
		PlayerLotteryService playerLotteryService = new PlayerLotteryService();
		boolean can = playerLotteryService.playerCanGetLottery(role_info
				.getBasicInfo().getPPk());
		if (can == false)
		{
			request.setAttribute("display", "���Ѿ��μӹ����ھ���,�����ĵȴ�����!");
			return mapping.findForward("display");
		}
		Date date = new Date();
		int hours = date.getHours();
		if (hours == 23)
		{
			hours = 0;
		}
		else
		{
			hours = hours + 1;
		}
		LotteryService ls = new LotteryService();
		String lottery_date = ls.getSysLotteryDate().substring(1, 6);
		int zhu = playerLotteryService.getLotteryTimeToday(role_info
				.getBasicInfo().getPPk(), lottery_date);
		int zhong = playerLotteryService.getLotteryBonusTimeToday(role_info
				.getBasicInfo().getPPk(), lottery_date);
		int time = 60 - date.getMinutes();
		StringBuffer sb = new StringBuffer();
		sb.append(role_info.getBasicInfo().getName() + "��ӭ���μӾ��»!<br/>");
		sb.append("������Ͷע" + zhu + "��,�н�" + zhong + "��!<br/>");
		int min = 60 - date.getMinutes();
		if (min == 1)
		{
			sb.append("������עʱ���Ѿ�����,��ȴ����ڿ���.���ڿ���ʱ��Ϊ" + hours + "��!");
			request.setAttribute("display", sb.toString());
			return mapping.findForward("display");
		}
		sb.append("����ʣ����עʱ��" + time + "����!<br/>");
		sb.append("ÿע���50"+GameConfig.getYuanbaoName()+"<br/>");
		sb.append("����ע��:<br/>");
		request.setAttribute("display", sb.toString());
		return mapping.findForward("yb");
	}

	/** ��ȡ�����б�(��ҳ) */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pageNO_str = request.getParameter("pageNO");
		int pageNO = 0;
		if (pageNO_str != null)
		{
			pageNO = Integer.parseInt(pageNO_str);
		}
		RoleService roleService = new RoleService();
		RoleEntity role_info = roleService.getRoleInfoBySession(request
				.getSession());
		LotteryOutInfoService lotteryOutInfoService = new LotteryOutInfoService();
		String display = lotteryOutInfoService.playerCatchMenu("n5", role_info
				.getBasicInfo().getName(), role_info.getBasicInfo().getPPk(),
				pageNO, request, response);
		request.setAttribute("display", display);
		return mapping.findForward("display");
	}

	/** �����ȡ����* */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity role_info = roleService.getRoleInfoBySession(request
				.getSession());
		String lottery_date = request.getParameter("lottery_date");
		if (lottery_date.length() != 8)
		{
			request.setAttribute("display", "Զ���Ʊ����ϧ����!");
			return mapping.findForward("display");
		}
		LotteryService ls = new LotteryService();
		String date_bak = ls.getSysLotteryDate();
		if (lottery_date.equals(date_bak))
		{
			request.setAttribute("display", "Զ���Ʊ����ϧ����!");
			return mapping.findForward("display");
		}
		LotteryOutInfoService lotteryOutInfoService = new LotteryOutInfoService();
		String display = lotteryOutInfoService.playerCatchBonus(role_info,
				lottery_date);
		request.setAttribute("display", display);
		return mapping.findForward("display");
	}

	/** ���¹���* */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward("rule");
	}

	/** ��ʷ��¼(ϵͳ����)(��ҳ)* */
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pageNO_str = request.getParameter("pageNO");
		int pageNO = 0;
		if (pageNO_str != null)
		{
			pageNO = Integer.parseInt(pageNO_str);
		}
		RoleService roleService = new RoleService();
		RoleEntity role_info = roleService.getRoleInfoBySession(request
				.getSession());
		LotteryOutInfoService lotteryOutInfoService = new LotteryOutInfoService();
		String display = lotteryOutInfoService.sysLotteryHistory("n8",
				role_info.getBasicInfo().getName(), pageNO, request, response);
		request.setAttribute("display", display);
		return mapping.findForward("display");
	}

	/** ��ʷ��¼(�������)(��ҳ)* */
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pageNO_str = request.getParameter("pageNO");
		int pageNO = 0;
		if (pageNO_str != null)
		{
			pageNO = Integer.parseInt(pageNO_str);
		}
		RoleService roleService = new RoleService();
		RoleEntity role_info = roleService.getRoleInfoBySession(request
				.getSession());
		LotteryOutInfoService lotteryOutInfoService = new LotteryOutInfoService();
		String display = lotteryOutInfoService.playerLotteryHistory("n9",
				role_info.getBasicInfo().getPPk(), role_info.getBasicInfo()
						.getName(), pageNO, request, response);
		request.setAttribute("display", display);
		return mapping.findForward("display");
	}

	public ActionForward n10(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String zhu = request.getParameter("zhu");
		request.setAttribute("zhu", zhu);
		return mapping.findForward("num1");
	}

	public ActionForward n11(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String zhu = request.getParameter("zhu");
		String num1 = request.getParameter("num1");
		request.setAttribute("zhu", zhu);
		request.setAttribute("num1", num1);
		return mapping.findForward("num2");
	}

	public ActionForward n12(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String zhu = request.getParameter("zhu");
		String num1 = request.getParameter("num1");
		String num2 = request.getParameter("num2");
		request.setAttribute("zhu", zhu);
		request.setAttribute("num1", num1);
		request.setAttribute("num2", num2);
		return mapping.findForward("num3");
	}

	public ActionForward n13(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String zhu = request.getParameter("zhu");
		String num1 = request.getParameter("num1");
		String num2 = request.getParameter("num2");
		String num3 = request.getParameter("num3");
		request.setAttribute("zhu", zhu);
		request.setAttribute("num1", num1);
		request.setAttribute("num2", num2);
		request.setAttribute("num3", num3);
		return mapping.findForward("num4");
	}
}
