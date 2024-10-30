package com.pm.action.auction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.Auctiontype;
import com.ls.pub.constant.Wrap;
import com.ls.pub.util.StringUtil;
import com.ls.web.action.ActionBase;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipDisplayService;
import com.ls.web.service.player.EconomyService;
import com.pm.constant.AuctionNumber;
import com.pm.service.auction.AuctionService;

public class AuctionAction extends ActionBase{

	Logger logger =  Logger.getLogger("log.action");
	
	// 道具列表
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		int w_type=Auctiontype.PROP;
		RoleEntity  roleInfo = this.getRoleEntity(request);
		int pPk = roleInfo.getBasicInfo().getPPk();
		EconomyService economyServcie = new EconomyService();
		long yuanbao = economyServcie.getYuanbao(roleInfo.getBasicInfo().getUPk());
		request.setAttribute("yuanbao", "" + yuanbao);

		GoodsService goodsSerivce = new GoodsService();
		if (w_type == Auctiontype.PROP)
		{
			List cure_list = goodsSerivce.getPlayerPropList(pPk, 0);
			request.setAttribute("backType", w_type+"");
			request.setAttribute("cure_list", cure_list);
			return mapping.findForward("cure_list");
		}
		return null;
	}
	//装备列表
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
	    String table_type=request.getParameter("table_type");
	    request.getSession().setAttribute("table_type", table_type);
	    String page_no=request.getParameter("page_no");
	    GoodsService gs=new GoodsService();
	    if(page_no==null||page_no.equals("null"))
	    {
	    	page_no="1";
	    }
	    QueryPage queryPage=gs.getPageEquipOnWrap(roleInfo.getPPk(), Integer.parseInt(table_type), Integer.parseInt(page_no));
	    queryPage.setURL(response, "/menu/auction.do?cmd=n5&amp;w_type="+table_type);
		request.setAttribute("queryPage",queryPage);
		request.setAttribute("table_type", table_type);
		return mapping.findForward("equip_list");
	}
	
	// 拍卖操作	
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		/*****1灵石拍卖2仙晶拍卖********************/
		String pay_type=(String)request.getSession().getAttribute("pay_type");
		/*****************************************/
		String backType=(String)request.getParameter("table_type");
		String pageNum=(String)request.getParameter("pageNo");
		logger.info("leihaoyi"+pay_type);
		RoleEntity  roleInfo = this.getRoleEntity(request);
		
		//如果是新手
		if( roleInfo.getIsRookie())
		{
			this.setHint(request, "你现在处在新手引导状态,无法拍卖");
			return mapping.findForward("return_hint");
		}
		
		int pPk = roleInfo.getBasicInfo().getPPk();
		EconomyService es=new EconomyService();
		long money= es.getYuanbao(roleInfo.getUPk());
		long copper=roleInfo.getBasicInfo().getCopper();
		
		//装备类型，1是药品，2是书，3是装备，4是任务，5是其他
		String w_type = request.getParameter("w_type");
		request.setAttribute("w_type",w_type);

		String resultWml = null;
		
		GoodsService goodsSerivce = new GoodsService();
		AuctionService auctionService = new AuctionService();
		
		//查看是否为不可拍卖物品，如果为不可拍卖物品，通知玩家不可.
		
		String pwPk = request.getParameter("pwPk");
		String pg_pk = request.getParameter("pg_pk");
		
		// 检测一口价输入时候正确
		String prop_silver = request.getParameter("prop_silver");
		if(prop_silver == null || prop_silver.equals("")){
			prop_silver = "0";
		} else {
			boolean flag = StringUtil.isNumber(prop_silver);
			if (!flag) {
				resultWml = "请正确输入一口价价格!";
				request.setAttribute("w_type", w_type);
				request.setAttribute("resultWml", resultWml);
				if (Integer.parseInt(w_type) == Wrap.EQUIP) {
    				request.setAttribute("pwPk", pwPk);
    				return mapping.findForward("reput_zb_price");	
				} else {
					request.setAttribute("pg_pk", pg_pk);
    				return mapping.findForward("reput_prop_numPrice");	
				}
			}
		}
		// 检测竞拍价输入是否正确
		String prop_copper = request.getParameter("prop_copper");
		if(prop_copper == null || prop_copper.equals("")){
			prop_copper = "0";
		} else {
			boolean flag = StringUtil.isNumber(prop_copper);
			if (!flag) {
				resultWml = "请正确输入竞拍价格!";
				request.setAttribute("w_type", w_type);
				request.setAttribute("resultWml", resultWml);
				if (Integer.parseInt(w_type) == Wrap.EQUIP) {
    				request.setAttribute("pwPk", pwPk);
    				return mapping.findForward("reput_zb_price");	
				} else {
					request.setAttribute("pg_pk", pg_pk);
    				return mapping.findForward("reput_prop_price");	
				}
			}
		}
		
		int propPrice = Integer.parseInt(prop_silver);
		int propAuctionPrice=Integer.parseInt(prop_copper);
		if(propPrice<=propAuctionPrice)
		{
			resultWml = "不符合拍卖规则,一口价必须高于竞拍价!";
			request.setAttribute("w_type", w_type);
			request.setAttribute("resultWml", resultWml);
			if (Integer.parseInt(w_type) == Wrap.EQUIP) {
				request.setAttribute("pwPk", pwPk);
				return mapping.findForward("reput_zb_price");	
			} else {
				request.setAttribute("pg_pk", pg_pk);
				return mapping.findForward("reput_prop_price");	
			}
		}
		/******仙晶拍卖进入拍卖场需交100仙晶*******/
		if(pay_type=="2")
		{
			if(money<100)
			{
				resultWml = "您身上的仙晶不够此次的税赋!";
				if(Integer.parseInt(w_type) == Wrap.EQUIP)
				{
					request.setAttribute("w_type", w_type);
					request.setAttribute("pwPk", pwPk);
					request.setAttribute("resultWml", resultWml);
					request.setAttribute("table_type", backType);
					request.setAttribute("pageNo", pageNum);
					return mapping.findForward("reput_zb_price");
				}
				else
				{
					request.setAttribute("w_type", w_type);
					request.setAttribute("pg_pk", pg_pk);
					request.setAttribute("resultWml", resultWml);
					return mapping.findForward("reput_prop_price");
				}
				
			}
		}
		// 拍卖装备
		if (Integer.parseInt(w_type) == Wrap.EQUIP)
		{
			try{	
				int auctionPrice=Integer.parseInt(prop_copper);
				if ( propPrice <= 0||auctionPrice<=0) {		
					// 数量的格式不正确	
					resultWml = "物品价格或竞拍价格不得为零!";
					request.setAttribute("w_type", w_type);
					request.setAttribute("pwPk", pwPk);
					request.setAttribute("resultWml", resultWml);
					request.setAttribute("table_type", backType);
					request.setAttribute("pageNo", pageNum);
					return mapping.findForward("reput_zb_price");					
				}
				logger.info("action 中的w_type : "+w_type+"copper : "+propPrice);
				resultWml = auctionService.removeAccouters(roleInfo.getBasicInfo().getUPk(),pPk,
						Integer.parseInt(pwPk),propPrice,Integer.parseInt(pay_type),Integer.parseInt(prop_copper));
			}catch (NumberFormatException e){
					// 数量的格式不正确
					resultWml = "正确输入物品拍卖价格";
					request.setAttribute("w_type", w_type);
					request.setAttribute("pwPk", pwPk);
					request.setAttribute("resultWml", resultWml);
					request.setAttribute("table_type", backType);
					request.setAttribute("pageNo", pageNum);
					return mapping.findForward("reput_zb_price");
			}
		} else
		{
			//是道具的情况
			
			
			/* 检查 */
			PlayerPropGroupVO goodsGroup = goodsSerivce.getGoodsGroupByPgPk(Integer.parseInt(pg_pk));
			
			String prop_num_str = request.getParameter("prop_num");
			logger.info("AuctionAction 中的 pg_pk 值为: "+pg_pk+", prop_num_str的值为　:　"+prop_num_str);
			int prop_num;
			try {
					prop_num = Integer.parseInt(prop_num_str);
					int auctionPrice=Integer.parseInt(prop_copper);
					if ( propPrice <= 0 || auctionPrice<=0) {		
						// 数量的格式不正确	
						resultWml = "物品价格或竞拍价格不得为零!";
						request.setAttribute("w_type", w_type);
						request.setAttribute("pg_pk", pg_pk);
						request.setAttribute("resultWml", resultWml);
						return mapping.findForward("reput_prop_price");					
					}
					logger.info("action 中的w_type : "+w_type+"copper : "+propPrice);
					
					if(prop_num <= 0) {
						// 数量为零
						resultWml = "对不起，拍卖物品至少要有一个！";
						request.setAttribute("w_type", w_type);
						request.setAttribute("pg_pk", pg_pk);
						request.setAttribute("resultWml", resultWml);
						
						return mapping.findForward("reput_prop_price");
					}
					if(prop_num < 0) {
						// 数量为零
						resultWml = "对不起，请输入正确数字！";
						request.setAttribute("w_type", w_type);
						request.setAttribute("pg_pk", pg_pk);
						request.setAttribute("resultWml", resultWml);
						return mapping.findForward("reput_prop_price");
					}
					if (goodsGroup.getPropNum() >= prop_num) {
							resultWml = auctionService.addPropToAuction(Integer.parseInt(pg_pk),prop_num,roleInfo,propPrice,Integer.parseInt(pay_type),Integer.parseInt(prop_copper));
					} else
					{
						// 数量不够
						resultWml = "对不起，该物品数量不够！";
						request.setAttribute("w_type", w_type);
						request.setAttribute("pg_pk", pg_pk);
						request.setAttribute("resultWml", resultWml);
						
						return mapping.findForward("reput_prop_price");
					}
				} catch (NumberFormatException e)
				{
					// 数量的格式不正确
					resultWml = "正确输入物品数量和拍卖价格！";
					request.setAttribute("w_type", w_type);
					request.setAttribute("pg_pk", pg_pk);
					request.setAttribute("resultWml", resultWml);
					
					return mapping.findForward("reput_prop_price");
				}
			}
		
		request.setAttribute("resultWml", resultWml);
		return mapping.findForward("auction_hint");
	}
	
	// 拍卖时包裹道具物品详情
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		RoleEntity  roleInfo = this.getRoleEntity(request);
		
		String w_type = request.getParameter("w_type");
		String pg_pk = request.getParameter("pg_pk");
		String goods_id = request.getParameter("goods_id");


		String prop_display = null;
		GoodsService goodsService = new GoodsService();
		
		prop_display = goodsService.getPropInfoWml(roleInfo.getPPk(),Integer.parseInt(goods_id),pg_pk);
		
		request.setAttribute("w_type", w_type);
		request.setAttribute("pg_pk", pg_pk);
		request.setAttribute("goods_id", goods_id);
		request.setAttribute("resultWml", prop_display);
		return mapping.findForward("prop_display");
	}
	
	/**
	 * 拍卖时包裹装备物品详情
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		GoodsService goodsService = new GoodsService();
		EquipDisplayService equipDisplayService = new EquipDisplayService();

		RoleEntity roleInfo = this.getRoleEntity(request);

		String pwPk = request.getParameter("pwPk");
		
		PlayerEquipVO equip = (PlayerEquipVO) goodsService.getEquipByID(Integer.parseInt(pwPk));
		
		String equip_display = equipDisplayService.getEquipDisplay(roleInfo,equip, true);

		request.setAttribute("equip_display", equip_display);
		request.setAttribute("equip", equip);
		request.setAttribute("pwPk", pwPk);
		request.setAttribute("table_type",request.getParameter("table_type"));
		
		return mapping.findForward("equip_display");
	}
}
