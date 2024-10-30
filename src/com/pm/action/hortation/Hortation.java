/**
 * 
 */
package com.pm.action.hortation;

import java.util.ArrayList;
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
import com.pm.dao.horta.HortaDao;
import com.pm.service.horta.HortaService;
import com.pm.vo.horta.HortaVO;

/**
 * @author Administrator
 *
 */
public class Hortation extends DispatchAction
{
	
	Logger logger = Logger.getLogger("log.action");
	
	// 显示所有的奖励类型
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleService roleService = new RoleService();
		RoleEntity roleEntity = roleService.getRoleInfoBySession(request.getSession());
		
		HortaService hortaService = new HortaService();
		List<HortaVO> horList = hortaService.getMainList();
		
		hortaService.checkHasNew(horList,roleEntity);
		
		request.setAttribute("main_list", horList);
		
		return mapping.findForward("main_list");
	}
	
	
	// 显示一个奖励类型所有的奖励内容
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleService roleService = new RoleService();
		RoleEntity roleEntity = roleService.getRoleInfoBySession(request.getSession());
		
		String main_type = request.getParameter("main_type");
		request.setAttribute("main_type", main_type);
		
		HortaService hortaService = new HortaService();
		List<HortaVO> horList = hortaService.getHortaSonList(main_type);
		
		List<HortaVO> ableList = new ArrayList<HortaVO>();
		List<HortaVO> unAbleList = new ArrayList<HortaVO>();
		
		String hint = hortaService.checkAbleList(horList,ableList,unAbleList,roleEntity);
		if ( hint != null && !hint.equals("") && !hint.equals("null")) {
			request.setAttribute("hint", hint);
			return mapping.findForward("main_list");			
		}
		
		request.setAttribute("ableList", ableList);
		request.setAttribute("unAbleList", unAbleList);
		
		return mapping.findForward("son_list");
	}
	
	
	// 领取奖励
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleService roleService = new RoleService();
		RoleEntity roleEntity = roleService.getRoleInfoBySession(request.getSession());
		
		String hor_id = request.getParameter("hor_id");
		String main_type = request.getParameter("main_type");
		request.setAttribute("main_type", main_type);
		
		HortaService hortaService = new HortaService();
		String hint = hortaService.checkAbleHorta(hor_id,roleEntity);
		if ( hint != null && !hint.equals("") && !hint.equals("null")) {
			request.setAttribute("jieguoString", hint);
			return mapping.findForward("jieguoString");			
		}
		
		String jieguoString = hortaService.giveJiangLi(hor_id,roleEntity);
		request.setAttribute("jieguoString", jieguoString);
		
		return mapping.findForward("jieguoString");
	}
	
	// 显示一个奖励类型的说明
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String hor_id = request.getParameter("hor_id");
		String main_type = request.getParameter("main_type");
		String lingqu = request.getParameter("lingqu");
		request.setAttribute("main_type", main_type);
		request.setAttribute("hor_id", hor_id);
		request.setAttribute("lingqu", lingqu);
		
		HortaDao hortadao = new HortaDao();
		HortaVO hortaVO = hortadao.getHortaByHorId(hor_id);
		
		request.setAttribute("hortaVO", hortaVO);
		return mapping.findForward("displayString");
	}

}
