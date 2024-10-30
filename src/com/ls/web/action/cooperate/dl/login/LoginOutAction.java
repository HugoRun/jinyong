package com.ls.web.action.cooperate.dl.login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.ben.vo.cooperate.dangle.PassportVO;
import com.ls.pub.util.encrypt.MD5Util;
import com.ls.web.service.cooperate.dangle.PassportService;


/**
 * @author Administrator 
 * 
 */
public class LoginOutAction extends Action
{
	
	Logger logger = Logger.getLogger("log.service");
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	
	{
		HttpSession session = request.getSession();
		String p_pk = (String)session.getAttribute("pPk");
		String u_pk = (String)session.getAttribute("uPk");
		
		String userName = "";
		
		if( p_pk==null || u_pk==null  )
		{
			logger.info("session中已无有效的pPk和uPk");
			return null;
		}

		PassportVO passport = null;
		
		PassportService passportService = new PassportService();
		
		
		passport = passportService.getPassportInfoByUPk(u_pk);
		
		if( passport!=null )
		{
			userName = passport.getUserName();
		}
		
		String timestamp=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		
		String u="logout-17-1-1.gmcs?username="+userName+"&timestamp="+timestamp+"&encrypt-type=0&merchant-key=xWx86231";
        logger.info("字符串:"+u);
		String ver=MD5Util.md5Hex(u);
		logger.info("加密字符串:"+ver);
        String urls="http://jy1.downjoy.com/plaf/wml/logout-17-1-1.gmcs?username="+userName+"&encrypt-type=0&verify-string="+ver+"&timestamp="+timestamp;
		
        try
		{
			response.sendRedirect(urls);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
        
		return null;
	}
}
