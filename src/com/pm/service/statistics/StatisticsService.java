package com.pm.service.statistics;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.pm.dao.statistics.StatisticsDao;

public class StatisticsService
{

	Logger logger = Logger.getLogger(StatisticsService.class);
	StatisticsDao statDao = new StatisticsDao();

	/**
	 * 
	 */
	public void loginRecord()
	{

	}

	/**
	 * 把登陆消息记录下来
	 * 
	 * @param uPk
	 */
	public void recordUserLoginInfo(int uPk)
	{
		logger.info("statDao.hasRecord(upk)=" + statDao.hasRecord(uPk));
		if (statDao.hasRecord(uPk) == 0)
		{
			statDao.insertToLoginInfo(uPk);
		}
		else
		{
			statDao.updateLoginInfo(uPk);
		}
	}

	/**
	 * 记录玩家角色登陆信息
	 * 
	 * @param pk
	 */
	public void recordPersonLoginInfo(String pPk, int p_grade)
	{
		if (statDao.hasRecordPPk(pPk) == 0)
		{
			statDao.insertToPersonLoginInfo(pPk, p_grade);
		}
		else
		{
			statDao.updatePersonLoginInfo(pPk, p_grade);
		}
	}

	/**
	 * 计算玩家的在线时长,将其存入数据库中.
	 * 
	 * @param creationTime
	 * @param lastAccessedTime
	 */
	public void recordOnLineTime(long creationTime, long lastAccessedTime,
			String uPk, String pPk)
	{
		// onlineTime为秒数
		long onlineTime = (lastAccessedTime - creationTime) / (1000);
		// 如果pPk为零,就不计算了.
		if (pPk == null || pPk.equals(""))
		{

		}
		else
		{
			if (statDao.hasOnlineTimeRecord(uPk, getToday()) == 0)
			{
				statDao.insertOnLineTime(uPk, pPk, onlineTime, getToday());
			}
			else
			{
				statDao.updateOnLineTime(uPk, onlineTime, getToday());
			}
		}
	}

	/**
	 * 启动jygame统计数据程序，要求每24小时运行一次.
	 */
	public void startJYGameRecord()
	{

		Timer timer = new Timer();
		TimerTask gameData = GameDataRecordService.getInstance();
		Date dt = getRecordStartTime();
		timer.schedule(gameData, dt, 1000 * 60 * 60 * 24);
	}

	/**
	 * 启动jygame在线人数统计数据程序，要求每十分钟运行一次.
	 */
	public void startJYGameOnlineNumRecord()
	{
		Timer timer = new Timer();
		TimerTask onlineNumData = GameOnlineNumRecordService.getInstance();
		Date dt = getOnlineNumRecordStartTime();
		//Date dtTest = new Date();
		timer.schedule(onlineNumData, dt, 1000 * 60 * 10);
	}

	/**
	 * 启动战场的统计工作,在每天的13:00和21:30分开始战场内的各阵营人数, 哪方人数较多,就让哪方胜利
	 */
	public void accoutFieldResult()
	{
		/*Timer timer = new Timer();
		TimerTask onlineNumData = FieldEndAccoutService.getInstance();
		TimerTask onlineNumData1 = FieldEndAccout2Service.getInstance();
		Date dt1 = getAccoutFieldResultTime();
		timer.schedule(onlineNumData, dt1, 1000 * 60 * 60 * 24);
		Date dt2 = getAccoutFieldResultTimeAnother();
		timer.schedule(onlineNumData1, dt2, 1000 * 60 * 60 * 24);*/
	}


