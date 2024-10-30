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
 * @author ��ƾ� 9��������תֱ����ѡ������
 */
public class VisitLeadAction extends ActionBase
{
	/**
	 * 9��������תֱ����ѡ������
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		return mapping.findForward("visitlead");
	}

	/**
	 * 9��������תֱ����ѡ������ type = 1ؤ�� 2���� 3����
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		RoleEntity roleEntity = this.getRoleEntity(request);
		
		TaskVisitLeadService taskVisitLeadService = new TaskVisitLeadService();
		// ����ʱ��
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//��ʱ����и�ʽ��
		/** ������ȡʱ�� */
		String createTime = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
		String type = request.getParameter("type");
		
		String hint = null;
		
		//�����������Ϊ1 
		int t_zuxl = 1;
		if (type.equals("1"))
		{// ؤ��
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
		{// ����
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
		{// ����
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
	 * 9��������תֱ����ѡ������
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		return mapping.findForward("visitleadok");
	}
}
