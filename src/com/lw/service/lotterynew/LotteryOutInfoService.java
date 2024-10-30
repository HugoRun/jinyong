package com.lw.service.lotterynew;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.lw.dao.lotterynew.LotteryDAO;
import com.lw.dao.lotterynew.PlayerLotteryDAO;
import com.lw.vo.lotterynew.LotteryOutPrintVO;
import com.lw.vo.lotterynew.LotteryVO;

public class LotteryOutInfoService
{
	Logger logger = Logger.getLogger(LotteryOutInfoService.class);

	// �˵���ʾ
	public String getLotteryMenu()
	{
		StringBuffer sb = new StringBuffer();
		LotteryService ls = new LotteryService();
		PlayerLotteryService playerLotteryService = new PlayerLotteryService();
		LotteryVO vo = ls.getSysLotteryInfo();
		String lottery_date = ls.getSysLotteryDate();
		String lottery_date_yes = ls.getSysLotteryYesdate();

		// ���ڲ�Ʊ��ʾ
		long zhu_all = playerLotteryService.getLotteryAllZhu(lottery_date);
		String bonus_outprint = playerLotteryService.getLaborageView(vo
				.getSys_lottery_bonus());

		sb.append("��" + lottery_date + "�ھ���<br/>" + "������Ͷע:" + zhu_all
				+ "ע<br/>" + "���ڽ����ۼƽ��" + vo.getSys_lottery_yb() + ""+GameConfig.getYuanbaoName()+"<br/>"
				+ "����ͷ��׷�ӽ���:" + bonus_outprint + "<br/>"
				+ "----------------------------------");

		// ���ڲ�Ʊ��ʾ
		LotteryVO vo_yes = ls.selectLotteryInfoByDate(lottery_date_yes);
		if (vo_yes.getLottery_content() != null)
		{
			String lottery_num = ls.getLotteryPerInfo(vo_yes
					.getLottery_content());
			long frist_bonus = playerLotteryService
					.getLotteryFristBonus(lottery_date_yes);
			long zhu = ls.getAllZhu(lottery_date_yes);
			sb.append("<br/>��" + lottery_date_yes + "�ھ���<br/>" + "�н�����:"
					+ lottery_num + "<br/>");
			if (zhu == 0)
			{
				sb.append("ͷ���н�ע��:��<br/>" + "ͷ��ÿע����:��<br/>");
			}
			else
			{
				sb.append("ͷ����ע��:" + zhu + "ע<br/>" + "ͷ��ÿע����:" + frist_bonus
						+ ""+GameConfig.getYuanbaoName()+"<br/>");
			}
			sb.append("----------------------------------");
		}

		return sb.toString();
	}

	// ��ҹ����Ʊ��ʾ
	public String playerBuyLottery(RoleEntity role_info, String lottery_num,
			int lottery_zhu)
	{
		PlayerLotteryService playerLotteryService = new PlayerLotteryService();
		String display = playerLotteryService.buyLottery(role_info,
				lottery_num, lottery_zhu);
		return display;
	}

	// �����ȡ��������ʾ
	public String playerCatchMenu(String action, String name, int p_pk,
			int pageNO, HttpServletRequest request, HttpServletResponse response)
	{
		LotteryDAO dao = new LotteryDAO();
		StringBuffer sb = new StringBuffer();
		sb.append(name + ",��ӭ���μӾ��»!<br/>");
		int allnum = dao.getLotteryHistoryAllNum();
		if (allnum == 0)
		{
			sb.append("��");
		}
		else
		{

			int pageall = 0;
			if (allnum > 24)
			{
				pageall = 3;
			}
			else
			{
				pageall = allnum / 8 + (allnum % 8 == 0 ? 0 : 1);
			}
			List<LotteryVO> list = dao.getLotteryHistory(pageNO, 8);
			for (int i = 0; i < list.size(); i++)
			{
				LotteryVO vo = list.get(i);
				sb.append("<anchor><go method=\"post\" href=\""
						+ response.encodeURL(GameConfig.getContextPath()
								+ "/newlottery.do") + "\">");
				sb
						.append("<postfield name=\"cmd\" value=\"" + action
								+ "\" />");
				sb.append("<postfield name=\"lottery_date\" value=\""
						+ vo.getLottery_date() + "\" /> ");
				sb.append("</go>��" + vo.getLottery_date()
						+ "�ھ����콱���</anchor><br/>");
			}
			sb.append(lotteryFoot("n4", pageall, pageNO, request, response));
		}
		return sb.toString();
	}

	// �����ȡ����
	public String playerCatchBonus(RoleEntity role_info, String lottery_date)
	{
		PlayerLotteryService playerLotteryService = new PlayerLotteryService();
		String display = playerLotteryService.playerCatchLotteryBonus(
				role_info, lottery_date);
		return display;
	}

