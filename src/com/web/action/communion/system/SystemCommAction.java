package com.web.action.communion.system;
 
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
 
import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;
import com.pm.service.systemInfo.SystemInfoService;
import com.pm.vo.sysInfo.SystemInfoVO;

/**
 * @author 侯浩军 聊天
 */
public class SystemCommAction extends DispatchAction
{
	/**
	 * 系统聊天 
	 * */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{   
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		SystemInfoService systemInfo = new SystemInfoService();
		List<SystemInfoVO> sysInfolist = systemInfo.getSystemInfoByPPkTime(roleInfo.getBasicInfo().getPPk()+"",request);
		
		if(sysInfolist != null){
			request.setAttribute("list",sysInfolist); 
		} 
		return mapping.findForward("systemcomm"); 
	} 
	
}
