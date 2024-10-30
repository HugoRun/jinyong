package com.pm.service.job.day;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ls.web.service.faction.FactionService;

/**
 * @author ls
 * ÿ����3�㶨ʱִ�еĲ���
 */
public class EveryDayJob implements Job
{
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		//���ɽ�ɢ���
		FactionService.checkDisband();
		//��ɢ�����Ƿ��ǻ�Ծ����
		FactionService.checkIsActived();
	}

}
