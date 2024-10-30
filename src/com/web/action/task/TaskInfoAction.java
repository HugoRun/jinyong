package com.web.action.task;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.dao.info.map.PartMapDAO;
import com.ben.dao.task.UTaskDAO;
import com.ben.vo.task.TaskVO;
import com.ben.vo.task.UTaskVO;
import com.ls.ben.cache.staticcache.menu.MenuCache;
import com.ls.ben.cache.staticcache.task.TaskCache;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.MapType;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.action.ActionBase;
import com.ls.web.service.log.LogService;
import com.ls.web.service.menu.MenuService;
import com.ls.web.service.mounts.MountsService;
import com.ls.web.service.room.RoomService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.web.service.taskpage.TaskPageService;

/**
 * @author 侯浩军 任务信息
 */
public class TaskInfoAction extends ActionBase
{
	Logger logger = Logger.getLogger("log.action");
	
	/**
	 * 任务列表
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String hint = (String)request.getAttribute("hint");
		RoleEntity  roleInfo = this.getRoleEntity(request);
		List list = roleInfo.getTaskInfo().getCurTaskList().getCurTaskList();
		request.setAttribute("list", list);
		request.setAttribute("hint", hint);
		return mapping.findForward("taskinfo");
	}	

	/**
	 * 查看任务详细信息
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String tId = request.getParameter("tId");
		String tPk = request.getParameter("tPk");
		request.setAttribute("tId", tId);
		request.setAttribute("tPk", tPk);
		return mapping.findForward("taskinfoview"); 
	}

	/**
	 * 移交任务
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = this.getRoleEntity(request);
		
		try
		{
		// 获取任务ID
		String npcID = request.getParameter("npcID");
		MenuService menuService = new MenuService();
		OperateMenuVO menu = menuService.getMenuById(Integer.parseInt(npcID));
		String hint = null;
		//返回任务的等级
		MountsService ms=new MountsService();
		hint=ms.useMountsByTask(roleEntity, menu.getMenuMap()+"");
		roleEntity.getBasicInfo().updateSceneId(menu.getMenuMap()+"");
		request.getRequestDispatcher("/pubaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response); 
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;

	}
	
	/**
	 * 移交任务
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = this.getRoleEntity(request);
		try
		{
	    String hint = null;
		String sceneid1 = roleEntity.getBasicInfo().getSceneId()+"";  
		//取得距当前时间最短的任务
		UTaskDAO dao = new UTaskDAO();
		UTaskVO uTaskVO =(UTaskVO) dao.getUTaskPklimit(roleEntity.getBasicInfo().getPPk() + "");
		if(uTaskVO==null){
			hint = "您没有任务，暂时不能传送"; 
			request.getRequestDispatcher("/pubaction.do?mapid="+sceneid1+"&hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response); 
			return null;
		} else{
		// 获取任务ID
		String npcID = uTaskVO.getTXrwnpcId()+"";
		MenuService menuService = new MenuService();
		OperateMenuVO menu = menuService.getMenuById(Integer.parseInt(npcID));
		RoomService roomService = new RoomService();
		int map_type = roomService.getMapType(Integer.valueOf(sceneid1));
		
		//如果任务在监狱, 
		if(map_type == MapType.PRISON) {
			hint = "对不起,仍在监狱,好好改造!";
			request.getRequestDispatcher("/pubaction.do?mapid="+sceneid1+"&hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response); 
			return null;
		}
		MountsService ms=new MountsService();
		hint=ms.useMountsByTask(roleEntity, menu.getMenuMap()+"");
		//TODO 页面返回转发
		request.getRequestDispatcher("/pubaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response); 
		
		}
		}
		catch (Exception e)
		{
		}
		return null; 
	}
	
	/**
	 * 查找当前等级任务
	 */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = this.getRoleEntity(request);
		String page_no_str = request.getParameter("page_no");
		int page_no = 0;
		if( page_no_str==null ) {
			page_no = 1;
		}else {
			page_no = Integer.parseInt(page_no_str);
		}
		logger.info("请求的是第"+page_no_str+"页");
		request.setAttribute("page_no", page_no);
		
