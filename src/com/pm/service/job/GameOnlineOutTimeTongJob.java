package com.pm.service.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ben.jms.JmsUtil;

public class GameOnlineOutTimeTongJob implements Job{   
	
	
	public void execute(JobExecutionContext context)  {
		
		//�������������������
		JmsUtil.QYDAODETAIL_MAP.clear();
	}

}
