/**
 * 
 */
package com.ls.model.property;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ben.dao.task.UTaskDAO;
import com.ben.vo.task.TaskVO;
import com.ben.vo.task.UTaskVO;
import com.ben.vo.task.UtaskCompleteVO;
import com.ls.ben.cache.staticcache.task.TaskCache;
import com.ls.model.property.task.CurTaskInfo;
import com.ls.model.property.task.CurTaskList;
import com.ls.model.property.task.TaskCompleteInfo;
import com.ls.model.user.RoleEntity;
import com.ls.model.user.UserBase;
import com.ls.pub.constant.TaskType;
import com.ls.web.service.player.RoleService;

/**
 * ���ܣ���ɫ�������
 * 
 * @author hhj Apr 2, 2009 4:09:39 PM
 */
public class RoleTaskInfo extends UserBase
{
	private TaskCompleteInfo taskCompleteInfo;// �Ѿ���ɵ�����
	private CurTaskList curTaskList;// δ��ɵ�����

	/** ***************��ɫ����״̬**************************** */
	private int taskId = -1;// ����ID
	private int taskMenu = -1;// ����˵�ID
	private String taskPoint = null;// �����м��
	private String taskMidstGs = null;// �м�����Ʒ
	private String taskMidstZb = null;// �м���װ��
	
	/**
	 * ��ʼ�����������������
	 * @param pPk
	 */
	public RoleTaskInfo(int pPk)
	{
		super(pPk);
		taskCompleteInfo = new TaskCompleteInfo(pPk);
		curTaskList = new CurTaskList(pPk);
	}

	public void acceptNewTask(RoleEntity roleInfo,TaskVO taskVO)
	{ 
		acceptNewTask(taskVOORUTaskVO(roleInfo, taskVO));
	}

	/**
	 * ����һ���µ�����
	 */
	private void acceptNewTask(CurTaskInfo new_task)
	{
		UTaskDAO dao = new UTaskDAO();
		int t_pk = -1;
		if (new_task.getTType() == TaskType.DIALOG)// �Ի����� 
		{
			t_pk = dao.getUTaskAdd(new_task.getPPk() + "", new_task.getPName(),new_task.getTZu(), new_task.getTPx(), new_task.getTId()
					+ "", new_task.getTTitle(), new_task.getTType()
					+ "", new_task.getTXrwnpcId() + "", new_task.getTNext()
					+ "", new_task.getCreateTime(), new_task.getTTime()+ "",new_task.getTGiveUp()+"",new_task.getUpTaskId()+"");
		}
		if (new_task.getTType() == TaskType.KILL)// ɱ������
		{
			t_pk = dao.getUTaskAddXG(new_task.getPPk() + "", new_task.getPName(),
					new_task.getTZu(), new_task.getTPx(), new_task.getTId()
							+ "", new_task.getTTitle(), new_task.getTType()
							+ "", new_task.getTXrwnpcId() + "", new_task.getTNext()
							+ "", new_task.getTKilling(), new_task.getTKillingNo()
							+ "", new_task.getCreateTime(), new_task.getTTime()
							+ "",new_task.getTGiveUp()+"",new_task.getUpTaskId()+"");
		}
		if (new_task.getTType() == TaskType.CARRY)// Я����
		{
			t_pk = dao.getUTaskAddXD(new_task.getPPk() + "", new_task.getPName(),
					new_task.getTZu(), new_task.getTPx(), new_task.getTId()
					+ "", new_task.getTTitle(), new_task.getTType()
					+ "", new_task.getTXrwnpcId() + "", new_task.getTNext()
					+ "", new_task.getTGoods(), new_task.getTGoodsNo(),
					new_task.getTGoodszb(), new_task.getTGoodszbNumber(),
					new_task.getCreateTime(), new_task.getTTime() + "",
					new_task.getTPet(), new_task.getTPetNumber(),new_task.getTGiveUp()+"",new_task.getUpTaskId()+"");
		}
		if (new_task.getTType() == TaskType.FIND)// Ѱ����
		{
			t_pk = dao.getUTaskAddXD(new_task.getPPk() + "", new_task.getPName(),
					new_task.getTZu(), new_task.getTPx(), new_task.getTId()
					+ "", new_task.getTTitle(), new_task.getTType()
					+ "", new_task.getTXrwnpcId() + "", new_task.getTNext()
					+ "", new_task.getTGoods(), new_task.getTGoodsNo(),
					new_task.getTGoodszb(), new_task.getTGoodszbNumber(),
					new_task.getCreateTime(), new_task.getTTime() + "",
					new_task.getTPet(), new_task.getTPetNumber(),new_task.getTGiveUp()+"",new_task.getUpTaskId()+"");
		}
		if (new_task.getTType() == TaskType.COMPLEX)// ������  
		{
			t_pk = dao.getUTaskAddFH(new_task.getPPk() + "", new_task.getPName(),
					new_task.getTZu(), new_task.getTPx(), new_task.getTId()
					+ "", new_task.getTTitle(), new_task.getTType()
					+ "", new_task.getTXrwnpcId() + "", new_task.getTNext()
					+ "", new_task.getTPoint(), new_task.getTZjdwp(),
					new_task.getTZjdwpNumber() + "", new_task.getTZjdzb(),
					new_task.getTZjdzbNumber() + "",
					new_task.getTDjscwp() + "", new_task.getTDjsczb() + "",
					new_task.getTMidstGs(), new_task.getTMidstZb(), 
					new_task.getTGoods(), new_task.getTGoodsNo(), 
					new_task.getTGoodszb(), new_task.getTGoodszbNumber(),
					new_task.getCreateTime(), new_task.getTTime() + "",new_task.getTGiveUp()+"",new_task.getUpTaskId()+"");
		}
		new_task.setTPk(t_pk);
		curTaskList.putNewTask(new_task);
	}

