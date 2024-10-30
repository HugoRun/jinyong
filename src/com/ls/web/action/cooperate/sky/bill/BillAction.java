package com.ls.web.action.cooperate.sky.bill;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.web.service.cooperate.sky.BillService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.validate.ValidateService;


public class BillAction extends DispatchAction {
	

	Logger logger = Logger.getLogger("log.pay");
	
	/**
	 * ����ͨ����ҳ
	 */
	public ActionForward n0(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		int channel_id = GameConfig.getChannelId();
		if( channel_id==Channel.SINA)//����ƽ̨
		{
			return mapping.findForward("sina_bill_index");
		}
		if( channel_id==Channel.DANGLE)//����ƽ̨
		{
			return mapping.findForward("dl_bill_index");
		}
		else if( channel_id==Channel.WANXIANG)//�Լ�ƽ̨
		{
			return mapping.findForward("yeepay_bill_index");
		}
		else if( channel_id==Channel.TIAO)//����ƽ̨
		{
			return mapping.findForward("tiao_bill_index");
		}
		else if( channel_id==Channel.JUU)//����ƽ̨
		{
			return mapping.findForward("juu_bill_index");
		}
		else if( channel_id==Channel.YOUVB)//����ƽ̨
		{
			return mapping.findForward("youvb_bill_index");
		}
		else if( channel_id==Channel.AIR)//������
		{
			return mapping.findForward("air_bill_index");
		}
		else if( channel_id==Channel.GGW)//������
		{
			return mapping.findForward("ggw_bill_index");
		}
		else if( channel_id==Channel.OKP)//������
		{
			return mapping.findForward("ok_bill_index");
		}
		else if( channel_id==Channel.TXW)//������
		{
			return mapping.findForward("txw_bill_index");
		}
		else if( channel_id==Channel.TELE)//���յ���
		{
			return mapping.findForward("tele_bill_index");
		}
		return mapping.findForward("index");//Ĭ��˼����ֵͨ��
	}

	/**
	 * ˼������ͨ��
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String uPk = (String)request.getSession().getAttribute("uPk");
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		
		String kbamt_str = request.getParameter("kbamt");//�û��ύ�۷ѽ��
		
		ValidateService validateService = new ValidateService();
		String hint = validateService.validateNonZeroNegativeIntegers(kbamt_str);
		
		if( hint!=null )
		{
			request.setAttribute("hint", hint);
			return mapping.findForward("index");
		}
		
		int kbamt = Integer.parseInt(kbamt_str.trim());
		
		if( kbamt>2000 )
		{
			hint = "ÿ�ζһ�KB���������ó���2000KB,�ɽ��ж�ζһ�";
			request.setAttribute("hint", hint);
			return mapping.findForward("index");
		}
		
		HttpSession session = request.getSession();
		String ssid = (String)session.getAttribute("ssid");
		String skyid = (String)session.getAttribute("skyid");
		
		BillService billService = new BillService();
		Map<String,String> pay_results = billService.pay(uPk,ssid, skyid, kbamt+"",roleInfo.getBasicInfo().getPPk());
		
		
		String result = pay_results.get("result");
		if( result!=null && result.equals("0"))
		{
			EconomyService economyService = new EconomyService();
			long yuanbao = economyService.getYuanbao(Integer.parseInt(uPk));
			//�һ��ɹ�,�������50����Ԫ������Ŀǰ�����С�Ԫ������120��
			String sd = billService.chongZhiJiangLi(roleInfo.getBasicInfo().getPPk(), kbamt);
			hint = "�һ��ɹ�,�������"+kbamt+"����"+GameConfig.getYuanbaoName()+"��,Ŀǰ�����С�"+GameConfig.getYuanbaoName()+"����"+yuanbao+"!"+sd;
			request.setAttribute("hint", hint);
			return mapping.findForward("success");
		}
		else
		{
			hint = billService.getPayHintByResult(pay_results);
			request.setAttribute("hint", hint);
			return mapping.findForward("fail");
		}
	}
	
	
}