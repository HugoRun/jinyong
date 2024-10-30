package com.lw.service.lottery;

import java.util.Date;
import java.util.List;

import com.ls.ben.cache.staticcache.equip.EquipCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.model.equip.GameEquip;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.MathUtil;
import com.ls.pub.util.MoneyUtil;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.log.LogService;
import com.ls.web.service.player.RoleService;
import com.lw.dao.lottery.LotteryInfoDao;
import com.lw.dao.lottery.LotteryNumberDao;
import com.lw.dao.lottery.PlayerLotteryInfoDao;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.lw.vo.lottery.LotteryInfoVO;
import com.lw.vo.lottery.LotteryNumberVO;
import com.lw.vo.lottery.PlayerLotteryInfoVO;
import com.pm.service.mail.MailInfoService;

public class LotteryService
{
	/** 得到生成中奖彩票号码 */
	public void getSysLotteryNum()
	{
		LotteryInfoDao dao = new LotteryInfoDao();
		int lotteryNum1 = MathUtil.getRandomBetweenXY(1, 4);
		int lotteryNum2 = MathUtil.getRandomBetweenXY(1, 4);
		int lotteryNum3 = MathUtil.getRandomBetweenXY(1, 4);
		int lotteryNum4 = MathUtil.getRandomBetweenXY(1, 3);
		String num = Num1(lotteryNum1) + "," + Num2(lotteryNum2) + ","
				+ Num3(lotteryNum3) + "," + Num4(lotteryNum4);
		dao.updateLotteryNumber(StringUtil.gbToISO(num));
	}

	/** 得到彩票的字符 */
	public String Num1(int x)
	{
		String a = null;
		if (x == 1)
		{
			a = "东风";
		}
		else
			if (x == 2)
			{
				a = "南风";
			}
			else
				if (x == 3)
				{
					a = "西风";
				}
				else
					if (x == 4)
					{
						a = "北风";
					}
		return a;
	}

	public String Num2(int x)
	{
		String a = null;
		if (x == 1)
		{
			a = "梅";
		}
		else
			if (x == 2)
			{
				a = "兰";
			}
			else
				if (x == 3)
				{
					a = "竹";
				}
				else
					if (x == 4)
					{
						a = "菊";
					}
		return a;
	}

	public String Num3(int x)
	{
		String a = null;
		if (x == 1)
		{
			a = "春";
		}
		else
			if (x == 2)
			{
				a = "夏";
			}
			else
				if (x == 3)
				{
					a = "秋";
				}
				else
					if (x == 4)
					{
						a = "冬";
					}
		return a;
	}

	public String Num4(int x)
	{
		String a = null;
		if (x == 1)
		{
			a = "红中";
		}
		else
			if (x == 2)
			{
				a = "白板";
			}
			else
				if (x == 3)
				{
					a = "发财";
				}
		return a;
	}

	/** 得到生成慈善中奖彩票号码 */
	public void getSysLotteryCharityNum()
	{
		LotteryInfoDao dao = new LotteryInfoDao();
		int lotteryNum1 = MathUtil.getRandomBetweenXY(1, 4);
		int lotteryNum2 = MathUtil.getRandomBetweenXY(1, 4);
		int lotteryNum3 = MathUtil.getRandomBetweenXY(1, 4);
		int lotteryNum4 = MathUtil.getRandomBetweenXY(1, 3);
		String num = Num1(lotteryNum1) + "," + Num2(lotteryNum2) + ","
				+ Num3(lotteryNum3) + "," + Num4(lotteryNum4);
		dao.updateLotteryCharityNumber(StringUtil.gbToISO(num));
	}

	/** 根据生成玩家彩票 */
	public void playerLotteryNumber(int p_pk, String num_1, String num_2,
			String num_3, String num_4, int lottery_type, int p_add_money)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoById(p_pk + "");

