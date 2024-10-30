package com.lw.service.gamesystemstatistics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.lw.dao.gamesystemstatistics.GameSystemStatisticsDao;
import com.lw.dao.gamesystemstatistics.GameSystemStatisticsMessageDao;
import com.lw.dao.gamesystemstatistics.SellInfoRecordDao;
import com.lw.vo.gamesystemstatistics.GameSystemStatisticsMessageVO;
import com.lw.vo.gamesystemstatistics.GameSystemStatisticsVO;
import com.ls.pub.constant.StatisticsType;

public class GameSystemStatisticsService
{
	/**
	 * 根据时间判断当天是否有数据 如果有数据 执行更新 如果没有执行插入
	 * 
	 * @param propType
	 *            6为金钱 7为元宝 8为商城物品
	 * 
	 * 
	 */
	public void addPropNum(int propID, int propType, int propNum,
			String propApproachType, String propApproach, int p_pk)
	{
		Date date = new Date();
		PropCache propCache = new PropCache();
		DateFormat dfdate = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dftime = new SimpleDateFormat("HH");
		String thisdate = dfdate.format(date.getTime());// 当前日期
		String thistime = dftime.format(date.getTime());// 当前时间
		GameSystemStatisticsDao dao = new GameSystemStatisticsDao();
		if (propCache.getPropPositionById(propID) == 6
				&& propApproachType.equals("used"))
		{
			int yuanbao = dao.getCommodityPrice(propID) * propNum;
			if (ifTodayHaveProp(0, 8, propNum + "", propApproachType,
					propApproach, thisdate, thistime) == true)
			{
				dao.insertPropMessage(0, 8, yuanbao + "", propApproachType,
						propApproach, thisdate, thistime);
			}
			else
			{
				dao.updatePropMessage(0, 8, yuanbao + "", propApproachType,
						propApproach, thisdate, thistime);
			}
		}

		if (ifHaveThisProp(propID, propType) == true)
		{
			if (ifTodayHaveProp(propID, propType, propNum + "",
					propApproachType, propApproach, thisdate, thistime) == true)
			{
				dao.insertPropMessage(propID, propType, propNum + "",
						propApproachType, propApproach, thisdate, thistime);
			}
			else
			{
				dao.updatePropMessage(propID, propType, propNum + "",
						propApproachType, propApproach, thisdate, thistime);
			}
		}
	}

	public void addPropNum(int propID, int propType, long propNum,
			String propApproachType, String propApproach, int p_pk)
	{
		Date date = new Date();
		// PropDao propDao = new PropDao();
		PropCache propCache = new PropCache();
		DateFormat dfdate = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dftime = new SimpleDateFormat("HH");
		String thisdate = dfdate.format(date.getTime());// 当前日期
		String thistime = dftime.format(date.getTime());// 当前时间
		GameSystemStatisticsDao dao = new GameSystemStatisticsDao();
		if (propCache.getPropPositionById(propID) == 6
				&& propApproachType.equals("used"))
		{
			long yuanbao = dao.getCommodityPrice(propID) * propNum;
			if (ifTodayHaveProp(0, 8, propNum + "", propApproachType,
					propApproach, thisdate, thistime) == true)
			{
				dao.insertPropMessage(0, 8, yuanbao + "", propApproachType,
						propApproach, thisdate, thistime);
			}
			else
			{
				dao.updatePropMessage(0, 8, yuanbao + "", propApproachType,
						propApproach, thisdate, thistime);
			}
		}

		if (ifHaveThisProp(propID, propType) == true)
		{
			if (ifTodayHaveProp(propID, propType, propNum + "",
					propApproachType, propApproach, thisdate, thistime) == true)
			{
				dao.insertPropMessage(propID, propType, propNum + "",
						propApproachType, propApproach, thisdate, thistime);
			}
			else
			{
				dao.updatePropMessage(propID, propType, propNum + "",
						propApproachType, propApproach, thisdate, thistime);
			}
		}
	}

