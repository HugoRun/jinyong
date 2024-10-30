package com.pm.action.auction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.config.GameConfig;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipDisplayService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.RoleService;
import com.pm.service.auction.AuctionService;
import com.pm.vo.auction.AuctionInfoVO;
import com.pm.vo.auction.AuctionVO;
import com.pm.vo.constant.AuctionType;

public class AuctionGetAction extends DispatchAction{
	
	Logger logger =  Logger.getLogger("log.action");
	

	/************拍卖场购买物品 一口价***************/
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		/*****1灵石拍卖2仙晶拍卖********************/
		String pay_type=(String)request.getSession().getAttribute("pay_type");
		/*****************************************/
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int pPk = roleInfo.getBasicInfo().getPPk();
		
		String resultWml = "";
		AuctionService auctionSerivce = new AuctionService();
		int auctionTypes = Integer.valueOf(request.getParameter("w_type"));
		String auction_id = request.getParameter("auction_id");
		
		// 如果没有确认,让其确认
		String code = (String) request.getParameter("code");
		if (code == null || code.equals("")) {
			request.setAttribute("w_type", auctionTypes+"");
			request.setAttribute("auction_id", auction_id);
			return mapping.findForward("resure");			
		}
		
		AuctionVO auctionvo = auctionSerivce.getAuctinVOById(auction_id);
		PartInfoVO partvo = auctionSerivce.getPartInfo(pPk);
		
