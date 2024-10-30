package com.lw.action.information;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;
import com.lw.dao.information.InformationDAO;
import com.lw.service.information.InformationService;

public class InformationAction extends DispatchAction
{
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward("information_menu");
	}

	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		InformationService se = new InformationService();
		if (se.getInformationBy50(roleInfo.getBasicInfo().getUPk()) == false)
		{
			request.setAttribute("display", "一个账号只能参加一次该级别活动,您的账号已经参加过本次活动!");
			return mapping.findForward("display");
		}
		else
		{
			if (roleInfo.getBasicInfo().getGrade() < 50)
			{
				request.setAttribute("display", "您的等级不够50级!加油啊!");
				return mapping.findForward("display");
			}
			request.setAttribute("type", "50级");
			return mapping.findForward("information_put");
		}
	}

	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		InformationService se = new InformationService();
		if (se.getInformationBy60(roleInfo.getBasicInfo().getUPk()) == false)
		{
			request.setAttribute("display", "一个账号只能参加一次该级别活动,您的账号已经参加过本次活动!");
			return mapping.findForward("display");
		}
		else
		{
			if (roleInfo.getBasicInfo().getGrade() < 60)
			{
				request.setAttribute("display", "您的等级不够60级!加油啊!");
				return mapping.findForward("display");
			}
			request.setAttribute("type", "60级");
			return mapping.findForward("information_put");
		}
	}

	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		InformationService se = new InformationService();
		if (se.getInformationByTong(roleInfo.getBasicInfo().getUPk(), String.valueOf(roleInfo.getBasicInfo().getFaction().getId())) == false)
		{
			request.setAttribute("display",
					"一个帮会只能有一个帮主参加本次活动,您所在的帮会已经参加过本次活动!");
			return mapping.findForward("display");
		}
		else
		{
			if (se.getInformationByTong(roleInfo.getBasicInfo().getPPk()) == false)
			{	
				request.setAttribute("display", "您还不是帮主!加油啊!");
				return mapping.findForward("display");
			}
			request.setAttribute("type", 3);
			return mapping.findForward("information_put");
		}
	}

	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		int uPk = roleInfo.getBasicInfo().getUPk();
		String id = request.getParameter("id").toString();
		String reid = request.getParameter("reid").toString();
		String type = request.getParameter("type").toString();
		if (id.equals(reid))
		{
			InformationDAO dao = new InformationDAO();
			dao.setId(uPk, id, type);
			request.setAttribute("display", "提交成功");
			return mapping.findForward("display");
		}
		request.setAttribute("display", "您的输入有误请重新输入!");
		return mapping.findForward("display");
	}
}
