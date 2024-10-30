/**
 * 
 */
package com.pm.action.forum;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.model.user.RoleEntity;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.player.RoleService;
import com.pm.service.forum.ForumClassService;
import com.pm.service.forum.ForumInfoService;
import com.pm.service.forum.ForumService;
import com.pm.vo.forum.ForumBean;
import com.pm.vo.forum.ForumClassBean;

/**
 * 用于论坛的帖子处理,主要是斑竹处理帖子所需要用到的方法
 * @version 1.0
 * @author 张俊俊
 *
 */
public class ForumInfoAction extends DispatchAction 
{
	Logger logger = Logger.getLogger("log.action");
	
	
	// 进入管理帖子页面
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
				
		//这是回到板块时的页数
		request.setAttribute("classPageNo", request.getParameter("classPageNo")+"");
		
		String page_id = request.getParameter("page_id");
		String classId = request.getParameter("classId");
		
		request.setAttribute("pageNo", request.getParameter("pageNo")+"");
		request.setAttribute("classId",classId );
		request.setAttribute("page_id",page_id );
		
		ForumClassService forumClassService = new ForumClassService();
		ForumClassBean forumClassBean = forumClassService.getByID(Integer.parseInt(classId));
		request.setAttribute("forumClassBean", forumClassBean);
		
		if(page_id!=null && StringUtil.isNumber(page_id))
      	{			
      		ForumService fs=new ForumService();
      		ForumBean fb = new ForumBean();
      		
         	fb=fs.getByID(Integer.parseInt(page_id)); 
         	if(fb!=null)
      		{
      			fs.updateNum(fb.getId(),"readNum");//更新阅读次数
      		}
			request.setAttribute("forumBean", fb);
      	}else {
      		logger.info("文章id为空！");
      	}		
		return mapping.findForward("contentView");
	}
	
	
	// 管理帖子
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		String page_id = request.getParameter("page_id");
		String classId = request.getParameter("classId");
		String classPageNo = request.getParameter("classPageNo");
		String managerType = request.getParameter("managerType");
		
		request.setAttribute("classId",classId );
		request.setAttribute("page_id", page_id);
		request.setAttribute("classPageNo",classPageNo );
		request.setAttribute("managerType",managerType );
		
		ForumService fs=new ForumService(); 
		boolean isManager = fs.isManager(roleInfo.getBasicInfo().getPPk());
		
		String hint = "";
		if (!isManager) {
			hint = "您不是管理员!无权处理帖子!";
			request.setAttribute("hint", hint);
			return mapping.findForward("hint");
		}
		// 1为禁止发帖，2为删帖，3为置顶,4为锁帖
		if ( managerType.equals("1")) {
			String pagePPk = request.getParameter("pagePPk");
			try {
	 			request.setAttribute("pagePPk", pagePPk);
				request.getRequestDispatcher("/jsp/forum/manager/forbid_forum.jsp").forward(request,response);
	 		 	return null;
	 		} catch (ServletException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			
		} else if ( managerType.equals("2")) {
			// 处理删帖事宜
			ForumInfoService forumInfoService = new ForumInfoService();
			forumInfoService.deleteForum(page_id);
			hint = "帖子已经删除!";
			request.setAttribute("type", "delete");
		}else if ( managerType.equals("3")) {
			// 处理置顶事宜
			ForumInfoService forumInfoService = new ForumInfoService();
			hint = forumInfoService.zhiDing(page_id);
			
		}else if ( managerType.equals("4")) {
			// 处理锁帖事宜
			ForumInfoService forumInfoService = new ForumInfoService();
			hint = forumInfoService.lockForum(page_id);
		}
		
		request.setAttribute("hint", hint);
		return mapping.findForward("hint");
	}
	
	//  禁止发帖
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		String pagePPk = request.getParameter("pagePPk");
		String page_id = request.getParameter("page_id");
		String classId = request.getParameter("classId");
		String classPageNo = request.getParameter("classPageNo");
		String managerType = request.getParameter("managerType");
		
		String forbid_time_str = request.getParameter("forbid_time");
		
		request.setAttribute("classId",classId );
		request.setAttribute("page_id", page_id);
		request.setAttribute("classPageNo",classPageNo );
		request.setAttribute("managerType",managerType );
		
		if (forbid_time_str == null  || forbid_time_str.equals("")) {
			forbid_time_str = "1";
		}
		String hint = "";
		int forbid_time = 0;
		try {
			forbid_time = Integer.parseInt(forbid_time_str);
			
		} catch (Exception e) {
			hint = "您输入的时间有误,请输入数字!";
			request.setAttribute("hint", hint);	
			return mapping.findForward("hint");
		}
		
		if ( forbid_time <= 0 ) {
			hint = "您输入的时间应该大于零!";
			request.setAttribute("hint", hint);	
			return mapping.findForward("hint");
		}
		
		if ( forbid_time > 50000 ) {
			hint = "您输入的时间最好不要大于五万分钟!";
			request.setAttribute("hint", hint);	
			return mapping.findForward("hint");
		}
		
		if ( roleInfo.getBasicInfo().getPPk() == Integer.parseInt(pagePPk)) {
			hint = "请不要禁止自己!";
			request.setAttribute("hint", hint);	
			return mapping.findForward("hint");
		}
		
		
		ForumInfoService forumInfoService = new ForumInfoService();
		hint = forumInfoService.insertIntoForbid(roleInfo,pagePPk,forbid_time);
		
		request.setAttribute("hint", hint);	
		
		return mapping.findForward("hint");
	}
}
