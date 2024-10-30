package com.lw.service.activities;

import org.apache.log4j.Logger;

import com.ben.lost.CompassService;
import com.ben.pk.active.PKActiveStartJob;
import com.pm.service.job.AuctionStartJob;
import com.pm.service.job.MenpaiContestStartJob;

public class ActivitiesService
{
	Logger logger = Logger.getLogger("log.service");

	/** **************是否加载活动************************ */

	public void runActivities()
	{
		
		MenpaiContestStartJob menpaiContestStartJob = new MenpaiContestStartJob();
		menpaiContestStartJob.runScheduler();
		logger.info("门派大弟子执行11111111111111111111111111111111111111111111111111111111111111111");

		new CompassService().loadToMemory();
		
		//执行PK活动
		PKActiveStartJob psj=new PKActiveStartJob();
		psj.runScheduler();
		logger.info("PK活动定时器开始执行.............................................");
		//执行拍卖场
		AuctionStartJob asj=new AuctionStartJob();
		asj.runScheduler();
		logger.info("拍卖场定时开始！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~");
	}
}
