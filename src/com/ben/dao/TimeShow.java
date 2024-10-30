package com.ben.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 侯浩军 功能 取除当前时候后的某个时间 1:00:44 PM
 */ 
public class TimeShow
{

	/**
	 * times 分单位
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
	 * 获取当前时间之前几分钟的的时间
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
	 * 获取到当前时间N分钟后的时间 返回格式为yyyy-MM-dd HH:mm:ss
	 * @param time
	 * @return
	 */
	public String endTime(int time){
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.MINUTE,time); 
		Date d = new Date();
		d.setTime(cal.getTimeInMillis());
		// 创建时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String endTime = formatter.format(d);// 从页面得到当前时间,并且赋给一个变量
		return endTime;
	}
	
	public static void  main(String[] s){
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.MINUTE,43200); 
		Date d = new Date();
		d.setTime(cal.getTimeInMillis());
		// 创建时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String endTime = formatter.format(d);// 从页面得到当前时间,并且赋给一个变量
		System.out.println(endTime);
	}
}
