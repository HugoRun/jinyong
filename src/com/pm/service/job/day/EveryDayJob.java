package com.pm.service.job.day;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ls.web.service.faction.FactionService;

/**
 * @author ls
 * 每天晚3点定时执行的操作
 */
public class EveryDayJob implements Job
{
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		//帮派解散检查
		FactionService.checkDisband();
		//解散帮派是否是活跃帮派
		FactionService.checkIsActived();
	}

}
