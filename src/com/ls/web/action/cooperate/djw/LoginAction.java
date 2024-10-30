package com.ls.web.action.cooperate.djw;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.ben.vo.cooperate.dangle.PassportVO;
import com.ls.pub.config.GameConfig;
import com.ls.web.service.cooperate.dangle.PassportService;
import com.ls.web.service.login.LoginService;
import com.lw.service.systemnotify.SystemNotifyService;
import com.lw.vo.systemnotify.SystemNotifyVO;

/**
 * MyEclipse Struts Creation date: 06-18-2009
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class LoginAction extends Action
{
	/*
	 * Generated Methods
	 */
	Logger logger = Logger.getLogger("log.service");

	/**
	 * JUU渠道登录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String user_id = request.getParameter("user-id");// 大家网帐号
		String timestamp = request.getParameter("timestamp");// UNIX时间戳
		String verify_string = request.getParameter("verify-string");// MD5加密
		String key = "eielwek9nea2oll1";
		String merchant_id = "oneshow";
		String game_id = "1";
		if (GameConfig.getGameState() == 2)// 判断游戏的状态
		{
			// 游戏状态为上线内部测试状态
			// 动态公告
			SystemNotifyService systemNotifyService = new SystemNotifyService();
			SystemNotifyVO first_notify_info = systemNotifyService
					.getFirstNotifyInfo();
			request.setAttribute("first_notify_info", first_notify_info);
			return mapping.findForward("game_test_state");
		}

		String login_params = request.getQueryString();
		String login_ip = request.getRemoteAddr();

		LoginService loginService = new LoginService();

		// 判断在线人数是否达到上线
		if (loginService.isFullOnlineRoleNum())
		{
			// 在线人数已达系统设置上线
			return mapping.findForward("user_num_limit_hint");
		}
		HttpSession session = request.getSession();
		session.setAttribute("ssid", user_id);

		logger.info("DJW用户登陆:ssid=" + user_id);
		PassportService passportService = new PassportService();
		PassportVO passport = passportService.loginFromDjw(user_id, timestamp, verify_string,
				login_ip);

		if (passport == null || passport.getUPk() == -1)// 登陆验证失败
		{
			logger.info("用户验证失败");
			return mapping.findForward("fail");
		}

		login_params = login_params.replaceAll("&", "&amp;");
		int uPk = passport.getUPk();
		String account_passport = passport.getUserId();

		session.setAttribute("uPk", uPk + "");
		session.setAttribute("ssid", account_passport);
		session.setAttribute("user_name", passport.getUserName());
		session.setAttribute("channel_id", 99 + "");
		session.setAttribute("login_params", login_params);// 登陆参数

		return mapping.findForward("success");
	}
}