		logger.info("pPk : "+pPk+"身上有钱:"+partvo.getPCopper()+"拍卖需要 : "+auctionvo.getGoodsPrice());
		if(roleInfo.getBasicInfo().getWrapSpare() > 1){
			logger.info("身上有空余空格");
			if("1".equals(pay_type))
			{
				if(Long.valueOf(partvo.getPCopper())>=auctionvo.getGoodsPrice())
				{
					resultWml = auctionSerivce.setToWrap(pPk,auction_id,partvo);
					request.setAttribute("resultWml", resultWml);
					request.setAttribute("w_type", auctionTypes+"");
					logger.info("装备的resultWml"+resultWml);
					return mapping.findForward("auction_buy_info");
				}
				else
				{
					resultWml = "您身上的灵石不足！";
					request.setAttribute("resultWml", resultWml);
					request.setAttribute("w_type", auctionTypes+"");
					request.setAttribute("auction_id", auction_id);
					logger.info("灵石不够resultWml"+resultWml);
					return mapping.findForward("auction_buy_info");
				}
			}
			else
			{
				EconomyService es=new EconomyService();
				long havemoney= es.getYuanbao(roleInfo.getBasicInfo().getUPk());
				if(havemoney>auctionvo.getGoodsPrice())
				{
					resultWml = auctionSerivce.setToWrap(pPk,auction_id,partvo);
					request.setAttribute("resultWml", resultWml);
					request.setAttribute("w_type", auctionTypes+"");
					logger.info("装备的resultWml"+resultWml);
					return mapping.findForward("auction_buy_info");
				}
				else
				{
					resultWml = "您身上的仙晶不足，请先充值再来竞拍";
					request.setAttribute("resultWml", resultWml);
					request.setAttribute("w_type", auctionTypes+"");
					request.setAttribute("auction_id", auction_id);
					logger.info("仙晶不够resultWml"+resultWml);
					return mapping.findForward("auction_buy_info");
				}
			}
			
		}else {
			resultWml = "您不能够携带更多的物品了！";
			request.setAttribute("resultWml", resultWml);
			request.setAttribute("w_type", auctionTypes+"");
			request.setAttribute("auction_id", auction_id);
			logger.info("包裹不够resultWml"+resultWml);
			return mapping.findForward("auction_buy_info");
		}
	}
	
    	/****************拍卖场购买物品 竞拍价********************/
    	public ActionForward n8(ActionMapping mapping, ActionForm form,
    			HttpServletRequest request, HttpServletResponse response)
    	{
    		
    		/*****1灵石拍卖2仙晶拍卖********************/
    		String pay_type=(String)request.getSession().getAttribute("pay_type");
    		/*****************************************/
    		logger.info("leihaoyi"+pay_type);
    		RoleService roleService = new RoleService();
    		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
    		int pPk = roleInfo.getBasicInfo().getPPk();
    		
    		String resultWml = "";
    		AuctionService auctionSerivce = new AuctionService();
    		int auctionTypes = Integer.valueOf(request.getParameter("w_type"));
    		String auction_id = request.getParameter("auction_id");
    		
    		AuctionVO auctionvo = auctionSerivce.getAuctinVOById(auction_id);
    		PartInfoVO partvo = auctionSerivce.getPartInfo(pPk);
    		String auctionprice=request.getParameter("auctionprice");
    		if(auctionprice==null||auctionprice.equals(""))
    		{
    			resultWml="竞拍价格不可为空，请输入正确的竞拍价格";
    			request.setAttribute("resultWml", resultWml);
    			request.setAttribute("w_type", auctionTypes+"");
    			request.setAttribute("auction_id", auction_id);
    			return mapping.findForward("auction_buy_info");
    		}
    		if(!StringUtil.isNumber(auctionprice))
    		{
    			resultWml="您输入的竞拍价格不是数字，请输入数字";
    			request.setAttribute("resultWml", resultWml);
    			request.setAttribute("w_type", auctionTypes+"");
    			request.setAttribute("auction_id", auction_id);
    			return mapping.findForward("auction_buy_info");
    		}
    		long needprice=0;
    		//如果已经有人竞拍此物品
    		if(auctionvo.getAuctionSell()==3)
    		{
    			needprice=auctionvo.getBuyPrice();
    			if(Integer.parseInt(auctionprice)<=needprice)
    			{
    				resultWml="您要输入比他人更高的竞拍价格才能竞拍";
        			request.setAttribute("resultWml", resultWml);
        			request.setAttribute("w_type", auctionTypes+"");
        			request.setAttribute("auction_id", auction_id);
        			return mapping.findForward("auction_buy_info");
    			}
    		}
    		else
    		{
    			needprice=auctionvo.getAuction_price();
    			if(Integer.parseInt(auctionprice)<needprice)
    			{
    				resultWml="输入价格不能低于竞拍价格";
        			request.setAttribute("resultWml", resultWml);
        			request.setAttribute("w_type", auctionTypes+"");
        			request.setAttribute("auction_id", auction_id);
        			return mapping.findForward("auction_buy_info");
    			}
    		}
    		logger.info("竞拍的支付类型为"+pay_type+"需要的的价格是"+needprice);
    		if(roleInfo.getBasicInfo().getWrapSpare() > 1){
    			logger.info("身上有空余空格");
    			if("1".equals(pay_type))
    			{
    				if(Long.valueOf(partvo.getPCopper())>Integer.parseInt(auctionprice))
    				{
    					resultWml= auctionSerivce.setToWrapByAuction(pPk, auction_id,Integer.parseInt(auctionprice) );
    					request.setAttribute("resultWml", resultWml);
    					request.setAttribute("w_type", auctionTypes+"");
    					logger.info("灵石竞拍"+resultWml);
    					return mapping.findForward("auction_buy_info");
    				}
    				else
    				{
    					resultWml = "您身上的灵石不足！";
    					request.setAttribute("resultWml", resultWml);
    					request.setAttribute("w_type", auctionTypes+"");
    					request.setAttribute("auction_id", auction_id);
    					logger.info("灵石不够resultWml"+resultWml);
    					return mapping.findForward("auction_buy_info");
    				}
    			}
    			else
    			{
    				EconomyService es=new EconomyService();
    				long havemoney= es.getYuanbao(roleInfo.getBasicInfo().getUPk());
    				if(havemoney>Integer.parseInt(auctionprice))
    				{
    					resultWml= auctionSerivce.setToWrapByAuction(pPk, auction_id,Integer.parseInt(auctionprice));
    					request.setAttribute("resultWml", resultWml);
    					request.setAttribute("w_type", auctionTypes+"");
    					logger.info("仙晶竞拍"+resultWml);
    					return mapping.findForward("auction_buy_info");
    				}
    				else
    				{
    					resultWml = "您身上的仙晶不足，请先充值再来竞拍";
    					request.setAttribute("resultWml", resultWml);
    					request.setAttribute("w_type", auctionTypes+"");
    					request.setAttribute("auction_id", auction_id);
    					logger.info("仙晶不够resultWml"+resultWml);
    					return mapping.findForward("auction_buy_info");
    				}
    			}
    			
    		}else {
    			resultWml = "您不能够携带更多的物品了！";
    			request.setAttribute("resultWml", resultWml);
    			request.setAttribute("w_type", auctionTypes+"");
    			request.setAttribute("auction_id", auction_id);
    			logger.info("包裹不够resultWml"+resultWml);
    			return mapping.findForward("auction_buy_info");
    		}
    	}
	
    	/************拍卖场仓库信息money洪荒版,下面那个方法将不再使用******************/
    	
    	public ActionForward n9(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		{
    		String getType=request.getParameter("getType");
    		if(getType==null)
    		{
    			getType="1";
    		}
    		RoleService roleService = new RoleService();
			RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
			int pPk = roleInfo.getBasicInfo().getPPk();
			AuctionService auctionSerivce = new AuctionService();
			if(getType.equals("1"))
			{
				//得到拍卖后的钱财
				List<AuctionVO> moneyList = auctionSerivce.getMoneyList(String.valueOf(pPk));
				request.setAttribute("list", moneyList);
				return mapping.findForward("house");
			}
			else
			{
				//得到没有卖出去的物品
				List<AuctionVO> goodsList = auctionSerivce.getGoodsList(String.valueOf(pPk),Integer.parseInt(getType));
				request.setAttribute("list", goodsList);
				return mapping.findForward("house");
			}
			
		}	
		
		// 拍卖场仓库取物品
		public ActionForward n3(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		{
			RoleService roleService = new RoleService();
			RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
			int pPk = roleInfo.getBasicInfo().getPPk();
			
			String resultWml = "";
			AuctionService auctionSerivce = new AuctionService();
			request.setAttribute("pPk", pPk + "");
			request.setAttribute("id", roleInfo.getBasicInfo().getUPk() + "");
			request.setAttribute("mapid", roleInfo.getBasicInfo().getSceneId()+"");

			int auctionTypes = Integer.valueOf(request.getParameter("w_type"));
			String auction_id = request.getParameter("auction_id");
			AuctionVO auctionvo = auctionSerivce.getAuctinVOById(auction_id);
			PartInfoVO partvo = auctionSerivce.getPartInfo(pPk);
			
			String flag = request.getParameter("flag");
			logger.info("flag : "+flag+"auctionType : "+auctionTypes);
			/*******从拍卖仓库取出物品******/
			if(flag.equals("goods")){
				if(roleInfo.getBasicInfo().getWrapSpare() > 1){
					if(auctionTypes == AuctionType.ACCOUTE || auctionTypes == AuctionType.ARM || auctionTypes == AuctionType.JEWELRY){
						
						resultWml = auctionSerivce.setAuctionWrap(pPk,auctionvo,partvo);
						request.setAttribute("resultWml", resultWml);
						logger.info("装备的resultWml"+resultWml);
						request.setAttribute("w_type", auctionTypes+"");
						return mapping.findForward("auction_hint");
					}else {
						resultWml = auctionSerivce.setAuctionPG(pPk,auctionvo,partvo);
						request.setAttribute("resultWml", resultWml);
						
						return mapping.findForward("auction_hint");
					}
				} else {
					resultWml = "您不能再携带更多的物品！";
					request.setAttribute("resultWml", resultWml);
					request.setAttribute("w_type", auctionTypes+"");
					request.setAttribute("auction_id", auction_id);
					logger.info("包裹不够resultWml"+resultWml);
					return mapping.findForward("auction_hint");
				}
			}
			/*********从拍卖仓库取出钱财********/
			else if(flag.equals("money")){
				//身上最多携带1000万银
				if(Long.valueOf(partvo.getPCopper())+auctionvo.getGoodsPrice() < 1000000000 ){
					resultWml = auctionSerivce.getAuctionMoney(pPk,auctionvo,partvo);
					request.setAttribute("resultWml", resultWml);
					request.setAttribute("w_type", auctionTypes+"");
					request.setAttribute("auction_id", auction_id);
					logger.info("成功resultWml"+resultWml);
					return mapping.findForward("auction_hint");
					
				}else {
					resultWml = "您不能再携带更多的金钱！";
					request.setAttribute("resultWml", resultWml);
					request.setAttribute("w_type", auctionTypes+"");
					request.setAttribute("auction_id", auction_id);
					logger.info("金钱多resultWml"+resultWml);
					return mapping.findForward("auction_hint");
				}
				
			}else {
				resultWml = "您的输入有误！";
				request.setAttribute("resultWml", resultWml);
				request.setAttribute("w_type", auctionTypes+"");
				request.setAttribute("auction_id", auction_id);
				logger.info("输入有误resultWml"+resultWml);
				return mapping.findForward("auction_hint");
			}
		}
		
		
		// 拍卖场消息助手
		public ActionForward n4(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		{
			RoleService roleService = new RoleService();
			RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
			int pPk = roleInfo.getBasicInfo().getPPk();
			String page_no= request.getParameter("page_no");
			if(page_no==null)
			{
				page_no="1";
			}
			AuctionService auctionSerivce = new AuctionService();
			request.setAttribute("pPk", pPk + "");
			request.setAttribute("id", roleInfo.getBasicInfo().getUPk() + "");
			request.setAttribute("mapid", roleInfo.getBasicInfo().getSceneId()+"");
			QueryPage queryPage = auctionSerivce.getAuctionInfoList(pPk,Integer.parseInt(page_no));
			queryPage.setURL(response, response.encodeURL(GameConfig.getContextPath()+"/menu/auctionGet.do?cmd=n4"));
			request.setAttribute("queryPage",queryPage);
			return mapping.findForward("auction_info");
		}
		
		// 查询拍卖场时包裹道具物品详情
		public ActionForward n5(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {

			//UserTempBean userTempBean = (UserTempBean) request.getSession().getAttribute("userTempBean");
			
			RoleService roleService = new RoleService();
			RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
			int pPk = roleInfo.getBasicInfo().getPPk();
			
			request.setAttribute("pPk",pPk);
			String auction_type = request.getParameter("auction_type");
			logger.info("auction_type="+auction_type);
			
			String auction_id = request.getParameter("auction_id");
			String goods_id = request.getParameter("prop_id");
			String page_no = request.getParameter("page_no");

			if (goods_id == null || auction_type == null || auction_id == null) {
				////System.out.print("goods_id或w_type或auction_id为空");
			}

			String prop_display = null;
			GoodsService goodsService = new GoodsService();
			
			prop_display = goodsService.getPropInfoWml(pPk,Integer.parseInt(goods_id));
			
			request.setAttribute("auction_type", auction_type);
			request.setAttribute("auction_id", auction_id);
			request.setAttribute("page_no", page_no);
			request.setAttribute("prop_id", goods_id);
			request.setAttribute("resultWml", prop_display);
			return mapping.findForward("prop_display");
		}
		
		// 查询拍卖场时包裹装备物品详情
		public ActionForward n6(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
			
			RoleService roleService = new RoleService();
			RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
			
			String auction_id = request.getParameter("auction_id");
			String goods_id = request.getParameter("prop_id");
			String page_no = request.getParameter("page_no");
			String auction_type = request.getParameter("auction_type");
			
			request.setAttribute("auction_type", auction_type);
			request.setAttribute("auction_id", auction_id);
			request.setAttribute("page_no", page_no);
			request.setAttribute("prop_id", goods_id);
			
			
			AuctionService auctionService = new AuctionService();
			AuctionVO auctionvo = (AuctionVO) auctionService.getAuctinVOById(auction_id);

			
			GoodsService goodsService = new GoodsService();
			EquipDisplayService equipDisplayService = new EquipDisplayService();

			PlayerEquipVO equip = (PlayerEquipVO) goodsService.getEquipByID(Integer.parseInt(goods_id));
			
			String equip_display = equipDisplayService.getEquipDisplay(roleInfo,equip, true);
			
			request.setAttribute("partWrapVO", equip);
			request.setAttribute("equip_display", equip_display);
			
			request.setAttribute("page_no", page_no);
			request.setAttribute("auctionvo", auctionvo);
			return mapping.findForward("equip_display");
		}
		
		// 拍买确认
		public ActionForward n7(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		{
			/***type1表示一口价2表示竞拍价**/
			String type=request.getParameter("type");
			/****************************/
			int auctionTypes = Integer.valueOf(request.getParameter("w_type"));
			String auction_id = request.getParameter("auction_id");
			
			AuctionService auctionSerivce = new AuctionService();
			AuctionVO auctionvo = auctionSerivce.getAuctinVOById(auction_id);
			
			
			request.setAttribute("w_type", auctionTypes);
			request.setAttribute("auction_id", auction_id);
			request.setAttribute("auctionvo", auctionvo);
			request.setAttribute("code", "code");
			request.setAttribute("page_no", request.getParameter("page_no"));
			
			request.setAttribute("sortType", request.getParameter("sortType"));
			request.setAttribute("prop_name", request.getParameter("prop_name"));
			if("1".equals(type))
			{
				return mapping.findForward("resure");
			}
			else
			{
				return mapping.findForward("auctionresure");
			}
			
		}
}
