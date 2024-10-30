package com.web.action.friend;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.dao.info.partinfo.PartInfoDAO;
import com.ben.vo.communion.CommunionVO;
import com.ben.vo.friend.FriendVO;
import com.ls.ben.cache.dynamic.manual.chat.ChatInfoCahe;
import com.ls.ben.vo.map.MapVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.MapType;
import com.ls.pub.constant.player.PlayerState;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.action.ActionBase;
import com.ls.web.service.group.GroupNotifyService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.system.UMsgService;
import com.pm.service.mail.MailInfoService;
import com.pub.ben.info.Expression;
import com.web.service.friend.BlacklistService;
import com.web.service.friend.FriendService;

/**
 * @author 侯浩军 功能:好友 9:40:46 AM
 */
public class FriendAction extends ActionBase
{
	/**
	 * 增加好友
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量

		String pByPk = request.getParameter("pByPk");
		String pByName = request.getParameter("pByName");
		
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("pByName", pByName);

		String hint = "";
		
		FriendService friendService = new FriendService();
		BlacklistService blacklistService = new BlacklistService();
		
		if (friendService.whetherfriend(roleInfo.getPPk(), pByPk) == false)
		{
			hint = "该玩家已经是您的好友了不需要再添加了！";
		}
		else if (friendService.friendupperlimit(roleInfo.getPPk()) == false)
		{
			hint = "您的好友数量已经达到上限！";
		}
		//如果提交玩家被加入黑名单 返回
		else if(blacklistService.whetherblacklist(Integer.parseInt(pByPk),roleInfo.getPPk()+"") == false)
		{
			hint = "对方以将您加入黑名单,您不可再加对方为好友!";
		}
		else if (Integer.parseInt(pByPk) == roleInfo.getPPk())
		{
			hint = "您不能自己加自己好友!";
		}
		else
		{
			// 如果该玩家在黑名单中 删除黑名单然后在加入好友
			if (blacklistService.whetherblacklist(roleInfo.getBasicInfo().getPPk(),pByPk) == false)
			{
				blacklistService.deleteblacklist(roleInfo.getBasicInfo().getPPk(),pByPk);
			}
			friendService.addfriend(roleInfo.getPPk(), pByPk,pByName, Time);
			hint = "您已将" + pByName + "加入您的好友列表里！";
		}
		
		this.setHint(request, hint);
		return super.dispath(request, response, "/swapaction.do?cmd=n13&pPks="+pByPk);
	}

	/**
	 * 好友List
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		FriendService friendService = new FriendService();
		QueryPage queryPage = new QueryPage();
		String page_Str = request.getParameter("page");
		int page = 0;
		if (page_Str != null)
		{
			page = Integer.parseInt(page_Str);
		}
		int size = friendService.getFriendNum(roleInfo.getBasicInfo().getPPk());
		List<FriendVO> friendlist = null;
		int pageall = 0;
		if(size!=0){
		pageall = size / queryPage.getPageSize() + (size % queryPage.getPageSize() == 0 ? 0 : 1);
		// 查询在线玩家
		friendlist = friendService.listfriend(roleInfo
				.getBasicInfo().getPPk(), page * queryPage.getPageSize(), queryPage.getPageSize());
		friendService.getFriendInMapName(friendlist);
		}
		request.setAttribute("pageall", pageall);
		request.setAttribute("page", page);
		request.setAttribute("friendlist", friendlist);
		return mapping.findForward("friendlist");
	}

	/**
	 * 好友View
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pByPk = request.getParameter("pByPk");
		
		RoleEntity other = RoleService.getRoleInfoById(pByPk);
		
		String pByName = request.getParameter("pByName");
		String page = request.getParameter("page");
		
		
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("page", page);
		request.setAttribute("pByName", pByName);
		
		request.setAttribute("other", other);
		return mapping.findForward("friendview");
	}

	/**
	 * 好友密聊
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pByPk = request.getParameter("pByPk");
		String pByName = request.getParameter("pByName");
		
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("pByName", pByName);
		return mapping.findForward("friendcolloguepage");
	}

	/**
	 * 好友密聊
	 */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量
		String pByPk = request.getParameter("pByPk");
		String pByName = request.getParameter("pByName");
		String title = request.getParameter("title");

		/** TODO:如果有该玩家 如果没有在线则直接发送玩家邮箱 */
		int type = 5;

