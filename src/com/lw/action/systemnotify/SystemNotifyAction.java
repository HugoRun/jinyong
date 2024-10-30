package com.lw.action.systemnotify;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.lw.service.systemnotify.SystemNotifyService;
import com.lw.vo.systemnotify.SystemNotifyVO;

public class SystemNotifyAction extends DispatchAction
{
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		int id = Integer.parseInt(request.getParameter("id"));
		SystemNotifyService systemNotifyService = new SystemNotifyService();
		String display = systemNotifyService.getNotifyContent(id);
		if (display == null)
		{
			display = "暂无公告";
		}
		request.setAttribute("display", display);
		return mapping.findForward("display");
	}
	
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		SystemNotifyService systemNotifyService = new SystemNotifyService();
		List<SystemNotifyVO> notifylist_huodong = systemNotifyService.getNotifyTitle(2);
		request.setAttribute("notifylist_huodong", notifylist_huodong);
		return mapping.findForward("system_notify_list");
	}
	
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		int id = Integer.parseInt(request.getParameter("id"));
		SystemNotifyService systemNotifyService = new SystemNotifyService();
		String display = systemNotifyService.getNotifyContent(id);
		if (display == null)
		{
			display = "暂无公告";
		}
		request.setAttribute("display", display);
		return mapping.findForward("display_game");
	}
}
