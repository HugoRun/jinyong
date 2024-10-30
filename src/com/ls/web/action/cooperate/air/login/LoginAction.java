package com.ls.web.action.cooperate.air.login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.kong.sns.rest.client.Client;
import net.kong.sns.rest.client.ClientFactory;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.ben.vo.cooperate.dangle.PassportVO;
import com.ls.pub.constant.Channel;
import com.ls.web.service.cooperate.dangle.PassportService;

public class LoginAction extends Action
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * ������������¼
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws IOException
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String sessionKey = request.getParameter("SessionKey");
		
		if( sessionKey!=null )
		{
			Client client;
			try
			{
				client = ClientFactory.createInstance(sessionKey);
    			Long userId = client.getLoggedInUserRest();
    			
    			String login_ip=request.getRemoteAddr();
    			PassportService passportService = new PassportService();
    			PassportVO passport = passportService.loginFromAir(userId+"",login_ip);
    			if (passport == null || passport.getUPk() == -1)// ��½��֤ʧ��
    			{
    				logger.info("�û���½ʧ����ӿ��������µ�½");
    				return mapping.findForward("fail");
    			}
    			else
    			{
    				HttpSession session = request.getSession();
    				String params = request.getQueryString(); 
    				int uPk = passport.getUPk();
    				session.setAttribute("uPk", uPk+"");
    				session.setAttribute("sessionKey", sessionKey);
    				session.setAttribute("userId", userId+"");
    				session.setAttribute("user_name", userId+"");
    				session.setAttribute("channel_id", Channel.AIR + "");
    				session.setAttribute("params", params);// ��½����
    				return mapping.findForward("success");
    			}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}
		logger.info("�û���֤ʧ��");
		return mapping.findForward("fail");
	}
}