		// 得到当前玩家信息
		String hint = null;
		PlayerService playerService = new PlayerService();

		hint = playerService.checkRoleState(Integer.parseInt(pByPk),
				PlayerState.TALK);
		if (hint != null)
		{
			String hintaa = hint + ",系统已经帮您转发至他(她)的邮箱中!";
			MailInfoService mailInfoService = new MailInfoService();
			String mailtitle = "来自好友" + roleInfo.getBasicInfo().getName() + "的邮件";
			mailInfoService.sendMail(Integer.parseInt(pByPk), roleInfo.getBasicInfo().getPPk(), 1, mailtitle, title);
			request.setAttribute("hint", hintaa);
			request.setAttribute("pByPk", pByPk);
			request.setAttribute("pByName", pByName);
			return mapping.findForward("friendcollogue");
		}
		
		
		// 执行插入公共聊天记录 c_pk,p_pk,p_name,p_pk_by,p_name_by,c_title,c_type,create_time
		CommunionVO communionVO = new CommunionVO();
		communionVO.setPPk(roleInfo.getBasicInfo().getPPk());
		communionVO.setPName(roleInfo.getBasicInfo().getName());
		communionVO.setPPkBy(Integer.parseInt(pByPk));
		communionVO.setPNameBy(pByName);
		communionVO.setCTitle(title);
		communionVO.setCType(type);
		communionVO.setCreateTime(Time);
		ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
		publicChatInfoCahe.put(communionVO);
		
		/*uPrivatelyDAO.getUPrivatelyAdd(roleInfo.getBasicInfo().getPPk()
				+ "", dao
				.getPartName(roleInfo.getBasicInfo().getPPk() + ""), pByPk
				+ "", pByName, title, type + "", Time);*/
		hint = "消息已经发送出去了!";
		
		/*RoleService roleServiceother = new RoleService();
		RoleEntity b_role_info = roleServiceother.getRoleInfoById(pByPk + "");*/

