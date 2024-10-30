package com.ls.model.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.ls.ben.dao.log.GameLogDao;
import com.ls.web.service.log.DataErrorLog;

/**
 * @author ls
 * ��־�����߳�
 */
public class GameLogThread implements Runnable
{
	private String fileName;
	
	public GameLogThread( String fileName )
	{
		this.fileName = fileName;
	}
	
	/**
	 * �����߳�
	 */
	public void start()
	{
		Thread thread = new Thread(this);
		thread.setName("GameLogManagerThread_"+fileName);
		thread.start();
	}
	
	/**
	 * �߳�ִ����
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
			DataErrorLog.debugLogic("GameLogManager.run�쳣���ļ���ȡ�쳣���ļ�����"+fileName);
		}
	}
	
	/**
	 * ��ȡ��־�ļ����������ݿ�
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
