/**
 * 
 */
package com.web.action.communion.privately;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.dao.info.partinfo.PartInfoDAO;
import com.ben.vo.communion.CommunionVO;
import com.ls.ben.cache.dynamic.manual.chat.ChatInfoCahe;
import com.ls.ben.cache.staticcache.forbid.ForBidCache;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.player.PlayerState;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.RoleService;
import com.pm.service.mail.MailInfoService;
import com.pub.ben.info.Expression;
import com.web.service.friend.BlacklistService;

/**
 * @author 侯浩军
 * 
 * 9:40:46 AM
 */
public class PrivatelyAddAction extends Action
{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		// 创建时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String pPk = roleInfo.getBasicInfo().getPPk() + "";

		String upTitle = request.getParameter("upTitle");
		String pNameBy = request.getParameter("pNameBy");
		String type = request.getParameter("type");
		String hint = null;
		PartInfoDAO dao = new PartInfoDAO();
		/*Pattern p = Pattern.compile(Expression.chinese_regexp);
		Matcher m = p.matcher(upTitle);
		boolean b = m.matches();
		if (b == false)
		{
			hint = "您的内容里有非法字符！请重新输入！";
			request.setAttribute("hint", hint);
			return mapping.findForward("successnoss");
		}*/
		int flag = Expression.hasPublish(upTitle);
		if (flag == -1)
		{
			hint = "您的内容里有非法字符！请重新输入！";
			request.setAttribute("hint", hint);
			return mapping.findForward("successnoss");
		}
		if(upTitle.length() > 30) { 
			String hint1 = "您只能输入30个字符";
			request.setAttribute("hint", hint1);
			return mapping.findForward("successnoss");
		}
		if (dao.getPartTypeListName(pNameBy) == false)
		{
			hint = "对不起，不存在该玩家。";
			request.setAttribute("hint", hint);
			return mapping.findForward("successnoss");
		}
		int pk = dao.getPartPk(pNameBy);
		if (pk == Integer.parseInt(pPk))
		{
			hint = "您不能给自己发送。";
			request.setAttribute("hint", hint);
			return mapping.findForward("successnoss");
		}
		BlacklistService blacklistService = new BlacklistService();
		if (blacklistService.whetherblacklist(roleInfo.getBasicInfo().getPPk(), pk+"") == false)
		{
			hint = "该玩家在您的黑名单里!";
			request.setAttribute("hint", hint);
			return mapping.findForward("successnoss");
		}
		int res = blacklistService.isBlacklist(pk, roleInfo.getPPk());
		if(res == 2){
			hint = "该玩家在您的黑名单中,您不能与他(她)进行密聊.";
			request.setAttribute("hint", hint);
			return mapping.findForward("successnoss");
		}else if(res == 1){
			hint = "您在该玩家的黑名单中,您不能与他(她)进行密聊.";
			request.setAttribute("hint", hint);
			return mapping.findForward("successnoss");
		}
		if (Expression.hasForbidChar(upTitle,ForBidCache.FORBIDCOMM))
		{
			hint = "对不起，您的发言中包含禁止字符!";
			request.setAttribute("hint", hint);
			return mapping.findForward("successnoss");
		}
		if(upTitle.trim().equals("")){
			hint = "对不起，您的发言中包含禁止字符!";
			request.setAttribute("hint", hint);
			return mapping.findForward("successnoss");
		}
		if(upTitle.indexOf("　") != -1){
			hint = "对不起，您的发言中包含禁止字符!";
			request.setAttribute("hint", hint);
			return mapping.findForward("successnoss");
		}
		
		PlayerService playerService = new PlayerService();
		hint = playerService.checkRoleState(pk, PlayerState.TALK);
		if(hint != null){
			MailInfoService mailInfoService = new MailInfoService();
			String mailtitle = "来自好友"+roleInfo.getBasicInfo().getName()+"的邮件";
			mailInfoService.sendPersonMail(pk, roleInfo.getBasicInfo().getPPk(), mailtitle, upTitle);
		String apply_hint = hint+",系统已经帮您转发至他(她)的邮箱中!";
		request.setAttribute("hint", apply_hint);
		return mapping.findForward("successnoss");
		}
		
		// 执行插入公共聊天记录 c_pk,p_pk,p_name,p_pk_by,p_name_by,c_title,c_type,create_time
		CommunionVO communionVO = new CommunionVO();
		communionVO.setPPk(roleInfo.getBasicInfo().getPPk());
		communionVO.setPName(roleInfo.getBasicInfo().getName());
		communionVO.setPPkBy(pk);
		communionVO.setPNameBy(pNameBy);
		communionVO.setCTitle(upTitle);
		communionVO.setCType(Integer.parseInt(type));
		communionVO.setCreateTime(Time);
		ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
		publicChatInfoCahe.put(communionVO);
		
		request.setAttribute("pByPk", pk+"");
		request.setAttribute("pByName", pNameBy);
		return mapping.findForward("success");
	}
}
