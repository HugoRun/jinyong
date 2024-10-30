package com.ls.web.action.cooperate.sms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MOCallBackAction extends DispatchAction
{
	Logger logger = Logger.getLogger("log.pay");

	/**
	 * 应答处理
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String resultWml = "";

		// 消息类别
		String message_type = formatString(request.getParameter("message_type"));
		// 标志码
		String msgid = formatString(request.getParameter("msgid"));
		// 手机号码
		String mobile = formatString(request.getParameter("mobile"));
		// 用户类型：userType 1：普通用户 3：黑名单用户
		String userType = formatString(request.getParameter("userType"));
		// 网关编号
		String gwid = formatString(request.getParameter("gwid"));
		// 手机上行内容
		String momsg = formatString(request.getParameter("momsg"));
		// 手机上行目的地址
		String spcode = formatString(request.getParameter("spcode"));
		// 计费代码
		String serviceType = formatString(request.getParameter("serviceType"));
		// Linkid
		String linked = formatString(request.getParameter("linked"));
		// 是否计费：feeflag 1为计费，0为不计费
		String feeflag = formatString(request.getParameter("feeflag"));
		// cpproductID产品编号
		String cpproductID = formatString(request.getParameter("cpproductID"));

		if (message_type != null && msgid != null && mobile != null
				&& userType != null && gwid != null && momsg != null
				&& spcode != null && serviceType != null && linked != null
				&& feeflag != null && cpproductID != null)
		{
			resultWml = "0";
			request.setAttribute("resultWml", resultWml);
			return mapping.findForward("success");
		}
		else
		{
			resultWml = "1003";
			request.setAttribute("resultWml", resultWml);
			return mapping.findForward("success");
		}
	}

	String formatString(String text)
	{
		if (text == null)
		{
			return "";
		}
		return text;
	}
}
