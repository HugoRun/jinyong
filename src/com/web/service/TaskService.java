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
 * 功能:菜单任务判断
 * 
 * @author 侯浩军 11:13:44 AM
 */
public class TaskService {
	Logger logger = Logger.getLogger("log.task");
	/**
	 * 执行对话任务
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
					hint = "对不起，您最多只能同时领取"+GameConfig.getTaskMaxNum()+"条任务！";
					return hint;
				}  
				//任务给道具判断包裹各数是否足够 
				if(taskJiangLiBaoGuoManZu(roleEntity,roleEntity.getBasicInfo().getPPk(),taskVO)==false){
					hint = "包裹格数不够";
					return hint;
				}
				// 给玩家道具
				if (taskVO.getTGeidj() != null && !taskVO.getTGeidj().equals("0") && !taskVO.getTGeidj().equals("")) {
					 hint = getGeiDJService(pPk, taskVO.getTGeidj(), GoodsType.PROP, taskVO.getTGeidjNumber());
					 if(hint != null){
						 return hint;
					 }
				}
				// 任务结束给玩家道具
				if (taskVO.getTEncouragement() != null && !taskVO.getTEncouragement().equals("0") && !taskVO.getTEncouragement().equals("")) {
					hint = this.getGeiDJService(pPk, taskVO.getTEncouragement(), GoodsType.PROP, taskVO.getTWncouragementNo());
					if(hint != null){
						 return hint;
					 }
				} 
				// 任务结束给玩家装备 
				if (taskVO.getTEncouragementZb() != null && !taskVO.getTEncouragementZb().equals("0") && !taskVO.getTEncouragementZb().equals("")) {
					hint = this.getGeiZBService(pPk, taskVO.getTEncouragementZb(), taskVO.getTEncouragementNoZb());
					if(hint != null){
						 return hint;
					 }
				}
				//给钱
				if (addMoney!=0) {
					 this.getAddMoney(roleEntity, addMoney);
				}
				//给经验
				if (addExp!=0) {
					 this.getAddExp(roleEntity,pPk, addExp);
				} 
				//给声望 
				if (addCredit!=0) {
					 this.addPlayerCredit(pPk, taskVO.getTSwType(), addCredit);
				} 
				//TODO
				logger.info(roleEntity.getBasicInfo().getName()+"：领取一条新的任务---"+taskVO.getTName());
				roleEntity.getTaskInfo().acceptNewTask(roleEntity,taskVO); 
			} else {
				// 当任务ID 和 下一个任务ID 相同的时候执行操作 说明改条为任务的结束任务
				if (taskVO.getTId() == taskVO.getTNext()) { 
					if(curTaskInfo.getTGiveUp() == 1){
						curTaskInfo.updateGiveUp(0);
					}
					
					//任务给道具判断包裹各数是否足够 
					if(taskJiangLiBaoGuoManZu(roleEntity,roleEntity.getBasicInfo().getPPk(),taskVO)==false){
						hint = "包裹格数不够";
						return hint;
					}
					// 给玩家道具
					if (taskVO.getTGeidj() != null && !taskVO.getTGeidj().equals("0") && !taskVO.getTGeidj().equals("")) {
						 hint = getGeiDJService(pPk, taskVO.getTGeidj(), GoodsType.PROP, taskVO.getTGeidjNumber());
						 if(hint != null){
							 return hint;
						 }
					}
					// 任务结束给玩家道具
					if (taskVO.getTEncouragement() != null && !taskVO.getTEncouragement().equals("0") && !taskVO.getTEncouragement().equals("")) {
						hint = this.getGeiDJService(pPk, taskVO.getTEncouragement(), GoodsType.PROP, taskVO.getTWncouragementNo());
						if(hint != null){
							 return hint;
						 }
					} 
					// 任务结束给玩家装备 
					if (taskVO.getTEncouragementZb() != null && !taskVO.getTEncouragementZb().equals("0") && !taskVO.getTEncouragementZb().equals("")) {
						hint = this.getGeiZBService(pPk, taskVO.getTEncouragementZb(), taskVO.getTEncouragementNoZb());
						if(hint != null){
							 return hint;
						 }
					}
					//给钱
					if (addMoney!=0) {
						 this.getAddMoney(roleEntity, addMoney);
					}
					//给经验
					if (addExp!=0) {
						 this.getAddExp(roleEntity,pPk, addExp);
					}
					//给声望 
					if (addCredit!=0) {
						 this.addPlayerCredit(pPk, taskVO.getTSwType(), addCredit);
					} 
					//TODO 删除任务 清除缓存中的任务记录
					logger.info(roleEntity.getBasicInfo().getName()+"：完成一组任务---"+taskVO.getTName());
					roleEntity.getTaskInfo().deleteTask(taskVO.getTId()+"", pPk+""); 
					if(taskVO.getTZu().equals("yindao")){
						hint = "-1";
						return hint;
					}
				} else { 
					if(curTaskInfo.getTGiveUp() == 1){
						curTaskInfo.updateGiveUp(0);
					}
					//任务给道具判断包裹各数是否足够 
					if(taskJiangLiBaoGuoManZu(roleEntity,roleEntity.getBasicInfo().getPPk(),taskVO)==false){
						hint = "包裹格数不够";
						return hint;
					}
					// 给玩家道具
					if (taskVO.getTGeidj() != null && !taskVO.getTGeidj().equals("0") && !taskVO.getTGeidj().equals("")) {
						hint = this.getGeiDJService(pPk, taskVO.getTGeidj(), GoodsType.PROP, taskVO.getTGeidjNumber());
						if(hint != null){
							 return hint;
						 }
					}
					
					// 任务结束给玩家道具
					if (taskVO.getTEncouragement() != null && !taskVO.getTEncouragement().equals("0") && !taskVO.getTEncouragement().equals("")) {
						hint = this.getGeiDJService(pPk, taskVO.getTEncouragement(), GoodsType.PROP, taskVO.getTWncouragementNo());
						if(hint != null){
							 return hint;
						 }
					} 
					// 任务结束给玩家装备 
					if (taskVO.getTEncouragementZb() != null && !taskVO.getTEncouragementZb().equals("0") && !taskVO.getTEncouragementZb().equals("")) {
						hint = this.getGeiZBService(pPk, taskVO.getTEncouragementZb(), taskVO.getTEncouragementNoZb());
						if(hint != null){
							 return hint;
						 }
					}
					//给钱
					if (addMoney!=0) {
						 this.getAddMoney(roleEntity, addMoney);
					}
					//给经验
					if (addExp!=0) {
						 this.getAddExp(roleEntity,pPk, addExp);
					}
					//给声望 
					if (addCredit!=0) {
						 this.addPlayerCredit(pPk, taskVO.getTSwType(), addCredit);
					}  
					//TODO 将该组任务存放在缓存中 
					logger.info(roleEntity.getBasicInfo().getName()+"：继续任务---"+taskVO.getTName()+",任务组为："+taskVO.getTZu()+"，组序列为"+taskVO.getTZuxl());
					roleEntity.getTaskInfo().updateTask(roleEntity,taskVO); 
				}
			}  
			//当任务id符合系统消息控制表中的任务id时，就发一个系统消息给该玩家.
			SystemInfoService systemInfoService = new SystemInfoService();
			systemInfoService.sendSystemInfoByTaskId(pPk,taskVO.getTId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hint;
	}
    
	/**
	 * 给玩家道具 
	 */
	public String getGeiDJService(int pPk, String tGeidj, int pROP,String tGeidjNumber) {
		GoodsService goodsService = new GoodsService();
			String[] tGeidjs = tGeidj.split(",");
			String[] tGeidjNumbers = tGeidjNumber.split(",");
			for(int i = 0 ; i < tGeidjs.length ;i++){
				int hint = goodsService.putGoodsToWrap(pPk, Integer.parseInt(tGeidjs[i]), pROP, Integer.parseInt(tGeidjNumbers[i]));
				if(hint == -1){
					String ss = "包裹格数不够";
					return ss;
				}
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(Integer.parseInt(tGeidjs[i]), StatisticsType.PROP, Integer.parseInt(tGeidjNumbers[i]), StatisticsType.DEDAO, StatisticsType.RENWU,pPk);
				
			}
		return null;
	}
	
