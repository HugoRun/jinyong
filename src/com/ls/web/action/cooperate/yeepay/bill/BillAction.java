package com.ls.web.action.cooperate.yeepay.bill;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.vo.cooperate.bill.UAccountRecordVO;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.pub.yeepay.nonbankcard.NonBankcardPaymentResult;
import com.ls.pub.yeepay.nonbankcard.NonBankcardService;
import com.ls.pub.yeepay.nonbankcard.SZFService;
import com.ls.web.action.cooperate.youle.login.LoginService;
import com.ls.web.service.cooperate.bill.BillService;


public class BillAction extends DispatchAction {
	
	Logger logger = Logger.getLogger("log.pay");
	
	/**
	 * 各个充值通道的跳转
	 */
	public ActionForward subIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String b_type_str = request.getParameter("b_type");
		request.setAttribute("b_type", b_type_str);
		HttpSession session=request.getSession();
		if( b_type_str!=null && !b_type_str.equals("") )
		{
			int b_type = Integer.parseInt(b_type_str);
			switch( b_type )
			{
				case 1 :
				{
					session.setAttribute("cardType","3003");
					return mapping.findForward("szx_index");//神州行
				}
				case 2 :
				{
					session.setAttribute("cardType","3006");
					return mapping.findForward("sd_index");//盛大卡
				}
				case 3 :
				{
					session.setAttribute("cardType","3004");
					return mapping.findForward("jun_index");//骏网一卡通
				}
				case 4 :
				{
					return mapping.findForward("zht_index");//征途卡
				}
				case 5 :
				{
					session.setAttribute("cardType","3011");
					return mapping.findForward("szftelecom_index");//电信卡
				}
				case 6 :
				{
					session.setAttribute("cardType","3008");
					return mapping.findForward("szfunicom_index");//联通卡
				}
				case 7 :
				{
					session.setAttribute("cardType","3003");
					return mapping.findForward("szfszx_index");//神州行
				}
			}
		}
		
