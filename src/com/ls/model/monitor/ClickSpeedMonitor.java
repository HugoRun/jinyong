/**
 * 
 */
package com.ls.model.monitor;

import org.apache.log4j.Logger;

import com.ben.dao.TimeShow;
import com.ls.ben.dao.system.ExceptionUserLogDao;

/**
 * ���ܣ���ҵ���ٶȼ����,�����ҵ���ٶ�
 * @author ls
 * Apr 29, 2009
 * 9:51:22 AM
 */
public class ClickSpeedMonitor
{
	protected Logger logger = Logger.getLogger("log.service");
	
	/**��������������Ĵ���*/
	private int monitor_time = 3;
	
	/**����������ʱ�����ƣ���λ���룬Ĭ��ֵ800����*/
	private int click_time_limit = 800;
	
	/**�������ʱ���쳣ʱ�����ޣ���λ���룬Ĭ��ֵ800���룬�������Сƽ�����ʱ�䣬С�ڸ�ֵΪ�쳣��ң���¼*/
	private int exception_click_time = 2000;
	
	/**��¼����ϴ��쳣�ĵ���ٶ�*/
	private long pre_exception_click_time = 0;
	/**��������ڼ��ε���ٶ��쳣*/
	private int exception_index=0;
	
	/**��ҵ������*/
	private int click_point = 0;
	
	/**ǰһ�εĵ��ʱ��*/
	private long pre_click_time = 0;
	
	/**��Сƽ�����ʱ��*/
	//private long min_avg_click_speed = Integer.MAX_VALUE;
	
	/**�쳣��ɫid*/
	//private String pPk = null;
	
	public ClickSpeedMonitor()
	{
		
	}
	
	public ClickSpeedMonitor( int click_time_limit )
	{
		this.click_time_limit = click_time_limit;
	}
	
	/**
	 * ������ұ��ε�����ʱ��
	 */
	private void setCurTime(long cur_time)
	{
		if( pre_click_time!=0 )
		{
			long time_interval = cur_time - pre_click_time;/**��һ���뱾�ε�ʱ����*/
			
			if( time_interval < exception_click_time )//����ٶ��쳣���¼
			{
				pre_exception_click_time=time_interval;
				exception_index++;
				logger.info("��"+exception_index+"�ε���ٶ��쳣,�쳣���ʱ�䣺"+pre_exception_click_time+"����");
			}
			else//����ٶ�����ʱ��ʼ������
			{
				exception_index = 0;
			}
		}
		click_point++;
		pre_click_time = cur_time;
	}
	
	/**
	 * ������Сƽ���ٶ�
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
	 * �ж���ҵ���ٶ��Ƿ���죬��������������ʱ�����ƣ��жϱ��ε���Ƿ���Ч
	 * @return    true��ʾ����
	 */
	public boolean isQuickClickSpeed(long cur_time)
	{
		boolean result = true;
		
		long cur_time_space = cur_time-pre_click_time;
		
		if( cur_time_space >= click_time_limit )
		{
			result = false;
		}
		
		logger.info("��ǰʱ������"+cur_time_space+"����");
		
		return result;
	}
	
	/**
	 * ��ʼ����ز���
	 *//*
	private void initMonitorParas()
	{
		exception_index = 0;
	}*/
	
	/**
	 * ��ص���ٶȣ��������Сƽ�����ʱ�䣬С�ڸ�ֵΪ�쳣��ң����¼
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
		
		
		if( exception_index>=monitor_time )//�����ҵ���ٶ��쳣,���¼
		{
			recordExceptionLog(uPk,pPk, cur_IP);
			hint = "���ĵ�����ٹ��������˳���Ϸ��";
			exception_index = 0;//��ʼ����ز���
		}
		
		return hint;
	}
	
	/**
	 * ��¼�쳣��־
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
