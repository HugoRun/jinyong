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
import com.lw.service.scratchticket.ScratchTicketService;
import com.web.jieyi.util.Constant;

public class ScratchTicketAction extends DispatchAction
{
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		if (roleInfo.getBasicInfo().getWrapSpare() < 5)
		{
			request.setAttribute("display", "�����������Է��¹γ�����Ʒ,���������,Ԥ��5�����ϸ���!");
			return display(mapping, form, request, response);
		}
		roleInfo.getStateInfo().setCurState(PlayerState.BOX);//������̳�ʱ״̬�ܱ���
		HashMap<Integer, String> map = (HashMap<Integer, String>) Constant.SCRTCHTICKETMAP
				.get(roleInfo.getBasicInfo().getPPk());
		String prop_id = (String) request.getAttribute("prop_id");
		GoodsService gs = new GoodsService();
		gs.removeProps(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(prop_id), 1,GameLogManager.R_USE);
		request.getSession().setAttribute("scratchticket_id", prop_id);
		Map<Integer, String> new_map = new HashMap<Integer, String>();
		roleInfo.getBasicInfo().setScratchticketnum(2);
		roleInfo.getBasicInfo().setAddscratchticketnum(3);
		if (map == null)
		{
			for (int i = 1; i < 11; i++)
			{
				new_map.put(i, "*��*��*��*��*");
			}
			Constant.SCRTCHTICKETMAP.put(roleInfo.getBasicInfo().getPPk(),
					new_map);
		}
		else
		{
			Constant.SCRTCHTICKETMAP.remove(roleInfo.getBasicInfo().getPPk());
			for (int i = 1; i < 11; i++)
			{
				new_map.put(i, "*��*��*��*��*");
			}
			Constant.SCRTCHTICKETMAP.put(roleInfo.getBasicInfo().getPPk(),
					new_map);
		}
		request.setAttribute("outmap", new_map);
		request.setAttribute("num", roleInfo.getBasicInfo()
				.getScratchticketnum());
		request.setAttribute("num_add", roleInfo.getBasicInfo()
				.getAddscratchticketnum());
		return mapping.findForward("index");
	}

	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String num = (String) request.getParameter("num");
		String scratchticket_id = (String) request.getSession().getAttribute(
				"scratchticket_id");
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		HashMap<Integer, String> map = (HashMap<Integer, String>) Constant.SCRTCHTICKETMAP
				.get(roleInfo.getBasicInfo().getPPk());
		if (map == null)
		{
			request.setAttribute("display", "����ϵGM��������!");
			return display(mapping, form, request, response);
		}
		ScratchTicketService ss = new ScratchTicketService();
		String hint = ss.getScratchTicketPrize(roleInfo, Integer
				.parseInt(scratchticket_id), map, num, response, request);
		request.setAttribute("hint", hint);
		request.setAttribute("outmap", map);
		request.setAttribute("num", roleInfo.getBasicInfo()
				.getScratchticketnum());
		request.setAttribute("num_add", roleInfo.getBasicInfo()
				.getAddscratchticketnum());
		return mapping.findForward("index");
	}

	public ActionForward over(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		HashMap<Integer, String> map = (HashMap<Integer, String>) Constant.SCRTCHTICKETMAP
				.get(roleInfo.getBasicInfo().getPPk());
		String scratchticket_id = (String) request.getSession().getAttribute(
				"scratchticket_id");
		ScratchTicketService ss = new ScratchTicketService();
		ss.getAllMap(roleInfo, map, Integer.parseInt(scratchticket_id));
		String display = "ϣ�����´ιγ����õĽ�Ʒ!<br/>";
		request.setAttribute("hint", display);
		request.setAttribute("outmap", map);
		request.setAttribute("num", roleInfo.getBasicInfo()
				.getScratchticketnum());
		request.setAttribute("num_add", roleInfo.getBasicInfo()
				.getAddscratchticketnum());
		return mapping.findForward("over");
	}

	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String display = (String) request.getAttribute("display");
		request.setAttribute("display", display);
		return mapping.findForward("display");
	}

	// ���Ӵ������� 4150
	public ActionForward addnum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		String hint = "";
		if (roleInfo.getBasicInfo().getAddscratchticketnum() < 1)
		{
			hint = "����ʹ�ô����Ѿ��ﵽ�������!<br/>";
		}
		else
		{
			// �����Ƿ����ʹ���ж�
			PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
			int tatol_prop_num = propGroupDao.getPropNumByByPropID(roleInfo
					.getBasicInfo().getPPk(), 4150);
			if (tatol_prop_num == 0)
			{
				hint = "���İ�����û�С��������!<br/>";
			}
			else
			{
				GoodsService gs = new GoodsService();
				gs.removeProps(roleInfo.getBasicInfo().getPPk(), 4150, 1,GameLogManager.R_USE);
				roleInfo.getBasicInfo().setAddscratchticketnum(
						roleInfo.getBasicInfo().getAddscratchticketnum() - 1);
				roleInfo.getBasicInfo().setScratchticketnum(
						roleInfo.getBasicInfo().getScratchticketnum() + 1);
			}
		}
		HashMap<Integer, String> map = (HashMap<Integer, String>) Constant.SCRTCHTICKETMAP
				.get(roleInfo.getBasicInfo().getPPk());
		if (map == null)
		{
			request.setAttribute("display", "����ϵGM��������!");
			return display(mapping, form, request, response);
		}
		request.setAttribute("hint", hint);
		request.setAttribute("outmap", map);
		request.setAttribute("num", roleInfo.getBasicInfo()
				.getScratchticketnum());
		request.setAttribute("num_add", roleInfo.getBasicInfo()
				.getAddscratchticketnum());
		return mapping.findForward("index");
	}
}
