package com.lw.action.player;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.lw.service.player.GmKickService;
import com.pub.ben.info.Expression;

public class GmKickAction extends DispatchAction
{
	/** 跳转 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward("input");
	}

	/** 踢人 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String p_name = request.getParameter("p_name");
		if (Expression.hasPublish(p_name) == -1)
		{
			request.setAttribute("display", "输入有误");
			return mapping.findForward("display");
		}
		GmKickService gmKickService = new GmKickService();
		String display = gmKickService.kickPlayer(p_name);
		request.setAttribute("display", display);
		return mapping.findForward("display");
	}
}
