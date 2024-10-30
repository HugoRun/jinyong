package com.ls.web.service.task;

import java.util.List;

import org.apache.log4j.Logger;

import com.ben.dao.task.TaskDAO;
import com.ben.dao.task.UTaskDAO;
import com.ben.vo.task.TaskVO;
import com.ls.ben.cache.staticcache.task.TaskCache;
import com.ls.ben.dao.task.AcceptTaskListDao;
import com.ls.ben.vo.info.effect.PropUseEffect;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.ben.vo.task.AcceptTaskListVO;
import com.ls.model.property.task.CurTaskInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.TaskType;
import com.ls.pub.util.MathUtil;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.player.RoleService;
import com.web.service.TaskFuHeService;
import com.web.service.TaskService;
import com.web.service.TaskShaGuaiService;
import com.web.service.TaskXieDaiService;
import com.web.service.TaskXunWuService;

/**
 * 功能:玩家的任务管理
 * @author 刘帅
 * Sep 19, 2008 9:06:33 AM
 */
public class TaskSubService
{
	
	Logger logger = Logger.getLogger("log.service");
	
	/**
	 * 根据id得到task信息
	 */
	public TaskVO getById( String task_id )
	{
		return TaskCache.getById(task_id);
	}
	/**
	 * 是否完成多个任务组中的任意一个任务组
	 * @param p_pk
	 * @param task_zu_str    多个任务组字符串 形式如:'zhuxian','yindao'
	 * @return
	 */
	public boolean isCompletedOneOfMany( int p_pk,String task_zu_str)
	{
		if(task_zu_str==null)
		{
			return true;
		}
		if(task_zu_str.equals(""))
		{
			return true;
		}
		UTaskDAO uTaskDAO = new UTaskDAO();
		return uTaskDAO.isCompletedOneOfMany(p_pk, task_zu_str);
	}
	
	/**
	 * 掉落时的，当前有哪些任务
	 * @param p_pk
	 * @return
	 */
	public String getDropTaskConditions( int p_pk )
	{
		UTaskDAO uTaskDAO = new UTaskDAO();
		return uTaskDAO.getMenuId(p_pk);
	}
	
	
	/**
	 * 根据任务id接受任务
	 * @param p_pk
	 * @param task_id
	 */
	public void acceptTask(RoleEntity roleEntity,int task_id )
	{
		TaskVO task = TaskCache.getById(task_id+"");
		acceptTask(roleEntity, task);
	}
	
	/**
	 * 根据任务id接受任务
	 * @param p_pk
	 * @param task_id
	 */
	public void acceptTask(RoleEntity roleEntity,TaskVO task )
	{
		if( task==null )
		{
			logger.info("任务领取失败");
			return;
		} 
		switch (task.getTType())
		{
		case TaskType.DIALOG:
			TaskService taskDialogService = new TaskService();
			taskDialogService.getDuiHuaService(roleEntity,task);
			
			break;
		case TaskType.FIND:
			TaskXunWuService taskXunWuService = new TaskXunWuService();
			taskXunWuService.getXunWuService(roleEntity,task);
			
			break;
		case TaskType.CARRY:
			TaskXieDaiService taskXieDaiService = new TaskXieDaiService();
			taskXieDaiService.getXieDaiService(roleEntity,task);
			
			break;
		case TaskType.KILL:
			TaskShaGuaiService taskShaGuaiService = new TaskShaGuaiService();
			taskShaGuaiService.getShaGuaiService(roleEntity,task);
			break;
		case TaskType.COMPLEX:
			TaskFuHeService taskFuHeService = new TaskFuHeService();
			taskFuHeService.getFuHeService(roleEntity,task);
			break;
		}
	}
	
	
	/**
	 * 从任务列表中接受任务
	 * @param p_pk
	 * @param prop_id
	 */
	public String accectTaskFromList(RoleEntity roleEntity,int prop_id,PropUseEffect propUseEffect ,int task_type)
	{
		int p_pk = roleEntity.getBasicInfo().getPPk();
		
		String hint = null;
		AcceptTaskListDao acceptTaskListDao = new AcceptTaskListDao();
		TaskDAO taskDAO = new TaskDAO();
		//int task_type = 1;
		List<AcceptTaskListVO> task_list = acceptTaskListDao.getTaskList(prop_id,task_type);
		//获取任任务的几率是百万分级别
		AcceptTaskListVO acceptTaskList = (AcceptTaskListVO)MathUtil.getRandomEntityFromList(task_list,1000000);
		
		if( acceptTaskList!=null ) 
		{
			String[] task_area = acceptTaskList.getTaskArea().split(",");
			if( isHaveTaskInTaskIDArea(p_pk,task_list) == -1)
			{
				TaskVO task = taskDAO.getTaskBySpacifyArea(task_area[0], task_area[1]);
				acceptTask(roleEntity, task);
				propUseEffect.setEffectDisplay(task.getTName()+"<br/>"+task.getTTishi());
				//hint = "接受任务成功";
			}
			else if(isHaveTaskInTaskIDArea(p_pk,task_list) == 1)
			{
				hint = "您还有没完成的引发的任务";
			}else
			{
				
				hint = "您不能接更多任务, 请先完成身上的任务吧!";
			} 
		}
		else
		{
			logger.info("接收任务失败");
		}
		logger.info("hint="+hint);
		return hint;
	}

