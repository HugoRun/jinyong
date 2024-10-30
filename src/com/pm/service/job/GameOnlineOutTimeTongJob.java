package com.pm.service.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ben.jms.JmsUtil;

public class GameOnlineOutTimeTongJob implements Job{   
	
	
	public void execute(JobExecutionContext context)  {
		
		//增加在线人数清楚操作
		JmsUtil.QYDAODETAIL_MAP.clear();
	}

}
