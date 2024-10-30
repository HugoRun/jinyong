package com.lw.action.lottery;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.model.user.RoleEntity;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.player.RoleService;
import com.lw.dao.lottery.LotteryInfoDao;
import com.lw.dao.lottery.LotteryNumberDao;
import com.lw.dao.lottery.PlayerLotteryInfoDao;
import com.lw.service.lottery.LotteryService;
import com.lw.vo.lottery.LotteryInfoVO;
import com.lw.vo.lottery.PlayerLotteryInfoVO;

public class LotteryAction extends DispatchAction
{
	/** 进入彩票竞猜界面 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		DateUtil dateutil = new DateUtil();
		MoneyUtil moneyutil = new MoneyUtil();
		LotteryInfoDao dao = new LotteryInfoDao();
		LotteryNumberDao ndao = new LotteryNumberDao();
		LotteryService se = new LotteryService();
		LotteryInfoVO vo = dao.getLotteryInfo();
		PlayerLotteryInfoDao pldao = new PlayerLotteryInfoDao();
		String allBonus = moneyutil.changeCopperToStr(vo.getLotteryBonus());
		String lotterynum = vo.getLotteryNumberPerDay();
		int lotteryallnum = vo.getLotteryWinNum();
		int todaylotterynum = ndao.getLotteryTodayNumber();
		int lotteryperbonus = pldao.oneLotteryMoney();
		int charitybonus = vo.getSysCharityBonus() / 100;
		int sysbonustype = vo.getSysBonusType();
		int sysbonusnum = vo.getSysBonusNum()/100;
		String lotterycharitynum = vo.getLotteryCharityNum();
		String prop = se.sysOutLotteryBonus();
		if (roleInfo.getBasicInfo().getGrade() < 20)
		{
			request.setAttribute("lotterydisplay", "对不起,请您到20级之后再来竞猜");
			return mapping.findForward("lottery_display");
		}
		if (se.sysTimeAtTwenty() == true)
		{

			String yestoday = dateutil.getTodayStr().replaceAll("-", "");
			int today = Integer.parseInt(yestoday) + 1;
			request.setAttribute("today", today);
			request.setAttribute("yestoday", yestoday);
			request.setAttribute("allBonus", allBonus);
			request.setAttribute("lotterynum", lotterynum);
			request.setAttribute("lotteryperbonus", lotteryperbonus);
			request.setAttribute("charitybonus", charitybonus);
			request.setAttribute("sysbonustype", sysbonustype);
			request.setAttribute("sysbonusnum", sysbonusnum);
			request.setAttribute("todaylotterynum", 0 + "");
			request.setAttribute("lotteryallnum", lotteryallnum);
			request.setAttribute("lotterycharitynum", lotterycharitynum);
			request.setAttribute("prop", prop);
			return mapping.findForward("lottery_menu");
		}
		String today = dateutil.getTodayStr().replaceAll("-", "");
		int yestoday = Integer.parseInt(today) - 1;
		request.setAttribute("today", today);
		request.setAttribute("yestoday", yestoday);
		request.setAttribute("allBonus", allBonus);
		request.setAttribute("lotterynum", lotterynum);
		request.setAttribute("lotteryperbonus", lotteryperbonus);
		request.setAttribute("charitybonus", charitybonus);
		request.setAttribute("sysbonustype", sysbonustype);
		request.setAttribute("sysbonusnum", sysbonusnum);
		request.setAttribute("todaylotterynum", todaylotterynum);
		request.setAttribute("lotteryallnum", lotteryallnum);
		request.setAttribute("lotterycharitynum", lotterycharitynum);
		request.setAttribute("prop", prop);
		return mapping.findForward("lottery_menu");

	}

	/** 判断玩家是否有投注资格 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		LotteryService se = new LotteryService();
		Integer lotterytype = Integer.parseInt(request.getParameter(
				"lotterytype").toString());
		if (se.sysTimeAtEight() == false)
		{
			request.setAttribute("lotterydisplay",
					"投注时间为每天的8:00～20:00，您不前不能投注!");
			return mapping.findForward("lottery_display");
		}
		else
		{
			if (lotterytype == 0)
			{
				if (se.playerGuessLottery(roleInfo.getBasicInfo().getPPk(), lotterytype) == true)
				{
					request.setAttribute("lotterydisplay",
							"对不起,您已经参加过本期彩票竞猜,每人每天只能竞猜一次,请耐心等待开奖结果!");
					return mapping.findForward("lottery_display");
				}
				else
				{
					se.buildPlayerLottery(roleInfo.getBasicInfo().getPPk());
					return mapping.findForward("lottery_set_money");
				}
			}
			else
			{
				if (se.playerGuessLottery(roleInfo.getBasicInfo().getPPk(), lotterytype) == true)
				{
					request.setAttribute("lotterydisplay",
							"对不起,您已经参加过本期彩票竞猜,每人每天只能竞猜一次,请耐心等待开奖结果!");
					return mapping.findForward("lottery_display");
				}
				else
				{
					if (se.getPlayerCharityLottery(roleInfo.getBasicInfo().getPPk()) == false)
					{
						request
								.setAttribute("lotterydisplay",
										"对不起,您没有资格参加慈善竞猜!<br/>每月投注总数超过15次,中奖0次才有资格参加慈善竞猜!");
						return mapping.findForward("lottery_display");
					}
					return mapping.findForward("lottery_number_one");
				}
			}
		}
	}

	/** 判断玩家是否有足够的银两买彩票 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		long cur_copper = roleInfo.getBasicInfo().getCopper();
		
		
		Integer money = Integer.parseInt(request.getParameter("money")
				.toString());
		int M = 0;
		if (money == 1)
		{
			M = 100;
			if (cur_copper < M)
			{
				request.setAttribute("lotterydisplay", "对不起,您钱不够!");
				return mapping.findForward("lottery_display");
			}
			else
			{
				request.setAttribute("money", money);
				return mapping.findForward("lottery_number_one");
			}
		}
		else
			if (money == 2)
			{
				M = 500;
				if (cur_copper < M)
				{
					request.setAttribute("lotterydisplay", "对不起,您钱不够!");
					return mapping.findForward("lottery_display");
				}
				else
				{
					request.setAttribute("money", money);
					return mapping.findForward("lottery_number_one");
				}
			}
			else
				if (money == 3)
				{
					M = 1000;
					if (cur_copper < M)
					{
						request.setAttribute("lotterydisplay", "对不起,您钱不够!");
						return mapping.findForward("lottery_display");
					}
					else
					{
						request.setAttribute("money", money);
						return mapping.findForward("lottery_number_one");
					}
				}
				else
					if (money == 4)
					{
						M = 2000;
						if (cur_copper < M)
						{
							request.setAttribute("lotterydisplay", "对不起,您钱不够!");
							return mapping.findForward("lottery_display");
						}
						else
						{
							request.setAttribute("money", money);
							return mapping.findForward("lottery_number_one");
						}
					}
					else
						if (money == 5)
						{
							M = 5000;
							if (cur_copper < M)
							{
								request.setAttribute("lotterydisplay",
										"对不起,您钱不够!");
								return mapping.findForward("lottery_display");
							}
							else
							{
								request.setAttribute("money", money);
								return mapping
										.findForward("lottery_number_one");
							}
						}
						else
							if (money == 6)
							{
								M = 10000;
								if (cur_copper < M)
								{
									request.setAttribute("lotterydisplay",
											"对不起,您钱不够!");
									return mapping
											.findForward("lottery_display");
								}
								else
								{
									request.setAttribute("money", money);
									return mapping
											.findForward("lottery_number_one");
								}
							}
							else
							{
								request
										.setAttribute("lotterydisplay",
												"请正确投注!");
								return mapping.findForward("lottery_display");
							}
	}

	/** 玩家投注彩票 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		Date now = new Date();
		if (now.getHours() == 20)
		{
			request.setAttribute("lotterydisplay", "投注时间已到,请明天再来!");
			return mapping.findForward("lottery_display");
		}
		else
		{
			LotteryInfoDao dao = new LotteryInfoDao();
			LotteryService se = new LotteryService();
			RoleService roleService = new RoleService();
			RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
			
			Integer pPk = roleInfo.getBasicInfo().getPPk();
			
			Integer lotterytype = Integer.parseInt(request.getParameter(
					"lotterytype").toString());
			String number1 = (String) request.getParameter("number1");
			String number2 = (String) request.getParameter("number2");
			String number3 = (String) request.getParameter("number3");
			String number4 = (String) request.getParameter("number4");
			Integer money = Integer.parseInt(request.getParameter("money")
					.toString());
			if (lotterytype == 0)
			{
				int M = 0;
				if (money == 1)
				{
					M = 100;
				}
				else
					if (money == 2)
					{
						M = 500;
					}
					else
						if (money == 3)
						{
							M = 1000;
						}
						else
							if (money == 4)
							{
								M = 2000;
							}
							else
								if (money == 5)
								{
									M = 5000;
								}
								else
									if (money == 6)
									{
										M = 10000;
									}
									else
									{
										request.setAttribute("lotterydisplay",
												"请正确投注!");
										return mapping
												.findForward("lottery_display");
									}
				se.playerLotteryNumber(pPk, number1, number2, number3, number4,
						lotterytype, M);

				request
						.setAttribute(
								"lotterydisplay",
								"您本期投注:"
										+ number1
										+ ","
										+ number2
										+ ","
										+ number3
										+ ","
										+ number4
										+ ","
										+ "价值"
										+ M
										/ 100
										+ "两!<br/>本期竞猜20:00开奖,21:00～24:00领取奖励,过期后您的奖励将自动进入下期奖励!");
				dao.updateBotteryBonus(M);
				return mapping.findForward("lottery_display");
			}
			else
			{
				se.playerLotteryNumber(pPk, number1, number2, number3, number4,
						lotterytype, money);

				request
						.setAttribute(
								"lotterydisplay",
								"您本期慈善投注:"
										+ number1
										+ ","
										+ number2
										+ ","
										+ number3
										+ ","
										+ number4
										+ "!<br/>本期竞猜20:00开奖,21:00～24:00领取奖励,过期后您的奖励将自动进入下期奖励!");
				return mapping.findForward("lottery_display");
			}
		}
	}

	/** 进入领奖界面(普通) */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		MoneyUtil moneyUtil = new MoneyUtil();
		LotteryService se = new LotteryService();
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		Integer pPk = roleInfo.getBasicInfo().getPPk();
		
