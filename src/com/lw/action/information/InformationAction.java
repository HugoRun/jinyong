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
			request.setAttribute("display", "һ���˺�ֻ�ܲμ�һ�θü���,�����˺��Ѿ��μӹ����λ!");
			return mapping.findForward("display");
		}
		else
		{
			if (roleInfo.getBasicInfo().getGrade() < 50)
			{
				request.setAttribute("display", "���ĵȼ�����50��!���Ͱ�!");
				return mapping.findForward("display");
			}
			request.setAttribute("type", "50��");
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
			request.setAttribute("display", "һ���˺�ֻ�ܲμ�һ�θü���,�����˺��Ѿ��μӹ����λ!");
			return mapping.findForward("display");
		}
		else
		{
			if (roleInfo.getBasicInfo().getGrade() < 60)
			{
				request.setAttribute("display", "���ĵȼ�����60��!���Ͱ�!");
				return mapping.findForward("display");
			}
			request.setAttribute("type", "60��");
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
					"һ�����ֻ����һ�������μӱ��λ,�����ڵİ���Ѿ��μӹ����λ!");
			return mapping.findForward("display");
		}
		else
		{
			if (se.getInformationByTong(roleInfo.getBasicInfo().getPPk()) == false)
			{	
				request.setAttribute("display", "�������ǰ���!���Ͱ�!");
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
			request.setAttribute("display", "�ύ�ɹ�");
			return mapping.findForward("display");
		}
		request.setAttribute("display", "����������������������!");
		return mapping.findForward("display");
	}
}
