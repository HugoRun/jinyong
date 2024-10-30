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
	 * ����ʱ���жϵ����Ƿ������� ��������� ִ�и��� ���û��ִ�в���
	 * 
	 * @param propType
	 *            6Ϊ��Ǯ 7ΪԪ�� 8Ϊ�̳���Ʒ
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
		String thisdate = dfdate.format(date.getTime());// ��ǰ����
		String thistime = dftime.format(date.getTime());// ��ǰʱ��
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
		String thisdate = dfdate.format(date.getTime());// ��ǰ����
		String thistime = dftime.format(date.getTime());// ��ǰʱ��
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
	 * �ж��Ƿ�ִ�в������
	 * 
	 * @return true Ϊִ�в��� false Ϊ��ִ�в���
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
	 * �ж��Ƿ�ͳ�Ƹ���Ʒ
	 * 
	 * @return true Ϊִ��ͳ�� false ��ִ��ͳ��
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

	/** ͳ�ƿ�� */
	public void statisticsPropKuCun()
	{
		Date date = new Date();
		DateFormat dfdate = new SimpleDateFormat("yyyy-MM-dd");
		GameSystemStatisticsDao dao = new GameSystemStatisticsDao();
		GameSystemStatisticsMessageDao gdao = new GameSystemStatisticsMessageDao();

		List<GameSystemStatisticsMessageVO> list = gdao.getProp();
		String thisdate = dfdate.format(date.getTime() - 1000 * 60 * 60 * 24);// ��ǰ����

		int money = dao.getPlayerCangkuMoney() + dao.getPlayerTongMoney()
				+ dao.getPlayerGroupMoney();// ��Ҳֿ��Ǯ+���ɲֿ��Ǯ+������Ͻ�Ǯ
		int yuanbao = dao.getPlayerYuanBao();// ���Ԫ��

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
							+ dao.getPlayerTongProp(prop_id);// �ֿ����+��������+���ɲֿ����
					dao.insertPropMessage(prop_id, prop_type, num + "",
							StatisticsType.KUCUN, "", thisdate, "");
				}
				else
				{
					int num = dao.getPlayerGroupZhuangbei(prop_id, prop_type)
							+ dao.getPlayerTongZhuangbei(prop_id, prop_type)
							+ dao.getPlayerCangkuEquip(prop_id, prop_type);// ����װ��+���ɲֿ�װ��+��Ҳֿ�װ��
					dao.insertPropMessage(prop_id, prop_type, num + "",
							StatisticsType.KUCUN, "", thisdate, "");
				}
			}
		}
	}

	/** �����ҽ��׼�¼ */
	public void insertSellInfoRecord(int p_pk_give, int p_pk_have,
			int prop_type, int prop_id, int num, long money)
	{
		SellInfoRecordDao dao = new SellInfoRecordDao();
		dao.insertRecord(p_pk_give, p_pk_have, prop_type, prop_id, num, money);
	}

	/** ɾ����ҽ��׼�¼��7����ǰ�� */
	public void deleteRecord()
	{
		SellInfoRecordDao dao = new SellInfoRecordDao();
		dao.deleteRecord();
	}
}
