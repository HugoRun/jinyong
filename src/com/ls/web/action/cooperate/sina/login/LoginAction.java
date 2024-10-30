package com.ls.web.action.cooperate.sina.login;

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
import com.ls.pub.constant.Channel;
import com.ls.pub.util.encrypt.MD5Util;
import com.ls.web.service.cooperate.dangle.PassportService;
import com.ls.web.service.login.LoginService;
import com.lw.service.systemnotify.SystemNotifyService;
import com.lw.vo.systemnotify.SystemNotifyVO;

public class LoginAction extends Action
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * 新浪渠道登录9
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
		HttpSession session = request.getSession();
		String wm = request.getParameter("wm");
		String sign = request.getParameter("sign");// 判断玩家是不是重新登陆
		session.setAttribute("wm", wm);
		String wm_bak = (String) session.getAttribute("wm");
		String sina_uid = request.getParameter("sina_uid");// UID字符串
		String key = request.getParameter("key");// UID字符串
		String sina_uid_key = sina_uid+"sina_uid_jytiexue";
		String key_bak = MD5Util.md5Hex(sina_uid_key);
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

		if (sina_uid == null || sina_uid.equals("null") || sina_uid.equals(""))
		{
			logger.info("用户验证失败");
			return mapping.findForward("fail");
		}
		if(!key_bak.equals(key)){
			logger.info("用户验证失败");
			return mapping.findForward("fail");
		}
		
		logger.info("新浪用户登陆:sina_uid=" + sina_uid);

		PassportService passportService = new PassportService();

		PassportVO passport_bak = passportService.getPassportInfoByUserID(
				sina_uid, Channel.SINA);

		if (sign == null || sign.equals(""))
		{
			if (passport_bak == null)
			{
				request.setAttribute("sina_uid", sina_uid);
				request.setAttribute("sina_uid_bak", sina_uid);
				return mapping.findForward("passport_relation");
			}
		}

		PassportVO passport = passportService.loginFromSina(sina_uid,
				login_ip, wm);

		if (passport == null || passport.getUPk() == -1)// 登陆验证失败
		{
			logger.info("用户验证失败");
			return mapping.findForward("fail");
		}

		if (login_params != null)
		{
			login_params = login_params.replaceAll("&", "&amp;");
		}
		int uPk = passport.getUPk();
		String skyid = passport.getUserId();

		session.setAttribute("uPk", uPk + "");
		session.setAttribute("skyid", skyid);
		session.setAttribute("user_name", skyid);
		session.setAttribute("channel_id", Channel.SINA + "");
		session.setAttribute("login_params", login_params);// 登陆参数
		session.setAttribute("ssid", sina_uid);
		return mapping.findForward("success");

	}
}