	/** 彩票信息 */
	public void sysLotteryAndLaborage()
	{
		try
		{
			Timer timer = new Timer();
			TimerTask onlineNumData = GameLotteryAndLaborageService
					.getInstance();
			// Date dt = getOnlineNumRecordStartTime();
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
			DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
			String Time = df1.format(new Date());// 从页面得到当前时间,并且赋给一个变量
			String times = Time + " 03:00:00";
			SimpleDateFormat formatterss = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
			Date time = formatterss.parse(times);
			Date nowtime = new Date();
			if (nowtime.getHours() > 3)
			{
				Date starttime = formatter.parse(df1.format(nowtime.getTime()
						+ 1000 * 60 * 60 * 24)
						+ " 12:00:00");
				timer.schedule(onlineNumData, starttime, 1000 * 60 * 60 * 24);
			}
			else
			{
				timer.schedule(onlineNumData, time, 1000 * 60 * 60 * 24);
			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

	/** 彩票生成 */
	public void sysLotteryNum()
	{
		try
		{
			Timer timer = new Timer();
			TimerTask onlineNumData = GameLotteryNumService.getInstance();
			// Date dt = getOnlineNumRecordStartTime();
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
			DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
			String Time = df1.format(new Date());// 从页面得到当前时间,并且赋给一个变量
			String times = Time + " 20:00:00";
			SimpleDateFormat formatterss = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
			Date time = formatterss.parse(times);
			Date nowtime = new Date();
			logger.info(nowtime.getHours());
			if (nowtime.getHours() > 20)
			{

				Date starttime = formatter.parse(df1.format(nowtime.getTime()
						+ 1000 * 60 * 60 * 24)
						+ " 12:00:00");
				timer.schedule(onlineNumData, starttime, 1000 * 60 * 60 * 24);
			}
			else
			{
				timer.schedule(onlineNumData, time, 1000 * 60 * 60 * 24);
			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

	/** 统计库存 */
	public void sysStatisticsKucun()
	{
		try
		{
			Timer timer = new Timer();
			TimerTask onlineNumData = GameStatisticsKucun.getInstance();
			// Date dt = getOnlineNumRecordStartTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
			DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
			String Time = df1.format(new Date());// 从页面得到当前时间,并且赋给一个变量
			String times = Time + " 01:00:00";
			SimpleDateFormat formatterss = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
			Date time = formatterss.parse(times);
			Date nowtime = new Date();
			logger.info(nowtime.getHours());
			if(nowtime.getHours() >= 1 && nowtime.getMinutes()>1) {

				Date starttime = formatter.parse(df1.format(nowtime.getTime() + 1000 * 60 * 60 * 24)+ " 12:00:00");
				timer.schedule(onlineNumData, starttime, 1000 * 60 * 60 * 24);
			}
			else{
				timer.schedule(onlineNumData, time, 1000 * 60 * 60 * 24);
			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 计算出战场计算后台的运行时间, 这是其中一个, 在12:30 之前启动的话就开始在今天的13:00开始计算,否则就在第二天的13:00开始计算。
	 * 
	 * @return
	 */
	private Date getAccoutFieldResultTime()
	{
		Date dt = new Date();

		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat df = new SimpleDateFormat("HH");
		DateFormat dfmintes = new SimpleDateFormat("mm");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		// 开始时间
		String startTime = null;
		String dd = df.format(dt);
		String mm = dfmintes.format(dt);
		if (Integer.valueOf(dd) < 12
				|| (Integer.valueOf(dd) < 13 && Integer.valueOf(mm) < 30))
		{
			startTime = df1.format(dt) + " 13:00:00";
		}
		else
		{
			startTime = df1.format(dt.getTime() + 1000 * 60 * 60 * 24)
					+ " 13:00:00";
			// startTime = df1.format(dt.getTime() )
			// + " 15:32:00";
		}
		// Date startTime = new Date(dt.getTime()+1000*60*60*12);

		Date start_time = null;
		try
		{
			start_time = df2.parse(startTime);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		logger.info("战场统计计算后台1的启动时间为=" + start_time + " ,格式="
				+ df2.format(start_time));
		return start_time;
	}

	/**
	 * 计算出战场计算后台的运行时间, 这是另外一个 在21 之前启动的话就开始在今天的21:00开始计算,否则就在第二天的21:30开始计算。
	 * 
	 * @return
	 */
	private Date getAccoutFieldResultTimeAnother()
	{
		Date dt = new Date();

		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat df = new SimpleDateFormat("HH");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		// 开始时间
		String startTime = null;
		String dd = df.format(dt);
		if (Integer.valueOf(dd) < 21)
		{
			startTime = df1.format(dt) + " 21:30:00";
		}
		else
		{
			startTime = df1.format(dt.getTime() + 1000 * 60 * 60 * 24)
					+ " 21:30:00";
		}
		// Date startTime = new Date(dt.getTime()+1000*60*60*12);

		Date start_time = null;
		try
		{
			start_time = df2.parse(startTime);
			logger.info("startTime=" + startTime + " ,格式="
					+ df2.format(start_time));
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		logger.info("战场统计计算后台2的启动时间为=" + start_time);
		return start_time;
	}

	/**
	 * 获得统计程序的初次运行时间.
	 * 
	 * @return
	 */
	private Date getRecordStartTime()
	{
		Date dt = new Date();

		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat df = new SimpleDateFormat("HH");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		// 开始时间
		String startTime = null;
		String dd = df.format(dt);
		if (Integer.valueOf(dd) < 4)
		{
			startTime = df1.format(dt) + " 04:00:00";
		}
		else
		{
			startTime = df1.format(dt.getTime() + 1000 * 60 * 60 * 24)
					+ " 04:00:00";

		}

		Date start_time = null;
		try
		{
			start_time = df2.parse(startTime);

		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		logger.info("统计后台的启动时间为=" + start_time + " ,格式="
				+ df2.format(start_time));
		return start_time;
	}

	/**
	 * 获得jygame在线人数统计数据程序的初次运行时间.
	 * 
	 * @return
	 */
	private Date getOnlineNumRecordStartTime()
	{
		Date dt = new Date();
		Date startTime = new Date(dt.getTime() + 1000 * 60 * 60);
		return startTime;
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
		String dtstr = sf.format(dt);
		return dtstr;
	}
}
