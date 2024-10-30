package com.ls.web.action.cooperate.tx;

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

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		// 此项交易的唯一订单号，长度不超过25，内容全数字
		String id = formatString(request.getParameter("id"));
		// 计费金额，以0.01蛙元为单位
		String value = formatString(request.getParameter("value"));
		// 支付用户uid
		String uid = formatString(request.getParameter("uid"));
		// MD5(id_appkey_uid)key举例：订单id 201001211310461011513 uid666666 appkey
		// NOHQL08K0IL3QJIB5KPBSQYE8XY1XWUR 则: key =
		// MD5(201001211310461011513_NOHQL08K0IL3QJIB5KPBSQYE8XY1XWUR_666666) =
		// A587A3BD1147F3DB61BFB32982326B84
		String key = formatString(request.getParameter("key"));
		// deal标签中携带的参数，平台仅进行透传，不做校验和解析
		String pid = formatString(request.getParameter("pid"));
		// deal标签中携带的参数，平台仅进行透传，不做校验和解析
		String pnum = formatString(request.getParameter("pnum"));
		String appkey = "IRBM7IJR5R2WM7U0TMEMQKJI8BY1KCUP";
		String md5_str = MD5Util.md5Hex(id + "_" + appkey + "_" + uid);
		String resultWml = "";
		PassportService passportService = new PassportService();
		MailInfoService mailInfoService = new MailInfoService();
		PassportVO passport = passportService.getPassportInfoByUPk(pid);
		UAccountRecordVO accountRecord = new UAccountRecordVO();
		// 充值成功 记录
		accountRecord.setMoney(Integer.parseInt(pnum));
		accountRecord.setUPk(passport.getUPk());
		accountRecord.setPPk(0);
		accountRecord.setChannel(Channel.TXW + "");
		accountRecord.setAccountState("success");
		accountRecord.setCode(id);
		if (md5_str.equals(key) && passport != null)
		{

			UAccountRecordDao accRecordDao = new UAccountRecordDao();
			EconomyService economyService = new EconomyService();
			GameSystemStatisticsService gsss = new GameSystemStatisticsService();

			accRecordDao.insert(accountRecord);
			// 给玩家增加元宝
			int yb_num = accountRecord.getMoney();// 1元获得10个元宝
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
					+ "元充值成功，获得【"+GameConfig.getYuanbaoName()+"】×" + yb_num + "！";
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
			resultWml = "0/mall.do?cmd=n0";
			request.setAttribute("resultWml", resultWml);
			return mapping.findForward("success");
		}
		else
		{
			String title = "充值失败";
			String content = "";
			String time_str = DateUtil.getCurrentTimeStr();
			content = "您于" + time_str + "充值" + accountRecord.getMoney()
					+ "元充值失败！";
			logger.info("邮件标题：" + title);
			logger.info("邮件内容：" + content);
			mailInfoService.sendMailBySystem(accountRecord.getPPk(), title,
					content);
			resultWml = "1/mall.do?cmd=n0";
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
