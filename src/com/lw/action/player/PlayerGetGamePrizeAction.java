package com.lw.action.player;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.web.action.ActionBase;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.player.RoleService;
import com.lw.service.player.PlayerGetGamePrizeService;
import com.lw.vo.player.PlayerGetGamePrizeVO;

public class PlayerGetGamePrizeAction extends ActionBase
{
	/** 页面跳转MENU */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		PlayerGetGamePrizeService playerGetGamePrizeService = new PlayerGetGamePrizeService();
		
		List<PlayerGetGamePrizeVO> list = null;
		
		if (GameConfig.getChannelId() == Channel.WANXIANG)
		{
			list = playerGetGamePrizeService.getPrizeTypeByUpk(roleInfo.getUPk() + "");
		}
		else
		{
			String user_id = playerGetGamePrizeService.getUserID(roleInfo.getUPk());
			list = playerGetGamePrizeService.getPrizeType(user_id);
		}

		if (list.size() == 0)
		{
			request.setAttribute("display", "您没有奖励可以领取!");
			return mapping.findForward("display");
		}

		else
		{
			request.setAttribute("list", list);
			return mapping.findForward("menu");
		}
	}

	/** 得到玩家领取奖励的详情 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		if (GameConfig.getChannelId() == Channel.WANXIANG)
		{
			RoleService roleService = new RoleService();
			RoleEntity roleInfo = roleService.getRoleInfoBySession(request
					.getSession());

			PlayerGetGamePrizeService playerGetGamePrizeService = new PlayerGetGamePrizeService();

			String id_str = request.getParameter("id");
			if (id_str == null)
			{
				request.setAttribute("display", "您没有奖励可以领取!");
				return mapping.findForward("display");
			}
			else
			{
				PlayerGetGamePrizeVO vo = playerGetGamePrizeService
						.getPlayerPrizeListByUpk(Integer.parseInt(id_str),
								roleInfo.getBasicInfo().getUPk() + "");
				String display = playerGetGamePrizeService
						.getPlayerPrizeOutByUpk(Integer.parseInt(id_str),
								roleInfo.getBasicInfo().getUPk() + "", request,
								response);
				request.setAttribute("display", display);
				request.setAttribute("vo", vo);
				request.setAttribute("id", id_str);
				return mapping.findForward("list");
			}
		}
		else
		{
			String u_passprot = (String) request.getSession().getAttribute(
					"user_name");

			RoleService roleService = new RoleService();
			RoleEntity roleInfo = roleService.getRoleInfoBySession(request
					.getSession());

			PlayerGetGamePrizeService playerGetGamePrizeService = new PlayerGetGamePrizeService();

			String user_id = playerGetGamePrizeService.getUserID(roleInfo
					.getBasicInfo().getUPk());

			String id_str = request.getParameter("id");
			if (id_str == null)
			{
				request.setAttribute("display", "您没有奖励可以领取!");
				return mapping.findForward("display");
			}
			else
			{
				PlayerGetGamePrizeVO vo = playerGetGamePrizeService
						.getPlayerPrizeList(Integer.parseInt(id_str), user_id);
				String display = playerGetGamePrizeService.getPlayerPrizeOut(
						Integer.parseInt(id_str), user_id, request, response);
				request.setAttribute("display", display);
				request.setAttribute("vo", vo);
				request.setAttribute("id", id_str);
				return mapping.findForward("list");
			}
		}

	}

	/** 领取奖品的过程和结果 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		if (GameConfig.getChannelId() == Channel.WANXIANG)
		{
			String id_str = request.getParameter("id");
			RoleService roleService = new RoleService();
			RoleEntity roleInfo = roleService.getRoleInfoBySession(request
					.getSession());
			PlayerGetGamePrizeService playerGetGamePrizeService = new PlayerGetGamePrizeService();

			if (id_str == null)
			{
				request.setAttribute("display", "您没有奖励可以领取!");
				return mapping.findForward("display");
			}
			else
			{

				String display = playerGetGamePrizeService
						.playerGetSystemPrizeByUpk(roleInfo, roleInfo
								.getBasicInfo().getUPk()
								+ "", Integer.parseInt(id_str));
				request.setAttribute("display", display);
				return mapping.findForward("display");
			}
		}
		else
		{
			String u_passprot = (String) request.getSession().getAttribute(
					"user_name");
			String id_str = request.getParameter("id");
			RoleService roleService = new RoleService();
			RoleEntity roleInfo = roleService.getRoleInfoBySession(request
					.getSession());
			PlayerGetGamePrizeService playerGetGamePrizeService = new PlayerGetGamePrizeService();
			String user_id = playerGetGamePrizeService.getUserID(roleInfo
					.getBasicInfo().getUPk());

			if (id_str == null)
			{
				request.setAttribute("display", "您没有奖励可以领取!");
				return mapping.findForward("display");
			}
			else
			{

				String display = playerGetGamePrizeService
						.playerGetSystemPrize(roleInfo, user_id, Integer
								.parseInt(id_str));
				request.setAttribute("display", display);
				return mapping.findForward("display");
			}
		}
	}

	/** 查看玩家道具详情 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		String prop_id = request.getParameter("prop_id");
		String id = request.getParameter("id");
		String prop_display = null;
		GoodsService goodsService = new GoodsService();
		prop_display = goodsService.getPropInfoWmlMai(roleInfo.getBasicInfo()
				.getPPk(), Integer.parseInt(prop_id));
		request.setAttribute("id", id);
		request.setAttribute("display", prop_display);
		return mapping.findForward("prop_display");
	}
}
