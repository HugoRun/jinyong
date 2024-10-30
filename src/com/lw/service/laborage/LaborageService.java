package com.lw.service.laborage;

import java.util.Date;
import java.util.List;

import com.ls.ben.cache.staticcache.equip.EquipCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.model.equip.GameEquip;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.log.LogService;
import com.ls.web.service.player.RoleService;
import com.lw.dao.laborage.LaborageDao;
import com.lw.dao.laborage.PlayerLaborageDao;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.lw.vo.laborage.LaborageVO;

public class LaborageService
{
	public String playerCatchMoney(int p_pk, int time)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");

		int bonusNum = getLaborageBonusNum(time);

		int wrapSpare = roleInfo.getBasicInfo().getWrapSpare();

		if (wrapSpare == 0 || wrapSpare < 0 || wrapSpare < bonusNum)
		{
			return "您包裹不足,请清理包裹!";
		}
		else
		{
			LaborageDao dao = new LaborageDao();
			LaborageVO vo = dao.getLaborageByTime(time);
			String bonus = vo.getLaborageBonus();
			String[] bonusprop = bonus.split(",");
			for (int i = 0; i < bonusprop.length; i++)
			{
				String[] prop = bonusprop[i].split("-");
				if (prop[0].equals("0"))
				{
					int laboragemoney = Integer.parseInt(prop[2]);

					// 监控
					LogService logService = new LogService();
					logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(),
							roleInfo.getBasicInfo().getName(), roleInfo
									.getBasicInfo().getCopper()
									+ "", laboragemoney + "", "工资");

					roleInfo.getBasicInfo().addCopper(laboragemoney);// 玩家领取奖金
					// 执行统计
					GameSystemStatisticsService gsss = new GameSystemStatisticsService();
					gsss.addPropNum(6, StatisticsType.MONEY, laboragemoney,
							StatisticsType.DEDAO, StatisticsType.XITONG, p_pk);
				}
				else
					if (prop[0].equals("5"))
					{
						int experience = Integer.parseInt(prop[2]);

						// 监控
						LogService logService = new LogService();
						logService.recordExpLog(roleInfo.getBasicInfo()
								.getPPk(), roleInfo.getBasicInfo().getName(),
								roleInfo.getBasicInfo().getCurExp(), experience
										+ "", "工资得到");

						roleInfo.getBasicInfo().updateAddCurExp(experience);
					}
					else
					{
						GoodsService goodsService = new GoodsService();
						goodsService.putGoodsToWrap(p_pk, Integer.parseInt(prop[1]), Integer.parseInt(prop[0]),0, Integer.parseInt(prop[2]),GameLogManager.G_SYSTEM);// 玩家获得系统发给的道具
						// 执行统计
						GameSystemStatisticsService gsss = new GameSystemStatisticsService();
						gsss.addPropNum(Integer.parseInt(prop[1]), Integer
								.parseInt(prop[0]), Integer.parseInt(prop[2]),
								StatisticsType.DEDAO, StatisticsType.XITONG,
								p_pk);
					}
			}
			PlayerLaborageDao playerdao = new PlayerLaborageDao();
			playerdao.updatePlayerCatchMoney(p_pk);// 标记人物领取过奖
			return "您获得了" + getLaborageView(time);
		}
	}

	/** 判断玩家是否具有领奖资格 平返回领奖最小时间 */
	public int getPlayerLaborageTime(int p_pk)
	{
		LaborageDao dao = new LaborageDao();
		PlayerLaborageDao playerdao = new PlayerLaborageDao();
		List timelist = dao.getMinTime();
		int time = playerdao.getPlayerOnlineTime(p_pk);
		if (timelist.size() != 0)
		{
			for (int i = 0; i < timelist.size(); i++)
			{
				if (time > Integer.parseInt(timelist.get(i).toString()))
				{
					return Integer.parseInt(timelist.get(i).toString());
				}
			}
		}
		return 0;
	}

	/** 系统每周更新玩家信息 凌晨3点 */
	public void sysUpdatePlayerOnlineTime()
	{
		Date date = new Date();
		if (date.getDay() == 1)
		{
			PlayerLaborageDao dao = new PlayerLaborageDao();
			dao.updatePlayerTime();
			dao.updatePlayerTimeZreo();
			dao.updatePlayerCatch();
		}
	}

	/** 时间确定 */
	public String getFirstDay()
	{
		Date date = new Date();
		LaborageDao dao = new LaborageDao();
		int x = date.getDay();
		if (x == 0)
		{
			return dao.getFirstDay(13);
		}
		else
			if (x == 1)
			{
				return dao.getFirstDay(7);
			}
			else
				if (x == 2)
				{
					return dao.getFirstDay(8);
				}
				else
					if (x == 3)
					{
						return dao.getFirstDay(9);
					}
					else
						if (x == 4)
						{
							return dao.getFirstDay(10);
						}
						else
							if (x == 5)
							{
								return dao.getFirstDay(11);
							}
							else
							{
								return dao.getFirstDay(12);
							}
	}

	public String getLastDay()
	{
		Date date = new Date();
		LaborageDao dao = new LaborageDao();
		int x = date.getDay();
		if (x == 0)
		{
			return dao.getLastDay(7);
		}
		else
			if (x == 1)
			{
				return dao.getLastDay(1);
			}
			else
				if (x == 2)
				{
					return dao.getLastDay(2);
				}
				else
					if (x == 3)
					{
						return dao.getLastDay(3);
					}
					else
						if (x == 4)
						{
							return dao.getLastDay(4);
						}
						else
							if (x == 5)
							{
								return dao.getLastDay(5);
							}
							else
							{
								return dao.getLastDay(6);
							}
	}

	public void insertPlayLaborageMessage(int p_pk)
	{
		PlayerLaborageDao dao = new PlayerLaborageDao();
		dao.buildPlayerLaborage(p_pk);
	}

	// 得到奖励物品的显示
	public String getLaborageView(int time)
	{
		LaborageDao dao = new LaborageDao();
		LaborageVO vo = dao.getLaborageByTime(time);
		String bonus = vo.getLaborageBonus();
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
				sb.append(""+ MoneyUtil.changeCopperToStr(prop[2]));
			}
			if (i != bonusprop.length - 1)
			{
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/** 算出奖励有多少物品 */
	public int getLaborageBonusNum(int time)
	{
		int num = 0;
		LaborageDao dao = new LaborageDao();
		LaborageVO vo = dao.getLaborageByTime(time);
		String bonus = vo.getLaborageBonus();
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
									+ (Integer.parseInt(prop[2]) % prop_num_ac + (Integer
											.parseInt(prop[2])
											/ prop_num_ac == 0 ? 0 : 1));
						}
		}
		return num;
	}
}
