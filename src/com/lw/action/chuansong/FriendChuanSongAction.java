package com.lw.action.chuansong;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ben.lost.CompassService;
import com.ben.vo.friend.FriendVO;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.room.RoomService;
import com.web.service.friend.FriendService;

public class FriendChuanSongAction extends DispatchAction
{
	// 判断物品是否可以使用
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String w_type = (String) request.getAttribute("w_type");
		String pg_pk = (String) request.getAttribute("pg_pk");
		String prop_id = (String) request.getAttribute("goods_id");
		String goods_type = (String) request.getAttribute("goods_type");
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		String scene_id = roleInfo.getBasicInfo().getSceneId();
		RoomService roomService = new RoomService();
		String hint = roomService.isCarryedOut(Integer.parseInt(scene_id));
		if (hint != null)
		{
			request.setAttribute("display", "对不起,该玩家地点不允许传出!");
			return mapping.findForward("display");
		}
		else
		{
			FriendService friendService = new FriendService();
			QueryPage queryPage = new QueryPage();
			String page_Str = request.getParameter("page");
			int page = 0;
			if (page_Str != null)
			{
				page = Integer.parseInt(page_Str);
			}
			int size = friendService.getFriendNumOnline(roleInfo.getBasicInfo()
					.getPPk());
			List<FriendVO> friendlist = null;
			int pageall = 0;
			if (size != 0)
			{
				pageall = size / queryPage.getPageSize()
						+ (size % queryPage.getPageSize() == 0 ? 0 : 1);
				// 查询在线玩家
				friendlist = friendService.listfriendOnline(roleInfo
						.getBasicInfo().getPPk(), page
						* queryPage.getPageSize(), queryPage.getPageSize());
				friendService.getFriendInMapName(friendlist);
			}

			if (friendlist == null)
			{
				request.setAttribute("display", "对不起,您没有好友在线!");
				return mapping.findForward("display");
			}
			else
			{
				request.setAttribute("w_type", w_type);
				request.setAttribute("pg_pk", pg_pk);
				request.setAttribute("goods_id", prop_id);
				request.setAttribute("goods_type", goods_type);
				request.setAttribute("pageall", pageall);
				request.setAttribute("page", page);
				request.setAttribute("friendlist", friendlist);
				return mapping.findForward("friendslist");
			}
		}
	}

	// 判断玩家选择的好友是否可以传送过去
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String p_pk = request.getParameter("p_pk");
		if (p_pk == null)
		{
			request.setAttribute("display", "好友错误!");
			return mapping.findForward("display");
		}
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoById(p_pk);
		if (roleInfo == null || roleInfo.isOnline()==false)
		{
			request.setAttribute("display", "好友不在线或已经下线!");
			return mapping.findForward("display");
		}
		FriendService friendService = new FriendService();
		RoleEntity role_info = roleService.getRoleInfoBySession(request
				.getSession());
		if (friendService.whetherfriend(Integer.parseInt(p_pk), role_info
				.getBasicInfo().getPPk()
				+ "") == true)
		{
			request.setAttribute("display", "对方没有加您为好友,不能使用该物品!");
			return mapping.findForward("display");
		}
		String scene_id = roleInfo.getBasicInfo().getSceneId();
		RoomService roomService = new RoomService();
		String hint = roomService.isCarryedIn(Integer.parseInt(scene_id));
		if (hint != null)
		{
			String w_type = (String) request.getParameter("w_type");
			String pg_pk = (String) request.getParameter("pg_pk");
			String prop_id = (String) request.getParameter("goods_id");
			String goods_type = (String) request.getParameter("goods_type");
			request.setAttribute("w_type", w_type);
			request.setAttribute("pg_pk", pg_pk);
			request.setAttribute("goods_id", prop_id);
			request.setAttribute("goods_type", goods_type);
			request.setAttribute("display", "对不起,该玩家地点不允许传入!");
			return mapping.findForward("hint");
		}
		else
		{
			String pg_pk = (String) request.getParameter("pg_pk");
			PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
			int p = 0;
			try
			{
				p = Integer.parseInt(pg_pk);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				request.setAttribute("display", "物品使用错误!");
				return mapping.findForward("display");

			}
			if (p == 0)
			{
				request.setAttribute("display", "物品使用错误!");
				return mapping.findForward("display");
			}
			PlayerPropGroupVO propGroup = propGroupDao.getByPgPk(p);
			role_info.getBasicInfo().updateSceneId(scene_id + "");
			CompassService.removeMiJing(roleInfo.getBasicInfo().getPPk(), propGroup.getPropType());//删除秘境地图
			GoodsService goodsService = new GoodsService();
			goodsService.removeProps(propGroup, 1);
			request.setAttribute("display", "您使用了" + propGroup.getPropName()
					+ "传送到好友" + roleInfo.getBasicInfo().getName() + "的所在地!");
			return mapping.findForward("display");
		}
	}
}
