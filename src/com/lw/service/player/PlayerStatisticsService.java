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
	/** 判断玩家是否有监控的数据* */
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

	/** 玩家登陆并且进入统计信息流程* */
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

	/** 更新玩家在线时间 */
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

	/** 今天日期 */
	public String getTodayDate()
	{
		Date date = new Date();
		DateFormat dfdate = new SimpleDateFormat("yyyy-MM-dd");
		String thisdate = dfdate.format(date.getTime());// 当前日期
		return thisdate;
	}

	/** 三天前日期 */
	public String getTodayDateAfter()
	{
		Date date = new Date();
		long i = date.getTime() - 3 * 24 * 60 * 60 * 1000;
		DateFormat dfdate = new SimpleDateFormat("yyyy-MM-dd");
		String thisdate = dfdate.format(i);// 当前日期
		return thisdate;
	}

	/** 今天小时 */
	public String getTodayHour()
	{
		Date date = new Date();
		DateFormat dftime = new SimpleDateFormat("HH");
		String thistime = dftime.format(date.getTime());// 当前时间
		return thistime;
	}

	/** 统计玩家的在线时间 */
	public int getOnlineTime(String date)
	{
		PlayerStatisticsDao dao = new PlayerStatisticsDao();
		return dao.getOnlineTime(date);
	}

	/** 统计玩家在线等级 */
	public int getOnlineGrade(String date)
	{
		PlayerStatisticsDao dao = new PlayerStatisticsDao();
		return dao.getOnlineGrade(date);
	}

	/** 统计玩家在线平均等级 */
	public int getOnlineAvgGrade(String date)
	{
		PlayerStatisticsDao dao = new PlayerStatisticsDao();
		return dao.getOnlineAvgGrade(9, date);
	}

	/** 得到今天有多少玩家登陆游戏 */
	public int getOnlinePlayer(String date)
	{
		PlayerStatisticsDao dao = new PlayerStatisticsDao();
		return dao.getOnlinePlayer(date);
	}

	/** 得到玩家的活跃用户数量 */
	public int getPlayerOnlineActivity()
	{
		String date = getTodayDateAfter();
		PlayerStatisticsDao dao = new PlayerStatisticsDao();
		return dao.getPlayerOnlineActivity(date);
	}

	/** 得到玩家账号数量今天登陆 */
	public int getOnlinePassport(String date)
	{
		PlayerStatisticsDao dao = new PlayerStatisticsDao();
		return dao.getOnlinePassport(date);
	}

	/** 平均在线 人数的计算 */

	public int getOnlinePlayerAvg(String today)
	{
		StatisticsDao dao = new StatisticsDao();
		int num = (int) dao.getAvgPlayerOnlineNum(today) / 24;
		return num;
	}

}
