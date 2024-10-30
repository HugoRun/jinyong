/**
 * 
 */
package com.web.action.communion.publica;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.dao.embargo.EmbargoDAO;
import com.ben.vo.communion.CommunionVO;
import com.ls.ben.cache.dynamic.manual.chat.ChatInfoCahe;
import com.ls.ben.cache.staticcache.forbid.ForBidCache;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.web.service.player.RoleService;
import com.pub.ben.info.Expression;

/**
 * @author 侯浩军
 * 
 * 9:40:46 AM
 */
public class UPublicAddAction extends Action
{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		// 创建时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String puTitle = request.getParameter("puTitle");
		/*Pattern p = Pattern.compile(Expression.chinese_regexp);
		Matcher m = p.matcher(puTitle);
		boolean b = m.matches();
		if (b == false)
		{
			String hint1 = "您的内容里有非法字符！请重新输入！";
			request.setAttribute("hint", hint1);
			return mapping.findForward("successno");
		}*/
		int flag = Expression.hasPublish(puTitle);
		if (flag == -1)
		{
			String hint1 = "您的内容里有非法字符！请重新输入！";
			request.setAttribute("hint", hint1);
			return mapping.findForward("successno");
		}
		if(puTitle.indexOf("GM") != -1){
			String hint = "对不起，您的发言中包含禁止字符!";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		if(puTitle.indexOf("Gm") != -1){
			String hint = "对不起，您的发言中包含禁止字符!";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		if(puTitle.indexOf("gm") != -1){
			String hint = "对不起，您的发言中包含禁止字符!";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		if (puTitle.length() > 30)
		{
			String hint1 = "您只能输入30个字符";
			request.setAttribute("hint", hint1);
			return mapping.findForward("successno");
		}
		// 判断是否重复发言
		EmbargoDAO embargoDAO = new EmbargoDAO();
		String s = embargoDAO.isEmbargo(roleInfo.getBasicInfo().getPPk(), Time);
		if (s != null)
		{
			String hint = "您被在公共聊天频道禁言" + s + "分钟！";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		
		//公聊的等级限制
		int public_chat_grade_limit = GameConfig.getPublicChatGradeLimit();
		if (roleInfo.getBasicInfo().getGrade() < public_chat_grade_limit )
		{
			String hint = "对不起，只有"+public_chat_grade_limit+"级以上的玩家才可以在公共频道发言！";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		
		if (Expression.hasForbidChar(puTitle,ForBidCache.FORBIDCOMM))
		{
			String hint = "对不起，您的发言中包含禁止字符!";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		if(puTitle.trim().equals("")){
			String hint = "对不起，您的发言中包含禁止字符!";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		if(puTitle.indexOf("　") != -1){
			String hint = "对不起，您的发言中包含禁止字符!";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}

		String hint = roleInfo.getStateInfo().isPublicChat();
		if( hint!=null )
		{
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		
		// 执行插入公共聊天记录
		CommunionVO communionVO = new CommunionVO();
		communionVO.setPPk(roleInfo.getBasicInfo().getPPk());
		communionVO.setPName(roleInfo.getBasicInfo().getName());
		communionVO.setCTitle(puTitle);
		communionVO.setCType(1);
		communionVO.setCreateTime(Time);
		ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
		publicChatInfoCahe.put(communionVO);
		return mapping.findForward("success");
	}
}