package com.ls.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.actions.DispatchAction;

import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;

public class ActionBase extends DispatchAction
{
	/**
	 * 得到分页页数
	 * @param request
	 * @return
	 */
	protected int getPageNo(HttpServletRequest request)
	{
		String page_no_str = request.getParameter("page_no");
		
		if( page_no_str==null )
		{
			page_no_str  = (String) request.getSession().getAttribute("page_no");
			if( page_no_str==null )
			{
				page_no_str = "1";
			}
		}
		request.getSession().setAttribute("page_no",page_no_str);
		return Integer.parseInt(page_no_str);
	}
	
	protected void setHint(HttpServletRequest request,String hint)
	{
		request.setAttribute("hint", hint+"<br/>");
	}
	
	protected void setError(HttpServletRequest request,String hint)
	{
		request.setAttribute("error", hint+"<br/>");
	}
	
	protected RoleEntity getRoleEntity(HttpServletRequest request)
	{
		RoleService roleService = new RoleService();
		return roleService.getRoleInfoBySession(request.getSession());
	}
	
	protected ActionForward dispath(HttpServletRequest request, HttpServletResponse response,String url)
	{
		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception e) {}
		return null;
	}
	/**
	 * 返回场景
	 * @return
	 */
	protected ActionForward returnScene(HttpServletRequest request, HttpServletResponse response)
	{
		return this.dispath(request, response, "/pubaction.do");
	}
	
}