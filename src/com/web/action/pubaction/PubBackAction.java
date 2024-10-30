/**
 * 
 */
package com.web.action.pubaction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
/**
 * @author Administrator
 * 
 */
public class PubBackAction extends Action {
	Logger logger = Logger.getLogger("log.action");

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String hint = request.getParameter("hint");
		if(hint == null || hint.equals("") || hint.equals("null")){
			hint = (String)request.getAttribute("hint");
		}
		if(null==hint||"".equals(hint.trim())){
			hint = (String) request.getAttribute("jieyihint");
		}
		request.setAttribute("hint", hint);
		return mapping.findForward("no_refurbish_scene");
	}
}
