package com.ben.jms;


import java.util.Hashtable;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ls.pub.config.GameConfig;


public class JmsReceive implements MessageListener,ServletContextListener {


	public void init() {
		try {
			Hashtable properties = new Hashtable();
			properties.put(Context.INITIAL_CONTEXT_FACTORY,
					"org.exolab.jms.jndi.InitialContextFactory");
			properties.put(Context.PROVIDER_URL, "rmi://"+GameConfig.getJmsUrl()+":1099/");
			Context context = new InitialContext(properties);
			QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) context
			.lookup("JmsQueueConnectionFactory");
			QueueConnection qCon = queueConnectionFactory.createQueueConnection();
			QueueSession qSession = qCon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = (Queue) context.lookup("queue3");
			QueueReceiver qReceiver = qSession.createReceiver(queue);
			qReceiver.setMessageListener(this);
			qCon.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onMessage(Message msg) {
		try{
			if(msg instanceof TextMessage){
				System.out.println("TextMessage : "+((TextMessage)msg).getText());
			}else if(msg instanceof ObjectMessage){
				Object clazz = ((ObjectMessage)msg).getObject();
				RoleJms rj = (RoleJms) ((ObjectMessage)msg).getObject();
					System.out.println(rj.getName());
				
			}else{
				System.out.println(msg.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		JmsReceive jr = new JmsReceive();
		jr.init();
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		init();
	}
}