		LotteryNumberDao dao = new LotteryNumberDao();
		PlayerLotteryInfoDao infodao = new PlayerLotteryInfoDao();
		String lottery_number = num_1 + "," + num_2 + "," + num_3 + "," + num_4;
		dao.insertPlayerLottery(p_pk, StringUtil.gbToISO(lottery_number),
				lottery_type, p_add_money);
		infodao.addPlayerLotteryNum(p_pk);
		if (lottery_type == 0)
		{
			//监控
			LogService logService = new LogService();
			logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", -p_add_money+"", "买彩票");
			
			roleInfo.getBasicInfo().addCopper(-p_add_money);
			// 执行统计
			GameSystemStatisticsService gsss = new GameSystemStatisticsService();
			gsss.addPropNum(6, StatisticsType.MONEY, p_add_money,
					StatisticsType.USED, StatisticsType.BUY, p_pk);

		}
	}

	/** 中奖后 按比例增加慈善奖金金额 */
	public void addSysCharityBonus(int num)
	{
		LotteryInfoDao infodao = new LotteryInfoDao();
		infodao.updateCharityBonus(num);
	}

	/** 按中奖比例得到单注单倍的中奖金额 */

	public int getLotteryPerBonus()
	{
		LotteryInfoDao infodao = new LotteryInfoDao();
		LotteryNumberDao dao = new LotteryNumberDao();
		int num = dao.getLotteryPer(infodao.getLotteryNumberEveryday());
		LotteryInfoVO vo = infodao.getLotteryInfo();
		if (num > 0)
		{
			if (vo.getSysBonusType() == 0)
			{
				int bonus = vo.getLotteryBonus() + vo.getSysBonusNum();

				int playerbonus = (bonus / num);
				return playerbonus;
			}
			else
			{
				int bonus = vo.getLotteryBonus();
				int playerbonus = (bonus / num);
				return playerbonus;
			}
		}
		return 0;
	}

	/** 判断玩家是否投注 */
	public boolean ifPlayerGetLottery(int p_pk, int lottery_type)
	{
		LotteryNumberDao dao = new LotteryNumberDao();
		if (dao.getLotteryNumber(p_pk, lottery_type) == null)
		{
			return true;
		}
		return false;
	}

	/**
	 * 根据PPK判断玩家是否中奖
	 * 
	 * @return 1为中奖 2为不中奖 3为没有投注
	 */
	public int ifPlayerCatch(int p_pk)
	{
		LotteryNumberDao dao = new LotteryNumberDao();
		LotteryInfoDao infodao = new LotteryInfoDao();
		if (dao.getLotteryNumber(p_pk, 0) == null)
		{
			return 3;
		}
		else
		{
			if (dao.getLotteryNumber(p_pk, 0).equals(
					infodao.getLotteryNumberEveryday()))
			{
				return 1;
			}
			else
			{
				return 2;
			}
		}
	}

	/**
	 * 根据PPK判断玩家是否中慈善奖
	 * 
	 * @return 1为中奖 2为不中奖 3为没有投注
	 */
	public int ifPlayerCatchCharity(int p_pk)
	{
		LotteryNumberDao dao = new LotteryNumberDao();
		LotteryInfoDao infodao = new LotteryInfoDao();
		if (dao.getLotteryNumber(p_pk, 1) == null)
		{
			return 3;
		}
		else
		{
			if (dao.getLotteryNumber(p_pk, 1).equals(
					infodao.getLotteryCharityNumber()))
			{
				return 1;
			}
			else
			{
				return 2;
			}
		}
	}

	/** 得到玩家获得的银两 */
	public int playerHaveMoney(int p_pk)
	{
		LotteryNumberDao numdao = new LotteryNumberDao();
		LotteryNumberVO numvo = numdao.getPlayerLottery();
		PlayerLotteryInfoDao playerdao = new PlayerLotteryInfoDao();
		PlayerLotteryInfoVO vo = playerdao.getLotteryInfoByPpk(p_pk);
		int money = vo.getLotteryPerBonus() * vo.getLotteryBonusMultiple()
				* numvo.getPlayerAddMoney();
		return money;
	}

	/** 玩家获取慈善奖金的金额 */
	public int playerHaveCharityMoney(int p_pk)
	{
		LotteryInfoDao lotterydao = new LotteryInfoDao();
		LotteryInfoVO infovo = lotterydao.getLotteryInfo();
		LotteryNumberDao dao = new LotteryNumberDao();
		return infovo.getSysCharityBonus()
				/ dao.getLotteryNumber(infovo.getLotteryCharityNum(), 1);
	}

	/** 玩家领取慈善奖金过程 */
	public void playerCatchCharityMoney(int p_pk)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoById(p_pk + "");

		LotteryInfoDao dao = new LotteryInfoDao();

		//监控
		LogService logService = new LogService();
		logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", playerHaveCharityMoney(p_pk)+"", "慈善获奖");
		
		roleInfo.getBasicInfo().addCopper(playerHaveCharityMoney(p_pk));

		dao.delCharityBonus(playerHaveCharityMoney(p_pk));
	}

	/** 玩家中奖领取奖金的流程 */
	public void playerCatchMoney(int p_pk)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoById(p_pk + "");

		LotteryNumberDao numdao = new LotteryNumberDao();
		LotteryNumberVO numvo = numdao.getPlayerLottery();
		PlayerLotteryInfoDao playerdao = new PlayerLotteryInfoDao();
		PlayerLotteryInfoVO vo = playerdao.getLotteryInfoByPpk(p_pk);
		LotteryInfoDao lotterydao = new LotteryInfoDao();
		LotteryInfoVO infovo = lotterydao.getLotteryInfo();
		int lotterymoney = vo.getLotteryPerBonus() * numvo.getPlayerAddMoney();// 玩家中奖后的总奖励
		int playgetmoney = lotterymoney * 85 / 100;// 玩家得到的钱
		int shuijin = lotterymoney - playgetmoney;// 税金
		int charitymoney = shuijin / 5;// 慈善奖励

		// 执行统计
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(6, StatisticsType.MONEY, shuijin, StatisticsType.USED,
				StatisticsType.SHUISHOU, p_pk);

		if (infovo.getSysBonusType() == 0)
		{
			addSysCharityBonus(charitymoney);// 玩家领奖 税金的一部分滚入慈善奖励
			lotterydao.delLotteryBonus(lotterymoney);// 奖池奖金递减
			lotterydao.updateLotterySunjoin(lotterymoney
					* (vo.getLotteryBonusMultiple() - 1));// 统计系统补贴的奖金

			//监控
			LogService logService = new LogService();
			logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", lotterymoney * vo.getLotteryBonusMultiple()+"", "彩票获奖");
			
			lotterymoney = lotterymoney + infovo.getSysBonusNum();
			roleInfo.getBasicInfo().addCopper(
					lotterymoney * vo.getLotteryBonusMultiple());// 玩家领取奖金
			// 执行统计
			gsss.addPropNum(6, StatisticsType.MONEY, lotterymoney
					* vo.getLotteryBonusMultiple(), StatisticsType.DEDAO,
					StatisticsType.BOCAI, p_pk);

			playerdao.updatePlayerCatch(p_pk);// 标记人物领取过奖金
			playerdao.updatePlayerAllBonus(lotterymoney
					* vo.getLotteryBonusMultiple(), p_pk);// 给玩家累加奖金
			playerdao.addPlayerWinNum(p_pk);// 给玩家的获奖倍数和获奖次数+1
		}
		else
		{
			addSysCharityBonus(charitymoney);// 玩家领奖 税金的一部分滚入慈善奖励
			lotterydao.delLotteryBonus(lotterymoney);// 奖池奖金递减
			lotterydao.updateLotterySunjoin(lotterymoney
					* (vo.getLotteryBonusMultiple() - 1)
					* numvo.getPlayerAddMoney());// 统计系统补贴的奖金

			//监控
			LogService logService = new LogService();
			logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", lotterymoney * vo.getLotteryBonusMultiple()+"", "彩票获奖");
			
			roleInfo.getBasicInfo().addCopper(
					lotterymoney * vo.getLotteryBonusMultiple());// 玩家领取奖金

			// 执行统计
			gsss.addPropNum(6, StatisticsType.MONEY, lotterymoney
					* vo.getLotteryBonusMultiple(), StatisticsType.DEDAO,
					StatisticsType.BOCAI, p_pk);

			playerdao.updatePlayerCatch(p_pk);// 标记人物领取过奖金
			playerdao.updatePlayerAllBonus(lotterymoney
					* vo.getLotteryBonusMultiple(), p_pk);// 给玩家累加奖金
			GoodsService goodsService = new GoodsService();
			goodsService.putGoodsToWrap(p_pk, infovo.getSysBonusId(), infovo
					.getSysBonusType(), infovo.getSysBonusIntro(), infovo
					.getSysBonusNum(),GameLogManager.G_SYSTEM);// 玩家获得系统发给的道具
			// 执行统计
			gsss.addPropNum(infovo.getSysBonusId(), infovo.getSysBonusType(),
					infovo.getSysBonusNum(), StatisticsType.DEDAO,
					StatisticsType.BOCAI, p_pk);

			playerdao.addPlayerWinNum(p_pk);// 给玩家的获奖倍数和获奖次数+1
		}
	}

	/** 玩家点击竞猜的同时生成玩家信息 */
	public void buildPlayerLottery(int p_pk)
	{
		PlayerLotteryInfoDao playerdao = new PlayerLotteryInfoDao();
		if (playerGuess(p_pk) == true)
			playerdao.setPlayerLotteryMessage(p_pk);
	}

	/** 判断玩家是否为第一次竞猜 是生成玩家彩票信息 */
	public boolean playerGuess(int p_pk)
	{
		PlayerLotteryInfoDao playerdao = new PlayerLotteryInfoDao();
		if (playerdao.getLotteryInfoByPpk(p_pk) == null)
		{
			return true;
		}
		return false;
	}

	/** 判断玩家是否竞猜过 true为竞猜过 false为没竞猜过 */
	public boolean playerGuessLottery(int p_pk, int lottery_type)
	{
		LotteryNumberDao dao = new LotteryNumberDao();
		if (dao.getLotteryNumber(p_pk, lottery_type) == null)
		{
			return false;
		}
		return true;
	}

	/** 系统自动生成彩票 计算单注奖金 并给玩家彩票信息表 添加 单注奖金金额 */
	public void sysLottery()
	{
		Date date = new Date();
		PlayerLotteryInfoDao dao = new PlayerLotteryInfoDao();
		LotteryInfoDao ldao = new LotteryInfoDao();
		LotteryInfoVO vo = ldao.getLotteryInfo();
		if (date.getDate() == 1)
		{
			getSysLotteryCharityNum();
			getSysLotteryNum();
			dao.setPerMoney(getLotteryPerBonus());
		}
		else
		{
			getSysLotteryNum();
			dao.setPerMoney(getLotteryPerBonus());
		}
		LotteryNumberDao ndao = new LotteryNumberDao();
		int x = ndao.getLotteryNumber(vo.getLotteryNumberPerDay(), 0);
		ldao.updateLotteryWinNumber(x);

	}

	/** 系统每日3点 更新玩家彩票信息表里信息 同时删除彩票表 并且删除系统彩票号码 删除慈善彩票号码 */
	public void sysDel()
	{
		Date date = new Date();
		LotteryNumberDao numdao = new LotteryNumberDao();
		PlayerLotteryInfoDao dao = new PlayerLotteryInfoDao();
		if (date.getDate() == 2)
		{
			// dao.delPlayerMessage();
			numdao.delAll();
			dao.updatePlayerCatchBySys();
			dao.delPlayerMessageByMonth();
		}
		else
		{
			// dao.delPlayerMessage();
			numdao.delAll();
			dao.updatePlayerCatchBySys();
		}
	}

	/** 判断玩家是否有参加慈善竞猜的资格 */
	public boolean getPlayerCharityLottery(int p_pk)
	{
		PlayerLotteryInfoDao dao = new PlayerLotteryInfoDao();
		PlayerLotteryInfoVO vo = dao.getLotteryInfoByPpk(p_pk);
		if (vo.getLotteryNum() >= 15 && vo.getLotteryWinNum() == 0)
		{
			return true;
		}
		return false;
	}

	/** 系统信息 */
	public String sysLotteryMessage()
	{
		Date date = new Date();
		LotteryInfoDao dao = new LotteryInfoDao();
		LotteryInfoVO vo = dao.getLotteryInfo();
		String num = vo.getLotteryNumberPerDay();
		String num1 = vo.getLotteryCharityNum();
		if (date.getDate() == 1)
		{

			return "中奖号码为" + num + " 慈善中奖号码为" + num1;
		}
		else
		{
			return "中奖号码为" + num;
		}
	}

	/** 发送系统邮件给玩家 */
	public void setSysLotteryMail()
	{
		LotteryNumberDao ndao = new LotteryNumberDao();
		LotteryInfoDao dao = new LotteryInfoDao();
		MailInfoService ms = new MailInfoService();
		LotteryInfoVO vo = dao.getLotteryInfo();
		String num = vo.getLotteryNumberPerDay();// 中奖号码
		int x = ndao.getLotteryNumber(num, 0);// 中奖人数
		String today = DateUtil.getTodayStr().replaceAll("-", "");// 开奖期数
		List<Integer> winplayer = ndao.getWinPlayer(num, 0);// 中奖者
		List<Integer> notwinplayer = ndao.getNotWinPlayer(num, 0);// 没中奖者
		List<Integer> allplayer = ndao.getAllPlayer(0);// 买彩票的人
		String title = "第" + today + "期博彩开奖";
		if (allplayer != null && allplayer.size() != 0)
		{
			if (x == 0)
			{
				for (int i = 0; i < allplayer.size(); i++)
				{
					int ppk = allplayer.get(i);
					String playerlotterynum = ndao.getLotteryNumber(ppk, 0);
					String allBonus = MoneyUtil.changeCopperToStr(vo
							.getLotteryBonus());
					String contentallnotwin = "第" + today + "期博彩开奖，中奖号码为" + num
							+ "。您投注的" + playerlotterynum + "号码没有中奖！本轮竞猜无人中奖，奖金"
							+ allBonus + "两滚入下一轮，希望下一轮中奖的人就是您！";
					ms.sendMailBySystem(ppk, title, contentallnotwin);
				}
			}
			else
			{
				if (winplayer != null && winplayer.size() != 0)
				{
					for (int i = 0; i < winplayer.size(); i++)
					{
						int ppk = winplayer.get(i);
						String contentwin = "第" + today + "期博彩开奖，恭喜您投注的" + num
								+ "号码中奖了！请在当天前往黑衣大汉处领奖！";
						ms.sendMailBySystem(ppk, title, contentwin);
					}
				}
				if (notwinplayer != null && notwinplayer.size() != 0)
				{
					for (int i = 0; i < notwinplayer.size(); i++)
					{
						int ppk = notwinplayer.get(i);
						String playerlotterynum = ndao.getLotteryNumber(ppk, 0);
						String contentnotwin = "第" + today + "期博彩开奖，中奖号码为"
								+ num + "。您投注的" + playerlotterynum
								+ "号码没有中奖！希望下一轮中奖的人就是您！";
						ms.sendMailBySystem(ppk, title, contentnotwin);
					}
				}
			}
		}
	}

	/** 系统追加奖励的显示 */
	public String sysOutLotteryBonus()
	{
		LotteryInfoDao ldao = new LotteryInfoDao();
		LotteryInfoVO vo = ldao.getLotteryInfo();
		
		if (vo.getSysBonusType() == 1)
		{
			int equip_id = vo.getSysBonusId();
			GameEquip equip = EquipCache.getById(equip_id);
			String intro = "";
			if (vo.getSysBonusIntro() == 1)
			{
				intro = "(优)";
			}
			if (vo.getSysBonusIntro() == 2)
			{
				intro = "(精)";
			}
			if (vo.getSysBonusIntro() == 3)
			{
				intro = "(极)";
			}
			String out = "系统追加奖励:" + equip.getName()+ intro + "×" + vo.getSysBonusNum();
			return out;
		}
		if (vo.getSysBonusType() == 4)
		{
			PropVO pvo = PropCache.getPropById(vo.getSysBonusId());
			String out = "系统追加奖励:" + pvo.getPropName() + "×"
					+ vo.getSysBonusNum();
			return out;
		}
		return "";
	}

	/** 判断系统时间 */
	public boolean sysTimeAtEight()
	{
		Date now = new Date();
		if (now.getHours() >= 8 && now.getHours() < 20)
		{
			return true;
		}
		return false;
	}

	public boolean sysTimeAtTwenty()
	{
		Date now = new Date();
		if (now.getHours() >= 20 && now.getHours() < 24)
		{
			return true;
		}
		return false;
	}

	/** 判断玩家的包裹 */
	public int getPlayerWrapSpare(int p_pk)
	{
		RoleEntity role_info = RoleService.getRoleInfoById(p_pk+"");
		int warespare = role_info.getBasicInfo().getWrapSpare();
		LotteryInfoDao lotterydao = new LotteryInfoDao();
		LotteryInfoVO infovo = lotterydao.getLotteryInfo();
		if (infovo.getSysBonusType() != 0)
		{
			if (warespare < infovo.getSysBonusNum())
			{
				return -1;
			}
		}
		return 0;
	}

}
