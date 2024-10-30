package com.ls.pub.yeepay.nonbankcard;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ls.pub.util.DES;
import com.ls.pub.util.encrypt.MD5Util;
import com.ls.pub.yeepay.Configuration;
import com.ls.pub.yeepay.HttpUtils;

/**
 * 
 * @author lu.li
 *
 */
public class SZFService {
	Logger logger = Logger.getLogger("log.pay");
	private static String szf_MerId 		= Configuration.getInstance().getValue("szf_MerId"); 	// ���ݸ��̻�id�����ַ
	private static String szf_keyValue 		= Configuration.getInstance().getValue("szf_keyValue"); 	// ���ݸ��̻���Կ�����ַ
	private static String szf_annulCardReqURL 		= Configuration.getInstance().getValue("szf_annulCardReqURL"); 	// ���ݸ������ַ
	private static String version  		= Configuration.getInstance().getValue("version"); 	// ���ݸ��汾��
	private static String merUserMail  		= Configuration.getInstance().getValue("merUserMail"); 	// ���ݸ�����
	private static String des_key  		= Configuration.getInstance().getValue("des_key"); 	// des��Կ
	
	//���� ���ݸ��ĳ�ֵ����
	public String payBySZF(String orderId,int money,String returnUrl,String pa_MP,String code,String psw,String cardTypeCombine){
		DES des = new DES();
		String cardInfo = des.getDesEncryptBase64String(money+"", code, psw, des_key);
		orderId = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date())+"-"+szf_MerId+"-"+orderId;
		String md5String= MD5Util.md5Hex(version+szf_MerId+(money*100)+orderId+returnUrl+cardInfo+pa_MP+cardTypeCombine+szf_keyValue);
		Map reqParams = new HashMap();
		reqParams.put("version", version);
		reqParams.put("merId", szf_MerId);
		reqParams.put("payMoney", money*100);
		reqParams.put("orderId", orderId);
		reqParams.put("returnUrl", returnUrl);
		reqParams.put("cardInfo", cardInfo);
		reqParams.put("merUserName", "");
		reqParams.put("merUserMail", merUserMail);
		reqParams.put("privateField", pa_MP);
		reqParams.put("verifyType", cardTypeCombine);
		reqParams.put("cardTypeCombine", cardTypeCombine);
		reqParams.put("md5String", md5String);
		reqParams.put("signString", "");
		String szfResponseCode = null;
		try {
			// ����֧������
			logger.debug("Begin http communications,request params[" + reqParams
					+ "]");
			szfResponseCode = HttpUtils.URLGetSZF(szf_annulCardReqURL, reqParams);
			logger.debug("End http communications.responseStr:" + szfResponseCode);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return szfResponseCode;
	}
}