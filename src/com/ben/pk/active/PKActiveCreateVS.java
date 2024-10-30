package com.ben.pk.active;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.lw.service.menpaicontest.MenpaiContestService;

public class PKActiveCreateVS implements Job
{

	Logger logger = Logger.getLogger("log.quartz");

	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month=cal.get(Calendar.MONTH)+1;
		if(month!=5)
		{
			if(day<=3)
			{
				return;
			}
		}
		else
		{
			if(day>=12&&day<=14)
			{
				return;
			}
		}
		PKActiveService ps=new PKActiveService();
		ps.createVSInfo();
	}

}
