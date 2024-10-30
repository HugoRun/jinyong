package com.ls.web.action.cooperate.tiao.bill;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.model.user.RoleEntity;
import com.ls.web.service.cooperate.tiao.BillService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.validate.ValidateService;


public class BillAction extends DispatchAction {
	

	Logger logger = Logger.getLogger("log.pay");
	

	/**
	 * 跳网付费通道
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String user_name = (String)request.getSession().getAttribute("user_name");
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		
		String kbamt_str = request.getParameter("kbamt");//用户提交扣费金额
		
		ValidateService validateService = new ValidateService();
		String hint = validateService.validateNonZeroNegativeIntegers(kbamt_str);
		
		if( hint!=null )
		{
			request.setAttribute("hint", hint);
			return mapping.findForward("index");
		}
		
		int kbamt = Integer.parseInt(kbamt_str.trim());
		
		if( kbamt>50000 )
		{
			hint = "每次兑换金豆的数量不得超过50000金豆,可进行多次兑换";
			request.setAttribute("hint", hint);
			return mapping.findForward("index");
		}
		
		BillService billService = new BillService();
		hint = billService.pay(roleInfo,user_name,kbamt_str.trim());
		
		request.setAttribute("hint", hint);
		return mapping.findForward("result");
	}
	
	
}