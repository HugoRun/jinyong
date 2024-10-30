package com.ls.web.action.wrap.equiprelela;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.web.action.ActionBase;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.RoleService;

/**
 * 展示装备
 * @author 张俊俊
 */
public class EquipRelelationAction extends ActionBase
{
	Logger logger = Logger.getLogger("log.action");

	/**
	 * 展示装备
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = this.getRoleEntity(request);
		
		String pwpk = request.getParameter("pwPk");
		
		GoodsService goodsService = new GoodsService();
		PlayerEquipVO equipVO = goodsService.getEquipByID(Integer.parseInt(pwpk));
		
		EconomyService economyService = new EconomyService();
		long yuanbao = economyService.getYuanbao(roleEntity.getBasicInfo().getUPk());
		
		String hint = null;
		if ( yuanbao < 50) {
			hint =  "对不起，每展示一次装备需消耗"+GameConfig.getYuanbaoName()+"×50!";
			request.setAttribute("hint", hint);
			return mapping.findForward("displayfalse");
		}
		
		EquipService equipService = new EquipService();
		hint = equipService.relelaEquip(roleEntity,equipVO,response,request);
		request.setAttribute("hint", hint);
		request.setAttribute("return_type", request.getParameter("return_type"));
		return mapping.findForward("sussend");
	}
	
	
	/**
	 * 获得装备展示信息
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		RoleService roleService = new RoleService();
		RoleEntity roleEntity = roleService.getRoleInfoBySession(request.getSession());
		
		String pwpk = request.getParameter("pwpk");
		
		EquipService equipService = new EquipService();
		String displayString = equipService.getEelelaEquipInfo(roleEntity, pwpk);
		
		
		request.setAttribute("hint", displayString);
		
		return mapping.findForward("sussend");
	}
}
