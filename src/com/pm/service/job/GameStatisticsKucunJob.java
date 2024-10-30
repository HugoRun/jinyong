package com.pm.service.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pm.service.statistics.GameStatisticsKucun;
import com.web.service.expnpcdrop.ExpnpcdropService;

public class GameStatisticsKucunJob implements Job
{

	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		GameStatisticsKucun gameStatisticsKucun = new GameStatisticsKucun();
		gameStatisticsKucun.kucun();
		// 系统消息
		ExpnpcdropService expnpcdropService = new ExpnpcdropService();
		expnpcdropService.sendSystemMessageByExp();
	}

}
