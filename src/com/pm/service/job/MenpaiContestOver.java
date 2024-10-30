package com.pm.service.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.lw.service.menpaicontest.MenpaiContestService;

public class MenpaiContestOver implements Job
{
	Logger logger = Logger.getLogger("log.quartz");

	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		MenpaiContestService mc = new MenpaiContestService();
		mc.menpaiContestOver(1);
		mc.menpaiContestOver(2);
		mc.menpaiContestOver(3);
	}

}
