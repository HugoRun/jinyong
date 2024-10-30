/**
 * 
 */
package com.ls.model.property.task;

import org.apache.log4j.Logger;

import com.ben.dao.task.UTaskDAO;
import com.ben.vo.task.UTaskVO;
import com.ls.model.user.PersistenceEntity;

/**
 * 功能：角色任务相关
 * 
 * @author hhj Apr 2, 2009 4:09:39 PM
 */
public class CurTaskInfo extends UTaskVO
{
	protected Logger logger = Logger.getLogger("log.cache");
	private UTaskDAO dao = null;

	/**
	 * 初始化玩家身上所有任务
	 * 
	 * @param pPk
	 */
	public CurTaskInfo(UTaskDAO dao)
	{
		this.dao = dao;
	}

	/**
	 * 更新中间点
	 */
	public void updatePoint(String taskPoint)
	{
		//logger.debug("-----------删除----------------"+taskPoint);
		this.setTPoint(taskPoint);
		dao.getUpMenuId(this.getTPk(), taskPoint);
	}

	/**
	 * 更新杀怪数量
	 */
	public void updateKillingOk(int killingNumber)
	{
		int number = this.getTKillingOk() + killingNumber;
		if (number <= this.getTKillingNo())
		{
			this.setTKillingOk(number);
			dao.tKillingOKUpdate(number, this.getTPk() + "");
		}
	}

	/**
	 * 是否放弃任务
	 */
	public void updateGiveUp(int type)
	{
		this.setTGiveUp(type);
		dao.updateGiveUp(this.getTPk() + "",type);
	}
}
