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

	// 菜单显示
	public String getLotteryMenu()
	{
		StringBuffer sb = new StringBuffer();
		LotteryService ls = new LotteryService();
		PlayerLotteryService playerLotteryService = new PlayerLotteryService();
		LotteryVO vo = ls.getSysLotteryInfo();
		String lottery_date = ls.getSysLotteryDate();
		String lottery_date_yes = ls.getSysLotteryYesdate();

		// 本期彩票显示
		long zhu_all = playerLotteryService.getLotteryAllZhu(lottery_date);
		String bonus_outprint = playerLotteryService.getLaborageView(vo
				.getSys_lottery_bonus());

		sb.append("第" + lottery_date + "期竞猜<br/>" + "本期已投注:" + zhu_all
				+ "注<br/>" + "本期奖池累计金额" + vo.getSys_lottery_yb() + ""+GameConfig.getYuanbaoName()+"<br/>"
				+ "本期头奖追加奖励:" + bonus_outprint + "<br/>"
				+ "----------------------------------");

		// 上期彩票显示
		LotteryVO vo_yes = ls.selectLotteryInfoByDate(lottery_date_yes);
		if (vo_yes.getLottery_content() != null)
		{
			String lottery_num = ls.getLotteryPerInfo(vo_yes
					.getLottery_content());
			long frist_bonus = playerLotteryService
					.getLotteryFristBonus(lottery_date_yes);
			long zhu = ls.getAllZhu(lottery_date_yes);
			sb.append("<br/>第" + lottery_date_yes + "期竞猜<br/>" + "中奖号码:"
					+ lottery_num + "<br/>");
			if (zhu == 0)
			{
				sb.append("头奖中奖注数:无<br/>" + "头奖每注奖励:无<br/>");
			}
			else
			{
				sb.append("头奖奖注数:" + zhu + "注<br/>" + "头奖每注奖励:" + frist_bonus
						+ ""+GameConfig.getYuanbaoName()+"<br/>");
			}
			sb.append("----------------------------------");
		}

		return sb.toString();
	}

	// 玩家购买彩票显示
	public String playerBuyLottery(RoleEntity role_info, String lottery_num,
			int lottery_zhu)
	{
		PlayerLotteryService playerLotteryService = new PlayerLotteryService();
		String display = playerLotteryService.buyLottery(role_info,
				lottery_num, lottery_zhu);
		return display;
	}

	// 玩家领取奖励的显示
	public String playerCatchMenu(String action, String name, int p_pk,
			int pageNO, HttpServletRequest request, HttpServletResponse response)
	{
		LotteryDAO dao = new LotteryDAO();
		StringBuffer sb = new StringBuffer();
		sb.append(name + ",欢迎您参加竞猜活动!<br/>");
		int allnum = dao.getLotteryHistoryAllNum();
		if (allnum == 0)
		{
			sb.append("无");
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
				sb.append("</go>第" + vo.getLottery_date()
						+ "期竞猜领奖入口</anchor><br/>");
			}
			sb.append(lotteryFoot("n4", pageall, pageNO, request, response));
		}
		return sb.toString();
	}

	// 玩家领取奖励
	public String playerCatchBonus(RoleEntity role_info, String lottery_date)
	{
		PlayerLotteryService playerLotteryService = new PlayerLotteryService();
		String display = playerLotteryService.playerCatchLotteryBonus(
				role_info, lottery_date);
		return display;
	}

	// 排名显示
	public List<LotteryOutPrintVO> playerLotteryRank()
	{
		PlayerLotteryDAO dao = new PlayerLotteryDAO();
		LotteryService ls = new LotteryService();
		String lottery_date = ls.getSysLotteryDate();
		return dao.getPlayerRank(lottery_date);
	}

	// 系统彩票历史记录显示
	public String sysLotteryHistory(String action, String name, int pageNO,
			HttpServletRequest request, HttpServletResponse response)
	{
		StringBuffer sb = new StringBuffer();
		LotteryService ls = new LotteryService();
		sb.append(name + ",欢迎您进入竞猜活动!<br/>");
		LotteryDAO dao = new LotteryDAO();
		int allnum = dao.getLotteryHistoryAllNum();
		if (allnum == 0)
		{
			sb.append("暂无记录");
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
				sb.append(vo.getLottery_date() + "期中奖号码:" + lottery + "<br/>");
			}
			sb.append(lotteryFoot(action, pageall, pageNO, request, response));
			sb.append("<anchor><go method=\"get\" href=\""
					+ response.encodeURL(GameConfig.getContextPath()
							+ "/newlottery.do?cmd=n9")
					+ "\"></go>查询自选历史号码</anchor>");
		}
		return sb.toString();
	}

	// 玩家彩票历史记录显示
	public String playerLotteryHistory(String action, int p_pk, String name,
			int pageNO, HttpServletRequest request, HttpServletResponse response)
	{
		StringBuffer sb = new StringBuffer();
		LotteryService ls = new LotteryService();
		PlayerLotteryService playerLotteryService = new PlayerLotteryService();
		int allnum = playerLotteryService.getPlayerLotteryAllNum(p_pk);

		sb.append(name + ",欢迎您进入竞猜活动!<br/>");
		if (allnum == 0)
		{
			sb.append("暂无记录");
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
					display = "(中奖!)";
				}
				else
				{
					display = "(未中)";
				}
				sb.append(vo.getLottery_date() + "期自选号码:" + lottery + ""
						+ display + "<br/>");
			}
			sb.append(lotteryFoot(action, pageall, pageNO, request, response));
			sb.append("<anchor><go method=\"get\" href=\""
					+ response.encodeURL(GameConfig.getContextPath()
							+ "/newlottery.do?cmd=n8") + "\"></go>返回上一级</anchor>");
		}
		return sb.toString();
	}

	// 页脚
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
				sb.append("</go>下一页</anchor>");
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
				sb.append("</go>上一页</anchor> ");
			}
			sb.append("第" + (pageNO + 1) + "页/共" + pageall + "页<br/>");
		}
		return sb.toString();
	}
}
