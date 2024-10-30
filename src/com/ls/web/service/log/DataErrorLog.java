package com.ls.web.service.log;

import org.apache.log4j.Logger;

import com.ben.vo.task.TaskVO;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.cache.staticcache.task.TaskCache;
import com.ls.ben.vo.goods.prop.PropVO;

/**
 * @author ls
 * ���ݴ�����־
 */
public class DataErrorLog
{
	private static Logger logger = Logger.getLogger("log.data");//����������־
	
	/**
	 * �����߼�����
	 * @param errer_log
	 */
	public static void debugLogic( String errer_log)
	{
		logger.error(errer_log);
	}
	
	/**
	 * �������ݴ���
	 * @param errer_log
	 */
	public static void debugData( String errer_log)
	{
		logger.error(errer_log);
	}
	
	/**
	 * ��¼�������ݴ���
	 * @param errer_log
	 */
	public static void prop( int prop_id,String errer_log)
	{
		PropVO prop = PropCache.getPropById(prop_id);
		if( prop!=null )
		{
			logger.error("�������ݴ��󣨵���id="+prop_id+";��������="+prop.getPropName()+"��;������Ϣ��"+errer_log);
		}
	}
	
	/**
	 * ��¼�������ݴ���
	 * @param errer_log
	 */
	public static void task( int id,String errer_log)
	{
		TaskVO tesk = TaskCache.getById(id+"");
		if( tesk!=null )
		{
			logger.error("�������ݴ�������id="+id+";��������="+tesk.getTName()+"��;������Ϣ��"+errer_log);
		}
	}
	
	/**
	 * ��¼�������ݴ���
	 * @param errer_log
	 */
	public static void task( String errer_log)
	{
		debugData("�������ݴ���"+errer_log);
	}
}
