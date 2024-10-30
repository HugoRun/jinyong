package com.ls.ben.cache.staticcache.task;

import java.util.HashMap;

import com.ben.dao.task.TaskDAO;
import com.ben.vo.task.TaskVO;
import com.ls.ben.cache.CacheBase;
import com.ls.web.service.log.DataErrorLog;

public class TaskCache extends CacheBase
{
	public static String TASK_BY_ID = "task_by_id";
	
	/**
	 * 通过id得到task信息
	 * @param task_id
	 * @return
	 */
	public static TaskVO getById( String task_id )
	{
		TaskVO task = null;
		HashMap result = getElementValue(STATIC_CACHE_NAME, TASK_BY_ID);
		task = (TaskVO)result.get(task_id);
		if( task==null )
		{
			TaskDAO taskDAO = new TaskDAO();
			task = taskDAO.getTaskView(task_id);
			if( task!=null )
			{
				result.put(task.getTId()+"",task);
			}
			else
			{
				DataErrorLog.debugData("无该任务：任务id="+task_id);
			}
		}
		
		return task;
	}
	/**
	 * 重新加在一条任务
	 */
	public void reloadOneTask(TaskVO taskVO){
		if(taskVO != null){
			HashMap result = getElementValue(STATIC_CACHE_NAME, TASK_BY_ID);
			result.remove(taskVO.getTId()+"");
			result.put(taskVO.getTId()+"", taskVO);
		}
	}
}
