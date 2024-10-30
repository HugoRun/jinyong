package com.ls.web.action.group;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.ben.cache.staticcache.map.SceneCache;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.vo.info.group.GroupNotifyVO;
import com.ls.ben.vo.map.MapVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.group.GroupModel;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.MapType;
import com.ls.pub.constant.player.PlayerState;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.action.ActionBase;
import com.ls.web.service.group.GroupNotifyService;
import com.ls.web.service.group.GroupService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.system.UMsgService;
import com.pm.service.pic.PicService;
import com.web.service.friend.BlacklistService;

/**
 * @author ls
 * 组队逻辑
 */
public class GroupAction extends ActionBase
{
	Logger logger = Logger.getLogger("log.action");


	// 提交组队申请
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		
		String b_ppk = request.getParameter("b_ppk");

		RoleEntity b_ppk_roleInfo = RoleService.getRoleInfoById(b_ppk);
		PlayerService playerService = new PlayerService();
		try
		{
			String aRaceName= roleInfo.getBasicInfo().getRaceName();
			String bRaceName=b_ppk_roleInfo.getBasicInfo().getRaceName();
			if(!aRaceName.equals(bRaceName))
			{
				request.getRequestDispatcher("/pubbuckaction.do?hint=" + "您和该玩家不属于同种族,您不能与他(她)进行组队.").forward(request, response);
				return null;
			}
			BlacklistService blacklistService = new BlacklistService();
			int res = blacklistService.isBlacklist(Integer.parseInt(b_ppk), roleInfo.getPPk());
			if(res == 2){
				request.getRequestDispatcher("/pubbuckaction.do?hint=" + "该玩家在您的黑名单中,您不能与他(她)进行组队.").forward(request, response);
				return null;
			}else if(res == 1){
				request.getRequestDispatcher("/pubbuckaction.do?hint=" + "您在该玩家的黑名单中,您不能与他(她)进行组队.").forward(request, response);
				return null;
			}
			SceneVO sceneVO = roleInfo.getBasicInfo().getSceneInfo();
			MapVO mapVO = sceneVO.getMap();
			if(mapVO.getMapType() == MapType.TIANGUAN){
				String hint = "您正在挑战天关,不可申请组队!";
				request.getRequestDispatcher("/pubbuckaction.do?hint=" + hint ).forward(request, response);
				return null;
			}else{
				// 判断玩家B的状态是否可以进行交易
				String hint = playerService.checkRoleState(Integer.parseInt(b_ppk),PlayerState.GROUP);
				if (hint != null)
				{
					request.getRequestDispatcher("/pubbuckaction.do?hint=" + hint).forward(request, response);
					return null;
				}	
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		
		GroupNotifyService groupNotifyService = new GroupNotifyService();
		// 添加组队申请
		String apply_hint = groupNotifyService.applyGroup(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(b_ppk));

		// 如果提交组队申请成功，在这里插入弹出式消息内容
		if (apply_hint == null)
		{
			UMsgService uMsgService = new UMsgService();
			UMessageInfoVO msgInfo = new UMessageInfoVO();
			msgInfo.setMsgType(PopUpMsgType.MESSAGE_GROUP);
			GroupModel group_info = b_ppk_roleInfo.getStateInfo().getGroupInfo();
			if ( group_info!=null )
			{
				msgInfo.setPPk(group_info.getCaptianID());
			}
			else
			{
				msgInfo.setPPk(Integer.parseInt(b_ppk));
			}
			apply_hint = "您已向对方提交过组队申请";
			msgInfo.setMsgPriority(PopUpMsgType.MESSAGE_GROUP_FIRST);
			uMsgService.sendPopUpMsg(msgInfo);
		}

		this.setHint(request, apply_hint);
		return this.dispath(request, response,"/swapaction.do?cmd=n13&pPks="+b_ppk);
	}

	// 组队管理列表
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		GroupService groupService = new GroupService();
		
		RoleEntity roleInfo = this.getRoleEntity(request);
		
		request.setAttribute("me", roleInfo);
		
		int p_pk = roleInfo.getPPk();

		GroupModel group_info = groupService.getGroupInfo(p_pk);

		if ( group_info!=null)
		{
			request.setAttribute("group_info", group_info);
			
			if ( group_info.getCaptianID() == p_pk )
			{
				return mapping.findForward("captain_group_list");
			}
			else
			{
				return mapping.findForward("member_group_list");
			}
		}
		else
		{
			return mapping.findForward("null_group_list");
		}

	}

	// 对组队通知处理
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		GroupNotifyService groupNotifyService = new GroupNotifyService();
		GroupNotifyVO groupNotify = groupNotifyService.getGroupNotify(roleInfo
				.getBasicInfo().getPPk());

