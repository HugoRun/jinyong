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
 * ����:ʱ�䴦����
 * @author ��˧
 *
 * 10:35:18 AM
 */
public class DateUtil {

	public static Logger logger =  Logger.getLogger("log.service");
	
	/*************����Ϊ�����ĳ���*****************/
	public static long MINUTE = 60;//����
	public static long HOUR = 60*MINUTE;//Сʱ
	public static long DAY = 24*HOUR;//��
	/***********************************/
	
	
	/**
	 * �ַ���ת����date
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
	 * �������ַ�ת������������
	 * @param �����ַ���
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
	 * �жϵ�ǰʱ���Ƿ���date_ks��date_js֮��,date_ks��date_js�ı�ʾ��ʽΪ2008-9-5 5:18:53
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
	 * �ַ���ת����time
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
	 * ���ؽ�����ַ���  ��ʽΪyyyy-MM-dd
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
	 * �жϵ�ǰʱ���Ƿ���time_ks��time_js֮��,time_ks��time_js�ı�ʾ��ʽΪ5:18:53
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
	 * �жϵ�ǰʱ���Ƿ���date_ks��date_js֮��,date_ks��date_js�ı�ʾ��ʽΪ2008-9-5 5:18:53
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
	 * �жϵ�ǰʱ���Ƿ��������ַ�������
	 * @param weekStr	�����ַ�������ʽ�磺1-3-4
	 */
	public static boolean isBetweenWeekStr(String weekStr)
	{
		//�����ڿ���
		if( weekStr==null || weekStr.trim().equals("") )
		{
			return true;
		}
		
		// �������ڼ�
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
	 * �õ���ǰʱ�������ڼ�
	 * ����һ��������Ϊ��1-7
	 */
	public static int getDayOfWeek()
	{
		Calendar now = Calendar.getInstance(); 
		//������Ϊ1��������Ϊ7
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
	 * ʱ���begin_time��ʼ������Чʱ����seconds�룬�жϵ�ǰʱ���Ƿ񳬹���Чʱ��
	 * @param begin_time ��ʽ ��:2008-9-11 23:58:00
	 * @param seconds   ��
	 * @return  ����true��ʾ������Чʱ�䣬false��ʾû�г�����Чʱ��
	 */
	public static boolean isOverdue( Date begin_time,long seconds )
	{
		boolean result = true;
		if( begin_time==null )
		{
			logger.debug("��ʼʱ�����Ϊ��");
			return true;
		}
		
		if( seconds==0 )
		{
			logger.debug("û��ʱ������");
			return true;
		}
		
		//��ǰʱ��
		Calendar current_time = Calendar.getInstance();
		//��ǰʱ��֮ǰ��seconds�����Чʱ��
		current_time.add(Calendar.SECOND, (int) -seconds);
		
		Date effect_time = current_time.getTime();
		
		logger.info("��ʼʱ��:"+begin_time.toString());
		logger.debug("Ч��ʱ��-"+seconds+"��,"+effect_time.toString());
		
		//�жϿ�ʼʱ���Ƿ�����Чʱ��֮ǰ���������Чʱ��֮ǰ���ʾ�ѵ�ǰʱ���ѳ�����ʼʱ��seconds�뷵��false
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
	 * �Ƚ����ڷ�������
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
	 * �Ƚ����ڷ�������
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
	 * �Ƚ����ڷ��ط���
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
	 * �Ƚ����ڷ��ط���
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
	 * ����pattern��ʽ��date
	 * @param date
	 * @param pattern  ��ʽ��"yyyy-MM-dd"
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
	 * ����pattern��ʽ��date
	 * @param date
	 * @param pattern  ��ʽ��"yyyy-MM-dd"
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
	 * �õ���ǰʱ����compare_time��������
	 * @param compare_time    
	 * @return
	 */
	public static int getDifferDaysToToday(Date compare_time ) 
	{
		if( compare_time ==null )
		{
			return 0;
		}
		//��ǰʱ��
	   Date today = new Date();
	   return getDifferDays(compare_time,today);
	}
	
	
	

	
	/**
	 * �жϵ�ǰʱ���Ƿ���compare_time��ͬһ��
	 * @param compare_time 
	 * @return
	 */
	public static boolean isSameDay( Date compare_time )
	{
		if( compare_time==null )
		{
			logger.debug("ʱ�����Ϊ��");
			return false;
		}
		boolean isSameDay = false;
		//��ǰʱ��
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
	 * ��ǰʱ����ʱ��compare_time����ֵ
	 * @param compare_time
	 *  @param time_difference    ʱ��
	 * @return				���ز�ֵ�����ӣ�
	 */
	public static int  getTimeDifference(Date compare_time,long time_difference )
	{
		if( compare_time==null )
		{
			return 0;
		}
		//��ǰʱ��
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
	 * �õ�����ʱ���ַ���
	 * @param args
	 */
	public static String getCurrentTimeStr()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy��MM��dd��HH��mm��");
		return formatter.format(new Date());
	}
	
	/**
	 * �õ���Ϸ��ҳ��ĵ���ʱ���ַ���
	 * @param args
	 */
	public static String getMainPageCurTimeStr()
	{
		Date cur_date_time = new Date();
		
		String time_str = "";
		String date_str = "";
		
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");// ��ʱ����и�ʽ��
		
		time_str = formatter.format(cur_date_time);
		
		formatter.applyPattern("yyyy-MM-dd");// �����ڽ��и�ʽ��
		
		date_str = formatter.format(cur_date_time);
		
		
		return time_str+"<br/>"+date_str;
	}
	
	/**
	 * �жϵ�ǰʱ���Ƿ�����Чʱ��,ͬʱ������������
	 * 1.�жϵ�ǰʱ���Ƿ���time_ks��time_js֮��,time_ks��time_js�ı�ʾ��ʽΪ5:18:53
	 * 2.�жϵ�ǰʱ���Ƿ���date_ks��date_js֮��,date_ks��date_js�ı�ʾ��ʽΪ2008-09-05 05:18:53
	 * 3.�жϵ�ǰʱ���Ƿ��������ַ�����
	 * @param time_begin
	 * @param time_end
	 * @param day_begin
	 * @param day_end
	 * @param weekStr       �����ַ�����
	 * @return
	 */
	public static boolean isEffectTime(String time_begin,String time_end,String day_begin,String day_end,String weekStr )
	{
		boolean result = false;
		
		if( StringUtils.isEmpty(time_begin) && StringUtils.isEmpty(time_end) &&  StringUtils.isEmpty(day_begin) &&  StringUtils.isEmpty(day_end) &&  StringUtils.isEmpty(weekStr) )
		{
			//ʱ����ƶ�Ϊ�գ�û��ʱ������
			return true;
		}
		else if( isTimenowBetweenBeginEnd(day_begin, day_end) )
		{
			//�жϵ�ǰʱ���Ƿ���time_ks��time_js֮��,time_ks��time_js�ı�ʾ��ʽΪ5:18:53
			return true;
		}
		else if( isDatenowBetweenBeginEnd(time_begin, time_end) )
		{
			//�жϵ�ǰʱ���Ƿ���date_ks��date_js֮��,date_ks��date_js�ı�ʾ��ʽΪ2008-9-5 5:18:53
			return true;
		}
		//�������ڿ���
		else if( isBetweenWeekStr(weekStr))
		{
			return true;
		}
		
		return result;
	}
	
	/**
	 * ���ؾ�overTimeʱ�仹ʣ�೤ʱ����ַ���
	 * @param overTime		��ʱʱ��
	 * @return		����null��ʾ�ѳ�ʱ
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
	 * ����ʱ���ַ���
	 * @param time ����
	 * @return
	 */
	public static String returnTimeStr(int itimesmes){
		int time=itimesmes * 60;   
        int day,hour,minute,second;
        day = time / 86400;
        time = time - 86400*day;
        hour=time/3600;   //int������   
        time=time-3600*hour;//��ȥ��Сʱ����ʣ�µ�ʱ��   
        minute=time/60;   
        time=time-60*minute;   
        second=time;
        String dayhint = "";
        if(day > 0 ){
        	dayhint = day+"��";
        }
        String hourhint = "";
        if(hour>0){
        	hourhint = hour+"Сʱ";
        }
        String minutehint = "";
        if(minute > 0){
        	minutehint = minute + "����";
        }
        String secondhint = "";
        if(second > 0){
        	secondhint = second+"��";
        }
		return dayhint + hourhint + minutehint + secondhint;
	}
	
	/**
	 * �����뷵������
	 * @param time ��
	 * @return
	 */
	public static String returnStr(double times){
		String result = "";
		int time = (int)times ;   
        int day,hour,minute,second;
        day = time / 86400;
        time = time - 86400*day;
        hour=time/3600;   //int������   
        time=time-3600*hour;//��ȥ��Сʱ����ʣ�µ�ʱ��   
        minute=time/60;   
        time=time-60*minute;   
        second=time;
        String dayhint = "";
        if(day > 0 ){
        	dayhint = day+"��";
        }
        String hourhint = "";
        if(hour>0){
        	hourhint = hour+"Сʱ";
        }
        String minutehint = "";
        if(minute > 0){
        	minutehint = minute + "����";
        }
        
        result = dayhint + hourhint + minutehint;
        
        if( result.equals("") && second!=0 )
        {
        	return second+"��";
        }
        
		return  result;
	}
	
	public static void main(String[] args) {   
		 Calendar now = Calendar.getInstance(); 
		 int week_day = now.get(Calendar.DAY_OF_WEEK);
		System.out.println( week_day);
	} 
}
