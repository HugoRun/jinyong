package com.pm.service.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pm.service.statistics.GameLotteryAndLaborageService;

public class GameLotteryAndLaborageJob implements Job{

	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		GameLotteryAndLaborageService gameLotteryAndLaborageService = new GameLotteryAndLaborageService();
		gameLotteryAndLaborageService.delPlayMessage();
		gameLotteryAndLaborageService.updateLaborageMessage();		
	}   
	
	
	

}
