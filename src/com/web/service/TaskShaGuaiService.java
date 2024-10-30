package com.web.service;

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
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 功能:菜单任务判断
 *
 * @author 侯浩军 11:13:44 AM
 */
public class TaskShaGuaiService {
    Logger logger = Logger.getLogger("log.task");

    /**
     * 执行杀怪
     */
    public String getShaGuaiService(RoleEntity roleEntity, TaskVO taskVO) {
        String hint = null;
        try {
            int pPk = roleEntity.getPPk();
            TaskService taskService = new TaskService();
            CurTaskInfo curTaskInfo = roleEntity.getTaskInfo().getCurTaskList().getByZu(taskVO.getTZu());

            int addMoney = ((taskVO.getTMoney() != null && !taskVO.getTMoney().equals("0") && !taskVO.getTMoney().trim().equals("")) ? Integer.parseInt(taskVO.getTMoney().trim()) : 0);
            int addExp = ((taskVO.getTExp() != null && !taskVO.getTExp().equals("0") && !taskVO.getTExp().trim().equals("")) ? Integer.parseInt(taskVO.getTExp().trim()) : 0);
            int addCredit = ((taskVO.getTSw() != null && !taskVO.getTSw().equals("0") && !taskVO.getTSw().trim().equals("")) ? Integer.parseInt(taskVO.getTSw().trim()) : 0);
            MyService my = new MyServiceImpl();
            if (my.isShitu(pPk)) {
                addMoney = addMoney * ShituConstant.MORE_TASK / 100;
                addExp = addExp * ShituConstant.MORE_TASK / 100;
                addCredit = addCredit * ShituConstant.MORE_TASK / 100;
            }
            String tId = taskVO.getTId() + "";
            String tNext = taskVO.getTNext() + "";
            if (curTaskInfo == null && Integer.parseInt(tId) != Integer.parseInt(tNext)) {
                List<UTaskVO> list = roleEntity.getTaskInfo().getCurTaskList().getCurTaskNotGiveUpList();
                if (list.size() == GameConfig.getTaskMaxNum()) {
                    hint = "对不起，您最多只能同时领取" + GameConfig.getTaskMaxNum() + "条任务！";
                    return hint;
                }

                //任务给道具判断包裹各数是否足够
                if (!taskService.taskJiangLiBaoGuoManZu(roleEntity, roleEntity.getBasicInfo().getPPk(), taskVO)) {
                    hint = "包裹格数不够";
                    return hint;
                }

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
                logger.info(roleEntity.getBasicInfo().getName() + "：领取一条新的任务---" + taskVO.getTName());
                roleEntity.getTaskInfo().acceptNewTask(roleEntity, taskVO);

            } else {
                if (Integer.parseInt(tId) == Integer.parseInt(tNext)) {
                    if (curTaskInfo.getTGiveUp() == 1) {
                        curTaskInfo.updateGiveUp(0);
                    }
                    // 判断完成杀怪数量是否与要求杀怪数量相等
                    if (curTaskInfo.getTKillingOk() == curTaskInfo.getTKillingNo()) {

                        //任务给道具判断包裹各数是否足够
                        if (!taskService.taskJiangLiBaoGuoManZu(roleEntity, roleEntity.getBasicInfo().getPPk(), taskVO)) {
                            hint = "包裹格数不够";
                            return hint;
                        }

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
                        hint = "您没有杀够怪物";
                        return hint;
                    }
                } else {
                    if (curTaskInfo.getTGiveUp() == 1) {
                        curTaskInfo.updateGiveUp(0);
                    }
                    // 判断完成杀怪数量是否与要求杀怪数量相等
                    if (curTaskInfo.getTKillingOk() == curTaskInfo.getTKillingNo()) {

                        //任务给道具判断包裹各数是否足够
                        if (!taskService.taskJiangLiBaoGuoManZu(roleEntity, roleEntity.getBasicInfo().getPPk(), taskVO)) {
                            hint = "包裹格数不够";
                            return hint;
                        }

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
                    } else {
                        hint = "您没有杀够怪物";
                        return hint;
                    }
                }
            }
            //当任务id符合系统消息控制表中的任务id时，就发一个系统消息给该玩家.
            SystemInfoService systemInfoService = new SystemInfoService();
            systemInfoService.sendSystemInfoByTaskId(pPk, Integer.parseInt(tId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hint;
    }

    /**
     * 执行杀怪
     */
    public boolean getShaGuaiDiscernService(String tId, int pPk) {
        try {
            TaskVO taskVO = TaskCache.getById(tId);
            RoleEntity roleInfo = RoleService.getRoleInfoById(pPk + "");
            CurTaskInfo curTaskInfo = roleInfo.getTaskInfo().getCurTaskList().getByZu(taskVO.getTZu());
            if (curTaskInfo != null) {
                if (curTaskInfo.getTKillingOk() != curTaskInfo.getTKillingNo()) {
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

}
