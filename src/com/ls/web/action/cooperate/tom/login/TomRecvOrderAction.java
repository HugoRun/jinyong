package com.ls.web.action.cooperate.tom.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.model.user.RoleEntity;
import com.ls.web.service.cooperate.bill.BillService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.RoleService;
import com.pub.MD5;

public class TomRecvOrderAction extends DispatchAction
{

	Logger logger = Logger.getLogger("log.pay");

	public static final int TOM_PAY_MONEY = 15;
	/**
	 * TOM请求充值确认
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		HttpSession session=request.getSession(); 
		
		BillService billService = new BillService();
		String resultWml = null;
		String call_key = "1";
		String pay_result = request.getParameter("pay_result");//支付返回值 1为充值成功 2为充值失败 
		
		String p_pk = request.getParameter("cons_amigo");//支付返回值 返回玩家的UPK
		
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoById(p_pk);
		String u_pk = roleInfo.getBasicInfo().getUPk()+"";
		
		
		HttpSession sessions = roleInfo.getStateInfo().getSession();
		sessions.removeAttribute("pPk"); 
		sessions.removeAttribute("uPk");
		sessions.invalidate();
		
		session.setAttribute("pPk", p_pk);
		session.setAttribute("uPk", roleInfo.getBasicInfo().getUPk()+"");
		request.getSession().setAttribute("pPk", p_pk);
		request.getSession().setAttribute("uPk", roleInfo.getBasicInfo().getUPk()+"");
		roleInfo.getStateInfo().setSession(session);
		
		//System.out.println(u_pk+call_key);
		//pay_result=MD5(cons_amigo+1) --充值成功的结果pay_result返回结果为cons_amigo的值和1组合在MD5加密 失败的话pay_result随便一个MD5值就可以
		String state = MD5.getInstance().getMD5ofStr(p_pk+call_key).toLowerCase();
		if (pay_result.equals(state))
		{
			logger.info("TOM充值渠道请求：充值成功");
			resultWml = billService.getTomSuccessHint();
			//在这里处理充值后的元宝处理
			EconomyService economyService = new EconomyService(); 
			economyService.addYuanbao(Integer.parseInt(p_pk),Integer.parseInt(u_pk), this.TOM_PAY_MONEY, "tomchongzhi");
			//economyService.addYuanbao(u_pk, this.TOM_PAY_MONEY, "tomchongzhi");
		}
		else
		{
			logger.info("TOM充值渠道请求：充值失败");
			resultWml = billService.getTomFailHint(pay_result);
			//resultWml = "充值失败,请重新操作.";
		}
		request.setAttribute("p_pk", p_pk);
		request.setAttribute("resultWml", resultWml.toString());
		return mapping.findForward("success");
	}
}