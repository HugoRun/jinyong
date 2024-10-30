package com.ls.web.action.cooperate.dl.bill.yeepay;

import java.io.UnsupportedEncodingException;

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
import com.ls.pub.yeepay.nonbankcard.NonBankcardPaymentResult;
import com.ls.pub.yeepay.nonbankcard.NonBankcardService;
import com.ls.web.service.cooperate.bill.BillService;


/**
 * @author ls
 * ����:�����г�ֵ
 * Jan 12, 2009
 */
public class ReqAction extends DispatchAction {
	
	Logger logger = Logger.getLogger("log.pay");
	
	/**
	 * ��ֵ
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
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
		String order_id="017001"+addtime;
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
		String p8_Url = formatString("http://189hi.cn/orderrcv/yeepayszx_feed.do");//��ʽ�������е�ַ
		if( pd_FrpId.equals("SNDACARD"))
		{
			p8_Url = "http://189hi.cn/orderrcv/yeepaysnda_feed.do";//��ʽ��ʢ���ַ
		}
		//"http://219.239.94.130:8080/orderrcv/yeepaysnda_feed.do"                             //��ʽ��ʢ���ַ
		//String p8_Url = formatString("http://203.86.68.248:8888/orderrcv/yeepayszx_feed.do");//���Ե�ַ
		
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
		
		String area_id = GameConfig.getAreaId();
		
		// �̻���չ��Ϣ
		//String pa_MP = formatString(request.getParameter("pa_MP"));
		String pa_MP = area_id+"and"+record_id;
		
		try {
			NonBankcardPaymentResult rs = NonBankcardService.pay(p2_Order,p3_Amt,p8_Url,pa_MP,pa7_cardNo,pa8_cardPwd,pd_FrpId);
			//��ֵ�ĳɹ�
			if("1".equals(rs.getR1_Code()))
			{
				resultWml = billService.getSuccessHint();
				//resultWml = resultWml+"�㽫�õ�"+p3_Amt+"��Ԫ��<br/>";
			}else
			{
				logger.info("�ױ�("+pd_FrpId+")ͨ���ύ��ֵ�����ύ��ֵ����ʧ��,�������"+rs.getR1_Code());
				resultWml = billService.getFailHint(rs.getR1_Code());

				//out.println("������<a href='"+response.encodeURL("index.jsp")+"'>���س�ֵ</a><br/>");
			}
			billService.updateState(record_id, rs.getR1_Code());
			
			/* 	�÷����Ǹ��ݡ��ױ�֧�������п�֧��רҵ��ӿ��ĵ� v3.0������һ��ģ��Ľ��׽��֪ͨ��.
			 * 	�̻�ʹ��ģ��Ľ��׽��֪ͨ������ֱ�Ӳ����Լ��Ľ��׽�����ճ���(callback)����ȷ��.
			 * 	ʵ�ʵĽ��׽��֪ͨ�����ԡ��ױ�֧�������п�֧��רҵ��ӿ��ĵ� v3.0��Ϊ׼���÷���ֻ��
			 * 	ģ���˽��׽��֪ͨ��.��ʽ����ʱ�벻Ҫ���ô˷���. 
			 */
			
			//NonBankcardService.generationTestCallback(p2_Order,p3_Amt,p8_Url,pa7_cardNo,pa8_cardPwd,pa_MP);
		}
		catch(Exception e )
		{
			e.printStackTrace();
			logger.info("�ױ�("+pd_FrpId+")ͨ���ύ��ֵ���󣺳�ֵʧ��,�������"+e.toString());
			resultWml = billService.getFailHint(e.toString());
			billService.updateState(record_id, e.toString());
		}
		//logger.info(resultWml);
		request.setAttribute("resultWml", resultWml);
		return mapping.findForward("success");
	}
	
	private  String formatString(String text){ 
		if(text == null) {
			return "";  
		}
		return text;
		
	}
}