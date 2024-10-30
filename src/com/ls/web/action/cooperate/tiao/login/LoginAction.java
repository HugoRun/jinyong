/**
 * 
 */
package com.ls.web.action.cooperate.tiao.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.ben.vo.cooperate.dangle.PassportVO;
import com.ls.pub.constant.Channel;
import com.ls.web.service.cooperate.dangle.PassportService;


/**
 * @author Administrator 
 * 
 */
public class LoginAction extends Action
{
	Logger logger = Logger.getLogger("log.service");
	
	/**
	 * ������½
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		String login_params = request.getQueryString(); 
		String ip=request.getRemoteAddr();
		
		logger.info("############�����û���½##############");
		String user_name = request.getParameter("user_name");
		String verify_string = request.getParameter("verify_string");
		
		logger.info("������½�ӿڴ������Ĳ���:"+login_params);
		
		logger.info("IP:"+ip);
		
		PassportService passportService = new PassportService();
		
		PassportVO passport_info = passportService.loginFromTiao( user_name, verify_string, ip);
		
		if( passport_info == null )//��½��֤ʧ��
		{
			logger.info("�û���֤ʧ��");
			return mapping.findForward("fail");
		} 
		
		login_params = login_params.replaceAll("&", "&amp;");
		
		HttpSession session = request.getSession();
		
		session.setAttribute("uPk", passport_info.getUPk()+"");
		session.setAttribute("user_name", user_name);
		session.setAttribute("channel_id", Channel.TIAO+"");
		session.setAttribute("login_params", login_params);//��½����
		return mapping.findForward("success");
	}
}
