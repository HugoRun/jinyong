package com.ls.pub.yeepay.nonbankcard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ls.pub.yeepay.Configuration;
import com.ls.pub.yeepay.DigestUtil;
import com.ls.pub.yeepay.HttpUtils;

/**
 * 
 * @author lu.li
 *
 */
public class NonBankcardService {
	private static Log log 						= LogFactory.getLog(NonBankcardService.class);
	private static String p0_Cmd 				= "AnnulCard"; 												// 请求命令名称
	private static String decodeCharset 		= "GBK"; 													// 字符方式
	private static String p1_MerId 				= Configuration.getInstance().getValue("p1_MerId"); 		// 商户编号
	private static String keyValue 				= Configuration.getInstance().getValue("keyValue"); 		// 商户密钥
	private static String annulCardReqURL 		= Configuration.getInstance().getValue("annulCardReqURL"); 	// 请求地址
	
	// 卡号卡密采用算法模式，当前固定为该值
	private static String annulCardReqDESMode = "1";
	// 使用应答机制
	private static String NEEDRESPONSE	= "1";
	/**
	 * 消费请求
	 * 该方法是根据《易宝支付非银行卡支付专业版接口文档 v3.0》对发起支付请求进行的封装
	 * 具体参数含义请仔细阅读《易宝支付非银行卡支付专业版接口文档 v3.0》
	 * @param p2_Order
	 * @param p3_Amt
	 * @param p8_Url
	 * @param pa_MP
	 * @param pa7_cardNo
	 * @param pa8_cardPwd
	 * @param pd_FrpId
	 * @return
	 */
	public static NonBankcardPaymentResult pay(String p2_Order, String p3_Amt,
			String p8_Url, String pa_MP,String pa7_cardNo, String pa8_cardPwd,String pd_FrpId) {
		// 卡号和卡密不得为空
		if(pa7_cardNo == null || pa7_cardNo.equals("") || pa8_cardPwd == null || pa8_cardPwd.equals("")) {
			log.error("pa7_cardNo or pa8_cardPwd is empty.");
			throw new RuntimeException("pa7_cardNo or pa8_cardPwd is empty.");
		}

		// 生成hmac，保证交易信息不被篡改,关于hmac详见《易宝支付非银行卡支付专业版接口文档 v3.0》
		String hmac = "";
		hmac = DigestUtil.getHmac(new String[] { p0_Cmd, p1_MerId, p2_Order,
				p3_Amt, p8_Url, pa_MP,pa7_cardNo, pa8_cardPwd,pd_FrpId,annulCardReqDESMode,NEEDRESPONSE }, keyValue);
		// 封装请求参数，参数说明详见《易宝支付非银行卡支付专业版接口文档 v3.0》
		Map reqParams = new HashMap();
		reqParams.put("p0_Cmd", p0_Cmd);
		reqParams.put("p1_MerId", p1_MerId);
		reqParams.put("p2_Order", p2_Order);
		reqParams.put("p3_Amt", p3_Amt);
		reqParams.put("p8_Url", p8_Url);
		reqParams.put("pa_MP", pa_MP);
		reqParams.put("pa7_cardNo", pa7_cardNo);
		reqParams.put("pa8_cardPwd", pa8_cardPwd);
		reqParams.put("pd_FrpId", pd_FrpId);
		reqParams.put("pa0_Mode", annulCardReqDESMode);
		reqParams.put("pr_NeedResponse", NEEDRESPONSE);
		reqParams.put("hmac", hmac);
		List responseStr = null;
		try {
			// 发起支付请求
			log.debug("Begin http communications,request params[" + reqParams
					+ "]");
			responseStr = HttpUtils.URLPost(annulCardReqURL, reqParams);
			log.debug("End http communications.responseStr:" + responseStr);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		if (responseStr.size() == 0) {
			log.error("no response.");
			throw new RuntimeException("no response.");
		}
		// 创建非银行卡专业版消费请求结果
		NonBankcardPaymentResult rs = new NonBankcardPaymentResult();
		// 解析易宝支付返回的消费请求结果,关于返回结果数据详见《易宝支付非银行卡支付专业版接口文档 v3.0》
		for (int t = 0; t < responseStr.size(); t++) {
			String currentResult = (String) responseStr.get(t);
			log.debug("responseStr[" + t + "]:" + currentResult);
			if (currentResult == null || currentResult.equals("")) {
				continue;
			}
			int i = currentResult.indexOf("=");
			log.debug("i=" + i);
			int j = currentResult.length();
			if (i >= 0) {
				log.debug("find =.");
				String sKey = currentResult.substring(0, i);
				String sValue = currentResult.substring(i + 1);
				if (sKey.equals("r0_Cmd")) {
					rs.setP0_Cmd(sValue);
				} else if (sKey.equals("r1_Code")) {
					rs.setR1_Code(sValue);
				} else if (sKey.equals("r2_TrxId")) {
					rs.setR2_TrxId(sValue);
				} else if (sKey.equals("r6_Order")) {
					rs.setR6_Order(sValue);
				} else if (sKey.equals("rq_ReturnMsg")) {
					rs.setRq_ReturnMsg(sValue);
				} else if (sKey.equals("hmac")) {
					rs.setHmac(sValue);
				} else {
					log.error("throw exception:" + currentResult);
					throw new RuntimeException(currentResult);
				}
			} else {
				log.error("throw exception:" + currentResult);
				throw new RuntimeException(currentResult);
			}
		}
		// 不成功则抛出异常
		if (!rs.getR1_Code().equals("1")) {
			log.error("errorCode:" + rs.getR1_Code() + ";errorMessage:"
					+ rs.getRq_ReturnMsg());
			throw new RuntimeException("errorCode:" + rs.getR1_Code()
					+ ";errorMessage:" + rs.getRq_ReturnMsg());
		}
		String newHmac = "";
		newHmac = DigestUtil.getHmac(new String[] { rs.getP0_Cmd(),
				rs.getR1_Code(), rs.getR2_TrxId(), rs.getR6_Order(),
				rs.getRq_ReturnMsg() }, keyValue);
		// hmac不一致则抛出异常
		if (!newHmac.equals(rs.getHmac())) {
			log.error("Hmac error");
			throw new RuntimeException("Hmac error");
		}
		return (rs);
	}

	/**
	 * 校验交易结果通知
	 * 该方法是根据《易宝支付非银行卡支付专业版接口文档 v3.0》对易宝支付返回扣款数据进行校验
	 * 具体参数含义请仔细阅读《易宝支付非银行卡支付专业版接口文档 v3.0》
	 * 业务类型
	 * @param r0_Cmd
	 * 支付结果
	 * @param r1_Code
	 * 商户编号
	 * @param p1_MerId
	 * 商户订单号
	 * @param rb_Order
	 * 易宝支付交易流水号
	 * @param r2_TrxId
	 * 商户扩展信息
	 * pa_MP
	 * 支付金额
	 * @param rc_Amt
	 * 签名数据
	 * @param hmac
	 */
	public static void verifyCallback(String r0_Cmd, String r1_Code,
			String p1_MerId, String rb_Order, String r2_TrxId, String pa_MP,String rc_Amt,
			String hmac) {
		log.debug("Recv payment result:[r0_Cmd=" + r0_Cmd + ";r1_Code="
				+ r1_Code + ";p1_MerId=" + p1_MerId + ";rb_Order=" + rb_Order
				+ ";r2_TrxId=" + r2_TrxId + ";pa_MP=" + pa_MP + ";rc_Amt=" + rc_Amt + ";hmac="
				+ hmac);
		String newHmac = DigestUtil.getHmac(new String[] { r0_Cmd, r1_Code,
				p1_MerId, rb_Order, r2_TrxId, pa_MP,rc_Amt }, keyValue);
		if (!hmac.equals(newHmac)) {
			String errorMessage = "hmac invalid!";
			log.debug(errorMessage);
			throw new RuntimeException(errorMessage);
		}
		if (!r1_Code.equals("1")) {
			throw new RuntimeException("Payment is fail!r1_Code=" + r1_Code);
		}
	}
	/**
	 * 该方法是根据《易宝支付非银行卡支付专业版接口文档 v3.0》生成一个模拟的交易结果通知串.
	 * 商户使用模拟的交易结果通知串可以直接测试自己的交易结果接收程序(callback)的正确性.
	 * 实际的交易结果通知机制以《易宝支付非银行卡支付专业版接口文档 v3.0》为准，该方法只是
	 * 模拟了交易结果通知串.
	 * @param p2_Order
	 * @param p3_Amt
	 * @param p8_Url
	 * @param pa7_cardNo
	 * @param pa8_cardPwd
	 * @param pa_MP
	 * @return
	 */
	public static String generationTestCallback(String p2_Order,String p3_Amt,String p8_Url,String pa7_cardNo,String pa8_cardPwd,String pa_MP) {
		String callback = "";
		Map reqParams = new HashMap();
		reqParams.put("p0_Cmd", p0_Cmd);
		reqParams.put("p1_MerId", p1_MerId);
		reqParams.put("p2_Order", p2_Order);
		reqParams.put("p3_Amt", p3_Amt);
		reqParams.put("p8_Url", p8_Url);
		reqParams.put("pa7_cardNo", pa7_cardNo);
		reqParams.put("pa8_cardPwd", pa8_cardPwd);
		reqParams.put("pa_MP", pa_MP);
		reqParams.put("pa0_Mode", annulCardReqDESMode);
		reqParams.put("pr_NeedResponse", NEEDRESPONSE);
		List responseStr = null;
		try {
			// 发起支付请求
			log.debug("Begin http communications,request params[" + reqParams
					+ "]");
			responseStr = HttpUtils.URLPost("http://localhost:8080/yeepay/callback.do", reqParams);
			log.debug("End http communications.responseStr:" + responseStr);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		if (responseStr.size() == 0) {
			log.error("no response.");
			throw new RuntimeException("no response.");
		}
		for (int t = 0; t < responseStr.size(); t++) {
			String currentResult = (String) responseStr.get(t);
			log.debug("responseStr[" + t + "]:" + currentResult);
			if (currentResult == null || currentResult.equals("")) {
				continue;
			}
			callback = (String)responseStr.get(t);
		}
		return (callback);
	}
}
