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
	 * ������ֵͨ������ת
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
					return mapping.findForward("szx_index");//������
				}
				case 2 :
				{
					session.setAttribute("cardType","3006");
					return mapping.findForward("sd_index");//ʢ��
				}
				case 3 :
				{
					session.setAttribute("cardType","3004");
					return mapping.findForward("jun_index");//����һ��ͨ
				}
				case 4 :
				{
					return mapping.findForward("zht_index");//��;��
				}
				case 5 :
				{
					session.setAttribute("cardType","3011");
					return mapping.findForward("szftelecom_index");//���ſ�
				}
				case 6 :
				{
					session.setAttribute("cardType","3008");
					return mapping.findForward("szfunicom_index");//��ͨ��
				}
				case 7 :
				{
					session.setAttribute("cardType","3003");
					return mapping.findForward("szfszx_index");//������
				}
			}
		}
		
		return mapping.findForward("index");
	}
	
	
	/**
	 * ���뿨�ź�����ĸ�����ֵͨ������ת
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
					return mapping.findForward("szx_input");//������
				}
				case 2 :
				{
					return mapping.findForward("sd_input");//ʢ��
				}
				case 3 :
				{
					return mapping.findForward("jun_input");//����һ��ͨ
				}
				case 4 :
				{
					return mapping.findForward("zht_input");//��;��
				}
				case 5 :
				{
					return mapping.findForward("szftelecom_input");//���ſ�
				}
				case 6 :
				{
					return mapping.findForward("szfunicom_input");//��ͨ��
				}
				case 7 :
				{
					return mapping.findForward("szfszx_input");//������
				}
			}
		}
		
		return mapping.findForward("index");
	}
	
	/**
	 * ��ת��ȷ��ҳ��
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
					return mapping.findForward("pay_comfirm");//������
				}
				case 2 :
				{
					request.setAttribute("pd_FrpId","SNDACARD");
					return mapping.findForward("pay_comfirm");//ʢ��
				}
				case 3 :
				{
					request.setAttribute("pd_FrpId", "JUNNET");
					return mapping.findForward("pay_comfirm");//����һ��ͨ
				}
				case 4 :
				{
					request.setAttribute("pd_FrpId", "ZHENGTU");
					return mapping.findForward("pay_comfirm");//��;��
				}
				case 5 :
				{
					request.setAttribute("pd_FrpId", "2");
					return mapping.findForward("szf_pay_comfirm");//���ſ�
				}
				case 6 :
				{
					request.setAttribute("pd_FrpId", "1");
					return mapping.findForward("szf_pay_comfirm");//��ͨ��
				}
				case 7 :
				{
					request.setAttribute("pd_FrpId", "0");
					return mapping.findForward("szf_pay_comfirm");//������
				}
			}
		}
		
		return mapping.findForward("index");
	}
	
	/**
	 * ��ֵ�ύ
	 */
	public ActionForward pay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		String p_pk = (String)session.getAttribute("pPk");
		String u_pk = (String)session.getAttribute("uPk");
		
		if( p_pk==null || u_pk==null  )
		{
			logger.info("session��������Ч��pPk��uPk");
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
		// �̻�������
		String addtime = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		String order_id=GameConfig.getChannelId()+"s"+addtime;
		//String p2_Order = formatString(request.getParameter("p2_Order"));
		String p2_Order=order_id;
		// �������
		String p3_Amt = formatString(request.getParameter("pay"));

		// ����
		String pa7_cardNo = formatString(request.getParameter("code"));

		// ������
		String pa8_cardPwd = formatString(request.getParameter("psw"));


		// ����ͨ��
		String pd_FrpId = formatString(request.getParameter("pd_FrpId"));
		
		// ���׳ɹ�֪ͨ��ַ
		String p8_Url = formatString(GameConfig.getUrlOfGame()+"/game/yeepay/callback.do");
		
		logger.info("�ص���ַ��"+p8_Url);
		String RechargeStatus="";
		//��¼��ҳ�ֵ��Ϣ
		UAccountRecordVO account_record = new UAccountRecordVO();
		account_record.setUPk(Integer.parseInt(u_pk));
		account_record.setPPk(Integer.parseInt(p_pk));
		account_record.setCode(pa7_cardNo);
		account_record.setPwd(pa8_cardPwd);
		account_record.setMoney(Integer.parseInt(p3_Amt));
		account_record.setChannel(pd_FrpId);
		account_record.setAccountState("���ͳ�ֵ����");
		
		logger.info("�ױ�("+pd_FrpId+")ͨ��");
		int record_id = billService.account(account_record);
		
		
		// �̻���չ��Ϣ
		String pa_MP = record_id+"";
		
		try {
			NonBankcardPaymentResult rs = NonBankcardService.pay(p2_Order,p3_Amt,p8_Url,pa_MP,pa7_cardNo,pa8_cardPwd,pd_FrpId);
			//��ֵ�ĳɹ�
			if("1".equals(rs.getR1_Code()))
			{
				resultWml = billService.getSuccessHint();
				RechargeStatus="0";
			}else
			{
				logger.info("�ױ�("+pd_FrpId+")ͨ���ύ��ֵ�����ύ��ֵ����ʧ��,�������"+rs.getR1_Code());
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
			logger.info("�ױ�("+pd_FrpId+")ͨ���ύ��ֵ���󣺳�ֵʧ��,�������"+e.toString());
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
	 * ��ֵ�ύ
	 */
	public ActionForward payBySZF(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		String p_pk = (String)session.getAttribute("pPk");
		String u_pk = (String)session.getAttribute("uPk");
		
		if( p_pk==null || u_pk==null  )
		{
			logger.info("session��������Ч��pPk��uPk");
			return null;
		}
		
		BillService billService = new BillService();
		
		String resultWml = null;
		
		// �̻�������
		String addtime = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		
		//������ID
		String orderId = GameConfig.getChannelId()+"s"+addtime;
		
		// �������
		String payMoney = formatString(request.getParameter("pay"));

		// ����
		String code = formatString(request.getParameter("code"));

		// ������
		String psw = formatString(request.getParameter("psw"));

		// ����ͨ��
		String cardTypeCombine = formatString(request.getParameter("pd_FrpId"));
		
		// ���׳ɹ�֪ͨ��ַ
		String returnUrl = formatString(GameConfig.getUrlOfGame()+"/szf/callback.do");
		
		logger.info("�ص���ַ��"+returnUrl);
		
		//��¼��ҳ�ֵ��Ϣ
		UAccountRecordVO account_record = new UAccountRecordVO();
		account_record.setUPk(Integer.parseInt(u_pk));
		account_record.setPPk(Integer.parseInt(p_pk));
		account_record.setCode(code);
		account_record.setPwd(psw);
		account_record.setMoney(Integer.parseInt(payMoney));
		account_record.setChannel(cardTypeCombine);
		account_record.setAccountState("���ͳ�ֵ����");
		String RechargeStatus="";
		logger.info("���ݸ�("+cardTypeCombine+")ͨ��");
		int record_id = billService.account(account_record);
		
		// �̻���չ��Ϣ
		String pa_MP = record_id+"";
		
		try {
			SZFService s = new SZFService();
			String rs = s.payBySZF(orderId,Integer.parseInt(payMoney),returnUrl,pa_MP,code,psw,cardTypeCombine);
			//��ֵ�ĳɹ�
			if(rs.equals("200"))
			{
				resultWml = billService.getSuccessHint();
				RechargeStatus="0";
			}else
			{
				logger.info("���ݸ�"+cardTypeCombine+")ͨ���ύ��ֵ�����ύ��ֵ����ʧ��,�������"+rs);
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
			logger.info("���ݸ�("+cardTypeCombine+")ͨ���ύ��ֵ���󣺳�ֵʧ��,�������"+e.toString());
			resultWml = billService.getFailHint(e.toString());
			billService.updateState(record_id, e.toString());
		}
		request.setAttribute("resultWml", resultWml);
		return mapping.findForward("success");
	}
	
}