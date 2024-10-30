package com.web.action.honour;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.vo.honour.RoleTitleVO;
import com.ls.model.user.RoleEntity;
import com.ls.web.action.ActionBase;

/**
 * 称号相关操作
 */
public class TitleAction extends ActionBase
{
	/**
	 * 查看自己的称号列表
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		List list = roleInfo.getTitleSet().getRoleTitleList();
		request.setAttribute("list", list);
		return mapping.findForward("title_list");
	}

	/**
	 * 查看自己的称号
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		
		String t_id = request.getParameter("tId");
		RoleTitleVO role_title = roleInfo.getTitleSet().getByTId(t_id);
		request.setAttribute("role_title", role_title);
		return mapping.findForward("title_view");
	}

	/**
	 * 是否显示
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		String t_id = request.getParameter("tId");
		roleInfo.getTitleSet().updateShowStatus(Integer.parseInt(t_id));
		return n1(mapping, form, request, response);
	}
}
