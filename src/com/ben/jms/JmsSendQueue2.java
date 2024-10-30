package com.ben.jms;

import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueSender;
import javax.jms.TextMessage;

public class JmsSendQueue2 {
	//统计专用QUEUE2
	private static QueueSender queueSender;
	static{
		try {
			
			Queue queue = (Queue) JmsConstant.getInstance().context
					.lookup("queue2");
			queueSender = JmsConstant.getInstance().queueSession
			.createSender(queue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void reload()
	{
		try
		{
            System.out.println("重新加载JSM服务");
			Queue queue = (Queue) JmsConstant.reload().context
					.lookup("queue2");
			queueSender = JmsConstant.getInstance().queueSession
					.createSender(queue);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void logString(String mess)
	{
		try
		{
			TextMessage message = JmsConstant.getInstance().queueSession
					.createTextMessage();
			message.setText(mess);
			queueSender.send(message);
		}
		catch (JMSException e)
		{
			reload();
			try
			{
				TextMessage message = JmsConstant.getInstance().queueSession
						.createTextMessage();
				message.setText(mess);
				queueSender.send(message);
			}
			catch (JMSException e1)
			{
				e1.printStackTrace();
			}
		}
	}

	public static void logObject(Object clazz) 
	{
		try
		{
			ObjectMessage message = JmsConstant.getInstance().queueSession
					.createObjectMessage();
			message.setObject((Serializable) clazz);
			queueSender.send(message);
			System.out.println("发送成功");
		}
		catch (JMSException e)
		{
			reload();
			try
			{
				ObjectMessage message = JmsConstant.getInstance().queueSession
				.createObjectMessage();
				 message.setObject((Serializable) clazz);
			        queueSender.send(message);
			        System.out.println("发送成功");
			}
			catch (JMSException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	       
		}
	}
	
	public static void log(Object o){
		if(o instanceof String){
			logString((String)o);
		}else{
			logObject(o);
		}
	}
	
	public static void main(String[] args)
	{
		for(int i = 0;i<5;i++){
		RoleJms rj = new RoleJms();
		rj.setName("ggfgggg");
		rj.setSuper_qudao("1");
		rj.setQudao("2");
		rj.setFenqu("1");
		rj.setLevel(10);
		rj.setUserid("韩");
		rj.setCaozuo(1);
		log(rj);
		}
	}
}
