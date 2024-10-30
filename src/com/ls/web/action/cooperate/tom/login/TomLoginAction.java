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
 * @author ��ƾ�
 * 
 */
public class TomLoginAction extends DispatchAction
{
	/*
	 * Generated Methods
	 */
	Logger logger = Logger.getLogger(TomLoginAction.class);

	/**
	 * TOM�û���½����ҳ��
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
			String qq = "�����,��ʹ�÷�����������¼��Ϸ,�뷵��ʹ���ֻ���¼��Ϸ";
			request.setAttribute("hint", qq);
			return mapping.findForward("ischeckpc");
		} */
		return mapping.findForward("tomindexpage");
	}
	/**
	 * TOM�û���½�������û�������ҳ��
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
	 * TOM�û���½��ѡ������ҳ��
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
		logger.info("############TOM�û���½##############");
		String name = request.getParameter("name");
		String paw = request.getParameter("paw");
		String lid = request.getParameter("lid");
		
		 
		
		logger.info("lid:" + lid);
		logger.info("user_name:" + name);
		logger.info("IP:" + ip);
		//������֤�û����������Ƿ���ȷ
		//�û���ƥ��Ϊ�ַ���
		 String hint = null;
		 Pattern p = Pattern.compile(Expression.letter_number_regexp);
		 Matcher m = p.matcher(name);
		 Matcher pw = p.matcher(paw);
		 boolean b = m.matches();
		 boolean pp = pw.matches();
		 if(b==false){
			 hint = "�˺��������СдӢ���ַ��������ַ�";
			 request.setAttribute("hint", hint);
			 request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage");
		 }
		 if(pp==false){
			 hint = "�����������СдӢ���ַ��������ַ�";
			 request.setAttribute("hint", hint);
			 request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage");
		 }
		if( name.indexOf(" ") != -1 )
		{
			hint = "�˺Ų����пո����";
			request.setAttribute("hint", hint);
			request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage");
		}
		if( name == null || name.equals("") )
		{
			hint = "�˺Ų���Ϊ��";
			request.setAttribute("hint", hint);
			request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage");
		}
		if( name.length() < 5 )
		{
			hint = "�˺�λ������С��5λ";
			request.setAttribute("hint", hint);
			request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage");
		}
		else if( name.length() > 11 )
		{
			
			hint = "�˺�λ�����ܴ���11λ"; 
			request.setAttribute("hint", hint); 
			request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage");
		}
		
		if( paw.length() < 5 )
		{
			hint = "����λ������С��5λ";
			request.setAttribute("hint", hint);
			request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage");
		}else if( paw.length() > 11 )
		{
			
			hint = "����λ�����ܴ���11λ"; 
			request.setAttribute("hint", hint); 
			request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage");
		}
		
		 
		if( paw.indexOf(" ") != -1 )
		{
			hint = "���벻���пո����";
			request.setAttribute("hint", hint);
			request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage");
		}
		if( paw == null || paw.equals("") )
		{
			hint = "���벻��Ϊ��";
			request.setAttribute("hint", hint);
			request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage");
		}
		if ( Expression.hasWeiFaChar(name)) {
			hint = "�������벻Ҫ��gm���ͷ�������!";
			request.setAttribute("hint", hint);
			request.setAttribute("lid", lid);
			 return mapping.findForward("tomvalidatepage"); 
		}
		 
		int u_pk = -1;
		PassportService passportService = new PassportService();
		u_pk = passportService.loginFromTOM(lid, name, paw, ip);
		request.getSession().setAttribute("channel_id", Channel.TOM + "");

		if (u_pk == -1)// ��½��֤ʧ��
		{	
			logger.info("�û���֤ʧ��");
			return mapping.findForward("fail");
		}

		request.setAttribute("uPk", u_pk + "");
		return mapping.findForward("success");
	}
	
	/**
	 * TOM�û���½�������û�������ҳ��
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