	public void updateTask(RoleEntity roleInfo,TaskVO taskVO)
	{
		updateTask(taskVOORUTaskVO(roleInfo, taskVO));
	}

	/**
	 * ����һ������
	 * @param curTaskInfo
	 */
	private void updateTask(CurTaskInfo curTaskInfo)
	{
		UTaskDAO dao = new UTaskDAO();
		RoleEntity roleEntity = RoleService.getRoleInfoById(curTaskInfo.getPPk()+ "");
		UTaskVO uTaskVO = (UTaskVO) roleEntity.getTaskInfo().getCurTaskList().getByZu(curTaskInfo.getTZu());// ͨ����KEY �õ���ɫ������Ϣ
		/** ��������Ϊ�м�ת������ʱ��ִ���޸���ʱ����� * */
		if (curTaskInfo.getTType() == TaskType.DIALOG)// �Ի�����
		{
			dao.getUTaskUpdate(curTaskInfo.getPPk() + "", curTaskInfo.getPName(), curTaskInfo.getTZu(), curTaskInfo.getTPx(),
					curTaskInfo.getTId() + "", curTaskInfo.getTTitle(),
					curTaskInfo.getTType() + "", curTaskInfo.getTXrwnpcId() + "", curTaskInfo.getTNext() + "", 
					curTaskInfo.getCreateTime(), curTaskInfo.getTTime() + "",curTaskInfo.getTGiveUp()+"",curTaskInfo.getUpTaskId()+"");
		}
		if (curTaskInfo.getTType() == TaskType.KILL)// ɱ��
		{
			dao.getUTaskUpdateXG(curTaskInfo.getPPk() + "", curTaskInfo.getPName(), curTaskInfo.getTZu(), curTaskInfo.getTPx(),
					curTaskInfo.getTId() + "", curTaskInfo.getTTitle(),
					curTaskInfo.getTType() + "", curTaskInfo.getTXrwnpcId()+ "", 
					curTaskInfo.getTNext() + "", curTaskInfo.getTKilling(), curTaskInfo.getTKillingNo() + "",
					curTaskInfo.getTKillingOk() + "", curTaskInfo.getCreateTime(), curTaskInfo.getTTime() + "",curTaskInfo.getTGiveUp()+"",curTaskInfo.getUpTaskId()+"");
		}
		if (curTaskInfo.getTType() == TaskType.CARRY)// Я��
		{
			dao.getUTaskUpdateXD(curTaskInfo.getPPk() + "", curTaskInfo.getPName(), curTaskInfo.getTZu(), curTaskInfo.getTPx(),
					curTaskInfo.getTId() + "", curTaskInfo.getTTitle(),
					curTaskInfo.getTType() + "", curTaskInfo.getTXrwnpcId()+ "", 
					curTaskInfo.getTNext() + "", curTaskInfo.getTGoods(), curTaskInfo.getTGoodsNo(),
					curTaskInfo.getTGoodszb(), curTaskInfo.getTGoodszbNumber(),
					curTaskInfo.getCreateTime(), curTaskInfo.getTTime() + "",
					curTaskInfo.getTPet(), curTaskInfo.getTPetNumber(),curTaskInfo.getTGiveUp()+"",curTaskInfo.getUpTaskId()+"");
		}
		if (curTaskInfo.getTType() == TaskType.FIND)// Ѱ����
		{
			dao.getUTaskUpdateXD(curTaskInfo.getPPk() + "", curTaskInfo.getPName(), curTaskInfo.getTZu(), curTaskInfo.getTPx(),
					curTaskInfo.getTId() + "", curTaskInfo.getTTitle(),
					curTaskInfo.getTType() + "", curTaskInfo.getTXrwnpcId()+ "", 
					curTaskInfo.getTNext() + "", curTaskInfo.getTGoods(), curTaskInfo.getTGoodsNo(),
					curTaskInfo.getTGoodszb(), curTaskInfo.getTGoodszbNumber(),
					curTaskInfo.getCreateTime(), curTaskInfo.getTTime() + "",
					curTaskInfo.getTPet(), curTaskInfo.getTPetNumber(),curTaskInfo.getTGiveUp()+"",curTaskInfo.getUpTaskId()+"");
		}
		if (curTaskInfo.getTType() == TaskType.COMPLEX)// ������
		{
			dao.getUTaskUpdateFH(curTaskInfo.getPPk() + "", curTaskInfo.getPName(), curTaskInfo.getTZu(), curTaskInfo.getTPx(),
					curTaskInfo.getTId() + "", curTaskInfo.getTTitle(),
					curTaskInfo.getTType() + "", curTaskInfo.getTXrwnpcId()+ "", curTaskInfo.getTNext() 
					+ "", curTaskInfo.getTPoint(), curTaskInfo.getTZjdwp(), curTaskInfo.getTZjdwpNumber()
					+ "", curTaskInfo.getTZjdzb(), curTaskInfo.getTZjdzbNumber()+ "", curTaskInfo.getTDjscwp() 
					+ "", curTaskInfo.getTDjsczb()+ "", curTaskInfo.getTMidstGs(), curTaskInfo.getTMidstZb(), curTaskInfo.getTGoods(),
					curTaskInfo.getTGoodsNo(), curTaskInfo.getTGoodszb(),
					curTaskInfo.getTGoodszbNumber(), curTaskInfo.getCreateTime(), curTaskInfo.getTTime() + "",curTaskInfo.getTGiveUp()+"",curTaskInfo.getUpTaskId()+"");
		}
		curTaskList.removeTask(uTaskVO);
		curTaskInfo.setTPk(uTaskVO.getTPk());
		curTaskList.putNewTask(curTaskInfo);

	}

