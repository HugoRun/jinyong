package com.lw.service.activities;

import com.ben.lost.CompassService;
import com.ben.pk.active.PKActiveStartJob;
import com.pm.service.job.AuctionStartJob;
import com.pm.service.job.MenpaiContestStartJob;
import org.apache.log4j.Logger;

public class ActivitiesService {
    // 日志句柄
    Logger logger = Logger.getLogger("log.service");

    /***
     * 加载获得列表
     */
    public void runActivities() {
        // 门派比武
        MenpaiContestStartJob menpaiContestStartJob = new MenpaiContestStartJob();
        menpaiContestStartJob.runScheduler();
        logger.info("门派大弟子执行11111111111111111111111111111111111111111111111111111111111111111");
        // 神秘迷宫
        new CompassService().loadToMemory();
        // 执行PK活动
        PKActiveStartJob psj = new PKActiveStartJob();
        psj.runScheduler();
        logger.info("PK活动定时器开始执行.............................................");
        // 执行拍卖场
        AuctionStartJob asj = new AuctionStartJob();
        asj.runScheduler();
        logger.info("拍卖场定时开始！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~！~");
    }
}
