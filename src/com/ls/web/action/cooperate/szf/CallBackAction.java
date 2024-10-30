package com.ls.web.action.cooperate.szf;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.vo.cooperate.bill.UAccountRecordVO;
import com.ls.pub.util.encrypt.MD5Util;
import com.ls.web.service.cooperate.bill.BillService;


public class CallBackAction extends DispatchAction {
	

	Logger logger = Logger.getLogger("log.pay");

	/**
	 * 应答处理
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		logger.info("##########神州付直接回调############");
		
		String resultWml = "";
		
		// 版本号
		String version = formatString(request.getParameter("version"));
		// 商户ID
		String merId = formatString(request.getParameter("merId"));
		// 支付金额
		String payMoney = formatString(request.getParameter("payMoney"));
		// 订单号
		String orderId = formatString(request.getParameter("orderId"));
		// 支付结果
		String payResult = formatString(request.getParameter("payResult"));
		// 商户私有数据
		String privateField = formatString(request.getParameter("privateField"));
		// 支付详情
		String payDetails = formatString(request.getParameter("payDetails"));
		// MD5加密串
		String md5String	= formatString(request.getParameter("md5String"));
		// 证书签名串
		String signString	= formatString(request.getParameter("signString"));
		
		String md5String_bak= MD5Util.md5Hex(version+merId+payMoney+orderId+payResult+privateField+payDetails+"b22fa05b6c9338545b7c078a7f877faa");
		
		logger.info("version:"+version);
		logger.info("merId:"+merId);
		logger.info("payMoney:"+payMoney);
		logger.info("orderId:"+orderId);
		logger.info("payResult:"+payResult);
		logger.info("privateField:"+privateField);
		logger.info("payDetails:"+payDetails);
		logger.info("signString:"+signString);
		
		if(!md5String_bak.equals(md5String)){
			logger.info("加密错误###############");
		}
		
		int record_id = -1;
		try
		{
			 record_id = Integer.parseInt(privateField.trim());
		}
		catch(Exception e)
		{
			logger.info("PM解析错误");
		}
		
		BillService billService = new BillService();
		UAccountRecordVO account_record = billService.getAccountRecord(record_id);

		if( account_record==null )
		{
			logger.info("#####无效ID为："+privateField+"的值记录#####");
		}
		
		
		//用户在当乐注册的用户名
		String userName = "devil1";
		//用户支付的实际金额，单位为元
		String amount = payMoney;
		//用户支付使用的通道ID，支付通道对照表由当乐提供给商户,详见:表格 3 pc-id（付款渠道）对照表
		String pc_id = "6";
		//唯一编号,订单号或系统生成的唯一序列号，由游戏厂商生成(防止重复提交)系统中会根据merchant-idgame-idserver-idseq-str唯一匹配数据
		String unique_str = orderId;
		//userName = passport.getUserName();
				
				//NonBankcardService.verifyCallback(r0_Cmd,r1_Code,p1_MerId,rb_Order,r2_TrxId,pa_MP,rc_Amt,hmac);
		
		if(payResult.equals("1"))
		{
			//logger.info(userName);
			if( billService.accountSuccessNotify(account_record)==true )
			{
				logger.info("神州付回调,充值成功;订单号："+orderId);
			}
		}
		else
		{
		    logger.info("神州付回调,充值失败,错误信息："+payResult+"；订单号："+orderId);
		    billService.accountFailNotify(account_record, payResult);
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