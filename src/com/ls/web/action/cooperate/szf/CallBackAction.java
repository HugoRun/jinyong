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
	 * Ӧ����
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		logger.info("##########���ݸ�ֱ�ӻص�############");
		
		String resultWml = "";
		
		// �汾��
		String version = formatString(request.getParameter("version"));
		// �̻�ID
		String merId = formatString(request.getParameter("merId"));
		// ֧�����
		String payMoney = formatString(request.getParameter("payMoney"));
		// ������
		String orderId = formatString(request.getParameter("orderId"));
		// ֧�����
		String payResult = formatString(request.getParameter("payResult"));
		// �̻�˽������
		String privateField = formatString(request.getParameter("privateField"));
		// ֧������
		String payDetails = formatString(request.getParameter("payDetails"));
		// MD5���ܴ�
		String md5String	= formatString(request.getParameter("md5String"));
		// ֤��ǩ����
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
			logger.info("���ܴ���###############");
		}
		
		int record_id = -1;
		try
		{
			 record_id = Integer.parseInt(privateField.trim());
		}
		catch(Exception e)
		{
			logger.info("PM��������");
		}
		
		BillService billService = new BillService();
		UAccountRecordVO account_record = billService.getAccountRecord(record_id);

		if( account_record==null )
		{
			logger.info("#####��ЧIDΪ��"+privateField+"��ֵ��¼#####");
		}
		
		
		//�û��ڵ���ע����û���
		String userName = "devil1";
		//�û�֧����ʵ�ʽ���λΪԪ
		String amount = payMoney;
		//�û�֧��ʹ�õ�ͨ��ID��֧��ͨ�����ձ��ɵ����ṩ���̻�,���:��� 3 pc-id���������������ձ�
		String pc_id = "6";
		//Ψһ���,�����Ż�ϵͳ���ɵ�Ψһ���кţ�����Ϸ��������(��ֹ�ظ��ύ)ϵͳ�л����merchant-idgame-idserver-idseq-strΨһƥ������
		String unique_str = orderId;
		//userName = passport.getUserName();
				
				//NonBankcardService.verifyCallback(r0_Cmd,r1_Code,p1_MerId,rb_Order,r2_TrxId,pa_MP,rc_Amt,hmac);
		
		if(payResult.equals("1"))
		{
			//logger.info(userName);
			if( billService.accountSuccessNotify(account_record)==true )
			{
				logger.info("���ݸ��ص�,��ֵ�ɹ�;�����ţ�"+orderId);
			}
		}
		else
		{
		    logger.info("���ݸ��ص�,��ֵʧ��,������Ϣ��"+payResult+"�������ţ�"+orderId);
		    billService.accountFailNotify(account_record, payResult);
		}
		// Ӧ������յ�֧�����֪ͨʱ�����д��"success"��ͷ���ַ���
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