/**
 * 
 */
package com.ls.model.property.task;

import org.apache.log4j.Logger;

import com.ben.dao.task.UTaskDAO;
import com.ben.vo.task.UTaskVO;
import com.ls.model.user.PersistenceEntity;

/**
 * ���ܣ���ɫ�������
 * 
 * @author hhj Apr 2, 2009 4:09:39 PM
 */
public class CurTaskInfo extends UTaskVO
{
	protected Logger logger = Logger.getLogger("log.cache");
	private UTaskDAO dao = null;

	/**
	 * ��ʼ�����������������
	 * 
	 * @param pPk
	 */
	public CurTaskInfo(UTaskDAO dao)
	{
		this.dao = dao;
	}

	/**
	 * �����м��
	 */
	public void updatePoint(String taskPoint)
	{
		//logger.debug("-----------ɾ��----------------"+taskPoint);
		this.setTPoint(taskPoint);
		dao.getUpMenuId(this.getTPk(), taskPoint);
	}

	/**
	 * ����ɱ������
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
	 * �Ƿ��������
	 */
	public void updateGiveUp(int type)
	{
		this.setTGiveUp(type);
		dao.updateGiveUp(this.getTPk() + "",type);
	}
}
