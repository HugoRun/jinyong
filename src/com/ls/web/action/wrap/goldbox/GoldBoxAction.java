package com.ls.web.action.wrap.goldbox;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.dao.mall.CommodityDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.attack.DropGoodsVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.ben.vo.mall.CommodityVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.GoodsType;
import com.ls.pub.constant.PropType;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipDisplayService;
import com.ls.web.service.goods.prop.GoldBoxService;
import com.ls.web.service.npc.NpcService;
import com.ls.web.service.player.RoleService;
import com.web.service.popupmsg.PopUpMsgService;

public class GoldBoxAction  extends DispatchAction
{
	Logger logger = Logger.getLogger("log.action");
	
	
	// 转向使用物品之列，列出 所有的黄金宝箱
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int p_pk = roleInfo.getBasicInfo().getPPk(); 
		
		String w_type = (String)request.getParameter("w_type");
		String pg_pk = (String)request.getParameter("pg_pk");
		String goods_id = (String)request.getParameter("goods_id");
		String goods_type = (String)request.getParameter("goods_type");
		
		String page_no_str = request.getParameter("page_no");
		int page_no; 
		
		if( page_no_str==null || page_no_str.equals("")) {
			page_no = 1;
		}else {
			page_no = Integer.parseInt(page_no_str);
		}
		
		
		request.setAttribute("goods_type", goods_type);
		request.setAttribute("goods_id", goods_id);
		request.setAttribute("w_type", w_type);
		request.setAttribute("pg_pk", pg_pk);
		
		// 获得天眼符的 信息
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		PlayerPropGroupVO groupVO = dao.getByPgPk(Integer.parseInt(pg_pk));
	 	PropVO propVO = PropCache.getPropById(groupVO.getPropId());	 	
	 		 	
	 	// 第一个是黄金宝箱ID,第二个是金钥匙ID
	 	String box_idString = propVO.getPropOperate1();
	 	String key_idString = propVO.getPropOperate2();
	 	request.getSession().setAttribute("key_info", key_idString);
	 	request.getSession().setAttribute("box_info", box_idString);
	 	PropVO boxVO = PropCache.getPropById(Integer.parseInt(box_idString));	 	
	 	
		GoldBoxService goldBoxService = new GoldBoxService();
		QueryPage gold_box_list = goldBoxService.getGoldBoxList(p_pk,box_idString,page_no);

		request.setAttribute("gold_box_list", gold_box_list);
		request.setAttribute("boxVO", boxVO);
		
