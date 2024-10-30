/**
 * 
 */
package com.web.action.pet;
 
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;
import com.web.service.petservice.PetService;
 

/**
 *
 *功能:遗弃宠物
 * @author 侯浩军
 * 
 */
public class PetChuckAction extends Action{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{   
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String pPk = roleInfo.getBasicInfo().getPPk()+"";
		String petPk = request.getParameter("petPk"); 
		String mapid = request.getParameter("mapid"); 
		String jumpterm = request.getParameter("jumpterm"); 
		
		PetService petService = new PetService();
		petService.getPetInfoDelete(petPk,pPk);
		
				 
		request.setAttribute("mapid", mapid);
		request.setAttribute("jumpterm", jumpterm);
		return mapping.findForward("success");
	}
}
