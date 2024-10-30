package com.ls.web.action.cooperate.dl.bill;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


public class BillAction extends DispatchAction {
	

	Logger logger = Logger.getLogger(BillAction.class);

	/**
	 * 各个充值通道的跳转
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String b_type_str = request.getParameter("b_type");
		
		if( b_type_str!=null && !b_type_str.equals("") )
		{
			int b_type = Integer.parseInt(b_type_str);
			switch( b_type )
			{
				case 1 :
				{
					return mapping.findForward("szx_index");
				}
				case 2 :
				{
					return mapping.findForward("jun_index");
				}
				case 3 :
				{
					return mapping.findForward("sd_index");
				}
				case 4 :
				{
					return mapping.findForward("szf_index");
				}
				case 5 :
				{
					return mapping.findForward("19py_index");
				}
			}
		}
		
		return mapping.findForward("index");
	}
	
	
}