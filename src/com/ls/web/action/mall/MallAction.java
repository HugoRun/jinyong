package com.ls.web.action.mall;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.mall.CommodityVO;
import com.ls.model.user.RoleEntity;
import com.ls.model.vip.Vip;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.pub.constant.player.PlayerState;
import com.ls.web.action.ActionBase;
import com.ls.web.action.cooperate.youle.login.LoginService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.mall.MallService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.RoleService;
import com.lw.service.player.PlayerPropGroupService;

/**
 * @author ls
 * 商城
 */
public class MallAction extends ActionBase {
	Logger logger = Logger.getLogger("log.consume");
	/** 
	 * 商城首页面
	 */
	public ActionForward n0(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleEntity roleInfo = this.getRoleEntity(request);
		
		//如果是新手
		if( roleInfo.getBasicInfo().getPlayer_state_by_new()==1)
		{
			this.setHint(request, "你现在处在新手引导状态,无权访问商城");
			return mapping.findForward("return_hint");
		}
		
		int u_pk = roleInfo.getBasicInfo().getUPk();
		
		roleInfo.getStateInfo().setCurState(PlayerState.MALL);//玩家在商城时状态受保护
		
		MallService mallService = new MallService();
		EconomyService economyService = new EconomyService();
		
		long yuanbao = economyService.getYuanbao(u_pk);
		int jifen = economyService.getJifen(u_pk);
		
		List<CommodityVO> newsell_commoditys = mallService.getNewSellCommodityListOfMainPage();
		List<CommodityVO> hotsell_commoditys = mallService.getHotSellCommodityListOfMainPage();
		List<CommodityVO> discount_commoditys = mallService.getDiscountCommodityListOfMainPage();
		
		request.setAttribute("roleInfo",roleInfo);
		request.setAttribute("yuanbao",yuanbao+"");
		request.setAttribute("jifen",jifen+"");
		request.setAttribute("newsell_commoditys",newsell_commoditys);
		request.setAttribute("hotsell_commoditys",hotsell_commoditys);
		request.setAttribute("discount_commoditys",discount_commoditys);
		
		return mapping.findForward("mall_index");
	}
	
	/** 
	 * VIP首页面
	 */
	public ActionForward vip(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		int u_pk = roleInfo.getBasicInfo().getUPk();
		
		EconomyService economyService = new EconomyService();
		MallService mallService = new MallService();
		
		//判断是否已是会员
		Vip vip = roleInfo.getTitleSet().getVIP();
		if( vip!=null )
		{
			return vipmall(mapping, form, request, response);
		}
		int jifen = economyService.getJifen(u_pk);
		
		String vip_name = "普通玩家";//会员名称
		String role_title = "亲爱的玩家";//会员商城的玩家的称呼
		
		
		Vip role_vip = roleInfo.getTitleSet().getVIP();
		if( role_vip!=null )
		{
			vip_name = role_vip.getName();
			role_title = mallService.getRoleTitleByVIPLevel(role_vip.getLevel());
		}
		
		request.setAttribute("vip_name",vip_name);
		request.setAttribute("role_title",role_title);
		request.setAttribute("jifen",jifen+"");
		
		return mapping.findForward("vip_index");
	}
	
	/** 
	 * VIP介绍页面
	 */
	public ActionForward vipIntro(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String cmd =request.getParameter("type");
		if( cmd==null )
		{
			cmd = "n0";
		}
		
		request.setAttribute("return_url","/mall.do?cmd=n0");
		return mapping.findForward("vip_intro");
	}
	
	
	/** 
	 * 积分商城首页面
	 */
	public ActionForward jifen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		int u_pk = roleInfo.getBasicInfo().getUPk();
		
		EconomyService economyService = new EconomyService();
		MallService mallService = new MallService();
		
		String role_title = "";//会员商城的玩家的称呼
		int jifen = economyService.getJifen(u_pk);
		
		request.setAttribute("jifen",jifen+"");
		
