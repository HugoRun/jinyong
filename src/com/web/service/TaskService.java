package com.web.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.ben.shitu.model.ShituConstant;
import com.ben.vo.task.TaskVO;
import com.dp.dao.credit.CreditProce;
import com.ls.ben.cache.staticcache.task.TaskCache;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.vo.info.partinfo.Fighter;
import com.ls.model.log.GameLogManager;
import com.ls.model.property.task.CurTaskInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.GoodsType;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.log.DataErrorLog;
import com.ls.web.service.log.LogService;
import com.ls.web.service.player.GrowService;
import com.ls.web.service.player.MyService;
import com.ls.web.service.player.MyServiceImpl;
import com.ls.web.service.player.PlayerService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.pm.service.systemInfo.SystemInfoService;

/**
 * ����:�˵������ж�
 * 
 * @author ��ƾ� 11:13:44 AM
 */
public class TaskService {
	Logger logger = Logger.getLogger("log.task");
	/**
	 * ִ�жԻ�����
	 */
	public String getDuiHuaService(RoleEntity roleEntity,TaskVO taskVO) {
		String hint = null;
		try { 
			int pPk = roleEntity.getBasicInfo().getPPk();
			CurTaskInfo curTaskInfo = (CurTaskInfo)roleEntity.getTaskInfo().getCurTaskList().getByZu(taskVO.getTZu());
			/*TaskDAO taskDAO = new TaskDAO();
			if(Integer.parseInt(taskVO.getTZuxl()) == 1){
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
			if (curTaskInfo == null && taskVO.getTId() != taskVO.getTNext()) {
				List list = roleEntity.getTaskInfo().getCurTaskList().getCurTaskNotGiveUpList();
				if(list.size()==GameConfig.getTaskMaxNum()){
					hint = "�Բ��������ֻ��ͬʱ��ȡ"+GameConfig.getTaskMaxNum()+"������";
					return hint;
				}  
				//����������жϰ��������Ƿ��㹻 
				if(taskJiangLiBaoGuoManZu(roleEntity,roleEntity.getBasicInfo().getPPk(),taskVO)==false){
					hint = "������������";
					return hint;
				}
				// ����ҵ���
				if (taskVO.getTGeidj() != null && !taskVO.getTGeidj().equals("0") && !taskVO.getTGeidj().equals("")) {
					 hint = getGeiDJService(pPk, taskVO.getTGeidj(), GoodsType.PROP, taskVO.getTGeidjNumber());
					 if(hint != null){
						 return hint;
					 }
				}
				// �����������ҵ���
				if (taskVO.getTEncouragement() != null && !taskVO.getTEncouragement().equals("0") && !taskVO.getTEncouragement().equals("")) {
					hint = this.getGeiDJService(pPk, taskVO.getTEncouragement(), GoodsType.PROP, taskVO.getTWncouragementNo());
					if(hint != null){
						 return hint;
					 }
				} 
				// ������������װ�� 
				if (taskVO.getTEncouragementZb() != null && !taskVO.getTEncouragementZb().equals("0") && !taskVO.getTEncouragementZb().equals("")) {
					hint = this.getGeiZBService(pPk, taskVO.getTEncouragementZb(), taskVO.getTEncouragementNoZb());
					if(hint != null){
						 return hint;
					 }
				}
				//��Ǯ
				if (addMoney!=0) {
					 this.getAddMoney(roleEntity, addMoney);
				}
				//������
				if (addExp!=0) {
					 this.getAddExp(roleEntity,pPk, addExp);
				} 
				//������ 
				if (addCredit!=0) {
					 this.addPlayerCredit(pPk, taskVO.getTSwType(), addCredit);
				} 
				//TODO
				logger.info(roleEntity.getBasicInfo().getName()+"����ȡһ���µ�����---"+taskVO.getTName());
				roleEntity.getTaskInfo().acceptNewTask(roleEntity,taskVO); 
			} else {
				// ������ID �� ��һ������ID ��ͬ��ʱ��ִ�в��� ˵������Ϊ����Ľ�������
				if (taskVO.getTId() == taskVO.getTNext()) { 
					if(curTaskInfo.getTGiveUp() == 1){
						curTaskInfo.updateGiveUp(0);
					}
					
					//����������жϰ��������Ƿ��㹻 
					if(taskJiangLiBaoGuoManZu(roleEntity,roleEntity.getBasicInfo().getPPk(),taskVO)==false){
						hint = "������������";
						return hint;
					}
					// ����ҵ���
					if (taskVO.getTGeidj() != null && !taskVO.getTGeidj().equals("0") && !taskVO.getTGeidj().equals("")) {
						 hint = getGeiDJService(pPk, taskVO.getTGeidj(), GoodsType.PROP, taskVO.getTGeidjNumber());
						 if(hint != null){
							 return hint;
						 }
					}
					// �����������ҵ���
					if (taskVO.getTEncouragement() != null && !taskVO.getTEncouragement().equals("0") && !taskVO.getTEncouragement().equals("")) {
						hint = this.getGeiDJService(pPk, taskVO.getTEncouragement(), GoodsType.PROP, taskVO.getTWncouragementNo());
						if(hint != null){
							 return hint;
						 }
					} 
					// ������������װ�� 
					if (taskVO.getTEncouragementZb() != null && !taskVO.getTEncouragementZb().equals("0") && !taskVO.getTEncouragementZb().equals("")) {
						hint = this.getGeiZBService(pPk, taskVO.getTEncouragementZb(), taskVO.getTEncouragementNoZb());
						if(hint != null){
							 return hint;
						 }
					}
					//��Ǯ
					if (addMoney!=0) {
						 this.getAddMoney(roleEntity, addMoney);
					}
					//������
					if (addExp!=0) {
						 this.getAddExp(roleEntity,pPk, addExp);
					}
					//������ 
					if (addCredit!=0) {
						 this.addPlayerCredit(pPk, taskVO.getTSwType(), addCredit);
					} 
					//TODO ɾ������ ��������е������¼
					logger.info(roleEntity.getBasicInfo().getName()+"�����һ������---"+taskVO.getTName());
					roleEntity.getTaskInfo().deleteTask(taskVO.getTId()+"", pPk+""); 
					if(taskVO.getTZu().equals("yindao")){
						hint = "-1";
						return hint;
					}
				} else { 
					if(curTaskInfo.getTGiveUp() == 1){
						curTaskInfo.updateGiveUp(0);
					}
					//����������жϰ��������Ƿ��㹻 
					if(taskJiangLiBaoGuoManZu(roleEntity,roleEntity.getBasicInfo().getPPk(),taskVO)==false){
						hint = "������������";
						return hint;
					}
					// ����ҵ���
					if (taskVO.getTGeidj() != null && !taskVO.getTGeidj().equals("0") && !taskVO.getTGeidj().equals("")) {
						hint = this.getGeiDJService(pPk, taskVO.getTGeidj(), GoodsType.PROP, taskVO.getTGeidjNumber());
						if(hint != null){
							 return hint;
						 }
					}
					
					// �����������ҵ���
					if (taskVO.getTEncouragement() != null && !taskVO.getTEncouragement().equals("0") && !taskVO.getTEncouragement().equals("")) {
						hint = this.getGeiDJService(pPk, taskVO.getTEncouragement(), GoodsType.PROP, taskVO.getTWncouragementNo());
						if(hint != null){
							 return hint;
						 }
					} 
					// ������������װ�� 
					if (taskVO.getTEncouragementZb() != null && !taskVO.getTEncouragementZb().equals("0") && !taskVO.getTEncouragementZb().equals("")) {
						hint = this.getGeiZBService(pPk, taskVO.getTEncouragementZb(), taskVO.getTEncouragementNoZb());
						if(hint != null){
							 return hint;
						 }
					}
					//��Ǯ
					if (addMoney!=0) {
						 this.getAddMoney(roleEntity, addMoney);
					}
					//������
					if (addExp!=0) {
						 this.getAddExp(roleEntity,pPk, addExp);
					}
					//������ 
					if (addCredit!=0) {
						 this.addPlayerCredit(pPk, taskVO.getTSwType(), addCredit);
					}  
					//TODO �������������ڻ����� 
					logger.info(roleEntity.getBasicInfo().getName()+"����������---"+taskVO.getTName()+",������Ϊ��"+taskVO.getTZu()+"��������Ϊ"+taskVO.getTZuxl());
					roleEntity.getTaskInfo().updateTask(roleEntity,taskVO); 
				}
			}  
			//������id����ϵͳ��Ϣ���Ʊ��е�����idʱ���ͷ�һ��ϵͳ��Ϣ�������.
			SystemInfoService systemInfoService = new SystemInfoService();
			systemInfoService.sendSystemInfoByTaskId(pPk,taskVO.getTId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hint;
	}
    
	/**
	 * ����ҵ��� 
	 */
	public String getGeiDJService(int pPk, String tGeidj, int pROP,String tGeidjNumber) {
		GoodsService goodsService = new GoodsService();
			String[] tGeidjs = tGeidj.split(",");
			String[] tGeidjNumbers = tGeidjNumber.split(",");
			for(int i = 0 ; i < tGeidjs.length ;i++){
				int hint = goodsService.putGoodsToWrap(pPk, Integer.parseInt(tGeidjs[i]), pROP, Integer.parseInt(tGeidjNumbers[i]));
				if(hint == -1){
					String ss = "������������";
					return ss;
				}
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(Integer.parseInt(tGeidjs[i]), StatisticsType.PROP, Integer.parseInt(tGeidjNumbers[i]), StatisticsType.DEDAO, StatisticsType.RENWU,pPk);
				
			}
		return null;
	}
	
	/**
	 * ��Ǯ
	 */
	public String getAddMoney(RoleEntity roleEntity, int money) {
		//���
		LogService logService = new LogService();
		logService.recordMoneyLog(roleEntity.getBasicInfo().getPPk(), roleEntity.getBasicInfo().getName(), roleEntity.getBasicInfo().getCopper()+"", money+"", "����");
		
		//�޸�Ǯ 
		roleEntity.getBasicInfo().addCopper(money);
		//ִ��ͳ��
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(6, StatisticsType.MONEY, ((long)(roleEntity.getBasicInfo().getCopper()) + money), StatisticsType.DEDAO, StatisticsType.RENWU,roleEntity.getBasicInfo().getPPk());
		return null;
	}
	/**
	 * ������
	 */
	public String getAddExp(RoleEntity roleEntity,int pPk, int exp) {
		// װ���û���Ϣ 
		GrowService growService = new GrowService();
		Fighter player = new Fighter();
		PlayerService playerService = new PlayerService();
		playerService.loadFighterByPpk(player, pPk);
		String hint = growService.playerGrowTask(roleEntity,player, exp);
		SystemInfoService systemInfoService = new SystemInfoService();
	    systemInfoService.insertSystemInfoBySystem(pPk, StringUtil.gbToISO(hint)); 
		return null;
	}
	
	/**
	 * �����װ��
	 * @param pPk
	 * @param tGeizbId				װ��id
	 * @param tGeizbNumber			װ������
	 * @return
	 */
	public String getGeiZBService(int pPk, String tGeizbId, String tGeizbNumber) {
		GoodsService goodsService = new GoodsService();
		int hint  = goodsService.putEquipToWrap(pPk, Integer.parseInt(tGeizbId), Integer.parseInt(tGeizbNumber));
		if(hint == -1)
		{
			return "������������";
		}
		//ִ��ͳ��
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(Integer.parseInt(tGeizbId), GoodsType.EQUIP, Integer.parseInt(tGeizbNumber), StatisticsType.DEDAO, StatisticsType.RENWU,pPk);
		return null;
	}

	/**
	 * �������װ��
	 */
	public boolean getXiaoZBService(String tGoodszb,int pPk,int uPk,String number) {
		String equip_id_str = tGoodszb;
		int equip_id = -1;
		try{
			equip_id = Integer.parseInt(equip_id_str);
		}
		catch (Exception e) {
			DataErrorLog.task(" ���������Ҫװ�����ݴ���"+equip_id_str);
		}
		
		//�ж��Ƿ��и�װ��
		PlayerEquipDao equipDao = new PlayerEquipDao();
		if(equipDao.isHaveByEquipId(pPk, equip_id)==false){
			return false;
		}else{
		for(int i = 0 ; i < Integer.parseInt(number) ;i++){ 
		GoodsService goodsService = new GoodsService();
		goodsService.removeEquipByEquipID(pPk, equip_id); 
		}
		}
		return true;
	}
	/**
	 * ������������
	 * @param pPk
	 * @param cID
	 * @param ncount
	 */
	public void addPlayerCredit(int pPk, int cID ,int ncount){
		CreditProce creditProce = new CreditProce();
		creditProce.addPlayerCredit(pPk, cID, ncount);
	}
	
	
	public void removeTaskStartBestowGoods(int pPk,int getTId){
		GoodsService  goodsService = new GoodsService();
		TaskVO taskVO =(TaskVO) TaskCache.getById(getTId+""); 
		// �����������ҵĵ���
		if (taskVO.getTGeidj() != null && !taskVO.getTGeidj().equals("0") && !taskVO.getTGeidj().equals("")) {
			this.removeTaskStartProp(pPk, taskVO);
		}
		//�����������ҵ��м�������Ʒ
		if (taskVO.getTMidstGs() != null && !taskVO.getTMidstGs().equals("0") && !taskVO.getTMidstGs().equals("")) {
			int dd = goodsService.getPropNum(pPk, Integer.parseInt(taskVO.getTMidstGs()));
			goodsService.removeProps(pPk, Integer.parseInt(taskVO.getTMidstGs()), dd,GameLogManager.R_TASK);
		} 
	}
	/**
	 * �Ƴ�����������
	 * @param pPk
	 * @param taskVO
	 */
	private void removeTaskStartProp(int pPk,TaskVO taskVO){
		GoodsService  goodsService = new GoodsService();
		String[] getTGoods = taskVO.getTGeidj().split(",");
		String[] getTGoodsNo = taskVO.getTGeidjNumber().split(",");
		for(int i = 0 ; i < getTGoods.length ;i++){
			if(getTGoods[i] != null && !getTGoods[i].equals("0") && !getTGoods[i].equals("")) {
				goodsService.removeProps(pPk, Integer.parseInt(getTGoods[i]), Integer.parseInt(getTGoodsNo[i]),GameLogManager.R_TASK);
			}
		}
	}
	
	/**
	 * ����������жϰ��������Ƿ��㹻 
	 * @param taskVO
	 * @return
	 */
	public boolean taskJiangLiBaoGuoManZu(RoleEntity roleEntity,int p_pk,TaskVO taskVO){
		GoodsService goodsService = new GoodsService();
		//���Դ���ÿ�����ߵ���Ҫռ�ݵİ����ռ䣬ǰһ������Ϊ����id,��һ������Ϊ�����ռ�
		int geidaoju= 0;
		if (taskVO.getTGeidj() != null && !taskVO.getTGeidj().equals("0") && !taskVO.getTGeidj().equals("")) {
			String[] tGeidjs = taskVO.getTGeidj().split(",");
			String[] tGeidjNumbers = taskVO.getTGeidjNumber().split(",");
			for(int i = 0 ; i < tGeidjs.length ;i++){
				//p_pk, , 
				int s = goodsService.returnpropspare(roleEntity,p_pk,Integer.parseInt(tGeidjs[i]),Integer.parseInt(tGeidjNumbers[i]));
				 if(s == -1){
					 return false;
				 }else{
					 geidaoju += s;
						logger.info("����������ռ�ð�������"+geidaoju); 
				 }
			}
			logger.info("������ռ�ð�������"+geidaoju);
		}
		// �����������ҵ���
		int jieshugeidaoju = 0;
		if (taskVO.getTEncouragement() != null && !taskVO.getTEncouragement().equals("0") && !taskVO.getTEncouragement().equals("")) {
			String[] tGeidjs = taskVO.getTEncouragement().split(",");
			String[] tGeidjNumbers = taskVO.getTWncouragementNo().split(",");
			for(int i = 0 ; i < tGeidjs.length ;i++){
				//p_pk, , 
				int s = goodsService.returnpropspare(roleEntity,p_pk,Integer.parseInt(tGeidjs[i]),Integer.parseInt(tGeidjNumbers[i]));
				 if(s == -1){
					 return false;
				 }else{
					 jieshugeidaoju += s;
						logger.info("����������ռ�ð�������"+jieshugeidaoju); 
				 }
			}
		} 
		// ������������װ�� 
		int jieshugeizhuangbei = 0;
		if (taskVO.getTEncouragementZb() != null && !taskVO.getTEncouragementZb().equals("0") && !taskVO.getTEncouragementZb().equals("")) {
			jieshugeizhuangbei = Integer.parseInt(taskVO.getTEncouragementNoZb());
			logger.info("������װ��ռ�ð�������"+jieshugeizhuangbei);
		}
		int number = geidaoju + jieshugeidaoju + jieshugeizhuangbei;
		if(goodsService.isEnoughWrapSpace(p_pk, number) == false){
			return false;
		}
		return true;
	}
}
