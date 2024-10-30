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
		// ����׵�Ψһ�����ţ����Ȳ�����25������ȫ����
		String id = formatString(request.getParameter("id"));
		// �Ʒѽ���0.01��ԪΪ��λ
		String value = formatString(request.getParameter("value"));
		// ֧���û�uid
		String uid = formatString(request.getParameter("uid"));
		// MD5(id_appkey_uid)key����������id 201001211310461011513 uid666666 appkey
		// NOHQL08K0IL3QJIB5KPBSQYE8XY1XWUR ��: key =
		// MD5(201001211310461011513_NOHQL08K0IL3QJIB5KPBSQYE8XY1XWUR_666666) =
		// A587A3BD1147F3DB61BFB32982326B84
		String key = formatString(request.getParameter("key"));
		// deal��ǩ��Я���Ĳ�����ƽ̨������͸��������У��ͽ���
		String pid = formatString(request.getParameter("pid"));
		// deal��ǩ��Я���Ĳ�����ƽ̨������͸��������У��ͽ���
		String pnum = formatString(request.getParameter("pnum"));
		String appkey = "IRBM7IJR5R2WM7U0TMEMQKJI8BY1KCUP";
		String md5_str = MD5Util.md5Hex(id + "_" + appkey + "_" + uid);
		String resultWml = "";
		PassportService passportService = new PassportService();
		MailInfoService mailInfoService = new MailInfoService();
		PassportVO passport = passportService.getPassportInfoByUPk(pid);
		UAccountRecordVO accountRecord = new UAccountRecordVO();
		// ��ֵ�ɹ� ��¼
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
			// ���������Ԫ��
			int yb_num = accountRecord.getMoney();// 1Ԫ���10��Ԫ��
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
					+ "Ԫ��ֵ�ɹ�����á�"+GameConfig.getYuanbaoName()+"����" + yb_num + "��";
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
			resultWml = "0/mall.do?cmd=n0";
			request.setAttribute("resultWml", resultWml);
			return mapping.findForward("success");
		}
		else
		{
			String title = "��ֵʧ��";
			String content = "";
			String time_str = DateUtil.getCurrentTimeStr();
			content = "����" + time_str + "��ֵ" + accountRecord.getMoney()
					+ "Ԫ��ֵʧ�ܣ�";
			logger.info("�ʼ����⣺" + title);
			logger.info("�ʼ����ݣ�" + content);
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
