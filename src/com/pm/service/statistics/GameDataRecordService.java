package com.pm.service.statistics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import com.lw.service.player.PlayerStatisticsService;
import com.pm.dao.statistics.StatisticsDao;
import com.pm.service.systemInfo.SystemInfoService;

public class GameDataRecordService extends TimerTask
{

	public GameDataRecordService()
	{
	}

	private static GameDataRecordService recordService = new GameDataRecordService();

	public static GameDataRecordService getInstance()
	{
		return recordService;
	}

	StatisticsDao statDao = new StatisticsDao();
	PlayerStatisticsService pss = new PlayerStatisticsService();

	// private static String today = getToday();

	/**
	 * 在这儿会每天运行一次, 定时一天运行一次.
	 */
	@Override
	public void run()
	{
		// 运行收集每日玩家等级
		// recordEveryDayPlayerGrade();
		// 运行收集上线玩家等级
		// recordOnlinePlayerGrade();
		// 运行收集沉默玩家等级
		// recordSilverPlayerGrade();
		// 运行收集玩家上线时间.
		// recordPlayerOnlineTime();
		// 运行收集玩家总览信息
		// recordPlayerInfoOverview();
		// 启动系统消息每日定时发送功能
		// sendSystemInfoByTime();
	}

	/**
	 * 定时发送系统消息
	 */
	public void sendSystemInfoByTime()
	{
		SystemInfoService infoService = new SystemInfoService();
		infoService.sendTimeSystemInfo();

	}

	/**
	 * 运行收集玩家总览信息， 所收集信息为自运行时前24小时至运行时的游戏数据
	 */
	public void recordPlayerInfoOverview()
	{
		// 总注册人数
		int allRegeistNum = statDao.getAllRegistNum();
		// 总角色数
		int allUserNum = statDao.getAllUserNum();
		// 今日注册玩家
		int todayRegistNum = statDao.getTodayRegistNum(getToday());
		// 今日上线玩家
		int todayOnlineNum = pss.getOnlinePassport(getToday());
		// 今日活跃用户
		int todayActiveNum = pss.getPlayerOnlineActivity();
		// 平均在线时间,单位（秒）
		long AllPlayerOnlineTime = pss.getOnlineTime(getToday());
		;
		int avgPlaerOnlineTime = 0;
		if (todayOnlineNum != 0)
		{
			avgPlaerOnlineTime = (int) (AllPlayerOnlineTime / todayOnlineNum);
		}
		// 平均在线等级
		double avgPlayerOnlineGrade = pss.getOnlineAvgGrade(getToday());
		// 平均在线人数
		double avgPlayerOnlineNum = pss.getOnlinePlayerAvg(getToday());

		// 当天注册玩家超过9级的数量
		int grade9today = statDao.getGrade9today(9, getToday());

		statDao.insertPlayerInfoOverview(allRegeistNum, allUserNum,
				todayRegistNum, todayOnlineNum, todayActiveNum,
				avgPlaerOnlineTime, avgPlayerOnlineGrade, avgPlayerOnlineNum,
				grade9today, getToday());
		// 清除不需要的数据
		deleteUnneed();
	}

	/**
	 * 清除不需要的数据
	 */
	public void deleteUnneed()
	{
		statDao.deleteUnneedRecordData();

	}

	/**
	 * 运行收集玩家上线时间
	 */
	public void recordPlayerOnlineTime()
	{
		int time10min = statDao.getOnlinePlayerTime(0, 10);
		int time30min = statDao.getOnlinePlayerTime(10, 30);
		int time60min = statDao.getOnlinePlayerTime(30, 60);
		int time120min = statDao.getOnlinePlayerTime(60, 120);
		int time120minUp = statDao.getOnlinePlayerTime(120, 1000);
		statDao.insertIntoOnlineTime(time10min, time30min, time60min,
				time120min, time120minUp, getToday());

	}

	/**
	 * 运行收集沉默玩家等级
	 */
	public void recordSilverPlayerGrade()
	{
		int grade1to9 = statDao.getSilverPlayerGrade(0, 10);
		int grade10to19 = statDao.getSilverPlayerGrade(9, 20);
		int grade20to29 = statDao.getSilverPlayerGrade(19, 30);
		int grade30to39 = statDao.getSilverPlayerGrade(29, 40);
		int grade40to49 = statDao.getSilverPlayerGrade(39, 50);
		int grade50to59 = statDao.getSilverPlayerGrade(49, 60);
		int grade60 = statDao.getSilverPlayerGrade(59, 61);
		statDao.insertIntoSilverPlayerGrade(grade1to9, grade10to19,
				grade20to29, grade30to39, grade40to49, grade50to59, grade60,
				getToday());

	}

	/**
	 * 运行收集上线玩家等级
	 */
	public void recordOnlinePlayerGrade()
	{
		int grade1to9 = statDao.getOnlinePlayerGrade(0, 10);
		int grade10to19 = statDao.getOnlinePlayerGrade(9, 20);
		int grade20to29 = statDao.getOnlinePlayerGrade(19, 30);
		int grade30to39 = statDao.getOnlinePlayerGrade(29, 40);
		int grade40to49 = statDao.getOnlinePlayerGrade(39, 50);
		int grade50to59 = statDao.getOnlinePlayerGrade(49, 60);
		int grade60 = statDao.getOnlinePlayerGrade(59, 61);
		statDao.insertIntoOnlinePlayerGrade(grade1to9, grade10to19,
				grade20to29, grade30to39, grade40to49, grade50to59, grade60,
				getToday());

	}

	/**
	 * 获得每天时间的格式化表示
	 * 
	 * @return
	 */
	public static String getToday()
	{
		Date dt = new Date();
		// DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date yestoday = new Date(dt.getTime() - 1000 * 60 * 60 * 24);
		String dtstr = sf.format(yestoday);
		return dtstr;
	}

	/**
	 * 获得当前时间的小时格式化表示
	 * 
	 * @return
	 */
	public static String getNowHours()
	{
		Date dt = new Date();
		// DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat sf = new SimpleDateFormat("HH");
		String dtstr = sf.format(dt);
		return dtstr;
	}

	// 运行收集每日玩家等级,并将其放入数据库中以备将来查询
	public void recordEveryDayPlayerGrade()
	{
		int grade1 = statDao.getAllUserGradeInfo(0, 2);
		int grade2to9 = statDao.getAllUserGradeInfo(1, 10);
		int grade10to19 = statDao.getAllUserGradeInfo(9, 20);
		int grade20to29 = statDao.getAllUserGradeInfo(19, 30);
		int grade30to39 = statDao.getAllUserGradeInfo(29, 40);
		int grade40to49 = statDao.getAllUserGradeInfo(39, 50);
		int grade50to59 = statDao.getAllUserGradeInfo(49, 60);
		int grade60 = statDao.getAllUserGradeInfo(59, 61);
		int avg_grade = statDao.getAvgGrade(10);
		statDao.insertEveryDayPlayerGrade(grade1, grade2to9, grade10to19,
				grade20to29, grade30to39, grade40to49, grade50to59, grade60,
				avg_grade, getToday());

	}

}