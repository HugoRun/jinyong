package com.pm.action.chuansong;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.room.RoomService;
import com.pm.dao.chuansong.SuiBianDao;
import com.pm.service.chuansong.SuiBianChuanService;
import com.pm.vo.chuansong.SuiBianChuanVO;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

public class SuiBianChuanAction extends DispatchAction
{

	Logger logger = Logger.getLogger("log.action");

	/**
	 * 
	 * 获得传送符可以 传送到的地方类型一览
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	// 
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());		
		int p_pk = roleInfo.getBasicInfo().getPPk();
		
		
		String w_type = (String)request.getAttribute("w_type");
		String pg_pk = (String)request.getAttribute("pg_pk");
		String goods_id = (String)request.getAttribute("goods_id");
		String goods_type = (String)request.getAttribute("goods_type");
		
		SuiBianChuanService suiBianChuanService = new SuiBianChuanService();
		String result = suiBianChuanService.checkIsUse(roleInfo,pg_pk);
		if ( result != null && !result.equals("")) {
			// 如果不能使用，告诉玩家原因
			request.setAttribute("hint", result);
			request.setAttribute("pg_pk", pg_pk);
			request.setAttribute("w_type", w_type);
			return mapping.findForward("hint");			
		}
		
		RoomService roomService = new RoomService();
		String carry_hint = roomService.isCarryedOut(Integer.parseInt(roleInfo.getBasicInfo().getSceneId()));
		if ( carry_hint != null && !carry_hint.equals("")) {
			// 如果不能使用，告诉玩家原因
			request.setAttribute("hint", carry_hint);
			request.setAttribute("pg_pk", pg_pk);
			request.setAttribute("w_type", w_type);
			return mapping.findForward("hint");			
		}
		
		List<SuiBianChuanVO> list = suiBianChuanService.getChuanSongType(roleInfo,pg_pk,goods_id,goods_type);
		
		request.setAttribute("list", list);
		request.setAttribute("pg_pk", pg_pk);
		request.setAttribute("w_type", w_type);
		return mapping.findForward("list");
		
	}
	
	

	/**
	 * 
	 * 获得传送符可以 传送到的地方类型一览
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	// 
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());		
		int p_pk = roleInfo.getBasicInfo().getPPk();
		
		
		String w_type = request.getParameter("w_type");
		String type = request.getParameter("type");
		String pg_pk = (String)request.getParameter("pg_pk");
		
		request.setAttribute("type", type);
		SuiBianChuanService suiBianChuanService = new SuiBianChuanService();
		String result = suiBianChuanService.checkIsUse(roleInfo,pg_pk);
		if ( result != null && !result.equals("")) {
			// 如果不能使用，告诉玩家原因
			request.setAttribute("hint", result);
			request.setAttribute("w_type", w_type);
			request.setAttribute("pg_pk", pg_pk);
			return mapping.findForward("hint");			
		}
		
		
		//List<SuiBianChuanVO> list = suiBianChuanService.getSuiBianByTypeId(type);
		
		//request.setAttribute("list", list);
		//request.setAttribute("pg_pk", pg_pk);
		//request.setAttribute("w_type", w_type);
		return mapping.findForward("scene_list");
		
	}
	
	/**
	 * 开始进行传送
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	// 
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());		
				
		String w_type = request.getParameter("w_type");
		String carryId = request.getParameter("carryId");
		String pg_pk = (String)request.getParameter("pg_pk");
		
		SuiBianChuanService suiBianChuanService = new SuiBianChuanService();
		String result = suiBianChuanService.checkIsUse(roleInfo,pg_pk);
		if ( result != null && !result.equals("")) {
			// 如果不能使用，告诉玩家原因
			request.setAttribute("hint", result);
			request.setAttribute("w_type", w_type);
			request.setAttribute("pg_pk", pg_pk);
			return mapping.findForward("hint");			
		}
		
		RoomService roomService = new RoomService();
		String carry_hint = roomService.isCarryedOut(Integer.parseInt(roleInfo.getBasicInfo().getSceneId()));
		if ( carry_hint != null && !carry_hint.equals("")) {
			// 如果不能使用，告诉玩家原因
			request.setAttribute("hint", carry_hint);
			request.setAttribute("pg_pk", pg_pk);
			request.setAttribute("w_type", w_type);
			return mapping.findForward("hint");			
		}
		
		String hint = suiBianChuanService.useSuiBianChuan(roleInfo, pg_pk, carryId);
		if ( hint != null && !hint.equals("")) {
			// 如果不能使用，告诉玩家原因
			request.setAttribute("hint", hint);
			request.setAttribute("w_type", w_type);
			request.setAttribute("pg_pk", pg_pk);
			return mapping.findForward("hint");			
		}
		
		
		
		hint = "传送消耗一张传送符!";
		request.setAttribute("hint", hint);
		
		try
		{
			request.getRequestDispatcher("/scene.do?hint="+hint).forward(request, response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

}
