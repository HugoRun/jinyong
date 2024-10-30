package com.ben.jms;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ls.pub.config.GameConfig;

public class JmsConstant {
	public static JmsConstant instance;
	public static QueueSession queueSession;
	public static QueueConnection queueConnection;
	public static Context context;
	public static JmsConstant getInstance() {
		if (instance == null) {
			try {
				instance = new JmsConstant();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	public JmsConstant() throws NamingException, JMSException {
		Hashtable properties = new Hashtable();
		properties.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.exolab.jms.jndi.InitialContextFactory");
		properties.put(Context.PROVIDER_URL, "rmi://"+GameConfig.getJmsUrl()+":1099/");
		context = new InitialContext(properties);
		QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) context
				.lookup("JmsQueueConnectionFactory");
		queueConnection = queueConnectionFactory.createQueueConnection();
		queueSession = queueConnection.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
	}
	
	public static JmsConstant reload(){
		try {
			instance = new JmsConstant();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return instance;
	}
}