		Vip role_vip = roleInfo.getTitleSet().getVIP();
		if( role_vip!=null )
		{
			String vip_discount = role_vip.getDiscount()+"";
			role_title = mallService.getRoleTitleByVIPLevel(role_vip.getLevel());
			request.setAttribute("vip_discount", vip_discount);
			request.setAttribute("role_title",role_title);
			return n1(mapping, form, request, response);
		}
		return mapping.findForward("jifen_index_for_normal");
	}
	
	/** 
	 * 积分商城介绍页面
	 */
	public ActionForward jifenIntro(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		return mapping.findForward("jifen_intro");
	}
	
	/** 
	 * 积分商品展示
	 */
	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RoleService roleService = new RoleService();
		RoleEntity role_info = roleService.getRoleInfoBySession(request.getSession());
		
		int p_pk = role_info.getBasicInfo().getPPk();
		
		String c_id = request.getParameter("c_id");
		
		MallService mallService = new MallService();
		GoodsService goodsService = new GoodsService();
		
		CommodityVO commodity_info = mallService.getCommodityInfo(c_id);
		
		String prop_wml = goodsService.getPropInfoWml(p_pk, commodity_info.getPropId());
		int original_price = commodity_info.getOriginalPrice();
		int discount = commodity_info.getDiscount();
		int discount_price = original_price * discount/100;
		
		request.setAttribute("prop_wml",prop_wml);
		request.setAttribute("original_price",original_price+"");
		request.setAttribute("discount_price",discount_price+"");
		request.setAttribute("discount",discount+"");
		
		return mapping.findForward("commodity_show");
	}
	
	
	/**
	 * 商品列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		MallService mallService = new MallService();
		EconomyService economyService = new EconomyService();
		int u_pk = roleInfo.getBasicInfo().getUPk();
		long yuanbao = economyService.getYuanbao(u_pk);
		int jifen = economyService.getJifen(u_pk);
		
		String type = request.getParameter("type");//查看物品详情后，要返回的页面的类型，对应商品的分类
		String page_no_str = request.getParameter("page_no");
		int page_no = 1;
		
		if( page_no_str!=null && !page_no_str.equals("") )
		{
			page_no = Integer.parseInt(page_no_str);
		}
		
		String shop_title = "";//商城标题
		String sell_type = GameConfig.getYuanbaoName();//消费类型
		QueryPage queryPage = null;
		
		shop_title = mallService.getShopTitleByType(type);
		queryPage = mallService.getCommodityListByType(type,page_no);
		request.setAttribute("roleInfo",roleInfo);
		request.setAttribute("yuanbao",yuanbao+"");
		request.setAttribute("jifen",jifen+"");
		request.setAttribute("type", type);
		request.setAttribute("shop_title", shop_title);
		request.setAttribute("sell_type", sell_type);
		request.setAttribute("queryPage", queryPage);
		return mapping.findForward("commodity_list");
	}
	
	
	
	
	/**
	 * 购买商品提示
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String c_id = request.getParameter("c_id");//商品id
		String type = request.getParameter("type");//查看物品详情后，要返回的页面的类型
		String page_no = request.getParameter("page_no");
		
		RoleService roleService = new RoleService();
		RoleEntity role_info = roleService.getRoleInfoBySession(request.getSession());
		
		int p_pk = role_info.getBasicInfo().getPPk();
		
		MallService mallService = new MallService();
		GoodsService goodsService = new GoodsService();
		
		CommodityVO commodity = mallService.getCommodityInfo(c_id);
	
		String prop_wml = goodsService.getPropInfoWml(p_pk, commodity.getPropId());
		
		if(type==null)
		{
			type = commodity.getType()+"";
		}
		if( page_no==null )
		{
			page_no = "";
		}
		
		String sell_type = GameConfig.getYuanbaoName();//消费类型
		
		if( commodity.getBuyMode() == 2 )//积分商品
		{
			sell_type = "积分";
		}
		
		int user_discount = 100;
		
		Vip role_vip = role_info.getTitleSet().getVIP();
		if( role_vip!=null )
		{
			user_discount = role_vip.getDiscount();
		}
		
		
		request.setAttribute("user_discount", user_discount+"");
		request.setAttribute("commodity", commodity);
		request.setAttribute("prop_wml", prop_wml);
		request.setAttribute("c_id", c_id);
		request.setAttribute("type", type);
		request.setAttribute("sell_type", sell_type);
		request.setAttribute("page_no", page_no);
		
		return mapping.findForward("buy_hint");
	}
	/**
	 * 查询剩余点数action  电信
	 */
	
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String hint="您不可以使用此功能";
		if(GameConfig.getChannelId()!=Channel.TELE)
		{
			request.setAttribute("hint",hint);
			return mapping.findForward("serchpoint");
		}
		MallService ms =new MallService();
		String url="http://202.102.39.11:9088/gameinterface/QueryAaccountBalance";
		String netElementId="888999";
		String custIdType="3";
		String custLabel=(String)request.getSession().getAttribute("teleid");
		String channelId=(String)request.getSession().getAttribute("channel_id");
		String cpId=(String)request.getSession().getAttribute("cpId");
		String transID=cpId+MallService.getDateStr()+"827315";
		String versionId="1_1_2";
		Map<String, String> params=new HashMap<String, String>();
		params.put("msgType", "QueryAaccountBalanceReq");
		params.put("netElementId",netElementId);
		params.put("custIdType",custIdType);
		params.put("custLabel", custLabel);
		params.put("channelId", channelId);
		params.put("transID", transID);
		params.put("versionId", versionId);
		String str= ms.serchPoint(url, params);
		if("1".equals(str))
		{
			hint="查询剩余点数失败，请重新操作";
			request.setAttribute("hint",hint);
		}
		else
		{
			hint="亲爱的"+custLabel+",您的剩余点数为："+str;
			request.setAttribute("hint",str);
		}
		return mapping.findForward("serchpoint");
	}
	/**
	 * 购买商品
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		RoleService roleService = new RoleService();
		RoleEntity role_info = roleService.getRoleInfoBySession(request.getSession());
		
		int p_pk = role_info.getBasicInfo().getPPk();
		
		String c_id = request.getParameter("c_id");//商品id
		String type = request.getParameter("type");//查看物品详情后，要返回的页面的类型
		String page_no = request.getParameter("page_no");
		String sell_num = request.getParameter("sell_num");//购买的数量
		
		MallService mallService = new MallService();
		
		CommodityVO commodity = mallService.getCommodityInfo(c_id);
		if(type==null)
		{
			type = "";
		}
		if( page_no==null )
		{
			page_no = "";
		}
		String sell_type = GameConfig.getYuanbaoName();//消费类型
		if(Integer.parseInt(type)==5)
		{
			sell_type="积分";
		}
		GoodsService goodsService = new GoodsService();
		if(sell_num==null||"".equals(sell_num.trim())){
			sell_num = "1";
		}
		if(Integer.parseInt(type)== 6){
			//判断是否已是会员
			Vip vip = role_info.getTitleSet().getVIP();
			if(vip == null){
				String prop_wml = goodsService.getPropInfoWml(p_pk, commodity.getPropId());
				request.setAttribute("hint", "您还不是会员,请成为会员后再够买此物品");
				request.setAttribute("c_id", c_id);
				request.setAttribute("type", type);
				request.setAttribute("sell_type", sell_type);
				request.setAttribute("page_no", page_no);
				request.setAttribute("commodity", commodity);
				request.setAttribute("prop_wml", prop_wml);
				return mapping.findForward("buy_hint");
			}
		}
		
		if( !commodity.isEnough(Integer.parseInt(sell_num.trim())) )
		{
			
			String prop_wml = goodsService.getPropInfoWml(p_pk, commodity.getPropId());
			
			int store_num = commodity.getStoreNum();
			
			request.setAttribute("hint", "库存不足,剩余数量为"+store_num);
			request.setAttribute("c_id", c_id);
			request.setAttribute("type", type);
			request.setAttribute("sell_type", sell_type);
			request.setAttribute("page_no", page_no);
			request.setAttribute("commodity", commodity);
			request.setAttribute("prop_wml", prop_wml);
			return mapping.findForward("buy_hint");
		}
		
		String hint=null;
		/*****电信购买道具*******/
		if(Channel.TELE==GameConfig.getChannelId())
		{
			hint=mallService.buyForTelecom(request,role_info, commodity, sell_num.trim(), 1,c_id);
		}
		/*****其他渠道购买道具*****/
		else
		{
			hint = mallService.buy(role_info, commodity, sell_num.trim(),1);
		}
		
		int user_discount = 100;
		
		Vip role_vip = role_info.getTitleSet().getVIP();
		if( role_vip!=null )
		{
			user_discount = role_vip.getDiscount();
		}
		
		
		if( hint!=null )
		{
			request.setAttribute("c_id", c_id);
			request.setAttribute("type", type);
			request.setAttribute("sell_type", sell_type);
			request.setAttribute("page_no", page_no);
			if( hint.equals("包裹空间不足") && role_info.getBasicInfo().getWrapContent()<100 )
			{
				return mapping.findForward("buy_wrap_space");
			}
			else
			{
				String prop_wml = goodsService.getPropInfoWml(p_pk, commodity.getPropId());
				
				request.setAttribute("user_discount", user_discount+"");
				request.setAttribute("hint", hint);
				request.setAttribute("commodity", commodity);
				request.setAttribute("prop_wml", prop_wml);
				return mapping.findForward("buy_hint");
			}
		}
		
		int need_num = Integer.parseInt(sell_num.trim())*commodity.getCurPrice(user_discount);
		
		hint = "提示:您购买了"+commodity.getPropName()+"×"+sell_num.trim()+",消费【"+sell_type+"】×"+need_num+"!";
		
		String wm = (String) session.getAttribute("wm");
		if(wm == null || wm.equals("null")){
			wm = "-";
		}
		String todayStr = null;
		Date date = new Date();;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			todayStr = df.format(date.getTime());
		} catch (Exception ex) {
			logger.debug(ex.getMessage());
		}
		float money = (float)need_num / (float)100;
		logger.info(todayStr+"|"+p_pk+"|"+role_info.getBasicInfo().getName()+"|"+wm+"|"+commodity.getPropId()+"|"+commodity.getPropName()+"|"+money);
		/*******悠乐渠道需要同步玩家消费信息******/
		if(GameConfig.getChannelId()==Channel.WANXIANG)
		{
			LoginService ls=new LoginService();
			String ConsumerCode=new Date().getTime()+"C";
			ls.synchronousConsume(request, ConsumerCode, need_num, Integer.parseInt(c_id));
		}
		request.setAttribute("commodity", commodity);
		request.setAttribute("hint", hint);
		request.setAttribute("type", type);
		request.setAttribute("page_no", page_no);
		
		return mapping.findForward("buy");
	}
	
	
	/**
	 * 购买包裹格数
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleService roleService = new RoleService();
		RoleEntity role_info = roleService.getRoleInfoBySession(request.getSession());
		
		String c_id = request.getParameter("c_id");//商品id
		String type = request.getParameter("type");//查看物品详情后，要返回的页面的类型
		String page_no = request.getParameter("page_no");
		String sell_num = request.getParameter("sell_num");//购买的数量
		
		MallService mallService = new MallService();
		CommodityVO commodity = mallService.getCommodityInfo(c_id);
		
		PlayerPropGroupService playerPropGroupService = new PlayerPropGroupService();
		
		//购买包裹格子
		String buy_wrap_content_hint = null;
		if(GameConfig.getChannelId()==Channel.TELE)
		{
			buy_wrap_content_hint=playerPropGroupService.buyPropGroup(request, role_info);
		}
		else
		{
			buy_wrap_content_hint=playerPropGroupService.buyPropGroup(role_info);
		}
		request.setAttribute("c_id", c_id);
		request.setAttribute("type", type);
		request.setAttribute("sell_num", sell_num);
		request.setAttribute("page_no", page_no);
		
		if(buy_wrap_content_hint == null)
		{
			request.setAttribute("hint", "您的"+GameConfig.getYuanbaoName()+"数量不够!");
			return mapping.findForward("buy");
		}
		
		String buy_commodity_hint = mallService.buy(role_info, commodity, sell_num.trim(),1);//购买
		
		if( buy_commodity_hint!=null )//购买失败
		{
			request.setAttribute("hint", buy_wrap_content_hint+buy_commodity_hint);
			return mapping.findForward("buy");
		}
		
		int user_discount = 100;
		
		Vip role_vip = role_info.getTitleSet().getVIP();
		if( role_vip!=null )
		{
			user_discount = role_vip.getDiscount();
		}
		
		int need_num = Integer.parseInt(sell_num.trim())*commodity.getCurPrice(user_discount);
		
		String sell_type = GameConfig.getYuanbaoName();//消费类型
		
		if( commodity.getBuyMode() == 2 )//积分商品
		{
			sell_type = "积分";
		}
		
		buy_commodity_hint = "提示:您购买了"+commodity.getPropName()+"×"+sell_num.trim()+",消费【"+sell_type+"】×"+need_num+"!";
		
	
		request.setAttribute("commodity", commodity);
		request.setAttribute("hint", buy_wrap_content_hint+buy_commodity_hint);
		request.setAttribute("type", type);
		request.setAttribute("page_no", page_no);
		
		return mapping.findForward("buy");
	}
	
	
	/**
	 * 查看详情商品
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleService roleService = new RoleService();
		RoleEntity role_info = roleService.getRoleInfoBySession(request.getSession());
		
		int p_pk = role_info.getBasicInfo().getPPk();
		
		String c_id = request.getParameter("c_id");//商品id
		String type = request.getParameter("type");//查看物品详情后，要返回的页面的类型
		String page_no = request.getParameter("page_no");
		
		MallService mallService = new MallService();
		GoodsService goodsService = new GoodsService();
		
		PropVO prop = mallService.getPropInfo(c_id);
		
		String prop_wml = goodsService.getPropInfoWml(p_pk, prop.getPropID());
		
		if(type==null)
		{
			type = "";
		}
		if( page_no==null )
		{
			page_no = "";
		}
		
		request.setAttribute("prop_wml", prop_wml);
		request.setAttribute("c_id", c_id);
		request.setAttribute("type", type);
		request.setAttribute("page_no", page_no);
		return mapping.findForward("prop_display");
	}
	
	/**
	 * 返回连接处理
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		/**
		 * back_type参数说明，1-6表示元宝商品的分类的列表，7表示积分商品的列表
		 */
		String type = request.getParameter("type");
		
		if( type==null || type.equals(""))
		{
			//返回商城首页
			return n0(mapping, form, request, response);
		}
		else if(type.equals("8"))
		{
			//返回会员主页
			return vip(mapping, form, request, response);
		}
		else if(type.equals("7"))
		{
			//返回积分商城
			return jifen(mapping, form, request, response);
		}
		else
		{
			//元宝商品分类列表
			return n1(mapping, form, request, response);
		}
	}
	
	public ActionForward n10(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		int u_pk = roleInfo.getBasicInfo().getUPk();
		
		roleInfo.getStateInfo().setCurState(PlayerState.MALL);//玩家在商城时状态受保护
		
		List<CommodityVO> hotsell_commoditys = null;
		List<CommodityVO> discount_commoditys = null;
		
		MallService mallService = new MallService();
		EconomyService economyService = new EconomyService();
		
		long yuanbao = economyService.getYuanbao(u_pk);
		int jifen = economyService.getJifen(u_pk);
		
		hotsell_commoditys = mallService.getHotSellCommodityListOfMainPage();
		discount_commoditys = mallService.getDiscountCommodityListOfMainPage();
		
		request.setAttribute("roleInfo",roleInfo);
		request.setAttribute("yuanbao",yuanbao+"");
		request.setAttribute("jifen",jifen+"");
		request.setAttribute("hotsell_commoditys",hotsell_commoditys);
		request.setAttribute("discount_commoditys",discount_commoditys);
		return mapping.findForward("mall_nomral_index");
	}
	
	public ActionForward nomral(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		EconomyService economyService = new EconomyService();
		MallService mallService = new MallService();
		int u_pk = roleInfo.getBasicInfo().getUPk();
		long yuanbao = economyService.getYuanbao(u_pk);
		int jifen = economyService.getJifen(u_pk);
		if(type == null || type.equals("") || type.equals("null")){
			type="1";
		}
		String page_no_str = request.getParameter("page_no");
		int page_no = 1;
		if( page_no_str!=null && !page_no_str.equals("") )
		{
			page_no = Integer.parseInt(page_no_str);
		}
		String shop_title = "";//商城标题
		String sell_type = GameConfig.getYuanbaoName();//消费类型
		QueryPage queryPage = mallService.getJifenCommodityList(1,type, page_no);
		request.setAttribute("roleInfo",roleInfo);
		request.setAttribute("yuanbao",yuanbao+"");
		request.setAttribute("jifen",jifen+"");
		request.setAttribute("type", type);
		request.setAttribute("shop_title", shop_title);
		request.setAttribute("sell_type", sell_type);
		request.setAttribute("queryPage", queryPage);
		request.setAttribute("enterMall","yes");
		return mapping.findForward("nomral_list");
	}
	
	public ActionForward hot(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		MallService mallService = new MallService();
		EconomyService economyService = new EconomyService();
		int u_pk = roleInfo.getBasicInfo().getUPk();
		long yuanbao = economyService.getYuanbao(u_pk);
		int jifen = economyService.getJifen(u_pk);
		String page_no_str = request.getParameter("page_no");
		int page_no = 1;
		if( page_no_str!=null && !page_no_str.equals("") )
		{
			page_no = Integer.parseInt(page_no_str);
		}
		String shop_title = "";//商城标题
		String sell_type = GameConfig.getYuanbaoName();//消费类型
		QueryPage queryPage = mallService.getHotSellCommodityList(page_no);
		request.setAttribute("roleInfo",roleInfo);
		request.setAttribute("yuanbao",yuanbao+"");
		request.setAttribute("jifen",jifen+"");
		request.setAttribute("type", "is_hotmall");
		request.setAttribute("shop_title", shop_title);
		request.setAttribute("sell_type", sell_type);
		request.setAttribute("queryPage", queryPage);
		return mapping.findForward("mall_prop_list");
	}
	
	public ActionForward jifenmall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		MallService mallService = new MallService();
		EconomyService economyService = new EconomyService();
		int u_pk = roleInfo.getBasicInfo().getUPk();
		long yuanbao = economyService.getYuanbao(u_pk);
		int jifen = economyService.getJifen(u_pk);
		if(type == null || type.equals("") || type.equals("null")){
			request.setAttribute("roleInfo",roleInfo);
			request.setAttribute("yuanbao",yuanbao+"");
			request.setAttribute("jifen",jifen+"");
			request.setAttribute("type", type);
			return mapping.findForward("jifen_index"); 
		}
		String page_no_str = request.getParameter("page_no");
		int page_no = 1;
		if( page_no_str!=null && !page_no_str.equals("") )
		{
			page_no = Integer.parseInt(page_no_str);
		}
		String shop_title = "";//商城标题
		String sell_type = "积分";//消费类型
		QueryPage queryPage = mallService.getJifenCommodityList(2,type, page_no);
		request.setAttribute("roleInfo",roleInfo);
		request.setAttribute("yuanbao",yuanbao+"");
		request.setAttribute("jifen",jifen+"");
		request.setAttribute("type", type);
		request.setAttribute("shop_title", shop_title);
		request.setAttribute("sell_type", sell_type);
		request.setAttribute("queryPage", queryPage);
		return mapping.findForward("mall_prop_list_jifen");
	}
	
	public ActionForward vipmall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		MallService mallService = new MallService();
		EconomyService economyService = new EconomyService();
		int u_pk = roleInfo.getBasicInfo().getUPk();
		long yuanbao = economyService.getYuanbao(u_pk);
		int jifen = economyService.getJifen(u_pk);
		String page_no_str = request.getParameter("page_no");
		int page_no = 1;
		if( page_no_str!=null && !page_no_str.equals("") )
		{
			page_no = Integer.parseInt(page_no_str);
		}
		String shop_title = "";//商城标题
		String sell_type = GameConfig.getYuanbaoName();//消费类型
		QueryPage queryPage = mallService.getVIPCommodityList(page_no);
		request.setAttribute("roleInfo",roleInfo);
		request.setAttribute("yuanbao",yuanbao+"");
		request.setAttribute("jifen",jifen+"");
		request.setAttribute("type", "is_vipmall");
		request.setAttribute("shop_title", shop_title);
		request.setAttribute("sell_type", sell_type);
		request.setAttribute("queryPage", queryPage);
		return mapping.findForward("mall_prop_list");
	}
}