	/**
	 * �Ƴ�һ������
	 * 
	 * @param taskVo
	 */
	public void deleteTask(String tId, String pPk)
	{
		TaskVO taskVO = TaskCache.getById(tId);// ���������Ϣ
		RoleEntity roleEntity = RoleService.getRoleInfoById(pPk);
		UTaskVO uTaskVO = (UTaskVO) roleEntity.getTaskInfo().getCurTaskList().getByZu(taskVO.getTZu());// ͨ����KEY �õ���ɫ������Ϣ
		UTaskDAO uTaskDAO = new UTaskDAO();
		uTaskDAO.getUTaskDelete(uTaskVO.getTPk() + "");// ɾ������
		curTaskList.removeTask(uTaskVO);
		// ������������ɵ�����
		UtaskCompleteVO utaskCompleteVO = new UtaskCompleteVO();
		utaskCompleteVO.setPPk(Integer.parseInt(pPk));
		utaskCompleteVO.setTaskZu(taskVO.getTZu());
		taskCompleteInfo.completeTask(utaskCompleteVO);
	}
	
	/**
	 * ��������
	 */
	public void dropTask(UTaskVO uTaskVO)
	{
		UTaskDAO uTaskDAO = new UTaskDAO();
		uTaskDAO.getUTaskDelete(uTaskVO.getTPk() + "");// ɾ������
		curTaskList.removeTask(uTaskVO);
	}
	
