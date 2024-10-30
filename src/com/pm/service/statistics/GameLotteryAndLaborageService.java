package com.pm.service.statistics;

import java.util.TimerTask;

import com.lw.service.laborage.LaborageService;
import com.lw.service.lottery.LotteryService;

public class GameLotteryAndLaborageService extends TimerTask
{
	public GameLotteryAndLaborageService()
	{
	}

	private static GameLotteryAndLaborageService llService = new GameLotteryAndLaborageService();

	public static GameLotteryAndLaborageService getInstance()
	{
		return llService;
	}

	@Override
	public void run()
	{
		delPlayMessage();
		updateLaborageMessage();
	}

	// ɾ����Ҳ�Ʊ��Ϣ
	public void delPlayMessage()
	{
		LotteryService ls = new LotteryService();
		ls.sysDel();

	}

	// ������ҵĹ�����Ϣ��
	public void updateLaborageMessage()
	{
		LaborageService ls = new LaborageService();
		ls.sysUpdatePlayerOnlineTime();
	}
}
