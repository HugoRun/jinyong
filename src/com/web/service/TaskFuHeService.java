package com.web.service;
   
import java.util.List;

import org.apache.log4j.Logger;

import com.ben.shitu.model.ShituConstant;
import com.ben.vo.task.TaskVO;
import com.ben.vo.task.UTaskVO;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.cache.staticcache.task.TaskCache;
import com.ls.model.log.GameLogManager;
import com.ls.model.property.task.CurTaskInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.GoodsType;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.player.MyService;
import com.ls.web.service.player.MyServiceImpl;
import com.ls.web.service.player.RoleService;
import com.pm.service.systemInfo.SystemInfoService;

/**
 * ����:�˵������ж�
 * 
 * @author ��ƾ� 11:13:44 AM
 */
public class TaskFuHeService { 
	Logger logger = Logger.getLogger("log.task");
	/**
	 * ִ��Ѱ�������� 
	 * */
	public String getFuHeService(RoleEntity roleEntity,TaskVO taskVO) {
		String hint = null;
		try {
			int pPk = roleEntity.getBasicInfo().getPPk();
			CurTaskInfo curTaskInfo = (CurTaskInfo)roleEntity.getTaskInfo().getCurTaskList().getByZu(taskVO.getTZu());
			
			/*if(Integer.parseInt(taskVO.getTZuxl()) == 1){
				taskVO.setUpTaskId(taskVO.getTId());
			}else{
				int task_id = taskDAO.getTaskZUXl(taskVO.getTZu(), (Integer.parseInt(taskVO.getTZuxl()) - 1)+"");
				taskVO.setUpTaskId(task_id);
			} */
			int addMoney = ((taskVO.getTMoney() != null && !taskVO.getTMoney().equals("0") && !taskVO.getTMoney().trim().equals(""))?Integer.parseInt(taskVO.getTMoney().trim()):0);
			int addExp = ((taskVO.getTExp() != null && !taskVO.getTExp().equals("0") && !taskVO.getTExp().trim().equals(""))?Integer.parseInt(taskVO.getTExp().trim()):0);
			int addCredit = ((taskVO.getTSw() != null && !taskVO.getTSw().equals("0") && !taskVO.getTSw().trim().equals(""))?Integer.parseInt(taskVO.getTSw().trim()):0);
			MyService my = new MyServiceImpl();
			if(my.isShitu(pPk)){
				addMoney = addMoney*ShituConstant.MORE_TASK/100;
				addExp = addExp*ShituConstant.MORE_TASK/100;
				addCredit = addCredit*ShituConstant.MORE_TASK/100;
			}
			TaskService taskService = new TaskService(); 
			String tId = taskVO.getTId()+"";
			String tNext = taskVO.getTNext()+"";   
			if (curTaskInfo == null && Integer.parseInt(tId) != Integer.parseInt(tNext)) { 
				 
				List list = roleEntity.getTaskInfo().getCurTaskList().getCurTaskNotGiveUpList();
				if(list.size()==GameConfig.getTaskMaxNum()){ 
					hint = "�Բ��������ֻ��ͬʱ��ȡ"+GameConfig.getTaskMaxNum()+"������";
					return hint;
				}    
				
				
				//����������жϰ��������Ƿ��㹻 
				if(taskService.taskJiangLiBaoGuoManZu(roleEntity,roleEntity.getBasicInfo().getPPk(),taskVO)==false){
					hint = "������������";
					return hint;
				}
				
				/***********************����Ǹ��˵��Ի� �˵�������Ʒ��װ��********************/
				//����ҵ���
				if (taskVO.getTGeidj() != null && !taskVO.getTGeidj().equals("0") && !taskVO.getTGeidj().equals("")) {
					hint = taskService.getGeiDJService(pPk, taskVO.getTGeidj(), GoodsType.PROP, taskVO.getTGeidjNumber());
					if(hint != null){
						return hint;
					}
				} 
				//�����װ��
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
				
			} else {  
				if (Integer.parseInt(tId) == Integer.parseInt(tNext)) {
					if(curTaskInfo.getTGiveUp() == 1){
						curTaskInfo.updateGiveUp(0);
					}
					TaskVO taskVOCache = TaskCache.getById(tId); 
				    UTaskVO uTaskVO = (UTaskVO) roleEntity.getTaskInfo().getCurTaskList().getByZu(taskVOCache.getTZu()); 
				    GoodsService  goodsService = new GoodsService();
				    
				  //����������жϰ��������Ƿ��㹻 
					if(taskService.taskJiangLiBaoGuoManZu(roleEntity,roleEntity.getBasicInfo().getPPk(),taskVO)==false){
						hint = "������������";
						return hint;
					}
					
					/** ********************************һ����������Ʒ******************************************** */
					if (uTaskVO.getTGoods() != null && !uTaskVO.getTGoods().equals("") && !uTaskVO.getTGoods().equals("0")) {
						String[] getTGoods = uTaskVO.getTGoods().split(",");
						String[] getTGoodsNo = uTaskVO.getTGoodsNo().split(",");
						for(int i = 0 ; i < getTGoods.length ;i++){
							if (goodsService.getPropNum(pPk, Integer.parseInt(getTGoods[i])) >= Integer.parseInt(getTGoodsNo[i])) { 
								goodsService.removeProps(pPk, Integer.parseInt(getTGoods[i]), Integer.parseInt(getTGoodsNo[i]),GameLogManager.R_TASK);
							} else { 
								hint = "��Ʒ��������";
								return hint;
							}
						}
					}
					/************************************ͨ���м����Ҫ����Ʒ*******************************************/ 
					/*if (uTaskVO.getTZjdwp() != null && !uTaskVO.getTZjdwp().equals("") && !uTaskVO.getTZjdwp().equals("0")) {
							if (goodsService.getPropNum(pPk, Integer.parseInt(uTaskVO.getTZjdwp())) >= uTaskVO.getTZjdwpNumber() && uTaskVO.getTDjscwp()==1) { 
								goodsService.removeProps(pPk, Integer.parseInt(uTaskVO.getTZjdwp()), uTaskVO.getTZjdwpNumber());
							} else { 
								hint = "û�������Ʒ";
								return hint;
							} 
					}*/
					
					 
					/** ********************************һ��������װ��************************************ */
					// �ж�װ���Ƿ�Ϊ�� 
					if (uTaskVO.getTGoodszb() != null && !uTaskVO.getTGoodszb().equals("") && !uTaskVO.getTGoodszb().equals("0")) {
						 if(taskService.getXiaoZBService(uTaskVO.getTGoodszb(),pPk,roleEntity.getBasicInfo().getUPk(),uTaskVO.getTGoodszbNumber())){
							 ////System.out.println("���������������Ʒ");
						 }else{
							 hint = "û�������Ʒ";
							 return hint;
						 }
					} 
					
					/***********************����Ǹ��˵��Ի� �˵�������Ʒ��װ��********************/
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
					if(curTaskInfo.getTGiveUp() == 1){
						curTaskInfo.updateGiveUp(0);
					}
					
					TaskVO taskVOCache = TaskCache.getById(tId); 
				    UTaskVO uTaskVO = (UTaskVO) roleEntity.getTaskInfo().getCurTaskList().getByZu(taskVOCache.getTZu()); 

				    
				    
				    GoodsService  goodsService = new GoodsService();
				    
				    
				  //����������жϰ��������Ƿ��㹻 
					if(taskService.taskJiangLiBaoGuoManZu(roleEntity,roleEntity.getBasicInfo().getPPk(),taskVO)==false){
						hint = "������������";
						return hint;
					}
				 
				
				/** ********************************һ����������Ʒ******************************************** */
				if (uTaskVO.getTGoods() != null && !uTaskVO.getTGoods().equals("") && !uTaskVO.getTGoods().equals("0")) { 
					String[] getTGoods = uTaskVO.getTGoods().split(",");
					String[] getTGoodsNo = uTaskVO.getTGoodsNo().split(",");
					for(int i = 0 ; i < getTGoods.length ;i++){
						if (goodsService.getPropNum(pPk, Integer.parseInt(getTGoods[i])) >= Integer.parseInt(getTGoodsNo[i])) { 
							goodsService.removeProps(pPk, Integer.parseInt(getTGoods[i]), Integer.parseInt(getTGoodsNo[i]),GameLogManager.R_TASK);
						} else { 
							hint = "û�������Ʒ";
							return hint;
						}
					}
				}
				/************************************ͨ���м����Ҫ����Ʒ*******************************************/ 
				/*if (uTaskVO.getTZjdwp() != null && !uTaskVO.getTZjdwp().equals("") && !uTaskVO.getTZjdwp().equals("0")) { 
						if (goodsService.getPropNum(pPk, Integer.parseInt(uTaskVO.getTZjdwp())) >= uTaskVO.getTZjdwpNumber() && uTaskVO.getTDjscwp()==1) { 
							goodsService.removeProps(pPk, Integer.parseInt(uTaskVO.getTZjdwp()), uTaskVO.getTZjdwpNumber());
						} else { 
							hint = "û�������Ʒ";
							return hint;
						} 
				}*/
				/** ********************************һ��������װ��************************************ */
				// �ж�װ���Ƿ�Ϊ�� 
				if (uTaskVO.getTGoodszb() != null && !uTaskVO.getTGoodszb().equals("") && !uTaskVO.getTGoodszb().equals("0")) {
					 if(taskService.getXiaoZBService(uTaskVO.getTGoodszb(),pPk,roleEntity.getBasicInfo().getUPk(),uTaskVO.getTGoodszbNumber())){
						 ////System.out.println("���������������Ʒ");
					 }else{
						 hint = "û�������Ʒ";
						 return hint;
					 }
				}
				
				
				/***********************����Ǹ��˵��Ի� �˵�������Ʒ��װ��********************/
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
				}
			}
			//������id����ϵͳ��Ϣ���Ʊ��е�����idʱ���ͷ�һ��ϵͳ��Ϣ�������.
			SystemInfoService systemInfoService = new SystemInfoService();
			systemInfoService.sendSystemInfoByTaskId(pPk,Integer.valueOf(tId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hint;
	} 
	
	/**
	 * ִ��Ѱ��������
	 * */
	public boolean getFuHeDiscernService(String tId,int pPk,int uPk) {
		try { 
			TaskService taskService = new TaskService(); 
			GoodsService goodsService = new GoodsService(); 
			TaskCache taskCache = new TaskCache();
			TaskVO taskVOCache = taskCache.getById(tId);
			RoleService roleService = new RoleService();
			RoleEntity roleEntity = roleService.getRoleInfoById(pPk+"");
		    UTaskVO uTaskVO = (UTaskVO) roleEntity.getTaskInfo().getCurTaskList().getByZu(taskVOCache.getTZu());
		    if(uTaskVO != null){
		    	if(uTaskVO.getTGoods() != null && !uTaskVO.getTGoods().equals("") && !uTaskVO.getTGoods().equals("0")) { 
					String[] getTGoods = uTaskVO.getTGoods().split(",");
					String[] getTGoodsNo = uTaskVO.getTGoodsNo().split(",");
					for(int i = 0 ; i < getTGoods.length ;i++){
						int dd = goodsService.getPropNum(pPk, Integer.parseInt(getTGoods[i]));
						if ( dd < Integer.parseInt(getTGoodsNo[i])) {  
							return false; 
					}
				    }
					} 
				// �ж�װ���Ƿ�Ϊ�� 
				if (uTaskVO.getTGoodszb() != null && !uTaskVO.getTGoodszb().equals("") && !uTaskVO.getTGoodszb().equals("0")) {
				if(taskService.getXiaoZBService(uTaskVO.getTGoodszb(),pPk,uPk,uTaskVO.getTGoodszbNumber()) == false){
				return false;
				}   
			    }
		    }else{
		    	return true;
			 }
			}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	} 
	
	/**
	 * �ж��м���Ƿ����
	 * */
	public boolean getFuHeDiscernZJService(String tId,int pPk,int uPk) {
		try {  
			TaskVO taskVOCache = TaskCache.getById(tId);
			RoleEntity roleEntity = RoleService.getRoleInfoById(pPk+"");
		    UTaskVO uTaskVO = (UTaskVO) roleEntity.getTaskInfo().getCurTaskList().getByZu(taskVOCache.getTZu());
		    if(uTaskVO != null){
		    	if (uTaskVO.getTPoint() != null&& !uTaskVO.getTPoint().equals("")&& !uTaskVO.getTPoint().equals("0")) { 
					return false;
				}
		    }else{
		    	return true;
			 }
			}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	} 

	/**
	 * ͨ���м����Ҫ����Ʒ
	 * @param pPk
	 * @param uTaskVO
	 * @return
	 */
	public String guozhongjiadanjiaowupin(int pPk,UTaskVO uTaskVO){
		/************************************ͨ���м����Ҫ����Ʒ*******************************************/ 
		String hint = null;
		if (uTaskVO.getTZjdwp() != null && !uTaskVO.getTZjdwp().equals("") && !uTaskVO.getTZjdwp().equals("0")) { 
			GoodsService  goodsService = new GoodsService();
				if (goodsService.getPropNum(pPk, Integer.parseInt(uTaskVO.getTZjdwp())) >= uTaskVO.getTZjdwpNumber() && uTaskVO.getTDjscwp()==1) { 
					goodsService.removeProps(pPk, Integer.parseInt(uTaskVO.getTZjdwp()), uTaskVO.getTZjdwpNumber(),GameLogManager.R_TASK);
				} else { 
					hint = "��û��"+PropCache.getPropNameById(Integer.parseInt(uTaskVO.getTZjdwp()))+"�����Ʒ";
					return hint;
				} 
		}
		return hint;
	}
}