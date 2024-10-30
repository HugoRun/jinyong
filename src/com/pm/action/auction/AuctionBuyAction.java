package com.pm.action.auction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipDisplayService;
import com.ls.web.service.player.RoleService;
import com.pm.service.auction.AuctionService;
import com.pm.vo.constant.AuctionType;

public class AuctionBuyAction extends DispatchAction
{

	Logger logger = Logger.getLogger("log.action");

	// 拍卖场页面
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());

		request.setAttribute("roleInfo", roleInfo);
		return mapping.findForward("auctionDisPlay");
	}

	// 拍卖场物品列表
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		/*****1灵石拍卖2仙晶拍卖********************/
		String pay_type=(String)request.getSession().getAttribute("pay_type");
		/*****************************************/
		String auctionType = request.getParameter("auctionType");
		String page_no_str = request.getParameter("page_no");
		String sortType = request.getParameter("sortType");
		logger.info("排序分类 : " + sortType + ",page_no : " + page_no_str
				+ ",auctionType : " + auctionType);
		int auctionTypes = 0;
		int page_no;

		if (auctionType == null)
		{
			String temp=(String)request.getSession().getAttribute("auctionTypes");
			auctionTypes=temp!=null?Integer.parseInt(temp):AuctionType.ARM;
		}
		else
		{
			auctionTypes = Integer.parseInt(auctionType);
		}
		request.getSession().setAttribute("auctionTypes", auctionTypes+"");
		if (page_no_str == null)
		{
			page_no = 1;
		}
		else
		{
			page_no = Integer.parseInt(page_no_str);
		}

		request.setAttribute("roleInfo", roleInfo);
		int pPk = roleInfo.getBasicInfo().getPPk();

		request.setAttribute("pPk", pPk);
		request.setAttribute("id", pPk + "");
		request.setAttribute("sortType", sortType);

		
		AuctionService auctionSerivce = new AuctionService();

		/*武器*/
		if (auctionTypes == AuctionType.ARM)
		{

			QueryPage cure_page = auctionSerivce.getPagePropList(pPk,
					AuctionType.ARM, page_no, sortType,Integer.parseInt(pay_type));
			request.setAttribute("type", AuctionType.ARM+"");
			request.setAttribute("arm_page", cure_page);
			request.setAttribute("mtype", "n2");
			return mapping.findForward("auctionDisPlay");
		}
		/*防具*/
		else
			if (auctionTypes == AuctionType.ACCOUTE)
			{
				QueryPage book_page = auctionSerivce.getPagePropList(pPk,
						AuctionType.ACCOUTE, page_no, sortType,Integer.parseInt(pay_type));
				request.setAttribute("type", AuctionType.ACCOUTE+"");
				request.setAttribute("arm_page", book_page);
				request.setAttribute("mtype", "n2");
				return mapping.findForward("auctionDisPlay");
			}
		/*首饰*/
			else
				if (auctionTypes == AuctionType.JEWELRY)
				{
					QueryPage material_page = auctionSerivce.getPagePropList(
							pPk, AuctionType.JEWELRY, page_no, sortType,Integer.parseInt(pay_type));
					request.setAttribute("type", AuctionType.JEWELRY+"");
					request.setAttribute("arm_page", material_page);
					request.setAttribute("mtype", "n2");
					return mapping.findForward("auctionDisPlay");
				}
		/*道具*/
				else
					if (auctionTypes == AuctionType.SHOP)
					{
						QueryPage shop_page = auctionSerivce.getPagePropList(
								pPk, AuctionType.SHOP, page_no, sortType,Integer.parseInt(pay_type));
						request.setAttribute("type", AuctionType.SHOP+"");
						request.setAttribute("arm_page", shop_page);
						request.setAttribute("mtype", "n2");
						return mapping.findForward("auctionDisPlay");
					}

		return mapping.findForward("auctionDisPlay");
	}

	// 特定物品列表
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		/*****1灵石拍卖2仙晶拍卖********************/
		String pay_type=(String)request.getSession().getAttribute("pay_type");
		/*****************************************/
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		int pPk = roleInfo.getBasicInfo().getPPk();

		String auctionType = request.getParameter("auctionType");
		String page_no_str = request.getParameter("page_no");
		String sortType = request.getParameter("sortType");
		logger.info("排序分类 : " + sortType);

		String propName = request.getParameter("prop_name");

		int page_no;

		if (page_no_str == null)
		{
			page_no = 1;
		}
		else
		{
			page_no = Integer.parseInt(page_no_str);
		}
		if (propName == null)
		{
			logger.info("auctionBuyAction中propName : " + propName);
			String resultWml = "请您输入正确的物品名";
			request.setAttribute("resultWml", resultWml);

		}
		else
		{

			request.setAttribute("pPk", pPk + "");
			request.setAttribute("id", roleInfo.getBasicInfo().getUPk() + "");
			request.setAttribute("sortType", sortType);
			request.setAttribute("propName", propName);

			AuctionService auctionSerivce = new AuctionService();

			QueryPage name_page = auctionSerivce.getPagePropByName(Integer
					.valueOf(pPk), propName, page_no, sortType,Integer.parseInt(pay_type),Integer.parseInt(auctionType));
			request.setAttribute("type", auctionType);
			request.setAttribute("arm_page", name_page);
			request.setAttribute("mtype", "n3");
			return mapping.findForward("auctionDisPlay");

		}

		return mapping.findForward("auctionDisPlay");

	}

	/**
	 * 查看物品详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());

		int auctionTypes = Integer.parseInt(request.getParameter("w_type"));
		String prop_id = request.getParameter("prop_id");

		String sortType = request.getParameter("sortType");
		String prop_name = request.getParameter("prop_name");
		String page_no = request.getParameter("page_no");
		if (page_no == null)
		{
			page_no = "1";
		}
		if (auctionTypes == AuctionType.ACCOUTE
				|| auctionTypes == AuctionType.ARM
				|| auctionTypes == AuctionType.JEWELRY)
		{
			GoodsService goodsService = new GoodsService();
			EquipDisplayService equipDisplayService = new EquipDisplayService();

			PlayerEquipVO equip = (PlayerEquipVO) goodsService.getEquipByID(Integer.parseInt(prop_id));
			
			String equip_display = equipDisplayService.getEquipDisplay(roleInfo,equip, true);

			request.setAttribute("display", equip_display);
			request.setAttribute("page_no", page_no);
			request.setAttribute("equip", equip);
			request.setAttribute("sortType", sortType);
			request.setAttribute("propName", prop_name);
			return mapping.findForward("goodsview");
		}
		else
		{
			// 查看道具
			GoodsService goodsService = new GoodsService();
			String prop_display = goodsService.getPropInfoWml(roleInfo
					.getBasicInfo().getPPk(), Integer.parseInt(prop_id));
			request.setAttribute("display", prop_display);
			return mapping.findForward("goodsview");
		}
	}

}