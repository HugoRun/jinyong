//package com.ben.jms;
//
//import java.io.Serializable;
//import javax.jms.JMSException;
//import javax.jms.ObjectMessage;
//import javax.jms.Queue;
//import javax.jms.QueueSender;
//import javax.jms.TextMessage;
//
//public class JmsSend {
//	
//	private static QueueSender queueSender;
//	static{
//		try {
//			
//			Queue queue = (Queue) JmsConstant.getInstance().context
//					.lookup("queue3");
//			queueSender = JmsConstant.getInstance().queueSession
//			.createSender(queue);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	
//	public static void logString(String mess) {
//          try {
//        	  TextMessage message = JmsConstant.getInstance().queueSession.createTextMessage();
//			message.setText(mess);
//			queueSender.send(message);
//		} catch (JMSException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static void logObject(Object clazz) {
//        try {
//      	  ObjectMessage message = JmsConstant.getInstance().queueSession
//			.createObjectMessage();
//			message.setObject((Serializable) clazz);
//			queueSender.send(message);
//			System.out.println("·¢ËÍ³É¹¦");
//		} catch (JMSException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static void log(Object o){
//		if(o instanceof String){
//			logString((String)o);
//		}else{
//			logObject(o);
//		}
//	}
//	
//	public static void main(String[] args)
//	{
//		for(int i = 0;i<5;i++){
//		RoleJms rj = new RoleJms();
//		rj.setName("ggfgggg");
//		rj.setSuper_qudao("1");
//		rj.setQudao("2");
//		rj.setFenqu("1");
//		rj.setLevel(10);
//		rj.setUserid("º«");
//		rj.setCaozuo(1);
//		log(rj);
//		}
//	}
//}
