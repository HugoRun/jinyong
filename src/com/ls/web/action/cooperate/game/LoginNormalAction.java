package com.ls.web.action.cooperate.game;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.pub.config.GameConfig;
 import com.ls.web.service.cooperate.dangle.PassportService;
import com.lw.service.systemnotify.SystemNotifyService;
import com.lw.vo.systemnotify.SystemNotifyVO;

public class LoginNormalAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String account = request.getParameter("account");
		String md5string = request.getParameter("md5string");
		String timestamp = request.getParameter("timestamp");
		String gameid = request.getParameter("gameid");
		String login_ip = request.getRemoteAddr();
		String login_params = request.getQueryString();
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
		
		PassportService passportService = new PassportService();
		int u_pk = passportService.loginall(account, md5string, timestamp, gameid, login_ip);
		if(u_pk == -1){
			return mapping.findForward("fail");
		}
		HttpSession session = request.getSession();
		session.setAttribute("ssid", account);
		session.setAttribute("uPk", u_pk + "");
		session.setAttribute("user_name", account);
		session.setAttribute("channel_id", gameid);
		session.setAttribute("login_params", login_params);// 登陆参数
		return mapping.findForward("success");
	}

}
