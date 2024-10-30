package com.web.service.task;

import com.ben.dao.task.TaskDAO;
import com.ben.dao.task.UTaskDAO;
import com.ben.vo.task.TaskVO;
import com.ben.vo.task.UTaskVO;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.log.DataErrorLog;
import com.web.service.TaskFuHeService;
import com.web.service.TaskService;
import com.web.service.TaskShaGuaiService;
import com.web.service.TaskXieDaiService;
import com.web.service.TaskXunWuService;

/**
 * 功能:9级任务跳转直接让选择门派
 * 
 * @author 侯浩军 11:13:44 AM 
 */
public class TaskVisitLeadService
{

	/**
	 * 接受一条任务
	 * @param roleEntity
	 * @param t_zu			任务组
	 * @return				任务提示
	 */
	public String acceptTask(RoleEntity roleEntity,String t_zu)
	{
		String hint = null;
		TaskDAO taskDAO = new TaskDAO();
		TaskService taskService = new TaskService();
		TaskShaGuaiService taskShaGuaiService = new TaskShaGuaiService();
		TaskXieDaiService taskXieDaiService = new TaskXieDaiService();
		TaskXunWuService taskXunWuService = new TaskXunWuService();
		TaskFuHeService taskFuHeService = new TaskFuHeService();
		
		//得到该任务的第一条任务
		int t_zuxl = 1;
		TaskVO taskVO = taskDAO.getTaskByZuAndXulie(t_zu, t_zuxl);
		
		if(taskVO!=null)
		{
			switch(taskVO.getTType())
			{
				// 对话任务
				case 1:hint = taskService.getDuiHuaService(roleEntity,taskVO);break;
				// 2代表杀怪任务
				case 2:hint = taskShaGuaiService.getShaGuaiService(roleEntity,taskVO);break;
				// 3代表携带类任务
				case 3:hint = taskXieDaiService.getXieDaiService(roleEntity,taskVO);break;
				// 4 寻物类
				case 4:hint = taskXunWuService.getXunWuService(roleEntity,taskVO);break;
				// 复合类
				case 5:hint = taskFuHeService.getFuHeService(roleEntity,taskVO);break;
			}
			
			if( hint!=null )
			{
				//可以接受该任务
				hint = taskVO.getTTishi();
			}
		}
		else
		{
			DataErrorLog.debugData("TaskVisitLeadService.acceptTask:无该任务，t_zu="+t_zu);
		}
		return hint;
	} 
	/**
	 * 返回任务的ID
	 * */
	public String getTaskPk(String title, int t_zuxl, int pPk,
			String createTime, String pName, int uPk){ 
		String hint = null;
		TaskDAO taskDAO = new TaskDAO(); 
		TaskVO taskVO = taskDAO.getTaskByZuAndXulie(title, t_zuxl);
		hint = taskVO.getTId()+"";
		return hint;
	}
	/**
	 * 返回角色任务的ID
	 * */
	public String getPartTaskPk(int pPk,int tid){ 
		String hint = null;
		UTaskDAO uTaskDAO = new UTaskDAO(); 
		UTaskVO uTaskVO = uTaskDAO.getPoint(pPk+"", tid);
		hint = uTaskVO.getTPk()+"";
		return hint;
	}
}
