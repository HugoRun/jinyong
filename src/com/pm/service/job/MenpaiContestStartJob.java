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

import com.lw.dao.menpaicontest.MenpaiContestDAO;
import com.lw.vo.menpaicontest.MenpaiContestVO;

public class MenpaiContestStartJob implements Job
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
			// ����׼�� Create a default instance of the Scheduler
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			logger.info("Scheduler was started at " + new Date());

			MenpaiContestDAO dao = new MenpaiContestDAO();
			MenpaiContestVO vo = dao.selectMenpaiContestData();
			if (vo != null)
			{
				// Create the JobDetail
				JobDetail jobDetail_ready = new JobDetail(
						"MenpaiContest_ready", Scheduler.DEFAULT_GROUP,
						MenpaiContestReady.class);

				// ����ÿ��JOBҪִ�е�ʱ��
				try
				{
					String time = "0 " + vo.getReady_minute() + " "
							+ vo.getReady_hour() + " ? * "
							+ vo.getTime_week().trim();
					// �Զ���ʱ��
					CronTrigger trigger_ready = new CronTrigger(
							"MyTrigger_ready", null, time);
					Date date = new Date();
					trigger_ready.setStartTime(date);
					scheduler.scheduleJob(jobDetail_ready, trigger_ready);
					logger.info("���俪ʼ��ʱ");
				}
				catch (ParseException ex)
				{
					logger.error("Couldn't parse cron expr", ex);
				}

				// ���俪ʼCreate the JobDetail
				JobDetail jobDetail_start = new JobDetail(
						"MenpaiContest_start", Scheduler.DEFAULT_GROUP,
						MenpaiContestRun.class);

				// ����ÿ��JOBҪִ�е�ʱ��
				try
				{
					String time = "0 " + vo.getRun_minute() + " "
							+ vo.getRun_hour() + " ? * "
							+ vo.getTime_week().trim();
					// �Զ���ʱ��
					CronTrigger trigger_start = new CronTrigger(
							"MyTrigger_start" + vo.getId(), null, time);
					Date date = new Date();
					trigger_start.setStartTime(date);
					scheduler.scheduleJob(jobDetail_start, trigger_start);
					logger.info("���䶨ʱ");
				}
				catch (ParseException ex)
				{
					logger.error("Couldn't parse cron expr", ex);
				}

				// ���俪ʼCreate the JobDetail
				JobDetail jobDetail_over = new JobDetail("MenpaiContest_over",
						Scheduler.DEFAULT_GROUP, MenpaiContestOver.class);

				// ����ÿ��JOBҪִ�е�ʱ��
				try
				{
					String time = "0 " + vo.getOver_minute() + " "
							+ vo.getOver_hour() + " ? * "
							+ vo.getTime_week().trim();
					// �Զ���ʱ��
					CronTrigger trigger_over = new CronTrigger(
							"MyTrigger_over", null, time);
					Date date = new Date();
					trigger_over.setStartTime(date);
					scheduler.scheduleJob(jobDetail_over, trigger_over);
					logger.info("���䶨ʱ");
				}
				catch (ParseException ex)
				{
					logger.error("Couldn't parse cron expr", ex);
				}
				
				// ���俪ʼCreate the JobDetail
				JobDetail jobDetail_all = new JobDetail("MenpaiContest_all",
						Scheduler.DEFAULT_GROUP, MenpaiContestAll.class);

				// ����ÿ��JOBҪִ�е�ʱ��
				try
				{
					String time = "0 " + vo.getAll_minute() + " "
							+ vo.getAll_hour() + " ? * "
							+ vo.getTime_week().trim();
					// �Զ���ʱ��
					CronTrigger trigger_all = new CronTrigger(
							"MyTrigger_all", null, time);
					Date date = new Date();
					trigger_all.setStartTime(date);
					scheduler.scheduleJob(jobDetail_all, trigger_all);
					logger.info("���䶨ʱ");
				}
				catch (ParseException ex)
				{
					logger.error("Couldn't parse cron expr", ex);
				}
			}
		}
		catch (SchedulerException ex)
		{
			logger.error(ex);
		}
	}
}
