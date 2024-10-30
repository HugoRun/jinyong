package com.pm.service.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pm.service.job.weekly.WeekRankStatisticsJob;
import com.pm.service.job.weekly.WeeklyGongZiJob;

public class MonthlyJob implements Job
{

	/**
	 * 每月执行一次
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		WeekRankStatisticsJob weekRankStatisticsJob = new WeekRankStatisticsJob();
		JobDetail jobDetail = arg0.getJobDetail();  
		JobDataMap jobDataMap = jobDetail.getJobDataMap();
		
		//Set SET = jobDataMap.entrySet();
		String path = (String) jobDataMap.get("path");
		
		String vlaue1 = (String) jobDataMap.get("key1");
		String vlaue2 = (String) jobDataMap.get("key2");
		String vlaue3 = (String) jobDataMap.get("key3");
		String vlaue4 = (String) jobDataMap.get("key4");
		String vlaue5 = (String) jobDataMap.get("key5");
		String vlaue6 = (String) jobDataMap.get("key6");
		String vlaue7 = (String) jobDataMap.get("key7");
		String vlaue8 = (String) jobDataMap.get("key8");
		String vlaue9 = (String) jobDataMap.get("key9");
		String vlaue10 = (String) jobDataMap.get("key10");
		String vlaue11 = (String) jobDataMap.get("key11");
		String value12 = (String)jobDataMap.get("key12");
		weekRankStatisticsJob.recordPaiMingData(vlaue1, path);
		weekRankStatisticsJob.recordPaiMingData(vlaue2, path);
		weekRankStatisticsJob.recordPaiMingData(vlaue3, path);
		weekRankStatisticsJob.recordPaiMingData(vlaue4, path);
		weekRankStatisticsJob.recordPaiMingData(vlaue5, path);
		
		weekRankStatisticsJob.recordPaiMingData(vlaue6, path);
		weekRankStatisticsJob.recordPaiMingData(vlaue7, path);
		weekRankStatisticsJob.recordPaiMingData(vlaue8, path);
		weekRankStatisticsJob.recordPaiMingData(vlaue9, path);
		weekRankStatisticsJob.recordPaiMingData(vlaue10, path);
		weekRankStatisticsJob.recordPaiMingData(vlaue11, path);
		weekRankStatisticsJob.recordPaiMingData(value12, path);

	}

}
