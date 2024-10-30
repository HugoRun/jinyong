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
	 * Ӧ����
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String resultWml = "";
		
		// ҵ������
		String r0_Cmd = formatString(request.getParameter("r0_Cmd"));
		// ֧�����
		String r1_Code = formatString(request.getParameter("r1_Code"));
		// �̻����
		String p1_MerId = formatString(request.getParameter("p1_MerId"));
		// �̻�������
		String rb_Order = formatString(request.getParameter("rb_Order"));
		// �ױ�֧��������ˮ��
		String r2_TrxId = formatString(request.getParameter("r2_TrxId"));
		// �̻���չ��Ϣ
		String pa_MP = formatString(request.getParameter("pa_MP"));
		// ֧�����
		String rc_Amt = formatString(request.getParameter("rc_Amt"));
		// ǩ������
		String hmac	= formatString(request.getParameter("hmac"));
		
		logger.info("##########�ױ��ص�############");
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
			logger.info("PM��������");
		}
		BillService billService = new BillService();
		PassportService passportService = new PassportService();
		UAccountRecordVO account_record = billService.getAccountRecord(record_id);

		if( account_record==null )
		{
			logger.info("#####�޳�IDΪ��"+pa_MP+"��ֵ��¼#####");
		}
		PassportVO passport = passportService.getPassportInfoByUPk(account_record.getUPk());
		//�û��ڵ���ע����û���
		String userName = "devil1";
		//�û�֧����ʵ�ʽ���λΪԪ
		String amount = rc_Amt;
		//�û�֧��ʹ�õ�ͨ��ID��֧��ͨ�����ձ��ɵ����ṩ���̻�,���:��� 3 pc-id���������������ձ�
		String pc_id = "6";
		//Ψһ���,�����Ż�ϵͳ���ɵ�Ψһ���кţ�����Ϸ��������(��ֹ�ظ��ύ)ϵͳ�л����merchant-idgame-idserver-idseq-strΨһƥ������
		String unique_str = rb_Order;
		if( passport!=null )
		{
			userName = passport.getUserName();
		}
		
		userName = passport.getUserName();
				
				//NonBankcardService.verifyCallback(r0_Cmd,r1_Code,p1_MerId,rb_Order,r2_TrxId,pa_MP,rc_Amt,hmac);
		
		if(r1_Code.equals("1"))
		{
			account_record.setMoney((int)Double.parseDouble(rc_Amt));//��ֵ����Իص����Ϊ��
			logger.info(userName);
			if( billService.accountSuccessNotify(account_record)==true )
			{
				logger.info("�ױ��ص�,��ֵ�ɹ�;�����ţ�"+rb_Order);
	    		String plant_notify = SendPostXml.sendPostToDangle(userName, amount, pc_id, unique_str);
	    		logger.info("ƽ̨֪ͨ����ֵ��"+plant_notify);
			}
		}
		else
		{
		    logger.info("�ױ��ص�,��ֵʧ��,������Ϣ��"+r1_Code+"�������ţ�"+rb_Order);
		    billService.accountFailNotify(account_record, r1_Code);
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