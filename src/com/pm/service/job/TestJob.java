package com.pm.service.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob implements Job
{
	Logger logger = Logger.getLogger("log.quartz");

	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		//System.out.println("阿肯定是解放");
	}
}