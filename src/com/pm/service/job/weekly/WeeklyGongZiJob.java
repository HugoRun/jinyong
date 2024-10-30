package com.pm.service.job.weekly;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.pm.service.mail.MailInfoService;

public class WeeklyGongZiJob implements Job
{
	Logger logger = Logger.getLogger("log.rank");

	/**
	 * ��ʱ���ϣ����н�ɫ��ʮ��ķ��ʼ�������Ӱ������
	 * ִ��ÿ��һ�賿���ĸ�JOB
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		logger.debug("��ʼ������ȡ�����ʼ�");
		sendGongZiMail();
		logger.debug("������ȡ�����ʼ�����");
	}

	/**
	 * ִ��ÿ��һ�賿���ĸ���ҷ��ʼ�
	 */
	private void sendGongZiMail() {

		MailInfoService mailInfoService = new MailInfoService();
		String mailContent = "һ��������ʱ��ﵽ210�������ϼ���ǰ������(��)�����(��)��ȡ����!";
		String title = "ϵͳ����֪ͨ";
		PartInfoDao partInfoDao = new PartInfoDao();
		List<Integer> allPPkList  = partInfoDao.getAllPPkList();
		int p_pk = 0;
		
		
//		MailInfoDao mailInfoDao = new MailInfoDao();
//		mailInfoDao.sendDataByBatch(allPPkList,title,mailContent);
		
		for ( int i = 0; i<allPPkList.size();i++) {
			p_pk = allPPkList.get(i);
			mailInfoService.sendMailBySystem(p_pk, title, mailContent);			
		}		
	}

}
