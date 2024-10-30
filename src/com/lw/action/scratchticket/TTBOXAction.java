package com.lw.action.scratchticket;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.player.PlayerState;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.player.RoleService;
import com.lw.service.scratchticket.TTBOXService;
import com.lw.vo.ttbox.TTBOXVO;
import com.web.jieyi.util.Constant;

public class TTBOXAction extends DispatchAction
{
	public ActionForward open(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		if (roleInfo.getBasicInfo().getWrapSpare() < 2)
		{
			request.setAttribute("display", "您包裹不足以放下刮出的物品,请整理包裹,预留2个以上格子!");
			return display(mapping, form, request, response);
		}
		roleInfo.getStateInfo().setCurState(PlayerState.BOX);//玩家在商城时状态受保护
		HashMap<Integer, TTBOXVO> map = (HashMap<Integer, TTBOXVO>) Constant.TTBOXMAP
				.get(roleInfo.getBasicInfo().getPPk());
		String prop_id = (String) request.getAttribute("prop_id");
		request.getSession().setAttribute("ttbox_id", prop_id);
		GoodsService gs = new GoodsService();
		TTBOXService ts = new TTBOXService();
		gs.removeProps(roleInfo.getPPk(), Integer.parseInt(prop_id), 1,GameLogManager.R_USE);
		Map<Integer, TTBOXVO> new_map = new HashMap<Integer, TTBOXVO>();
		if (map == null)
		{
			new_map = ts
					.getAllMap(roleInfo, new_map, Integer.parseInt(prop_id));
			Constant.TTBOXMAP.put(roleInfo.getBasicInfo().getPPk(), new_map);
		}
		else
		{
			Constant.TTBOXMAP.remove(roleInfo.getBasicInfo().getPPk());
			new_map = ts
					.getAllMap(roleInfo, new_map, Integer.parseInt(prop_id));
			Constant.TTBOXMAP.put(roleInfo.getBasicInfo().getPPk(), new_map);
		}
		roleInfo.getBasicInfo().setScratchticketnum(0);
		roleInfo.getBasicInfo().setAddscratchticketnum(2);
		request.setAttribute("outmap", new_map);
		request.setAttribute("num", roleInfo.getBasicInfo()
				.getScratchticketnum());
		request.setAttribute("num_add", roleInfo.getBasicInfo()
				.getAddscratchticketnum());
		return mapping.findForward("index");
	}

	public ActionForward one(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		HashMap<Integer, TTBOXVO> map = (HashMap<Integer, TTBOXVO>) Constant.TTBOXMAP
				.get(roleInfo.getBasicInfo().getPPk());
		if (map == null)
		{
			request.setAttribute("display", "请联系GM补发道具!");
		}
		else
		{
			TTBOXVO vo = map.get(0);
			TTBOXService ts = new TTBOXService();
			String hint = ts.getTTBoxPrize(roleInfo, vo, response, request);
			request.setAttribute("display", hint);
		}
		return display(mapping, form, request, response);
	}

	public ActionForward two(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		HashMap<Integer, TTBOXVO> map = (HashMap<Integer, TTBOXVO>) Constant.TTBOXMAP
				.get(roleInfo.getBasicInfo().getPPk());
		if (map == null)
		{
			request.setAttribute("display", "请联系GM补发道具!");
		}
		else
		{
			TTBOXVO vo = map.get(1);
			TTBOXService ts = new TTBOXService();
			String hint = ts.getTTBoxPrize(roleInfo, vo, response, request);
			request.setAttribute("display", hint);
		}
		return display(mapping, form, request, response);
	}

	public ActionForward three(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		HashMap<Integer, TTBOXVO> map = (HashMap<Integer, TTBOXVO>) Constant.TTBOXMAP
				.get(roleInfo.getBasicInfo().getPPk());
		if (map == null)
		{
			request.setAttribute("display", "请联系GM补发道具!");
		}
		else
		{
			TTBOXVO vo = map.get(2);
			TTBOXService ts = new TTBOXService();
			String hint = ts.getTTBoxPrize(roleInfo, vo, response, request);
			request.setAttribute("display", hint);
		}
		return display(mapping, form, request, response);
	}

