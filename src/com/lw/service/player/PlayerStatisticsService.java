package com.lw.service.player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ls.model.user.RoleEntity;
import com.lw.dao.player.PlayerStatisticsDao;
import com.lw.vo.player.PlayerStatisticsVO;
import com.pm.dao.statistics.StatisticsDao;

public class PlayerStatisticsService
{
	/** �ж�����Ƿ��м�ص�����* */
	public boolean getPlayerStaInfo(int u_pk, int p_pk, String date)
	{
		PlayerStatisticsDao dao = new PlayerStatisticsDao();
		PlayerStatisticsVO vo = dao.getPlayerInfo(u_pk, p_pk, date);
		if (vo == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	/** ��ҵ�½���ҽ���ͳ����Ϣ����* */
	public void playerStatisticsFlow(RoleEntity roleEntity)
	{
		PlayerStatisticsDao dao = new PlayerStatisticsDao();
		boolean x = getPlayerStaInfo(roleEntity.getBasicInfo().getUPk(),
				roleEntity.getBasicInfo().getPPk(), getTodayDate());
		if (x == true)
		{
			PlayerStatisticsVO vo = dao.getPlayerInfo(roleEntity.getBasicInfo()
					.getUPk(), roleEntity.getBasicInfo().getPPk(),
					getTodayDate());
			dao.updatePlayerInfo(roleEntity.getBasicInfo().getUPk(), roleEntity
					.getBasicInfo().getPPk(), roleEntity.getBasicInfo()
					.getGrade(), getTodayDate(), getTodayHour(), vo.getId());
		}
		else
		{
			dao.insertPlayerStatistics(roleEntity.getBasicInfo().getUPk(),
					roleEntity.getBasicInfo().getPPk(), roleEntity
							.getBasicInfo().getGrade(), getTodayDate(),
					getTodayHour());
		}
	}

	/** �����������ʱ�� */
	public void updatePlayerOnlineTime(RoleEntity roleEntity)
	{
		PlayerStatisticsDao dao = new PlayerStatisticsDao();
		PlayerStatisticsVO vo = dao.getPlayerInfo(roleEntity.getBasicInfo()
				.getUPk(), roleEntity.getBasicInfo().getPPk(), getTodayDate());
		try
		{
			if (vo == null)
			{
				dao.insertPlayerStatistics(roleEntity.getBasicInfo().getUPk(),
						roleEntity.getBasicInfo().getPPk(), roleEntity
								.getBasicInfo().getGrade(), getTodayDate(),
						getTodayHour());
				PlayerStatisticsVO vo1 = dao.getPlayerInfo(roleEntity
						.getBasicInfo().getUPk(), roleEntity.getBasicInfo()
						.getPPk(), getTodayDate());
				Date date = new Date();
				int i = (int) (date.getTime() - vo1.getLogintime().getTime()) / 60000;
				dao.updatePlayerOnlineTime(roleEntity.getBasicInfo().getUPk(),
						roleEntity.getBasicInfo().getPPk(), i, vo1.getId());
			}
			else
			{
				Date date = new Date();
				int i = (int) (date.getTime() - vo.getLogintime().getTime()) / 60000;
				dao.updatePlayerOnlineTime(roleEntity.getBasicInfo().getUPk(),
						roleEntity.getBasicInfo().getPPk(), i, vo.getId());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	/** �������� */
	public String getTodayDate()
	{
		Date date = new Date();
		DateFormat dfdate = new SimpleDateFormat("yyyy-MM-dd");
		String thisdate = dfdate.format(date.getTime());// ��ǰ����
		return thisdate;
	}

	/** ����ǰ���� */
	public String getTodayDateAfter()
	{
		Date date = new Date();
		long i = date.getTime() - 3 * 24 * 60 * 60 * 1000;
		DateFormat dfdate = new SimpleDateFormat("yyyy-MM-dd");
		String thisdate = dfdate.format(i);// ��ǰ����
		return thisdate;
	}

	/** ����Сʱ */
	public String getTodayHour()
	{
		Date date = new Date();
		DateFormat dftime = new SimpleDateFormat("HH");
		String thistime = dftime.format(date.getTime());// ��ǰʱ��
		return thistime;
	}

	/** ͳ����ҵ�����ʱ�� */
	public int getOnlineTime(String date)
	{
		PlayerStatisticsDao dao = new PlayerStatisticsDao();
		return dao.getOnlineTime(date);
	}

	/** ͳ��������ߵȼ� */
	public int getOnlineGrade(String date)
	{
		PlayerStatisticsDao dao = new PlayerStatisticsDao();
		return dao.getOnlineGrade(date);
	}

	/** ͳ���������ƽ���ȼ� */
	public int getOnlineAvgGrade(String date)
	{
		PlayerStatisticsDao dao = new PlayerStatisticsDao();
		return dao.getOnlineAvgGrade(9, date);
	}

	/** �õ������ж�����ҵ�½��Ϸ */
	public int getOnlinePlayer(String date)
	{
		PlayerStatisticsDao dao = new PlayerStatisticsDao();
		return dao.getOnlinePlayer(date);
	}

	/** �õ���ҵĻ�Ծ�û����� */
	public int getPlayerOnlineActivity()
	{
		String date = getTodayDateAfter();
		PlayerStatisticsDao dao = new PlayerStatisticsDao();
		return dao.getPlayerOnlineActivity(date);
	}

	/** �õ�����˺����������½ */
	public int getOnlinePassport(String date)
	{
		PlayerStatisticsDao dao = new PlayerStatisticsDao();
		return dao.getOnlinePassport(date);
	}

	/** ƽ������ �����ļ��� */

	public int getOnlinePlayerAvg(String today)
	{
		StatisticsDao dao = new StatisticsDao();
		int num = (int) dao.getAvgPlayerOnlineNum(today) / 24;
		return num;
	}

}
