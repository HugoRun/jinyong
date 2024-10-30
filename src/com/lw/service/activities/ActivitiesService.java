package com.lw.service.activities;

import org.apache.log4j.Logger;

import com.ben.lost.CompassService;
import com.ben.pk.active.PKActiveStartJob;
import com.pm.service.job.AuctionStartJob;
import com.pm.service.job.MenpaiContestStartJob;

public class ActivitiesService
{
	Logger logger = Logger.getLogger("log.service");

	/** **************�Ƿ���ػ************************ */

	public void runActivities()
	{
		
		MenpaiContestStartJob menpaiContestStartJob = new MenpaiContestStartJob();
		menpaiContestStartJob.runScheduler();
		logger.info("���ɴ����ִ��11111111111111111111111111111111111111111111111111111111111111111");

		new CompassService().loadToMemory();
		
		//ִ��PK�
		PKActiveStartJob psj=new PKActiveStartJob();
		psj.runScheduler();
		logger.info("PK���ʱ����ʼִ��.............................................");
		//ִ��������
		AuctionStartJob asj=new AuctionStartJob();
		asj.runScheduler();
		logger.info("��������ʱ��ʼ��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~��~");
	}
}
