package com.web.action.task.visitlead;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.model.user.RoleEntity;
import com.ls.web.action.ActionBase;
import com.web.service.task.TaskVisitLeadService;

/**
 * @author 侯浩军 9级任务跳转直接让选择门派
 */
public class VisitLeadAction extends ActionBase
{
	/**
	 * 9级任务跳转直接让选择门派
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		return mapping.findForward("visitlead");
	}

	/**
	 * 9级任务跳转直接让选择门派 type = 1丐帮 2少林 3明教
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		RoleEntity roleEntity = this.getRoleEntity(request);
		
		TaskVisitLeadService taskVisitLeadService = new TaskVisitLeadService();
		// 创建时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//对时间进行格式化
		/** 任务领取时间 */
		String createTime = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量
		String type = request.getParameter("type");
		
		String hint = null;
		
		//任务组的序列为1 
		int t_zuxl = 1;
		if (type.equals("1"))
		{// 丐帮
			String t_zu = "gb_yidaidizi";  
			hint = taskVisitLeadService.acceptTask(roleEntity,t_zu);
			String tId = taskVisitLeadService.getTaskPk(t_zu,t_zuxl,roleEntity.getPPk(),createTime,roleEntity.getName(),roleEntity.getUPk());
			String tPk = taskVisitLeadService.getPartTaskPk(roleEntity.getPPk(), Integer.parseInt(tId));
			try
			{
				request.getRequestDispatcher("/taskinfoaction.do?cmd=n2&tId="+tId+"&tPk="+tPk+"&chair="+request.getParameter("chair")).forward(request, response);
			}
			catch (UnsupportedEncodingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (ServletException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		if (type.equals("2"))
		{// 少林
			String t_zu = "sl_shaodiseng";
			
			hint = taskVisitLeadService.acceptTask(roleEntity,t_zu);
			String tId = taskVisitLeadService.getTaskPk(t_zu,t_zuxl,roleEntity.getPPk(),createTime,roleEntity.getName(),roleEntity.getUPk());
			String tPk = taskVisitLeadService.getPartTaskPk(roleEntity.getPPk(), Integer.parseInt(tId));
			try
			{
				request.getRequestDispatcher("/taskinfoaction.do?cmd=n2&tId="+tId+"&tPk="+tPk+"&chair="+request.getParameter("chair")).forward(request, response);
			}
			catch (UnsupportedEncodingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (ServletException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		if (type.equals("3"))
		{// 明教
			String t_zu = "mj_xunshandizi";
			
			hint = taskVisitLeadService.acceptTask(roleEntity,t_zu);
			String tId = taskVisitLeadService.getTaskPk(t_zu,t_zuxl,roleEntity.getPPk(),createTime,roleEntity.getName(),roleEntity.getUPk());
			String tPk = taskVisitLeadService.getPartTaskPk(roleEntity.getPPk(), Integer.parseInt(tId));
			try
			{
				request.getRequestDispatcher("/taskinfoaction.do?cmd=n2&tId="+tId+"&tPk="+tPk+"&chair="+request.getParameter("chair")).forward(request, response);
			}
			catch (UnsupportedEncodingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (ServletException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		return null;
	}
	
	/**
	 * 9级任务跳转直接让选择门派
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		return mapping.findForward("visitleadok");
	}
}
