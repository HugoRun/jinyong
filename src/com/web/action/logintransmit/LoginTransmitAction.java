/**
 * 
 */
package com.web.action.logintransmit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 * @author ºîºÆ¾ü
 */
public class LoginTransmitAction extends DispatchAction
{
	Logger logger = Logger.getLogger("log.action");

	/**
	 *  µÇÂ½×ª·¢
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward("refurbish_scene");
	}
}
