package com.pm.action.setting;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.model.user.RoleEntity;
import com.ls.model.user.SettingInfo;
import com.ls.web.service.player.RoleService;
import com.pm.service.setting.SysSettingService;

/**
 * 系统设置
 */
public class SysSettingAction extends DispatchAction {

	/**
	 * 系统设置主页
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("index");
	}
	
	/**
	 * 系统设置列表（图片设置和交易设置）
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		SettingInfo settingInfo = roleInfo.getSettingInfo();
		
		request.setAttribute("settingInfo",settingInfo);
		return mapping.findForward("pic_setting");
	}
	
	/**
	 * 修改系统设置（图片设置和交易设置）
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String type = request.getParameter("type");
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		SysSettingService sysSettingService = new SysSettingService();
		sysSettingService.updateSetting(roleInfo, Integer.parseInt(type));
		
		SettingInfo settingInfo = roleInfo.getSettingInfo();
		
		request.setAttribute("settingInfo",settingInfo);
		return mapping.findForward("pic_setting");
	}
	
	/**
	 * 系统设置页面
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("settingpage");
	}
	
	/**
	 * 聊天设置
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		SettingInfo settingInfo = roleInfo.getSettingInfo();
		
		request.setAttribute("settingInfo",settingInfo);
		return mapping.findForward("chat_setting");
	}
	/**
	 * 修改聊天开关设置
	 */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String type = request.getParameter("type");
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		SysSettingService sysSettingService = new SysSettingService();
		sysSettingService.updateSetting(roleInfo, Integer.parseInt(type));
		
		SettingInfo settingInfo = roleInfo.getSettingInfo();
		
		request.setAttribute("settingInfo",settingInfo);
		return mapping.findForward("chat_setting");
	}
	
	/**
	 * 系统设置列表（图片设置和交易设置）
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		SettingInfo settingInfo = roleInfo.getSettingInfo();
		
		request.setAttribute("settingInfo",settingInfo);
		return mapping.findForward("other_setting");
	}
	
	/**
	 * 修改系统设置（图片设置和交易设置）
	 */
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String type = request.getParameter("type");
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		SysSettingService sysSettingService = new SysSettingService();
		sysSettingService.updateSetting(roleInfo, Integer.parseInt(type));
		
		SettingInfo settingInfo = roleInfo.getSettingInfo();
		
		request.setAttribute("settingInfo",settingInfo);
		return mapping.findForward("other_setting");
	}
}
