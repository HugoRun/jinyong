package com.pm.action.secondPass;

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
import com.pm.dao.secondpass.SecondPassDao;
import com.pm.service.secondpass.SecondPassService;
import com.pm.vo.passsecond.SecondPassVO;
import com.pub.MD5;

public class ModifyPasswordAction extends DispatchAction 
{

	Logger logger = Logger.getLogger("log.action");
	//转向二级密码页面
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int uPk = roleInfo.getBasicInfo().getUPk();
		
		logger.info("uPk="+uPk);
		request.setAttribute("uPk", uPk);
		String second_pass = request.getParameter("second_pass");
		SecondPassService secondPassService = new SecondPassService();
		
		if(second_pass != null && !second_pass.equals("")) {	//检查其是否为空
			if(StringUtil.isNumber(second_pass)) {				//检测其是不是都是由数字组成.
				if(second_pass.length() == 6) {					//检查其是不是六位
					SecondPassDao seconddao = new SecondPassDao();
					//搜索出数据库中的二级密码
					SecondPassVO secondPassVO = seconddao.getSecondPassTime(uPk);
					if(secondPassVO.getSecondPass() == null || secondPassVO.getSecondPass().equals("")) {
						String hint = "您还没有设置二级密码, 不能修改登录密码！";
						request.setAttribute("hint", hint);
						return mapping.findForward("failedPass");
					}
					//如果二级密码错误次数小于3,则让其继续核对.
					if(!secondPassService.checkSeconePass(uPk,secondPassVO)) {
						if(secondPassVO.getSecondPass().equals(MD5.getMD5Str(second_pass))) {
							request.setAttribute("authenPass", "erjipasswordistrue");
							return mapping.findForward("sussendPass");
						} else {
							secondPassService.insertErrorSecondPsw(uPk); 
							String hint = "";
							//如果已经错两次,那么给玩家看的提示也会不一样.
							if(secondPassVO.getPassWrongFlag() == 2) {
								hint = "您已经在24小时内三次输入错误的帐号二级密码，为了保护该帐号的安全，24小时内该帐号不可再使用修改游戏登录密码功能！！";
								
							} else {
								hint = "对不起，您输入的二级密码有误，24小时内三次输入错误的二级密码将被冻结修改密码功能一天！";
							}
							request.setAttribute("hint", hint);
							return mapping.findForward("failedPass");
						}
					//如果已经大于3,就告诉玩家不能再核对了.	
					} else {
						secondPassService.insertErrorSecondPsw(uPk); 
						String hint = "您已经在24小时内三次输入错误的帐号二级密码，为了保护该帐号的安全，24小时内该帐号不可再使用修改游戏登录密码功能！！";
						request.setAttribute("hint", hint);
						return mapping.findForward("failedPass");
					}
				}
			}
			String hint = "对不起，您输入的二级密码格式不正确,二级密码为六位数字组成！";
			request.setAttribute("hint", hint);
			return mapping.findForward("failedPass");
		}
		String hint = "对不起，请不要输入空密码！";
		request.setAttribute("hint", hint);
		return mapping.findForward("failedPass");
	}
	
	//修改登录密码
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		String pass_word = request.getParameter("pass_word");
		
		if(pass_word != null && !pass_word.equals("")) {		
			if(StringUtil.isNumberAndChar(pass_word)) {			//登录密码为数字和字母的混合
				if(pass_word.length() >=5 && pass_word.length() <= 11) {	//登录密码的长度在5到11之间	
					request.setAttribute("firstPassword", pass_word);
					request.setAttribute("authenPass", "reputpassword");
					return mapping.findForward("firstpassword_true");
				}
			}
			String hint = "对不起，您输入的密码组合不符合规定！游戏帐号密码为5-11位,0-9,a-z组合!";
			request.setAttribute("hint", hint);
			request.setAttribute("authenPass", "erjipasswordistrue");
			return mapping.findForward("sussendPass");
		}
		String hint = "对不起，密码不能为空!";
		request.setAttribute("authenPass", "erjipasswordistrue");
		request.setAttribute("hint", hint);
		return mapping.findForward("sussendPass");
	}
	
	//修改登录密码
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String firstPassword = request.getParameter("firstPassword");
		String re_pass_word = request.getParameter("re_pass_word");
		
		// 可能会在此处出现空指针错误，因为取不到uPk之类的原因， 等测试后再改。
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int uPk = roleInfo.getBasicInfo().getUPk();
				
		//logger.info("re_pass_word="+re_pass_word+",equals="+re_pass_word.equals(firstPassword));
		if(re_pass_word == null || re_pass_word.equals("")) {		
			String hint = "对不起，密码不能为空!";
			request.setAttribute("hint", hint);
			request.setAttribute("firstPassword", firstPassword);
			request.setAttribute("authenPass", "reputpassword");
			return mapping.findForward("firstpassword_true");
		}
		
		if(re_pass_word != null && !re_pass_word.equals("")) {		
			if(StringUtil.isNumberAndChar(re_pass_word)) {			//登录密码为数字和字母的混合
				if(re_pass_word.length() >=5 && re_pass_word.length() <= 11) {	//登录密码的长度在5到11之间	
					
					if (!firstPassword.equals(re_pass_word)) {
						String hint = "对不起，您前后输入新的帐号登录密码不同！请重新输入您新的帐号登录密码:!";
						request.setAttribute("hint", hint);
						request.setAttribute("firstPassword", firstPassword);
						request.setAttribute("authenPass", "reputpassword");
						return mapping.findForward("sussendPass");
					} else {
						
						request.setAttribute("pass_word", re_pass_word);
						SecondPassService secondPassService = new SecondPassService();
						secondPassService.updateLoginPaw(uPk, MD5.getMD5Str(re_pass_word));
						
						return mapping.findForward("sussend_set_psw");
					}
				}
			
			String hint = "对不起，您输入的密码组合不符合规定！游戏帐号密码为5-11位,0-9,a-z组合!";
			request.setAttribute("hint", hint);
			request.setAttribute("firstPassword", firstPassword);
			request.setAttribute("authenPass", "reputpassword");
			return mapping.findForward("firstpassword_true");
			}
		}
		String hint = "对不起，您输入的密码不能为空!";
		request.setAttribute("hint", hint);
		request.setAttribute("firstPassword", firstPassword);
		request.setAttribute("authenPass", "reputpassword");
		return mapping.findForward("firstpassword_true");
	}
	
	
}
