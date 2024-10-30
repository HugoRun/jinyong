package com.lw.service.lotterynew;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ls.ben.cache.staticcache.equip.EquipCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.model.equip.GameEquip;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.log.LogService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.rank.RankService;
import com.lw.dao.lotterynew.LotteryDAO;
import com.lw.dao.lotterynew.PlayerLotteryDAO;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.lw.vo.lotterynew.LotteryOutPrintVO;
import com.lw.vo.lotterynew.LotteryVO;
import com.lw.vo.lotterynew.PlayerLotteryVO;

public class PlayerLotteryService
{
	Logger logger = Logger.getLogger(PlayerLotteryService.class);

	/** 玩家购买彩票* */
	private void playerBuyLottery(PlayerLotteryVO vo)
	{
		PlayerLotteryDAO dao = new PlayerLotteryDAO();
		dao.insertPlayerLotteryInfo(vo);
	}

	/** *得到玩家的中奖等级 */
	public int playerCatchLotteyLevel(String lottery_num, String sys_lottery)
	{
		int x = 0;
		if (sys_lottery == null)
		{
			return x;
		}
		String[] lottery_num_temp = lottery_num.split(",");
		String[] sys_lottery_temp = sys_lottery.split(",");
		if (lottery_num_temp[0].equals(sys_lottery_temp[0]))
		{
			x = x + 1;
		}
		if (lottery_num_temp[1].equals(sys_lottery_temp[1]))
		{
			x = x + 1;
		}
		if (lottery_num_temp[2].equals(sys_lottery_temp[2]))
		{
			x = x + 1;
		}
		if (lottery_num_temp[3].equals(sys_lottery_temp[3]))
		{
			x = x + 1;
		}
		return x;
	}

