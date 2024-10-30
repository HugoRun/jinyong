package com.ben.pk.active;

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
import org.quartz.Trigger;
import org.quartz.helpers.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;

public class PKActiveStartJob implements Job
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
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			logger.info("Scheduler was started at " + new Date());
			//创建对阵表
			JobDetail jobDetail_createVs = new JobDetail("PkActive_createVS", Scheduler.DEFAULT_GROUP,PKActiveCreateVS.class);
			try
			{
				CronTrigger trigger_createVS = new CronTrigger("MyTrigger_createVS", null, "0 00 00 ? * *");//每天下午24:00执行
				Date date = new Date();
				trigger_createVS.setStartTime(date);
				scheduler.scheduleJob(jobDetail_createVs, trigger_createVS);
				logger.info("创建对阵表定时开始.........");
			}
			catch (ParseException e)
			{
				logger.error(e);
			}
			//比赛时间没有进入比赛场地
			JobDetail jobDetail_outOfEnter=new JobDetail("PKActive_outOfEnter",Scheduler.DEFAULT_GROUP,PKActiveOutOfEnter.class);
			try
			{
				CronTrigger trigger_outOFEnter=new CronTrigger("MyTrigger_outOfEnter",null,"0 5 13 ? * *");//每天下午一点五分执行
				Date date = new Date();
				trigger_outOFEnter.setStartTime(date);
				scheduler.scheduleJob(jobDetail_outOfEnter,trigger_outOFEnter);//trigger_outOFEnter
				logger.info("到时间没有进入比赛场地计时开始.......");
			}
			catch (ParseException e)
			{
				logger.error(e);
			}
			//比赛超时
			JobDetail jobDetail_outOfPK=new JobDetail("PKActive_outOfPK",Scheduler.DEFAULT_GROUP,PKActiveOutOfPk.class);
			try
			{
				CronTrigger trigger_outOfPK=new CronTrigger("MyTrigger_outOfPK",null,"0 30 13 ? * *");//每天下午一点半
				Date date = new Date();
				trigger_outOfPK.setStartTime(date);
				scheduler.scheduleJob(jobDetail_outOfPK,trigger_outOfPK);//trigger_outOFEnter
			}
			catch (ParseException e)
			{
				logger.error(e);
			}

		}
		catch (SchedulerException ex)
		{
			logger.error(ex);
		}
	}

}
