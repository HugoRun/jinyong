package com.pm.service.statistics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import com.lw.service.player.PlayerStatisticsService;
import com.pm.dao.statistics.StatisticsDao;
import com.pm.service.systemInfo.SystemInfoService;

public class GameDataRecordService extends TimerTask
{

	public GameDataRecordService()
	{
	}

	private static GameDataRecordService recordService = new GameDataRecordService();

	public static GameDataRecordService getInstance()
	{
		return recordService;
	}

	StatisticsDao statDao = new StatisticsDao();
	PlayerStatisticsService pss = new PlayerStatisticsService();

	// private static String today = getToday();

	/**
	 * �������ÿ������һ��, ��ʱһ������һ��.
	 */
	@Override
	public void run()
	{
		// �����ռ�ÿ����ҵȼ�
		// recordEveryDayPlayerGrade();
		// �����ռ�������ҵȼ�
		// recordOnlinePlayerGrade();
		// �����ռ���Ĭ��ҵȼ�
		// recordSilverPlayerGrade();
		// �����ռ��������ʱ��.
		// recordPlayerOnlineTime();
		// �����ռ����������Ϣ
		// recordPlayerInfoOverview();
		// ����ϵͳ��Ϣÿ�ն�ʱ���͹���
		// sendSystemInfoByTime();
	}

	/**
	 * ��ʱ����ϵͳ��Ϣ
	 */
	public void sendSystemInfoByTime()
	{
		SystemInfoService infoService = new SystemInfoService();
		infoService.sendTimeSystemInfo();

	}

	/**
	 * �����ռ����������Ϣ�� ���ռ���ϢΪ������ʱǰ24Сʱ������ʱ����Ϸ����
	 */
	public void recordPlayerInfoOverview()
	{
		// ��ע������
		int allRegeistNum = statDao.getAllRegistNum();
		// �ܽ�ɫ��
		int allUserNum = statDao.getAllUserNum();
		// ����ע�����
		int todayRegistNum = statDao.getTodayRegistNum(getToday());
		// �����������
		int todayOnlineNum = pss.getOnlinePassport(getToday());
		// ���ջ�Ծ�û�
		int todayActiveNum = pss.getPlayerOnlineActivity();
		// ƽ������ʱ��,��λ���룩
		long AllPlayerOnlineTime = pss.getOnlineTime(getToday());
		;
		int avgPlaerOnlineTime = 0;
		if (todayOnlineNum != 0)
		{
			avgPlaerOnlineTime = (int) (AllPlayerOnlineTime / todayOnlineNum);
		}
		// ƽ�����ߵȼ�
		double avgPlayerOnlineGrade = pss.getOnlineAvgGrade(getToday());
		// ƽ����������
		double avgPlayerOnlineNum = pss.getOnlinePlayerAvg(getToday());

		// ����ע����ҳ���9��������
		int grade9today = statDao.getGrade9today(9, getToday());

		statDao.insertPlayerInfoOverview(allRegeistNum, allUserNum,
				todayRegistNum, todayOnlineNum, todayActiveNum,
				avgPlaerOnlineTime, avgPlayerOnlineGrade, avgPlayerOnlineNum,
				grade9today, getToday());
		// �������Ҫ������
		deleteUnneed();
	}

	/**
	 * �������Ҫ������
	 */
	public void deleteUnneed()
	{
		statDao.deleteUnneedRecordData();

	}

	/**
	 * �����ռ��������ʱ��
	 */
	public void recordPlayerOnlineTime()
	{
		int time10min = statDao.getOnlinePlayerTime(0, 10);
		int time30min = statDao.getOnlinePlayerTime(10, 30);
		int time60min = statDao.getOnlinePlayerTime(30, 60);
		int time120min = statDao.getOnlinePlayerTime(60, 120);
		int time120minUp = statDao.getOnlinePlayerTime(120, 1000);
		statDao.insertIntoOnlineTime(time10min, time30min, time60min,
				time120min, time120minUp, getToday());

	}

	/**
	 * �����ռ���Ĭ��ҵȼ�
	 */
	public void recordSilverPlayerGrade()
	{
		int grade1to9 = statDao.getSilverPlayerGrade(0, 10);
		int grade10to19 = statDao.getSilverPlayerGrade(9, 20);
		int grade20to29 = statDao.getSilverPlayerGrade(19, 30);
		int grade30to39 = statDao.getSilverPlayerGrade(29, 40);
		int grade40to49 = statDao.getSilverPlayerGrade(39, 50);
		int grade50to59 = statDao.getSilverPlayerGrade(49, 60);
		int grade60 = statDao.getSilverPlayerGrade(59, 61);
		statDao.insertIntoSilverPlayerGrade(grade1to9, grade10to19,
				grade20to29, grade30to39, grade40to49, grade50to59, grade60,
				getToday());

	}

	/**
	 * �����ռ�������ҵȼ�
	 */
	public void recordOnlinePlayerGrade()
	{
		int grade1to9 = statDao.getOnlinePlayerGrade(0, 10);
		int grade10to19 = statDao.getOnlinePlayerGrade(9, 20);
		int grade20to29 = statDao.getOnlinePlayerGrade(19, 30);
		int grade30to39 = statDao.getOnlinePlayerGrade(29, 40);
		int grade40to49 = statDao.getOnlinePlayerGrade(39, 50);
		int grade50to59 = statDao.getOnlinePlayerGrade(49, 60);
		int grade60 = statDao.getOnlinePlayerGrade(59, 61);
		statDao.insertIntoOnlinePlayerGrade(grade1to9, grade10to19,
				grade20to29, grade30to39, grade40to49, grade50to59, grade60,
				getToday());

	}

	/**
	 * ���ÿ��ʱ��ĸ�ʽ����ʾ
	 * 
	 * @return
	 */
	public static String getToday()
	{
		Date dt = new Date();
		// DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date yestoday = new Date(dt.getTime() - 1000 * 60 * 60 * 24);
		String dtstr = sf.format(yestoday);
		return dtstr;
	}

	/**
	 * ��õ�ǰʱ���Сʱ��ʽ����ʾ
	 * 
	 * @return
	 */
	public static String getNowHours()
	{
		Date dt = new Date();
		// DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat sf = new SimpleDateFormat("HH");
		String dtstr = sf.format(dt);
		return dtstr;
	}

	// �����ռ�ÿ����ҵȼ�,������������ݿ����Ա�������ѯ
	public void recordEveryDayPlayerGrade()
	{
		int grade1 = statDao.getAllUserGradeInfo(0, 2);
		int grade2to9 = statDao.getAllUserGradeInfo(1, 10);
		int grade10to19 = statDao.getAllUserGradeInfo(9, 20);
		int grade20to29 = statDao.getAllUserGradeInfo(19, 30);
		int grade30to39 = statDao.getAllUserGradeInfo(29, 40);
		int grade40to49 = statDao.getAllUserGradeInfo(39, 50);
		int grade50to59 = statDao.getAllUserGradeInfo(49, 60);
		int grade60 = statDao.getAllUserGradeInfo(59, 61);
		int avg_grade = statDao.getAvgGrade(10);
		statDao.insertEveryDayPlayerGrade(grade1, grade2to9, grade10to19,
				grade20to29, grade30to39, grade40to49, grade50to59, grade60,
				avg_grade, getToday());

	}

}