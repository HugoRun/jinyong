package com.ben.lost;

import java.util.TimerTask;

import com.pm.service.systemInfo.SystemInfoService;

public class EndLostTimer extends TimerTask
{
	public void run()
	{
		new SystemInfoService().insertSystemInfoBySystem("神秘迷宫的大门已经关闭，没有找到宝藏的大侠们下周四再努力吧！");
	}
}
