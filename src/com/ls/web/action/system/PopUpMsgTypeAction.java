/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.ls.web.action.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.system.UMsgService;

/** 
 * MyEclipse Struts
 * Creation date: 03-09-2009
 * 
 * XDoclet definition:
 * @struts.action parameter="cmd" validate="true"
 * @struts.action-forward name="result" path="/jsp/system/message.jsp"
 */
public class PopUpMsgTypeAction extends DispatchAction {
	/*
	 * Generated Methods
	 */

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		UMessageInfoVO uMsgInfo = (UMessageInfoVO)request.getAttribute("uMsgInfo");
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		UMessageInfoVO result = null; 
		UMsgService uMsgService = new UMsgService(); 
		result = uMsgService.processMsg(uMsgInfo, roleInfo,request,response);
		if(result.equals(PopUpMsgType.MESSAGE_SWAP)){//����ǽ������� ��ô��ת������ҳ��
			
		}
		request.setAttribute("result", result.getResult());
		return mapping.findForward("result");
	}
}