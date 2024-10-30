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
 * ����ɵ�����
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
	 * �õ�����ɵ������б�
	 * @return
	 */
	public List<UtaskCompleteVO> taskCompleteList(){
		return new ArrayList<UtaskCompleteVO>(taskComplete.values());
	}
	
	/**
	 * �õ�����ɵ������б�
	 * @return
	 */
	public HashMap<String,UtaskCompleteVO> taskCompleteHashMap(){  
		return taskComplete;
	}
	
	/**
	 * �Ƿ��Ѿ���������
	 */
	public boolean taskCompleteBoo(String taskZu)
	{
		if (taskComplete.containsKey(taskZu))
		{
			return true;//����
		}
		return false;//û����
	}
	
	/**
	 * �Ѿ���ɸ����񣬲���¼
	 */
	public void completeTask(UtaskCompleteVO utaskCompleteVO){
		
		int p_pk = utaskCompleteVO.getPPk();
		String task_zu = utaskCompleteVO.getTaskZu();//������
		
		//������������¼
		UTaskDAO uTaskDAO = new UTaskDAO();
		uTaskDAO.taskComplete(p_pk, task_zu);
		taskComplete.put(task_zu, utaskCompleteVO);
		
		//************************���һЩ������Ҫ���⴦��
		//�ж��Ƿ���30�����ɵ�
		if( task_zu.equals(GameConfig.getPropertiesObject("task_30tong_zu")) )
		{
			new PopUpMsgService().addSysSpecialMsg(p_pk,30,0, PopUpMsgType.TASK_30TONG);
		}
		//�ж���PK����������
		else if(task_zu.equals(GameConfig.getPropertiesObject("task_30pk_zu")))
		{
			new PopUpMsgService().addSysSpecialMsg(p_pk,30,0, PopUpMsgType.TASK_30PK);
		}
		//�ж��Ƿ���������������
		else if( task_zu.indexOf("ty_juezhanbuzhoushan")!=-1 )
		{
			RoleEntity role_info = RoleService.getRoleInfoById(p_pk+"");
			role_info.reset();
		}
	}
}
