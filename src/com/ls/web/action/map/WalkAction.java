package com.ls.web.action.map;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * ¹¦ÄÜ£º
 * @author ls
 * Apr 19, 2009
 * 1:27:01 PM
 */
public class WalkAction extends Action
{
	Logger logger = Logger.getLogger("log.action");

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		return mapping.findForward("refurbish_scene");
	}

}
