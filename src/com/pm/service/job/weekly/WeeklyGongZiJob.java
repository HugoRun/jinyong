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
	 * 暂时不上：所有角色几十万的发邮件，严重影响性能
	 * 执行每周一凌晨零点的给JOB
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		logger.debug("开始发送领取工资邮件");
		sendGongZiMail();
		logger.debug("发送领取工资邮件结束");
	}

	/**
	 * 执行每周一凌晨零点的给玩家发邮件
	 */
	private void sendGongZiMail() {

		MailInfoService mailInfoService = new MailInfoService();
		String mailContent = "一周内在线时间达到210分钟以上即可前往伏羲(妖)或后土(巫)领取工资!";
		String title = "系统工资通知";
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