	// 是否可以参加投注
	public boolean playerCanGetLottery(int p_pk)
	{
		LotteryService ls = new LotteryService();
		String lottery_date = ls.getSysLotteryDate();
		PlayerLotteryDAO dao = new PlayerLotteryDAO();
		PlayerLotteryVO vo_temp = dao.getPlayerLotteryInfo(p_pk, lottery_date);
		if (vo_temp == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/** 购买彩票流程 */
	public String buyLottery(RoleEntity role_info, String lottery_num,
			int lottery_zhu)
	{
		LotteryService ls = new LotteryService();
		PlayerLotteryVO vo = new PlayerLotteryVO();
		String lottery_date = ls.getSysLotteryDate();
		if (lottery_zhu < 0)
		{
			lottery_zhu = 2;
		}
		if (lottery_zhu > 999)
		{
			lottery_zhu = 999;
		}
		EconomyService economyService = new EconomyService();
		long user_yb = economyService.getYuanbao(role_info.getBasicInfo()
				.getUPk());
		int yb = 50 * lottery_zhu;
		if (yb > user_yb)
		{
			return "您的"+GameConfig.getYuanbaoName()+"不足!";
		}
		else
		{
			LotteryDAO lotteryDAO = new LotteryDAO();
			LotteryVO lotteryVO = ls.selectLotteryInfoByDate(lottery_date);
			// 得到中奖等级
			int lv = playerCatchLotteyLevel(lottery_num, lotteryVO
					.getLottery_content());
			vo.setP_pk(role_info.getBasicInfo().getPPk());
			vo.setLottery_date(lottery_date);
			vo.setLottery_content(lottery_num);
			vo.setLottery_zhu(lottery_zhu);
			vo.setLottery_bonus_lv(lv);
			if (lv == 3)
			{
				vo.setLottery_bonus(5000);
			}
			else
				if (lv == 2)
				{
					vo.setLottery_bonus(100);
				}
				else
				{
					vo.setLottery_bonus(0);
				}
			// 生成彩票
			playerBuyLottery(vo);
			// 扣除元宝
			economyService.spendYuanbao(role_info.getBasicInfo().getUPk(), yb);
			// 更新玩家中奖的内容
			String catch_player = ls.changePlayer(lv, lotteryVO
					.getLottery_catch_player(), lottery_zhu);
			lotteryDAO.updateLotteryAllybAndPlayer(lottery_date, yb,
					catch_player);

			// 得到开将时间
			Date date = new Date();
			int hour_temp = date.getHours();
			int hour = 0;
			if (hour_temp != 23)
			{
				hour = hour_temp + 1;
			}
			String lottery_num_out = ls.getLotteryPerInfo(lottery_num);
			return "您本期投注:" + lottery_num_out + ",价值" + yb + ""+GameConfig.getYuanbaoName()+"!本期竞猜" + hour
					+ "点开奖，请在开奖后的24小时内领取奖励!";
		}
	}

	/** 玩家领取奖励* */
	public String playerCatchLotteryBonus(RoleEntity role_info,
			String lottery_date)
	{
		PlayerLotteryDAO dao = new PlayerLotteryDAO();
		LotteryDAO lotteryDAO = new LotteryDAO();
		LotteryService ls = new LotteryService();
		EconomyService economyService = new EconomyService();
		LotteryVO sys_vo = ls.getSysLotteryInfo();
		PlayerLotteryVO vo = dao.getPlayerLotteryInfo(role_info.getBasicInfo()
				.getPPk(), lottery_date);

		if (vo == null)
		{
			return "您没有购买" + lottery_date + "期彩票!";
		}
		else
		{
			if (vo.getIs_have() == 0)
			{
				long get_yb = Long.parseLong(vo.getLottery_bonus()
						* vo.getLottery_zhu() + "")
						* (1000 - sys_vo.getSys_lottery_tax()) / 1000;
				String bonus_sys = "";
				if (vo.getLottery_bonus_lv() == 4)
				{
					// 处理头奖的奖励
					if (sys_vo.getSys_lottery_bonus() != null)
					{
						int bonusNum = getLaborageBonusNum(sys_vo
								.getSys_lottery_bonus());

						int wrapSpare = role_info.getBasicInfo().getWrapSpare();
						if (wrapSpare == 0 || wrapSpare < 0
								|| wrapSpare < bonusNum)
						{
							return "您包裹不足,请清理包裹!";
						}
						else
						{
							String[] bonusprop = sys_vo.getSys_lottery_bonus()
									.split(",");
							for (int i = 0; i < bonusprop.length; i++)
							{
								String[] prop = bonusprop[i].split("-");
								if (prop[0].equals("0"))
								{
									int laboragemoney = Integer
											.parseInt(prop[2]);

									// 监控
									LogService logService = new LogService();
									logService.recordMoneyLog(role_info
											.getBasicInfo().getPPk(), role_info
											.getBasicInfo().getName(),
											role_info.getBasicInfo()
													.getCopper()
													+ "", laboragemoney + "",
											"彩票得到");

									role_info.getBasicInfo().addCopper(
											laboragemoney);// 玩家领取奖金
									// 执行统计
									GameSystemStatisticsService gsss = new GameSystemStatisticsService();
									gsss.addPropNum(6, StatisticsType.MONEY,
											laboragemoney,
											StatisticsType.DEDAO,
											StatisticsType.XITONG, role_info
													.getBasicInfo().getPPk());
								}
								else
									if (prop[0].equals("5"))
									{
										int experience = Integer
												.parseInt(prop[2]);

										// 监控
										LogService logService = new LogService();
										logService.recordExpLog(role_info
												.getBasicInfo().getPPk(),
												role_info.getBasicInfo()
														.getName(), role_info
														.getBasicInfo()
														.getCurExp(),
												experience + "", "彩票得到");

										role_info.getBasicInfo()
												.updateAddCurExp(experience);
									}
									else
									{
										GoodsService goodsService = new GoodsService();
										goodsService.putGoodsToWrap(role_info
												.getBasicInfo().getPPk(),
												Integer.parseInt(prop[1]),
												Integer.parseInt(prop[0]), 0,
												Integer.parseInt(prop[2]),GameLogManager.G_SYSTEM);// 玩家获得系统发给的道具
										// 执行统计
										GameSystemStatisticsService gsss = new GameSystemStatisticsService();
										gsss.addPropNum(Integer
												.parseInt(prop[1]), Integer
												.parseInt(prop[0]), Integer
												.parseInt(prop[2]),
												StatisticsType.DEDAO,
												StatisticsType.XITONG,
												role_info.getBasicInfo()
														.getPPk());
									}
							}
						}
						bonus_sys = getLaborageView(sys_vo
								.getSys_lottery_bonus());
					}

					economyService.addYuanbao(
							role_info.getBasicInfo().getUPk(), get_yb);
					dao.updatePlayerCatchBonus(role_info.getBasicInfo()
							.getPPk(), lottery_date);
					lotteryDAO.updateLotteryCatchyb(lottery_date, vo
							.getLottery_bonus()
							* vo.getLottery_zhu());
					// 记录玩家领取奖励的排行
					RankService rankService = new RankService();
					rankService.updateAdd(role_info.getBasicInfo().getPPk(),
							"boyi", get_yb);
					return "你领取了第" + lottery_date + "期中奖奖励:"+GameConfig.getYuanbaoName()+"" + get_yb + " "
							+ bonus_sys;
				}
				else
					if (vo.getLottery_bonus_lv() < 2)
					{
						return "您所购买的" + lottery_date + "期彩票没有中奖,请下次继续努力!";
					}
					else
					{
						economyService.addYuanbao(role_info.getBasicInfo()
								.getUPk(), get_yb);
						dao.updatePlayerCatchBonus(role_info.getBasicInfo()
								.getPPk(), lottery_date);
						lotteryDAO.updateLotteryCatchyb(lottery_date, vo
								.getLottery_bonus()
								* vo.getLottery_zhu());
						// 记录玩家领取奖励的排行
						RankService rankService = new RankService();
						rankService.updateAdd(
								role_info.getBasicInfo().getPPk(), "boyi",
								get_yb);
						return "你领取了第" + lottery_date + "期中奖奖励:"+GameConfig.getYuanbaoName()+"" + get_yb;
					}
			}
			else
			{
				return "您已领取过奖励";
			}
		}
	}

	// 得到头奖奖金
	public long getLotteryFristBonus(String lottery_date)
	{
		PlayerLotteryDAO dao = new PlayerLotteryDAO();
		return dao.getLotteryFristBonus(lottery_date);
	}

	// 得到总投注数量
	public long getLotteryAllZhu(String lottery_date)
	{
		PlayerLotteryDAO dao = new PlayerLotteryDAO();
		return dao.getLotteryAllZhu(lottery_date);
	}

	// 得到玩家彩票历史记录
	public List<LotteryOutPrintVO> getPlayerLotteryHistory(int p_pk, int page,
			int perpage)
	{
		PlayerLotteryDAO dao = new PlayerLotteryDAO();
		List<LotteryOutPrintVO> list = dao.getPlayerLotteryHistory(p_pk, page,
				perpage);
		return list;
	}

	// 得到玩家彩票总投注数量
	public int getPlayerLotteryAllNum(int p_pk)
	{
		PlayerLotteryDAO dao = new PlayerLotteryDAO();
		return dao.getPlayerLotteryAllNum(p_pk);
	}

	/** 算出奖励有多少物品 */
	public int getLaborageBonusNum(String bonus)
	{
		int num = 0;
		if (bonus == null)
		{
			return num;
		}
		String[] bonusprop = bonus.split(",");
		for (int i = 0; i < bonusprop.length; i++)
		{
			String[] prop = bonusprop[i].split("-");
			if (prop[0].equals("1"))
			{
				num = num + 1;
			}
			else
				if (prop[0].equals("2"))
				{
					num = num + 1;
				}
				else
					if (prop[0].equals("3"))
					{
						num = num + 1;
					}
					else
						if (prop[0].equals("4"))
						{
							PropCache pc = new PropCache();
							int prop_num_ac = pc.getPropAccumulate(Integer
									.parseInt(prop[1]));
							num = num
									+ (Integer.parseInt(prop[2]) / prop_num_ac + (Integer
											.parseInt(prop[2])
											% prop_num_ac == 0 ? 0 : 1));
						}
		}
		return num;
	}

	// 得到奖励物品的显示
	public String getLaborageView(String bonus)
	{
		if (bonus == null)
		{
			return "无";
		}
		StringBuffer sb = new StringBuffer();
		String[] bonusprop = bonus.split(",");
		for (int i = 0; i < bonusprop.length; i++)
		{
			String[] prop = bonusprop[i].split("-");
			if (prop[0].equals("1"))
			{
				int equip_id = Integer.parseInt(prop[1]);
				GameEquip equip = EquipCache.getById(equip_id);
				sb.append(equip.getName() + "×" + prop[2]);
			}
			else if (prop[0].equals("4"))
			{
				int prop_id = Integer.parseInt(prop[1]);
				PropVO pvo =  PropCache.getPropById(prop_id);
				sb.append(pvo.getPropName() + "×" + prop[2]);
			}
			else if (prop[0].equals("5"))
			{
				sb.append("经验  " + prop[2]);
			}
			else
			{
				sb.append("银两"+ MoneyUtil.changeCopperToStr(prop[2]));
			}
			if (i != bonusprop.length - 1)
			{
				sb.append(",");
			}
		}
		return sb.toString();
	}

	// 投注次数
	public int getLotteryTimeToday(int p_pk, String lottery_date)
	{
		PlayerLotteryDAO dao = new PlayerLotteryDAO();
		return dao.getPlayerLotteryNumToday(p_pk, lottery_date, 0);
	}

	// 中奖次数
	public int getLotteryBonusTimeToday(int p_pk, String lottery_date)
	{
		PlayerLotteryDAO dao = new PlayerLotteryDAO();
		return dao.getPlayerLotteryNumToday(p_pk, lottery_date, 1);
	}
}
