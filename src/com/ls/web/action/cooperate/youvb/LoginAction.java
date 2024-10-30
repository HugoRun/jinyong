package com.ls.web.action.cooperate.youvb;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ls.pub.yeepay.HttpUtils;
import com.ls.web.service.cooperate.dangle.PassportService;

public class LoginAction extends Action
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * ����������¼
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
		HttpSession session = request.getSession();
		String xsid = request.getParameter("xsid");
		String login_params = request.getQueryString();
		String login_ip = request.getRemoteAddr();
		Map<String, String> map = new HashMap<String, String>();
		map.put("xsid", xsid);
		String url = "http://login.youvb.cn/ul/y_xsid.jsp";
		List result = null;
		try
		{
			// ��������
			result = HttpUtils.URLGet(url, map);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
		String rs = "";
		for (int i = 0; i < result.size(); i++)
		{
			rs = (String) result.get(i);
			if (rs != null && !rs.equals(""))
			{
				break;
			}
		}
		if (rs != null && rs.equals("1"))// ��֤����ɵ��û�
		{
			logger.info("�û���֤ʧ��");
			return mapping.findForward("fail");
		}
		else
		{
			String[] passport_youvb = rs.split("@@@@@");

			PassportService passportService = new PassportService();
			PassportVO passport = passportService.loginFromYouvb(
					passport_youvb[0], login_ip);

			if (passport == null || passport.getUPk() == -1)// ��½��֤ʧ��
			{
				logger.info("�û���֤ʧ��");
				return mapping.findForward("fail");
			}
			else
			{
				login_params = login_params.replaceAll("&", "&amp;");
				int uPk = passport.getUPk();
				String skyid = passport.getUserId();
				session.setAttribute("uPk", uPk + "");
				session.setAttribute("skyid", skyid);
				session.setAttribute("xsid", xsid);
				session.setAttribute("user_name", skyid);
				session.setAttribute("channel_id", Channel.YOUVB + "");
				session.setAttribute("login_params", login_params);// ��½����
				session.setAttribute("ssid", passport_youvb[0]);
				return mapping.findForward("success");
			}
		}
	}
}
