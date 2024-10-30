package com.ls.web.action.cooperate.dl.bill.jun;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.vo.cooperate.bill.UAccountRecordVO;
import com.ls.ben.vo.cooperate.dangle.PassportVO;
import com.ls.web.service.cooperate.bill.BillService;
import com.ls.web.service.cooperate.dangle.PassportService;
import com.ls.web.service.cooperate.dangle.SendPostXml;

public class CallBackAction extends DispatchAction
{

	Logger logger = Logger.getLogger("log.pay");

	/**
	 * 充值确认
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String resultWml = "";

		String merchant_key = "2WE46VM162146qso9tCcg5T3oiEf3659UW71w9V63qQ959R93LR2aw2IT4op";

		String cmd = request.getParameter("cmd");
		String pay_result = request.getParameter("pay_result");
		String pay_sq = request.getParameter("pay_sq");
		String amount = request.getParameter("amount");
		String currency = request.getParameter("currency");
		String order_id = request.getParameter("order_id");
		String bank_order_id = request.getParameter("bank_order_id");
		String ext = request.getParameter("ext");
		String hmac = request.getParameter("hmac");
		
		logger.info("########骏网回调########");
		logger.info("cmd:"+cmd);
		logger.info("pay_result:"+pay_result);
		logger.info("pay_sq:"+pay_sq);
		logger.info("currency:"+currency);
		logger.info("order_id:"+order_id);
		logger.info("bank_order_id:"+bank_order_id);
		logger.info("ext:"+ext);
		logger.info("hmac:"+hmac);

		int record_id = -1;
		
		if( ext!=null )
		{
			String[] params = ext.split("and");
			if( params.length == 2)
			{
				record_id = Integer.parseInt(params[1]);
			}
		}
		
		BillService billService = new BillService();
		PassportService passportService = new PassportService();
		UAccountRecordVO account_record = billService.getAccountRecord(record_id);

		if( account_record==null )
		{
			logger.info("#####无充ID为："+ext+"的值记录#####");
		}

		PassportVO passport = passportService.getPassportInfoByUPk(account_record.getUPk());

		// 用户在当乐注册的用户名
		String userName = "devil1";
		// 用户支付的实际金额，单位为元
		// String amount = amount;
		// 用户支付使用的通道ID，支付通道对照表由当乐提供给商户,详见:表格 3 pc-id（付款渠道）对照表
		String pc_id = "6";
		// 唯一编号,订单号或系统生成的唯一序列号，由游戏厂商生成(防止重复提交)系统中会根据merchant-idgame-idserver-idseq-str唯一匹配数据
		String unique_str = order_id;

		if (passport != null)
		{
			userName = passport.getUserName();
		}

		if (!pay_result.equals("1"))
		{// 失败订单
			logger.info("订单支付失败!订单号:"+order_id+";金额："+amount+";错误代码："+pay_result);
			billService.accountFailNotify(account_record, pay_result);
		}
		else
		{
			String[] hmacArgs = { cmd, pay_result, pay_sq, amount, currency,
					order_id, bank_order_id, ext };
			
//			String newHmac = DigestUtil.getHmac(hmacArgs, merchant_key);
//			logger.info("newHmac:"+newHmac);
			/*if (!newHmac.equals(hmac))
			{// hmac验证失败
				resultWml = "hmac验证失败!";
				logger.info("骏网回调,充值失败:"+resultWml+"；订单号："+order_id);
			}
			else*/
			{
				// 商户相应逻辑操作
				// 注意:需要对根据订单号排重，相同订单不做处理
				if( billService.accountSuccessNotify(account_record) )
				{
					String plant_notify = SendPostXml.sendPostToDangle(userName, amount, pc_id,unique_str);
					logger.info("骏网回调,充值成功;平台通知返回值："+plant_notify+"；订单号："+order_id);
				}
			}
		}
		resultWml = "Y";
		request.setAttribute("resultWml", resultWml);
		return mapping.findForward("success");
	}
}