		/*if (b_role_info != null)
		{
			
		}
		else
		{
			hint = "您的好友没有在线!系统已经帮您转发至他(她)的邮箱中!";
			// //System.out.println("加邮箱");
			MailInfoService mailInfoService = new MailInfoService();
			String mailtitle = "来自您的好友" + roleInfo.getBasicInfo().getName()
					+ "的邮件";
			mailInfoService.sendMail(Integer.parseInt(pByPk), roleInfo
					.getBasicInfo().getPPk(), 1, mailtitle, title);
		}*/
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("pByName", pByName);
		request.setAttribute("hint", hint);
		return mapping.findForward("friendcollogue");
	}

	/**
	 * 删除好友
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pByPk = request.getParameter("pByPk");
		String pByName = request.getParameter("pByName");
		String page = request.getParameter("page");

		request.setAttribute("page", page);
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("pByName", pByName);
		return mapping.findForward("frienddelete");
	}

	/**
	 * 删除好友
	 */
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		String pByPk = request.getParameter("pByPk");
		String pByName = request.getParameter("pByName");
		FriendService friendService = new FriendService();
		FriendVO fv = friendService.viewfriend(roleInfo.getBasicInfo().getPPk(), pByPk);
		if(fv.getRelation()==1){
			request.setAttribute("message", "请先解除结义关系");
		}else if(fv.getRelation()==2){
			request.setAttribute("message", "请先解除婚姻关系");
		}else{
		friendService.deletefriend(roleInfo.getBasicInfo().getPPk(), pByPk);
		}
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("pByName", pByName);
		return mapping.findForward("frienddeleteok");
	}

	/**
	 * 邀请组队
	 */
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		String pByPk = request.getParameter("pByPk");
		String pByName = request.getParameter("pByName");
		
		SceneVO sceneVO = roleInfo.getBasicInfo().getSceneInfo();
		MapVO mapVO = sceneVO.getMap();
		if(mapVO.getMapType() == MapType.TIANGUAN){
			String hint = "您正在挑战天关,不可申请组队!";
			super.setHint(request, hint);
			request.setAttribute("pByPk", pByPk);
			request.setAttribute("pByName", pByName);
			return this.n3(mapping, form, request, response);
		}
		
		PlayerService playerService = new PlayerService();
		String hint = playerService.isRoleState(Integer.parseInt(pByPk), 2);
		if(hint != null){
			super.setHint(request, hint);
			request.setAttribute("pByPk", pByPk);
			request.setAttribute("pByName", pByName);
			return this.n3(mapping, form, request, response);
		} 
		GroupNotifyService groupNotifyService = new GroupNotifyService();
		
		
		// 添加组队申请
		String apply_hint = groupNotifyService.applyGroup(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(pByPk));
		
		//如果提交组队申请成功，在这里插入弹出式消息内容
		if(apply_hint == null)
		{
			RoleEntity pByPk_roleInfo = RoleService.getRoleInfoById(pByPk);
    		UMsgService uMsgService = new UMsgService();
    		UMessageInfoVO msgInfo = new UMessageInfoVO();
    		msgInfo.setMsgType(PopUpMsgType.MESSAGE_GROUP);
    		int group_id = pByPk_roleInfo.getStateInfo().getGroup_id();
    		if(group_id != -1){
    				msgInfo.setPPk(group_id);
    		} else{
    				msgInfo.setPPk(Integer.parseInt(pByPk));
    		}
    		apply_hint = "您已向对方提交过组队申请";
    		msgInfo.setMsgPriority(PopUpMsgType.MESSAGE_GROUP_FIRST);
    		uMsgService.sendPopUpMsg(msgInfo);
		}
		
		super.setHint(request, apply_hint);
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("pByName", pByName);
		return this.n3(mapping, form, request, response);
	}

	/**
	 * 增加好友
	 */
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward("friendaddpage");
	}

	/**
	 * 增加好友
	 */
	public ActionForward n10(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量

		PartInfoDAO partInfoDAO = new PartInfoDAO();
		String pByName = request.getParameter("pByName");
		int pByPk = partInfoDAO.getPartPk(pByName);
		FriendService friendService = new FriendService();
		String hint = null;
		// 屏蔽特殊字符
		Pattern p = Pattern.compile(Expression.chinese_regexp);
		Matcher m = p.matcher(pByName);
		boolean b = m.matches();
		if (b == false)
		{
			hint = "玩家名字不正确!";
			request.setAttribute("hint", hint);
			return mapping.findForward("friendaddpageOK");
		}
		if (partInfoDAO.getPartTypeListName(pByName) == false)
		{
			hint = "该玩家不存在!";
			request.setAttribute("hint", hint);
			return mapping.findForward("friendaddpageOK");
		}
		if(partInfoDAO.getIsNewState(pByName))
		{
			hint = "该玩家处于新手引导阶段不可加为好友！";
			request.setAttribute("hint", hint);
			return mapping.findForward("friendaddpageOK");
		}
		if (friendService.whetherfriend(roleInfo.getBasicInfo().getPPk(), pByPk
				+ "") == false)
		{
			hint = "该玩家已经是您的好友了不需要再添加了!";
			request.setAttribute("hint", hint);
			return mapping.findForward("friendaddpageOK");
		}
		if (friendService.friendupperlimit(roleInfo.getBasicInfo().getPPk()) == false)
		{
			hint = "您的好友数量已经达到上限!";
			request.setAttribute("hint", hint);
			return mapping.findForward("friendaddpageOK");
		}
		if (pByPk == roleInfo.getBasicInfo().getPPk())
		{
			hint = "您不能自己加自己好友!";
			request.setAttribute("hint", hint);
			return mapping.findForward("friendaddpageOK");
		}
		// 如果该玩家在黑名单中 删除黑名单然后在加入好友
		BlacklistService blacklistService = new BlacklistService();
		
		if(blacklistService.whetherblacklist(pByPk,roleInfo.getBasicInfo().getPPk()+"") == false)
		{
			hint = "对方以将您加入黑名单,您不可再加对方为好友!";
			request.setAttribute("hint", hint);
			return mapping.findForward("friendaddpageOK");
		}
		
		if (blacklistService.whetherblacklist(roleInfo.getBasicInfo().getPPk(),
				pByPk + "") == false)
		{
			blacklistService.deleteblacklist(roleInfo.getBasicInfo().getPPk(),
					pByPk + "");
		}

		friendService.addfriend(roleInfo.getBasicInfo().getPPk(), pByPk + "",
				pByName, Time);
		hint = "您已将" + pByName + "加入您的好友列表里！";
		request.setAttribute("hint", hint);
		return mapping.findForward("friendaddpageOK");
	}
}
