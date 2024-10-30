package com.ls.web.action.cooperate.sina.bill;

import com.ls.ben.dao.cooparate.dangle.PassportDao;
import com.ls.ben.vo.cooperate.dangle.PassportVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.util.encrypt.MD5Util;
import com.ls.web.service.cooperate.sina.BillService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.validate.ValidateService;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class BillAction extends DispatchAction
{
	Logger logger = Logger.getLogger("log.pay");

	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String uPk = (String) request.getSession().getAttribute("uPk");

		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());

		String sinaamt_str = request.getParameter("sinaamt_str");

		ValidateService validateService = new ValidateService();
		String hint = validateService
				.validateNonZeroNegativeIntegers(sinaamt_str);

		if (hint != null)
		{
			request.setAttribute("hint", hint);
			return mapping.findForward("index");
		}
		PassportDao passportDao = new PassportDao();
		BillService billService = new BillService();
		String sina_amt = Integer.parseInt(sinaamt_str.trim())+".00";
		PassportVO passport = passportDao.getPassportByUPk(roleInfo
				.getBasicInfo().getUPk());
		int pay_record_id = billService.addPayRecord(passport.getUserId(), sina_amt, roleInfo.getBasicInfo().getPPk());
		String time = Long.toString(new Date().getTime() / 1000);
		String sina_md5 = Integer.parseInt(sinaamt_str.trim())+".00";
		String hashStr = MD5Util.md5Hex("jygame" + passport.getUserId() + sina_md5 + pay_record_id
				+ time + "TY8U2Rx5rh5R6HJqm9O21peCM3sfw95X");
		
		request.setAttribute("sina_uid", passport.getUserId());
		request.setAttribute("sina_amt", sina_amt);
		request.setAttribute("pay_record_id", pay_record_id);
		request.setAttribute("time", time);
		request.setAttribute("hashStr", hashStr);
		return mapping.findForward("submit");
	}
}