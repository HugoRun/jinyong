package com.ben.lost;

import java.util.TimerTask;

import com.pm.service.systemInfo.SystemInfoService;

public class LastFiveTimer extends TimerTask
{
	public void run()
	{
		new SystemInfoService().insertSystemInfoBySystem("�����Թ��Ĵ��Ż���5���Ӿ�Ҫ�ر��ˣ������Թ�̽�յ����ץ��ʱ��Ŷ��");
	}
}
