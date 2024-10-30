package com.pm.service.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.pm.service.statistics.GameDataRecordService;

public class GameDataRecordJob implements Job{   
	Logger logger =  Logger.getLogger("log.quartz");
	public void execute(JobExecutionContext context)  {
		
		GameDataRecordService gameDataRecordService = new GameDataRecordService();
		//�����ռ�ÿ����ҵȼ�
		gameDataRecordService.recordEveryDayPlayerGrade();
		//�����ռ�������ҵȼ�
		gameDataRecordService.recordOnlinePlayerGrade();
		//�����ռ���Ĭ��ҵȼ�
		gameDataRecordService.recordSilverPlayerGrade();
		//�����ռ��������ʱ��.
		gameDataRecordService.recordPlayerOnlineTime();
		//�����ռ����������Ϣ
		gameDataRecordService.recordPlayerInfoOverview();
		//����ϵͳ��Ϣÿ�ն�ʱ���͹���
		gameDataRecordService.sendSystemInfoByTime();
	}

}
