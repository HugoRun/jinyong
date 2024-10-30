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
	 * ����������¼9
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
		String sign = request.getParameter("sign");// �ж�����ǲ������µ�½
		session.setAttribute("wm", wm);
		String wm_bak = (String) session.getAttribute("wm");
		String sina_uid = request.getParameter("sina_uid");// UID�ַ���
		String key = request.getParameter("key");// UID�ַ���
		String sina_uid_key = sina_uid+"sina_uid_jytiexue";
		String key_bak = MD5Util.md5Hex(sina_uid_key);
		if (GameConfig.getGameState() == 2)// �ж���Ϸ��״̬
		{
			// ��Ϸ״̬Ϊ�����ڲ�����״̬
			// ��̬����
			SystemNotifyService systemNotifyService = new SystemNotifyService();
			SystemNotifyVO first_notify_info = systemNotifyService
					.getFirstNotifyInfo();
			request.setAttribute("first_notify_info", first_notify_info);
			return mapping.findForward("game_test_state");
		}

		String login_params = request.getQueryString();
		String login_ip = request.getRemoteAddr();

		LoginService loginService = new LoginService();

		// �ж����������Ƿ�ﵽ����
		if (loginService.isFullOnlineRoleNum())
		{
			// ���������Ѵ�ϵͳ��������
			return mapping.findForward("user_num_limit_hint");
		}

		if (sina_uid == null || sina_uid.equals("null") || sina_uid.equals(""))
		{
			logger.info("�û���֤ʧ��");
			return mapping.findForward("fail");
		}
		if(!key_bak.equals(key)){
			logger.info("�û���֤ʧ��");
			return mapping.findForward("fail");
		}
		
		logger.info("�����û���½:sina_uid=" + sina_uid);

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

		if (passport == null || passport.getUPk() == -1)// ��½��֤ʧ��
		{
			logger.info("�û���֤ʧ��");
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
		session.setAttribute("login_params", login_params);// ��½����
		session.setAttribute("ssid", sina_uid);
		return mapping.findForward("success");

	}
}
