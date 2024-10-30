package com.pm.service.job;

import com.pm.service.auction.AuctionService;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class AuctionFalse implements Job {

    Logger logger = Logger.getLogger("log.quartz");

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        AuctionService as = new AuctionService();
        as.processAuctionFalse();
    }

}
