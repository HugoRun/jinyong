package com.ls.model.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.ls.ben.dao.log.GameLogDao;
import com.ls.web.service.log.DataErrorLog;

/**
 * @author ls
 * 日志处理线程
 */
public class GameLogThread implements Runnable
{
	private String fileName;
	
	public GameLogThread( String fileName )
	{
		this.fileName = fileName;
	}
	
	/**
	 * 启动线程
	 */
	public void start()
	{
		Thread thread = new Thread(this);
		thread.setName("GameLogManagerThread_"+fileName);
		thread.start();
	}
	
	/**
	 * 线程执行体
	 */
	public void run()
	{
		try
		{
			readLogFileAndIncertDB();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			DataErrorLog.debugLogic("GameLogManager.run异常：文件读取异常，文件名："+fileName);
		}
	}
	
	/**
	 * 读取日志文件并保存数据库
	 * @throws IOException 
	 */
	private void readLogFileAndIncertDB() throws IOException
	{
		File file = new File(fileName);
		if( file.exists() )
		{
			BufferedReader reader = new BufferedReader( new FileReader(file));
			GameLogDao.getInstance().incertByBufferedReader(reader);
			reader.close();
			file.delete();
		}
	}
}
