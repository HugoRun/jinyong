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
	 * ��ֵȷ��
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
		
		logger.info("########�����ص�########");
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
			logger.info("#####�޳�IDΪ��"+ext+"��ֵ��¼#####");
		}

		PassportVO passport = passportService.getPassportInfoByUPk(account_record.getUPk());

		// �û��ڵ���ע����û���
		String userName = "devil1";
		// �û�֧����ʵ�ʽ���λΪԪ
		// String amount = amount;
		// �û�֧��ʹ�õ�ͨ��ID��֧��ͨ�����ձ��ɵ����ṩ���̻�,���:��� 3 pc-id���������������ձ�
		String pc_id = "6";
		// Ψһ���,�����Ż�ϵͳ���ɵ�Ψһ���кţ�����Ϸ��������(��ֹ�ظ��ύ)ϵͳ�л����merchant-idgame-idserver-idseq-strΨһƥ������
		String unique_str = order_id;

		if (passport != null)
		{
			userName = passport.getUserName();
		}

		if (!pay_result.equals("1"))
		{// ʧ�ܶ���
			logger.info("����֧��ʧ��!������:"+order_id+";��"+amount+";������룺"+pay_result);
			billService.accountFailNotify(account_record, pay_result);
		}
		else
		{
			String[] hmacArgs = { cmd, pay_result, pay_sq, amount, currency,
					order_id, bank_order_id, ext };
			
//			String newHmac = DigestUtil.getHmac(hmacArgs, merchant_key);
//			logger.info("newHmac:"+newHmac);
			/*if (!newHmac.equals(hmac))
			{// hmac��֤ʧ��
				resultWml = "hmac��֤ʧ��!";
				logger.info("�����ص�,��ֵʧ��:"+resultWml+"�������ţ�"+order_id);
			}
			else*/
			{
				// �̻���Ӧ�߼�����
				// ע��:��Ҫ�Ը��ݶ��������أ���ͬ������������
				if( billService.accountSuccessNotify(account_record) )
				{
					String plant_notify = SendPostXml.sendPostToDangle(userName, amount, pc_id,unique_str);
					logger.info("�����ص�,��ֵ�ɹ�;ƽ̨֪ͨ����ֵ��"+plant_notify+"�������ţ�"+order_id);
				}
			}
		}
		resultWml = "Y";
		request.setAttribute("resultWml", resultWml);
		return mapping.findForward("success");
	}
}