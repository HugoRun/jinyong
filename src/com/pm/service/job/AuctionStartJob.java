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

import com.ben.pk.active.PKActiveCreateVS;
import com.ben.pk.active.PKActiveOutOfEnter;
import com.ben.pk.active.PKActiveOutOfPk;

public class AuctionStartJob implements Job
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
			//ͳ�ƾ����Ƿ�ɹ�
			JobDetail jobDetail_auctionSuccess = new JobDetail("auction_success", Scheduler.DEFAULT_GROUP,AuctionSuccess.class);
			try
			{
				CronTrigger trigger_auctionSuccess = new CronTrigger("MyTrigger_success", null, "0 0/5 * * * ?");//ÿ�����ִ��һ��
				Date date = new Date();
				trigger_auctionSuccess.setStartTime(date);
				scheduler.scheduleJob(jobDetail_auctionSuccess, trigger_auctionSuccess);
				logger.info("��������ʱ��ʼ��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~");
			}
			catch (ParseException e)
			{
				logger.error(e);
			}
			//ͳ��������������Ҫ������߼�
			JobDetail jobDetail_AuctionFalse=new JobDetail("auction_false",Scheduler.DEFAULT_GROUP,AuctionFalse.class);
			try
			{
				CronTrigger trigger_auctionFalse=new CronTrigger("MyTrigger_auctionFalse",null,"0 00 00 ? * *");//ÿ��0ʱִ��һ��
				Date date = new Date();
				trigger_auctionFalse.setStartTime(date);
				scheduler.scheduleJob(jobDetail_AuctionFalse,trigger_auctionFalse);//trigger_outOFEnter
				logger.info("��������ʱ��ʼ��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~");
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
