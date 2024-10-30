package com.pm.service.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.lw.service.menpaicontest.MenpaiContestService;

public class MenpaiContestReady implements Job
{
	Logger logger = Logger.getLogger("log.quartz");

	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		MenpaiContestService mc = new MenpaiContestService();
		mc.menpaiContestReady();
	}

}
