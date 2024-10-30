package com.ben.jms;

import java.util.Date;

import com.ben.shitu.model.DateUtil;

public class JmsThread2 extends Thread
{
	public void run()
	{
		QudaoMessage qm = new QudaoMessage();
		qm.setFenqu("1");
		qm.setMax_peo(100);
		qm.setNow_peo(321);
		qm.setQudao("222");
		qm.setSuper_qudao("1");
		qm.setRecord_time(DateUtil.getDate(new Date()));
		int i = 1;
		while (true)
		{
			JmsUtil.sendQudaoMessage(qm);
			System.out.println("统计  :  " + i);
			i++;
		}
	}

}
