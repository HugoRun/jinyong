package com.pm.service.job;

import java.text.ParseException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.lw.service.lotterynew.LotteryService;

public class NewLotteryJob implements Job
{
	Logger logger = Logger.getLogger("log.quartz");

	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		LotteryService ls = new LotteryService();
		String date_str = ls.newLotteryBuild();
		runSchedulerNewLottery(date_str);
	}

	public void runSchedulerNewLottery(String date_str)
	{

		Scheduler scheduler = null;

		try
		{
			// Create a default instance of the Scheduler
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			logger.info("Scheduler was started at " + new Date());
			// Create the JobDetail
			JobDetail jobDetail = new JobDetail("NewLotteryJob" + date_str,
					Scheduler.DEFAULT_GROUP, NewLotteryJob.class);

			// 创建每个JOB要执行的时间
			try
			{
				LotteryService ls = new LotteryService();
				String time = ls.getQuartzTime();
				// 自定义时间
				CronTrigger trigger = new CronTrigger("NewLotteryTrigger"
						+ date_str, null, time);
				Date date = new Date();
				trigger.setStartTime(date);
				scheduler.scheduleJob(jobDetail, trigger);
				logger
						.info("彩票定时"
								+ time
								+ "ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
			}
			catch (ParseException ex)
			{
				logger.error("Couldn't parse cron expr", ex);
			}

		}
		catch (SchedulerException ex)
		{
			logger.error(ex);
		}
	}
}
