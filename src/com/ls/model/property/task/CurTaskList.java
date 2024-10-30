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
 * ���ܣ���ɫ���ϵ�����Ϊ�������
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
	 * ������ӵ���������
	 */
	public void clear()
	{
		task_list_by_id.clear();
		task_list_by_zu.clear();
	}
	
	/**
	 * ��HASHMAP�����ת��ΪLIST
	 * 
	 * @return
	 */
	public List getCurTaskList()
	{
		List list = new ArrayList(task_list_by_id.values());
		return list;
	}

	/**
	 * ��HASHMAP�����ת��ΪLIST
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
	 * ͨ������ID �õ�������Ϣ
	 * 
	 * @param task_pk
	 * @return
	 */
	public CurTaskInfo getById(String task_pk)
	{
		return task_list_by_id.get(task_pk);
	}

	/**
	 * ͨ��������õ�������Ϣ
	 * 
	 * @param task_zu
	 * @return
	 */
	public CurTaskInfo getByZu(String task_zu)
	{
		return task_list_by_zu.get(task_zu);
	}

	/**
	 * �ڻ���������һ��������
	 */
	public void putNewTask(CurTaskInfo new_task)
	{
		task_list_by_id.put(new_task.getTPk() + "", new_task);
		task_list_by_zu.put(new_task.getTZu(), new_task);
	}
	
	/**
	 * �ڻ������Ƴ�һ��������
	 */
	public void removeTask(UTaskVO uTaskVO)
	{
		task_list_by_id.remove(uTaskVO.getTPk()+"");
		task_list_by_zu.remove(uTaskVO.getTZu());
	}
	
	/**
	 * �ڻ������Ƴ�һ�������� KEY Ϊ��
	 */
	public void removeTaskZu(CurTaskInfo curTaskInfo)
	{ 
		task_list_by_id.remove(curTaskInfo.getTZu());
	}
}
