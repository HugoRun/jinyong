package com.web.action.communion;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.web.action.ActionBase;
import com.ls.web.service.player.RoleService;
import com.pm.dao.setting.SettingDao;
import com.pm.service.setting.SysSettingService;
import com.pm.vo.setting.SettingVO;
import com.web.service.communion.CommunionIndexService;
import com.web.service.communion.CommunionService;

/**
 * @author ��ƾ� ����
 */
public class CommunionInfoAction extends ActionBase
{
	Logger logger =  Logger.getLogger("log.action");
	/**
	 * ��
	 * */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{    
		//UTaskDAO dao = new UTaskDAO();
		//List<UTaskVO> list = dao.getUTaskPk(roleInfo.getBasicInfo().getPPk()+"");
		//List list = roleInfo.getTaskInfo().getCurTaskList().getCurTaskList();
		//request.setAttribute("list", list);
		return mapping.findForward("public");

	}
	
	/**
	 * ��
	 * */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{   
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		request.setAttribute("pPk", roleInfo.getBasicInfo().getPPk()+"");
		return mapping.findForward("camp");

	}
	
	/**
	 * ��
	 * */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{   
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		request.setAttribute("pPk", roleInfo.getBasicInfo().getPPk()+"");
		return mapping.findForward("group");

	}
	
	/**
	 * ��
	 * */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{   
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		request.setAttribute("pPk", roleInfo.getBasicInfo().getPPk()+"");
		return mapping.findForward("tong");

	}
	
	/**
	 * ��
	 * */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{   
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		request.setAttribute("pPk", roleInfo.getBasicInfo().getPPk()+"");
		String pByPk = (String)request.getAttribute("pByPk");
		if(pByPk == null){
			pByPk = request.getParameter("pByPk");
		}
		String pByName = (String)request.getAttribute("pByName");
		if(pByName == null){
			pByName = request.getParameter("pByName");
		}
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("pByName", pByName);
		return mapping.findForward("privately");
	}
	
	
	/**
	 * ��ѯ��ʾ����������7�����
	 */
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession()); 
		CommunionService communionService = new CommunionService();
		List list = communionService.getOtherWhisper(roleInfo.getBasicInfo().getPPk(), 5);
		request.setAttribute("list", list);
		return mapping.findForward("otherwhisper");
	}
	/**
	 * ��ҳ���췢��
	 */
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String chat_channel = request.getParameter("chatChannel");
		if( chat_channel!=null )
		{ 
			String chat_content = request.getParameter("chatContent");
			CommunionIndexService communionIndexService = new CommunionIndexService();
			//���ĵĵȼ�����
			int public_chat_grade_limit = GameConfig.getPublicChatGradeLimit();
			String chatHint = communionIndexService.Communion(roleInfo, Integer.parseInt(chat_channel),chat_content, public_chat_grade_limit+"");
			if( chatHint==null )
			{
				chatHint = "";
			}
			return super.dispath(request, response, "/scene.do?chatHint="+chatHint);
		}
		return mapping.findForward("no_refurbish_scene"); 
	} 
}
