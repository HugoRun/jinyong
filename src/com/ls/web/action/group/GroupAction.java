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
 * ����߼�
 */
public class GroupAction extends ActionBase
{
	Logger logger = Logger.getLogger("log.action");


	// �ύ�������
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
				request.getRequestDispatcher("/pubbuckaction.do?hint=" + "���͸���Ҳ�����ͬ����,����������(��)�������.").forward(request, response);
				return null;
			}
			BlacklistService blacklistService = new BlacklistService();
			int res = blacklistService.isBlacklist(Integer.parseInt(b_ppk), roleInfo.getPPk());
			if(res == 2){
				request.getRequestDispatcher("/pubbuckaction.do?hint=" + "����������ĺ�������,����������(��)�������.").forward(request, response);
				return null;
			}else if(res == 1){
				request.getRequestDispatcher("/pubbuckaction.do?hint=" + "���ڸ���ҵĺ�������,����������(��)�������.").forward(request, response);
				return null;
			}
			SceneVO sceneVO = roleInfo.getBasicInfo().getSceneInfo();
			MapVO mapVO = sceneVO.getMap();
			if(mapVO.getMapType() == MapType.TIANGUAN){
				String hint = "��������ս���,�����������!";
				request.getRequestDispatcher("/pubbuckaction.do?hint=" + hint ).forward(request, response);
				return null;
			}else{
				// �ж����B��״̬�Ƿ���Խ��н���
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
		// ����������
		String apply_hint = groupNotifyService.applyGroup(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(b_ppk));

		// ����ύ�������ɹ�����������뵯��ʽ��Ϣ����
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
			apply_hint = "������Է��ύ���������";
			msgInfo.setMsgPriority(PopUpMsgType.MESSAGE_GROUP_FIRST);
			uMsgService.sendPopUpMsg(msgInfo);
		}

		this.setHint(request, apply_hint);
		return this.dispath(request, response,"/swapaction.do?cmd=n13&pPks="+b_ppk);
	}

	// ��ӹ����б�
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

	// �����֪ͨ����
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
					|| groupNotify.getNotifyType() == GroupNotifyService.INVITE)// ���֪ͨ
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

	// ͬ�����
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String a_pk = request.getParameter("a_pk");// ��֪ͨ���
		String b_pk = request.getParameter("b_pk");// ����֪ͨ���
		String notify_type = request.getParameter("notify_type");
		logger.info("a_pk=" + a_pk + " ");
		RoleService roleService = new RoleService();
		GroupNotifyService groupNotifyService = new GroupNotifyService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		GroupService groupService = new GroupService();
		
		GroupModel group_info = groupService.getGroupInfo(Integer.parseInt(b_pk));

		// �����ж���������Ƿ�ﵽ
		if (group_info!=null)
		{
			if (group_info.isFull())
			{
				String hint = "������������";
				request.setAttribute("hint", hint);
				return mapping.findForward("group_add_hint");
			}
		}
		switch (Integer.parseInt(notify_type))
		{
			case GroupNotifyService.CREATE:
			{
				group_info = groupService.joinGroup(Integer.parseInt(a_pk),
						Integer.parseInt(b_pk));// �����¶���
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

	// �ܾ����
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		return mapping.findForward("accept_hint");
	}

	// �߳���Ա
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String member_pk = request.getParameter("member_pk");

		GroupService groupService = new GroupService();
		RoleService roleService = new RoleService();
		
		if (member_pk == null)
		{
			logger.info("member_pk����Ϊ��");
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

	// ��ɢ����
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

	// ��Ա�뿪����
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
	 * // �������ж��� public ActionForward n9(ActionMapping mapping, ActionForm form,
	 * HttpServletRequest request, HttpServletResponse response) { String
	 * notify_type = request.getParameter("notify_type"); switch
	 * (Integer.parseInt(notify_type)) { case GroupNotifyService.JOIN: {
	 * request.setAttribute("groupNotify", groupNotify); return
	 * mapping.findForward("group_hint"); } case GroupNotifyService.INVITE: {
	 * request.setAttribute("groupNotify", groupNotify); return
	 * mapping.findForward("group_hint"); } } return
	 * mapping.findForward("accept_hint"); }
	 */

	// �鿴������ҵ���Ϣ
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

		// ��ȡ�Է�������ͼƬ
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

	// ҳ����ת
	public ActionForward n11(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward("group_menu");
	}

	/**
	 * ���ҵ�ǰ���ж�����Ϣ
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
			request.setAttribute("display", "û��Ѱ�ҵ�����!");
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
	 * Ѱ�����ʱ���鿴�������г�Ա
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
			GroupService.removeGroupFromAllGroups(p_pk);//�����ж������Ƴ�����
			request.setAttribute("display", "�ö����ѽ�ɢ!");
			return mapping.findForward("display");
		}

	}

	// ������� ����
	public ActionForward n14(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String p_pk = request.getParameter("p_pk");
		GroupService groupService = new GroupService();
		
		GroupModel group_info = groupService.getGroupInfo(Integer.parseInt(p_pk));
		
		if (group_info== null)
		{
			request.setAttribute("display", "�ö��鲻���ڻ��ѽ�ɢ!");
			return mapping.findForward("display");
		}
		if (group_info.isFull())
		{
			request.setAttribute("display", "�ö�����������!");
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
			String hint = "��������ս���,�����������!";
			request.setAttribute("display", hint);
			return mapping.findForward("display");
		}
		
		RoleEntity roleInfo = roleService.getRoleInfoById(p_pk);
		String name = roleInfo.getBasicInfo().getName();
		GroupNotifyService groupNotifyService = new GroupNotifyService();
		
		// ����������
		String apply_hint = groupNotifyService.applyGroupBySearch(role_Info
				.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getPPk());

		// ����ύ�������ɹ�����������뵯��ʽ��Ϣ����
		if (apply_hint == null)
		{
			UMsgService uMsgService = new UMsgService();
			UMessageInfoVO msgInfo = new UMessageInfoVO();
			msgInfo.setMsgType(PopUpMsgType.MESSAGE_GROUP);
			msgInfo.setPPk(roleInfo.getBasicInfo().getPPk());
			msgInfo.setMsgPriority(PopUpMsgType.MESSAGE_GROUP_FIRST);
			uMsgService.sendPopUpMsg(msgInfo);
			request.setAttribute("display", "�����������" + name + "�Ķ���,�����ĵȴ��Է���Ӧ!");
			return mapping.findForward("display");
		}
		request.setAttribute("display", apply_hint);
		return mapping.findForward("display");
	}
}