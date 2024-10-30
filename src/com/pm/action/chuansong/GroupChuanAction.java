/**
 * 
 */
package com.pm.action.chuansong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.model.group.GroupModel;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.room.RoomService;
import com.pm.service.chuansong.SuiBianChuanService;

/**
 * @author zhangjj 
 *
 */
public class GroupChuanAction extends DispatchAction
{

	Logger logger = Logger.getLogger("log.action");

	/**
	 * 
	 * ��ö�Ա����
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
		
		String w_type = (String)request.getAttribute("w_type");
		String pg_pk = (String)request.getAttribute("pg_pk");
		
		GroupModel group_info = roleInfo.getStateInfo().getGroupInfo();
		
		if ( group_info==null ) {
			String hint = "�㻹û�ж��飡";
			request.setAttribute("hint", hint);
			request.setAttribute("w_type", w_type);
			request.setAttribute("return_type", "2");	//1Ϊ���ض��飬2δ������Ʒ��
			return mapping.findForward("hint");
		}
		
		RoomService roomService = new RoomService();
		String carry_hint = roomService.isCarryedOut(Integer.parseInt(roleInfo.getBasicInfo().getSceneId()));
		if ( carry_hint != null && !carry_hint.equals("")) {
			// �������ʹ�ã��������ԭ��
			request.setAttribute("hint", carry_hint);
			request.setAttribute("pg_pk", pg_pk);
			request.setAttribute("w_type", w_type);
			return mapping.findForward("hint");			
		}
		
		SuiBianChuanService suiBianChuanService = new SuiBianChuanService();
		String hint = suiBianChuanService.checkIsUse(roleInfo, pg_pk);
		if ( hint != null && !hint.equals("")) {
			// �������ʹ�ã��������ԭ��
			request.setAttribute("hint", hint);
			request.setAttribute("pg_pk", pg_pk);
			request.setAttribute("w_type", w_type);
			request.setAttribute("return_type", "1");
			return mapping.findForward("hint");			
		}
		
		request.setAttribute("group_info", group_info);
				
		return mapping.findForward("list");
	}
	
	/**
	 * 
	 * ��ʼ���д���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		
		String w_type = (String)request.getParameter("w_type");
		String pg_pk = (String)request.getParameter("pg_pk");
		String friend_pk = (String)request.getParameter("friend_pk");
		
		
		SuiBianChuanService suiBianChuanService = new SuiBianChuanService();
		String hint = suiBianChuanService.checkIsUse(roleInfo, pg_pk);
		if ( hint != null && !hint.equals("")) {
			// �������ʹ�ã��������ԭ��
			request.setAttribute("hint", hint);
			request.setAttribute("pg_pk", pg_pk);
			request.setAttribute("w_type", w_type);
			request.setAttribute("return_type", "1");
			return mapping.findForward("hint");			
		}
		
		RoomService roomService = new RoomService();
		String carry_hint = roomService.isCarryedOut(Integer.parseInt(roleInfo.getBasicInfo().getSceneId()));
		if ( carry_hint != null && !carry_hint.equals("")) {
			// �������ʹ�ã��������ԭ��
			request.setAttribute("hint", carry_hint);
			request.setAttribute("pg_pk", pg_pk);
			request.setAttribute("w_type", w_type);
			return mapping.findForward("hint");			
		}
		
		String hint2 = suiBianChuanService.useGroupChuan(roleInfo,pg_pk,friend_pk);
		
		if (hint2 != null && !hint2.equals("")) {
			// �������ʹ�ã��������ԭ��
			request.setAttribute("hint", hint2);
			request.setAttribute("pg_pk", pg_pk);
			request.setAttribute("w_type", w_type);
			request.setAttribute("return_type", "2");
			return mapping.findForward("hint");		
		}
		
		request.setAttribute("hint", "��������һ�Ŷ��Ѵ��ͷ�!");
		
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