	// ������ʾ
	public List<LotteryOutPrintVO> playerLotteryRank()
	{
		PlayerLotteryDAO dao = new PlayerLotteryDAO();
		LotteryService ls = new LotteryService();
		String lottery_date = ls.getSysLotteryDate();
		return dao.getPlayerRank(lottery_date);
	}

	// ϵͳ��Ʊ��ʷ��¼��ʾ
	public String sysLotteryHistory(String action, String name, int pageNO,
			HttpServletRequest request, HttpServletResponse response)
	{
		StringBuffer sb = new StringBuffer();
		LotteryService ls = new LotteryService();
		sb.append(name + ",��ӭ�����뾺�»!<br/>");
		LotteryDAO dao = new LotteryDAO();
		int allnum = dao.getLotteryHistoryAllNum();
		if (allnum == 0)
		{
			sb.append("���޼�¼");
		}
		else
		{
			int pageall = 0;
			if (allnum > 24)
			{
				pageall = 3;
			}
			else
			{
				pageall = allnum / 8 + (allnum % 8 == 0 ? 0 : 1);
			}
			List<LotteryVO> list = dao.getLotteryHistory(pageNO, 8);
			for (int i = 0; i < list.size(); i++)
			{
				LotteryVO vo = list.get(i);
				String lottery = ls.getLotteryPerInfo(vo.getLottery_content());
				sb.append(vo.getLottery_date() + "���н�����:" + lottery + "<br/>");
			}
			sb.append(lotteryFoot(action, pageall, pageNO, request, response));
			sb.append("<anchor><go method=\"get\" href=\""
					+ response.encodeURL(GameConfig.getContextPath()
							+ "/newlottery.do?cmd=n9")
					+ "\"></go>��ѯ��ѡ��ʷ����</anchor>");
		}
		return sb.toString();
	}

	// ��Ҳ�Ʊ��ʷ��¼��ʾ
	public String playerLotteryHistory(String action, int p_pk, String name,
			int pageNO, HttpServletRequest request, HttpServletResponse response)
	{
		StringBuffer sb = new StringBuffer();
		LotteryService ls = new LotteryService();
		PlayerLotteryService playerLotteryService = new PlayerLotteryService();
		int allnum = playerLotteryService.getPlayerLotteryAllNum(p_pk);

		sb.append(name + ",��ӭ�����뾺�»!<br/>");
		if (allnum == 0)
		{
			sb.append("���޼�¼");
		}
		else
		{
			int pageall = 0;
			if (allnum > 24)
			{
				pageall = 3;
			}
			else
			{
				pageall = allnum / 8 + (allnum % 8 == 0 ? 0 : 1);
			}
			List<LotteryOutPrintVO> list = playerLotteryService
					.getPlayerLotteryHistory(p_pk, pageNO, 8);
			for (int i = 0; i < list.size(); i++)
			{
				LotteryOutPrintVO vo = list.get(i);
				String lottery = ls.getLotteryPerInfo(vo.getPlayer_lottery());
				int x = playerLotteryService.playerCatchLotteyLevel(vo
						.getPlayer_lottery(), vo.getSys_lottery());
				String display = "";
				if (x > 1)
				{
					display = "(�н�!)";
				}
				else
				{
					display = "(δ��)";
				}
				sb.append(vo.getLottery_date() + "����ѡ����:" + lottery + ""
						+ display + "<br/>");
			}
			sb.append(lotteryFoot(action, pageall, pageNO, request, response));
			sb.append("<anchor><go method=\"get\" href=\""
					+ response.encodeURL(GameConfig.getContextPath()
							+ "/newlottery.do?cmd=n8") + "\"></go>������һ��</anchor>");
		}
		return sb.toString();
	}

	// ҳ��
	private String lotteryFoot(String action, int pageall, int pageNO,
			HttpServletRequest request, HttpServletResponse response)
	{
		StringBuffer sb = new StringBuffer();
		if (pageall != 0)
		{
			if (pageNO != pageall - 1)
			{
				sb.append("<anchor><go method=\"post\" href=\""
						+ response.encodeURL(GameConfig.getContextPath()
								+ "/newlottery.do") + "\">");
				sb.append("<postfield name=\"pageNO\" value=\"" + (pageNO + 1)
						+ "\" /> ");
				sb
						.append("<postfield name=\"cmd\" value=\"" + action
								+ "\" />");
				sb.append("</go>��һҳ</anchor>");
			}
			if (pageNO != 0)
			{
				sb.append("<anchor><go method=\"post\" href=\""
						+ response.encodeURL(GameConfig.getContextPath()
								+ "/newlottery.do") + "\">");
				sb.append("<postfield name=\"pageNO\" value=\"" + (pageNO - 1)
						+ "\" /> ");
				sb
						.append("<postfield name=\"cmd\" value=\"" + action
								+ "\" />");
				sb.append("</go>��һҳ</anchor> ");
			}
			sb.append("��" + (pageNO + 1) + "ҳ/��" + pageall + "ҳ<br/>");
		}
		return sb.toString();
	}
}
