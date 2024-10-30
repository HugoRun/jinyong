/**
 * 
 */
package com.ls.model.monitor;

import org.apache.log4j.Logger;

import com.ben.dao.TimeShow;
import com.ls.ben.dao.system.ExceptionUserLogDao;

/**
 * 功能：玩家点击速度监控器,监控玩家点击速度
 * @author ls
 * Apr 29, 2009
 * 9:51:22 AM
 */
public class ClickSpeedMonitor
{
	protected Logger logger = Logger.getLogger("log.service");
	
	/**监控玩家连续点击的次数*/
	private int monitor_time = 3;
	
	/**玩家连续点击时间限制，单位毫秒，默认值800毫秒*/
	private int click_time_limit = 800;
	
	/**连续点击时间异常时间上限，单位毫秒，默认值800毫秒，当玩家最小平均点击时间，小于该值为异常玩家，记录*/
	private int exception_click_time = 2000;
	
	/**记录玩家上次异常的点击速度*/
	private long pre_exception_click_time = 0;
	/**玩家连续第几次点击速度异常*/
	private int exception_index=0;
	
	/**玩家点击次数*/
	private int click_point = 0;
	
	/**前一次的点击时间*/
	private long pre_click_time = 0;
	
	/**最小平均点击时间*/
	//private long min_avg_click_speed = Integer.MAX_VALUE;
	
	/**异常角色id*/
	//private String pPk = null;
	
	public ClickSpeedMonitor()
	{
		
	}
	
	public ClickSpeedMonitor( int click_time_limit )
	{
		this.click_time_limit = click_time_limit;
	}
	
	/**
	 * 设置玩家本次的请求时间
	 */
	private void setCurTime(long cur_time)
	{
		if( pre_click_time!=0 )
		{
			long time_interval = cur_time - pre_click_time;/**上一次与本次的时间间隔*/
			
			if( time_interval < exception_click_time )//点击速度异常则记录
			{
				pre_exception_click_time=time_interval;
				exception_index++;
				logger.info("第"+exception_index+"次点击速度异常,异常点击时间："+pre_exception_click_time+"毫秒");
			}
			else//点击速度正常时初始化参数
			{
				exception_index = 0;
			}
		}
		click_point++;
		pre_click_time = cur_time;
	}
	
	/**
	 * 设置最小平均速度
	 * @return
	 *//*
	private void setMinAvgClickSpeed()
	{
		if( click_time[0]==0 )
		{
			return;
		}
		
		long cur_avg_click_speed = (click_time[0]+click_time[1]+click_time[2])/monitor_time;
		
		logger.info("1:"+click_time[0]+";2:"+click_time[1]+";3:"+click_time[2]);
		
		if( cur_avg_click_speed < min_avg_click_speed )
		{
			min_avg_click_speed = cur_avg_click_speed;
		}
	}*/
	
	
	/**
	 * 判断玩家点击速度是否过快，根据玩家连续点击时间限制，判断本次点击是否有效
	 * @return    true表示过快
	 */
	public boolean isQuickClickSpeed(long cur_time)
	{
		boolean result = true;
		
		long cur_time_space = cur_time-pre_click_time;
		
		if( cur_time_space >= click_time_limit )
		{
			result = false;
		}
		
		logger.info("当前时间间隔："+cur_time_space+"毫秒");
		
		return result;
	}
	
	/**
	 * 初始化监控参数
	 *//*
	private void initMonitorParas()
	{
		exception_index = 0;
	}*/
	
	/**
	 * 监控点击速度，当玩家最小平均点击时间，小于该值为异常玩家，则记录
	 * @param session
	 */
	public String monitor(String uPk,String pPk,String cur_IP,long cur_time )
	{
		String hint = null;
		if (pPk==null||cur_IP==null )
		{
			return hint;
		}
		
		setCurTime(cur_time);
		
		
		if( exception_index>=monitor_time )//如果玩家点击速度异常,则记录
		{
			recordExceptionLog(uPk,pPk, cur_IP);
			hint = "您的点击速速过快让您退出游戏啦";
			exception_index = 0;//初始化监控参数
		}
		
		return hint;
	}
	
	/**
	 * 记录异常日志
	 */
	private void recordExceptionLog(String uPk,String pPk,String exception_ip)
	{
		TimeShow timeShow = new TimeShow();
		String time = timeShow.endTime(1);
		ExceptionUserLogDao exceptionUserLogDao = new ExceptionUserLogDao();
		if( !exceptionUserLogDao.isHave(uPk,pPk, exception_ip))
		{
			if(exception_ip.indexOf("211") != -1){
				
			}else{
				exceptionUserLogDao.insert(uPk,pPk, exception_ip,pre_exception_click_time+"",time);
			}
		}
	}
}