		return mapping.findForward("index");
	}
	
	
	/**
	 * 输入卡号和密码的各个充值通道的跳转
	 */
	public ActionForward input(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String money = request.getParameter("money");
		String b_type_str = request.getParameter("b_type");
		
		request.setAttribute("money", money);
		request.setAttribute("b_type", b_type_str);
		
		if( b_type_str!=null && !b_type_str.equals("") )
		{
			int b_type = Integer.parseInt(b_type_str);
			switch( b_type )
			{
				case 1 :
				{
					return mapping.findForward("szx_input");//神州行
				}
				case 2 :
				{
					return mapping.findForward("sd_input");//盛大卡
				}
				case 3 :
				{
					return mapping.findForward("jun_input");//骏网一卡通
				}
				case 4 :
				{
					return mapping.findForward("zht_input");//征途卡
				}
				case 5 :
				{
					return mapping.findForward("szftelecom_input");//电信卡
				}
				case 6 :
				{
					return mapping.findForward("szfunicom_input");//联通卡
				}
				case 7 :
				{
					return mapping.findForward("szfszx_input");//神州行
				}
			}
		}
		
		return mapping.findForward("index");
	}
	
	/**
	 * 跳转到确认页面
	 */
	public ActionForward comfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String b_type_str = request.getParameter("b_type");
		request.setAttribute("b_type", b_type_str);
		
		String amount=request.getParameter("pay");
		String pay_cardno=request.getParameter("code");
		String pay_cardpwd=request.getParameter("psw");
		
		
		request.setAttribute("pay", amount);
		request.setAttribute("code", pay_cardno);
		request.setAttribute("psw", pay_cardpwd);
		
		if( b_type_str!=null && !b_type_str.equals("") )
		{
			int b_type = Integer.parseInt(b_type_str);
			switch( b_type )
			{
				case 1 :
				{
					request.setAttribute("pd_FrpId","SZX");
					return mapping.findForward("pay_comfirm");//神州行
				}
				case 2 :
				{
					request.setAttribute("pd_FrpId","SNDACARD");
					return mapping.findForward("pay_comfirm");//盛大卡
				}
				case 3 :
				{
					request.setAttribute("pd_FrpId", "JUNNET");
					return mapping.findForward("pay_comfirm");//骏网一卡通
				}
				case 4 :
				{
					request.setAttribute("pd_FrpId", "ZHENGTU");
					return mapping.findForward("pay_comfirm");//征途卡
				}
				case 5 :
				{
					request.setAttribute("pd_FrpId", "2");
					return mapping.findForward("szf_pay_comfirm");//电信卡
				}
				case 6 :
				{
					request.setAttribute("pd_FrpId", "1");
					return mapping.findForward("szf_pay_comfirm");//联通卡
				}
				case 7 :
				{
					request.setAttribute("pd_FrpId", "0");
					return mapping.findForward("szf_pay_comfirm");//神州行
				}
			}
		}
		
		return mapping.findForward("index");
	}
	
	/**
	 * 充值提交
	 */
	public ActionForward pay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		String p_pk = (String)session.getAttribute("pPk");
		String u_pk = (String)session.getAttribute("uPk");
		
		if( p_pk==null || u_pk==null  )
		{
			logger.info("session中已无有效的pPk和uPk");
			return null;
		}
		
		BillService billService = new BillService();
		
		String resultWml = null;
		
		try
		{
			request.setCharacterEncoding("GBK");
		}
		catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}
		// 商户订单号
		String addtime = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		String order_id=GameConfig.getChannelId()+"s"+addtime;
		//String p2_Order = formatString(request.getParameter("p2_Order"));
		String p2_Order=order_id;
		// 订单金额
		String p3_Amt = formatString(request.getParameter("pay"));

		// 卡号
		String pa7_cardNo = formatString(request.getParameter("code"));

		// 卡密码
		String pa8_cardPwd = formatString(request.getParameter("psw"));


		// 具体通道
		String pd_FrpId = formatString(request.getParameter("pd_FrpId"));
		
		// 交易成功通知地址
		String p8_Url = formatString(GameConfig.getUrlOfGame()+"/game/yeepay/callback.do");
		
		logger.info("回调地址："+p8_Url);
		String RechargeStatus="";
		//记录玩家充值信息
		UAccountRecordVO account_record = new UAccountRecordVO();
		account_record.setUPk(Integer.parseInt(u_pk));
		account_record.setPPk(Integer.parseInt(p_pk));
		account_record.setCode(pa7_cardNo);
		account_record.setPwd(pa8_cardPwd);
		account_record.setMoney(Integer.parseInt(p3_Amt));
		account_record.setChannel(pd_FrpId);
		account_record.setAccountState("发送充值请求");
		
		logger.info("易宝("+pd_FrpId+")通道");
		int record_id = billService.account(account_record);
		
		
		// 商户扩展信息
		String pa_MP = record_id+"";
		
		try {
			NonBankcardPaymentResult rs = NonBankcardService.pay(p2_Order,p3_Amt,p8_Url,pa_MP,pa7_cardNo,pa8_cardPwd,pd_FrpId);
			//充值的成功
			if("1".equals(rs.getR1_Code()))
			{
				resultWml = billService.getSuccessHint();
				RechargeStatus="0";
			}else
			{
				logger.info("易宝("+pd_FrpId+")通道提交充值请求：提交充值请求失败,错误代码"+rs.getR1_Code());
				resultWml = billService.getFailHint(rs.getR1_Code());
				RechargeStatus="1";
			}
			billService.updateState(record_id, rs.getR1_Code());
			if(GameConfig.getChannelId()==Channel.WANXIANG)
			{
				LoginService ls=new LoginService();
				String RechargeCode=new Date().getTime()+"x";
				String cardType=(String)request.getSession().getAttribute("cardType");
				ls.synchronousRecharge(request, RechargeCode, p3_Amt, Integer.parseInt(p3_Amt)*100+"",cardType, RechargeStatus);
			}
		}
		catch(Exception e )
		{
			e.printStackTrace();
			logger.info("易宝("+pd_FrpId+")通道提交充值请求：充值失败,错误代码"+e.toString());
			resultWml = billService.getFailHint(e.toString());
			billService.updateState(record_id, e.toString());
		}
		request.setAttribute("resultWml", resultWml);
		return mapping.findForward("success");
	}
	
	private  String formatString(String text){ 
		if(text == null) {
			return "";  
		}
		return text;
		
	}
	
	/**
	 * 充值提交
	 */
	public ActionForward payBySZF(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		String p_pk = (String)session.getAttribute("pPk");
		String u_pk = (String)session.getAttribute("uPk");
		
		if( p_pk==null || u_pk==null  )
		{
			logger.info("session中已无有效的pPk和uPk");
			return null;
		}
		
		BillService billService = new BillService();
		
		String resultWml = null;
		
		// 商户订单号
		String addtime = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		
		//订单号ID
		String orderId = GameConfig.getChannelId()+"s"+addtime;
		
		// 订单金额
		String payMoney = formatString(request.getParameter("pay"));

		// 卡号
		String code = formatString(request.getParameter("code"));

		// 卡密码
		String psw = formatString(request.getParameter("psw"));

		// 具体通道
		String cardTypeCombine = formatString(request.getParameter("pd_FrpId"));
		
		// 交易成功通知地址
		String returnUrl = formatString(GameConfig.getUrlOfGame()+"/szf/callback.do");
		
		logger.info("回调地址："+returnUrl);
		
		//记录玩家充值信息
		UAccountRecordVO account_record = new UAccountRecordVO();
		account_record.setUPk(Integer.parseInt(u_pk));
		account_record.setPPk(Integer.parseInt(p_pk));
		account_record.setCode(code);
		account_record.setPwd(psw);
		account_record.setMoney(Integer.parseInt(payMoney));
		account_record.setChannel(cardTypeCombine);
		account_record.setAccountState("发送充值请求");
		String RechargeStatus="";
		logger.info("神州付("+cardTypeCombine+")通道");
		int record_id = billService.account(account_record);
		
		// 商户扩展信息
		String pa_MP = record_id+"";
		
		try {
			SZFService s = new SZFService();
			String rs = s.payBySZF(orderId,Integer.parseInt(payMoney),returnUrl,pa_MP,code,psw,cardTypeCombine);
			//充值的成功
			if(rs.equals("200"))
			{
				resultWml = billService.getSuccessHint();
				RechargeStatus="0";
			}else
			{
				logger.info("神州付"+cardTypeCombine+")通道提交充值请求：提交充值请求失败,错误代码"+rs);
				resultWml = billService.getFailHint(rs);
				RechargeStatus="1";
			}
			billService.updateState(record_id, rs);
			if(GameConfig.getChannelId()==Channel.WANXIANG)
			{
				LoginService ls=new LoginService();
				String RechargeCode=new Date().getTime()+"x";
				String cardType=(String)request.getSession().getAttribute("cardType");
				ls.synchronousRecharge(request, RechargeCode, payMoney, Integer.parseInt(payMoney)*100+"",cardType, RechargeStatus);
			}
		}
		catch(Exception e )
		{
			e.printStackTrace();
			logger.info("神州付("+cardTypeCombine+")通道提交充值请求：充值失败,错误代码"+e.toString());
			resultWml = billService.getFailHint(e.toString());
			billService.updateState(record_id, e.toString());
		}
		request.setAttribute("resultWml", resultWml);
		return mapping.findForward("success");
	}
	
}