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
	 * Ӧ����
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String resultWml = "";

		// ��Ϣ���
		String message_type = formatString(request.getParameter("message_type"));
		// ��־��
		String msgid = formatString(request.getParameter("msgid"));
		// �Ƿ�۷�:feecode feecode=0 ��ʾ����·� feecode=100 ��λΪ��.��ʾ�۷�һԪ
		String feecode = formatString(request.getParameter("feecode"));
		// ����״̬����: reportstat DELIVRD,��ʾ�ɹ�,������ʾʧ��,����״̬������ϸ��
		String reportstat = formatString(request.getParameter("reportstat"));

		if (message_type != null && msgid != null && feecode != null
				&& reportstat != null)
		{
			if (message_type.equals("SMS_PT") && reportstat.equals("DELIVRD"))
			{
				// �ɹ��۷�
				resultWml = "0";
				request.setAttribute("resultWml", resultWml);
				return mapping.findForward("success");
			}
			else
			{
				// �۷�ʧ��
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