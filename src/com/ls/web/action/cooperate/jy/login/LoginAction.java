/**
 * 
 */
package com.ls.web.action.cooperate.jy.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.pub.constant.Channel;
import com.ls.web.service.cooperate.dangle.PassportService;


/**
 * @author Administrator 
 * 
 */
public class LoginAction extends Action
{
	Logger logger = Logger.getLogger(LoginAction.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String ip=request.getRemoteAddr();
		logger.info("############金银岛户登陆##############");
		String user_id = request.getParameter("userid");
		String user_name = request.getParameter("username");
		
		logger.info("user_id:"+user_id);
		logger.info("user_name:"+user_name);
		
		int u_pk = -1;
		
		PassportService passportService = new PassportService();
		
		u_pk = passportService.loginFromJY(user_id, user_name,ip);
		
		//request.getSession().setAttribute("channel_id", Channel.JINYINDAO+"");
		
		if( u_pk ==-1 )//登陆验证失败
		{
			logger.info("用户验证失败");
			return mapping.findForward("fail");
		}
		
		
		request.setAttribute("uPk", u_pk + "");
		
		request.setAttribute("user_id", user_id);
		request.setAttribute("user_name",user_name);
		return mapping.findForward("success");
	}
}
