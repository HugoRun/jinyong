/**
 * 
 */
package com.ls.pub.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 功能:时间处理工具
 * @author 刘帅
 *
 * 10:35:18 AM
 */
public class DateUtil {

	public static Logger logger =  Logger.getLogger("log.service");
	
	/*************以秒为基数的常量*****************/
	public static long MINUTE = 60;//分钟
	public static long HOUR = 60*MINUTE;//小时
	public static long DAY = 24*HOUR;//天
	/***********************************/
	
	
	/**
	 * 字符串转换成date
	 * @param dateString
	 * @return
	 */
	public static Date strToDate(String dateString)
	{
		Date date = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = df.parse(dateString);
		} catch (Exception ex) {
			logger.debug(ex.getMessage());
		}
		return date;
	}
	/**
	 * 将日期字符转换成日期类型
	 * @param 日期字符串
	 * **/
	public static Date strToShortDate(String str) {
		Date de = null;
		try {
			if(str.length()>=10){
				str=str.substring(0,10);
			}
			String[] dateStr = str.split("-");
			int year = Integer.parseInt(dateStr[0]);
			int month = Integer.parseInt(dateStr[1]);
			int day = Integer.parseInt(dateStr[2]);
			de = new Date(year - 1900, month - 1, day);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return de;
	}
	/**
	 * 判断当前时间是否在date_ks和date_js之间,date_ks和date_js的表示形式为2008-9-5 5:18:53
	 * @param date_ks
	 * @param date_js
	 * @return
	 */
	public static boolean isShortDatenowBetweenBeginEnd(String date_ks,String date_js)
	{
		boolean result = false;
		
		if( date_ks==null || date_ks.equals("") || date_js==null || date_js.equals(""))
		{
			return result;
		}
		Date nowTime = new Date();
		Date begin = DateUtil.strToShortDate(date_ks); 
		Date end = DateUtil.strToShortDate(date_js);
		
		if( nowTime.after(begin) && nowTime.before(end) )
		{
			result = true;
		}
		
		
		return result;
	}
	/**
	 * 字符串转换成time
	 * @param dateString
	 * @return
	 */
	public static Date strToTime(String timeString)
	{
		Date date = null;
		date = DateUtil.strToDate(DateUtil.getTodayStr()+ " " +timeString);
		if( date != null)
			logger.debug(date.toString());
		
		return date;
	}
	
	/**
	 * 返回今天的字符串  格式为yyyy-MM-dd
	 * @return
	 */
	public static String getTodayStr()
	{
		String todayStr = null;
		Date date = new Date();;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			todayStr = df.format(date.getTime());
		} catch (Exception ex) {
			logger.debug(ex.getMessage());
		}
		
		return todayStr;
	}

	/**
	 * 判断当前时间是否在time_ks和time_js之间,time_ks和time_js的表示形式为5:18:53
	 * @param time_ks 
	 * @param time_js
	 * @return
	 */
	public static boolean isTimenowBetweenBeginEnd(String time_ks,String time_js)
	{
		boolean result = false;
		
		if( time_ks==null || time_ks.equals("") || time_js==null || time_js.equals(""))
		{
			return result;
		}
		Date nowTime = new Date();
		Date begin = DateUtil.strToTime(time_ks); 
		Date end = DateUtil.strToTime(time_js);
		
		if( nowTime.after(begin) && nowTime.before(end) )
		{
			result = true;
		}
		
		
		return result;
	}
	 
	/**
	 * 判断当前时间是否在date_ks和date_js之间,date_ks和date_js的表示形式为2008-9-5 5:18:53
	 * @param date_ks
	 * @param date_js
	 * @return
	 */
	public static boolean isDatenowBetweenBeginEnd(String date_ks,String date_js)
	{
		boolean result = false;
		
		if( date_ks==null || date_ks.equals("") || date_js==null || date_js.equals(""))
		{
			return result;
		}
		Date nowTime = new Date();
		Date begin = DateUtil.strToDate(date_ks); 
		Date end = DateUtil.strToDate(date_js);
		
		if( nowTime.after(begin) && nowTime.before(end) )
		{
			result = true;
		}
		
		return result;
	}
	
	/**
	 * 判断当前时间是否在星期字符串当中
	 * @param weekStr	星期字符串，形式如：1-3-4
	 */
	public static boolean isBetweenWeekStr(String weekStr)
	{
		//无星期控制
		if( weekStr==null || weekStr.trim().equals("") )
		{
			return true;
		}
		
		// 今天星期几
		 String week_day_str = getDayOfWeek()+"";
		 
		 if( weekStr.indexOf(week_day_str+"")!=-1 )
		 {
			 return true;
		 }
		 else
		 {
			 return false;
		 }
	}
	
	/**
	 * 得到当前时间是星期几
	 * 星期一至星期日为：1-7
	 */
	public static int getDayOfWeek()
	{
		Calendar now = Calendar.getInstance(); 
		//星期日为1，星期六为7
		int week_day = now.get(Calendar.DAY_OF_WEEK);

		if(week_day==1)
		{
			return 7;
		}
		else
		{
			return week_day-1;
		}
	}
	
	/**
	 * 时间从begin_time开始算起，有效时间是seconds秒，判断当前时间是否超过有效时间
	 * @param begin_time 形式 如:2008-9-11 23:58:00
	 * @param seconds   秒
	 * @return  返回true表示超过有效时间，false表示没有超过有效时间
	 */
	public static boolean isOverdue( Date begin_time,long seconds )
	{
		boolean result = true;
		if( begin_time==null )
		{
			logger.debug("开始时间参数为空");
			return true;
		}
		
		if( seconds==0 )
		{
			logger.debug("没有时间限制");
			return true;
		}
		
		//当前时间
		Calendar current_time = Calendar.getInstance();
		//当前时间之前的seconds秒的有效时间
		current_time.add(Calendar.SECOND, (int) -seconds);
		
		Date effect_time = current_time.getTime();
		
		logger.info("开始时间:"+begin_time.toString());
		logger.debug("效果时间-"+seconds+"秒,"+effect_time.toString());
		
		//判断开始时间是否在有效时间之前，如果在有效时间之前则表示已当前时间已超过开始时间seconds秒返回false
		if( begin_time.before(effect_time) )
		{
			result = true;
		}
		else
		{
			result = false;
		}
		
		return result;
	}
	
	/**
	 * 比较日期返回天数
	 * @param begin_time
	 * @param end_time
	 * @return
	 */
	public static int getDifferDays(Date begin_time,Date end_time ) 
	{
		if( begin_time ==null || end_time==null )
		{
			return 0;
		}

		return (int) ((end_time.getTime()-begin_time.getTime())/(DAY*1000));
	}
	
	/**
	 * 比较日期返回天数
	 * @param begin_time
	 * @param end_time
	 * @return
	 */
	public static int getDifferDays(String begin_time,String end_time ) 
	{
		if( begin_time ==null || end_time==null )
		{
			return 0;
		}
		
		Date begin_times = formatDate(strToDate(begin_time),"yyyy-MM-dd HH:mm:ss");
		Date end_timea = formatDate(strToDate(end_time),"yyyy-MM-dd HH:mm:ss");
		
		return (int) ((end_timea.getTime()-begin_times.getTime())/(DAY*1000));
	}
	
	/**
	 * 比较日期返回分钟
	 * @param begin_time
	 * @param end_time
	 * @return
	 */
	public static int getDifferTimes(Date begin_time,Date end_time ) 
	{
		if( begin_time ==null || end_time==null )
		{
			return 0;
		}
		
		begin_time = formatDate(begin_time,"yyyy-MM-dd HH:mm:ss");
		end_time = formatDate(end_time,"yyyy-MM-dd HH:mm:ss");
		
		int days=0;
		long a = end_time.getTime()-begin_time.getTime();
		days=(int) ((end_time.getTime()-begin_time.getTime())/(MINUTE*1000));
		return days;
	}
	
	/**
	 * 比较日期返回分钟
	 * @param begin_time
	 * @param end_time
	 * @return
	 */
	public static int getDifferTimes(String begin_time,String end_time ) 
	{
		if( begin_time ==null || end_time==null )
		{
			return 0;
		}
		
		Date begin_times = formatDate(strToDate(begin_time),"yyyy-MM-dd HH:mm:ss");
		Date end_timea = formatDate(strToDate(end_time),"yyyy-MM-dd HH:mm:ss");
		
		int days=0;
		long a = end_timea.getTime()-begin_times.getTime();
		days=(int) ((end_timea.getTime()-begin_times.getTime())/(MINUTE*1000));
		return days;
	}
	
	/**
	 * 根据pattern格式化date
	 * @param date
	 * @param pattern  形式如"yyyy-MM-dd"
	 * @return
	 */
	public static Date formatDate( Date date,String pattern )
	{
		if( date==null || pattern==null )
		{
			return date;
		}
		Date new_date = null;
		
		SimpleDateFormat f = new SimpleDateFormat(pattern);
		
		String date_str = f.format(date);
		
		try
		{
			new_date = f.parse(date_str);
		}
		catch (java.text.ParseException e)
		{
			e.printStackTrace();
		}
			
		return new_date;
	}
	
	
	/**
	 * 根据pattern格式化date
	 * @param date
	 * @param pattern  形式如"yyyy-MM-dd"
	 * @return
	 */
	public static String formatDateToStr( Date date,String pattern )
	{
		if( date==null || pattern==null )
		{
			return "";
		}
		
		SimpleDateFormat f = new SimpleDateFormat(pattern);
		
		String date_str = f.format(date);
		
		
		return date_str;
	}
	
	
	/**
	 * 得到当前时间与compare_time所差天数
	 * @param compare_time    
	 * @return
	 */
	public static int getDifferDaysToToday(Date compare_time ) 
	{
		if( compare_time ==null )
		{
			return 0;
		}
		//当前时间
	   Date today = new Date();
	   return getDifferDays(compare_time,today);
	}
	
	
	

	
	/**
	 * 判断当前时间是否与compare_time在同一天
	 * @param compare_time 
	 * @return
	 */
	public static boolean isSameDay( Date compare_time )
	{
		if( compare_time==null )
		{
			logger.debug("时间参数为空");
			return false;
		}
		boolean isSameDay = false;
		//当前时间
		Calendar current_time = Calendar.getInstance();
		int current_year = current_time.get(Calendar.YEAR);
		int current_day_of_year = current_time.get(Calendar.DAY_OF_YEAR);
		
		Calendar compare_datetime = Calendar.getInstance();
		compare_datetime.setTime(compare_time);
		int compare_year = compare_datetime.get(Calendar.YEAR);
		int compare__day_of_year = compare_datetime.get(Calendar.DAY_OF_YEAR);
		
		if( current_year==compare_year && current_day_of_year==compare__day_of_year )
		{
			isSameDay = true;
		}
		return isSameDay;
	}
	
	/**
	 * 当前时间与时间compare_time做差值
	 * @param compare_time
	 *  @param time_difference    时间
	 * @return				返回差值（分钟）
	 */
	public static int  getTimeDifference(Date compare_time,long time_difference )
	{
		if( compare_time==null )
		{
			return 0;
		}
		//当前时间
		Date now_time = new Date();
		long result = time_difference - (now_time.getTime()-compare_time.getTime());

		long temp = result%60000; 
		
		if( temp==0 )
		{
			return (int)(result/60000);
		}
		else
		{
			return (int)(result/60000)+1;
		}
		
	}
	
	/**
	 * 得到当请时间字符串
	 * @param args
	 */
	public static String getCurrentTimeStr()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH点mm分");
		return formatter.format(new Date());
	}
	
	/**
	 * 得到游戏主页面的当请时间字符串
	 * @param args
	 */
	public static String getMainPageCurTimeStr()
	{
		Date cur_date_time = new Date();
		
		String time_str = "";
		String date_str = "";
		
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");// 对时间进行格式化
		
		time_str = formatter.format(cur_date_time);
		
		formatter.applyPattern("yyyy-MM-dd");// 对日期进行格式化
		
		date_str = formatter.format(cur_date_time);
		
		
		return time_str+"<br/>"+date_str;
	}
	
	/**
	 * 判断当前时间是否是有效时间,同时满足以下条件
	 * 1.判断当前时间是否在time_ks和time_js之间,time_ks和time_js的表示形式为5:18:53
	 * 2.判断当前时间是否在date_ks和date_js之间,date_ks和date_js的表示形式为2008-09-05 05:18:53
	 * 3.判断当前时间是否在星期字符串中
	 * @param time_begin
	 * @param time_end
	 * @param day_begin
	 * @param day_end
	 * @param weekStr       星期字符串中
	 * @return
	 */
	public static boolean isEffectTime(String time_begin,String time_end,String day_begin,String day_end,String weekStr )
	{
		boolean result = false;
		
		if( StringUtils.isEmpty(time_begin) && StringUtils.isEmpty(time_end) &&  StringUtils.isEmpty(day_begin) &&  StringUtils.isEmpty(day_end) &&  StringUtils.isEmpty(weekStr) )
		{
			//时间控制都为空，没有时间限制
			return true;
		}
		else if( isTimenowBetweenBeginEnd(day_begin, day_end) )
		{
			//判断当前时间是否在time_ks和time_js之间,time_ks和time_js的表示形式为5:18:53
			return true;
		}
		else if( isDatenowBetweenBeginEnd(time_begin, time_end) )
		{
			//判断当前时间是否在date_ks和date_js之间,date_ks和date_js的表示形式为2008-9-5 5:18:53
			return true;
		}
		//根据星期控制
		else if( isBetweenWeekStr(weekStr))
		{
			return true;
		}
		
		return result;
	}
	
	/**
	 * 返回距overTime时间还剩多长时间的字符串
	 * @param overTime		超时时间
	 * @return		返回null表示已超时
	 */
	public static String returnTimeStr(Date overTime)
	{
		if( overTime==null )
		{
			return null;
		}
		long over_time = overTime.getTime();
		long cur_time = System.currentTimeMillis();
		
		long sub_time = over_time - cur_time;
		
		if( sub_time<0 )
		{
			return null;
		}
		return returnTimeStr((int)(sub_time/(1000*60)));
	}
	
	/**
	 * 返回时间字符串
	 * @param time 分钟
	 * @return
	 */
	public static String returnTimeStr(int itimesmes){
		int time=itimesmes * 60;   
        int day,hour,minute,second;
        day = time / 86400;
        time = time - 86400*day;
        hour=time/3600;   //int型整除   
        time=time-3600*hour;//除去整小时数后剩下的时间   
        minute=time/60;   
        time=time-60*minute;   
        second=time;
        String dayhint = "";
        if(day > 0 ){
        	dayhint = day+"天";
        }
        String hourhint = "";
        if(hour>0){
        	hourhint = hour+"小时";
        }
        String minutehint = "";
        if(minute > 0){
        	minutehint = minute + "分钟";
        }
        String secondhint = "";
        if(second > 0){
        	secondhint = second+"秒";
        }
		return dayhint + hourhint + minutehint + secondhint;
	}
	
	/**
	 * 传入秒返回天数
	 * @param time 秒
	 * @return
	 */
	public static String returnStr(double times){
		String result = "";
		int time = (int)times ;   
        int day,hour,minute,second;
        day = time / 86400;
        time = time - 86400*day;
        hour=time/3600;   //int型整除   
        time=time-3600*hour;//除去整小时数后剩下的时间   
        minute=time/60;   
        time=time-60*minute;   
        second=time;
        String dayhint = "";
        if(day > 0 ){
        	dayhint = day+"天";
        }
        String hourhint = "";
        if(hour>0){
        	hourhint = hour+"小时";
        }
        String minutehint = "";
        if(minute > 0){
        	minutehint = minute + "分钟";
        }
        
        result = dayhint + hourhint + minutehint;
        
        if( result.equals("") && second!=0 )
        {
        	return second+"秒";
        }
        
		return  result;
	}
	
	public static void main(String[] args) {   
		 Calendar now = Calendar.getInstance(); 
		 int week_day = now.get(Calendar.DAY_OF_WEEK);
		System.out.println( week_day);
	} 
}
