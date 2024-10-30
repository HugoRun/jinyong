/**
 * ��������
 */
package com.web.action.task;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.vo.task.TaskVO;
import com.ls.ben.cache.staticcache.task.TaskCache;
import com.ls.model.property.task.CurTaskInfo;
import com.ls.model.user.RoleEntity;
import com.ls.web.action.ActionBase;
import com.pm.service.systemInfo.SystemInfoService;
import com.web.service.TaskFuHeService;
import com.web.service.TaskService;
import com.web.service.TaskShaGuaiService;
import com.web.service.TaskXieDaiService;
import com.web.service.TaskXunWuService;
import com.web.service.task.TaskPageService;

/**
 * ��������
 */
public class TaskAction extends ActionBase
{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
		 	HttpServletRequest request, HttpServletResponse response) {
	    RoleEntity roleInfo = this.getRoleEntity(request);
		/** �����ID */
		String tId = request.getParameter("tId");
		String noCarry=request.getParameter("nocarry");
		if(tId == null ){
			tId = (String) request.getAttribute("tId");
		}
		String menu_id = (String) request.getAttribute("menu_id");
		if(menu_id == null ){
			menu_id = (String) request.getAttribute("menu_id");
		}
		String type = (String) request.getAttribute("type");
		if(type == null ){
			type = request.getParameter("type");
		}
		String menu_type = (String) request.getAttribute("menu_type");
		if(menu_type == null ){
			menu_type = (String) request.getAttribute("menu_type");
		}
		
		TaskPageService taskPageService = new TaskPageService();
		TaskVO taskVO = TaskCache.getById(tId);
		String[] taskKeyValue = taskVO.getTKeyValue().split(",");
		// 1����Ի�����
		TaskService taskService = new TaskService();
		TaskShaGuaiService taskShaGuaiService = new TaskShaGuaiService();
		TaskXieDaiService taskXieDaiService = new TaskXieDaiService();
		TaskXunWuService taskXunWuService = new TaskXunWuService();
		TaskFuHeService taskFuHeService = new TaskFuHeService();
		String hint = null;
		// �ٴ��ж��Ƿ����ȡ����
		if ((taskVO.getTLevelXiao() > roleInfo.getBasicInfo().getGrade())
				|| roleInfo.getBasicInfo().getGrade() > taskVO.getTLevelDa() 
				|| (roleInfo.getTitleSet().isHaveByTitleStr(taskVO.getTSchool())==false))
		{
			hint = "��ȡ����������";
			request.setAttribute("hint", hint);
			return mapping.findForward("taskhint");
		} 
			
    	CurTaskInfo curTaskInfo = (CurTaskInfo)roleInfo.getTaskInfo().getCurTaskList().getByZu(taskVO.getTZu());
    	if(curTaskInfo !=null)
    	{
    		if(taskVO.getTCycle() == 0  && !tId.equals(curTaskInfo.getTNext()+""))
    		{
    			hint = "�벻Ҫ�Ƿ�ˢȡ������!";
    			request.setAttribute("hint", hint);
    			return mapping.findForward("taskhint");		
    		}
    	}
    	if(roleInfo.getTaskInfo().getTaskCompleteInfo().taskCompleteBoo(taskVO.getTZu()) == true)
    	{
    		if(taskVO.getTCycle() == 0)
    		{
    			hint = "�벻Ҫ�Ƿ�ˢȡ������!";
    			request.setAttribute("hint", hint);
    			return mapping.findForward("taskhint");	
    		}
    	}
    	
    	SystemInfoService systemInfoService = new SystemInfoService();
    	systemInfoService.setNewPlayerGuideInfoMSG(roleInfo,7+"",tId);
    	
    	
    	switch(taskVO.getTType())
    	{
    		case 1://�Ի���
    			hint = taskService.getDuiHuaService(roleInfo,taskVO);
    			if(hint!=null && hint.equals("-1"))
    			{
    				return this.dispath(request, response, "/visitleadaction.do?cmd=n1");
    			}
    			break;
    		case 2:// 2����ɱ������
    			hint = taskShaGuaiService.getShaGuaiService(roleInfo,taskVO);
    			break;
    		case 3:// 3����Я��������
    			hint = taskXieDaiService.getXieDaiService(roleInfo,taskVO);
    			break;
    		case 4:// Ѱ����
    			hint = taskXunWuService.getXunWuService(roleInfo,taskVO);
    			break;
    		case 5:// ������
    			hint = taskFuHeService.getFuHeService(roleInfo,taskVO);
    			break;
    	}
    	
    	if( hint != null)
    	{
    		//�������ύ����ʾ
			request.setAttribute("hint", hint);
			return mapping.findForward("taskhint");
		}
    	
    	if( taskVO.isLast()==true && taskVO.getTZu().indexOf("ty_juezhanbuzhoushan")!=-1 )
    	{
    		//���������һ����������
    		return super.dispath(request, response, "/guide.do?step=end_cartoon1");
    	}
    	if("yes".equals(noCarry))
    	{
    		return mapping.findForward("successpub");
    	}
    	//������
		if(taskKeyValue[0] != null && !taskKeyValue[0].equals("") && !taskKeyValue[0].equals("null"))
		{
			hint = taskPageService.taskRemit(roleInfo, taskKeyValue[0], tId);
			request.setAttribute("hint", hint);
			return mapping.findForward("successpub");
		}
    		
    		
    	request.setAttribute("hint", hint);
    	request.setAttribute("mapid", roleInfo.getBasicInfo().getSceneId()+"");
    	if(type != null && !type.equals(""))
    	{
    		if(Integer.parseInt(type) == 1)
    		{
    			request.setAttribute("menu_id", menu_id);
    			request.setAttribute("task_id", taskVO.getTNext()+"");
    			request.setAttribute("menu_type", menu_type); 
    			return mapping.findForward("menudo");
    		}
    	}
    	return mapping.findForward("success");
	}
}
