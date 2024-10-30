package com.ls.web.action.cooperate.youle.login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.vo.cooperate.dangle.PassportVO;
import com.ls.web.service.cooperate.dangle.PassportService;

/***
 * 悠乐渠道登陆action
 * @author thomas.lei
 */
public class LoginAction extends DispatchAction
{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		String ChannelID =request.getParameter("ChannelID");
		String MachineID=request.getParameter("MachineID");
		String ModelID=request.getParameter("ModelID");
		String UserAccount=request.getParameter("UserAccount");
		String login_ip=request.getLocalAddr();
		if(ChannelID==null||ModelID==null||UserAccount==null)
		{
			try
			{
				response.getWriter().print(1);
				System.out.println("登陆参数为null........");
				return null;
			}
			catch (IOException e)
			{
				System.out.println("返回值发送失败......");
			}
		}
		/******登陆******/
		PassportService ps=new PassportService();
		PassportVO passportVo= ps.loginFromYoule(UserAccount, login_ip);
		if(passportVo==null||passportVo.getUPk()==-1)
		{
			try
			{
				response.getWriter().print(1);
				System.out.println("登陆失败......");
			}
			catch (IOException e)
			{
				System.out.println("登陆失败......");
			}
			return null;
		}
		else
		{
			HttpSession session = request.getSession();
			LoginService loginService=new LoginService();
			int upk=passportVo.getUPk();
			session.setAttribute("ChannelID",ChannelID);
			session.setAttribute("ModelID",ModelID);
			session.setAttribute("uPk", upk+"");
			session.setAttribute("UserAccount",passportVo.getUserName() );
			session.setAttribute("MachineID",MachineID);
			session.setAttribute("PartnerID","10006");
			session.setAttribute("GameNO","2014");
			/******同步给悠乐登陆信息******/
			loginService.synchronousLoginState(UserAccount, "1", "2006");
			return mapping.findForward("success");
		}
	}
}
