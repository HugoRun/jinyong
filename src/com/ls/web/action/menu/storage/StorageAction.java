package com.ls.web.action.menu.storage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.ben.dao.storage.WareHouseDao;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.ben.vo.storage.WareHouseVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.pub.constant.Wrap;
import com.ls.web.action.ActionBase;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipDisplayService;
import com.ls.web.service.log.LogService;
import com.ls.web.service.mall.MallService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.storage.StorageService;

/**
 * 物品仓库操作
 * @author ls
 */
public class StorageAction extends ActionBase{

	// 包裹物品列表
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		StorageService storageService = new StorageService();
		GoodsService goodsSerivce = new GoodsService();
		
		String uw_type_str = request.getParameter("w_type");
		String page_no_str = request.getParameter("page_no");
		
		int w_type=Wrap.BOOK;
		int page_no = 1;
		
		if (uw_type_str!=null)
		{
			w_type = Integer.parseInt(uw_type_str);
		}
		
		if (page_no_str!=null && !page_no_str.equals("") && !page_no_str.equals("null"))
		{
			page_no = Integer.parseInt(page_no_str);
		}
		RoleEntity  roleInfo = super.getRoleEntity(request);
		
		int p_pk = roleInfo.getBasicInfo().getPPk();

		/* 查询数据库中该角色有没有该类型仓库 */
		WareHouseVO warevo = storageService.getWareHouseByPPk(p_pk);; 
		
		QueryPage item_page = null;
		switch(w_type)
		{
			case Wrap.CURE:item_page = goodsSerivce.getPagePropList(p_pk, Wrap.CURE,page_no);break;
			case Wrap.BOOK:item_page = goodsSerivce.getPagePropList(p_pk,Wrap.BOOK, page_no);break;
			case Wrap.EQUIP:item_page = goodsSerivce.getPageEquipOnWrap(p_pk, page_no);break;
			case Wrap.TASK:item_page = goodsSerivce.getPagePropList(p_pk, Wrap.TASK,page_no);break;
			case Wrap.REST:item_page = goodsSerivce.getPagePropList(p_pk, Wrap.REST,page_no);break;
			case Wrap.SHOP:item_page = goodsSerivce.getPagePropList(p_pk, Wrap.SHOP,page_no);break;
		}
		item_page.setURL(response, "/menu/storage.do?cmd=n1&amp;w_type="+w_type);
		
