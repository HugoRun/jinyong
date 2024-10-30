package com.ls.web.action.cooperate.ok.bill;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.vo.cooperate.dangle.PassportVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.Channel;
import com.ls.web.service.cooperate.dangle.PassportService;
import com.ls.web.service.cooperate.sky.BillService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.validate.ValidateService;

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

		String amt_str = request.getParameter("kbamt");// 用户提交扣费金额

		ValidateService validateService = new ValidateService();
		String hint = validateService.validateNonZeroNegativeIntegers(amt_str);

		if (hint != null)
		{
			request.setAttribute("hint", hint);
			return mapping.findForward("index");
		}

		int amt = Integer.parseInt(amt_str.trim());

		HttpSession session = request.getSession();
		String osid = (String) session.getAttribute("osid");

		PassportService passportService = new PassportService();
		PassportVO passport = passportService.getPassportInfoByUserID(osid,
				Channel.OKP);
		
		BillService billService = new BillService();
		Map<String, String> pay_results = billService.OKpay(uPk, osid, passport.getUserName(),
				amt + "", roleInfo.getBasicInfo().getPPk());

		String result = null;
		Set<String> keys = pay_results.keySet();
		for(String key:keys )
		{
			if( key.indexOf("result")!=-1 )
			{
				result = pay_results.get(key);
			}
		}
		
		if (result != null && result.equals("10"))
		{
			EconomyService economyService = new EconomyService();
			long yuanbao = economyService.getYuanbao(Integer.parseInt(uPk));
			// 兑换成功,您获得了50个【元宝】，目前您共有【元宝】×120！
			String sd = billService.chongZhiJiangLi(roleInfo.getBasicInfo()
					.getPPk(), amt);
			hint = "兑换成功,您获得了" + amt*112 + "个【元宝】,目前您共有【元宝】×" + yuanbao + "!" + sd;
			request.setAttribute("hint", hint);
			return mapping.findForward("success");
		}
		else
		{
			if (result.equals("11"))
			{
				hint = "认证失败,请重新兑换!";
			}
			if (result.equals("13"))
			{
				hint = "余额不足,请重新兑换!";
			}
			else
			{
				hint = "未知错误,请重新兑换!";
			}
			request.setAttribute("hint", hint);
			return mapping.findForward("fail");
		}
	}
}
