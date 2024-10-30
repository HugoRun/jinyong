package com.ben.shitu.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
    private static final SimpleDateFormat sdf = new SimpleDateFormat();

    /**
     *
     * @return String
     */
    public static synchronized String getDateSecondFormat() {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        return getDateFormat(new Date(), pattern);
    }

    public static synchronized String getDateFormat(Date date, String pattern) {
        synchronized (sdf) {
            String str = null;
            sdf.applyPattern(pattern);
            str = sdf.format(date);
            return str;
        }
    }

    public static synchronized String getDate(Date date) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        return getDateFormat(date, pattern);
    }

    public static synchronized Date getTimeFormat(String time, String pattern) {
        synchronized (sdf) {
            Date date = null;
            sdf.applyPattern(pattern);
            try {
                date = sdf.parse(time);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return date;
        }
    }

    public static synchronized Date getTimeFormat(String time) {
        Date date = null;
        String pattern = "yyyy-MM-dd HH:mm:ss";
        sdf.applyPattern(pattern);
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }


    public static boolean checkTime(String time) {
        if (time == null || "".equals(time.trim())) {
            return true;
        }
        String pattern = "yyyy-MM-dd HH:mm:ss";
        Date date = getTimeFormat(time, pattern);
        if (date == null) {
            return true;
        }
        Date today = new Date();
        int day1 = date.getDate();
        int day2 = today.getDate();
        int month1 = date.getMonth();
        int month2 = today.getMonth();
        System.out.println(day1 + ":" + day2 + ":" + month1 + ":" + month2);
        return month1 != month2 || day1 != day2;
    }

    public static int delTime(String time) {
        if (time == null || "".equals(time.trim())) {
            return 0;
        }
        String pattern = "yyyy-MM-dd HH:mm:ss";
        Date date = getTimeFormat(time, pattern);
        if (date == null) {
            return 0;
        }
        Date today = new Date();
        return Integer.parseInt((today.getTime() - date.getTime()) / 1000 / 60 + "");
    }

    public static boolean check(String time, int min) {
        try {
            if (time == null || "".equals(time.trim())) {
                return true;
            }
            String pattern = "yyyy-MM-dd HH:mm:ss";
            Date date = getTimeFormat(time, pattern);
            if (date == null) {
                return true;
            }
            Date today = new Date();
            date.setMinutes(date.getMinutes() + min);
            return today.after(date);
        } catch (Exception e) {
            return true;
        }
    }

    @SuppressWarnings("deprecation")
    public static boolean checkMin(Date date, int min) {
        try {
            if (date == null) {
                return true;
            }
            Date today = new Date();
            date.setMinutes(date.getMinutes() + min);
            return today.after(date);
        } catch (Exception e) {
            return true;
        }
    }

    public static boolean checkSecond(String time, int second) {
        try {
            if (time == null || "".equals(time.trim())) {
                return true;
            }
            String pattern = "yyyy-MM-dd HH:mm:ss";
            Date date = getTimeFormat(time, pattern);
            if (date == null) {
                return true;
            }
            Date today = new Date();
            date.setSeconds(date.getSeconds() + second);
            return today.after(date);
        } catch (Exception e) {
            return true;
        }
    }


    public static boolean check(Date date) {
        if (date == null) {
            return true;
        } else {
            Date date1 = new Date();
            date1.setDate(date1.getDate() - 7);
            String pattern = "yyyy-MM-dd HH:mm:ss";
            System.out.println(getDateFormat(date, pattern));
            System.out.println(getDateFormat(date1, pattern));
            return date1.after(date);
        }
    }

    //传入的时间是否在当前时间之前
    public static boolean checkNow(Date date) {
        if (date == null) {
            return true;
        }
        return date.before(new Date());
    }


    public static int getWeek() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static boolean isFreeGuaji(int weekDay, int beg, int end, Date now) {
        if (beg < end && weekDay == getWeek()) {
//			当天
            return isBetween(beg, end, now, true);
        } else if (beg > end && weekDay + 1 == getWeek()) {
//			隔天
            return isBetween(beg, end, now, false);
        }
        return false;
    }


    public static boolean isBetween(int beg, int end, Date now, boolean isOneDay) {
        Date date1 = new Date();
        Date date2 = new Date();
        if (!isOneDay) {
            date1.setDate(date1.getDate() - 1);
        }
        date1.setHours(beg);
        date2.setHours(end);
        return now.after(date1) && now.before(date2);
    }
	
	/*public static String getDes(Active active){
		String time = null;
		String pattern = "MM月dd日HH:mm";
		sdf.applyPattern(pattern);
		if(!checkNow(active.getRound1_endtime())){
			time = "本场擂台时间为"+sdf.format(active.getRound1_starttime())+"—"+sdf.format(active.getRound1_endtime());
		}else if(!checkNow(active.getRound2_endtime())){
			time = "本场擂台时间为"+sdf.format(active.getRound2_starttime())+"—"+sdf.format(active.getRound2_endtime());
		}else if(!checkNow(active.getRound3_endtime())){
			time = "本场擂台时间为"+sdf.format(active.getRound3_starttime())+"—"+sdf.format(active.getRound3_endtime());
		}else{
			time = "擂台已经结束";
		}
		return time;
	}*/
	
	/*//获取当前擂台为第几轮
	public static int getRound(Active active){
		if(active==null){
			return -100;
		}
		int round = 0;
		boolean round11 = checkNow(active.getRound1_starttime());//第一轮开始时间是否已到，T否F是
		boolean round12 = checkNow(active.getRound1_endtime());//第一轮结束时间是否已到，T否F是
		boolean round21 = checkNow(active.getRound2_starttime());//第二轮开始时间是否已到，T否F是
		boolean round22 = checkNow(active.getRound2_endtime());//第二轮结束时间是否已到，T否F是
		boolean round31 = checkNow(active.getRound3_starttime());//第三轮开始时间是否已到，T否F是
		boolean round32 = checkNow(active.getRound3_endtime());//第三轮结束时间是否已到，T否F是
		if(!round11){
			round = -1;//第一轮还没开始
		}else if(!round12){
			round = 1;//第一轮进行中
		}else if(round12&&!round21){
			round = -2;//第一轮结束，第二轮还没开始
		}else if(round21&&!round22){
			round = 2;//第二轮进行中
		}else if(round22&&!round31){
			round = -3;//第二轮结束，第三轮还没开始
		}else if(round31&&!round32){
			round = 3;//第三轮进行中
		}else {
			round = -4;//第三轮已经结束
		}
		return round;
	}
	*/

    //获取时间差
    public static String getTimeCha(String date, String detail) {
        Date date2 = new Date();
        Date date1 = getDate(date);
        if (date1 == null) {
            return "出错了，请联系管理员";
        }
        if (date1.before(date2)) {
            return "已经开始";
        } else {
            long time1 = date1.getTime();
            long time2 = date2.getTime();
            long cha = time1 - time2;
            int day = (int) (cha / 1000 / 60 / 60 / 24);
            int hour = (int) (cha / 1000 / 60 / 60 % 24);
            int min = (int) (cha / 1000 / 60 % 60);
            int sec = (int) (cha / 1000 % 60);
//			return MessageFormat.format("离比赛开始还有{0}天{1}时{2}分{3}秒.",day,hour,min,sec);
            return detail + (day > 0 ? (day + "天") : "") + (hour > 0 ? (hour + "时") : "") + (min > 0 ? (min + "分") : "") + (sec > 0 ? (sec + "秒") : ".");
        }
    }

    //获取时间差
    public static String getTimeCha(String date, String detail, int overtime) {
        Date date2 = new Date();
        Date date1 = getDate(date);
        if (date1 == null) {
            return "出错了，请联系管理员";
        }
        if (date1.before(date2)) {
            return "已经开始";
        } else {
            long time1 = date1.getTime();
            long time2 = date2.getTime();
            long cha = time1 - time2 - (long) overtime * 60 * 1000;
            if (cha < 0) {
                return "擂台已经开始了";
            } else {
                int day = (int) (cha / 1000 / 60 / 60 / 24);
                int hour = (int) (cha / 1000 / 60 / 60 % 24);
                int min = (int) (cha / 1000 / 60 % 60);
                int sec = (int) (cha / 1000 % 60);
//			return MessageFormat.format("离比赛开始还有{0}天{1}时{2}分{3}秒.",day,hour,min,sec);
                return detail + (day > 0 ? (day + "天") : "") + (hour > 0 ? (hour + "时") : "") + (min > 0 ? (min + "分") : "") + (sec > 0 ? (sec + "秒.") : ".");
            }
        }
    }

    //擂台是否已经开始
    public static boolean isstart(String baomingstarttime) {
        if (baomingstarttime == null || "".equals(baomingstarttime.trim())) {
            return true;
        } else {
            Date date2 = new Date();
            Date date1 = getDate(baomingstarttime);
            if (date1 == null) {
                return true;
            }
            return date1.before(date2);
        }
    }


    //擂台是否已经开始
    public static boolean isstart1(String baomingstarttime, int overtime) {
        if (baomingstarttime == null || "".equals(baomingstarttime.trim())) {
            return true;
        } else {
            Date date2 = new Date();
            Date date1 = getDate(baomingstarttime);
            if (date1 == null) {
                return true;
            }
            date1.setMinutes(date1.getMinutes() - overtime);
            return date1.before(date2);
        }
    }

    //获取时间
    public static String getDelTIme(String time, int overtime) {
        if (time == null || "".equals(time.trim())) {
            return "出错了";
        } else {
            Date date1 = getDate(time);
            if (date1 == null) {
                return "出错了";
            } else {
                date1.setMinutes(date1.getMinutes() - overtime);
                return "请于" + getDate(date1) + "进入擂台";
            }
        }
    }

    //获取时间
    public static boolean getDate(String time, int overtime) {
        if (time == null || "".equals(time.trim())) {
            return false;
        }
        Date date = null;
        try {
            String pattern = "yyyy-MM-dd HH:mm:ss";
            String pattern1 = "yyyy-MM-dd";
            sdf.applyPattern(pattern1);
            String pat = sdf.format(new Date());
            sdf.applyPattern(pattern);
            date = sdf.parse(pat + " " + time);
            date.setMinutes(date.getMinutes() - overtime);
        } catch (Exception e) {
            System.out.println("时间格式错误");
            return false;
        }
        return new Date().after(date);
    }

    public static Date getDate(String s) {
        if (s == null || "".equals(s.trim())) {
            return null;
        }
        try {
            String pattern = "yyyy-MM-dd HH:mm:ss";
            String pattern1 = "yyyy-MM-dd";
            sdf.applyPattern(pattern1);
            String pat = sdf.format(new Date());
            sdf.applyPattern(pattern);
            return sdf.parse(pat + " " + s);
        } catch (Exception e) {
            System.out.println("时间格式错误");
            return null;
        }
    }

    public static void main(String[] args) {
        addSecond(15);
    }

    public static Date addSecond(int second) {
        Date date = new Date();
        date.setSeconds(date.getSeconds() + second);
        return date;
    }

    public static int subMin(Date date) {
        if (date == null) {
            return 0;
        } else {
            long t1 = date.getTime();
            long t2 = new Date().getTime();
            long sub = t2 - t1;
            return (int) sub / 1000 / 60;
        }
    }
}
