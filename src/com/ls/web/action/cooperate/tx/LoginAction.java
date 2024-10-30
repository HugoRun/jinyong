package com.ls.web.action.cooperate.tx;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ls.ben.vo.cooperate.dangle.PassportVO;
import com.ls.pub.constant.Channel;
import com.ls.web.service.cooperate.dangle.PassportService;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LoginAction extends Action
{
	Logger logger = Logger.getLogger("log.pay");

	/**
	 * TX��������¼
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

		String ownerId = request.getParameter("tx-owner");
		String appId = request.getParameter("tx-app");
		String sessionId = request.getParameter("tx-session");
		String uid = request.getParameter("uid");
		
		logger.info("########���µ�½########");
		logger.info("tx-owner:"+ownerId);
		logger.info("tx-app:"+appId);
		logger.info("tx-session:"+sessionId);
		logger.info("uid:"+uid);
		
		if (ownerId != null)
		{
			String login_ip = request.getRemoteAddr();
			PassportService passportService = new PassportService();
			PassportVO passport = passportService.loginFromTxw(ownerId + "",
					login_ip);
			if (passport == null || passport.getUPk() == -1)// ��½��֤ʧ��
			{
				logger.info("�û���½ʧ��������������µ�½");
				return mapping.findForward("fail");
			}
			else
			{
				HttpSession session = request.getSession();
				String params = request.getQueryString();
				int uPk = passport.getUPk();
				session.setAttribute("uPk", uPk + "");
				session.setAttribute("userId", ownerId + "");
				session.setAttribute("user_name", ownerId + "");
				session.setAttribute("channel_id", Channel.TXW + "");
				session.setAttribute("params", params);// ��½����
				return mapping.findForward("success");
			}
		}
		logger.info("�û���֤ʧ��");
		return mapping.findForward("fail");
	}
}
