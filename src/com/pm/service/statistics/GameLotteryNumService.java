package com.pm.service.statistics;

import java.util.TimerTask;

import com.lw.service.lottery.LotteryService;
import com.pm.service.systemInfo.SystemInfoService;

public class GameLotteryNumService extends TimerTask
{
	public GameLotteryNumService()
	{
	}

	private static GameLotteryNumService llService = new GameLotteryNumService();

	public static GameLotteryNumService getInstance()
	{
		return llService;
	}

	@Override
	public void run()
	{
		updateLotteryNum();

	}

	// ���ɲ�Ʊ
	public void updateLotteryNum()
	{
		LotteryService ls = new LotteryService();
		ls.sysLottery();
		String info = ls.sysLotteryMessage();
		if (info == null && info.equals(""))
		{
			return;
		}
		else
		{
			SystemInfoService ss = new SystemInfoService();
			ss.insertSystemInfoBySystem(info);
		}
		ls.setSysLotteryMail();// ����ҷ���ϵͳ�ʼ�
	}
}
