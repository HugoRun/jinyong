package com.pm.action.secondPass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ben.dao.logininfo.LoginInfoDAO;
import com.ben.vo.logininfo.LoginInfoVO;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.StringUtil;
import com.pm.dao.secondpass.SecondPassDao;
import com.pm.service.secondpass.SecondPassService;
import com.pm.vo.passsecond.SecondPassVO;
import com.pub.MD5;

public class ZhiModifyPasswordAction extends DispatchAction 
{

	Logger logger = Logger.getLogger("log.action");
	//转向未登录前就修改登录密码的二级密码输入页面
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String uName = request.getParameter("name");
		LoginInfoDAO infodao = new LoginInfoDAO();
		LoginInfoVO infovo = infodao.getUserInfoLoginName(uName);
		int uPk = infovo.getUPk();
		if(uPk == 0) {
			String uPk1 = (String)request.getSession().getAttribute("uPk");
			uPk = Integer.valueOf(uPk1);
		} 
		logger.info("uPk1111="+uPk);
		
		request.getSession().setAttribute("uPk", uPk+"");
		request.setAttribute("uPk", uPk+"");
		request.setAttribute("authenPass", "erjipasswordistrue");
		request.setAttribute("zhijie", "henzhijie");
		return mapping.findForward("startPass");
		
	}
	
	//输入二级密码进行验证
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String uPk1 = (String)request.getSession().getAttribute("uPk");
		if(uPk1 == null ){
			uPk1 = request.getParameter("uPk");
		}
		request.setAttribute("uPk", uPk1);
		logger.info("uPk="+uPk1);
		int uPk = Integer.valueOf(uPk1);
		
		String second_pass = request.getParameter("second_pass");
		logger.info("second_pass="+second_pass);
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
								hint = "您输入的该帐号的二级密码错误，如24小时内输入三次错误的二级密码，该帐号的登录密码在随后的24小时内不可被更改!";
								
							} else {
								hint = "该帐号已被锁定，24小时内不能修改游戏登录密码！";
							}
							request.setAttribute("hint", hint);
							return mapping.findForward("failedPass");
						}
					//如果已经大于3,就告诉玩家不能再核对了.	
					} else {
						secondPassService.insertErrorSecondPsw(uPk); 
						String hint = "该帐号已被锁定，24小时内不能修改游戏登录密码！";
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
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String uPk1 = (String)request.getSession().getAttribute("uPk");
		if(uPk1 == null ){
			uPk1 = request.getParameter("uPk");
		}
		
		logger.info("uPk="+uPk1);
		int uPk = Integer.valueOf(uPk1);
		
		String getTodayStr = DateUtil.getTodayStr();
		String auth = MD5.getMD5Str(getTodayStr).substring(1,6);
		request.setAttribute("auth", auth);
		
		String pass_word = request.getParameter("pass_word");
		logger.info("pass_word="+pass_word);
		
		if(pass_word != null && !pass_word.equals("")) {		
			if(StringUtil.isNumberAndChar(pass_word)) {			//登录密码为数字和字母的混合
				if(pass_word.length() >=5 && pass_word.length() <= 11) {	//登录密码的长度在5到11之间	
					SecondPassDao seconddao = new SecondPassDao();
					String oldPass = seconddao.getUserLoginPawByUPk(uPk);
					String newPass = MD5.getMD5Str(pass_word);
					if(!newPass.equals(oldPass)) {				//如果登录密码和二级密码相同就不让其通过,并警告玩家.
						SecondPassService secondPassService = new SecondPassService();
						secondPassService.updateLoginPaw(uPk,newPass);
						request.setAttribute("pass_word", pass_word); 
						return mapping.findForward("sussendupdateLoginPsw");
					}else {
						String hint = "您的二级密码不能和登录相同!";
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
	
	//转向未登录前就修改登录密码的二级密码输入页面
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		
		return mapping.findForward("");
	}

}
