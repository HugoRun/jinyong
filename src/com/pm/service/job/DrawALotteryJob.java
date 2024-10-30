package com.pm.service.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.lw.service.lottery.DrawALotteryService;

public class DrawALotteryJob implements Job
{
	Logger logger = Logger.getLogger("log.quartz");

	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		// �õ�����
		String name = arg0.getJobDetail().getFullName();
		String[] id_str = name.split("_");
		int id = Integer.parseInt(id_str[1]);
		// ���������õ��齱����
		DrawALotteryService ds = new DrawALotteryService();
		logger.debug("��ʼ�齱");
		logger.info("�齱id"+id);
		ds.getSystemInfo(id);
		logger.debug("�齱����");
	}
}
