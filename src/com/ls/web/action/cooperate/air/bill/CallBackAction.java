package com.ls.web.action.cooperate.air.bill;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.kong.sns.rest.client.Client;
import net.kong.sns.rest.client.ClientFactory;
import net.kong.sns.rest.client.pay.PayIsCompletedMethodInvoke;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.vo.cooperate.bill.UAccountRecordVO;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.DateUtil;
import com.ls.web.service.cooperate.bill.BillService;
import com.ls.web.service.player.EconomyService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.pm.service.mail.MailInfoService;

public class CallBackAction extends DispatchAction
{
	Logger logger = Logger.getLogger("log.pay");

	/**
	 * 应答处理
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String resultWml = "";
		logger.info("##########空中网直接回调############");
		// userId
		String userId = request.getParameter("userId");
		// sessionKey
		String sessionKey = formatString(request.getParameter("sessionKey"));
		// pwd
		String pwd = formatString(request.getParameter("pwd"));
		// orderId
		String orderId = formatString(request.getParameter("orderId"));
		// result
		String result = request.getParameter("result");
		String account_state = "";
		Client client;
		try
		{
			client = ClientFactory.createInstance(sessionKey);
			if (result.equals("1"))
			{
				BillService billService = new BillService();
				UAccountRecordVO order = billService.getAccountRecord(Integer
						.parseInt(orderId));

				account_state = "success";

				// 测试用
				PayIsCompletedMethodInvoke p = new PayIsCompletedMethodInvoke(
						order.getId());
				if (client.pay_isCompleted(order.getId()))
				{
					// 兑换成功
					int add_yuanbao = order.getMoney() * 10;
					int add_jifen = add_yuanbao * GameConfig.getJifenNum();

					EconomyService economyService = new EconomyService();
					//增加积分：每成功充值1人民币=1积分
					economyService.addJifen(order.getUPk(), add_jifen);
					economyService.addYuanbao(0,order.getUPk(), add_yuanbao,"chongzhi");
					
					
					long yuanbao = economyService.getYuanbao(order.getUPk());
					String title = "充值成功";
					String time_str = DateUtil.getCurrentTimeStr();
					String content = "您于" + time_str + order.getMoney()
							+ "K金购买"+GameConfig.getYuanbaoName()+"成功，获得【"+GameConfig.getYuanbaoName()+"】×" + add_yuanbao + "!目前您共有"+GameConfig.getYuanbaoName()+""
							+ yuanbao;
					logger.info("邮件标题：" + title);
					logger.info("邮件内容：" + content);
					MailInfoService mailInfoService = new MailInfoService();
					mailInfoService.sendMailBySystem(order.getPPk(), title,
							content);
					GameSystemStatisticsService gsss = new GameSystemStatisticsService();
					gsss.addPropNum(0, StatisticsType.RMB, order.getMoney(),
							StatisticsType.DEDAO, "chongzhi", order.getPPk());// 统计RMB
					gsss.addPropNum(0, StatisticsType.PLAYER, 1, "player", "chongzhi",order.getUPk());//统计充值人次
					resultWml = "{\"userId\":" + userId + ",\"orderId\":"
							+ orderId + ",\"amount\":" + order.getMoney() + "}";
					billService.updateState(order.getId(), account_state);
				}
				else
				{

				}
			}
		}
		catch (IOException e)
		{
			resultWml = "网络异常请重试";
			e.printStackTrace();
		}
		request.setAttribute("resultWml", resultWml);
		return mapping.findForward("success");
	}

	String formatString(String text)
	{
		if (text == null)
		{
			return "";
		}
		return text;
	}
}
