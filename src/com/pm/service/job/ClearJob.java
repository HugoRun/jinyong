package com.pm.service.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pm.dao.mail.MailInfoDao;

public class ClearJob implements Job
{
	Logger logger = Logger.getLogger("log.quartz");

	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		logger.debug("开始垃圾清理");
		logger.debug("开始清理邮件");
		
		MailInfoDao mailInfoDao = new MailInfoDao();
		mailInfoDao.deleteMailIfOutSeven();
		
		logger.debug("邮件清理结束");
		logger.debug("垃圾清理结束");
	}

}
