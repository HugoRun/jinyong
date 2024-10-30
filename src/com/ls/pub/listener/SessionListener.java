package com.ls.pub.listener;

import com.pm.service.outLine.OutLineService;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
    public void sessionCreated(HttpSessionEvent hse) {

    }

    public void sessionDestroyed(HttpSessionEvent hse) {

        HttpSession session = hse.getSession();
        OutLineService outLineService = new OutLineService();

        // session销毁处理
        outLineService.destorySessionClear(session);
    }
}