		TaskPageService taskService = new TaskPageService();
		List<TaskVO> list = taskService.getCurrentageTaskByGrade(roleEntity);
		request.setAttribute("list", list);
		
		return mapping.findForward("nowtaskinfoview");

	}
	
	/**
	 * 查找当前等级任务,并传送
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{		
		RoleEntity roleEntity = this.getRoleEntity(request);
				
		String hint = "";
		// 获取任务ID
		String taskid = request.getParameter("t_id"); 
		if(taskid == null || taskid.equals("") || taskid.equals("null")){
			taskid = (String)request.getAttribute("t_id");
		}
		TaskVO taskVOCache = TaskCache.getById(taskid);
		
		MenuService menuService = new MenuService();
		MenuCache menuCache = new MenuCache();
		
		UTaskDAO uTaskDAO = new UTaskDAO();
		
		// 如果这条任务在身上，且是已被放弃的，就取任务的下一步npc所在地，
		// 否则就取 任务的开始npc所在地
		String t_zu = taskVOCache.getTZu();
		OperateMenuVO menuvo = null;
		if ( uTaskDAO.getUserHasTask(roleEntity.getBasicInfo().getPPk(),t_zu)) {
			menuvo = menuCache.getById(taskVOCache.getTXrwnpcId()+"");
		} else {
			menuvo = menuService.getNpcIdByTaskId(taskid);
		}
		
		
		TaskPageService taskPageService = new TaskPageService();
		taskPageService.getAmendMenuMap(menuvo,taskid);
		logger.info("menuvo.getId()="+menuvo.getId()+" ,menuvo.getMenuMap()="+menuvo.getMenuMap());
		
		try{
			if(menuvo == null || menuvo.getMenuMap() == 0){
				hint = "此任务无法接受！";
				request.getRequestDispatcher("/pubaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response); 
				return null;
			}
			MountsService ms =new MountsService();
			hint=ms.useMountsByTask(roleEntity, menuvo.getMenuMap()+"");
			logger.info("新的mapid="+roleEntity.getBasicInfo().getSceneId());
			request.getRequestDispatcher("/pubaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response); 
		}catch( Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 查找当前等级任务,并传送
	 */
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		try{
		RoleEntity roleInfo = this.getRoleEntity(request);
		String mapid = request.getParameter("mapid");  
		String old_scene_id = roleInfo.getBasicInfo().getSceneId() ;  
		String hint = null;
		RoomService roomService = new RoomService();
		int map_type = roomService.getMapType(Integer.valueOf(old_scene_id));
		//如果任务在监狱, 
		if(map_type == MapType.PRISON) {
			hint = "对不起,仍在监狱,好好改造!"; 
			request.getRequestDispatcher("/pubaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response);
			return null;
		}
		if(map_type== MapType.COMPASS){
			hint = "对不起，迷宫中不能使用该功能！";
			request.getRequestDispatcher("/pubaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response);
			return null;
		}
		//判断是否可以传出
		hint  = roomService.isCarryedOut(Integer.parseInt(old_scene_id));
		if(hint != null){
			request.getRequestDispatcher("/pubaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response);
			return null;
		}
		//判断是否可以传入
		hint  = roomService.isCarryedIn(Integer.parseInt(mapid));
		if(hint != null){
			request.getRequestDispatcher("/pubaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response);
			return null;
		}
		MountsService ms=new MountsService();
		hint=ms.useMountsByTask(roleInfo, mapid);
		request.getRequestDispatcher("/pubaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response);
		return null;
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 任务放弃（无该功能）
	 *//*
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String upTaskId = request.getParameter("upTaskId");
		String tPk = request.getParameter("t_pk");
		TaskCache taskCache = new TaskCache();
		TaskVO task =(TaskVO) taskCache.getById(upTaskId); 
		CurTaskInfo curTaskInfo = (CurTaskInfo)roleInfo.getTaskInfo().getCurTaskList().getById(tPk);
		TaskVO taskVO =(TaskVO) taskCache.getById(curTaskInfo.getTId()+""); 
		//首先取得改任务是否可以放弃
		String hint = null;
		if(taskVO.getTAbandon() == 0){ 
		//放弃任务的第一条的时候
		if(Integer.parseInt(curTaskInfo.getTPx()) == 1 && Integer.parseInt(upTaskId) == task.getTId()){
			roleInfo.getTaskInfo().dropTask(curTaskInfo);
			//删除任务所给的物品
			TaskService taskService = new TaskService();
			taskService.removeTaskStartBestowGoods(roleInfo.getBasicInfo().getPPk(), curTaskInfo.getTId()); 
		}else{ 
			//curTaskInfo.updateGiveUp(1);
			switch (task.getTType())
			{
			case TaskType.DIALOG:
				TaskService taskDialogService = new TaskService();
				taskDialogService.giveUpHuaService(roleInfo,task,curTaskInfo.getTId()); 
				break;
			case TaskType.FIND:
				TaskXunWuService taskXunWuService = new TaskXunWuService();
				taskXunWuService.giveUpXunWuService(roleInfo,task,curTaskInfo.getTId()); 
				break;
			case TaskType.CARRY:
				TaskXieDaiService taskXieDaiService = new TaskXieDaiService();
				taskXieDaiService.giveUpXieDaiService(roleInfo,task,curTaskInfo.getTId()); 
				break;
			case TaskType.KILL:
				TaskShaGuaiService taskShaGuaiService = new TaskShaGuaiService();
				taskShaGuaiService.giveUpShaGuaiService(roleInfo,task,curTaskInfo.getTId());
				break;
			case TaskType.COMPLEX:
				TaskFuHeService taskFuHeService = new TaskFuHeService();
				taskFuHeService.giveUpFuHeService(roleInfo,task,curTaskInfo.getTId());
				break;
			}
		}
		hint = "您放弃了该条任务";
		}else{
		hint = "该条任务不能放弃";	
		}
		//CurTaskInfo curTaskInfo = (CurTaskInfo)roleInfo.getTaskInfo().getCurTaskList().getById(giveUp);
		//取出上一条任务的ID
		//int GiveUp = curTaskInfo.getTGiveUp();
		//curTaskInfo.updateGiveUp();
		request.setAttribute("hint", hint);
		return n1(mapping,form,request,response);
	}
	*/
	/**
	 * 查看任务详细信息
	 */
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		int mapType = roleInfo.getBasicInfo().getSceneInfo().getMap().getMapType();
		if(mapType== MapType.COMPASS){
			request.setAttribute("message", "对不起，迷宫中不能使用该功能！");
			return mapping.findForward("langjun");
		}
		if(mapType==MapType.ACTIVE_LEITAI||mapType==MapType.LEITAI_CHALLENGE){
			request.setAttribute("message", "对不起，擂台中不能使用该功能！");
			return mapping.findForward("langjun");
		}
		String tId = request.getParameter("tId");
		
		TaskVO taskVO = TaskCache.getById(tId);
		if(Integer.parseInt(taskVO.getTZuxl()) == 1){
			request.setAttribute("t_id", tId);
			return n6(mapping, form, request, response);
		}
		request.setAttribute("tId", tId);
		return mapping.findForward("xunzhaorenwuview"); 
	}
	
	/**
	 * 下一步任务传送
	 */
	public ActionForward n10(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		com.web.service.task.TaskPageService taskPageService = new com.web.service.task.TaskPageService();
		String taskKeyValue = request.getParameter("taskKeyValue");
		if(taskKeyValue != null && !taskKeyValue.equals("") && !taskKeyValue.equals("null"))
		{
			String tId = request.getParameter("tId");
			String taskRemit = taskPageService.taskRemit(roleInfo, taskKeyValue, tId);
			request.setAttribute("hint", taskRemit);
		}
		return this.returnScene(request, response);
	}
}
