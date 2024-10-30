/**
 * 
 */
package com.ls.web.action.mall;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.mall.CommodityVO;
import com.ls.model.user.RoleEntity;
import com.ls.model.vip.Vip;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.mall.MallService;
import com.ls.web.service.player.RoleService;

/**
 * @author Administrator
 *
 */
public class AttackMallAction extends DispatchAction {

	Logger logger = Logger.getLogger("log.action");


	// 查看还魂丹
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			
		RoleService roleService = new RoleService();
		RoleEntity role_info = roleService.getRoleInfoBySession(request.getSession());
		
		int p_pk = role_info.getBasicInfo().getPPk();
		
		String c_id = (String)request.getAttribute("c_id");//商品id
		if (c_id == null || c_id.equals("")) {
			c_id = "20";
		}
		
		MallService mallService = new MallService();
		GoodsService goodsService = new GoodsService();
		
		PropVO prop = mallService.getPropInfo(c_id);
		
		String prop_wml = goodsService.getPropInfoWml(p_pk, prop.getPropID());
		
		request.setAttribute("prop_wml", prop_wml);
		request.setAttribute("c_id", c_id);
		return mapping.findForward("prop_display");
			
	}
	
	
	// 购买还魂丹
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleService roleService = new RoleService();
		RoleEntity role_info = roleService.getRoleInfoBySession(request.getSession());		
				
		MallService mallService = new MallService();
		String c_id = "20";
		String sell_num = "1";
		
		CommodityVO commodity = mallService.getCommodityInfo(c_id);
		
		String error_hint = null;
		if(GameConfig.getChannelId()==Channel.TELE)
		{
			error_hint=mallService.buyForTelecom(request, role_info, commodity, sell_num, 2, c_id);
		}
		else
		{
			error_hint  = mallService.buy(role_info, commodity, sell_num.trim(),2);//购买
		}
		if( error_hint!=null )
		{
			request.setAttribute("hint", error_hint);
			request.setAttribute("commodity", commodity);
			request.getSession().setAttribute("return_type", "1");
			try
			{
				request.getRequestDispatcher("/sky/bill.do?cmd=n0").forward(request, response);
			}
			catch (Exception e)
			{
				System.out.println("往充值界面跳转出错!");
			}
			return null;
		}
		
		
		int user_discount = 100;//vip折扣		
		Vip role_vip = role_info.getTitleSet().getVIP();
		if( role_vip!=null )
		{
			user_discount = role_vip.getDiscount();
		}
		int need_num = commodity.getCurPrice(user_discount)*Integer.parseInt(sell_num);//需要消耗的数量
		
		
		String buy_hint = null;
		buy_hint = "提示:您购买了"+commodity.getPropName()+"×"+sell_num+",消费【"+GameConfig.getYuanbaoName()+"】×"+need_num+"!";
		request.setAttribute("commodity", commodity);
		
		request.setAttribute("hint", buy_hint);
		request.getSession().setAttribute("sussend","sussend");
		
		return mapping.findForward("buy_hint");
		
	}
	
	
	//   在商城买完还魂丹后的处理
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleService roleService = new RoleService();
		RoleEntity role_info = roleService.getRoleInfoBySession(request.getSession());		
				
		MallService mallService = new MallService();
		String c_id = "27";
		String sell_num = "1";
		
		CommodityVO commodity = mallService.getCommodityInfo(c_id);
		
		String error_hint = null;
		
//		if( error_hint!=null )
//		{
//			request.setAttribute("hint", error_hint);
//			request.setAttribute("commodity", commodity);
//			return mapping.findForward("buy_hint");
//		}
		
		error_hint  = mallService.buy(role_info, commodity, sell_num.trim(),2);//购买
		
		
		if( error_hint!=null )
		{
			request.setAttribute("hint", error_hint);
			request.setAttribute("commodity", commodity);
			return mapping.findForward("buy_hint");
		}
		
		
		int user_discount = 100;//vip折扣		
		Vip role_vip = role_info.getTitleSet().getVIP();
		if( role_vip!=null )
		{
			user_discount = role_vip.getDiscount();
		}
		int need_num = commodity.getCurPrice(user_discount)*Integer.parseInt(sell_num);//需要消耗的数量
		
		
		String buy_hint = null;
		buy_hint = "提示:您购买了"+commodity.getPropName()+"×"+sell_num+",消费【"+GameConfig.getYuanbaoName()+"】×"+need_num+"!";
		request.setAttribute("commodity", commodity);
		
		request.setAttribute("hint", buy_hint);
		request.getSession().setAttribute("sussend","sussend");
		request.getSession().removeAttribute("return_type");
		
		return mapping.findForward("buy_hint");
		
		
		
		
		
	}
}
