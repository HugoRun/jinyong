/**
 * 
 */
package com.ls.web.action.cooperate.pingtai;

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
import com.ls.pub.util.encrypt.MD5Util;
import com.ls.web.service.cooperate.dangle.PassportService;
import com.ls.web.service.login.LoginService;


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
		String ip=request.getRemoteAddr();
		
		logger.info("############ƽ̨��½##############");
		String channel = request.getParameter("channel");//����
		String product = request.getParameter("product");//��Ʒ����
		String timestamp = request.getParameter("timestamp");//ʱ��
		String loginType = request.getParameter("loginType");//��½����
		String name = request.getParameter("name");//�û���
		String received_verify_string = request.getParameter("received_verify_string");// MD5��
		String key = request.getParameter("key");//������֤��
		//String user_id = request.getParameter("user_id");
		
		String verify_string = "channel="+channel+"&username="+name+"&key="+key+"&timestamp="+timestamp+"&product="+product;
		System.out.println(verify_string);
		verify_string = MD5Util.md5Hex(verify_string);
		
		System.out.println(verify_string);
		logger.info("IP:"+ip);
		int u_pk = -1;
		
		PassportService passportService = new PassportService();
		u_pk = passportService.loginFromPingTai(channel, product, timestamp, loginType, name,received_verify_string,key,ip);
		
		if( u_pk ==-1 )//��½��֤ʧ��
		{
			logger.info("�û���֤ʧ��");
			return mapping.findForward("fail");
		} 
		
		//login_params = login_params.replaceAll("&", "&amp;");
		
		HttpSession session = request.getSession();
		session.setAttribute("uPk", u_pk+"");
		session.setAttribute("user_name", name);
		session.setAttribute("channel_id", channel);
		session.setAttribute("product", product);
		session.setAttribute("timestamp", Time);
		session.setAttribute("key", key);
		session.setAttribute("received_verify_string", verify_string);
		//session.setAttribute("login_params", login_params);//��½���� 
		return mapping.findForward("success");
	}
}
