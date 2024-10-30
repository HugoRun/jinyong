package com.ls;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import com.ls.model.log.GameLogManager;

public class Test implements Runnable
{
	private int startIndex = 1;
	
	public Test( int startIndex )
	{
		this.startIndex =  startIndex;
	}
	
	public void start()
	{
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run()
	{
		for(int i=0;i<10;i++ )
		{
			try
			{
				startIndex++;
				GameLogManager.getInstance().saveLogFile(startIndex+"");
				System.out.println(startIndex);
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) 
	{
		/*Reader reader = new Reader();
		Writer writer = new Writer();
		
		reader.start();
		writer.start();*/
		
		Test test1 = new Test(0);
		Test test2 = new Test(1000);
		
		test1.start();
		test2.start();
	}

	
}
class Reader implements Runnable
{

	public void start()
	{
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run()
	{
		while( true )
		{
			try
			{
				Thread.sleep(500);
				RandomAccessFile access = new RandomAccessFile("","rw");
				FileChannel channel = access.getChannel();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
class Writer implements Runnable
{
	
	public void start()
	{
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run()
	{
		while( true )
		{
			try
			{
				Thread.sleep(1000);
				RandomAccessFile access = new RandomAccessFile("","rw");
				FileChannel channel = access.getChannel();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
