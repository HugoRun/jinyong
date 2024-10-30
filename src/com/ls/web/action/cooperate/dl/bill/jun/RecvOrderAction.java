package com.ls.web.action.cooperate.dl.bill.jun;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.web.service.cooperate.bill.BillService;

public class RecvOrderAction extends DispatchAction
{

	Logger logger = Logger.getLogger("log.pay");

	/**
	 * 充值确认
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		BillService billService = new BillService();
		
		String resultWml = null;
		
		String u_k = request.getParameter("uid");// 提交来的u_pk
		String pay_result = request.getParameter("pay_result");
		String cmd = request.getParameter("cmd");
		String amount = request.getParameter("amount");
		String pay_sq = request.getParameter("pay_sq");
		String order_id = request.getParameter("order_id");
		logger.info("u pay_result= " + pay_result + " amount=" + amount+"  order_id="+order_id);

		if ("1".equals(pay_result))
		{
			logger.info("骏网通道提交充值请求：成功提交充值请求,卡号："+order_id+";充值金额："+amount+"元");
			resultWml = billService.getSuccessHint();
			//resultWml = resultWml + "你将得到" + amount + "元宝<br/>";
		}
		else
		{
			logger.info("骏网通道提交充值请求：提交充值请求失败,错误代码"+pay_result);
			resultWml = billService.getFailHint(pay_result);
			//resultWml = "充值失败,请重新操作.";
		}

		request.setAttribute("resultWml", resultWml.toString());
		return mapping.findForward("success");
	}
}