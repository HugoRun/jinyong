package com.lw.action.wishingtree;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.player.RoleService;
import com.lw.service.wishingtree.WishingTreeService;
import com.lw.vo.wishingtree.WishingTreeVO;
import com.pub.ben.info.Expression;

public class WishingTreeAction extends DispatchAction
{
	// 查看信息
	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		WishingTreeService ws = new WishingTreeService();
		String hint = (String) request.getAttribute("hint");
		String page_no_str = request.getParameter("page_no");

		// 得到置顶信息
		List<WishingTreeVO> list = ws.getTopWishing();

		int page_no = 1;

		if (page_no_str != null && !page_no_str.equals(""))
		{
			page_no = Integer.parseInt(page_no_str);
		}

		QueryPage queryPage = ws.getAllWishing(page_no);
		request.setAttribute("hint", hint);
		request.setAttribute("list", list);
		request.setAttribute("queryPage", queryPage);
		return mapping.findForward("wishing_index");
	}

	// 写信息
	public ActionForward write(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String display = (String) request.getAttribute("hint");
		if (display != null && !display.equals("") && !display.equals("null"))
		{
			request.setAttribute("hint", display);
		}
		else
		{
			request.setAttribute("hint", "");
		}
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		PropCache propCache = new PropCache();
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		String prop_name = propCache.getPropNameById(4116);
		int prop_num = propGroupDao.getPropNumByByPropID(roleInfo
				.getBasicInfo().getPPk(), 4116);
		if (prop_num < 1)
		{
			request.setAttribute("display", "您没有" + prop_name + "这个物品!");
			return display(mapping, form, request, response);
		}
		return mapping.findForward("write");
	}

	public ActionForward top(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("id");
		request.getSession().setAttribute("top_id", id);
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		PropCache propCache = new PropCache();
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		String prop_name = propCache.getPropNameById(4117);
		int prop_num = propGroupDao.getPropNumByByPropID(roleInfo
				.getBasicInfo().getPPk(), 4117);
		if (prop_num < 1)
		{
			request.setAttribute("display", "您没有" + prop_name + "这个物品!");
			return display(mapping, form, request, response);
		}
		return mapping.findForward("set_top");
	}

	public ActionForward set(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		String wishing = (String) request.getParameter("wishing");
		if (Expression.hasPublishWithMail(wishing) == -1)
		{
			request.setAttribute("hint", "您输入了非法字符!");
			return write(mapping, form, request, response);
		}
		WishingTreeService ws = new WishingTreeService();
		ws.insertWishing(roleInfo.getBasicInfo().getPPk(), roleInfo
				.getBasicInfo().getName(), wishing);
		GoodsService gs = new GoodsService();
		gs.removeProps(roleInfo.getBasicInfo().getPPk(), 4116, 1,GameLogManager.R_USE);
		request.setAttribute("display", "发送成功!");
		return mapping.findForward("display");
	}

	public ActionForward settop(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String id = (String) request.getSession().getAttribute("top_id");
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		WishingTreeService ws = new WishingTreeService();
		ws.setTopWishing(Integer.parseInt(id));
		GoodsService gs = new GoodsService();
		gs.removeProps(roleInfo.getBasicInfo().getPPk(), 4117, 1,GameLogManager.R_USE);
		request.setAttribute("display", "置顶成功!");
		return mapping.findForward("display");
	}

	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String display = (String) request.getAttribute("display");
		request.setAttribute("display", display);
		return mapping.findForward("display");
	}

	// 查看信息
	public ActionForward indexGM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		WishingTreeService ws = new WishingTreeService();
		String hint = (String) request.getAttribute("hint");
		String page_no_str = request.getParameter("page_no");

		// 得到置顶信息
		List<WishingTreeVO> list = ws.getTopWishing();

		int page_no = 1;

		if (page_no_str != null && !page_no_str.equals(""))
		{
			page_no = Integer.parseInt(page_no_str);
		}

		QueryPage queryPage = ws.getAllWishing(page_no);
		request.setAttribute("hint", hint);
		request.setAttribute("list", list);
		request.setAttribute("queryPage", queryPage);
		return mapping.findForward("wishing_index_gm");
	}

	// 查看信息
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		WishingTreeService ws = new WishingTreeService();
		String hint = (String) request.getAttribute("hint");
		String page_no_str = request.getParameter("page_no");

		String id = request.getParameter("id");
		ws.deleteWishing(Integer.parseInt(id));

		// 得到置顶信息
		List<WishingTreeVO> list = ws.getTopWishing();

		int page_no = 1;

		if (page_no_str != null && !page_no_str.equals(""))
		{
			page_no = Integer.parseInt(page_no_str);
		}

		QueryPage queryPage = ws.getAllWishing(page_no);
		request.setAttribute("hint", hint);
		request.setAttribute("list", list);
		request.setAttribute("queryPage", queryPage);
		return mapping.findForward("wishing_index_gm");
	}

}
