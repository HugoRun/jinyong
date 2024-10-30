package com.ls.web.action.cooperate.game.bill;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.web.service.cooperate.bill.BillService;

public class CallbackAction extends DispatchAction
{
	/**
	 * Ӧ����
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		// ����ʺ�
		String account = formatString(request.getParameter("account"));
		// ��ֵ���
		String pay_money = formatString(request.getParameter("pay_money"));
		// ʱ��
		String timestamp = formatString(request.getParameter("timestamp"));
		// ��Ϸ����
		String gamepoint = formatString(request.getParameter("gamepoint"));
		// ��ϷID
		String gameid = formatString(request.getParameter("gameid"));
		// MD5string
		String MD5string = formatString(request.getParameter("MD5string"));
		//������
		String orderid = formatString(request.getParameter("orderid"));
		String resultWml = "";
		BillService bs = new BillService();
		boolean chongzhi = bs.chongzhibynormal(account, pay_money, timestamp,
				gameid, MD5string, gamepoint, orderid);
		if (chongzhi == true)
		{
			resultWml = "success";
		}
		else
		{
			resultWml = "fail";
		}
		request.setAttribute("resultWml", resultWml);
		return mapping.findForward("success");
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
