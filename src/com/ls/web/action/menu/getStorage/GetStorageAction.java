package com.ls.web.action.menu.getStorage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.storage.WareHouseDao;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.ben.vo.storage.WareHouseVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.Wrap;
import com.ls.web.action.ActionBase;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipDisplayService;
import com.ls.web.service.storage.StorageService;

public class GetStorageAction extends ActionBase{
	Logger logger =  Logger.getLogger("log.action");
	
	// 仓库物品列表
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		StorageService storageService = new StorageService();
		
		String uw_type_str = request.getParameter("w_type");
		String page_no_str = request.getParameter("page_no");
		
		int w_type=Wrap.BOOK;
		int page_no = 1;
		if (uw_type_str != null)
		{
			w_type = Integer.parseInt(uw_type_str);
		} 
		if( page_no_str!=null )
		{
			page_no = Integer.parseInt(page_no_str);
		}
		
		RoleEntity  roleInfo = this.getRoleEntity(request);
		
		int p_pk = roleInfo.getBasicInfo().getPPk();
		

		/** 查询数据库中该角色有没有该类型仓库 */
		WareHouseVO warevo = storageService.getWareHouseByPPk(p_pk);
		
		QueryPage item_page = null;
		switch(w_type)
		{
			case Wrap.CURE:item_page = storageService.getPagePropList(p_pk, Wrap.CURE,page_no);break;
			case Wrap.BOOK:item_page = storageService.getPagePropList(p_pk,Wrap.BOOK, page_no);break;
			case Wrap.EQUIP:item_page = storageService.getPageEquipOnStorage(p_pk, page_no);break;
			case Wrap.TASK:item_page = storageService.getPagePropList(p_pk, Wrap.TASK,page_no);break;
			case Wrap.REST:item_page = storageService.getPagePropList(p_pk, Wrap.REST,page_no);break;
			case Wrap.SHOP:item_page = storageService.getPagePropList(p_pk, Wrap.SHOP,page_no);break;
		}
		item_page.setURL(response, "/menu/getStorage.do?cmd=n1&amp;w_type="+w_type);
		
