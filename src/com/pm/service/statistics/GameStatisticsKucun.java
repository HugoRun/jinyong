package com.pm.service.statistics;

import java.util.TimerTask;

import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;

public class GameStatisticsKucun extends TimerTask
{
	public GameStatisticsKucun()
	{
	}

	private static GameStatisticsKucun llService = new GameStatisticsKucun();

	public static GameStatisticsKucun getInstance()
	{
		return llService;
	}

	@Override
	public void run()
	{
		kucun();
	}

	public void kucun()
	{
		GameSystemStatisticsService gs = new GameSystemStatisticsService();
		gs.statisticsPropKuCun();
	}
}