	/**
	 * 从任务列表中接受任务
	 * @param p_pk
	 * @param prop_id
	 */
	public String accectTaskFromList(OperateMenuVO menu,RoleEntity roleEntity,int prop_id,PropUseEffect propUseEffect ,int task_type)
	{
		int p_pk = roleEntity.getBasicInfo().getPPk();
		String hint = null;
		AcceptTaskListDao acceptTaskListDao = new AcceptTaskListDao();
		TaskDAO taskDAO = new TaskDAO();
		//int task_type = 1;
		List<AcceptTaskListVO> task_list = acceptTaskListDao.getTaskList(prop_id,task_type);
		//获取任任务的几率是百万分级别
		AcceptTaskListVO acceptTaskList = (AcceptTaskListVO)MathUtil.getRandomEntityFromList(task_list,1000000);
		

		TimeControlService timeControlService = new TimeControlService();
		
		if( acceptTaskList!=null ) 
		{
			String[] task_area = acceptTaskList.getTaskArea().split(",");
			//首先判断是否做够次数了
			if(menu != null && menu.getMenuOperate2()!=null && !menu.getMenuOperate2().equals("")){
		    if(timeControlService.isUseable(p_pk, menu.getId(), TimeControlService.MENUTOUCHTASK, Integer.parseInt(menu.getMenuOperate2())) == false){
		    	hint = "每天只能领取"+menu.getMenuOperate2()+"次";
		    	return hint;
		    }
			}
			if(isHaveTaskInTaskIDArea(p_pk,task_list) == -1)
			{
				//首先取得时间次数控制 
				/*TimeControlDao timeControlDao = new TimeControlDao();
				TimeControlVO timeControl = timeControlDao.getControlInfo(p_pk, menu.getId(), TimeControlService.MENUTOUCHTASK);
				if( timeControl!=null && DateUtil.isSameDay(timeControl.getUseDatetime()) && (timeControl.getUseTimes()+1) == Integer.parseInt(menu.getMenuOperate2()))
				{
					 
				}*/
				
				TaskVO task = taskDAO.getTaskBySpacifyArea(task_area[0], task_area[1]);
				acceptTask(roleEntity, task);
				propUseEffect.setEffectDisplay(task.getTName()+"<br/>"+task.getTTishi());
				timeControlService.updateControlInfo(roleEntity.getBasicInfo().getPPk(), menu.getId(), TimeControlService.MENUTOUCHTASK);
				//hint = "接受任务成功";
			}
			else if(isHaveTaskInTaskIDArea(p_pk,task_list) == 1)
			{
				hint = "您没有完成已引发的任务"; 
			}else
			{
				
				hint = "您不能接更多任务, 请先完成身上的任务吧!";
			} 
		}
		else
		{
			logger.info("接收任务失败");
		}
		logger.info("hint="+hint);
		return hint;
	}
	
	/**
	 * 判断玩家是否已经完成 接收任务道具 的任务.
	 * 如果已经完成,返回-1,如果未完成,返回1 .如果身上任务数量已经超过10条, 则返回2.
	 * @param p_pk
	 * @param prop_id
	 * @return
	 */
	private int isHaveTaskInTaskIDArea(int p_pk, List<AcceptTaskListVO> task_list)
	{
		int flag = -1;
		RoleEntity roleEntity = RoleService.getRoleInfoById(p_pk+"");
		//获取玩家身上尚未完成的任务id
		//List<Integer> list = utaskdao.getUTaskByPk(p_pk);
		List list = roleEntity.getTaskInfo().getCurTaskList().getCurTaskList();
		
		if(list != null && list.size() >= 20) {
			return 2;
		}
		AcceptTaskListVO accepttaskvo = null;
		String task_area = null;
		int t_id = 0;
		for(int i=0; i<task_list.size(); i++ ) {
			accepttaskvo = task_list.get(i);
			task_area = accepttaskvo.getTaskArea();
			
			for(int a=0; a<list.size(); a++ ) {
				CurTaskInfo vo = (CurTaskInfo)list.get(a);
				t_id = vo.getTId();
				//logger.info("t_id="+t_id+" ,task_ares="+task_area);
				String taskareaxiao = task_area.split(",")[0];
				String taskareada = task_area.split(",")[1];
				if(t_id >= Integer.valueOf(taskareaxiao) && t_id <= Integer.valueOf(taskareada)) {
					flag = 1;
				}
			}
		}
		logger.info("flag="+flag);
		return flag;
	}


	
	
}

