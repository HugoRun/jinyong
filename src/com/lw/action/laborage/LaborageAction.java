package com.lw.action.laborage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;
import com.lw.dao.laborage.PlayerLaborageDao;
import com.lw.service.laborage.LaborageService;

public class LaborageAction extends DispatchAction
{
	/** 进入领取奖金页面 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());

		LaborageService se = new LaborageService();
		PlayerLaborageDao dao = new PlayerLaborageDao();
		if (se.getPlayerLaborageTime(roleInfo.getBasicInfo().getPPk()) == 0)
		{

			request.setAttribute("display", "您上周在线时间为"
					+ dao.getPlayerOnlineTime(roleInfo.getBasicInfo().getPPk())
					+ "分钟,未达到领取工资的时间!");
		}
		else
		{
			String display = "本周可领取工资"
					+ se.getLaborageView(se.getPlayerLaborageTime(roleInfo
							.getBasicInfo().getPPk())) + "!";
			request.setAttribute("display", display);
		}
		// request.setAttribute("date", "(" + se.getFirstDay() + "~"+
		// se.getLastDay() + ")");
		request.setAttribute("time", dao.getPlayerOnlineTime(roleInfo
				.getBasicInfo().getPPk()));
		return mapping.findForward("laborage_menu");
	}

	/** 获取奖金 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());

		LaborageService se = new LaborageService();
		PlayerLaborageDao dao = new PlayerLaborageDao();
		if (se.getPlayerLaborageTime(roleInfo.getBasicInfo().getPPk()) == 0)
		{
			request.setAttribute("display", "您上周在线时间为"
					+ dao.getPlayerOnlineTime(roleInfo.getBasicInfo().getPPk())
					+ "分钟<br/>只有一周内在线时间达到210分钟以上的玩家才可领取工资!");
			return mapping.findForward("display");
		}
		else
		{
			if (dao.getPlayerCatch(roleInfo.getBasicInfo().getPPk()) == 1)
			{
				request.setAttribute("display", "您已经领取了上周(" + se.getFirstDay()
						+ "~" + se.getLastDay() + ")工资!");
				return mapping.findForward("display");
			}
			String display = se.playerCatchMoney(roleInfo.getBasicInfo()
					.getPPk(), se.getPlayerLaborageTime(roleInfo.getBasicInfo()
					.getPPk()));
			request.setAttribute("display", display);
			return mapping.findForward("display");
		}
	}
}
