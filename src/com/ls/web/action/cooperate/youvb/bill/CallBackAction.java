package com.ls.web.action.cooperate.youvb.bill;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.dao.cooparate.bill.UAccountRecordDao;
import com.ls.ben.vo.cooperate.bill.UAccountRecordVO;
import com.ls.pub.constant.Channel;
import com.ls.pub.util.encrypt.MD5Util;
import com.ls.web.service.cooperate.bill.BillService;

public class CallBackAction extends DispatchAction
{
	Logger logger = Logger.getLogger("log.pay");

	/**
	 * 应答处理
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		logger.info("##########youvb直接回调############");

		String resultWml = "";

		// 业务类型
		String amount = formatString(request.getParameter("amount"));
		// 游戏帐号
		String account = formatString(request.getParameter("account"));
		// 商户订单号
		String order_no = formatString(request.getParameter("order_no"));
		// 商户订单号
		String time = formatString(request.getParameter("time"));
		// 易宝支付交易流水号
		String sign = formatString(request.getParameter("sign"));

		String key = "2ikdwjcw0923jd34fgg34fgas242441";

		String sign_bak = MD5Util.md5Hex(amount + account + order_no + time
				+ key);
		if (sign.equals(sign_bak))
		{
			// 判断是否有改订单号
			UAccountRecordDao dao = new UAccountRecordDao();
			UAccountRecordVO vo = dao.getRecord(order_no, Channel.YOUVB + "");
			String[] pk = account.split("@@@@@");
			if (pk.length > 1)
			{
				if (vo == null)
				{
					// 给玩家充值加元宝
					UAccountRecordVO new_vo = new UAccountRecordVO();
					new_vo.setUPk(Integer.parseInt(pk[0]));
					new_vo.setPPk(Integer.parseInt(pk[1]));
					new_vo.setChannel(Channel.YOUVB + "");
					new_vo.setCode(order_no);
					new_vo.setMoney(((int) Double.parseDouble(amount)));
					new_vo.setAccountState(1 + "");
					new_vo.setId(dao.insert(new_vo));
					BillService bs = new BillService();
					bs.accountSuccessNotify(new_vo);

					resultWml = "success";
					request.setAttribute("resultWml", resultWml);
					return mapping.findForward("success");
				}
				else
				{
					resultWml = "fail";
					request.setAttribute("resultWml", resultWml);
					return mapping.findForward("success");
				}
			}
			else
			{
				resultWml = "fail";
				request.setAttribute("resultWml", resultWml);
				return mapping.findForward("success");
			}

		}
		else
		{
			resultWml = "fail";
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