		PlayerLotteryInfoDao playerdao = new PlayerLotteryInfoDao();
		PlayerLotteryInfoVO vo = playerdao.getLotteryInfoByPpk(pPk);
		if (se.sysTimeAtTwenty() == false)
		{	
			request.setAttribute("lotterydisplay", "开奖时间没有到,请您在21点以后再领奖!");
			return mapping.findForward("lottery_display");
		}
		else
		{
			if (se.ifPlayerCatch(pPk) == 3)
			{
				request.setAttribute("lotterydisplay", "抱歉您没有投注!");
				return mapping.findForward("lottery_display");
			}
			else
			{
				if (se.ifPlayerCatch(pPk) == 2)
				{
					request.setAttribute("lotterydisplay", "抱歉您没有中奖,请下次努力!");
					playerdao.delPlayerLotteryMultiple(pPk);
					return mapping.findForward("lottery_display");
				}
				else
				{
					if (vo.getLotteryCatchMoney() == 1)
					{
						request.setAttribute("lotterydisplay", "您已经领过奖金!");
						return mapping.findForward("lottery_display");
					}
					else
					{
						if (se.getPlayerWrapSpare(pPk) == -1)
						{
							request.setAttribute("lotterydisplay",
									"您的包裹已满请清理包裹后再来!");
							return mapping.findForward("lottery_display");
						}
						else
						{
							request.setAttribute("lotterydisplay", "您获得"
									+ moneyUtil.changeCopperToStr(se
											.playerHaveMoney(pPk)) + "!");
							se.playerCatchMoney(pPk);
							String prop = se.sysOutLotteryBonus();
							request.setAttribute("prop", prop);
							return mapping.findForward("lottery_get_money");
						}
					}
				}
			}
		}
	}

	/** 进入彩票排名界面 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession();
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		Integer pPk = roleInfo.getBasicInfo().getPPk();
		
		PlayerLotteryInfoDao dao = new PlayerLotteryInfoDao();
		List<Integer> lotteryrank = dao.getLotteryRank();
		PlayerLotteryInfoDao pdao = new PlayerLotteryInfoDao();
		int money = pdao.getPlayerAllBonus(pPk) / 100;
		int rank = dao.playerRank(money);
		request.setAttribute("lotteryrank", lotteryrank);
		request.setAttribute("rank", rank);
		request.setAttribute("money", money);
		return mapping.findForward("lottery_rank");
	}

	/** 进入领奖界面(慈善) */
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		MoneyUtil moneyUtil = new MoneyUtil();
		LotteryService se = new LotteryService();
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		Integer pPk = roleInfo.getBasicInfo().getPPk();
		
		PlayerLotteryInfoDao playerdao = new PlayerLotteryInfoDao();
		LotteryNumberDao dao = new LotteryNumberDao();
		if (se.sysTimeAtTwenty() == false)
		{
			request.setAttribute("lotterydisplay", "开奖时间没有到,请您在21点以后再领奖!");
			return mapping.findForward("lottery_display");
		}
		else
		{
			if (se.ifPlayerCatchCharity(pPk) == 3)
			{
				request.setAttribute("lotterydisplay", "抱歉您没有投注或已经领过奖金!");
				return mapping.findForward("lottery_display");
			}
			else
			{
				if (se.ifPlayerCatchCharity(pPk) == 2)
				{
					request.setAttribute("lotterydisplay", "抱歉您没有中奖,请下次努力!");
					playerdao.delPlayerLotteryMultiple(pPk);
					return mapping.findForward("lottery_display");
				}
				else
				{
					request.setAttribute("lotterydisplay", "您获得"
							+ moneyUtil.changeCopperToStr(se
									.playerHaveCharityMoney(pPk)) + "!");
					se.playerCatchCharityMoney(pPk);
					dao.delCharityNum(pPk);
					String prop = se.sysOutLotteryBonus();
					request.setAttribute("prop", prop);
					return mapping.findForward("lottery_get_money");
				}
			}
		}
	}
}
