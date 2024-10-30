package com.pm.action.forum;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.cache.staticcache.forbid.ForBidCache;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.player.PlayerState;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.player.RoleService;
import com.pm.service.forum.ForumClassService;
import com.pm.service.forum.ForumRevertService;
import com.pm.service.forum.ForumService;
import com.pm.vo.forum.ForumBean;
import com.pm.vo.forum.ForumClassBean;
import com.pm.vo.forum.ForumRevertBean;
import com.pub.ben.info.Expression;
import com.web.service.friend.BlacklistService;
import com.web.service.friend.FriendService;


public class ForumAction extends DispatchAction 
{

	Logger logger = Logger.getLogger("log.action");
	
	// ��̳�б�
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		ForumClassService fs=new ForumClassService();
		//�������״̬
		roleInfo.getStateInfo().setCurState(PlayerState.FORUM);
		List<ForumClassBean> forumclassList =fs.getAllForumClass();
		request.setAttribute("forumclassList", forumclassList);
		request.setAttribute("uPk", roleInfo.getBasicInfo().getUPk()+"");
		return mapping.findForward("forumclassList");
	}
	
	// ��̳�����б�
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String uPk = roleInfo.getBasicInfo().getUPk()+"";
		
		String page_no_str = request.getParameter("pageNo");
		int page_no = 1;
		String classId = request.getParameter("classId");
		logger.info("page_no_str="+page_no_str);
		try {
    		if( page_no_str==null || page_no_str.equals("") || page_no_str.equals("null")) {
    			page_no = 1;
    		} else {
    			page_no = Integer.parseInt(page_no_str);
    		}
		} catch (Exception e) {
			page_no = 1;
		}
		
		if ( page_no <= 0) {
			page_no = 1;
		}
		
		ForumService fs=new ForumService(); 
		QueryPage forum_page = fs.getAllForumByClassId(Integer.valueOf(classId),page_no);
		List<String> managerList = fs.getManagerList(classId);
		boolean isManager = fs.isManager(roleInfo.getBasicInfo().getPPk());
		
		//logger.info("forumlist.size()="+forum_page.getResult());
		ForumClassService fcs=new ForumClassService();
		ForumClassBean fcvo = fcs.getByID(Integer.valueOf(classId));
		
		if ( page_no > forum_page.getPageSize()) {
			page_no = forum_page.getPageSize();
		}
		
		request.setAttribute("isManager", isManager);
		request.setAttribute("managerList", managerList);
		request.setAttribute("forumClassBean", fcvo);
		request.setAttribute("forum_page", forum_page);
		request.setAttribute("uPk", uPk);
		request.setAttribute("pageNo",page_no+"");
		
		return mapping.findForward("indexList");
	}
	
	// ���˷��Դ���
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//���ǻص����ʱ��ҳ��
		//request.setAttribute("classPageNo", request.getParameter("classPageNo")+"");
		
		//DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		String title=request.getParameter("title").trim();
	 	String content=request.getParameter("content");
	 	String classId = request.getParameter("classId").trim();
	 	logger.info("title="+title+" ,content="+content+" ,classId="+classId);
	 	
	 	ForumService fas=new ForumService();
	 	
	 	String hint = null;
	 	if(content == null || content.trim().equals(""))
	 	{
	 		hint = "���ݲ���Ϊ��!<br/>";
	 	} else {
	 		hint = fas.haveSameContent(roleInfo.getBasicInfo().getPPk(),content);
	 	}
	 	int lengt = content.trim().length();
	 	if(content.trim().length() > 500 || title.length() > 15) {
	 		hint = "���ı�������ݳ�����������!";
	 	}
	 	if(title == null || title.trim().equals("")) {
	 		hint = "���ⲻ��Ϊ��!<br/>";
	 	} else {
	 		hint = fas.haveSameContent(roleInfo.getBasicInfo().getPPk(),title);
	 	}
	 	if(fas.isInTenMinute(roleInfo.getBasicInfo().getPPk())) {
	 		hint = "���η���ʱ����δ��ʮ����!";
	 	}
	 	if (fas.checkForbid(roleInfo.getBasicInfo().getPPk())) {
	 		hint = "�Բ���,�������ڱ���ֹ����״̬!���Ժ��ٷ�!";
	 	}
	 	
	 	
	 	if(hint != null && !hint.equals("")) {
	 		try {
	 			request.setAttribute("hint", hint);
				request.getRequestDispatcher("/jsp/forum/add_forum_content.jsp").forward(request,response);
	 		 	return null;
	 		} catch (ServletException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}else {
			logger.info("content="+content);
			
			ForumBean wab=new ForumBean();
			int flag = Expression.hasPublish(content);
			if(flag == -1) {
				request.setAttribute("title", title);
				String hint1 = "�����������зǷ��ַ������������룡";
				request.setAttribute("hint", hint1);
				return mapping.findForward("reput_content");
			}
			
			// ����Ƿ��н�ֹ�ؼ���
			if (Expression.hasForbidChar(title,ForBidCache.FORBIDCOMM))
			{
				request.setAttribute("title", title);
				String hint1 = "�Բ������ķ����а�����ֹ�ַ�!";
				request.setAttribute("hint", hint1);
				return mapping.findForward("reput_content");
			}
			
			wab.setTitle(StringUtil.gbToISO(title));
			wab.setContent(StringUtil.gbToISO(content));
			wab.setClassID(Integer.valueOf(classId));
			wab.setUserID(roleInfo.getBasicInfo().getPPk());
			PartInfoDao partdao = new PartInfoDao();
			//String pName = partdao.getNameByPpk(userTempBean.getPPk());
			wab.setUserName(roleInfo.getBasicInfo().getName());
			fas.addForum(wab);
		}
		 
	 		ForumService fs=new ForumService(); 
	 		//String page_no_str = request.getParameter("pageNo");
	 		int page_no = 1;
		
	 		QueryPage forum_page =fs.getAllForumByClassId(Integer.valueOf(classId),page_no);
		
	 		ForumClassService fcs=new ForumClassService();
	 		ForumClassBean fcvo = fcs.getByID(Integer.valueOf(classId));
	 		boolean isManager = fs.isManager(roleInfo.getBasicInfo().getPPk());
	 		List<String> managerList = fs.getManagerList(classId);
	 		
	 		request.setAttribute("forum_page", forum_page);
	 		request.setAttribute("forumClassBean", fcvo);
	 		request.setAttribute("isManager", isManager);
	 		request.setAttribute("managerList", managerList);
	 		
	 		
	 		return mapping.findForward("indexList");
		
	}
	
	// �鿴����
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//���ǻص����ʱ��ҳ��
		request.setAttribute("classPageNo", request.getParameter("classPageNo")+"");
		
		String page_id = request.getParameter("page_id");
		String classId = request.getParameter("classId");
		
		// page_no_str ��ָ�ڴ������л�����ҳ��
		String page_no_str = request.getParameter("pageNo");
		request.setAttribute("pageNo", page_no_str);
		request.setAttribute("classId",classId );
		request.setAttribute("erpage", request.getParameter("erpage"));
		
		int page_no = 1;
		try
		{
    		if( page_no_str==null || page_no_str.equals("")) { 
    			page_no = 1;
    		}else {
    			page_no = Integer.parseInt(page_no_str);
    		}
		}catch (Exception e) {
			page_no = 1;
		}	
		// ���С�ڵ�����,��ȻΪ1
		if ( page_no <= 0) {
			page_no = 1;
		}
		ForumRevertService frs=new ForumRevertService(); 
		QueryPage revert_page = frs.getAllForumRevert(Integer.valueOf(page_id),page_no);
		
		ForumService fs=new ForumService();
  		request.setAttribute("className", fs.getForumNameById(classId));
  		
		if(page_id!=null && StringUtil.isNumber(page_id))
      	{
			ForumBean fb = new ForumBean();      		
         	fb=fs.getByID(Integer.parseInt(page_id)); 
         	if(fb!=null)
      		{
      			fs.updateNum(fb.getId(),"readNum");//�����Ķ�����
      		}
			request.setAttribute("forumBean", fb);
      	}else {
      		logger.info("����idΪ�գ�");
      	}
		
		request.setAttribute("revert_page",revert_page );
		return mapping.findForward("contentView");
	}
	
	
	// ��̳���ӻظ��б�
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		//���ǻص����ʱ��ҳ��
		request.setAttribute("classPageNo", request.getParameter("classPageNo")+"");
				
		String pageId = request.getParameter("pageId");
		// page_no ��ָ�ڴ������л�����ҳ��
		String page_no_str = request.getParameter("pageNo");
		int page_no = 1;
		String classId = request.getParameter("classId");
		try
		{
    		if( page_no_str==null || page_no_str.equals("")) { 
    			page_no = 1;
    		}else {
    			page_no = Integer.parseInt(page_no_str);
    		}
		}catch (Exception e) {
			page_no = 1;
		}	
		
		if ( page_no <= 0) {
			page_no = 1;
		}
		logger.info("pageID="+pageId+" ,page_no="+page_no);
		 
		ForumRevertService frs=new ForumRevertService(); 
		QueryPage revert_page = frs.getAllForumRevert(Integer.valueOf(pageId),page_no);
		//logger.info("forumlist.size()="+revert_page.getResult());
		 
		request.setAttribute("pageId", pageId);
		request.setAttribute("classId",classId );
		request.setAttribute("pageNo",page_no_str );		
		request.setAttribute("revert_page", revert_page);
		
		return mapping.findForward("revert_view");
	}
	
	// ��̳���ӻظ�
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//���ǻص����ʱ��ҳ��
		request.setAttribute("classPageNo", request.getParameter("classPageNo")+"");
		
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		String recontent = request.getParameter("recontent");
		logger.info("recontent="+recontent+" ,pageID="+request.getParameter("pageId")
				+" ,classId="+request.getParameter("classId"));
		
		String pageID = request.getParameter("pageId").trim();
		request.setAttribute("pageId", pageID);
		String classId = request.getParameter("classId").trim();
		request.setAttribute("classId", classId);
		
		ForumRevertService frService = new ForumRevertService();
		
		if(recontent == null || recontent.trim().equals("")) {
			String hint = "�����ܷ�������";
			request.setAttribute("hint", hint);
			return mapping.findForward("return_revert");
		}
		if(recontent.trim().length() > 100 ) {
			String hint = "���Ļ������ݳ�����������!";
	 		request.setAttribute("hint", hint);
			return mapping.findForward("return_revert");
	 	}
		recontent = recontent.trim();
		ForumRevertBean frb = new ForumRevertBean();
		ForumService forumService = new ForumService();
		ForumBean forumBean = forumService.getByID(Integer.parseInt(pageID));
		
		int flag = Expression.hasPublish(recontent);
		if(flag == -1) {			
			String hint1 = "�����������зǷ��ַ������������룡";
			request.setAttribute("hint", hint1);
			return mapping.findForward("return_revert");
		}
		if(frService.isInTwoMinute(roleInfo.getBasicInfo().getPPk())) {
			String hint1 = "����ʱ������һ����.";
			request.setAttribute("hint", hint1);
			return mapping.findForward("return_revert");
		}
		
		
		if(forumBean.getVouch() > 0 ) {
			String hint1 = "�����Ѿ�����!";
			request.setAttribute("hint", hint1);
			return mapping.findForward("return_revert");
		}
		
		if (forumService.checkForbid(roleInfo.getBasicInfo().getPPk())) {
			String hint1 = "�Բ���,�������ڱ���ֹ����״̬!���Ժ��ٷ�!";
	 		request.setAttribute("hint", hint1);
			return mapping.findForward("return_revert");
	 	}
		// ����Ƿ��н�ֹ�ؼ���
		if (Expression.hasForbidChar(recontent,ForBidCache.FORBIDCOMM))
		{
			String hint1 = "�Բ������ķ����а�����ֹ�ַ�!";
			request.setAttribute("hint", hint1);
			return mapping.findForward("return_revert");
		}
		
		
		frb.setContent(StringUtil.gbToISO(recontent));
		frb.setFid(Integer.valueOf(pageID));
		frb.setUserID(roleInfo.getBasicInfo().getPPk());
		PartInfoDao partdao = new PartInfoDao();
		frb.setUserName(roleInfo.getBasicInfo().getName());
		
		frService.addForumRevert(frb);
		
		return mapping.findForward("sussendRevert");
	}
	
	// ��̳���ӻظ�
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String Time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������

		request.setAttribute("classPageNo", request.getParameter("classPageNo"));
		request.setAttribute("classId", request.getParameter("classId"));
		request.setAttribute("className", request.getParameter("className"));
		
		String pByPk = request.getParameter("pByPk");
		String pByName = request.getParameter("pByName");
		String pPks = request.getParameter("pPks");

		FriendService friendService = new FriendService();
		
		if(roleInfo.getBasicInfo().getPPk() == Integer.parseInt(pByPk)){
			String apply_hint = "�Լ����ܼ��Լ�Ϊ����";
			request.setAttribute("pPks", pPks);
			request.setAttribute("apply_hint", apply_hint);
			return mapping.findForward("sussendAddFriend");
		}
		
		if (friendService.whetherfriend(roleInfo.getBasicInfo().getPPk(), pByPk) == false)
		{
			String apply_hint = "������Ѿ������ĺ����˲���Ҫ������ˣ�";
			request.setAttribute("pPks", pPks);
			request.setAttribute("apply_hint", apply_hint);
			return mapping.findForward("sussendAddFriend");
		}
		if (friendService.friendupperlimit(roleInfo.getBasicInfo().getPPk()) == false)
		{
			String apply_hint = "���ĺ��������Ѿ��ﵽ���ޣ�";
			request.setAttribute("pPks", pPks);
			request.setAttribute("apply_hint", apply_hint);
			return mapping.findForward("sussendAddFriend");
		}
		
		// ���������ں������� ɾ��������Ȼ���ڼ������
		BlacklistService blacklistService = new BlacklistService();
		if (blacklistService.whetherblacklist(roleInfo.getBasicInfo().getPPk(), pByPk) == false)
		{
			blacklistService.deleteblacklist(roleInfo.getBasicInfo().getPPk(), pByPk);
		}

		friendService.addfriend(roleInfo.getBasicInfo().getPPk(), pByPk, pByName, Time);
		String apply_hint = "���ѽ�" + pByName + "�������ĺ����б��";
		request.setAttribute("apply_hint", apply_hint);
		request.setAttribute("pPks", pPks);
		return mapping.findForward("sussendAddFriend");
	
	}
}
