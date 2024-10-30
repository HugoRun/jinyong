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
	 * 应答处理
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		// 玩家帐号
		String account = formatString(request.getParameter("account"));
		// 充值金额
		String pay_money = formatString(request.getParameter("pay_money"));
		// 时间
		String timestamp = formatString(request.getParameter("timestamp"));
		// 游戏点数
		String gamepoint = formatString(request.getParameter("gamepoint"));
		// 游戏ID
		String gameid = formatString(request.getParameter("gameid"));
		// MD5string
		String MD5string = formatString(request.getParameter("MD5string"));
		//订单号
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
