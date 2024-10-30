package com.ls.web.action.cooperate.sms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class PTCallBackAction extends DispatchAction
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
		// 是否扣费:feecode feecode=0 表示免费下发 feecode=100 单位为分.表示扣费一元
		String feecode = formatString(request.getParameter("feecode"));
		// 发送状态报告: reportstat DELIVRD,表示成功,其他表示失败,错误状态报告详细见
		String reportstat = formatString(request.getParameter("reportstat"));

		if (message_type != null && msgid != null && feecode != null
				&& reportstat != null)
		{
			if (message_type.equals("SMS_PT") && reportstat.equals("DELIVRD"))
			{
				// 成功扣费
				resultWml = "0";
				request.setAttribute("resultWml", resultWml);
				return mapping.findForward("success");
			}
			else
			{
				// 扣费失败
				resultWml = "1003";
				request.setAttribute("resultWml", resultWml);
				return mapping.findForward("success");
			}
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