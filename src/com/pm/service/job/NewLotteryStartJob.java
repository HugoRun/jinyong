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

public class NewLotteryStartJob implements Job
{

	Logger logger = Logger.getLogger("log.service");

	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		runSchedulerNewLottery();
	}

	public void runSchedulerNewLottery()
	{

		Scheduler scheduler = null;

		try
		{
			// Create a default instance of the Scheduler
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			logger.info("Scheduler was started at " + new Date());
			// Create the JobDetail
			JobDetail jobDetail = new JobDetail("NewLotteryJob",
					Scheduler.DEFAULT_GROUP, NewLotteryJob.class);

			// ����ÿ��JOBҪִ�е�ʱ��
			try
			{
				LotteryService ls = new LotteryService();
				String time = ls.getQuartzTime();
				// �Զ���ʱ��
				CronTrigger trigger = new CronTrigger("NewLotteryTrigger",
						null, time);
				Date date = new Date();
				trigger.setStartTime(date);
				scheduler.scheduleJob(jobDetail, trigger);
				logger
						.info("��Ʊ��ʱ"
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