	/**
	 * ������ӵ���������
	 */
	public void clear()
	{
		curTaskList.clear();
		UTaskDAO uTaskDAO = new UTaskDAO();
		uTaskDAO.clear(p_pk);// ɾ������
	}
	
	/**
	 * ����ת��
	 * @param roleInfo
	 * @param taskVO
	 * @return 
	 */
	private CurTaskInfo taskVOORUTaskVO(RoleEntity roleInfo,TaskVO taskVO)
	{
		// ����ʱ��
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String Time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
		UTaskDAO dao = new UTaskDAO();
		CurTaskInfo curTaskInfo = new CurTaskInfo(dao);
		curTaskInfo.setCreateTime(Time);
		curTaskInfo.setPName(roleInfo.getBasicInfo().getName());
		curTaskInfo.setPPk(roleInfo.getBasicInfo().getPPk());
		curTaskInfo.setTDjscwp(taskVO.getTDjsc());
		curTaskInfo.setTGoods(taskVO.getTGoods());
		curTaskInfo.setTGoodsNo(taskVO.getTGoodsNumber());
		curTaskInfo.setTGoodsOk("0");
		curTaskInfo.setTGoodszb(taskVO.getTGoodszb());
		curTaskInfo.setTGoodszbNumber(taskVO.getTGoodszbNumber());
		curTaskInfo.setTGoodszbOk("0");
		curTaskInfo.setTId(taskVO.getTId());
		curTaskInfo.setTKilling(taskVO.getTKilling());
		curTaskInfo.setTKillingNo(taskVO.getTKillingNo());
		curTaskInfo.setTKillingOk(0);
		curTaskInfo.setTMidstGs(taskVO.getTMidstGs());
		curTaskInfo.setTNext(taskVO.getTNext());
		curTaskInfo.setTPetOk(0); 
		curTaskInfo.setTPoint(taskVO.getTPoint());
		curTaskInfo.setTPointNo("0");
		curTaskInfo.setTPx(taskVO.getTZuxl());
		curTaskInfo.setTTitle(taskVO.getTName());
		curTaskInfo.setTXrwnpcId(taskVO.getTXrwnpcId());
		curTaskInfo.setTType(taskVO.getTType());
		curTaskInfo.setTZjdwp(taskVO.getTZjdwp());
		curTaskInfo.setTZjdwpNumber(taskVO.getTZjdwpNumber());
		curTaskInfo.setTZjdwpOk(0);
		curTaskInfo.setTZjdzbOk(0);
		curTaskInfo.setTZu(taskVO.getTZu()); 
		return curTaskInfo;
	}

	public int getTaskId()
	{
		return taskId;
	}

	public void setTaskId(int taskId)
	{
		this.taskId = taskId;
	}

	public int getTaskMenu()
	{
		return taskMenu;
	}

	public void setTaskMenu(int taskMenu)
	{
		this.taskMenu = taskMenu;
	}

	public String getTaskPoint()
	{
		return taskPoint;
	}

	public void setTaskPoint(String taskPoint)
	{
		this.taskPoint = taskPoint;
	}

	public String getTaskMidstGs()
	{
		return taskMidstGs;
	}

	public void setTaskMidstGs(String taskMidstGs)
	{
		this.taskMidstGs = taskMidstGs;
	}

	public String getTaskMidstZb()
	{
		return taskMidstZb;
	}

	public void setTaskMidstZb(String taskMidstZb)
	{
		this.taskMidstZb = taskMidstZb;
	}

	public TaskCompleteInfo getTaskCompleteInfo()
	{
		return taskCompleteInfo;
	}

	public CurTaskList getCurTaskList()
	{
		return curTaskList;
	}
}
