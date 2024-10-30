package com.web.service;

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
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 功能:菜单任务判断
 *
 * @author 侯浩军 11:13:44 AM
 */
public class TaskFuHeService {
    Logger logger = Logger.getLogger("log.task");

    /**
     * 执行寻物类任务
     */
    public String getFuHeService(RoleEntity roleEntity, TaskVO taskVO) {
        String hint = null;
        try {
            int pPk = roleEntity.getBasicInfo().getPPk();
            CurTaskInfo curTaskInfo = roleEntity.getTaskInfo().getCurTaskList().getByZu(taskVO.getTZu());
			
			/*if(Integer.parseInt(taskVO.getTZuxl()) == 1){
				taskVO.setUpTaskId(taskVO.getTId());
			}else{
				int task_id = taskDAO.getTaskZUXl(taskVO.getTZu(), (Integer.parseInt(taskVO.getTZuxl()) - 1)+"");
				taskVO.setUpTaskId(task_id);
			} */
            int addMoney = ((taskVO.getTMoney() != null && !taskVO.getTMoney().equals("0") && !taskVO.getTMoney().trim().equals("")) ? Integer.parseInt(taskVO.getTMoney().trim()) : 0);
            int addExp = ((taskVO.getTExp() != null && !taskVO.getTExp().equals("0") && !taskVO.getTExp().trim().equals("")) ? Integer.parseInt(taskVO.getTExp().trim()) : 0);
            int addCredit = ((taskVO.getTSw() != null && !taskVO.getTSw().equals("0") && !taskVO.getTSw().trim().equals("")) ? Integer.parseInt(taskVO.getTSw().trim()) : 0);
            MyService my = new MyServiceImpl();
            if (my.isShitu(pPk)) {
                addMoney = addMoney * ShituConstant.MORE_TASK / 100;
                addExp = addExp * ShituConstant.MORE_TASK / 100;
                addCredit = addCredit * ShituConstant.MORE_TASK / 100;
            }
            TaskService taskService = new TaskService();
            String tId = taskVO.getTId() + "";
            String tNext = taskVO.getTNext() + "";
            if (curTaskInfo == null && Integer.parseInt(tId) != Integer.parseInt(tNext)) {

                List list = roleEntity.getTaskInfo().getCurTaskList().getCurTaskNotGiveUpList();
                if (list.size() == GameConfig.getTaskMaxNum()) {
                    hint = "对不起，您最多只能同时领取" + GameConfig.getTaskMaxNum() + "条任务！";
                    return hint;
                }


                //任务给道具判断包裹各数是否足够
                if (!taskService.taskJiangLiBaoGuoManZu(roleEntity, roleEntity.getBasicInfo().getPPk(), taskVO)) {
                    hint = "包裹格数不够";
                    return hint;
                }

                /***********************这个是跟菜单对话 菜单给的物品和装备********************/
                //给玩家道具
                if (taskVO.getTGeidj() != null && !taskVO.getTGeidj().equals("0") && !taskVO.getTGeidj().equals("")) {
                    hint = taskService.getGeiDJService(pPk, taskVO.getTGeidj(), GoodsType.PROP, taskVO.getTGeidjNumber());
                    if (hint != null) {
                        return hint;
                    }
                }
                //给玩家装备
                // 任务结束给玩家道具
                if (taskVO.getTEncouragement() != null && !taskVO.getTEncouragement().equals("0") && !taskVO.getTEncouragement().equals("")) {
                    hint = taskService.getGeiDJService(pPk, taskVO.getTEncouragement(), GoodsType.PROP, taskVO.getTWncouragementNo());
                    if (hint != null) {
                        return hint;
                    }
                }
                // 任务结束给玩家装备
                if (taskVO.getTEncouragementZb() != null && !taskVO.getTEncouragementZb().equals("0") && !taskVO.getTEncouragementZb().equals("")) {
                    hint = taskService.getGeiZBService(pPk, taskVO.getTEncouragementZb(), taskVO.getTEncouragementNoZb());
                    if (hint != null) {
                        return hint;
                    }
                }

                //给钱
                if (addMoney != 0) {
                    taskService.getAddMoney(roleEntity, addMoney);
                }
                //给经验
                if (addExp != 0) {
                    taskService.getAddExp(roleEntity, pPk, addExp);
                }
                //给声望
                if (addCredit != 0) {
                    taskService.addPlayerCredit(pPk, taskVO.getTSwType(), addCredit);
                }

                //TODO 将该组任务存放在缓存中
                logger.info(roleEntity.getBasicInfo().getName() + "：领取一条新的任务---" + taskVO.getTName());
                roleEntity.getTaskInfo().acceptNewTask(roleEntity, taskVO);

            } else {
                if (Integer.parseInt(tId) == Integer.parseInt(tNext)) {
                    if (curTaskInfo.getTGiveUp() == 1) {
                        curTaskInfo.updateGiveUp(0);
                    }
                    TaskVO taskVOCache = TaskCache.getById(tId);
                    UTaskVO uTaskVO = roleEntity.getTaskInfo().getCurTaskList().getByZu(taskVOCache.getTZu());
                    GoodsService goodsService = new GoodsService();

                    //任务给道具判断包裹各数是否足够
                    if (!taskService.taskJiangLiBaoGuoManZu(roleEntity, roleEntity.getBasicInfo().getPPk(), taskVO)) {
                        hint = "包裹格数不够";
                        return hint;
                    }

                    /** ********************************一下是消除物品******************************************** */
                    if (uTaskVO.getTGoods() != null && !uTaskVO.getTGoods().equals("") && !uTaskVO.getTGoods().equals("0")) {
                        String[] getTGoods = uTaskVO.getTGoods().split(",");
                        String[] getTGoodsNo = uTaskVO.getTGoodsNo().split(",");
                        for (int i = 0; i < getTGoods.length; i++) {
                            if (goodsService.getPropNum(pPk, Integer.parseInt(getTGoods[i])) >= Integer.parseInt(getTGoodsNo[i])) {
                                goodsService.removeProps(pPk, Integer.parseInt(getTGoods[i]), Integer.parseInt(getTGoodsNo[i]), GameLogManager.R_TASK);
                            } else {
                                hint = "物品数量不够";
                                return hint;
                            }
                        }
                    }
                    /************************************通过中间点需要的物品*******************************************/
					/*if (uTaskVO.getTZjdwp() != null && !uTaskVO.getTZjdwp().equals("") && !uTaskVO.getTZjdwp().equals("0")) {
							if (goodsService.getPropNum(pPk, Integer.parseInt(uTaskVO.getTZjdwp())) >= uTaskVO.getTZjdwpNumber() && uTaskVO.getTDjscwp()==1) { 
								goodsService.removeProps(pPk, Integer.parseInt(uTaskVO.getTZjdwp()), uTaskVO.getTZjdwpNumber());
							} else { 
								hint = "没有这个物品";
								return hint;
							} 
					}*/


                    /** ********************************一下是消除装备************************************ */
                    // 判断装备是否为空
                    if (uTaskVO.getTGoodszb() != null && !uTaskVO.getTGoodszb().equals("") && !uTaskVO.getTGoodszb().equals("0")) {
                        if (taskService.getXiaoZBService(uTaskVO.getTGoodszb(), pPk, roleEntity.getBasicInfo().getUPk(), uTaskVO.getTGoodszbNumber())) {
                            ////System.out.println("消除您身上这件物品");
                        } else {
                            hint = "没有这个物品";
                            return hint;
                        }
                    }

                    /***********************这个是跟菜单对话 菜单给的物品和装备********************/
                    //给玩家道具
                    if (taskVO.getTGeidj() != null && !taskVO.getTGeidj().equals("0") && !taskVO.getTGeidj().equals("")) {
                        hint = taskService.getGeiDJService(pPk, taskVO.getTGeidj(), GoodsType.PROP, taskVO.getTGeidjNumber());
                        if (hint != null) {
                            return hint;
                        }
                    }


                    // 任务结束给玩家道具
                    if (taskVO.getTEncouragement() != null && !taskVO.getTEncouragement().equals("0") && !taskVO.getTEncouragement().equals("")) {
                        hint = taskService.getGeiDJService(pPk, taskVO.getTEncouragement(), GoodsType.PROP, taskVO.getTWncouragementNo());
                        if (hint != null) {
                            return hint;
                        }
                    }
                    // 任务结束给玩家装备
                    if (taskVO.getTEncouragementZb() != null && !taskVO.getTEncouragementZb().equals("0") && !taskVO.getTEncouragementZb().equals("")) {
                        hint = taskService.getGeiZBService(pPk, taskVO.getTEncouragementZb(), taskVO.getTEncouragementNoZb());
                        if (hint != null) {
                            return hint;
                        }
                    }
                    //给钱
                    if (addMoney != 0) {
                        taskService.getAddMoney(roleEntity, addMoney);
                    }
                    //给经验
                    if (addExp != 0) {
                        taskService.getAddExp(roleEntity, pPk, addExp);
                    }
                    //给声望
                    if (addCredit != 0) {
                        taskService.addPlayerCredit(pPk, taskVO.getTSwType(), addCredit);
                    }

                    //TODO 删除任务 清除缓存中的任务记录
                    logger.info(roleEntity.getBasicInfo().getName() + "：完成一组任务---" + taskVO.getTName());
                    roleEntity.getTaskInfo().deleteTask(tId, pPk + "");

                } else {
                    if (curTaskInfo.getTGiveUp() == 1) {
                        curTaskInfo.updateGiveUp(0);
                    }

                    TaskVO taskVOCache = TaskCache.getById(tId);
                    UTaskVO uTaskVO = roleEntity.getTaskInfo().getCurTaskList().getByZu(taskVOCache.getTZu());


                    GoodsService goodsService = new GoodsService();


                    //任务给道具判断包裹各数是否足够
                    if (!taskService.taskJiangLiBaoGuoManZu(roleEntity, roleEntity.getBasicInfo().getPPk(), taskVO)) {
                        hint = "包裹格数不够";
                        return hint;
                    }


                    /** ********************************一下是消除物品******************************************** */
                    if (uTaskVO.getTGoods() != null && !uTaskVO.getTGoods().equals("") && !uTaskVO.getTGoods().equals("0")) {
                        String[] getTGoods = uTaskVO.getTGoods().split(",");
                        String[] getTGoodsNo = uTaskVO.getTGoodsNo().split(",");
                        for (int i = 0; i < getTGoods.length; i++) {
                            if (goodsService.getPropNum(pPk, Integer.parseInt(getTGoods[i])) >= Integer.parseInt(getTGoodsNo[i])) {
                                goodsService.removeProps(pPk, Integer.parseInt(getTGoods[i]), Integer.parseInt(getTGoodsNo[i]), GameLogManager.R_TASK);
                            } else {
                                hint = "没有这个物品";
                                return hint;
                            }
                        }
                    }
                    /************************************通过中间点需要的物品*******************************************/
				/*if (uTaskVO.getTZjdwp() != null && !uTaskVO.getTZjdwp().equals("") && !uTaskVO.getTZjdwp().equals("0")) { 
						if (goodsService.getPropNum(pPk, Integer.parseInt(uTaskVO.getTZjdwp())) >= uTaskVO.getTZjdwpNumber() && uTaskVO.getTDjscwp()==1) { 
							goodsService.removeProps(pPk, Integer.parseInt(uTaskVO.getTZjdwp()), uTaskVO.getTZjdwpNumber());
						} else { 
							hint = "没有这个物品";
							return hint;
						} 
				}*/
                    /** ********************************一下是消除装备************************************ */
                    // 判断装备是否为空
                    if (uTaskVO.getTGoodszb() != null && !uTaskVO.getTGoodszb().equals("") && !uTaskVO.getTGoodszb().equals("0")) {
                        if (taskService.getXiaoZBService(uTaskVO.getTGoodszb(), pPk, roleEntity.getBasicInfo().getUPk(), uTaskVO.getTGoodszbNumber())) {
                            ////System.out.println("消除您身上这件物品");
                        } else {
                            hint = "没有这个物品";
                            return hint;
                        }
                    }


                    /***********************这个是跟菜单对话 菜单给的物品和装备********************/
                    //给玩家道具
                    if (taskVO.getTGeidj() != null && !taskVO.getTGeidj().equals("0") && !taskVO.getTGeidj().equals("")) {
                        hint = taskService.getGeiDJService(pPk, taskVO.getTGeidj(), GoodsType.PROP, taskVO.getTGeidjNumber());
                        if (hint != null) {
                            return hint;
                        }
                    }
                    // 任务结束给玩家道具
                    if (taskVO.getTEncouragement() != null && !taskVO.getTEncouragement().equals("0") && !taskVO.getTEncouragement().equals("")) {
                        hint = taskService.getGeiDJService(pPk, taskVO.getTEncouragement(), GoodsType.PROP, taskVO.getTWncouragementNo());
                        if (hint != null) {
                            return hint;
                        }
                    }
                    // 任务结束给玩家装备
                    if (taskVO.getTEncouragementZb() != null && !taskVO.getTEncouragementZb().equals("0") && !taskVO.getTEncouragementZb().equals("")) {
                        hint = taskService.getGeiZBService(pPk, taskVO.getTEncouragementZb(), taskVO.getTEncouragementNoZb());
                        if (hint != null) {
                            return hint;
                        }
                    }
                    //给钱
                    if (addMoney != 0) {
                        taskService.getAddMoney(roleEntity, addMoney);
                    }
                    //给经验
                    if (addExp != 0) {
                        taskService.getAddExp(roleEntity, pPk, addExp);
                    }
                    //给声望
                    if (addCredit != 0) {
                        taskService.addPlayerCredit(pPk, taskVO.getTSwType(), addCredit);
                    }

                    //TODO 将该组任务存放在缓存中
                    logger.info(roleEntity.getBasicInfo().getName() + "：继续任务---" + taskVO.getTName() + ",任务组为：" + taskVO.getTZu() + "，组序列为" + taskVO.getTZuxl());
                    roleEntity.getTaskInfo().updateTask(roleEntity, taskVO);
                }
            }
            //当任务id符合系统消息控制表中的任务id时，就发一个系统消息给该玩家.
            SystemInfoService systemInfoService = new SystemInfoService();
            systemInfoService.sendSystemInfoByTaskId(pPk, Integer.valueOf(tId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hint;
    }

    /**
     * 执行寻物类任务
     */
    public boolean getFuHeDiscernService(String tId, int pPk, int uPk) {
        try {
            TaskService taskService = new TaskService();
            GoodsService goodsService = new GoodsService();
            TaskCache taskCache = new TaskCache();
            TaskVO taskVOCache = TaskCache.getById(tId);
            RoleService roleService = new RoleService();
            RoleEntity roleEntity = RoleService.getRoleInfoById(pPk + "");
            UTaskVO uTaskVO = roleEntity.getTaskInfo().getCurTaskList().getByZu(taskVOCache.getTZu());
            if (uTaskVO != null) {
                if (uTaskVO.getTGoods() != null && !uTaskVO.getTGoods().equals("") && !uTaskVO.getTGoods().equals("0")) {
                    String[] getTGoods = uTaskVO.getTGoods().split(",");
                    String[] getTGoodsNo = uTaskVO.getTGoodsNo().split(",");
                    for (int i = 0; i < getTGoods.length; i++) {
                        int dd = goodsService.getPropNum(pPk, Integer.parseInt(getTGoods[i]));
                        if (dd < Integer.parseInt(getTGoodsNo[i])) {
                            return false;
                        }
                    }
                }
                // 判断装备是否为空
                if (uTaskVO.getTGoodszb() != null && !uTaskVO.getTGoodszb().equals("") && !uTaskVO.getTGoodszb().equals("0")) {
                    if (!taskService.getXiaoZBService(uTaskVO.getTGoodszb(), pPk, uPk, uTaskVO.getTGoodszbNumber())) {
                        return false;
                    }
                }
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 判断中间点是否被完成
     */
    public boolean getFuHeDiscernZJService(String tId, int pPk, int uPk) {
        try {
            TaskVO taskVOCache = TaskCache.getById(tId);
            RoleEntity roleEntity = RoleService.getRoleInfoById(pPk + "");
            UTaskVO uTaskVO = roleEntity.getTaskInfo().getCurTaskList().getByZu(taskVOCache.getTZu());
            if (uTaskVO != null) {
                if (uTaskVO.getTPoint() != null && !uTaskVO.getTPoint().equals("") && !uTaskVO.getTPoint().equals("0")) {
                    return false;
                }
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 通过中间点需要的物品
     *
     * @param pPk
     * @param uTaskVO
     * @return
     */
    public String guozhongjiadanjiaowupin(int pPk, UTaskVO uTaskVO) {
        /************************************通过中间点需要的物品*******************************************/
        String hint = null;
        if (uTaskVO.getTZjdwp() != null && !uTaskVO.getTZjdwp().equals("") && !uTaskVO.getTZjdwp().equals("0")) {
            GoodsService goodsService = new GoodsService();
            if (goodsService.getPropNum(pPk, Integer.parseInt(uTaskVO.getTZjdwp())) >= uTaskVO.getTZjdwpNumber() && uTaskVO.getTDjscwp() == 1) {
                goodsService.removeProps(pPk, Integer.parseInt(uTaskVO.getTZjdwp()), uTaskVO.getTZjdwpNumber(), GameLogManager.R_TASK);
            } else {
                hint = "你没有" + PropCache.getPropNameById(Integer.parseInt(uTaskVO.getTZjdwp())) + "这个物品";
                return hint;
            }
        }
        return hint;
    }
}
