package com.ls.web.action.goods;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.model.equip.StoneProduct;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.validate.ValidateService;

/**
 * @author ls
 * ���ߵĻ�������(������ʯ)
 */
public class PropAction extends DispatchAction
{
	/**
	 * ������ʯ��ҳ
	 */
	public ActionForward upgradeIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleEntity = roleService.getRoleInfoBySession(request.getSession());
		StoneProduct stoneProduct = roleEntity.getStoneProduct();
		request.setAttribute("stoneProduct",stoneProduct);
		return mapping.findForward("upgrade_index");
	}
	
	/**
	 * ��ʯ�б�
	 */
	public ActionForward stoneList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		GoodsService goodsService = new GoodsService();
	
		String page_no = request.getParameter("page_no");
		if( page_no==null || page_no.equals(""))
		{
			page_no = "1";
		}
		
		RoleEntity roleEntity = roleService.getRoleInfoBySession(request.getSession());
		QueryPage stone_list =  goodsService.getPageInlayStoneList(roleEntity.getBasicInfo().getPPk(),Integer.parseInt(page_no));
		stone_list.setURL(response, "/prop.do?cmd=stoneList");
		request.setAttribute("stone_list", stone_list);
		return mapping.findForward("stone_list");
	}
	/**
	 * ����ɹ��ʱ�ʯ����
	 */
	public ActionForward inputStoneNum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String propId = request.getParameter("propId");
		
		request.setAttribute("propId", propId);
		return mapping.findForward("input_stone_num");
	}
	/**
	 * ȷ��ʹ�ñ�ʯ
	 */
	public ActionForward useStone(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		
		String propId = request.getParameter("propId");
		String propNum = request.getParameter("propNum");
		
		ValidateService validateService = new ValidateService();
		
		String hint = validateService.validateNonZeroNegativeIntegers(propNum);
		if( hint!=null  )
		{
			request.setAttribute("hint", hint+"<br/>");
			request.setAttribute("propId", propId);
			return mapping.findForward("input_stone_num");
		}
		
		RoleEntity roleEntity = roleService.getRoleInfoBySession(request.getSession());
		StoneProduct stoneProduct = roleEntity.getStoneProduct();
		hint = stoneProduct.selectStone(Integer.parseInt(propId), Integer.parseInt(propNum)); 
		
		if( hint!=null )
		{
			request.setAttribute("hint", hint+"<br/>");
			request.setAttribute("propId", propId);
			return mapping.findForward("input_stone_num");
		}
		
		return this.upgradeIndex(mapping, form, request, response);
	}
	
	/**
	 * ����
	 */
	public ActionForward upgrade(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		
		RoleEntity roleEntity = roleService.getRoleInfoBySession(request.getSession());
		StoneProduct stoneProduct = roleEntity.getStoneProduct();
		String hint = stoneProduct.upgrade();
		request.setAttribute("hint", hint);
		return mapping.findForward("upgrade_hint");
	}
	
	/**
	 * ��������
	 */
	public ActionForward des(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		GoodsService goodsService = new GoodsService();
		
		String propId = request.getParameter("propId");
		String pre = request.getParameter("pre");
		
		RoleEntity roleEntity = roleService.getRoleInfoBySession(request.getSession());
		String prop_des = goodsService.getPropInfoWmlMai(roleEntity.getBasicInfo().getPPk(),Integer.parseInt(propId));
		
		if( pre!=null && pre.equals("inlayStone"))
		{
			request.setAttribute("pwPk", request.getParameter("pwPk"));
		}
		
		request.setAttribute("prop_des", prop_des);
		request.setAttribute("pre", pre);
		return mapping.findForward("prop_des");
	}
}
