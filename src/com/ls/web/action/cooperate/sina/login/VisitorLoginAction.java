package com.ls.web.action.cooperate.sina.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.vo.cooperate.dangle.PassportVO;
import com.ls.pub.constant.Channel;
import com.ls.web.service.cooperate.dangle.PassportService;
import com.ls.web.service.cooperate.sina.LoginService;

public class VisitorLoginAction extends DispatchAction
{
	Logger logger = Logger.getLogger("log.action");

	/** �ο͵�½ҳ��* */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward("visitor_choose");
	}

	/** �������ת��������ɫҳ��* */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward("role_choose");
	}

	/** ʹ���οͺŵ�½ */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
 		String passport_visitor = request.getParameter("passport_visitor");
		LoginService ls = new LoginService();
		String passport_visitor_bak = ls.loginByVisitorSina(passport_visitor);

		if (passport_visitor != null && passport_visitor.equals(""))
		{
			if (passport_visitor_bak == null || passport_visitor_bak.equals(""))
			{
				request.setAttribute("passport_visitor", passport_visitor);
				return mapping.findForward("relation_sucess");
			}
			else
			{
				request.setAttribute("passport_visitor", passport_visitor_bak);
				String outview = passport_visitor_bak.replace("visitor", "");
				request.setAttribute("display", "�����οͺ�Ϊ" + outview + ",���μ������οͺ�!");
				return mapping.findForward("relation_sucess");
			}
		}
		else
		{
			if (passport_visitor_bak == null || passport_visitor_bak.equals(""))
			{
				request.setAttribute("passport_visitor", "visitor"+passport_visitor);
				return mapping.findForward("relation_sucess");
			}
			else
			{
				request.setAttribute("passport_visitor", passport_visitor_bak);
				String outview = passport_visitor_bak.replace("visitor", "");
				request.setAttribute("display", "�����οͺ�Ϊ" + outview + ",���μ������οͺ�!");
				return mapping.findForward("relation_sucess");
			}
		}
	}

	/** �����ת�������ʺŵĵط� */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String passport_visitor = request.getParameter("passport_visitor");
		String sina_uid = request.getParameter("sina_uid_bak");
		if (passport_visitor == null || passport_visitor.equals(""))
		{
			if (sina_uid == null || sina_uid.equals(""))
			{
				return mapping.findForward("role_choose");
			}
			else
			{
				request.setAttribute("sina_uid_bak", sina_uid);
				request.setAttribute("display", "����������");
				return mapping.findForward("passport_relation");
			}
		}
		else
		{
			LoginService ls = new LoginService();
			String display = ls
					.relationPassportSina(sina_uid, passport_visitor);
			if (display.equals(""))
			{
				request.setAttribute("display", "�ʺŹ����ɹ�!");
				request.setAttribute("passport_visitor", sina_uid);
				request.setAttribute("sina_uid", sina_uid);
				return mapping.findForward("relation_sucess");
			}
			else
			{
				request.setAttribute("sina_uid_bak", sina_uid);
				request.setAttribute("display", display);
				return mapping.findForward("passport_relation");
			}
		}
	}

	// ������Ϸ
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession();
		String login_params = request.getQueryString();
		String login_ip = request.getRemoteAddr();
		String wm = (String)session.getAttribute("wm");
		String passport_visitor = request.getParameter("passport_visitor");
		PassportService passportService = new PassportService();
		PassportVO passport = passportService.loginFromSina(passport_visitor,
				login_ip,wm);

		if (passport == null || passport.getUPk() == -1)// ��½��֤ʧ��
		{
			logger.info("�û���֤ʧ��");
			return mapping.findForward("fail");
		}

		login_params = login_params.replaceAll("&", "&amp;");
		int uPk = passport.getUPk();
		String skyid = passport.getUserId();
		session.setAttribute("uPk", uPk + "");
		session.setAttribute("skyid", skyid);
		session.setAttribute("user_name", skyid);
		session.setAttribute("channel_id", Channel.SINA + "");
		session.setAttribute("login_params", login_params);// ��½����
		session.setAttribute("ssid", passport_visitor);
		return mapping.findForward("login_game");
	}
}
