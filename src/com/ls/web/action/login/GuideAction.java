package com.ls.web.action.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.model.user.RoleEntity;
import com.ls.web.action.ActionBase;
import com.ls.web.service.login.LoginService;

/**
 * @author ls
 * 新手引导
 */
public class GuideAction extends ActionBase
{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String step = request.getParameter("step");
		if(StringUtils.isEmpty(step)==true)
		{
			step = "cartoon1";
			String p_pk = (String) request.getSession().getAttribute("pPk");
			if( StringUtils.isEmpty(p_pk)==true)
			{
				return super.dispath(request, response, "/login.do?cmd=n3");
			}
		}
		
		RoleEntity role_info = this.getRoleEntity(request);
		
		if( step.indexOf("accept_task")!=-1 )
		{
			request.setAttribute("role_name", role_info.getName());
			step = step+"_"+role_info.getBasicInfo().getPRace();
		}
		else if( step.equals("end"))
		{
			//新手引导结束
			//登陆，初始化
			return this.login(mapping, form, request, response);
		}
		
		return mapping.findForward(step);
	}
	
	/**
	 * 新手登陆
	 */
	private ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String p_pk = (String) request.getSession().getAttribute("pPk");
		if( StringUtils.isEmpty(p_pk)==true)
		{
			return super.dispath(request, response, "/login.do?cmd=n3");
		}
		LoginService loginService = new LoginService();
		loginService.loginRookieRole(p_pk,request);
		return super.dispath(request, response, "/scene.do");
	}
	
}
