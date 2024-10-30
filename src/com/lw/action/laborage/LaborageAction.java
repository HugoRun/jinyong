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
	/** ������ȡ����ҳ�� */
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

			request.setAttribute("display", "����������ʱ��Ϊ"
					+ dao.getPlayerOnlineTime(roleInfo.getBasicInfo().getPPk())
					+ "����,δ�ﵽ��ȡ���ʵ�ʱ��!");
		}
		else
		{
			String display = "���ܿ���ȡ����"
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

	/** ��ȡ���� */
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
			request.setAttribute("display", "����������ʱ��Ϊ"
					+ dao.getPlayerOnlineTime(roleInfo.getBasicInfo().getPPk())
					+ "����<br/>ֻ��һ��������ʱ��ﵽ210�������ϵ���Ҳſ���ȡ����!");
			return mapping.findForward("display");
		}
		else
		{
			if (dao.getPlayerCatch(roleInfo.getBasicInfo().getPPk()) == 1)
			{
				request.setAttribute("display", "���Ѿ���ȡ������(" + se.getFirstDay()
						+ "~" + se.getLastDay() + ")����!");
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
