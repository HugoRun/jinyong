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
		// 创建时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量
		String ip=request.getRemoteAddr();
		
		logger.info("############平台登陆##############");
		String channel = request.getParameter("channel");//渠道
		String product = request.getParameter("product");//产品编码
		String timestamp = request.getParameter("timestamp");//时间
		String loginType = request.getParameter("loginType");//登陆类型
		String name = request.getParameter("name");//用户名
		String received_verify_string = request.getParameter("received_verify_string");// MD5码
		String key = request.getParameter("key");//渠道验证码
		//String user_id = request.getParameter("user_id");
		
		String verify_string = "channel="+channel+"&username="+name+"&key="+key+"&timestamp="+timestamp+"&product="+product;
		System.out.println(verify_string);
		verify_string = MD5Util.md5Hex(verify_string);
		
		System.out.println(verify_string);
		logger.info("IP:"+ip);
		int u_pk = -1;
		
		PassportService passportService = new PassportService();
		u_pk = passportService.loginFromPingTai(channel, product, timestamp, loginType, name,received_verify_string,key,ip);
		
		if( u_pk ==-1 )//登陆验证失败
		{
			logger.info("用户验证失败");
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
		//session.setAttribute("login_params", login_params);//登陆参数 
		return mapping.findForward("success");
	}
}
