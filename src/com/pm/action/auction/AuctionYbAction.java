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
 * 拍卖元宝的 action
 * @author Administrator
 *
 */
public class AuctionYbAction extends DispatchAction{
	Logger logger =  Logger.getLogger("log.action");

	// 拍卖元宝列表
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
	
	// 准备购买元宝
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
	
	// 购买元宝
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String uyb_id = request.getParameter("uybId");
		
		AuctionYbService ybService = new AuctionYbService();
		AuctionYBVO auctionYBVO = ybService.getAuctionYbByUybId(uyb_id,AuctionNumber.YUANSELLING);
		String hint = null;
		if ( auctionYBVO == null) {
			hint = "该"+GameConfig.getYuanbaoName()+"已经被拍卖!";
		}
		
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		// 比较购买元宝需要的钱 是否小于身上所携带的钱
		if ( roleInfo.getBasicInfo().getCopper() >= auctionYBVO.getYbPrice()) {
			
			hint = ybService.buyYuan(auctionYBVO,roleInfo,request,response);
		} else {
			
			hint = "您所携带的金钱不足以购买此商品。";			
		}
		
		
		request.setAttribute("hint", hint);
		return mapping.findForward("buy_hint");
	}
	
	
	
	// 进入拍卖元宝页面
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
	
	// 开始拍卖元宝,玩家输入拍卖元宝数量,如果正常,转入输入拍卖金钱数量
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
			request.setAttribute("hint","您输入的"+GameConfig.getYuanbaoName()+"数量不合法!<br/>");
			request.setAttribute("yuanbao", yuanbao);			
			return mapping.findForward("ready_auction");			
		}
		if (  paimaiYuanBao <= 0) {
			request.setAttribute("hint","您输入的"+GameConfig.getYuanbaoName()+"数量必须大于零!<br/>");
			request.setAttribute("yuanbao", yuanbao);			
			return mapping.findForward("ready_auction");			
		} else if ( paimaiYuanBao <= yuanbao) {
			// 拍卖的元宝数量小于身上的元宝数量
			request.setAttribute("paimaiYuanBao", paimaiYuanBao);			
			return mapping.findForward("paimaiYuanBao");		
		} else {
			request.setAttribute("hint","您身上的"+GameConfig.getYuanbaoName()+"数量不够!<br/>");
			request.setAttribute("yuanbao", yuanbao);			
			return mapping.findForward("ready_auction");				
		}
	}
		
		// 开始拍卖元宝,玩家转入输入拍卖金钱数量,如果正常，开始将元宝拍卖
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
				request.setAttribute("hint","您输入的"+GameConfig.getYuanbaoName()+"数量不合法!<br/>");
				request.setAttribute("yuanbao", yuanbao);			
				return mapping.findForward("ready_auction");			
			}
			if (  paimaiYuanBao <= 0) {
				request.setAttribute("hint","您输入的"+GameConfig.getYuanbaoName()+"数量必须大于零!<br/>");
				request.setAttribute("yuanbao", yuanbao);			
				return mapping.findForward("ready_auction");			
			} else if ( paimaiYuanBao >= yuanbao) {
				request.setAttribute("hint","您身上的"+GameConfig.getYuanbaoName()+"数量不够!<br/>");
				request.setAttribute("yuanbao", yuanbao);			
				return mapping.findForward("ready_auction");		
			} else {
				// 拍卖的元宝数量小于身上的元宝数量,继续下去
    			String copperString = request.getParameter("copperString");
    			int copper = 0;
    			try
    			{
    				copper = Integer.valueOf(copperString);
    			}catch (Exception e)
    			{
    				request.setAttribute("hint","您输入的金钱数量不合法!<br/>");
    				request.setAttribute("yuanbao", yuanbao);			
    				return mapping.findForward("ready_auction");			
    			}
    			long money = copper; 	// 输入的金钱数量
    			long bodyMoney = roleInfo.getBasicInfo().getCopper();
    			if (  money <= 0) {
    				request.setAttribute("hint","您输入的金钱数量必须大于零!<br/>");
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
		
		// 开始拍卖元宝,玩家转入输入拍卖金钱数量, 开始拍卖
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
				request.setAttribute("hint","您输入的"+GameConfig.getYuanbaoName()+"数量不合法!<br/>");
				request.setAttribute("yuanbao", yuanbao);			
				return mapping.findForward("ready_auction");			
			}
			if (  paimaiYuanBao <= 0) {
				request.setAttribute("hint","您输入的"+GameConfig.getYuanbaoName()+"数量必须大于零!<br/>");
				request.setAttribute("yuanbao", yuanbao);			
				return mapping.findForward("ready_auction");			
			} else if ( paimaiYuanBao >= yuanbao) {
				request.setAttribute("hint","您身上的"+GameConfig.getYuanbaoName()+"数量不够!<br/>");
				request.setAttribute("yuanbao", yuanbao);			
				return mapping.findForward("ready_auction");		
			} else {
				// 拍卖的元宝数量小于身上的元宝数量,继续下去
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
    				request.setAttribute("hint","您输入的金钱数量不合法!<br/>");
    				request.setAttribute("yuanbao", yuanbao);			
    				return mapping.findForward("ready_auction");			
    			}
    			long money = copper; 	// 输入的金钱数量
    			if (  money <= 0) {
    				request.setAttribute("hint","您输入的金钱数量必须大于零!<br/>");
    				request.setAttribute("yuanbao", yuanbao);			
    				return mapping.findForward("ready_auction");			
    			} else {
    				
    				request.setAttribute("yuanbaoString", yuanbaoString);
    				request.setAttribute("copperString", copper+"");
    				
    				return mapping.findForward("auction_sure");	
    			}
			}	
	}
		
		// 根据邮件，开始取回所得银两
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
				request.setAttribute("hint", "对不起,您已经取出了这些银两!");
				return mapping.findForward("return_mail");
			} else {
				ybService.getMoneyByUybId(auctionYBVO,roleInfo,mailId);
				if ( mailId != null && !mailId.equals("")) {
    				// 删除掉该邮件
    				MailInfoService mailInfoService = new MailInfoService();
    				mailInfoService.deleteMailByid(mailId,roleInfo.getBasicInfo().getPPk());
				}
				request.setAttribute("hint", "您取回了银两"+MoneyUtil.changeCopperToStr(auctionYBVO.getYbPrice())+"!,该邮件已经删除!");
				return mapping.findForward("return_mail");
			}
		}
		
		// 根据邮件，开始取回所得银两
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
				request.setAttribute("hint", "对不起,您已经取出了这些"+GameConfig.getYuanbaoName()+"!");
				return mapping.findForward("return_mail");
			} else {	
				if ( mailId != null && !mailId.equals("")) {
    				// 删除掉该邮件
    				MailInfoService mailInfoService = new MailInfoService();
    				mailInfoService.deleteMailByid(mailId,roleInfo.getBasicInfo().getPPk());
				}
				ybService.getYuanBaoByUybId(auctionYBVO,roleInfo,mailId);
				request.setAttribute("hint", "您取回了"+GameConfig.getYuanbaoName()+""+auctionYBVO.getYbNum()+"!,该邮件已经删除!");
				return mapping.findForward("return_mail");
			}			
		}	
}
