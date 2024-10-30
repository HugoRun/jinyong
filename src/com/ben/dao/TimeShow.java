package com.ben.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ��ƾ� ���� ȡ����ǰʱ����ĳ��ʱ�� 1:00:44 PM
 */ 
public class TimeShow
{

	/**
	 * times �ֵ�λ
	 */
	public String time(int times)
	{
		String timeStr = "";
		Calendar cal = Calendar.getInstance();
		String today = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		String hourMinute = new SimpleDateFormat("HHmm").format(cal.getTime());
		int time2 = Integer.parseInt(hourMinute);
		time2 = time2 + times;

		int hour = time2 / 100;
		int minute = time2 % 100;

		if (minute > 60)
		{
			hour = hour + 1;
			minute = minute % 60;
		}
		String minuteStr = "" + minute;
		if (minute < 10)
		{
			minuteStr = "0" + minute;
		}

		String hourStr = "" + hour;
		if (hour > 23)
		{

			hour = hour % 24;
		}
		if (hour < 10)
		{
			hourStr = "0" + hour;
		} 
		timeStr = today + " " + hourStr + ":" + minuteStr + ":" + "00"; 
		return timeStr; 
	}

	/**
	 * ��ȡ��ǰʱ��֮ǰ�����ӵĵ�ʱ��
	 * */
	@SuppressWarnings("deprecation")
	public String Minutes(int minutes){
		String twoMinutes = "";
        Date mydate = new Date();
        ////System.out.println("####mydate111:==" + mydate);
        mydate.setMinutes(mydate.getMinutes() - minutes );
        twoMinutes = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mydate); 
        return twoMinutes;
	}
	
	/**
	 * ��ȡ����ǰʱ��N���Ӻ��ʱ�� ���ظ�ʽΪyyyy-MM-dd HH:mm:ss
	 * @param time
	 * @return
	 */
	public String endTime(int time){
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.MINUTE,time); 
		Date d = new Date();
		d.setTime(cal.getTimeInMillis());
		// ����ʱ��
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String endTime = formatter.format(d);// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
		return endTime;
	}
	
	public static void  main(String[] s){
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.MINUTE,43200); 
		Date d = new Date();
		d.setTime(cal.getTimeInMillis());
		// ����ʱ��
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String endTime = formatter.format(d);// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
		System.out.println(endTime);
	}
}
