package com.pm.service.job;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ben.vo.friend.FriendVO;
import com.ls.web.service.rank.RankService;
import com.web.service.friend.FriendService;

public class DelLoveDear implements Job
{

	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		FriendService fs = new FriendService();
		List<FriendVO> list = fs.findLoveDear();
		if(list!=null){
			for(FriendVO fv : list){
				fs.delLoveDear(fv.getFPk());
				//ͳ����Ҫ
				new RankService().updateAdd(fv.getPPk(), "dear", -1);
			}
		}
	}

}
