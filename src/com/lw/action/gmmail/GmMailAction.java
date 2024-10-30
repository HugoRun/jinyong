package com.lw.action.gmmail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;
import com.lw.service.gmmail.GmMailService;

public class GmMailAction extends DispatchAction
{

	// ҳ����ת
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward("GMmail_menu");
	}

	// ���ͷ���
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String content = request.getParameter("content");
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		GmMailService gs = new GmMailService();
		String hint = gs.sendMailToGM(roleInfo.getBasicInfo().getPPk(),
				roleInfo.getBasicInfo().getName(), content);
		if (hint == null)
		{
			request.setAttribute("hint", "����ʧ��!");
			return mapping.findForward("hint");
		}
		else
		{
			request.setAttribute("hint", hint);
			return mapping.findForward("hint");
		}
	}
	//��GM����BUG�ύ��mail
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String content = request.getParameter("content");
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		GmMailService gs = new GmMailService();
		String hint = gs.sendMailToGM(roleInfo.getBasicInfo().getPPk(),roleInfo.getBasicInfo().getName(), content);
		if (hint == null)
		{
			request.setAttribute("hint", "����ʧ�������³���!");
			return mapping.findForward("reporthint");
		}
		else
		{
			request.setAttribute("hint", hint);
			return mapping.findForward("reporthint");
		}
	}
}
