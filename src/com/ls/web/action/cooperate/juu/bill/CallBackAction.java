package com.ls.web.action.cooperate.juu.bill;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ben.jms.JmsUtil;
import com.ls.ben.dao.cooparate.bill.UAccountRecordDao;
import com.ls.ben.vo.cooperate.bill.UAccountRecordVO;
import com.ls.ben.vo.cooperate.dangle.PassportVO;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.encrypt.MD5Util;
import com.ls.web.service.cooperate.dangle.PassportService;
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

		logger.info("##########JUU直接回调############");

		String resultWml = "";

		// 消费金额
		String amount = formatString(request.getParameter("amount"));
		// 支付类型
		String card_type = formatString(request.getParameter("card_type"));
		// 游戏帐号
		String account = formatString(request.getParameter("account"));
		// 商户订单
		String order_no = formatString(request.getParameter("order_no"));
		// 时间戳
		String time = formatString(request.getParameter("time"));
		// 验证码
		String sign = formatString(request.getParameter("sign"));

		logger.info("amount:" + amount);
		logger.info("card_type:" + card_type);
		logger.info("account:" + account);
		logger.info("order_no:" + order_no);
		logger.info("time:" + time);
		logger.info("sign:" + sign);

		String key = "3IOJ3934KJ3493KJ94K";
		String sign_bak = MD5Util.md5Hex(amount + card_type + account
				+ order_no + time + key);
		UAccountRecordVO accountRecord = new UAccountRecordVO();
		UAccountRecordDao accRecordDao = new UAccountRecordDao();

		long time_bak = new Date().getTime() / 1000;// 时间
		long time_long;
		try
		{
			time_long = Long.parseLong(time);
		}
		catch (Exception e)
		{
			resultWml = "err_time";
			request.setAttribute("resultWml", resultWml);
			return mapping.findForward("success");
		}

		if ((time_bak - time_long) > 900 || (time_long - time_bak) > 900)
		{
			resultWml = "err_time";
			request.setAttribute("resultWml", resultWml);
			return mapping.findForward("success");
		}

		UAccountRecordVO vo = accRecordDao
				.getRecord(order_no, Channel.JUU + "");
		if (vo != null)
		{
			resultWml = "err_repeat";
			request.setAttribute("resultWml", resultWml);
			return mapping.findForward("success");
		}

		if (sign_bak.equals(sign))
		{
			PassportService passportService = new PassportService();
			PassportVO passport = passportService.getPassportInfoByUserID(
					account, Channel.JUU);
			EconomyService economyService = new EconomyService();
			GameSystemStatisticsService gsss = new GameSystemStatisticsService();
			MailInfoService mailInfoService = new MailInfoService();
			// 充值成功 记录
			accountRecord.setMoney(Integer.parseInt(amount));
			accountRecord.setUPk(passport.getUPk());
			accountRecord.setChannel(Channel.JUU + "");
			accountRecord.setAccountState("success");
			accountRecord.setCode(order_no);
			accRecordDao.insert(accountRecord);
			// 给玩家增加元宝
			int yb_num = accountRecord.getMoney() * 100;// 1元获得10个元宝
			int jf_num = yb_num * GameConfig.getJifenNum();// 1元获得1个积分

			economyService.addYuanbao(accountRecord.getPPk(), accountRecord
					.getUPk(), yb_num, "chongzhi");
			economyService.addJifen(accountRecord.getUPk(), jf_num);// 增加积分：每成功充值1人民币=1积分

			gsss.addPropNum(0, StatisticsType.PLAYER, 1, "player", "chongzhi",
					accountRecord.getPPk());// 统计充值人次
			// 发邮件
			String title = "充值成功";
			String content = "";
			String time_str = DateUtil.getCurrentTimeStr();
			content = "您于" + time_str + "充值" + accountRecord.getMoney()
					+ "元充值成功，获得【" + GameConfig.getYuanbaoName() + "】×" + yb_num
					+ "！";
			logger.info("邮件标题：" + title);
			logger.info("邮件内容：" + content);
			mailInfoService.sendMailBySystem(accountRecord.getPPk(), title,
					content);

			gsss.addPropNum(0, StatisticsType.RMB, accountRecord.getMoney(),
					StatisticsType.DEDAO, Channel.JUU + "", accountRecord
							.getPPk());// 统计RMB
			JmsUtil.chongzhi(accountRecord.getPPk(), accountRecord.getMoney(),
					accountRecord.getChannel());
			// 应答机制收到支付结果通知时必须回写以"success"开头的字符串
			resultWml = "success";
			request.setAttribute("resultWml", resultWml);
			return mapping.findForward("success");
		}
		else
		{
			// 应答机制收到支付结果通知时必须回写以"success"开头的字符串
			resultWml = "err_sign";
			request.setAttribute("resultWml", resultWml);
			return mapping.findForward("success");
		}
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