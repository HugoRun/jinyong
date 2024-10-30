package com.ls.pub.listener;

/**
 * 功能:游戏启动
 *
 * @author 刘帅
 * Oct 21, 2008  1:59:51 PM
 */

import com.ben.dao.logininfo.LoginInfoDAO;
import com.ben.shitu.service.ShituService;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.web.service.cache.CacheService;
import com.ls.web.service.group.GroupService;
import com.lw.dao.lottery.DrawALotteryDao;
import com.lw.service.activities.ActivitiesService;
import com.pm.service.job.DrawALotteryStartJob;
import com.pm.service.job.SinaStartJob;
import com.web.jieyi.util.Constant;
import org.apache.log4j.Logger;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Hashtable;

public class ShutdownContextListener implements ServletContextListener {

    Logger logger = Logger.getLogger("log.service");

    public void contextInitialized(ServletContextEvent event) {
        logger.debug("###############游戏启动###############");
        try {
            LoginInfoDAO loginInfoDAO = new LoginInfoDAO();

            CacheService cacheService = new CacheService();
            GroupService groupService = new GroupService();

            // 设置用户为不在线状态
            loginInfoDAO.updateLoginState("0");
            logger.info("##############设置玩家登陆状态#################");
            // 更新登陆状态
            PartInfoDao partInfoDao = new PartInfoDao();
            // 設置玩家登陸不在線狀態
            partInfoDao.updateLoginState("0");
            // 初始化缓存
            cacheService.initCache();
            // 初始化组队信息
            groupService.initTempGroupInfo();

            logger.info("##############抽奖重置#################");
            DrawALotteryDao dao = new DrawALotteryDao();
            //给抽奖重置成未定义状态
            dao.updateIsRun(1, 0);
            dao.updateIsRun(2, 0);

            //执行抽奖信息
            DrawALotteryStartJob drawALotteryStartJob = new DrawALotteryStartJob();

            logger.info("抽奖执行11111111111111111111111111111111111111111111111111111111111111111");
            drawALotteryStartJob.runScheduler();
            ActivitiesService as = new ActivitiesService();
            as.runActivities();
            if (Channel.SINA == GameConfig.getChannelId()) {
                SinaStartJob sinaStartJob = new SinaStartJob();
                sinaStartJob.runScheduler();
                logger.info("新浪日志11111111111111111111111111111111111111111111111111111111111111111");
            }
            //初始化NPC 列表
            Constant.MENPAINPC.put(1, 0);
            // 执行彩票信息
            //NewLotteryStartJob newLotteryStartJob = new NewLotteryStartJob();
            //newLotteryStartJob.runSchedulerNewLottery();

            // CommunionDAO communionDAO = new CommunionDAO();
            // communionDAO.clearCommList();//清除聊天信息
            // 清楚视野表
            // PartAnnalDAO dao = new PartAnnalDAO();
            // dao.clearPartAnnal();
            // 删除pk通知表
            // PKNotifyDao pKNotifyDao = new PKNotifyDao();
            // pKNotifyDao.deleteByPlayer();
            // 设置删除角色交流表
            // //当服务器启动时, 将当前在线人数置为零
            // PlayerOnlineNumRecord playerOnlineNumRecord = new
            // PlayerOnlineNumRecord();
            // playerOnlineNumRecord.setOnlineNumToZero();

            // 数据统计启动程序
            // StatisticsService statService = new StatisticsService();
            // statService.startJYGameRecord();
            // statService.startJYGameOnlineNumRecord();
            // statService.startJYGameOnlineTongKill();
            // statService.accoutFieldResult();
            // statService.sysLotteryNum();
            // statService.sysLotteryAndLaborage();

            logger.info("加载师徒系统");
            ShituService ss = new ShituService();
            ss.loadAllStudent();
            ss.loadAllTeacher();
            logger.info("加载师徒系统完成");

            if (GameConfig.jmsIsOn()) {
                init();
            }

            logger.info("###############游戏启动完成###############");
        } catch (Exception e) {
            logger.debug("###############游戏启动失败###############");
            logger.debug("失败原因：" + e);
            e.getStackTrace();
        }

    }

    public void contextDestroyed(ServletContextEvent event) {
        logger.info("###############关闭游戏###############");
        // 容器关闭时时执行的代码
    }

    public void init() {
        try {
            Hashtable<String, String> properties = new Hashtable();
            properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.exolab.jms.jndi.InitialContextFactory");
            properties.put(Context.PROVIDER_URL, "rmi://" + GameConfig.getJmsUrl() + ":1099/");
            Context context = new InitialContext(properties);
            QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) context.lookup("JmsQueueConnectionFactory");
            QueueConnection qCon = queueConnectionFactory.createQueueConnection();
            QueueSession qSession = qCon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = (Queue) context.lookup("queue3");
            QueueReceiver qReceiver = qSession.createReceiver(queue);
            qCon.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("JMS服务没有启动");
        }
    }
}
