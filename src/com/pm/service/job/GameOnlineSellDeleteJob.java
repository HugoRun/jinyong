package com.pm.service.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ben.dao.TimeShow;
import com.ben.dao.sellinfo.SellInfoDAO;

public class GameOnlineSellDeleteJob implements Job{   
	
	
	public void execute(JobExecutionContext context)  {
		TimeShow timeShow = new TimeShow();
		 //这里执行删除超过5分钟的数据
		SellInfoDAO dao = new SellInfoDAO();
		dao.deleteSellPetTime(timeShow.endTime(5));
		dao.deleteSellPropTime(timeShow.endTime(5));
	}

}
