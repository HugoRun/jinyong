package com.ben.lost;

import java.util.TimerTask;

import com.pm.service.systemInfo.SystemInfoService;

public class EndLostTimer extends TimerTask
{
	public void run()
	{
		new SystemInfoService().insertSystemInfoBySystem("�����Թ��Ĵ����Ѿ��رգ�û���ҵ����صĴ�������������Ŭ���ɣ�");
	}
}