	// 聚宝盆的增加使用次数道具 ID为4152
	public ActionForward addnum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		String display = "";
		HashMap<Integer, TTBOXVO> map = (HashMap<Integer, TTBOXVO>) Constant.TTBOXMAP
				.get(roleInfo.getBasicInfo().getPPk());
		if (roleInfo.getBasicInfo().getAddscratchticketnum() < 1)
		{
			display = "您【金手指】的使用次数已达到限制!";
			request.setAttribute("display", display);
			request.setAttribute("outmap", map);
			request.setAttribute("num", roleInfo.getBasicInfo()
					.getScratchticketnum());
			request.setAttribute("num_add", roleInfo.getBasicInfo()
					.getAddscratchticketnum());
			return mapping.findForward("index");
		}
		else
		{
			// 道具是否可以使用判断
			PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
			int tatol_prop_num = propGroupDao.getPropNumByByPropID(roleInfo
					.getBasicInfo().getPPk(), 4152);
			if (tatol_prop_num == 0)
			{
				display = "您的包裹里没有【金手指】!<br/>";
				request.setAttribute("display", display);
				request.setAttribute("outmap", map);
				request.setAttribute("num", roleInfo.getBasicInfo()
						.getScratchticketnum());
				request.setAttribute("num_add", roleInfo.getBasicInfo()
						.getAddscratchticketnum());
				return mapping.findForward("index");
			}
			else
			{
				GoodsService gs = new GoodsService();
				gs.removeProps(roleInfo.getPPk(), 4152, 1,GameLogManager.R_USE);
				roleInfo.getBasicInfo().setScratchticketnum(
						roleInfo.getBasicInfo().getScratchticketnum() + 1);
				roleInfo.getBasicInfo().setAddscratchticketnum(
						roleInfo.getBasicInfo().getAddscratchticketnum() - 1);
				return get(mapping, form, request, response);
			}
		}
	}

	public ActionForward get(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		String display = "";
		HashMap<Integer, TTBOXVO> map = (HashMap<Integer, TTBOXVO>) Constant.TTBOXMAP
				.get(roleInfo.getBasicInfo().getPPk());
		if (roleInfo.getBasicInfo().getScratchticketnum() < 1)
		{
			display = "您不能再轮转【聚宝盆】!";
			request.setAttribute("outmap", map);
		}
		else
		{
			roleInfo.getBasicInfo().setScratchticketnum(
					roleInfo.getBasicInfo().getScratchticketnum() - 1);
			String prop_id = (String) request.getSession().getAttribute(
					"ttbox_id");
			TTBOXService ts = new TTBOXService();
			Map<Integer, TTBOXVO> new_map = new HashMap<Integer, TTBOXVO>();
			if (map == null)
			{
				new_map = ts.getAllMap(roleInfo, new_map, Integer
						.parseInt(prop_id));
				Constant.TTBOXMAP
						.put(roleInfo.getBasicInfo().getPPk(), new_map);
			}
			else
			{
				Constant.TTBOXMAP.remove(roleInfo.getBasicInfo().getPPk());
				new_map = ts.getAllMap(roleInfo, new_map, Integer
						.parseInt(prop_id));
				Constant.TTBOXMAP
						.put(roleInfo.getBasicInfo().getPPk(), new_map);
			}
			request.setAttribute("outmap", new_map);
		}
		request.setAttribute("display", display);
		request.setAttribute("num", roleInfo.getBasicInfo()
				.getScratchticketnum());
		request.setAttribute("num_add", roleInfo.getBasicInfo()
				.getAddscratchticketnum());
		return mapping.findForward("index");
	}

	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String display = (String) request.getAttribute("display");
		request.setAttribute("display", display);
		return mapping.findForward("display");
	}
}
