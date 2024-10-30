package com.ls.web.action.cooperate.tele.login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SynuserinterfaceAction extends Action
{
	Logger logger = Logger.getLogger("log.service");
	/**
	 * 电信渠道登陆同步接口 
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String custId=request.getParameter("custId");
		String md5code=request.getParameter("md5code");
		String zeroNo=request.getParameter("zeroNo");
		/**返回0表示失败1表示成功**/
		logger.debug("同步方法md5code********************************"+md5code);
		System.out.println("同步了*******************************************");
		try
		{
			if(custId==null ||md5code==null)
			{
				response.getWriter().print(1);
				logger.debug("同步失败");
			}
			else
			{
				request.getSession().setAttribute("md5code", md5code);
				response.getWriter().print(0);
				logger.debug("同步成功");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
