package com.web.action.friend;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.vo.friend.BlacklistVO;
import com.ben.vo.friend.FriendVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.web.action.ActionBase;
import com.ls.web.service.player.RoleService;
import com.web.service.friend.BlacklistService;
import com.web.service.friend.FriendService;

/**
 * @author 侯浩军 功能:黑名单 9:40:46 AM
 */
public class BlacklistAction extends ActionBase
{
	/**
	 * 增加黑名单
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量

		String pByPk = request.getParameter("pByPk");
		String pByName = request.getParameter("pByName");
        FriendService fs = new FriendService();
        FriendVO fv = fs.viewfriend(roleInfo.getBasicInfo().getPPk(), pByPk);
        if(fv!=null){
        	if(fv.getRelation()==1){
        		String pa = "请先解除结义关系";
    			request.setAttribute("pa", pa);
    			request.setAttribute("pByPk", pByPk);
    			request.setAttribute("pByName", pByName);
    			return mapping.findForward("friendadd");
        	}else if(fv.getRelation()==2){
        		String pa = "请先解除婚姻关系";
    			request.setAttribute("pa", pa);
    			request.setAttribute("pByPk", pByPk);
    			request.setAttribute("pByName", pByName);
    			return mapping.findForward("friendadd");
        	}
        }
		BlacklistService blacklistService = new BlacklistService();
		if (blacklistService.whetherblacklist(roleInfo.getBasicInfo().getPPk(),
				pByPk) == false)
		{
			String pa = "该玩家已经在您的黑名单里了不需要再添加了！";
			request.setAttribute("pa", pa);
			request.setAttribute("pByPk", pByPk);
			request.setAttribute("pByName", pByName);
			return mapping.findForward("friendadd");

		}
		if (blacklistService.blacklistupperlimit(roleInfo.getBasicInfo()
				.getPPk()) == false)
		{
			String pa = "您的黑名单数量已经达到上限！";
			request.setAttribute("pa", pa);
			request.setAttribute("pByPk", pByPk);
			request.setAttribute("pByName", pByName);
			return mapping.findForward("friendadd");
		}
		if (Integer.parseInt(pByPk) == roleInfo.getBasicInfo().getPPk())
		{
			String pa = "您不能自己加自己黑名单!";
			request.setAttribute("pa", pa);
			request.setAttribute("pByPk", pByPk);
			request.setAttribute("pByName", pByName);
			return mapping.findForward("friendadd");
		}
		
		// 删除好友列表
		FriendService friendService = new FriendService();
		friendService.deletefriend(roleInfo.getBasicInfo().getPPk(), pByPk);
		// 加入黑名单
		blacklistService.addblacklist(roleInfo.getBasicInfo().getPPk(), pByPk,
				pByName, Time);
		String pa = "您已将" + pByName + "加入您的黑名单列表里！";
		request.setAttribute("pa", pa);
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("pByName", pByName);
		return mapping.findForward("blacklistadd");
	}

	/**
	 * 增加黑名单
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
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
		
		FriendService fs = new FriendService();
		BlacklistService blacklistService = new BlacklistService();
		FriendVO fv = fs.viewfriend(roleInfo.getPPk(), pByPk);
        if(fv!=null){
        	if(fv.getRelation()==1){
        		hint = "请先解除结义关系";
        	}else if(fv.getRelation()==2){
        		hint = "请先解除婚姻关系";
        	}
        }
        else if (blacklistService.whetherblacklist(roleInfo.getPPk(),pByPk) == false)
		{
			hint = "该玩家已经在您的黑名单里了不需要再添加了！";

		}
        else if (blacklistService.blacklistupperlimit(roleInfo.getPPk()) == false)
		{
			hint = "您的黑名单数量已经达到上限！";
		}
        else if (Integer.parseInt(pByPk) == roleInfo.getPPk())
		{
			hint = "您不能自己加自己黑名单!";
		}
        else
        {
        	// 删除好友列表
    		FriendService friendService = new FriendService();
    		friendService.deletefriend(roleInfo.getBasicInfo().getPPk(), pByPk);
    		// 加入黑名单
    		blacklistService.addblacklist(roleInfo.getBasicInfo().getPPk(), pByPk,pByName, Time);
    		hint = "您已将" + pByName + "加入您的黑名单列表里！";
        }
		this.setHint(request, hint);
		return super.dispath(request, response, "/swapaction.do?cmd=n13&pPks="+pByPk);
	}

	/**
	 * 黑名单List
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		BlacklistService blacklistService = new BlacklistService();
		List<BlacklistVO> list1 = blacklistService.listblacklist(roleInfo
				.getBasicInfo().getPPk());
		List<BlacklistVO> list = null;
		QueryPage queryPage = new QueryPage();
		String page_Str = request.getParameter("page");
		int pagenum = 0;
		if (page_Str != null)
		{
			pagenum = Integer.parseInt(page_Str);
		}
		int allnum = list1.size();
		int pageall = 0;
		if (allnum != 0)
		{
			pageall = allnum / queryPage.getPageSize()
					+ (allnum % queryPage.getPageSize() == 0 ? 0 : 1);

			list = blacklistService.listpage(roleInfo
					.getBasicInfo().getPPk(), pagenum, queryPage.getPageSize());
		}
		request.setAttribute("pagenum", pagenum);
		request.setAttribute("pageall", pageall);
		request.setAttribute("list", list);
		return mapping.findForward("blacklist");
	}

	/**
	 * 踢出黑名单
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		String pByPk = request.getParameter("pByPk");
		BlacklistService blacklistService = new BlacklistService();
		blacklistService.deleteblacklist(roleInfo.getBasicInfo().getPPk(),
				pByPk);
		return mapping.findForward("blackdelete");
	}
}
