package com.pm.service.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pm.service.statistics.GameLotteryNumService;

public class GameLotteryNumJob implements Job{

	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		GameLotteryNumService  gameLotteryNumService = new GameLotteryNumService();
		gameLotteryNumService.updateLotteryNum();		
	}   
	
}
