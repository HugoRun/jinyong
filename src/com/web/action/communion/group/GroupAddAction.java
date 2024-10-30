/**
 * 
 */
package com.web.action.communion.group;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.vo.communion.CommunionVO;
import com.ls.ben.cache.dynamic.manual.chat.ChatInfoCahe;
import com.ls.ben.cache.staticcache.forbid.ForBidCache;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.group.GroupService;
import com.ls.web.service.player.RoleService;
import com.pub.ben.info.Expression;

/**
 * @author 侯浩军
 * 
 * 9:40:46 AM
 */
public class GroupAddAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		// 创建时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String puTitle = request.getParameter("puTitle");
		String type = request.getParameter("type");
		String hint = null;
		/*Pattern p = Pattern.compile(Expression.chinese_regexp);
		Matcher m = p.matcher(puTitle);
		boolean b = m.matches();
		if (b == false)
		{
			hint = "您的内容里有非法字符！请重新输入！";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}*/
		int flag = Expression.hasPublish(puTitle);
		if (flag == -1)
		{
			hint = "您的内容里有非法字符！请重新输入！";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		if (puTitle.length() > 30)
		{
			String hint1 = "您只能输入30个字符";
			request.setAttribute("hint", hint1);
			return mapping.findForward("successno");
		}
		if (roleInfo.getStateInfo().getGroup_id() == -1)
		{
			hint = "对不起，您目前没有加入队伍，无法在组队频道发言！";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		if (Expression.hasForbidChar(puTitle,ForBidCache.FORBIDCOMM))
		{
			hint = "对不起，您的发言中包含禁止字符!";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		if(puTitle.trim().equals("")){
			hint = "对不起，您的发言中包含禁止字符!";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		if(puTitle.indexOf("　") != -1){
			hint = "对不起，您的发言中包含禁止字符!";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		// 执行插入公共聊天记录 c_pk,p_pk,p_name,c_dui,c_title,c_type,create_time
		CommunionVO communionVO = new CommunionVO();
		communionVO.setPPk(roleInfo.getBasicInfo().getPPk());
		communionVO.setPName(roleInfo.getBasicInfo().getName());
		communionVO.setCDui(roleInfo.getStateInfo().getGroup_id());
		communionVO.setCTitle(puTitle);
		communionVO.setCType(Integer.parseInt(type));
		communionVO.setCreateTime(Time);
		ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
		publicChatInfoCahe.put(communionVO);
		return mapping.findForward("success");
	}
}
