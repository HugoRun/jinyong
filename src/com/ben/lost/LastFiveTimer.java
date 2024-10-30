package com.ben.lost;

import java.util.TimerTask;

import com.pm.service.systemInfo.SystemInfoService;

public class LastFiveTimer extends TimerTask
{
	public void run()
	{
		new SystemInfoService().insertSystemInfoBySystem("神秘迷宫的大门还有5分钟就要关闭了，还在迷宫探险的玩家抓紧时间哦！");
	}
}
