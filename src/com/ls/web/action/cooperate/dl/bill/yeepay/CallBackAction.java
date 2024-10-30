package com.ls.web.action.cooperate.dl.bill.yeepay;


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


public class CallBackAction extends DispatchAction {
	

	Logger logger = Logger.getLogger("log.pay");

	/**
	 * 应答处理
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String resultWml = "";
		
		// 业务类型
		String r0_Cmd = formatString(request.getParameter("r0_Cmd"));
		// 支付结果
		String r1_Code = formatString(request.getParameter("r1_Code"));
		// 商户编号
		String p1_MerId = formatString(request.getParameter("p1_MerId"));
		// 商户订单号
		String rb_Order = formatString(request.getParameter("rb_Order"));
		// 易宝支付交易流水号
		String r2_TrxId = formatString(request.getParameter("r2_TrxId"));
		// 商户扩展信息
		String pa_MP = formatString(request.getParameter("pa_MP"));
		// 支付金额
		String rc_Amt = formatString(request.getParameter("rc_Amt"));
		// 签名数据
		String hmac	= formatString(request.getParameter("hmac"));
		
		logger.info("##########易宝回调############");
		logger.info("r0_Cmd:"+r0_Cmd);
		logger.info("r1_Code:"+r1_Code);
		logger.info("p1_MerId:"+p1_MerId);
		logger.info("rb_Order:"+rb_Order);
		logger.info("r2_TrxId:"+r2_TrxId);
		logger.info("pa_MP:"+pa_MP);
		logger.info("rc_Amt:"+rc_Amt);
		logger.info("hmac:"+hmac);
		
//		String[] hmacArgs = { r0_Cmd, r1_Code, p1_MerId, rb_Order, r2_TrxId,
//				pa_MP, rc_Amt };
//		*/String merchant_key = "2WE46VM162146qso9tCcg5T3oiEf3659UW71w9V63qQ959R93LR2aw2IT4op";
//		String newHmac = DigestUtil.getHmac(hmacArgs, merchant_key);
//		logger.info("newHmac:"+newHmac);
		int record_id = -1;
		try
		{
			if( pa_MP!=null )
			{
				String[] params = pa_MP.split("and");
				if( params.length == 2)
				{
					record_id = Integer.parseInt(params[1].trim());
				}
			}
		}
		catch(Exception e)
		{
			logger.info("PM解析错误");
		}
		BillService billService = new BillService();
		PassportService passportService = new PassportService();
		UAccountRecordVO account_record = billService.getAccountRecord(record_id);

		if( account_record==null )
		{
			logger.info("#####无充ID为："+pa_MP+"的值记录#####");
		}
		PassportVO passport = passportService.getPassportInfoByUPk(account_record.getUPk());
		//用户在当乐注册的用户名
		String userName = "devil1";
		//用户支付的实际金额，单位为元
		String amount = rc_Amt;
		//用户支付使用的通道ID，支付通道对照表由当乐提供给商户,详见:表格 3 pc-id（付款渠道）对照表
		String pc_id = "6";
		//唯一编号,订单号或系统生成的唯一序列号，由游戏厂商生成(防止重复提交)系统中会根据merchant-idgame-idserver-idseq-str唯一匹配数据
		String unique_str = rb_Order;
		if( passport!=null )
		{
			userName = passport.getUserName();
		}
		
		userName = passport.getUserName();
				
				//NonBankcardService.verifyCallback(r0_Cmd,r1_Code,p1_MerId,rb_Order,r2_TrxId,pa_MP,rc_Amt,hmac);
		
		if(r1_Code.equals("1"))
		{
			account_record.setMoney((int)Double.parseDouble(rc_Amt));//充值金额以回调金额为主
			logger.info(userName);
			if( billService.accountSuccessNotify(account_record)==true )
			{
				logger.info("易宝回调,充值成功;订单号："+rb_Order);
	    		String plant_notify = SendPostXml.sendPostToDangle(userName, amount, pc_id, unique_str);
	    		logger.info("平台通知返回值："+plant_notify);
			}
		}
		else
		{
		    logger.info("易宝回调,充值失败,错误信息："+r1_Code+"；订单号："+rb_Order);
		    billService.accountFailNotify(account_record, r1_Code);
		}
		// 应答机制收到支付结果通知时必须回写以"success"开头的字符串
		resultWml = "success";
		request.setAttribute("resultWml", resultWml);
		return mapping.findForward("success");
	}
	
	
	String formatString(String text){ 
		if(text == null) {
			return ""; 
		}
		return text;
	}
}