		if (groupNotify != null)
		{
			groupNotifyService.deleteNotify(groupNotify.getNPk());
			if (groupNotify.getNotifyType() == GroupNotifyService.CREATE
					|| groupNotify.getNotifyType() == GroupNotifyService.JOIN
					|| groupNotify.getNotifyType() == GroupNotifyService.INVITE)// 组队通知
			{
				request.setAttribute("notify_type", groupNotify.getNotifyType()
						+ "");
				request.setAttribute("groupNotify", groupNotify);
				return mapping.findForward("accept_hint");
			}
			else
				if (groupNotify.getNotifyType() == GroupNotifyService.GROUPHINT)
				{
					return mapping.findForward("group_hint");
				}
		}
		return this.n2(mapping, form, request, response);
	}

	// 同意组队
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String a_pk = request.getParameter("a_pk");// 被通知玩家
		String b_pk = request.getParameter("b_pk");// 创建通知玩家
		String notify_type = request.getParameter("notify_type");
		logger.info("a_pk=" + a_pk + " ");
		RoleService roleService = new RoleService();
		GroupNotifyService groupNotifyService = new GroupNotifyService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		GroupService groupService = new GroupService();
		
		GroupModel group_info = groupService.getGroupInfo(Integer.parseInt(b_pk));

		// 首先判断组队人数是否达到
		if (group_info!=null)
		{
			if (group_info.isFull())
			{
				String hint = "队伍人数已满";
				request.setAttribute("hint", hint);
				return mapping.findForward("group_add_hint");
			}
		}
		switch (Integer.parseInt(notify_type))
		{
			case GroupNotifyService.CREATE:
			{
				group_info = groupService.joinGroup(Integer.parseInt(a_pk),
						Integer.parseInt(b_pk));// 创建新队伍
				groupNotifyService.clareNotify(Integer.parseInt(a_pk));
				groupNotifyService.clareNotify(Integer.parseInt(b_pk));
				break;
			}
			case GroupNotifyService.JOIN:
			{
				group_info = groupService.joinGroup(Integer.parseInt(b_pk),
						Integer.parseInt(a_pk));
				groupNotifyService.clareNotify(Integer.parseInt(a_pk));
				groupNotifyService.clareNotify(Integer.parseInt(b_pk));
				break;
			}
			case GroupNotifyService.INVITE:
			{
				group_info = groupService.joinGroup(Integer.parseInt(a_pk),
						Integer.parseInt(b_pk));
				groupNotifyService.clareNotify(Integer.parseInt(a_pk));
				groupNotifyService.clareNotify(Integer.parseInt(b_pk));
				break;
			}
		}
		
		if ( group_info!=null )
		{
			request.setAttribute("group_info", group_info);
			if ( group_info.getCaptianID() == roleInfo.getBasicInfo().getPPk())
			{
				return mapping.findForward("captain_group_list");
			}
			else
			{
				return mapping.findForward("member_group_list");
			}
		}
		else
		{
			return mapping.findForward("null_group_list");
		}

	}

	// 拒绝组队
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		return mapping.findForward("accept_hint");
	}

	// 踢出队员
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String member_pk = request.getParameter("member_pk");

		GroupService groupService = new GroupService();
		RoleService roleService = new RoleService();
		
		if (member_pk == null)
		{
			logger.info("member_pk参数为空");
		}
		else
		{
			groupService.kickMember(Integer.parseInt(member_pk));
		}
		
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		GroupModel group_info = roleInfo.getStateInfo().getGroupInfo();

		if( group_info!=null )
		{
			request.setAttribute("group_info", group_info);
			return mapping.findForward("captain_group_list");
		}
		else
		{
			return mapping.findForward("null_group_list");
		}
	}

	// 解散队伍
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		GroupService groupService = new GroupService();
		groupService.disband(roleInfo.getBasicInfo().getPPk());
		return mapping.findForward("disband_hint");
	}

	// 队员离开队伍
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		GroupService groupService = new GroupService();
		groupService.leaveGroup(roleInfo.getBasicInfo().getPPk());
		return mapping.findForward("leave_hint");
	}

	/*
	 * // 加入已有队伍 public ActionForward n9(ActionMapping mapping, ActionForm form,
	 * HttpServletRequest request, HttpServletResponse response) { String
	 * notify_type = request.getParameter("notify_type"); switch
	 * (Integer.parseInt(notify_type)) { case GroupNotifyService.JOIN: {
	 * request.setAttribute("groupNotify", groupNotify); return
	 * mapping.findForward("group_hint"); } case GroupNotifyService.INVITE: {
	 * request.setAttribute("groupNotify", groupNotify); return
	 * mapping.findForward("group_hint"); } } return
	 * mapping.findForward("accept_hint"); }
	 */

	// 查看其他玩家的信息
	public ActionForward n10(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());

		String bPpk = request.getParameter("b_pk");
		String a_pk = request.getParameter("a_pk");
		String notify_type = request.getParameter("notify_type");

		request.setAttribute("notify_type", notify_type);
		request.setAttribute("a_pk", a_pk);
		request.setAttribute("b_pk", bPpk);

		// 获取对方的形象图片
		PicService picService = new PicService();
		String playerPic = picService.getPlayerPicStr(roleInfo, bPpk);
		request.setAttribute("playerPicB", playerPic);

		RoleEntity b_role_info = roleService.getRoleInfoById(bPpk);
		
		if( b_role_info!=null )
		{
			PlayerEquipDao playerEquipDao = new PlayerEquipDao();
			List equip_list = playerEquipDao.getEquipListOnBody(Integer.parseInt(bPpk));
			request.setAttribute("equip_list", equip_list);
			request.setAttribute("b_basic_info", b_role_info.getBasicInfo());
		}
		
		return mapping.findForward("player_display");
	}

	// 页面跳转
	public ActionForward n11(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward("group_menu");
	}

	/**
	 * 需找当前所有队伍信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n12(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		QueryPage queryPage = new QueryPage();
		String page_Str = request.getParameter("page");
		int page = 0;
		if (page_Str != null && !page_Str.equals("") && !page_Str.equals("null"))
		{
			page = Integer.parseInt(page_Str);
		}
		
		int group_num = GroupService.getGroupNum();
		int pageall = group_num/ queryPage.getPageSize()+ (group_num% queryPage.getPageSize() == 0 ? 0 : 1);
		
		List<GroupModel> groups = GroupService.getGroupListByPage(page);
		if (groups == null || groups.size() == 0)
		{
			request.setAttribute("display", "没有寻找到队伍!");
			return mapping.findForward("hint");
		}
		else
		{
			request.setAttribute("pageall", pageall);
			request.setAttribute("page", page);
			request.setAttribute("groups", groups);
			return mapping.findForward("group_list");
		}

	}

	/**
	 * 寻找组队时，查看队伍所有成员
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n13(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String p_pk = request.getParameter("p_pk");
		String page = request.getParameter("page");

		GroupService groupService = new GroupService();
		
		GroupModel group_info = groupService.getGroupInfo(Integer.parseInt(p_pk));

		if (group_info!=null)
		{
			request.setAttribute("group_info", group_info);
			request.setAttribute("page", page);
			return mapping.findForward("group_member");
		}
		else
		{
			GroupService.removeGroupFromAllGroups(p_pk);//从所有队伍中移除队伍
			request.setAttribute("display", "该队伍已解散!");
			return mapping.findForward("display");
		}

	}

	// 加入队伍 返回
	public ActionForward n14(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String p_pk = request.getParameter("p_pk");
		GroupService groupService = new GroupService();
		
		GroupModel group_info = groupService.getGroupInfo(Integer.parseInt(p_pk));
		
		if (group_info== null)
		{
			request.setAttribute("display", "该队伍不存在或已解散!");
			return mapping.findForward("display");
		}
		if (group_info.isFull())
		{
			request.setAttribute("display", "该队伍人数已满!");
			return mapping.findForward("display");
		}
		RoleService roleService = new RoleService();
		RoleEntity role_Info = roleService.getRoleInfoBySession(request
				.getSession());
		String scene_id = role_Info.getBasicInfo().getSceneId();
		SceneCache sc = new SceneCache();
		SceneVO sceneVO = sc.getById(scene_id);
		MapVO mapVO = sceneVO.getMap();
		if(mapVO.getMapType() == MapType.TIANGUAN){
			String hint = "您正在挑战天关,不可申请组队!";
			request.setAttribute("display", hint);
			return mapping.findForward("display");
		}
		
		RoleEntity roleInfo = roleService.getRoleInfoById(p_pk);
		String name = roleInfo.getBasicInfo().getName();
		GroupNotifyService groupNotifyService = new GroupNotifyService();
		
		// 添加组队申请
		String apply_hint = groupNotifyService.applyGroupBySearch(role_Info
				.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getPPk());

		// 如果提交组队申请成功，在这里插入弹出式消息内容
		if (apply_hint == null)
		{
			UMsgService uMsgService = new UMsgService();
			UMessageInfoVO msgInfo = new UMessageInfoVO();
			msgInfo.setMsgType(PopUpMsgType.MESSAGE_GROUP);
			msgInfo.setPPk(roleInfo.getBasicInfo().getPPk());
			msgInfo.setMsgPriority(PopUpMsgType.MESSAGE_GROUP_FIRST);
			uMsgService.sendPopUpMsg(msgInfo);
			request.setAttribute("display", "你已申请加入" + name + "的队伍,请耐心等待对方反应!");
			return mapping.findForward("display");
		}
		request.setAttribute("display", apply_hint);
		return mapping.findForward("display");
	}
}