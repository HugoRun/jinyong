package com.ls.model.property.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.ben.dao.task.TaskCompleteDao;
import com.ben.dao.task.UTaskDAO;
import com.ben.vo.task.UtaskCompleteVO;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.model.user.UserBase;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.system.UMsgService;
import com.web.service.popupmsg.PopUpMsgService;

/**
 * 已完成的任务
 */
public class TaskCompleteInfo extends UserBase
{
	private TaskCompleteDao dao = null;
	private HashMap<String,UtaskCompleteVO> taskComplete;

	public TaskCompleteInfo(int p_pk)
	{
		super(p_pk);
		dao = new TaskCompleteDao();
		taskComplete = dao.getPlayerTaskComplete(p_pk);
	}
	
	/**
	 * 得到已完成的任务列表
	 * @return
	 */
	public List<UtaskCompleteVO> taskCompleteList(){
		return new ArrayList<UtaskCompleteVO>(taskComplete.values());
	}
	
	/**
	 * 得到已完成的任务列表
	 * @return
	 */
	public HashMap<String,UtaskCompleteVO> taskCompleteHashMap(){  
		return taskComplete;
	}
	
	/**
	 * 是否已经做完任务
	 */
	public boolean taskCompleteBoo(String taskZu)
	{
		if (taskComplete.containsKey(taskZu))
		{
			return true;//做完
		}
		return false;//没做完
	}
	
	/**
	 * 已经完成该任务，并记录
	 */
	public void completeTask(UtaskCompleteVO utaskCompleteVO){
		
		int p_pk = utaskCompleteVO.getPPk();
		String task_zu = utaskCompleteVO.getTaskZu();//任务组
		
		//增加完成任务记录
		UTaskDAO uTaskDAO = new UTaskDAO();
		uTaskDAO.taskComplete(p_pk, task_zu);
		taskComplete.put(task_zu, utaskCompleteVO);
		
		//************************完成一些任务需要特殊处理
		//判断是否是30级帮派的
		if( task_zu.equals(GameConfig.getPropertiesObject("task_30tong_zu")) )
		{
			new PopUpMsgService().addSysSpecialMsg(p_pk,30,0, PopUpMsgType.TASK_30TONG);
		}
		//判断是PK任务做完了
		else if(task_zu.equals(GameConfig.getPropertiesObject("task_30pk_zu")))
		{
			new PopUpMsgService().addSysSpecialMsg(p_pk,30,0, PopUpMsgType.TASK_30PK);
		}
		//判断是否是新手体验任务
		else if( task_zu.indexOf("ty_juezhanbuzhoushan")!=-1 )
		{
			RoleEntity role_info = RoleService.getRoleInfoById(p_pk+"");
			role_info.reset();
		}
	}
}
