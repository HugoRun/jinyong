package com.ls.web.action.menu.buy;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.ben.dao.info.npc.NpcShopDao;
import com.ls.ben.vo.info.npc.NpcShopVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.GoodsType;
import com.ls.web.action.ActionBase;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipDisplayService;
import com.pub.ben.info.Expression;

/**
 * 在NPC处购买
 */
public class BuyAction extends ActionBase
{
	Logger logger = Logger.getLogger("log.action");

	// 显示所买的物品
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String menu_id = request.getParameter("menu_id");
		NpcShopDao npcShopDao = new NpcShopDao();
		List<NpcShopVO> npcshops = npcShopDao.getListByMenuId(Integer.parseInt(menu_id));
		request.setAttribute("npcshops", npcshops);
		request.setAttribute("menu_id", menu_id);
		return mapping.findForward("buy_list");
	}

	// 显示物品详情
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity  roleInfo = this.getRoleEntity(request);
		
		String npcshop_id = request.getParameter("npcshop_id");
		request.setAttribute("pageNo", request.getParameter("pageNo"));

		String goods_display = null;

		NpcShopDao npcShopDao = new NpcShopDao();

		NpcShopVO npcShop = npcShopDao.getNpcShopById(Integer.parseInt(npcshop_id));

		GoodsService goodsService = new GoodsService();

		if (npcShop.getGoodsType() == GoodsType.PROP)
		{
			goods_display = goodsService.getPropInfoWmlMai(roleInfo, npcShop);
		}
		else
		{
			EquipDisplayService equipDisplayService = new EquipDisplayService();
			goods_display = equipDisplayService.getBuyEquipDisplay(roleInfo,npcShop);
		}

		request.setAttribute("menu_id", npcShop.getNpcId() + "");
		request.setAttribute("npcshop_id", npcshop_id);
		request.setAttribute("goods_display", goods_display);
		return mapping.findForward("goods_display");
	}

	// 购买
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String menu_id = request.getParameter("menu_id");
		String npcshop_id = request.getParameter("npcshop_id");
		String goods_num_str = request.getParameter("goods_num");

		request.setAttribute("pageNo", request.getParameter("pageNo"));

		String resultWml = null;
		if (goods_num_str == null || goods_num_str.equals("") || goods_num_str.equals("null"))
		{
			goods_num_str = "1";
		}
		Pattern p = Pattern.compile(Expression.positive_integer_contain0_regexp);
		Matcher m = p.matcher(goods_num_str);
		boolean b = m.matches();
		if (b == false)
		{
			resultWml = "购买数量格式不正确,请输入数字<br/>";
		}
		if(goods_num_str.length() > 5){
		    resultWml = "购买数量格式不正确,请输入数字<br/>";
		}
		if (resultWml == null)// 玩家的购买数量格式正确
		{
			RoleEntity  roleInfo = this.getRoleEntity(request);
			
			GoodsService goodsService = new GoodsService(); 
			resultWml = goodsService.buyGoods(roleInfo, Integer.parseInt(npcshop_id), Integer.parseInt(goods_num_str));
		} 
		
		NpcShopDao npcShopDao = new NpcShopDao();
		List<NpcShopVO> npcshops = npcShopDao.getListByMenuId(Integer.parseInt(menu_id)); 
		request.setAttribute("resultWml", resultWml);
		request.setAttribute("menu_id", menu_id);
		request.setAttribute("npcshops", npcshops);
		return mapping.findForward("buy_list");
	}
}