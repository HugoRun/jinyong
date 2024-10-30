/**
 * 
 */
package com.ls.web.action.cooperate.tom.login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.pub.constant.Channel;
import com.ls.web.service.cooperate.dangle.PassportService;
import com.pub.ben.info.Expression;
import com.web.service.checkpcrequest.CheckPcRequestService;

/**
 * @author 侯浩军
 * 
 */
public class TomLoginAction extends DispatchAction
{
	/*
	 * Generated Methods
	 */
	Logger logger = Logger.getLogger(TomLoginAction.class);

	/**
	 * TOM用户登陆到首页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		String lid = request.getParameter("lid");
		request.setAttribute("lid", lid);
		/*CheckPcRequestService checkPcRequestService = new CheckPcRequestService();
		if(checkPcRequestService.CheckPcRequest(request) == false){
			String qq = "经检测,你使用非正常方法登录游戏,请返回使用手机登录游戏";
			request.setAttribute("hint", qq);
			return mapping.findForward("ischeckpc");
		} */
		return mapping.findForward("tomindexpage");
	}
	/**
	 * TOM用户登陆到输入用户名密码页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		String lid = request.getParameter("lid");
		//String hint = (String)request.getAttribute("hint"); 
		//request.setAttribute("hint", hint);
		request.setAttribute("lid", lid);
		 
		return mapping.findForward("tomvalidatepage");
	}
	
	/**
	 * TOM用户登陆到选择区域页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */ 
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String ip = (String) request.getRemoteAddr();
		logger.info("############TOM用户登陆##############");
		String name = request.getParameter("name");
		String paw = request.getParameter("paw");
		String lid = request.getParameter("lid");
		
		 
		
		logger.info("lid:" + lid);
		logger.info("user_name:" + name);
		logger.info("IP:" + ip);
		//首先验证用户名和密码是否正确
		//用户名匹配为字符型
		 String hint = null;
		 Pattern p = Pattern.compile(Expression.letter_number_regexp);
		 Matcher m = p.matcher(name);
		 Matcher pw = p.matcher(paw);
		 boolean b = m.matches();
		 boolean pp = pw.matches();
		 if(b==false){
			 hint = "账号请输入大小写英文字符和数字字符";
			 request.setAttribute("hint", hint);
			 request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage");
		 }
		 if(pp==false){
			 hint = "密码请输入大小写英文字符和数字字符";
			 request.setAttribute("hint", hint);
			 request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage");
		 }
		if( name.indexOf(" ") != -1 )
		{
			hint = "账号不能有空格出现";
			request.setAttribute("hint", hint);
			request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage");
		}
		if( name == null || name.equals("") )
		{
			hint = "账号不能为空";
			request.setAttribute("hint", hint);
			request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage");
		}
		if( name.length() < 5 )
		{
			hint = "账号位数不能小于5位";
			request.setAttribute("hint", hint);
			request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage");
		}
		else if( name.length() > 11 )
		{
			
			hint = "账号位数不能大于11位"; 
			request.setAttribute("hint", hint); 
			request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage");
		}
		
		if( paw.length() < 5 )
		{
			hint = "密码位数不能小于5位";
			request.setAttribute("hint", hint);
			request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage");
		}else if( paw.length() > 11 )
		{
			
			hint = "密码位数不能大于11位"; 
			request.setAttribute("hint", hint); 
			request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage");
		}
		
		 
		if( paw.indexOf(" ") != -1 )
		{
			hint = "密码不能有空格出现";
			request.setAttribute("hint", hint);
			request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage");
		}
		if( paw == null || paw.equals("") )
		{
			hint = "密码不能为空";
			request.setAttribute("hint", hint);
			request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage");
		}
		if ( Expression.hasWeiFaChar(name)) {
			hint = "名字中请不要有gm、客服等字样!";
			request.setAttribute("hint", hint);
			request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage"); 
		}
		 
		int u_pk = -1;
		PassportService passportService = new PassportService();
		u_pk = passportService.loginFromTOM(lid, name, paw, ip);
		request.getSession().setAttribute("channel_id", Channel.TOM + "");

		if (u_pk == -1)// 登陆验证失败
		{	
			logger.info("用户验证失败");
			return mapping.findForward("fail");
		}

		request.setAttribute("uPk", u_pk + "");
		return mapping.findForward("success");
	}
	
	/**
	 * TOM用户登陆到输入用户名密码页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 	
		String uPk = request.getParameter("uPk");
		//String hint = (String)request.getAttribute("hint"); 
		//request.setAttribute("hint", hint);
		String lid = request.getParameter("lid");
		request.setAttribute("lid", lid);
		request.setAttribute("uPk", uPk); 
		return mapping.findForward("success");
	}
}
