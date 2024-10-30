package com.web.action.systemresources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ben.vo.task.TaskVO;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.npc.NpcVO;
import com.ls.ben.vo.info.npc.NpcdropVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.web.service.systemresources.SystemResourcesService;

/**
 * @author 侯浩军 管理远登陆
 */
public class SystemResourcesAction extends DispatchAction
{ 

	/**
	 * 进入登陆页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n0(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String username = request.getParameter("username");
		String userpaw = request.getParameter("userpaw");
		SystemResourcesService systemResourcesService = new SystemResourcesService();
		String hint = systemResourcesService.login(username, userpaw);
		if(hint != null){
			request.setAttribute("hint", hint);
			return mapping.findForward("notlogin");
		}
		return this.n1(mapping, form, request, response);
	} 
	
	/**
	 * 首页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		TaskVO taskVO = (TaskVO) request.getAttribute("taskVO");
		request.setAttribute("taskVO", taskVO);
		OperateMenuVO operateMenuVO = (OperateMenuVO) request.getAttribute("operateMenuVO");
		request.setAttribute("operateMenuVO", operateMenuVO);
		SceneVO sceneVO = (SceneVO) request.getAttribute("sceneVO");
		request.setAttribute("sceneVO", sceneVO);
		PropVO propVO = (PropVO) request.getAttribute("propVO");
		request.setAttribute("propVO", propVO);
		NpcVO npcVO = (NpcVO) request.getAttribute("npcVO");
		request.setAttribute("npcVO", npcVO);
		NpcdropVO npcdropVO = (NpcdropVO) request.getAttribute("npcdropVO");
		request.setAttribute("npcdropVO", npcdropVO);
		String type = request.getParameter("type");
		if(type == null || type.equals("") || type.equals("null")){
			type = (String) request.getAttribute("type");
		}if(type == null || type.equals("") || type.equals("null")){
			type = "1";
		}
		request.setAttribute("type", type);
		return mapping.findForward("login");
	} 
	
	/**
	 * 通过任务ID查找
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String task_id = request.getParameter("task_id");
		TaskVO taskVO = SystemResourcesService.getTaskView(task_id);
		request.setAttribute("type", "1");
		request.setAttribute("taskVO", taskVO);
		return mapping.findForward("syspage");
	}
	/**
	 * 全部重载任务
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		SystemResourcesService.initTaskCache();//重新加载
		request.setAttribute("type", "1");
		return n1(mapping, form, request, response);
	} 
	/**
	 * 单条任务更改并加载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		String task_id = request.getParameter("task_id");
		TaskVO taskVO = SystemResourcesService.getTaskView(task_id);
		if(taskVO != null){
			SystemResourcesService.reloadOneTask(taskVO);
			request.setAttribute("taskVO", taskVO);
		}
		request.setAttribute("type", "1");
		return mapping.findForward("syspage");
	}
	
	/**
	 * 通过菜单ID查找
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String menu_id = request.getParameter("menu_id");
		OperateMenuVO operateMenuVO = SystemResourcesService.getMenuView(menu_id);
		request.setAttribute("operateMenuVO", operateMenuVO);
		request.setAttribute("type", "2");
		return mapping.findForward("syspage");
	}
	
	/**
	 * 全部重载菜单
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{ 
		SystemResourcesService.initMenuCache();//重新加载
		request.setAttribute("type", "2");
		return n1(mapping, form, request, response);
	} 
	/**
	 * 单条菜单更改并加载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		String menu_id = request.getParameter("menu_id");
		OperateMenuVO operateMenuVO = SystemResourcesService.getMenuView(menu_id);
		if(operateMenuVO != null){
			SystemResourcesService.reloadOneMenu(operateMenuVO);
			request.setAttribute("operateMenuVO", operateMenuVO);
			request.setAttribute("type", "2");
		}
		return mapping.findForward("syspage");
	}
	
	/**
	 * 通scene过地图ID查找
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String scene_id = request.getParameter("scene_id");
		SceneVO sceneVO = SystemResourcesService.getSceneView(scene_id);
		request.setAttribute("sceneVO", sceneVO);
		request.setAttribute("type", "3");
		return mapping.findForward("syspage");
	}
	/**
	 * 单条scene更改并加载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		String scene_id = request.getParameter("scene_id");
		SceneVO sceneVO = SystemResourcesService.getSceneView(scene_id);
		if(sceneVO != null){
			SystemResourcesService.reloadOneScene(sceneVO);
			request.setAttribute("sceneVO", sceneVO);
			request.setAttribute("type", "3");
		}
		return mapping.findForward("syspage");
	} 
	/**
	 * 通道具ID查找
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n10(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String prop_id = request.getParameter("prop_id");
		PropVO propVO = SystemResourcesService.getPropView(prop_id);
		request.setAttribute("propVO", propVO);
		request.setAttribute("type", "4");
		return mapping.findForward("syspage");
	}
	/**
	 * 全部重载道具
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n11(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		SystemResourcesService.initPropCache();//重新加载
		request.setAttribute("type", "4");
		return n1(mapping, form, request, response);
	}  
	/**
	 * 单条道具数据加载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n12(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		String prop_id = request.getParameter("prop_id");
		PropVO propVO = SystemResourcesService.getPropView(prop_id);
		if(propVO != null){
			SystemResourcesService.reloadOneProp(propVO);
			request.setAttribute("propVO", propVO);
			request.setAttribute("type", "4");
		}
		return mapping.findForward("syspage");
	} 
	/**
	 * 通NPCID查找
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n13(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String npc_id = request.getParameter("npc_id");
		NpcVO npcVO = SystemResourcesService.getNPCView(npc_id);
		request.setAttribute("npcVO", npcVO);
		request.setAttribute("type", "5");
		return mapping.findForward("syspage");
	}
	/**
	 * 全部重载NPC
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n14(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		SystemResourcesService.initNPCCache();//重新加载
		request.setAttribute("type", "5");
		return n1(mapping, form, request, response);
	} 
	/**
	 * 单条道具数据加载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n15(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		String npc_id = request.getParameter("npc_id");
		NpcVO npcVO = SystemResourcesService.getNPCView(npc_id);
		if(npcVO != null){
			SystemResourcesService.reloadOneNPC(npcVO);
			request.setAttribute("npcVO", npcVO);
			request.setAttribute("type", "5");
		}
		return mapping.findForward("syspage");
	} 
	
	/**
	 * 通NPCID查找
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n16(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String npc_drop_id = request.getParameter("npc_drop_id");
		NpcdropVO npcdropVO = SystemResourcesService.getNPCDropView(npc_drop_id);
		request.setAttribute("npcdropVO", npcdropVO);
		request.setAttribute("type", "6");
		return mapping.findForward("syspage");
	}
}