		return mapping.findForward("gold_box_list");
	}
	
	// 打开黄金宝箱
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int p_pk = roleInfo.getBasicInfo().getPPk();
		
		String w_type = (String)request.getParameter("w_type");
		String pg_pk = (String)request.getParameter("pg_pk");
		String goods_id = (String)request.getParameter("goods_id");
		String goods_type = (String)request.getParameter("goods_type");
		String gold_box_pgpk = (String)request.getParameter("gold_box_pgpk");
		String key_info = (String) request.getSession().getAttribute("key_info");
		
		request.setAttribute("goods_type", goods_type);
		request.setAttribute("goods_id", goods_id);
		request.setAttribute("w_type", w_type);
		request.setAttribute("pg_pk", pg_pk);
		
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		PropVO keyVO = PropCache.getPropById(Integer.parseInt(key_info));	 	
		PropVO propVO = PropCache.getPropById(Integer.parseInt(goods_id));	 	
		PlayerPropGroupVO boxvo = dao.getByPgPk(Integer.parseInt(gold_box_pgpk));
	 	PropVO box_PropVO = PropCache.getPropById(boxvo.getPropId());
		
		GoldBoxService goldBoxService = new GoldBoxService();
		String hintString = "";
		if ( box_PropVO.getPropClass() == PropType.GOLD_BOX) {
			// 如果是黄金宝箱
			hintString = goldBoxService.openGoldBox(pg_pk,gold_box_pgpk,roleInfo);
		} else if ( box_PropVO.getPropClass() == PropType.OTHER_GOLD_BOX){
			// 如果不是黄金宝箱
			hintString = goldBoxService.openGoldBoxByOther(pg_pk, gold_box_pgpk, roleInfo);
		}
		
		List<DropGoodsVO> dropgoods = roleInfo.getDropSet().getList();
		
		request.setAttribute("dropgoods", dropgoods);
		request.setAttribute("hintString", hintString);
		request.setAttribute("w_type", w_type);
		request.setAttribute("keyVO", keyVO);
		request.setAttribute("boxvo", boxvo);
		request.setAttribute("propVO", propVO);
		request.setAttribute("gold_box_pgpk", gold_box_pgpk);
		return mapping.findForward("goods_list");
	}
	
	// 查看黄金宝箱里的物品
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		String w_type = (String)request.getParameter("w_type");
		String gold_box_pgpk = (String)request.getParameter("gold_box_pgpk");
		request.setAttribute("w_type", w_type);
		request.setAttribute("gold_box_pgpk", gold_box_pgpk);
		String goods_id = request.getParameter("goods_id");
		String goods_type_str = request.getParameter("goods_type");
		
		if (goods_id == null || goods_type_str == null || goods_type_str.equals("") || goods_id.equals(""))
		{
			System.out.print("buyAction中n2方法出现意外：npcShop或npcshop_id为空");
		}
		
		GoodsService goodsService = new GoodsService();
		EquipDisplayService equipDisplayService = new EquipDisplayService();
		String goods_display = null;
		int goods_type = Integer.parseInt(goods_type_str);
		
		if (goods_type == GoodsType.EQUIP)
		{
			goods_display = equipDisplayService.getEquipDisplay(roleInfo, Integer.parseInt(goods_id), false);
		}
		else if (goods_type == GoodsType.PROP)
		{
				goods_display = goodsService.getPropInfoWmlMai(
						roleInfo.getBasicInfo().getPPk(), Integer.parseInt(goods_id));
				goods_display = goods_display + "<br/>";
		}
		
		request.setAttribute("goods_id", goods_id);
		request.setAttribute("goods_display", goods_display);
		return mapping.findForward("goods_display");
		
	}
	
	// 试图获得黄金宝箱中的物品
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		GoldBoxService goldBoxService = new GoldBoxService();
		NpcService npcService = new NpcService();
		
		String key_info = (String) request.getSession().getAttribute("key_info");
		request.getSession().removeAttribute("key_info");
		String w_type = (String)request.getParameter("w_type");
		request.setAttribute("w_type", w_type);
		
		String box_info = (String) request.getSession().getAttribute("box_info");
		PropVO keyVO = PropCache.getPropById(Integer.parseInt(key_info));	 	
		PropVO boxVO = PropCache.getPropById(Integer.parseInt(box_info));	 	
		request.getSession().removeAttribute("box_info");
		
		String hint = "";
		if (goldBoxService.checkHaveGoldYueShi(roleInfo,key_info)) {
			// 如果是黄金宝箱
			if ( boxVO.getPropClass() == PropType.GOLD_BOX) {
				hint = goldBoxService.getGoldBoxGoods(roleInfo,key_info,keyVO,boxVO,response,request);
			} else if ( boxVO.getPropClass() == PropType.OTHER_GOLD_BOX){
				// 如果不是黄金宝箱
				hint = goldBoxService.getGoldBoxGoodsByOtherBox(roleInfo,key_info,keyVO,boxVO,response,request);
			}
			npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
			request.setAttribute("hint", hint);
			return mapping.findForward("hint");
			
		} else {
			int digit = GameConfig.useProp(boxVO.getPropID(), "use_prop_id");//返回玩家等级是否在系统设定之内
			if( digit != -1){
				new PopUpMsgService().addSysSpecialMsg(roleInfo.getBasicInfo().getPPk(),boxVO.getPropID(),digit, PopUpMsgType.USE_PROP);
			}
			hint = "您身上没有"+keyVO.getPropName()+",打开"+boxVO.getPropName()+"失败!";
			npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
			request.setAttribute("hint", hint);
			return mapping.findForward("hint");
		}
	}
	
	
	// 放弃获得黄金宝箱中的物品
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		GoldBoxService goldBoxService = new GoldBoxService();
		
		String w_type = (String)request.getParameter("w_type");
		request.setAttribute("w_type", w_type);
		
		String box_info = (String) request.getSession().getAttribute("box_info");
		PropVO boxVO = PropCache.getPropById(Integer.parseInt(box_info));	 	
		request.getSession().removeAttribute("box_info");
		
		String hint = goldBoxService.dropGoldBoxGoods(roleInfo,boxVO);
		request.setAttribute("hint", hint);
		
		return mapping.findForward("hint");
	}
	
	

	// 用金钥匙直接打开黄金宝箱
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
				
		String w_type = (String)request.getAttribute("w_type");
		request.setAttribute("w_type", w_type);
		String pg_pk = (String)request.getAttribute("pg_pk");
		String goods_id = (String)request.getAttribute("goods_id");
		String goods_type = (String)request.getAttribute("goods_type");
		request.setAttribute("goods_type", goods_type);
		request.setAttribute("goods_id", goods_id);
		request.setAttribute("pg_pk", pg_pk);
		 
		//获得宝箱的数据
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		PlayerPropGroupVO groupVO = dao.getByPgPk(Integer.parseInt(pg_pk));
	 	PropVO boxVO = PropCache.getPropById(groupVO.getPropId());	
		
		/***********************这里判断开启的物品****************************************/
	 	//获得开启的道具ID [0] 掉落数量 [1]开启的ID
	 	String[] oper_prop_id = boxVO.getPropOperate2().split(",");
	 	PropVO oper_prop_vo = PropCache.getPropById(Integer.parseInt(oper_prop_id[1]));
	 	//是否有某种道具
	 	boolean flag = dao.getUserHasProp(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(oper_prop_id[1]));
	 	if ( !flag) {
	 		int digit = GameConfig.useProp(Integer.parseInt(goods_id), "use_prop_id");//返回玩家等级是否在系统设定之内
			if( digit != -1){
				new PopUpMsgService().addSysSpecialMsg(roleInfo.getBasicInfo().getPPk(),Integer.parseInt(goods_id),digit, PopUpMsgType.USE_PROP);
			}
			CommodityDao commodityDao = new CommodityDao();
			CommodityVO commodityVO = commodityDao.getPropCommodity(oper_prop_vo.getPropID()+"");
			if(commodityVO == null){
				request.setAttribute("hint", "您身上没有"+oper_prop_vo.getPropName()+"!");
			}else{
				request.setAttribute("hint", "您身上没有<anchor><go method=\"post\" href=\"mall.do\"><postfield name=\"cmd\" value=\"n6\"/><postfield name=\"c_id\" value=\""+commodityVO.getId()+"\"/></go>"+oper_prop_vo.getPropName()+"</anchor>!");
			}
			return mapping.findForward("hint");
		}
	 	/************************************************************************/
	 	String box_id = boxVO.getPropID()+"";
	 	String hint = null;
		GoldBoxService goldBoxService = new GoldBoxService();
		NpcService npcService = new NpcService();		
		// 如果是黄金宝箱
		if ( boxVO.getPropClass() == PropType.GOLD_BOX) {
    		String hintString = goldBoxService.isOpenGoldBox(pg_pk,roleInfo,box_id);		
    		if (hintString != null && !hintString.equals("")) {
    			request.setAttribute("hint", hintString);
    			return mapping.findForward("hint");
    		}		
    		hint = goldBoxService.getGoldBoxGoods(roleInfo,oper_prop_vo.getPropID()+"",oper_prop_vo,boxVO,response,request);
		} else if (boxVO.getPropClass() == PropType.OTHER_GOLD_BOX) {
			// 如果不是黄金宝箱
			String hintString = goldBoxService.zhiJieOpenGoldBoxByOther(pg_pk,roleInfo,box_id);		
    		if (hintString != null && !hintString.equals("")) {
    			request.setAttribute("hint", hintString);
    			return mapping.findForward("hint");
    		}
    		hint = goldBoxService.getGoldBoxGoodsByOtherBox(roleInfo,oper_prop_vo.getPropID()+"",oper_prop_vo,boxVO,response,request);
		}
		npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
		request.setAttribute("hint", hint);
		
		
		 
		
		/*
		// 获得金钥匙的 信息
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		//PropDao propDAO = new PropDao();
		PropCache propCache = new PropCache();
		PlayerPropGroupVO groupVO = dao.getByPgPk(Integer.parseInt(pg_pk));
	 	PropVO propVO = propCache.getPropById(groupVO.getPropId());	 	
	 
	 	String box_id = propVO.getPropOperate1();
	 	PropVO boxVO = propCache.getPropById(Integer.parseInt(box_id));	 	
		
	 	boolean flag = dao.getUserHasProp(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(box_id));
	 			
		if ( !flag) {
			request.setAttribute("hint", "您身上没有"+boxVO.getPropName()+"!");
			return mapping.findForward("hint");
		}
		String hint = null;
		GoldBoxService goldBoxService = new GoldBoxService();
		NpcService npcService = new NpcService();		
		// 如果是黄金宝箱
		if ( boxVO.getPropClass() == PropType.GOLD_BOX) {
    		String hintString = goldBoxService.zhiJieOpenGoldBox(pg_pk,roleInfo,box_id);		
    		if (hintString != null && !hintString.equals("")) {
    			request.setAttribute("hint", hintString);
    			return mapping.findForward("hint");
    		}		
    		hint = goldBoxService.getGoldBoxGoods(roleInfo,groupVO.getPropId()+"",propVO,boxVO);
		} else if (boxVO.getPropClass() == PropType.OTHER_GOLD_BOX) {
			// 如果不是黄金宝箱
			String hintString = goldBoxService.zhiJieOpenGoldBoxByOther(pg_pk,roleInfo,box_id);		
    		if (hintString != null && !hintString.equals("")) {
    			request.setAttribute("hint", hintString);
    			return mapping.findForward("hint");
    		}
    		hint = goldBoxService.getGoldBoxGoodsByOtherBox(roleInfo,groupVO.getPropId()+"",propVO,boxVO);
		}
		npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
		request.setAttribute("hint", hint);*/
		return mapping.findForward("hint");
	}
	
	// 返回到 选择物品的页面上来 
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
				
		String w_type = (String)request.getParameter("w_type");
		String gold_box_pgpk = (String)request.getParameter("gold_box_pgpk");
		
		request.setAttribute("w_type", w_type);
		String key_info = (String) request.getSession().getAttribute("key_info");
		
		PropVO keyVO = PropCache.getPropById(Integer.parseInt(key_info));
		
		request.setAttribute("keyVO", keyVO);
		
		PlayerPropGroupDao dao = new PlayerPropGroupDao(); 	
		PlayerPropGroupVO boxvo = dao.getByPgPk(Integer.parseInt(gold_box_pgpk));
		request.setAttribute("boxvo", boxvo);
		request.setAttribute("gold_box_pgpk", gold_box_pgpk);
		
		List<DropGoodsVO> dropgoods = roleInfo.getDropSet().getList();
		
		request.setAttribute("dropgoods", dropgoods);
		
		return mapping.findForward("goods_list");
	}
}
