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
		// �ֻ�����
		String mobile = formatString(request.getParameter("mobile"));
		// �û����ͣ�userType 1����ͨ�û� 3���������û�
		String userType = formatString(request.getParameter("userType"));
		// ���ر��
		String gwid = formatString(request.getParameter("gwid"));
		// �ֻ���������
		String momsg = formatString(request.getParameter("momsg"));
		// �ֻ�����Ŀ�ĵ�ַ
		String spcode = formatString(request.getParameter("spcode"));
		// �ƷѴ���
		String serviceType = formatString(request.getParameter("serviceType"));
		// Linkid
		String linked = formatString(request.getParameter("linked"));
		// �Ƿ�Ʒѣ�feeflag 1Ϊ�Ʒѣ�0Ϊ���Ʒ�
		String feeflag = formatString(request.getParameter("feeflag"));
		// cpproductID��Ʒ���
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
