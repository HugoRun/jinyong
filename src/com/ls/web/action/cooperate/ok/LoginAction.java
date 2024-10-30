package com.ls.web.action.cooperate.ok;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.ben.dao.register.RegisterDao;
import com.ls.ben.vo.cooperate.dangle.PassportVO;
import com.ls.pub.constant.Channel;
import com.ls.pub.util.http.HttpRequester;
import com.ls.pub.util.http.HttpRespons;
import com.ls.pub.util.http.parseContent.ParseNormalContent;
import com.ls.web.service.cooperate.dangle.PassportService;

public class LoginAction extends Action
{

	Logger logger = Logger.getLogger("log.service");

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String osid = request.getParameter("osid");
		HttpSession session = request.getSession();
		session.setAttribute("osid", osid);
		String login_params = request.getQueryString();
		String login_ip = request.getRemoteAddr();
		HttpRequester request_yanzheng = new HttpRequester();
		request_yanzheng.setDefaultContentEncoding("utf-8");

		HttpRespons response_yanzheng = null;
		try
		{
			response_yanzheng = request_yanzheng.sendGet("http://wapok.cn/n_gamecheck.php?osid=" + osid);// 提交验证请求
		}
		catch (IOException e)
		{
			logger.debug("ok平台osid验证异常");
			e.printStackTrace();
			return mapping.findForward("fail");
		}

		ParseNormalContent parseContent = new ParseNormalContent();
		Map<String, String> result = parseContent.parse(response_yanzheng
				.getContent());// 得到解析后的响应结果
		String account = null;
		Set<String> keys = result.keySet();
		for(String key:keys )
		{
			if( key.indexOf("result10")!=-1 )
			{
				account = result.get(key);
			}
		}

		if (result != null && account != null)// 表示osid验证成功
		{
			if (!account.equals("") && !account.equals("null"))
			{
				// 正常登陆

				PassportVO passport = null;
				int u_pk = -1;
				PassportService passportService = new PassportService();
				passport = passportService.getPassportInfoByUserID(osid,
						Channel.OKP);
				if (passport == null)// 添加新用户
				{
					u_pk = passportService.addNewUser(osid, account,
							Channel.OKP, login_ip);
					if( u_pk==-1 )
					{
						return mapping.findForward("fail");
					}
					passport = new PassportVO();
					passport.setUPk(u_pk);
				}
				else
				{
					u_pk = passport.getUPk();
					RegisterDao dao = new RegisterDao();
					dao.updateIp(u_pk, login_ip);
				}
				passport.setUserId(osid);
				login_params = login_params.replaceAll("&", "&amp;");
				int uPk = passport.getUPk();
				String skyid = passport.getUserId();
				session.setAttribute("uPk", uPk + "");
				session.setAttribute("skyid", skyid);
				session.setAttribute("user_name", passport.getUserName());
				session.setAttribute("channel_id", Channel.OKP + "");
				session.setAttribute("login_params", login_params);// 登陆参数
				return mapping.findForward("success");
			}
			else
			{
				logger.debug("ok平台osid验证异常,osid：" + osid + "无效");
				return mapping.findForward("fail");
			}
		}
		else
		{
			logger.debug("ok平台osid验证异常,osid：" + osid + "无效");
			return mapping.findForward("fail");
		}
	}
}
