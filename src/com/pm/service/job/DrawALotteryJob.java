package com.pm.service.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.lw.service.lottery.DrawALotteryService;

public class DrawALotteryJob implements Job
{
	Logger logger = Logger.getLogger("log.quartz");

	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		// 得到主键
		String name = arg0.getJobDetail().getFullName();
		String[] id_str = name.split("_");
		int id = Integer.parseInt(id_str[1]);
		// 根据主键得到抽奖内容
		DrawALotteryService ds = new DrawALotteryService();
		logger.debug("开始抽奖");
		logger.info("抽奖id"+id);
		ds.getSystemInfo(id);
		logger.debug("抽奖结束");
	}
}