	/**
	 * 判断是否执行插入语句
	 * 
	 * @return true 为执行插入 false 为不执行插入
	 */
	public boolean ifTodayHaveProp(int propID, int propType, String propNum,
			String propApproachType, String propApproach, String date,
			String time)
	{
		GameSystemStatisticsDao dao = new GameSystemStatisticsDao();
		GameSystemStatisticsVO vo = dao.selectPropMessage(propID, propType,
				propApproachType, propApproach, date, time);
		if (vo != null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 * 判断是否统计该物品
	 * 
	 * @return true 为执行统计 false 不执行统计
	 */
	public boolean ifHaveThisProp(int propID, int propType)
	{
		GameSystemStatisticsMessageDao dao = new GameSystemStatisticsMessageDao();
		if (dao.getGameSystemStatisticsMessage(propID, propType) != 0)
		{
			return true;
		}
		else
		{
			if (propType == 0)
			{
				return true;
			}
			else
				if (propType == 6)
				{
					return true;
				}
				else
					if (propType == 7)
					{
						return true;
					}
					else
						if (propType == 8)
						{
							return true;
						}
						else
							if (propType == 9)
							{
								return true;
							}
							else
							{
								if (propType == 10)
								{
									return true;
								}
								else
								{
									// PropDao propDao = new PropDao();
									PropCache propCache = new PropCache();
									if (propCache.getPropPositionById(propID) == 6)
									{
										return true;
									}
								}
							}
			return false;
		}
	}

	/** 统计库存 */
	public void statisticsPropKuCun()
	{
		Date date = new Date();
		DateFormat dfdate = new SimpleDateFormat("yyyy-MM-dd");
		GameSystemStatisticsDao dao = new GameSystemStatisticsDao();
		GameSystemStatisticsMessageDao gdao = new GameSystemStatisticsMessageDao();

		List<GameSystemStatisticsMessageVO> list = gdao.getProp();
		String thisdate = dfdate.format(date.getTime() - 1000 * 60 * 60 * 24);// 当前日期

		int money = dao.getPlayerCangkuMoney() + dao.getPlayerTongMoney()
				+ dao.getPlayerGroupMoney();// 玩家仓库金钱+帮派仓库金钱+玩家身上金钱
		int yuanbao = dao.getPlayerYuanBao();// 玩家元宝

		addPropNum(6, StatisticsType.MONEY, money, StatisticsType.KUCUN, "", 0);
		addPropNum(7, StatisticsType.YUANBAO, yuanbao, StatisticsType.KUCUN,
				"", 0);

		if (list.size() != 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				GameSystemStatisticsMessageVO vo = list.get(i);
				int prop_id = vo.getPropID();
				int prop_type = vo.getPropType();
				if (prop_type == 4)
				{
					int num = dao.getPlayerCangku(prop_id, prop_type)
							+ dao.getPlayerGroupProp(prop_id)
							+ dao.getPlayerTongProp(prop_id);// 仓库道具+包裹道具+帮派仓库道具
					dao.insertPropMessage(prop_id, prop_type, num + "",
							StatisticsType.KUCUN, "", thisdate, "");
				}
				else
				{
					int num = dao.getPlayerGroupZhuangbei(prop_id, prop_type)
							+ dao.getPlayerTongZhuangbei(prop_id, prop_type)
							+ dao.getPlayerCangkuEquip(prop_id, prop_type);// 包裹装备+帮派仓库装备+玩家仓库装备
					dao.insertPropMessage(prop_id, prop_type, num + "",
							StatisticsType.KUCUN, "", thisdate, "");
				}
			}
		}
	}

	/** 添加玩家交易记录 */
	public void insertSellInfoRecord(int p_pk_give, int p_pk_have,
			int prop_type, int prop_id, int num, long money)
	{
		SellInfoRecordDao dao = new SellInfoRecordDao();
		dao.insertRecord(p_pk_give, p_pk_have, prop_type, prop_id, num, money);
	}

	/** 删除玩家交易记录（7七天前） */
	public void deleteRecord()
	{
		SellInfoRecordDao dao = new SellInfoRecordDao();
		dao.deleteRecord();
	}
}
