package com.ls.model.event;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author handan
 * ��ʱ�¼�
 */
abstract public class TimerEvent implements Delayed
{
	private long endTime;//����ʱ��
	
	public TimerEvent(long endTime)
	{
		this.endTime = endTime;
	}
	/**
	 * �¼�����
	 */
	public abstract void handle();
	
	/**
	 * ��ǰʱ��ൽʱ����೤ʱ�䣨��λ���룩
	 */
	public long getDelay(TimeUnit unit)
	{
		return endTime-System.currentTimeMillis();
	}

	public int compareTo(Delayed delay)
	{
		if( this.getDelay(TimeUnit.MILLISECONDS)>delay.getDelay(TimeUnit.MILLISECONDS))
		{
			return 1;
		}
		else if( this.getDelay(TimeUnit.MILLISECONDS)<delay.getDelay(TimeUnit.MILLISECONDS) )
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}

}
