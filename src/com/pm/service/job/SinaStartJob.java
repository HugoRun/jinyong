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

public class SinaStartJob implements Job
{
	Logger logger = Logger.getLogger("log.service");

	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		runScheduler();
	}

	public void runScheduler()
	{

		Scheduler scheduler = null;

		try
		{
			// ������־ Create a default instance of the Scheduler
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			logger.info("Scheduler was started at " + new Date());
			// Create the JobDetail
			JobDetail jobDetail = new JobDetail("SinaJob",
					Scheduler.DEFAULT_GROUP, SinaJob.class);

			// ����ÿ��JOBҪִ�е�ʱ��
			try
			{
				String time = "0 59 23 ? * *";
				// �Զ���ʱ��
				CronTrigger trigger = new CronTrigger("MyTrigger_sina", null,
						time);
				Date date = new Date();
				trigger.setStartTime(date);
				scheduler.scheduleJob(jobDetail, trigger);
				logger.info("���˶�ʱ��־");
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