		request.setAttribute("warevo", warevo);
		request.setAttribute("item_page", item_page);
		request.setAttribute("w_type", "" + w_type);
		request.setAttribute("page_no", page_no+"");
		return mapping.findForward("getStorage_list");
	}
	
	
	// 取出操作
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity  roleInfo = this.getRoleEntity(request);
		
		String warehouseID = request.getParameter("warehouseID");

		//装备类型，1是药品，2是书，3是装备，4是任务，5是其他 6是商城类道具
		String w_type = request.getParameter("w_type");

		//存储页数
		request.setAttribute("page_no", request.getParameter("page_no")+"");
		
		String resultWml = null;

		StorageService storageSerivce = new StorageService();
		WareHouseDao wareHouse = new WareHouseDao();
		WareHouseVO wareHouseVO = wareHouse.getWareHouseVOByWareHouseId(warehouseID,roleInfo.getPPk());
		
		
		/* 查询该角色的包裹有多少空余 */
		int pWrapSpare = roleInfo.getBasicInfo().getWrapSpare();
		
		String prop_Id = request.getParameter("prop_id");
		// 取出装备
		if (Integer.parseInt(w_type) == Wrap.EQUIP)
		{
			String WPk = request.getParameter("WPk");
			if(pWrapSpare >=1 ){
				resultWml = storageSerivce.takeoutEquip(roleInfo.getPPk(),Integer.parseInt(WPk));
			}else {
				resultWml = "对不起，您的包裹已满！";
			}
		} else
		{
			
			String prop_num_str = request.getParameter("prop_num");
			int prop_num;
			if (prop_num_str == null)
			{
				// 道具
				if (wareHouseVO.getUwPropNumber() == 1)// 只有一个
				{
						if(pWrapSpare >=1 ){
							resultWml = storageSerivce.getStorageProps(Integer.valueOf(prop_Id),1,roleInfo,wareHouseVO);
						}else {
							resultWml = "对不起，您的包裹已满！";
						}
					 
				} else
				// 有多个，让用户添加数量
				{
					request.setAttribute("warehouseID", warehouseID);
					request.setAttribute("w_type", w_type);
					request.setAttribute("prop_id",prop_Id);
					return mapping.findForward("input_num");
				}
			} else
			{
				try
				{
					if(prop_num_str == null || prop_num_str.equals("")||prop_num_str.equals("null")||prop_num_str.equals("all")){
						prop_num_str = wareHouseVO.getUwPropNumber()+"";
					}
					prop_num = Integer.parseInt(prop_num_str);
					if(prop_num < 0){
						resultWml = "新金警告您, 刷装备是一件违法行为!";
						request.setAttribute("w_type", w_type);
						request.setAttribute("resultWml", resultWml);
						request.setAttribute("warehouseID", warehouseID);
						request.setAttribute("prop_id",prop_Id);
						return mapping.findForward("input_num");
					}
					else if(prop_num == 0){
						resultWml = "物品取出数量不能是零个!";
						request.setAttribute("w_type", w_type);
						request.setAttribute("resultWml", resultWml);
						request.setAttribute("warehouseID", warehouseID);
						request.setAttribute("prop_id",prop_Id);
						return mapping.findForward("input_num");
					}
					else if (wareHouseVO.getUwPropNumber() >= prop_num)
					{
						//得到prop_num个道具需要多少空间
						int need_space = PropCache.getPropById(wareHouseVO.getUwPropId()).getNeedSpace(prop_num);
						if(pWrapSpare >= need_space){
							resultWml = storageSerivce.getStorageProps(Integer.valueOf(prop_Id),prop_num,roleInfo,wareHouseVO);
						}else{
							resultWml = "对不起，您的包裹已满！";
						}
					} else
					{
						// 数量不够
						resultWml = "对不起，该物品数量不够！";
						request.setAttribute("w_type", w_type);
						request.setAttribute("resultWml", resultWml);
						request.setAttribute("warehouseID", warehouseID);
						request.setAttribute("prop_id",prop_Id);
						return mapping.findForward("input_num");
					}
				} catch (NumberFormatException e)
				{
					// 数量的格式不正确
					resultWml = "正确输入物品数量";
					request.setAttribute("w_type", w_type);
					request.setAttribute("resultWml", resultWml);
					request.setAttribute("warehouseID", warehouseID);
					request.setAttribute("prop_id",prop_Id);
					return mapping.findForward("input_num");
				}
			}
		}

		request.setAttribute("w_type", w_type);
		request.setAttribute("resultWml", resultWml);
		try
		{
			request.getRequestDispatcher("/menu/getStorage.do?cmd=n1&w_type="+w_type+"&page_no="+request.getAttribute("page_no")+"").forward(request, response);
		}
		catch (Exception e)
		{
			System.out.println("转发出错......");
		}
		return null;
	}
	
	// 角色包裹道具查看详细信息
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		GoodsService goodsService = new GoodsService();
		
		RoleEntity  roleInfo = this.getRoleEntity(request);
		
		String goods_id = request.getParameter("goods_id");

		String goods_display = goodsService.getPropInfoWml(roleInfo.getBasicInfo().getPPk(),Integer.parseInt(goods_id));

		request.setAttribute("w_type", request.getParameter("w_type"));
		request.setAttribute("goods_display", goods_display);
		request.setAttribute("page_no", request.getParameter("page_no"));
		request.setAttribute("prop_id", goods_id);
		request.setAttribute("warehouseID", request.getParameter("warehouseID"));
		return mapping.findForward("warehousepropview");
	}
	
	
	/**
	 * 角色包裹装备查看详细信息
	 */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		GoodsService goodsService = new GoodsService();
		EquipDisplayService equipDisplayService = new EquipDisplayService();
		
		RoleEntity  roleInfo = this.getRoleEntity(request);
		
		String pwPk = request.getParameter("pwPk");
		
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		
		String equip_display = equipDisplayService.getEquipDisplay(roleInfo,equip,false);
		
		request.setAttribute("equip_display", equip_display);
		request.setAttribute("pwPk", pwPk);
		request.setAttribute("page_no", request.getParameter("page_no"));
		return mapping.findForward("warehouseequipview");
	}
}
