package com.ls.web.action.cooperate.dl.bill.yeepay;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


public class ComfirmAction extends DispatchAction {
	

	Logger logger = Logger.getLogger("log.pay");

	/**
	 * »∑»œ≥‰÷µ
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String amount=request.getParameter("pay");
		String pay_cardno=request.getParameter("code");
		String pay_cardpwd=request.getParameter("psw");
		String pd_FrpId=request.getParameter("pd_FrpId");
		
		
		request.setAttribute("pay", amount);
		request.setAttribute("code", pay_cardno);
		request.setAttribute("psw", pay_cardpwd);
		request.setAttribute("pd_FrpId", pd_FrpId);
		
		if( pd_FrpId.equals("SNDACARD"))
		{
			return mapping.findForward("sd_comfirm");
		}
		else if( pd_FrpId.equals("SZX"))
		{
			return mapping.findForward("szx_comfirm");
		}
		
		return mapping.findForward("index");
	}
}