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
 * @author ��ƾ� ������Ϣ
 */
public class TaskInfoAction extends ActionBase
{
	Logger logger = Logger.getLogger("log.action");
	
	/**
	 * �����б�
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
	 * �鿴������ϸ��Ϣ
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
	 * �ƽ�����
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = this.getRoleEntity(request);
		
		try
		{
		// ��ȡ����ID
		String npcID = request.getParameter("npcID");
		MenuService menuService = new MenuService();
		OperateMenuVO menu = menuService.getMenuById(Integer.parseInt(npcID));
		String hint = null;
		//��������ĵȼ�
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
	 * �ƽ�����
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = this.getRoleEntity(request);
		try
		{
	    String hint = null;
		String sceneid1 = roleEntity.getBasicInfo().getSceneId()+"";  
		//ȡ�þ൱ǰʱ����̵�����
		UTaskDAO dao = new UTaskDAO();
		UTaskVO uTaskVO =(UTaskVO) dao.getUTaskPklimit(roleEntity.getBasicInfo().getPPk() + "");
		if(uTaskVO==null){
			hint = "��û��������ʱ���ܴ���"; 
			request.getRequestDispatcher("/pubaction.do?mapid="+sceneid1+"&hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response); 
			return null;
		} else{
		// ��ȡ����ID
		String npcID = uTaskVO.getTXrwnpcId()+"";
		MenuService menuService = new MenuService();
		OperateMenuVO menu = menuService.getMenuById(Integer.parseInt(npcID));
		RoomService roomService = new RoomService();
		int map_type = roomService.getMapType(Integer.valueOf(sceneid1));
		
		//��������ڼ���, 
		if(map_type == MapType.PRISON) {
			hint = "�Բ���,���ڼ���,�úø���!";
			request.getRequestDispatcher("/pubaction.do?mapid="+sceneid1+"&hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response); 
			return null;
		}
		MountsService ms=new MountsService();
		hint=ms.useMountsByTask(roleEntity, menu.getMenuMap()+"");
		//TODO ҳ�淵��ת��
		request.getRequestDispatcher("/pubaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response); 
		
		}
		}
		catch (Exception e)
		{
		}
		return null; 
	}
	
	/**
	 * ���ҵ�ǰ�ȼ�����
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
		logger.info("������ǵ�"+page_no_str+"ҳ");
		request.setAttribute("page_no", page_no);
		
		TaskPageService taskService = new TaskPageService();
		List<TaskVO> list = taskService.getCurrentageTaskByGrade(roleEntity);
		request.setAttribute("list", list);
		
		return mapping.findForward("nowtaskinfoview");

	}
	
	/**
	 * ���ҵ�ǰ�ȼ�����,������
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{		
		RoleEntity roleEntity = this.getRoleEntity(request);
				
		String hint = "";
		// ��ȡ����ID
		String taskid = request.getParameter("t_id"); 
		if(taskid == null || taskid.equals("") || taskid.equals("null")){
			taskid = (String)request.getAttribute("t_id");
		}
		TaskVO taskVOCache = TaskCache.getById(taskid);
		
		MenuService menuService = new MenuService();
		MenuCache menuCache = new MenuCache();
		
		UTaskDAO uTaskDAO = new UTaskDAO();
		
		// ����������������ϣ������ѱ������ģ���ȡ�������һ��npc���ڵأ�
		// �����ȡ ����Ŀ�ʼnpc���ڵ�
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
				hint = "�������޷����ܣ�";
				request.getRequestDispatcher("/pubaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response); 
				return null;
			}
			MountsService ms =new MountsService();
			hint=ms.useMountsByTask(roleEntity, menuvo.getMenuMap()+"");
			logger.info("�µ�mapid="+roleEntity.getBasicInfo().getSceneId());
			request.getRequestDispatcher("/pubaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response); 
		}catch( Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ���ҵ�ǰ�ȼ�����,������
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
		//��������ڼ���, 
		if(map_type == MapType.PRISON) {
			hint = "�Բ���,���ڼ���,�úø���!"; 
			request.getRequestDispatcher("/pubaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response);
			return null;
		}
		if(map_type== MapType.COMPASS){
			hint = "�Բ����Թ��в���ʹ�øù��ܣ�";
			request.getRequestDispatcher("/pubaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response);
			return null;
		}
		//�ж��Ƿ���Դ���
		hint  = roomService.isCarryedOut(Integer.parseInt(old_scene_id));
		if(hint != null){
			request.getRequestDispatcher("/pubaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response);
			return null;
		}
		//�ж��Ƿ���Դ���
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
	 * ����������޸ù��ܣ�
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
		//����ȡ�ø������Ƿ���Է���
		String hint = null;
		if(taskVO.getTAbandon() == 0){ 
		//��������ĵ�һ����ʱ��
		if(Integer.parseInt(curTaskInfo.getTPx()) == 1 && Integer.parseInt(upTaskId) == task.getTId()){
			roleInfo.getTaskInfo().dropTask(curTaskInfo);
			//ɾ��������������Ʒ
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
		hint = "�������˸�������";
		}else{
		hint = "���������ܷ���";	
		}
		//CurTaskInfo curTaskInfo = (CurTaskInfo)roleInfo.getTaskInfo().getCurTaskList().getById(giveUp);
		//ȡ����һ�������ID
		//int GiveUp = curTaskInfo.getTGiveUp();
		//curTaskInfo.updateGiveUp();
		request.setAttribute("hint", hint);
		return n1(mapping,form,request,response);
	}
	*/
	/**
	 * �鿴������ϸ��Ϣ
	 */
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		int mapType = roleInfo.getBasicInfo().getSceneInfo().getMap().getMapType();
		if(mapType== MapType.COMPASS){
			request.setAttribute("message", "�Բ����Թ��в���ʹ�øù��ܣ�");
			return mapping.findForward("langjun");
		}
		if(mapType==MapType.ACTIVE_LEITAI||mapType==MapType.LEITAI_CHALLENGE){
			request.setAttribute("message", "�Բ�����̨�в���ʹ�øù��ܣ�");
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
	 * ��һ��������
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
