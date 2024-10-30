package com.pm.service.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pm.service.auction.AuctionService;

public class AuctionFalse implements Job
{

	Logger logger = Logger.getLogger("log.quartz");
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		AuctionService as =new AuctionService();
		as.processAuctionFalse();
	}

}
