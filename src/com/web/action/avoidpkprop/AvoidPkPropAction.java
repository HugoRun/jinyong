package com.web.action.avoidpkprop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;
import com.web.service.avoidpkprop.AvoidPkPropService;

/**
 * @author ��ƾ� ��PK����
 */
public class AvoidPkPropAction extends DispatchAction
{
	/**
	 * ��PK����
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		//UserTempBean userTempBean = (UserTempBean) request.getSession().getAttribute("userTempBean");
		
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		AvoidPkPropService avoidPkPropService = new AvoidPkPropService();
		int times = avoidPkPropService.getAvoidPkProp(roleInfo.getBasicInfo().getPPk());
		if(times > 0){
			String hint = "���ı���ʱ�仹ʣ��"+times+"����<br/>��ȷ�����PK����״̬<br/>";
			String type = "1";
			request.setAttribute("hint", hint);
			request.setAttribute("type", type);
		}else{
			String hint = "����PK����ʱ��Ϊ"+times+"���ӣ�����Ҫ���";
			String type = "2";
			request.setAttribute("hint", hint);
			request.setAttribute("type", type);
		} 
		return mapping.findForward("avoidpkproppage");
	}
	/**
	 * ��PK����
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		//UserTempBean userTempBean = (UserTempBean) request.getSession().getAttribute("userTempBean");
		
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		AvoidPkPropService avoidPkPropService = new AvoidPkPropService();
		avoidPkPropService.deleteAvoidPkProp(roleInfo.getBasicInfo().getPPk());
		String hint = "��ȡ����PK����״̬";
		String type = "2";
		request.setAttribute("hint", hint);
		request.setAttribute("type", type);
		return mapping.findForward("avoidpkproppage");
	}
}
