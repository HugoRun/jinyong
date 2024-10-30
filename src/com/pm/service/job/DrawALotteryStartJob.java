package com.pm.service.job;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.lw.dao.lottery.DrawALotteryDao;
import com.lw.vo.lottery.DrawALotteryVO;

public class DrawALotteryStartJob implements Job
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
			// Create a default instance of the Scheduler
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			logger.info("Scheduler was started at " + new Date());
			DrawALotteryDao dao = new DrawALotteryDao();
			// 每天
			List<DrawALotteryVO> list = dao.getDrawALotteryInfoByTimeType(1);
			for (int i = 0; i < list.size(); i++)
			{
				DrawALotteryVO vo = list.get(i);
				// Create the JobDetail
				JobDetail jobDetail = new JobDetail("DrawALotteryJob_"
						+ vo.getId(), Scheduler.DEFAULT_GROUP,
						DrawALotteryJob.class);

				// 创建每个JOB要执行的时间
				try
				{
					String time = "0 " + vo.getTimeminute() + " "
							+ vo.getTimeHour() + " ? * *";
					// 自定义时间
					CronTrigger trigger = new CronTrigger("MyTrigger_"
							+ vo.getId(), null, time);
					Date date = new Date();
					trigger.setStartTime(date);
					scheduler.scheduleJob(jobDetail, trigger);
					logger.info("抽奖定时成功");
				}
				catch (ParseException ex)
				{
					logger.error("Couldn't parse cron expr", ex);
				}
			}
			dao.updateIsRun(1, 1);

			// 按时间类型 每周
			List<DrawALotteryVO> list_time = dao
					.getDrawALotteryInfoByTimeType(2);
			for (int i = 0; i < list_time.size(); i++)
			{
				DrawALotteryVO vo = list_time.get(i);
				// Create the JobDetail
				JobDetail jobDetail = new JobDetail("DrawALotteryJob_"
						+ vo.getId(), Scheduler.DEFAULT_GROUP,
						DrawALotteryJob.class);

				// 创建每个JOB要执行的时间
				try
				{
					String time = "0 " + vo.getTimeminute() + " "
							+ vo.getTimeHour() + " ? * "
							+ vo.getTimeweek().trim();
					// 自定义时间
					CronTrigger trigger = new CronTrigger("MyTrigger_"
							+ vo.getId(), null, time);
					Date date = new Date();
					trigger.setStartTime(date);
					scheduler.scheduleJob(jobDetail, trigger);
					logger.info("抽奖定时成功");
				}
				catch (ParseException ex)
				{
					logger.error("Couldn't parse cron expr", ex);
				}
			}
			dao.updateIsRun(2, 1);
		}
		catch (SchedulerException ex)
		{
			logger.error(ex);
		}
	}
}