	/**
	 * 给钱
	 */
	public String getAddMoney(RoleEntity roleEntity, int money) {
		//监控
		LogService logService = new LogService();
		logService.recordMoneyLog(roleEntity.getBasicInfo().getPPk(), roleEntity.getBasicInfo().getName(), roleEntity.getBasicInfo().getCopper()+"", money+"", "任务");
		
		//修改钱 
		roleEntity.getBasicInfo().addCopper(money);
		//执行统计
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(6, StatisticsType.MONEY, ((long)(roleEntity.getBasicInfo().getCopper()) + money), StatisticsType.DEDAO, StatisticsType.RENWU,roleEntity.getBasicInfo().getPPk());
		return null;
	}
	/**
	 * 给经验
	 */
	public String getAddExp(RoleEntity roleEntity,int pPk, int exp) {
		// 装载用户信息 
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
	 * 给玩家装备
	 * @param pPk
	 * @param tGeizbId				装备id
	 * @param tGeizbNumber			装备数量
	 * @return
	 */
	public String getGeiZBService(int pPk, String tGeizbId, String tGeizbNumber) {
		GoodsService goodsService = new GoodsService();
		int hint  = goodsService.putEquipToWrap(pPk, Integer.parseInt(tGeizbId), Integer.parseInt(tGeizbNumber));
		if(hint == -1)
		{
			return "包裹格数不够";
		}
		//执行统计
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(Integer.parseInt(tGeizbId), GoodsType.EQUIP, Integer.parseInt(tGeizbNumber), StatisticsType.DEDAO, StatisticsType.RENWU,pPk);
		return null;
	}

	/**
	 * 消除玩家装备
	 */
	public boolean getXiaoZBService(String tGoodszb,int pPk,int uPk,String number) {
		String equip_id_str = tGoodszb;
		int equip_id = -1;
		try{
			equip_id = Integer.parseInt(equip_id_str);
		}
		catch (Exception e) {
			DataErrorLog.task(" 完成任务需要装备数据错误："+equip_id_str);
		}
		
		//判断是否有该装备
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
	 * 任务给玩家声望
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
		// 消除任务给玩家的道具
		if (taskVO.getTGeidj() != null && !taskVO.getTGeidj().equals("0") && !taskVO.getTGeidj().equals("")) {
			this.removeTaskStartProp(pPk, taskVO);
		}
		//消除任务给玩家的中间点给的物品
		if (taskVO.getTMidstGs() != null && !taskVO.getTMidstGs().equals("0") && !taskVO.getTMidstGs().equals("")) {
			int dd = goodsService.getPropNum(pPk, Integer.parseInt(taskVO.getTMidstGs()));
			goodsService.removeProps(pPk, Integer.parseInt(taskVO.getTMidstGs()), dd,GameLogManager.R_TASK);
		} 
	}
	/**
	 * 移除任务奖励道具
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
	 * 任务给道具判断包裹各数是否足够 
	 * @param taskVO
	 * @return
	 */
	public boolean taskJiangLiBaoGuoManZu(RoleEntity roleEntity,int p_pk,TaskVO taskVO){
		GoodsService goodsService = new GoodsService();
		//用以储存每个道具的需要占据的包裹空间，前一个参数为道具id,后一个参数为包裹空间
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
						logger.info("结束给道具占用包裹各数"+geidaoju); 
				 }
			}
			logger.info("给道具占用包裹各数"+geidaoju);
		}
		// 任务结束给玩家道具
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
						logger.info("结束给道具占用包裹各数"+jieshugeidaoju); 
				 }
			}
		} 
		// 任务结束给玩家装备 
		int jieshugeizhuangbei = 0;
		if (taskVO.getTEncouragementZb() != null && !taskVO.getTEncouragementZb().equals("0") && !taskVO.getTEncouragementZb().equals("")) {
			jieshugeizhuangbei = Integer.parseInt(taskVO.getTEncouragementNoZb());
			logger.info("结束给装备占用包裹各数"+jieshugeizhuangbei);
		}
		int number = geidaoju + jieshugeidaoju + jieshugeizhuangbei;
		if(goodsService.isEnoughWrapSpace(p_pk, number) == false){
			return false;
		}
		return true;
	}
}
