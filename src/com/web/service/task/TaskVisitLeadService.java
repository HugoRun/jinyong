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
 * ����:9��������תֱ����ѡ������
 * 
 * @author ��ƾ� 11:13:44 AM 
 */
public class TaskVisitLeadService
{

	/**
	 * ����һ������
	 * @param roleEntity
	 * @param t_zu			������
	 * @return				������ʾ
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
		
		//�õ�������ĵ�һ������
		int t_zuxl = 1;
		TaskVO taskVO = taskDAO.getTaskByZuAndXulie(t_zu, t_zuxl);
		
		if(taskVO!=null)
		{
			switch(taskVO.getTType())
			{
				// �Ի�����
				case 1:hint = taskService.getDuiHuaService(roleEntity,taskVO);break;
				// 2����ɱ������
				case 2:hint = taskShaGuaiService.getShaGuaiService(roleEntity,taskVO);break;
				// 3����Я��������
				case 3:hint = taskXieDaiService.getXieDaiService(roleEntity,taskVO);break;
				// 4 Ѱ����
				case 4:hint = taskXunWuService.getXunWuService(roleEntity,taskVO);break;
				// ������
				case 5:hint = taskFuHeService.getFuHeService(roleEntity,taskVO);break;
			}
			
			if( hint!=null )
			{
				//���Խ��ܸ�����
				hint = taskVO.getTTishi();
			}
		}
		else
		{
			DataErrorLog.debugData("TaskVisitLeadService.acceptTask:�޸�����t_zu="+t_zu);
		}
		return hint;
	} 
	/**
	 * ���������ID
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
	 * ���ؽ�ɫ�����ID
	 * */
	public String getPartTaskPk(int pPk,int tid){ 
		String hint = null;
		UTaskDAO uTaskDAO = new UTaskDAO(); 
		UTaskVO uTaskVO = uTaskDAO.getPoint(pPk+"", tid);
		hint = uTaskVO.getTPk()+"";
		return hint;
	}
}
