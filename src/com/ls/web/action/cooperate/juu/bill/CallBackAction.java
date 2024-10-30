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
	 * Ӧ����
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		logger.info("##########JUUֱ�ӻص�############");

		String resultWml = "";

		// ���ѽ��
		String amount = formatString(request.getParameter("amount"));
		// ֧������
		String card_type = formatString(request.getParameter("card_type"));
		// ��Ϸ�ʺ�
		String account = formatString(request.getParameter("account"));
		// �̻�����
		String order_no = formatString(request.getParameter("order_no"));
		// ʱ���
		String time = formatString(request.getParameter("time"));
		// ��֤��
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

		long time_bak = new Date().getTime() / 1000;// ʱ��
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
			// ��ֵ�ɹ� ��¼
			accountRecord.setMoney(Integer.parseInt(amount));
			accountRecord.setUPk(passport.getUPk());
			accountRecord.setChannel(Channel.JUU + "");
			accountRecord.setAccountState("success");
			accountRecord.setCode(order_no);
			accRecordDao.insert(accountRecord);
			// ���������Ԫ��
			int yb_num = accountRecord.getMoney() * 100;// 1Ԫ���10��Ԫ��
			int jf_num = yb_num * GameConfig.getJifenNum();// 1Ԫ���1������

			economyService.addYuanbao(accountRecord.getPPk(), accountRecord
					.getUPk(), yb_num, "chongzhi");
			economyService.addJifen(accountRecord.getUPk(), jf_num);// ���ӻ��֣�ÿ�ɹ���ֵ1�����=1����

			gsss.addPropNum(0, StatisticsType.PLAYER, 1, "player", "chongzhi",
					accountRecord.getPPk());// ͳ�Ƴ�ֵ�˴�
			// ���ʼ�
			String title = "��ֵ�ɹ�";
			String content = "";
			String time_str = DateUtil.getCurrentTimeStr();
			content = "����" + time_str + "��ֵ" + accountRecord.getMoney()
					+ "Ԫ��ֵ�ɹ�����á�" + GameConfig.getYuanbaoName() + "����" + yb_num
					+ "��";
			logger.info("�ʼ����⣺" + title);
			logger.info("�ʼ����ݣ�" + content);
			mailInfoService.sendMailBySystem(accountRecord.getPPk(), title,
					content);

			gsss.addPropNum(0, StatisticsType.RMB, accountRecord.getMoney(),
					StatisticsType.DEDAO, Channel.JUU + "", accountRecord
							.getPPk());// ͳ��RMB
			JmsUtil.chongzhi(accountRecord.getPPk(), accountRecord.getMoney(),
					accountRecord.getChannel());
			// Ӧ������յ�֧�����֪ͨʱ�����д��"success"��ͷ���ַ���
			resultWml = "success";
			request.setAttribute("resultWml", resultWml);
			return mapping.findForward("success");
		}
		else
		{
			// Ӧ������յ�֧�����֪ͨʱ�����д��"success"��ͷ���ַ���
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