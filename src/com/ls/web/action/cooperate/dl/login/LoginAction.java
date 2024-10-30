/**
 * 
 */
package com.ls.web.action.cooperate.dl.login;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	Logger logger = Logger.getLogger("log.service");
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		// ����ʱ��
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String Time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
		
		String login_params = request.getQueryString(); 
		String ip=request.getRemoteAddr();
		
		logger.info("############�����û���½##############");
		String user_id = request.getParameter("userid");
		String user_name = request.getParameter("username");
		String verify_string = request.getParameter("verify-string");
		String timestamp = request.getParameter("timestamp");
		String encrypt_type = request.getParameter("encrypt-type");
		
		logger.info("���ֵ�½�ӿڴ������Ĳ���:"+login_params);
		
		logger.info("IP:"+ip);
		
		int u_pk = -1;
		
		PassportService passportService = new PassportService();
		
		u_pk = passportService.loginFromDangLe(user_id, user_name, verify_string, timestamp, encrypt_type,ip);
		
		if( u_pk ==-1 )//��½��֤ʧ��
		{
			logger.info("�û���֤ʧ��");
			return mapping.findForward("fail");
		} 
		
		login_params = login_params.replaceAll("&", "&amp;");
		
		HttpSession session = request.getSession();
		
		session.setAttribute("uPk", u_pk+"");
		session.setAttribute("user_name", user_name);
		session.setAttribute("channel_id", Channel.DANGLE+"");
		session.setAttribute("login_params", login_params);//��½����
		return mapping.findForward("success");
	}
}
