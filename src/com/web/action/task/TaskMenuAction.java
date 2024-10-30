package com.web.action.task;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.model.user.RoleEntity;
import com.ls.web.action.ActionBase;
import com.web.service.taskpage.TaskPageService;

/**
 * �˵���������
 */
public class TaskMenuAction extends ActionBase
{
	/**
	 * ����˵�
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		String menu_id = request.getParameter("menu_id");
		String task_id = request.getParameter("task_id");
		String menu_type = request.getParameter("menu_type");
		String sg = request.getParameter("sg");
		String xd = request.getParameter("xd");
		String xw = request.getParameter("xw");
		String fh = request.getParameter("fh");
		
		RoleEntity role_info  = this.getRoleEntity(request);
		
		TaskPageService taskPageService = new TaskPageService();
		//�õ�����ҳ����ʾ
		String display = taskPageService.taskPageViewService(role_info,task_id, menu_id,menu_type, sg, xd, xw, fh,request,response);
		
		request.setAttribute("display", display);
		return mapping.findForward("taskmenu");
	}
 
}
