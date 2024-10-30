package com.ls.web.service.log;

import org.apache.log4j.Logger;

import com.ben.vo.task.TaskVO;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.cache.staticcache.task.TaskCache;
import com.ls.ben.vo.goods.prop.PropVO;

/**
 * @author ls
 * 数据错误日志
 */
public class DataErrorLog
{
	private static Logger logger = Logger.getLogger("log.data");//数据做错日志
	
	/**
	 * 调试逻辑错误
	 * @param errer_log
	 */
	public static void debugLogic( String errer_log)
	{
		logger.error(errer_log);
	}
	
	/**
	 * 调试数据错误
	 * @param errer_log
	 */
	public static void debugData( String errer_log)
	{
		logger.error(errer_log);
	}
	
	/**
	 * 记录道具数据错误
	 * @param errer_log
	 */
	public static void prop( int prop_id,String errer_log)
	{
		PropVO prop = PropCache.getPropById(prop_id);
		if( prop!=null )
		{
			logger.error("道具数据错误（道具id="+prop_id+";道具名字="+prop.getPropName()+"）;错误信息："+errer_log);
		}
	}
	
	/**
	 * 记录任务数据错误
	 * @param errer_log
	 */
	public static void task( int id,String errer_log)
	{
		TaskVO tesk = TaskCache.getById(id+"");
		if( tesk!=null )
		{
			logger.error("任务数据错误（任务id="+id+";任务名字="+tesk.getTName()+"）;错误信息："+errer_log);
		}
	}
	
	/**
	 * 记录任务数据错误
	 * @param errer_log
	 */
	public static void task( String errer_log)
	{
		debugData("任务数据错误："+errer_log);
	}
}
