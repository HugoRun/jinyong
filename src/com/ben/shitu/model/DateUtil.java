package com.ben.shitu.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil
{
	private static SimpleDateFormat sdf = new SimpleDateFormat();

	/**
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateSecondFormat()
	{
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(new Date(), pattern);
	}

	public static synchronized String getDateFormat(Date date, String pattern)
	{
		synchronized (sdf)
		{
			String str = null;
			sdf.applyPattern(pattern);
			str = sdf.format(date);
			return str;
		}
	}
	
	public static synchronized String getDate(Date date){
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(date, pattern);
	}

	public static synchronized Date getTimeFormat(String time, String pattern)
	{
		synchronized (sdf)
		{
			Date date = null;
			sdf.applyPattern(pattern);
			try
			{
				date = sdf.parse(time);
			}
			catch (ParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return date;
		}
	}
	
	public static synchronized Date getTimeFormat(String time){
		Date date = null;
		String pattern = "yyyy-MM-dd HH:mm:ss";
		sdf.applyPattern(pattern);
		try
		{
			date = sdf.parse(time);
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}


	public static boolean checkTime(String time)
	{
		if(time==null||"".equals(time.trim())){
			return true;
		}
		String pattern = "yyyy-MM-dd HH:mm:ss";
		Date date = getTimeFormat(time, pattern);
		if(date==null){
			return true;
		}
		Date today = new Date();
		int day1 = date.getDate();
		int day2 = today.getDate();
		int month1 = date.getMonth();
		int month2 = today.getMonth();
		System.out.println(day1+":"+day2+":"+month1+":"+month2);
		return month1==month2?day1==day2?false:true:true;
	}
	
	public static int delTime(String time){
		if(time==null||"".equals(time.trim())){
			return 0;
		}
		String pattern = "yyyy-MM-dd HH:mm:ss";
		Date date = getTimeFormat(time, pattern);
		if(date==null){
			return 0;
		}
		Date today = new Date();
		return Integer.parseInt((today.getTime()-date.getTime())/1000/60+"");
	}
	
	public static boolean check(String time,int min){
		try{
		if(time==null||"".equals(time.trim())){   
			return true;
		}
		String pattern = "yyyy-MM-dd HH:mm:ss";
		Date date = getTimeFormat(time, pattern);
		if(date==null){
			return true;
		}
		Date today = new Date();
		date.setMinutes(date.getMinutes()+min);
		return today.after(date);
		}catch(Exception e){
			return true;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static boolean checkMin(Date date,int min){
		try{
		if(date==null){   
			return true;
		}
		Date today = new Date();
		date.setMinutes(date.getMinutes()+min);
		return today.after(date);
		}catch(Exception e){
			return true;
		}
	}
	
	public static boolean checkSecond(String time,int second){
		try{
		if(time==null||"".equals(time.trim())){   
			return true;
		}
		String pattern = "yyyy-MM-dd HH:mm:ss";
		Date date = getTimeFormat(time, pattern);
		if(date==null){
			return true;
		}
		Date today = new Date();
		date.setSeconds(date.getSeconds()+second);
		return today.after(date);
		}catch(Exception e){
			return true;
		}
	}
	
	
	
	public static boolean check(Date date){
		if(date==null){
			return true;
		}
		else{
			Date date1 = new Date();
			date1.setDate(date1.getDate()-7);
			String pattern = "yyyy-MM-dd HH:mm:ss";
			System.out.println(getDateFormat(date, pattern));
			System.out.println(getDateFormat(date1,pattern));
			return date1.after(date);
		}
	}
	
	//�����ʱ���Ƿ��ڵ�ǰʱ��֮ǰ
	public static boolean checkNow(Date date){
		if(date==null){
			return true;
		}
		return date.before(new Date());
	}
	
	
	
	public static int getWeek(){
		Calendar c = Calendar.getInstance();
		return c.get(c.DAY_OF_WEEK)-1;
	}
	
	public static boolean isFreeGuaji(int weekDay,int beg,int end,Date now){
		if(beg<end&&weekDay==getWeek()){
//			����
			return isBetween(beg, end,now,true);
		}else if(beg>end&&weekDay+1==getWeek()){
//			����
			return isBetween(beg, end,now,false);
		}
		return false;
	}
	
	
	public static boolean isBetween(int beg,int end,Date now ,boolean isOneDay){
		Date date1 = new Date();
		Date date2 = new Date();
		if(!isOneDay){
			date1.setDate(date1.getDate()-1);
		}
		date1.setHours(beg);
		date2.setHours(end);
		return now.after(date1)?now.before(date2)?true:false:false;
	}
	
	/*public static String getDes(Active active){
		String time = null;
		String pattern = "MM��dd��HH:mm";
		sdf.applyPattern(pattern);
		if(!checkNow(active.getRound1_endtime())){
			time = "������̨ʱ��Ϊ"+sdf.format(active.getRound1_starttime())+"��"+sdf.format(active.getRound1_endtime());
		}else if(!checkNow(active.getRound2_endtime())){
			time = "������̨ʱ��Ϊ"+sdf.format(active.getRound2_starttime())+"��"+sdf.format(active.getRound2_endtime());
		}else if(!checkNow(active.getRound3_endtime())){
			time = "������̨ʱ��Ϊ"+sdf.format(active.getRound3_starttime())+"��"+sdf.format(active.getRound3_endtime());
		}else{
			time = "��̨�Ѿ�����";
		}
		return time;
	}*/
	
	/*//��ȡ��ǰ��̨Ϊ�ڼ���
	public static int getRound(Active active){
		if(active==null){
			return -100;
		}
		int round = 0;
		boolean round11 = checkNow(active.getRound1_starttime());//��һ�ֿ�ʼʱ���Ƿ��ѵ���T��F��
		boolean round12 = checkNow(active.getRound1_endtime());//��һ�ֽ���ʱ���Ƿ��ѵ���T��F��
		boolean round21 = checkNow(active.getRound2_starttime());//�ڶ��ֿ�ʼʱ���Ƿ��ѵ���T��F��
		boolean round22 = checkNow(active.getRound2_endtime());//�ڶ��ֽ���ʱ���Ƿ��ѵ���T��F��
		boolean round31 = checkNow(active.getRound3_starttime());//�����ֿ�ʼʱ���Ƿ��ѵ���T��F��
		boolean round32 = checkNow(active.getRound3_endtime());//�����ֽ���ʱ���Ƿ��ѵ���T��F��
		if(!round11){
			round = -1;//��һ�ֻ�û��ʼ
		}else if(!round12){
			round = 1;//��һ�ֽ�����
		}else if(round12&&!round21){
			round = -2;//��һ�ֽ������ڶ��ֻ�û��ʼ
		}else if(round21&&!round22){
			round = 2;//�ڶ��ֽ�����
		}else if(round22&&!round31){
			round = -3;//�ڶ��ֽ����������ֻ�û��ʼ
		}else if(round31&&!round32){
			round = 3;//�����ֽ�����
		}else {
			round = -4;//�������Ѿ�����
		}
		return round;
	}
	*/
	
	//��ȡʱ���
	public static String getTimeCha(String date,String detail){
		Date date2 = new Date();
		Date date1 = getDate(date);
		if(date1==null){
			return "�����ˣ�����ϵ����Ա";
		}
		if(date1.before(date2)){
			return "�Ѿ���ʼ";
		}else{
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			long cha = time1 - time2;
			int day = (int)(cha/1000/60/60/24);
			int hour = (int)(cha/1000/60/60%24);
			int min = (int)(cha/1000/60%60);
			int sec = (int)(cha/1000%60);
//			return MessageFormat.format("�������ʼ����{0}��{1}ʱ{2}��{3}��.",day,hour,min,sec);
			return detail+(day>0?(day+"��"):"")+(hour>0?(hour+"ʱ"):"")+(min>0?(min+"��"):"")+(sec>0?(sec+"��"):".");
		}
	}
	
	//��ȡʱ���
	public static String getTimeCha(String date,String detail,int overtime){
		Date date2 = new Date();
		Date date1 = getDate(date);
		if(date1==null){
			return "�����ˣ�����ϵ����Ա";
		}
		if(date1.before(date2)){
			return "�Ѿ���ʼ";
		}else{
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			long cha = time1 - time2-overtime*60*1000;
			if(cha<0){
				return "��̨�Ѿ���ʼ��";
			}else{
			int day = (int)(cha/1000/60/60/24);
			int hour = (int)(cha/1000/60/60%24);
			int min = (int)(cha/1000/60%60);
			int sec = (int)(cha/1000%60);
//			return MessageFormat.format("�������ʼ����{0}��{1}ʱ{2}��{3}��.",day,hour,min,sec);
			return detail+(day>0?(day+"��"):"")+(hour>0?(hour+"ʱ"):"")+(min>0?(min+"��"):"")+(sec>0?(sec+"��."):".");
			}
		}
	}
	
	//��̨�Ƿ��Ѿ���ʼ
	public static boolean isstart(String baomingstarttime){
		if(baomingstarttime==null||"".equals(baomingstarttime.trim())){
			return true;
		}else{
			Date date2 = new Date();
			Date date1 = getDate(baomingstarttime);
			if(date1==null){
				return true;
			}
			if(date1.before(date2)){
				return true;
			}else{
				return false;
			}
		}
	}
	
	
	//��̨�Ƿ��Ѿ���ʼ
	public static boolean isstart1(String baomingstarttime,int overtime){
		if(baomingstarttime==null||"".equals(baomingstarttime.trim())){
			return true;
		}else{
			Date date2 = new Date();
			Date date1 = getDate(baomingstarttime);
			if(date1==null){
				return true;
			}
			date1.setMinutes(date1.getMinutes()-overtime);
			if(date1.before(date2)){
				return true;
			}else{
				return false;
			}
		}
	}
	
	//��ȡʱ��
	public static String getDelTIme(String time,int overtime){
		if(time==null||"".equals(time.trim())){
			return "������";
		}else{
			Date date1 = getDate(time);
			if(date1==null){
				return "������";
			}else{
				date1.setMinutes(date1.getMinutes()-overtime);
				return "����"+getDate(date1)+"������̨";
			}
		}
	}
	
	//��ȡʱ��
	public static boolean getDate(String time,int overtime){
		if(time==null||"".equals(time.trim())){
			return false;
		}
		Date date = null;
		try{
		String pattern = "yyyy-MM-dd HH:mm:ss";
		String pattern1 = "yyyy-MM-dd";
		sdf.applyPattern(pattern1);
		String pat = sdf.format(new Date());
		sdf.applyPattern(pattern);
		date = sdf.parse(pat+" "+time);
		date.setMinutes(date.getMinutes()-overtime);
		}catch(Exception e){
			System.out.println("ʱ���ʽ����");
			return false;
		}
		return new Date().after(date);
	}
	
	public static Date getDate(String s) {
		if(s==null||"".equals(s.trim())){
			return null;
		}
		try{
		String pattern = "yyyy-MM-dd HH:mm:ss";
		String pattern1 = "yyyy-MM-dd";
		sdf.applyPattern(pattern1);
		String pat = sdf.format(new Date());
		sdf.applyPattern(pattern);
		return sdf.parse(pat+" "+s);
		}catch(Exception e){
			System.out.println("ʱ���ʽ����");
			return null;
		}
	}
	
	public static void main(String[] args)
	{
		addSecond(15);
	}
	
	public static Date addSecond(int second){
		Date date = new Date();
		date.setSeconds(date.getSeconds()+second);
		return date;
	}
	
	public static int subMin(Date date){
		if(date==null){
			return 0;
		}else{
			long t1 = date.getTime();
			long t2 = new Date().getTime();
			long sub = t2-t1;
			return (int)sub/1000/60;
		}
	}
}
