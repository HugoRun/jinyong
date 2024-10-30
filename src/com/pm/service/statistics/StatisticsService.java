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
	 * �ѵ�½��Ϣ��¼����
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
	 * ��¼��ҽ�ɫ��½��Ϣ
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
	 * ������ҵ�����ʱ��,����������ݿ���.
	 * 
	 * @param creationTime
	 * @param lastAccessedTime
	 */
	public void recordOnLineTime(long creationTime, long lastAccessedTime,
			String uPk, String pPk)
	{
		// onlineTimeΪ����
		long onlineTime = (lastAccessedTime - creationTime) / (1000);
		// ���pPkΪ��,�Ͳ�������.
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
	 * ����jygameͳ�����ݳ���Ҫ��ÿ24Сʱ����һ��.
	 */
	public void startJYGameRecord()
	{

		Timer timer = new Timer();
		TimerTask gameData = GameDataRecordService.getInstance();
		Date dt = getRecordStartTime();
		timer.schedule(gameData, dt, 1000 * 60 * 60 * 24);
	}

	/**
	 * ����jygame��������ͳ�����ݳ���Ҫ��ÿʮ��������һ��.
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
	 * ����ս����ͳ�ƹ���,��ÿ���13:00��21:30�ֿ�ʼս���ڵĸ���Ӫ����, �ķ������϶�,�����ķ�ʤ��
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


	/** ��Ʊ��Ϣ */
	public void sysLotteryAndLaborage()
	{
		try
		{
			Timer timer = new Timer();
			TimerTask onlineNumData = GameLotteryAndLaborageService
					.getInstance();
			// Date dt = getOnlineNumRecordStartTime();
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
			DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
			String Time = df1.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
			String times = Time + " 03:00:00";
			SimpleDateFormat formatterss = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
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

	/** ��Ʊ���� */
	public void sysLotteryNum()
	{
		try
		{
			Timer timer = new Timer();
			TimerTask onlineNumData = GameLotteryNumService.getInstance();
			// Date dt = getOnlineNumRecordStartTime();
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
			DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
			String Time = df1.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
			String times = Time + " 20:00:00";
			SimpleDateFormat formatterss = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
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

	/** ͳ�ƿ�� */
	public void sysStatisticsKucun()
	{
		try
		{
			Timer timer = new Timer();
			TimerTask onlineNumData = GameStatisticsKucun.getInstance();
			// Date dt = getOnlineNumRecordStartTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
			DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
			String Time = df1.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
			String times = Time + " 01:00:00";
			SimpleDateFormat formatterss = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
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
	 * �����ս�������̨������ʱ��, ��������һ��, ��12:30 ֮ǰ�����Ļ��Ϳ�ʼ�ڽ����13:00��ʼ����,������ڵڶ����13:00��ʼ���㡣
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
		// ��ʼʱ��
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
		logger.info("ս��ͳ�Ƽ����̨1������ʱ��Ϊ=" + start_time + " ,��ʽ="
				+ df2.format(start_time));
		return start_time;
	}

	/**
	 * �����ս�������̨������ʱ��, ��������һ�� ��21 ֮ǰ�����Ļ��Ϳ�ʼ�ڽ����21:00��ʼ����,������ڵڶ����21:30��ʼ���㡣
	 * 
	 * @return
	 */
	private Date getAccoutFieldResultTimeAnother()
	{
		Date dt = new Date();

		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat df = new SimpleDateFormat("HH");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		// ��ʼʱ��
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
			logger.info("startTime=" + startTime + " ,��ʽ="
					+ df2.format(start_time));
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		logger.info("ս��ͳ�Ƽ����̨2������ʱ��Ϊ=" + start_time);
		return start_time;
	}

	/**
	 * ���ͳ�Ƴ���ĳ�������ʱ��.
	 * 
	 * @return
	 */
	private Date getRecordStartTime()
	{
		Date dt = new Date();

		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat df = new SimpleDateFormat("HH");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		// ��ʼʱ��
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
		logger.info("ͳ�ƺ�̨������ʱ��Ϊ=" + start_time + " ,��ʽ="
				+ df2.format(start_time));
		return start_time;
	}

	/**
	 * ���jygame��������ͳ�����ݳ���ĳ�������ʱ��.
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
	 * ���ÿ��ʱ��ĸ�ʽ����ʾ
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
