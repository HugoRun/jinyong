package com.ben.lost;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ben.shitu.model.DateUtil;

public class LostJob implements Job
{

	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		JobDetail jobDetail = arg0.getJobDetail();  
		JobDataMap jobDataMap = jobDetail.getJobDataMap();
		String vlaue1 = (String) jobDataMap.get("endtime");
		Date date = DateUtil.getDate(vlaue1);
		Date date1 = DateUtil.getDate(vlaue1);
		new CompassService().startLost(date, date1);
		
	}

}
