package com.pm.action.secondPass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ben.dao.logininfo.LoginInfoDAO;
import com.ls.pub.util.StringUtil;
import com.pm.dao.secondpass.SecondPassDao;
import com.pm.service.mail.MailInfoService;
import com.pm.service.secondpass.SecondPassService;
import com.pub.MD5;


public class SecondPassAction extends DispatchAction 
{

	Logger logger = Logger.getLogger("log.action");
	
	//转向二级密码页面
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		//String pPk = request.getParameter("pPk");	//对于pPk到底如何得到,还要进行下一步分析
		String secondPass = request.getParameter("secondPass");
		String uPk = (String)request.getSession().getAttribute("uPk");
		logger.info("修改二级密码时的upk="+uPk);
		
		if(secondPass != null && !secondPass.equals("")) {
			boolean flag = StringUtil.isNumber(secondPass);
			if(flag == true) {	
				if(secondPass.length() == 6) {	
					
					LoginInfoDAO infoDao = new LoginInfoDAO();
					String nowpaw = infoDao.getUserLoginPawByUPk( Integer.parseInt(uPk));
					if(nowpaw.equals(MD5.getMD5Str(secondPass))) {
						String hint = "对不起，二级密码不能与登录密码相同!";
						request.setAttribute("hint", hint);
						return mapping.findForward("failedPass");
					}					
					
					SecondPassService secondPassService = new SecondPassService();
					// 以前是因为在发邮件时直接进行了初始化，所有现在只需要update即可，现在没有初始化了，只能在此进行insert操作
					secondPassService.insertSecondPass(Integer.parseInt(uPk),secondPass);
					request.setAttribute("secondPass",secondPass);
					request.setAttribute("authenPass", "shizhen");
					
					//当确定修改二级密码后, 删除修改二级密码邮件
					MailInfoService mailInfoService = new MailInfoService();
					mailInfoService.deleteSecondPassMail(Integer.parseInt(uPk));
					return mapping.findForward("sussendPass");
				}	
			}		
			String hint = "对不起，您输入的二级密码组合不符合规定！游戏帐号的二级密码为6位0～9数值组合!";
			request.setAttribute("hint", hint);
			return mapping.findForward("failedPass");
		}		
		//String hint = "二级密码不能为空!";
		
		SecondPassService secondPassService = new SecondPassService();
		
		String hint = secondPassService.getHasSetSecondPassword(uPk);
		request.setAttribute("hint", hint);
		return mapping.findForward("failedPass");
	}	
		
	
	// 设置二级密码
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String pass_word = request.getParameter("pass_word");
		
		if(pass_word != null && !pass_word.equals("")) {		
			if(StringUtil.isNumberAndChar(pass_word)) {			//登录密码为数字和字母的混合
				if(pass_word.length() >=5 && pass_word.length() <= 11) {	//登录密码的长度在5到11之间	
					SecondPassDao seconddao = new SecondPassDao();
					String uPk = (String)request.getSession().getAttribute("uPk");
					logger.info("修改密码时的upk="+uPk);
					
					String oldPass = seconddao.getUserLoginPawByUPk(Integer.parseInt(uPk));
					String newPass = MD5.getMD5Str(pass_word);
					if(!newPass.equals(oldPass)) {				//如果登录密码和二级密码相同就不让其通过,并警告玩家.
						SecondPassService secondPassService = new SecondPassService();
						secondPassService.updateLoginPaw(Integer.parseInt(uPk),newPass);
						request.setAttribute("pass_word", pass_word);
						return mapping.findForward("sussendupdateLoginPsw");
					}else {
						String hint = "您的二级密码不能和登录密码相同!";
						request.setAttribute("hint", hint);
						return mapping.findForward("password_wrong");
					}
				}
			}
			String hint = "对不起，您输入的密码组合不符合规定！游戏帐号密码为5-11位,0-9,a-z组合!";
			request.setAttribute("hint", hint);
			return mapping.findForward("password_wrong");
		}
		String hint = "对不起, 您不能输入空密码作为二级密码!";
		request.setAttribute("hint", hint);
		return mapping.findForward("password_wrong");
	}
}