		request.setAttribute("warevo", warevo);
		request.setAttribute("item_page", item_page);
		request.setAttribute("w_type", "" + w_type);
		request.setAttribute("pageNo", page_no+"");
		return mapping.findForward("storage_list");
	}	
	
	// 储存操作
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{	
		RoleEntity  roleInfo = this.getRoleEntity(request);
		
		//装备类型，1是药品，2是书，3是装备，4是任务，5是其他
		String w_type = request.getParameter("w_type");
		request.setAttribute("w_type",w_type);

		//存储页数
		request.setAttribute("page_no", request.getParameter("pageNo")+"");
		
		String resultWml = null;

		GoodsService goodsSerivce = new GoodsService();
		StorageService storageSerivce = new StorageService();
		
		int warespare = storageSerivce.getWareSpareById( roleInfo.getBasicInfo().getPPk());
		
		// 储存装备
		if (Integer.parseInt(w_type) == Wrap.EQUIP)
		{
			String equip_id = request.getParameter("equip_id");
			if(warespare >=1 ){
				resultWml = storageSerivce.storeEquip( roleInfo.getBasicInfo().getPPk(),Integer.parseInt(equip_id));
			}else {
				resultWml = "对不起，仓库已满！";
			}
			
		} else	
		{	
			/* 查询该角色的仓库有多少空余 */
			String pg_pk = request.getParameter("pg_pk");
			PlayerPropGroupVO goodsGroup = goodsSerivce.getGoodsGroupByPgPk(Integer.parseInt(pg_pk));
			String prop_num_str = request.getParameter("prop_num");
			int prop_num;
			if (prop_num_str == null)	
			{
				
				// 道具
				if (goodsGroup.getPropNum() == 1)// 只有一个
				{
						
					if(warespare >=1 ){
						
						resultWml = storageSerivce.storeProps(Integer.parseInt(pg_pk),1,roleInfo);
					}else {	
						resultWml = "对不起，仓库已满！";
					}
					
				} else
				// 有多个，让用户添加数量
				{	
					request.setAttribute("w_type", w_type);
					request.setAttribute("pg_pk", pg_pk);
					return mapping.findForward("input_num");
				}
			} else
			{
				if(prop_num_str == null || prop_num_str.equals("")||prop_num_str.equals("null")||prop_num_str.equals("all")){
					prop_num_str = goodsGroup.getPropNum()+"";
				}
				try
				{
					prop_num = Integer.parseInt(prop_num_str);
					if(prop_num < 0){
						resultWml = "新金警告您, 刷装备是一件违法行为!";
						request.setAttribute("w_type", w_type);
						request.setAttribute("pg_pk", pg_pk);
						request.setAttribute("resultWml", resultWml);
						return mapping.findForward("input_num");
					}	
					if(prop_num == 0){
						resultWml = "不能存储零个物品!";
						request.setAttribute("w_type", w_type);
						request.setAttribute("pg_pk", pg_pk);
						request.setAttribute("resultWml", resultWml);
						return mapping.findForward("input_num");
					}	
					if (goodsGroup.getPropNum() >= prop_num)
					{
						int need_space = goodsGroup.getPropInfo().getNeedSpace(prop_num);
						if(warespare >= need_space ){
							resultWml = storageSerivce.storeProps(Integer.parseInt(pg_pk),prop_num,roleInfo);
						}else{	
							resultWml = "对不起，仓库已满！";
						}
						
					} else
					{
						// 数量不够
						resultWml = "对不起，该物品数量不够！";
						request.setAttribute("w_type", w_type);
						request.setAttribute("pg_pk", pg_pk);
						request.setAttribute("resultWml", resultWml);
						return mapping.findForward("input_num");
					}
				} catch (NumberFormatException e)
				{
					// 数量的格式不正确
					resultWml = "正确输入物品数量";
					request.setAttribute("w_type", w_type);
					request.setAttribute("pg_pk", pg_pk);
					request.setAttribute("resultWml", resultWml);
					return mapping.findForward("input_num");
				}
			}
		}
		request.setAttribute("resultWml", resultWml);
		
		try
		{
			request.getRequestDispatcher("/menu/storage.do?cmd=n1&w_type="+w_type+"&page_no="+request.getAttribute("page_no")+"").forward(request, response);
		}
		catch (Exception e)
		{
			System.out.println("跳转错误.....");
		}
		return null;
	}
	
	
	/**
	 * 角色包裹装备查看详细信息
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		GoodsService goodsService = new GoodsService();
		EquipDisplayService equipDisplayService = new EquipDisplayService();
		
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		String pwPk = request.getParameter("pwPk");
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		
		String equip_display = equipDisplayService.getEquipDisplay(roleInfo,equip,true);
		
		request.setAttribute("equip", equip);
		request.setAttribute("equip_display", equip_display);
		request.setAttribute("page_no", request.getParameter("page_no"));
		request.setAttribute("w_type", request.getParameter("w_type"));
		return mapping.findForward("equip_view");
	}
	
	// 角色包裹道具查看详细信息
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		GoodsService goodsService = new GoodsService();
		
		RoleEntity  roleInfo = this.getRoleEntity(request);
				
		String goods_id = request.getParameter("goods_id");
		
		String goods_display = goodsService.getPropInfoWml(roleInfo.getBasicInfo().getPPk(),Integer.parseInt(goods_id));
		
		request.setAttribute("page_no", request.getParameter("page_no"));
		request.setAttribute("w_type", request.getParameter("w_type"));
		request.setAttribute("pg_pk", request.getParameter("pg_pk"));
		request.setAttribute("goods_display", goods_display);
		return mapping.findForward("goods_view");
	}
	
	/**增加仓库包裹的二次确认页面*/
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RoleEntity  roleInfo = super.getRoleEntity(request);
				
		WareHouseDao dao = new WareHouseDao();
		int num = dao.getPlayerWarehouseNum(roleInfo.getBasicInfo().getPPk());
		if(num == 80){
			request.setAttribute("num", ""+1);
			request.setAttribute("display", "第一次购买增加20个栏位需要花费【"+GameConfig.getYuanbaoName()+"】×200!");
			return mapping.findForward("submit");
		}else if(num == 100){
			request.setAttribute("num", ""+2);
			request.setAttribute("display", "第二次购买增加20个栏位需要花费【"+GameConfig.getYuanbaoName()+"】×500!");
			return mapping.findForward("submit");
		}else if(num == 120){
			request.setAttribute("num", ""+3);
			request.setAttribute("display", "第三次购买增加20个栏位需要花费【"+GameConfig.getYuanbaoName()+"】×1000!");
			return mapping.findForward("submit");
		}if(num == 140){
			request.setAttribute("num", ""+4);
			request.setAttribute("display", "第四次购买增加20个栏位需要花费【"+GameConfig.getYuanbaoName()+"】×1500!");
			return mapping.findForward("submit");
		}else if(num == 160){
			request.setAttribute("num", ""+5);
			request.setAttribute("display", "第五次购买增加20个栏位需要花费【"+GameConfig.getYuanbaoName()+"】×2000!");
			return mapping.findForward("submit");
		}else{
			request.setAttribute("display", "您的仓库栏位已经为最大了!");
			return mapping.findForward("display");
		}
		
	}
	
	/**增加包裹并减去玩家的元宝数量*/
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		/**如果是电信平台走专用渠道**/
		if(GameConfig.getChannelId()==Channel.TELE)
		{
			return n8(mapping,form,request,response);
		}
		RoleEntity  roleInfo = super.getRoleEntity(request);
		int num = Integer.parseInt(request.getParameter("num").toString());
		EconomyService economyService = new EconomyService();
		long yuanbao = economyService.getYuanbao(roleInfo.getBasicInfo().getUPk());
		int useyuanbao = 0;
		if (num == 1)
		{
			useyuanbao = 200;
		}
		else
			if (num == 2)
			{
				useyuanbao = 500;
			}
			else
				if (num == 3)
				{
					useyuanbao = 1000;
				}
				else
					if (num == 4)
					{
						useyuanbao = 1500;
					}
					else
						if (num == 5)
						{
							useyuanbao = 2000;
						}
						else
						{
							useyuanbao = -1;
						}

		if (useyuanbao == -1)
		{
			request.setAttribute("display", "数量错误请重新购买<br/>");
			return mapping.findForward("display");
		}
		else
		
		if(useyuanbao > yuanbao){
			request.setAttribute("display", "您的"+GameConfig.getYuanbaoName()+"数量不够!");
			return mapping.findForward("display");
		}else{
		WareHouseDao dao = new WareHouseDao();
		dao.updatePlayerWarehouseMaxNum(roleInfo.getBasicInfo().getPPk(), 20);
		dao.updatePlayerWarehouseNum(roleInfo.getBasicInfo().getPPk(), 20);
		economyService.spendYuanbao(roleInfo.getBasicInfo().getUPk(), useyuanbao);
		
		LogService logService = new LogService();
		logService.recordYBLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), useyuanbao+"", useyuanbao+"", "购买仓库");
		
		request.setAttribute("display", "购买成功,您的仓库格数增加了20个格子!");
		return mapping.findForward("display");
		}
	} 
	/**
	 * 电信专用
	 */
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		RoleEntity  roleInfo = super.getRoleEntity(request);
		int num = Integer.parseInt(request.getParameter("num").toString());
		String consumCode="";
		if(num==1)
		{
			consumCode="store1";
		}
		else if(num==2)
		{
			consumCode="store2";
		}
		else if(num==3)
		{
			consumCode="store3";
		}
		else if(num==4)
		{
			consumCode="store4";
		}
		else if(num==5)
		{
			consumCode="store5";
		}
		String display = null;
		MallService ms=new MallService();
		String hint=ms.consumeForTele(request, roleInfo, consumCode, "1");
		if(hint!=null)
		{
			display=hint;
		}
		else
		{
			display="购买成功！您的仓库增加了10个格子！";
		}
		return mapping.findForward("display");
	}
}
