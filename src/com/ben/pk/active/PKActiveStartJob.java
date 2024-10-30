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
			//���������
			JobDetail jobDetail_createVs = new JobDetail("PkActive_createVS", Scheduler.DEFAULT_GROUP,PKActiveCreateVS.class);
			try
			{
				CronTrigger trigger_createVS = new CronTrigger("MyTrigger_createVS", null, "0 00 00 ? * *");//ÿ������24:00ִ��
				Date date = new Date();
				trigger_createVS.setStartTime(date);
				scheduler.scheduleJob(jobDetail_createVs, trigger_createVS);
				logger.info("���������ʱ��ʼ.........");
			}
			catch (ParseException e)
			{
				logger.error(e);
			}
			//����ʱ��û�н����������
			JobDetail jobDetail_outOfEnter=new JobDetail("PKActive_outOfEnter",Scheduler.DEFAULT_GROUP,PKActiveOutOfEnter.class);
			try
			{
				CronTrigger trigger_outOFEnter=new CronTrigger("MyTrigger_outOfEnter",null,"0 5 13 ? * *");//ÿ������һ�����ִ��
				Date date = new Date();
				trigger_outOFEnter.setStartTime(date);
				scheduler.scheduleJob(jobDetail_outOfEnter,trigger_outOFEnter);//trigger_outOFEnter
				logger.info("��ʱ��û�н���������ؼ�ʱ��ʼ.......");
			}
			catch (ParseException e)
			{
				logger.error(e);
			}
			//������ʱ
			JobDetail jobDetail_outOfPK=new JobDetail("PKActive_outOfPK",Scheduler.DEFAULT_GROUP,PKActiveOutOfPk.class);
			try
			{
				CronTrigger trigger_outOfPK=new CronTrigger("MyTrigger_outOfPK",null,"0 30 13 ? * *");//ÿ������һ���
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
