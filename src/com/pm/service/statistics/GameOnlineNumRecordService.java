package com.pm.service.statistics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import com.ls.web.service.login.LoginService;
import com.pm.dao.statistics.StatisticsDao;

public class GameOnlineNumRecordService  extends TimerTask {

	private GameOnlineNumRecordService(){}
	
	private static GameOnlineNumRecordService recordService = new GameOnlineNumRecordService();
	
	public static GameOnlineNumRecordService getInstance() {
		return recordService;
	}
	
	@Override
	public void run()
	{
		StatisticsDao statDao = new StatisticsDao();
		//在线玩家人数统计
		int nowOnlineNum = LoginService.getOnlineNum();;
		
		int nowNum = statDao.getNowHourNum(getNowHours(),getToday());
		
		if(nowOnlineNum < 0) {
			nowOnlineNum = 0;
		}
		
		if ( nowOnlineNum >= nowNum) {
			statDao.insertPlayerOnlineNumRecord(nowOnlineNum, getNowHours(), getToday());
		}
	}
	
	/**
	 * 获得当前时间的小时格式化表示
	 * @return
	 */
	private static String getNowHours()
	{
		Date dt = new Date();
		DateFormat sf = new SimpleDateFormat("HH"); 
		String dtstr = sf.format(dt);
		return dtstr;
	}
	
	/**
	 * 获得每天时间的格式化表示
	 * @return
	 */
	private static String getToday()
	{
		Date dt = new Date();
		DateFormat sf = new SimpleDateFormat("yyyy-MM-dd"); 
		String dtstr = sf.format(dt);
		return dtstr;
	}

}
