package com.ls.web.action.cooperate.ok.bill;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.web.service.cooperate.sky.BillService;

public class CallBackAction extends DispatchAction
{
	Logger logger = Logger.getLogger("log.pay");

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String resultWml = "";

		// 用户唯一标示
		String okid = formatString(request.getParameter("okid"));
		// 消费交易号1
		String okbillid1 = formatString(request.getParameter("okbillid1"));
		// 消费交易号2
		String okbillid2 = formatString(request.getParameter("okbillid2"));
		// 充值后兑换的ok币的数量
		String oknum = formatString(request.getParameter("oknum"));

		if (okid == null || okid.equals("") || okid.equals("null")
				|| okbillid1 == null || okbillid1.equals("")
				|| okbillid1.equals("null") || okbillid2 == null
				|| okbillid2.equals("") || okbillid2.equals("null")
				|| oknum == null || oknum.equals("") || oknum.equals("null"))
		{
			request.setAttribute("resultWml", "fail");
			return mapping.findForward("success");
		}

		BillService bs = new BillService();
		resultWml = bs.OKcallbackpay(okid, okbillid1, okbillid2, oknum);
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
