package com.pub.Listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author 侯浩军
 * 4:16:23 PM
 */
public class OnlineCounterListener implements HttpSessionListener
{
	public void sessionCreated(HttpSessionEvent hse)
	{	
		/*HttpSession session = hse.getSession();
		//System.out.println("系统创建新SESSION ----- "+session.getId());
*/	}

	public void sessionDestroyed(HttpSessionEvent hse) {
		/*
		HttpSession session = hse.getSession();
		OutLineService outLineService = new OutLineService();
		
//		outLineService.destorySessionClear(session);//session销毁处理
*/	}
}
