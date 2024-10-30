package com.pm.service.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.pm.service.statistics.GameDataRecordService;

public class GameDataRecordJob implements Job{   
	Logger logger =  Logger.getLogger("log.quartz");
	public void execute(JobExecutionContext context)  {
		
		GameDataRecordService gameDataRecordService = new GameDataRecordService();
		//运行收集每日玩家等级
		gameDataRecordService.recordEveryDayPlayerGrade();
		//运行收集上线玩家等级
		gameDataRecordService.recordOnlinePlayerGrade();
		//运行收集沉默玩家等级
		gameDataRecordService.recordSilverPlayerGrade();
		//运行收集玩家上线时间.
		gameDataRecordService.recordPlayerOnlineTime();
		//运行收集玩家总览信息
		gameDataRecordService.recordPlayerInfoOverview();
		//启动系统消息每日定时发送功能
		gameDataRecordService.sendSystemInfoByTime();
	}

}
