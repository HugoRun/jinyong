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
		logger.debug("��ʼ��������");
		logger.debug("��ʼ�����ʼ�");
		
		MailInfoDao mailInfoDao = new MailInfoDao();
		mailInfoDao.deleteMailIfOutSeven();
		
		logger.debug("�ʼ��������");
		logger.debug("�����������");
	}

}
