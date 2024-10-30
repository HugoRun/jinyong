package com.web.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.ben.shitu.model.ShituConstant;
import com.ben.vo.task.TaskVO;
import com.ben.vo.task.UTaskVO;
import com.ls.ben.cache.staticcache.task.TaskCache;
import com.ls.model.property.task.CurTaskInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.GoodsType;
import com.ls.web.service.player.MyService;
import com.ls.web.service.player.MyServiceImpl;
import com.ls.web.service.player.RoleService;
import com.pm.service.systemInfo.SystemInfoService;

/**
 * ����:�˵������ж� 
 * 
 * @author ��ƾ� 11:13:44 AM
 */
public class TaskShaGuaiService { 
	Logger logger = Logger.getLogger("log.task");
	/**
	 * ִ��ɱ��
	 * */
	public String getShaGuaiService(RoleEntity roleEntity,TaskVO taskVO) {
		String hint = null;
		try {
			int pPk = roleEntity.getPPk();
			TaskService taskService = new TaskService();
			CurTaskInfo curTaskInfo = (CurTaskInfo)roleEntity.getTaskInfo().getCurTaskList().getByZu(taskVO.getTZu()); 
			
			int addMoney = ((taskVO.getTMoney() != null && !taskVO.getTMoney().equals("0") && !taskVO.getTMoney().trim().equals(""))?Integer.parseInt(taskVO.getTMoney().trim()):0);
			int addExp = ((taskVO.getTExp() != null && !taskVO.getTExp().equals("0") && !taskVO.getTExp().trim().equals(""))?Integer.parseInt(taskVO.getTExp().trim()):0);
			int addCredit = ((taskVO.getTSw() != null && !taskVO.getTSw().equals("0") && !taskVO.getTSw().trim().equals(""))?Integer.parseInt(taskVO.getTSw().trim()):0);
			MyService my = new MyServiceImpl();
			if(my.isShitu(pPk)){
				addMoney = addMoney*ShituConstant.MORE_TASK/100;
				addExp = addExp*ShituConstant.MORE_TASK/100;
				addCredit = addCredit*ShituConstant.MORE_TASK/100;
			}
			String tId = taskVO.getTId()+"";
			String tNext = taskVO.getTNext()+""; 
			if (curTaskInfo == null && Integer.parseInt(tId) != Integer.parseInt(tNext)) {  
				List<UTaskVO> list = roleEntity.getTaskInfo().getCurTaskList().getCurTaskNotGiveUpList();
				if(list.size()==GameConfig.getTaskMaxNum()){ 
					hint = "�Բ��������ֻ��ͬʱ��ȡ"+GameConfig.getTaskMaxNum()+"������";
					return hint;
				} 
				
				//����������жϰ��������Ƿ��㹻 
				if(taskService.taskJiangLiBaoGuoManZu(roleEntity,roleEntity.getBasicInfo().getPPk(),taskVO)==false){
					hint = "������������";
					return hint;
				}
				
				//����ҵ���
				if (taskVO.getTGeidj() != null && !taskVO.getTGeidj().equals("0") && !taskVO.getTGeidj().equals("")) {
					hint = taskService.getGeiDJService(pPk, taskVO.getTGeidj(), GoodsType.PROP, taskVO.getTGeidjNumber());
					if(hint != null){
						return hint;
					}
				} 
				// �����������ҵ���
				if (taskVO.getTEncouragement() != null && !taskVO.getTEncouragement().equals("0") && !taskVO.getTEncouragement().equals("")) {
					hint = taskService.getGeiDJService(pPk, taskVO.getTEncouragement(), GoodsType.PROP, taskVO.getTWncouragementNo());
					if(hint != null){
						return hint;
					}
				} 
				// ������������װ�� 
				if (taskVO.getTEncouragementZb() != null && !taskVO.getTEncouragementZb().equals("0") && !taskVO.getTEncouragementZb().equals("")) {
					hint = taskService.getGeiZBService(pPk, taskVO.getTEncouragementZb(), taskVO.getTEncouragementNoZb());
					if(hint != null){
						return hint;
					}
				}
				//��Ǯ
				if (addMoney!=0) {
					taskService.getAddMoney(roleEntity, addMoney);
				}
				//������
				if (addExp!=0) {
					taskService.getAddExp(roleEntity,pPk, addExp);
				}
				//������ 
				if (addCredit!=0) {
					taskService.addPlayerCredit(pPk, taskVO.getTSwType(), addCredit);
				}
				//TODO �������������ڻ����� 
				logger.info(roleEntity.getBasicInfo().getName()+"����ȡһ���µ�����---"+taskVO.getTName());
				roleEntity.getTaskInfo().acceptNewTask(roleEntity,taskVO);
				
			}else { 
				if (Integer.parseInt(tId) == Integer.parseInt(tNext)) {
					if(curTaskInfo.getTGiveUp() == 1){
						curTaskInfo.updateGiveUp(0);
					}
					// �ж����ɱ�������Ƿ���Ҫ��ɱ���������
					if (curTaskInfo.getTKillingOk() ==  curTaskInfo.getTKillingNo()) { 
						
						//����������жϰ��������Ƿ��㹻 
						if(taskService.taskJiangLiBaoGuoManZu(roleEntity,roleEntity.getBasicInfo().getPPk(),taskVO)==false){
							hint = "������������";
							return hint;
						}
						
						//����ҵ���
						if (taskVO.getTGeidj() != null && !taskVO.getTGeidj().equals("0") && !taskVO.getTGeidj().equals("")) {
							hint = taskService.getGeiDJService(pPk, taskVO.getTGeidj(), GoodsType.PROP, taskVO.getTGeidjNumber());
							if(hint != null){
								return hint;
							}
						} 
						// �����������ҵ���
						if (taskVO.getTEncouragement() != null && !taskVO.getTEncouragement().equals("0") && !taskVO.getTEncouragement().equals("")) {
							hint = taskService.getGeiDJService(pPk, taskVO.getTEncouragement(), GoodsType.PROP, taskVO.getTWncouragementNo()); 
							if(hint != null){
								return hint;
							}
						} 
						// ������������װ�� 
						if (taskVO.getTEncouragementZb() != null && !taskVO.getTEncouragementZb().equals("0") && !taskVO.getTEncouragementZb().equals("")) {
							hint = taskService.getGeiZBService(pPk, taskVO.getTEncouragementZb(), taskVO.getTEncouragementNoZb());
							if(hint != null){
								return hint;
							}
						}
						//��Ǯ
						if (addMoney!=0) {
							taskService.getAddMoney(roleEntity, addMoney);
						}
						//������
						if (addExp!=0) {
							taskService.getAddExp(roleEntity,pPk, addExp);
						}
						//������ 
						if (addCredit!=0) {
							taskService.addPlayerCredit(pPk, taskVO.getTSwType(), addCredit);
						}
						//TODO ɾ������ ��������е������¼ 
						logger.info(roleEntity.getBasicInfo().getName()+"�����һ������---"+taskVO.getTName());
						roleEntity.getTaskInfo().deleteTask(tId,pPk+"");  
					} else {
						 hint = "��û��ɱ������";
						return hint;
					}
				} else {    
					if(curTaskInfo.getTGiveUp() == 1){
						curTaskInfo.updateGiveUp(0);
					}
					// �ж����ɱ�������Ƿ���Ҫ��ɱ���������
					if (curTaskInfo.getTKillingOk() == curTaskInfo.getTKillingNo()) {
						
						//����������жϰ��������Ƿ��㹻 
						if(taskService.taskJiangLiBaoGuoManZu(roleEntity,roleEntity.getBasicInfo().getPPk(),taskVO)==false){
							hint = "������������";
							return hint;
						}
						
						//����ҵ���
						if (taskVO.getTGeidj() != null && !taskVO.getTGeidj().equals("0") && !taskVO.getTGeidj().equals("")) {
							hint = taskService.getGeiDJService(pPk, taskVO.getTGeidj(), GoodsType.PROP, taskVO.getTGeidjNumber());
							if(hint != null){
								return hint;
							}
						} 
						// �����������ҵ���
						if (taskVO.getTEncouragement() != null && !taskVO.getTEncouragement().equals("0") && !taskVO.getTEncouragement().equals("")) {
							hint = taskService.getGeiDJService(pPk, taskVO.getTEncouragement(), GoodsType.PROP, taskVO.getTWncouragementNo());
							if(hint != null){
								return hint;
							}
						} 
						// ������������װ�� 
						if (taskVO.getTEncouragementZb() != null && !taskVO.getTEncouragementZb().equals("0") && !taskVO.getTEncouragementZb().equals("")) {
							hint = taskService.getGeiZBService(pPk, taskVO.getTEncouragementZb(), taskVO.getTEncouragementNoZb());
							if(hint != null){
								return hint;
							}
						}
						//��Ǯ
						if (addMoney!=0) {
							taskService.getAddMoney(roleEntity, addMoney);
						}
						//������
						if (addExp!=0) {
							taskService.getAddExp(roleEntity,pPk, addExp);
						}
						//������ 
						if (addCredit!=0) {
							taskService.addPlayerCredit(pPk, taskVO.getTSwType(), addCredit);
						}
						//TODO �������������ڻ����� 
						logger.info(roleEntity.getBasicInfo().getName()+"����������---"+taskVO.getTName()+",������Ϊ��"+taskVO.getTZu()+"��������Ϊ"+taskVO.getTZuxl());
						roleEntity.getTaskInfo().updateTask(roleEntity,taskVO);  
					} else { 
						hint = "��û��ɱ������";
						return hint;
					}
				}
			} 
			//������id����ϵͳ��Ϣ���Ʊ��е�����idʱ���ͷ�һ��ϵͳ��Ϣ�������.
			SystemInfoService systemInfoService = new SystemInfoService();
			systemInfoService.sendSystemInfoByTaskId(pPk,Integer.parseInt(tId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hint;
	} 
	
	/**
	 * ִ��ɱ��
	 * */
	public boolean getShaGuaiDiscernService(String tId,int pPk) {
		try {  
			TaskVO taskVO = TaskCache.getById(tId); 
			 RoleEntity roleInfo = RoleService.getRoleInfoById(pPk+""); 
			 CurTaskInfo curTaskInfo = (CurTaskInfo)roleInfo.getTaskInfo().getCurTaskList().getByZu(taskVO.getTZu()); 
			 if(curTaskInfo != null){
				 if (curTaskInfo.getTKillingOk() != curTaskInfo.getTKillingNo()) {  
					 return false;
				 }  
			 }else{
				 return true;
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	} 
	
}
