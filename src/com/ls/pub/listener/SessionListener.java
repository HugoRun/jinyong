package com.ls.pub.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.pm.service.outLine.OutLineService;

public class SessionListener implements HttpSessionListener
{
	public void sessionCreated(HttpSessionEvent hse)
	{	

	}

	public void sessionDestroyed(HttpSessionEvent hse) {
		
		HttpSession session = hse.getSession();
		OutLineService outLineService = new OutLineService();
		
		outLineService.destorySessionClear(session);//sessionœ˙ªŸ¥¶¿Ì
	}
}
