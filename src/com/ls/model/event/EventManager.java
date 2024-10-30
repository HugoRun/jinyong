package com.ls.model.event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;

/**
 * @author handan
 * ��ʱ�¼�������
 */
public class EventManager
{
	private static int process_event_num;//ÿ�δ����¼�������
	
	 private DelayQueue<TimerEvent> timer_events = new DelayQueue<TimerEvent>();
	 
	 public void add(TimerEvent event)
	 {
		 if( event!=null )
		 {
			 timer_events.add(event);
		 }
	 }
	 
	 public void remove(TimerEvent event)
	 {
		 if( event!=null )
		 {
			 timer_events.remove(event);
		 }
	 }
	 
	 /**
	  * �����Ƿ����¼���ִ���ԣ����������
	  */
	 public void listener()
	 {
		 TimerEvent over_time_event = timer_events.poll();
		 if( over_time_event!=null )
		 {
			 over_time_event.handle();
		 }
	 }
	 
	 private List<TimerEvent> getOverTimeEventList()
	 {
		 List<TimerEvent> list = new ArrayList<TimerEvent>();
		 timer_events.drainTo(list, process_event_num);
		 return list;
	 }
}
