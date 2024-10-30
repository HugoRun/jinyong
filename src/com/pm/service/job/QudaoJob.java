package com.pm.service.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.lw.service.player.QudaoService;

public class QudaoJob implements Job
{
	Logger logger = Logger.getLogger("log.quartz");

	public void execute(JobExecutionContext context)
	{
		logger.debug("����ͳ�ƿ�ʼ");
		QudaoService qs = new QudaoService();
		qs.insertQudao();
		logger.debug("����ͳ�ƽ���");
	}

}