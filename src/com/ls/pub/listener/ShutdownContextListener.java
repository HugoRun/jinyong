package com.ls.pub.listener;

/**
 * ����:��Ϸ����
 * @author ��˧
 * Oct 21, 2008  1:59:51 PM
 */
import java.util.Hashtable;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

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

public class ShutdownContextListener implements ServletContextListener
{

	Logger logger = Logger.getLogger("log.service");
	
	public void contextInitialized(ServletContextEvent event)
	{
		logger.debug("###############��Ϸ����###############");
		try
		{
    		LoginInfoDAO loginInfoDAO = new LoginInfoDAO();
    
    		CacheService cacheService = new CacheService();
    		GroupService groupService = new GroupService();
    
    		loginInfoDAO.updateLoginState("0"); // �����û�Ϊ������״̬
    		logger.info("##############������ҵ�½״̬#################");
    		// ���µ�½״̬
    		PartInfoDao partInfoDao = new PartInfoDao();
    		partInfoDao.updateLoginState("0"); // �O����ҵ�ꑲ��ھ���B
    
    		cacheService.initCache();// ��ʼ������
    
    		groupService.initTempGroupInfo();// ��ʼ�������Ϣ
    
    		logger.info("##############�齱����#################");
    		DrawALotteryDao dao = new DrawALotteryDao();
    		//���齱���ó�δ����״̬
    		dao.updateIsRun(1, 0);
    		dao.updateIsRun(2, 0);
    
    		//ִ�г齱��Ϣ
    		DrawALotteryStartJob drawALotteryStartJob = new DrawALotteryStartJob();
    		
    		logger.info("�齱ִ��11111111111111111111111111111111111111111111111111111111111111111");
    		drawALotteryStartJob.runScheduler();
    		ActivitiesService as = new ActivitiesService();
    		as.runActivities();
    		if(Channel.SINA == GameConfig.getChannelId()){
    			SinaStartJob sinaStartJob = new SinaStartJob();
    			sinaStartJob.runScheduler();
    			logger.info("������־11111111111111111111111111111111111111111111111111111111111111111");
    		}
    		//��ʼ��NPC �б�
    		Constant.MENPAINPC.put(1, 0);
    		// ִ�в�Ʊ��Ϣ
    		//NewLotteryStartJob newLotteryStartJob = new NewLotteryStartJob();
    		//newLotteryStartJob.runSchedulerNewLottery();
    
    		// CommunionDAO communionDAO = new CommunionDAO();
    		// communionDAO.clearCommList();//���������Ϣ
    		// �����Ұ��
    		// PartAnnalDAO dao = new PartAnnalDAO();
    		// dao.clearPartAnnal();
    		// ɾ��pk֪ͨ��
    		// PKNotifyDao pKNotifyDao = new PKNotifyDao();
    		// pKNotifyDao.deleteByPlayer();
    		// ����ɾ����ɫ������
    		// //������������ʱ, ����ǰ����������Ϊ��
    		// PlayerOnlineNumRecord playerOnlineNumRecord = new
    		// PlayerOnlineNumRecord();
    		// playerOnlineNumRecord.setOnlineNumToZero();
    
    		// ����ͳ����������
    		// StatisticsService statService = new StatisticsService();
    		// statService.startJYGameRecord();
    		// statService.startJYGameOnlineNumRecord();
    		// statService.startJYGameOnlineTongKill();
    		// statService.accoutFieldResult();
    		// statService.sysLotteryNum();
    		// statService.sysLotteryAndLaborage();
    		
    		logger.info("����ʦͽϵͳ");
    		ShituService ss = new ShituService();
    		ss.loadAllStudent();
    		ss.loadAllTeacher();
    		logger.info("����ʦͽϵͳ���");
    		
    		if (GameConfig.jmsIsOn())
    		{
    			init();
    		}
    		
    		logger.info("###############��Ϸ�������###############");
		}
		catch (Exception e)
		{
			logger.debug("###############��Ϸ����ʧ��###############");
			logger.debug("ʧ��ԭ��"+e.toString());
			e.getStackTrace();
		}

	}

	public void contextDestroyed(ServletContextEvent event)
	{
		logger.info("###############�ر���Ϸ###############");

		// �����ر�ʱʱִ�еĴ���
	}

	public void init()
	{
		try
		{
			Hashtable properties = new Hashtable();
			properties.put(Context.INITIAL_CONTEXT_FACTORY,
					"org.exolab.jms.jndi.InitialContextFactory");
			properties.put(Context.PROVIDER_URL, "rmi://"
					+ GameConfig.getJmsUrl() + ":1099/");
			Context context = new InitialContext(properties);
			QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) context
					.lookup("JmsQueueConnectionFactory");
			QueueConnection qCon = queueConnectionFactory
					.createQueueConnection();
			QueueSession qSession = qCon.createQueueSession(false,
					Session.AUTO_ACKNOWLEDGE);
			Queue queue = (Queue) context.lookup("queue3");
			QueueReceiver qReceiver = qSession.createReceiver(queue);
			qCon.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("JMS����û������");
		}
	}
}
