package com.ls.web.action.organize;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.model.item.Item;
import com.ls.model.organize.faction.Faction;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.PropType;
import com.ls.web.action.ActionBase;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.validate.ValidateService;

/**
 * @author ls
 * 帮派仓库
 */
public class FStorageAction extends ActionBase
{

	/**
	 * 帮派材料列表
	 */
	public ActionForward fMList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return super.dispath(request, response, "/faction.do?cmd=index");
		}
		
		List<Item> item_list = faction.getFStorage().getList();
		request.setAttribute("item_list", item_list);
		return mapping.findForward("faction_material_list");
	}
	/**
	 * 包裹里的材料列表
	 */
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward wMList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		GoodsService goodsService = new GoodsService();
		QueryPage item_page = goodsService.getPageListByPropType(roleEntity.getPPk(), PropType.F_MATERIAL, this.getPageNo(request));
		item_page.setURL(response, "/fStorage.do?cmd=wMList");
		request.setAttribute("item_page", item_page);
		return mapping.findForward("wrap_material_list");
	}
	/**
	 * 输入贡献材料的数量
	 */
	public ActionForward inputMNum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("pg_pk", request.getParameter("pg_pk"));
		return mapping.findForward("input_material_num");
	}
	/**
	 * 贡献
	 */
	public ActionForward contribute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return super.dispath(request, response, "/faction.do?cmd=index");
		}
		
		String pg_pk = request.getParameter("pg_pk");
		String num = request.getParameter("num");
		String hint = ValidateService.validateNonZeroNegativeIntegers(num);
		if( hint!=null )
		{
			this.setError(request, hint);
			request.setAttribute("pg_pk", request.getParameter("pg_pk"));
			return mapping.findForward("input_material_num");
		}
		
		GoodsService goodsService = new GoodsService();
		PlayerPropGroupVO material = goodsService.getGoodsGroupByPgPk(Integer.parseInt(pg_pk));
		
		hint = faction.getFStorage().contribute(roleEntity, material, Integer.parseInt(num));
		if( hint!=null )
		{
			this.setError(request, hint);
			request.setAttribute("pg_pk", request.getParameter("pg_pk"));
			return mapping.findForward("input_material_num");
		}
		
		return this.wMList(mapping, form, request, response);
	}
	/**
	 * 全部贡献
	 */
	public ActionForward contributeAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pg_pk = request.getParameter("pg_pk");
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return super.dispath(request, response, "/faction.do?cmd=index");
		}
		
		GoodsService goodsService = new GoodsService();
		PlayerPropGroupVO material = goodsService.getGoodsGroupByPgPk(Integer.parseInt(pg_pk));
		
		String hint = faction.getFStorage().contributeAll(roleEntity, material);
		if( hint!=null )
		{
			this.setError(request, hint);
			request.setAttribute("pg_pk", request.getParameter("pg_pk"));
			return mapping.findForward("input_material_num");
		}
		
		return this.wMList(mapping, form, request, response);
	}
}
