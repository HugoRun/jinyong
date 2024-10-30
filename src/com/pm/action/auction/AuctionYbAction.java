package com.pm.action.auction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.config.GameConfig;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.RoleService;
import com.pm.constant.AuctionNumber;
import com.pm.service.auction.AuctionYbService;
import com.pm.service.mail.MailInfoService;
import com.pm.vo.auction.AuctionYBVO;

/**
 * ����Ԫ���� action
 * @author Administrator
 *
 */
public class AuctionYbAction extends DispatchAction{
	Logger logger =  Logger.getLogger("log.action");

	// ����Ԫ���б�
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		String page_no_str = request.getParameter("page_no");
		if(page_no_str == null || page_no_str.equals("")) {
			page_no_str = "1";
		}
		
		int page_no = 1;
		try
		{
			page_no = Integer.valueOf(page_no_str);
		}
		catch (Exception e)
		{
			page_no = 1;
		}
		
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
				
		AuctionYbService ybService = new AuctionYbService();
		
		QueryPage yb_page = ybService.getYbPageList(roleInfo,  page_no,request,response);
		request.setAttribute("yb_page", yb_page);
		request.setAttribute("roleInfo", roleInfo);
		
		return mapping.findForward("yb_list");
		
	}
	
	// ׼������Ԫ��
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String uyb_id = request.getParameter("w_type");
		String page_no = request.getParameter("page_no");
		
		AuctionYbService ybService = new AuctionYbService();
		AuctionYBVO auctionYBVO = ybService.getAuctionYbByUybId(uyb_id,AuctionNumber.YUANSELLING);
		
		request.setAttribute("auctionYBVO", auctionYBVO);
		request.setAttribute("page_no", page_no);
		
		return mapping.findForward("want_to_buy");
		
	}
	
	// ����Ԫ��
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String uyb_id = request.getParameter("uybId");
		
		AuctionYbService ybService = new AuctionYbService();
		AuctionYBVO auctionYBVO = ybService.getAuctionYbByUybId(uyb_id,AuctionNumber.YUANSELLING);
		String hint = null;
		if ( auctionYBVO == null) {
			hint = "��"+GameConfig.getYuanbaoName()+"�Ѿ�������!";
		}
		
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		// �ȽϹ���Ԫ����Ҫ��Ǯ �Ƿ�С��������Я����Ǯ
		if ( roleInfo.getBasicInfo().getCopper() >= auctionYBVO.getYbPrice()) {
			
			hint = ybService.buyYuan(auctionYBVO,roleInfo,request,response);
		} else {
			
			hint = "����Я���Ľ�Ǯ�����Թ������Ʒ��";			
		}
		
		
		request.setAttribute("hint", hint);
		return mapping.findForward("buy_hint");
	}
	
	
	
	// ��������Ԫ��ҳ��
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		EconomyService economyService = new EconomyService();
		long yuanbao = economyService.getYuanbao(roleInfo.getBasicInfo().getUPk());
		
		request.setAttribute("yuanbao", yuanbao);
		
		return mapping.findForward("ready_auction");
	}
	
	// ��ʼ����Ԫ��,�����������Ԫ������,�������,ת������������Ǯ����
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		EconomyService economyService = new EconomyService();
		long yuanbao = economyService.getYuanbao(roleInfo.getBasicInfo().getUPk());
		
		String yuanbaoString = request.getParameter("yuanbao");
		int paimaiYuanBao = 0;
		try
		{
			paimaiYuanBao = Integer.valueOf(yuanbaoString);
		}
		catch (Exception e)
		{
			request.setAttribute("hint","�������"+GameConfig.getYuanbaoName()+"�������Ϸ�!<br/>");
			request.setAttribute("yuanbao", yuanbao);			
			return mapping.findForward("ready_auction");			
		}
		if (  paimaiYuanBao <= 0) {
			request.setAttribute("hint","�������"+GameConfig.getYuanbaoName()+"�������������!<br/>");
			request.setAttribute("yuanbao", yuanbao);			
			return mapping.findForward("ready_auction");			
		} else if ( paimaiYuanBao <= yuanbao) {
			// ������Ԫ������С�����ϵ�Ԫ������
			request.setAttribute("paimaiYuanBao", paimaiYuanBao);			
			return mapping.findForward("paimaiYuanBao");		
		} else {
			request.setAttribute("hint","�����ϵ�"+GameConfig.getYuanbaoName()+"��������!<br/>");
			request.setAttribute("yuanbao", yuanbao);			
			return mapping.findForward("ready_auction");				
		}
	}
		
		// ��ʼ����Ԫ��,���ת������������Ǯ����,�����������ʼ��Ԫ������
		public ActionForward n7(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		{
			RoleService roleService = new RoleService();
			RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
			
			EconomyService economyService = new EconomyService();
			long yuanbao = economyService.getYuanbao(roleInfo.getBasicInfo().getUPk());
			
			String yuanbaoString = request.getParameter("yuanbaoString");
			int paimaiYuanBao = 0;
			try
			{
				paimaiYuanBao = Integer.valueOf(yuanbaoString);
			}catch (Exception e)
			{
				request.setAttribute("hint","�������"+GameConfig.getYuanbaoName()+"�������Ϸ�!<br/>");
				request.setAttribute("yuanbao", yuanbao);			
				return mapping.findForward("ready_auction");			
			}
			if (  paimaiYuanBao <= 0) {
				request.setAttribute("hint","�������"+GameConfig.getYuanbaoName()+"�������������!<br/>");
				request.setAttribute("yuanbao", yuanbao);			
				return mapping.findForward("ready_auction");			
			} else if ( paimaiYuanBao >= yuanbao) {
				request.setAttribute("hint","�����ϵ�"+GameConfig.getYuanbaoName()+"��������!<br/>");
				request.setAttribute("yuanbao", yuanbao);			
				return mapping.findForward("ready_auction");		
			} else {
				// ������Ԫ������С�����ϵ�Ԫ������,������ȥ
    			String copperString = request.getParameter("copperString");
    			int copper = 0;
    			try
    			{
    				copper = Integer.valueOf(copperString);
    			}catch (Exception e)
    			{
    				request.setAttribute("hint","������Ľ�Ǯ�������Ϸ�!<br/>");
    				request.setAttribute("yuanbao", yuanbao);			
    				return mapping.findForward("ready_auction");			
    			}
    			long money = copper; 	// ����Ľ�Ǯ����
    			long bodyMoney = roleInfo.getBasicInfo().getCopper();
    			if (  money <= 0) {
    				request.setAttribute("hint","������Ľ�Ǯ�������������!<br/>");
    				request.setAttribute("yuanbao", yuanbao);			
    				return mapping.findForward("ready_auction");			
    			} else {
    				AuctionYbService ybService = new AuctionYbService();
    				String hint = ybService.auctionYuanBao(roleInfo,paimaiYuanBao,money);
    				request.setAttribute("hint", hint);
    				return mapping.findForward("buy_hint");
    			}
			}	
	}
		
		// ��ʼ����Ԫ��,���ת������������Ǯ����, ��ʼ����
		public ActionForward n8(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		{
			RoleService roleService = new RoleService();
			RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
			
			EconomyService economyService = new EconomyService();
			long yuanbao = economyService.getYuanbao(roleInfo.getBasicInfo().getUPk());
			
			String yuanbaoString = request.getParameter("paimaiYuanBao");
			int paimaiYuanBao = 0;
			try
			{
				paimaiYuanBao = Integer.valueOf(yuanbaoString);
			}catch (Exception e)
			{
				request.setAttribute("hint","�������"+GameConfig.getYuanbaoName()+"�������Ϸ�!<br/>");
				request.setAttribute("yuanbao", yuanbao);			
				return mapping.findForward("ready_auction");			
			}
			if (  paimaiYuanBao <= 0) {
				request.setAttribute("hint","�������"+GameConfig.getYuanbaoName()+"�������������!<br/>");
				request.setAttribute("yuanbao", yuanbao);			
				return mapping.findForward("ready_auction");			
			} else if ( paimaiYuanBao >= yuanbao) {
				request.setAttribute("hint","�����ϵ�"+GameConfig.getYuanbaoName()+"��������!<br/>");
				request.setAttribute("yuanbao", yuanbao);			
				return mapping.findForward("ready_auction");		
			} else {
				// ������Ԫ������С�����ϵ�Ԫ������,������ȥ
    			String copperString = request.getParameter("copper");
    			int copper = 0;
    			if (copperString == null || copperString.equals("")) {
    				copperString = "0";
    			}
    			try
    			{
    				copper = Integer.valueOf(copperString);
    			}catch (Exception e)
    			{
    				request.setAttribute("hint","������Ľ�Ǯ�������Ϸ�!<br/>");
    				request.setAttribute("yuanbao", yuanbao);			
    				return mapping.findForward("ready_auction");			
    			}
    			long money = copper; 	// ����Ľ�Ǯ����
    			if (  money <= 0) {
    				request.setAttribute("hint","������Ľ�Ǯ�������������!<br/>");
    				request.setAttribute("yuanbao", yuanbao);			
    				return mapping.findForward("ready_auction");			
    			} else {
    				
    				request.setAttribute("yuanbaoString", yuanbaoString);
    				request.setAttribute("copperString", copper+"");
    				
    				return mapping.findForward("auction_sure");	
    			}
			}	
	}
		
		// �����ʼ�����ʼȡ����������
		public ActionForward n9(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		{	
			String uybId = request.getParameter("uybId");
			String mailId = request.getParameter("mailId");
			
			RoleService roleService = new RoleService();
			RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
			
			AuctionYbService ybService = new AuctionYbService();
			AuctionYBVO auctionYBVO = ybService.getAuctionYbByUybIdAndPPk(uybId,AuctionNumber.YUANSELLED,roleInfo.getBasicInfo().getPPk());
			
			if ( auctionYBVO == null) {
				request.setAttribute("hint", "�Բ���,���Ѿ�ȡ������Щ����!");
				return mapping.findForward("return_mail");
			} else {
				ybService.getMoneyByUybId(auctionYBVO,roleInfo,mailId);
				if ( mailId != null && !mailId.equals("")) {
    				// ɾ�������ʼ�
    				MailInfoService mailInfoService = new MailInfoService();
    				mailInfoService.deleteMailByid(mailId,roleInfo.getBasicInfo().getPPk());
				}
				request.setAttribute("hint", "��ȡ��������"+MoneyUtil.changeCopperToStr(auctionYBVO.getYbPrice())+"!,���ʼ��Ѿ�ɾ��!");
				return mapping.findForward("return_mail");
			}
		}
		
		// �����ʼ�����ʼȡ����������
		public ActionForward n10(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		{	
			String uybId = request.getParameter("uybId");
			String mailId = request.getParameter("mailId");
			
			RoleService roleService = new RoleService();
			RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
			
			AuctionYbService ybService = new AuctionYbService();
			AuctionYBVO auctionYBVO = ybService.getAuctionYbByUybIdAndPPk(uybId,AuctionNumber.YUANNOTSELL,roleInfo.getBasicInfo().getPPk());
			
			if ( auctionYBVO == null) {
				request.setAttribute("hint", "�Բ���,���Ѿ�ȡ������Щ"+GameConfig.getYuanbaoName()+"!");
				return mapping.findForward("return_mail");
			} else {	
				if ( mailId != null && !mailId.equals("")) {
    				// ɾ�������ʼ�
    				MailInfoService mailInfoService = new MailInfoService();
    				mailInfoService.deleteMailByid(mailId,roleInfo.getBasicInfo().getPPk());
				}
				ybService.getYuanBaoByUybId(auctionYBVO,roleInfo,mailId);
				request.setAttribute("hint", "��ȡ����"+GameConfig.getYuanbaoName()+""+auctionYBVO.getYbNum()+"!,���ʼ��Ѿ�ɾ��!");
				return mapping.findForward("return_mail");
			}			
		}	
}
