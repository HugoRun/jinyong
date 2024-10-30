/**
 * 
 */
package com.ls.model.property.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ben.dao.task.UTaskDAO;
import com.ben.vo.task.UTaskVO;

/**
 * 功能：角色身上的所有为完成任务
 * 
 * @author hhj
 */
public class CurTaskList
{

	private HashMap<String, CurTaskInfo> task_list_by_id = null;
	private HashMap<String, CurTaskInfo> task_list_by_zu = null;

	private UTaskDAO dao = null;

	public CurTaskList(int p_pk)
	{
		dao = new UTaskDAO();
		HashMap[] result = dao.getPlayerTask(p_pk + "");
		task_list_by_id = result[0];
		task_list_by_zu = result[1];
	}

	/**
	 * 清除所接的所有任务
	 */
	public void clear()
	{
		task_list_by_id.clear();
		task_list_by_zu.clear();
	}
	
	/**
	 * 将HASHMAP结果集转化为LIST
	 * 
	 * @return
	 */
	public List getCurTaskList()
	{
		List list = new ArrayList(task_list_by_id.values());
		return list;
	}

	/**
	 * 将HASHMAP结果集转化为LIST
	 * 
	 * @return
	 */
	public List getCurTaskNotGiveUpList()
	{
		List listmor = new ArrayList();
		List list = new ArrayList(task_list_by_id.values());
		for (int i = 0; i < list.size(); i++) {
			UTaskVO vo = (UTaskVO) list.get(i);
			if(vo.getTGiveUp() != 1){ 
				listmor.add(vo);
			}
			
		}
		return listmor;
	}
	
	/**
	 * 通过任务ID 得到任务信息
	 * 
	 * @param task_pk
	 * @return
	 */
	public CurTaskInfo getById(String task_pk)
	{
		return task_list_by_id.get(task_pk);
	}

	/**
	 * 通过任务组得到任务信息
	 * 
	 * @param task_zu
	 * @return
	 */
	public CurTaskInfo getByZu(String task_zu)
	{
		return task_list_by_zu.get(task_zu);
	}

	/**
	 * 在缓存中增加一个新任务
	 */
	public void putNewTask(CurTaskInfo new_task)
	{
		task_list_by_id.put(new_task.getTPk() + "", new_task);
		task_list_by_zu.put(new_task.getTZu(), new_task);
	}
	
	/**
	 * 在缓存中移除一个新任务
	 */
	public void removeTask(UTaskVO uTaskVO)
	{
		task_list_by_id.remove(uTaskVO.getTPk()+"");
		task_list_by_zu.remove(uTaskVO.getTZu());
	}
	
	/**
	 * 在缓存中移除一个新任务 KEY 为组
	 */
	public void removeTaskZu(CurTaskInfo curTaskInfo)
	{ 
		task_list_by_id.remove(curTaskInfo.getTZu());
	}
}
