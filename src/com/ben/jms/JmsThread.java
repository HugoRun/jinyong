package com.ben.jms;

public class JmsThread extends Thread
{
	public void run()
	{
		int i = 1;
         while(true){
        	JmsUtil.sendJmsRole("1102","222", "jy00004", "fdsa", 5);
        	System.out.println("角色  :  "+i);
        	i++;
         }
	}
	
	
	
	public static void main(String[] args)
	{
		JmsThread jt = new JmsThread();
		jt.start();
		JmsThread1 jt1 = new JmsThread1();
		jt1.start();
		JmsThread2 jt2 = new JmsThread2();
		jt2.start();
	}
}
